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
package net.unusual.block_factorys_bosses.client.particle.bedrock.rate;

import com.google.gson.JsonObject;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.Mth;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffect;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEmitter;
import net.unusual.block_factorys_bosses.client.particle.bedrock.rate.EmitterRate;
import software.bernie.geckolib.loading.math.MathValue;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record EmitterRateSteady(MathValue spawnRate, MathValue maxParticles) implements EmitterRate
{
    @Override
    public void tick(BedrockParticleEmitter emitter) {
        int spawnCount;
        int maxParticles = Mth.floor((double)this.maxParticles.get());
        float spawnRatePerTick = (float)this.spawnRate.get() / 20.0f;
        for (int remaining = spawnCount = Mth.floor((float)((float)emitter.getAge() * spawnRatePerTick)) - Mth.floor((float)((float)(emitter.getAge() - 1) * spawnRatePerTick)); remaining > 0 && emitter.getChildren().size() < maxParticles; --remaining) {
            emitter.emitParticle();
        }
    }

    public static EmitterRateSteady deserialize(JsonObject component) {
        MathValue spawnRate = BedrockParticleEffect.parseMolangJson(component.get("spawn_rate"), 1.0);
        MathValue maxParticles = BedrockParticleEffect.parseMolangJson(component.get("max_particles"), 50.0);
        return new EmitterRateSteady(spawnRate, maxParticles);
    }
}

