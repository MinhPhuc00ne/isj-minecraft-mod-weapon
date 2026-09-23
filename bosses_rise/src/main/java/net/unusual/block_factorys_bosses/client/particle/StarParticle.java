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
import net.unusual.block_factorys_bosses.client.particle.BRParticleRenderType;

@OnlyIn(value=Dist.CLIENT)
public class StarParticle
extends TextureSheetParticle {
    private final SpriteSet spriteSet;

    public static StarParticleProvider provider(SpriteSet spriteSet) {
        return new StarParticleProvider(spriteSet);
    }

    protected StarParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.quadSize = 0.5f;
        this.lifetime = 30;
        this.setColor(0.0f, 0.961f, 0.98f);
        this.setAlpha(0.0f);
        this.pickSprite(spriteSet);
    }

    public int getLightColor(float partialTick) {
        return 0xF000F0;
    }

    public ParticleRenderType getRenderType() {
        return BRParticleRenderType.NORMAL_TRANSLUCENT;
    }

    public float getQuadSize(float scale) {
        return this.age < 15 ? (float)this.age / 15.0f * this.quadSize : (30.0f - (float)this.age) / 15.0f * this.quadSize;
    }

    public void tick() {
        super.tick();
        this.setAlpha(this.getQuadSize(1.0f) / this.quadSize);
    }

    public static class StarParticleProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public StarParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new StarParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

