package de.cas_ual_ty.guncus.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;

import de.cas_ual_ty.guncus.item.AttachmentItem;
import de.cas_ual_ty.guncus.item.GunItem;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import de.cas_ual_ty.guncus.item.attachments.Optic;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.data.IModelData;

public class BakedModelGunFinalized implements IBakedModel
{
    private final GunItem gun;
    private final IBakedModel modelMain;
    private final IBakedModel[][] attachmentModels;
    
    private ItemStack tmpItemStack;
    
    public BakedModelGunFinalized(GunItem gun, IBakedModel modelMain, IBakedModel[][] attachmentModels)
    {
        this.gun = gun;
        this.modelMain = modelMain;
        this.attachmentModels = attachmentModels;
        
        this.tmpItemStack = null;
    }
    
    public BakedModelGunFinalized setCurrentItemStack(ItemStack itemStack)
    {
        this.tmpItemStack = itemStack;
        return this;
    }
    
    @Override
    public ItemOverrideList getOverrides()
    {
        return this.modelMain.getOverrides();
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
    
    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand)
    {
        return this.getQuads(state, side, rand, null);
    }
    
    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand, IModelData extraData)
    {
        ArrayList<BakedQuad> list = new ArrayList<>();
        
        IBakedModel model;
        List<BakedQuad> list2;
        AttachmentItem attachment;
        
        for(EnumAttachmentType type : EnumAttachmentType.RENDER_ORDER)
        {
            attachment = this.gun.getAttachment(this.tmpItemStack, type);
            
            if(attachment != null)
            {
                model = this.attachmentModels[type.getSlot()][this.gun.getIndexForAttachment(attachment)];
                
                if(model != null)
                {
                    list2 = model.getQuads(state, side, rand, extraData);
                    
                    if(list2 != null && !list2.isEmpty())
                    {
                        list.addAll(list2);
                    }
                }
            }
        }
        
        return list;
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
    public boolean isSideLit()
    {
        return this.modelMain.isSideLit();
    }
    
    @Override
    public boolean isGui3d()
    {
        return this.modelMain.isGui3d();
    }
    
    @Override
    public IBakedModel handlePerspective(TransformType transformType, MatrixStack mat)
    {
        if(transformType == TransformType.FIRST_PERSON_RIGHT_HAND)
        {
            PlayerEntity entityPlayer = ClientProxy.getClientPlayer();
            
            if(entityPlayer != null && !entityPlayer.isSprinting() && entityPlayer.getHeldItemMainhand().getItem() instanceof GunItem && entityPlayer.getHeldItemOffhand().isEmpty() && ClientProxy.isAiming())
            {
                ItemStack itemStack = entityPlayer.getHeldItemMainhand();
                Optic optic = this.gun.<Optic>getAttachmentCalled(itemStack, EnumAttachmentType.OPTIC);
                int idx = this.gun.getIndexForAttachment(optic);
                
                if(this.gun.getNBTCanAimGun(itemStack) && optic.canAim() && idx != -1)
                {
                    this.attachmentModels[EnumAttachmentType.OPTIC.getSlot()][idx].handlePerspective(transformType, mat);
                    return this;
                }
            }
        }
        
        this.modelMain.handlePerspective(transformType, mat);
        return this;
    }
}
