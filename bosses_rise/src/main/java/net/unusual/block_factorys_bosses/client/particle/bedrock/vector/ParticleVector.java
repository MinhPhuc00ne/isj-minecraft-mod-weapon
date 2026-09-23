/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  javax.annotation.Nullable
 *  net.minecraft.world.phys.Vec3
 *  org.joml.Vector3f
 *  software.bernie.geckolib.loading.math.MathParser
 *  software.bernie.geckolib.loading.math.MathValue
 *  software.bernie.geckolib.loading.math.value.Constant
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import javax.annotation.Nullable;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.client.particle.bedrock.vector.ParticleDirection;
import org.joml.Vector3f;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.value.Constant;

public record ParticleVector(MathValue x, MathValue y, MathValue z) implements ParticleDirection
{
    @Override
    public void getDirection(Vector3f offsetInput, Vector3f direction) {
        this.getVector(direction);
    }

    public void getVector(Vector3f vector) {
        vector.set(this.x.get(), this.y.get(), this.z.get());
    }

    public Vector3f getNewVector() {
        Vector3f vector = new Vector3f();
        this.getVector(vector);
        return vector;
    }

    public static ParticleVector deserialize(JsonElement vectorJson) {
        if (vectorJson == null) {
            throw new IllegalArgumentException("Received a null vector.");
        }
        if (!(vectorJson instanceof JsonArray)) {
            throw new IllegalArgumentException("Vector must be an array.");
        }
        JsonArray jsonArray = (JsonArray)vectorJson;
        if (jsonArray.size() != 3) {
            throw new IllegalArgumentException("Vector array must have 3 entries.");
        }
        return new ParticleVector(MathParser.parseJson((JsonElement)jsonArray.get(0)), MathParser.parseJson((JsonElement)jsonArray.get(1)), MathParser.parseJson((JsonElement)jsonArray.get(2)));
    }

    @Nullable
    public static ParticleVector deserializeOrNull(@Nullable JsonElement vectorJson) {
        if (vectorJson == null) {
            return null;
        }
        return ParticleVector.deserialize(vectorJson);
    }

    public static ParticleVector deserialize(@Nullable JsonElement vectorJson, Vec3 defaultVector) {
        if (vectorJson == null) {
            return ParticleVector.of(defaultVector);
        }
        return ParticleVector.deserialize(vectorJson);
    }

    public static ParticleVector of(Vec3 vector) {
        return new ParticleVector((MathValue)new Constant(vector.x()), (MathValue)new Constant(vector.y()), (MathValue)new Constant(vector.z()));
    }
}

