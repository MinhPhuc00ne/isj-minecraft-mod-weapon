package com.minhphuc.weapons.client;

import com.minhphuc.weapons.init.ModItems;
import com.minhphuc.weapons.mixin.ItemPropertiesAccessor;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.CustomData;

public class ClientModelProperties {
    public static void register() {
        ItemPropertiesAccessor.weapons$register(
                ModItems.SEER_FLESH_ARM.get(),
                ResourceLocation.fromNamespaceAndPath("weapons", "eyes"),
                (stack, level, entity, seed) -> {
                    int eyes = 3;
                    if (stack.has(DataComponents.CUSTOM_DATA)) {
                        CustomData cd = stack.get(DataComponents.CUSTOM_DATA);
                        if (cd != null && cd.contains("EyesRemaining")) {
                            eyes = cd.copyTag().getInt("EyesRemaining");
                        }
                    }
                    return eyes / 3.0F;
                }
        );
    }
}
