package de.cas_ual_ty.guncus.client;

import com.mojang.blaze3d.matrix.MatrixStack;

import de.cas_ual_ty.guncus.item.ItemGun;
import de.cas_ual_ty.guncus.item.attachments.Accessory;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import de.cas_ual_ty.guncus.item.attachments.Optic;
import net.minecraft.client.MainWindow;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SightRenderer
{
    @SubscribeEvent
    public void fovUpdate(FOVUpdateEvent event)
    {
        PlayerEntity entityPlayer = ProxyClient.getClientPlayer();
        
        if(entityPlayer != null && ProxyClient.isAiming())
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
                    event.setNewfov(SightRenderer.calculateFov(optic.getZoom() * modifier + 0.1F + extra, event.getFov()));
                }
            }
        }
    }
    
    private static float calculateFov(float zoom, float fov)
    {
        return (float)Math.atan(Math.tan(fov) / zoom);
    }
    
    @SubscribeEvent
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
            
            if(optic != null && optic.canAim() && !entityPlayer.isSprinting() && ProxyClient.isAiming())
            {
                SightRenderer.drawSight(event.getMatrixStack(), optic, event.getWindow());
                
                if(!event.isCanceled())
                {
                    event.setCanceled(true);
                }
            }
        }
    }
    
    private static void drawSight(MatrixStack ms, Optic optic, MainWindow sr)
    {
        ProxyClient.drawDrawFullscreenImage(ms, optic.getOverlay(), 1024, 256, sr);
    }
}
