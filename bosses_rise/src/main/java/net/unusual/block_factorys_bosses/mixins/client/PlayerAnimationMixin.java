/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.CameraType
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.client.model.PlayerModel
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.model.geom.PartPose
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  org.joml.Vector3f
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  software.bernie.geckolib.animation.Animation
 *  software.bernie.geckolib.animation.EasingType
 *  software.bernie.geckolib.animation.keyframe.AnimationPoint
 *  software.bernie.geckolib.animation.keyframe.BoneAnimation
 *  software.bernie.geckolib.animation.keyframe.Keyframe
 *  software.bernie.geckolib.animation.keyframe.KeyframeLocation
 *  software.bernie.geckolib.animation.keyframe.KeyframeStack
 *  software.bernie.geckolib.cache.GeckoLibCache
 *  software.bernie.geckolib.loading.math.MathValue
 *  software.bernie.geckolib.loading.math.value.Constant
 *  software.bernie.geckolib.loading.object.BakedAnimations
 */
package net.unusual.block_factorys_bosses.mixins.client;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerAnimationHandler;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.animation.EasingType;
import software.bernie.geckolib.animation.keyframe.AnimationPoint;
import software.bernie.geckolib.animation.keyframe.BoneAnimation;
import software.bernie.geckolib.animation.keyframe.Keyframe;
import software.bernie.geckolib.animation.keyframe.KeyframeLocation;
import software.bernie.geckolib.animation.keyframe.KeyframeStack;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.value.Constant;
import software.bernie.geckolib.loading.object.BakedAnimations;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(value={PlayerModel.class})
public abstract class PlayerAnimationMixin<T extends LivingEntity>
extends HumanoidModel<T> {
    @Shadow
    @Final
    private ModelPart cloak;
    @Unique
    private ModelPart br$root = null;

    public PlayerAnimationMixin(ModelPart root) {
        super(root);
    }

    @Inject(method={"<init>"}, at={@At(value="TAIL")})
    private void injectInit(ModelPart root, boolean slim, CallbackInfo ci) {
        this.br$root = root;
    }

    @Inject(method={"setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V"}, at={@At(value="HEAD")})
    private void setup(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        this.body.resetPose();
        this.head.resetPose();
        this.leftLeg.resetPose();
        this.rightLeg.resetPose();
        this.leftArm.resetPose();
        this.rightArm.resetPose();
        this.cloak.resetPose();
    }

    @Inject(method={"setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V"}, at={@At(value="TAIL")})
    private void animateBones(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player)entity;
        Minecraft mc = Minecraft.getInstance();
        if (player == mc.player && mc.options.getCameraType() == CameraType.FIRST_PERSON) {
            return;
        }
        PlayerAnimationHandler attachment = PlayerAnimationHandler.fromPlayer(player);
        int tick = attachment.getTick();
        if (tick <= 0) {
            return;
        }
        PlayerAnimationHandler.PlayerAnimation playerAnimation = attachment.getCurrentAnimation();
        if (playerAnimation == null) {
            return;
        }
        BakedAnimations bakedAnimations = (BakedAnimations)GeckoLibCache.getBakedAnimations().get(playerAnimation.path());
        if (bakedAnimations == null) {
            return;
        }
        Animation animation = bakedAnimations.getAnimation(playerAnimation.name());
        if (animation == null) {
            return;
        }
        float partialTick = mc.getTimer().getGameTimeDeltaPartialTick(false);
        float factor = 1.0f - ((float)tick - partialTick) / (float)attachment.getDuration();
        double adjustedTick = animation.length() * (double)factor;
        float lerp = Math.min(playerAnimation.lerp(), tick > 3 || !attachment.canLerp() ? 1.0f : ((float)tick - partialTick) / 3.0f);
        float anti = 1.0f - lerp;
        for (BoneAnimation boneAnimation : animation.boneAnimations()) {
            Vector3f rotations = PlayerAnimationMixin.br$getAnimationPointAtTick((KeyframeStack<Keyframe<MathValue>>)boneAnimation.rotationKeyFrames(), adjustedTick, true);
            Vector3f positions = PlayerAnimationMixin.br$getAnimationPointAtTick((KeyframeStack<Keyframe<MathValue>>)boneAnimation.positionKeyFrames(), adjustedTick, false);
            for (ModelPart bone : this.br$getPartsFor(boneAnimation.boneName())) {
                if (rotations != null) {
                    bone.xRot = rotations.x + bone.xRot * anti;
                    bone.yRot = rotations.y + bone.yRot * anti;
                    bone.zRot = rotations.z + bone.zRot * anti;
                }
                if (positions == null) continue;
                PartPose pose = bone.getInitialPose();
                bone.x = Mth.lerp((float)anti, (float)(positions.x + pose.x), (float)bone.x);
                bone.y = Mth.lerp((float)anti, (float)(positions.y + pose.y), (float)bone.y);
                bone.z = Mth.lerp((float)anti, (float)(positions.z + pose.z), (float)bone.z);
            }
        }
    }

    @Unique
    private List<ModelPart> br$getPartsFor(String name) {
        ArrayList<ModelPart> list = new ArrayList<ModelPart>();
        if (this.br$root.hasChild(name)) {
            String other = null;
            list.add(this.br$root.getChild(name));
            switch (name) {
                case "body": {
                    other = "jacket";
                    break;
                }
                case "head": {
                    other = "hat";
                    break;
                }
                case "left_arm": {
                    other = "left_sleeve";
                    break;
                }
                case "right_arm": {
                    other = "right_sleeve";
                    break;
                }
                case "left_leg": {
                    other = "left_pants";
                    break;
                }
                case "right_leg": {
                    other = "right_pants";
                    break;
                }
                default: {
                    other = null;
                }
            }
            if (other != null && this.br$root.hasChild(other)) {
                list.add(this.br$root.getChild(other));
            }
        }
        return list;
    }

    @Unique
    @Nullable
    private static Vector3f br$getAnimationPointAtTick(KeyframeStack<Keyframe<MathValue>> keyFrames, double tick, boolean isRotation) {
        if (keyFrames.xKeyframes().isEmpty()) {
            return null;
        }
        Vector3f vec3f = new Vector3f();
        for (Direction.Axis axis : Direction.Axis.values()) {
            List frames = switch (axis) {
                default -> throw new MatchException(null, null);
                case Direction.Axis.X -> keyFrames.xKeyframes();
                case Direction.Axis.Y -> keyFrames.yKeyframes();
                case Direction.Axis.Z -> keyFrames.zKeyframes();
            };
            KeyframeLocation<Keyframe<MathValue>> location = PlayerAnimationMixin.br$getCurrentKeyFrameLocation(frames, tick);
            Keyframe currentFrame = location.keyframe();
            double startValue = currentFrame.startValue().get();
            double endValue = currentFrame.endValue().get();
            if (isRotation) {
                if (!(currentFrame.startValue() instanceof Constant)) {
                    startValue = Math.toRadians(startValue);
                    if (axis == Direction.Axis.X || axis == Direction.Axis.Y) {
                        startValue *= -1.0;
                    }
                }
                if (!(currentFrame.endValue() instanceof Constant)) {
                    endValue = Math.toRadians(endValue);
                    if (axis == Direction.Axis.X || axis == Direction.Axis.Y) {
                        endValue *= -1.0;
                    }
                }
            }
            float var = (float)EasingType.lerpWithOverride((AnimationPoint)new AnimationPoint(currentFrame, location.startTick(), currentFrame.length(), startValue, endValue), null);
            if (axis == Direction.Axis.Y || isRotation && axis == Direction.Axis.X) {
                var *= -1.0f;
            }
            vec3f.setComponent(axis.ordinal(), var);
        }
        return vec3f;
    }

    @Unique
    private static KeyframeLocation<Keyframe<MathValue>> br$getCurrentKeyFrameLocation(List<Keyframe<MathValue>> frames, double ageInTicks) {
        double totalFrameTime = 0.0;
        for (Keyframe<MathValue> frame : frames) {
            if (!((totalFrameTime += frame.length()) > ageInTicks)) continue;
            return new KeyframeLocation(frame, ageInTicks - (totalFrameTime - frame.length()));
        }
        return new KeyframeLocation((Keyframe)frames.getLast(), ageInTicks);
    }
}

