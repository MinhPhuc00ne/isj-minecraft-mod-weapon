/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.Difficulty
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.SpawnGroupData
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
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.levelgen.Heightmap$Types
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

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.unusual.block_factorys_bosses.block.entity.KrakenSpawnerBlockEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
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
public class PirateCaptainEntity
extends Monster
implements GeoEntity,
KrakenSpawnerBlockEntity.SpawnerTied {
    public static final EntityDataAccessor<Integer> ATTACK_COOLDOWN = SynchedEntityData.defineId(PirateCaptainEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ATTACK_ANIMTIME = SynchedEntityData.defineId(PirateCaptainEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final RawAnimation ATTACK_ANIM = RawAnimation.begin().thenLoop("animation.bf_bs.melee_pirate_melee_attack");
    public static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("animation.bf_bs.melee_pirate_idle");
    public static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("animation.bf_bs.melee_pirate_walk");
    public static final RawAnimation DEATH_ANIM = RawAnimation.begin().thenPlay("animation.bf_bs.melee_pirate_death");
    private static final int DEATH_DURATION = 22;
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);

    public PirateCaptainEntity(EntityType<PirateCaptainEntity> type, Level world) {
        super(type, world);
        this.setPersistenceRequired();
        this.xpReward = 100;
    }

    public void die(DamageSource damageSource) {
        super.die(damageSource);
        if (this.dead) {
            this.triggerAnim(null, "death");
            if (this.isPassenger()) {
                this.removeVehicle();
            }
        }
    }

    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime >= 22 && !this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent((Entity)this, (byte)60);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACK_COOLDOWN, 30);
        builder.define(ATTACK_ANIMTIME, 0);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, (Goal)new MeleeAttackGoal((PathfinderMob)this, 0.9, false){

            protected boolean canPerformAttack(LivingEntity entity) {
                return false;
            }
        });
        this.targetSelector.addGoal(2, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]));
        this.goalSelector.addGoal(3, (Goal)new RandomStrollGoal((PathfinderMob)this, 0.8));
        this.goalSelector.addGoal(4, (Goal)new RandomLookAroundGoal((Mob)this));
        this.targetSelector.addGoal(5, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, false, false));
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

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("attack_cooldown", ((Integer)this.entityData.get(ATTACK_COOLDOWN)).intValue());
        compound.putInt("attack_animtime", ((Integer)this.entityData.get(ATTACK_ANIMTIME)).intValue());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("attack_cooldown")) {
            this.entityData.set(ATTACK_COOLDOWN, compound.getInt("Dataattack_cooldown"));
        }
        if (compound.contains("attack_animtime")) {
            this.entityData.set(ATTACK_ANIMTIME, compound.getInt("attack_animtime"));
        }
    }

    public void baseTick() {
        super.baseTick();
        LivingEntity target = this.getTarget();
        if ((Integer)this.getEntityData().get(ATTACK_ANIMTIME) > 0) {
            this.getEntityData().set(ATTACK_ANIMTIME, ((Integer)this.getEntityData().get(ATTACK_ANIMTIME) - 1));
            if ((Integer)this.getEntityData().get(ATTACK_ANIMTIME) != 4) {
                return;
            }
            if (target == null || !((double)target.distanceTo((Entity)this) < 2.0)) {
                return;
            }
            if (this.isAlive()) {
                if (target.isBlocking()) {
                    if (target instanceof Player) {
                        Player player = (Player)target;
                        player.getCooldowns().addCooldown(player.getUseItem().getItem(), 100);
                    }
                    return;
                }
                target.hurt(this.damageSources().mobAttack(this), (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
            }
            target.setDeltaMovement(new Vec3(target.getDeltaMovement().x() + this.getLookAngle().x * 0.2, target.getDeltaMovement().y() + 0.1, target.getDeltaMovement().z() + this.getLookAngle().z * 0.2));
        } else if ((Integer)this.getEntityData().get(ATTACK_COOLDOWN) >= 40) {
            if (target != null && (double)target.distanceTo((Entity)this) < 1.5) {
                this.getEntityData().set(ATTACK_COOLDOWN, 0);
                this.getEntityData().set(ATTACK_ANIMTIME, 20);
            }
        } else if (target != null && (double)target.distanceTo((Entity)this) < 16.0) {
            this.getEntityData().set(ATTACK_COOLDOWN, ((Integer)this.getEntityData().get(ATTACK_COOLDOWN) + 1));
        }
    }

    public static void init(RegisterSpawnPlacementsEvent event) {
        event.register((EntityType)BossesRiseEntities.PIRATE_CAPTAIN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> world.getDifficulty() != Difficulty.PEACEFUL, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
        builder = builder.add(Attributes.MAX_HEALTH, 40.0);
        builder = builder.add(Attributes.ARMOR, 6.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 10.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
        builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.2);
        return builder;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)BossesRiseItems.PIRATE_SABER.get()));
        return data;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        AnimationController controller = new AnimationController((GeoAnimatable)this, "main_controller", 5, state -> (Integer)this.getEntityData().get(ATTACK_ANIMTIME) > 0 ? state.setAndContinue(ATTACK_ANIM) : (state.isMoving() ? state.setAndContinue(WALK_ANIM) : state.setAndContinue(IDLE_ANIM)));
        controller.triggerableAnim("death", DEATH_ANIM);
        controllers.add(controller);
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }
}

