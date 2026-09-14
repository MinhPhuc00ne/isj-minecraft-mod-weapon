package com.minhphuc.weapons.mixin;

import net.minecraft.world.entity.Display;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Display.ItemDisplay.class)
public interface ItemDisplayAccessor {
    @Invoker("setItemStack")
    void weapons$setItemStack(ItemStack itemStack);

    @Invoker("setItemTransform")
    void weapons$setItemTransform(ItemDisplayContext itemTransform);
}
