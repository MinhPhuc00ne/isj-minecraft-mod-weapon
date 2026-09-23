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
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Explosion
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.attachment.entity.GauntletAttachment;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerAnimationHandler;
import net.unusual.block_factorys_bosses.entity.OwnableByAllEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeEntity;
import net.unusual.block_factorys_bosses.geckolib.DangerZonesProvider;
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
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
public class IceSpikeClusterEntity
extends Entity
implements OwnableByAllEntity,
GeoEntity,
DangerZonesProvider,
IIceSpike {
    public static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID = SynchedEntityData.defineId(IceSpikeClusterEntity.class, (EntityDataSerializer)EntityDataSerializers.OPTIONAL_UUID);
    public static final EntityDataAccessor<Boolean> DATA_HELD = SynchedEntityData.defineId(IceSpikeClusterEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> DATA_REMAINING_HIT = SynchedEntityData.defineId(IceSpikeClusterEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_HIT_ANIMATION_TIME = SynchedEntityData.defineId(IceSpikeClusterEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_SPAWN_ANIM_TIME = SynchedEntityData.defineId(IceSpikeClusterEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_DELAY = SynchedEntityData.defineId(IceSpikeClusterEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> DATA_EVIL = SynchedEntityData.defineId(IceSpikeClusterEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    public static final int SPAWN_ANIMATION_DURATION = 5;
    public static final int SPAWN_DELAY_DEFAULT = 16;
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private final DangerZonesProvider.DangerZone zone = new DangerZonesProvider.DangerZone();
    private float damage = 10.0f;

    public IceSpikeClusterEntity(EntityType<IceSpikeClusterEntity> type, Level world) {
        super(type, world);
        this.zone.setOffset(new Vector3f(0.0f, 0.0f, 0.0f));
        this.scaleZone();
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_OWNER_UUID, Optional.empty());
        builder.define(DATA_HELD, false);
        builder.define(DATA_REMAINING_HIT, 5);
        builder.define(DATA_HIT_ANIMATION_TIME, 0);
        builder.define(DATA_SPAWN_ANIM_TIME, 0);
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

    public boolean getHeld() {
        return (Boolean)this.entityData.get(DATA_HELD);
    }

    public void setHeld(boolean held) {
        this.entityData.set(DATA_HELD, held);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (DATA_HELD.equals(key)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(key);
    }

    public EntityDimensions getDimensions(Pose pose) {
        if (this.getHeld()) {
            return super.getDimensions(pose).scale(0.1f);
        }
        return super.getDimensions(pose);
    }

    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(BossesRiseItems.ICE_GAUNTLET)) {
            GauntletAttachment attachment = GauntletAttachment.fromPlayer(player);
            int index = attachment.getNewShardIndex(player.getRandom());
            if (index == -1) {
                return InteractionResult.PASS;
            }
            this.entityData.set(DATA_REMAINING_HIT, index);
            attachment.getShards()[index] = this.getUUID();
            player.startUsingItem(hand);
            PlayerAnimationHandler.fromPlayer(player).startAnimation(player, GauntletAttachment.GRAB_AND_HOLD);
            Level level = player.level();
            if (level instanceof ServerLevel) {
                ServerLevel level2 = (ServerLevel)level;
                level2.sendParticles((ParticleOptions)ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 15, 0.25, 0.5, 0.25, 0.0);
            }
            this.setHeld(true);
            this.level().playSound(null, this.blockPosition(), (SoundEvent)BossesRiseSounds.ICICLE_PICKUP.value(), SoundSource.PLAYERS, 0.9f, (float)Mth.nextDouble((RandomSource)this.random, (double)0.15, (double)0.35));
            this.setOwnerUUID(player.getUUID());
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    public void tick() {
        LivingEntity livingEntity = this.getOwner();
        if (livingEntity instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)livingEntity;
            if (this.getHeld() && !player.isUsingItem()) {
                this.setHeld(false);
            }
        }
        super.tick();
    }

    public Vec3 position() {
        Player player;
        LivingEntity livingEntity = this.getOwner();
        if (livingEntity instanceof Player && (player = (Player)livingEntity).isUsingItem() && this.getHeld()) {
            return this.heldPos(player, 1.0f);
        }
        return super.position();
    }

    public Vec3 heldPos(Player player, float partialTick) {
        int place = (Integer)this.entityData.get(DATA_REMAINING_HIT);
        float range = 0.85f;
        float rot = place % 2 == 1 ? 90.0f : -90.0f;
        float offset = place / 2 % 2 == 1 ? range : -range;
        Vec3 eyePosition = player.getEyePosition(partialTick);
        Vec3 viewVector = player.calculateViewVector(0.0f, player.getViewYRot(partialTick));
        return eyePosition.add(viewVector.x * (double)range * 0.5, viewVector.y * (double)range * 0.5 - (double)this.getBbHeight() * 0.5 + (double)offset, viewVector.z * (double)range * 0.5).add(viewVector.scale((double)range).yRot(rot * ((float)Math.PI / 180)));
    }

    public boolean hurt(DamageSource damagesource, float amount) {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        this.playSound((SoundEvent)BossesRiseSounds.ICICLE_HURT.value());
        if ((Integer)this.getEntityData().get(DATA_HIT_ANIMATION_TIME) == 0) {
            int health = (Integer)this.getEntityData().get(DATA_REMAINING_HIT);
            if (health > 1) {
                this.getEntityData().set(DATA_REMAINING_HIT, (health - 1));
                this.level().playSound(null, BlockPos.containing((double)x, (double)y, (double)z), (SoundEvent)BossesRiseSounds.ICICLE_BREAK.value(), SoundSource.NEUTRAL, 1.0f, 3.0f);
                this.getEntityData().set(DATA_HIT_ANIMATION_TIME, 8);
            } else {
                this.remove(Entity.RemovalReason.KILLED);
            }
            return true;
        }
        return false;
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
                this.level().playSound(null, pos, (SoundEvent)BossesRiseSounds.ICICLE_BREAK.value(), SoundSource.NEUTRAL, 0.5f, 0.5f);
            }
            this.level().levelEvent(2001, pos, Block.getId((BlockState)((Block)BossesRiseBlocks.ICE_BLOCK_PARTICULES.get()).defaultBlockState()));
        }
        super.remove(reason);
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putBoolean("held", ((Boolean)this.entityData.get(DATA_HELD)).booleanValue());
        tag.putInt("health", ((Integer)this.entityData.get(DATA_REMAINING_HIT)).intValue());
        tag.putInt("hurt_animation", ((Integer)this.entityData.get(DATA_HIT_ANIMATION_TIME)).intValue());
        tag.putInt("spawn_animation", ((Integer)this.entityData.get(DATA_SPAWN_ANIM_TIME)).intValue());
        if (this.getOwnerUUID() != null) {
            tag.putUUID("owner", this.getOwnerUUID());
        }
        tag.putInt("delay", ((Integer)this.entityData.get(DATA_DELAY)).intValue());
        tag.putBoolean("evil", ((Boolean)this.entityData.get(DATA_EVIL)).booleanValue());
        tag.putInt("age", this.tickCount);
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        this.entityData.set(DATA_HELD, tag.getBoolean("held"));
        this.entityData.set(DATA_REMAINING_HIT, tag.getInt("health"));
        this.entityData.set(DATA_HIT_ANIMATION_TIME, tag.getInt("hurt_animation"));
        this.entityData.set(DATA_SPAWN_ANIM_TIME, tag.getInt("spawn_animation"));
        if (tag.hasUUID("owner")) {
            this.setOwnerUUID(tag.getUUID("owner"));
        }
        this.entityData.set(DATA_DELAY, tag.getInt("delay"));
        this.entityData.set(DATA_EVIL, tag.getBoolean("evil"));
        this.tickCount = tag.getInt("age");
    }

    public void baseTick() {
        ServerLevel level;
        Level level2;
        if (this.firstTick && (level2 = this.level()) instanceof ServerLevel) {
            level = (ServerLevel)level2;
            level.sendParticles((ParticleOptions)ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 10, 0.5, 0.5, 0.5, 0.0);
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
        if ((Integer)this.getEntityData().get(DATA_HIT_ANIMATION_TIME) > 0) {
            this.getEntityData().set(DATA_HIT_ANIMATION_TIME, ((Integer)this.getEntityData().get(DATA_HIT_ANIMATION_TIME) - 1));
        }
        if (this.tickCount >= this.getDelay() && !this.getHeld()) {
            if (this.tickCount == this.getDelay()) {
                this.playSound((SoundEvent)BossesRiseSounds.ICICLE_SPAWN.value(), 0.5f, 1.0f + this.random.nextFloat() * 0.25f);
                level.sendParticles((ParticleOptions)ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 10, 0.5, 0.5, 0.5, 0.0);
                level.getEntities((Entity)this, this.getBoundingBox(), entity1 -> IceSpikeEntity.canHurt(entity1, this)).forEach(entity -> {
                    entity.hurt(entity.damageSources().freeze(), this.damage);
                    entity.setTicksFrozen(200);
                });
            }
            int spawnTime = (Integer)this.getEntityData().get(DATA_SPAWN_ANIM_TIME);
            this.getEntityData().set(DATA_SPAWN_ANIM_TIME, (spawnTime + 1));
            if (spawnTime >= 400 + this.getDelay()) {
                this.discard();
                level.sendParticles((ParticleOptions)ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 5, 0.25, 0.25, 0.25, 0.0);
            }
        }
    }

    public boolean canFreeze() {
        return false;
    }

    public boolean isPickable() {
        return true;
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

