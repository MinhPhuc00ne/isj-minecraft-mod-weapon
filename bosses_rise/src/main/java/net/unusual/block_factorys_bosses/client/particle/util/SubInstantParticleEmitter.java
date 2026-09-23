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
public abstract class SubInstantParticleEmitter
extends SubParticleEmitter {
    protected final int count;

    protected SubInstantParticleEmitter(ClientLevel world, SpriteSet spriteSet, Vec3 vec3, int count) {
        super(world, spriteSet, vec3);
        this.lifetime = 1;
        this.count = count;
    }

    protected SubInstantParticleEmitter(ClientLevel world, double x, double y, double z, SpriteSet spriteSet, int count) {
        this(world, spriteSet, new Vec3(x, y, z), count);
    }

    @Override
    protected void spawnParticles() {
        for (int i = 0; i < this.count; ++i) {
            this.spawnSubParticle();
        }
    }
}

