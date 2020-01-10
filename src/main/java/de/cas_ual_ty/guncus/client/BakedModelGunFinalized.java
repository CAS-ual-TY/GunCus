package de.cas_ual_ty.guncus.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import de.cas_ual_ty.guncus.item.ItemAttachment;
import de.cas_ual_ty.guncus.item.ItemGun;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import de.cas_ual_ty.guncus.item.attachments.Optic;
import de.cas_ual_ty.guncus.item.attachments.Paint;
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
    private final IBakedModel modelMain;
    private final IBakedModel[][] attachmentModels;
    private ItemStack itemStack;
    
    public BakedModelGunFinalized(IBakedModel modelMain, IBakedModel[][] attachmentModels, Matrix4f aimMatrix)
    {
        this.modelMain = modelMain;
        this.attachmentModels = attachmentModels;
        this.itemStack = null;
    }
    
    public BakedModelGunFinalized setCurrentItemStack(ItemStack itemStack)
    {
        this.itemStack = itemStack;
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
    
    @SuppressWarnings("deprecation")
    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand)
    {
        ArrayList<BakedQuad> list = new ArrayList<>();
        
        List<BakedQuad> list1 = this.modelMain.getQuads(state, side, rand);
        
        ItemGun gun = (ItemGun)this.itemStack.getItem();
        
        Paint paint = gun.<Paint> getAttachmentCalled(this.itemStack, EnumAttachmentType.PAINT);
        IBakedModel model;
        
        if(paint != null && paint.shouldLoadModel())
        {
            model = this.attachmentModels[EnumAttachmentType.PAINT.getSlot()][gun.getIndexForAttachment(paint)];
            
            if(model != null)
            {
                list1 = model.getQuads(state, side, rand);
            }
        }
        
        if(list1 != null && !list1.isEmpty())
        {
            list.addAll(list1);
        }
        
        List<BakedQuad> list2;
        
        ItemAttachment attachment;
        
        for(EnumAttachmentType type : EnumAttachmentType.VALUES)
        {
            attachment = gun.getAttachment(this.itemStack, type);
            
            if(attachment != null && attachment.shouldRender())
            {
                model = this.attachmentModels[type.getSlot()][gun.getIndexForAttachment(attachment)];
                
                if(model != null)
                {
                    list2 = model.getQuads(state, side, rand);
                    
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
    public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand, IModelData extraData)
    {
        ArrayList<BakedQuad> list = new ArrayList<>();
        
        List<BakedQuad> list1 = this.modelMain.getQuads(state, side, rand, extraData);
        
        ItemGun gun = (ItemGun)this.itemStack.getItem();
        
        Paint paint = gun.<Paint> getAttachmentCalled(this.itemStack, EnumAttachmentType.PAINT);
        IBakedModel model;
        
        if(paint != null && paint.shouldLoadModel())
        {
            model = this.attachmentModels[EnumAttachmentType.PAINT.getSlot()][gun.getIndexForAttachment(paint)];
            
            if(model != null)
            {
                list1 = model.getQuads(state, side, rand, extraData);
            }
        }
        
        if(list1 != null && !list1.isEmpty())
        {
            list.addAll(list1);
        }
        
        List<BakedQuad> list2;
        
        ItemAttachment attachment;
        
        for(EnumAttachmentType type : EnumAttachmentType.VALUES)
        {
            attachment = gun.getAttachment(this.itemStack, type);
            
            if(attachment != null && attachment.shouldRender())
            {
                model = this.attachmentModels[type.getSlot()][gun.getIndexForAttachment(attachment)];
                
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
    public boolean isGui3d()
    {
        return this.modelMain.isGui3d();
    }
    
    public static final Matrix4f NULL_MATRIX = new Matrix4f();
    
    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType transformType)
    {
        if(transformType == TransformType.FIRST_PERSON_RIGHT_HAND)
        {
            PlayerEntity entityPlayer = ProxyClient.getClientPlayer();
            
            if(entityPlayer != null && !entityPlayer.isSprinting() && entityPlayer.getHeldItemMainhand().getItem() instanceof ItemGun && entityPlayer.getHeldItemOffhand().isEmpty() && ProxyClient.BUTTON_AIM_DOWN.get())
            {
                ItemStack itemStack = entityPlayer.getHeldItemMainhand();
                ItemGun gun = (ItemGun)itemStack.getItem();
                
                Optic optic = gun.<Optic> getAttachmentCalled(itemStack, EnumAttachmentType.OPTIC);
                
                if(gun.getNBTCanAimGun(itemStack) && optic.canAim())
                {
                    if(optic.isDefault())
                    {
                        //						return Pair.of(this, this.aimMatrix);
                        return Pair.of(this, this.modelMain.handlePerspective(transformType).getRight());
                    }
                    else
                    {
                        return Pair.of(this, BakedModelGunFinalized.NULL_MATRIX);
                    }
                }
            }
        }
        
        return Pair.of(this, this.modelMain.handlePerspective(transformType).getRight());
    }
}
