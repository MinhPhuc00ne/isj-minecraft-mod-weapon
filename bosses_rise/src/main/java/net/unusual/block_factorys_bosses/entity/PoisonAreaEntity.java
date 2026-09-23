/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.material.PushReaction
 *  net.minecraft.world.phys.Vec3
 */
package net.unusual.block_factorys_bosses.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockEmitterParticleOptions;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;

public class PoisonAreaEntity
extends Entity {
    private static final String TAG_LIVING_TIME = "Dataliving_time";
    public static final EntityDataAccessor<Integer> DATA_LIVING_TIME = SynchedEntityData.defineId(PoisonAreaEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    private final Map<Entity, Integer> victims = new HashMap<Entity, Integer>();

    public PoisonAreaEntity(EntityType<PoisonAreaEntity> type, Level world) {
        super(type, world);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_LIVING_TIME, 130);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt(TAG_LIVING_TIME, ((Integer)this.entityData.get(DATA_LIVING_TIME)).intValue());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains(TAG_LIVING_TIME)) {
            this.entityData.set(DATA_LIVING_TIME, compound.getInt(TAG_LIVING_TIME));
        }
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            if (this.tickCount % 5 == 2 && this.getRandom().nextInt(3) == 0) {
                this.level().addParticle((ParticleOptions)new BedrockEmitterParticleOptions((ParticleType<BedrockEmitterParticleOptions>)((ParticleType)BossesRiseParticleTypes.POISON_AOE.get()), null, true), this.getX(), this.getY() + 0.2, this.getZ(), 0.0, 0.0, 0.0);
            }
        } else {
            this.victims.values().removeIf(removeAtTick -> removeAtTick <= this.tickCount);
            List<LivingEntity> touchingEntities = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox());
            for (LivingEntity touchingEntity : touchingEntities) {
                if (!touchingEntity.isAffectedByPotions() || this.victims.containsKey(touchingEntity)) continue;
                this.victims.put((Entity)touchingEntity, this.tickCount + 20);
                touchingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 1));
            }
        }
    }

    public void baseTick() {
        super.baseTick();
        this.setDeltaMovement(new Vec3(0.0, 0.0, 0.0));
        if ((Integer)this.getEntityData().get(DATA_LIVING_TIME) > 0) {
            this.getEntityData().set(DATA_LIVING_TIME, ((Integer)this.getEntityData().get(DATA_LIVING_TIME) - 1));
        } else if (!this.level().isClientSide()) {
            this.discard();
        }
    }

    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }
}

