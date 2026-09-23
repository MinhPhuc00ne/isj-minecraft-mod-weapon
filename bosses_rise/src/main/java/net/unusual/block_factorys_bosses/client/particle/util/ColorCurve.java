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
public record ColorCurve(Point[] points, BiFunction<SubParticle, Float, Float> input) {
    public FloatRGBA get(SubParticle proxy, float partial) {
        return this.interpolate(this.input.apply(proxy, Float.valueOf(partial)).floatValue());
    }

    public FloatRGBA interpolate(float x) {
        int i;
        if (this.points.length < 2) {
            throw new IllegalArgumentException("Array must contain at least two points.");
        }
        if (x < 0.0f) {
            x = 0.0f;
        } else if (x > 1.0f) {
            x = 1.0f;
        }
        for (i = 0; i < this.points.length - 1 && x > this.points[i + 1].x; ++i) {
        }
        float x0 = this.points[i].x;
        float x1 = this.points[i + 1].x;
        float xFactor = (x - x0) / (x1 - x0);
        float r0 = this.points[i].color.r;
        float r1 = this.points[i + 1].color.r;
        float g0 = this.points[i].color.g;
        float g1 = this.points[i + 1].color.g;
        float b0 = this.points[i].color.b;
        float b1 = this.points[i + 1].color.b;
        float a0 = this.points[i].color.a;
        float a1 = this.points[i + 1].color.a;
        return new FloatRGBA(r0 + (r1 - r0) * xFactor, g0 + (g1 - g0) * xFactor, b0 + (b1 - b0) * xFactor, a0 + (a1 - a0) * xFactor);
    }

    public record Point(FloatRGBA color, float x) {
    }

    public record FloatRGBA(float r, float g, float b, float a) {
        public static FloatRGBA fromARGB(int color) {
            return new FloatRGBA((float)(color >> 16 & 0xFF) / 255.0f, (float)(color >> 8 & 0xFF) / 255.0f, (float)(color & 0xFF) / 255.0f, (float)(color >> 24 & 0xFF) / 255.0f);
        }
    }
}

