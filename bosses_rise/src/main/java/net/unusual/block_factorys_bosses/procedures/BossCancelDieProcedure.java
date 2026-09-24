/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  org.jetbrains.annotations.NotNull
 */
package net.unusual.block_factorys_bosses.procedures;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.boss.AbstractBossEntity;
import net.unusual.block_factorys_bosses.entity.boss.dragon.boss.InfernalDragonEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.YetiEntity;
import org.jetbrains.annotations.NotNull;

public class BossCancelDieProcedure {
    private static void execute(@NotNull AbstractBossEntity boss, int dieAnimtime, @Nullable ResourceLocation deathSound) {
        boss.getEntityData().set(AbstractBossEntity.DATA_DIE_ANIMTIME, dieAnimtime);
        if (deathSound != null) {
            boss.level().playSound(null, BlockPos.containing((double)boss.getX(), (double)boss.getY(), (double)boss.getZ()), (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(deathSound), SoundSource.HOSTILE, 8.0f, 1.0f);
        }
    }

    public static void execute(@NotNull AbstractBossEntity entity) {
        if (entity.isState("dead") || entity.isState("fake_dead")) {
            if (entity instanceof InfernalDragonEntity) {
                BossCancelDieProcedure.execute(entity, 88, null);
            } else if (entity instanceof YetiEntity) {
                BossCancelDieProcedure.execute(entity, 60, BossesRise.prefix("yeti_death"));
            }
        }
    }
}

