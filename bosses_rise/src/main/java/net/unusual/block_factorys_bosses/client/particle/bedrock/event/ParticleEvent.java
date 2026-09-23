/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.event;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.AbstractBedrockParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.event.ParticleEventParticleEffect;
import net.unusual.block_factorys_bosses.client.particle.bedrock.event.ParticleEventRandomize;
import net.unusual.block_factorys_bosses.client.particle.bedrock.event.ParticleEventSequence;
import net.unusual.block_factorys_bosses.client.particle.bedrock.event.ParticleEventSoundEffect;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface ParticleEvent {
    public void trigger(AbstractBedrockParticle var1);

    public static ParticleEvent deserialize(JsonObject json) {
        if (json.has("sequence")) {
            return ParticleEventSequence.deserialize(json);
        }
        if (json.has("randomize")) {
            return ParticleEventRandomize.deserialize(json);
        }
        ArrayList<ParticleEvent> events = new ArrayList<ParticleEvent>(2);
        if (json.has("particle_effect")) {
            events.add(ParticleEventParticleEffect.deserialize(json));
        }
        if (json.has("sound_effect")) {
            events.add(ParticleEventSoundEffect.deserialize(json));
        }
        if (events.isEmpty()) {
            throw new IllegalArgumentException("Unknown particle event: " + String.valueOf(json));
        }
        if (events.size() == 1) {
            return (ParticleEvent)events.getFirst();
        }
        return new ParticleEventSequence(events);
    }
}

