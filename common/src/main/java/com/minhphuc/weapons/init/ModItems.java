package com.minhphuc.weapons.init;

import com.minhphuc.weapons.WeaponsMod;
import com.minhphuc.weapons.content.infinitygauntlet.InfinityGauntletItem;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(WeaponsMod.MOD_ID, Registries.ITEM);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(WeaponsMod.MOD_ID, Registries.CREATIVE_MODE_TAB);

    // ==========================================
    // MODULE: INFINITY GAUNTLET & STONES
    // ==========================================
    public static final RegistrySupplier<Item> INFINITY_GAUNTLET = ITEMS.register("infinity_gauntlet",
            () -> new InfinityGauntletItem(new Item.Properties()));

    public static final RegistrySupplier<Item> POWER_STONE = ITEMS.register("power_stone",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistrySupplier<Item> SPACE_STONE = ITEMS.register("space_stone",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistrySupplier<Item> REALITY_STONE = ITEMS.register("reality_stone",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistrySupplier<Item> SOUL_STONE = ITEMS.register("soul_stone",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistrySupplier<Item> TIME_STONE = ITEMS.register("time_stone",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistrySupplier<Item> MIND_STONE = ITEMS.register("mind_stone",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    // ==========================================
    // MODULE: TENSURA DEMON LORD EVOLUTION
    // ==========================================
    public static final RegistrySupplier<Item> DEMON_LORD_SEED = ITEMS.register("demon_lord_seed",
            () -> new com.minhphuc.weapons.content.tensura.DemonLordSeedItem(new Item.Properties()));

    public static final RegistrySupplier<Item> DEMON_LORD_SOUL = ITEMS.register("demon_lord_soul",
            () -> new com.minhphuc.weapons.content.tensura.DemonLordSoulItem(new Item.Properties()));

    public static final DeferredRegister<net.minecraft.world.item.ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(WeaponsMod.MOD_ID, Registries.ARMOR_MATERIAL);

    // ==========================================
    // MODULE: DIVINE ARMOR & MOONLIGHT SWORD (THẦN THOẠI)
    // ==========================================
    public static final RegistrySupplier<net.minecraft.world.item.ArmorMaterial> DIVINE_ARMOR_MATERIAL = ARMOR_MATERIALS.register("divine", () -> {
        java.util.Map<net.minecraft.world.item.ArmorItem.Type, Integer> defense = java.util.Map.of(
                net.minecraft.world.item.ArmorItem.Type.BOOTS, 5,
                net.minecraft.world.item.ArmorItem.Type.LEGGINGS, 8,
                net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, 10,
                net.minecraft.world.item.ArmorItem.Type.HELMET, 5
        );
        return new net.minecraft.world.item.ArmorMaterial(
                defense,
                25,
                net.minecraft.sounds.SoundEvents.ARMOR_EQUIP_NETHERITE,
                () -> net.minecraft.world.item.crafting.Ingredient.EMPTY,
                java.util.List.of(new net.minecraft.world.item.ArmorMaterial.Layer(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(WeaponsMod.MOD_ID, "divine"))),
                4.0F,
                0.2F
        );
    });

    public static final RegistrySupplier<Item> DIVINE_HELMET = ITEMS.register("divine_helmet",
            () -> new com.minhphuc.weapons.content.divine.DivineArmorItem(DIVINE_ARMOR_MATERIAL, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistrySupplier<Item> DIVINE_CHESTPLATE = ITEMS.register("divine_chestplate",
            () -> new com.minhphuc.weapons.content.divine.DivineArmorItem(DIVINE_ARMOR_MATERIAL, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistrySupplier<Item> DIVINE_LEGGINGS = ITEMS.register("divine_leggings",
            () -> new com.minhphuc.weapons.content.divine.DivineArmorItem(DIVINE_ARMOR_MATERIAL, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistrySupplier<Item> DIVINE_BOOTS = ITEMS.register("divine_boots",
            () -> new com.minhphuc.weapons.content.divine.DivineArmorItem(DIVINE_ARMOR_MATERIAL, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistrySupplier<Item> MOONLIGHT_SWORD = ITEMS.register("moonlight_sword",
            () -> new com.minhphuc.weapons.content.divine.MoonlightSwordItem(new Item.Properties()));

    public static final RegistrySupplier<Item> DISINTEGRATION_MAGIC_CIRCLE = ITEMS.register("disintegration_magic_circle",
            () -> new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> BEELZEBUTH_MAGIC_CIRCLE = ITEMS.register("beelzebuth_magic_circle",
            () -> new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> BEELZEBUTH_DRAGON_MAW = ITEMS.register("beelzebuth_dragon_maw",
            () -> new Item(new Item.Properties()));

    // ==========================================
    // CREATIVE TAB
    // ==========================================
    public static final RegistrySupplier<CreativeModeTab> WEAPONS_TAB = CREATIVE_MODE_TABS.register("weapons_tab",
            () -> CreativeTabRegistry.create(builder -> builder
                    .title(Component.literal("§6§lVũ Khí & Găng Tay Vô Cực"))
                    .icon(() -> new ItemStack(INFINITY_GAUNTLET.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(INFINITY_GAUNTLET.get());
                        output.accept(POWER_STONE.get());
                        output.accept(SPACE_STONE.get());
                        output.accept(REALITY_STONE.get());
                        output.accept(SOUL_STONE.get());
                        output.accept(TIME_STONE.get());
                        output.accept(MIND_STONE.get());
                        output.accept(DEMON_LORD_SEED.get());
                        output.accept(DEMON_LORD_SOUL.get());
                        output.accept(DIVINE_HELMET.get());
                        output.accept(DIVINE_CHESTPLATE.get());
                        output.accept(DIVINE_LEGGINGS.get());
                        output.accept(DIVINE_BOOTS.get());
                        output.accept(MOONLIGHT_SWORD.get());
                    })
            ));

    public static void register() {
        ARMOR_MATERIALS.register();
        ITEMS.register();
        CREATIVE_MODE_TABS.register();
    }
}
