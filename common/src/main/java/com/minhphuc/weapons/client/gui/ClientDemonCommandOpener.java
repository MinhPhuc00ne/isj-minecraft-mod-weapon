package com.minhphuc.weapons.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

public class ClientDemonCommandOpener {
    public static void openScreen(ItemStack pactStack) {
        Minecraft.getInstance().setScreen(new DemonCommandScreen(pactStack));
    }
}
