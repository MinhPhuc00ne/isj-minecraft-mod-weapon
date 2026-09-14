package com.minhphuc.weapons.content.tensura;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class DemonLordSoulItem extends Item {

    public DemonLordSoulItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide() && player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
            net.minecraft.nbt.CompoundTag data = com.minhphuc.weapons.data.EntityDataHelper.getCustomData(player);
            boolean isTrueDemonLord = data.getBoolean("TensuraTrueDemonLord");
            boolean hasSeed = data.getBoolean("TensuraHasSeed");

            if (isTrueDemonLord) {
                player.displayClientMessage(
                    Component.literal("§e§l[GIỌNG NÓI THẾ GIỚI] §fBáo cáo. Cá thể đã thức tỉnh thành Chân Ma Vương không thể dung nạp thêm linh hồn"),
                    true
                );
                return InteractionResultHolder.pass(stack);
            }

            if (!hasSeed) {
                player.displayClientMessage(
                    Component.literal("§e§l[GIỌNG NÓI THẾ GIỚI] §cBáo cáo. Cá thể chưa sở hữu Hạt Giống Ma Vương!"),
                    false
                );
                level.playSound(null, player.getX(), player.getY(), player.getZ(), net.minecraft.sounds.SoundEvents.VILLAGER_NO, net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);
                return InteractionResultHolder.fail(stack);
            }

            int currentSouls = data.getInt("TensuraCollectedSouls");
            if (currentSouls >= 64 && hasSeed) {
                // Đã đủ 64 linh hồn và bấm chuột phải -> Khởi động nghi thức thức tỉnh ngay lập tức!
                TensuraEvents.triggerDemonLordEvolution(serverPlayer);
                return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
            }

            int toAbsorb = player.isShiftKeyDown() ? stack.getCount() : 1;
            int needed = Math.max(0, 64 - currentSouls);
            int actualAbsorbed = Math.min(toAbsorb, needed > 0 ? needed : toAbsorb);

            stack.shrink(actualAbsorbed);
            int newTotal = currentSouls + actualAbsorbed;
            data.putInt("TensuraCollectedSouls", newTotal);

            level.playSound(null, player.getX(), player.getY(), player.getZ(), net.minecraft.sounds.SoundEvents.SOUL_ESCAPE, net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.2F);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), net.minecraft.sounds.SoundEvents.EXPERIENCE_ORB_PICKUP, net.minecraft.sounds.SoundSource.PLAYERS, 0.8F, 1.0F);

            if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.SOUL, player.getX(), player.getY() + 1.0D, player.getZ(), 10, 0.2D, 0.4D, 0.2D, 0.05D);
            }

            player.displayClientMessage(
                Component.literal("§e§l[GIỌNG NÓI THẾ GIỚI] §aBáo cáo. Đã dung nạp " + actualAbsorbed + " Linh Hồn. Tiến độ hiện tại: §6" + newTotal + "/64§a."),
                true
            );
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§b§lLinh Hồn Tích Lũy Ma Vương"));
        tooltip.add(Component.literal("§7Nguồn năng lượng huyền bí cần thiết để nuôi dưỡng Hạt Giống Ma Vương."));
        tooltip.add(Component.literal("§7- §eChuột Phải: §fDung nạp 1 linh hồn vào bản thể"));
        tooltip.add(Component.literal("§7- §eShift + Chuột Phải: §fDung nạp toàn bộ linh hồn trên tay"));
        tooltip.add(Component.literal("§e⚡ Cần đủ §664 Linh Hồn §e& Lên Giường Đi Ngủ để Thức Tỉnh Chân Ma Vương."));
    }
}
