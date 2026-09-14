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
            case 0 -> "§4§l1. Thần Tỵ (Divine Departure)";
            case 1 -> "§e§l2. Thánh Giới Linh Tử Băng Hoại (Sanctuary Disintegration)";
            case 2 -> "§d§l3. Bạo Thực Vương Beelzebuth";
            default -> "§7Chưa chọn";
        };
    }

    public static void cycleSkill(ServerPlayer player, ItemStack stack) {
        boolean isTrueDemonLord = EntityDataHelper.getCustomData(player).getBoolean("TensuraTrueDemonLord");
        int maxSkills = isTrueDemonLord ? 3 : 2; // Nếu là Chân Ma Vương thì có thêm skill 3: Beelzebuth

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
                // Chiêu 1: Thần Tỵ
                executeThanTy(serverLevel, serverPlayer, stack);
            } else if (skill == 1) {
                // Chiêu 2: Thánh Giới Linh Tử Băng Hoại (Sanctuary Disintegration)
                SanctuaryDisintegrationAbility.cast(serverLevel, serverPlayer, stack);
            } else if (skill == 2) {
                // Chiêu 3: Bạo Thực Vương Beelzebuth (Dành cho Chân Ma Vương)
                BeelzebuthAbility.executeBeelzebuth(serverLevel, serverPlayer);
                player.getCooldowns().addCooldown(this, 30);
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    /**
     * Chiêu 1: Thần Tỵ (Vung nhát chém kiếm khí đỏ - đen bay xa 10 blocks kết liễu mọi sinh vật)
     */
    private void executeThanTy(ServerLevel level, ServerPlayer player, ItemStack stack) {
        Vec3 eyePos = player.getEyePosition(1.0F);
        Vec3 look = player.getLookAngle();
        double maxDist = 10.0D;

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 2.0F, 0.6F);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 1.5F, 0.8F);

        Set<LivingEntity> killedEntities = new HashSet<>();

        // Véc tơ vuông góc ngang để tạo hình vòng cung kiếm khí
        Vec3 right = new Vec3(-look.z, 0, look.x).normalize();

        // 1. Quét trảm sát toàn bộ sinh vật trong quạt chém kiếm khí (1 query duy nhất thay vì 19 queries)
        Vec3 end = eyePos.add(look.scale(maxDist));
        double maxArcWidth = 1.2D + (maxDist * 0.2D);
        AABB slashSector = new AABB(eyePos, end).inflate(maxArcWidth + 1.0D);
        List<LivingEntity> potentialTargets = level.getEntitiesOfClass(LivingEntity.class, slashSector, e -> e != player && e.isAlive());

        for (LivingEntity target : potentialTargets) {
            Vec3 toTarget = target.position().add(0, target.getBbHeight() * 0.5D, 0).subtract(eyePos);
            double distAlongLook = toTarget.dot(look);

            if (distAlongLook >= 0.5D && distAlongLook <= maxDist) {
                double arcWidthAtDist = 1.2D + (distAlongLook * 0.25D);
                double lateralDist = Math.abs(toTarget.dot(right));
                double verticalDist = Math.abs(toTarget.y);

                if (lateralDist <= arcWidthAtDist && verticalDist <= 2.2D) {
                    com.minhphuc.weapons.content.tensura.TensuraEvents.handleMobDeathDrop(player, target);
                    target.hurt(level.damageSources().playerAttack(player), 100000.0F);
                    if (target.isAlive()) {
                        target.discard();
                    }
                    killedEntities.add(target);
                }
            }
        }

        // 2. Hiệu ứng hạt lưỡi liềm kiếm khí đỏ - đen sắc nét (step = 1.0m)
        for (double d = 1.0D; d <= maxDist; d += 1.0D) {
            Vec3 center = eyePos.add(look.scale(d));
            double arcWidth = 1.2D + (d * 0.2D);

            for (double offset = -arcWidth; offset <= arcWidth; offset += 0.6D) {
                Vec3 p = center.add(right.scale(offset));

                level.sendParticles(new DustParticleOptions(new Vector3f(0.85F, 0.05F, 0.05F), 1.2F),
                        p.x, p.y, p.z, 1, 0.03D, 0.03D, 0.03D, 0);
                level.sendParticles(new DustParticleOptions(new Vector3f(0.1F, 0.0F, 0.12F), 1.2F),
                        p.x, p.y + 0.1D, p.z, 1, 0.03D, 0.03D, 0.03D, 0);
            }
        }

        player.displayClientMessage(
            Component.literal("§4§l[THẦN TỴ] §fBáo cáo. Đã phóng trảm kích đỏ đen xé rách không gian 10 blocks! (Xóa sổ " + killedEntities.size() + " cá thể)"),
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
        tooltip.add(Component.literal("§7   + §41. Thần Tỵ: §fTrảm kích đỏ đen xé rách không gian 10 blocks"));
        tooltip.add(Component.literal("§7   + §e2. Thánh Giới Linh Tử Băng Hoại: §fTriệu hồi Ma Pháp Trận & Cột Thiên Phạt phân rã"));
        tooltip.add(Component.literal("§7   + §d3. Bạo Thực Vương: §fNuốt chửng vạn vật (Chân Ma Vương)"));
        tooltip.add(Component.literal("§7- Nhấn §a[Chuột Phải] §7để thi triển kỹ năng đã chọn"));
    }
}
