/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
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
 *  org.joml.Vector3f
 */
package net.unusual.block_factorys_bosses.client.particle.knight;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
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
import net.unusual.block_factorys_bosses.client.particle.util.SubParticle;
import net.unusual.block_factorys_bosses.client.particle.util.SubSteadyParticleEmitter;
import org.joml.Vector3f;

@OnlyIn(value=Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PhaseTransition6Low
extends SubSteadyParticleEmitter {
    private static final ColorCurve COLOR_CURVE = new ColorCurve(new ColorCurve.Point[]{new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(60159), 0.0f), new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-16716801), 0.21f), new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(1073807303), 0.51f), new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(60671), 1.0f)}, (subParticle, partial) -> Float.valueOf(((float)subParticle.age + partial.floatValue()) / (float)subParticle.lifeTime));

    public static Provider provider(SpriteSet spriteSet) {
        return new Provider(spriteSet);
    }

    protected PhaseTransition6Low(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z, spriteSet, 50, 15, 100);
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
        return 20;
    }

    @Override
    protected Vector3f getSubSizeVector(SubParticle subParticle, float partialTick, float ageInSeconds) {
        return new Vector3f(0.25f, 0.5f, 1.0f);
    }

    @Override
    protected ColorCurve.FloatRGBA getSubColor(SubParticle subParticle, float partialTick, float ageInSeconds) {
        return COLOR_CURVE.get(subParticle, partialTick);
    }

    @Override
    protected SubParticle makeParticle() {
        Vec3 pos;
        SubParticle subParticle = super.makeParticle();
        Vector3f halfDimensions = new Vector3f(0.2f, 0.5f, 0.2f);
        subParticle.pos = pos = new Vec3((double)Mth.randomBetween((RandomSource)this.random, (float)(-halfDimensions.x), (float)halfDimensions.x), (double)Mth.randomBetween((RandomSource)this.random, (float)(-halfDimensions.y), (float)halfDimensions.y), (double)Mth.randomBetween((RandomSource)this.random, (float)(-halfDimensions.z), (float)halfDimensions.z)).add(0.0, 0.85, 0.0);
        subParticle.pos0 = pos;
        subParticle.linearAcceleration = new Vec3(0.0, -1.0, 0.0);
        subParticle.setVelocity(pos, subParticle.random2 * 0.5f);
        return subParticle;
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new PhaseTransition6Low(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

