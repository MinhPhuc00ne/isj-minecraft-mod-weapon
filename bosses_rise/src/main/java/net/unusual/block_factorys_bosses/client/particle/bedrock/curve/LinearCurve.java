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
public record LinearCurve(List<MathValue> nodes) implements Curve
{
    public LinearCurve {
        if (nodes.size() < 2) {
            throw new IllegalArgumentException("LinearCurve must have at least two nodes.");
        }
    }

    @Override
    public double interpolate(float progress) {
        int lastPointIndex;
        float floatIndex = (progress = Mth.clamp((float)progress, (float)0.0f, (float)1.0f)) * (float)(lastPointIndex = this.nodes.size() - 1);
        int previousIndex = Mth.floor((float)floatIndex);
        int nextIndex = previousIndex + 1;
        if (nextIndex >= this.nodes.size()) {
            return this.nodes.get(lastPointIndex).get();
        }
        return Mth.lerp((double)Mth.frac((float)floatIndex), (double)this.nodes.get(previousIndex).get(), (double)this.nodes.get(nextIndex).get());
    }

    public static LinearCurve deserialize(JsonObject curveObject) {
        List<MathValue> points = Curve.parseNodes(curveObject);
        return new LinearCurve(points);
    }
}

