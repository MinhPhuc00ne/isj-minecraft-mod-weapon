/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.CampfireSmokeParticle
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.particle.ParticleProvider
 *  net.minecraft.client.particle.ParticleRenderType
 *  net.minecraft.client.particle.SpriteSet
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package net.unusual.block_factorys_bosses.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.CampfireSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.particle.BRParticleRenderType;

@OnlyIn(value=Dist.CLIENT)
public class DustCloudParticle
extends CampfireSmokeParticle {
    private final SpriteSet spriteSet;

    public static DustCloudParticleProvider provider(SpriteSet spriteSet) {
        return new DustCloudParticleProvider(spriteSet);
    }

    protected DustCloudParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z, 0.0, 0.0, 0.0, true);
        this.spriteSet = spriteSet;
        this.setSize(0.9f, 0.9f);
        this.lifetime = 22 + this.random.nextInt(5);
        this.gravity = 0.0f;
        this.hasPhysics = true;
        this.xd = vx * 0.4;
        this.yd = vy * 0.4;
        this.zd = vz * 0.4;
        this.roll = 0.1f * (float)Math.random();
        this.pickSprite(spriteSet);
        this.setAlpha(0.9f);
        float rdm = (float)Math.random();
        this.rCol = 0.639f * (1.0f - rdm) + 0.533f * rdm;
        this.gCol = 0.518f * (1.0f - rdm) + 0.396f * rdm;
        this.bCol = 0.404f * (1.0f - rdm) + 0.251f * rdm;
    }

    public ParticleRenderType getRenderType() {
        return BRParticleRenderType.NORMAL_TRANSLUCENT;
    }

    public float getQuadSize(float scale) {
        return super.getQuadSize(scale) * (1.0f + (float)(this.lifetime - this.age) / (float)this.lifetime);
    }

    public void tick() {
        super.tick();
        this.xd *= (double)0.9f;
        this.yd *= (double)0.9f;
        this.zd *= (double)0.9f;
        this.oRoll = this.roll;
        this.roll += (float)(this.zd * (double)0.1f);
        if (this.age >= this.lifetime - 5 && this.alpha > 0.01f) {
            this.alpha -= 0.12f;
            if (this.alpha < 0.0f) {
                this.alpha = 0.0f;
            }
            this.setAlpha(this.alpha);
        }
    }

    public static class DustCloudParticleProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public DustCloudParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new DustCloudParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

