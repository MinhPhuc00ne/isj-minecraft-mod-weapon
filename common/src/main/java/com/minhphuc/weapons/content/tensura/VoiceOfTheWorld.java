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
            Component.literal("§e§l==================================================\n" +
                    "§e§l[GIỌNG NÓI THẾ GIỚI] §d§lBÁO CÁO. LỄ HỘI THỨC TỈNH (HARVEST FESTIVAL) HOÀN TẤT!\n" +
                    "§fToàn bộ 64 Linh Hồn đã được hiến tế thành công.\n" +
                    "§fCá thể đã chính thức thức tỉnh và tiến hóa thành §d§l[CHÂN MA VƯƠNG (TRUE DEMON LORD)]§f!\n" +
                    "§aMở khóa Kỹ Năng Tối Thượng (Ultimate Skill): §6§lBẠO THỰC VƯƠNG BEELZEBUTH§a!\n" +
                    "§e⚡ Hướng dẫn: §fKhông cần cầm Hạt Giống, bạn có thể nhấn phím §6§l[Z] §fbất cứ lúc nào để thi triển Beelzebuth!\n" +
                    "§e§l==================================================")
        );

        player.displayClientMessage(
            Component.literal("§d§l[CHÂN MA VƯƠNG THỨC TỈNH] §aĐã mở khóa Bạo Thực Vương Beelzebuth (Nhấn phím Z)!"),
            true
        );

        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                SoundEvents.UI_TOAST_CHALLENGE_COMPLETE,
                SoundSource.PLAYERS,
                2.0F, 1.0F
            );
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

            // Bắn chùm hạt hào quang ma pháp quanh người chơi
            for (int i = 0; i < 360; i += 15) {
                double rad = Math.toRadians(i);
                double px = player.getX() + Math.cos(rad) * 2.5D;
                double pz = player.getZ() + Math.sin(rad) * 2.5D;
                serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.DRAGON_BREATH, px, player.getY() + 1.0D, pz, 4, 0.1D, 0.3D, 0.1D, 0.05D);
                serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.END_ROD, px, player.getY() + 0.5D, pz, 2, 0.05D, 0.2D, 0.05D, 0.02D);
                serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.TOTEM_OF_UNDYING, px, player.getY() + 1.5D, pz, 3, 0.1D, 0.3D, 0.1D, 0.05D);
            }
            serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.FLASH, player.getX(), player.getY() + 1.5D, player.getZ(), 2, 0, 0, 0, 0);
        }
    }
}
