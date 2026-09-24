/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;

public class PlaceholderItemInInventoryTickProcedure {
    public static void execute(Entity entity) {
        if (entity == null) {
            return;
        }
        if (entity instanceof Player) {
            Player _player = (Player)entity;
            ItemStack _stktoremove = new ItemStack((ItemLike)BossesRiseItems.PLACEHOLDER.get());
            _player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 64, (Container)_player.inventoryMenu.getCraftSlots());
        }
    }
}

