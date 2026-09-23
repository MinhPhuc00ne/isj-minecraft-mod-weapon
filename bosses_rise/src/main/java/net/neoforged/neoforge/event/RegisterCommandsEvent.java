package net.neoforged.neoforge.event;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class RegisterCommandsEvent {
    private final CommandDispatcher<CommandSourceStack> dispatcher;
    private final CommandBuildContext buildContext;
    private final Commands.CommandSelection selection;

    public RegisterCommandsEvent(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext, Commands.CommandSelection selection) {
        this.dispatcher = dispatcher;
        this.buildContext = buildContext;
        this.selection = selection;
    }

    public CommandDispatcher<CommandSourceStack> getDispatcher() {
        return dispatcher;
    }

    public CommandBuildContext getBuildContext() {
        return buildContext;
    }

    public Commands.CommandSelection getSelection() {
        return selection;
    }
}
