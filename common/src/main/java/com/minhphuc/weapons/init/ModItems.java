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

    public static final RegistrySupplier<Item> BEELZEBUTH_DRAGON_MAW = ITEMS.register("beelzebuth_dragon_maw",
            () -> new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> BEELZEBUTH_MAGIC_CIRCLE = ITEMS.register("beelzebuth_magic_circle",
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
                    })
            ));

    public static void register() {
        ITEMS.register();
        CREATIVE_MODE_TABS.register();
    }
}
