/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Rarity
 */
package net.unusual.block_factorys_bosses.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class DragonBoneItem
extends Item {
    public DragonBoneItem() {
        super(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.UNCOMMON));
    }
}

