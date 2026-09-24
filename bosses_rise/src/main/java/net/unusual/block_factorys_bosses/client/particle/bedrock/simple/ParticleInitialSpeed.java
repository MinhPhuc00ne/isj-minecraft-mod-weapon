/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  software.bernie.geckolib.loading.math.MathValue
 *  software.bernie.geckolib.loading.math.value.Constant
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.simple;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffect;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.value.Constant;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleInitialSpeed(MathValue initialSpeed) {
    public static ParticleInitialSpeed deserializeFromComponents(JsonObject allComponents) {
        return ParticleInitialSpeed.deserialize(allComponents.get("minecraft:particle_initial_speed"));
    }

    public static ParticleInitialSpeed deserialize(@Nullable JsonElement componentJson) {
        JsonPrimitive primitive;
        if (componentJson instanceof JsonPrimitive && (primitive = (JsonPrimitive)componentJson).isNumber()) {
            return new ParticleInitialSpeed((MathValue)new Constant((double)primitive.getAsFloat()));
        }
        return new ParticleInitialSpeed(BedrockParticleEffect.parseMolangJson(componentJson, 0.0));
    }
}

