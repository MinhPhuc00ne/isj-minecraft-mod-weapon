/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ColorParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.entity.projectile.ProjectileUtil
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockBehaviour$BlockStateBase
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.neoforge.event.EventHooks
 */
package net.unusual.block_factorys_bosses.entity.projectile;

import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import net.unusual.block_factorys_bosses.client.ClientParticleHandler;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;

public class RiftProjectileEntity
extends Projectile {
    private Entity target;
    private int timer = 0;

    public RiftProjectileEntity(EntityType<? extends RiftProjectileEntity> type, Level world) {
        super(type, world);
    }

    protected double getDefaultGravity() {
        return 0.0;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public void tick() {
        super.tick();
        Vec3 vel = this.getDeltaMovement();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector((Entity)this, x$0 -> this.canHitEntity((Entity)x$0));
        if (hitresult.getType() != HitResult.Type.MISS && !EventHooks.onProjectileImpact((Projectile)this, (HitResult)hitresult)) {
            this.hitTargetOrDeflectSelf(hitresult);
        }
        double d0 = this.getX() + vel.x;
        double d1 = this.getY() + vel.y;
        double d2 = this.getZ() + vel.z;
        this.updateRotation();
        if (++this.timer >= 60) {
            this.discard();
        } else if (this.level().getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir)) {
            this.discard();
        } else if (this.isInWaterOrBubble()) {
            this.discard();
        } else {
            double speed = vel.length();
            vel = vel.normalize();
            if (this.timer < 20 && this.target != null) {
                Vec3 targetDir = this.target.position().subtract(this.position()).normalize();
                double dot = vel.dot(targetDir);
                double theta = Math.min(Math.acos(Math.clamp((double)dot, (double)-1.0, (double)1.0)), Math.toRadians(281.25 * speed) / 20.0);
                Vec3 rotationAxis = vel.cross(targetDir).normalize();
                double cosTheta = Math.cos(theta);
                double sinTheta = Math.sin(theta);
                Vec3 comp1 = vel.scale(cosTheta);
                Vec3 comp2 = rotationAxis.cross(vel).scale(sinTheta);
                Vec3 comp3 = rotationAxis.scale(dot * (1.0 - cosTheta));
                vel = comp1.add(comp2).add(comp3);
            }
            this.setDeltaMovement(vel.normalize().scale(speed));
            this.applyGravity();
            this.setPos(d0, d1, d2);
        }
        if (this.level().isClientSide()) {
            for (int i = 0; i < 2; ++i) {
                Vec3 pos = new Vec3(this.getX() + (double)this.random.nextFloat() - 0.5, this.getY() + (double)this.random.nextFloat() - 0.5, this.getZ() + (double)this.random.nextFloat() - 0.5);
                ClientParticleHandler.addParticle((ParticleOptions)ColorParticleOption.create((ParticleType)((ParticleType)BossesRiseParticleTypes.PIXEL.get()), (int)62969), pos, Vec3.ZERO);
            }
        }
    }

    protected void onHitEntity(EntityHitResult result) {
        Level level;
        Level level2;
        DamageSource damagesource;
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        if (this.getOwner() != entity && entity.hurt(damagesource = this.damageSources().magic(), 2.0f) && (level2 = this.level()) instanceof ServerLevel) {
            ServerLevel serverlevel = (ServerLevel)level2;
            EnchantmentHelper.doPostAttackEffects((ServerLevel)serverlevel, (Entity)entity, (DamageSource)damagesource);
        }
        if ((level = this.level()) instanceof ServerLevel) {
            ServerLevel level3 = (ServerLevel)level;
            level3.playSound(null, this.getX(), this.getY(), this.getZ(), BossesRiseSounds.RIFT_FIREBALL, SoundSource.HOSTILE, 0.5f, 1.0f);
            this.discard();
        }
    }

    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        Level level = this.level();
        if (level instanceof ServerLevel) {
            ServerLevel level2 = (ServerLevel)level;
            level2.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.RING.get()), this.getX(), this.getY(), this.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
            level2.playSound(null, this.getX(), this.getY(), this.getZ(), BossesRiseSounds.RIFT_FIREBALL, SoundSource.HOSTILE, 0.25f, 1.0f);
            this.discard();
        }
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Timer", this.timer);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.timer = compound.getInt("Timer");
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    public boolean shouldRenderAtSqrDistance(double distance) {
        double d0 = this.getBoundingBox().getSize() * 10.0;
        if (Double.isNaN(d0)) {
            d0 = 0.0;
        }
        return distance < (d0 *= 64.0 * RiftProjectileEntity.getViewScale()) * d0;
    }
}

