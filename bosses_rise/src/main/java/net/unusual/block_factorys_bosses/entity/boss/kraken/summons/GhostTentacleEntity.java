/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.commands.arguments.EntityAnchorArgument$Anchor
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.Difficulty
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.SpawnPlacementTypes
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.MeleeAttackGoal
 *  net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
 *  net.minecraft.world.entity.ai.goal.RandomStrollGoal
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.level.material.PushReaction
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent
 *  net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent$Operation
 *  org.jetbrains.annotations.Nullable
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animatable.GeoEntity
 *  software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.animation.AnimationController
 *  software.bernie.geckolib.animation.RawAnimation
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package net.unusual.block_factorys_bosses.entity.boss.kraken.summons;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.unusual.block_factorys_bosses.block.entity.KrakenSpawnerBlockEntity;
import net.unusual.block_factorys_bosses.entity.OwnableByAllEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GhostTentacleEntity
extends Monster
implements GeoEntity,
OwnableByAllEntity,
KrakenSpawnerBlockEntity.SpawnerTied {
    public static final EntityDataAccessor<Integer> ATTACK_COOLDOWN = SynchedEntityData.defineId(GhostTentacleEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ATTACK_ANIMTIME = SynchedEntityData.defineId(GhostTentacleEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(GhostTentacleEntity.class, (EntityDataSerializer)EntityDataSerializers.OPTIONAL_UUID);
    public static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("animation.bf_br_ghost_tentacle.idle");
    public static final RawAnimation IDLE_ANIM_TWO = RawAnimation.begin().thenLoop("animation.bf_br_ghost_tentacle.idle2");
    public static final RawAnimation ATTACK_ANIM = RawAnimation.begin().thenLoop("animation.bf_br_ghost_tentacle.attack");
    public static final RawAnimation SPAWN_ANIM = RawAnimation.begin().thenPlay("animation.bf_br_ghost_tentacle.spawn");
    public static final RawAnimation DEATH_ANIM = RawAnimation.begin().thenPlay("animation.bf_br_ghost_tentacle.despawn");
    private static final int DEATH_DURATION = 30;
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);

    public GhostTentacleEntity(EntityType<GhostTentacleEntity> type, Level world) {
        super(type, world);
        this.setPersistenceRequired();
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACK_COOLDOWN, 30);
        builder.define(ATTACK_ANIMTIME, 0);
        builder.define(OWNER_UUID, Optional.empty());
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, (Goal)new MeleeAttackGoal((PathfinderMob)this, 0.9, false){

            protected boolean canPerformAttack(LivingEntity entity) {
                return false;
            }
        });
        this.goalSelector.addGoal(3, (Goal)new RandomStrollGoal((PathfinderMob)this, 0.8));
        this.goalSelector.addGoal(4, (Goal)new RandomLookAroundGoal((Mob)this));
        this.targetSelector.addGoal(1, new OwnableByAllEntity.OwnerHurtByTargetGoal<GhostTentacleEntity>(this));
        this.targetSelector.addGoal(2, new OwnableByAllEntity.OwnerHurtTargetGoal<GhostTentacleEntity>(this));
        this.targetSelector.addGoal(3, new OwnableByAllEntity.DoNotAttackOwnerGoal<GhostTentacleEntity, Player>(this, Player.class, false, false));
        this.targetSelector.addGoal(4, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("attack_cooldown", ((Integer)this.entityData.get(ATTACK_COOLDOWN)).intValue());
        compound.putInt("attack_animtime", ((Integer)this.entityData.get(ATTACK_ANIMTIME)).intValue());
        if (this.getOwnerUUID() != null) {
            compound.putUUID("Owner", this.getOwnerUUID());
        }
        compound.putInt("ghost_age", this.tickCount);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("attack_cooldown")) {
            this.entityData.set(ATTACK_COOLDOWN, compound.getInt("Dataattack_cooldown"));
        }
        if (compound.contains("attack_animtime")) {
            this.entityData.set(ATTACK_ANIMTIME, compound.getInt("attack_animtime"));
        }
        if (compound.hasUUID("Owner")) {
            this.setOwnerUUID(compound.getUUID("Owner"));
        }
        this.tickCount = compound.getInt("ghost_age");
    }

    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime >= 30 && !this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent((Entity)this, (byte)60);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    public void baseTick() {
        super.baseTick();
        if (this.level() instanceof ServerLevel && this.tickCount > 200) {
            if (!this.isDeadOrDying()) {
                this.kill();
                this.playSound((SoundEvent)BossesRiseSounds.UNDYING_TENTACLE_DISAPPEARING.value());
                this.triggerAnim("main_controller", "death");
            }
            return;
        }
        LivingEntity target = this.getTarget();
        if ((Integer)this.getEntityData().get(ATTACK_ANIMTIME) > 0) {
            this.getEntityData().set(ATTACK_ANIMTIME, ((Integer)this.getEntityData().get(ATTACK_ANIMTIME) - 1));
            if ((Integer)this.getEntityData().get(ATTACK_ANIMTIME) != 4) {
                return;
            }
            if (target == null || !((double)target.distanceTo((Entity)this) < 6.0)) {
                return;
            }
            this.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
            if (this.isAlive()) {
                if (target.isBlocking()) {
                    if (target instanceof Player) {
                        Player player = (Player)target;
                        player.getCooldowns().addCooldown(player.getUseItem().getItem(), 100);
                    }
                    return;
                }
                this.playSound((SoundEvent)BossesRiseSounds.UNDYING_TENTACLE_SMASH_GROUND.value());
                target.hurt(this.damageSources().mobAttack(this), (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
            }
            target.setDeltaMovement(new Vec3(target.getDeltaMovement().x() + this.getLookAngle().x * 0.2, target.getDeltaMovement().y() + 0.1, target.getDeltaMovement().z() + this.getLookAngle().z * 0.2));
        } else if ((Integer)this.getEntityData().get(ATTACK_COOLDOWN) >= 40) {
            if (target != null && (double)target.distanceTo((Entity)this) < 4.5) {
                this.getEntityData().set(ATTACK_COOLDOWN, 0);
                this.getEntityData().set(ATTACK_ANIMTIME, 20);
            }
        } else if (target != null && (double)target.distanceTo((Entity)this) < 16.0) {
            this.getEntityData().set(ATTACK_COOLDOWN, ((Integer)this.getEntityData().get(ATTACK_COOLDOWN) + 1));
        }
    }

    public static void init(RegisterSpawnPlacementsEvent event) {
        event.register((EntityType)BossesRiseEntities.GHOST_TENTACLE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> world.getDifficulty() != Difficulty.PEACEFUL, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.0);
        builder = builder.add(Attributes.MAX_HEALTH, 20.0);
        builder = builder.add(Attributes.ARMOR, 6.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 5.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
        builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.1);
        return builder;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        AnimationController controller = new AnimationController((GeoAnimatable)this, "main_controller", 5, state -> (Integer)this.getEntityData().get(ATTACK_ANIMTIME) > 0 ? state.setAndContinue(ATTACK_ANIM) : (this.getId() % 2 == 0 ? state.setAndContinue(IDLE_ANIM_TWO) : state.setAndContinue(IDLE_ANIM)));
        controller.triggerableAnim("spawn", SPAWN_ANIM);
        controller.triggerableAnim("death", DEATH_ANIM);
        controllers.add(controller);
    }

    public boolean isPushable() {
        return false;
    }

    public void setDeltaMovement(Vec3 vec) {
    }

    protected void pushEntities() {
    }

    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    @Nullable
    public UUID getOwnerUUID() {
        return ((Optional<UUID>)this.entityData.get(OWNER_UUID)).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID uuid) {
        this.entityData.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    protected boolean shouldDropLoot() {
        return false;
    }
}

