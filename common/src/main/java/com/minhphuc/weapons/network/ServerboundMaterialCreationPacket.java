package com.minhphuc.weapons.network;

import com.minhphuc.weapons.data.EntityDataHelper;
import com.minhphuc.weapons.init.ModItems;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

/**
 * Packet gửi từ Client lên Server để kích hoạt Kỹ Năng [Sáng Tạo Vật Chất] (Material Creation).
 * - Yêu cầu: Phải là Chân Ma Vương (TensuraTrueDemonLord) VÀ đã đánh bại Hắc Sắc Thủy Tổ Noir (TensuraMaterialCreation).
 * - Tạo Móng Vuốt Huyền Thoại & Súng Lục Hoàng Kim.
 * - Tạo Đạn: 3 loại thường mất 2 máu (1 tim), loại Judgement mất 5 máu (2.5 tim).
 */
public class ServerboundMaterialCreationPacket {

    public static final int ID_CLAW_DESPAIR = 0;
    public static final int ID_CLAW_CALAMITY = 1;
    public static final int ID_GOLDEN_GUN = 2;
    public static final int ID_BULLET_RAPID = 3;
    public static final int ID_BULLET_GRAVITY = 4;
    public static final int ID_BULLET_ABYSS = 5;
    public static final int ID_BULLET_JUDGEMENT = 6;

    private final int creationId;

    public ServerboundMaterialCreationPacket(int creationId) {
        this.creationId = creationId;
    }

    public ServerboundMaterialCreationPacket(FriendlyByteBuf buf) {
        this.creationId = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.creationId);
    }

    public void handle(Supplier<NetworkManager.PacketContext> contextSupplier) {
        NetworkManager.PacketContext context = contextSupplier.get();
        context.queue(() -> {
            ServerPlayer player = (ServerPlayer) context.getPlayer();
            if (player == null) return;

            boolean isTrueDemonLord = EntityDataHelper.getCustomData(player).getBoolean("TensuraTrueDemonLord");
            boolean hasMaterialCreation = EntityDataHelper.getCustomData(player).getBoolean("TensuraMaterialCreation");

            if (!isTrueDemonLord || !hasMaterialCreation) {
                player.displayClientMessage(
                        Component.literal("§c§l« CẢNH BÁO: BẠN CẦN TRỞ THÀNH CHÂN MA VƯƠNG VÀ ĐÁNH BẠI HẮC SẮC THỦY TỔ ĐỂ HỌC [SÁNG TẠO VẬT CHẤT]! »"),
                        false
                );
                player.playNotifySound(SoundEvents.DISPENSER_FAIL, SoundSource.PLAYERS, 1.2F, 0.8F);
                return;
            }

            ItemStack createdStack = ItemStack.EMPTY;
            float hpCost = 0.0F;
            String itemName = "";

            switch (this.creationId) {
                case ID_CLAW_DESPAIR -> {
                    createdStack = new ItemStack(ModItems.ABYSSAL_CLAW_DESPAIR.get());
                    itemName = "§5§lVuốt Hắc Ám Tuyệt Vọng (Abyssal Claw of Despair)";
                }
                case ID_CLAW_CALAMITY -> {
                    createdStack = new ItemStack(ModItems.VOID_CLAW_CALAMITY.get());
                    itemName = "§d§lVuốt Hư Vô Tai Ương (Void Claw of Calamity)";
                }
                case ID_GOLDEN_GUN -> {
                    createdStack = new ItemStack(ModItems.GOLDEN_GUN.get());
                    itemName = "§6§lSúng Lục Hoàng Kim (Carrera's Golden Gun)";
                }
                case ID_BULLET_RAPID -> {
                    createdStack = new ItemStack(ModItems.CARRERA_BULLET_RAPID.get(), 8);
                    hpCost = 2.0F; // Mất 2 máu (1 tim)
                    itemName = "§6§l8x Đạn Hoàng Kim Xạ Kích (Mất 2 Máu)";
                }
                case ID_BULLET_GRAVITY -> {
                    createdStack = new ItemStack(ModItems.CARRERA_BULLET_GRAVITY.get(), 8);
                    hpCost = 2.0F; // Mất 2 máu (1 tim)
                    itemName = "§9§l8x Đạn Trọng Lực Sụp Đổ (Mất 2 Máu)";
                }
                case ID_BULLET_ABYSS -> {
                    createdStack = new ItemStack(ModItems.CARRERA_BULLET_ABYSS.get(), 8);
                    hpCost = 2.0F; // Mất 2 máu (1 tim)
                    itemName = "§d§l8x Đạn Hạch Thâm Uyên (Mất 2 Máu)";
                }
                case ID_BULLET_JUDGEMENT -> {
                    createdStack = new ItemStack(ModItems.CARRERA_BULLET_JUDGEMENT.get(), 4);
                    hpCost = 5.0F; // Mất 5 máu (2.5 tim)
                    itemName = "§e§l4x Đạn Thần Tốc Phán Quyết (Mất 5 Máu)";
                }
            }

            if (createdStack.isEmpty()) return;

            // Kiểm tra máu nếu chế tạo đạn
            if (hpCost > 0.0F) {
                if (player.getHealth() <= hpCost + 1.0F) {
                    player.displayClientMessage(
                            Component.literal("§c§l[SÁNG TẠO VẬT CHẤT] §fLượng sinh mệnh không đủ để trích xuất ma huyết! Cần tối thiểu " + (int)(hpCost + 2) + " máu!"),
                            true
                    );
                    player.playNotifySound(SoundEvents.PLAYER_HURT, SoundSource.PLAYERS, 1.0F, 1.2F);
                    return;
                }
                // Trừ máu tạo đạn
                player.setHealth(player.getHealth() - hpCost);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.PLAYER_HURT_SWEET_BERRY_BUSH, SoundSource.PLAYERS, 1.5F, 0.9F);
                if (player.level() instanceof ServerLevel sl) {
                    sl.sendParticles(ParticleTypes.DAMAGE_INDICATOR, player.getX(), player.getY() + 1.0D, player.getZ(), (int)hpCost * 2, 0.3D, 0.5D, 0.3D, 0.1D);
                }
            }

            // Thêm vật phẩm vào túi đồ
            boolean added = player.getInventory().add(createdStack);
            if (!added) {
                player.drop(createdStack, false);
            }

            // Hiệu ứng và âm thanh
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 2.0F, 1.2F);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 1.8F, 1.4F);

            if (player.level() instanceof ServerLevel sl) {
                sl.sendParticles(ParticleTypes.FLASH, player.getX(), player.getY() + 1.0D, player.getZ(), 2, 0, 0, 0, 0);
                sl.sendParticles(ParticleTypes.DRAGON_BREATH, player.getX(), player.getY() + 0.8D, player.getZ(), 30, 0.5D, 0.5D, 0.5D, 0.05D);
                sl.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, player.getX(), player.getY() + 0.8D, player.getZ(), 20, 0.4D, 0.4D, 0.4D, 0.08D);
            }

            player.displayClientMessage(
                    Component.literal("§d§l✦ [SÁNG TẠO VẬT CHẤT] §fĐã ngưng tụ thành công: " + itemName + " §f!"),
                    false
            );
        });
    }
}
