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
 *  net.minecraft.util.Mth
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
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class FireAOEParticle
extends TextureSheetParticle {
    public static FireAoeParticleProvider provider(SpriteSet spriteSet) {
        return new FireAoeParticleProvider(spriteSet);
    }

    protected FireAOEParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.setSize(0.3f, 0.3f);
        this.lifetime = 20;
        this.gravity = 0.0f;
        this.hasPhysics = false;
        this.xd = vx * 1.0;
        this.yd = vy * 1.0;
        this.zd = vz * 1.0;
        this.pickSprite(spriteSet);
        this.oRoll = this.roll = (float)(Math.random() <= 0.5 ? 0.0 : Math.PI);
        this.alpha = 0.0f;
    }

    public int getLightColor(float partialTick) {
        return 0xF000F0;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
        super.tick();
        float dragFactor = 0.33333334f;
        this.xd *= (double)dragFactor;
        this.yd *= (double)dragFactor;
        this.zd *= (double)dragFactor;
        this.yd += (double)0.01f;
        float ageRatio = (float)this.age / (float)this.lifetime;
        if (ageRatio <= 0.11f) {
            float t = ageRatio / 0.11f;
            this.rCol = 1.0f;
            this.gCol = 1.0f;
            this.bCol = 1.0f;
            this.alpha = t;
            return;
        }
        if (ageRatio <= 0.26f) {
            float t = (ageRatio - 0.11f) / 0.15f;
            this.rCol = 1.0f;
            this.gCol = Mth.lerp((float)t, (float)1.0f, (float)0.831f);
            this.bCol = Mth.lerp((float)t, (float)1.0f, (float)0.0f);
            this.alpha = 1.0f;
            return;
        }
        if (ageRatio <= 0.5f) {
            float t = (ageRatio - 0.26f) / 0.24f;
            float easedT = t * t * t;
            this.rCol = Mth.lerp((float)easedT, (float)1.0f, (float)0.365f);
            this.gCol = Mth.lerp((float)easedT, (float)0.831f, (float)0.078f);
            this.bCol = Mth.lerp((float)easedT, (float)0.0f, (float)0.016f);
            this.alpha = 1.0f;
            return;
        }
        float t = (ageRatio - 0.5f) / 0.5f;
        this.rCol = Mth.lerp((float)t, (float)0.365f, (float)0.0f);
        this.gCol = Mth.lerp((float)t, (float)0.078f, (float)0.0f);
        this.bCol = Mth.lerp((float)t, (float)0.016f, (float)0.0f);
        this.alpha = 1.0f - t;
    }

    public static class FireAoeParticleProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public FireAoeParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new FireAOEParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

