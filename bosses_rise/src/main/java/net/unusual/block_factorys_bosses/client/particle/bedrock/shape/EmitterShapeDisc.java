/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.phys.Vec3
 *  org.joml.Matrix4x3f
 *  org.joml.Vector3f
 *  org.joml.Vector3fc
 *  software.bernie.geckolib.loading.math.MathValue
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.shape;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
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
import org.joml.Matrix4x3f;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import software.bernie.geckolib.loading.math.MathValue;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record EmitterShapeDisc(ParticleVector offset, ParticleDirection direction, ParticleVector planeNormal, MathValue radius, boolean surfaceOnly) implements EmitterShape
{
    @Override
    public void getSpawnPosition(BedrockParticleEmitter emitter, Vector3f offset, Vector3f direction) {
        RandomSource random = emitter.getLevel().getRandom();
        offset.set(random.nextGaussian(), random.nextGaussian(), 0.0);
        float distance = (float)this.radius.get();
        if (!this.surfaceOnly) {
            distance *= random.nextFloat();
        }
        offset.normalize(distance);
        Vector3f planeNormal = this.planeNormal.getNewVector();
        float normalDotUp = planeNormal.dot(0.0f, 1.0f, 0.0f);
        if (normalDotUp > 0.999999f) {
            offset.set(offset.x(), -offset.z(), offset.y());
        } else if (normalDotUp < -0.999999f) {
            offset.set(offset.x(), offset.z(), -offset.y());
        } else {
            Matrix4x3f toNormalSpace = new Matrix4x3f().setLookAlong(planeNormal.x(), planeNormal.y(), planeNormal.z(), 0.0f, 1.0f, 0.0f);
            toNormalSpace.transformDirection(offset);
        }
        this.direction.getDirection(offset, direction);
        offset.add((Vector3fc)this.offset.getNewVector());
    }

    public static EmitterShapeDisc deserialize(JsonObject component) {
        ParticleVector planeNormal;
        JsonPrimitive planeNormalPrimitive;
        JsonElement offset = component.get("offset");
        JsonElement direction = component.get("direction");
        JsonElement planeNormalElement = component.get("plane_normal");
        MathValue radius = BedrockParticleEffect.parseMolangJson(component.get("radius"), 1.0);
        JsonElement surfaceOnly = component.get("surface_only");
        if (planeNormalElement instanceof JsonPrimitive && (planeNormalPrimitive = (JsonPrimitive)planeNormalElement).isString()) {
            String planeNormalString;
            planeNormal = switch (planeNormalString = planeNormalPrimitive.getAsString()) {
                case "x" -> ParticleVector.of(new Vec3(1.0, 0.0, 0.0));
                case "y" -> ParticleVector.of(new Vec3(0.0, 1.0, 0.0));
                case "z" -> ParticleVector.of(new Vec3(0.0, 0.0, 1.0));
                default -> throw new IllegalArgumentException("Unknown plane_normal constant: " + planeNormalString);
            };
        } else {
            planeNormal = ParticleVector.deserialize(planeNormalElement, new Vec3(0.0, 1.0, 0.0));
        }
        return new EmitterShapeDisc(ParticleVector.deserialize(offset, new Vec3(0.0, 0.0, 0.0)), ParticleDirection.deserialize(direction), planeNormal, radius, surfaceOnly != null && surfaceOnly.getAsBoolean());
    }
}

