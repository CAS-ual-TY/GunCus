package de.cas_ual_ty.gci.client;

import java.util.Collections;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BakedModelGun implements IBakedModel
{
	/*
	 * The only usage of this class is the item overrides list:
	 * Basically get the itemstack, pass it onto the modelFinal, then return modelFinal for rendering,
	 * so that during the rendering you have information about the itemstack
	 */
	private final IBakedModel modelMain;
	private final BakedModelGunFinalized modelFinal;
	private final ItemOverrideListGCI overridesList;
	
	public BakedModelGun(IBakedModel modelMain, IBakedModel[][] attachmentModels)
	{
		this.modelMain = modelMain;
		this.modelFinal = new BakedModelGunFinalized(this.modelMain, attachmentModels);
		this.overridesList = new ItemOverrideListGCI(this);
	}
	
	public BakedModelGunFinalized getModelFinal()
	{
		return this.modelFinal;
	}
	
	@Override
	public ItemOverrideList getOverrides()
	{
		return this.overridesList;
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
	
	private static class ItemOverrideListGCI extends ItemOverrideList
	{
		private BakedModelGun modelGun;
		
		public ItemOverrideListGCI(BakedModelGun modelGun)
		{
			super(Collections.EMPTY_LIST);
			this.modelGun = modelGun;
		}
		
		@Override
		public IBakedModel handleItemState(IBakedModel originalModel, ItemStack itemStack, World world, EntityLivingBase entity)
		{
			return this.modelGun.getModelFinal().setCurrentItemStack(itemStack);
		}
	}
}
