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
 *  net.minecraft.client.particle.SingleQuadParticle$FacingCameraMode
 *  net.minecraft.client.particle.SpriteSet
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.phys.Vec3
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
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
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
public class YetiDeathFrost
extends SubSteadyParticleEmitter {
    private static final LinearCurve GROW_FACTOR = new LinearCurve(new float[]{0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f}, (subParticle, partial) -> Float.valueOf(((float)subParticle.age + partial.floatValue()) / (float)subParticle.lifeTime), (subParticle, partial) -> Float.valueOf((float)subParticle.lifeTime / 20.0f));
    private static final ColorCurve COLOR_CURVE = new ColorCurve(new ColorCurve.Point[]{new ColorCurve.Point(new ColorCurve.FloatRGBA(1.0f, 1.0f, 1.0f, 0.0f), 0.0f), new ColorCurve.Point(new ColorCurve.FloatRGBA(0.8f, 0.91f, 0.96f, 1.0f), 0.1f), new ColorCurve.Point(new ColorCurve.FloatRGBA(0.64f, 0.77f, 0.83f, 1.0f), 0.69f), new ColorCurve.Point(new ColorCurve.FloatRGBA(0.67f, 0.83f, 0.87f, 0.0f), 0.92f), new ColorCurve.Point(new ColorCurve.FloatRGBA(0.21f, 0.03f, 0.0f, 0.0f), 1.0f)}, (subParticle, partial) -> Float.valueOf(((float)subParticle.age + partial.floatValue()) / (float)subParticle.lifeTime + subParticle.random2));

    public static YetiDeathSoftProvider provider(SpriteSet spriteSet) {
        return new YetiDeathSoftProvider(spriteSet);
    }

    protected YetiDeathFrost(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z, spriteSet, 160, 8, 100);
        this.setSize(0.6f, 0.6f);
        this.gravity = 0.0f;
        this.hasPhysics = false;
        this.xd = vx * 0.4;
        this.yd = vy * 0.4;
        this.zd = vz * 0.4;
    }

    @Override
    protected int getSubParticleLifeTime() {
        return Mth.randomBetweenInclusive((RandomSource)this.random, (int)40, (int)80);
    }

    @Override
    protected float getSubSize(SubParticle subParticle, float partialTick, float ageInSeconds) {
        return (float)(0.4 * (0.8 + (double)subParticle.random1 * 0.4));
    }

    @Override
    protected ColorCurve.FloatRGBA getSubColor(SubParticle subParticle, float partialTick, float ageInSeconds) {
        return COLOR_CURVE.get(subParticle, partialTick);
    }

    @Override
    protected Quaternionf getQuaternion(SubParticle subParticle, Camera camera, float partialTick, float ageInSeconds) {
        Quaternionf quaternion = new Quaternionf();
        SingleQuadParticle.FacingCameraMode.LOOKAT_Y.setRotation(quaternion, camera, partialTick);
        float lerp = ((float)subParticle.age + partialTick) / (float)subParticle.lifeTime;
        quaternion.rotateZ(Mth.lerp((float)lerp, (float)(subParticle.random1 * 360.0f), (float)(subParticle.random2 * 360.0f)));
        return quaternion;
    }

    @Override
    protected SubParticle makeParticle() {
        Vec3 pos;
        SubParticle subParticle = super.makeParticle();
        float angle = this.random.nextFloat() * 360.0f * ((float)Math.PI / 180);
        subParticle.pos = pos = new Vec3(4.0, -0.1, 0.0).yRot(angle);
        subParticle.pos0 = pos;
        subParticle.linearAcceleration = new Vec3((double)Mth.randomBetween((RandomSource)this.random, (float)-2.0f, (float)2.0f), -4.0, (double)Mth.randomBetween((RandomSource)this.random, (float)-2.0f, (float)2.0f));
        subParticle.linearDragCoefficient = 2.0;
        subParticle.setVelocity(new Vec3((double)0.1f, 1.0, 0.0).yRot(angle), Mth.randomBetween((RandomSource)this.random, (float)12.0f, (float)48.0f));
        return subParticle;
    }

    public static class YetiDeathSoftProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public YetiDeathSoftProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new YetiDeathFrost(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

