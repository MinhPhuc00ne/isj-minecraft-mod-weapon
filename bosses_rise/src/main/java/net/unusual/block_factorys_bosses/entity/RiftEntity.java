/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.particles.ColorParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.material.PushReaction
 */
package net.unusual.block_factorys_bosses.entity;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.unusual.block_factorys_bosses.entity.projectile.BigRiftProjectileEntity;
import net.unusual.block_factorys_bosses.entity.projectile.RiftProjectileEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RiftEntity
extends Entity {
    private static final String TAG_TIMER = "Timer";
    private int timer = 120;
    private Entity owner;
    private Entity target;

    public RiftEntity(EntityType<RiftEntity> type, Level world) {
        super(type, world);
        this.setNoGravity(true);
        this.noPhysics = true;
    }

    public void setOwnerAndTarget(Entity owner, Entity target) {
        this.owner = owner;
        this.target = target;
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt(TAG_TIMER, this.timer);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains(TAG_TIMER)) {
            this.timer = compound.getInt(TAG_TIMER);
        }
    }

    public void baseTick() {
        super.baseTick();
        this.level().addParticle((ParticleOptions)ColorParticleOption.create((ParticleType)((ParticleType)BossesRiseParticleTypes.PIXEL.get()), (int)62969), true, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        if (this.timer == 120) {
            this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.RIFT_OPEN.get(), true, this.getX(), this.getY(), this.getZ(), 10.0, 0.0, 0.0);
            this.playSound((SoundEvent)BossesRiseSounds.RIFT_OPEN.value(), 8.0f, 1.0f);
            this.playSound((SoundEvent)BossesRiseSounds.RIFT_IDLE.value(), 4.0f, 1.0f);
        }
        if (this.timer == 112) {
            this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.BIG_STAR.get(), true, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
        if (this.timer == 110) {
            this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.RIFT.get(), true, this.getX(), this.getY(), this.getZ(), 100.0, 0.0, 0.0);
        }
        if (this.timer == 30) {
            this.playSound((SoundEvent)BossesRiseSounds.RIFT_CLOSE.value(), 8.0f, 1.0f);
        }
        if (this.timer == 10) {
            this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.RIFT_CLOSE.get(), true, this.getX(), this.getY(), this.getZ(), 10.0, 0.0, 0.0);
        }
        if (this.timer > 20 && this.timer < 110) {
            double z;
            double y;
            double x;
            if (this.timer % 2 == 0) {
                SimpleParticleType particle = (double)this.random.nextFloat() > 0.5 ? (SimpleParticleType)BossesRiseParticleTypes.SOUL.get() : (SimpleParticleType)BossesRiseParticleTypes.SOUL_FLIP.get();
                x = this.getX() + (double)this.random.nextFloat() * 4.5 - 2.25;
                y = this.getY() + (double)(this.random.nextFloat() * 9.0f) - 4.5;
                z = this.getZ() + (double)this.random.nextFloat() * 4.5 - 2.25;
                this.level().addParticle((ParticleOptions)particle, true, x, y, z, 0.0, 0.0, 0.0);
            }
            double x2 = this.getX() + (double)(this.random.nextFloat() * 4.0f) - 2.0;
            double y2 = this.getY() + (double)(this.random.nextFloat() * 8.0f) - 4.0;
            double z2 = this.getZ() + (double)(this.random.nextFloat() * 4.0f) - 2.0;
            this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.WEIRD_SMOKE.get(), true, x2, y2, z2, this.getX(), this.getY(), this.getZ());
            for (int i = 0; i < 2; ++i) {
                x = this.getX() + (double)(this.random.nextFloat() * 3.0f) - 1.5;
                y = this.getY() + (double)(this.random.nextFloat() * 6.0f) - 3.0;
                z = this.getZ() + (double)(this.random.nextFloat() * 3.0f) - 1.5;
                this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.STAR.get(), true, x, y, z, 0.0, 0.0, 0.0);
            }
            if (!this.level().isClientSide() && this.timer % 2 == 0 && this.owner != null && this.target != null) {
                RiftProjectileEntity proj = this.timer % 20 == 0 ? new BigRiftProjectileEntity((EntityType<? extends BigRiftProjectileEntity>)((EntityType)BossesRiseEntities.BIG_RIFT_PROJECTILE.get()), this.level()) : new RiftProjectileEntity((EntityType<? extends RiftProjectileEntity>)((EntityType)BossesRiseEntities.RIFT_PROJECTILE.get()), this.level());
                proj.setOwner(this.owner);
                proj.setTarget(this.target);
                proj.setPos(this.getX(), this.getY(), this.getZ());
                proj.shoot(0.0, 1.0, 0.0, 1.2f, 25.0f);
                this.level().addFreshEntity((Entity)proj);
            }
        }
        if (--this.timer <= 0) {
            this.discard();
        }
    }

    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }
}

