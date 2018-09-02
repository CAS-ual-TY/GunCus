package de.cas_ual_ty.gci.client.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactoryGCI implements IRenderFactory<Entity>
{
	@Override
	public Render<? super Entity> createRenderFor(RenderManager rm)
	{
		return null;
	}
}
