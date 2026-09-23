/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonPrimitive
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Camera
 *  net.minecraft.client.particle.SingleQuadParticle$FacingCameraMode
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.joml.Quaternionf
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.appearance;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.SingleQuadParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Quaternionf;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface FacingCameraMode {
    public static final Logger LOGGER = LogManager.getLogger();

    public void getFacing(BedrockParticle var1, Quaternionf var2, Camera var3, float var4);

    public static FacingCameraMode deserialize(JsonElement facingJson) {
        String mode;
        JsonPrimitive jsonPrimitive;
        if (facingJson == null) {
            throw new IllegalArgumentException("Received a null facing_camera_vector.");
        }
        if (!(facingJson instanceof JsonPrimitive) || !(jsonPrimitive = (JsonPrimitive)facingJson).isString()) {
            throw new IllegalArgumentException("facing_camera_vector must be a string.");
        }
        return switch (mode = jsonPrimitive.getAsString()) {
            case "direction_x", "direction_y", "direction_z", "emitter_transform_xy", "emitter_transform_xz", "emitter_transform_yz", "lookat_xyz", "lookat_y", "lookat_direction" -> {
                LOGGER.warn("Bedrock particle facing_camera_mode '{}' is not yet implemented, falling back to rotate_xyz", (Object)mode);
                yield FacingCameraModeRotateXYZ.INSTANCE;
            }
            case "rotate_xyz" -> FacingCameraModeRotateXYZ.INSTANCE;
            case "rotate_y" -> FacingCameraModeRotateY.INSTANCE;
            default -> throw new IllegalArgumentException("Unknown facing_camera_vector: " + mode);
        };
    }

    public record FacingCameraModeRotateXYZ() implements FacingCameraMode
    {
        public static FacingCameraModeRotateXYZ INSTANCE = new FacingCameraModeRotateXYZ();

        @Override
        public void getFacing(BedrockParticle particle, Quaternionf quaternion, Camera renderInfo, float partialTicks) {
            SingleQuadParticle.FacingCameraMode.LOOKAT_XYZ.setRotation(quaternion, renderInfo, partialTicks);
        }
    }

    public record FacingCameraModeRotateY() implements FacingCameraMode
    {
        public static FacingCameraModeRotateY INSTANCE = new FacingCameraModeRotateY();

        @Override
        public void getFacing(BedrockParticle particle, Quaternionf quaternion, Camera renderInfo, float partialTicks) {
            SingleQuadParticle.FacingCameraMode.LOOKAT_Y.setRotation(quaternion, renderInfo, partialTicks);
        }
    }
}

