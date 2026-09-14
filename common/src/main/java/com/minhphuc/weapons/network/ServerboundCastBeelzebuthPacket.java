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

            if (isTrueDemonLord) {
                // Kiểm tra hồi chiêu kỹ năng (1.5 giây)
                if (player.getCooldowns().isOnCooldown(com.minhphuc.weapons.init.ModItems.DEMON_LORD_SEED.get())) {
                    return;
                }
                
                int selectedSkill = EntityDataHelper.getCustomData(player).getInt("TensuraDemonLordSkill");
                ServerLevel serverLevel = (ServerLevel) player.level();
                
                if (selectedSkill == 1) {
                    // Chiêu 2: Bạo Thực Vương - Hủ Hóa & Bạo Liệt
                    BeelzebuthAbility.executeCorrosion(serverLevel, player);
                } else {
                    // Chiêu 1: Bạo Thực Vương - Thôn Phệ (Mặc định)
                    BeelzebuthAbility.executeBeelzebuth(serverLevel, player);
                }
            } else {
                player.displayClientMessage(
                    Component.literal("§e§l[GIỌNG NÓI THẾ GIỚI] §cBáo cáo. Cá thể chưa thức tỉnh thành Chân Ma Vương!"),
                    true
                );
            }
        });
    }
}
