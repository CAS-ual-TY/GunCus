package de.cas_ual_ty.gci.client;

import java.util.List;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import de.cas_ual_ty.gci.item.attachment.Optic;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class BakedModelOptic implements IBakedModel
{
	private final IBakedModel modelMain;
	
	public BakedModelOptic(IBakedModel modelMain)
	{
		this.modelMain = modelMain;
	}
	
	@Override
	public ItemOverrideList getOverrides()
	{
		return this.modelMain.getOverrides();
	}
	
	@Override
	public TextureAtlasSprite getParticleTexture()
	{
		return this.modelMain.getParticleTexture();
	}
	
	@Override
	public List<BakedQuad> getQuads(IBlockState arg0, EnumFacing arg1, long arg2)
	{
		return this.modelMain.getQuads(arg0, arg1, arg2);
	}
	
	@Override
	public boolean isAmbientOcclusion()
	{
		return this.modelMain.isAmbientOcclusion();
	}
	
	@Override
	public boolean isBuiltInRenderer()
	{
		return this.modelMain.isBuiltInRenderer();
	}
	
	@Override
	public boolean isGui3d()
	{
		return this.modelMain.isGui3d();
	}
	
	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType transformType)
	{
		if(transformType == TransformType.FIRST_PERSON_RIGHT_HAND)
		{
			EntityPlayer entityPlayer = Minecraft.getMinecraft().player;
			
			if(entityPlayer != null && !entityPlayer.isSprinting() && entityPlayer.getHeldItemMainhand().getItem() instanceof Optic && Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown())
			{
				ItemStack itemStack = entityPlayer.getHeldItemMainhand();
				Optic optic = (Optic) itemStack.getItem();
				
				if(optic != null && optic.canAim())
				{
					return Pair.of(this, BakedModelGunFinalized.NULL_MATRIX);
				}
			}
		}
		
		return Pair.of(this, this.modelMain.handlePerspective(transformType).getRight());
	}
}
