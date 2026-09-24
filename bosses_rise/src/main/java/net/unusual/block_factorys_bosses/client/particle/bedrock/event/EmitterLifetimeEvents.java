/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.event;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.lang.runtime.SwitchBootstraps;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEmitter;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record EmitterLifetimeEvents(@Nullable List<String> creationEvents, @Nullable List<String> expirationEvents, @Nullable List<TimelineEvent> timelineEvents) {
    public void tick(BedrockParticleEmitter emitter) {
        if (this.timelineEvents() != null) {
            float time = emitter.elapsedCycleSeconds;
            float previousTime = time - 0.05f;
            for (TimelineEvent timelineEvent : this.timelineEvents()) {
                if (previousTime > timelineEvent.time()) break;
                if (!(timelineEvent.time() <= time) || !(timelineEvent.time() > previousTime)) continue;
                this.triggerEvents(emitter, timelineEvent.events());
            }
        }
    }

    public void triggerCreationEvents(BedrockParticleEmitter emitter) {
        this.triggerEvents(emitter, this.creationEvents());
    }

    public void triggerExpirationEvents(BedrockParticleEmitter emitter) {
        this.triggerEvents(emitter, this.expirationEvents());
    }

    private void triggerEvents(BedrockParticleEmitter emitter, @Nullable List<String> eventNames) {
        if (eventNames == null) {
            return;
        }
        for (String eventName : eventNames) {
            emitter.triggerParticleEvent(eventName);
        }
    }

    public static EmitterLifetimeEvents deserializeFromComponents(JsonObject allComponents) {
        JsonElement json = allComponents.get("minecraft:emitter_lifetime_events");
        if (json != null) {
            return EmitterLifetimeEvents.deserialize(json.getAsJsonObject());
        }
        return new EmitterLifetimeEvents(null, null, null);
    }

    @Nullable
    private static List<String> parseEventsOrNull(@Nullable JsonElement eventsJson) {
        if (eventsJson == null) {
            return null;
        }
        if (eventsJson.isJsonArray()) {
            return eventsJson.getAsJsonArray().asList().stream().map(JsonElement::getAsString).toList();
        }
        if (eventsJson.isJsonPrimitive() && eventsJson.getAsJsonPrimitive().isString()) {
            return List.of(eventsJson.getAsString());
        }
        throw new IllegalArgumentException("Unknown event names element: " + String.valueOf(eventsJson));
    }

    private static List<String> parseEvents(@Nullable JsonElement eventsJson) {
        List<String> events = EmitterLifetimeEvents.parseEventsOrNull(eventsJson);
        if (events == null) {
            return List.of();
        }
        return events;
    }

    static EmitterLifetimeEvents deserialize(JsonObject json) {
        List<TimelineEvent> list;
        List<String> list2 = EmitterLifetimeEvents.parseEventsOrNull(json.get("creation_event"));
        List<String> list3 = EmitterLifetimeEvents.parseEventsOrNull(json.get("expiration_event"));
        JsonElement jsonElement = json.get("timeline");
        if (jsonElement instanceof JsonObject) {
            JsonObject jsonObject = (JsonObject)jsonElement;
            list = TimelineEvent.deserialize(jsonObject);
        } else {
            list = null;
        }
        return new EmitterLifetimeEvents(list2, list3, list);
    }

    public record TimelineEvent(float time, List<String> events) {
        public static List<TimelineEvent> deserialize(JsonObject timelineJson) {
            return timelineJson.entrySet().stream().map(entry -> new TimelineEvent(Float.parseFloat((String)entry.getKey()), EmitterLifetimeEvents.parseEvents((JsonElement)entry.getValue()))).sorted((a, b) -> Float.compare(a.time(), b.time())).toList();
        }
    }
}

