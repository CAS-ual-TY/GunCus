package de.cas_ual_ty.guncus.client;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;

import de.cas_ual_ty.guncus.GunCus;
import de.cas_ual_ty.guncus.IProxy;
import de.cas_ual_ty.guncus.client.gui.GunTableScreen;
import de.cas_ual_ty.guncus.client.gui.MakerScreen;
import de.cas_ual_ty.guncus.item.AttachmentItem;
import de.cas_ual_ty.guncus.item.GunItem;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import de.cas_ual_ty.guncus.item.attachments.Optic;
import de.cas_ual_ty.guncus.itemgroup.ShuffleItemGroup;
import de.cas_ual_ty.guncus.network.ShootMessage;
import de.cas_ual_ty.guncus.registries.GunCusContainerTypes;
import de.cas_ual_ty.guncus.util.GunCusUtility;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ClientProxy implements IProxy
{
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
        bus.register(new HitmarkerRenderer());
        bus.register(new LaserRenderer());
        bus.register(new SightRenderer());
    }
    
    @Override
    public void init()
    {
        ScreenManager.registerFactory(GunCusContainerTypes.GUN_TABLE, GunTableScreen::new);
        ScreenManager.registerFactory(GunCusContainerTypes.GUN_MAKER, MakerScreen::new);
        ScreenManager.registerFactory(GunCusContainerTypes.BULLET_MAKER, MakerScreen::new);
        ScreenManager.registerFactory(GunCusContainerTypes.OPTIC_MAKER, MakerScreen::new);
        ScreenManager.registerFactory(GunCusContainerTypes.ACCESSORY_MAKER, MakerScreen::new);
        ScreenManager.registerFactory(GunCusContainerTypes.BARREL_MAKER, MakerScreen::new);
        ScreenManager.registerFactory(GunCusContainerTypes.UNDERBARREL_MAKER, MakerScreen::new);
        ScreenManager.registerFactory(GunCusContainerTypes.AUXILIARY_MAKER, MakerScreen::new);
        ScreenManager.registerFactory(GunCusContainerTypes.AMMO_MAKER, MakerScreen::new);
        ScreenManager.registerFactory(GunCusContainerTypes.MAGAZINE_MAKER, MakerScreen::new);
        ScreenManager.registerFactory(GunCusContainerTypes.PAINT_MAKER, MakerScreen::new);
    }
    
    @Override
    public void addHitmarker(PlayerEntity player)
    {
        HitmarkerRenderer.addHitmarker();
    }
    
    @Override
    public PlayerEntity getPlayerFromContext(@Nullable Context context)
    {
        return ClientProxy.getClientPlayer();
    }
    
    @Override
    public void shot(ItemStack itemStack, GunItem gun, PlayerEntity player, Hand hand)
    {
        int fireRate = gun.calcCurrentFireRate(gun.getCurrentAttachments(itemStack));
        ClientProxy.shootTime[hand == Hand.MAIN_HAND ? 0 : 1] = fireRate;
        ClientProxy.inaccuracyTime = Math.min(15, ClientProxy.inaccuracyTime + 2 + fireRate);
    }
    
    public void modelBake(ModelBakeEvent event)
    {
        ModelResourceLocation mrl;
        IBakedModel main;
        
        Optic optic;
        
        for(AttachmentItem attachment : AttachmentItem.ALL_ATTACHMENTS_LIST)
        {
            if(attachment.getType() == EnumAttachmentType.OPTIC)
            {
                optic = (Optic)attachment;
                
                if(optic != null && optic.canAim() && !optic.isDefault())
                {
                    mrl = new ModelResourceLocation(optic.getRegistryName().toString(), "inventory");
                    main = event.getModelRegistry().get(mrl);
                    event.getModelRegistry().put(mrl, new BakedModelOptic(main));
                }
            }
        }
        
        int i;
        int j;
        AttachmentItem attachment;
        
        IBakedModel[][] models; //These are the ItemAttachment models which will be passed onto the gun model for use
        
        for(GunItem gun : GunItem.ALL_GUNS_LIST) //Cycle through all guns
        {
            models = new IBakedModel[EnumAttachmentType.LENGTH][];
            
            for(EnumAttachmentType type : EnumAttachmentType.RENDER_ORDER) //This represents the layers
            {
                i = type.getSlot();
                
                models[i] = new IBakedModel[gun.getAmmountForSlot(type)];
                
                for(j = 0; j < models[i].length; ++j) //Ammount of ItemAttachments for each layer
                {
                    attachment = gun.getAttachment(type, j);
                    
                    if(attachment != null) //Make sure its not null-attachment and the model is needed
                    {
                        models[i][j] = event.getModelRegistry().get(new ModelResourceLocation(gun.getRegistryName().toString() + "/" + attachment.getRegistryName().getPath(), "inventory")); //Add ItemAttachment model to the array
                    }
                }
            }
            
            main = models[EnumAttachmentType.PAINT.getSlot()][0]; //Get the model of the gun
            event.getModelRegistry().put(new ModelResourceLocation(gun.getRegistryName().toString(), "inventory"), new BakedModelGun(gun, main, models)); //Replace model of the gun with custom IBakedModel and pass all the ItemAttachment models to it
        }
    }
    
    public void modelRegistry(ModelRegistryEvent event)
    {
        int i;
        AttachmentItem attachment;
        
        for(GunItem gun : GunItem.ALL_GUNS_LIST)
        {
            for(EnumAttachmentType type : EnumAttachmentType.RENDER_ORDER) //All layers
            {
                ModelLoader.addSpecialModel(new ModelResourceLocation(gun.getRegistryName().toString() + "/" + type.getDefaultResource(), "inventory"));
                
                for(i = 0; i < gun.getAmmountForSlot(type); ++i) //All attachments per layer
                {
                    attachment = gun.getAttachment(type, i);
                    ModelLoader.addSpecialModel(new ModelResourceLocation(gun.getRegistryName().toString() + "/" + attachment.getRegistryName().getPath(), "inventory")); //Add MRL to the list
                }
            }
        }
    }
    
    public void clientTick(ClientTickEvent event)
    {
        if(event.phase == Phase.START)
        {
            PlayerEntity entityPlayer = ClientProxy.getClientPlayer();
            
            if(entityPlayer == null)
            {
                return;
            }
            
            ItemStack itemStack;
            GunItem gun;
            
            int i = 0;
            for(Hand hand : GunCusUtility.HANDS)
            {
                if(ClientProxy.shootTime[i] > 0)
                {
                    --ClientProxy.shootTime[i];
                }
                
                itemStack = entityPlayer.getHeldItem(hand);
                
                if(itemStack.getItem() instanceof GunItem)
                {
                    gun = (GunItem)itemStack.getItem();
                    
                    if(gun.getNBTIsReloading(itemStack))
                    {
                        ClientProxy.shootTime[i] = 1;
                    }
                    else if(hand == Hand.MAIN_HAND)
                    {
                        if(entityPlayer.inventory.currentItem != ClientProxy.prevSelectedMain)
                        {
                            ClientProxy.shootTime[i] += gun.calcCurrentSwitchTime(gun.getCurrentAttachments(itemStack));
                        }
                    }
                }
                
                ++i;
            }
            
            ClientProxy.prevSelectedMain = entityPlayer.inventory.currentItem;
            
            if(ClientProxy.inaccuracyTime > 0)
            {
                --ClientProxy.inaccuracyTime;
            }
            
            // ---
            
            if(ClientProxy.isShooting() && (entityPlayer.getHeldItemMainhand().getItem() instanceof GunItem || entityPlayer.getHeldItemOffhand().getItem() instanceof GunItem))
            {
                boolean aiming = false;
                
                if(ClientProxy.isAiming() && !entityPlayer.isSprinting())
                {
                    if(entityPlayer.getHeldItemMainhand().getItem() instanceof GunItem && entityPlayer.getHeldItemOffhand().isEmpty())
                    {
                        itemStack = entityPlayer.getHeldItemMainhand();
                        gun = (GunItem)itemStack.getItem();
                        
                        if(gun.getNBTCanAimGun(itemStack))
                        {
                            Optic optic = gun.<Optic>getAttachmentCalled(itemStack, EnumAttachmentType.OPTIC);
                            aiming = optic.canAim();
                        }
                    }
                }
                
                i = 0;
                int handsInt = 0;
                
                for(i = 0; i < ClientProxy.shootTime.length; ++i)
                {
                    if(entityPlayer.getHeldItem(GunCusUtility.HANDS[i]).getItem() instanceof GunItem && ClientProxy.shootTime[i] <= 0)
                    {
                        handsInt += i + 1;
                    }
                }
                
                if(handsInt > 0)
                {
                    GunCus.channel.sendToServer(new ShootMessage(aiming, ClientProxy.inaccuracyTime, handsInt));
                    GunItem.tryShoot(entityPlayer, aiming, ClientProxy.inaccuracyTime, GunCusUtility.intToHands(handsInt));
                }
            }
            
            // ---
            
            for(ShuffleItemGroup group : ShuffleItemGroup.GROUPS_LIST)
            {
                group.tick();
            }
        }
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
        
        ClientProxy.getMC().getTextureManager().bindTexture(rl);
        
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
        return ClientProxy.getMC().player;
    }
    
    @SuppressWarnings("resource")
    public static FontRenderer getFontRenderer()
    {
        return ClientProxy.getMC().fontRenderer;
    }
    
    @SuppressWarnings("resource")
    public static boolean isAiming()
    {
        return ClientProxy.getMC().gameSettings.keyBindUseItem.isKeyDown();
    }
    
    @SuppressWarnings("resource")
    public static boolean isShooting()
    {
        return ClientProxy.getMC().gameSettings.keyBindAttack.isKeyDown();
    }
    
    public static void renderDisabledRect(MatrixStack ms, float x, float y, float w, float h)
    {
        RenderSystem.colorMask(true, true, true, false);
        drawRect(ms, x, y, w, h, 0F, 0F, 0F, 0.5F);
        RenderSystem.colorMask(true, true, true, true);
    }
    
    public static void drawRect(MatrixStack ms, float x, float y, float w, float h, float r, float g, float b, float a)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        
        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        
        // Use src_color * src_alpha
        // and dest_color * (1 - src_alpha) for colors
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        
        RenderSystem.color4f(r, g, b, a);
        
        Matrix4f m = ms.getLast().getMatrix();
        
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(m, x, y + h, 0F).endVertex(); // BL
        bufferbuilder.pos(m, x + w, y + h, 0F).endVertex(); // BR
        bufferbuilder.pos(m, x + w, y, 0F).endVertex(); // TR
        bufferbuilder.pos(m, x, y, 0F).endVertex(); // TL
        tessellator.draw();
        
        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
        RenderSystem.color4f(1F, 1F, 1F, 1F);
    }
}
