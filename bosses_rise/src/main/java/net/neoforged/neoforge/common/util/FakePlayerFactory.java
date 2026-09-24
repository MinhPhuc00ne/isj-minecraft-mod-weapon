package net.neoforged.neoforge.common.util;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class FakePlayerFactory {
    private static final GameProfile MINECRAFT = new GameProfile(UUID.fromString("41C82C87-7AfB-4045-BB58-001C3C3D5D52"), "[Minecraft]");

    public static ServerPlayer getMinecraft(ServerLevel level) {
        return new ServerPlayer(level.getServer(), level, MINECRAFT, ClientInformation.createDefault());
    }
}
