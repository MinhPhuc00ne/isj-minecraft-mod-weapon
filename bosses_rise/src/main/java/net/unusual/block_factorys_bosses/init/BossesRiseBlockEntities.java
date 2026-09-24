/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.entity.BlockEntityType$BlockEntitySupplier
 *  net.minecraft.world.level.block.entity.BlockEntityType$Builder
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.capabilities.Capabilities$ItemHandler
 *  net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
 *  net.neoforged.neoforge.registries.DeferredHolder
 *  net.neoforged.neoforge.registries.DeferredRegister
 */
package net.unusual.block_factorys_bosses.init;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unusual.block_factorys_bosses.block.entity.BossSpawnerBlockEntity;
import net.unusual.block_factorys_bosses.block.entity.DragonBannerBlockEntity;
import net.unusual.block_factorys_bosses.block.entity.KrakenSpawnerBlockEntity;
import net.unusual.block_factorys_bosses.block.entity.PlankBlockEntity;
import net.unusual.block_factorys_bosses.block.entity.PrisonDoorBlockEntity;
import net.unusual.block_factorys_bosses.block.entity.RopeRollBlockEntity;
import net.unusual.block_factorys_bosses.block.entity.RustyPrisonDoorBlockEntity;
import net.unusual.block_factorys_bosses.block.entity.UnderworldArenaDoorBlockEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create((Registry)BuiltInRegistries.BLOCK_ENTITY_TYPE, (String)"block_factorys_bosses");
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PrisonDoorBlockEntity>> PRISON_DOOR = BossesRiseBlockEntities.register("prison_door", BossesRiseBlocks.PRISON_DOOR, PrisonDoorBlockEntity::new);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RustyPrisonDoorBlockEntity>> RUSTY_PRISON_DOOR = BossesRiseBlockEntities.register("rusty_prison_door", BossesRiseBlocks.RUSTY_PRISON_DOOR, RustyPrisonDoorBlockEntity::new);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DragonBannerBlockEntity>> DRAGON_BANNER = BossesRiseBlockEntities.register("dragon_banner", BossesRiseBlocks.DRAGON_BANNER, DragonBannerBlockEntity::new);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BossSpawnerBlockEntity>> BOSS_SPAWNER = BossesRiseBlockEntities.register("boss_spawner", BossesRiseBlocks.BOSS_SPAWNER, BossSpawnerBlockEntity::new);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<KrakenSpawnerBlockEntity>> KRAKEN_SPAWNER = BossesRiseBlockEntities.register("kraken_spawner", BossesRiseBlocks.KRAKEN_SPAWNER, KrakenSpawnerBlockEntity::new);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<UnderworldArenaDoorBlockEntity>> UNDERWORLD_ARENA_DOOR = BossesRiseBlockEntities.register("underworld_arena_door", BossesRiseBlocks.UNDERWORLD_ARENA_DOOR, UnderworldArenaDoorBlockEntity::new);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RopeRollBlockEntity>> ROPE_ROLL = BossesRiseBlockEntities.register("rope_roll", BossesRiseBlocks.ROPE_ROLL, RopeRollBlockEntity::new);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PlankBlockEntity>> PLANK = BossesRiseBlockEntities.register("plank", BossesRiseBlocks.PLANK, PlankBlockEntity::new);

    private static <T extends BlockEntity, P extends Block> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String registryname, DeferredHolder<Block, P> block, BlockEntityType.BlockEntitySupplier<T> supplier) {
        return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of((BlockEntityType.BlockEntitySupplier)supplier, (Block[])new Block[]{(Block)block.get()}).build(null));
    }
}

