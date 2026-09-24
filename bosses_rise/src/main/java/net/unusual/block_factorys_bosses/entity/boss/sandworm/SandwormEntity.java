/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nonnull
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.Util
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerBossEvent
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.tags.DamageTypeTags
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.BossEvent$BossBarColor
 *  net.minecraft.world.BossEvent$BossBarOverlay
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntitySelector
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.SpawnGroupData
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.control.LookControl
 *  net.minecraft.world.entity.ai.control.MoveControl
 *  net.minecraft.world.entity.ai.control.MoveControl$Operation
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.PathNavigationRegion
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.pathfinder.FlyNodeEvaluator
 *  net.minecraft.world.level.pathfinder.Node
 *  net.minecraft.world.level.pathfinder.NodeEvaluator
 *  net.minecraft.world.level.pathfinder.Path
 *  net.minecraft.world.level.pathfinder.PathFinder
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.neoforged.neoforge.common.NeoForgeMod
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Vector3f
 *  software.bernie.geckolib.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.animation.AnimationController
 *  software.bernie.geckolib.animation.RawAnimation
 *  software.bernie.geckolib.loading.math.MathParser
 *  software.bernie.geckolib.model.GeoModel
 */
package net.unusual.block_factorys_bosses.entity.boss.sandworm;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.FlyNodeEvaluator;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerVariables;
import net.unusual.block_factorys_bosses.client.camera.client.CinematicAnimationController;
import net.unusual.block_factorys_bosses.configuration.ServerConfiguration;
import net.unusual.block_factorys_bosses.entity.SandColumnEntity;
import net.unusual.block_factorys_bosses.entity.boss.AbstractStateBossEntity;
import net.unusual.block_factorys_bosses.entity.boss.part.AbstractEntityPartParent;
import net.unusual.block_factorys_bosses.entity.boss.part.AbstractGeoEntityPart;
import net.unusual.block_factorys_bosses.entity.boss.sandworm.SandwormEntityPart;
import net.unusual.block_factorys_bosses.entity.projectile.PoisonSpitPrEntity;
import net.unusual.block_factorys_bosses.geckolib.DangerZonesProvider;
import net.unusual.block_factorys_bosses.geckolib.ServerAnimationPlayer;
import net.unusual.block_factorys_bosses.geckolib.boss.worm.SandwormModel;
import net.unusual.block_factorys_bosses.init.BossesRiseDataAttachments;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.random.WeightedPool;
import net.unusual.block_factorys_bosses.state.LambdaDataState;
import net.unusual.block_factorys_bosses.state.LambdaState;
import net.unusual.block_factorys_bosses.state.State;
import net.unusual.block_factorys_bosses.state.StateController;
import net.unusual.block_factorys_bosses.state.StateData;
import net.unusual.block_factorys_bosses.state.StateGoal;
import net.unusual.block_factorys_bosses.state.StateRef;
import net.unusual.block_factorys_bosses.state.StateRegistry;
import net.unusual.block_factorys_bosses.state.StateSequence;
import net.unusual.block_factorys_bosses.util.SpatialUtil;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.model.GeoModel;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SandwormEntity
extends AbstractStateBossEntity
implements AbstractEntityPartParent<SandwormEntity, SandwormEntityPart>,
DangerZonesProvider {
    private static final String TAG_RECENT_DAMAGE = "block_factorys_bosses:recent_damage";
    private static final String TAG_RECENT_DAMAGE_TIMER = "block_factorys_bosses:recent_damage_timer";
    private static final String TAG_HOME_POSITION = "block_factorys_bosses:home_position";
    private final ServerAnimationPlayer<SandwormEntity> serverAnimationPlayer = new ServerAnimationPlayer<SandwormEntity>((GeoModel<SandwormEntity>)new SandwormModel(), this);
    private static final String[] PART_ANCHOR_BONE_NAMES = new String[]{"body1_fx", "body2_fx", "body3_fx", "body4_fx", "body5_fx", "body6_fx", "body7_fx", "body8_fx", "body9_fx", "body10_fx", "body11_fx", "body12_fx", "body13_fx", "body14_fx", "body15_fx", "body16_fx"};
    private static final String[] PART_BODY_BONE_NAMES = new String[]{"body1", "body2", "body3", "body4", "body5", "body6", "body7", "body8", "body9", "body10", "body11", "body12", "body13", "body14", "body15", "body16"};
    private final SandwormEntityPart[] parts = new SandwormEntityPart[PART_ANCHOR_BONE_NAMES.length];
    public static final int FIGHT_PHASE = 0;
    public static final int DYING_PHASE = 1;
    public static final EntityDataAccessor<Boolean> DATA_IS_HIDDEN = SynchedEntityData.defineId(SandwormEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> DATA_DANGER_ZONE_VISIBLE = SynchedEntityData.defineId(SandwormEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> DATA_DAMAGED_SEGMENTS = SynchedEntityData.defineId(SandwormEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_CAMERA_SHAKE_TICKS = SynchedEntityData.defineId(SandwormEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    private BlockPos homePosition;
    private boolean hpGate75 = true;
    private boolean hpGate50 = true;
    private boolean hpGate25 = true;
    private float recentDamage = 0.0f;
    private int recentDamageResetTimer = 0;
    private static final String MAIN_CONTROLLER = "main_controller";
    private static final String ANIM_PREFIX = "animation.block_factory.bosses_rise.sandworm.";
    public static final RawAnimation EMERGENCE_ANIM = RawAnimation.begin().thenPlay("animation.block_factory.bosses_rise.sandworm.emergence");
    public static final RawAnimation EMERGED_IDLE_ANIM = RawAnimation.begin().thenLoop("animation.block_factory.bosses_rise.sandworm.emerged_idle");
    public static final RawAnimation ROAR_DIVE_ANIM = RawAnimation.begin().thenPlayAndHold("animation.block_factory.bosses_rise.sandworm.roar_dive");
    public static final RawAnimation EARTHQUAKE_START_ANIM = RawAnimation.begin().thenPlayAndHold("animation.block_factory.bosses_rise.sandworm.earthquake_start");
    public static final RawAnimation EARTHQUAKE_SLAM_ANIM = RawAnimation.begin().thenPlayAndHold("animation.block_factory.bosses_rise.sandworm.earthquake");
    public static final RawAnimation EARTHQUAKE_END_ANIM = RawAnimation.begin().thenPlayAndHold("animation.block_factory.bosses_rise.sandworm.earthquake_end");
    public static final RawAnimation DIVE_START_ANIM = RawAnimation.begin().thenPlayAndHold("animation.block_factory.bosses_rise.sandworm.dive_start");
    public static final RawAnimation DIVE_END_ANIM = RawAnimation.begin().thenPlayAndHold("animation.block_factory.bosses_rise.sandworm.dive_end");
    public static final RawAnimation SCREECH_ANIM = RawAnimation.begin().thenPlayAndHold("animation.block_factory.bosses_rise.sandworm.screech");
    public static final RawAnimation BITE_ANIM = RawAnimation.begin().thenPlayAndHold("animation.block_factory.bosses_rise.sandworm.bite");
    public static final RawAnimation POISON_SPIT_ANIM = RawAnimation.begin().thenPlayAndHold("animation.block_factory.bosses_rise.sandworm.poison_spit");
    public static final RawAnimation DEATH_ANIM = RawAnimation.begin().thenPlayAndHold("animation.block_factory.bosses_rise.sandworm.death");
    private static final StateRef<SandwormEntity> IDLE_STATE = State.named("idle");
    private static final StateRef<SandwormEntity> PATROL_STATE = State.named("patrol");
    private static final StateRef<SandwormEntity> PATROL_WANDER_STATE = State.named("patrol_wander");
    private static final StateRef<SandwormEntity> INTRO_STATE = State.named("intro");
    private static final StateRef<SandwormEntity> DIVE_ATTACK_STATE = State.named("dive_attack");
    private static final StateRef<SandwormEntity> DIVE_START_STATE = State.named("dive_start");
    private static final StateRef<SandwormEntity> DIVE_HIDDEN_STATE = State.named("dive_hidden");
    private static final StateRef<SandwormEntity> DIVE_EMERGE_WARNING_STATE = State.named("dive_emerge_warning");
    private static final StateRef<SandwormEntity> DIVE_EMERGE_STATE = State.named("dive_emerge");
    private static final StateRef<SandwormEntity> BITE_ATTACK_STATE = State.named("bite_attack");
    private static final StateRef<SandwormEntity> BITE_STATE = State.named("bite");
    private static final StateRef<SandwormEntity> CHASE_ATTACK_STATE = State.named("chase_attack");
    private static final StateRef<SandwormEntity> CHASE_CIRCLE_STATE = State.named("chase_circle");
    private static final StateRef<SandwormEntity> CHASE_CHARGE_STATE = State.named("chase_charge");
    private static final StateRef<SandwormEntity> EARTHQUAKE_ATTACK_STATE = State.named("earthquake_attack");
    private static final StateRef<SandwormEntity> EARTHQUAKE_START_STATE = State.named("earthquake_start");
    private static final StateRef<SandwormEntity> EARTHQUAKE_APPROACH_STATE = State.named("earthquake_approach");
    private static final StateRef<SandwormEntity> EARTHQUAKE_SLAM_EMERGE_STATE = State.named("earthquake_slam_emerge");
    private static final StateRef<SandwormEntity> EARTHQUAKE_SLAM_STATE = State.named("earthquake_slam");
    private static final StateRef<SandwormEntity> EARTHQUAKE_SLAM_END_STATE = State.named("earthquake_slam_end");
    private static final StateRef<SandwormEntity> POISON_SPIT_ATTACK_STATE = State.named("poison_spit_attack");
    private static final StateRef<SandwormEntity> POISON_SPIT_STATE = State.named("poison_spit");
    private static final StateRef<SandwormEntity> SCREECH_ATTACK_STATE = State.named("screech_attack");
    private static final StateRef<SandwormEntity> SCREECH_STATE = State.named("screech");
    private static final StateRef<SandwormEntity> DYING_STATE = State.named("dying");
    private static final StateRef<SandwormEntity> DEAD_STATE = State.named("dead");
    private static final StateRef<SandwormEntity> PHASE_1_PATTERN_1_STATE = State.named("phase_1_pattern_1");
    private static final StateRef<SandwormEntity> PHASE_1_PATTERN_2_STATE = State.named("phase_1_pattern_2");
    private static final StateRef<SandwormEntity> PHASE_1_PATTERN_3_STATE = State.named("phase_1_pattern_3");
    private static final StateRef<SandwormEntity> PHASE_1_PATTERN_4_STATE = State.named("phase_1_pattern_4");
    private static final StateRef<SandwormEntity> PHASE_2_PATTERN_1_STATE = State.named("phase_2_pattern_1");
    private static final StateRef<SandwormEntity> PHASE_2_PATTERN_2_STATE = State.named("phase_2_pattern_2");
    private static final StateRef<SandwormEntity> PHASE_3_PATTERN_1_STATE = State.named("phase_3_pattern_1");
    private static final StateRef<SandwormEntity> PHASE_3_PATTERN_2_STATE = State.named("phase_3_pattern_2");
    private static final StateRef<SandwormEntity> PHASE_3_PATTERN_3_STATE = State.named("phase_3_pattern_3");
    private static final StateRef<SandwormEntity> PHASE_3_PATTERN_4_STATE = State.named("phase_3_pattern_4");
    private static final StateRef<SandwormEntity> PHASE_4_PATTERN_1_STATE = State.named("phase_4_pattern_1");
    private static final StateRef<SandwormEntity> PHASE_4_PATTERN_2_STATE = State.named("phase_4_pattern_2");
    private static final StateRef<SandwormEntity> PHASE_4_PATTERN_3_STATE = State.named("phase_4_pattern_3");
    private static final StateRef<SandwormEntity> GATE_1_PATTERN_STATE = State.named("gate_1_pattern");
    private static final StateRef<SandwormEntity> GATE_2_PATTERN_STATE = State.named("gate_2_pattern");
    private static final StateRef<SandwormEntity> GATE_3_PATTERN_STATE = State.named("gate_3_pattern");
    private static final StateRegistry<SandwormEntity> STATE_REGISTRY = new StateRegistry();
    private final StateController<SandwormEntity> stateController = new StateController<SandwormEntity>(this, STATE_REGISTRY, state -> (Integer)this.getEntityData().get(DATA_BOSS_PHASE) == -1 ? PATROL_STATE : IDLE_STATE);
    private static final WeightedPool<StateRef<SandwormEntity>> PHASE_1_ATTACK_STATES = WeightedPool.of(WeightedPool.option(1, PHASE_1_PATTERN_1_STATE), WeightedPool.option(1, PHASE_1_PATTERN_2_STATE), WeightedPool.option(1, PHASE_1_PATTERN_3_STATE), WeightedPool.option(1, PHASE_1_PATTERN_4_STATE));
    private static final WeightedPool<StateRef<SandwormEntity>> PHASE_2_ATTACK_STATES = WeightedPool.of(WeightedPool.option(18, PHASE_1_PATTERN_1_STATE), WeightedPool.option(18, PHASE_1_PATTERN_2_STATE), WeightedPool.option(10, PHASE_1_PATTERN_3_STATE), WeightedPool.option(7, PHASE_1_PATTERN_4_STATE), WeightedPool.option(27, PHASE_2_PATTERN_1_STATE), WeightedPool.option(20, PHASE_2_PATTERN_2_STATE));
    private static final WeightedPool<StateRef<SandwormEntity>> PHASE_3_ATTACK_STATES = WeightedPool.of(WeightedPool.option(8, PHASE_1_PATTERN_1_STATE), WeightedPool.option(8, PHASE_1_PATTERN_2_STATE), WeightedPool.option(4, PHASE_1_PATTERN_3_STATE), WeightedPool.option(8, PHASE_1_PATTERN_4_STATE), WeightedPool.option(18, PHASE_3_PATTERN_1_STATE), WeightedPool.option(22, PHASE_3_PATTERN_2_STATE), WeightedPool.option(14, PHASE_3_PATTERN_3_STATE), WeightedPool.option(16, PHASE_3_PATTERN_4_STATE));
    private static final WeightedPool<StateRef<SandwormEntity>> PHASE_4_ATTACK_STATES = WeightedPool.of(WeightedPool.option(6, PHASE_1_PATTERN_1_STATE), WeightedPool.option(6, PHASE_1_PATTERN_2_STATE), WeightedPool.option(3, PHASE_1_PATTERN_3_STATE), WeightedPool.option(5, PHASE_1_PATTERN_4_STATE), WeightedPool.option(35, PHASE_4_PATTERN_1_STATE), WeightedPool.option(22, PHASE_4_PATTERN_2_STATE), WeightedPool.option(23, PHASE_4_PATTERN_3_STATE));

    public SandwormEntity(EntityType<SandwormEntity> type, Level world) {
        super(type, world);
        for (int index = 0; index < this.parts.length; ++index) {
            SandwormEntityPart part;
            this.parts[index] = part = new SandwormEntityPart(this, index, PART_ANCHOR_BONE_NAMES[index], PART_BODY_BONE_NAMES[index], index == 0 ? 4.5f : 4.0f, index == 0 ? 4.5f : 4.0f);
        }
        this.setId(ENTITY_COUNTER.getAndAdd(this.parts.length + 1) + 1);
        this.lookControl = new SandwormLookControl(this);
        this.moveControl = new SandwormMoveControl();
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public StateController<SandwormEntity> getStateController() {
        return this.stateController;
    }

    @Override
    @NotNull
    protected ServerBossEvent createBossEvent() {
        return (ServerBossEvent)Util.make(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.NOTCHED_6), bossEvent -> bossEvent.setVisible(false));
    }

    @Override
    protected int getBossType() {
        return 5;
    }

    public void setId(int id) {
        super.setId(id);
        for (int i = 0; i < this.parts.length; ++i) {
            this.parts[i].setId(id + i + 1);
        }
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        AnimationController controller = new CinematicAnimationController<SandwormEntity>(this, MAIN_CONTROLLER, state -> state.setAndContinue(EMERGED_IDLE_ANIM)).triggerableAnim(INTRO_STATE.getName(), EMERGENCE_ANIM).triggerableAnim(DIVE_START_STATE.getName(), DIVE_START_ANIM).triggerableAnim(DIVE_EMERGE_STATE.getName(), DIVE_END_ANIM).triggerableAnim(BITE_STATE.getName(), BITE_ANIM).triggerableAnim(POISON_SPIT_STATE.getName(), POISON_SPIT_ANIM).triggerableAnim(SCREECH_STATE.getName(), SCREECH_ANIM).triggerableAnim(EARTHQUAKE_START_STATE.getName(), ROAR_DIVE_ANIM).triggerableAnim(EARTHQUAKE_SLAM_EMERGE_STATE.getName(), EARTHQUAKE_START_ANIM).triggerableAnim(EARTHQUAKE_SLAM_STATE.getName(), EARTHQUAKE_SLAM_ANIM).triggerableAnim(EARTHQUAKE_SLAM_END_STATE.getName(), EARTHQUAKE_END_ANIM).triggerableAnim(DYING_STATE.getName(), DEATH_ANIM);
        controllers.add(controller);
    }

    public void setRendererVariables() {
        for (int index = 1; index <= 16; ++index) {
            int value = 20 * index;
            MathParser.setVariable((String)("v.delay" + index), () -> value);
        }
        boolean baseShift = false;
        MathParser.setVariable((String)"v.shift", () -> 0.0);
        for (int index = 2; index <= 8; ++index) {
            int value = 0 + (index - 1);
            MathParser.setVariable((String)("v.shift" + index), () -> value);
        }
        MathParser.setVariable((String)"v.idle", () -> 100.0);
        MathParser.setVariable((String)"v.swim", () -> 360.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_IS_HIDDEN, false);
        builder.define(DATA_DANGER_ZONE_VISIBLE, false);
        builder.define(DATA_DAMAGED_SEGMENTS, 0);
        builder.define(DATA_CAMERA_SHAKE_TICKS, 0);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (DATA_IS_HIDDEN.equals(key)) {
            boolean isHidden = this.isHiddenUnderground();
            this.blocksBuilding = !isHidden;
            for (SandwormEntityPart part : this.parts) {
                part.blocksBuilding = !isHidden;
            }
        }
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, (Goal)new StateGoal<SandwormEntity>(this){

            @Override
            public boolean isInterruptable() {
                return SandwormEntity.this.getStateController().isStateActive(IDLE_STATE);
            }
        });
        this.targetSelector.addGoal(2, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]));
        this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, false, false));
    }

    public SoundEvent getAmbientSound() {
        return (SoundEvent)BossesRiseSounds.SANDWORM_AMBIENT.value();
    }

    public SoundEvent getHurtSound(DamageSource ds) {
        return (SoundEvent)BossesRiseSounds.SANDWORM_HURT.value();
    }

    public boolean isDiveMoving() {
        StateRef<SandwormEntity> state = this.stateController.getActiveStateRef();
        return state == DIVE_HIDDEN_STATE || state == CHASE_CHARGE_STATE || state == CHASE_CIRCLE_STATE || state == EARTHQUAKE_APPROACH_STATE || state == PATROL_WANDER_STATE;
    }

    public boolean isDying() {
        return (Integer)this.getEntityData().get(DATA_BOSS_PHASE) == 1;
    }

    public boolean isAllowedTurning() {
        StateRef<SandwormEntity> state = this.stateController.getActiveStateRef();
        if (state == DIVE_START_STATE) {
            return false;
        }
        if (state == EARTHQUAKE_START_STATE || state == EARTHQUAKE_SLAM_EMERGE_STATE || state == EARTHQUAKE_SLAM_STATE || state == EARTHQUAKE_SLAM_END_STATE) {
            return false;
        }
        return state != DYING_STATE && state != DEAD_STATE;
    }

    @Override
    public void tick() {
        super.tick();
        this.setNoGravity(true);
        if (this.hasEffect(MobEffects.POISON)) {
            this.removeEffect(MobEffects.POISON);
        }
        if (this.level().isClientSide() && this.isHiddenUnderground()) {
            Vec3 pos = this.findGroundPosition().add(0.0, 0.1, 0.0);
            for (int i = 0; i < 3; ++i) {
                this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.DUST_CLOUD.get(), pos.x(), pos.y(), pos.z(), (Math.random() - 0.5) * 2.0, Math.random() * 0.25, (Math.random() - 0.5) * 2.0);
                this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.GROUND_DUST.get(), pos.x(), pos.y(), pos.z(), (Math.random() - 0.5) * 2.0, Math.random() * 0.025, (Math.random() - 0.5) * 2.0);
            }
        }
        int bossPhase = (Integer)this.entityData.get(DATA_BOSS_PHASE);
        if (this.level().isClientSide()) {
            return;
        }
        if (bossPhase == 0) {
            this.entityData.set(AbstractStateBossEntity.DATA_BATTLE_TIME, ((Integer)this.entityData.get(AbstractStateBossEntity.DATA_BATTLE_TIME) + 1));
            this.setPlayingMusic(true);
        } else {
            this.setPlayingMusic(false);
        }
        if (net.unusual.block_factorys_bosses.util.EntityPersistentData.get(this).getDouble("soundCooldown") >= 80.0) {
            this.playSound(this.getAmbientSound());
            net.unusual.block_factorys_bosses.util.EntityPersistentData.get(this).putDouble("soundCooldown", 0.0);
        } else {
            net.unusual.block_factorys_bosses.util.EntityPersistentData.get(this).putDouble("soundCooldown", net.unusual.block_factorys_bosses.util.EntityPersistentData.get(this).getDouble("soundCooldown") + 1.0);
        }
    }

    public void baseTick() {
        super.baseTick();
        this.setRendererVariables();
        this.serverAnimationPlayer.tickAnimations();
        AbstractGeoEntityPart.positionPartsFromBones(this.parts, this.serverAnimationPlayer, Vec3.ZERO);
        if (!this.isHiddenUnderground()) {
            this.pushCollidingEntities();
        }
        if (!this.level().isClientSide()) {
            this.serverBaseTick();
        }
    }

    private void serverBaseTick() {
        int cameraShakeTicks;
        if (this.recentDamageResetTimer > 0) {
            --this.recentDamageResetTimer;
            if (this.recentDamageResetTimer == 0) {
                this.recentDamage = 0.0f;
            }
        }
        if ((cameraShakeTicks = ((Integer)this.getEntityData().get(DATA_CAMERA_SHAKE_TICKS)).intValue()) > 0) {
            this.getEntityData().set(DATA_CAMERA_SHAKE_TICKS, (cameraShakeTicks - 1));
        }
    }

    private void startFight() {
        this.setYRot((float)net.unusual.block_factorys_bosses.util.EntityPersistentData.get(this).getDouble("angle"));
        this.setXRot(0.0f);
        this.setYBodyRot(this.getYRot());
        this.setYHeadRot(this.getYRot());
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
        this.yBodyRotO = this.getYRot();
        this.yHeadRotO = this.getYRot();
        this.forEachNearbyPlayer(32.0, player -> {
            PlayerVariables playerVars = net.neoforged.neoforge.attachment.AttachmentHolder.getData(player, BossesRiseDataAttachments.PLAYER_VARIABLES);
            playerVars.boss_no_hit = true;
            playerVars.syncPlayerVariables((Entity)player);
        });
        this.setBarVisible(true);
        this.stateController.replaceAll(INTRO_STATE);
        this.stateController.push(EARTHQUAKE_APPROACH_STATE);
        this.stateController.push(DIVE_START_STATE);
    }

    public StateRef<SandwormEntity> chooseAttackState() {
        double ratio = this.getHealthRatio();
        if (ratio <= 0.75 && this.hpGate75) {
            this.hpGate75 = false;
            return GATE_1_PATTERN_STATE;
        }
        if (ratio <= 0.5 && this.hpGate50) {
            this.hpGate50 = false;
            return GATE_2_PATTERN_STATE;
        }
        if (ratio <= 0.25 && this.hpGate25) {
            this.hpGate25 = false;
            return GATE_3_PATTERN_STATE;
        }
        LivingEntity target = this.getTarget();
        if (target != null && (double)this.distanceTo((Entity)target) < 8.0 + Math.random() * 8.0) {
            if (this.getRandom().nextFloat() < 0.4f) {
                return SCREECH_ATTACK_STATE;
            }
            return DIVE_ATTACK_STATE;
        }
        WeightedPool<StateRef<SandwormEntity>> attackPool = switch ((int)(ratio * 4.0)) {
            case 0 -> PHASE_4_ATTACK_STATES;
            case 1 -> PHASE_3_ATTACK_STATES;
            case 2 -> PHASE_2_ATTACK_STATES;
            default -> PHASE_1_ATTACK_STATES;
        };
        return attackPool.selectRandom();
    }

    public void shootPoisonSpit(SandwormEntityPart sourcePart, @Nullable Entity target, boolean towardsTarget) {
        Level level = this.level();
        if (!(level instanceof ServerLevel)) {
            return;
        }
        ServerLevel level2 = (ServerLevel)level;
        PoisonSpitPrEntity spit = new PoisonSpitPrEntity((EntityType<? extends PoisonSpitPrEntity>)((EntityType)BossesRiseEntities.POISON_SPIT_PR.get()), (Level)level2);
        spit.setOwner((Entity)this);
        spit.setBaseDamage(5.0);
        spit.setSilent(true);
        float distance = target == null ? 3.0f : this.distanceTo(target) * 0.045f;
        Vector3f bonePosition = this.getBoneWorldPosition(sourcePart.getAnchorBoneName());
        spit.setPos(bonePosition.x(), bonePosition.y(), bonePosition.z());
        Vec3 lookDirection = towardsTarget && target != null ? target.position().subtract(new Vec3(bonePosition)).normalize() : this.getLookAngle();
        spit.shoot(lookDirection.x * 0.3, 0.2, lookDirection.z * 0.3, distance, 20.0f);
        level2.addFreshEntity((Entity)spit);
    }

    public boolean hurt(@Nonnull DamageSource damageSource, float amount) {
        if (this.level().isClientSide()) {
            return false;
        }
        return this.hurt(this.parts[1], damageSource, amount);
    }

    @Override
    public boolean hurt(SandwormEntityPart part, DamageSource damageSource, float damage) {
        float multiplier = 1.0f;
        if (this.isSegmentDamaged(part.getPartIndex())) {
            multiplier += 0.3f;
        }
        if ((damage *= multiplier) < 0.01f) {
            return false;
        }
        float previousHealth = this.getHealth();
        boolean gotDamaged = this.reallyHurt(damageSource, damage);
        if (gotDamaged) {
            this.recentDamage += Math.max(previousHealth - this.getHealth(), 0.0f);
            if (this.recentDamage >= 25.0f) {
                this.makeSegmentDamaged(part);
                this.recentDamage = 0.0f;
            } else {
                this.recentDamageResetTimer = 60;
            }
            if (this.isSegmentDamaged(part.getPartIndex()) && damageSource.isDirect() && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                this.shootPoisonSpit(part, damageSource.getDirectEntity(), true);
            }
        }
        return gotDamaged;
    }

    public boolean reallyHurt(DamageSource damageSource, float amount) {
        return super.hurt(damageSource, amount);
    }

    public boolean isInvulnerable() {
        if (this.stateController.isStateActive(DYING_STATE)) {
            return true;
        }
        return super.isInvulnerable();
    }

    public boolean isInvulnerableTo(DamageSource damageSource) {
        if (damageSource.is(DamageTypes.FALL)) {
            return true;
        }
        if (damageSource.is(DamageTypes.CACTUS)) {
            return true;
        }
        if (damageSource.is(NeoForgeMod.POISON_DAMAGE)) {
            return true;
        }
        if (damageSource.is(DamageTypes.LAVA)) {
            return true;
        }
        return super.isInvulnerableTo(damageSource);
    }

    public void lavaHurt() {
    }

    public int getSegmentIndexFromBoneName(String bodyBoneName) {
        for (int index = 0; index < this.parts.length; ++index) {
            if (!this.parts[index].getBodyBoneName().equals(bodyBoneName)) continue;
            return index;
        }
        return -1;
    }

    public boolean isSegmentDamaged(int index) {
        return ((Integer)this.getEntityData().get(DATA_DAMAGED_SEGMENTS) & 1 << index) != 0;
    }

    public void setSegmentDamaged(int index, boolean damaged) {
        this.getEntityData().set(DATA_DAMAGED_SEGMENTS, ((Integer)this.getEntityData().get(DATA_DAMAGED_SEGMENTS) & ~(1 << index) | (damaged ? 1 : 0) << index));
    }

    public void healAllSegments() {
        for (SandwormEntityPart part : this.parts) {
            this.setSegmentDamaged(part.getPartIndex(), false);
        }
    }

    public void makeSegmentDamaged(SandwormEntityPart part) {
        if (this.isSegmentDamaged(part.getPartIndex())) {
            return;
        }
        this.setSegmentDamaged(part.getPartIndex(), true);
        Level level = this.level();
        if (level instanceof ServerLevel) {
            ServerLevel level2 = (ServerLevel)level;
            Vector3f bonePosition = this.getBoneWorldPosition(part.getAnchorBoneName());
            level2.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.POISON_SPIT.get()), (double)bonePosition.x(), (double)bonePosition.y(), (double)bonePosition.z(), 15, 0.4, 0.4, 0.4, 0.1);
        }
    }

    private void applyUndergroundEffects() {
        this.healAllSegments();
        this.clearFire();
        this.removeFrost();
        this.setAirSupply(this.getMaxAirSupply());
    }

    public Vector3f getBoneWorldPosition(String boneName) {
        return this.serverAnimationPlayer.getBoneWorldPosition(boneName);
    }

    private void pushCollidingEntities() {
        List<PartCollision> allCollisions = this.findPartCollisions(0.0);
        for (PartCollision collision : allCollisions) {
            LivingEntity entity = collision.entity();
            if (!(!this.level().isClientSide() ^ entity instanceof Player)) continue;
            Vec3 force = entity.position().subtract(collision.part().position());
            SpatialUtil.pushEntity((Entity)entity, force, 0.3f);
        }
    }

    private void attackCollidingEntities(Vec3 lineStart, Vec3 lineEnd, double maxDistance, float maxDamageScale) {
        this.attackCollidingEntities(lineStart, lineEnd, maxDistance, maxDamageScale, (entity, collision) -> {});
    }

    private void attackCollidingEntities(Vec3 lineStart, Vec3 lineEnd, double maxDistance, float maxDamageScale, BiConsumer<LivingEntity, LineCollision> callback) {
        this.attackCollidingEntities(List.of(Pair.of(lineStart, lineEnd)), maxDistance, maxDamageScale, callback);
    }

    private void attackCollidingEntities(List<Pair<Vec3, Vec3>> lines, double maxDistance, float maxDamageScale, BiConsumer<LivingEntity, LineCollision> callback) {
        float halfPartSize = 2.0f;
        HashMap<LivingEntity, LineCollision> lineCollisions = new HashMap<LivingEntity, LineCollision>();
        for (Pair<Vec3, Vec3> pair : lines) {
            Vec3 lineStart = (Vec3)pair.getFirst();
            Vec3 lineEnd = (Vec3)pair.getSecond();
            List<LivingEntity> collisions = this.level().getEntitiesOfClass(LivingEntity.class, new AABB(lineStart, lineEnd).inflate(maxDistance + 2.0), e -> e != this && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(e));
            Vec3 lineVector = lineEnd.subtract(lineStart);
            double lineLength = lineVector.length();
            Vec3 lineDirection = lineVector.normalize();
            for (LivingEntity entity : collisions) {
                LineCollision existingCollision;
                double lineDistance;
                Vec3 forceOrigin;
                Vec3 entityCenter = entity.getBoundingBox().getCenter();
                Vec3 forceOriginLocalEntityPos = entityCenter.subtract(forceOrigin = lineStart.add(lineDirection.scale(lineDistance = Math.clamp((double)lineDirection.dot(entityCenter.subtract(lineStart)), (double)0.0, (double)lineLength))));
                double forceOriginDistance = forceOriginLocalEntityPos.length();
                double distanceModifier = 1.0 - Math.max((forceOriginDistance - 2.0) / maxDistance, 0.0);
                if (distanceModifier <= 0.0 || (existingCollision = (LineCollision)lineCollisions.get(entity)) != null && (double)existingCollision.distanceModifier > distanceModifier) continue;
                lineCollisions.put(entity, new LineCollision(forceOrigin, forceOriginLocalEntityPos.normalize(), (float)distanceModifier));
            }
        }
        for (Map.Entry<LivingEntity, LineCollision> entry : lineCollisions.entrySet()) {
            LivingEntity entity = entry.getKey();
            LineCollision collision = entry.getValue();
            SpatialUtil.pushEntity((Entity)entity, collision.forceDirection.scale(maxDistance * (double)collision.distanceModifier), 2.0);
            this.attackEntity((Entity)entity, maxDamageScale * collision.distanceModifier());
            callback.accept(entity, collision);
        }
    }

    private List<PartCollision> findPartCollisions(double inflateCollider) {
        ArrayList<PartCollision> allCollisions = new ArrayList<PartCollision>(4);
        for (SandwormEntityPart part : this.parts) {
            List<LivingEntity> partCollisions = this.level().getEntitiesOfClass(LivingEntity.class, part.getBoundingBox().inflate(inflateCollider), e -> e != this && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(e));
            for (LivingEntity collidingEntity : partCollisions) {
                AABB overlapBBox = collidingEntity.getBoundingBox().intersect(part.getBoundingBox());
                float overlapVolume = (float)(overlapBBox.getXsize() * overlapBBox.getYsize() * overlapBBox.getZsize());
                PartCollision previousCollision = allCollisions.stream().filter(it -> it.entity == collidingEntity).findFirst().orElse(null);
                if (previousCollision != null) {
                    if (previousCollision.overlapVolume >= overlapVolume) continue;
                    allCollisions.remove(previousCollision);
                }
                allCollisions.add(new PartCollision(collidingEntity, part, overlapVolume));
            }
        }
        return allCollisions;
    }

    public void triggerAnim(@Nullable String controllerName, String animName) {
        ServerAnimationPlayer.retriggerAnim(this.serverAnimationPlayer, controllerName, animName);
    }

    public void triggerAnim(String animName) {
        this.triggerAnim(MAIN_CONTROLLER, animName);
    }

    public void stopTriggeredAnim(@Nullable String controllerName, @Nullable String animName) {
        ServerAnimationPlayer.stopTriggeredAnim(this.serverAnimationPlayer, controllerName, animName);
    }

    public void stopTriggeredAnim(@Nullable String animName) {
        this.stopTriggeredAnim(MAIN_CONTROLLER, animName);
    }

    @Override
    public Collection<DangerZonesProvider.DangerZone> getDangerZones() {
        if (!this.isDangerZoneVisible()) {
            return List.of();
        }
        Vec3 offset = this.findGroundPosition().subtract(this.position());
        return List.of(new DangerZonesProvider.DangerZone().setSize(5.0f, 5.0f).setOffset(offset.toVector3f()));
    }

    public void setDangerZoneVisible(boolean visible) {
        this.getEntityData().set(DATA_DANGER_ZONE_VISIBLE, visible);
    }

    public boolean isDangerZoneVisible() {
        return (Boolean)this.getEntityData().get(DATA_DANGER_ZONE_VISIBLE);
    }

    public void setHiddenUnderground(boolean hidden) {
        this.getEntityData().set(DATA_IS_HIDDEN, hidden);
    }

    public boolean isHiddenUnderground() {
        return (Boolean)this.getEntityData().get(DATA_IS_HIDDEN);
    }

    private void setCameraShakeTicks(int ticks) {
        this.getEntityData().set(DATA_CAMERA_SHAKE_TICKS, ticks);
    }

    public float getCameraShakeStrength() {
        if ((Integer)this.getEntityData().get(DATA_CAMERA_SHAKE_TICKS) > 0) {
            return 12.0f;
        }
        return 0.0f;
    }

    @Override
    protected ResourceLocation getNoHitAdvancement() {
        return BossesRise.prefix("no_hit_sandworm");
    }

    @Override
    protected ResourceLocation getKillAdvancement() {
        return BossesRise.prefix("kill_sandworm");
    }

    public boolean isPushable() {
        return false;
    }

    public void knockback(double strength, double x, double z) {
    }

    public boolean isAttackable() {
        return !this.isHiddenUnderground() && super.isAttackable();
    }

    public boolean isPickable() {
        return super.isPickable() && !this.isHiddenUnderground();
    }

    public boolean isInWall() {
        return false;
    }

    protected boolean isAffectedByFluids() {
        return false;
    }

    public boolean displayFireAnimation() {
        return false;
    }

    public boolean realDisplayFireAnimation() {
        return super.displayFireAnimation();
    }

    @Nonnull
    public AABB getBoundingBoxForCulling() {
        return AABB.ofSize((Vec3)this.position(), (double)70.0, (double)70.0, (double)70.0);
    }

    protected float getMaxHeadRotationRelativeToBody() {
        return 0.0f;
    }

    public int getMaxHeadYRot() {
        return 0;
    }

    public int getMaxHeadXRot() {
        return this.getHeadRotSpeed();
    }

    public int getHeadRotSpeed() {
        if (!this.isAllowedTurning()) {
            return 0;
        }
        StateRef<SandwormEntity> state = this.stateController.getActiveStateRef();
        if (state == CHASE_CIRCLE_STATE) {
            return 9;
        }
        if (state == DIVE_HIDDEN_STATE) {
            return 10;
        }
        return 4;
    }

    protected float tickHeadTurn(float yRot, float animStep) {
        this.yBodyRot = Mth.rotLerp((float)animStep, (float)this.yHeadRotO, (float)this.yHeadRot);
        return animStep;
    }

    @Nonnull
    protected PathNavigation createNavigation(@Nonnull Level level) {
        return new SandwormPathNavigation(level);
    }

    public boolean canStandOnFluid(@NotNull FluidState fluidState) {
        return true;
    }

    public Vec3 findGroundPosition() {
        Vec3 position = this.position();
        if (this.level().getBlockState(this.blockPosition()).isAir()) {
            BlockHitResult hitResult = this.level().clip(new ClipContext(position.add(0.0, 2.0, 0.0), position.add(0.0, -60.0, 0.0), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, CollisionContext.empty()));
            return hitResult.getLocation();
        }
        for (int y = (int)position.y; y < this.level().getMaxBuildHeight(); y += 3) {
            BlockHitResult hitResult = this.level().clip(new ClipContext(new Vec3(position.x, (double)(y + 3), position.z), new Vec3(position.x, (double)y, position.z), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, CollisionContext.empty()));
            if (hitResult.getType() == HitResult.Type.MISS || hitResult.isInside()) continue;
            return hitResult.getLocation();
        }
        return position;
    }

    private void snapToGround() {
        Level level = this.level();
        if (!(level instanceof ServerLevel)) {
            return;
        }
        ServerLevel level2 = (ServerLevel)level;
        Vec3 groundPosition = this.findGroundPosition();
        this.teleportTo(level2, groundPosition.x(), groundPosition.y(), groundPosition.z(), Set.of(), this.getYRot(), this.getXRot());
    }

    @Override
    public void lookAtTarget() {
        LivingEntity target = this.getTarget();
        if (target == null) {
            return;
        }
        this.getLookControl().setLookAt((Entity)target);
    }

    private void circleAroundPosition(Vec3 centerPosition, float radius, float speed) {
        this.circleAroundPosition(centerPosition, radius, speed, 20.0f);
    }

    private void circleAroundPosition(Vec3 centerPosition, float radius, float speed, float degreeOffset) {
        Vec3 directionToCenter = centerPosition.subtract(this.position()).normalize();
        Vec3 directionToRightOfCenter = directionToCenter.cross(new Vec3(0.0, 1.0, 0.0));
        boolean invertCirclingDirection = this.getLookAngle().dot(directionToRightOfCenter) < 0.0;
        float angleToCenter = (float)Mth.atan2((double)directionToCenter.z, (double)(-directionToCenter.x));
        Vec3 moveTarget = new Vec3((double)radius, 0.0, 0.0).yRot(angleToCenter + (invertCirclingDirection ? -degreeOffset : degreeOffset) * ((float)Math.PI / 180)).add(centerPosition);
        this.getMoveControl().setWantedPosition(moveTarget.x(), moveTarget.y(), moveTarget.z(), (double)speed);
    }

    public boolean isMultipartEntity() {
        return true;
    }

    @Nonnull
    public SandwormEntityPart[] getParts() {
        return this.parts;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.recentDamage > 0.0f) {
            compound.putFloat(TAG_RECENT_DAMAGE, this.recentDamage);
        }
        if (this.recentDamageResetTimer > 0) {
            compound.putInt(TAG_RECENT_DAMAGE_TIMER, this.recentDamageResetTimer);
        }
        if (this.homePosition != null) {
            compound.put(TAG_HOME_POSITION, NbtUtils.writeBlockPos((BlockPos)this.homePosition));
        }
        compound.putBoolean("HpGate75", this.hpGate75);
        compound.putBoolean("HpGate50", this.hpGate50);
        compound.putBoolean("HpGate25", this.hpGate25);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains(TAG_RECENT_DAMAGE_TIMER, 3) && compound.contains(TAG_RECENT_DAMAGE, 5)) {
            this.recentDamageResetTimer = compound.getInt(TAG_RECENT_DAMAGE_TIMER);
            this.recentDamage = compound.getFloat(TAG_RECENT_DAMAGE);
        }
        if (compound.contains(TAG_HOME_POSITION)) {
            NbtUtils.readBlockPos((CompoundTag)compound, (String)TAG_HOME_POSITION).ifPresent(pos -> {
                this.homePosition = pos;
            });
        }
        if (!compound.contains("BossPhase")) {
            return;
        }
        this.hpGate75 = compound.getBoolean("HpGate75");
        this.hpGate50 = compound.getBoolean("HpGate50");
        this.hpGate25 = compound.getBoolean("HpGate25");
    }

    public void onAddedToLevel() {
        if (this.homePosition == null && !this.level().isClientSide()) {
            this.homePosition = this.blockPosition();
        }
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata) {
        SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(((Double)ServerConfiguration.SANDWORM_HEALTH.get()).doubleValue());
        this.getAttribute(Attributes.ARMOR).setBaseValue(((Double)ServerConfiguration.SANDWORM_ARMOR.get()).doubleValue());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(((Double)ServerConfiguration.SANDWORM_ATK.get()).doubleValue());
        this.setHealth((float)((Double)ServerConfiguration.SANDWORM_HEALTH.get()).doubleValue());
        if (reason != MobSpawnType.STRUCTURE) {
            this.setBarVisible(true);
        }
        return retval;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.MAX_HEALTH, 150.0).add(Attributes.ARMOR, 20.0).add(Attributes.ATTACK_DAMAGE, 10.0).add(Attributes.FOLLOW_RANGE, 96.0).add(Attributes.STEP_HEIGHT, 0.6);
    }

    private void replaceStateAfterIdle(StateRef<SandwormEntity> newState) {
        if (this.isHiddenUnderground()) {
            this.stateController.replaceAll(newState);
            this.stateController.push(DIVE_EMERGE_STATE);
            return;
        }
        StateRef<SandwormEntity> state = this.stateController.getActiveStateRef();
        if (state == DIVE_EMERGE_STATE) {
            this.stateController.pushBefore(Objects.requireNonNull(this.stateController.getActiveState()), newState);
            return;
        }
        if (state == EARTHQUAKE_SLAM_STATE) {
            this.stateController.replaceAll(newState);
            this.stateController.push(DIVE_EMERGE_STATE);
            this.stateController.push(EARTHQUAKE_SLAM_END_STATE);
            return;
        }
        if (state == DIVE_START_STATE || state == EARTHQUAKE_START_STATE || state == EARTHQUAKE_SLAM_END_STATE) {
            this.stateController.pushBefore(Objects.requireNonNull(this.stateController.getActiveState()), newState);
            this.stateController.pushBefore(Objects.requireNonNull(this.stateController.getActiveState()), DIVE_EMERGE_STATE);
            return;
        }
        this.stateController.replaceAll(newState);
    }

    public void startDeathSequence() {
        if (this.isDying()) {
            return;
        }
        assert (!this.stateController.hasActiveStateRef(it -> it == DYING_STATE || it == DEAD_STATE));
        this.replaceStateAfterIdle(DYING_STATE);
        this.getEntityData().set(DATA_BOSS_PHASE, 1);
    }

    @Override
    public boolean shouldCancelDeath() {
        if (!this.isDying()) {
            this.startDeathSequence();
            return true;
        }
        return !this.stateController.isStateActive(DEAD_STATE);
    }

    static <D extends StateData> LambdaDataState.LambdaDataStateBuilder<SandwormEntity, D> stateBuilderWithData(Codec<D> dataCodec, Supplier<D> defaultDataSupplier) {
        return new LambdaDataState.LambdaDataStateBuilder<SandwormEntity, D>().withData(dataCodec, defaultDataSupplier);
    }

    private static LambdaState.LambdaStateBuilder<SandwormEntity> stateBuilder() {
        return new LambdaState.LambdaStateBuilder<>();
    }

    static {
        STATE_REGISTRY.register(IDLE_STATE, stateBuilder().endAfter(10).onEnd(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            entity.stopTriggeredAnim(null);
            if (entity.isDying()) {
                entity.stateController.replaceAll(DEAD_STATE);
                return;
            }
            if (entity.getTarget() == null || (Integer)entity.entityData.get(DATA_BOSS_PHASE) == -1) {
                return;
            }
            state.getController().push(entity.chooseAttackState());
        }));
        STATE_REGISTRY.register(PATROL_STATE, stateBuilder().onTick(state -> {
            int timer;
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            if (entity.getTarget() instanceof Player) {
                entity.startFight();
                return State.Result.END;
            }
            RandomSource random = entity.getRandom();
            if (random.nextInt(60) == 0) {
                entity.getLookControl().setLookAt(new Vec3(random.nextGaussian(), 0.0, random.nextGaussian()).normalize());
            }
            if ((timer = state.getTimer()) > 400 || timer > 120 && random.nextInt(20) == 0) {
                entity.stateController.replaceActive(DIVE_EMERGE_STATE);
                entity.stateController.push(PATROL_WANDER_STATE);
                entity.stateController.push(DIVE_START_STATE);
                return State.Result.END;
            }
            return State.Result.CONTINUE;
        }));
        STATE_REGISTRY.register(PATROL_WANDER_STATE, stateBuilder().onTick(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            RandomSource random = entity.getRandom();
            int timer = state.getTimer();
            entity.circleAroundPosition(entity.homePosition.getBottomCenter(), 40.0f + random.nextFloat() * (float)Math.min(timer / 5, 15), 1.5f, 50.0f);
            return timer > 200 || timer > 80 && random.nextInt(80) == 0 ? State.Result.END : State.Result.CONTINUE;
        }));
        STATE_REGISTRY.register(INTRO_STATE, stateBuilder().onStart(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            entity.snapToGround();
            entity.triggerAnim(INTRO_STATE.getName());
            entity.setHiddenUnderground(false);
            entity.applyUndergroundEffects();
            entity.getEntityData().set(AbstractStateBossEntity.DATA_BOSS_PHASE, 0);
        }).onTickFor(200, state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            entity.setDeltaMovement(Vec3.ZERO);
            entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(((Double)ServerConfiguration.SANDWORM_HEALTH.get()).doubleValue());
            entity.getAttribute(Attributes.ARMOR).setBaseValue(((Double)ServerConfiguration.SANDWORM_ARMOR.get()).doubleValue());
            entity.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(((Double)ServerConfiguration.SANDWORM_ATK.get()).doubleValue());
            int timer = state.getTimer();
            if (timer == 10) {
                entity.setCameraShakeTicks(6);
                ServerLevel level = (ServerLevel)entity.level();
                for (int i = 0; i < 26; ++i) {
                    level.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.DUST_CLOUD.get()), entity.getX(), entity.getY() + 0.1, entity.getZ(), 0, (Math.random() - 0.5) * 4.0, Math.random() * 0.3, (Math.random() - 0.5) * 4.0, 1.0);
                    level.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.GROUND_DUST.get()), entity.getX(), entity.getY() + 0.1, entity.getZ(), 0, (Math.random() - 0.5) * 5.0, Math.random() * 0.4, (Math.random() - 0.5) * 5.0, 1.0);
                }
            } else if (timer == 13) {
                entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_EMERGE.value());
            } else if (timer == 63) {
                entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_SCREECH.value());
            }
        }));
        STATE_REGISTRY.register(DIVE_START_STATE, stateBuilder().onStart(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            if (entity.isHiddenUnderground()) {
                return;
            }
            entity.triggerAnim(DIVE_START_STATE.getName());
            entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_DIVE.value());
        }).onTick(state -> {
            Level patt0$temp;
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            if (entity.isHiddenUnderground()) {
                return State.Result.END;
            }
            int timer = state.getTimer();
            if (timer == 9) {
                entity.attackCollidingEntities(new Vec3(entity.getBoneWorldPosition(PART_ANCHOR_BONE_NAMES[0])), new Vec3(entity.getBoneWorldPosition(PART_ANCHOR_BONE_NAMES[3])), 2.0, 0.8f);
                return State.Result.CONTINUE;
            }
            if (timer < 30) {
                return State.Result.CONTINUE;
            }
            entity.setHiddenUnderground(true);
            entity.applyUndergroundEffects();
            Vec3 eyePosition = entity.position().add(0.0, 9.0, 0.0);
            Vec3 diveDirection = entity.calculateViewVector(45.0f, entity.getYRot());
            BlockHitResult hitResult = entity.level().clip(new ClipContext(eyePosition.add(diveDirection.scale(5.0)), eyePosition.add(diveDirection.scale(20.0)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty()));
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                Vec3 teleportTarget = hitResult.getBlockPos().above().getBottomCenter();
                entity.teleportTo(teleportTarget.x, teleportTarget.y, teleportTarget.z);
            }
            if ((patt0$temp = entity.level()) instanceof ServerLevel) {
                ServerLevel level = (ServerLevel)patt0$temp;
                for (int i = 0; i < 26; ++i) {
                    level.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.DUST_CLOUD.get()), entity.getX(), entity.getY() + 0.1, entity.getZ(), 0, (Math.random() - 0.5) * 4.0, Math.random() * 0.3, (Math.random() - 0.5) * 4.0, 1.0);
                    level.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.GROUND_DUST.get()), entity.getX(), entity.getY() + 0.1, entity.getZ(), 0, (Math.random() - 0.5) * 5.0, Math.random() * 0.4, (Math.random() - 0.5) * 5.0, 1.0);
                }
            }
            return State.Result.END;
        }));
        STATE_REGISTRY.register(DIVE_HIDDEN_STATE, stateBuilder().onStart(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            entity.triggerAnim(DIVE_HIDDEN_STATE.getName());
        }).onTickFor(140, state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            LivingEntity target = entity.getTarget();
            if (target != null) {
                entity.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 2.0);
                if (entity.distanceToSqr((Entity)target) < 4.0) {
                    entity.teleportTo(target.getX(), target.getY(), target.getZ());
                    state.getController().endActive();
                }
            }
        }).onEnd(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            entity.snapToGround();
        }));
        STATE_REGISTRY.register(DIVE_EMERGE_WARNING_STATE, stateBuilder().onStart(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            if (!entity.isHiddenUnderground()) {
                return;
            }
            entity.setDangerZoneVisible(true);
        }).onTick(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            if (!entity.isHiddenUnderground()) {
                return State.Result.END;
            }
            if (state.getTimer() >= 5) {
                state.getController().replaceActive(DIVE_EMERGE_STATE);
                return State.Result.END;
            }
            return State.Result.CONTINUE;
        }).onEnd(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            entity.setDangerZoneVisible(false);
        }));
        STATE_REGISTRY.register(DIVE_EMERGE_STATE, stateBuilder().onStart(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            if (!entity.isHiddenUnderground()) {
                return;
            }
            entity.snapToGround();
            entity.triggerAnim(DIVE_EMERGE_STATE.getName());
            entity.applyUndergroundEffects();
            entity.setHiddenUnderground(false);
        }).onTick(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            if (entity.isHiddenUnderground()) {
                return State.Result.END;
            }
            int timer = state.getTimer();
            if (timer == 3) {
                entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_EMERGE.value());
                entity.setCameraShakeTicks(2);
                Vec3 position = entity.position();
                entity.attackCollidingEntities(position.add(0.0, -2.0, 0.0), position.add(0.0, 4.0, 0.0), 1.5, 1.5f, (collidingEntity, collision) -> SpatialUtil.pushEntity((Entity)collidingEntity, new Vec3(0.0, 1.6, 0.0).scale((double)collision.distanceModifier), 2.0));
                Level patt0$temp = entity.level();
                if (patt0$temp instanceof ServerLevel) {
                    ServerLevel level = (ServerLevel)patt0$temp;
                    for (int i = 0; i < 26; ++i) {
                        level.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.DUST_CLOUD.get()), entity.getX(), entity.getY() + 0.1, entity.getZ(), 0, (Math.random() - 0.5) * 4.0, Math.random() * 0.3, (Math.random() - 0.5) * 4.0, 1.0);
                        level.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.GROUND_DUST.get()), entity.getX(), entity.getY() + 0.1, entity.getZ(), 0, (Math.random() - 0.5) * 5.0, Math.random() * 0.4, (Math.random() - 0.5) * 5.0, 1.0);
                    }
                }
            }
            return timer >= 30 ? State.Result.END : State.Result.CONTINUE;
        }));
        STATE_REGISTRY.register(BITE_STATE, stateBuilder().onStart(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            entity.triggerAnim(BITE_STATE.getName());
            entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_BITE.value(), 8.0f, 1.0f);
        }).onTickFor(60, state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            int timer = state.getTimer();
            if (timer < 30) {
                entity.lookAtTarget();
            }
            if (timer == 33) {
                entity.attackCollidingEntities(List.of(new Pair((Object)new Vec3(entity.getBoneWorldPosition(PART_ANCHOR_BONE_NAMES[0])), (Object)new Vec3(entity.getBoneWorldPosition(PART_ANCHOR_BONE_NAMES[2]))), new Pair((Object)new Vec3(entity.getBoneWorldPosition(PART_ANCHOR_BONE_NAMES[2])), (Object)new Vec3(entity.getBoneWorldPosition(PART_ANCHOR_BONE_NAMES[5]))), new Pair((Object)new Vec3(entity.getBoneWorldPosition(PART_ANCHOR_BONE_NAMES[5])), (Object)new Vec3(entity.getBoneWorldPosition(PART_ANCHOR_BONE_NAMES[8])))), 2.0, 1.0f, (hitEntity, collision) -> {
                    hitEntity.addEffect(new MobEffectInstance(MobEffects.POISON, (int)(collision.distanceModifier * 200.0f), 1));
                    SpatialUtil.pushEntity((Entity)hitEntity, hitEntity.position().subtract(entity.position()).multiply(1.0, 0.0, 1.0).normalize().scale(1.5 * (double)collision.distanceModifier), 2.0);
                });
            }
        }));
        STATE_REGISTRY.register(CHASE_CIRCLE_STATE, stateBuilder().onTick(state -> {
            boolean isAttackLineSuitable;
            int timer = state.getTimer();
            if (timer >= 300) {
                return State.Result.END;
            }
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            LivingEntity target = entity.getTarget();
            if (target == null) {
                return State.Result.CONTINUE;
            }
            float minimumDistanceToAttack = 5.0f;
            float circlingDistance = minimumDistanceToAttack + 15.0f;
            Vec3 targetPosition = target.position();
            Vec3 deltaToTarget = targetPosition.subtract(entity.position());
            double distanceToTarget = deltaToTarget.length();
            double angleToTarget = Math.acos(deltaToTarget.normalize().dot(entity.getLookAngle())) * 57.2957763671875;
            if (distanceToTarget >= (double)minimumDistanceToAttack && angleToTarget < 20.0) {
                return State.Result.END;
            }
            if (angleToTarget < 110.0 && timer >= 30 && (isAttackLineSuitable = true)) {
                entity.getMoveControl().setWantedPosition(targetPosition.x(), targetPosition.y(), targetPosition.z(), 2.5);
                return State.Result.CONTINUE;
            }
            entity.circleAroundPosition(target.position(), circlingDistance, 3.0f, distanceToTarget < (double)minimumDistanceToAttack ? 1.0f : 20.0f);
            return State.Result.CONTINUE;
        }));
        STATE_REGISTRY.register(CHASE_CHARGE_STATE, stateBuilder().onStart(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_CHARGE.value());
        }).onTick(state -> {
            Vec3 wantedPosition;
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            LivingEntity target = entity.getTarget();
            int timer = state.getTimer();
            if (timer > 40) {
                return State.Result.END;
            }
            Vec3 lookDirection = entity.getLookAngle();
            Vec3 positionAhead = lookDirection.multiply(1.0, 0.0, 1.0).normalize().scale(20.0).add(entity.position());
            Vec3 targetPosition = target != null ? target.position() : positionAhead;
            double distanceToTarget = lookDirection.dot(targetPosition.subtract(entity.position()));
            double previousDistanceToTarget = distanceToTarget + new Vec3(entity.getX() - entity.xOld, entity.getY() - entity.yOld, entity.getZ() - entity.zOld).length();
            double forcedColumnAt = 0.5;
            Vec3 sandColumnOffset = null;
            if (timer % 6 == 0 && distanceToTarget < 15.0 && distanceToTarget > -10.0 && (target == null || Math.abs(distanceToTarget - forcedColumnAt) > 2.0)) {
                sandColumnOffset = lookDirection.cross(new Vec3(0.0, 1.0, 0.0)).normalize().scale((double)(entity.getRandom().nextFloat() - 0.5f));
            } else if (previousDistanceToTarget >= forcedColumnAt && distanceToTarget < forcedColumnAt) {
                if (target == null) {
                    sandColumnOffset = new Vec3(0.0, 0.0, 0.0);
                } else {
                    Vec3 offsetToTarget = target.position().subtract(entity.position()).multiply(1.0, 0.0, 1.0);
                    sandColumnOffset = offsetToTarget.normalize().scale(Math.min(1.0, offsetToTarget.length()));
                }
            }
            if (sandColumnOffset != null) {
                SandColumnEntity.spawnSandColumn((ServerLevel)entity.level(), entity.position().add(sandColumnOffset), 0, (LivingEntity)entity);
            }
            if (distanceToTarget > 0.0) {
                wantedPosition = targetPosition;
            } else if (distanceToTarget > -20.0) {
                wantedPosition = positionAhead;
            } else {
                return State.Result.END;
            }
            entity.getMoveControl().setWantedPosition(wantedPosition.x(), wantedPosition.y(), wantedPosition.z(), 4.0);
            return State.Result.CONTINUE;
        }));
        STATE_REGISTRY.register(EARTHQUAKE_START_STATE, stateBuilder().onStart(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            entity.triggerAnim(EARTHQUAKE_START_STATE.getName());
        }).onTickFor(210, state -> {
            String anchor = null;
            int timer = state.getTimer();
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            ServerLevel level = (ServerLevel)entity.level();
            switch (timer) {
                case 124: {
                    anchor = "body1_fx";
                    break;
                }
                case 131: {
                    anchor = "body3_fx";
                    break;
                }
                case 136: {
                    anchor = "body5_fx";
                    break;
                }
                case 148: {
                    anchor = "body7_fx";
                    break;
                }
                case 161: {
                    anchor = "body9_fx";
                    break;
                }
                case 169: {
                    anchor = "body11_fx";
                    break;
                }
                case 184: {
                    anchor = "body13_fx";
                    break;
                }
                case 192: {
                    anchor = "body15_fx";
                    break;
                }
                default: {
                    anchor = null;
                }
            }
            if (anchor != null) {
                Vector3f position = entity.getBoneWorldPosition(anchor);
                level.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.DUST_CLOUD.get()), (double)position.x(), (double)position.y(), (double)position.z(), 10, 0.1, 0.1, 0.1, 1.0);
                level.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.SAND_ROCK.get()), (double)position.x(), (double)position.y(), (double)position.z(), 10, 0.1, 0.2, 0.1, 1.0);
            }
            if (timer == 24) {
                entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_SCREECH.value());
            } else if (timer == 121) {
                entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_DIVE_LONG.value());
            }
        }).onEnd(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            entity.applyUndergroundEffects();
        }));
        STATE_REGISTRY.register(EARTHQUAKE_APPROACH_STATE, stateBuilder().onTick(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            int timer = state.getTimer();
            if (timer >= 200) {
                return State.Result.END;
            }
            LivingEntity target = entity.getTarget();
            if (target != null) {
                float circleRadius = 10.0f;
                if (timer > 50 && entity.closerThan((Entity)target, circleRadius + 3.0f)) {
                    return State.Result.END;
                }
                entity.circleAroundPosition(target.position(), circleRadius, 2.0f);
            }
            return State.Result.CONTINUE;
        }).onEnd(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            entity.snapToGround();
        }));
        STATE_REGISTRY.register(EARTHQUAKE_SLAM_EMERGE_STATE, stateBuilder().onStart(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            entity.triggerAnim(EARTHQUAKE_SLAM_EMERGE_STATE.getName());
            entity.setHiddenUnderground(false);
            entity.applyUndergroundEffects();
        }).onTickFor(70, state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            int timer = state.getTimer();
            if (timer == 18) {
                entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_EMERGE.value());
            } else if (timer == 22) {
                ServerLevel level = (ServerLevel)entity.level();
                Vec3 position = entity.position();
                level.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.DUST_CLOUD.get()), position.x(), position.y() - 1.0, position.z(), 40, 0.3, 0.2, 0.3, 0.3);
                level.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.GROUND_DUST.get()), position.x(), position.y() - 1.0, position.z(), 20, 0.3, 0.2, 0.3, 0.5);
                level.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.SAND_ROCK.get()), position.x(), position.y() - 1.0, position.z(), 10, 0.1, 0.2, 0.1, 0.6);
                entity.attackCollidingEntities(position.add(0.0, 0.0, 0.0), position.add(0.0, 2.0, 0.0), 1.5, 0.25f);
            }
        }));
        STATE_REGISTRY.register(EARTHQUAKE_SLAM_STATE, stateBuilder().onStart(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            entity.triggerAnim(EARTHQUAKE_SLAM_STATE.getName());
        }).onTickFor(176, state -> {
            String anchor = null;
            int ERUPTION_DELAY = 20;
            int timer = state.getTimer();
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            ServerLevel level = (ServerLevel)entity.level();
            switch (timer) {
                case 26: 
                case 82: 
                case 127: 
                case 151: {
                    anchor = "body13_fx";
                    break;
                }
                case 27: 
                case 83: 
                case 128: 
                case 152: {
                    anchor = "body14_fx";
                    break;
                }
                case 28: 
                case 84: 
                case 129: 
                case 153: {
                    anchor = "body15_fx";
                    break;
                }
                case 29: 
                case 85: 
                case 130: 
                case 154: {
                    anchor = "body16_fx";
                    break;
                }
                default: {
                    anchor = null;
                }
            }
            if (anchor != null) {
                Vector3f position = entity.getBoneWorldPosition(anchor);
                level.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.DUST_CLOUD.get()), (double)position.x(), (double)position.y(), (double)position.z(), 10, 0.1, 0.1, 0.1, 1.0);
                level.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.SAND_ROCK.get()), (double)position.x(), (double)position.y(), (double)position.z(), 10, 0.1, 0.2, 0.1, 1.0);
            }
            if (timer != 28 && timer != 85 && timer != 127 && timer != 151) {
                return;
            }
            RandomSource random = entity.getRandom();
            LivingEntity target = entity.getTarget();
            Vec3 centerPos = target != null ? target.position() : entity.position().offsetRandom(random, 30.0f);
            int squareSize = target == null ? 10 : 4;
            int squareHalfSize = target == null ? 4 : 1;
            for (int gridX = -2; gridX <= 2; ++gridX) {
                for (int gridZ = -2; gridZ <= 2; ++gridZ) {
                    SandColumnEntity sandColumn;
                    int gridDistanceSquared = gridX * gridX + gridZ * gridZ;
                    if (gridDistanceSquared > 5) continue;
                    double columnX = centerPos.x;
                    double columnY = centerPos.y;
                    double columnZ = centerPos.z;
                    if (gridDistanceSquared != 0) {
                        columnX += (double)(gridX * squareSize + random.nextIntBetweenInclusive(-squareHalfSize, squareHalfSize));
                        columnZ += (double)(gridZ * squareSize + random.nextIntBetweenInclusive(-squareHalfSize, squareHalfSize));
                    }
                    if ((sandColumn = SandColumnEntity.spawnSandColumn(level, new Vec3(columnX, columnY, columnZ), 20 + gridDistanceSquared, (LivingEntity)entity)) == null || gridDistanceSquared == 0) continue;
                    sandColumn.setSilent(true);
                }
            }
            entity.attackCollidingEntities(new Vec3(entity.getBoneWorldPosition("body12_fx")), new Vec3(entity.getBoneWorldPosition("body16_fx")), 8.0, 1.0f);
            entity.setCameraShakeTicks(4);
            entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_TAIL_IMPACT.value());
        }));
        STATE_REGISTRY.register(EARTHQUAKE_SLAM_END_STATE, stateBuilder().onStart(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            entity.triggerAnim(EARTHQUAKE_SLAM_END_STATE.getName());
            entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_DIVE_SHORT.value());
        }).endAfter(50).onEnd(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            entity.applyUndergroundEffects();
            entity.setHiddenUnderground(true);
        }));
        STATE_REGISTRY.register(POISON_SPIT_STATE, stateBuilder().onStart(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            entity.triggerAnim(POISON_SPIT_STATE.getName());
            entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_POISON_SPIT.value());
        }).onTickFor(40, state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            int timer = state.getTimer();
            if (timer < 20) {
                entity.lookAtTarget();
            }
            if (timer >= 17 && timer <= 20 && entity.getRandom().nextDouble() < 0.75) {
                entity.shootPoisonSpit(entity.getParts()[0], (Entity)entity.getTarget(), false);
            }
        }));
        STATE_REGISTRY.register(SCREECH_STATE, stateBuilder().onStart(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            entity.triggerAnim(SCREECH_STATE.getName());
            entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_SCREECH.value());
        }).onTickFor(100, state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            entity.lookAtTarget();
            int timer = state.getTimer();
            if (timer >= 17 && timer <= 79) {
                entity.forEachNearbyEntity(64.0, Vec3.ZERO, EntitySelector.NO_SPECTATORS, otherEntity -> {
                    Vec3 delta = otherEntity.position().subtract(entity.position());
                    double distance = delta.length();
                    if (distance > 64.0) {
                        return;
                    }
                    Vec3 pushForce = delta.normalize().scale(Mth.clampedMap((double)distance, (double)0.0, (double)20.0, (double)0.3f, (double)0.15f));
                    SpatialUtil.pushEntity((Entity)otherEntity, new Vec3(pushForce.x, 0.0, pushForce.z), 2.0);
                });
            }
        }));
        STATE_REGISTRY.register(DYING_STATE, stateBuilder().onStart(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            entity.triggerAnim(DYING_STATE.getName());
            entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_DEATH.value());
        }).onTickFor(98, state -> {
            String[] anchors = null;
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            int timer = state.getTimer();
            switch (timer) {
                case 91: {
                    anchors = new String[]{"body8_fx", "body7_fx", "body6_fx", "body5_fx"};
                    break;
                }
                case 92: {
                    anchors = new String[]{"body4_fx", "body3_fx", "body2_fx", "body1_fx"};
                    break;
                }
                case 95: {
                    anchors = new String[]{"body6_fx", "body5_fx", "body4_fx", "body3_fx"};
                    break;
                }
                case 96: {
                    anchors = new String[]{"body8_fx", "body7_fx", "body2_fx", "body1_fx"};
                    break;
                }
                default: {
                    anchors = null;
                }
            }
            if (anchors != null) {
                ServerLevel level = (ServerLevel)entity.level();
                for (String anchor : anchors) {
                    Vector3f position = entity.getBoneWorldPosition(anchor);
                    level.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.DUST_CLOUD.get()), (double)position.x(), (double)(position.y() - 2.0f), (double)position.z(), 10, 0.1, 0.1, 0.1, 1.0);
                    level.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.SAND_ROCK.get()), (double)position.x(), (double)(position.y() - 2.0f), (double)position.z(), 10, 0.1, 0.2, 0.1, 1.0);
                }
            }
            if (timer == 91) {
                entity.playSound((SoundEvent)BossesRiseSounds.SANDWORM_BODY_IMPACT.value());
            }
        }).onEnd(state -> state.getController().replaceAll(DEAD_STATE)));
        STATE_REGISTRY.register(DEAD_STATE, stateBuilder().endAfter(300).onEnd(state -> {
            SandwormEntity entity = (SandwormEntity)state.getEntity();
            entity.simulatePlayerKill();
        }));
        STATE_REGISTRY.register(DIVE_ATTACK_STATE, StateSequence.of(DIVE_START_STATE, DIVE_HIDDEN_STATE, DIVE_EMERGE_WARNING_STATE));
        STATE_REGISTRY.register(BITE_ATTACK_STATE, StateSequence.of(DIVE_EMERGE_WARNING_STATE, BITE_STATE));
        STATE_REGISTRY.register(CHASE_ATTACK_STATE, StateSequence.of(DIVE_START_STATE, CHASE_CIRCLE_STATE, CHASE_CHARGE_STATE));
        STATE_REGISTRY.register(EARTHQUAKE_ATTACK_STATE, StateSequence.of(DIVE_START_STATE, EARTHQUAKE_APPROACH_STATE, EARTHQUAKE_SLAM_EMERGE_STATE, EARTHQUAKE_SLAM_STATE, EARTHQUAKE_SLAM_END_STATE, DIVE_HIDDEN_STATE, DIVE_EMERGE_WARNING_STATE));
        STATE_REGISTRY.register(POISON_SPIT_ATTACK_STATE, StateSequence.of(DIVE_EMERGE_WARNING_STATE, POISON_SPIT_STATE));
        STATE_REGISTRY.register(SCREECH_ATTACK_STATE, StateSequence.of(DIVE_EMERGE_WARNING_STATE, SCREECH_STATE));
        STATE_REGISTRY.register(PHASE_1_PATTERN_1_STATE, StateSequence.of(DIVE_ATTACK_STATE, BITE_ATTACK_STATE));
        STATE_REGISTRY.register(PHASE_1_PATTERN_2_STATE, StateSequence.of(DIVE_ATTACK_STATE, POISON_SPIT_ATTACK_STATE));
        STATE_REGISTRY.register(PHASE_1_PATTERN_3_STATE, StateSequence.of(BITE_ATTACK_STATE, BITE_ATTACK_STATE));
        STATE_REGISTRY.register(PHASE_1_PATTERN_4_STATE, StateSequence.of(POISON_SPIT_ATTACK_STATE, DIVE_ATTACK_STATE));
        STATE_REGISTRY.register(PHASE_2_PATTERN_1_STATE, StateSequence.of(CHASE_ATTACK_STATE, DIVE_ATTACK_STATE, DIVE_ATTACK_STATE));
        STATE_REGISTRY.register(PHASE_2_PATTERN_2_STATE, StateSequence.of(CHASE_ATTACK_STATE, DIVE_ATTACK_STATE, POISON_SPIT_ATTACK_STATE));
        STATE_REGISTRY.register(PHASE_3_PATTERN_1_STATE, StateSequence.of(CHASE_ATTACK_STATE, CHASE_ATTACK_STATE, DIVE_ATTACK_STATE));
        STATE_REGISTRY.register(PHASE_3_PATTERN_2_STATE, StateSequence.of(CHASE_ATTACK_STATE, CHASE_ATTACK_STATE, DIVE_ATTACK_STATE, DIVE_ATTACK_STATE));
        STATE_REGISTRY.register(PHASE_3_PATTERN_3_STATE, StateSequence.of(SCREECH_ATTACK_STATE, CHASE_ATTACK_STATE, CHASE_ATTACK_STATE, DIVE_ATTACK_STATE, DIVE_ATTACK_STATE, POISON_SPIT_ATTACK_STATE));
        STATE_REGISTRY.register(PHASE_3_PATTERN_4_STATE, StateSequence.of(SCREECH_ATTACK_STATE, DIVE_ATTACK_STATE, CHASE_ATTACK_STATE, DIVE_ATTACK_STATE, DIVE_ATTACK_STATE, CHASE_ATTACK_STATE, DIVE_ATTACK_STATE, BITE_ATTACK_STATE));
        STATE_REGISTRY.register(PHASE_4_PATTERN_1_STATE, StateSequence.of(CHASE_ATTACK_STATE, CHASE_ATTACK_STATE, CHASE_ATTACK_STATE, DIVE_ATTACK_STATE, DIVE_ATTACK_STATE));
        STATE_REGISTRY.register(PHASE_4_PATTERN_2_STATE, StateSequence.of(CHASE_ATTACK_STATE, DIVE_ATTACK_STATE, EARTHQUAKE_ATTACK_STATE, CHASE_ATTACK_STATE, DIVE_ATTACK_STATE, EARTHQUAKE_ATTACK_STATE));
        STATE_REGISTRY.register(PHASE_4_PATTERN_3_STATE, StateSequence.of(SCREECH_ATTACK_STATE, EARTHQUAKE_ATTACK_STATE, DIVE_ATTACK_STATE, EARTHQUAKE_ATTACK_STATE, DIVE_ATTACK_STATE));
        STATE_REGISTRY.register(GATE_1_PATTERN_STATE, StateSequence.of(SCREECH_ATTACK_STATE, CHASE_ATTACK_STATE, CHASE_ATTACK_STATE, DIVE_ATTACK_STATE));
        STATE_REGISTRY.register(GATE_2_PATTERN_STATE, StateSequence.of(SCREECH_ATTACK_STATE, EARTHQUAKE_ATTACK_STATE, EARTHQUAKE_ATTACK_STATE, CHASE_ATTACK_STATE, CHASE_ATTACK_STATE, DIVE_ATTACK_STATE));
        STATE_REGISTRY.register(GATE_3_PATTERN_STATE, StateSequence.of(SCREECH_ATTACK_STATE, EARTHQUAKE_ATTACK_STATE, EARTHQUAKE_ATTACK_STATE, CHASE_ATTACK_STATE, CHASE_ATTACK_STATE, CHASE_ATTACK_STATE, EARTHQUAKE_ATTACK_STATE, DIVE_ATTACK_STATE));
    }

    public class SandwormLookControl
    extends LookControl {
        public SandwormLookControl(SandwormEntity this$0) {
            super((Mob)this$0);
        }

        public void tick() {
            this.getXRotD().ifPresent(targetXRot -> this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), targetXRot.floatValue(), this.mob.getHeadRotSpeed())));
            this.getYRotD().ifPresent(targetYRot -> {
                int headRotSpeed;
                float smoothBelow;
                float differenceToTarget = Mth.degreesDifferenceAbs((float)targetYRot.floatValue(), (float)this.mob.yHeadRot);
                float yRotSpeedLimit = differenceToTarget < (smoothBelow = Math.min((float)(headRotSpeed = this.mob.getHeadRotSpeed()), 10.0f)) ? differenceToTarget / 4.0f : Mth.clamp((float)differenceToTarget, (float)1.0f, (float)headRotSpeed);
                this.mob.setYHeadRot(this.rotateTowards(this.mob.yHeadRot, targetYRot.floatValue(), yRotSpeedLimit));
                this.mob.setYRot(this.mob.yHeadRot);
            });
            this.mob.yBodyRot = this.mob.getYHeadRot();
        }
    }

    public class SandwormMoveControl
    extends MoveControl {
        public SandwormMoveControl() {
            super((Mob)SandwormEntity.this);
        }

        public void tick() {
            if (!SandwormEntity.this.isDiveMoving()) {
                this.operation = MoveControl.Operation.WAIT;
                this.mob.setDeltaMovement(0.0, 0.0, 0.0);
                Vec3 fallDistance = Entity.collideBoundingBox((Entity)this.mob, (Vec3)new Vec3(0.0, -0.025, 0.0), (AABB)this.mob.getBoundingBox(), (Level)this.mob.level(), List.of());
                if (fallDistance.y() < 0.0) {
                    this.mob.addDeltaMovement(fallDistance);
                }
                return;
            }
            this.mob.setDeltaMovement(this.mob.getDeltaMovement().scale((double)0.4f));
            if (this.operation != MoveControl.Operation.MOVE_TO) {
                return;
            }
            this.operation = MoveControl.Operation.WAIT;
            this.mob.getLookControl().setLookAt(this.wantedX, this.wantedY, this.wantedZ);
            Vec3 targetDelta = this.mob.position().subtract(this.wantedX, this.wantedY, this.wantedZ);
            float distanceToTargetSquared = (float)targetDelta.lengthSqr();
            if (distanceToTargetSquared <= 0.25f) {
                return;
            }
            double speed = this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED) * this.speedModifier;
            double limitedSpeed = Mth.clamp((double)speed, (double)0.0, (double)(Mth.sqrt((float)distanceToTargetSquared) * 0.9f));
            this.mob.setDeltaMovement(this.mob.getLookAngle().scale(limitedSpeed));
        }
    }

    private record PartCollision(LivingEntity entity, SandwormEntityPart part, float overlapVolume) {
    }

    private record LineCollision(Vec3 forceOrigin, Vec3 forceDirection, float distanceModifier) {
    }

    @FieldsAreNonnullByDefault
    @MethodsReturnNonnullByDefault
    @ParametersAreNonnullByDefault
    public class SandwormPathNavigation
    extends PathNavigation {
        public SandwormPathNavigation(Level level) {
            super((Mob)SandwormEntity.this, level);
        }

        protected PathFinder createPathFinder(int maxVisitedNodes) {
            return new SandwormPathFinder(maxVisitedNodes);
        }

        protected Vec3 getTempMobPos() {
            return SandwormEntity.this.position();
        }

        protected boolean canUpdatePath() {
            return false;
        }

        protected double getGroundY(Vec3 vec) {
            return vec.y;
        }
    }

    @FieldsAreNonnullByDefault
    @MethodsReturnNonnullByDefault
    @ParametersAreNonnullByDefault
    public static class SandwormPathFinder
    extends PathFinder {
        public SandwormPathFinder(int maxVisitedNodes) {
            super((NodeEvaluator)new FlyNodeEvaluator(), maxVisitedNodes);
        }

        @Nullable
        public Path findPath(PathNavigationRegion region, Mob mob, Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier) {
            if (targetPositions.isEmpty()) {
                return null;
            }
            BlockPos mobPosition = mob.blockPosition();
            BlockPos targetPosition = targetPositions.iterator().next();
            return new Path(List.of(new Node(mobPosition.getX(), mobPosition.getY(), mobPosition.getZ()), new Node(targetPosition.getX(), targetPosition.getY(), targetPosition.getZ())), targetPosition, true);
        }
    }
}

