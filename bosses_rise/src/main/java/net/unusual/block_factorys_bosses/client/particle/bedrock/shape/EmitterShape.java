/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.gson.JsonObject
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  org.joml.Vector3f
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.shape;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import java.util.Set;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffect;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEmitter;
import net.unusual.block_factorys_bosses.client.particle.bedrock.shape.EmitterShapeBox;
import net.unusual.block_factorys_bosses.client.particle.bedrock.shape.EmitterShapeCustom;
import net.unusual.block_factorys_bosses.client.particle.bedrock.shape.EmitterShapeDisc;
import net.unusual.block_factorys_bosses.client.particle.bedrock.shape.EmitterShapePoint;
import net.unusual.block_factorys_bosses.client.particle.bedrock.shape.EmitterShapeSphere;
import org.joml.Vector3f;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface EmitterShape {
    public static final Set<String> KNOWN_TYPES = ImmutableSet.of("minecraft:emitter_shape_box", "minecraft:emitter_shape_custom", "minecraft:emitter_shape_disc", "minecraft:emitter_shape_entity_aabb", "minecraft:emitter_shape_point", "minecraft:emitter_shape_sphere");

    public void getSpawnPosition(BedrockParticleEmitter var1, Vector3f var2, Vector3f var3);

    public static EmitterShape deserialize(JsonObject allComponents) {
        String type = BedrockParticleEffect.getOneComponentOf(allComponents, KNOWN_TYPES);
        if (type == null) {
            throw new IllegalArgumentException("No shape component found.");
        }
        JsonObject componentJson = allComponents.getAsJsonObject(type);
        return switch (type) {
            case "minecraft:emitter_shape_box" -> EmitterShapeBox.deserialize(componentJson);
            case "minecraft:emitter_shape_custom" -> EmitterShapeCustom.deserialize(componentJson);
            case "minecraft:emitter_shape_disc" -> EmitterShapeDisc.deserialize(componentJson);
            case "minecraft:emitter_shape_point" -> EmitterShapePoint.deserialize(componentJson);
            case "minecraft:emitter_shape_sphere" -> EmitterShapeSphere.deserialize(componentJson);
            default -> throw new IllegalArgumentException("Unknown shape component type: " + type);
        };
    }
}

