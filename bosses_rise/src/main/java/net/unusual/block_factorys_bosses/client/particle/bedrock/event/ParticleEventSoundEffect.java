/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.event;

import com.google.gson.JsonObject;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.unusual.block_factorys_bosses.client.particle.bedrock.AbstractBedrockParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.event.ParticleEvent;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleEventSoundEffect(ResourceLocation soundEffectIdentifier) implements ParticleEvent
{
    @Override
    public void trigger(AbstractBedrockParticle particle) {
        particle.getLevel().playSound(null, particle.getX(), particle.getY(), particle.getZ(), SoundEvent.createFixedRangeEvent((ResourceLocation)this.soundEffectIdentifier(), (float)32.0f), SoundSource.AMBIENT);
    }

    public static ParticleEventSoundEffect deserialize(JsonObject json) {
        JsonObject soundEffectJson = json.getAsJsonObject("sound_effect");
        return new ParticleEventSoundEffect(ResourceLocation.parse((String)soundEffectJson.get("event_name").getAsString()));
    }
}

