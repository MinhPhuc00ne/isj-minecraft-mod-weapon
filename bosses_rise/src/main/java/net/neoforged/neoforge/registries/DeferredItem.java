package net.neoforged.neoforge.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import java.util.function.Supplier;

public class DeferredItem<T extends Item> extends DeferredHolder<Item, T> implements ItemLike {
    public DeferredItem(ResourceLocation id, Supplier<T> supplier) {
        super(id, supplier);
    }

    @Override
    public Item asItem() {
        return get();
    }

    public ItemStack toStack() {
        return new ItemStack(get());
    }

    public ItemStack toStack(int count) {
        return new ItemStack(get(), count);
    }
}
