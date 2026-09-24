/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.level.Level
 */
package net.unusual.block_factorys_bosses.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.unusual.block_factorys_bosses.procedures.PlaceholderItemInInventoryTickProcedure;

public class PlaceholderItem
extends Item {
    public PlaceholderItem() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
    }

    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        PlaceholderItemInInventoryTickProcedure.execute(entity);
    }
}

