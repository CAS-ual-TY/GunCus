package de.cas_ual_ty.guncus.client;

import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.IProxy;
import de.cas_ual_ty.guncus.client.gui.GuiContainerGunTable;
import de.cas_ual_ty.guncus.entity.EntityBullet;
import de.cas_ual_ty.guncus.item.ItemAttachment;
import de.cas_ual_ty.guncus.item.ItemGun;
import de.cas_ual_ty.guncus.item.attachments.Accessory;
import de.cas_ual_ty.guncus.item.attachments.Accessory.Laser;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import de.cas_ual_ty.guncus.item.attachments.Optic;
import de.cas_ual_ty.guncus.itemgroup.ItemGroupShuffle;
import de.cas_ual_ty.guncus.network.MessageShoot;
import de.cas_ual_ty.guncus.registries.GunCusContainerTypes;
import de.cas_ual_ty.guncus.util.GunCusUtility;
import net.minecraft.block.Blocks;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ProxyClient implements IProxy
{
    public static final Supplier<KeyBinding> BUTTON_AIM = () -> Minecraft.getInstance().gameSettings.keyBindUseItem;
    public static final Supplier<KeyBinding> BUTTON_SHOOT = () -> Minecraft.getInstance().gameSettings.keyBindAttack;
    public static final Supplier<Boolean> BUTTON_AIM_DOWN = () -> ProxyClient.BUTTON_AIM.get().isKeyDown();
    public static final Supplier<Boolean> BUTTON_SHOOT_DOWN = () -> ProxyClient.BUTTON_SHOOT.get().isKeyDown();
    
    public static final ResourceLocation HITMARKER_TEXTURE = new ResourceLocation(GunCus.MOD_ID, "textures/gui/hitmarker.png");
    
    public static final int HITMARKER_RESET = 4;
    
    private static int hitmarkerTick = 0;
    private static int shootTime[] = new int[GunCusUtility.HANDS.length];
    private static int inaccuracyTime = 0;
    private static int prevSelectedMain = -1;
    
    @Override
    public void registerModEventListeners(IEventBus bus)
    {
        bus.addListener(this::modelBake);
        bus.addListener(this::modelRegistry);
    }
    
    @Override
    public void registerForgeEventListeners(IEventBus bus)
    {
        bus.addListener(this::clientTick);
        bus.addListener(this::fovUpdate);
        bus.addListener(this::renderGameOverlay);
        bus.addListener(this::renderWorldLast);
    }
    
    @Override
    public void init()
    {
        ScreenManager.registerFactory(GunCusContainerTypes.TYPE_GUN_TABLE, GuiContainerGunTable::new);
    }
    
    @Override
    public void addHitmarker(PlayerEntity player)
    {
        ProxyClient.hitmarkerTick = ProxyClient.HITMARKER_RESET;
    }
    
    @Override
    public PlayerEntity getPlayerFromContext(@Nullable Context context)
    {
        return ProxyClient.getClientPlayer();
    }
    
    @Override
    public void shot(ItemStack itemStack, ItemGun gun, PlayerEntity player, Hand hand)
    {
        int fireRate = gun.calcCurrentFireRate(gun.getCurrentAttachments(itemStack));
        ProxyClient.shootTime[hand == Hand.MAIN_HAND ? 0 : 1] = fireRate;
        ProxyClient.inaccuracyTime = Math.min(15, ProxyClient.inaccuracyTime + 2 + fireRate);
    }
    
    public void modelBake(ModelBakeEvent event)
    {
        ModelResourceLocation mrl;
        IBakedModel main;
        
        Optic optic;
        
        for (ItemAttachment attachment : ItemAttachment.ATTACHMENTS_LIST)
        {
            if (attachment.getType() == EnumAttachmentType.OPTIC)
            {
                optic = (Optic) attachment;
                
                if (optic != null && optic.canAim())
                {
                    mrl = new ModelResourceLocation(optic.getRegistryName().toString(), "inventory");
                    main = event.getModelRegistry().get(mrl);
                    event.getModelRegistry().put(mrl, new BakedModelOptic(main));
                }
            }
        }
        
        int i;
        int j;
        ItemAttachment attachment;
        
        IBakedModel[][] models; //These are the ItemAttachment models which will be passed onto the gun model for use
        
        for (ItemGun gun : ItemGun.GUNS_LIST) //Cycle through all guns
        {
            models = new IBakedModel[EnumAttachmentType.LENGTH][];
            
            for (EnumAttachmentType type : EnumAttachmentType.VALUES) //This represents the layers
            {
                i = type.getSlot();
                
                models[i] = new IBakedModel[gun.getAmmountForSlot(type)];
                
                for (j = 0; j < models[i].length; ++j) //Ammount of ItemAttachments for each layer
                {
                    attachment = gun.getAttachment(type, j);
                    
                    if (attachment != null && attachment.shouldLoadModel()) //Make sure its not null-attachment and the model is needed
                    {
                        models[i][j] = event.getModelRegistry().get(new ModelResourceLocation(gun.getRegistryName().toString() + "/" + attachment.getRegistryName().getPath(), "inventory")); //Add ItemAttachment model to the array
                    }
                }
            }
            
            mrl = new ModelResourceLocation(gun.getRegistryName().toString(), "inventory"); //This is the MRL of the main item (gun)
            
            main = event.getModelRegistry().get(mrl); //Get the model of the gun
            
            Matrix4f aimMatrix = event.getModelRegistry().get(new ModelResourceLocation(gun.getRegistryName().toString() + "/aim", "inventory")).handlePerspective(TransformType.FIRST_PERSON_RIGHT_HAND).getRight();
            
            event.getModelRegistry().put(mrl, new BakedModelGun(main, models, aimMatrix)); //Replace model of the gun with custom IBakedModel and pass all the ItemAttachment models to it
        }
    }
    
    public void modelRegistry(ModelRegistryEvent event)
    {
        int i;
        ItemAttachment attachment;
        
        for (ItemGun gun : ItemGun.GUNS_LIST)
        {
            ModelLoader.addSpecialModel(new ModelResourceLocation(gun.getRegistryName().toString() + "/gun", "inventory"));
            
            for (EnumAttachmentType type : EnumAttachmentType.VALUES) //All layers
            {
                for (i = 0; i < gun.getAmmountForSlot(type); ++i) //All attachments per layer
                {
                    attachment = gun.getAttachment(type, i);
                    
                    if (attachment != null && attachment.shouldLoadModel()) //null-attachment exists, as well as some which are not visible
                    {
                        ModelLoader.addSpecialModel(new ModelResourceLocation(gun.getRegistryName().toString() + "/" + attachment.getRegistryName().getPath(), "inventory")); //Add MRL to the list
                    }
                }
            }
            
            ModelLoader.addSpecialModel(new ModelResourceLocation(gun.getRegistryName().toString() + "/aim", "inventory"));
        }
    }
    
    public void clientTick(ClientTickEvent event)
    {
        if (event.phase == Phase.START)
        {
            PlayerEntity entityPlayer = ProxyClient.getClientPlayer();
            
            if (entityPlayer == null)
            {
                return;
            }
            
            ItemStack itemStack;
            ItemGun gun;
            
            int i = 0;
            for (Hand hand : GunCusUtility.HANDS)
            {
                if (ProxyClient.shootTime[i] > 0)
                {
                    --ProxyClient.shootTime[i];
                }
                
                itemStack = entityPlayer.getHeldItem(hand);
                
                if (itemStack.getItem() instanceof ItemGun)
                {
                    gun = (ItemGun) itemStack.getItem();
                    
                    if (gun.getNBTIsReloading(itemStack))
                    {
                        ProxyClient.shootTime[i] = 1;
                    }
                    else if (hand == Hand.MAIN_HAND)
                    {
                        if (entityPlayer.inventory.currentItem != ProxyClient.prevSelectedMain)
                        {
                            ProxyClient.shootTime[i] += gun.calcCurrentSwitchTime(gun.getCurrentAttachments(itemStack));
                        }
                    }
                }
                
                ++i;
            }
            
            ProxyClient.prevSelectedMain = entityPlayer.inventory.currentItem;
            
            if (ProxyClient.inaccuracyTime > 0)
            {
                --ProxyClient.inaccuracyTime;
            }
            
            // ---
            
            if (ProxyClient.BUTTON_SHOOT_DOWN.get() && (entityPlayer.getHeldItemMainhand().getItem() instanceof ItemGun || entityPlayer.getHeldItemOffhand().getItem() instanceof ItemGun))
            {
                boolean aiming = false;
                
                if (ProxyClient.BUTTON_AIM_DOWN.get() && !entityPlayer.isSprinting())
                {
                    if (entityPlayer.getHeldItemMainhand().getItem() instanceof ItemGun && entityPlayer.getHeldItemOffhand().isEmpty())
                    {
                        itemStack = entityPlayer.getHeldItemMainhand();
                        gun = (ItemGun) itemStack.getItem();
                        
                        if (gun.getNBTCanAimGun(itemStack))
                        {
                            Optic optic = gun.<Optic> getAttachmentCalled(itemStack, EnumAttachmentType.OPTIC);
                            aiming = optic.canAim();
                        }
                    }
                }
                
                i = 0;
                int handsInt = 0;
                
                for (i = 0; i < ProxyClient.shootTime.length; ++i)
                {
                    if (entityPlayer.getHeldItem(GunCusUtility.HANDS[i]).getItem() instanceof ItemGun && ProxyClient.shootTime[i] <= 0)
                    {
                        handsInt += i + 1;
                    }
                }
                
                if (handsInt > 0)
                {
                    GunCus.channel.sendToServer(new MessageShoot(aiming, ProxyClient.inaccuracyTime, handsInt));
                    ItemGun.tryShoot(entityPlayer, aiming, ProxyClient.inaccuracyTime, GunCusUtility.intToHands(handsInt));
                }
            }
            
            // ---
            
            for (ItemGroupShuffle group : ItemGroupShuffle.GROUPS_LIST)
            {
                group.tick();
            }
        }
        else if (event.phase == Phase.END)
        {
            if (ProxyClient.hitmarkerTick > 0)
            {
                --ProxyClient.hitmarkerTick;
            }
        }
    }
    
    public void fovUpdate(FOVUpdateEvent event)
    {
        PlayerEntity entityPlayer = ProxyClient.getClientPlayer();
        
        if (entityPlayer != null && ProxyClient.BUTTON_AIM_DOWN.get())
        {
            if (!entityPlayer.isSprinting())
            {
                Optic optic = null;
                float modifier = 1F;
                float extra = 0F;
                
                if (entityPlayer.getHeldItemMainhand().getItem() instanceof ItemGun || entityPlayer.getHeldItemOffhand().getItem() instanceof ItemGun)
                {
                    if (entityPlayer.getHeldItemOffhand().isEmpty())
                    {
                        ItemStack itemStack = entityPlayer.getHeldItemMainhand();
                        ItemGun gun = (ItemGun) itemStack.getItem();
                        
                        if (gun.getNBTCanAimGun(itemStack))
                        {
                            optic = gun.<Optic> getAttachmentCalled(itemStack, EnumAttachmentType.OPTIC);
                        }
                        
                        if (optic != null && gun.isNBTAccessoryTurnedOn(itemStack) && !gun.getAttachment(itemStack, EnumAttachmentType.ACCESSORY).isDefault())
                        {
                            Accessory accessory = gun.<Accessory> getAttachmentCalled(itemStack, EnumAttachmentType.ACCESSORY);
                            
                            if (optic.isCompatibleWithMagnifiers())
                            {
                                modifier = accessory.getZoomModifier();
                            }
                            
                            if (optic.isCompatibleWithExtraZoom())
                            {
                                extra = accessory.getExtraZoom();
                            }
                        }
                    }
                }
                else if (entityPlayer.getHeldItemMainhand().getItem() instanceof Optic)
                {
                    ItemStack itemStack = entityPlayer.getHeldItemMainhand();
                    optic = (Optic) itemStack.getItem();
                }
                
                if (optic != null && optic.canAim())
                {
                    event.setNewfov(ProxyClient.calculateFov(optic.getZoom() * modifier + 0.1F + extra, event.getFov()));
                }
            }
        }
    }
    
    public static float calculateFov(float zoom, float fov)
    {
        return (float) Math.atan(Math.tan(fov) / zoom);
    }
    
    public void renderGameOverlay(RenderGameOverlayEvent event)
    {
        PlayerEntity entityPlayer = ProxyClient.getClientPlayer();
        
        if (event.getType() == ElementType.CROSSHAIRS && entityPlayer != null)
        {
            Optic optic = null;
            
            if (entityPlayer.getHeldItemMainhand().getItem() instanceof ItemGun || entityPlayer.getHeldItemOffhand().getItem() instanceof ItemGun)
            {
                if (entityPlayer.getHeldItemOffhand().isEmpty())
                {
                    ItemStack itemStack = entityPlayer.getHeldItemMainhand();
                    ItemGun gun = (ItemGun) itemStack.getItem();
                    
                    if (gun.getNBTCanAimGun(itemStack))
                    {
                        optic = gun.<Optic> getAttachmentCalled(itemStack, EnumAttachmentType.OPTIC);
                    }
                }
                
                event.setCanceled(true);
            }
            else if (entityPlayer.getHeldItemMainhand().getItem() instanceof Optic)
            {
                optic = (Optic) entityPlayer.getHeldItemMainhand().getItem();
            }
            
            if (optic != null && optic.canAim() && !entityPlayer.isSprinting() && ProxyClient.BUTTON_AIM_DOWN.get())
            {
                ProxyClient.drawSight(optic, event.getWindow());
                
                if (!event.isCanceled())
                {
                    event.setCanceled(true);
                }
            }
            
            // ---
            
            if (ProxyClient.hitmarkerTick > 0)
            {
                ProxyClient.drawHitmarker(event.getWindow());
            }
        }
    }
    
    public static void drawSight(Optic optic, MainWindow sr)
    {
        ProxyClient.drawDrawFullscreenImage(optic.getOverlay(), 1024, 256, sr);
    }
    
    public static void drawHitmarker(MainWindow sr)
    {
        ProxyClient.drawDrawFullscreenImage(ProxyClient.HITMARKER_TEXTURE, 1024, 256, sr);
    }
    
    public static void drawDrawFullscreenImage(ResourceLocation rl, int texWidth, int texHeight, MainWindow sr)
    {
        GlStateManager.pushMatrix();
        
        GlStateManager.enableBlend();
        GlStateManager.disableDepthTest();
        GlStateManager.depthMask(false);
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color4f(1F, 1F, 1F, 1F);
        GlStateManager.disableAlphaTest();
        
        Minecraft.getInstance().getTextureManager().bindTexture(rl);
        
        double x = sr.getScaledWidth();
        double y = sr.getScaledHeight();
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder b = tessellator.getBuffer();
        
        b.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        
        b.pos(x * 0.5D - 2 * y, y, -90D).tex(0D, 1D).endVertex();
        b.pos(x * 0.5D + 2 * y, y, -90D).tex(1D, 1D).endVertex();
        b.pos(x * 0.5D + 2 * y, 0D, -90D).tex(1D, 0D).endVertex();
        b.pos(x * 0.5D - 2 * y, 0D, -90D).tex(0D, 0D).endVertex();
        
        tessellator.draw();
        
        GlStateManager.depthMask(true);
        GlStateManager.enableDepthTest();
        GlStateManager.enableAlphaTest();
        
        GlStateManager.popMatrix();
    }
    
    public void renderWorldLast(RenderWorldLastEvent event)
    {
        PlayerEntity clientPlayer = ProxyClient.getClientPlayer();
        
        if (clientPlayer != null)
        {
            World world = clientPlayer.world;
            
            ItemStack itemStack;
            ItemGun gun;
            Accessory accessory;
            Laser laser;
            
            Vec3d start;
            Vec3d end;
            
            Vec3d clientPos = new Vec3d(clientPlayer.posX, clientPlayer.posY + clientPlayer.getEyeHeight(), clientPlayer.posZ);
            Vec3d clientLook = clientPlayer.getLookVec().normalize();
            Vec3d handOff;
            
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder b = tessellator.getBuffer();
            
            for (PlayerEntity entityPlayer : world.getPlayers())
            {
                if (entityPlayer.isAlive())
                {
                    for (Hand hand : GunCusUtility.HANDS)
                    {
                        accessory = null;
                        itemStack = entityPlayer.getHeldItem(hand);
                        
                        if (itemStack.getItem() instanceof ItemGun)
                        {
                            gun = (ItemGun) itemStack.getItem();
                            
                            if (gun.isNBTAccessoryTurnedOn(itemStack))
                            {
                                accessory = gun.<Accessory> getAttachmentCalled(itemStack, EnumAttachmentType.ACCESSORY);
                            }
                        }
                        else if (itemStack.getItem() instanceof Accessory)
                        {
                            accessory = (Accessory) itemStack.getItem();
                        }
                        
                        if (accessory != null && accessory.getLaser() != null)
                        {
                            laser = accessory.getLaser();
                            
                            handOff = ProxyClient.getOffsetForHand(entityPlayer, hand);
                            
                            start = clientPos.add(handOff);
                            end = start.add(clientLook.scale(laser.getMaxRange()));
                            
                            end = ProxyClient.findHit(world, entityPlayer, start, end);
                            
                            start = start.subtract(clientPos);
                            end = end.subtract(clientPos);
                            
                            GlStateManager.disableTexture();
                            
                            if (laser.isPoint() && !ProxyClient.tmpHitNothing)
                            {
                                ProxyClient.renderLaserPoint(b, tessellator, laser, start, end);
                            }
                            
                            if (laser.isBeam())
                            {
                                ProxyClient.renderLaserBeam(b, tessellator, laser, start.add(ProxyClient.getVectorForRotation(entityPlayer.rotationPitch + -345F, entityPlayer.rotationYaw)), end);
                            }
                            
                            GlStateManager.enableTexture();
                        }
                    }
                }
            }
        }
    }
    
    public static void renderLaserPoint(BufferBuilder b, Tessellator tessellator, Laser laser, Vec3d start, Vec3d end)
    {
        b.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        
        final double size = 0.05D;
        
        b.pos(end.x + size, end.y + size, end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(end.x + size, end.y + size, end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(end.x - size, end.y + size, end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(end.x - size, end.y + size, end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        
        b.pos(end.x + size, end.y - size, end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(end.x - size, end.y - size, end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(end.x - size, end.y - size, end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(end.x + size, end.y - size, end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        
        b.pos(end.x + size, end.y - size, end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(end.x + size, end.y - size, end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(end.x + size, end.y + size, end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(end.x + size, end.y + size, end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        
        b.pos(end.x - size, end.y - size, end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(end.x - size, end.y + size, end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(end.x - size, end.y + size, end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(end.x - size, end.y - size, end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        
        b.pos(end.x - size, end.y - size, end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(end.x + size, end.y - size, end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(end.x + size, end.y + size, end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(end.x - size, end.y + size, end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        
        b.pos(end.x - size, end.y - size, end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(end.x - size, end.y + size, end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(end.x + size, end.y + size, end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(end.x + size, end.y - size, end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        
        tessellator.draw();
    }
    
    public static void renderLaserBeam(BufferBuilder b, Tessellator tessellator, Laser laser, Vec3d start, Vec3d end)
    {
        b.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        
        b.pos(start.x, start.y, start.z).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(end.x, end.y, end.z).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        
        tessellator.draw();
    }
    
    public static Vec3d getVectorForRotation(float pitch, float yaw)
    {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3d(f1 * f2, f3, f * f2);
    }
    
    public static Vec3d getOffsetForHandRaw(PlayerEntity entityPlayer, Hand hand)
    {
        Vec3d vec = ProxyClient.getVectorForRotation(entityPlayer.rotationPitch + 1, entityPlayer.rotationYaw + 90F);
        
        if (hand == Hand.OFF_HAND)
        {
            vec = vec.scale(-1);
        }
        
        if (entityPlayer.rotationPitch >= 89)
        {
            vec = vec.scale(-1);
        }
        
        return new Vec3d(vec.x, 0, vec.z).normalize().scale(0.4D);
    }
    
    public static Vec3d getOffsetForHand(PlayerEntity entityPlayer, Hand hand)
    {
        Vec3d vec = ProxyClient.getOffsetForHandRaw(entityPlayer, hand);
        
        return vec.add(ProxyClient.getVectorForRotation(entityPlayer.rotationPitch, entityPlayer.rotationYaw).scale(0.4D));
    }
    
    private static boolean tmpHitNothing = false;
    
    public static Vec3d findHit(World world, PlayerEntity entityPlayer, Vec3d start, Vec3d end)
    {
        ProxyClient.tmpHitNothing = false;
        
        BlockRayTraceResult resultBlock = ProxyClient.findBlockOnPath(world, entityPlayer, start, end);
        EntityRayTraceResult resultEntity = ProxyClient.findEntityOnPath(world, entityPlayer, start, end);
        
        if (resultEntity != null)
        {
            double rangeBlockSq = resultBlock.getHitVec().squareDistanceTo(start);
            double rangeEntitySq = resultEntity.getHitVec().squareDistanceTo(start);
            
            if (rangeBlockSq < rangeEntitySq)
            {
                return resultBlock.getHitVec();
            }
            else
            {
                return resultEntity.getHitVec();
            }
            
        }
        else
        {
            ProxyClient.tmpHitNothing = world.getBlockState(resultBlock.getPos()).getBlock() == Blocks.AIR;
            return resultBlock.getHitVec();
        }
    }
    
    public static BlockRayTraceResult findBlockOnPath(World world, PlayerEntity entityPlayer, Vec3d start, Vec3d end)
    {
        return world.rayTraceBlocks(new RayTraceContext(start, end, BlockMode.COLLIDER, FluidMode.NONE, entityPlayer));
    }
    
    public static EntityRayTraceResult findEntityOnPath(World world, PlayerEntity entityPlayer, Vec3d start, Vec3d end)
    {
        EntityRayTraceResult result = null;
        Optional<Vec3d> opt;
        
        double rangeSq = 0;
        double currentRangeSq;
        
        for (Entity entity : world.getEntitiesInAABBexcluding(entityPlayer, GunCusUtility.aabbFromVec3ds(start, end), (entity1) -> true))
        {
            if (entity != null && (entity.getEntityId() != entityPlayer.getEntityId()) && !(entity instanceof EntityBullet))
            {
                AxisAlignedBB axisalignedbb = entity.getBoundingBox().grow(0.30000001192092896D);
                
                opt = axisalignedbb.rayTrace(start, end);
                
                if (opt.isPresent())
                {
                    currentRangeSq = start.squareDistanceTo(entity.getPositionVec());
                    
                    if (currentRangeSq < rangeSq || result == null)
                    {
                        result = new EntityRayTraceResult(entity, opt.get());
                        rangeSq = currentRangeSq;
                    }
                }
            }
        }
        return result;
    }
    
    @Nullable
    public static PlayerEntity getClientPlayer()
    {
        return Minecraft.getInstance().player;
    }
}
