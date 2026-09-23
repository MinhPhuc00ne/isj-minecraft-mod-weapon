/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.component.ItemAttributeModifiers
 *  net.minecraft.world.item.component.ItemAttributeModifiers$Builder
 *  net.minecraft.world.item.component.ItemAttributeModifiers$Entry
 */
package net.unusual.block_factorys_bosses.util;

import net.minecraft.world.item.component.ItemAttributeModifiers;

public class ItemAttributeModifiersUtil {
    public static ItemAttributeModifiers.Builder addAll(ItemAttributeModifiers.Builder builder, ItemAttributeModifiers modifiers) {
        for (ItemAttributeModifiers.Entry modifier : modifiers.modifiers()) {
            builder.add(modifier.attribute(), modifier.modifier(), modifier.slot());
        }
        return builder;
    }
}

