package net.neoforged.neoforge.network.registration;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public class PayloadRegistrar {
    public <T extends CustomPacketPayload> void playBidirectional(CustomPacketPayload.Type<T> type, StreamCodec<? extends FriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
    }
}
