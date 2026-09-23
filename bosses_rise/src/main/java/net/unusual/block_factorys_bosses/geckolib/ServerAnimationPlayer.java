/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.math.Axis
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 *  org.joml.Quaternionf
 *  org.joml.Quaternionfc
 *  org.joml.Vector3f
 *  software.bernie.geckolib.GeckoLibServices
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animation.AnimatableManager
 *  software.bernie.geckolib.animation.AnimationProcessor
 *  software.bernie.geckolib.animation.AnimationState
 *  software.bernie.geckolib.cache.object.GeoBone
 *  software.bernie.geckolib.model.GeoModel
 */
package net.unusual.block_factorys_bosses.geckolib;

import com.mojang.math.Axis;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.unusual.block_factorys_bosses.geckolib.CommonPoseStack;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import software.bernie.geckolib.GeckoLibServices;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationProcessor;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ServerAnimationPlayer<T extends Entity & GeoAnimatable> {
    private GeoModel<T> model;
    private final T animatable;

    public ServerAnimationPlayer(GeoModel<T> model, T animatable) {
        this.model = model;
        this.animatable = animatable;
    }

    private Entity getEntity() {
        return this.animatable;
    }

    public GeoModel<T> getModel() {
        return this.model;
    }

    public void setModel(GeoModel<T> model) {
        this.model = model;
    }

    public void triggerAnim(@Nullable String controllerName, String animName) {
        Entity entity = this.getEntity();
        if (!entity.level().isClientSide()) {
            GeckoLibServices.NETWORK.triggerEntityAnim(entity, false, controllerName, animName);
        }
        if (controllerName != null) {
            this.animatable.getAnimatableInstanceCache().getManagerForId((long)entity.getId()).tryTriggerAnimation(controllerName, animName);
        } else {
            this.animatable.getAnimatableInstanceCache().getManagerForId((long)entity.getId()).tryTriggerAnimation(animName);
        }
    }

    public void stopTriggeredAnim(@Nullable String controllerName, @Nullable String animName) {
        AnimatableManager animatableManager;
        Entity entity = this.getEntity();
        if (!entity.level().isClientSide()) {
            GeckoLibServices.NETWORK.stopTriggeredEntityAnim(entity, false, controllerName, animName);
        }
        if ((animatableManager = this.animatable.getAnimatableInstanceCache().getManagerForId((long)entity.getId())) == null) {
            return;
        }
        if (controllerName != null) {
            animatableManager.stopTriggeredAnimation(controllerName, animName);
        } else {
            animatableManager.stopTriggeredAnimation(animName);
        }
    }

    public static <T extends Entity & GeoAnimatable> void retriggerAnim(@Nullable ServerAnimationPlayer<T> serverAnimationPlayer, @Nullable String controllerName, String animName) {
        ServerAnimationPlayer.stopTriggeredAnim(serverAnimationPlayer, controllerName, animName);
        ServerAnimationPlayer.triggerAnim(serverAnimationPlayer, controllerName, animName);
    }

    public static <T extends Entity & GeoAnimatable> void triggerAnim(@Nullable ServerAnimationPlayer<T> serverAnimationPlayer, @Nullable String controllerName, String animName) {
        if (serverAnimationPlayer == null) {
            return;
        }
        serverAnimationPlayer.triggerAnim(controllerName, animName);
    }

    public static <T extends Entity & GeoAnimatable> void stopTriggeredAnim(@Nullable ServerAnimationPlayer<T> serverAnimationPlayer, @Nullable String controllerName, @Nullable String animName) {
        if (serverAnimationPlayer == null) {
            return;
        }
        serverAnimationPlayer.stopTriggeredAnim(controllerName, animName);
    }

    public void tickAnimations() {
        Entity entity = this.getEntity();
        this.model.getBakedModel(this.model.getModelResource(this.animatable));
        AnimatableManager manager = this.animatable.getAnimatableInstanceCache().getManagerForId((long)entity.getId());
        AnimationState frameState = new AnimationState(this.animatable, 0.0f, 0.0f, 0.0f, false);
        double animTime = entity.tickCount;
        if (manager.getFirstTickTime() == -1.0) {
            manager.startedAt(animTime);
        }
        manager.updatedAt(animTime);
        frameState.animationTick = animTime;
        AnimationProcessor processor = this.model.getAnimationProcessor();
        processor.preAnimationSetup(frameState, animTime);
        if (!processor.getRegisteredBones().isEmpty()) {
            processor.tickAnimation(this.animatable, this.model, manager, animTime, frameState, false);
        }
    }

    private void applyBoneTransformations(ServerPoseStack poseStack, GeoBone bone) {
        GeoBone parent = bone.getParent();
        if (parent != null) {
            this.applyBoneTransformations(poseStack, parent);
        }
        poseStack.translate(-bone.getPosX() / 16.0f, bone.getPosY() / 16.0f, bone.getPosZ() / 16.0f);
        poseStack.translate(bone.getPivotX() / 16.0f, bone.getPivotY() / 16.0f, bone.getPivotZ() / 16.0f);
        if (bone.getRotZ() != 0.0f) {
            poseStack.mulPose(Axis.ZP.rotation(bone.getRotZ()));
        }
        if (bone.getRotY() != 0.0f) {
            poseStack.mulPose(Axis.YP.rotation(bone.getRotY()));
        }
        if (bone.getRotX() != 0.0f) {
            poseStack.mulPose(Axis.XP.rotation(bone.getRotX()));
        }
        poseStack.scale(bone.getScaleX(), bone.getScaleY(), bone.getScaleZ());
        poseStack.translate(-bone.getPivotX() / 16.0f, -bone.getPivotY() / 16.0f, -bone.getPivotZ() / 16.0f);
    }

    public void applyWorldSpaceTransformations(ServerPoseStack poseStack) {
        T t = this.animatable;
        if (t instanceof CustomTransformationsEntity) {
            CustomTransformationsEntity tentacle = (CustomTransformationsEntity)t;
            tentacle.applyCustomWorldSpaceTransformations(poseStack);
        }
        poseStack.translate(this.animatable.xo, this.animatable.yo, this.animatable.zo);
        t = this.animatable;
        if (t instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)t;
            float entityScale = livingEntity.getScale();
            poseStack.scale(entityScale, entityScale, entityScale);
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0f - livingEntity.yBodyRot));
        }
    }

    public GeoBone getBone(String boneName) {
        AnimationProcessor processor = this.model.getAnimationProcessor();
        return Objects.requireNonNull(processor.getBone(boneName));
    }

    public Vector3f getBonePosition(ServerPoseStack poseStack, GeoBone bone) {
        poseStack.push();
        this.applyBoneTransformations(poseStack, bone);
        Vector3f position = poseStack.last().transformPosition(new Vector3f(bone.getPivotX(), bone.getPivotY(), bone.getPivotZ()).div(16.0f));
        poseStack.pop();
        return position;
    }

    public Vector3f getBoneWorldPosition(String boneName) {
        ServerPoseStack poseStack = new ServerPoseStack();
        this.applyWorldSpaceTransformations(poseStack);
        GeoBone bone = this.getBone(boneName);
        return this.getBonePosition(poseStack, bone);
    }

    public static class ServerPoseStack
    implements CommonPoseStack {
        private final Deque<Matrix4f> stack = new ArrayDeque<Matrix4f>();

        public ServerPoseStack() {
            this.stack.addLast(new Matrix4f());
        }

        public void push() {
            this.stack.addLast(new Matrix4f((Matrix4fc)this.stack.getLast()));
        }

        public void pop() {
            this.stack.removeLast();
        }

        public void translate(double x, double y, double z) {
            this.translate((float)x, (float)y, (float)z);
        }

        public void translate(float x, float y, float z) {
            this.stack.getLast().translate(x, y, z);
        }

        public void scale(float x, float y, float z) {
            this.stack.getLast().scale(x, y, z);
        }

        public void mulPose(Matrix4f pose) {
            this.stack.getLast().mul((Matrix4fc)pose);
        }

        public void mulPose(Quaternionf quaternion) {
            this.stack.getLast().rotate((Quaternionfc)quaternion);
        }

        public Matrix4f last() {
            return this.stack.getLast();
        }

        @Override
        public void bosses_rise_java$push() {
            this.push();
        }

        @Override
        public void bosses_rise_java$pop() {
            this.pop();
        }

        @Override
        public void bosses_rise_java$translate(double x, double y, double z) {
            this.translate(x, y, z);
        }

        @Override
        public void bosses_rise_java$translate(float x, float y, float z) {
            this.translate(x, y, z);
        }

        @Override
        public void bosses_rise_java$scale(float x, float y, float z) {
            this.scale(x, y, z);
        }

        @Override
        public void bosses_rise_java$mulPose(Matrix4f pose) {
            this.mulPose(pose);
        }

        @Override
        public void bosses_rise_java$mulPose(Quaternionf quaternion) {
            this.mulPose(quaternion);
        }

        @Override
        public Matrix4f bosses_rise_java$last() {
            return this.last();
        }
    }

    public static interface CustomTransformationsEntity {
        public void applyCustomWorldSpaceTransformations(ServerPoseStack var1);
    }
}

