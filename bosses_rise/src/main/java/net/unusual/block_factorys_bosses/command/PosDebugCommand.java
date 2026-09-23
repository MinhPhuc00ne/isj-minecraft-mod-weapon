/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.DoubleArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.core.Direction
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.common.util.FakePlayerFactory
 *  net.neoforged.neoforge.event.RegisterCommandsEvent
 */
package net.unusual.block_factorys_bosses.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.unusual.block_factorys_bosses.procedures.PosDebugPrProcedure;

@EventBusSubscriber
public class PosDebugCommand {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register((LiteralArgumentBuilder)Commands.literal((String)"posdebug").then(Commands.argument((String)"x", (ArgumentType)DoubleArgumentType.doubleArg()).then(Commands.argument((String)"y", (ArgumentType)DoubleArgumentType.doubleArg()).then(Commands.argument((String)"z", (ArgumentType)DoubleArgumentType.doubleArg()).executes(arguments -> {
            Level world = ((CommandSourceStack)arguments.getSource()).getLevel();
            double x = ((CommandSourceStack)arguments.getSource()).getPosition().x();
            double y = ((CommandSourceStack)arguments.getSource()).getPosition().y();
            double z = ((CommandSourceStack)arguments.getSource()).getPosition().z();
            Entity entity = ((CommandSourceStack)arguments.getSource()).getEntity();
            if (entity == null && world instanceof ServerLevel) {
                ServerLevel _servLevel = (ServerLevel)world;
                entity = FakePlayerFactory.getMinecraft((ServerLevel)_servLevel);
            }
            Direction direction = Direction.DOWN;
            if (entity != null) {
                direction = entity.getDirection();
            }
            PosDebugPrProcedure.execute((LevelAccessor)world, (CommandContext<CommandSourceStack>)arguments);
            return 0;
        })))));
    }
}

