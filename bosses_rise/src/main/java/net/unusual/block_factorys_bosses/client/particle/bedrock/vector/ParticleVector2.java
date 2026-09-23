/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.phys.Vec2
 *  org.joml.Vector2f
 *  software.bernie.geckolib.loading.math.MathParser
 *  software.bernie.geckolib.loading.math.MathValue
 *  software.bernie.geckolib.loading.math.value.Constant
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.phys.Vec2;
import org.joml.Vector2f;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.value.Constant;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleVector2(MathValue x, MathValue y) {
    public void getVector(Vector2f vector) {
        vector.set(this.x.get(), this.y.get());
    }

    public Vector2f getNewVector() {
        Vector2f vector = new Vector2f();
        this.getVector(vector);
        return vector;
    }

    public static ParticleVector2 deserialize(JsonElement vectorJson) {
        if (vectorJson == null) {
            throw new IllegalArgumentException("Received a null vector.");
        }
        if (!(vectorJson instanceof JsonArray)) {
            throw new IllegalArgumentException("Vector must be an array.");
        }
        JsonArray jsonArray = (JsonArray)vectorJson;
        if (jsonArray.size() != 2) {
            throw new IllegalArgumentException("Vector array must have 2 entries.");
        }
        return new ParticleVector2(MathParser.parseJson((JsonElement)jsonArray.get(0)), MathParser.parseJson((JsonElement)jsonArray.get(1)));
    }

    public static ParticleVector2 deserialize(@Nullable JsonElement vectorJson, Vec2 defaultVector) {
        if (vectorJson == null) {
            return ParticleVector2.of(defaultVector);
        }
        return ParticleVector2.deserialize(vectorJson);
    }

    public static ParticleVector2 of(Vec2 vector) {
        return new ParticleVector2((MathValue)new Constant((double)vector.x), (MathValue)new Constant((double)vector.y));
    }
}

