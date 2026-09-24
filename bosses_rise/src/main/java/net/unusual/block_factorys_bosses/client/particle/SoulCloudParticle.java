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
import net.unusual.block_factorys_bosses.procedures.SnowCloudParticleVisualScaleProcedure;

@OnlyIn(value=Dist.CLIENT)
public class SoulCloudParticle
extends CampfireSmokeParticle {
    private final SpriteSet spriteSet;

    public static SoulCloudParticleProvider provider(SpriteSet spriteSet) {
        return new SoulCloudParticleProvider(spriteSet);
    }

    protected SoulCloudParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
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
        this.rCol = 0.25f;
        this.gCol = 0.415f;
        this.bCol = 0.39f;
    }

    public int getLightColor(float partialTick) {
        return 0xF000F0;
    }

    public ParticleRenderType getRenderType() {
        return BRParticleRenderType.NORMAL_TRANSLUCENT;
    }

    public float getQuadSize(float scale) {
        return super.getQuadSize(scale) * (float)SnowCloudParticleVisualScaleProcedure.execute(this.age);
    }

    public void tick() {
        super.tick();
        this.oRoll = this.roll;
        if (this.age >= this.lifetime - 5 && this.alpha > 0.01f) {
            this.alpha -= 0.12f;
            if (this.alpha < 0.0f) {
                this.alpha = 0.0f;
            }
            this.setAlpha(this.alpha);
            float t = (this.lifetime - this.age) / this.lifetime;
            if (t < 1.0f) {
                this.rCol = 0.02f;
                this.gCol = 0.02f;
                this.bCol = 0.02f;
            } else {
                this.rCol = 0.25f * (1.0f - t) + 0.02f * t;
                this.gCol = 0.415f * (1.0f - t) + 0.02f * t;
                this.bCol = 0.39f * (1.0f - t) + 0.02f * t;
            }
        }
    }

    public static class SoulCloudParticleProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public SoulCloudParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SoulCloudParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

