/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.joml.Vector3f
 *  org.joml.Vector3fc
 *  software.bernie.geckolib.loading.math.MathValue
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.motion;

import com.google.gson.JsonObject;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.client.particle.bedrock.AbstractBedrockParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffect;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import software.bernie.geckolib.loading.math.MathValue;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ParticleMotionCollision(MathValue enabled, float coefficientOfRestitution, float collisionDrag, float collisionRadius, boolean expireOnContact) {
    public void initialize(AbstractBedrockParticle particle) {
        particle.setSize(this.collisionRadius(), this.collisionRadius());
    }

    public Vec3 move(AbstractBedrockParticle particle, Vec3 motion) {
        boolean collided;
        if (this.enabled().get() == 0.0) {
            return motion;
        }
        Vec3 restrictedMotion = Entity.collideBoundingBox(null, (Vec3)motion, (AABB)particle.getBoundingBox(), (Level)particle.getLevel(), List.of());
        boolean bl = collided = !restrictedMotion.equals((Object)motion);
        if (!collided) {
            return motion;
        }
        if (this.expireOnContact()) {
            particle.remove();
            return motion;
        }
        if (this.coefficientOfRestitution() > 0.0f) {
            Vec3 collisionNormal = restrictedMotion.subtract(motion).normalize();
            Vector3f velocity = particle.getVelocity();
            Vec3 impulse = collisionNormal.scale((double)(velocity.length() * (1.0f + this.coefficientOfRestitution())));
            velocity.add((Vector3fc)impulse.toVector3f());
            particle.setVelocity(velocity);
        }
        if (this.collisionDrag() > 0.0f) {
            restrictedMotion = restrictedMotion.length() > (double)this.collisionDrag() ? restrictedMotion.subtract(restrictedMotion.normalize().scale((double)this.collisionDrag())) : Vec3.ZERO;
        }
        return restrictedMotion;
    }

    @Nullable
    public static ParticleMotionCollision deserializeFromComponents(JsonObject allComponents) {
        JsonObject componentJson = allComponents.getAsJsonObject("minecraft:particle_motion_collision");
        return componentJson == null ? null : ParticleMotionCollision.deserialize(componentJson);
    }

    private static float getFloatOrDefault(JsonObject json, String key, float defaultValue) {
        if (!json.has(key)) {
            return defaultValue;
        }
        return json.get(key).getAsFloat();
    }

    private static boolean getBooleanOrDefault(JsonObject json, String key, boolean defaultValue) {
        if (!json.has(key)) {
            return defaultValue;
        }
        return json.get(key).getAsBoolean();
    }

    public static ParticleMotionCollision deserialize(JsonObject component) {
        return new ParticleMotionCollision(BedrockParticleEffect.parseMolangJson(component.get("enabled"), 1.0), ParticleMotionCollision.getFloatOrDefault(component, "coefficient_of_restitution", 0.0f), ParticleMotionCollision.getFloatOrDefault(component, "collision_drag", 0.0f), ParticleMotionCollision.getFloatOrDefault(component, "collision_radius", 0.1f), ParticleMotionCollision.getBooleanOrDefault(component, "expire_on_contact", false));
    }
}

