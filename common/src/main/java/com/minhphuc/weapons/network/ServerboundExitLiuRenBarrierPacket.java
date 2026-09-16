package com.minhphuc.weapons.network;

import com.minhphuc.weapons.content.darkgathering.LiuRenBarrierAbility;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import java.util.function.Supplier;

/**
 * Packet gửi từ Client khi người chơi nhấn Phím Cách 2 lần (Double-Tap Space)
 * để bay lên và thoát khỏi kết giới Lục Nhậm Thần Khóa ngay lập tức.
 */
public class ServerboundExitLiuRenBarrierPacket {

    public ServerboundExitLiuRenBarrierPacket() {
    }

    public ServerboundExitLiuRenBarrierPacket(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkManager.PacketContext> contextSupplier) {
        NetworkManager.PacketContext context = contextSupplier.get();
        context.queue(() -> {
            ServerPlayer player = (ServerPlayer) context.getPlayer();
            if (player == null) return;

            if (LiuRenBarrierAbility.isBarrierActive(player)) {
                ServerLevel level = (ServerLevel) player.level();
                LiuRenBarrierAbility.toggleBarrier(level, player);

                // Hất vút người chơi bay lên trời theo đúng yêu cầu "nhấn cách 2 lần kiểu bay lên"
                player.setDeltaMovement(0, 0.75D, 0);
                player.hurtMarked = true;

                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.PLAYERS, 2.5F, 1.2F);
            }
        });
    }
}
