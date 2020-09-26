package de.cas_ual_ty.guncus.entity;

import de.cas_ual_ty.guncus.GunCus;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class EntityBullet extends ThrowableEntity
{
    public static final float BASE_SPEED = 20F;
    public static final int TICKS = 1;
    
    protected static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(EntityBullet.class, DataSerializers.FLOAT);
    protected static final DataParameter<Float> GRAVITY = EntityDataManager.createKey(EntityBullet.class, DataSerializers.FLOAT);
    
    public EntityBullet(EntityType<EntityBullet> type, World worldIn)
    {
        super(type, worldIn);
    }
    
    public EntityBullet(EntityType<EntityBullet> type, LivingEntity livingEntityIn, World worldIn)
    {
        super(type, livingEntityIn, worldIn);
    }
    
    public EntityBullet setDamage(float damage)
    {
        this.dataManager.set(EntityBullet.DAMAGE, damage);
        return this;
    }
    
    public EntityBullet setGravity(float gravity)
    {
        this.dataManager.set(EntityBullet.GRAVITY, gravity);
        return this;
    }
    
    @Override
    public void tick()
    {
        this.setMotion(this.getMotion().scale(1F / EntityBullet.TICKS));
        for(int i = 0; i < EntityBullet.TICKS; ++i)
        {
            super.tick();
        }
        this.setMotion(this.getMotion().scale(EntityBullet.TICKS));
        
        this.spawnParticles();
        
        if(this.ticksExisted >= 20)
        {
            this.remove();
        }
    }
    
    public void spawnParticles()
    {
        Vector3d mot = this.getMotion();
        Vector3d pos = this.getPositionVec();
        double d1 = mot.x;
        double d2 = mot.y;
        double d0 = mot.z;
        for(int i = 0; i < 4; ++i)
        {
            this.world.addParticle(ParticleTypes.CRIT, pos.x + d1 * (double)i / 4.0D, pos.y + d2 * (double)i / 4.0D, pos.z + d0 * (double)i / 4.0D, -d1, -d2 + 0.2D, -d0);
            this.world.addParticle(ParticleTypes.CLOUD, pos.x + d1 * (double)i / 4.0D, pos.y + d2 * (double)i / 4.0D, pos.z + d0 * (double)i / 4.0D, -d1, -d2 + 0.2D, -d0);
            this.world.addParticle(ParticleTypes.EXPLOSION, pos.x + d1 * (double)i / 4.0D, pos.y + d2 * (double)i / 4.0D, pos.z + d0 * (double)i / 4.0D, -d1, -d2 + 0.2D, -d0);
            this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, pos.x + d1 * (double)i / 4.0D, pos.y + d2 * (double)i / 4.0D, pos.z + d0 * (double)i / 4.0D, -d1, -d2 + 0.2D, -d0);
        }
    }
    
    @Override
    protected void onImpact(RayTraceResult result)
    {
        if(result.getType() == Type.ENTITY)
        {
            EntityRayTraceResult hit = (EntityRayTraceResult)result;
            
            if((hit.getEntity() == this.func_234616_v_()) && (this.ticksExisted <= 5))
            {
                return;
            }
            
            if(!this.world.isRemote && hit.getEntity() instanceof LivingEntity)
            {
                LivingEntity entity = (LivingEntity)hit.getEntity();
                entity.attackEntityFrom(DamageSource.causeMobDamage((LivingEntity)this.func_234616_v_()), this.getBulletDamage());
                
                if(this.func_234616_v_() instanceof PlayerEntity)
                {
                    GunCus.proxy.addHitmarker((PlayerEntity)this.func_234616_v_());
                }
            }
        }
        
        this.remove();
    }
    
    @Override
    protected void registerData()
    {
        this.dataManager.register(EntityBullet.DAMAGE, 4F);
        this.dataManager.register(EntityBullet.GRAVITY, 1F);
    }
    
    @Override
    protected float getGravityVelocity()
    {
        return super.getGravityVelocity() * this.getBulletGravity() / EntityBullet.TICKS;
    }
    
    protected float getBulletDamage()
    {
        return this.dataManager.get(EntityBullet.DAMAGE);
    }
    
    protected float getBulletGravity()
    {
        return this.dataManager.get(EntityBullet.GRAVITY);
    }
}
