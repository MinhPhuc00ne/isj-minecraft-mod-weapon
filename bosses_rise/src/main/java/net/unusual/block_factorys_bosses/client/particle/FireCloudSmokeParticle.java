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
public class FireCloudSmokeParticle
extends TextureSheetParticle {
    private final SpriteSet spriteSet;
    private static final int FRAMES_PER_SECOND = 30;
    private static final int TOTAL_FRAMES = 8;

    public static FireCloudSmokeParticleProvider provider(SpriteSet spriteSet) {
        return new FireCloudSmokeParticleProvider(spriteSet);
    }

    protected FireCloudSmokeParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        this.lifetime = 20;
        this.gravity = 0.0f;
        this.hasPhysics = false;
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
        float ticksPerFrame = 0.6666667f;
        int currentFrame = (int)((float)this.age / ticksPerFrame) % 8;
        if (this.spriteSet.get(currentFrame, 8) != null) {
            this.setSprite(this.spriteSet.get(currentFrame, 8));
        }
    }

    public static class FireCloudSmokeParticleProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public FireCloudSmokeParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new FireCloudSmokeParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

