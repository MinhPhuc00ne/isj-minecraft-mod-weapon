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
public class SandRockParticle
extends TextureSheetParticle {
    private final SpriteSet spriteSet;
    private float fall;
    private float angularVelocity;
    private float angularDrag;

    public static Provider provider(SpriteSet spriteSet) {
        return new Provider(spriteSet);
    }

    protected SandRockParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.pickSprite(spriteSet);
        this.setSize(0.2f, 0.2f);
        this.lifetime = Math.max(1, 60 + (this.random.nextInt(4) - 2));
        this.gravity = 1.2f;
        this.fall = 0.8f;
        this.hasPhysics = true;
        this.xd = vx;
        this.yd = vy;
        this.zd = vz;
        this.angularVelocity = 0.3f;
        this.angularDrag = 0.01f;
        float rdm = (float)Math.random();
        this.rCol = 0.9f + 0.1f * rdm;
        this.gCol = 0.7f + 0.3f * rdm;
        this.bCol = 0.7f + 0.3f * rdm;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void pickSprite(SpriteSet sprite) {
        this.setSprite(sprite.get(this.age, this.lifetime));
    }

    public float getQuadSize(float scale) {
        return super.getQuadSize(scale) * ((float)(60 - this.age) / 60.0f) * 1.2f;
    }

    public void tick() {
        super.tick();
        this.oRoll = this.roll;
        this.roll += this.angularVelocity;
        this.angularVelocity -= this.angularVelocity * this.angularDrag;
        if (this.onGround) {
            this.fall = 0.6f * this.fall;
            this.yd = this.fall;
        }
        this.pickSprite(this.spriteSet);
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SandRockParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

