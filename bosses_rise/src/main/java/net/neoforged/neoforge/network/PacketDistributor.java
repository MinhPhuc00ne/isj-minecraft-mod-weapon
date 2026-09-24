package net.neoforged.neoforge.network;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;

public class PacketDistributor {
    public static void sendToPlayer(ServerPlayer player, CustomPacketPayload payload, CustomPacketPayload... extra) {
        if (player != null && payload != null) {
            ServerPlayNetworking.send(player, payload);
            for (CustomPacketPayload p : extra) {
                ServerPlayNetworking.send(player, p);
            }
        }
    }

    public static void sendToAllPlayers(CustomPacketPayload payload, CustomPacketPayload... extra) {
        // Can be handled when player is known or via server
    }

    public static void sendToPlayersInDimension(ServerLevel level, CustomPacketPayload payload, CustomPacketPayload... extra) {
        if (level != null && payload != null) {
            for (ServerPlayer player : PlayerLookup.world(level)) {
                sendToPlayer(player, payload, extra);
            }
        }
    }

    public static void sendToPlayersTrackingEntity(Entity entity, CustomPacketPayload payload, CustomPacketPayload... extra) {
        if (entity != null && payload != null) {
            for (ServerPlayer player : PlayerLookup.tracking(entity)) {
                sendToPlayer(player, payload, extra);
            }
        }
    }

    public static void sendToPlayersTrackingEntityAndSelf(Entity entity, CustomPacketPayload payload, CustomPacketPayload... extra) {
        if (entity != null && payload != null) {
            for (ServerPlayer player : PlayerLookup.tracking(entity)) {
                sendToPlayer(player, payload, extra);
            }
            if (entity instanceof ServerPlayer sp) {
                sendToPlayer(sp, payload, extra);
            }
        }
    }

    public static void sendToPlayersTrackingChunk(ServerLevel level, ChunkPos chunkPos, CustomPacketPayload payload, CustomPacketPayload... extra) {
        if (level != null && payload != null) {
            for (ServerPlayer player : PlayerLookup.tracking(level, chunkPos)) {
                sendToPlayer(player, payload, extra);
            }
        }
    }

    public static void sendToPlayersNear(ServerLevel level, ServerPlayer excluded, double x, double y, double z, double radius, CustomPacketPayload payload, CustomPacketPayload... extra) {
        if (level != null && payload != null) {
            for (ServerPlayer player : PlayerLookup.around(level, new Vec3(x, y, z), radius)) {
                if (player != excluded) {
                    sendToPlayer(player, payload, extra);
                }
            }
        }
    }
}
