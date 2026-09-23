/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.phys.Vec3
 *  org.joml.Vector3f
 *  org.joml.Vector3fc
 *  software.bernie.geckolib.loading.math.MathValue
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.shape;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffect;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEmitter;
import net.unusual.block_factorys_bosses.client.particle.bedrock.shape.EmitterShape;
import net.unusual.block_factorys_bosses.client.particle.bedrock.vector.ParticleDirection;
import net.unusual.block_factorys_bosses.client.particle.bedrock.vector.ParticleVector;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import software.bernie.geckolib.loading.math.MathValue;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record EmitterShapeSphere(ParticleVector offset, ParticleDirection direction, MathValue radius, boolean surfaceOnly) implements EmitterShape
{
    @Override
    public void getSpawnPosition(BedrockParticleEmitter emitter, Vector3f offset, Vector3f direction) {
        RandomSource random = emitter.getLevel().getRandom();
        offset.set(random.nextGaussian(), random.nextGaussian(), random.nextGaussian());
        float distance = (float)this.radius.get();
        if (!this.surfaceOnly) {
            distance *= random.nextFloat();
        }
        offset.normalize(distance);
        this.direction.getDirection(offset, direction);
        offset.add((Vector3fc)this.offset.getNewVector());
    }

    public static EmitterShapeSphere deserialize(JsonObject component) {
        JsonElement offset = component.get("offset");
        JsonElement direction = component.get("direction");
        MathValue radius = BedrockParticleEffect.parseMolangJson(component.get("radius"), 1.0);
        JsonElement surfaceOnly = component.get("surface_only");
        return new EmitterShapeSphere(ParticleVector.deserialize(offset, new Vec3(0.0, 0.0, 0.0)), ParticleDirection.deserialize(direction), radius, surfaceOnly == null ? false : surfaceOnly.getAsBoolean());
    }
}

