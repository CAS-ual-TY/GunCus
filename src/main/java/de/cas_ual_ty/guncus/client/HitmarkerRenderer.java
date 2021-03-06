package de.cas_ual_ty.guncus.client;

import com.mojang.blaze3d.matrix.MatrixStack;

import de.cas_ual_ty.guncus.GunCus;
import net.minecraft.client.MainWindow;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HitmarkerRenderer
{
    public static final ResourceLocation HITMARKER_TEXTURE = new ResourceLocation(GunCus.MOD_ID, "textures/gui/hitmarker.png");
    
    private static final int HITMARKER_RESET = 4;
    private static int hitmarkerTick = 0;
    
    public static void addHitmarker()
    {
        HitmarkerRenderer.hitmarkerTick = HitmarkerRenderer.HITMARKER_RESET;
    }
    
    @SubscribeEvent
    public void renderGameOverlayPre(RenderGameOverlayEvent.Pre event)
    {
        PlayerEntity entityPlayer = ClientProxy.getClientPlayer();
        
        if(event.getType() == ElementType.CROSSHAIRS && entityPlayer != null)
        {
            if(HitmarkerRenderer.hitmarkerTick > 0)
            {
                HitmarkerRenderer.drawHitmarker(event.getMatrixStack(), event.getWindow());
            }
        }
    }
    
    private static void drawHitmarker(MatrixStack ms, MainWindow sr)
    {
        ClientProxy.drawDrawFullscreenImage(ms, HitmarkerRenderer.HITMARKER_TEXTURE, 1024, 256, sr);
    }
    
    @SubscribeEvent
    public void clientTick(ClientTickEvent event)
    {
        if(event.phase == Phase.END)
        {
            if(HitmarkerRenderer.hitmarkerTick > 0)
            {
                --HitmarkerRenderer.hitmarkerTick;
            }
        }
    }
}
