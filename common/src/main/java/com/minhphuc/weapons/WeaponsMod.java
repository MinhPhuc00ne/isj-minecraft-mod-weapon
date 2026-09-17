package com.minhphuc.weapons;

import com.minhphuc.weapons.client.ClientInputEvents;
import com.minhphuc.weapons.config.AIGeminiConfig;
import com.minhphuc.weapons.content.infinitygauntlet.InfinityGauntletEvents;
import com.minhphuc.weapons.content.tensura.TensuraEvents;
import com.minhphuc.weapons.init.ModItems;
import com.minhphuc.weapons.init.ModKeyBindings;
import com.minhphuc.weapons.network.ModMessages;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public class WeaponsMod {
    public static final String MOD_ID = "weapons";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        // Đăng ký Items và Creative Tabs
        ModItems.register();

        // Đăng ký Network Packets
        ModMessages.register();

        // Nạp cấu hình Gemini AI
        AIGeminiConfig.loadConfig();

        // Đăng ký các sự kiện Gameplay đa nền tảng
        InfinityGauntletEvents.register();
        TensuraEvents.register();
        com.minhphuc.weapons.content.divine.DivineWeaponEvents.register();

        LOGGER.info("Weapons Mod (Cross-Platform) initialized successfully!");
    }

    public static void initClient() {
        ModKeyBindings.register();
        ClientInputEvents.register();
        com.minhphuc.weapons.client.ClientTaisuiHandler.init();
        com.minhphuc.weapons.client.ClientModelProperties.register();
        LOGGER.info("Weapons Mod Client Setup complete!");
    }
}
