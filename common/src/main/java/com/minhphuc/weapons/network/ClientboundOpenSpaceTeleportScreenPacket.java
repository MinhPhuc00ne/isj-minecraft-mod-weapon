package com.minhphuc.weapons.network;

import com.minhphuc.weapons.content.infinitygauntlet.SpaceTeleportScreen;
import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Supplier;

public class ClientboundOpenSpaceTeleportScreenPacket {

    public ClientboundOpenSpaceTeleportScreenPacket() {
    }

    public ClientboundOpenSpaceTeleportScreenPacket(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkManager.PacketContext> contextSupplier) {
        NetworkManager.PacketContext context = contextSupplier.get();
        context.queue(() -> {
            Minecraft.getInstance().setScreen(new SpaceTeleportScreen());
        });
    }
}
