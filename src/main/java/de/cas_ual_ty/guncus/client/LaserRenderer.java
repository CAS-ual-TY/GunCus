package de.cas_ual_ty.guncus.client;

import java.util.Optional;
import java.util.OptionalDouble;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import de.cas_ual_ty.guncus.entity.EntityBullet;
import de.cas_ual_ty.guncus.item.ItemGun;
import de.cas_ual_ty.guncus.item.attachments.Accessory;
import de.cas_ual_ty.guncus.item.attachments.Accessory.Laser;
import de.cas_ual_ty.guncus.item.attachments.EnumAttachmentType;
import de.cas_ual_ty.guncus.util.GunCusUtility;
import net.minecraft.block.Blocks;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LaserRenderer
{
    // --- RENDER STUFF - COPIED FROM RenderType CLASS ---
    
    public static final RenderState.TransparencyState LASER_TRANSPARENCY = new RenderState.TransparencyState("laser_transparency", () ->
    {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
    }, () ->
    {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });
    
    public static final RenderState.ShadeModelState SHADE_ENABLED = new RenderState.ShadeModelState(true);
    public static final RenderState.WriteMaskState COLOR_WRITE = new RenderState.WriteMaskState(true, false);
    
    // Lightning Copied
    public static final RenderType LASER_POINT = RenderType.makeType("laser_point", DefaultVertexFormats.POSITION_COLOR, GL11.GL_QUADS, 256, false, true, RenderType.State.getBuilder().writeMask(LaserRenderer.COLOR_WRITE).transparency(LaserRenderer.LASER_TRANSPARENCY).shadeModel(LaserRenderer.SHADE_ENABLED).build(false));
    public static final RenderType LASER_BEAM = RenderType.makeType("laser_beam", DefaultVertexFormats.POSITION_COLOR, GL11.GL_LINES, 256, false, true, RenderType.State.getBuilder().line(new RenderState.LineState(OptionalDouble.empty())).writeMask(LaserRenderer.COLOR_WRITE).transparency(LaserRenderer.LASER_TRANSPARENCY).shadeModel(LaserRenderer.SHADE_ENABLED).build(false));
    
    // --- RENDER STUFF END ---
    
    private static boolean tmpHitNothing = false;
    
    @SuppressWarnings("resource")
    @SubscribeEvent
    public void renderWorldLast(RenderWorldLastEvent event)
    {
        if(ProxyClient.getMC().getRenderViewEntity() != null)
        {
            
            MatrixStack matrixStack = event.getMatrixStack();
            
            matrixStack.push();
            
            ActiveRenderInfo renderInfo = Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
            Vec3d v = renderInfo.getProjectedView();
            matrixStack.translate(-v.x, -v.y, -v.z);
            
            IRenderTypeBuffer.Impl b = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
            World world = ProxyClient.getMC().getRenderViewEntity().world;
            final float partialTicks = event.getPartialTicks();
            
            for(PlayerEntity entityPlayer : world.getPlayers())
            {
                Vec3d playerPos = entityPlayer.getEyePosition(partialTicks);
                Vec3d playerLook = entityPlayer.getLookVec().normalize();
                
                if(entityPlayer.isAlive())
                {
                    for(Hand hand : GunCusUtility.HANDS)
                    {
                        Accessory accessory = null;
                        ItemStack itemStack = entityPlayer.getHeldItem(hand);
                        
                        if(itemStack.getItem() instanceof ItemGun)
                        {
                            ItemGun gun = (ItemGun)itemStack.getItem();
                            
                            if(gun.isNBTAccessoryTurnedOn(itemStack))
                            {
                                accessory = gun.<Accessory>getAttachmentCalled(itemStack, EnumAttachmentType.ACCESSORY);
                            }
                        }
                        else if(itemStack.getItem() instanceof Accessory)
                        {
                            accessory = (Accessory)itemStack.getItem();
                        }
                        
                        if(accessory != null && accessory.getLaser() != null)
                        {
                            Laser laser = accessory.getLaser();
                            
                            Vec3d handOff = LaserRenderer.getOffsetForHand(entityPlayer, hand).add(LaserRenderer.getVectorForRotation(entityPlayer.rotationPitch + -345F, entityPlayer.rotationYaw));
                            
                            Vec3d start = playerPos.add(handOff);
                            Vec3d end = start.add(playerLook.scale(laser.getMaxRange()));
                            
                            end = LaserRenderer.findHit(world, entityPlayer, start, end, partialTicks);
                            
                            if(laser.isPoint() && !LaserRenderer.tmpHitNothing)
                            {
                                IVertexBuilder vb = b.getBuffer(LaserRenderer.LASER_POINT);
                                LaserRenderer.renderLaserPoint(vb, matrixStack, laser, start, end);
                                b.finish(LaserRenderer.LASER_POINT);
                            }
                            
                            if(laser.isBeam())
                            {
                                IVertexBuilder vb = b.getBuffer(LaserRenderer.LASER_BEAM);
                                LaserRenderer.renderLaserBeam(vb, matrixStack, laser, start, end);
                                b.finish(LaserRenderer.LASER_BEAM);
                            }
                        }
                    }
                }
            }
            
            matrixStack.pop();
        }
    }
    
    public static void renderLaserPoint(IVertexBuilder b, MatrixStack m, Laser laser, Vec3d start, Vec3d end)
    {
        Matrix4f matrix = m.getLast().getMatrix();
        
        final float size = 0.05F;
        
        b.pos(matrix, (float)end.x + size, (float)(float)end.y + size, (float)(float)end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(matrix, (float)end.x + size, (float)end.y + size, (float)end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(matrix, (float)end.x - size, (float)end.y + size, (float)end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(matrix, (float)end.x - size, (float)end.y + size, (float)end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        
        b.pos(matrix, (float)end.x + size, (float)end.y - size, (float)end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(matrix, (float)end.x - size, (float)end.y - size, (float)end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(matrix, (float)end.x - size, (float)end.y - size, (float)end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(matrix, (float)end.x + size, (float)end.y - size, (float)end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        
        b.pos(matrix, (float)end.x + size, (float)end.y - size, (float)end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(matrix, (float)end.x + size, (float)end.y - size, (float)end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(matrix, (float)end.x + size, (float)end.y + size, (float)end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(matrix, (float)end.x + size, (float)end.y + size, (float)end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        
        b.pos(matrix, (float)end.x - size, (float)end.y - size, (float)end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(matrix, (float)end.x - size, (float)end.y + size, (float)end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(matrix, (float)end.x - size, (float)end.y + size, (float)end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(matrix, (float)end.x - size, (float)end.y - size, (float)end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        
        b.pos(matrix, (float)end.x - size, (float)end.y - size, (float)end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(matrix, (float)end.x + size, (float)end.y - size, (float)end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(matrix, (float)end.x + size, (float)end.y + size, (float)end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(matrix, (float)end.x - size, (float)end.y + size, (float)end.z + size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        
        b.pos(matrix, (float)end.x - size, (float)end.y - size, (float)end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(matrix, (float)end.x - size, (float)end.y + size, (float)end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(matrix, (float)end.x + size, (float)end.y + size, (float)end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(matrix, (float)end.x + size, (float)end.y - size, (float)end.z - size).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
    }
    
    public static void renderLaserBeam(IVertexBuilder b, MatrixStack m, Laser laser, Vec3d start, Vec3d end)
    {
        Matrix4f matrix = m.getLast().getMatrix();
        
        b.pos(matrix, (float)start.x, (float)start.y, (float)start.z).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
        b.pos(matrix, (float)end.x, (float)end.y, (float)end.z).color(laser.getR(), laser.getG(), laser.getB(), 1F).endVertex();
    }
    
    public static Vec3d getVectorForRotation(float pitch, float yaw)
    {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3d(f1 * f2, f3, f * f2);
    }
    
    public static Vec3d getOffsetForHandRaw(PlayerEntity entityPlayer, Hand hand)
    {
        Vec3d vec = LaserRenderer.getVectorForRotation(entityPlayer.rotationPitch + 1, entityPlayer.rotationYaw + 90F);
        
        if(hand == Hand.OFF_HAND)
        {
            vec = vec.scale(-1);
        }
        
        if(entityPlayer.rotationPitch >= 89)
        {
            vec = vec.scale(-1);
        }
        
        return new Vec3d(vec.x, 0, vec.z).normalize().scale(0.4D);
    }
    
    public static Vec3d getOffsetForHand(PlayerEntity entityPlayer, Hand hand)
    {
        Vec3d vec = LaserRenderer.getOffsetForHandRaw(entityPlayer, hand);
        
        return vec.add(LaserRenderer.getVectorForRotation(entityPlayer.rotationPitch, entityPlayer.rotationYaw).scale(0.4D));
    }
    
    public static Vec3d findHit(World world, Entity entity, Vec3d start, Vec3d end, float partialTicks)
    {
        LaserRenderer.tmpHitNothing = false;
        
        BlockRayTraceResult resultBlock = LaserRenderer.findBlockOnPath(world, entity, start, end);
        EntityRayTraceResult resultEntity = LaserRenderer.findEntityOnPath(world, entity, start, end, partialTicks);
        
        if(resultEntity != null)
        {
            double rangeBlockSq = resultBlock.getHitVec().squareDistanceTo(start);
            double rangeEntitySq = resultEntity.getHitVec().squareDistanceTo(start);
            
            if(rangeBlockSq < rangeEntitySq)
            {
                return resultBlock.getHitVec();
            }
            else
            {
                return resultEntity.getHitVec();
            }
            
        }
        else
        {
            LaserRenderer.tmpHitNothing = world.getBlockState(resultBlock.getPos()).getBlock() == Blocks.AIR;
            return resultBlock.getHitVec();
        }
    }
    
    public static BlockRayTraceResult findBlockOnPath(World world, Entity entity, Vec3d start, Vec3d end)
    {
        return world.rayTraceBlocks(new RayTraceContext(start, end, BlockMode.COLLIDER, FluidMode.NONE, entity));
    }
    
    public static EntityRayTraceResult findEntityOnPath(World world, Entity entity0, Vec3d start, Vec3d end, float partialTicks)
    {
        EntityRayTraceResult result = null;
        Optional<Vec3d> opt;
        
        double rangeSq = 0;
        double currentRangeSq;
        
        for(Entity entity : world.getEntitiesInAABBexcluding(entity0, GunCusUtility.aabbFromVec3ds(start, end), (entity1) -> true))
        {
            if(entity != null && (entity.getEntityId() != entity0.getEntityId()) && !(entity instanceof EntityBullet))
            {
                AxisAlignedBB axisalignedbb = entity.getBoundingBox().offset(entity.getMotion().scale(partialTicks)).grow(0.30000001192092896D);
                
                opt = axisalignedbb.rayTrace(start, end);
                
                if(opt.isPresent())
                {
                    currentRangeSq = start.squareDistanceTo(entity.getPositionVec());
                    
                    if(currentRangeSq < rangeSq || result == null)
                    {
                        result = new EntityRayTraceResult(entity, opt.get());
                        rangeSq = currentRangeSq;
                    }
                }
            }
        }
        return result;
    }
    
    @SubscribeEvent
    public void renderGameOverlayPre(RenderGameOverlayEvent.Pre event)
    {
        PlayerEntity entityPlayer = ProxyClient.getClientPlayer();
        
        if(event.getType() == ElementType.CROSSHAIRS && entityPlayer != null)
        {
            Accessory accessory;
            Vec3d start = new Vec3d(entityPlayer.getPosX(), entityPlayer.getPosY() + entityPlayer.getEyeHeight(), entityPlayer.getPosZ());
            Vec3d end;
            Vec3d hit;
            
            for(Hand hand : GunCusUtility.HANDS)
            {
                ItemStack itemStack = entityPlayer.getHeldItem(hand);
                
                if(itemStack.getItem() instanceof ItemGun)
                {
                    ItemGun gun = (ItemGun)itemStack.getItem();
                    accessory = gun.getAttachmentCalled(itemStack, EnumAttachmentType.ACCESSORY);
                }
                else if(itemStack.getItem() instanceof Accessory)
                {
                    accessory = (Accessory)itemStack.getItem();
                }
                else
                {
                    accessory = (Accessory)EnumAttachmentType.ACCESSORY.getDefault();
                }
                
                if(accessory.getLaser() != null && accessory.getLaser().isRangeFinder())
                {
                    end = start.add(entityPlayer.getLookVec().normalize().scale(accessory.getLaser().getMaxRange()));
                    hit = LaserRenderer.findHit(entityPlayer.world, entityPlayer, start, end, event.getPartialTicks());
                    
                    hit = hit.subtract(start);
                    
                    LaserRenderer.drawRangeFinder(event.getWindow(), hand, hit.length());
                }
            }
        }
    }
    
    public static void drawRangeFinder(MainWindow sr, Hand hand, double range)
    {
        LaserRenderer.drawRangeFinder(sr, hand, (int)range + "");
    }
    
    public static void drawRangeFinder(MainWindow sr, Hand hand, String text)
    {
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.color4f(1F, 1F, 1F, 1F);
        
        int off = 8;
        FontRenderer font = ProxyClient.getMC().fontRenderer;
        off = hand == Hand.OFF_HAND ? -(font.getStringWidth(text) + 1 + off) : off;
        
        font.drawStringWithShadow(text, sr.getScaledWidth() / 2 + off, sr.getScaledHeight() / 2, 0xFFFFFF);
        
        RenderSystem.disableBlend();
        RenderSystem.popMatrix();
    }
}
