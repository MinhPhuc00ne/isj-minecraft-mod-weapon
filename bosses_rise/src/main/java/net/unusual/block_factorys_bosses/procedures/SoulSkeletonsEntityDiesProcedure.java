/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 */
package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;

public class SoulSkeletonsEntityDiesProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        ServerLevel _level;
        if (world instanceof ServerLevel) {
            _level = (ServerLevel)world;
            _level.sendParticles((ParticleOptions)ParticleTypes.SOUL, x, y + 1.2, z, Mth.nextInt((RandomSource)RandomSource.create(), (int)5, (int)10), 0.4, 0.4, 0.4, 0.0);
        }
        if (world instanceof ServerLevel) {
            _level = (ServerLevel)world;
            _level.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.FANCY_SMOKE.get()), x, y + 1.2, z, Mth.nextInt((RandomSource)RandomSource.create(), (int)3, (int)5), 0.4, 0.4, 0.4, 0.0);
        }
        if (world instanceof Level _lvl) {
            if (!_lvl.isClientSide()) {
                _lvl.playSound(null, BlockPos.containing((double)x, (double)y, (double)z), SoundEvents.SOUL_SAND_BREAK, SoundSource.HOSTILE, 1.0f, (float)Mth.nextDouble((RandomSource)RandomSource.create(), (double)0.4, (double)0.7));
            } else {
                _lvl.playLocalSound(x, y, z, SoundEvents.SOUL_SAND_BREAK, SoundSource.HOSTILE, 1.0f, (float)Mth.nextDouble((RandomSource)RandomSource.create(), (double)0.4, (double)0.7), false);
            }
        }
    }
}

