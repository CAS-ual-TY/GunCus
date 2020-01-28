package de.cas_ual_ty.guncus.client;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.IModelData;

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
    
    public BakedModelGun(IBakedModel modelMain, IBakedModel[][] attachmentModels, Matrix4f aimMatrix)
    {
        this.modelMain = modelMain;
        this.modelFinal = new BakedModelGunFinalized(this.modelMain, attachmentModels, aimMatrix);
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
    
    @SuppressWarnings("deprecation")
    @Override
    public TextureAtlasSprite getParticleTexture()
    {
        return this.modelMain.getParticleTexture();
    }
    
    @Override
    public TextureAtlasSprite getParticleTexture(IModelData data)
    {
        return this.modelMain.getParticleTexture(data);
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand)
    {
        return this.modelMain.getQuads(state, side, rand);
    }
    
    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand, net.minecraftforge.client.model.data.IModelData extraData)
    {
        return this.modelMain.getQuads(state, side, rand, extraData);
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
    public boolean func_230044_c_()
    {
        return this.modelMain.func_230044_c_();
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
            super();
            this.modelGun = modelGun;
        }
        
        @Override
        public IBakedModel getModelWithOverrides(IBakedModel model, ItemStack stack, World worldIn, LivingEntity entityIn)
        {
            return this.modelGun.getModelFinal().setCurrentItemStack(stack);
        }
    }
}
