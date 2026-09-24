/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.Registry
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceLocation
 *  org.joml.Vector3f
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.event;

import com.google.gson.JsonObject;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.client.particle.bedrock.AbstractBedrockParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockEmitterParticleOptions;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEmitter;
import net.unusual.block_factorys_bosses.client.particle.bedrock.event.ParticleEvent;
import org.joml.Vector3f;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleEventParticleEffect(ResourceLocation particleEffectIdentifier, ParticleEffectType type) implements ParticleEvent
{
    @Override
    public void trigger(AbstractBedrockParticle particle) {
        this.type().trigger(particle, this.resolveParticleType(particle));
    }

    private ParticleType<BedrockEmitterParticleOptions> resolveParticleType(AbstractBedrockParticle particle) {
        Registry particleTypeRegistry = (Registry)particle.getLevel().registryAccess().registry(Registries.PARTICLE_TYPE).orElseThrow();
        ParticleType particleType = (ParticleType)particleTypeRegistry.get(this.particleEffectIdentifier());
        if (particleType == null) {
            throw new IllegalArgumentException("Bedrock particle " + String.valueOf(particle.particleEffect) + " has particle effect with invalid particle: " + String.valueOf(this.particleEffectIdentifier()));
        }
        return particleType;
    }

    public static ParticleEventParticleEffect deserialize(JsonObject json) {
        JsonObject particleEffectJson = json.getAsJsonObject("particle_effect");
        return new ParticleEventParticleEffect(ResourceLocation.parse((String)particleEffectJson.get("effect").getAsString()), ParticleEffectType.deserialize(particleEffectJson));
    }

    public static interface ParticleEffectType {
        public void trigger(AbstractBedrockParticle var1, ParticleType<BedrockEmitterParticleOptions> var2);

        public static ParticleEffectType deserialize(JsonObject json) {
            String type;
            return switch (type = json.getAsJsonPrimitive("type").getAsString()) {
                case "emitter" -> ParticleEffectTypeEmitter.INSTANCE;
                case "emitter_bound" -> ParticleEffectTypeEmitterBound.INSTANCE;
                case "particle" -> ParticleEffectTypeEmitterParticle.INSTANCE;
                case "particle_with_velocity" -> ParticleEffectTypeEmitterParticleWithVelocity.INSTANCE;
                default -> throw new IllegalArgumentException("Unknown particle_effect type: " + type);
            };
        }
    }

    public record ParticleEffectTypeEmitterParticleWithVelocity() implements ParticleEffectType
    {
        public static ParticleEffectTypeEmitterParticleWithVelocity INSTANCE = new ParticleEffectTypeEmitterParticleWithVelocity();

        @Override
        public void trigger(AbstractBedrockParticle particle, ParticleType<BedrockEmitterParticleOptions> particleEffect) {
            BedrockParticleEmitter emitter;
            Vector3f velocity = particle.getVelocity();
            particle.getLevel().addParticle((ParticleOptions)new BedrockEmitterParticleOptions(particleEffect, particle instanceof BedrockParticleEmitter ? (emitter = (BedrockParticleEmitter)particle) : null, false), particle.getX(), particle.getY(), particle.getZ(), (double)velocity.x(), (double)velocity.y(), (double)velocity.z());
        }
    }

    public record ParticleEffectTypeEmitterParticle() implements ParticleEffectType
    {
        public static ParticleEffectTypeEmitterParticle INSTANCE = new ParticleEffectTypeEmitterParticle();

        @Override
        public void trigger(AbstractBedrockParticle particle, ParticleType<BedrockEmitterParticleOptions> particleEffect) {
            BedrockParticleEmitter emitter;
            particle.getLevel().addParticle((ParticleOptions)new BedrockEmitterParticleOptions(particleEffect, particle instanceof BedrockParticleEmitter ? (emitter = (BedrockParticleEmitter)particle) : null, false), particle.getX(), particle.getY(), particle.getZ(), 0.0, 0.0, 0.0);
        }
    }

    public record ParticleEffectTypeEmitterBound() implements ParticleEffectType
    {
        public static ParticleEffectTypeEmitterBound INSTANCE = new ParticleEffectTypeEmitterBound();

        @Override
        public void trigger(AbstractBedrockParticle particle, ParticleType<BedrockEmitterParticleOptions> particleEffect) {
            particle.getLevel().addParticle((ParticleOptions)new BedrockEmitterParticleOptions(particleEffect, null, true), particle.getX(), particle.getY(), particle.getZ(), 0.0, 0.0, 0.0);
        }
    }

    public record ParticleEffectTypeEmitter() implements ParticleEffectType
    {
        public static ParticleEffectTypeEmitter INSTANCE = new ParticleEffectTypeEmitter();

        @Override
        public void trigger(AbstractBedrockParticle particle, ParticleType<BedrockEmitterParticleOptions> particleEffect) {
            particle.getLevel().addParticle((ParticleOptions)new BedrockEmitterParticleOptions(particleEffect, null, true), particle.getX(), particle.getY(), particle.getZ(), 0.0, 0.0, 0.0);
        }
    }
}

