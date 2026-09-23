package net.neoforged.neoforge.event.entity.player;

import net.minecraft.world.entity.player.Player;

public class PlayerEvent {
    private final Player player;
    public PlayerEvent(Player player) { this.player = player; }
    public Player getEntity() { return player; }

    public static class StartTracking extends PlayerEvent {
        private final net.minecraft.world.entity.Entity target;
        public StartTracking(Player player, net.minecraft.world.entity.Entity target) {
            super(player);
            this.target = target;
        }
        public net.minecraft.world.entity.Entity getTarget() { return target; }
    }

    public static class StopTracking extends PlayerEvent {
        private final net.minecraft.world.entity.Entity target;
        public StopTracking(Player player, net.minecraft.world.entity.Entity target) {
            super(player);
            this.target = target;
        }
        public net.minecraft.world.entity.Entity getTarget() { return target; }
    }

    public static class PlayerLoggedInEvent extends PlayerEvent {
        public PlayerLoggedInEvent(Player player) { super(player); }
    }
    public static class PlayerLoggedOutEvent extends PlayerEvent {
        public PlayerLoggedOutEvent(Player player) { super(player); }
    }
    public static class PlayerRespawnEvent extends PlayerEvent {
        public PlayerRespawnEvent(Player player) { super(player); }
    }
    public static class PlayerChangedDimensionEvent extends PlayerEvent {
        public PlayerChangedDimensionEvent(Player player) { super(player); }
    }
    public static class Clone extends PlayerEvent {
        private final Player original;
        public Clone(Player player, Player original) { super(player); this.original = original; }
        public Player getOriginal() { return original; }
    }
}
