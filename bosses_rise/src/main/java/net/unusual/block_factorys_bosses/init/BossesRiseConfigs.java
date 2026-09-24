/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.ModList
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.fml.config.IConfigSpec
 *  net.neoforged.fml.config.ModConfig$Type
 *  net.neoforged.fml.event.lifecycle.FMLConstructModEvent
 */
package net.unusual.block_factorys_bosses.init;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.unusual.block_factorys_bosses.configuration.ClientConfiguration;
import net.unusual.block_factorys_bosses.configuration.ServerConfiguration;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@EventBusSubscriber
public class BossesRiseConfigs {
    @SubscribeEvent
    public static void register(FMLConstructModEvent event) {
        event.enqueueWork(() -> ModList.get().getModContainerById("block_factorys_bosses").ifPresent(modContainer -> {
            modContainer.registerConfig(ModConfig.Type.CLIENT, (IConfigSpec)ClientConfiguration.SPEC);
            modContainer.registerConfig(ModConfig.Type.SERVER, (IConfigSpec)ServerConfiguration.SPEC);
        }));
    }
}

