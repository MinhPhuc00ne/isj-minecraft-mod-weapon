/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Position
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.OwnableEntity
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.level.Explosion
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector3f
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animatable.GeoEntity
 *  software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package net.unusual.block_factorys_bosses.entity.boss.yeti;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.unusual.block_factorys_bosses.entity.OwnableByAllEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeClusterEntity;
import net.unusual.block_factorys_bosses.geckolib.DangerZonesProvider;
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class IceSpikeEntity
extends Entity
implements OwnableByAllEntity,
GeoEntity,
DangerZonesProvider,
IIceSpike {
    public static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID = SynchedEntityData.defineId(IceSpikeEntity.class, (EntityDataSerializer)EntityDataSerializers.OPTIONAL_UUID);
    public static final EntityDataAccessor<Integer> DATA_SPAWN_ANIM_TIME = SynchedEntityData.defineId(IceSpikeEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_SCALE = SynchedEntityData.defineId(IceSpikeEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_DELAY = SynchedEntityData.defineId(IceSpikeEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> DATA_EVIL = SynchedEntityData.defineId(IceSpikeEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    public static final int SPAWN_ANIMATION_DURATION = 11;
    public static final int DEFAULT_SCALE = 4;
    public static final int SPAWN_DELAY_DEFAULT = 4;
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private final DangerZonesProvider.DangerZone zone = new DangerZonesProvider.DangerZone();
    private float damage = 10.0f;

    public IceSpikeEntity(EntityType<IceSpikeEntity> type, Level world) {
        super(type, world);
        this.zone.setOffset(new Vector3f(0.0f, 0.0f, 0.0f));
        this.scaleZone();
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_OWNER_UUID, Optional.empty());
        builder.define(DATA_SPAWN_ANIM_TIME, 0);
        builder.define(DATA_SCALE, 4);
        builder.define(DATA_DELAY, 16);
        builder.define(DATA_EVIL, false);
    }

    private void scaleZone() {
        float size = this.getDimensions(Pose.STANDING).width();
        this.zone.setSize(size, size);
    }

    public AABB getBoundingBoxForCulling() {
        return super.getBoundingBoxForCulling().inflate(3.0);
    }

    @Nullable
    public UUID getOwnerUUID() {
        return ((Optional<UUID>)this.entityData.get(DATA_OWNER_UUID)).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID uuid) {
        this.entityData.set(DATA_OWNER_UUID, Optional.ofNullable(uuid));
    }

    public int getScale() {
        return (Integer)this.entityData.get(DATA_SCALE);
    }

    public void setScale(int scale) {
        if (scale < 1) {
            scale = 1;
        } else if (scale > 4) {
            scale = 4;
        }
        this.entityData.set(DATA_SCALE, scale);
    }

    public int getDelay() {
        return (Integer)this.entityData.get(DATA_DELAY);
    }

    public void setDelay(int scale) {
        this.entityData.set(DATA_DELAY, scale);
    }

    public boolean getEvil() {
        return (Boolean)this.entityData.get(DATA_EVIL);
    }

    public void setEvil(boolean evil) {
        this.entityData.set(DATA_EVIL, evil);
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return this.damage;
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (DATA_SCALE.equals(key)) {
            this.refreshDimensions();
            this.scaleZone();
        }
        super.onSyncedDataUpdated(key);
    }

    public EntityDimensions getDimensions(Pose pose) {
        float size = switch (this.getScale()) {
            case 1 -> 0.4f;
            case 2 -> 0.6f;
            case 3 -> 0.8f;
            default -> 1.0f;
        };
        return super.getDimensions(pose).scale(size);
    }

    public boolean ignoreExplosion(Explosion explosion) {
        return true;
    }

    public boolean fireImmune() {
        return true;
    }

    public void remove(Entity.RemovalReason reason) {
        if (reason == Entity.RemovalReason.KILLED) {
            BlockPos pos = BlockPos.containing((Position)this.position());
            if (this.random.nextInt(10) == 0) {
                this.level().playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL, 0.5f, 0.5f);
            }
            this.level().levelEvent(2001, pos, Block.getId((BlockState)((Block)BossesRiseBlocks.ICE_BLOCK_PARTICULES.get()).defaultBlockState()));
        }
        super.remove(reason);
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("spawn_animation", ((Integer)this.entityData.get(DATA_SPAWN_ANIM_TIME)).intValue());
        if (this.getOwnerUUID() != null) {
            tag.putUUID("owner", this.getOwnerUUID());
        }
        tag.putInt("scale", ((Integer)this.entityData.get(DATA_SCALE)).intValue());
        tag.putInt("delay", ((Integer)this.entityData.get(DATA_DELAY)).intValue());
        tag.putBoolean("evil", ((Boolean)this.entityData.get(DATA_EVIL)).booleanValue());
        tag.putInt("age", this.tickCount);
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        this.entityData.set(DATA_SPAWN_ANIM_TIME, tag.getInt("spawn_animation"));
        if (tag.hasUUID("owner")) {
            this.setOwnerUUID(tag.getUUID("owner"));
        }
        this.entityData.set(DATA_SCALE, tag.getInt("scale"));
        this.entityData.set(DATA_DELAY, tag.getInt("delay"));
        this.entityData.set(DATA_EVIL, tag.getBoolean("evil"));
        this.tickCount = tag.getInt("age");
    }

    public void baseTick() {
        ServerLevel level;
        Level level2;
        if (this.firstTick && (level2 = this.level()) instanceof ServerLevel) {
            level = (ServerLevel)level2;
            level.sendParticles((ParticleOptions)ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), this.getScale() * 2, 0.5, 0.5, 0.5, 0.0);
            level.getEntities((Entity)this, this.getBoundingBox(), entity1 -> IceSpikeEntity.canHurt(entity1, this)).forEach(entity -> {
                if (entity instanceof LivingEntity) {
                    LivingEntity living = (LivingEntity)entity;
                    living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1));
                }
            });
        }
        super.baseTick();
        level2 = this.level();
        if (!(level2 instanceof ServerLevel)) {
            return;
        }
        level = (ServerLevel)level2;
        if (this.tickCount >= this.getDelay()) {
            if (this.tickCount == this.getDelay()) {
                this.playSound((SoundEvent)BossesRiseSounds.ICICLE_SPAWN.value(), 0.15f, 1.0f + this.random.nextFloat() * 0.25f);
                level.sendParticles((ParticleOptions)ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), this.getScale() * 2, 0.5, 0.5, 0.5, 0.0);
                level.getEntities((Entity)this, this.getBoundingBox(), entity1 -> IceSpikeEntity.canHurt(entity1, this)).forEach(entity -> {
                    entity.hurt(entity.damageSources().freeze(), this.damage);
                    entity.setTicksFrozen(200);
                });
            }
            int spawnTime = (Integer)this.getEntityData().get(DATA_SPAWN_ANIM_TIME);
            this.getEntityData().set(DATA_SPAWN_ANIM_TIME, (spawnTime + 1));
            if (spawnTime == 11) {
                this.discard();
                level.sendParticles((ParticleOptions)ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), this.getScale(), 0.25, 0.25, 0.25, 0.0);
            }
        }
    }

    public static <T extends Entity> boolean canHurt(Entity target, T iceSpike) {
        OwnableEntity ownable;
        if (((OwnableByAllEntity)iceSpike).getOwner() == target) {
            return false;
        }
        if (target instanceof OwnableEntity && (ownable = (OwnableEntity)target).getOwnerUUID() == ((OwnableEntity)iceSpike).getOwnerUUID()) {
            return false;
        }
        return target.isAlive() && !target.isSpectator() && !(target instanceof IceSpikeEntity) && !(target instanceof IceSpikeClusterEntity);
    }

    public boolean canFreeze() {
        return false;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    @Override
    public Collection<DangerZonesProvider.DangerZone> getDangerZones() {
        if (this.tickCount < this.getDelay()) {
            return List.of(this.zone);
        }
        return List.of();
    }
}

