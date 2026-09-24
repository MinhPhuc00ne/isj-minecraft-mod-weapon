package net.neoforged.neoforge.registries;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class DeferredRegister<T> {
    protected final ResourceKey<? extends Registry<T>> registryKey;
    protected final String modid;
    protected final List<DeferredHolder<T, ? extends T>> entries = new ArrayList<>();

    public DeferredRegister(ResourceKey<? extends Registry<T>> registryKey, String modid) {
        this.registryKey = registryKey;
        this.modid = modid;
    }

    public static <T> DeferredRegister<T> create(ResourceKey<? extends Registry<T>> registryKey, String modid) {
        return new DeferredRegister<>(registryKey, modid);
    }

    public static <T> DeferredRegister<T> create(Registry<T> registry, String modid) {
        return new DeferredRegister<>(registry.key(), modid);
    }

    public static Items createItems(String modid) {
        return Items.createItems(modid);
    }

    public static Blocks createBlocks(String modid) {
        return Blocks.createBlocks(modid);
    }

    public String getModid() { return modid; }

    public void register(DeferredHolder<T, ? extends T> holder) {
        entries.add(holder);
    }

    public <I extends T> DeferredHolder<T, I> register(String name, Supplier<? extends I> supplier) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(modid, name);
        DeferredHolder<T, I> holder = new DeferredHolder<>(id, (Supplier<I>) supplier);
        entries.add(holder);
        return holder;
    }

    public void register(IEventBus bus) {
        Registry<T> reg = (Registry<T>) BuiltInRegistries.REGISTRY.get(registryKey.location());
        if (reg != null) {
            for (DeferredHolder<T, ? extends T> holder : entries) {
                Registry.register(reg, holder.getId(), holder.get());
            }
        }
    }

    public static class Items extends DeferredRegister<net.minecraft.world.item.Item> {
        public Items(ResourceKey<? extends Registry<net.minecraft.world.item.Item>> registryKey, String modid) {
            super(registryKey, modid);
        }

        public static Items createItems(String modid) {
            return new Items(Registries.ITEM, modid);
        }

        @Override
        public <I extends net.minecraft.world.item.Item> DeferredItem<I> register(String name, Supplier<? extends I> supplier) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(this.getModid(), name);
            DeferredItem<I> holder = new DeferredItem<>(id, (Supplier<I>) supplier);
            this.register((DeferredHolder) holder);
            return holder;
        }

        public <I extends net.minecraft.world.item.Item> DeferredItem<I> registerItem(String name, Function<net.minecraft.world.item.Item.Properties, I> factory, net.minecraft.world.item.Item.Properties properties) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(this.getModid(), name);
            DeferredItem<I> holder = new DeferredItem<>(id, () -> factory.apply(properties));
            this.register((DeferredHolder) holder);
            return holder;
        }

        public <I extends net.minecraft.world.item.Item> DeferredItem<I> registerItem(String name, Function<net.minecraft.world.item.Item.Properties, I> factory) {
            return registerItem(name, factory, new net.minecraft.world.item.Item.Properties());
        }

        public <I extends net.minecraft.world.item.Item> DeferredItem<I> registerSimpleItem(String name, Function<net.minecraft.world.item.Item.Properties, I> factory) {
            return registerItem(name, factory);
        }

        public <B extends net.minecraft.world.level.block.Block> DeferredItem<net.minecraft.world.item.BlockItem> registerSimpleBlockItem(String name, DeferredHolder<net.minecraft.world.level.block.Block, B> block) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(this.getModid(), name);
            DeferredItem<net.minecraft.world.item.BlockItem> holder = new DeferredItem<>(id, () -> new net.minecraft.world.item.BlockItem(block.get(), new net.minecraft.world.item.Item.Properties()));
            this.register((DeferredHolder) holder);
            return holder;
        }

        public <B extends net.minecraft.world.level.block.Block> DeferredItem<net.minecraft.world.item.BlockItem> registerSimpleBlockItem(DeferredHolder<net.minecraft.world.level.block.Block, B> block) {
            return registerSimpleBlockItem(block.getId().getPath(), block);
        }

        public void addAlias(ResourceLocation from, ResourceLocation to) {}
    }

    public static class Blocks extends DeferredRegister<net.minecraft.world.level.block.Block> {
        public Blocks(ResourceKey<? extends Registry<net.minecraft.world.level.block.Block>> registryKey, String modid) {
            super(registryKey, modid);
        }

        public static Blocks createBlocks(String modid) {
            return new Blocks(Registries.BLOCK, modid);
        }

        @Override
        public <B extends net.minecraft.world.level.block.Block> DeferredBlock<B> register(String name, Supplier<? extends B> supplier) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(this.getModid(), name);
            DeferredBlock<B> holder = new DeferredBlock<>(id, (Supplier<B>) supplier);
            this.register((DeferredHolder) holder);
            return holder;
        }

        public <B extends net.minecraft.world.level.block.Block> DeferredBlock<B> registerBlock(String name, Function<net.minecraft.world.level.block.state.BlockBehaviour.Properties, B> factory, net.minecraft.world.level.block.state.BlockBehaviour.Properties properties) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(this.getModid(), name);
            DeferredBlock<B> holder = new DeferredBlock<>(id, () -> factory.apply(properties));
            this.register((DeferredHolder) holder);
            return holder;
        }
    }

    public static class DataComponents extends DeferredRegister<net.minecraft.core.component.DataComponentType<?>> {
        public DataComponents(ResourceKey<? extends Registry<net.minecraft.core.component.DataComponentType<?>>> registryKey, String modid) {
            super(registryKey, modid);
        }

        public <D> Supplier<net.minecraft.core.component.DataComponentType<D>> registerComponentType(String name, UnaryOperator<net.minecraft.core.component.DataComponentType.Builder<D>> builder) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(modid, name);
            Supplier<net.minecraft.core.component.DataComponentType<D>> sup = () -> builder.apply(net.minecraft.core.component.DataComponentType.builder()).build();
            DeferredHolder<net.minecraft.core.component.DataComponentType<?>, net.minecraft.core.component.DataComponentType<D>> holder = new DeferredHolder<>(id, sup);
            entries.add((DeferredHolder) holder);
            return holder;
        }
    }

    public static DataComponents createDataComponents(ResourceKey<? extends Registry<net.minecraft.core.component.DataComponentType<?>>> registryKey, String modid) {
        return new DataComponents(registryKey, modid);
    }

    public java.util.Optional<Registry<T>> getRegistry() {
        return java.util.Optional.ofNullable((Registry<T>) BuiltInRegistries.REGISTRY.get(registryKey.location()));
    }
}
