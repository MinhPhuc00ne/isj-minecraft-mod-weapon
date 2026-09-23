/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animatable.GeoBlockEntity
 *  software.bernie.geckolib.animatable.SingletonGeoAnimatable
 *  software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.animation.AnimationController
 *  software.bernie.geckolib.animation.AnimationController$CustomKeyframeHandler
 *  software.bernie.geckolib.animation.AnimationController$State
 *  software.bernie.geckolib.animation.AnimationState
 *  software.bernie.geckolib.animation.PlayState
 *  software.bernie.geckolib.animation.RawAnimation
 *  software.bernie.geckolib.animation.keyframe.event.CustomInstructionKeyframeEvent
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package net.unusual.block_factorys_bosses.block.entity;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.unusual.block_factorys_bosses.block.HugeDoorBlock;
import net.unusual.block_factorys_bosses.client.camera.client.CinematicAnimationController;
import net.unusual.block_factorys_bosses.geckolib.util.SoundKeyframePlayer;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.keyframe.event.CustomInstructionKeyframeEvent;
import software.bernie.geckolib.util.GeckoLibUtil;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class HugeDoorBlockEntity
extends BlockEntity
implements GeoBlockEntity,
AnimationController.CustomKeyframeHandler<HugeDoorBlockEntity> {
    private static final RawAnimation OPENING_ANIM = RawAnimation.begin().thenPlayAndHold("animation.bf_bosses.underworld_arena_door.opening");
    private static final RawAnimation OPEN_ANIM = RawAnimation.begin().thenPlayAndHold("animation.bf_bosses.underworld_arena_door.open");
    private static final RawAnimation CLOSING_ANIM = RawAnimation.begin().thenPlayAndHold("animation.bf_bosses.underworld_arena_door.closing");
    private static final RawAnimation CLOSED_ANIM = RawAnimation.begin().thenPlayAndHold("animation.bf_bosses.underworld_arena_door.closed");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private long startedMovingAtGameTime = -1L;
    private long startedUnlockingAtGameTime = -1L;

    public HugeDoorBlockEntity(BlockEntityType<? extends HugeDoorBlockEntity> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        SingletonGeoAnimatable.registerSyncedAnimatable((GeoAnimatable)this);
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        AnimationController controller = new CinematicAnimationController<HugeDoorBlockEntity>(this, "main_controller", this::getAnimationState).setSoundKeyframeHandler(new SoundKeyframePlayer()).setCustomInstructionKeyframeHandler((AnimationController.CustomKeyframeHandler)this);
        controllers.add(controller);
    }

    public void handle(CustomInstructionKeyframeEvent<HugeDoorBlockEntity> event) {
        String instruction;
        switch (instruction = event.getKeyframeData().getInstructions()) {
            case "use_unlocking_texture;": {
                assert (this.getLevel() != null);
                this.startedUnlockingAtGameTime = this.getLevel().getGameTime();
                break;
            }
            case "use_normal_texture;": {
                this.startedUnlockingAtGameTime = -1L;
            }
        }
    }

    private PlayState getAnimationState(AnimationState<HugeDoorBlockEntity> state) {
        BlockState blockState = this.getBlockState();
        Boolean isOpen = (Boolean)blockState.getValue((Property)HugeDoorBlock.OPEN);
        if (!((Boolean)blockState.getValue((Property)HugeDoorBlock.MOVING)).booleanValue()) {
            if (state.getAnimationTick() <= 0.0) {
                return state.setAndContinue(isOpen != false ? OPEN_ANIM : CLOSED_ANIM);
            }
            return PlayState.CONTINUE;
        }
        return state.setAndContinue(isOpen != false ? CLOSING_ANIM : OPENING_ANIM);
    }

    public boolean isPlayingOpeningAnimation() {
        AnimationController controller = (AnimationController)this.getAnimatableInstanceCache().getManagerForId(this.getBlockPos().asLong()).getAnimationControllers().get("main_controller");
        if (controller == null) {
            return false;
        }
        return controller.getAnimationState() == AnimationController.State.RUNNING && controller.getCurrentRawAnimation() == OPENING_ANIM;
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public boolean isFinishedMoving(int duration) {
        assert (this.getLevel() != null);
        return this.getLevel().getGameTime() - this.startedMovingAtGameTime >= (long)duration;
    }

    public boolean isStartedMoving() {
        return this.startedMovingAtGameTime != -1L;
    }

    public void setStartedMoving() {
        assert (this.getLevel() != null);
        this.startedMovingAtGameTime = this.getLevel().getGameTime();
    }

    public int getUnlockingTextureFrame() {
        if (this.startedUnlockingAtGameTime < 0L) {
            return -1;
        }
        assert (this.getLevel() != null);
        return (int)(this.getLevel().getGameTime() - this.startedUnlockingAtGameTime);
    }
}

