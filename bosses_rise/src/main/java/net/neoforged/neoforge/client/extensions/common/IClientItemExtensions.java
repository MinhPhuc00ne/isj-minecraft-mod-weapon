package net.neoforged.neoforge.client.extensions.common;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;

public interface IClientItemExtensions {
    IClientItemExtensions DEFAULT = new IClientItemExtensions() {};

    default BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return null;
    }

    static IClientItemExtensions of(net.minecraft.world.item.ItemStack stack) {
        return DEFAULT;
    }
}
