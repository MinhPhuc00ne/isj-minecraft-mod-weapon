/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.commands.CommandSource
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.Vec2
 *  net.minecraft.world.phys.Vec3
 */
package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class LootTableStickDragonRightClickedOnBlockProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) {
            return;
        }
        boolean found = false;
        if (world.getBlockState(BlockPos.containing((double)x, (double)y, (double)z)).getBlock() == Blocks.CHEST) {
            Player _player;
            if (world instanceof ServerLevel) {
                ServerLevel _level = (ServerLevel)world;
                _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", (Component)Component.literal((String)""), _level.getServer(), null).withSuppressedOutput(), "setblock ~ ~ ~ minecraft:chest{LootTable: \"block_factorys_bosses:chests/dragon_tower_common\"} destroy");
            }
            if (entity instanceof Player && !(_player = (Player)entity).level().isClientSide()) {
                _player.displayClientMessage((Component)Component.literal((String)"Dragon Chest"), true);
            }
        } else if (world.getBlockState(BlockPos.containing((double)x, (double)y, (double)z)).getBlock() == Blocks.BARREL) {
            Player _player;
            if (world instanceof ServerLevel) {
                ServerLevel _level = (ServerLevel)world;
                _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", (Component)Component.literal((String)""), _level.getServer(), null).withSuppressedOutput(), "setblock ~ ~ ~ minecraft:barrel{LootTable: \"block_factorys_bosses:chests/dragon_tower_common\"} destroy");
            }
            if (entity instanceof Player && !(_player = (Player)entity).level().isClientSide()) {
                _player.displayClientMessage((Component)Component.literal((String)"Dragon Barrel"), true);
            }
        }
    }
}

