/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animation.AnimationController
 *  software.bernie.geckolib.animation.AnimationController$AnimationStateHandler
 *  software.bernie.geckolib.animation.AnimationController$CustomKeyframeHandler
 *  software.bernie.geckolib.animation.AnimationState
 *  software.bernie.geckolib.animation.RawAnimation
 *  software.bernie.geckolib.animation.keyframe.event.CustomInstructionKeyframeEvent
 *  software.bernie.geckolib.animation.keyframe.event.data.CustomInstructionKeyframeData
 *  software.bernie.geckolib.animation.state.BoneSnapshot
 *  software.bernie.geckolib.cache.object.GeoBone
 *  software.bernie.geckolib.model.GeoModel
 */
package net.unusual.block_factorys_bosses.client.camera.client;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.CinematicEntity;
import net.unusual.block_factorys_bosses.client.camera.BossesRiseClientCinematicCamera;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.keyframe.event.CustomInstructionKeyframeEvent;
import software.bernie.geckolib.animation.keyframe.event.data.CustomInstructionKeyframeData;
import software.bernie.geckolib.animation.state.BoneSnapshot;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

public class CinematicAnimationController<T extends GeoAnimatable>
extends AnimationController<T>
implements AnimationController.CustomKeyframeHandler<T> {
    private AnimationController.CustomKeyframeHandler<T> customKeyframeHandler;
    @Nullable
    private AnimationEndListener<T> animationEndListener;

    public CinematicAnimationController(T animatable, AnimationController.AnimationStateHandler<T> animationHandler) {
        this(animatable, "base_controller", animationHandler);
    }

    public CinematicAnimationController(T animatable, String name, AnimationController.AnimationStateHandler<T> animationHandler) {
        this(animatable, name, 0, animationHandler);
    }

    public CinematicAnimationController(T animatable, int transitionTickTime, AnimationController.AnimationStateHandler<T> animationHandler) {
        this(animatable, "base_controller", transitionTickTime, animationHandler);
    }

    public CinematicAnimationController(T animatable, String name, int transitionTickTime, AnimationController.AnimationStateHandler<T> animationHandler) {
        super(animatable, name, transitionTickTime, animationHandler);
        super.setCustomInstructionKeyframeHandler((AnimationController.CustomKeyframeHandler)this);
        this.setAnimationEndListener((animatable1, animation) -> {});
    }

    public CinematicAnimationController(T animatable, String name, int transitionTickTime, AnimationController.AnimationStateHandler<T> animationHandler, AnimationEndListener<T> animationEndListener) {
        super(animatable, name, transitionTickTime, animationHandler);
        super.setCustomInstructionKeyframeHandler((AnimationController.CustomKeyframeHandler)this);
        this.setAnimationEndListener(animationEndListener);
    }

    public CinematicAnimationController<T> setAnimationEndListener(@Nullable AnimationEndListener<T> listener) {
        this.animationEndListener = listener;
        return this;
    }

    public void process(GeoModel<T> model, AnimationState<T> state, Map<String, GeoBone> bones, Map<String, BoneSnapshot> snapshots, double seekTime, boolean crashWhenCantFindBone) {
        RawAnimation triggeredBefore = this.getTriggeredAnimation();
        super.process(model, state, bones, snapshots, seekTime, crashWhenCantFindBone);
        if (this.animationEndListener != null && triggeredBefore != null && this.getTriggeredAnimation() == null) {
            this.animationEndListener.onAnimationEnd(this.animatable, triggeredBefore);
        }
    }

    protected boolean stopTriggeredAnimation() {
        RawAnimation endingAnim = this.getTriggeredAnimation();
        boolean stopped = super.stopTriggeredAnimation();
        if (stopped && this.animationEndListener != null) {
            this.animationEndListener.onAnimationEnd(this.animatable, endingAnim);
        }
        return stopped;
    }

    public AnimationController<T> setCustomInstructionKeyframeHandler(AnimationController.CustomKeyframeHandler<T> customKeyframeHandler) {
        this.customKeyframeHandler = customKeyframeHandler;
        return this;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void handle(CustomInstructionKeyframeEvent<T> event) {
        CustomInstructionKeyframeData data = event.getKeyframeData();
        if (data == null) {
            return;
        }
        if (!data.getInstructions().startsWith("cinematic_")) {
            if (this.customKeyframeHandler != null) {
                this.customKeyframeHandler.handle(event);
            }
            return;
        }
        String[] splits = data.getInstructions().split(";");
        if (splits.length < 2 || splits[1].isEmpty()) {
            if (splits[0].endsWith("end")) {
                BossesRiseClientCinematicCamera.stopCinematicCamera((CinematicEntity)this.animatable);
            }
            return;
        }
        if (splits[0].endsWith("start")) {
            String cameraID = splits.length > 2 ? splits[2] : null;
            GeoAnimatable geoAnimatable = this.animatable;
            if (geoAnimatable instanceof CinematicEntity) {
                CinematicEntity cinematicEntity = (CinematicEntity)geoAnimatable;
                BossesRiseClientCinematicCamera.startCinematicCamera(splits[1], cinematicEntity, cameraID);
                return;
            }
            return;
        }
        BossesRiseClientCinematicCamera.stopCinematicCamera(splits[1], (CinematicEntity)this.animatable);
    }

    @FunctionalInterface
    public static interface AnimationEndListener<T extends GeoAnimatable> {
        default public void onAnimationEnd(T animatable, @Nullable RawAnimation animation) {
            this.executeOnAnimationEnd(animatable, animation);
            Level level = null;
            if (animatable instanceof Entity) {
                Entity e = (Entity)animatable;
                level = e.level();
            }
            if (animatable instanceof BlockEntity) {
                BlockEntity be = (BlockEntity)animatable;
                level = be.getLevel();
            }
            if (level == null) {
                return;
            }
            if (!level.isClientSide()) {
                return;
            }
            BossesRiseClientCinematicCamera.stopCinematicCamera();
        }

        public void executeOnAnimationEnd(T var1, @Nullable RawAnimation var2);
    }
}

