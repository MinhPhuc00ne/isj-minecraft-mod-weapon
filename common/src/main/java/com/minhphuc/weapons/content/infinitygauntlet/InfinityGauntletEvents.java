package com.minhphuc.weapons.content.infinitygauntlet;

import com.minhphuc.weapons.WeaponsMod;
import com.minhphuc.weapons.ai.GeminiAIService;
import com.minhphuc.weapons.config.AIGeminiConfig;
import com.minhphuc.weapons.data.EntityDataHelper;
import com.minhphuc.weapons.data.ItemStackDataHelper;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.ChatEvent;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.InteractionEvent;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class InfinityGauntletEvents {

    public static void register() {
        EntityEvent.LIVING_HURT.register(InfinityGauntletEvents::onLivingHurt);
        InteractionEvent.LEFT_CLICK_BLOCK.register(InfinityGauntletEvents::onLeftClickBlock);
        ChatEvent.RECEIVED.register(InfinityGauntletEvents::onServerChat);
    }

    public static EventResult onLivingHurt(LivingEntity living, DamageSource damageSource, float amount) {
        if (living instanceof Player player) {
            if (isHoldingGauntlet(player)) {
                player.setHealth(player.getMaxHealth());
                return EventResult.interruptFalse();
            }
        } else {
            // Tượng Băng bị tấn công -> Phá vỡ tượng băng & Tiêu diệt sinh vật lập tức
            if (living != null && EntityDataHelper.getCustomData(living).getBoolean("InfinityGauntletFrozen")) {
                EntityDataHelper.getCustomData(living).remove("InfinityGauntletFrozen");

                if (living.level() instanceof ServerLevel serverLevel) {
                    serverLevel.playSound(null, living.getX(), living.getY(), living.getZ(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 2.0F, 0.8F);
                    serverLevel.playSound(null, living.getX(), living.getY(), living.getZ(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS, 2.0F, 1.2F);

                    serverLevel.sendParticles(
                        new net.minecraft.core.particles.BlockParticleOption(ParticleTypes.BLOCK, net.minecraft.world.level.block.Blocks.PACKED_ICE.defaultBlockState()),
                        living.getX(), living.getY() + 1.0D, living.getZ(),
                        40, 0.4D, 0.6D, 0.4D, 0.1D
                    );
                    serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, living.getX(), living.getY() + 1.0D, living.getZ(), 20, 0.3D, 0.5D, 0.3D, 0.05D);

                    living.hurt(serverLevel.damageSources().genericKill(), 100000.0F);
                    if (living.isAlive()) {
                        living.discard();
                    }
                }
                return EventResult.interruptFalse();
            }
        }
        return EventResult.pass();
    }

    public static EventResult onLeftClickBlock(Player player, InteractionHand hand, net.minecraft.core.BlockPos pos, net.minecraft.core.Direction direction) {
        if (player == null || player.level().isClientSide()) return EventResult.pass();

        ItemStack heldStack = player.getItemInHand(hand);
        if (heldStack.getItem() instanceof InfinityGauntletItem) {
            player.displayClientMessage(
                Component.literal("§7[Găng Tay Vô Cực] §fBáo cáo. Nhấn §e[PgUp] §7để chọn viên đá. Chuột Phải để dùng Chiêu Chính, Shift+Chuột Phải để dùng Chiêu Phụ!"),
                true
            );
            return EventResult.interruptFalse();
        }
        return EventResult.pass();
    }

    public static EventResult onServerChat(ServerPlayer player, Component component) {
        if (player == null) return EventResult.pass();

        String rawText = component.getString().trim();
        if (rawText.isEmpty()) return EventResult.pass();

        ItemStack heldStack = player.getMainHandItem();
        if (!(heldStack.getItem() instanceof InfinityGauntletItem)) {
            heldStack = player.getOffhandItem();
        }

        if (!(heldStack.getItem() instanceof InfinityGauntletItem)) return EventResult.pass();

        int mode = ItemStackDataHelper.getInt(heldStack, InfinityGauntletItem.NBT_MODE);
        if (mode != 7) return EventResult.pass(); // Chỉ hoạt động ở Chế độ 6 viên đá (Mode 7)

        String prompt = rawText;
        if (prompt.isEmpty()) {
            player.sendSystemMessage(Component.literal("§c[Găng Tay Vô Cực - AI] §fBáo cáo. Vui lòng nhập mệnh lệnh cho Gemini AI!"));
            return EventResult.interruptFalse();
        }

        if (!AIGeminiConfig.isApiKeyValid()) {
            player.sendSystemMessage(Component.literal("§c[Găng Tay Vô Cực - AI] §fBáo cáo. Chưa có API Key! Hãy dán API Key vào file:\n§e" + AIGeminiConfig.getConfigAbsolutePath()));
            return EventResult.interruptFalse();
        }

        // Thông báo cho người chơi AI đang xử lý
        player.sendSystemMessage(Component.literal("§d§l[GEMINI AI] §fBáo cáo. Đang lắng nghe và biến mệnh lệnh của cá thể §e\"" + prompt + "\" §fthành thực tại..."));

        ServerLevel level = (ServerLevel) player.level();
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.BEACON_AMBIENT, SoundSource.PLAYERS, 1.0F, 1.2F);

        // Gọi API bất đồng bộ
        GeminiAIService.processCommandAsync(level, player, prompt).thenAcceptAsync(response -> {
            level.getServer().execute(() -> {
                if (!response.isSuccess()) {
                    player.sendSystemMessage(Component.literal("§c[GEMINI AI ERROR] " + response.getError()));
                    return;
                }

                // Phát âm thanh & hạt sấm sét chói lọi khi AI thực thi
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 0.8F, 1.0F);
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 0.8F, 1.0F);

                for (int i = 0; i < 360; i += 20) {
                    double rad = Math.toRadians(i);
                    double px = player.getX() + Math.cos(rad) * 2.0D;
                    double pz = player.getZ() + Math.sin(rad) * 2.0D;
                    level.sendParticles(ParticleTypes.END_ROD, px, player.getY() + 1.0D, pz, 2, 0.1D, 0.3D, 0.1D, 0.05D);
                    level.sendParticles(ParticleTypes.DRAGON_BREATH, px, player.getY() + 0.5D, pz, 2, 0.1D, 0.1D, 0.1D, 0.02D);
                }

                // Thực thi các lệnh Minecraft do AI đề xuất với quyền tối cao (OP level 4)
                int executedCount = 0;
                for (String command : response.getCommands()) {
                    try {
                        level.getServer().getCommands().performPrefixedCommand(
                                player.createCommandSourceStack().withPermission(4).withSuppressedOutput(),
                                command
                        );
                        executedCount++;
                    } catch (Exception e) {
                        WeaponsMod.LOGGER.error("Failed to execute AI command: {}", command, e);
                    }
                }

                // Hiển thị lời đáp của Gemini AI
                player.sendSystemMessage(Component.literal("§d§l[GEMINI AI] §aBáo cáo. " + response.getReply() + " §7(Đã thực thi " + executedCount + " lệnh)"));
            });
        });

        return EventResult.interruptFalse();
    }

    private static boolean isHoldingGauntlet(Player player) {
        return player.getMainHandItem().getItem() instanceof InfinityGauntletItem
                || player.getOffhandItem().getItem() instanceof InfinityGauntletItem;
    }
}
