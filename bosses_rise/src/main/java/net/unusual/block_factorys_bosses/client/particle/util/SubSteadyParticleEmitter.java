/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.SpriteSet
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package net.unusual.block_factorys_bosses.client.particle.util;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticleEmitter;

@OnlyIn(value=Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class SubSteadyParticleEmitter
extends SubParticleEmitter {
    protected final int spawnRate;
    protected final int max;
    protected float spawn = 0.0f;

    protected SubSteadyParticleEmitter(ClientLevel world, SpriteSet spriteSet, Vec3 vec3, int lifeTime, int spawnRate, int max) {
        super(world, spriteSet, vec3);
        this.lifetime = lifeTime;
        this.spawnRate = spawnRate;
        this.max = max;
    }

    protected SubSteadyParticleEmitter(ClientLevel world, double x, double y, double z, SpriteSet spriteSet, int lifeTime, int spawnRate, int max) {
        this(world, spriteSet, new Vec3(x, y, z), lifeTime, spawnRate, max);
    }

    @Override
    protected void spawnParticles() {
        this.spawn += (float)this.spawnRate / 20.0f;
        while (this.spawn > 1.0f) {
            if (this.proxies.size() < this.max) {
                this.spawnSubParticle();
            }
            this.spawn -= 1.0f;
        }
    }
}

