/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  software.bernie.geckolib.loading.math.MathValue
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.lifetime;

import com.google.gson.JsonObject;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffect;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEmitter;
import net.unusual.block_factorys_bosses.client.particle.bedrock.lifetime.EmitterLifetime;
import software.bernie.geckolib.loading.math.MathValue;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record EmitterLifetimeLooping(MathValue activeTime, MathValue sleepTime) implements EmitterLifetime
{
    @Override
    public void tick(BedrockParticleEmitter emitter) {
        emitter.elapsedCycleSeconds += 0.05f;
        if (emitter.elapsedCycleSeconds >= emitter.cycleDuration) {
            emitter.cycleDuration = (float)(emitter.emitting ? this.sleepTime.get() : this.activeTime.get());
            emitter.elapsedCycleSeconds = 0.0f;
            emitter.emitting = !emitter.emitting;
        }
    }

    public static EmitterLifetimeLooping deserialize(JsonObject component) {
        MathValue activeTime = BedrockParticleEffect.parseMolangJson(component.get("active_time"), 10.0);
        MathValue sleepTime = BedrockParticleEffect.parseMolangJson(component.get("sleep_time"), 0.0);
        return new EmitterLifetimeLooping(activeTime, sleepTime);
    }
}

