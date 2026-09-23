/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.particle.ParticleProvider
 *  net.minecraft.client.particle.ParticleRenderType
 *  net.minecraft.client.particle.RisingParticle
 *  net.minecraft.client.particle.SpriteSet
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package net.unusual.block_factorys_bosses.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.RisingParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.particle.BRParticleRenderType;

@OnlyIn(value=Dist.CLIENT)
public class RiftParticle
extends RisingParticle {
    private final SpriteSet spriteSet;

    public static RiftParticleProvider provider(SpriteSet spriteSet) {
        return new RiftParticleProvider(spriteSet);
    }

    protected RiftParticle(ClientLevel world, double x, double y, double z, double lifetime, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.spriteSet = spriteSet;
        this.quadSize = 7.5f;
        this.lifetime = (int)lifetime;
        if (this.lifetime < 1) {
            this.lifetime = 1;
        }
        this.setColor(0.0f, 0.961f, 0.98f);
        this.setSpriteFromAge(spriteSet);
    }

    public ParticleRenderType getRenderType() {
        return BRParticleRenderType.NORMAL_TRANSLUCENT;
    }

    public int getLightColor(float partialTick) {
        return 0xF000F0;
    }

    public float getQuadSize(float scale) {
        return this.quadSize + (float)Math.sin((float)this.age / 4.0f) * 0.2f;
    }

    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.spriteSet);
    }

    public static class RiftParticleProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public RiftParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new RiftParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

