package com.minhphuc.weapons.network;

import com.minhphuc.weapons.client.ClientTaisuiHandler;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;
import java.util.function.Supplier;

public class ClientboundSyncTaisuiPacket {
    private final UUID playerUuid;
    private final boolean active;
    private final int starsRemaining;

    public ClientboundSyncTaisuiPacket(UUID playerUuid, boolean active, int starsRemaining) {
        this.playerUuid = playerUuid;
        this.active = active;
        this.starsRemaining = starsRemaining;
    }

    public ClientboundSyncTaisuiPacket(FriendlyByteBuf buf) {
        this.playerUuid = buf.readUUID();
        this.active = buf.readBoolean();
        this.starsRemaining = buf.readVarInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(this.playerUuid);
        buf.writeBoolean(this.active);
        buf.writeVarInt(this.starsRemaining);
    }

    public void handle(Supplier<NetworkManager.PacketContext> contextSupplier) {
        NetworkManager.PacketContext context = contextSupplier.get();
        context.queue(() -> {
            ClientTaisuiHandler.setTaisuiState(this.playerUuid, this.active, this.starsRemaining);
        });
    }
}
