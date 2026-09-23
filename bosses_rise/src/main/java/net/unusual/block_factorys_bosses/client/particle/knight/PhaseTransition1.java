/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.particle.ParticleProvider
 *  net.minecraft.client.particle.SpriteSet
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package net.unusual.block_factorys_bosses.client.particle.knight;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
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
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;

@OnlyIn(value=Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PhaseTransition1
extends SubSteadyParticleEmitter {
    private static final LinearCurve X_FACTOR = new LinearCurve(new float[]{0.0f, 1.0f, 1.1f, 1.07f, 1.0f}, (subParticle, partial) -> Float.valueOf(((float)subParticle.age + partial.floatValue()) / (float)subParticle.lifeTime), (subParticle, partial) -> Float.valueOf(1.0f));
    private static final ColorCurve COLOR_CURVE = new ColorCurve(new ColorCurve.Point[]{new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(0xFFFFFF), 0.0f), new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-16713473), 0.31f), new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(0xFFFFFF), 1.0f)}, (subParticle, partial) -> Float.valueOf(((float)subParticle.age + partial.floatValue()) / (float)subParticle.lifeTime));

    public static Provider provider(SpriteSet spriteSet) {
        return new Provider(spriteSet);
    }

    protected PhaseTransition1(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z, spriteSet, 60, 5, 100);
        this.setSize(0.6f, 0.6f);
        this.gravity = 0.0f;
        this.hasPhysics = false;
    }

    @Override
    protected int getSubParticleLifeTime() {
        return Mth.randomBetweenInclusive((RandomSource)this.random, (int)40, (int)60);
    }

    @Override
    protected float getSubSize(SubParticle subParticle, float partialTick, float ageInSeconds) {
        return X_FACTOR.get(subParticle, partialTick) * 0.6f;
    }

    @Override
    protected ColorCurve.FloatRGBA getSubColor(SubParticle subParticle, float partialTick, float ageInSeconds) {
        return COLOR_CURVE.get(subParticle, partialTick);
    }

    @Override
    protected SubParticle makeParticle() {
        Vec3 pos;
        SubParticle subParticle = new SubParticle(this, this, this.set, this.getSubParticleLifeTime(), this.random){

            @Override
            public Vec3 getLinearAcceleration() {
                return new Vec3(Math.cos((float)this.age * 360.0f) * 5.0, 3.0, Math.sin((float)this.age * 360.0f) * 5.0);
            }

            @Override
            protected List<Pair<Float, BiConsumer<SubParticle, Float>>> createLifetimeEvents() {
                ArrayList<Pair<Float, BiConsumer<SubParticle, Float>>> pairs = new ArrayList<Pair<Float, BiConsumer<SubParticle, Float>>>();
                for (float f = 0.0f; f <= 2.7f; f += 0.01f) {
                    pairs.add(Pair.of(f, this::event));
                }
                return pairs;
            }

            private void event(SubParticle sub, Float partialTick) {
                Vec3 pos = this.parent.getActualSubPos(sub, partialTick.floatValue(), ((float)this.age + partialTick.floatValue()) / 20.0f);
                this.parent.getLevel().addParticle((ParticleOptions)BossesRiseParticleTypes.KNIGHT_INTRODUCTION_ATTACK_EVENT.get(), pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
            }
        };
        float angle = this.random.nextFloat() * 360.0f * ((float)Math.PI / 180);
        subParticle.pos = pos = new Vec3(4.0, 0.0, 0.0).yRot(angle);
        subParticle.pos0 = pos;
        subParticle.setVelocity(pos, -subParticle.random2);
        return subParticle;
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new PhaseTransition1(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

