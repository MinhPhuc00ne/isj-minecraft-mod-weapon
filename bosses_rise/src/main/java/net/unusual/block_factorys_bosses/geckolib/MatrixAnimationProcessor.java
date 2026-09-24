/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.util.Mth
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animation.AnimatableManager
 *  software.bernie.geckolib.animation.AnimationController
 *  software.bernie.geckolib.animation.AnimationProcessor
 *  software.bernie.geckolib.animation.AnimationState
 *  software.bernie.geckolib.animation.EasingType
 *  software.bernie.geckolib.animation.keyframe.AnimationPoint
 *  software.bernie.geckolib.animation.keyframe.BoneAnimationQueue
 *  software.bernie.geckolib.animation.state.BoneSnapshot
 *  software.bernie.geckolib.cache.object.GeoBone
 *  software.bernie.geckolib.model.GeoModel
 */
package net.unusual.block_factorys_bosses.geckolib;

import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.boss.yeti.YetiEntity;
import net.unusual.block_factorys_bosses.mixins.accessor.AnimatableManagerAccessor;
import net.unusual.block_factorys_bosses.mixins.accessor.AnimationControllerAccessor;
import net.unusual.block_factorys_bosses.mixins.accessor.AnimationProcessorAccessor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationProcessor;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.EasingType;
import software.bernie.geckolib.animation.keyframe.AnimationPoint;
import software.bernie.geckolib.animation.keyframe.BoneAnimationQueue;
import software.bernie.geckolib.animation.state.BoneSnapshot;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MatrixAnimationProcessor<T extends GeoAnimatable>
extends AnimationProcessor<T> {
    public MatrixAnimationProcessor(GeoModel<T> model) {
        super(model);
    }

    public void tickAnimation(T animatable, GeoModel<T> model, AnimatableManager<T> animatableManager, double animTime, AnimationState<T> state, boolean crashWhenCantFindBone) {
        Map boneSnapshots = animatableManager.getBoneSnapshotCollection();
        for (GeoBone bone : this.getRegisteredBones()) {
            if (boneSnapshots.containsKey(bone.getName())) continue;
            boneSnapshots.put(bone.getName(), BoneSnapshot.copy((BoneSnapshot)bone.getInitialSnapshot()));
        }
        for (AnimationController controller : animatableManager.getAnimationControllers().values()) {
            if (this.reloadAnimations) {
                controller.forceAnimationReset();
                controller.getBoneAnimationQueues().clear();
            }
            ((AnimationControllerAccessor)controller).setIsJustStarting(animatableManager.isFirstTick());
            state.withController(controller);
            controller.process(model, state, ((AnimationProcessorAccessor)((Object)this)).getBones(), boneSnapshots, animTime, crashWhenCantFindBone);
            for (Object boneAnimObj : controller.getBoneAnimationQueues().values()) {
                BoneAnimationQueue boneAnimation = (BoneAnimationQueue)boneAnimObj;
                GeoBone bone = boneAnimation.bone();
                BoneSnapshot snapshot = (BoneSnapshot)boneSnapshots.get(bone.getName());
                BoneSnapshot initialSnapshot = bone.getInitialSnapshot();
                Vec3AnimationPoint pointVector = MatrixAnimationProcessor.process(boneAnimation);
                AnimationPoint posXPoint = (AnimationPoint)boneAnimation.positionXQueue().poll();
                AnimationPoint posYPoint = (AnimationPoint)boneAnimation.positionYQueue().poll();
                AnimationPoint posZPoint = (AnimationPoint)boneAnimation.positionZQueue().poll();
                AnimationPoint scaleXPoint = (AnimationPoint)boneAnimation.scaleXQueue().poll();
                AnimationPoint scaleYPoint = (AnimationPoint)boneAnimation.scaleYQueue().poll();
                AnimationPoint scaleZPoint = (AnimationPoint)boneAnimation.scaleZQueue().poll();
                EasingType easingType = (EasingType)((AnimationControllerAccessor)controller).getOverrideEasingTypeFunction().apply(animatable);
                if (pointVector != null) {
                    YetiEntity yeti;
                    if (animatable instanceof YetiEntity && (yeti = (YetiEntity)animatable).isDead()) {
                        bone.setRotX(0.0f + initialSnapshot.getRotX());
                        bone.setRotY(0.0f + initialSnapshot.getRotY());
                        bone.setRotZ(0.0f + initialSnapshot.getRotZ());
                    } else {
                        bone.setRotX((float)EasingType.lerpWithOverride((AnimationPoint)pointVector.x, (EasingType)easingType) + initialSnapshot.getRotX());
                        bone.setRotY((float)EasingType.lerpWithOverride((AnimationPoint)pointVector.y, (EasingType)easingType) + initialSnapshot.getRotY());
                        bone.setRotZ((float)EasingType.lerpWithOverride((AnimationPoint)pointVector.z, (EasingType)easingType) + initialSnapshot.getRotZ());
                    }
                    snapshot.updateRotation(bone.getRotX(), bone.getRotY(), bone.getRotZ());
                    snapshot.startRotAnim();
                    bone.markRotationAsChanged();
                }
                if (posXPoint != null && posYPoint != null && posZPoint != null) {
                    bone.setPosX((float)EasingType.lerpWithOverride((AnimationPoint)posXPoint, (EasingType)easingType));
                    bone.setPosY((float)EasingType.lerpWithOverride((AnimationPoint)posYPoint, (EasingType)easingType));
                    bone.setPosZ((float)EasingType.lerpWithOverride((AnimationPoint)posZPoint, (EasingType)easingType));
                    snapshot.updateOffset(bone.getPosX(), bone.getPosY(), bone.getPosZ());
                    snapshot.startPosAnim();
                    bone.markPositionAsChanged();
                }
                if (scaleXPoint == null || scaleYPoint == null || scaleZPoint == null) continue;
                bone.setScaleX((float)EasingType.lerpWithOverride((AnimationPoint)scaleXPoint, (EasingType)easingType));
                bone.setScaleY((float)EasingType.lerpWithOverride((AnimationPoint)scaleYPoint, (EasingType)easingType));
                bone.setScaleZ((float)EasingType.lerpWithOverride((AnimationPoint)scaleZPoint, (EasingType)easingType));
                snapshot.updateScale(bone.getScaleX(), bone.getScaleY(), bone.getScaleZ());
                snapshot.startScaleAnim();
                bone.markScaleAsChanged();
            }
        }
        this.reloadAnimations = false;
        double resetTickLength = animatable.getBoneResetTime();
        for (GeoBone bone : this.getRegisteredBones()) {
            double percentageReset;
            BoneSnapshot saveSnapshot;
            BoneSnapshot initialSnapshot;
            if (!bone.hasRotationChanged()) {
                initialSnapshot = bone.getInitialSnapshot();
                saveSnapshot = (BoneSnapshot)boneSnapshots.get(bone.getName());
                if (saveSnapshot.isRotAnimInProgress()) {
                    saveSnapshot.stopRotAnim(animTime);
                }
                percentageReset = resetTickLength == 0.0 ? 1.0 : Math.min((animTime - saveSnapshot.getLastResetRotationTick()) / resetTickLength, 1.0);
                float initialRotX = initialSnapshot.getRotX();
                float initialRotY = initialSnapshot.getRotY();
                float initialRotZ = initialSnapshot.getRotZ();
                float lastXRot = saveSnapshot.getRotX();
                float lastYRot = saveSnapshot.getRotY();
                float lastZRot = saveSnapshot.getRotZ();
                if (percentageReset == 0.0) {
                    if (lastXRot != initialRotX && this.isSuspectedCompletedRotation(lastXRot)) {
                        lastXRot = initialRotX;
                        percentageReset = 1.0;
                    }
                    if (lastYRot != initialRotY && this.isSuspectedCompletedRotation(lastYRot)) {
                        lastYRot = initialRotY;
                        percentageReset = 1.0;
                    }
                    if (lastZRot != initialRotZ && this.isSuspectedCompletedRotation(lastZRot)) {
                        lastZRot = initialRotZ;
                        percentageReset = 1.0;
                    }
                }
                bone.setRotX((float)Mth.lerp((double)percentageReset, (double)lastXRot, (double)initialRotX));
                bone.setRotY((float)Mth.lerp((double)percentageReset, (double)lastYRot, (double)initialRotY));
                bone.setRotZ((float)Mth.lerp((double)percentageReset, (double)lastZRot, (double)initialRotZ));
                if (percentageReset >= 1.0) {
                    saveSnapshot.updateRotation(bone.getRotX(), bone.getRotY(), bone.getRotZ());
                }
            }
            if (!bone.hasPositionChanged()) {
                initialSnapshot = bone.getInitialSnapshot();
                saveSnapshot = (BoneSnapshot)boneSnapshots.get(bone.getName());
                if (saveSnapshot.isPosAnimInProgress()) {
                    saveSnapshot.stopPosAnim(animTime);
                }
                percentageReset = resetTickLength == 0.0 ? 1.0 : Math.min((animTime - saveSnapshot.getLastResetPositionTick()) / resetTickLength, 1.0);
                bone.setPosX((float)Mth.lerp((double)percentageReset, (double)saveSnapshot.getOffsetX(), (double)initialSnapshot.getOffsetX()));
                bone.setPosY((float)Mth.lerp((double)percentageReset, (double)saveSnapshot.getOffsetY(), (double)initialSnapshot.getOffsetY()));
                bone.setPosZ((float)Mth.lerp((double)percentageReset, (double)saveSnapshot.getOffsetZ(), (double)initialSnapshot.getOffsetZ()));
                if (percentageReset >= 1.0) {
                    saveSnapshot.updateOffset(bone.getPosX(), bone.getPosY(), bone.getPosZ());
                }
            }
            if (bone.hasScaleChanged()) continue;
            initialSnapshot = bone.getInitialSnapshot();
            saveSnapshot = (BoneSnapshot)boneSnapshots.get(bone.getName());
            if (saveSnapshot.isScaleAnimInProgress()) {
                saveSnapshot.stopScaleAnim(animTime);
            }
            percentageReset = resetTickLength == 0.0 ? 1.0 : Math.min((animTime - saveSnapshot.getLastResetScaleTick()) / resetTickLength, 1.0);
            bone.setScaleX((float)Mth.lerp((double)percentageReset, (double)saveSnapshot.getScaleX(), (double)initialSnapshot.getScaleX()));
            bone.setScaleY((float)Mth.lerp((double)percentageReset, (double)saveSnapshot.getScaleY(), (double)initialSnapshot.getScaleY()));
            bone.setScaleZ((float)Mth.lerp((double)percentageReset, (double)saveSnapshot.getScaleZ(), (double)initialSnapshot.getScaleZ()));
            if (!(percentageReset >= 1.0)) continue;
            saveSnapshot.updateScale(bone.getScaleX(), bone.getScaleY(), bone.getScaleZ());
        }
        this.getRegisteredBones().forEach(GeoBone::resetStateChanges);
        ((AnimatableManagerAccessor)animatableManager).invokeFinishFirstTick();
    }

    private boolean isSuspectedCompletedRotation(float lastRotation) {
        float rotations = Mth.abs((float)(lastRotation / ((float)Math.PI * 2)));
        float partialRotation = 1.0f - (rotations - (float)((int)rotations));
        return partialRotation == 1.0f || (double)partialRotation < 0.026 * (double)rotations;
    }

    @Nullable
    private static Vec3AnimationPoint process(BoneAnimationQueue boneAnimation) {
        AnimationPoint xPoint = (AnimationPoint)boneAnimation.rotationXQueue().poll();
        AnimationPoint yPoint = (AnimationPoint)boneAnimation.rotationYQueue().poll();
        AnimationPoint zPoint = (AnimationPoint)boneAnimation.rotationZQueue().poll();
        if (xPoint == null || yPoint == null || zPoint == null) {
            return null;
        }
        Vec3 start = new Vec3(xPoint.animationStartValue(), yPoint.animationStartValue(), zPoint.animationStartValue());
        Vec3 end = new Vec3(xPoint.animationEndValue(), yPoint.animationEndValue(), zPoint.animationEndValue());
        double[][] startMatrix = MatrixAnimationProcessor.eulerToRotationMatrix(start);
        double[][] endMatrix = MatrixAnimationProcessor.eulerToRotationMatrix(end);
        Vec3 reStart = MatrixAnimationProcessor.rotationMatrixToEuler(startMatrix);
        Vec3 reEnd = MatrixAnimationProcessor.rotationMatrixToEuler(endMatrix);
        return new Vec3AnimationPoint(new AnimationPoint(xPoint.keyFrame(), xPoint.currentTick(), xPoint.transitionLength(), reStart.x, reEnd.x), new AnimationPoint(yPoint.keyFrame(), yPoint.currentTick(), yPoint.transitionLength(), reStart.y, reEnd.y), new AnimationPoint(zPoint.keyFrame(), zPoint.currentTick(), zPoint.transitionLength(), reStart.z, reEnd.z));
    }

    public static double[][] eulerToRotationMatrix(Vec3 angles) {
        double[][] x = new double[][]{{1.0, 0.0, 0.0}, {0.0, Math.cos(angles.x), -Math.sin(angles.x)}, {0.0, Math.sin(angles.x), Math.cos(angles.x)}};
        double[][] y = new double[][]{{Math.cos(angles.y), 0.0, Math.sin(angles.y)}, {0.0, 1.0, 0.0}, {-Math.sin(angles.y), 0.0, Math.cos(angles.y)}};
        double[][] z = new double[][]{{Math.cos(angles.z), -Math.sin(angles.z), 0.0}, {Math.sin(angles.z), Math.cos(angles.z), 0.0}, {0.0, 0.0, 1.0}};
        return MatrixAnimationProcessor.multiplyMatrices(MatrixAnimationProcessor.multiplyMatrices(z, y), x);
    }

    public static double[][] multiplyMatrices(double[][] a, double[][] b) {
        int aRows = a.length;
        int aCols = a[0].length;
        int bCols = b[0].length;
        double[][] result = new double[aRows][bCols];
        for (int i = 0; i < aRows; ++i) {
            for (int j = 0; j < bCols; ++j) {
                for (int k = 0; k < aCols; ++k) {
                    double[] dArray = result[i];
                    int n = j;
                    dArray[n] = dArray[n] + a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    public static Vec3 rotationMatrixToEuler(double[][] matrix) {
        if (matrix[2][0] < 1.0) {
            if (matrix[2][0] > -1.0) {
                return new Vec3(Math.atan2(matrix[2][1], matrix[2][2]), Math.asin(-matrix[2][0]), Math.atan2(matrix[1][0], matrix[0][0]));
            }
            return new Vec3(Math.atan2(-matrix[1][2], matrix[1][1]), 1.5707963267948966, 0.0);
        }
        return new Vec3(Math.atan2(-matrix[1][2], matrix[1][1]), -1.5707963267948966, 0.0);
    }

    private record Vec3AnimationPoint(AnimationPoint x, AnimationPoint y, AnimationPoint z) {
    }
}

