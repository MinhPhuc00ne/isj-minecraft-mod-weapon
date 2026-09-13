package com.minhphuc.weapons.forge;

import com.minhphuc.weapons.WeaponsMod;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WeaponsMod.MOD_ID)
public class WeaponsModForge {
    public WeaponsModForge() {
        EventBuses.registerModEventBus(WeaponsMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        WeaponsMod.init();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> WeaponsMod::initClient);
    }
}
