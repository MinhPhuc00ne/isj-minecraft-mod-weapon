package com.minhphuc.weapons.content.divine;

import com.minhphuc.weapons.content.tensura.BeelzebuthAbility;
import com.minhphuc.weapons.data.EntityDataHelper;
import com.minhphuc.weapons.data.ItemStackDataHelper;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.Unbreakable;

public class MoonlightSwordItem extends Item {
    public static final String NBT_SKILL = "ActiveSkill";

    public MoonlightSwordItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC).fireResistant()
                .component(DataComponents.UNBREAKABLE, new Unbreakable(false)));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return false; // Bất tử, không bao giờ mất độ bền
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true; // Hào quang Nguyệt Quang Thần Thoại
    }

    public static String getSkillName(int skillOrdinal) {
        return switch (skillOrdinal) {
            case 0 -> "§4§l1. Thần Tỵ - Hắc Thiểm Bá Vương (Divine Departure - Black Flash)";
            case 1 -> "§e§l2. Tam Trọng Thánh Giới - Linh Tử Băng Hoại (Multi-Tier Disintegration)";
            case 2 -> "§b§l3. Tà Khứ Vũ Thê Tử (Jacob's Ladder)";
            case 3 -> "§d§l4. Bạo Thực Vương Beelzebuth";
            default -> "§7Chưa chọn";
        };
    }

    public static void cycleSkill(ServerPlayer player, ItemStack stack) {
        boolean isTrueDemonLord = EntityDataHelper.getCustomData(player).getBoolean("TensuraTrueDemonLord");
        int maxSkills = isTrueDemonLord ? 4 : 3; // Nếu là Chân Ma Vương thì có thêm skill 4: Beelzebuth

        int current = ItemStackDataHelper.getInt(stack, NBT_SKILL);
        int next = (current + 1) % maxSkills;
        ItemStackDataHelper.putInt(stack, NBT_SKILL, next);

        player.displayClientMessage(
            Component.literal("§6§l[NGUYỆT QUANG THẦN TẾ KIẾM] §fBáo cáo. Chế độ: " + getSkillName(next) + " §7(Chuột Phải để dùng)"),
            true
        );

        float pitch = 1.0F + (next * 0.3F);
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, pitch);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
            int skill = ItemStackDataHelper.getInt(stack, NBT_SKILL);

            if (skill == 0) {
                // Chiêu 1: Thần Tỵ - Hắc Thiểm Bá Vương
                executeThanTy(serverLevel, serverPlayer, stack);
            } else if (skill == 1) {
                // Chiêu 2: Tam Trọng Thánh Giới - Linh Tử Băng Hoại
                SanctuaryDisintegrationAbility.cast(serverLevel, serverPlayer, stack);
            } else if (skill == 2) {
                // Chiêu 3: Tà Khứ Vũ Thê Tử (Jacob's Ladder)
                JacobsLadderAbility.cast(serverLevel, serverPlayer, stack);
            } else if (skill == 3) {
                // Chiêu 4: Bạo Thực Vương Beelzebuth (Dành cho Chân Ma Vương)
                BeelzebuthAbility.executeBeelzebuth(serverLevel, serverPlayer);
                player.getCooldowns().addCooldown(this, 30);
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    /**
     * Chiêu 1: Thần Tỵ - Hắc Thiểm Bá Vương (Tia sét đỏ - đen xé rách không gian kết liễu mọi sinh vật)
     */
    private void executeThanTy(ServerLevel level, ServerPlayer player, ItemStack stack) {
        Vec3 eyePos = player.getEyePosition(1.0F);
        Vec3 look = player.getLookAngle();
        double maxDist = 12.0D;

        // 1. Kích hoạt hiệu ứng âm thanh Hắc Thiểm & Haki Bá Vương rung chấn
        BlackFlashVFX.playBlackFlashSounds(level, player);

        // 2. Kích hoạt chuỗi tia sét ziczac Hắc Thiểm Đỏ - Đen đa tầng
        BlackFlashVFX.spawnBlackFlashSlashVFX(level, player, eyePos, look, maxDist);

        Set<LivingEntity> killedEntities = new HashSet<>();

        // Véc tơ vuông góc ngang để tạo hình vòng cung kiếm khí
        Vec3 right = new Vec3(-look.z, 0, look.x).normalize();

        // 3. Quét trảm sát toàn bộ sinh vật trong quạt chém kiếm khí
        Vec3 end = eyePos.add(look.scale(maxDist));
        double maxArcWidth = 1.5D + (maxDist * 0.25D);
        AABB slashSector = new AABB(eyePos, end).inflate(maxArcWidth + 1.0D);
        List<LivingEntity> potentialTargets = level.getEntitiesOfClass(LivingEntity.class, slashSector, e -> e != player && e.isAlive());

        for (LivingEntity target : potentialTargets) {
            Vec3 toTarget = target.position().add(0, target.getBbHeight() * 0.5D, 0).subtract(eyePos);
            double distAlongLook = toTarget.dot(look);

            if (distAlongLook >= 0.5D && distAlongLook <= maxDist) {
                double arcWidthAtDist = 1.5D + (distAlongLook * 0.3D);
                double lateralDist = Math.abs(toTarget.dot(right));
                double verticalDist = Math.abs(toTarget.y);

                if (lateralDist <= arcWidthAtDist && verticalDist <= 2.8D) {
                    com.minhphuc.weapons.content.tensura.TensuraEvents.handleMobDeathDrop(player, target);
                    
                    // Hiệu ứng nổ bùng tia sét Hắc Thiểm ngay tại cơ thể mục tiêu bị trảm
                    BlackFlashVFX.spawnTargetImpactVFX(level, target);

                    target.hurt(level.damageSources().playerAttack(player), 100000.0F);
                    if (target.isAlive()) {
                        target.discard();
                    }
                    killedEntities.add(target);
                }
            }
        }

        player.displayClientMessage(
            Component.literal("§4§l[THẦN TỴ - HẮC THIỂM BÁ VƯƠNG] §fBáo cáo. Đã giải phóng tia sét đỏ đen xé rách không gian! (Xóa sổ " + killedEntities.size() + " cá thể)"),
            true
        );

        player.getCooldowns().addCooldown(this, 30); // 1.5 giây hồi chiêu
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        int currentSkill = ItemStackDataHelper.getInt(stack, NBT_SKILL);

        tooltip.add(Component.literal("§6§l[VŨ KHÍ THẦN THOẠI]"));
        tooltip.add(Component.literal("§b§lNguyệt Quang Thần Tế Kiếm (Moonlight Ritual Sword)"));
        tooltip.add(Component.literal("§7Bảo kiếm hộ vệ thánh điện của Thánh Kỵ sĩ Hinata."));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§e⚡ Đặc Tính Thần Thoại:"));
        tooltip.add(Component.literal("§7- §aChém thường 1 phát kết liễu quái thường & phá vỡ toàn bộ trang bị đối thủ"));
        tooltip.add(Component.literal("§7- §aTrảm Boss (Rồng Ender, Wither, Warden): Đòn 1 rút 80% HP, đòn 2 tất sát"));
        tooltip.add(Component.literal("§7- §aĐộ bền bất tử, không bao giờ bị phá hủy"));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§6⚡ Kỹ Năng Đang Chọn: " + getSkillName(currentSkill)));
        tooltip.add(Component.literal("§7- Nhấn phím §e[Z] §7để chuyển đổi thứ tự chiêu thức:"));
        tooltip.add(Component.literal("§7   + §41. Thần Tỵ (Hắc Thiểm): §fTia sét đỏ đen xé rách không gian, tiêu diệt mọi sinh vật trong 12 blocks"));
        tooltip.add(Component.literal("§7   + §e2. Tam Trọng Thánh Giới: §fMa Pháp Trận 3 tầng giam cầm tuyệt đối & Cột Thiên Phạt phân rã"));
        tooltip.add(Component.literal("§7   + §b3. Tà Khứ Vũ Thê Tử: §fCột sáng vuông 4x4 chọc trời, thanh tẩy tà thuật & diệt trừ nguyền rủa"));
        tooltip.add(Component.literal("§7   + §d4. Bạo Thực Vương: §fNuốt chửng vạn vật (Chân Ma Vương)"));
        tooltip.add(Component.literal("§7- Nhấn §a[Chuột Phải] §7để thi triển kỹ năng đã chọn"));
    }
}
