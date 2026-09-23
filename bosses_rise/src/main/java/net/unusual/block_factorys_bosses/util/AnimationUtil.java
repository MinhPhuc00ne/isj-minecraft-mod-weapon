/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.Direction$Axis
 *  org.joml.Vector3f
 *  software.bernie.geckolib.animation.EasingType
 *  software.bernie.geckolib.animation.keyframe.AnimationPoint
 *  software.bernie.geckolib.animation.keyframe.Keyframe
 *  software.bernie.geckolib.animation.keyframe.KeyframeLocation
 *  software.bernie.geckolib.animation.keyframe.KeyframeStack
 *  software.bernie.geckolib.loading.math.MathValue
 *  software.bernie.geckolib.loading.math.value.Constant
 */
package net.unusual.block_factorys_bosses.util;

import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import org.joml.Vector3f;
import software.bernie.geckolib.animation.EasingType;
import software.bernie.geckolib.animation.keyframe.AnimationPoint;
import software.bernie.geckolib.animation.keyframe.Keyframe;
import software.bernie.geckolib.animation.keyframe.KeyframeLocation;
import software.bernie.geckolib.animation.keyframe.KeyframeStack;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.value.Constant;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class AnimationUtil {
    @Nullable
    public static Vector3f getAnimationPointAtTick(KeyframeStack<Keyframe<MathValue>> keyFrames, double tick, boolean isRotation) {
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
            KeyframeLocation<Keyframe<MathValue>> location = AnimationUtil.getCurrentKeyFrameLocation(frames, tick);
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
            vec3f.setComponent(axis.ordinal(), var);
        }
        return vec3f;
    }

    private static KeyframeLocation<Keyframe<MathValue>> getCurrentKeyFrameLocation(List<Keyframe<MathValue>> frames, double ageInTicks) {
        double totalFrameTime = 0.0;
        for (Keyframe<MathValue> frame : frames) {
            if (!((totalFrameTime += frame.length()) > ageInTicks)) continue;
            return new KeyframeLocation(frame, ageInTicks - (totalFrameTime - frame.length()));
        }
        return new KeyframeLocation((Keyframe)frames.getLast(), ageInTicks);
    }
}

