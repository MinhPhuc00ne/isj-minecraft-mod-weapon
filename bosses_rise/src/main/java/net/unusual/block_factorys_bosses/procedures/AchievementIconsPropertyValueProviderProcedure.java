/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.component.CustomData
 */
package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class AchievementIconsPropertyValueProviderProcedure {
    public static double execute(ItemStack itemstack) {
        return ((CustomData)itemstack.getOrDefault(DataComponents.CUSTOM_DATA, (Object)CustomData.EMPTY)).copyTag().getDouble("icon_index");
    }
}

