/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  org.joml.Vector2f
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.appearance;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.appearance.ParticleUV;
import org.joml.Vector2f;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleUVFull() implements ParticleUV
{
    public static ParticleUVFull INSTANCE = new ParticleUVFull();

    @Override
    public void getUV(BedrockParticle particle, float partialTicks, Vector2f topLeft, Vector2f bottomRight) {
        topLeft.set(0.0f, 0.0f);
        bottomRight.set(1.0f, 1.0f);
    }
}

