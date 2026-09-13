package com.minhphuc.weapons.network;

import com.minhphuc.weapons.content.tensura.BeelzebuthAbility;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundCastBeelzebuthPacket {

    public ServerboundCastBeelzebuthPacket() {
    }

    public ServerboundCastBeelzebuthPacket(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            boolean isTrueDemonLord = player.getPersistentData().getBoolean("TensuraTrueDemonLord");
            boolean hasSeedItem = player.getMainHandItem().getItem() instanceof com.minhphuc.weapons.content.tensura.DemonLordSeedItem
                    || player.getOffhandItem().getItem() instanceof com.minhphuc.weapons.content.tensura.DemonLordSeedItem;

            if (isTrueDemonLord || hasSeedItem) {
                BeelzebuthAbility.executeBeelzebuth((ServerLevel) player.level(), player);
            } else {
                player.displayClientMessage(
                    Component.literal("§c[BEELZEBUTH] Bạn chưa thức tỉnh thành Chân Ma Vương! (Cần Hạt Giống Ma Vương + 64 Linh Hồn & Đi Ngủ)"),
                    true
                );
            }
        });
        context.setPacketHandled(true);
    }
}
