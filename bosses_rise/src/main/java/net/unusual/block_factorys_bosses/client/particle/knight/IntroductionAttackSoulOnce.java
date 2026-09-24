/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Camera
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.particle.ParticleProvider
 *  net.minecraft.client.particle.SpriteSet
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 */
package net.unusual.block_factorys_bosses.client.particle.knight;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.particle.util.ColorCurve;
import net.unusual.block_factorys_bosses.client.particle.util.LinearCurve;
import net.unusual.block_factorys_bosses.client.particle.util.SubInstantParticleEmitter;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticle;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(value=Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class IntroductionAttackSoulOnce
extends SubInstantParticleEmitter {
    private static final LinearCurve X_FACTOR = new LinearCurve(new float[]{1.0f, 1.0f, 1.0f, 1.0f, 0.94f, 0.86f, 0.56f}, (subParticle, partial) -> Float.valueOf(((float)subParticle.age + partial.floatValue()) / 20.0f), (subParticle, partial) -> Float.valueOf((float)subParticle.lifeTime / 20.0f));
    private static final LinearCurve S_FACTOR = new LinearCurve(new float[]{1.48f, 1.17f, 1.06f, 1.0f, 1.0f, 1.0f, 1.0f}, (subParticle, partial) -> Float.valueOf(((float)subParticle.age + partial.floatValue()) / 20.0f), (subParticle, partial) -> Float.valueOf((float)subParticle.lifeTime / 20.0f));
    private static final ColorCurve COLOR_CURVE = new ColorCurve(new ColorCurve.Point[]{new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-9699352), 0.0f), new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-615190545), 0.87f), new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(0xAAF6FF), 1.0f)}, (subParticle, partial) -> Float.valueOf(((float)subParticle.age + partial.floatValue()) / (float)subParticle.lifeTime));

    public static Provider provider(SpriteSet spriteSet) {
        return new Provider(spriteSet);
    }

    protected IntroductionAttackSoulOnce(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z, spriteSet, 1);
        this.setSize(0.6f, 0.6f);
        this.gravity = 0.0f;
        this.hasPhysics = false;
        this.xd = vx * 0.4;
        this.yd = vy * 0.4;
        this.zd = vz * 0.4;
    }

    @Override
    public boolean shouldAttach() {
        return true;
    }

    @Override
    protected int getSubParticleLifeTime() {
        return 34;
    }

    @Override
    protected Vector3f getSubSizeVector(SubParticle subParticle, float partialTick, float ageInSeconds) {
        float x = X_FACTOR.get(subParticle, partialTick);
        float y = x * S_FACTOR.get(subParticle, partialTick);
        return new Vector3f(x, y, x);
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
    protected Quaternionf getQuaternion(SubParticle subParticle, Camera camera, float partialTick, float ageInSeconds) {
        Quaternionf quaternion = new Quaternionf();
        this.getFacingCameraMode().setRotation(quaternion, camera, partialTick);
        return quaternion;
    }

    @Override
    protected SubParticle makeParticle() {
        SubParticle subParticle = new SubParticle(this, this, this.set, this.getSubParticleLifeTime(), this.random){

            @Override
            protected List<Pair<Float, BiConsumer<SubParticle, Float>>> createLifetimeEvents() {
                ArrayList<Pair<Float, BiConsumer<SubParticle, Float>>> pairs = new ArrayList<Pair<Float, BiConsumer<SubParticle, Float>>>();
                for (float f = 0.0f; f <= 2.0f; f += 0.01f) {
                    pairs.add(Pair.of(f, this::event));
                }
                return pairs;
            }

            private void event(SubParticle sub, Float partialTick) {
                Vec3 pos = this.parent.getCurrentPos(partialTick.floatValue());
                this.parent.getLevel().addParticle((ParticleOptions)BossesRiseParticleTypes.SOUL_TRAIL.get(), pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
            }
        };
        subParticle.pos0 = subParticle.pos = new Vec3(0.0, 1.0, 0.0);
        return subParticle;
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new IntroductionAttackSoulOnce(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

