/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.event;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.AbstractBedrockParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.event.ParticleEvent;
import net.unusual.block_factorys_bosses.random.WeightedPool;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleEventRandomize(WeightedPool<ParticleEvent> events) implements ParticleEvent
{
    @Override
    public void trigger(AbstractBedrockParticle particle) {
        ParticleEvent event = this.events().selectRandom();
        event.trigger(particle);
    }

    public static ParticleEventRandomize deserialize(JsonObject json) {
        JsonArray randomizeJsonArray = json.getAsJsonArray("randomize");
        ArrayList options = new ArrayList(randomizeJsonArray.size());
        for (JsonElement eventJsonElement : randomizeJsonArray) {
            JsonObject eventJson = eventJsonElement.getAsJsonObject();
            int weight = eventJson.getAsJsonPrimitive("weight").getAsInt();
            ParticleEvent event = ParticleEvent.deserialize(eventJson);
            options.add(new WeightedPool.PoolOption<ParticleEvent>(weight, event));
        }
        return new ParticleEventRandomize(new WeightedPool<ParticleEvent>(options));
    }
}

