/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.Util
 *  net.minecraft.advancements.AdvancementHolder
 *  net.minecraft.advancements.AdvancementProgress
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.particles.ItemParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.PlayerAdvancements
 *  net.minecraft.server.ServerAdvancementManager
 *  net.minecraft.server.level.ServerBossEvent
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.BlockTags
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
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.OwnableEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.SpawnGroupData
 *  net.minecraft.world.entity.ai.attributes.AttributeInstance
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.AbstractArrow
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.pathfinder.Path
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.level.BlockEvent$BreakEvent
 *  software.bernie.geckolib.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.animation.AnimationController$ParticleKeyframeHandler
 *  software.bernie.geckolib.animation.AnimationController$SoundKeyframeHandler
 *  software.bernie.geckolib.animation.PlayState
 *  software.bernie.geckolib.animation.RawAnimation
 *  software.bernie.geckolib.animation.keyframe.event.data.ParticleKeyframeData
 *  software.bernie.geckolib.animation.keyframe.event.data.SoundKeyframeData
 */
package net.unusual.block_factorys_bosses.entity.boss.yeti;

import java.lang.runtime.SwitchBootstraps;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.attachment.entity.GauntletAttachment;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerVariables;
import net.unusual.block_factorys_bosses.client.camera.client.CinematicAnimationController;
import net.unusual.block_factorys_bosses.configuration.ServerConfiguration;
import net.unusual.block_factorys_bosses.entity.SmoothGroundPathNavigation;
import net.unusual.block_factorys_bosses.entity.boss.AbstractBossEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.GlacialShoveEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeProjectileEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.goals.YetiChaseGoal;
import net.unusual.block_factorys_bosses.entity.boss.yeti.goals.YetiLookAroundGoal;
import net.unusual.block_factorys_bosses.geckolib.boss.yeti.YetiRenderer;
import net.unusual.block_factorys_bosses.init.BossesRiseDataAttachments;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.keyframe.event.data.ParticleKeyframeData;
import software.bernie.geckolib.animation.keyframe.event.data.SoundKeyframeData;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class YetiEntity
extends AbstractBossEntity {
    public static final EntityDataAccessor<Integer> DATA_HAS_USED_ULTIMATE = SynchedEntityData.defineId(YetiEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_IS_ENRAGED = SynchedEntityData.defineId(YetiEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_GROUNDSMASH_ANIMTIME = SynchedEntityData.defineId(YetiEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Long> DATA_HIT_TICK = SynchedEntityData.defineId(YetiEntity.class, (EntityDataSerializer)EntityDataSerializers.LONG);
    public static final int ANIMATION_TRANSITION_TIME = 3;
    public static final double ULTIMATE_HEALTH_THRESHOLD = 0.2;
    public static final int HURT_ANIMATION_TIME = 27;
    private static final Map<String, AbstractBossEntity.State> STATES = new HashMap<String, AbstractBossEntity.State>();
    public static final RawAnimation FROZEN_ANIM = RawAnimation.begin().thenLoop("animation.spawn_pose");
    public static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("animation.idle");
    public static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("animation.run");
    public static final RawAnimation INTRO_ANIM = RawAnimation.begin().thenPlay("animation.spawn");
    public static final RawAnimation ENRAGED_ANIM = RawAnimation.begin().thenPlay("animation.enraged");
    public static final RawAnimation DEATH_ANIM = RawAnimation.begin().thenPlayAndHold("animation.death");
    public static final RawAnimation SWIPE_1_ANIM = RawAnimation.begin().thenPlay("animation.hand_swipe_1");
    public static final RawAnimation SWIPE_2_ANIM = RawAnimation.begin().thenPlay("animation.hand_swipe_2");
    public static final RawAnimation PUNCH_ANIM = RawAnimation.begin().thenPlay("animation.hand_punch_1");
    public static final RawAnimation GROUND_SMASH_ANIM = RawAnimation.begin().thenPlay("animation.ground_smash");
    public static final RawAnimation SPIKE_ANIM = RawAnimation.begin().thenPlay("animation.throw_icespike");
    public static final RawAnimation RANGE_CONTROL_ANIM = RawAnimation.begin().thenPlay("animation.range_control");
    public static final RawAnimation ICE_BARRAGE_ANIM = RawAnimation.begin().thenPlay("animation.ice_barrage");
    public static final RawAnimation LEAP_START = RawAnimation.begin().thenPlay("animation.leap_start");
    public static final RawAnimation LEAP_AIR = RawAnimation.begin().thenPlay("animation.leap_in_air");
    public static final RawAnimation LEAP_SMASH = RawAnimation.begin().thenPlay("animation.leap_smash");
    private static final List<Vec3i> BREAK_1 = List.of(new Vec3i(1, 4, -1), new Vec3i(2, 4, -1), new Vec3i(0, 4, 0), new Vec3i(1, 4, 0), new Vec3i(2, 4, 0), new Vec3i(0, 4, 1), new Vec3i(1, 4, 1), new Vec3i(2, 5, -1), new Vec3i(-1, 5, 0), new Vec3i(0, 5, 0), new Vec3i(1, 5, 0), new Vec3i(2, 5, 0), new Vec3i(-1, 5, 1), new Vec3i(0, 5, 1), new Vec3i(1, 5, 1), new Vec3i(-1, 5, 2), new Vec3i(1, 5, 2), new Vec3i(0, 6, 0), new Vec3i(-1, 6, 0), new Vec3i(-1, 6, -1));
    private static final List<Vec3i> BREAK_2 = List.of(new Vec3i(-1, 3, 0), new Vec3i(-1, 3, 1), new Vec3i(-1, 3, 2), new Vec3i(-2, 3, 0), new Vec3i(-2, 3, 1), new Vec3i(-1, 4, 0), new Vec3i(-1, 4, 1), new Vec3i(-1, 4, 2));
    private static final List<Vec3i> BREAK_3 = List.of(new Vec3i(0, 1, -3), new Vec3i(-1, 1, -2), new Vec3i(0, 2, -3), new Vec3i(0, 3, -2), new Vec3i(1, 3, -3));
    private static final List<Vec3i> BREAK_4 = List.of(new Vec3i(3, 0, -1), new Vec3i(3, 0, 0), new Vec3i(3, 0, 1), new Vec3i(3, 1, -1), new Vec3i(3, 1, 0), new Vec3i(3, 1, 1), new Vec3i(3, 2, -1), new Vec3i(3, 2, 0), new Vec3i(3, 2, 1), new Vec3i(3, 3, -1), new Vec3i(3, 3, 0), new Vec3i(3, 3, 1), new Vec3i(3, 4, -1), new Vec3i(3, 4, 0), new Vec3i(3, 4, 1), new Vec3i(2, 4, 1), new Vec3i(3, 5, 0), new Vec3i(3, 5, 1), new Vec3i(2, 5, 1), new Vec3i(-2, 1, -1), new Vec3i(0, 1, -1), new Vec3i(1, 1, -3), new Vec3i(2, 2, 1), new Vec3i(1, 2, 1), new Vec3i(0, 2, 1), new Vec3i(-1, 2, 1), new Vec3i(2, 2, 0), new Vec3i(1, 2, 0), new Vec3i(0, 2, 0), new Vec3i(-1, 2, 0), new Vec3i(-2, 2, 0), new Vec3i(2, 2, -1), new Vec3i(1, 2, -1), new Vec3i(0, 2, -1), new Vec3i(-1, 2, -1), new Vec3i(-2, 2, -1), new Vec3i(2, 2, -2), new Vec3i(1, 2, -2), new Vec3i(0, 2, -2), new Vec3i(1, 2, -3), new Vec3i(0, 2, -3), new Vec3i(2, 3, 1), new Vec3i(1, 3, 1), new Vec3i(0, 3, 1), new Vec3i(2, 3, 0), new Vec3i(1, 3, 0), new Vec3i(0, 3, 0), new Vec3i(2, 3, -1), new Vec3i(1, 3, -1), new Vec3i(0, 3, -1), new Vec3i(2, 3, -2), new Vec3i(1, 3, -2));
    public static final int ENRAGE_FLAG_ICED = 0;
    public static final int ENRAGE_FLAG_EMERGING = 1;
    public static final int ENRAGE_FLAG_CALM = 2;
    public static final int ENRAGE_FLAG_READY = 3;
    public static final int ENRAGE_FLAG_START = 4;
    public static final int ENRAGE_FLAG_PISSED = 5;
    public static final int ENRAGE_FLAG_POPSICLE = 6;
    private YetiAttackPattern attackPattern;
    private int attackPatternIndex = 0;
    public AbstractBossEntity.Attack attackBuffer = null;
    private int targetTooFarTicks = 0;
    private static final YetiAttackPatternPool POOL;
    private static final YetiAttackPatternPool ENRAGED_POOL;

    public YetiEntity(EntityType<YetiEntity> type, Level world) {
        super(type, world);
        this.xpReward = 500;
    }

    @Override
    protected ServerBossEvent createBossEvent() {
        return (ServerBossEvent)Util.make(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.NOTCHED_6), bossEvent -> bossEvent.setVisible(false));
    }

    @Override
    protected int getBossType() {
        return 3;
    }

    protected PathNavigation createNavigation(Level level) {
        return new SmoothGroundPathNavigation((Mob)this, level);
    }

    public AABB getBoundingBoxForCulling() {
        return super.getBoundingBoxForCulling().inflate(3.0);
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        CinematicAnimationController<YetiEntity> controller = new CinematicAnimationController<YetiEntity>(this, "main_controller", 3, state -> {
            if (this.isDead()) {
                return PlayState.STOP;
            }
            if ((Integer)this.getEntityData().get(DATA_IS_ENRAGED) == 0) {
                return state.setAndContinue(FROZEN_ANIM);
            }
            return this.isTimerDone() && state.isMoving() ? state.setAndContinue(WALK_ANIM) : state.setAndContinue(IDLE_ANIM);
        });
        for (YetiState state2 : YetiState.values()) {
            state2.getAnimation().ifPresent(rawAnimation -> controller.triggerableAnim(state2.toString(), (RawAnimation)rawAnimation));
        }
        controller.triggerableAnim("leap_air", LEAP_AIR);
        controller.triggerableAnim("leap_smash", LEAP_SMASH);
        AnimationController.ParticleKeyframeHandler particleHandler = event -> {
            ParticleKeyframeData data = event.getKeyframeData();
            EntityRenderer patt0$temp = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer((Entity)((YetiEntity)event.getAnimatable()));
            if (patt0$temp instanceof YetiRenderer) {
                YetiRenderer renderer = (YetiRenderer)patt0$temp;
                renderer.getGeoModel().getBone(data.getLocator()).ifPresent(geoBone -> {
                    Vec3 position = ((YetiEntity)event.getAnimatable()).position().add(geoBone.getLocalPosition().x, geoBone.getLocalPosition().y, geoBone.getLocalPosition().z);
                    if (Minecraft.getInstance().level != null) {
                        switch (data.getEffect()) {
                            case "leap_smash_1": {
                                Minecraft.getInstance().level.addParticle((ParticleOptions)BossesRiseParticleTypes.YETI_SMOKE_IMPACT_1.get(), position.x, position.y, position.z, 0.0, 0.0, 0.0);
                                Minecraft.getInstance().level.addParticle((ParticleOptions)BossesRiseParticleTypes.YETI_SMOKE_IMPACT_2.get(), position.x, position.y, position.z, 0.0, 0.0, 0.0);
                                break;
                            }
                            case "hand_punch_1": 
                            case "run": 
                            case "hand_swipe_1_1": 
                            case "hand_swipe_1_2": 
                            case "throw_snowball_1": 
                            case "throw_snowball_2": 
                            case "throw_snowball_3": 
                            case "leap_in_air": {
                                if (this.isEnraged()) {
                                    Minecraft.getInstance().level.addParticle((ParticleOptions)BossesRiseParticleTypes.SNOW_CLOUD.get(), position.x, position.y, position.z, 0.0, 0.0, 0.0);
                                    Minecraft.getInstance().level.addParticle((ParticleOptions)BossesRiseParticleTypes.FALLING_DUST.get(), position.x + 0.25, position.y + 0.25, position.z + 0.25, 0.0, 0.0, 0.0);
                                    break;
                                }
                                Minecraft.getInstance().level.addParticle((ParticleOptions)BossesRiseParticleTypes.SNOW_CLOUD.get(), position.x, position.y, position.z, 0.0, 0.0, 0.0);
                                break;
                            }
                            case "ice_chunks": {
                                Minecraft.getInstance().level.addParticle((ParticleOptions)BossesRiseParticleTypes.YETI_ICE_CHUNK.get(), position.x, position.y, position.z, 0.0, 0.0, 0.0);
                                break;
                            }
                            case "get_hit": {
                                Minecraft.getInstance().level.addParticle((ParticleOptions)BossesRiseParticleTypes.SNOW_FLAKE.get(), position.x, position.y, position.z, 0.0, 0.0, 0.0);
                                break;
                            }
                            case "leap_start_1": {
                                Minecraft.getInstance().level.addParticle((ParticleOptions)BossesRiseParticleTypes.YETI_SMOKE_IMPACT_2.get(), position.x, position.y, position.z, 0.0, 0.0, 0.0);
                                break;
                            }
                            default: {
                                BossesRise.LOGGER.debug("Unknown Particle in Yeti: {}", (Object)data.getEffect());
                            }
                        }
                    }
                });
            }
        };
        controller.setParticleKeyframeHandler(particleHandler);
        AnimationController.SoundKeyframeHandler soundHandler = event -> {
            SoundKeyframeData data = event.getKeyframeData();
            Level patt0$temp = ((YetiEntity)event.getAnimatable()).level();
            if (patt0$temp instanceof ClientLevel) {
                ClientLevel clientLevel = (ClientLevel)patt0$temp;
            }
        };
        controller.setSoundKeyframeHandler(soundHandler);
        controllers.add(controller);
    }

    @Override
    public Map<String, AbstractBossEntity.State> getStates() {
        return STATES;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_HAS_USED_ULTIMATE, 0);
        builder.define(DATA_IS_ENRAGED, 0);
        builder.define(DATA_GROUNDSMASH_ANIMTIME, 0);
        builder.define(DATA_HIT_TICK, -1L);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, (Goal)new YetiChaseGoal(this, 1.2));
        this.goalSelector.addGoal(2, (Goal)new YetiLookAroundGoal(this));
        this.targetSelector.addGoal(1, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]));
        this.targetSelector.addGoal(2, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, false, false));
    }

    public boolean isEnraged() {
        return (Integer)this.entityData.get(DATA_IS_ENRAGED) == 5;
    }

    public boolean isDead() {
        return (Integer)this.entityData.get(DATA_IS_ENRAGED) == 6;
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
        YetiState state = YetiState.byState(this.getState());
        if (state != null && state.stopsMovement && !this.isTimerDone()) {
            return;
        }
        this.setYBodyRot(this.yBodyRotO);
        this.setYRot(this.yRotO);
        this.setYHeadRot(this.yHeadRotO);
    }

    public float getSpeed() {
        YetiState state = YetiState.byState(this.getState());
        if (state != null && state.stopsMovement && !this.isTimerDone()) {
            return 0.0f;
        }
        return super.getSpeed();
    }

    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound((SoundEvent)BossesRiseSounds.YETI_FOOTSTEP.value(), 0.15f, 1.0f);
    }

    public SoundEvent getHurtSound(DamageSource ds) {
        return (SoundEvent)BossesRiseSounds.YETI_HIT.value();
    }

    public boolean canCollideWith(Entity entity) {
        IceSpikeEntity spike;
        if (entity instanceof IceSpikeEntity && (spike = (IceSpikeEntity)entity).getOwner() == this) {
            return false;
        }
        return super.canCollideWith(entity);
    }

    @Override
    public void tick() {
        super.tick();
        this.setInvisible(false);
        Level level = this.level();
        if (!(level instanceof ServerLevel)) {
            return;
        }
        ServerLevel serverLevel = (ServerLevel)level;
        int animTime = (Integer)this.entityData.get(DATA_HIT_ANIMTIME);
        if (animTime > 0) {
            this.entityData.set(DATA_HIT_ANIMTIME, (animTime - 1));
            if (animTime - 1 <= 0) {
                this.entityData.set(DATA_HIT_TICK, -1L);
            }
        }
        if (this.getTicksFrozen() > 0) {
            this.setTicksFrozen(0);
        }
        int timer = this.getTimer();
        int frame = timer - 2;
        YetiState state = YetiState.byState(this.getState());
        Path path = this.navigation.getPath();
        if (path != null && !path.canReach() && this.getTarget() != null && state != YetiState.LEAP_SMASH) {
            this.doAttack(YetiState.LEAP_SMASH.attackOrDefault());
            this.navigation.stop();
            return;
        }
        YetiState yetiState = state;
        switch (yetiState) {
            case INTRO: {
                if (timer == 1) {
                    this.playSound((SoundEvent)BossesRiseSounds.YETI_SPAWN.value());
                    this.level().playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)BossesRiseSounds.YETI_INTRO.value(), SoundSource.MUSIC, 8.0f, 1.0f);
                    this.forEachNearbyPlayer(32.0, player -> {
                        PlayerVariables playerVars = net.neoforged.neoforge.attachment.AttachmentHolder.getData(player, BossesRiseDataAttachments.PLAYER_VARIABLES);
                        playerVars.boss_no_hit = true;
                        playerVars.syncPlayerVariables((Entity)player);
                    });
                    this.setBarVisible(true);
                    break;
                }
                if (frame == 31) {
                    BREAK_1.forEach(vec3i -> {
                        Vec3i offset = switch (this.getDirection()) {
                            case Direction.EAST -> new Vec3i(-vec3i.getZ(), vec3i.getY(), vec3i.getX());
                            case Direction.SOUTH -> new Vec3i(-vec3i.getX(), vec3i.getY(), -vec3i.getZ());
                            case Direction.WEST -> new Vec3i(vec3i.getZ(), vec3i.getY(), -vec3i.getX());
                            default -> vec3i;
                        };
                        serverLevel.setBlock(this.blockPosition().offset(offset), Blocks.AIR.defaultBlockState(), 3);
                    });
                    break;
                }
                if (frame == 55) {
                    BREAK_2.forEach(vec3i -> {
                        Vec3i offset = switch (this.getDirection()) {
                            case Direction.EAST -> new Vec3i(-vec3i.getZ(), vec3i.getY(), vec3i.getX());
                            case Direction.SOUTH -> new Vec3i(-vec3i.getX(), vec3i.getY(), -vec3i.getZ());
                            case Direction.WEST -> new Vec3i(vec3i.getZ(), vec3i.getY(), -vec3i.getX());
                            default -> vec3i;
                        };
                        serverLevel.setBlock(this.blockPosition().offset(offset), Blocks.AIR.defaultBlockState(), 3);
                    });
                    break;
                }
                if (frame == 75) {
                    BREAK_3.forEach(vec3i -> {
                        Vec3i offset = switch (this.getDirection()) {
                            case Direction.EAST -> new Vec3i(-vec3i.getZ(), vec3i.getY(), vec3i.getX());
                            case Direction.SOUTH -> new Vec3i(-vec3i.getX(), vec3i.getY(), -vec3i.getZ());
                            case Direction.WEST -> new Vec3i(vec3i.getZ(), vec3i.getY(), -vec3i.getX());
                            default -> vec3i;
                        };
                        serverLevel.setBlock(this.blockPosition().offset(offset), Blocks.AIR.defaultBlockState(), 3);
                    });
                    break;
                }
                if (frame == 90) {
                    BREAK_4.forEach(vec3i -> {
                        Vec3i offset = switch (this.getDirection()) {
                            case Direction.EAST -> new Vec3i(-vec3i.getZ(), vec3i.getY(), vec3i.getX());
                            case Direction.SOUTH -> new Vec3i(-vec3i.getX(), vec3i.getY(), -vec3i.getZ());
                            case Direction.WEST -> new Vec3i(vec3i.getZ(), vec3i.getY(), -vec3i.getX());
                            default -> vec3i;
                        };
                        serverLevel.setBlock(this.blockPosition().offset(offset), Blocks.AIR.defaultBlockState(), 3);
                    });
                    break;
                }
                if (frame == 124) {
                    for (int x = -4; x <= 4; ++x) {
                        for (int y = 0; y <= 6; ++y) {
                            for (int z = -4; z <= 4; ++z) {
                                BlockPos pos = this.blockPosition().offset(x, y, z);
                                if (!serverLevel.getBlockState(pos).is(BlockTags.ICE)) continue;
                                serverLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                            }
                        }
                    }
                    break;
                }
                if (frame == 144) {
                    this.push(this.getViewVector((float)1.0f).x * 0.75, 0.63, this.getViewVector((float)1.0f).z * 0.75);
                    break;
                }
                if (frame != 190) break;
                this.getEntityData().set(DATA_IS_ENRAGED, 2);
                break;
            }
            case SWIPE_1: {
                if (frame == 21) {
                    this.forEachNearbyEntity(8.0, this.getLookOffset(2.5, 2.0), entity -> {
                        entity.setTicksFrozen(160);
                        entity.invulnerableTime = 0;
                        this.attackEntity((Entity)entity, 0.8f);
                        double distance = this.distanceTo((Entity)entity);
                        entity.setDeltaMovement(new Vec3(entity.getDeltaMovement().x() + (distance == 0.0 ? 0.0 : ((entity.getX() - this.getX()) / distance + this.getLookAngle().z * 2.5) * 1.5), entity.getDeltaMovement().y(), entity.getDeltaMovement().z() + (distance == 0.0 ? 0.0 : ((entity.getZ() - this.getZ()) / distance + this.getLookAngle().x * -2.5) * 1.5)));
                    });
                }
                if (frame > 31 || frame < 24) break;
                this.forEachNearbyEntity(6.0, this.getLookOffset(-2.5, 2.0), entity -> {
                    double distance = this.distanceTo((Entity)entity);
                    entity.setDeltaMovement(new Vec3(entity.getDeltaMovement().x() + (distance == 0.0 ? 0.0 : (entity.getX() - this.getX()) / distance * 0.5), entity.getDeltaMovement().y(), entity.getDeltaMovement().z() + (distance == 0.0 ? 0.0 : (entity.getZ() - this.getZ()) / distance * 0.5)));
                });
                break;
            }
            case SWIPE_2: {
                if (frame == 28) {
                    this.forEachNearbyEntity(8.0, this.getLookOffset(2.5, 2.0), entity -> {
                        entity.setTicksFrozen(160);
                        entity.invulnerableTime = 0;
                        this.attackEntity((Entity)entity, 0.8f);
                        double distance = this.distanceTo((Entity)entity);
                        entity.setDeltaMovement(new Vec3(entity.getDeltaMovement().x() + (distance == 0.0 ? 0.0 : ((entity.getX() - this.getX()) / distance + this.getLookAngle().z * -2.5) * 1.5), entity.getDeltaMovement().y(), entity.getDeltaMovement().z() + (distance == 0.0 ? 0.0 : ((entity.getZ() - this.getZ()) / distance + this.getLookAngle().x * 2.5) * 1.5)));
                    });
                }
                if (frame != 25) break;
                this.forEachNearbyEntity(6.0, this.getLookOffset(-2.5, 2.0), entity -> {
                    double distance = this.distanceTo((Entity)entity);
                    entity.setDeltaMovement(new Vec3(entity.getDeltaMovement().x() + (distance == 0.0 ? 0.0 : (entity.getX() - this.getX()) / distance * 1.5), entity.getDeltaMovement().y(), entity.getDeltaMovement().z() + (distance == 0.0 ? 0.0 : (entity.getZ() - this.getZ()) / distance * 1.5)));
                });
                break;
            }
            case PUNCH: {
                if (frame == 18) {
                    this.forEachNearbyEntity(8.0, this.getLookOffset(2.5), entity -> {
                        entity.setTicksFrozen(320);
                        entity.invulnerableTime = 0;
                        this.attackEntity((Entity)entity, 0.8f);
                        double distance = this.distanceTo((Entity)entity);
                        entity.setDeltaMovement(new Vec3(entity.getDeltaMovement().x() + (distance == 0.0 ? 0.0 : ((entity.getX() - this.getX()) / distance + this.getLookAngle().z * -2.5) * 2.0), entity.getDeltaMovement().y(), entity.getDeltaMovement().z() + (distance == 0.0 ? 0.0 : ((entity.getZ() - this.getZ()) / distance + this.getLookAngle().x * 2.5) * 2.0)));
                    });
                }
                if (frame == 20) {
                    for (int index8 = 0; index8 < 26; ++index8) {
                        this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.SNOW_CLOUD.get(), this.getX() + this.getLookAngle().x * 4.0, this.getY() + 0.1, this.getZ() + this.getLookAngle().z * 4.0, (Math.random() - 0.5) * 2.0, Math.random() * 0.3, (Math.random() - 0.5) * 2.0);
                        this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.FALLING_DUST.get(), this.getX() + this.getLookAngle().x * 4.0, this.getY() + 0.1, this.getZ() + this.getLookAngle().z * 4.0, (Math.random() - 0.5) * 2.5, Math.random() * 0.4, (Math.random() - 0.5) * 2.5);
                        if (!(Math.random() < 0.3)) continue;
                        this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.SNOW_FLAKE.get(), this.getX() + this.getLookAngle().x * 4.0, this.getY() + 0.1, this.getZ() + this.getLookAngle().z * 4.0, (Math.random() - 0.5) * 1.5, Math.random() * 0.3, (Math.random() - 0.5) * 1.5);
                    }
                }
                if (frame >= 20) break;
                double nb = 0.0;
                double nb2 = 2.0 + (double)(18 - frame) * 0.1;
                int index9 = 0;
                while ((long)index9 < Math.round(18.0 * nb2)) {
                    Level level2;
                    double new_x = this.getLookAngle().x * 4.0 + Math.cos(nb) * nb2 * 2.0;
                    double new_z = this.getLookAngle().z * 4.0 + Math.sin(nb) * nb2 * 2.0;
                    ItemStack stack = new ItemStack((ItemLike)this.level().getBlockState(BlockPos.containing((double)Math.floor(this.getX() + new_x), (double)(this.getY() - 1.0), (double)Math.floor(this.getZ() + new_z))).getBlock());
                    if (stack.getItem() != ItemStack.EMPTY.getItem() && (level2 = this.level()) instanceof ServerLevel) {
                        ServerLevel _level = (ServerLevel)level2;
                        _level.sendParticles((ParticleOptions)new ItemParticleOption(ParticleTypes.ITEM, stack), this.getX() + new_x, this.getY() + 0.1, this.getZ() + new_z, 3, 0.0, 0.0, 0.0, 0.05);
                    }
                    nb += nb2;
                    ++index9;
                }
                break;
            }
            case GROUND_SMASH: {
                if (frame == 19) {
                    for (int index10 = 0; index10 < 26; ++index10) {
                        this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.SNOW_CLOUD.get(), this.getX() + this.getLookAngle().x * 4.0, this.getY() + 0.1, this.getZ() + this.getLookAngle().z * 4.0, (Math.random() - 0.5) * 2.0, Math.random() * 0.3, (Math.random() - 0.5) * 2.0);
                        this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.FALLING_DUST.get(), this.getX() + this.getLookAngle().x * 4.0, this.getY() + 0.1, this.getZ() + this.getLookAngle().z * 4.0, (Math.random() - 0.5) * 2.5, Math.random() * 0.4, (Math.random() - 0.5) * 2.5);
                        if (!(Math.random() < 0.3)) continue;
                        this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.SNOW_FLAKE.get(), this.getX() + this.getLookAngle().x * 4.0, this.getY() + 0.1, this.getZ() + this.getLookAngle().z * 4.0, (Math.random() - 0.5) * 1.5, Math.random() * 0.3, (Math.random() - 0.5) * 1.5);
                    }
                    this.forEachNearbyEntity(8.0, this.getLookOffset(3.0), entity -> {
                        entity.setTicksFrozen(320);
                        entity.invulnerableTime = 0;
                        this.attackEntity((Entity)entity, 1.2f);
                        double distance = this.distanceTo((Entity)entity);
                        entity.setDeltaMovement(new Vec3(entity.getDeltaMovement().x() + (distance == 0.0 ? 0.0 : ((entity.getX() - this.getX()) / distance + this.getLookAngle().z * -2.5) * 3.5), entity.getDeltaMovement().y() + 0.8, entity.getDeltaMovement().z() + (distance == 0.0 ? 0.0 : ((entity.getZ() - this.getZ()) / distance + this.getLookAngle().x * 2.5) * 3.5)));
                    });
                    GauntletAttachment.iceWave((LivingEntity)this, serverLevel, 20.0f, 60, this.position(), this.getViewVector(1.0f), (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.7f, 1.5f);
                }
                if (frame >= 20) break;
                double nb = 0.0;
                double nb2 = 3.0 + (double)(18 - frame) * 0.1;
                int index11 = 0;
                while ((long)index11 < Math.round(18.0 * nb2)) {
                    Level level3;
                    double new_x = this.getLookAngle().x * 4.0 + Math.cos(nb) * nb2 * 2.0;
                    double new_z = this.getLookAngle().z * 4.0 + Math.sin(nb) * nb2 * 2.0;
                    ItemStack stack = new ItemStack((ItemLike)this.level().getBlockState(BlockPos.containing((double)Math.floor(this.getX() + new_x), (double)(this.getY() - 1.0), (double)Math.floor(this.getZ() + new_z))).getBlock());
                    if (stack.getItem() != ItemStack.EMPTY.getItem() && (level3 = this.level()) instanceof ServerLevel) {
                        ServerLevel _level = (ServerLevel)level3;
                        _level.sendParticles((ParticleOptions)new ItemParticleOption(ParticleTypes.ITEM, stack), this.getX() + new_x, this.getY() + 0.1, this.getZ() + new_z, 3, 0.0, 0.0, 0.0, 0.05);
                    }
                    nb += nb2;
                    ++index11;
                }
                break;
            }
            case THROW_SPIKE: {
                LivingEntity target = this.getTarget();
                if (frame < 25) {
                    this.lookAtTarget();
                    break;
                }
                if (frame != 25 || target == null) break;
                if (this.isEnraged()) {
                    float xRot = this.getXRot() * ((float)Math.PI / 180);
                    for (int i = -1; i <= 1; ++i) {
                        float yRot = -Mth.wrapDegrees((float)(this.getYRot() + (float)(i * 20))) * ((float)Math.PI / 180);
                        float cosY = Mth.cos((float)yRot);
                        float sinY = Mth.sin((float)yRot);
                        float cosX = Mth.cos((float)xRot);
                        float sinX = Mth.sin((float)xRot);
                        Vec3 viewVector = new Vec3((double)(sinY * cosX), (double)(-sinX), (double)(cosY * cosX));
                        IceSpikeProjectileEntity spike = new IceSpikeProjectileEntity((EntityType<? extends IceSpikeProjectileEntity>)((EntityType)BossesRiseEntities.ICE_SPIKE_PR.get()), (LivingEntity)this, (Level)serverLevel);
                        spike.setPos(this.getX(), this.getEyeY() - (double)0.1f, this.getZ());
                        spike.shoot(viewVector.x, viewVector.y, viewVector.z, 4.0f, 0.0f);
                        spike.setSilent(true);
                        spike.setOwner((Entity)this);
                        spike.setBaseDamage((int)(this.getAttributeValue(Attributes.ATTACK_DAMAGE) * (double)0.3f));
                        spike.setKnockback(8);
                        serverLevel.addFreshEntity((Entity)spike);
                    }
                    this.level().playSound(null, this.blockPosition(), (SoundEvent)BossesRiseSounds.THROW_ICICLE.value(), SoundSource.PLAYERS, 0.9f, (float)Mth.nextDouble((RandomSource)this.random, (double)1.15, (double)1.75));
                    break;
                }
                Vec3 viewVector = this.getViewVector(1.0f);
                IceSpikeProjectileEntity spike = new IceSpikeProjectileEntity((EntityType<? extends IceSpikeProjectileEntity>)((EntityType)BossesRiseEntities.ICE_SPIKE_PR.get()), (LivingEntity)this, (Level)serverLevel);
                spike.setPos(this.getX(), this.getEyeY() - (double)0.1f, this.getZ());
                spike.shoot(viewVector.x, viewVector.y, viewVector.z, 4.0f, 0.0f);
                spike.setSilent(true);
                spike.setOwner((Entity)this);
                spike.setBaseDamage((int)(this.getAttributeValue(Attributes.ATTACK_DAMAGE) * (double)0.2f));
                spike.setKnockback(8);
                serverLevel.addFreshEntity((Entity)spike);
                this.level().playSound(null, this.blockPosition(), (SoundEvent)BossesRiseSounds.THROW_ICICLE.value(), SoundSource.PLAYERS, 0.9f, (float)Mth.nextDouble((RandomSource)this.random, (double)1.15, (double)1.75));
                break;
            }
            case RANGED_CONTROL: {
                LivingEntity target = this.getTarget();
                int hit = 20;
                if (timer == 5 + hit && target instanceof ServerPlayer) {
                    ServerPlayer player2 = (ServerPlayer)target;
                    GlacialShoveEntity glacialShove = new GlacialShoveEntity((EntityType)BossesRiseEntities.GLACIAL_SHOVE.get(), this.level());
                    glacialShove.moveTo(target.position());
                    glacialShove.setTarget((LivingEntity)player2);
                    glacialShove.setOwnerUUID(this.getUUID());
                    glacialShove.setDelay(31 - hit);
                    this.level().addFreshEntity((Entity)glacialShove);
                }
                if (timer > 31) break;
                this.lookAtTarget();
                break;
            }
            case ENRAGED: {
                if (timer > YetiState.ENRAGED.state.duration()) break;
                this.entityData.set(DATA_GROUNDSMASH_ANIMTIME, ((Integer)this.entityData.get(DATA_GROUNDSMASH_ANIMTIME) + 1));
                if (timer >= 180) {
                    this.entityData.set(DATA_IS_ENRAGED, 5);
                }
                return;
            }
            case ICE_BARRAGE: {
                LivingEntity target = this.getTarget();
                int delay = 10;
                if (timer > YetiState.ICE_BARRAGE.state.duration()) break;
                if ((Integer)this.entityData.get(DATA_HAS_USED_ULTIMATE) > 1 || timer > 20) {
                    serverLevel.getEntities((Entity)this, this.getBoundingBox().inflate(3.0, 5.0, 3.0), entity1 -> entity1.canFreeze() && !(entity1 instanceof YetiEntity)).forEach(entity -> {
                        if (entity instanceof LivingEntity) {
                            LivingEntity living = (LivingEntity)entity;
                            this.attackEntity((Entity)entity, 0.225f, this.damageSources().freeze());
                            living.setTicksFrozen(200);
                        }
                    });
                }
                if (timer == 1) {
                    this.playSound((SoundEvent)BossesRiseSounds.ICE_BARRAGE_WHIRLPOOL.value());
                    serverLevel.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.ICE_WHIRLPOOL_1.get()), this.getX(), this.getY() + 0.1, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
                    serverLevel.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.ICE_WHIRLPOOL_2.get()), this.getX(), this.getY() + 0.1, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
                    serverLevel.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.ICE_WHIRLPOOL_3.get()), this.getX(), this.getY() + 0.1, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
                    serverLevel.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.ICE_WHIRLPOOL_4.get()), this.getX(), this.getY() + 0.1, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
                    serverLevel.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.ICE_WHIRLPOOL_5.get()), this.getX(), this.getY() + 0.1, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
                }
                if (target == null) break;
                if (frame == 43) {
                    GauntletAttachment.delayedBurst((LivingEntity)this, target.position(), serverLevel, 1.0f, 10, (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.2f, delay);
                    break;
                }
                if (frame == 74) {
                    GauntletAttachment.delayedBurst((LivingEntity)this, target.position(), serverLevel, 1.0f, 20, (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.4f, delay);
                    break;
                }
                if (frame != 109) break;
                GlacialShoveEntity glacialShove = new GlacialShoveEntity((EntityType)BossesRiseEntities.GLACIAL_SHOVE.get(), this.level());
                glacialShove.moveTo(target.position());
                glacialShove.setTarget(target);
                glacialShove.setDelay(delay);
                glacialShove.setOwnerUUID(this.getUUID());
                glacialShove.shouldSetAttack = false;
                this.level().addFreshEntity((Entity)glacialShove);
                if ((Integer)this.entityData.get(DATA_HAS_USED_ULTIMATE) >= 3) break;
                this.entityData.set(DATA_HAS_USED_ULTIMATE, ((Integer)this.entityData.get(DATA_HAS_USED_ULTIMATE) + 1));
                this.doAttack(YetiState.ICE_BARRAGE.getAttack());
                break;
            }
            case LEAP_SMASH: {
                LivingEntity target = this.getTarget();
                this.entityData.set(AbstractBossEntity.DATA_ATTACK_ANIMTIME, 0);
                timer = YetiState.LEAP_SMASH.state.duration() - timer;
                if (!this.isAlive()) break;
                double x = this.getX();
                double y = this.getY();
                double z = this.getZ();
                if (timer == 68) {
                    this.playSound((SoundEvent)BossesRiseSounds.YETI_JUMP.value());
                }
                if (timer == 60) {
                    this.push(0.0, this.isInWaterOrBubble() ? 3.8 : 1.8, 0.0);
                    this.triggerAnim("main_controller", "leap_air");
                }
                if (timer < 80 && timer > 26 && target != null) {
                    this.push((target.getX() - this.getX()) * 0.05, 0.0, (target.getZ() - this.getZ()) * 0.05);
                    for (int sx = -2; sx <= 2; ++sx) {
                        for (int sy = 3; sy <= 6; ++sy) {
                            for (int sz = -2; sz <= 2; ++sz) {
                                if (!serverLevel.getLevelData().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || !(serverLevel.getBlockState(BlockPos.containing((double)(Math.floor(x) + (double)sx), (double)(Math.floor(y) + (double)sy), (double)(Math.floor(z) + (double)sz))).getDestroySpeed((BlockGetter)serverLevel, BlockPos.containing((double)(Math.floor(x) + (double)sx), (double)(Math.floor(y) + (double)sy), (double)(Math.floor(z) + (double)sz))) > 0.0f) || !(serverLevel.getBlockState(BlockPos.containing((double)(Math.floor(x) + (double)sx), (double)(Math.floor(y) + (double)sy), (double)(Math.floor(z) + (double)sz))).getDestroySpeed((BlockGetter)serverLevel, BlockPos.containing((double)(Math.floor(x) + (double)sx), (double)(Math.floor(y) + (double)sy), (double)(Math.floor(z) + (double)sz))) < 50.0f)) continue;
                                BlockPos _pos = BlockPos.containing((double)(Math.floor(x) + (double)sx), (double)(Math.floor(y) + (double)sy), (double)(Math.floor(z) + (double)sz));
                                Block.dropResources((BlockState)serverLevel.getBlockState(_pos), (LevelAccessor)serverLevel, (BlockPos)BlockPos.containing((double)(Math.floor(x) + (double)sx), (double)(Math.floor(y) + (double)sy), (double)(Math.floor(z) + (double)sz)), null);
                                serverLevel.destroyBlock(_pos, false);
                            }
                        }
                    }
                }
                if (timer == 23) {
                    this.playSound((SoundEvent)BossesRiseSounds.YETI_LAND.value());
                    this.triggerAnim("main_controller", "leap_smash");
                    this.push(0.0, -1.0, 0.0);
                }
                if (timer == 20) {
                    this.forEachNearbyEntity(4.0, new Vec3(0.0, 2.0, 0.0), entity -> {
                        entity.setTicksFrozen(320);
                        this.attackEntity((Entity)entity, 1.0f);
                        double distance = this.distanceTo((Entity)entity);
                        entity.setDeltaMovement(new Vec3(entity.getDeltaMovement().x() + (distance == 0.0 ? 0.0 : ((entity.getX() - this.getX()) / distance + this.getLookAngle().z * -2.5) * 1.5), entity.getDeltaMovement().y(), entity.getDeltaMovement().z() + (distance == 0.0 ? 0.0 : ((entity.getZ() - this.getZ()) / distance + this.getLookAngle().x * 2.5) * 1.5)));
                    });
                }
                if (timer == 20) {
                    GauntletAttachment.iceBurst((LivingEntity)this, this.position(), serverLevel, 8.0f, 40, (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.7f, (random, integer) -> random.nextInt(20) == 0);
                    for (int index5 = 0; index5 < 26; ++index5) {
                        serverLevel.addParticle((ParticleOptions)BossesRiseParticleTypes.SNOW_CLOUD.get(), x + this.getLookAngle().x * 4.0, y + 0.1, z + this.getLookAngle().z * 4.0, (Math.random() - 0.5) * 2.0, Math.random() * 0.3, (Math.random() - 0.5) * 2.0);
                        serverLevel.addParticle((ParticleOptions)BossesRiseParticleTypes.FALLING_DUST.get(), x + this.getLookAngle().x * 4.0, y + 0.1, z + this.getLookAngle().z * 4.0, (Math.random() - 0.5) * 2.5, Math.random() * 0.4, (Math.random() - 0.5) * 2.5);
                        if (!(Math.random() < 0.3)) continue;
                        serverLevel.addParticle((ParticleOptions)BossesRiseParticleTypes.SNOW_FLAKE.get(), x + this.getLookAngle().x * 4.0, y + 0.1, z + this.getLookAngle().z * 4.0, (Math.random() - 0.5) * 1.5, Math.random() * 0.3, (Math.random() - 0.5) * 1.5);
                    }
                }
                if (timer >= 20) break;
                double nb = 0.0;
                double nb2 = (double)(20 - timer) * 0.1;
                int index6 = 0;
                while ((long)index6 < Math.round(18.0 * nb2)) {
                    double new_x = this.getLookAngle().x * 4.0 + Math.cos(nb) * nb2 * 2.5;
                    double new_z = this.getLookAngle().z * 4.0 + Math.sin(nb) * nb2 * 2.5;
                    ItemStack stack = new ItemStack((ItemLike)serverLevel.getBlockState(BlockPos.containing((double)Math.floor(x + new_x), (double)(y - 1.0), (double)Math.floor(z + new_z))).getBlock());
                    if (stack.getItem() != ItemStack.EMPTY.getItem()) {
                        serverLevel.sendParticles((ParticleOptions)new ItemParticleOption(ParticleTypes.ITEM, stack), x + new_x, this.getY() + 0.1, z + new_z, 3, 0.0, 0.0, 0.0, 0.05);
                    }
                    nb += nb2;
                    ++index6;
                }
                break;
            }
            case DEATH: {
                if (timer != YetiState.DEATH.state.duration()) break;
                serverLevel.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.YETI_DEATH_SOLID.get()), this.getX(), this.getY() + 0.1, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
                this.getEntityData().set(DATA_IS_ENRAGED, 6);
                this.setNoAi(true);
                this.setBarVisible(false);
                break;
            }
        }
        this.entityData.set(DATA_GROUNDSMASH_ANIMTIME, 0);
    }

    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    public Vec3 getDeltaMovement() {
        if ((Integer)this.entityData.get(DATA_IS_ENRAGED) == 0) {
            return Vec3.ZERO;
        }
        return super.getDeltaMovement();
    }

    public boolean hurt(DamageSource source, float amount) {
        Level level;
        int enrageFlag = (Integer)this.entityData.get(DATA_IS_ENRAGED);
        if (enrageFlag == 6 && (level = this.level()) instanceof ServerLevel) {
            ServerLevel level2 = (ServerLevel)level;
            for (int i = 0; i < 8; ++i) {
                this.level().playSound(null, this.blockPosition(), (SoundEvent)BossesRiseSounds.ICICLE_BREAK.value(), SoundSource.PLAYERS, 0.9f, (float)Mth.nextDouble((RandomSource)this.random, (double)0.15, (double)0.35));
                level2.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.YETI_ICE_CHUNK.get()), this.getX() + this.random.nextDouble() - this.random.nextDouble(), this.getEyeY() + this.random.nextDouble() - this.random.nextDouble(), this.getZ() + this.random.nextDouble() - this.random.nextDouble(), 1, 0.0, 0.0, 0.0, 0.0);
                level2.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.YETI_ICE_CHUNK.get()), this.getX() + this.random.nextDouble() - this.random.nextDouble(), this.getY() + 1.0 + this.random.nextDouble() - this.random.nextDouble(), this.getZ() + this.random.nextDouble() - this.random.nextDouble(), 1, 0.0, 0.0, 0.0, 0.0);
            }
            this.discard();
            return true;
        }
        if (enrageFlag == 0 && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        }
        if (enrageFlag == 1 && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        }
        if (enrageFlag == 3 || enrageFlag == 4 || source.is(DamageTypes.FALL) || source.is(DamageTypes.DROWN) || source.is(DamageTypes.FALLING_ANVIL) || source.is(DamageTypes.WITHER) || source.is(DamageTypes.WITHER_SKULL) || (Integer)this.entityData.get(DATA_SPAWN_ANIMTIME) > 0 || (Integer)this.entityData.get(DATA_HIT_ANIMTIME) > 13 || (Integer)this.entityData.get(DATA_GROUNDSMASH_ANIMTIME) > 0 && (Integer)this.entityData.get(DATA_GROUNDSMASH_ANIMTIME) < 119) {
            return false;
        }
        if (source.getDirectEntity() instanceof AbstractArrow) {
            amount *= 0.15f;
        }
        if (source.is(DamageTypes.TRIDENT)) {
            amount *= 0.3f;
        }
        if ((Integer)this.entityData.get(DATA_HIT_ANIMTIME) == 0 && !this.level().isClientSide()) {
            this.entityData.set(DATA_HIT_ANIMTIME, 27);
            this.entityData.set(DATA_HIT_TICK, this.level().getGameTime());
        }
        if ((double)(this.getHealth() - amount) <= 0.0) {
            if (this.getState() == null || !this.getState().name().equals(YetiState.DEATH.toString())) {
                OwnableEntity ownable;
                LivingEntity living;
                this.playSound((SoundEvent)BossesRiseSounds.YETI_DEATH.value());
                this.setState(YetiState.DEATH.toString());
                LivingEntity killer = null;
                Entity entity2 = source.getDirectEntity();
                if (entity2 instanceof LivingEntity) {
                    LivingEntity living2;
                    killer = living2 = (LivingEntity)entity2;
                } else {
                    entity2 = source.getEntity();
                    if (entity2 instanceof LivingEntity) {
                        killer = living = (LivingEntity)entity2;
                    } else {
                        entity2 = source.getEntity();
                        if (entity2 instanceof OwnableEntity && (ownable = (OwnableEntity)entity2).getOwner() != null) {
                            killer = ownable.getOwner();
                        }
                    }
                }
                if (this.deathScore >= 0 && killer != null) {
                    killer.awardKillScore((Entity)this, this.deathScore, source);
                }
                Level currentLevel = this.level();
                if (currentLevel instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.YETI_DEATH_FROST.get()), this.getX(), this.getY() + 0.1, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
                    serverLevel.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.YETI_DEATH_LIGHT.get()), this.getX(), this.getY() + 0.1, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
                    serverLevel.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.YETI_DEATH_SOFT.get()), this.getX(), this.getY() + 0.1, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
                    serverLevel.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.YETI_DEATH_SOLID.get()), this.getX(), this.getY() + 0.1, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
                    serverLevel.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.YETI_DEATH_SHOCKWAVE.get()), this.getX(), this.getY() + 0.1, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
                    Entity sourceEntity = source.getEntity();
                    if (sourceEntity instanceof ServerPlayer) {
                        AdvancementProgress progress;
                        AdvancementProgress progress2;
                        ServerPlayer player = (ServerPlayer)sourceEntity;
                        PlayerAdvancements playerAdvancements = player.getAdvancements();
                        ServerAdvancementManager advancementManager = serverLevel.getServer().getAdvancements();
                        AdvancementHolder killBossUnderMinute = advancementManager.get(BossesRise.prefix("kill_boss_under_minute"));
                        AdvancementHolder bossNoHit = advancementManager.get(this.getNoHitAdvancement());
                        if (killBossUnderMinute != null && (Integer)this.getEntityData().get(DATA_BATTLE_TIME) <= 1200 && !(progress2 = playerAdvancements.getOrStartProgress(killBossUnderMinute)).isDone()) {
                            for (String criteria : progress2.getRemainingCriteria()) {
                                playerAdvancements.award(killBossUnderMinute, criteria);
                            }
                        }
                        PlayerVariables playerVars = net.neoforged.neoforge.attachment.AttachmentHolder.getData(player, BossesRiseDataAttachments.PLAYER_VARIABLES);
                        if (bossNoHit != null && playerVars.boss_no_hit && !(progress = playerAdvancements.getOrStartProgress(bossNoHit)).isDone()) {
                            for (String criteria : progress.getRemainingCriteria()) {
                                playerAdvancements.award(bossNoHit, criteria);
                            }
                        }
                        playerVars.boss_no_hit = true;
                        playerVars.syncPlayerVariables((Entity)player);
                    }
                    this.dropAllDeathLoot(serverLevel, source);
                }
            }
            this.setHealth(0.1f);
            return true;
        }
        if (enrageFlag == 2 && (double)((this.getHealth() - amount) / this.getMaxHealth()) <= 0.5) {
            this.level().getEntities((Entity)this, this.getBoundingBox().inflate(32.0)).forEach(entity -> {
                if (entity instanceof LivingEntity) {
                    LivingEntity living = (LivingEntity)entity;
                    living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, YetiState.ENRAGED.state.duration(), 200, false, false));
                    living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, YetiState.ENRAGED.state.duration(), 200, false, false));
                }
            });
            this.entityData.set(DATA_IS_ENRAGED, 3);
        } else if ((double)(this.getHealth() / this.getMaxHealth()) > 0.2 && (double)((this.getHealth() - amount) / this.getMaxHealth()) <= 0.2 && (Integer)this.entityData.get(DATA_HAS_USED_ULTIMATE) == 0) {
            this.doAttack(YetiState.ICE_BARRAGE.getAttack());
            this.entityData.set(DATA_HAS_USED_ULTIMATE, 1);
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean shouldCancelDeath() {
        return false;
    }

    @Override
    protected ResourceLocation getNoHitAdvancement() {
        return BossesRise.prefix("no_hit_yeti");
    }

    @Override
    protected ResourceLocation getKillAdvancement() {
        return BossesRise.prefix("kill_yeti");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        if (this.getState() == null) {
            this.setState(YetiState.IDLE.name());
        }
        super.addAdditionalSaveData(tag);
        tag.putInt("has_used_ultimate", ((Integer)this.entityData.get(DATA_HAS_USED_ULTIMATE)).intValue());
        tag.putInt("is_enraged", ((Integer)this.entityData.get(DATA_IS_ENRAGED)).intValue());
        tag.putInt("GroundsmashAnimtime", ((Integer)this.entityData.get(DATA_GROUNDSMASH_ANIMTIME)).intValue());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (!tag.contains("BossPhase")) {
            return;
        }
        this.entityData.set(DATA_HAS_USED_ULTIMATE, tag.getInt("has_used_ultimate"));
        this.entityData.set(DATA_IS_ENRAGED, tag.getInt("is_enraged"));
        this.entityData.set(DATA_GROUNDSMASH_ANIMTIME, tag.getInt("GroundsmashAnimtime"));
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData groupData) {
        SpawnGroupData data = super.finalizeSpawn(world, difficulty, reason, groupData);
        this.assignIfPresent(this.getAttribute(Attributes.MAX_HEALTH), (Double)ServerConfiguration.YETI_HEALTH.get());
        this.assignIfPresent(this.getAttribute(Attributes.ARMOR), (Double)ServerConfiguration.YETI_ARMOR.get());
        this.assignIfPresent(this.getAttribute(Attributes.ATTACK_DAMAGE), (Double)ServerConfiguration.YETI_ATK.get());
        this.setHealth((float)((Double)ServerConfiguration.YETI_HEALTH.get()).doubleValue());
        if (reason != MobSpawnType.STRUCTURE) {
            this.getEntityData().set(DATA_IS_ENRAGED, 2);
            this.setBarVisible(true);
        }
        return data;
    }

    private void assignIfPresent(@Nullable AttributeInstance holder, double value) {
        if (holder != null) {
            holder.setBaseValue(value);
        }
    }

    public void baseTick() {
        super.baseTick();
        if (this.getTicksFrozen() > 0) {
            this.setTicksFrozen(0);
        }
        if ((Integer)this.getEntityData().get(DATA_IS_ENRAGED) == 0 || (Integer)this.getEntityData().get(DATA_IS_ENRAGED) == 6) {
            this.setPlayingMusic(false);
            return;
        }
        Supplier<Integer> attackCooldown = () -> (Integer)this.entityData.get(AbstractBossEntity.DATA_ATTACK_COOLDOWN);
        Supplier<Integer> hitAnimtime = () -> (Integer)this.entityData.get(AbstractBossEntity.DATA_HIT_ANIMTIME);
        int dieAnimtime = (Integer)this.entityData.get(AbstractBossEntity.DATA_DIE_ANIMTIME);
        if (dieAnimtime > 0) {
            this.setDeltaMovement(new Vec3(0.0, -1.0, 0.0));
            this.entityData.set(AbstractBossEntity.DATA_ATTACK_ANIMTIME, 0);
            this.entityData.set(AbstractBossEntity.DATA_HIT_ANIMTIME, 0);
            this.entityData.set(AbstractBossEntity.DATA_DIE_ANIMTIME, (--dieAnimtime));
            this.entityData.set(DATA_GROUNDSMASH_ANIMTIME, 0);
            if (dieAnimtime == 1) {
                this.simulatePlayerKill();
            }
            this.setPlayingMusic(false);
            return;
        }
        this.setPlayingMusic(true);
        this.entityData.set(AbstractBossEntity.DATA_BATTLE_TIME, ((Integer)this.entityData.get(AbstractBossEntity.DATA_BATTLE_TIME) + 1));
        if (hitAnimtime.get() == 26) {
            for (int index7 = 0; index7 < 4; ++index7) {
                this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.SNOW_FLAKE.get(), this.getX() + this.getLookAngle().x * 2.0, this.getY() + (double)this.getBbHeight() * 0.5, this.getZ() + this.getLookAngle().z * 2.0, (Math.random() - 0.5) * 2.5, (Math.random() - 0.5) * 1.2, (Math.random() - 0.5) * 2.5);
            }
        }
        if (this.level().isClientSide) {
            return;
        }
        if ((Integer)this.entityData.get(DATA_IS_ENRAGED) == 3 && this.isTimerDone()) {
            this.stopTriggeredAnim("main_controller", null);
            this.setState(YetiState.ENRAGED.toString());
            this.entityData.set(DATA_IS_ENRAGED, 4);
            this.playSound((SoundEvent)BossesRiseSounds.YETI_ENRAGE.value(), 1.0f, 1.0f);
            return;
        }
        if ((Integer)this.entityData.get(DATA_IS_ENRAGED) == 4 && this.getState() != YetiState.ENRAGED.getState()) {
            this.entityData.set(DATA_IS_ENRAGED, 5);
            return;
        }
        if (this.getTarget() == null) {
            return;
        }
        if (this.targetTooFarTicks >= 50 && this.isTimerDone() && this.getTarget().onGround()) {
            this.doAttack(YetiState.RANGED_CONTROL.attack);
            this.targetTooFarTicks = 0;
            return;
        }
        if (attackCooldown.get() >= 1) {
            AbstractBossEntity.Attack attack = this.attackBuffer == null ? this.chooseAttack() : this.attackBuffer;
            YetiState yetiState = YetiState.byAttack(attack);
            if (yetiState == YetiState.THROW_SPIKE) {
                this.doAttack(attack);
                return;
            }
            if (yetiState == YetiState.ICE_BARRAGE) {
                this.entityData.set(DATA_HAS_USED_ULTIMATE, 1);
            }
            if (!new AABB(this.getX(), this.getY(), this.getZ(), this.getX(), this.getY(), this.getZ()).inflate(5.0).contains(this.getTarget().position())) {
                this.attackBuffer = attack;
                return;
            }
            if (this.attackBuffer != null) {
                this.attackBuffer = null;
            }
            this.doAttack(attack);
            return;
        }
        if (this.isTargetNear(32.0)) {
            if (this.targetTooFarTicks > 0) {
                --this.targetTooFarTicks;
            }
            if (this.isTimerDone()) {
                this.entityData.set(AbstractBossEntity.DATA_ATTACK_COOLDOWN, ((Integer)this.entityData.get(AbstractBossEntity.DATA_ATTACK_COOLDOWN) + 1));
            }
        } else {
            ++this.targetTooFarTicks;
        }
    }

    @Override
    public boolean isTimerDone() {
        return this.getState() == null || super.isTimerDone();
    }

    @Override
    public void setState(String name) {
        if (this.getState() != null) {
            if (YetiState.byState(this.getState()) == YetiState.DEATH) {
                return;
            }
            if (name.equals(this.getState().name())) {
                this.stopTriggeredAnim("main_controller", null);
            }
        }
        super.setState(name);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.5).add(Attributes.MAX_HEALTH, 250.0).add(Attributes.ARMOR, 2.0).add(Attributes.ATTACK_DAMAGE, 18.0).add(Attributes.FOLLOW_RANGE, 64.0).add(Attributes.STEP_HEIGHT, 2.3).add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    @Override
    public AbstractBossEntity.Attack chooseAttack() {
        this.navigation.stop();
        if (this.attackPattern == null || this.attackPatternIndex >= this.attackPattern.length()) {
            this.attackPattern = this.isEnraged() ? ENRAGED_POOL.selectRandom(this) : POOL.selectRandom(this);
            this.attackPatternIndex = 0;
        }
        return this.attackPattern != null ? this.attackPattern.get(this.attackPatternIndex++).attackOrDefault() : YetiState.PUNCH.attackOrDefault();
    }

    @Override
    public void doAttack(@Nullable AbstractBossEntity.Attack attack) {
        if (this.attackBuffer != null) {
            this.attackBuffer = null;
        }
        super.doAttack(attack);
    }

    private static void addState(AbstractBossEntity.State state) {
        STATES.put(state.name(), state);
    }

    static {
        for (YetiState yetiState : YetiState.values()) {
            YetiEntity.addState(yetiState.getState());
        }
        POOL = new YetiAttackPatternPool(new YetiAttackPattern[]{new YetiAttackPattern(yeti -> {
            LivingEntity target = yeti.getTarget();
            return target != null && target.distanceTo((Entity)yeti) < 15.0f ? 22 : 12;
        }, new YetiState[]{YetiState.SWIPE_1}), new YetiAttackPattern(yeti -> {
            LivingEntity target = yeti.getTarget();
            return target != null && target.distanceTo((Entity)yeti) < 15.0f ? 12 : 2;
        }, new YetiState[]{YetiState.SWIPE_1, YetiState.SWIPE_2}), new YetiAttackPattern(yeti -> {
            LivingEntity target = yeti.getTarget();
            return target != null && target.distanceTo((Entity)yeti) < 15.0f ? 10 : 30;
        }, new YetiState[]{YetiState.THROW_SPIKE}), new YetiAttackPattern(yeti -> 10, new YetiState[]{YetiState.GROUND_SMASH}), new YetiAttackPattern(yeti -> 8, new YetiState[]{YetiState.PUNCH}), new YetiAttackPattern(yeti -> 10, new YetiState[]{YetiState.GROUND_SMASH, YetiState.SWIPE_1}), new YetiAttackPattern(yeti -> 8, new YetiState[]{YetiState.SWIPE_1, YetiState.SWIPE_2, YetiState.PUNCH}), new YetiAttackPattern(yeti -> 6, new YetiState[]{YetiState.SWIPE_1, YetiState.THROW_SPIKE}), new YetiAttackPattern(yeti -> 4, new YetiState[]{YetiState.SWIPE_1, YetiState.SWIPE_2, YetiState.THROW_SPIKE}), new YetiAttackPattern(yeti -> 2, new YetiState[]{YetiState.GROUND_SMASH, YetiState.PUNCH, YetiState.PUNCH})});
        ENRAGED_POOL = new YetiAttackPatternPool(new YetiAttackPattern[]{new YetiAttackPattern(yeti -> {
            LivingEntity target = yeti.getTarget();
            return target != null && target.distanceTo((Entity)yeti) < 15.0f ? 14 : 4;
        }, new YetiState[]{YetiState.SWIPE_1, YetiState.SWIPE_2, YetiState.PUNCH, YetiState.GROUND_SMASH}), new YetiAttackPattern(yeti -> 10, new YetiState[]{YetiState.SWIPE_1, YetiState.SWIPE_2, YetiState.THROW_SPIKE}), new YetiAttackPattern(yeti -> {
            LivingEntity target = yeti.getTarget();
            return target != null && target.distanceTo((Entity)yeti) < 15.0f ? 14 : 4;
        }, new YetiState[]{YetiState.LEAP_SMASH, YetiState.SWIPE_1, YetiState.SWIPE_2, YetiState.GROUND_SMASH}), new YetiAttackPattern(yeti -> 10, new YetiState[]{YetiState.LEAP_SMASH, YetiState.GROUND_SMASH}), new YetiAttackPattern(yeti -> 10, new YetiState[]{YetiState.GROUND_SMASH, YetiState.SWIPE_1, YetiState.LEAP_SMASH}), new YetiAttackPattern(yeti -> 10, new YetiState[]{YetiState.THROW_SPIKE, YetiState.LEAP_SMASH}), new YetiAttackPattern(yeti -> 8, new YetiState[]{YetiState.PUNCH, YetiState.SWIPE_1, YetiState.SWIPE_2}), new YetiAttackPattern(yeti -> 7, new YetiState[]{YetiState.SWIPE_1, YetiState.SWIPE_2}), new YetiAttackPattern(yeti -> 6, new YetiState[]{YetiState.GROUND_SMASH}), new YetiAttackPattern(yeti -> {
            LivingEntity target = yeti.getTarget();
            return target != null && target.distanceTo((Entity)yeti) < 15.0f ? 1 : 21;
        }, new YetiState[]{YetiState.THROW_SPIKE}), new YetiAttackPattern(yeti -> (Integer)yeti.entityData.get(DATA_HAS_USED_ULTIMATE) > 0 ? 5 : 0, new YetiState[]{YetiState.ICE_BARRAGE})});
    }

    public static enum YetiState {
        IDLE(30, null, false),
        INTRO(193, INTRO_ANIM, true),
        ENRAGED(200, ENRAGED_ANIM, true),
        DEATH(112, DEATH_ANIM, true),
        SWIPE_1(41, BossesRiseSounds.YETI_HANDSWIPE, SWIPE_1_ANIM, true),
        SWIPE_2(53, BossesRiseSounds.YETI_HANDSWIPE2, SWIPE_2_ANIM, true),
        LEAP_SMASH(70, BossesRiseSounds.YETI_JUMP, LEAP_START, true),
        GROUND_SMASH(42, BossesRiseSounds.YETI_GROUNDSMASH, GROUND_SMASH_ANIM, true),
        THROW_SPIKE(43, BossesRiseSounds.THROW_ICICLE, SPIKE_ANIM, true),
        PUNCH(40, BossesRiseSounds.YETI_PUNCH, PUNCH_ANIM, true),
        RANGED_CONTROL(50, BossesRiseSounds.YETI_RANGED_CONTROL, RANGE_CONTROL_ANIM, true),
        ICE_BARRAGE(180, BossesRiseSounds.YETI_ICE_BARRAGE, ICE_BARRAGE_ANIM, true);

        private final AbstractBossEntity.State state;
        @Nullable
        private final AbstractBossEntity.Attack attack;
        @Nullable
        private final RawAnimation animation;
        public final boolean stopsMovement;

        private YetiState(int duration, RawAnimation animation, boolean stopsMovement) {
            this.state = new AbstractBossEntity.State(this.toString(), duration);
            this.attack = null;
            this.animation = animation;
            this.stopsMovement = stopsMovement;
        }

        private YetiState(int duration, Holder<SoundEvent> attackSound, RawAnimation animation, boolean stopsMovement) {
            this.state = new AbstractBossEntity.State(this.toString(), duration);
            this.attack = new AbstractBossEntity.Attack(this.toString(), attackSound);
            this.animation = animation;
            this.stopsMovement = stopsMovement;
        }

        public AbstractBossEntity.State getState() {
            return this.state;
        }

        public boolean isAttack() {
            return this.attack != null;
        }

        @Nullable
        public AbstractBossEntity.Attack getAttack() {
            return this.attack;
        }

        public AbstractBossEntity.Attack attackOrDefault() {
            return this.attack != null ? this.attack : new AbstractBossEntity.Attack(SWIPE_1.toString(), BossesRiseSounds.YETI_HANDSWIPE);
        }

        public Optional<RawAnimation> getAnimation() {
            return Optional.ofNullable(this.animation);
        }

        @Nullable
        public static YetiState byState(@Nullable AbstractBossEntity.State state) {
            if (state == null) {
                return null;
            }
            return YetiState.valueOf(state.name());
        }

        @Nullable
        public static YetiState byAttack(@Nullable AbstractBossEntity.Attack attack) {
            if (attack == null) {
                return null;
            }
            return YetiState.valueOf(attack.name());
        }
    }

    public record YetiAttackPattern(Function<YetiEntity, Integer> weight, YetiState[] attacks) {
        public int length() {
            return this.attacks.length;
        }

        public YetiState get(int index) {
            return this.attacks[index];
        }
    }

    public record YetiAttackPatternPool(YetiAttackPattern[] attackPatterns) {
        public int totalWeight(YetiEntity yeti) {
            return Arrays.stream(this.attackPatterns).mapToInt(yetiAttackPattern -> yetiAttackPattern.weight().apply(yeti)).sum();
        }

        @Nullable
        public YetiAttackPattern selectRandom(YetiEntity yeti) {
            int randomWeight = (int)(Math.random() * (double)this.totalWeight(yeti));
            int weightSum = 0;
            for (YetiAttackPattern pattern : this.attackPatterns) {
                if (randomWeight >= (weightSum += pattern.weight().apply(yeti).intValue())) continue;
                return pattern;
            }
            return null;
        }
    }

    @EventBusSubscriber
    public static class YetiEvents {
        @SubscribeEvent
        private static void onBreakBlock(BlockEvent.BreakEvent event) {
            Level level = event.getPlayer().level();
            if (level instanceof ServerLevel) {
                ServerLevel level2 = (ServerLevel)level;
                if (event.getState().is(Blocks.ICE)) {
                    for (YetiEntity yeti : level2.getEntitiesOfClass(YetiEntity.class, new AABB(event.getPos()).inflate(10.0))) {
                        if ((Integer)yeti.getEntityData().get(DATA_IS_ENRAGED) != 0) continue;
                        yeti.setState(YetiState.INTRO.toString());
                        yeti.entityData.set(DATA_IS_ENRAGED, 1);
                        event.setCanceled(true);
                        level2.setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 3);
                        return;
                    }
                }
            }
        }
    }
}

