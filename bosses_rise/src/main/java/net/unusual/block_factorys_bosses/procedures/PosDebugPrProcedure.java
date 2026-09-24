/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.DoubleArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.world.level.LevelAccessor
 */
package net.unusual.block_factorys_bosses.procedures;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.level.LevelAccessor;
import net.unusual.block_factorys_bosses.network.BlockFactorysBossesModVariables;

public class PosDebugPrProcedure {
    public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments) {
        BlockFactorysBossesModVariables.MapVariables.get((LevelAccessor)world).debugX = DoubleArgumentType.getDouble(arguments, (String)"x");
        BlockFactorysBossesModVariables.MapVariables.get(world).syncData(world);
        BlockFactorysBossesModVariables.MapVariables.get((LevelAccessor)world).debugY = DoubleArgumentType.getDouble(arguments, (String)"y");
        BlockFactorysBossesModVariables.MapVariables.get(world).syncData(world);
        BlockFactorysBossesModVariables.MapVariables.get((LevelAccessor)world).debugZ = DoubleArgumentType.getDouble(arguments, (String)"z");
        BlockFactorysBossesModVariables.MapVariables.get(world).syncData(world);
    }
}

