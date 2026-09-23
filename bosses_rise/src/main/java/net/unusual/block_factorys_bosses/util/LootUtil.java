/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.NonNullList
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.item.ItemStack
 */
package net.unusual.block_factorys_bosses.util;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LootUtil {
    public static void saveItemsToTag(NonNullList<ItemStack> items, RegistryAccess access, CompoundTag tag) {
        tag.putBoolean("has_saved_items", !items.isEmpty());
        if (!items.isEmpty()) {
            ListTag listtag = new ListTag();
            for (int i = 0; i < items.size(); ++i) {
                ItemStack itemstack = (ItemStack)items.get(i);
                if (itemstack.isEmpty()) continue;
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putByte("slot", (byte)i);
                listtag.add((Tag)itemstack.save((HolderLookup.Provider)access, (Tag)compoundTag));
            }
            tag.put("saved_items", (Tag)listtag);
        }
    }

    public static void loadItemsFromTag(NonNullList<ItemStack> items, RegistryAccess access, CompoundTag tag) {
        if (tag.getBoolean("has_saved_items")) {
            ListTag listtag = tag.getList("saved_items", 10);
            for (int i = 0; i < listtag.size(); ++i) {
                CompoundTag compoundtag = listtag.getCompound(i);
                int j = compoundtag.getByte("slot") & 0xFF;
                items.set(j, ItemStack.parse((HolderLookup.Provider)access, (Tag)compoundtag).orElse(ItemStack.EMPTY));
            }
        }
    }
}

