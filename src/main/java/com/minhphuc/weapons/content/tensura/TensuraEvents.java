package com.minhphuc.weapons.content.tensura;

import com.minhphuc.weapons.WeaponsMod;
import com.minhphuc.weapons.init.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WeaponsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TensuraEvents {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) return;

        LivingEntity victim = event.getEntity();
        if (victim instanceof Player) return; // Không tính khi chết người chơi

        ServerLevel level = (ServerLevel) player.level();
        boolean hasSeed = player.getInventory().contains(new ItemStack(ModItems.DEMON_LORD_SEED.get()))
                || player.getPersistentData().getBoolean("TensuraHasSeed");

        // 1. Tỷ lệ 5% rớt Hạt Giống Ma Vương khi diệt sinh vật
        if (!hasSeed && level.random.nextFloat() <= 0.05F) {
            ItemEntity seedDrop = new ItemEntity(
                level, victim.getX(), victim.getY() + 0.5D, victim.getZ(),
                new ItemStack(ModItems.DEMON_LORD_SEED.get())
            );
            level.addFreshEntity(seedDrop);

            player.getPersistentData().putBoolean("TensuraHasSeed", true);
            VoiceOfTheWorld.announce(player, "Báo cáo. Cá thể vừa tiêu diệt sinh vật và khai mở §d§l[HẠT GIỐNG MA VƯƠNG (Demon Lord Seed)]§f!");
            return;
        }

        // 2. Thu thập Linh Hồn Ma Vương nếu đã có Hạt Giống
        if (hasSeed && !player.getPersistentData().getBoolean("TensuraTrueDemonLord")) {
            int currentSouls = player.getPersistentData().getInt("TensuraCollectedSouls");

            // Rớt ra vật phẩm Linh Hồn Ma Vương
            ItemEntity soulDrop = new ItemEntity(
                level, victim.getX(), victim.getY() + 0.5D, victim.getZ(),
                new ItemStack(ModItems.DEMON_LORD_SOUL.get())
            );
            level.addFreshEntity(soulDrop);

            if (currentSouls < 64) {
                currentSouls++;
                player.getPersistentData().putInt("TensuraCollectedSouls", currentSouls);

                if (currentSouls == 64) {
                    VoiceOfTheWorld.announce(player, "§aBáo cáo. Đã thu thập đủ §664/64 Linh Hồn Ma Vương§a! Điều kiện tiến hóa hoàn tất! Vui lòng leo lên giường §d§lĐI NGỦ §anắm lấy giấc ngủ ngàn năm để thức tỉnh!");
                } else if (currentSouls % 10 == 0) {
                    VoiceOfTheWorld.announce(player, "Tiến độ thu thập Linh Hồn Ma Vương: §6" + currentSouls + "/64§f.");
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        boolean isTrueDemonLord = player.getPersistentData().getBoolean("TensuraTrueDemonLord");
        if (isTrueDemonLord) return;

        boolean hasSeed = player.getInventory().contains(new ItemStack(ModItems.DEMON_LORD_SEED.get()))
                || player.getPersistentData().getBoolean("TensuraHasSeed");
        int souls = player.getPersistentData().getInt("TensuraCollectedSouls");

        // Đủ Hạt Giống + 64 Linh Hồn -> Kích hoạt Lễ Hội Thức Tỉnh Ma Vương khi thức dậy!
        if (hasSeed && souls >= 64) {
            player.getPersistentData().putBoolean("TensuraTrueDemonLord", true);

            // Tiêu thụ 64 Linh Hồn
            player.getPersistentData().putInt("TensuraCollectedSouls", 0);

            // Phát thông báo tiến hóa Chân Ma Vương rực rỡ
            VoiceOfTheWorld.announceEvolutionSuccess(player);
        }
    }
}
