package net.neoforged.neoforge.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class Tags {
    public static class Items {
        public static final TagKey<Item> TOOLS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools"));
        public static final TagKey<Item> MELEE_WEAPON_TOOLS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/melee_weapon"));
    }
    public static class Blocks {
    }
}
