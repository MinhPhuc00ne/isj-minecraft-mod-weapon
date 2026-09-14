package com.minhphuc.weapons.fabric;

import com.minhphuc.weapons.WeaponsMod;
import net.fabricmc.api.ModInitializer;

public class WeaponsModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        WeaponsMod.init();
    }
}
