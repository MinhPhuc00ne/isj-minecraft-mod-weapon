package com.minhphuc.weapons.network;

import com.minhphuc.weapons.content.tensura.PrimordialPactItem;
import com.minhphuc.weapons.data.ItemStackDataHelper;
import com.minhphuc.weapons.entity.tensura.PrimordialDemonEntity;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Supplier;

public class ServerboundDemonCommandPacket {
    public static final int ACTION_CAST_SKILL = 0;
    public static final int ACTION_STANCE_FOLLOW = 1;
    public static final int ACTION_STANCE_GUARD = 2;
    public static final int ACTION_DISMISS = 3;

    private final int actionType;
    private final int skillIndex;

    public ServerboundDemonCommandPacket(int actionType, int skillIndex) {
        this.actionType = actionType;
        this.skillIndex = skillIndex;
    }

    public ServerboundDemonCommandPacket(FriendlyByteBuf buf) {
        this.actionType = buf.readInt();
        this.skillIndex = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.actionType);
        buf.writeInt(this.skillIndex);
    }

    public void handle(Supplier<NetworkManager.PacketContext> contextSupplier) {
        NetworkManager.PacketContext context = contextSupplier.get();
        context.queue(() -> {
            ServerPlayer player = (ServerPlayer) context.getPlayer();
            if (player == null) return;
            ServerLevel level = player.serverLevel();

            // Tìm ác ma thuộc quyền sở hữu của người chơi
            List<PrimordialDemonEntity> demons = level.getEntitiesOfClass(
                    PrimordialDemonEntity.class,
                    new AABB(player.getX() - 128, player.getY() - 64, player.getZ() - 128, player.getX() + 128, player.getY() + 64, player.getZ() + 128),
                    d -> d.isAlive() && d.isOwnedBy(player)
            );

            if (demons.isEmpty()) {
                player.displayClientMessage(Component.literal("§c[Khế Ước] Không tìm thấy Ác Ma Thủy Tổ nào đang hiện diện gần đây!"), true);
                return;
            }

            PrimordialDemonEntity demon = demons.get(0);

            switch (actionType) {
                case ACTION_CAST_SKILL -> {
                    LivingEntity target = demon.getTarget();
                    demon.executeCommandedSkill(skillIndex, target);
                    player.displayClientMessage(Component.literal("§6§l[Mệnh Lệnh] §eĐã ra lệnh cho §c§l" + demon.getDemonType().getColorName().toUpperCase() + " §ethực hiện kỹ năng!"), true);
                }
                case ACTION_STANCE_FOLLOW -> {
                    demon.setOrderedToSit(false);
                    player.displayClientMessage(Component.literal("§a§l[Mệnh Lệnh] §e" + demon.getDemonType().getColorName() + " §aĐANG ĐI THEO BẢO VỆ CHỦ NHÂN!"), true);
                }
                case ACTION_STANCE_GUARD -> {
                    demon.setOrderedToSit(true);
                    demon.getNavigation().stop();
                    player.displayClientMessage(Component.literal("§c§l[Mệnh Lệnh] §e" + demon.getDemonType().getColorName() + " §cĐANG ĐỨNG CANH GIỮ VỊ TRÍ!"), true);
                }
                case ACTION_DISMISS -> {
                    level.sendParticles(ParticleTypes.DRAGON_BREATH, demon.getX(), demon.getY() + 1.0D, demon.getZ(), 40, 0.5D, 0.8D, 0.5D, 0.1D);
                    level.playSound(null, demon.getX(), demon.getY(), demon.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 2.0F, 0.8F);
                    player.displayClientMessage(Component.literal("§a✔ Đã cho §e" + demon.getDemonType().getColorName() + " §alui về nghỉ ngơi trong Ấn Chú!"), true);
                    demon.discard();

                    // Cập nhật pact item trong túi đồ
                    for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                        ItemStack st = player.getInventory().getItem(i);
                        if (st.getItem() instanceof PrimordialPactItem) {
                            ItemStackDataHelper.putBoolean(st, "IsSummoned", false);
                            ItemStackDataHelper.putString(st, "DemonUUID", "");
                        }
                    }
                }
            }
        });
    }
}
