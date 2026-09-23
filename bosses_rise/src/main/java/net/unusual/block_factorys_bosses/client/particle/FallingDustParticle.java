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
import net.unusual.block_factorys_bosses.procedures.FallingDustParticleVisualScaleProcedure;

@OnlyIn(value=Dist.CLIENT)
public class FallingDustParticle
extends TextureSheetParticle {
    private final SpriteSet spriteSet;
    private float fall;
    private float angularVelocity;
    private float angularAcceleration;

    public static FallingDustParticleProvider provider(SpriteSet spriteSet) {
        return new FallingDustParticleProvider(spriteSet);
    }

    protected FallingDustParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        this.lifetime = Math.max(1, 60 + (this.random.nextInt(4) - 2));
        this.gravity = 1.2f;
        this.fall = 0.8f;
        this.hasPhysics = true;
        this.xd = vx * 0.3;
        this.yd = Math.abs(vy) * 0.4;
        this.zd = vz * 0.3;
        this.angularVelocity = 0.3f;
        this.angularAcceleration = 0.0f;
        this.pickSprite(spriteSet);
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public float getQuadSize(float scale) {
        ClientLevel world = this.level;
        return super.getQuadSize(scale) * (float)FallingDustParticleVisualScaleProcedure.execute(this.age);
    }

    public void tick() {
        super.tick();
        this.oRoll = this.roll;
        this.roll += this.angularVelocity;
        this.angularVelocity += this.angularAcceleration;
        ClientLevel world = this.level;
        if (this.onGround) {
            this.fall = 0.75f * this.fall;
            this.yd = this.fall;
        }
    }

    public static class FallingDustParticleProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public FallingDustParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new FallingDustParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

