package com.minhphuc.weapons.content.divine;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import java.util.Random;

public class SkillPowerRoll {
    public enum Tier {
        LOW,
        NORMAL,
        OVERDRIVE
    }

    private static final Random RNG = new Random();

    public final Tier tier;
    public final int percentage;
    public final float multiplier;

    public SkillPowerRoll(Tier tier, int percentage, float multiplier) {
        this.tier = tier;
        this.percentage = percentage;
        this.multiplier = multiplier;
    }

    public static SkillPowerRoll roll() {
        float r = RNG.nextFloat();
        if (r < 0.30F) {
            // LOW TIER (30% tỉ lệ): Xuất lực yếu 25% - 45%
            int percent = 25 + RNG.nextInt(21);
            return new SkillPowerRoll(Tier.LOW, percent, percent / 100.0F);
        } else if (r < 0.80F) {
            // NORMAL TIER (50% tỉ lệ): Xuất lực chuẩn 80% - 110%
            int percent = 80 + RNG.nextInt(31);
            return new SkillPowerRoll(Tier.NORMAL, percent, percent / 100.0F);
        } else {
            // OVERDRIVE TIER (20% tỉ lệ): Bạo kích cực hạn 200% - 300%
            int percent = 200 + RNG.nextInt(101);
            return new SkillPowerRoll(Tier.OVERDRIVE, percent, percent / 100.0F);
        }
    }

    public boolean isLow() {
        return tier == Tier.LOW;
    }

    public boolean isNormal() {
        return tier == Tier.NORMAL;
    }

    public boolean isOverdrive() {
        return tier == Tier.OVERDRIVE;
    }

    public void announceAndPlayEffects(ServerPlayer player, String skillName) {
        if (player == null) return;
        ServerLevel level = (ServerLevel) player.level();
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        switch (tier) {
            case LOW -> {
                player.displayClientMessage(
                        Component.literal("§7§l[ĐẦU RA THẤP - " + percentage + "%] §c§l" + skillName + " §7Ma lực dao động! Đòn đánh bị hụt lực..."),
                        true
                );
                level.playSound(null, x, y, z, SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.5F, 1.5F);
                level.playSound(null, x, y, z, SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 1.2F, 1.8F);
            }
            case NORMAL -> {
                player.displayClientMessage(
                        Component.literal("§b§l[XUẤT LỰC CHUẨN - " + percentage + "%] §a§l" + skillName + " §fUy lực ổn định bộc phát!"),
                        true
                );
                level.playSound(null, x, y, z, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 2.0F, 1.2F);
                level.playSound(null, x, y, z, SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0F, 1.4F);
            }
            case OVERDRIVE -> {
                player.displayClientMessage(
                        Component.literal("§4§l⚡⚡ [BẠO KÍCH TỐI THƯỢNG - " + percentage + "%] ⚡⚡ §e§l" + skillName + " §c§lTẤT SÁT VẠN VẬT!"),
                        true
                );
                level.playSound(null, x, y, z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 3.0F, 1.1F);
                level.playSound(null, x, y, z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 2.5F, 1.2F);
                level.playSound(null, x, y, z, SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.PLAYERS, 1.5F, 1.0F);
            }
        }
    }
}
