/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.Util
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.item.ArmorItem$Type
 *  net.minecraft.world.item.ArmorMaterial
 *  net.minecraft.world.item.ArmorMaterial$Layer
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.neoforged.neoforge.registries.DeferredHolder
 *  net.neoforged.neoforge.registries.DeferredRegister
 */
package net.unusual.block_factorys_bosses.init;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;

public class BossesRiseArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> REGISTRY = DeferredRegister.create((ResourceKey)Registries.ARMOR_MATERIAL, (String)"block_factorys_bosses");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> DRAGON_ARMOR = REGISTRY.register("dragon_bones", () -> new ArmorMaterial((Map)Util.make(new EnumMap(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
        map.put(ArmorItem.Type.BODY, 8);
    }), 9, SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of((ItemStack[])new ItemStack[]{new ItemStack((ItemLike)BossesRiseItems.DRAGON_BONE.get())}), List.of(new ArmorMaterial.Layer(BossesRise.prefix("dragon_armor"))), 2.0f, 0.0f));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> KNIGHT_ARMOR = REGISTRY.register("knight", () -> new ArmorMaterial((Map)Util.make(new EnumMap(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.CHESTPLATE, 6);
        map.put(ArmorItem.Type.HELMET, 2);
        map.put(ArmorItem.Type.BODY, 6);
    }), 9, SoundEvents.ARMOR_EQUIP_NETHERITE, Ingredient::of, List.of(new ArmorMaterial.Layer(BossesRise.prefix("invisible"))), 0.5f, 0.0f));
}

