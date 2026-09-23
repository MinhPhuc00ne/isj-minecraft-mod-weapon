/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.FlameParticle
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
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.particle.BRParticleRenderType;

@OnlyIn(value=Dist.CLIENT)
public class BigExplosionParticle
extends FlameParticle {
    private final SpriteSet spriteSet;

    public static BigExplosionParticleProvider provider(SpriteSet spriteSet) {
        return new BigExplosionParticleProvider(spriteSet);
    }

    protected BigExplosionParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.spriteSet = spriteSet;
        this.quadSize = 4.0f;
        this.lifetime = 5 + this.random.nextInt(3);
        this.alpha = 0.75f;
        this.oRoll = this.roll = (float)Math.random() * ((float)Math.PI * 2);
        this.setColor(1.0f, 0.471f, 0.318f);
        this.setSpriteFromAge(spriteSet);
    }

    public ParticleRenderType getRenderType() {
        return BRParticleRenderType.NORMAL_TRANSLUCENT;
    }

    public int getLightColor(float partialTick) {
        return 0xF000F0;
    }

    public float getQuadSize(float scale) {
        return this.quadSize;
    }

    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.spriteSet);
        this.rCol = (this.rCol + 1.0f) / 2.0f;
        this.gCol = (this.gCol + 1.0f) / 2.0f;
        this.bCol = (this.bCol + 1.0f) / 2.0f;
    }

    public static class BigExplosionParticleProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public BigExplosionParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new BigExplosionParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

