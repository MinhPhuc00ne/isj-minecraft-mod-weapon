/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonPrimitive
 *  org.joml.Vector3f
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.unusual.block_factorys_bosses.client.particle.bedrock.vector.InwardParticleDirection;
import net.unusual.block_factorys_bosses.client.particle.bedrock.vector.OutwardParticleDirection;
import net.unusual.block_factorys_bosses.client.particle.bedrock.vector.ParticleVector;
import org.joml.Vector3f;

public interface ParticleDirection {
    public void getDirection(Vector3f var1, Vector3f var2);

    public static ParticleDirection deserialize(JsonElement directionJson) {
        JsonPrimitive jsonPrimitive;
        if (directionJson instanceof JsonPrimitive && (jsonPrimitive = (JsonPrimitive)directionJson).isString()) {
            String directionString = jsonPrimitive.getAsString();
            if (directionString.equals("inwards")) {
                return InwardParticleDirection.INSTANCE;
            }
            if (directionString.equals("outwards")) {
                return OutwardParticleDirection.INSTANCE;
            }
        } else if (directionJson instanceof JsonArray) {
            JsonArray jsonArray = (JsonArray)directionJson;
            return ParticleVector.deserialize((JsonElement)jsonArray);
        }
        throw new IllegalArgumentException("Unknown shape direction: " + String.valueOf(directionJson));
    }
}

