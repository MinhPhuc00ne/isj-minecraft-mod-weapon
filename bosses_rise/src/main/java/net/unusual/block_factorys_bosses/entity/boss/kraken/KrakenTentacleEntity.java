/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.DynamicOps
 *  io.netty.buffer.ByteBuf
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Registry
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtOps
 *  net.minecraft.network.codec.ByteBufCodecs
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.ByIdMap
 *  net.minecraft.util.ByIdMap$OutOfBoundsStrategy
 *  net.minecraft.util.Mth
 *  net.minecraft.util.StringRepresentable
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntitySelector
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.control.BodyRotationControl
 *  net.minecraft.world.entity.ai.control.LookControl
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.WrappedGoal
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.material.PushReaction
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animatable.GeoEntity
 *  software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.animation.Animation$LoopType
 *  software.bernie.geckolib.animation.AnimationController
 *  software.bernie.geckolib.animation.AnimationController$ParticleKeyframeHandler
 *  software.bernie.geckolib.animation.RawAnimation
 *  software.bernie.geckolib.animation.keyframe.event.ParticleKeyframeEvent
 *  software.bernie.geckolib.animation.keyframe.event.data.ParticleKeyframeData
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package net.unusual.block_factorys_bosses.entity.boss.kraken;

import com.mojang.serialization.DynamicOps;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticleEmitter;
import net.unusual.block_factorys_bosses.entity.IParticleAttachment;
import net.unusual.block_factorys_bosses.entity.OwnableByAllEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenTentacleEntityPart;
import net.unusual.block_factorys_bosses.entity.boss.kraken.goals.KrakenTentacleAttackGoal;
import net.unusual.block_factorys_bosses.entity.boss.kraken.goals.KrakenTentacleCrateGoal;
import net.unusual.block_factorys_bosses.entity.boss.kraken.goals.KrakenTentacleRunawayGoal;
import net.unusual.block_factorys_bosses.entity.boss.part.AbstractEntityPartParent;
import net.unusual.block_factorys_bosses.entity.boss.part.AbstractGeoEntityPart;
import net.unusual.block_factorys_bosses.entity.control.FollowHeadBodyRotationControl;
import net.unusual.block_factorys_bosses.entity.control.NoMoveControl;
import net.unusual.block_factorys_bosses.entity.projectile.CannonballEntity;
import net.unusual.block_factorys_bosses.geckolib.CommonPoseStack;
import net.unusual.block_factorys_bosses.geckolib.ServerAnimationPlayer;
import net.unusual.block_factorys_bosses.geckolib.boss.kraken.KrakenTentacleModel;
import net.unusual.block_factorys_bosses.geckolib.boss.kraken.KrakenTentacleRenderer;
import net.unusual.block_factorys_bosses.geckolib.util.ParticleLocator;
import net.unusual.block_factorys_bosses.geckolib.util.SoundKeyframePlayer;
import net.unusual.block_factorys_bosses.init.BossesRiseDamageTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseEntityDataSerializers;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.random.WeightedPool;
import net.unusual.block_factorys_bosses.structures.KrakenShipStructure;
import net.unusual.block_factorys_bosses.util.SpatialUtil;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.keyframe.event.ParticleKeyframeEvent;
import software.bernie.geckolib.animation.keyframe.event.data.ParticleKeyframeData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.util.GeckoLibUtil;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class KrakenTentacleEntity
extends Monster
implements AbstractEntityPartParent<KrakenTentacleEntity, KrakenTentacleEntityPart>,
GeoEntity,
OwnableByAllEntity,
ServerAnimationPlayer.CustomTransformationsEntity,
IParticleAttachment {
    public static final float VERTICAL_REACH_OFFSET = 7.0f;
    public static final float VERTICAL_REACH_RANGE = 3.0f;
    private static final String TAG_OWNER = "Owner";
    private static final String TAG_TENTACLE_TYPE = "tentacle_type";
    private static final String TAG_TENTACLE_ID = "tentacle_id";
    private static final String TAG_TARGET_ROTATION = "target_rotation";
    private static final String TAG_TARGET_ROTATION_RANGE = "target_rotation_range";
    private static final EntityDataAccessor<TentacleType> DATA_TENTACLE_TYPE = SynchedEntityData.defineId(KrakenTentacleEntity.class, (EntityDataSerializer)((EntityDataSerializer)BossesRiseEntityDataSerializers.TENTACLE_TYPE.get()));
    public static final EntityDataAccessor<Vec3> DATA_LERP_POSITION = SynchedEntityData.defineId(KrakenTentacleEntity.class, (EntityDataSerializer)((EntityDataSerializer)BossesRiseEntityDataSerializers.VEC_3.get()));
    public static final EntityDataAccessor<Long> DATA_LERP_TIME = SynchedEntityData.defineId(KrakenTentacleEntity.class, (EntityDataSerializer)EntityDataSerializers.LONG);
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private static final KrakenTentacleModel MODEL = new KrakenTentacleModel(false);
    private static final KrakenTentacleModel SLIM_MODEL = new KrakenTentacleModel(true);
    private final ServerAnimationPlayer<KrakenTentacleEntity> serverAnimationPlayer = new ServerAnimationPlayer<KrakenTentacleEntity>((GeoModel<KrakenTentacleEntity>)MODEL, this);
    private static final TentaclePartDefinition[] PART_DEFINITIONS = new TentaclePartDefinition[]{new TentaclePartDefinition("hitbox1", 2.5f, 2.5f), new TentaclePartDefinition("hitbox2", 2.5f, 2.5f), new TentaclePartDefinition("hitbox3", 2.5f, 2.5f), new TentaclePartDefinition("hitbox4", 2.5f, 2.5f), new TentaclePartDefinition("hitbox5", 2.5f, 2.5f), new TentaclePartDefinition("hitbox6", 2.5f, 2.5f), new TentaclePartDefinition("hitbox7", 2.5f, 2.5f), new TentaclePartDefinition("hitbox8", 3.0f, 2.5f), new TentaclePartDefinition("hitbox9", 3.0f, 2.5f), new TentaclePartDefinition("hitbox10", 2.5f, 2.5f), new TentaclePartDefinition("hitbox11", 3.0f, 2.25f), new TentaclePartDefinition("hitbox12", 3.0f, 2.25f), new TentaclePartDefinition("hitbox13", 2.0f, 1.75f), new TentaclePartDefinition("hitbox14", 1.75f, 1.75f)};
    private final KrakenTentacleEntityPart[] parts = new KrakenTentacleEntityPart[PART_DEFINITIONS.length];
    @org.jetbrains.annotations.Nullable
    private UUID ownerUUID = null;
    @org.jetbrains.annotations.Nullable
    private KrakenEntity owner;
    @org.jetbrains.annotations.Nullable
    private String tentacleId = null;
    private float targetYRot = 0.0f;
    private float targetYRotRange = 180.0f;
    private static final String MAIN_CONTROLLER = "main_controller";
    private static final int DEATH_DURATION = 115;
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("1st_phase_idle");
    public static final String LIGHT_ATTACK_ANIM_NAME = "light_attack";
    private static final RawAnimation LIGHT_ATTACK_ANIM = RawAnimation.begin().then("light_slam", Animation.LoopType.PLAY_ONCE);
    public static final String HEAVY_ATTACK_LEFT_ANIM_NAME = "heavy_attack_left";
    private static final RawAnimation HEAVY_ATTACK_LEFT_ANIM = RawAnimation.begin().then("strong_slam", Animation.LoopType.PLAY_ONCE);
    public static final String HEAVY_ATTACK_RIGHT_ANIM_NAME = "heavy_attack_right";
    private static final RawAnimation HEAVY_ATTACK_RIGHT_ANIM = RawAnimation.begin().then("strong_slam_mirrored", Animation.LoopType.PLAY_ONCE);
    public static final String DEATH_TENTACLE_NAME = "death_tentacle";
    private static final RawAnimation DEATH_TENTACLE_ANIM = RawAnimation.begin().thenPlayAndHold("death_tentacle");
    public static final String THROW_CRATE_NAME = "phase_2_throw_crate";
    private static final RawAnimation THROW_CRATE_ANIM = RawAnimation.begin().then("phase_2_throw_crate", Animation.LoopType.PLAY_ONCE);
    public static final String STUNNED_ANIM_NAME = "stunned";
    private static final RawAnimation STUNNED_ANIM = RawAnimation.begin().then("tentacle_stun_start1", Animation.LoopType.PLAY_ONCE).then("tentacle_stun_loop1", Animation.LoopType.PLAY_ONCE).then("tentacle_stun_end1", Animation.LoopType.PLAY_ONCE);
    public static final String HIDDEN_1_ANIM_NAME = "hidden_1";
    private static final RawAnimation HIDDEN_1_ANIM = RawAnimation.begin().then("death_tentacle", Animation.LoopType.PLAY_ONCE).thenPlayXTimes("hidden", 92).then("Intro_cin_tentacle_3", Animation.LoopType.PLAY_ONCE);
    public static final String HIDDEN_2_ANIM_NAME = "hidden_2";
    private static final RawAnimation HIDDEN_2_ANIM = RawAnimation.begin().then("death_tentacle", Animation.LoopType.PLAY_ONCE).thenPlayXTimes("hidden", 80).then("Intro_cin_tentacle_4", Animation.LoopType.PLAY_ONCE);
    public static final String HIDDEN_3_ANIM_NAME = "hidden_3";
    private static final RawAnimation HIDDEN_3_ANIM = RawAnimation.begin().then("death_tentacle", Animation.LoopType.PLAY_ONCE).thenPlayXTimes("hidden", 86).then("Intro_cin_tentacle_6", Animation.LoopType.PLAY_ONCE);
    public static final String INTRO_TENTACLE_NAME_1 = "Intro_cin_tentacle_1";
    private static final RawAnimation INTRO_TENTACLE_ANIM_1 = RawAnimation.begin().then("Intro_cin_tentacle_1", Animation.LoopType.PLAY_ONCE);
    public static final String INTRO_TENTACLE_NAME_2 = "Intro_cin_tentacle_2";
    private static final RawAnimation INTRO_TENTACLE_ANIM_2 = RawAnimation.begin().then("Intro_cin_tentacle_2", Animation.LoopType.PLAY_ONCE);
    public static final String INTRO_TENTACLE_NAME_3 = "Intro_cin_tentacle_3";
    private static final RawAnimation INTRO_TENTACLE_ANIM_3 = RawAnimation.begin().then("Intro_cin_tentacle_3", Animation.LoopType.PLAY_ONCE);
    public static final String INTRO_TENTACLE_NAME_4 = "Intro_cin_tentacle_4";
    private static final RawAnimation INTRO_TENTACLE_ANIM_4 = RawAnimation.begin().then("Intro_cin_tentacle_4", Animation.LoopType.PLAY_ONCE);
    public static final String INTRO_TENTACLE_NAME_5 = "Intro_cin_tentacle_5";
    private static final RawAnimation INTRO_TENTACLE_ANIM_5 = RawAnimation.begin().then("Intro_cin_tentacle_5", Animation.LoopType.PLAY_ONCE);
    public static final String INTRO_TENTACLE_NAME_6 = "Intro_cin_tentacle_6";
    private static final RawAnimation INTRO_TENTACLE_ANIM_6 = RawAnimation.begin().then("Intro_cin_tentacle_6", Animation.LoopType.PLAY_ONCE);
    public static final String INTRO_TENTACLE_NAME_7 = "Intro_cin_tentacle_7";
    private static final RawAnimation INTRO_TENTACLE_ANIM_7 = RawAnimation.begin().then("Intro_cin_tentacle_7", Animation.LoopType.PLAY_ONCE);
    public static final String INTRO_TENTACLE_NAME_8 = "Intro_cin_tentacle_8";
    private static final RawAnimation INTRO_TENTACLE_ANIM_8 = RawAnimation.begin().then("Intro_cin_tentacle_8", Animation.LoopType.PLAY_ONCE);
    public static final String INTRO_CANNON_NAME = "intro_cannon";
    private static final RawAnimation INTRO_CANNON_ANIM = RawAnimation.begin().then("intro_tentacle_cannon", Animation.LoopType.PLAY_ONCE);
    public static final String DEATH_CANNON_NAME = "death_cannon";
    private static final RawAnimation DEATH_CANNON_ANIM = RawAnimation.begin().thenPlayAndHold("death_tentacle_cannon");
    private static final WeightedPool<String> STUNNED_ANIMATION_POOL = new WeightedPool(List.of(WeightedPool.option(2, "stunned"), WeightedPool.option(1, "hidden_1"), WeightedPool.option(1, "hidden_2"), WeightedPool.option(1, "hidden_3")));
    public static final int MAX_GRAB_TIME = 80;
    private static final TentacleAttackCollider[] HEAVY_ATTACK_COLLIDERS = new TentacleAttackCollider[]{new TentacleAttackCollider(1.05f, 0.1f, 0.4f), new TentacleAttackCollider(0.6f, 0.5f, 0.4f), new TentacleAttackCollider(0.9f, 0.4f, 0.4f), new TentacleAttackCollider(1.05f, -0.2f, 0.4f), new TentacleAttackCollider(1.05f, -0.4f, 0.2f)};

    public KrakenTentacleEntityPart getThrowPart() {
        return this.parts[this.parts.length - 3];
    }

    public KrakenTentacleEntity(EntityType<? extends KrakenTentacleEntity> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);
        this.setPersistenceRequired();
        for (int index = 0; index < PART_DEFINITIONS.length; ++index) {
            KrakenTentacleEntityPart part;
            TentaclePartDefinition def = PART_DEFINITIONS[index];
            this.parts[index] = part = new KrakenTentacleEntityPart(this, index, def.boneName(), def.boneName(), 1.0f, 1.0f);
        }
        this.setId(ENTITY_COUNTER.getAndAdd(this.parts.length + 1) + 1);
        this.updateParts();
        this.moveControl = new NoMoveControl((Mob)this);
        this.lookControl = new KrakenTentacleLookControl((Mob)this);
    }

    public void setId(int id) {
        super.setId(id);
        for (int i = 0; i < this.parts.length; ++i) {
            this.parts[i].setId(id + i + 1);
        }
    }

    protected void registerGoals() {
        this.targetSelector.addGoal(1, (Goal)new NearestAttackableTargetGoal<Player>((Mob)this, Player.class, true, false){

            protected AABB getTargetSearchArea(double targetDistance) {
                return this.mob.getBoundingBox().inflate(targetDistance, 4.0, targetDistance).move(0.0, 5.0, 0.0);
            }
        });
        this.targetSelector.addGoal(2, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]));
        this.goalSelector.addGoal(1, (Goal)new KrakenTentacleAttackGoal(this, HEAVY_ATTACK_LEFT_ANIM_NAME, 0.4f, 0.2f, 100, 20, 40, tentacle -> tentacle.attackEntitiesCollidingBox(true, 1.0f)));
        this.goalSelector.addGoal(1, (Goal)new KrakenTentacleAttackGoal(this, HEAVY_ATTACK_RIGHT_ANIM_NAME, 0.4f, 0.2f, 100, 20, 40, tentacle -> tentacle.attackEntitiesCollidingBox(false, 1.0f)));
        this.goalSelector.addGoal(2, (Goal)new KrakenTentacleAttackGoal(this, LIGHT_ATTACK_ANIM_NAME, 0.9f, 0.2f, 75, 20, 36, tentacle -> tentacle.attackEntitiesCollidingPlane(4.0f, 0.5f)));
        this.goalSelector.addGoal(3, (Goal)new KrakenTentacleCrateGoal(this, THROW_CRATE_NAME, 0.8f, 250, 130));
        this.goalSelector.addGoal(4, (Goal)new KrakenTentacleRunawayGoal(this, INTRO_CANNON_NAME, 0.8f, 154, 114));
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController((GeoAnimatable)this, MAIN_CONTROLLER, state -> state.setAndContinue(IDLE_ANIM)).triggerableAnim(LIGHT_ATTACK_ANIM_NAME, LIGHT_ATTACK_ANIM).triggerableAnim(HEAVY_ATTACK_LEFT_ANIM_NAME, HEAVY_ATTACK_LEFT_ANIM).triggerableAnim(HEAVY_ATTACK_RIGHT_ANIM_NAME, HEAVY_ATTACK_RIGHT_ANIM).triggerableAnim(DEATH_TENTACLE_NAME, DEATH_TENTACLE_ANIM).triggerableAnim(THROW_CRATE_NAME, THROW_CRATE_ANIM).triggerableAnim(STUNNED_ANIM_NAME, STUNNED_ANIM).triggerableAnim(HIDDEN_1_ANIM_NAME, HIDDEN_1_ANIM).triggerableAnim(HIDDEN_2_ANIM_NAME, HIDDEN_2_ANIM).triggerableAnim(HIDDEN_3_ANIM_NAME, HIDDEN_3_ANIM).triggerableAnim(INTRO_TENTACLE_NAME_1, INTRO_TENTACLE_ANIM_1).triggerableAnim(INTRO_TENTACLE_NAME_2, INTRO_TENTACLE_ANIM_2).triggerableAnim(INTRO_TENTACLE_NAME_3, INTRO_TENTACLE_ANIM_3).triggerableAnim(INTRO_TENTACLE_NAME_4, INTRO_TENTACLE_ANIM_4).triggerableAnim(INTRO_TENTACLE_NAME_5, INTRO_TENTACLE_ANIM_5).triggerableAnim(INTRO_TENTACLE_NAME_6, INTRO_TENTACLE_ANIM_6).triggerableAnim(INTRO_TENTACLE_NAME_7, INTRO_TENTACLE_ANIM_7).triggerableAnim(INTRO_TENTACLE_NAME_8, INTRO_TENTACLE_ANIM_8).triggerableAnim(INTRO_CANNON_NAME, INTRO_CANNON_ANIM).triggerableAnim(DEATH_CANNON_NAME, DEATH_CANNON_ANIM).setSoundKeyframeHandler(new SoundKeyframePlayer()).setParticleKeyframeHandler(event -> {
            if (!((KrakenTentacleEntity)event.getAnimatable()).level().isClientSide()) {
                return;
            }
            ParticleKeyframeData data = event.getKeyframeData();
            ParticleType particleType = (ParticleType)((Registry)BossesRiseParticleTypes.REGISTRY.getRegistry().get()).get(BossesRise.prefix(data.getEffect()));
            if (particleType == null) {
                return;
            }
            ParticleOptions particle = (ParticleOptions)particleType.codec().codec().parse((DynamicOps)((KrakenTentacleEntity)event.getAnimatable()).level().registryAccess().createSerializationContext((DynamicOps)NbtOps.INSTANCE), (Object)new CompoundTag()).getOrThrow();
            ParticleLocator locator = KrakenTentacleRenderer.BONER.get(data.getLocator());
            SubParticleEmitter.attachNewParticle((KrakenTentacleEntity)event.getAnimatable(), particle, locator);
        }));
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

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    public void playRandomStunnedAnimation() {
        if (this.isDeadOrDying()) {
            return;
        }
        this.triggerAnim(STUNNED_ANIMATION_POOL.selectRandom());
    }

    public int getHeadRotSpeed() {
        return 5;
    }

    @Override
    public void applyCustomWorldSpaceTransformations(ServerAnimationPlayer.ServerPoseStack poseStack) {
        this.transformCrateGrab(poseStack, 1.0f);
    }

    public void transformCrateGrab(CommonPoseStack poseStack, float partialTick) {
        if (this.getTentacleType() == TentacleType.CRATE) {
            long setTime = (Long)this.getEntityData().get(DATA_LERP_TIME);
            if (setTime == 0L) {
                return;
            }
            float time = (float)(this.level().getGameTime() - (Long)this.getEntityData().get(DATA_LERP_TIME)) + partialTick;
            if (time > 80.0f) {
                return;
            }
            float factor = time / 80.0f;
            float wave = Math.clamp((float)((float)Math.sin(factor * (float)Math.PI) * 1.5f), (float)0.0f, (float)1.0f);
            Vec3 create = (Vec3)this.getEntityData().get(DATA_LERP_POSITION);
            Vec3 cratePos = create.subtract(this.getViewVector(partialTick).scale(6.0).add(0.0, 6.0, 0.0));
            Vec3 diff = cratePos.subtract(this.position()).scale((double)wave);
            poseStack.bosses_rise_java$translate(diff.x(), diff.y(), diff.z());
        }
    }

    protected BodyRotationControl createBodyControl() {
        return new FollowHeadBodyRotationControl((Mob)this);
    }

    public void setTargetRotation(float targetRotation, float targetRotationRange) {
        this.targetYRot = targetRotation;
        this.targetYRotRange = targetRotationRange;
        this.setYRot(targetRotation);
    }

    public float getTargetRotation() {
        return this.targetYRot;
    }

    public float getTargetRotationRange() {
        return this.targetYRotRange;
    }

    public void setOwner(KrakenEntity entity) {
        this.ownerUUID = entity.getUUID();
    }

    @org.jetbrains.annotations.Nullable
    public KrakenEntity getOwnerKraken() {
        return this.owner;
    }

    @org.jetbrains.annotations.Nullable
    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    private void updateOwnerReference() {
        KrakenEntity kraken;
        Level level = this.level();
        if (!(level instanceof ServerLevel)) {
            return;
        }
        ServerLevel level2 = (ServerLevel)level;
        UUID ownerUuid = this.getOwnerUUID();
        if (ownerUuid == null) {
            this.owner = null;
            return;
        }
        if (this.owner != null && this.owner.getUUID().equals(ownerUuid) && this.owner.isRemoved() == this.isRemoved()) {
            return;
        }
        Entity entity = level2.getEntity(ownerUuid);
        this.owner = entity instanceof KrakenEntity ? (kraken = (KrakenEntity)entity) : null;
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_TENTACLE_TYPE, TentacleType.MELEE);
        builder.define(DATA_LERP_POSITION, Vec3.ZERO);
        builder.define(DATA_LERP_TIME, 0L);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (DATA_TENTACLE_TYPE.equals(key)) {
            TentacleType tentacleType = this.getTentacleType();
            this.serverAnimationPlayer.setModel((GeoModel<KrakenTentacleEntity>)(tentacleType.isSlim() ? SLIM_MODEL : MODEL));
            this.updateParts();
        }
    }

    public void setTentacleType(TentacleType tentacleType) {
        this.entityData.set(DATA_TENTACLE_TYPE, tentacleType);
    }

    public TentacleType getTentacleType() {
        return (TentacleType)((Object)this.entityData.get(DATA_TENTACLE_TYPE));
    }

    public void setTentacleId(String tentacleId) {
        this.tentacleId = tentacleId;
    }

    @org.jetbrains.annotations.Nullable
    public String getTentacleId() {
        return this.tentacleId;
    }

    public boolean isInWall() {
        return false;
    }

    public void baseTick() {
        this.updateOwnerReference();
        super.baseTick();
        this.serverAnimationPlayer.tickAnimations();
        AbstractGeoEntityPart.positionPartsFromBones(this.parts, this.serverAnimationPlayer, (Vec3)(this.getTentacleType().isSlim() ? Vec3.ZERO : KrakenTentacleModel.ROOT_OFFSET));
    }

    public boolean isAttacking() {
        for (WrappedGoal wrappedGoal : this.goalSelector.getAvailableGoals()) {
            Goal goal;
            if (!wrappedGoal.isRunning() || !((goal = wrappedGoal.getGoal()) instanceof KrakenTentacleAttackGoal) && !(goal instanceof KrakenTentacleCrateGoal)) continue;
            return true;
        }
        return false;
    }

    private List<LivingEntity> findCollidingEntities(AABB box) {
        return this.level().getEntitiesOfClass(LivingEntity.class, box, e -> !(e instanceof KrakenTentacleEntity) && !(e instanceof KrakenEntity) && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(e));
    }

    public void attackEntitiesCollidingPlane(float horizontalReach, float maxDamageScale) {
        float tentacleHalfWidth = horizontalReach / 2.0f;
        double tentacleReach = this.getAttributeValue(Attributes.FOLLOW_RANGE);
        Vec3 direction = this.getLookAngle().multiply(1.0, 0.0, 1.0).normalize();
        Vec3 normal = direction.cross(new Vec3(0.0, 1.0, 0.0));
        AABB box = new AABB(this.position().add(0.0, 4.0, 0.0), this.position().add(direction.scale(tentacleReach)).add(0.0, 10.0, 0.0)).inflate((double)tentacleHalfWidth, 0.0, (double)tentacleHalfWidth);
        List<LivingEntity> collisions = this.findCollidingEntities(box);
        for (LivingEntity entity : collisions) {
            Vec3 entityPosition = entity.position();
            Vec3 offset = entityPosition.subtract(this.position());
            double distanceToPlaneSigned = direction.z() * offset.x() - direction.x() * offset.z();
            double distanceToPlane = Math.abs(distanceToPlaneSigned);
            if (distanceToPlane > (double)tentacleHalfWidth) continue;
            double distanceModifier = ((double)tentacleHalfWidth - distanceToPlane) / (double)tentacleHalfWidth;
            this.attackEntityWithSlam(new AttackTarget(entity, (float)distanceModifier, normal.scale(Math.signum(-distanceToPlaneSigned) * distanceModifier * 0.8).add(0.0, distanceModifier * 0.6, 0.0)), maxDamageScale);
        }
        this.destroyShip(box);
    }

    public void attackEntitiesCollidingBox(boolean mirrorColliders, float maxDamageScale) {
        float scale = 18.0f * this.getScale();
        Vec3 direction = this.getLookAngle().multiply(1.0, 0.0, 1.0).normalize();
        Vec3 normal = direction.cross(new Vec3(0.0, 1.0, 0.0));
        Vec3 basePosition = this.position().add(0.0, 7.0, 0.0);
        ArrayList<AttackTarget> targets = new ArrayList<AttackTarget>();
        for (int index = 0; index < HEAVY_ATTACK_COLLIDERS.length; ++index) {
            TentacleAttackCollider collider = HEAVY_ATTACK_COLLIDERS[index];
            float distanceOffset = collider.distanceOffset() * scale;
            float sideOffset = collider.sideOffset() * scale * (float)(mirrorColliders ? -1 : 1);
            float boxSize = collider.boxSize() * scale;
            AABB box = AABB.ofSize((Vec3)basePosition.add(direction.scale((double)distanceOffset)).add(normal.scale((double)sideOffset)), (double)boxSize, (double)6.0, (double)boxSize);
            Vec3 boxCenter = box.getCenter();
            if (index == 0) {
                this.destroyShip(box);
            }
            List<LivingEntity> collisions = this.findCollidingEntities(box);
            for (LivingEntity entity : collisions) {
                Vec3 offsetFromCenter = entity.position().subtract(boxCenter);
                float distanceModifier = Mth.clamp((float)(1.0f - (float)offsetFromCenter.length() / boxSize), (float)0.0f, (float)1.0f);
                Vec3 impulse = offsetFromCenter.normalize().scale((double)distanceModifier * 0.8).add(0.0, (double)distanceModifier * 0.6, 0.0);
                AttackTarget existingTarget = targets.stream().filter(target -> target.entity.equals((Object)entity)).findFirst().orElse(null);
                if (existingTarget != null) {
                    targets.remove(existingTarget);
                }
                targets.add(new AttackTarget(entity, distanceModifier, impulse).combinedWith(existingTarget));
            }
            this.destroyShip(box);
        }
        targets.forEach(target -> this.attackEntityWithSlam((AttackTarget)target, maxDamageScale));
    }

    private void attackEntityWithSlam(AttackTarget target, float maxDamageScale) {
        target.entity().hurt(this.level().damageSources().source(BossesRiseDamageTypes.KRAKEN_TENTACLE_SMASH, (Entity)this, (Entity)this.getOwner()), (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * maxDamageScale * Mth.clamp((float)target.distanceModifier(), (float)0.5f, (float)1.0f));
        SpatialUtil.pushEntity((Entity)target.entity(), target.impulse(), 1.5);
    }

    public boolean hurt(DamageSource damageSource, float amount) {
        if (this.level().isClientSide()) {
            return false;
        }
        return this.hurt(this.parts[8], damageSource, amount);
    }

    @Override
    public boolean hurt(KrakenTentacleEntityPart part, DamageSource damageSource, float amount) {
        if (CannonballEntity.hasCausedDamage(damageSource)) {
            amount *= 5.0f;
        }
        return this.reallyHurt(damageSource, amount);
    }

    public boolean reallyHurt(DamageSource damageSource, float amount) {
        return super.hurt(damageSource, amount);
    }

    public void killQuietly() {
        if (this.isDeadOrDying()) {
            return;
        }
        this.setHealth(0.0f);
        this.die(this.level().damageSources().genericKill());
    }

    public void die(DamageSource damageSource) {
        super.die(damageSource);
        if (this.dead) {
            this.triggerAnim(null, this.getTentacleType().isSlim ? DEATH_CANNON_NAME : DEATH_TENTACLE_NAME);
            if (this.isPassenger()) {
                this.removeVehicle();
            }
        }
    }

    public void remove(Entity.RemovalReason reason) {
        super.remove(reason);
        this.updateOwnerReference();
        if (this.owner != null) {
            this.owner.onTentacleDestroyed(this);
        }
    }

    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime >= 115 && !this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent((Entity)this, (byte)60);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    private void destroyShip(AABB box) {
        Level level = this.level();
        if (!(level instanceof ServerLevel)) {
            return;
        }
        ServerLevel level2 = (ServerLevel)level;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        int x = (int)box.minX;
        while ((double)x <= box.maxX) {
            int y = (int)box.minY - 2;
            while ((double)y <= box.maxY + 2.0) {
                int z = (int)box.minZ;
                while ((double)z <= box.maxZ) {
                    pos.set(x, y - 6, z);
                    KrakenShipStructure.replaceWithDestroyed(level2, (BlockPos)pos);
                    ++z;
                }
                ++y;
            }
            ++x;
        }
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.getOwnerUUID() != null) {
            compound.putUUID(TAG_OWNER, this.getOwnerUUID());
        }
        compound.putString(TAG_TENTACLE_TYPE, this.getTentacleType().getSerializedName());
        if (this.getTentacleId() != null) {
            compound.putString(TAG_TENTACLE_ID, this.getTentacleId());
        }
        compound.putFloat(TAG_TARGET_ROTATION, this.targetYRot);
        compound.putFloat(TAG_TARGET_ROTATION_RANGE, this.targetYRotRange);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.hasUUID(TAG_OWNER)) {
            this.ownerUUID = compound.getUUID(TAG_OWNER);
        }
        if (compound.contains(TAG_TENTACLE_TYPE)) {
            this.setTentacleType(TentacleType.NAME_LOOKUP.apply(compound.getString(TAG_TENTACLE_TYPE)));
        }
        if (compound.contains(TAG_TENTACLE_ID, 8)) {
            this.setTentacleId(compound.getString(TAG_TENTACLE_ID));
        }
        this.targetYRot = compound.getFloat(TAG_TARGET_ROTATION);
        this.targetYRotRange = compound.getFloat(TAG_TARGET_ROTATION_RANGE);
    }

    public float rotate(Rotation transformRotation) {
        this.setTargetRotation(SpatialUtil.rotateDegrees(this.getTargetRotation(), transformRotation), this.getTargetRotationRange());
        return super.rotate(transformRotation);
    }

    public float mirror(Mirror transformMirror) {
        this.setTargetRotation(SpatialUtil.mirrorDegrees(this.getTargetRotation(), transformMirror), this.getTargetRotationRange());
        return super.mirror(transformMirror);
    }

    public boolean hasLineOfSight(Entity target) {
        return this.hasLineOfSight(target, this.getTargetRotationRange());
    }

    public boolean hasLineOfSight(Entity target, float maxRotationDelta) {
        if (target.level() != this.level()) {
            return false;
        }
        Vec3 offset = target.position().subtract(this.position().add(0.0, 7.0, 0.0));
        double followRange = this.getAttributeValue(Attributes.FOLLOW_RANGE);
        if (this.getTentacleType() == TentacleType.CRATE) {
            return offset.lengthSqr() <= followRange * followRange * 1.5;
        }
        if (Math.abs(offset.y()) >= 3.0) {
            return false;
        }
        if (offset.lengthSqr() > followRange * followRange) {
            return false;
        }
        return !(this.getDeltaFromTargetRotation(offset) > maxRotationDelta);
    }

    private float getDeltaFromTargetRotation(Vec3 offset) {
        return Mth.degreesDifferenceAbs((float)((float)Mth.atan2((double)(-offset.x()), (double)offset.z()) * 57.295776f), (float)this.getTargetRotation());
    }

    public boolean isMultipartEntity() {
        return true;
    }

    public KrakenTentacleEntityPart[] getParts() {
        return this.parts;
    }

    private void updateParts() {
        boolean isSlim = this.getTentacleType().isSlim();
        for (int index = 0; index < this.parts.length; ++index) {
            KrakenTentacleEntityPart part = this.parts[index];
            TentaclePartDefinition def = PART_DEFINITIONS[index];
            float size = isSlim ? def.slimSize() : def.normalSize();
            part.setBoundingBoxSize(size, size);
        }
    }

    public AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(50.0);
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

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.0).add(Attributes.MAX_HEALTH, 50.0).add(Attributes.ARMOR, 20.0).add(Attributes.ATTACK_DAMAGE, 10.0).add(Attributes.FOLLOW_RANGE, 24.0);
    }

    public record TentaclePartDefinition(String boneName, float normalSize, float slimSize) {
    }

    public class KrakenTentacleLookControl
    extends LookControl {
        public KrakenTentacleLookControl(Mob mob) {
            super(mob);
        }

        public void tick() {
            this.mob.setXRot(0.0f);
            if (KrakenTentacleEntity.this.getTentacleType() == TentacleType.CANNON) {
                return;
            }
            this.getYRotD().ifPresent(targetYRot -> {
                float yRot = this.rotateTowards(this.mob.getYRot(), targetYRot.floatValue(), this.yMaxRotSpeed);
                yRot = Mth.rotateIfNecessary((float)yRot, (float)KrakenTentacleEntity.this.targetYRot, (float)KrakenTentacleEntity.this.targetYRotRange);
                this.mob.setYRot(yRot);
                this.mob.yHeadRot = yRot;
            });
        }
    }

    public static enum TentacleType implements StringRepresentable
    {
        MELEE("melee", false),
        CANNON("cannon", true),
        CRATE("create", false),
        RUNAWAY("runaway", true);

        public static final Function<String, TentacleType> NAME_LOOKUP;
        public static final IntFunction<TentacleType> BY_ID;
        public static final ToIntFunction<TentacleType> FROM_ID;
        public static final StreamCodec<ByteBuf, TentacleType> STREAM_CODEC;
        private final String name;
        private final boolean isSlim;

        private TentacleType(String name, boolean isSlim) {
            this.name = name;
            this.isSlim = isSlim;
        }

        public String getSerializedName() {
            return this.name;
        }

        public boolean isSlim() {
            return this.isSlim;
        }

        static {
            NAME_LOOKUP = StringRepresentable.createNameLookup(TentacleType.values(), value -> value);
            BY_ID = ByIdMap.continuous(TentacleType::ordinal, TentacleType.values(), ByIdMap.OutOfBoundsStrategy.ZERO);
            FROM_ID = TentacleType::ordinal;
            STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, FROM_ID);
        }
    }

    private record AttackTarget(LivingEntity entity, float distanceModifier, Vec3 impulse) {
        public AttackTarget combinedWith(@org.jetbrains.annotations.Nullable AttackTarget other) {
            if (other == null) {
                return this;
            }
            return new AttackTarget(this.entity(), Math.max(this.distanceModifier(), other.distanceModifier()), this.impulse().normalize().cross(other.impulse().normalize()).scale((this.impulse().length() + other.impulse().length()) / 2.0));
        }
    }

    private record TentacleAttackCollider(float distanceOffset, float sideOffset, float boxSize) {
    }
}

