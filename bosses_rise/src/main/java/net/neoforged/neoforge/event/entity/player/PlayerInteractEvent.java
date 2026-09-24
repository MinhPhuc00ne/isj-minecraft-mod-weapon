package net.neoforged.neoforge.event.entity.player;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PlayerInteractEvent {
    private final Player player;
    private final InteractionHand hand;

    public PlayerInteractEvent(Player player, InteractionHand hand) {
        this.player = player;
        this.hand = hand;
    }

    public Player getEntity() { return player; }
    public InteractionHand getHand() { return hand; }
    public ItemStack getItemStack() { return player.getItemInHand(hand); }

    public static class RightClickItem extends PlayerInteractEvent {
        public RightClickItem(Player player, InteractionHand hand) { super(player, hand); }
    }

    public static class RightClickEmpty extends PlayerInteractEvent {
        public RightClickEmpty(Player player, InteractionHand hand) { super(player, hand); }
    }
}
