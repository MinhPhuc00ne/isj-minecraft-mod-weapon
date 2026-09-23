/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 */
package net.unusual.block_factorys_bosses.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.unusual.block_factorys_bosses.procedures.AchievementIconsEntitySwingsItemProcedure;

public class AchievementIconsItem
extends Item {
    public AchievementIconsItem() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
    }

    public boolean onEntitySwing(ItemStack itemstack, LivingEntity entity, InteractionHand hand) {
        boolean retval = false;
        AchievementIconsEntitySwingsItemProcedure.execute(itemstack);
        return retval;
    }
}

