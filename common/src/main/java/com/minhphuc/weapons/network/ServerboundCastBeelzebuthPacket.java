package com.minhphuc.weapons.network;

import com.minhphuc.weapons.content.tensura.BeelzebuthAbility;
import com.minhphuc.weapons.data.EntityDataHelper;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Supplier;

public class ServerboundCastBeelzebuthPacket {

    public ServerboundCastBeelzebuthPacket() {
    }

    public ServerboundCastBeelzebuthPacket(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkManager.PacketContext> contextSupplier) {
        NetworkManager.PacketContext context = contextSupplier.get();
        context.queue(() -> {
            ServerPlayer player = (ServerPlayer) context.getPlayer();
            if (player == null) return;

            boolean isTrueDemonLord = EntityDataHelper.getCustomData(player).getBoolean("TensuraTrueDemonLord");
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
    }
}
