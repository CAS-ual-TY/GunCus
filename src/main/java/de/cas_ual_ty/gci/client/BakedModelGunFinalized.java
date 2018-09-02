package de.cas_ual_ty.gci.client;

import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Matrix4f;
import org.apache.commons.lang3.tuple.Pair;

import de.cas_ual_ty.gci.item.ItemGun;
import de.cas_ual_ty.gci.item.attachment.Attachment;
import de.cas_ual_ty.gci.item.attachment.EnumAttachmentType;
import de.cas_ual_ty.gci.item.attachment.Optic;
import de.cas_ual_ty.gci.item.attachment.Paint;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class BakedModelGunFinalized implements IBakedModel
{
	private final IBakedModel modelMain;
	private final IBakedModel[][] attachmentModels;
	private ItemStack itemStack;
	
	public BakedModelGunFinalized(IBakedModel modelMain, IBakedModel[][] attachmentModels)
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

	@Override
	public TextureAtlasSprite getParticleTexture()
	{
		return this.modelMain.getParticleTexture();
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState arg0, EnumFacing arg1, long arg2)
	{
		ArrayList<BakedQuad> list = new ArrayList<BakedQuad>();
		
		List<BakedQuad> list1 = this.modelMain.getQuads(arg0, arg1, arg2);
		
		ItemGun gun = (ItemGun) this.itemStack.getItem();
		
		Paint paint = gun.<Paint>getAttachmentCalled(this.itemStack, EnumAttachmentType.PAINT.getSlot());
		IBakedModel model;
		
		if(paint != null && paint.shouldRegister())
		{
			model = this.attachmentModels[EnumAttachmentType.PAINT.getSlot()][paint.getID()];
			
			if(model != null)
			{
				list1 = model.getQuads(arg0, arg1, arg2);
			}
		}
		
		if(list1 != null && !list1.isEmpty())
		{
			list.addAll(list1);
		}
		
		List<BakedQuad> list2;
		
		Attachment attachment;
		
		for(int i = 0; i < EnumAttachmentType.values().length; ++i)
		{
			attachment = gun.getAttachment(itemStack, i);
			
			if(attachment != null && attachment.shouldRender())
			{
				model = this.attachmentModels[i][attachment.getID()];
				
				if(model != null)
				{
					list2 = model.getQuads(arg0, arg1, arg2);
					
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
			EntityPlayer entityPlayer = Minecraft.getMinecraft().player;
			
			if(entityPlayer != null && !entityPlayer.isSprinting() && entityPlayer.getHeldItemMainhand().getItem() instanceof ItemGun && entityPlayer.getHeldItemOffhand().isEmpty() && Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown())
			{
				ItemStack itemStack = entityPlayer.getHeldItemMainhand();
				ItemGun gun = (ItemGun) itemStack.getItem();
				
				Optic optic = gun.<Optic>getAttachmentCalled(itemStack, EnumAttachmentType.OPTIC.getSlot());
				
				if(optic != null && optic.canAim())
				{
					return Pair.of(this, NULL_MATRIX);
				}
			}
		}
		
		return Pair.of(this, this.modelMain.handlePerspective(transformType).getRight());
	}
}
