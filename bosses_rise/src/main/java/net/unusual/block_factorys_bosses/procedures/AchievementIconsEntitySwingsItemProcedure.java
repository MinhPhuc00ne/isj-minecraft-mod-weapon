/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.component.DataComponentType
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.component.CustomData
 */
package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class AchievementIconsEntitySwingsItemProcedure {
    public static void execute(ItemStack itemstack) {
        if (((CustomData)itemstack.getOrDefault(DataComponents.CUSTOM_DATA, (Object)CustomData.EMPTY)).copyTag().getDouble("icon_index") > 11.0) {
            String _tagName = "icon_index";
            double _tagValue = 0.0;
            CustomData.update((DataComponentType)DataComponents.CUSTOM_DATA, (ItemStack)itemstack, tag -> tag.putDouble("icon_index", 0.0));
        } else {
            String _tagName = "icon_index";
            double _tagValue = ((CustomData)itemstack.getOrDefault(DataComponents.CUSTOM_DATA, (Object)CustomData.EMPTY)).copyTag().getDouble("icon_index") + 1.0;
            CustomData.update((DataComponentType)DataComponents.CUSTOM_DATA, (ItemStack)itemstack, tag -> tag.putDouble("icon_index", _tagValue));
        }
    }
}

