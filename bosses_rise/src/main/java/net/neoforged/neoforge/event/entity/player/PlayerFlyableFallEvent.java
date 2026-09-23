package net.neoforged.neoforge.event.entity.player;

import net.minecraft.world.entity.player.Player;

public class PlayerFlyableFallEvent {
    private final Player player;

    public PlayerFlyableFallEvent(Player player) {
        this.player = player;
    }

    public Player getEntity() {
        return player;
    }
}
