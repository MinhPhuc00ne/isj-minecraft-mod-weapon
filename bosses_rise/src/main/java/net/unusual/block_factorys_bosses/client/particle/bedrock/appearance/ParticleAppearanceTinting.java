/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  software.bernie.geckolib.loading.math.MathParser
 *  software.bernie.geckolib.loading.math.MathValue
 *  software.bernie.geckolib.loading.math.value.Constant
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.appearance;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.List;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.curve.ColorGradient;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.value.Constant;

public record ParticleAppearanceTinting(MathValue interpolant, ColorGradient color) {
    private static final ParticleAppearanceTinting DEFAULT = new ParticleAppearanceTinting((MathValue)new Constant(0.0), new ColorGradient(List.of(new ColorGradient.ColorNode(0.0f, -1))));

    public int getColor(BedrockParticle particle) {
        float progress = (float)this.interpolant().get();
        return this.color().getColor(progress);
    }

    public static ParticleAppearanceTinting deserializeFromComponents(JsonObject allComponents) {
        JsonObject json = allComponents.getAsJsonObject("minecraft:particle_appearance_tinting");
        if (json == null) {
            return DEFAULT;
        }
        return ParticleAppearanceTinting.deserialize(json);
    }

    public static ParticleAppearanceTinting deserialize(JsonObject jsonObject) {
        JsonElement colorJson = jsonObject.get("color");
        if (colorJson instanceof JsonArray || colorJson instanceof JsonPrimitive) {
            return new ParticleAppearanceTinting((MathValue)new Constant(0.0), new ColorGradient(List.of(new ColorGradient.ColorNode(0.0f, ColorGradient.parseColor(colorJson)))));
        }
        if (colorJson instanceof JsonObject) {
            JsonObject colorObject = (JsonObject)colorJson;
            return new ParticleAppearanceTinting(MathParser.parseJson((JsonElement)colorObject.get("interpolant")), ColorGradient.deserialize(colorObject.get("gradient")));
        }
        throw new IllegalArgumentException("Tinting color must be an object or a single color, but received: " + String.valueOf(colorJson));
    }
}

