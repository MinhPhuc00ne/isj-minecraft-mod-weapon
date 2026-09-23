/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 */
package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;

public class CorpseBreakProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        block6: {
            block5: {
                if (entity == null) {
                    return;
                }
                if (!(entity instanceof Player)) break block5;
                Player _plr = (Player)entity;
                if (_plr.getAbilities().instabuild) break block6;
            }
            if (world instanceof ServerLevel) {
                ServerLevel _level = (ServerLevel)world;
                ItemEntity entityToSpawn = new ItemEntity((Level)_level, x, y, z, new ItemStack((ItemLike)BossesRiseBlocks.CORPSE.get()));
                entityToSpawn.setPickUpDelay(10);
                _level.addFreshEntity((Entity)entityToSpawn);
            }
        }
    }
}

