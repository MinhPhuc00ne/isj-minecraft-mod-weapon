package com.minhphuc.weapons.content.tensura;

import com.minhphuc.weapons.data.ItemStackDataHelper;
import com.minhphuc.weapons.entity.ModEntities;
import com.minhphuc.weapons.entity.tensura.DemonType;
import com.minhphuc.weapons.entity.tensura.PrimordialDemonEntity;
import com.minhphuc.weapons.init.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.UUID;

public class PrimordialPactItem extends Item {

    public PrimordialPactItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC).fireResistant());
    }

    public static ItemStack createPact(DemonType type, Player owner) {
        Item pactItem = type != null ? type.getPactItem().get() : ModItems.PRIMORDIAL_PACT.get();
        ItemStack stack = new ItemStack(pactItem);
        ItemStackDataHelper.putString(stack, "DemonType", type != null ? type.name() : "NOIR");
        ItemStackDataHelper.putBoolean(stack, "IsSummoned", false);
        if (owner != null) {
            ItemStackDataHelper.putString(stack, "OwnerUUID", owner.getStringUUID());
            ItemStackDataHelper.putString(stack, "OwnerName", owner.getName().getString());
        }
        return stack;
    }

    public static DemonType getDemonType(ItemStack stack) {
        Item item = stack.getItem();
        if (item == ModItems.PRIMORDIAL_PACT_NOIR.get()) return DemonType.NOIR;
        if (item == ModItems.PRIMORDIAL_PACT_ROUGE.get()) return DemonType.ROUGE;
        if (item == ModItems.PRIMORDIAL_PACT_BLANC.get()) return DemonType.BLANC;
        if (item == ModItems.PRIMORDIAL_PACT_JAUNE.get()) return DemonType.JAUNE;
        if (item == ModItems.PRIMORDIAL_PACT_VIOLET.get()) return DemonType.VIOLET;
        if (item == ModItems.PRIMORDIAL_PACT_BLEU.get()) return DemonType.BLEU;
        if (item == ModItems.PRIMORDIAL_PACT_VERT.get()) return DemonType.VERT;

        String name = ItemStackDataHelper.getString(stack, "DemonType");
        if (name == null || name.isEmpty()) {
            return DemonType.NOIR;
        }
        try {
            return DemonType.valueOf(name);
        } catch (IllegalArgumentException e) {
            return DemonType.NOIR;
        }
    }

    public static boolean isSummoned(ItemStack stack) {
        return ItemStackDataHelper.getBoolean(stack, "IsSummoned");
    }

    @Override
    public Component getName(ItemStack stack) {
        DemonType type = getDemonType(stack);
        return Component.literal("§4§lKhế Ước Thủy Tổ: §c§l" + type.getColorName() + " §7(" + type.getTitleVi() + ")");
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide()) {
            return InteractionResultHolder.success(stack);
        }

        ServerLevel serverLevel = (ServerLevel) level;
        DemonType type = getDemonType(stack);
        boolean summoned = isSummoned(stack);

        // Tìm ác ma đã được triệu hồi trước đó nếu có
        String demonUuidStr = ItemStackDataHelper.getString(stack, "DemonUUID");
        PrimordialDemonEntity targetDemon = null;

        if (!demonUuidStr.isEmpty()) {
            try {
                UUID id = UUID.fromString(demonUuidStr);
                Entity e = serverLevel.getEntity(id);
                if (e instanceof PrimordialDemonEntity pde && pde.isAlive()) {
                    targetDemon = pde;
                }
            } catch (Exception ignored) {}
        }

        if (targetDemon == null) {
            List<PrimordialDemonEntity> nearby = serverLevel.getEntitiesOfClass(
                    PrimordialDemonEntity.class,
                    new AABB(player.getX() - 64, player.getY() - 32, player.getZ() - 64, player.getX() + 64, player.getY() + 32, player.getZ() + 64),
                    d -> d.isOwnedBy(player) && d.getDemonType() == type
            );
            if (!nearby.isEmpty()) {
                targetDemon = nearby.get(0);
            }
        }

        if (player.isShiftKeyDown()) {
            // ==========================================
            // SHIFT + CHUỘT PHẢI: DỊCH CHUYỂN & ĐỔI CHẾ ĐỘ
            // ==========================================
            if (targetDemon != null) {
                targetDemon.teleportTo(player.getX() + player.getLookAngle().x * 2.0D, player.getY(), player.getZ() + player.getLookAngle().z * 2.0D);
                boolean newSitting = !targetDemon.isOrderedToSit();
                targetDemon.setOrderedToSit(newSitting);
                targetDemon.getNavigation().stop();

                serverLevel.sendParticles(ParticleTypes.PORTAL, targetDemon.getX(), targetDemon.getY() + 1.0D, targetDemon.getZ(), 30, 0.4D, 0.6D, 0.4D, 0.1D);
                serverLevel.playSound(null, targetDemon.getX(), targetDemon.getY(), targetDemon.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.5F, 1.2F);

                if (newSitting) {
                    player.displayClientMessage(Component.literal("§e§l[" + type.getColorName() + "] §6ĐÃ ĐƯỢC ĐẶT Ở CHẾ ĐỘ: §cĐỨNG CANH GIỮ VỊ TRÍ NÀY!"), true);
                } else {
                    player.displayClientMessage(Component.literal("§e§l[" + type.getColorName() + "] §6ĐÃ ĐƯỢC ĐẶT Ở CHẾ ĐỘ: §aĐI THEO BẢO VỆ CHỦ NHÂN!"), true);
                }
                return InteractionResultHolder.consume(stack);
            } else {
                player.displayClientMessage(Component.literal("§7Ác ma §e" + type.getColorName() + " §7hiện đang nghỉ ngơi trong ấn chú. Bấm Chuột Phải thường để triệu hồi xuất thế!"), true);
                return InteractionResultHolder.fail(stack);
            }
        } else {
            // ==========================================
            // CHUỘT PHẢI THƯỜNG:
            // - NẾU ĐÃ CÓ MẶT: SÀI THÊM LẦN NỮA ĐỂ CHO LUI ĐI VỀ ẤN CHÚ!
            // - NẾU CHƯA CÓ: TRIỆU HỒI RA THẾ GIỚI!
            // ==========================================
            if (targetDemon != null) {
                // CHO LUI ĐI (DISMISS)
                serverLevel.sendParticles(ParticleTypes.DRAGON_BREATH, targetDemon.getX(), targetDemon.getY() + 1.0D, targetDemon.getZ(), 40, 0.5D, 0.8D, 0.5D, 0.1D);
                serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, targetDemon.getX(), targetDemon.getY() + 1.0D, targetDemon.getZ(), 30, 0.5D, 0.6D, 0.5D, 0.05D);
                serverLevel.playSound(null, targetDemon.getX(), targetDemon.getY(), targetDemon.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 2.0F, 0.8F);

                String farewell = getFarewellMessage(type);
                player.displayClientMessage(Component.literal("§d§l[" + type.getColorName().toUpperCase() + "] §f\"" + farewell + "\""), false);
                player.displayClientMessage(Component.literal("§a✔ Đã cho §e" + type.getColorName() + " §alui về nghỉ ngơi trong Ấn Chú Khế Ước!"), true);

                targetDemon.discard();
                ItemStackDataHelper.putBoolean(stack, "IsSummoned", false);
                ItemStackDataHelper.putString(stack, "DemonUUID", "");
                return InteractionResultHolder.consume(stack);
            } else {
                // TRIỆU HỒI XUẤT THẾ (SUMMON)
                PrimordialDemonEntity newDemon = ModEntities.PRIMORDIAL_DEMON.get().create(serverLevel);
                if (newDemon != null) {
                    double spawnX = player.getX() + player.getLookAngle().x * 2.5D;
                    double spawnY = player.getY();
                    double spawnZ = player.getZ() + player.getLookAngle().z * 2.5D;

                    newDemon.moveTo(spawnX, spawnY, spawnZ, player.getYRot() + 180.0F, 0.0F);
                    newDemon.setDemonType(type);
                    newDemon.setWinged(type == DemonType.NOIR);
                    newDemon.tame(player);
                    newDemon.setHealth(newDemon.getMaxHealth());
                    newDemon.setOrderedToSit(false);

                    serverLevel.addFreshEntity(newDemon);

                    // Hiệu ứng triệu hồi
                    serverLevel.sendParticles(ParticleTypes.FLASH, spawnX, spawnY + 1.5D, spawnZ, 2, 0, 0, 0, 0);
                    serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, spawnX, spawnY + 1.2D, spawnZ, 50, 0.6D, 0.8D, 0.6D, 0.15D);
                    serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, spawnX, spawnY + 1.0D, spawnZ, 35, 0.5D, 0.5D, 0.5D, 0.08D);

                    serverLevel.playSound(null, spawnX, spawnY, spawnZ, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 2.5F, 1.4F);
                    serverLevel.playSound(null, spawnX, spawnY, spawnZ, SoundEvents.WARDEN_HEARTBEAT, SoundSource.PLAYERS, 2.0F, 1.0F);
                    serverLevel.playSound(null, spawnX, spawnY, spawnZ, SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.PLAYERS, 2.0F, 1.0F);

                    if (player instanceof ServerPlayer sp && sp.connection != null) {
                        sp.connection.send(new ClientboundSetTitleTextPacket(Component.literal("§6§l★ THỦY TỔ ÁC MA XUẤT THẾ ★")));
                        sp.connection.send(new ClientboundSetSubtitleTextPacket(Component.literal("§e" + type.getColorName() + " §7(" + type.getTitleVi() + ") §ađáp lời hiệu triệu!")));
                        VoiceOfTheWorld.announce(sp, "Báo cáo. Thủy Tổ Ác Ma: " + type.getColorName() + " (" + type.getTitleVi() + ") đã xuất thế và phục tùng chủ nhân tuyệt đối!");
                    }

                    String summonGreet = getSummonGreeting(type);
                    player.displayClientMessage(Component.literal("§d§l[" + type.getColorName().toUpperCase() + "] §f\"" + summonGreet + "\""), false);
                    player.displayClientMessage(Component.literal("§6★ TRIỆU HỒI THÀNH CÔNG: §e" + type.getColorName() + " §ađã phục tùng và luôn bảo vệ bạn! (Bấm lại để cho lui đi)"), true);

                    ItemStackDataHelper.putBoolean(stack, "IsSummoned", true);
                    ItemStackDataHelper.putString(stack, "DemonUUID", newDemon.getStringUUID());
                    return InteractionResultHolder.consume(stack);
                }
            }
        }

        return InteractionResultHolder.pass(stack);
    }

    private static String getFarewellMessage(DemonType type) {
        switch (type) {
            case ROUGE:
                return "Hmph! Khi nào có kẻ địch xứng tầm thì hãy triệu gọi ta.";
            case NOIR:
                return "Kufufufu... Tôi xin tạm lui về ấn chú. Bất cứ khi nào ngài cần, tôi sẽ lập tức xuất hiện phụng sự ngài, Master!";
            case BLANC:
                return "Ta sẽ nghỉ ngơi trong ấn chú. Đừng để ta đợi quá lâu nhé~";
            case JAUNE:
                return "Haha! Nhớ gọi ta ra đánh nhau sớm đấy nhé!";
            case VIOLET:
                return "Tạm biệt chủ nhân yêu quý~ Nhớ triệu hồi em sớm nha~";
            case BLEU:
                return "Tôi xin phép lui đi nghỉ ngơi...";
            case VERT:
                return "Tôi xin tạm lui. Chúc ngài vạn sự bình an.";
            default:
                return "Tôi xin tạm lui về ấn chú.";
        }
    }

    private static String getSummonGreeting(DemonType type) {
        switch (type) {
            case ROUGE:
                return "Haha! Kẻ nào dám làm phiền chủ nhân của ta? Ta sẽ thiêu rụi chúng thành tro bụi!";
            case NOIR:
                return "Kufufufu... Tôi đã có mặt theo tiếng gọi của ngài, Master! Mọi kẻ ngáng đường ngài đều sẽ bị móng vuốt của tôi xé toạc!";
            case BLANC:
                return "Ara ara... Đã đến lúc ta thanh tẩy những kẻ bất kính này rồi sao?";
            case JAUNE:
                return "Haha! Tuyệt vời! Hãy xem ta dùng trọng lực và ma pháp hủy diệt nghiền nát bọn chúng!";
            case VIOLET:
                return "Hihihi~ Em đến giúp chủ nhân đây! Bọn chúng sẽ nếm trải tử độc của em!";
            case BLEU:
                return "Tôi đã tới. Mọi mục tiêu sẽ bị đóng băng vĩnh viễn.";
            case VERT:
                return "Tôi xin kính cẩn tuân lệnh ngài. Kết giới bão tố đã sẵn sàng bảo hộ ngài chu toàn.";
            default:
                return "Tôi đã xuất thế theo lệnh ngài!";
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        DemonType type = getDemonType(stack);
        boolean summoned = isSummoned(stack);

        tooltip.add(Component.literal("§8§m--------------------------------"));
        tooltip.add(Component.literal("§6Ác Ma Thủy Tổ: §c§l" + type.getColorName() + " §7(" + type.getTitleVi() + ")"));
        tooltip.add(Component.literal("§7Quyền Năng: §e" + type.getSkillNameVi()));
        tooltip.add(Component.literal("§c❤ Sinh lực: §f" + (int) type.getMaxHealth() + " HP §7| §e⚔ Sát thương: §f" + (int) type.getAttackDamage()));
        tooltip.add(Component.literal("§d★ Tỷ lệ diệt Boss: §a" + (int) (type.getBossWinRate() * 100) + "%"));

        if (summoned) {
            tooltip.add(Component.literal("§a● Trạng thái: §fĐang Hiện Diện Trên Chiến Trường"));
            tooltip.add(Component.literal(""));
            tooltip.add(Component.literal("§e▶ Chuột Phải: §6Cho lui đi §7(Thu hồi an toàn vào ấn chú)"));
            tooltip.add(Component.literal("§b▶ Shift + Chuột Phải: §fDịch chuyển & Đổi lệnh §a(Đi Theo / Đứng Canh)"));
        } else {
            tooltip.add(Component.literal("§7● Trạng thái: §bĐang Phong Ấn Trong Huyết Khế"));
            tooltip.add(Component.literal(""));
            tooltip.add(Component.literal("§e▶ Chuột Phải: §aTriệu hồi xuất thế §f(Phục tùng & Bảo vệ chủ nhân)"));
            tooltip.add(Component.literal("§7(Họ sẽ phục tùng trừ khi bị ai khác giết hoặc sài lại để cho lui đi)"));
        }
        tooltip.add(Component.literal("§8§m--------------------------------"));
    }
}
