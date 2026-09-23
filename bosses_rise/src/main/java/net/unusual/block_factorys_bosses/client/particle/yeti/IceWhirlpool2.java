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
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  org.joml.Quaternionf
 */
package net.unusual.block_factorys_bosses.client.particle.yeti;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.particle.util.ColorCurve;
import net.unusual.block_factorys_bosses.client.particle.util.LinearCurve;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticle;
import net.unusual.block_factorys_bosses.client.particle.util.SubSteadyParticleEmitter;
import org.joml.Quaternionf;

@OnlyIn(value=Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class IceWhirlpool2
extends SubSteadyParticleEmitter {
    private static final LinearCurve C_FACTOR = new LinearCurve(new float[]{1.0f, 0.25f, 1.0f, 1.04f, 1.06f, 1.05f, 1.0f, 0.95f, 0.88f, 0.75f, 0.56f, 0.29f}, (subParticle, partial) -> Float.valueOf(subParticle.random2), (subParticle, partial) -> Float.valueOf((float)subParticle.lifeTime / 20.0f));
    private static final LinearCurve A_FACTOR = new LinearCurve(new float[]{0.0f, 0.47f, 0.8f, 0.95f, 1.09f, 1.14f, 1.15f, 1.11f, 1.0f, 0.78f, 0.55f, 0.32f, 0.0f}, (subParticle, partial) -> Float.valueOf(subParticle.random2), (subParticle, partial) -> Float.valueOf((float)subParticle.lifeTime / 20.0f));
    private static final LinearCurve X_FACTOR = new LinearCurve(new float[]{1.0f, 1.0f, 0.65f, 0.0f}, (subParticle, partial) -> Float.valueOf(((float)subParticle.age + partial.floatValue()) / (float)subParticle.lifeTime), (subParticle, partial) -> Float.valueOf((float)subParticle.lifeTime / 20.0f));
    private static final LinearCurve V_FACTOR = new LinearCurve(new float[]{2.86f, 1.82f, 1.0f, 1.0f, 1.0f}, (subParticle, partial) -> Float.valueOf(((float)subParticle.age + partial.floatValue()) / (float)subParticle.lifeTime), (subParticle, partial) -> Float.valueOf((float)subParticle.lifeTime / 20.0f));
    private static final LinearCurve RANDOM_FACTOR = new LinearCurve(new float[]{0.44f, 1.47f, 0.64f, 0.59f}, (subParticle, partial) -> Float.valueOf(subParticle.random2), (subParticle, partial) -> Float.valueOf(1.0f));
    private static final ColorCurve COLOR_CURVE = new ColorCurve(new ColorCurve.Point[]{new ColorCurve.Point(new ColorCurve.FloatRGBA(1.0f, 1.0f, 1.0f, 0.25f), 0.0f), new ColorCurve.Point(new ColorCurve.FloatRGBA(0.0f, 1.0f, 0.97f, 1.0f), 0.46f), new ColorCurve.Point(new ColorCurve.FloatRGBA(1.0f, 1.0f, 1.0f, 0.5f), 1.0f)}, (subParticle, partial) -> Float.valueOf(((float)subParticle.age + partial.floatValue()) / (float)subParticle.lifeTime));

    public static IceWhirlpool2Provider provider(SpriteSet spriteSet) {
        return new IceWhirlpool2Provider(spriteSet);
    }

    protected IceWhirlpool2(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z, spriteSet, 20, 30, 300);
        this.setSize(0.6f, 0.6f);
        this.gravity = 0.0f;
        this.hasPhysics = false;
        this.xd = vx * 0.4;
        this.yd = vy * 0.4;
        this.zd = vz * 0.4;
    }

    @Override
    protected int getSubParticleLifeTime() {
        return 40;
    }

    @Override
    protected double getSubX(SubParticle subParticle, float partialTick, float ageInSeconds) {
        return Math.cos(180.0f * ageInSeconds / 20.0f * this.randomValue + subParticle.random4 * 720.0f) * (double)C_FACTOR.get(subParticle, partialTick) * (double)V_FACTOR.get(subParticle, partialTick) * 2.0;
    }

    @Override
    protected double getSubY(SubParticle subParticle, float partialTick, float ageInSeconds) {
        return subParticle.random1 * 2.0f * ageInSeconds * 3.0f * C_FACTOR.get(subParticle, partialTick) * 20.0f;
    }

    @Override
    protected double getSubZ(SubParticle subParticle, float partialTick, float ageInSeconds) {
        return Math.sin(180.0f * ageInSeconds / 20.0f * this.randomValue + subParticle.random4 * 720.0f) * (double)C_FACTOR.get(subParticle, partialTick) * (double)V_FACTOR.get(subParticle, partialTick) * 2.0;
    }

    @Override
    protected float getSubSize(SubParticle subParticle, float partialTick, float ageInSeconds) {
        return X_FACTOR.get(subParticle, partialTick) * 0.2f;
    }

    @Override
    protected ColorCurve.FloatRGBA getSubColor(SubParticle subParticle, float partialTick, float ageInSeconds) {
        return COLOR_CURVE.get(subParticle, partialTick);
    }

    @Override
    protected Quaternionf getQuaternion(SubParticle subParticle, Camera camera, float partialTick, float ageInSeconds) {
        Quaternionf quaternion = new Quaternionf();
        this.getFacingCameraMode().setRotation(quaternion, camera, partialTick);
        quaternion.rotateZ(subParticle.random2 * 360.0f + ageInSeconds * 30.0f * subParticle.random2);
        return quaternion;
    }

    public static class IceWhirlpool2Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public IceWhirlpool2Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new IceWhirlpool2(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

