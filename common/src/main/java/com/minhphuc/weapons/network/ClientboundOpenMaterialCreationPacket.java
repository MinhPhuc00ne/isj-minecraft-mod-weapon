package com.minhphuc.weapons.network;

import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Supplier;

public class ClientboundOpenMaterialCreationPacket {

    public ClientboundOpenMaterialCreationPacket() {
    }

    public ClientboundOpenMaterialCreationPacket(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkManager.PacketContext> contextSupplier) {
        NetworkManager.PacketContext context = contextSupplier.get();
        context.queue(() -> {
            if (context.getPlayer() != null) {
                openScreen();
            }
        });
    }

    private void openScreen() {
        Minecraft.getInstance().setScreen(new com.minhphuc.weapons.client.gui.MaterialCreationScreen());
    }
}
