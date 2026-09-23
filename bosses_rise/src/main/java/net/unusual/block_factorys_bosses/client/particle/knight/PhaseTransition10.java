/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.VertexConsumer
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
 *  org.joml.Vector3d
 *  org.joml.Vector3dc
 *  org.joml.Vector3f
 *  software.bernie.geckolib.animation.state.BoneSnapshot
 *  software.bernie.geckolib.cache.object.GeoBone
 */
package net.unusual.block_factorys_bosses.client.particle.knight;

import com.mojang.blaze3d.vertex.VertexConsumer;
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
import net.unusual.block_factorys_bosses.event.ClientEvents;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import software.bernie.geckolib.animation.state.BoneSnapshot;
import software.bernie.geckolib.cache.object.GeoBone;

@OnlyIn(value=Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PhaseTransition10
extends SubSteadyParticleEmitter {
    private static final LinearCurve R_FACTOR = new LinearCurve(new float[]{0.33f, 1.0f, 0.71f}, (subParticle, partial) -> Float.valueOf(((float)subParticle.age + partial.floatValue()) / (float)subParticle.lifeTime), (subParticle, partial) -> Float.valueOf(1.0f));
    private static final ColorCurve COLOR_CURVE = new ColorCurve(new ColorCurve.Point[]{new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(11663871), 0.0f), new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-16716801), 0.4f), new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(1157693383), 0.75f), new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(60671), 1.0f)}, (subParticle, partial) -> Float.valueOf(((float)subParticle.age + partial.floatValue()) / (float)subParticle.lifeTime));

    public static Provider provider(SpriteSet spriteSet) {
        return new Provider(spriteSet);
    }

    protected PhaseTransition10(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z, spriteSet, 60, 30, 100);
        this.setSize(0.6f, 0.6f);
        this.gravity = 0.0f;
        this.hasPhysics = false;
    }

    @Override
    protected int getSubParticleLifeTime() {
        return 40;
    }

    @Override
    protected float getSubSize(SubParticle subParticle, float partialTick, float ageInSeconds) {
        return 0.5f * R_FACTOR.get(subParticle, partialTick);
    }

    @Override
    protected ColorCurve.FloatRGBA getSubColor(SubParticle subParticle, float partialTick, float ageInSeconds) {
        return COLOR_CURVE.get(subParticle, partialTick);
    }

    @Override
    public void tick() {
        if (ClientEvents.PARTICULARS.containsValue((Object)this)) {
            this.age = 0;
        }
        super.tick();
    }

    @Override
    public boolean shouldAttach() {
        return true;
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTick) {
        Vec3 vec3 = camera.getPosition();
        Vec3 pos = this.getCurrentPos(partialTick);
        Vector3f rot = null;
        if (this.attachment != null && this.attachment.locator() != null) {
            GeoBone geoBone = this.attachment.locator().bone();
            Vector3d vector = this.get(geoBone);
            while ((geoBone = geoBone.getParent()) != null) {
                vector.add((Vector3dc)this.get(geoBone));
            }
            rot = new Vector3f((float)((double)(this.attachment.entity().getViewXRot(partialTick) * ((float)Math.PI / 180)) + vector.x * 0.01745329238474369), (float)((double)(-this.attachment.entity().getViewYRot(partialTick) * ((float)Math.PI / 180)) + vector.y * 0.01745329238474369), (float)vector.z * ((float)Math.PI / 180));
        }
        for (SubParticle subParticle : this.proxies) {
            subParticle.onRender(partialTick);
            float ageInSeconds = ((float)subParticle.age + partialTick) / 20.0f;
            Vec3 sub = this.getRelativeSubPos(subParticle, partialTick, ageInSeconds);
            if (rot != null) {
                sub = sub.xRot(rot.x);
                sub = sub.yRot(rot.y);
                sub = sub.zRot(rot.z);
            }
            Quaternionf quaternion = this.getQuaternion(subParticle, camera, partialTick, ageInSeconds);
            float x = (float)(pos.x - vec3.x() + sub.x);
            float y = (float)(pos.y - vec3.y() + sub.y);
            float z = (float)(pos.z - vec3.z() + sub.z);
            Vector3f size = this.getSubSizeVector(subParticle, partialTick, ageInSeconds);
            float u0 = subParticle.getU0();
            float u1 = subParticle.getU1();
            float v0 = subParticle.getV0();
            float v1 = subParticle.getV1();
            int lightColor = this.getLightColor(partialTick);
            ColorCurve.FloatRGBA color = this.getSubColor(subParticle, partialTick, ageInSeconds);
            this.renderProxyVertex(buffer, quaternion, x, y, z, 1.0f, -1.0f, size, u1, v1, lightColor, color);
            this.renderProxyVertex(buffer, quaternion, x, y, z, 1.0f, 1.0f, size, u1, v0, lightColor, color);
            this.renderProxyVertex(buffer, quaternion, x, y, z, -1.0f, 1.0f, size, u0, v0, lightColor, color);
            this.renderProxyVertex(buffer, quaternion, x, y, z, -1.0f, -1.0f, size, u0, v1, lightColor, color);
        }
    }

    private Vector3d get(GeoBone bone) {
        BoneSnapshot snapshot = bone.getInitialSnapshot();
        if (snapshot == null) {
            return bone.getRotationVector();
        }
        return bone.getRotationVector().add((double)snapshot.getRotX(), (double)snapshot.getRotY(), (double)snapshot.getRotZ());
    }

    @Override
    public Vec3 getActualSubPos(SubParticle subParticle, float partialTick, float ageInSeconds) {
        return super.getActualSubPos(subParticle, partialTick, ageInSeconds);
    }

    @Override
    protected SubParticle makeParticle() {
        Vec3 pos;
        SubParticle subParticle = new SubParticle(this, this, this.set, this.getSubParticleLifeTime(), this.random){

            @Override
            public Vec3 getLinearAcceleration() {
                return new Vec3(Math.cos((float)this.age * 360.0f) * 2.0 * (double)this.random2, (double)this.random2, Math.cos((float)this.age * 360.0f) * 2.0 * (double)this.random2);
            }
        };
        double distance = 0.1;
        double theta = this.random.nextDouble() * Math.PI;
        double phi = this.random.nextDouble() * 2.0 * Math.PI;
        subParticle.pos = pos = new Vec3(distance * Math.sin(theta) * Math.cos(phi), distance * Math.sin(theta) * Math.sin(phi), distance * Math.cos(theta)).add(0.0, 0.35, 0.25);
        subParticle.pos0 = pos;
        subParticle.setVelocity(new Vec3((double)Mth.randomBetween((RandomSource)this.random, (float)-0.5f, (float)0.5f), 0.0, 0.0), 1.5f);
        return subParticle;
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new PhaseTransition10(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

