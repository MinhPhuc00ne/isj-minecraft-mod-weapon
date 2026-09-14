package com.minhphuc.weapons.content.tensura;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class DemonLordSeedItem extends Item {

    public DemonLordSeedItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC).fireResistant());
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true; // Hiệu ứng hào quang ma pháp phát sáng
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            net.minecraft.nbt.CompoundTag data = com.minhphuc.weapons.data.EntityDataHelper.getCustomData(player);
            boolean isTrueDemonLord = data.getBoolean("TensuraTrueDemonLord");
            boolean hasSeed = data.getBoolean("TensuraHasSeed");

            if (isTrueDemonLord) {
                // Đã là Chân Ma Vương
                player.displayClientMessage(
                    Component.literal("§e§l[GIỌNG NÓI THẾ GIỚI] §fThông báo. Cá thể đã thức tỉnh thành Chân Ma Vương không thể dung nạp thêm hạt giống"),
                    false
                );
                level.playSound(null, player.getX(), player.getY(), player.getZ(), net.minecraft.sounds.SoundEvents.VILLAGER_NO, net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);
                return InteractionResultHolder.fail(stack);
            }

            if (hasSeed) {
                // Đã dung nạp hạt giống từ trước
                int souls = TensuraEvents.getAvailableSouls((net.minecraft.server.level.ServerPlayer) player);
                if (souls >= 64) {
                    TensuraEvents.triggerDemonLordEvolution((net.minecraft.server.level.ServerPlayer) player);
                    return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
                } else {
                    player.displayClientMessage(
                        Component.literal("§e§l[GIỌNG NÓI THẾ GIỚI] §fThông báo. cá thể đã sỡ hữu Hạt Giống Ma Vương\n§7Tiến độ Linh Hồn hiện tại: §6" + souls + "/64."),
                        false
                    );
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), net.minecraft.sounds.SoundEvents.VILLAGER_NO, net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);
                    return InteractionResultHolder.fail(stack);
                }
            }

            // Kích hoạt thành công: Tiêu hao hạt giống (biến mất khỏi tay)
            stack.shrink(1);
            data.putBoolean("TensuraHasSeed", true);

            // Âm thanh & hiệu ứng kích hoạt thức tỉnh ma vương
            level.playSound(null, player.getX(), player.getY(), player.getZ(), net.minecraft.sounds.SoundEvents.PLAYER_LEVELUP, net.minecraft.sounds.SoundSource.PLAYERS, 1.2F, 1.0F);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), net.minecraft.sounds.SoundEvents.BEACON_ACTIVATE, net.minecraft.sounds.SoundSource.PLAYERS, 1.5F, 1.5F);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), net.minecraft.sounds.SoundEvents.ENDER_DRAGON_GROWL, net.minecraft.sounds.SoundSource.PLAYERS, 0.8F, 1.4F);

            if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.DRAGON_BREATH, player.getX(), player.getY() + 1.0D, player.getZ(), 40, 0.4D, 0.6D, 0.4D, 0.05D);
                serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.TOTEM_OF_UNDYING, player.getX(), player.getY() + 1.0D, player.getZ(), 30, 0.5D, 0.5D, 0.5D, 0.1D);
                serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.FLASH, player.getX(), player.getY() + 1.5D, player.getZ(), 1, 0, 0, 0, 0);
            }

            player.displayClientMessage(
                Component.literal("§e§l[GIỌNG NÓI THẾ GIỚI] §aBáo cáo đã đủ điều ban đầu để thức tỉnh thành ma vương cần bổ sung 64 linh hồn để hoàn tất điều kiện thức tỉnh"),
                false
            );
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§d§lVật Phẩm Huyền Thoại Anime Tensura"));
        tooltip.add(Component.literal("§7Bằng chứng về tư chất trở thành kẻ thống trị thế giới."));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§e⚡ Cách Sử Dụng & Thức Tỉnh Chân Ma Vương:"));
        tooltip.add(Component.literal("§71. Nhấn §aChuột Phải §7để dung hợp Hạt Giống vào linh hồn (§cHạt giống sẽ tiêu biến§7)"));
        tooltip.add(Component.literal("§72. Diệt quái tích lũy đủ §664 Linh Hồn Ma Vương"));
        tooltip.add(Component.literal("§73. Leo lên giường §aĐI NGỦ §7để khởi động Lễ Hội Thức Tỉnh!"));
    }
}
