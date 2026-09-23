package net.neoforged.neoforge.event.tick;

import net.minecraft.server.MinecraftServer;

public class ServerTickEvent {
    public static class Post {
        public MinecraftServer getServer() { return null; }
    }
}
