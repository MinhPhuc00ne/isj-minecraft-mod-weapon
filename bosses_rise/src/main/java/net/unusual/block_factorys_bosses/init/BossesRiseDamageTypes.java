/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.damagesource.DamageType
 */
package net.unusual.block_factorys_bosses.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.unusual.block_factorys_bosses.BossesRise;

public class BossesRiseDamageTypes {
    public static final ResourceKey<DamageType> CANNONBALL_HIT = ResourceKey.create((ResourceKey)Registries.DAMAGE_TYPE, (ResourceLocation)BossesRise.prefix("cannonball_hit"));
    public static final ResourceKey<DamageType> KRAKEN_TENTACLE_SMASH = ResourceKey.create((ResourceKey)Registries.DAMAGE_TYPE, (ResourceLocation)BossesRise.prefix("kraken_tentacle_smash"));
}

