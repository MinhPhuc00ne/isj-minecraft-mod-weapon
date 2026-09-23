/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.particle.ParticleProvider
 *  net.minecraft.client.particle.ParticleRenderType
 *  net.minecraft.client.particle.SpriteSet
 *  net.minecraft.client.particle.TextureSheetParticle
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  org.joml.Quaternionf
 *  org.joml.Quaternionfc
 *  org.joml.Vector3f
 */
package net.unusual.block_factorys_bosses.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.particle.BRParticleRenderType;
import net.unusual.block_factorys_bosses.procedures.SnowCloudParticleVisualScaleProcedure;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;

@OnlyIn(value=Dist.CLIENT)
public class BlazingFlameParticle
extends TextureSheetParticle {
    private final SpriteSet spriteSet;
    private final float random_roll = 1.2f * (float)Math.random();

    public static BlazingFlameParticleProvider provider(SpriteSet spriteSet) {
        return new BlazingFlameParticleProvider(spriteSet);
    }

    protected BlazingFlameParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.9f, 0.9f);
        this.lifetime = 15;
        this.gravity = 0.0f;
        this.hasPhysics = true;
        this.xd = vx * 0.4;
        this.yd = vy * 0.4;
        this.zd = vz * 0.4;
        this.roll = this.random_roll;
        this.pickSprite(spriteSet);
        this.setAlpha(0.8f);
        this.rCol = 1.0f;
        this.gCol = 1.0f;
        this.bCol = 1.0f;
    }

    public ParticleRenderType getRenderType() {
        return BRParticleRenderType.NORMAL_TRANSLUCENT;
    }

    public int getLightColor(float partialTick) {
        return 0xF000F0;
    }

    public float getQuadSize(float scale) {
        ClientLevel world = this.level;
        return super.getQuadSize(scale) * 4.0f * (float)SnowCloudParticleVisualScaleProcedure.execute(this.age);
    }

    public void tick() {
        super.tick();
        this.oRoll = this.roll;
        this.roll = this.random_roll;
        this.rCol = 1.0f - (float)this.age / 16.0f + (float)this.age / 16.0f;
        this.gCol = 1.0f - (float)this.age / 16.0f + 0.0f;
        this.bCol = 1.0f - (float)this.age / 16.0f + 0.0f;
        if (!this.removed) {
            this.setSprite(this.spriteSet.get(this.age % 16 + 1, 16));
        }
    }

    protected void renderRotatedQuad(VertexConsumer buffer, Quaternionf quaternion, float x, float y, float z, float partialTicks) {
        quaternion = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
        quaternion = new Quaternionf((Quaternionfc)quaternion).rotateZ(this.roll);
        float f = this.getQuadSize(partialTicks);
        float u0 = this.getU0();
        float u1 = this.getU1();
        float v0 = this.getV0();
        float v1 = this.getV1();
        int light = this.getLightColor(partialTicks);
        this.renderVertex(buffer, quaternion, x, y, z, 0.5f, -0.5f, f, u1, v1, light);
        this.renderVertex(buffer, quaternion, x, y, z, 0.5f, 0.5f, f, u1, v0, light);
        this.renderVertex(buffer, quaternion, x, y, z, -0.5f, 0.5f, f, u0, v0, light);
        this.renderVertex(buffer, quaternion, x, y, z, -0.5f, -0.5f, f, u0, v1, light);
    }

    private void renderVertex(VertexConsumer buffer, Quaternionf quaternion, float x, float y, float z, float xOffset, float yOffset, float quadSize, float u, float v, int packedLight) {
        Vector3f vector3f = new Vector3f(xOffset, yOffset, 0.0f).rotate((Quaternionfc)quaternion).mul(quadSize).add(x, y, z);
        buffer.addVertex(vector3f.x(), vector3f.y(), vector3f.z()).setUv(u, v).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(packedLight);
    }

    public static class BlazingFlameParticleProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public BlazingFlameParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new BlazingFlameParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

