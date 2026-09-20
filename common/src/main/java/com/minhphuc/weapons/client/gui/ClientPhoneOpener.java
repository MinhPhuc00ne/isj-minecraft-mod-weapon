package com.minhphuc.weapons.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;

public class ClientPhoneOpener {
    public static void openScreen(LivingEntity target) {
        Minecraft.getInstance().setScreen(new MagisteelPhoneScreen(target));
    }
}
