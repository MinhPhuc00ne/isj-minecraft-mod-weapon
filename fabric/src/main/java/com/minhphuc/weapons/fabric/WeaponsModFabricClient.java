package com.minhphuc.weapons.fabric;

import com.minhphuc.weapons.WeaponsMod;
import com.minhphuc.weapons.init.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class WeaponsModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WeaponsMod.initClient();
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.INCUBATION_CAPSULE.get(), RenderType.translucent());
    }
}
