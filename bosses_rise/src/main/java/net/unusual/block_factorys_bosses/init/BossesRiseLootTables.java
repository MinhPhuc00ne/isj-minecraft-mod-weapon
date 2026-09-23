/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.storage.loot.LootTable
 */
package net.unusual.block_factorys_bosses.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.unusual.block_factorys_bosses.BossesRise;

public class BossesRiseLootTables {
    public static final ResourceKey<LootTable> ANCIENT_TRIAL_KEY = ResourceKey.create((ResourceKey)Registries.LOOT_TABLE, (ResourceLocation)BossesRise.prefix("spawners/ancient_trial_key"));
}

