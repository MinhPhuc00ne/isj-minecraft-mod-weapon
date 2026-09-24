/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Camera
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.particle.ParticleProvider
 *  net.minecraft.client.particle.SpriteSet
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 */
package net.unusual.block_factorys_bosses.client.particle.knight;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.particle.util.ColorCurve;
import net.unusual.block_factorys_bosses.client.particle.util.LinearCurve;
import net.unusual.block_factorys_bosses.client.particle.util.SubInstantParticleEmitter;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticle;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(value=Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MarkGlintExp2
extends SubInstantParticleEmitter {
    private static final LinearCurve X_FACTOR = new LinearCurve(new float[]{4.95f, 0.88f, 1.0f}, (subParticle, partial) -> Float.valueOf(((float)subParticle.age + partial.floatValue()) / (float)subParticle.lifeTime), (subParticle, partial) -> Float.valueOf(1.0f));
    private static final ColorCurve COLOR_CURVE = new ColorCurve(new ColorCurve.Point[]{new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-1), 0.0f), new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-1), 0.27f), new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-29440), 0.4f), new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(0xFF0000), 1.0f)}, (subParticle, partial) -> Float.valueOf(((float)subParticle.age + partial.floatValue()) / (float)subParticle.lifeTime));

    public static Provider provider(SpriteSet spriteSet) {
        return new Provider(spriteSet);
    }

    protected MarkGlintExp2(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, spriteSet, new Vec3(x, y, z), 12);
        this.setSize(0.6f, 0.6f);
        this.gravity = 0.0f;
        this.hasPhysics = false;
    }

    @Override
    protected int getSubParticleLifeTime() {
        return Mth.randomBetweenInclusive((RandomSource)this.random, (int)6, (int)10);
    }

    @Override
    protected Vector3f getSubSizeVector(SubParticle subParticle, float partialTick, float ageInSeconds) {
        return new Vector3f(0.16f, (1.0f - X_FACTOR.get(subParticle, partialTick)) * 0.8f, 1.0f);
    }

    @Override
    protected Quaternionf getQuaternion(SubParticle subParticle, Camera camera, float partialTick, float ageInSeconds) {
        Quaternionf quaternion = new Quaternionf();
        Vec3 vec3 = subParticle.getDirection();
        double sqrt = Math.sqrt(vec3.x * vec3.x + vec3.z * vec3.z);
        double xRot = Mth.wrapDegrees((float)((float)(-(Mth.atan2((double)vec3.y, (double)sqrt) * 57.2957763671875)) + subParticle.random3 * 360.0f));
        double yRot = Mth.wrapDegrees((float)((float)(Mth.atan2((double)vec3.z, (double)vec3.x) * 57.2957763671875)));
        quaternion.rotationYXZ((float)(Math.PI - yRot * (Math.PI / 180)), (float)(-xRot * (Math.PI / 180)), -1.5707964f);
        return quaternion;
    }

    @Override
    protected ColorCurve.FloatRGBA getSubColor(SubParticle subParticle, float partialTick, float ageInSeconds) {
        return COLOR_CURVE.get(subParticle, partialTick);
    }

    @Override
    protected SubParticle makeParticle() {
        SubParticle subParticle = new SubParticle(this, this, this.set, this.getSubParticleLifeTime(), this.random){

            @Override
            public void tick() {
                if (this.age > this.lifeTime) {
                    return;
                }
                this.tickSprite();
                this.pos0 = new Vec3(this.pos.x, this.pos.y, this.pos.z);
                Vec3 acceleration = this.getLinearAcceleration().subtract(this.velocity.scale(this.linearDragCoefficient));
                this.velocity = this.velocity.add(acceleration.scale((double)0.05f));
            }
        };
        double theta = this.random.nextDouble() * Math.PI;
        double phi = this.random.nextDouble() * 2.0 * Math.PI;
        Vec3 direction = new Vec3(Math.sin(theta) * Math.cos(phi), Math.sin(theta) * Math.sin(phi), Math.cos(theta));
        subParticle.linearDragCoefficient = 4.0;
        subParticle.setVelocity(direction, 12.0f);
        return subParticle;
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new MarkGlintExp2(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

