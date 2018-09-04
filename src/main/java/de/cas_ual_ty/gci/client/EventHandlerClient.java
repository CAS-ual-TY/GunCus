package de.cas_ual_ty.gci.client;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import de.cas_ual_ty.gci.EntityBullet;
import de.cas_ual_ty.gci.GunCus;
import de.cas_ual_ty.gci.client.render.GuiSight;
import de.cas_ual_ty.gci.item.ItemGun;
import de.cas_ual_ty.gci.item.attachment.Accessory;
import de.cas_ual_ty.gci.item.attachment.Accessory.Laser;
import de.cas_ual_ty.gci.item.attachment.EnumAttachmentType;
import de.cas_ual_ty.gci.item.attachment.Optic;
import de.cas_ual_ty.gci.network.MessageShoot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class EventHandlerClient
{
	public static final GuiSight GUI_SIGHT = new GuiSight();
	private static final ModelRendererTransformationHelper TRANSFORMATION_HELPER = new ModelRendererTransformationHelper();
	
//	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(RenderPlayerEvent.Pre event)
	{
		EntityPlayer entityPlayer = event.getEntityPlayer();
		
		if(!entityPlayer.isSprinting() && !entityPlayer.isSneaking())
		{
			if(entityPlayer.getHeldItemMainhand().getItem() instanceof ItemGun)
			{
				event.getRenderer().getMainModel().bipedRightArm.isHidden = true;
				
				if(entityPlayer.getHeldItemOffhand().getItem() instanceof ItemGun || entityPlayer.getHeldItemOffhand().isEmpty())
				{
					event.getRenderer().getMainModel().bipedLeftArm.isHidden = true;
				}
			}
			else if(entityPlayer.getHeldItemOffhand().getItem() instanceof ItemGun)
			{
				event.getRenderer().getMainModel().bipedLeftArm.isHidden = true;
			}
		}
	}
	
//	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(RenderPlayerEvent.Post event)
	{
		EntityPlayer entityPlayer = event.getEntityPlayer();
		
		if(!entityPlayer.isSprinting() && !entityPlayer.isSneaking())
		{
			if(entityPlayer.getHeldItemMainhand().getItem() instanceof ItemGun)
			{
				Minecraft.getMinecraft().getTextureManager().bindTexture(event.getRenderer().getEntityTexture((AbstractClientPlayer) entityPlayer));
				
				if(entityPlayer.getHeldItemOffhand().getItem() instanceof ItemGun)
				{
					event.getRenderer().getMainModel().bipedRightArm.isHidden = false;
					TRANSFORMATION_HELPER.set(
							-MathHelper.cos((float) Math.toRadians(entityPlayer.renderYawOffset)) * 4.5F,
							20,
							-MathHelper.sin((float) Math.toRadians(entityPlayer.renderYawOffset)) * 4.5F,
							(float) Math.toRadians(90D),
							(float) -Math.toRadians(entityPlayer.renderYawOffset),
							0
							).transformAndRender(entityPlayer, event.getRenderer().getMainModel().bipedRightArm, 0.0625F);
					
					event.getRenderer().getMainModel().bipedLeftArm.isHidden = false;
					TRANSFORMATION_HELPER.set(
							MathHelper.cos((float) Math.toRadians(entityPlayer.renderYawOffset)) * 4.5F,
							20,
							MathHelper.sin((float) Math.toRadians(entityPlayer.renderYawOffset)) * 4.5F,
							(float) Math.toRadians(90D),
							(float) -Math.toRadians(entityPlayer.renderYawOffset),
							0
							).transformAndRender(entityPlayer, event.getRenderer().getMainModel().bipedLeftArm, 0.0625F);
				}
				else if(!entityPlayer.getHeldItemOffhand().isEmpty())
				{
					event.getRenderer().getMainModel().bipedRightArm.isHidden = false;
					TRANSFORMATION_HELPER.set(
							-MathHelper.cos((float) Math.toRadians(entityPlayer.renderYawOffset)) * 4.5F,
							20,
							-MathHelper.sin((float) Math.toRadians(entityPlayer.renderYawOffset)) * 4.5F,
							(float) Math.toRadians(90D),
							(float) -Math.toRadians(entityPlayer.renderYawOffset),
							0
							).transformAndRender(entityPlayer, event.getRenderer().getMainModel().bipedRightArm, 0.0625F);
				}
				else
				{
					event.getRenderer().getMainModel().bipedRightArm.isHidden = false;
					TRANSFORMATION_HELPER.set(
							-MathHelper.cos((float) Math.toRadians(entityPlayer.renderYawOffset)) * 4.5F,
							20,
							-MathHelper.sin((float) Math.toRadians(entityPlayer.renderYawOffset)) * 4.5F,
							(float) Math.toRadians(90D),
							(float) -Math.toRadians(entityPlayer.renderYawOffset),
							0
							).transformAndRender(entityPlayer, event.getRenderer().getMainModel().bipedRightArm, 0.0625F);
					
					event.getRenderer().getMainModel().bipedLeftArm.isHidden = false;
					TRANSFORMATION_HELPER.set(
							MathHelper.cos((float) Math.toRadians(entityPlayer.renderYawOffset)) * 4.5F,
							20,
							MathHelper.sin((float) Math.toRadians(entityPlayer.renderYawOffset)) * 4.5F,
							(float) Math.toRadians(90D),
							(float) -Math.toRadians(entityPlayer.renderYawOffset + 45D),
							0
							).transformAndRender(entityPlayer, event.getRenderer().getMainModel().bipedLeftArm, 0.0625F);
				}
			}
			else if(entityPlayer.getHeldItemOffhand().getItem() instanceof ItemGun)
			{
				Minecraft.getMinecraft().getTextureManager().bindTexture(event.getRenderer().getEntityTexture((AbstractClientPlayer) entityPlayer));
				
				event.getRenderer().getMainModel().bipedLeftArm.isHidden = false;
				TRANSFORMATION_HELPER.set(
						MathHelper.cos((float) Math.toRadians(entityPlayer.renderYawOffset)) * 4.5F,
						20,
						MathHelper.sin((float) Math.toRadians(entityPlayer.renderYawOffset)) * 4.5F,
						(float) Math.toRadians(90D),
						(float) -Math.toRadians(entityPlayer.renderYawOffset),
						0
						).transformAndRender(entityPlayer, event.getRenderer().getMainModel().bipedLeftArm, 0.0625F);
			}
		}
	}
	
	private int tabCounter = 0;
	private static final int TAB_ITEM_INTERVAL = 20;
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void clientTick(ClientTickEvent event)
	{
		if(event.phase == Phase.START)
		{
			if(Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown() && Minecraft.getMinecraft().player != null)
			{
				boolean aiming = false;
				EntityPlayer entityPlayer = Minecraft.getMinecraft().player;
				
				if(Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown() && !entityPlayer.isSprinting())
				{
					
					if(entityPlayer.getHeldItemMainhand().getItem() instanceof ItemGun && entityPlayer.getHeldItemOffhand().isEmpty())
					{
						ItemStack itemStack = entityPlayer.getHeldItemMainhand();
						ItemGun gun = (ItemGun) itemStack.getItem();
						
						if(gun.getCanAim(itemStack))
						{
							Optic optic = gun.<Optic>getAttachmentCalled(itemStack, EnumAttachmentType.OPTIC.getSlot());
							
							if(optic != null && optic.canAim())
							{
								aiming = true;
							}
						}
					}
				}
				
				GunCus.channel.sendToServer(new MessageShoot(aiming, (!entityPlayer.onGround || (entityPlayer.motionX != 0) || (entityPlayer.motionY != 0))));
			}
			
			++this.tabCounter;
			
			if(this.tabCounter >= TAB_ITEM_INTERVAL)
			{
				GunCus.TAB_GUNCUS.shuffleItemStack();
				
				this.tabCounter = 0;
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void renderGameOverlay(RenderGameOverlayEvent event)
	{
		if(event.getType() == ElementType.CROSSHAIRS && Minecraft.getMinecraft().player != null)
		{
			EntityPlayer entityPlayer = Minecraft.getMinecraft().player;
			Optic optic = null;
			
			if(entityPlayer.getHeldItemMainhand().getItem() instanceof ItemGun || entityPlayer.getHeldItemOffhand().getItem() instanceof ItemGun)
			{
				if(entityPlayer.getHeldItemOffhand().isEmpty())
				{
					ItemStack itemStack = entityPlayer.getHeldItemMainhand();
					ItemGun gun = (ItemGun) itemStack.getItem();
					
					if(gun.getCanAim(itemStack))
					{
						optic = gun.<Optic>getAttachmentCalled(itemStack, EnumAttachmentType.OPTIC.getSlot());
					}
				}
				
				event.setCanceled(true);
			}
			else if(entityPlayer.getHeldItemMainhand().getItem() instanceof Optic)
			{
				ItemStack itemStack = entityPlayer.getHeldItemMainhand();
				optic = (Optic) itemStack.getItem();
			}
			
			if(!entityPlayer.isSprinting() && Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown() && optic != null && optic.canAim())
			{
				GUI_SIGHT.draw(optic, event.getResolution());
				
				if(!event.isCanceled())
					event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void fovUpdate(FOVUpdateEvent event)
	{
		if(Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown())
		{
			EntityPlayer entityPlayer = Minecraft.getMinecraft().player;
			
			if(!entityPlayer.isSprinting() && Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown())
			{
				Optic optic = null;
				float modifier = 1F;
				float extra = 0F;
				
				if(entityPlayer.getHeldItemMainhand().getItem() instanceof ItemGun || entityPlayer.getHeldItemOffhand().getItem() instanceof ItemGun)
				{
					if(entityPlayer.getHeldItemOffhand().isEmpty())
					{
						ItemStack itemStack = entityPlayer.getHeldItemMainhand();
						ItemGun gun = (ItemGun) itemStack.getItem();
						
						if(gun.getCanAim(itemStack))
						{
							optic = gun.<Optic>getAttachmentCalled(itemStack, EnumAttachmentType.OPTIC.getSlot());
						}
						
						if(gun.isAccessoryTurnedOn(itemStack) && gun.getAttachment(itemStack, EnumAttachmentType.ACCESSORY.getSlot()) != null)
						{
							Accessory accessory = gun.<Accessory>getAttachmentCalled(itemStack, EnumAttachmentType.ACCESSORY.getSlot());
							
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
					optic = (Optic) itemStack.getItem();
				}
				
				if(optic != null && optic.canAim())
				{
					event.setNewfov(calculateFov(optic.getZoom() * modifier + 0.1F, event.getFov()));
				}
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void renderWorldLastEvent(RenderWorldLastEvent event)
	{
		if(Minecraft.getMinecraft().world != null)
		{
			World world = Minecraft.getMinecraft().world;
			
			ItemStack itemStack;
			ItemGun gun;
			Accessory accessory = null;
			Laser laser;
			
			Vec3d start;
			Vec3d end;
			Vec3d subtract;
			RayTraceResult resultBlock;
			RayTraceResult resultEntity;
			
			double rangeBlockSq;
			double rangeEntitySq;
			
			boolean renderPoint = false;
			
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder b = tessellator.getBuffer();
			
			for(EntityPlayer entityPlayer : world.playerEntities)
			{
				if(!entityPlayer.isDead)
				{
//					subtract = new Vec3d(entityPlayer.posX - entityPlayer.motionX * event.getPartialTicks(), entityPlayer.posY /*+ entityPlayer.getEyeHeight()*/ - (entityPlayer.onGround ? 0 : entityPlayer.motionY * event.getPartialTicks()), entityPlayer.posZ - entityPlayer.motionZ * event.getPartialTicks());
					subtract = new Vec3d(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ);
					
					for(EnumHand hand : EnumHand.values())
					{
						accessory = null;
						itemStack = entityPlayer.getHeldItem(hand);
						
						if(itemStack.getItem() instanceof ItemGun)
						{
							gun = (ItemGun) itemStack.getItem();
							
							if(gun.isAccessoryTurnedOn(itemStack))
							{
								accessory = gun.<Accessory>getAttachmentCalled(itemStack, EnumAttachmentType.ACCESSORY.getSlot());
								laser = accessory.getLaser();
							}
						}
						else if(itemStack.getItem() instanceof Accessory)
						{
							accessory = (Accessory) itemStack.getItem();
						}
						
						if(accessory != null && accessory.getLaser() != null)
						{
							laser = accessory.getLaser();
							start = new Vec3d(entityPlayer.posX, entityPlayer.posY + entityPlayer.getEyeHeight(), entityPlayer.posZ);
							end = entityPlayer.getLookVec().normalize().scale(laser.getMaxRange()).add(start).add(getOffsetForHandRaw(entityPlayer, hand));
							
							resultBlock = findBlockOnPath(world, entityPlayer, start, end);
							resultEntity = findEntityOnPath(world, entityPlayer, start, end);
							
							if(resultBlock != null && resultEntity != null)
							{
								rangeBlockSq = resultBlock.hitVec.distanceTo(start);
								rangeEntitySq = resultEntity.hitVec.distanceTo(start);
								
								if(rangeBlockSq < rangeEntitySq)
								{
									end = resultBlock.hitVec;
								}
								else
								{
									end = resultEntity.hitVec;
								}
								
								renderPoint = laser.isPoint();
							}
							else if(resultBlock != null)
							{
								end = resultBlock.hitVec;
								
								renderPoint = laser.isPoint();
							}
							else if(resultEntity != null)
							{
								end = resultEntity.hitVec;
								
								renderPoint = laser.isPoint();
							}
							
							start = new Vec3d(entityPlayer.posX, entityPlayer.posY + entityPlayer.getEyeHeight() * 0.8F, entityPlayer.posZ).add(getOffsetForHand(entityPlayer, hand));
							
							start = start.subtract(subtract);
							end = end.subtract(subtract);
							
							GlStateManager.disableTexture2D();
							
							if(renderPoint)
							{
								b.begin(7, DefaultVertexFormats.POSITION_COLOR);
								
								b.pos(end.x + 0.05D, end.y + 0.05D, end.z + 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								b.pos(end.x + 0.05D, end.y + 0.05D, end.z - 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								b.pos(end.x - 0.05D, end.y + 0.05D, end.z - 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								b.pos(end.x - 0.05D, end.y + 0.05D, end.z + 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								
								b.pos(end.x + 0.05D, end.y - 0.05D, end.z + 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								b.pos(end.x - 0.05D, end.y - 0.05D, end.z + 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								b.pos(end.x - 0.05D, end.y - 0.05D, end.z - 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								b.pos(end.x + 0.05D, end.y - 0.05D, end.z - 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								
								b.pos(end.x + 0.05D, end.y - 0.05D, end.z + 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								b.pos(end.x + 0.05D, end.y - 0.05D, end.z - 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								b.pos(end.x + 0.05D, end.y + 0.05D, end.z - 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								b.pos(end.x + 0.05D, end.y + 0.05D, end.z + 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								
								b.pos(end.x - 0.05D, end.y - 0.05D, end.z + 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								b.pos(end.x - 0.05D, end.y + 0.05D, end.z + 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								b.pos(end.x - 0.05D, end.y + 0.05D, end.z - 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								b.pos(end.x - 0.05D, end.y - 0.05D, end.z - 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								
								b.pos(end.x - 0.05D, end.y - 0.05D, end.z + 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								b.pos(end.x + 0.05D, end.y - 0.05D, end.z + 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								b.pos(end.x + 0.05D, end.y + 0.05D, end.z + 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								b.pos(end.x - 0.05D, end.y + 0.05D, end.z + 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								
								b.pos(end.x - 0.05D, end.y - 0.05D, end.z - 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								b.pos(end.x - 0.05D, end.y + 0.05D, end.z - 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								b.pos(end.x + 0.05D, end.y + 0.05D, end.z - 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								b.pos(end.x + 0.05D, end.y - 0.05D, end.z - 0.05D).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								
								tessellator.draw();
							}
							
							if(laser.isBeam())
							{
								b.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
								
								b.pos(start.x, start.y, start.z).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								b.pos(end.x, end.y, end.z).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
								
								tessellator.draw();
							}
							
							GlStateManager.enableTexture2D();
						}
					}
				}
			}
		}
	}
	
	public static Vec3d getVectorForRotation(float pitch, float yaw)
	{
		float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
		float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
		float f2 = -MathHelper.cos(-pitch * 0.017453292F);
		float f3 = MathHelper.sin(-pitch * 0.017453292F);
		return new Vec3d(f1 * f2, f3, f * f2);
	}
	
	public static Vec3d getOffsetForHandRaw(EntityPlayer entityPlayer, EnumHand hand)
	{
		Vec3d vec = getVectorForRotation(entityPlayer.rotationPitch, entityPlayer.rotationYaw + 90F);
		
		if(hand == EnumHand.OFF_HAND)
		{
			vec = vec.scale(-1);
		}
		
		return new Vec3d(vec.x, 0, vec.z).normalize().scale(0.4D);
	}
	
	public static Vec3d getOffsetForHand(EntityPlayer entityPlayer, EnumHand hand)
	{
		Vec3d vec = getOffsetForHandRaw(entityPlayer, hand);
		
		return vec.add(getVectorForRotation(entityPlayer.rotationPitch, entityPlayer.rotationYaw).scale(0.4D));
	}
	
	@Nullable
	public static RayTraceResult findBlockOnPath(World world, EntityPlayer entityPlayer, Vec3d start, Vec3d end)
	{
		return world.rayTraceBlocks(start, end, false, false, true);
	}
	
	@Nullable
	public static RayTraceResult findEntityOnPath(World world, EntityPlayer entityPlayer, Vec3d start, Vec3d end)
	{
		RayTraceResult result = null;
		
		double rangeSq = 0;
		double currentRangeSq;
		
		for(Entity entity : world.loadedEntityList)
		{
			if (entity != entityPlayer && !(entity instanceof EntityBullet))
			{
				AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow(0.30000001192092896D);
				
				RayTraceResult result1 = axisalignedbb.calculateIntercept(start, end);
				
				if (result1 != null)
				{
					currentRangeSq = start.squareDistanceTo(result1.hitVec);
					
					if (currentRangeSq < rangeSq || result == null)
					{
						result = result1;
						rangeSq = currentRangeSq;
					}
				}
			}
		}
		return result;
	}
	
	public static float calculateFov(float zoom, float fov)
	{
		return (float) Math.atan(Math.tan(fov) / zoom);
	}
	
	public static class ModelRendererTransformationHelper
	{
		private float rotationPointX;
		private float rotationPointY;
		private float rotationPointZ;
		
		private float rotateAngleX;
		private float rotateAngleY;
		private float rotateAngleZ;
		
		public ModelRendererTransformationHelper set(float rotationPointX,
				float rotationPointY, float rotationPointZ, float rotateAngleX,
				float rotateAngleY, float rotateAngleZ)
		{
			this.rotationPointX = rotationPointX;
			this.rotationPointY = rotationPointY;
			this.rotationPointZ = rotationPointZ;
			this.rotateAngleX = rotateAngleX;
			this.rotateAngleY = rotateAngleY;
			this.rotateAngleZ = rotateAngleZ;
			
			return this;
		}
		
		public ModelRendererTransformationHelper transform(EntityPlayer entityPlayer, ModelRenderer model)
		{
			float temp;
			
			temp = model.rotationPointX;
			model.rotationPointX = this.getRotationPointX();
			this.rotationPointX = temp;
			
			temp = model.rotationPointY;
			model.rotationPointY = this.getRotationPointY();
			this.rotationPointY = temp;
			
			temp = model.rotationPointZ;
			model.rotationPointZ = this.getRotationPointZ();
			this.rotationPointZ = temp;
			
			temp = model.rotateAngleX;
			model.rotateAngleX = this.getRotateAngleX();
			this.rotateAngleX = temp;
			
			temp = model.rotateAngleY;
			model.rotateAngleY = this.getRotateAngleY();
			this.rotateAngleY = temp;
			
			temp = model.rotateAngleZ;
			model.rotateAngleZ = this.getRotateAngleZ();
			this.rotateAngleZ = temp;
			
			return this;
		}
		
		public ModelRendererTransformationHelper render(EntityPlayer entityPlayer, ModelRenderer model, float f)
		{
			model.renderWithRotation(f);
			return this;
		}
		
		public ModelRendererTransformationHelper transformAndRender(EntityPlayer entityPlayer, ModelRenderer model, float f)
		{
			this.transform(entityPlayer, model);
			this.render(entityPlayer, model, f);
			this.transform(entityPlayer, model);
			return this;
		}
		
		public float getRotationPointX() {
			return rotationPointX;
		}
		public float getRotationPointY() {
			return rotationPointY;
		}
		public float getRotationPointZ() {
			return rotationPointZ;
		}
		public float getRotateAngleX() {
			return rotateAngleX;
		}
		public float getRotateAngleY() {
			return rotateAngleY;
		}
		public float getRotateAngleZ() {
			return rotateAngleZ;
		}
	}
}
