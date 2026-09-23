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
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.particle.util.ColorCurve;
import net.unusual.block_factorys_bosses.client.particle.util.LinearCurve;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticle;
import net.unusual.block_factorys_bosses.client.particle.util.SubSteadyParticleEmitter;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(value=Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PhaseTransition4
extends SubSteadyParticleEmitter {
    private static final LinearCurve R_FACTOR = new LinearCurve(new float[]{0.54f, 1.0f, 0.54f}, (subParticle, partial) -> Float.valueOf(subParticle.random2), (subParticle, partial) -> Float.valueOf(1.0f));
    private static final LinearCurve SCALE_FACTOR = new LinearCurve(new float[]{1.0f, 1.0f, 1.0f, 1.08f, 0.59f, 0.0f, 0.0f}, (subParticle, partial) -> Float.valueOf(((float)subParticle.age + partial.floatValue()) / (float)subParticle.lifeTime), (subParticle, partial) -> Float.valueOf(1.0f));
    private static final ColorCurve COLOR_CURVE = new ColorCurve(new ColorCurve.Point[]{new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(65471), 0.0f), new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-486473780), 0.24f), new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(50687), 0.91f), new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(50687), 1.0f)}, (subParticle, partial) -> Float.valueOf(((float)subParticle.age + partial.floatValue()) / (float)subParticle.lifeTime));

    public static Provider provider(SpriteSet spriteSet) {
        return new Provider(spriteSet);
    }

    protected PhaseTransition4(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z, spriteSet, 300, 20, 100);
        this.setSize(0.6f, 0.6f);
        this.gravity = 0.0f;
        this.hasPhysics = false;
    }

    @Override
    public boolean shouldAttach() {
        return true;
    }

    @Override
    protected int getSubParticleLifeTime() {
        return 40;
    }

    @Override
    protected Vector3f getSubSizeVector(SubParticle subParticle, float partialTick, float ageInSeconds) {
        return new Vector3f(R_FACTOR.get(subParticle, partialTick) * SCALE_FACTOR.get(subParticle, partialTick) * 0.7f, R_FACTOR.get(subParticle, partialTick) * SCALE_FACTOR.get(subParticle, partialTick) * 0.6f, 1.0f);
    }

    @Override
    protected ColorCurve.FloatRGBA getSubColor(SubParticle subParticle, float partialTick, float ageInSeconds) {
        return COLOR_CURVE.get(subParticle, partialTick);
    }

    @Override
    protected Quaternionf getQuaternion(SubParticle subParticle, Camera camera, float partialTick, float ageInSeconds) {
        Quaternionf quaternion = new Quaternionf();
        this.getFacingCameraMode().setRotation(quaternion, camera, partialTick);
        quaternion.rotateZ(subParticle.random2 * 360.0f);
        return quaternion;
    }

    @Override
    protected SubParticle makeParticle() {
        Vec3 pos;
        SubParticle subParticle = new SubParticle(this, this, this.set, this.getSubParticleLifeTime(), this.random){

            @Override
            protected void tickSprite() {
                this.sprite = this.set.get(this.random2 <= 0.5f ? this.age : this.age + this.lifeTime, this.lifeTime * 2);
            }
        };
        double distance = 1.0;
        double theta = this.random.nextDouble() * Math.PI;
        double phi = this.random.nextDouble() * 2.0 * Math.PI;
        subParticle.pos = pos = new Vec3(distance * Math.sin(theta) * Math.cos(phi), distance * Math.sin(theta) * Math.sin(phi), distance * Math.cos(theta));
        subParticle.pos0 = pos;
        subParticle.linearDragCoefficient = 3.0;
        subParticle.setVelocity(pos, -0.1f);
        return subParticle;
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new PhaseTransition4(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

