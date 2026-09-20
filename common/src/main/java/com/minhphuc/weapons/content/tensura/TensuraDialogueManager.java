package com.minhphuc.weapons.content.tensura;

import com.minhphuc.weapons.entity.tensura.DemonType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

import java.util.Random;

public class TensuraDialogueManager {

    private static final Random RANDOM = new Random();

    // ==========================================
    // 1. GIỌNG NÓI THẾ GIỚI (VOICE OF THE WORLD)
    // ==========================================
    public static void announceWorld(ServerLevel level, double x, double y, double z, double radius, String translationKey, Object... args) {
        Component message = Component.literal("§e§l[THẾ GIỚI CHI THANH] §f").append(Component.translatable(translationKey, args));
        broadcastToArea(level, x, y, z, radius, message);
        level.playSound(null, x, y, z, SoundEvents.BELL_BLOCK, SoundSource.AMBIENT, 2.0F, 1.2F);
    }

    public static void announceWorldToPlayer(ServerPlayer player, String translationKey, Object... args) {
        Component message = Component.literal("§e§l[THẾ GIỚI CHI THANH] §f").append(Component.translatable(translationKey, args));
        player.displayClientMessage(message, false);
        player.playNotifySound(SoundEvents.BELL_BLOCK, SoundSource.AMBIENT, 1.5F, 1.2F);
    }

    // ==========================================
    // 2. CHƯỚC NHIỆT LONG VELGRYND DIALOGUES
    // ==========================================
    public static void sayVelgrynd(Entity velgrynd, String dialogueKey, Object... args) {
        if (velgrynd.level().isClientSide()) return;
        Component message = Component.literal("§c§l[VELGRYND] §e").append(Component.translatable(dialogueKey, args));
        broadcastToArea((ServerLevel) velgrynd.level(), velgrynd.getX(), velgrynd.getY(), velgrynd.getZ(), 48.0D, message);
    }

    // ==========================================
    // 3. PRIMORDIAL DEMONS DIALOGUES
    // ==========================================
    public static void sayDemon(Entity demon, DemonType type, String dialogueKey, Object... args) {
        if (demon.level().isClientSide()) return;
        String prefixColor = switch (type) {
            case ROUGE -> "§c§l[GUY CRIMSON] §f";
            case NOIR -> "§8§l[DIABLO] §f";
            case BLANC -> "§f§l[TESTAROSSA] §f";
            case JAUNE -> "§e§l[CARRERA] §f";
            case VIOLET -> "§d§l[ULTIMA] §f";
            case BLEU -> "§9§l[RAIN] §f";
            case VERT -> "§a§l[MISERY] §f";
        };
        Component message = Component.literal(prefixColor).append(Component.translatable(dialogueKey, args));
        broadcastToArea((ServerLevel) demon.level(), demon.getX(), demon.getY(), demon.getZ(), 36.0D, message);
    }

    private static void broadcastToArea(ServerLevel level, double x, double y, double z, double radius, Component message) {
        for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, new AABB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius))) {
            player.displayClientMessage(message, false);
        }
    }
}
