package de.cas_ual_ty.guncus.client;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.IProxy;
import de.cas_ual_ty.guncus.client.gui.GuiContainerBulletMaker;
import de.cas_ual_ty.guncus.client.gui.GuiContainerGunMaker;
import de.cas_ual_ty.guncus.client.gui.GuiContainerGunTable;
import de.cas_ual_ty.guncus.item.ItemAttachment;
import de.cas_ual_ty.guncus.item.ItemGun;
import de.cas_ual_ty.guncus.item.attachments.Accessory;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import de.cas_ual_ty.guncus.item.attachments.Optic;
import de.cas_ual_ty.guncus.itemgroup.ItemGroupShuffle;
import de.cas_ual_ty.guncus.network.MessageShoot;
import de.cas_ual_ty.guncus.registries.GunCusContainerTypes;
import de.cas_ual_ty.guncus.util.GunCusUtility;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ProxyClient implements IProxy
{
    public static final Supplier<KeyBinding> BUTTON_AIM = () -> ProxyClient.getMC().gameSettings.keyBindUseItem;
    public static final Supplier<KeyBinding> BUTTON_SHOOT = () -> ProxyClient.getMC().gameSettings.keyBindAttack;
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
        bus.addListener(this::clientSetup);
        bus.addListener(this::modelBake);
        bus.addListener(this::modelRegistry);
    }
    
    @Override
    public void registerForgeEventListeners(IEventBus bus)
    {
        bus.addListener(this::clientTick);
        bus.addListener(this::fovUpdate);
        bus.addListener(this::renderGameOverlayPre);
        bus.register(new LaserRenderer());
    }
    
    @Override
    public void init()
    {
        ScreenManager.registerFactory(GunCusContainerTypes.GUN_TABLE, GuiContainerGunTable::new);
        ScreenManager.registerFactory(GunCusContainerTypes.GUN_MAKER, GuiContainerGunMaker::new);
        ScreenManager.registerFactory(GunCusContainerTypes.BULLET_MAKER, GuiContainerBulletMaker::new);
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
    
    public void clientSetup(FMLClientSetupEvent event)
    {
        // TODO
    }
    
    public void modelBake(ModelBakeEvent event)
    {
        ModelResourceLocation mrl;
        IBakedModel main;
        
        Optic optic;
        
        for(ItemAttachment attachment : ItemAttachment.ATTACHMENTS_LIST)
        {
            if(attachment.getType() == EnumAttachmentType.OPTIC)
            {
                optic = (Optic)attachment;
                
                if(optic != null && optic.canAim())
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
        
        for(ItemGun gun : ItemGun.GUNS_LIST) //Cycle through all guns
        {
            models = new IBakedModel[EnumAttachmentType.LENGTH][];
            
            for(EnumAttachmentType type : EnumAttachmentType.VALUES) //This represents the layers
            {
                i = type.getSlot();
                
                models[i] = new IBakedModel[gun.getAmmountForSlot(type)];
                
                for(j = 0; j < models[i].length; ++j) //Ammount of ItemAttachments for each layer
                {
                    attachment = gun.getAttachment(type, j);
                    
                    if(attachment != null && attachment.shouldLoadModel()) //Make sure its not null-attachment and the model is needed
                    {
                        models[i][j] = event.getModelRegistry().get(new ModelResourceLocation(gun.getRegistryName().toString() + "/" + attachment.getRegistryName().getPath(), "inventory")); //Add ItemAttachment model to the array
                    }
                }
            }
            
            mrl = new ModelResourceLocation(gun.getRegistryName().toString(), "inventory"); //This is the MRL of the main item (gun)
            
            main = event.getModelRegistry().get(mrl); //Get the model of the gun
            
            MatrixStack stack = new MatrixStack();
            event.getModelRegistry().get(new ModelResourceLocation(gun.getRegistryName().toString() + "/aim", "inventory")).handlePerspective(TransformType.FIRST_PERSON_RIGHT_HAND, stack);
            Matrix4f aimMatrix = stack.getLast().getMatrix();
            
            event.getModelRegistry().put(mrl, new BakedModelGun(main, models, aimMatrix)); //Replace model of the gun with custom IBakedModel and pass all the ItemAttachment models to it
        }
    }
    
    public void modelRegistry(ModelRegistryEvent event)
    {
        int i;
        ItemAttachment attachment;
        
        for(ItemGun gun : ItemGun.GUNS_LIST)
        {
            ModelLoader.addSpecialModel(new ModelResourceLocation(gun.getRegistryName().toString() + "/gun", "inventory"));
            
            for(EnumAttachmentType type : EnumAttachmentType.VALUES) //All layers
            {
                for(i = 0; i < gun.getAmmountForSlot(type); ++i) //All attachments per layer
                {
                    attachment = gun.getAttachment(type, i);
                    
                    if(attachment != null && attachment.shouldLoadModel()) //null-attachment exists, as well as some which are not visible
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
        if(event.phase == Phase.START)
        {
            PlayerEntity entityPlayer = ProxyClient.getClientPlayer();
            
            if(entityPlayer == null)
            {
                return;
            }
            
            ItemStack itemStack;
            ItemGun gun;
            
            int i = 0;
            for(Hand hand : GunCusUtility.HANDS)
            {
                if(ProxyClient.shootTime[i] > 0)
                {
                    --ProxyClient.shootTime[i];
                }
                
                itemStack = entityPlayer.getHeldItem(hand);
                
                if(itemStack.getItem() instanceof ItemGun)
                {
                    gun = (ItemGun)itemStack.getItem();
                    
                    if(gun.getNBTIsReloading(itemStack))
                    {
                        ProxyClient.shootTime[i] = 1;
                    }
                    else if(hand == Hand.MAIN_HAND)
                    {
                        if(entityPlayer.inventory.currentItem != ProxyClient.prevSelectedMain)
                        {
                            ProxyClient.shootTime[i] += gun.calcCurrentSwitchTime(gun.getCurrentAttachments(itemStack));
                        }
                    }
                }
                
                ++i;
            }
            
            ProxyClient.prevSelectedMain = entityPlayer.inventory.currentItem;
            
            if(ProxyClient.inaccuracyTime > 0)
            {
                --ProxyClient.inaccuracyTime;
            }
            
            // ---
            
            if(ProxyClient.BUTTON_SHOOT_DOWN.get() && (entityPlayer.getHeldItemMainhand().getItem() instanceof ItemGun || entityPlayer.getHeldItemOffhand().getItem() instanceof ItemGun))
            {
                boolean aiming = false;
                
                if(ProxyClient.BUTTON_AIM_DOWN.get() && !entityPlayer.isSprinting())
                {
                    if(entityPlayer.getHeldItemMainhand().getItem() instanceof ItemGun && entityPlayer.getHeldItemOffhand().isEmpty())
                    {
                        itemStack = entityPlayer.getHeldItemMainhand();
                        gun = (ItemGun)itemStack.getItem();
                        
                        if(gun.getNBTCanAimGun(itemStack))
                        {
                            Optic optic = gun.<Optic>getAttachmentCalled(itemStack, EnumAttachmentType.OPTIC);
                            aiming = optic.canAim();
                        }
                    }
                }
                
                i = 0;
                int handsInt = 0;
                
                for(i = 0; i < ProxyClient.shootTime.length; ++i)
                {
                    if(entityPlayer.getHeldItem(GunCusUtility.HANDS[i]).getItem() instanceof ItemGun && ProxyClient.shootTime[i] <= 0)
                    {
                        handsInt += i + 1;
                    }
                }
                
                if(handsInt > 0)
                {
                    GunCus.channel.sendToServer(new MessageShoot(aiming, ProxyClient.inaccuracyTime, handsInt));
                    ItemGun.tryShoot(entityPlayer, aiming, ProxyClient.inaccuracyTime, GunCusUtility.intToHands(handsInt));
                }
            }
            
            // ---
            
            for(ItemGroupShuffle group : ItemGroupShuffle.GROUPS_LIST)
            {
                group.tick();
            }
        }
        else if(event.phase == Phase.END)
        {
            if(ProxyClient.hitmarkerTick > 0)
            {
                --ProxyClient.hitmarkerTick;
            }
        }
    }
    
    public void fovUpdate(FOVUpdateEvent event)
    {
        PlayerEntity entityPlayer = ProxyClient.getClientPlayer();
        
        if(entityPlayer != null && ProxyClient.BUTTON_AIM_DOWN.get())
        {
            if(!entityPlayer.isSprinting())
            {
                Optic optic = null;
                float modifier = 1F;
                float extra = 0F;
                
                if(entityPlayer.getHeldItemMainhand().getItem() instanceof ItemGun || entityPlayer.getHeldItemOffhand().getItem() instanceof ItemGun)
                {
                    if(entityPlayer.getHeldItemOffhand().isEmpty())
                    {
                        ItemStack itemStack = entityPlayer.getHeldItemMainhand();
                        ItemGun gun = (ItemGun)itemStack.getItem();
                        
                        if(gun.getNBTCanAimGun(itemStack))
                        {
                            optic = gun.<Optic>getAttachmentCalled(itemStack, EnumAttachmentType.OPTIC);
                        }
                        
                        if(optic != null && gun.isNBTAccessoryTurnedOn(itemStack) && !gun.getAttachment(itemStack, EnumAttachmentType.ACCESSORY).isDefault())
                        {
                            Accessory accessory = gun.<Accessory>getAttachmentCalled(itemStack, EnumAttachmentType.ACCESSORY);
                            
                            if(optic.isCompatibleWithMagnifiers())
                            {
                                modifier = accessory.getZoomModifier();
                            }
                            
                            if(optic.isCompatibleWithExtraZoom())
                            {
                                extra = accessory.getExtraZoom();
                            }
                        }
                    }
                }
                else if(entityPlayer.getHeldItemMainhand().getItem() instanceof Optic)
                {
                    ItemStack itemStack = entityPlayer.getHeldItemMainhand();
                    optic = (Optic)itemStack.getItem();
                }
                
                if(optic != null && optic.canAim())
                {
                    event.setNewfov(ProxyClient.calculateFov(optic.getZoom() * modifier + 0.1F + extra, event.getFov()));
                }
            }
        }
    }
    
    public static float calculateFov(float zoom, float fov)
    {
        return (float)Math.atan(Math.tan(fov) / zoom);
    }
    
    public void renderGameOverlayPre(RenderGameOverlayEvent.Pre event)
    {
        PlayerEntity entityPlayer = ProxyClient.getClientPlayer();
        ItemStack itemStack;
        ItemGun gun;
        
        if(event.getType() == ElementType.CROSSHAIRS && entityPlayer != null)
        {
            Optic optic = null;
            
            if(entityPlayer.getHeldItemMainhand().getItem() instanceof ItemGun || entityPlayer.getHeldItemOffhand().getItem() instanceof ItemGun)
            {
                if(entityPlayer.getHeldItemOffhand().isEmpty())
                {
                    itemStack = entityPlayer.getHeldItemMainhand();
                    gun = (ItemGun)itemStack.getItem();
                    
                    if(gun.getNBTCanAimGun(itemStack))
                    {
                        optic = gun.<Optic>getAttachmentCalled(itemStack, EnumAttachmentType.OPTIC);
                    }
                }
                
                event.setCanceled(true);
            }
            else if(entityPlayer.getHeldItemMainhand().getItem() instanceof Optic)
            {
                optic = (Optic)entityPlayer.getHeldItemMainhand().getItem();
            }
            
            if(optic != null && optic.canAim() && !entityPlayer.isSprinting() && ProxyClient.BUTTON_AIM_DOWN.get())
            {
                ProxyClient.drawSight(event.getMatrixStack(), optic, event.getWindow());
                
                if(!event.isCanceled())
                {
                    event.setCanceled(true);
                }
            }
            
            // ---
            
            Accessory accessory;
            Vector3d start = new Vector3d(entityPlayer.getPosX(), entityPlayer.getPosY() + entityPlayer.getEyeHeight(), entityPlayer.getPosZ());
            Vector3d end;
            Vector3d hit;
            
            for(Hand hand : GunCusUtility.HANDS)
            {
                itemStack = entityPlayer.getHeldItem(hand);
                
                if(itemStack.getItem() instanceof ItemGun)
                {
                    gun = (ItemGun)itemStack.getItem();
                    accessory = gun.getAttachmentCalled(itemStack, EnumAttachmentType.ACCESSORY);
                }
                else if(itemStack.getItem() instanceof Accessory)
                {
                    accessory = (Accessory)itemStack.getItem();
                }
                else
                {
                    accessory = (Accessory)EnumAttachmentType.ACCESSORY.getDefault();
                }
                
                if(accessory.getLaser() != null && accessory.getLaser().isRangeFinder())
                {
                    end = start.add(entityPlayer.getLookVec().normalize().scale(accessory.getLaser().getMaxRange()));
                    hit = LaserRenderer.findHit(entityPlayer.world, entityPlayer, start, end);
                    
                    hit = hit.subtract(start);
                    
                    ProxyClient.drawRangeFinder(event.getMatrixStack(), event.getWindow(), hand, hit.length());
                }
            }
            
            // ---
            
            if(ProxyClient.hitmarkerTick > 0)
            {
                ProxyClient.drawHitmarker(event.getMatrixStack(), event.getWindow());
            }
        }
    }
    
    public static void drawSight(MatrixStack ms, Optic optic, MainWindow sr)
    {
        ProxyClient.drawDrawFullscreenImage(ms, optic.getOverlay(), 1024, 256, sr);
    }
    
    public static void drawRangeFinder(MatrixStack ms, MainWindow sr, Hand hand, double range)
    {
        ProxyClient.drawRangeFinder(ms, sr, hand, (int)range + "");
    }
    
    public static void drawRangeFinder(MatrixStack ms, MainWindow sr, Hand hand, String text)
    {
        ms.push();
        RenderSystem.enableBlend();
        RenderSystem.color4f(1F, 1F, 1F, 1F);
        
        int off = 8;
        FontRenderer font = ProxyClient.getFontRenderer();
        off = hand == Hand.OFF_HAND ? -(font.getStringWidth(text) + 1 + off) : off;
        
        font.drawStringWithShadow(ms, text, sr.getScaledWidth() / 2 + off, sr.getScaledHeight() / 2, 0xFFFFFF);
        
        RenderSystem.disableBlend();
        ms.pop();
    }
    
    public static void drawHitmarker(MatrixStack ms, MainWindow sr)
    {
        ProxyClient.drawDrawFullscreenImage(ms, ProxyClient.HITMARKER_TEXTURE, 1024, 256, sr);
    }
    
    public static void drawDrawFullscreenImage(MatrixStack ms, ResourceLocation rl, int texWidth, int texHeight, MainWindow sr)
    {
        ms.push();
        
        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1F, 1F, 1F, 1F);
        RenderSystem.disableAlphaTest();
        
        ProxyClient.getMC().getTextureManager().bindTexture(rl);
        
        int x = sr.getScaledWidth();
        int y = sr.getScaledHeight();
        AbstractGui.blit(ms, (x - texWidth) / 2, (y - texHeight) / 2, texWidth, texHeight, 0, 0, texWidth, texHeight, texWidth, texHeight);
        
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.enableAlphaTest();
        
        ms.pop();
    }
    
    public static Minecraft getMC()
    {
        return Minecraft.getInstance();
    }
    
    @SuppressWarnings("resource")
    @Nullable
    public static PlayerEntity getClientPlayer()
    {
        return ProxyClient.getMC().player;
    }
    
    @SuppressWarnings("resource")
    public static FontRenderer getFontRenderer()
    {
        return ProxyClient.getMC().fontRenderer;
    }
}
