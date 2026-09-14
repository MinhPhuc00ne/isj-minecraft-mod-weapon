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

    public static boolean hasSeedItemInInventory(ServerPlayer player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(ModItems.DEMON_LORD_SEED.get())) {
                return true;
            }
        }
        return false;
    }

    public static void consumeSeedItemIfPresent(ServerPlayer player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(ModItems.DEMON_LORD_SEED.get())) {
                stack.shrink(1);
                return;
            }
        }
    }

    public static int countSoulsInInventory(ServerPlayer player) {
        int count = 0;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(ModItems.DEMON_LORD_SOUL.get())) {
                count += stack.getCount();
            }
        }
        return count;
    }

    public static int getAvailableSouls(ServerPlayer player) {
        CompoundTag playerData = EntityDataHelper.getCustomData(player);
        int nbtSouls = playerData.getInt("TensuraCollectedSouls");
        int invSouls = countSoulsInInventory(player);
        return nbtSouls + invSouls;
    }

    public static void consumeAllSouls(ServerPlayer player) {
        // Xóa sạch toàn bộ vật phẩm Linh Hồn trên tay chính, tay phụ và trong túi đồ
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(ModItems.DEMON_LORD_SOUL.get())) {
                player.getInventory().setItem(i, ItemStack.EMPTY);
            }
        }
        // Xóa bộ đếm trong dữ liệu NBT
        EntityDataHelper.getCustomData(player).putInt("TensuraCollectedSouls", 0);
    }

    public static void handleMobDeathDrop(ServerPlayer player, LivingEntity victim) {
        if (player == null || victim == null || victim instanceof Player) return;

        ServerLevel level = (ServerLevel) player.level();
        CompoundTag playerData = EntityDataHelper.getCustomData(player);
        boolean hasSeed = playerData.getBoolean("TensuraHasSeed") || hasSeedItemInInventory(player);
        boolean isTrueDemonLord = playerData.getBoolean("TensuraTrueDemonLord");

        // 1. Tỷ lệ 5% rớt Hạt Giống Ma Vương khi diệt sinh vật nếu người chơi chưa sở hữu
        if (!hasSeed && !isTrueDemonLord && level.random.nextFloat() <= 0.05F) {
            ItemEntity seedDrop = new ItemEntity(
                level, victim.getX(), victim.getY() + 0.5D, victim.getZ(),
                new ItemStack(ModItems.DEMON_LORD_SEED.get())
            );
            level.addFreshEntity(seedDrop);

            VoiceOfTheWorld.announce(player, "Báo cáo. Cá thể vừa thu nhận được hạt giống ma vương");
            return;
        }

        // 2. Thu thập Linh Hồn Ma Vương nếu đã kích hoạt Hạt Giống (chưa thành Chân Ma Vương)
        // Áp dụng cho MỌI LOẠI VŨ KHÍ (Kiếm, Cung, Nỏ, Rìu, Găng tay, Búng tay SNAP...)
        if (hasSeed && !isTrueDemonLord) {
            // Rớt ra vật phẩm Linh Hồn Ma Vương dưới chân quái tử trận
            ItemEntity soulDrop = new ItemEntity(
                level, victim.getX(), victim.getY() + 0.5D, victim.getZ(),
                new ItemStack(ModItems.DEMON_LORD_SOUL.get())
            );
            level.addFreshEntity(soulDrop);

            int totalSouls = getAvailableSouls(player);
            if (totalSouls < 64) {
                int nbtSouls = playerData.getInt("TensuraCollectedSouls") + 1;
                playerData.putInt("TensuraCollectedSouls", nbtSouls);
                int totalAfter = getAvailableSouls(player);

                if (totalAfter % 10 == 0) {
                    VoiceOfTheWorld.announce(player, "Báo cáo. Tiến độ thu thập Linh Hồn Ma Vương: " + totalAfter + "/64.");
                }
            }
        }
    }

    public static EventResult onLivingDeath(LivingEntity victim, DamageSource source) {
        if (victim.level().isClientSide()) return EventResult.pass();
        if (victim instanceof Player) return EventResult.pass(); // Không tính khi chết người chơi

        ServerPlayer player = null;
        if (source.getEntity() instanceof ServerPlayer sp) {
            player = sp;
        } else if (source.getDirectEntity() instanceof ServerPlayer sp) {
            player = sp;
        } else if (victim.getLastHurtByMob() instanceof ServerPlayer sp) {
            // Bao gồm quái bị người chơi đánh trúng rồi chết bởi cháy, rơi, hiệu ứng đòn quét...
            player = sp;
        }

        if (player != null) {
            handleMobDeathDrop(player, victim);
        }
        return EventResult.pass();
    }

    public static void onPlayerWakeUp(Player rawPlayer) {
        if (rawPlayer.level().isClientSide()) return;
        if (!(rawPlayer instanceof ServerPlayer player)) return;

        CompoundTag playerData = EntityDataHelper.getCustomData(player);
        boolean isTrueDemonLord = playerData.getBoolean("TensuraTrueDemonLord");
        if (isTrueDemonLord) return;

        boolean hasSeed = playerData.getBoolean("TensuraHasSeed") || hasSeedItemInInventory(player);
        int totalSouls = getAvailableSouls(player);

        // Đủ Hạt Giống + ít nhất 64 Linh Hồn -> Kích hoạt Lễ Hội Thức Tỉnh Ma Vương khi thức dậy!
        if (hasSeed && totalSouls >= 64) {
            triggerDemonLordEvolution(player);
        } else if (totalSouls >= 64 && !hasSeed) {
            VoiceOfTheWorld.announce(player, "§cBáo cáo. Cá thể đã thu thập đủ §664 Linh Hồn §cnhưng §eCHƯA CÓ HẠT GIỐNG MA VƯƠNG§c! Cần sở hữu Hạt Giống Ma Vương để làm mầm mống thức tỉnh!");
        } else if (hasSeed && totalSouls > 0 && totalSouls < 64) {
            VoiceOfTheWorld.announce(player, "§7Báo cáo. Tiến độ thức tỉnh của cá thể chưa hoàn tất: §6" + totalSouls + "/64 Linh Hồn§7. Hãy tiêu diệt thêm cá thể để tích đủ!");
        }
    }

    public static boolean triggerDemonLordEvolution(ServerPlayer player) {
        CompoundTag playerData = EntityDataHelper.getCustomData(player);
        boolean isTrueDemonLord = playerData.getBoolean("TensuraTrueDemonLord");
        if (isTrueDemonLord || HarvestFestival.isPlayerInRitual(player)) return false;

        boolean hasSeed = playerData.getBoolean("TensuraHasSeed") || hasSeedItemInInventory(player);
        int totalSouls = getAvailableSouls(player);

        if (!hasSeed) {
            VoiceOfTheWorld.announce(player, "§cBáo cáo. Cá thể chưa sở hữu Hạt Giống Ma Vương, không thể tiến hóa!");
            return false;
        }

        if (totalSouls < 64) {
            VoiceOfTheWorld.announce(player, "§cBáo cáo. Chưa đủ Linh Hồn! Hiện có: §6" + totalSouls + "/64§c.");
            return false;
        }

        // Bắt đầu Lễ Hội Thu Hoạch 4 giai đoạn kịch tính & hoành tráng!
        HarvestFestival.start(player);
        return true;
    }
}
