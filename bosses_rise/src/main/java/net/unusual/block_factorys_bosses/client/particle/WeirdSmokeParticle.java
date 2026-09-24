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
 *  net.minecraft.world.phys.Vec3
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
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.particle.BRParticleRenderType;

@OnlyIn(value=Dist.CLIENT)
public class WeirdSmokeParticle
extends TextureSheetParticle {
    private final SpriteSet spriteSet;
    private final Vec3 target;

    public static WeirdSmokeParticleProvider provider(SpriteSet spriteSet) {
        return new WeirdSmokeParticleProvider(spriteSet);
    }

    protected WeirdSmokeParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.quadSize = 3.0f;
        this.lifetime = 40;
        this.target = new Vec3(vx, vy, vz);
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
        return (40.0f - (float)this.age) / 40.0f * (this.quadSize - 0.25f) + 0.25f;
    }

    public void tick() {
        super.tick();
        Vec3 currentPos = new Vec3(this.x, this.y, this.z);
        Vec3 pos = this.target.subtract(currentPos).scale(0.02f).add(currentPos);
        this.setPos(pos.x, pos.y, pos.z);
        this.setAlpha(this.age < 20 ? (float)this.age / 20.0f : (40.0f - (float)this.age) / 20.0f);
    }

    public static class WeirdSmokeParticleProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public WeirdSmokeParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new WeirdSmokeParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}

