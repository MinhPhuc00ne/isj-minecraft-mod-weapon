package com.minhphuc.weapons.content.tensura;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class VoiceOfTheWorld {

    /**
     * Phát thông báo Giọng Nói Thế Giới cho người chơi
     */
    public static void announce(ServerPlayer player, String message) {
        if (player == null) return;

        player.sendSystemMessage(
            Component.literal("§e§l[GIỌNG NÓI THẾ GIỚI] §f" + message)
        );

        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                SoundEvents.BEACON_ACTIVATE,
                SoundSource.PLAYERS,
                1.0F, 1.4F
            );
            serverLevel.playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                SoundEvents.UI_TOAST_CHALLENGE_COMPLETE,
                SoundSource.PLAYERS,
                0.8F, 1.2F
            );
        }
    }

    public static void announceEvolutionSuccess(ServerPlayer player) {
        if (player == null) return;

        player.sendSystemMessage(
            Component.literal("§e§l[GIỌNG NÓI THẾ GIỚI] §d§lBÁO CÁO. QUÁ TRÌNH TIẾN HÓA HOÀN TẤT!\n" +
                    "§fCá thể đã chính thức thức tỉnh thành §d§l[CHÂN MA VƯƠNG (TRUE DEMON LORD)]§f!\n" +
                    "§aMở khóa Kỹ Năng Tối Thượng: §6§lBẠO THỰC VƯƠNG BEELZEBUTH §7(Nhấn phím Z hoặc Chuột phải để thi triển)!")
        );

        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                SoundEvents.WITHER_SPAWN,
                SoundSource.PLAYERS,
                1.5F, 1.0F
            );
            serverLevel.playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                SoundEvents.LIGHTNING_BOLT_THUNDER,
                SoundSource.PLAYERS,
                1.5F, 1.0F
            );
        }
    }
}
