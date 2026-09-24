/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 */
package net.unusual.block_factorys_bosses.init;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.unusual.block_factorys_bosses.BossesRise;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseTags {

    public static class Biomes {
        public static final TagKey<Biome> DRAGON_TOWER = TagKey.create((ResourceKey)Registries.BIOME, (ResourceLocation)BossesRise.prefix("dragon_tower"));
        public static final TagKey<Biome> SANDWORM_NEST = TagKey.create((ResourceKey)Registries.BIOME, (ResourceLocation)BossesRise.prefix("sandworm_nest"));
        public static final TagKey<Biome> UNDERWORLD_ARENA = TagKey.create((ResourceKey)Registries.BIOME, (ResourceLocation)BossesRise.prefix("underworld_arena"));
        public static final TagKey<Biome> YETI_HIDEOUT = TagKey.create((ResourceKey)Registries.BIOME, (ResourceLocation)BossesRise.prefix("yeti_hideout"));
        public static final TagKey<Biome> KRAKEN_SHIP = TagKey.create((ResourceKey)Registries.BIOME, (ResourceLocation)BossesRise.prefix("kraken_ship"));
    }

    public static class Items {
        public static final TagKey<Item> BONE_REMAINS = TagKey.create(Registries.ITEM, BossesRise.prefix("bone_remains"));
        public static final TagKey<Item> GLOWING_LOOT = TagKey.create(Registries.ITEM, BossesRise.prefix("glowing_loot"));
        public static final TagKey<Item> SHIP_PLANKS = TagKey.create(Registries.ITEM, BossesRise.prefix("ship_planks"));
    }

    public static class Blocks {
        public static final TagKey<Block> UNDERWORLD_ARENA_VALID_SPAWN = TagKey.create(Registries.BLOCK, BossesRise.prefix("underworld_arena_valid_spawn"));
        public static final TagKey<Block> BONE_REMAINS = TagKey.create(Registries.BLOCK, BossesRise.prefix("bone_remains"));
        public static final TagKey<Block> SHIP_PLANKS = TagKey.create(Registries.BLOCK, BossesRise.prefix("ship_planks"));
        public static final TagKey<Block> PROTECTION_EXEMPT = TagKey.create(Registries.BLOCK, BossesRise.prefix("protection_exempt"));
    }

    public static class EntityTypes {
        public static final TagKey<EntityType<?>> BF_BOSS = TagKey.create((ResourceKey)Registries.ENTITY_TYPE, (ResourceLocation)BossesRise.prefix("bf_boss"));
        public static final TagKey<EntityType<?>> PIRATE = TagKey.create((ResourceKey)Registries.ENTITY_TYPE, (ResourceLocation)BossesRise.prefix("pirate"));
    }
}

