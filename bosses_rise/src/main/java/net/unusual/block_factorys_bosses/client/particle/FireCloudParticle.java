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
public class FireCloudParticle
extends TextureSheetParticle {
    private final SpriteSet spriteSet;

    public static FireCloudParticleProvider provider(SpriteSet spriteSet) {
        return new FireCloudParticleProvider(spriteSet);
    }

    protected FireCloudParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(40.0f, 40.0f);
        this.lifetime = 20;
        this.gravity = 0.0f;
        this.hasPhysics = true;
        this.xd = 0.0;
        this.yd = 0.0;
        this.zd = 0.0;
        this.pickSprite(spriteSet);
        this.rCol = 1.0f;
        this.gCol = 1.0f;
        this.bCol = 1.0f;
        this.alpha = 1.0f;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
        super.tick();
        this.xd *= (double)0.2f;
        this.yd *= (double)0.2f;
        this.zd *= (double)0.2f;
        this.yd += (double)0.05f;
        float ageRatio = (float)this.age / (float)this.lifetime;
        this.alpha = 1.0f - ageRatio;
    }

    public static class FireCloudParticleProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public FireCloudParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new FireCloudParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

