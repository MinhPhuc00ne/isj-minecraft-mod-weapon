/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  software.bernie.geckolib.loading.math.MathParser
 *  software.bernie.geckolib.loading.math.MathValue
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.curve;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffect;
import net.unusual.block_factorys_bosses.client.particle.bedrock.curve.BezierChainCurve;
import net.unusual.block_factorys_bosses.client.particle.bedrock.curve.BezierCurve;
import net.unusual.block_factorys_bosses.client.particle.bedrock.curve.Curve;
import net.unusual.block_factorys_bosses.client.particle.bedrock.curve.LinearCurve;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleCurve(Curve curve, MathValue input, MathValue inputHorizontalRange) {
    public double getValue() {
        float progress = (float)(this.input.get() / this.inputHorizontalRange.get());
        return this.curve.interpolate(progress);
    }

    public static ParticleCurve deserialize(JsonObject curveObject) {
        String type = curveObject.get("type").getAsString();
        MathValue input = MathParser.parseJson((JsonElement)curveObject.get("input"));
        MathValue inputHorizontalRange = BedrockParticleEffect.parseMolangJson(type.equals("bezier_chain") ? null : curveObject.get("horizontal_range"), 1.0);
        Record curve = switch (type) {
            case "linear" -> LinearCurve.deserialize(curveObject);
            case "bezier" -> BezierCurve.deserialize(curveObject);
            case "bezier_chain" -> BezierChainCurve.deserialize(curveObject);
            default -> throw new IllegalArgumentException("Unknown curve type: " + type);
        };
        return new ParticleCurve((Curve)((Object)curve), input, inputHorizontalRange);
    }
}

