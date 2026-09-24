/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.util.Mth
 *  software.bernie.geckolib.loading.math.MathValue
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.curve;

import com.google.gson.JsonObject;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.Mth;
import net.unusual.block_factorys_bosses.client.particle.bedrock.curve.Curve;
import software.bernie.geckolib.loading.math.MathValue;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record BezierCurve(List<MathValue> nodes) implements Curve
{
    public BezierCurve {
        if (nodes.size() != 4) {
            throw new IllegalArgumentException("Curve with type bezier must have exactly 4 nodes, but has " + nodes.size() + ".");
        }
    }

    @Override
    public double interpolate(float progress) {
        progress = Mth.clamp((float)progress, (float)0.0f, (float)1.0f);
        float a = (float)this.nodes.get(0).get();
        float b = (float)this.nodes.get(1).get();
        float c = (float)this.nodes.get(2).get();
        float d = (float)this.nodes.get(3).get();
        return BezierCurve.cubicBezier(progress, a, b, c, d);
    }

    private static double quadraticBezier(float progress, float a, float b, float c) {
        float progressInv = 1.0f - progress;
        return progressInv * progressInv * a + 2.0f * progress * progressInv * b + progress * progress * c;
    }

    private static double cubicBezier(float progress, float a, float b, float c, float d) {
        float progressInv = 1.0f - progress;
        return (double)progressInv * BezierCurve.quadraticBezier(progress, a, b, c) + (double)progress * BezierCurve.quadraticBezier(progress, b, c, d);
    }

    public static BezierCurve deserialize(JsonObject curveObject) {
        List<MathValue> points = Curve.parseNodes(curveObject);
        return new BezierCurve(points);
    }
}

