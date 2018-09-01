package de.cas_ual_ty.gci.client;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import de.cas_ual_ty.gci.item.attachment.Optic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class GuiSight
{
	protected void draw(@Nullable Optic optic, ScaledResolution sr)
	{
		GL11.glPushMatrix();
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		
		this.drawDrawFullscreenImage(optic.getOverlay(), 1024, 256, sr);
		
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		
		GL11.glPopMatrix();
	}
	
	public void drawDrawFullscreenImage(ResourceLocation rl, int texWidth, int texHeight, ScaledResolution sr)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(rl);
		
		double x = sr.getScaledWidth();
		double y = sr.getScaledHeight();
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder b = tessellator.getBuffer();
		
		b.begin(7, DefaultVertexFormats.POSITION_TEX);
		
		b.pos(x * 0.5D - 2 * y, y, -90D).tex(0D, 1D).endVertex();;
		b.pos(x * 0.5D + 2 * y, y, -90D).tex(1D, 1D).endVertex();;
		b.pos(x * 0.5D + 2 * y, 0D, -90D).tex(1D, 0D).endVertex();;
		b.pos(x * 0.5D - 2 * y, 0D, -90D).tex(0D, 0D).endVertex();;
		
		tessellator.draw();
	}
}
