package com.minhphuc.weapons.content.tensura;

import com.minhphuc.weapons.data.EntityDataHelper;
import com.minhphuc.weapons.init.ModItems;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TensuraEvents {

    public static void register() {
        EntityEvent.LIVING_DEATH.register(TensuraEvents::onLivingDeath);
    }

    public static EventResult onLivingDeath(LivingEntity victim, DamageSource source) {
        if (victim.level().isClientSide()) return EventResult.pass();
        if (!(source.getEntity() instanceof ServerPlayer player)) return EventResult.pass();
        if (victim instanceof Player) return EventResult.pass(); // Không tính khi chết người chơi

        ServerLevel level = (ServerLevel) player.level();
        CompoundTag playerData = EntityDataHelper.getCustomData(player);
        boolean hasSeed = player.getInventory().contains(new ItemStack(ModItems.DEMON_LORD_SEED.get()))
                || playerData.getBoolean("TensuraHasSeed");

        // 1. Tỷ lệ 5% rớt Hạt Giống Ma Vương khi diệt sinh vật
        if (!hasSeed && level.random.nextFloat() <= 0.05F) {
            ItemEntity seedDrop = new ItemEntity(
                level, victim.getX(), victim.getY() + 0.5D, victim.getZ(),
                new ItemStack(ModItems.DEMON_LORD_SEED.get())
            );
            level.addFreshEntity(seedDrop);

            playerData.putBoolean("TensuraHasSeed", true);
            VoiceOfTheWorld.announce(player, "Báo cáo. Cá thể vừa tiêu diệt sinh vật và khai mở §d§l[HẠT GIỐNG MA VƯƠNG (Demon Lord Seed)]§f!");
            return EventResult.pass();
        }

        // 2. Thu thập Linh Hồn Ma Vương nếu đã có Hạt Giống
        if (hasSeed && !playerData.getBoolean("TensuraTrueDemonLord")) {
            int currentSouls = playerData.getInt("TensuraCollectedSouls");

            // Rớt ra vật phẩm Linh Hồn Ma Vương
            ItemEntity soulDrop = new ItemEntity(
                level, victim.getX(), victim.getY() + 0.5D, victim.getZ(),
                new ItemStack(ModItems.DEMON_LORD_SOUL.get())
            );
            level.addFreshEntity(soulDrop);

            if (currentSouls < 64) {
                currentSouls++;
                playerData.putInt("TensuraCollectedSouls", currentSouls);

                if (currentSouls == 64) {
                    VoiceOfTheWorld.announce(player, "§aBáo cáo. Đã thu thập đủ §664/64 Linh Hồn Ma Vương§a! Điều kiện tiến hóa hoàn tất! Vui lòng leo lên giường §d§lĐI NGỦ §anắm lấy giấc ngủ ngàn năm để thức tỉnh!");
                } else if (currentSouls % 10 == 0) {
                    VoiceOfTheWorld.announce(player, "Tiến độ thu thập Linh Hồn Ma Vương: §6" + currentSouls + "/64§f.");
                }
            }
        }
        return EventResult.pass();
    }

    public static void onPlayerWakeUp(Player rawPlayer) {
        if (rawPlayer.level().isClientSide()) return;
        if (!(rawPlayer instanceof ServerPlayer player)) return;

        CompoundTag playerData = EntityDataHelper.getCustomData(player);
        boolean isTrueDemonLord = playerData.getBoolean("TensuraTrueDemonLord");
        if (isTrueDemonLord) return;

        boolean hasSeed = player.getInventory().contains(new ItemStack(ModItems.DEMON_LORD_SEED.get()))
                || playerData.getBoolean("TensuraHasSeed");
        int souls = playerData.getInt("TensuraCollectedSouls");

        // Đủ Hạt Giống + 64 Linh Hồn -> Kích hoạt Lễ Hội Thức Tỉnh Ma Vương khi thức dậy!
        if (hasSeed && souls >= 64) {
            playerData.putBoolean("TensuraTrueDemonLord", true);

            // Tiêu thụ 64 Linh Hồn
            playerData.putInt("TensuraCollectedSouls", 0);

            // Phát thông báo tiến hóa Chân Ma Vương rực rỡ
            VoiceOfTheWorld.announceEvolutionSuccess(player);
        }
    }
}
