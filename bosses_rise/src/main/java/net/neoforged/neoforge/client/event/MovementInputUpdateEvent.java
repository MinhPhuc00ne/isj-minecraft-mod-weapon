package net.neoforged.neoforge.client.event;

import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;

public class MovementInputUpdateEvent {
    private final Player entity;
    private final Input input;

    public MovementInputUpdateEvent(Player entity, Input input) {
        this.entity = entity;
        this.input = input;
    }

    public Player getEntity() {
        return entity;
    }

    public Input getInput() {
        return input;
    }
}
