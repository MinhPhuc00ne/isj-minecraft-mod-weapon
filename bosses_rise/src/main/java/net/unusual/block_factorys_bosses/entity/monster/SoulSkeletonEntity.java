/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.Difficulty
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.AnimationState
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.SpawnPlacementTypes
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.MeleeAttackGoal
 *  net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
 *  net.minecraft.world.entity.ai.goal.RandomStrollGoal
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent
 *  net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent$Operation
 */
package net.unusual.block_factorys_bosses.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseTags;
import net.unusual.block_factorys_bosses.procedures.SoulSkeletonOnEntityTickUpdateProcedure;
import net.unusual.block_factorys_bosses.procedures.SoulSkeletonsEntityDiesProcedure;

public class SoulSkeletonEntity
extends Monster {
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public static final EntityDataAccessor<Integer> DATA_attack_cooldown = SynchedEntityData.defineId(SoulSkeletonEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_attack_animtime = SynchedEntityData.defineId(SoulSkeletonEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);

    public SoulSkeletonEntity(EntityType<SoulSkeletonEntity> type, Level world) {
        super(type, world);
        this.xpReward = 5;
        this.setNoAi(false);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_attack_cooldown, 30);
        builder.define(DATA_attack_animtime, 0);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, (Goal)new MeleeAttackGoal((PathfinderMob)this, 0.9, false){

            protected boolean canPerformAttack(LivingEntity entity) {
                return this.isTimeToAttack() && this.mob.distanceToSqr((Entity)entity) < (double)(this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth()) && this.mob.getSensing().hasLineOfSight((Entity)entity);
            }
        });
        this.targetSelector.addGoal(2, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]));
        this.goalSelector.addGoal(3, (Goal)new RandomStrollGoal((PathfinderMob)this, 0.8));
        this.goalSelector.addGoal(4, (Goal)new RandomLookAroundGoal((Mob)this));
        this.targetSelector.addGoal(5, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, false, false));
    }

    public void tick() {
        if (this.level().isClientSide) {
            this.idleAnimationState.animateWhen(true, this.tickCount);
            this.attackAnimationState.animateWhen((Integer)this.getEntityData().get(DATA_attack_animtime) > 0, this.tickCount);
        }
        super.tick();
    }

    public SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.SKELETON_HURT;
    }

    public SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    public boolean hurt(DamageSource damagesource, float amount) {
        if (damagesource.is(DamageTypes.IN_FIRE)) {
            return false;
        }
        return super.hurt(damagesource, amount);
    }

    public boolean fireImmune() {
        return true;
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Dataattack_cooldown", ((Integer)this.entityData.get(DATA_attack_cooldown)).intValue());
        compound.putInt("Dataattack_animtime", ((Integer)this.entityData.get(DATA_attack_animtime)).intValue());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Dataattack_cooldown")) {
            this.entityData.set(DATA_attack_cooldown, compound.getInt("Dataattack_cooldown"));
        }
        if (compound.contains("Dataattack_animtime")) {
            this.entityData.set(DATA_attack_animtime, compound.getInt("Dataattack_animtime"));
        }
    }

    public void baseTick() {
        super.baseTick();
        SoulSkeletonOnEntityTickUpdateProcedure.execute((LevelAccessor)this.level(), (Entity)this);
    }

    public void die(DamageSource source) {
        super.die(source);
        SoulSkeletonsEntityDiesProcedure.execute((LevelAccessor)this.level(), this.getX(), this.getY(), this.getZ());
    }

    public static void init(RegisterSpawnPlacementsEvent event) {
        event.register((EntityType)BossesRiseEntities.SOUL_SKELETON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> world.getDifficulty() != Difficulty.PEACEFUL && Mob.checkMobSpawnRules((EntityType)entityType, (LevelAccessor)world, (MobSpawnType)reason, (BlockPos)pos, (RandomSource)random) && (reason == MobSpawnType.SPAWNER || world.getBlockState(pos.below()).is(BossesRiseTags.Blocks.UNDERWORLD_ARENA_VALID_SPAWN)), RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
        builder = builder.add(Attributes.MAX_HEALTH, 20.0);
        builder = builder.add(Attributes.ARMOR, 4.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 6.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
        builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.1);
        return builder;
    }
}

