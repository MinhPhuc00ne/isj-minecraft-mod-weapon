/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.control.BodyRotationControl
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animatable.GeoEntity
 *  software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.animation.PlayState
 *  software.bernie.geckolib.animation.RawAnimation
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package net.unusual.block_factorys_bosses.entity.boss.kraken;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.client.camera.client.CinematicAnimationController;
import net.unusual.block_factorys_bosses.entity.OwnableByAllEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenEntity;
import net.unusual.block_factorys_bosses.entity.control.FollowHeadBodyRotationControl;
import net.unusual.block_factorys_bosses.entity.control.NoLookControl;
import net.unusual.block_factorys_bosses.entity.control.NoMoveControl;
import net.unusual.block_factorys_bosses.geckolib.util.SoundKeyframePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class KrakenCinematicEntity
extends Monster
implements GeoEntity,
OwnableByAllEntity {
    @Nullable
    private KrakenEntity owner;
    @Nullable
    private UUID ownerUUID;
    private final RawAnimation INTRO_ANIM = RawAnimation.begin().thenPlayAndHold("intro_cinematic2_java");
    private final RawAnimation PHASE_TRANSITION_ANIM = RawAnimation.begin().thenPlayAndHold("phase_transition_java");
    private static final int INTRO_ANIM_DURATION = 490;
    private static final int PHASE_TRANSITION_ANIM_DURATION = 290;
    private static final String INTRO_ANIM_TRIGGER_NAME = "intro";
    private static final String PHASE_TRANSITION_ANIM_TRIGGER_NAME = "phase_transition";
    private long despawnAtGameTime = -1L;
    private boolean wasReloaded = false;
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);

    public KrakenCinematicEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
        this.moveControl = new NoMoveControl((Mob)this);
        this.lookControl = new NoLookControl((Mob)this);
    }

    protected BodyRotationControl createBodyControl() {
        return new FollowHeadBodyRotationControl((Mob)this);
    }

    public void setInitialRotation(float yRot) {
        super.setYRot(yRot);
        this.yRotO = yRot;
        this.yBodyRot = yRot;
        this.yBodyRotO = yRot;
        this.yHeadRot = yRot;
        this.yHeadRotO = yRot;
    }

    public void travel(@NotNull Vec3 travelVector) {
    }

    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    public void push(double x, double y, double z) {
    }

    public void knockback(double strength, double x, double z) {
    }

    @Nullable
    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    public void setOwner(@Nullable KrakenEntity entity) {
        this.owner = entity;
        this.ownerUUID = entity == null ? null : entity.getUUID();
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new CinematicAnimationController<KrakenCinematicEntity>(this, "main_controller", 0, state -> PlayState.CONTINUE).setSoundKeyframeHandler(new SoundKeyframePlayer()).triggerableAnim(INTRO_ANIM_TRIGGER_NAME, this.INTRO_ANIM).triggerableAnim(PHASE_TRANSITION_ANIM_TRIGGER_NAME, this.PHASE_TRANSITION_ANIM));
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    public void playAnimation(int currentPhase) {
        String anim = currentPhase == -1 ? INTRO_ANIM_TRIGGER_NAME : PHASE_TRANSITION_ANIM_TRIGGER_NAME;
        long duration = currentPhase == -1 ? 490L : 290L;
        this.despawnAtGameTime = this.level().getGameTime() + duration;
        this.triggerAnim("main_controller", anim);
    }

    public boolean hurt(@NotNull DamageSource source, float amount) {
        return false;
    }

    public boolean animationFinished() {
        if (this.despawnAtGameTime < 0L) {
            return false;
        }
        return this.level().getGameTime() >= this.despawnAtGameTime;
    }

    public void tick() {
        super.tick();
        if (this.wasReloaded) {
            this.discard();
            return;
        }
        if (this.despawnAtGameTime < 0L) {
            return;
        }
        if (this.animationFinished()) {
            this.discard();
        }
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putLong("despawn_at_game_time", this.despawnAtGameTime);
        if (this.ownerUUID != null) {
            compound.putUUID("owner", this.ownerUUID);
        }
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.despawnAtGameTime = compound.getLong("despawn_at_game_time");
        this.wasReloaded = true;
        if (compound.hasUUID("owner")) {
            this.ownerUUID = compound.getUUID("owner");
        }
    }

    public void customServerAiStep() {
    }
}

