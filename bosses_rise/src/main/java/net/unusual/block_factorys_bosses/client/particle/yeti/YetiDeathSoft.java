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
public class YetiDeathSoft
extends SubSteadyParticleEmitter {
    private static final LinearCurve GROW_FACTOR = new LinearCurve(new float[]{0.67f, 0.86f, 1.0f, 1.0f, 1.0f, 1.0f, 0.84f, 0.55f, 0.0f}, (subParticle, partial) -> Float.valueOf(((float)subParticle.age + partial.floatValue()) / (float)subParticle.lifeTime), (subParticle, partial) -> Float.valueOf((float)subParticle.lifeTime / 20.0f));
    private static final ColorCurve COLOR_CURVE = new ColorCurve(new ColorCurve.Point[]{new ColorCurve.Point(new ColorCurve.FloatRGBA(0.73f, 0.94f, 1.0f, 1.0f), 0.0f), new ColorCurve.Point(new ColorCurve.FloatRGBA(0.77f, 0.94f, 1.0f, 0.42f), 0.07f), new ColorCurve.Point(new ColorCurve.FloatRGBA(0.63f, 0.85f, 0.93f, 0.0f), 0.23f), new ColorCurve.Point(new ColorCurve.FloatRGBA(0.63f, 0.85f, 0.93f, 0.0f), 1.0f)}, (subParticle, partial) -> Float.valueOf(((float)subParticle.age + partial.floatValue()) / (float)subParticle.lifeTime + subParticle.random2));

    public static YetiDeathSoftProvider provider(SpriteSet spriteSet) {
        return new YetiDeathSoftProvider(spriteSet);
    }

    protected YetiDeathSoft(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z, spriteSet, 160, 30, 100);
        this.setSize(0.6f, 0.6f);
        this.gravity = 0.0f;
        this.hasPhysics = false;
        this.xd = vx * 0.4;
        this.yd = vy * 0.4;
        this.zd = vz * 0.4;
    }

    @Override
    protected int getSubParticleLifeTime() {
        return Mth.randomBetweenInclusive((RandomSource)this.random, (int)40, (int)120);
    }

    @Override
    protected float getSubSize(SubParticle subParticle, float partialTick, float ageInSeconds) {
        return (float)(4.0 * (0.7 + (double)subParticle.random1 * 0.6) * (double)GROW_FACTOR.get(subParticle, partialTick));
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

    @Override
    protected SubParticle makeParticle() {
        Vec3 pos;
        SubParticle subParticle = super.makeParticle();
        float angle = this.random.nextFloat() * 360.0f * ((float)Math.PI / 180);
        subParticle.pos = pos = new Vec3(4.0, 0.0, 0.0).yRot(angle);
        subParticle.pos0 = pos;
        subParticle.linearAcceleration = new Vec3((double)Mth.randomBetween((RandomSource)this.random, (float)-2.0f, (float)2.0f), -5.0, (double)Mth.randomBetween((RandomSource)this.random, (float)-2.0f, (float)2.0f));
        subParticle.linearDragCoefficient = 1.5;
        subParticle.setVelocity(new Vec3((double)0.1f, 1.0, 0.0).yRot(angle), Mth.randomBetween((RandomSource)this.random, (float)2.0f, (float)32.0f));
        return subParticle;
    }

    public static class YetiDeathSoftProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public YetiDeathSoftProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new YetiDeathSoft(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

