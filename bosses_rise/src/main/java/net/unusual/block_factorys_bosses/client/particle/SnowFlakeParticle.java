/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.Camera
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.particle.ParticleProvider
 *  net.minecraft.client.particle.ParticleRenderType
 *  net.minecraft.client.particle.SpriteSet
 *  net.minecraft.client.particle.TextureSheetParticle
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package net.unusual.block_factorys_bosses.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.procedures.SnowFlakeParticleVisualScaleProcedure;

@OnlyIn(value=Dist.CLIENT)
public class SnowFlakeParticle
extends TextureSheetParticle {
    private final SpriteSet spriteSet;
    private float angularVelocity;
    private float angularAcceleration;

    public static SnowFlakeParticleProvider provider(SpriteSet spriteSet) {
        return new SnowFlakeParticleProvider(spriteSet);
    }

    protected SnowFlakeParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.6f, 0.6f);
        this.lifetime = Math.max(1, 40 + (this.random.nextInt(4) - 2));
        this.gravity = 0.1f;
        this.hasPhysics = true;
        this.xd = vx * 0.4;
        this.yd = vy * 0.4;
        this.zd = vz * 0.4;
        this.angularVelocity = 0.2f;
        this.angularAcceleration = 0.0f;
        this.pickSprite(spriteSet);
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public int getLightColor(float partialTick) {
        return 0xF000F0;
    }

    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        super.render(buffer, renderInfo, partialTicks);
    }

    public float getQuadSize(float scale) {
        ClientLevel world = this.level;
        return super.getQuadSize(scale) * (float)SnowFlakeParticleVisualScaleProcedure.execute(this.age);
    }

    public void tick() {
        super.tick();
        this.oRoll = this.roll;
        this.roll += this.angularVelocity;
        this.angularVelocity += this.angularAcceleration;
    }

    public static class SnowFlakeParticleProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public SnowFlakeParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SnowFlakeParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

