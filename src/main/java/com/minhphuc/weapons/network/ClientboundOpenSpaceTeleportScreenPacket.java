package com.minhphuc.weapons.network;

import com.minhphuc.weapons.content.infinitygauntlet.SpaceTeleportScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundOpenSpaceTeleportScreenPacket {

    public ClientboundOpenSpaceTeleportScreenPacket() {
    }

    public ClientboundOpenSpaceTeleportScreenPacket(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Minecraft.getInstance().setScreen(new SpaceTeleportScreen());
            });
        });
        context.setPacketHandled(true);
    }
}
