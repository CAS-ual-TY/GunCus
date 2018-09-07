package de.cas_ual_ty.gci;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBullet extends EntityArrow
{
	private int xTile;
	private int yTile;
	private int zTile;
	private Block inTile;
	private int inData;
	protected boolean inGround;
	protected int timeInGround;
	public int arrowShake;
	public Entity shootingEntity;
	private int ticksInAir;
	private float damage;
	private int knockbackStrength;
	
	public EntityBullet(World worldIn) {
		super(worldIn);
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		this.damage = 2.0F;
		this.setSize(0.5F, 0.5F);
	}
	
	public EntityBullet(World worldIn, double x, double y, double z) {
		this(worldIn);
		this.setPosition(x, y, z);
	}
	
	public EntityBullet(World worldIn, EntityLivingBase shooter) {
		this(worldIn, shooter.posX, shooter.posY + shooter.getEyeHeight()
		- 0.10000000149011612D, shooter.posZ);
		this.shootingEntity = shooter;
		if ((shooter instanceof EntityPlayer)) {
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 10.0D;
		if (Double.isNaN(d0)) {
			d0 = 1.0D;
		}
		d0 = d0 * 64.0D * Entity.getRenderDistanceWeight();
		return distance < d0 * d0;
	}
	
	@Override
	protected void entityInit() {
	}
	
	@Override
	public void shoot(Entity shooter, float pitch, float yaw,
			float unused, float velocity, float inaccuracy) {
		float f = -MathHelper.sin(yaw * 0.017453292F)
				* MathHelper.cos(pitch * 0.017453292F);
		float f1 = -MathHelper.sin(pitch * 0.017453292F);
		float f2 = MathHelper.cos(yaw * 0.017453292F)
				* MathHelper.cos(pitch * 0.017453292F);
		this.shoot(f, f1, f2, velocity, inaccuracy);
		this.motionX += shooter.motionX;
		this.motionZ += shooter.motionZ;
		if (!shooter.onGround) {
			this.motionY += shooter.motionY;
		}
	}
	
	@Override
	public void shoot(double x, double y, double z, float velocity,
			float inaccuracy) {
		float f = MathHelper.sqrt(x * x + y * y + z * z);
		x /= f;
		y /= f;
		z /= f;
		x += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
		y += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
		z += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
		x *= velocity;
		y *= velocity;
		z *= velocity;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float f1 = MathHelper.sqrt(x * x + z * z);
		this.rotationYaw = ((float) (MathHelper.atan2(x, z) * 57.29577951308232D));
		this.rotationPitch = ((float) (MathHelper.atan2(y, f1) * 57.29577951308232D));
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotationDirect(double x, double y, double z,
			float yaw, float pitch, int posRotationIncrements, boolean teleport) {
		this.setPosition(x, y, z);
		this.setRotation(yaw, pitch);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double x, double y, double z) {
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		if ((this.prevRotationPitch == 0.0F) && (this.prevRotationYaw == 0.0F)) {
			float f = MathHelper.sqrt(x * x + z * z);
			this.rotationPitch = ((float) (MathHelper.atan2(y, f) * 57.29577951308232D));
			this.rotationYaw = ((float) (MathHelper.atan2(x, z) * 57.29577951308232D));
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ,
					this.rotationYaw, this.rotationPitch);
		}
	}
	
	@Override
	public void onUpdate() {
		this.onEntityUpdate();
		if ((this.prevRotationPitch == 0.0F) && (this.prevRotationYaw == 0.0F)) {
			float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = ((float) (MathHelper.atan2(this.motionX, this.motionZ) * 57.29577951308232D));
			this.rotationPitch = ((float) (MathHelper.atan2(this.motionY, f) * 57.29577951308232D));
			this.prevRotationYaw = this.rotationYaw;
			this.prevRotationPitch = this.rotationPitch;
		}
		BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
		IBlockState iblockstate = this.world.getBlockState(blockpos);
		Block block = iblockstate.getBlock();
		if (iblockstate.getMaterial() != Material.AIR)
		{
			AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(this.world, blockpos);
			if ((axisalignedbb != Block.NULL_AABB)
					&& (axisalignedbb.offset(blockpos).contains(new Vec3d(
							this.posX, this.posY, this.posZ)))) {
				this.inGround = true;
			}
		}
		if (this.arrowShake > 0) {
			this.arrowShake -= 1;
		}
		if (this.inGround) {
			this.setDead();
		} else {
			this.timeInGround = 0;
			this.ticksInAir += 1;
			
			if(this.ticksInAir >= 200)
			{
				this.setDead();
			}
			
			Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
			Vec3d vec3d = new Vec3d(this.posX + this.motionX, this.posY
					+ this.motionY, this.posZ + this.motionZ);
			RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d1,
					vec3d, false, true, false);
			vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
			vec3d = new Vec3d(this.posX + this.motionX, this.posY
					+ this.motionY, this.posZ + this.motionZ);
			if (raytraceresult != null) {
				vec3d = new Vec3d(raytraceresult.hitVec.x,
						raytraceresult.hitVec.y, raytraceresult.hitVec.z);
			}
			Entity entity = this.findEntityOnPath(vec3d1, vec3d);
			if (entity != null) {
				raytraceresult = new RayTraceResult(entity);
			}
			if ((raytraceresult != null)
					&& ((raytraceresult.entityHit instanceof EntityPlayer))) {
				EntityPlayer entityplayer = (EntityPlayer) raytraceresult.entityHit;
				if (((this.shootingEntity instanceof EntityPlayer))
						&& (!((EntityPlayer) this.shootingEntity)
								.canAttackPlayer(entityplayer))) {
					raytraceresult = null;
				}
			}
			if ((raytraceresult != null)
					&& (!ForgeEventFactory.onProjectileImpact(this,
							raytraceresult))) {
				this.onHit(raytraceresult);
			}
			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			float f4 = MathHelper.sqrt(this.motionX * this.motionX
					+ this.motionZ * this.motionZ);
			this.rotationYaw = ((float) (MathHelper.atan2(this.motionX,
					this.motionZ) * 57.29577951308232D));
			for (this.rotationPitch = ((float) (MathHelper.atan2(this.motionY,
					f4) * 57.29577951308232D)); this.rotationPitch
					- this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
			}
			while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
				this.prevRotationPitch += 360.0F;
			}
			while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
				this.prevRotationYaw -= 360.0F;
			}
			while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
				this.prevRotationYaw += 360.0F;
			}
			this.rotationPitch = (this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F);
			this.rotationYaw = (this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F);
			float f1 = 0.99F;
			float f2 = 0.05F;
			if (this.isInWater()) {
				this.setDead();
			}
			this.motionX *= f1;
			this.motionY *= f1;
			this.motionZ *= f1;
			
			this.setPosition(this.posX, this.posY, this.posZ);
			
			this.doBlockCollisions();
		}
	}
	
	@Override
	protected void onHit(RayTraceResult raytraceResultIn) {
		Entity entity = raytraceResultIn.entityHit;
		if (entity != null) {
			DamageSource damagesource = null;
			if (this.shootingEntity == null) {
				damagesource = DamageSource.causeArrowDamage(this, this);
			} else {
				damagesource = DamageSource.causeArrowDamage(this,
						this.shootingEntity);
			}
			if ((this.isBurning()) && (!(entity instanceof EntityEnderman))) {
				entity.setFire(5);
			}
			if (entity.attackEntityFrom(damagesource, this.damage)) {
				if ((entity instanceof EntityLivingBase)) {
					EntityLivingBase entitylivingbase = (EntityLivingBase) entity;
					if (!this.world.isRemote) {
						entitylivingbase.setArrowCountInEntity(entitylivingbase
								.getArrowCountInEntity() + 1);
					}
					if (this.knockbackStrength > 0) {
						float f1 = MathHelper.sqrt(this.motionX * this.motionX
								+ this.motionZ * this.motionZ);
						if (f1 > 0.0F) {
							entitylivingbase.addVelocity(this.motionX
									* this.knockbackStrength
									* 0.6000000238418579D / f1, 0.1D,
									this.motionZ * this.knockbackStrength
									* 0.6000000238418579D / f1);
						}
					}
					if ((this.shootingEntity instanceof EntityLivingBase)) {
						EnchantmentHelper.applyThornEnchantments(
								entitylivingbase, this.shootingEntity);
						EnchantmentHelper.applyArthropodEnchantments(
								(EntityLivingBase) this.shootingEntity,
								entitylivingbase);
					}
					this.arrowHit(entitylivingbase);
					if ((this.shootingEntity != null)
							&& (entitylivingbase != this.shootingEntity)
							&& ((entitylivingbase instanceof EntityPlayer))
							&& ((this.shootingEntity instanceof EntityPlayerMP))) {
						((EntityPlayerMP) this.shootingEntity).connection
						.sendPacket(new SPacketChangeGameState(6, 0.0F));
					}
				}
				//				playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F)); TODO
				if (!(entity instanceof EntityEnderman)) {
					this.setDead();
				}
			} else {
				this.setDead();
			}
		} else {
			/*
			BlockPos blockpos = raytraceResultIn.getBlockPos();
			this.xTile = blockpos.getX();
			this.yTile = blockpos.getY();
			this.zTile = blockpos.getZ();
			IBlockState iblockstate = this.world.getBlockState(blockpos);
			this.inTile = iblockstate.getBlock();
			this.inData = this.inTile.getMetaFromState(iblockstate);
			this.motionX = ((float) (raytraceResultIn.hitVec.x - this.posX));
			this.motionY = ((float) (raytraceResultIn.hitVec.y - this.posY));
			this.motionZ = ((float) (raytraceResultIn.hitVec.z - this.posZ));
			float f2 = MathHelper
					.sqrt(this.motionX * this.motionX + this.motionY
			 * this.motionY + this.motionZ * this.motionZ);
			this.posX -= this.motionX / f2 * 0.05000000074505806D;
			this.posY -= this.motionY / f2 * 0.05000000074505806D;
			this.posZ -= this.motionZ / f2 * 0.05000000074505806D;
			playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F,
					1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
			this.inGround = true;
			this.arrowShake = 7;
			if (iblockstate.getMaterial() != Material.AIR) {
				this.inTile.onEntityCollidedWithBlock(this.world, blockpos,
						iblockstate, this);
			}*/
			this.setDead();
		}
	}
	
	@Override
	public void move(MoverType type, double x, double y, double z) {
		super.move(type, x, y, z);
		if (this.inGround) {
			this.xTile = MathHelper.floor(this.posX);
			this.yTile = MathHelper.floor(this.posY);
			this.zTile = MathHelper.floor(this.posZ);
		}
	}
	
	@Override
	protected void arrowHit(EntityLivingBase living) {
	}
	
	private static final Predicate<Entity> ARROW_TARGETS = Predicates
			.and(new Predicate[] { EntitySelectors.NOT_SPECTATING,
					EntitySelectors.IS_ALIVE, new Predicate() {
				public boolean apply(@Nullable Entity e) {
					return e.canBeCollidedWith();
				}
				
				@Override
				public boolean test(Object arg0) {
					return false;
				}
				
				@Override
				public boolean apply(Object input) {
					return input instanceof Entity ? this
							.apply((Entity) input) : false;
				}
			} });
	
	@Override
	@Nullable
	protected Entity findEntityOnPath(Vec3d start, Vec3d end) {
		Entity entity = null;
		List<Entity> list = this.world.getEntitiesInAABBexcluding(
				this,
				this.getEntityBoundingBox().expand(this.motionX, this.motionY,
						this.motionZ).grow(1.0D), EntityBullet.ARROW_TARGETS);
		double d0 = 0.0D;
		for (int i = 0; i < list.size(); i++) {
			Entity entity1 = list.get(i);
			if ((entity1 != this.shootingEntity) || (this.ticksInAir >= 5)) {
				AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox()
						.grow(0.30000001192092896D);
				RayTraceResult raytraceresult = axisalignedbb
						.calculateIntercept(start, end);
				if (raytraceresult != null) {
					double d1 = start.squareDistanceTo(raytraceresult.hitVec);
					if ((d1 < d0) || (d0 == 0.0D)) {
						entity = entity1;
						d0 = d1;
					}
				}
			}
		}
		return entity;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("xTile", this.xTile);
		compound.setInteger("yTile", this.yTile);
		compound.setInteger("zTile", this.zTile);
		ResourceLocation resourcelocation = Block.REGISTRY
				.getNameForObject(this.inTile);
		compound.setString("inTile", resourcelocation == null ? ""
				: resourcelocation.toString());
		compound.setByte("inData", (byte) this.inData);
		compound.setByte("shake", (byte) this.arrowShake);
		compound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
		compound.setFloat("damage", this.damage);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		this.xTile = compound.getInteger("xTile");
		this.yTile = compound.getInteger("yTile");
		this.zTile = compound.getInteger("zTile");
		if (compound.hasKey("inTile", 8)) {
			this.inTile = Block.getBlockFromName(compound.getString("inTile"));
		} else {
			this.inTile = Block.getBlockById(compound.getByte("inTile") & 0xFF);
		}
		this.inData = (compound.getByte("inData") & 0xFF);
		this.arrowShake = (compound.getByte("shake") & 0xFF);
		this.inGround = (compound.getByte("inGround") == 1);
		if (compound.hasKey("damage", 99)) {
			this.damage = compound.getFloat("damage");
		}
	}
	
	@Override
	public void onCollideWithPlayer(EntityPlayer entityIn)
	{
	}
	
	public void setDamage(float damageIn) {
		this.damage = damageIn;
	}
	
	@Override
	public double getDamage() {
		return this.damage;
	}
	
	@Override
	public void setKnockbackStrength(int knockbackStrengthIn) {
		this.knockbackStrength = knockbackStrengthIn;
	}
	
	@Override
	public boolean canBeAttackedWithItem() {
		return false;
	}
	
	@Override
	public float getEyeHeight() {
		return 0.0F;
	}
	
	@Override
	protected ItemStack getArrowStack()
	{
		return ItemStack.EMPTY;
	}
}