/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class PoisonSpitParticle
extends TextureSheetParticle {
    private final SpriteSet spriteSet;
    private float angularVelocity;
    private float angularAcceleration;

    public static PoisonSpitParticleProvider provider(SpriteSet spriteSet) {
        return new PoisonSpitParticleProvider(spriteSet);
    }

    protected PoisonSpitParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        this.lifetime = Math.max(1, 90 + (this.random.nextInt(4) - 2));
        this.quadSize *= 1.5f + ((float)Math.random() - 0.5f) * 2.0f;
        this.gravity = 0.8f;
        this.hasPhysics = true;
        this.xd = vx * 1.0;
        this.yd = vy * 1.0;
        this.zd = vz * 1.0;
        this.roll = -1.0f;
        this.angularVelocity = 0.3f;
        this.angularAcceleration = 0.0f;
        this.pickSprite(spriteSet);
        this.rCol = 0.651f;
        this.gCol = 1.0f;
        this.bCol = 0.333f;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public int getLightColor(float partialTick) {
        return 0xF000F0;
    }

    public void tick() {
        float t;
        super.tick();
        this.oRoll = this.roll;
        if (this.roll < 3.0f) {
            this.roll += this.angularVelocity;
            this.angularVelocity += this.angularAcceleration;
        }
        if (this.onGround) {
            ++this.age;
        }
        if ((t = (float)((double)this.age * 0.0111)) < 1.0f) {
            this.rCol = 0.651f * (1.0f - t) + 0.078f * t;
            this.gCol = 1.0f * (1.0f - t) + 0.607f * t;
            this.bCol = 0.333f * (1.0f - t) + 0.129f * t;
        }
    }

    public static class PoisonSpitParticleProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public PoisonSpitParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new PoisonSpitParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

