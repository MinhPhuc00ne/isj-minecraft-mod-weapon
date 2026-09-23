/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package net.unusual.block_factorys_bosses.client.particle.util;

import java.util.function.BiFunction;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticle;

@OnlyIn(value=Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record LinearCurve(float[] points, BiFunction<SubParticle, Float, Float> input, BiFunction<SubParticle, Float, Float> horizontal_range) {
    public float get(SubParticle proxy, float partial) {
        return this.interpolate(this.input.apply(proxy, Float.valueOf(partial)).floatValue()) * this.horizontal_range.apply(proxy, Float.valueOf(partial)).floatValue();
    }

    public float interpolate(float x) {
        if (this.points.length < 2) {
            throw new IllegalArgumentException("Array must contain at least two points.");
        }
        if (x < 0.0f) {
            x = 0.0f;
        } else if (x > 1.0f) {
            x = 1.0f;
        }
        int n = this.points.length - 1;
        float segmentLength = 1.0f / (float)n;
        int i = (int)(x / segmentLength);
        if (i >= n) {
            i = n - 1;
        }
        float y0 = this.points[i];
        float y1 = this.points[i + 1];
        float x0 = (float)i * segmentLength;
        float x1 = (float)(i + 1) * segmentLength;
        return y0 + (y1 - y0) * ((x - x0) / (x1 - x0));
    }
}

