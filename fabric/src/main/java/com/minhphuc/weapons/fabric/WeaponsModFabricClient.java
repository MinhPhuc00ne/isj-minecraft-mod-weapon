package com.minhphuc.weapons.fabric;

import com.minhphuc.weapons.WeaponsMod;
import net.fabricmc.api.ClientModInitializer;

public class WeaponsModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WeaponsMod.initClient();
    }
}
