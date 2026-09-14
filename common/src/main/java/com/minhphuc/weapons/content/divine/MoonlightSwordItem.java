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
            case 1 -> "§e§l2. Băng Ma Linh Tử Trảm (Ice Demon Spirit Slash)";
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
            Component.literal("§6§l[NGUYỆT QUANG THẦN TẾ KIẾM] §fChế độ: " + getSkillName(next) + " §7(Chuột Phải để dùng)"),
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
                // Chiêu 2: Băng Ma Linh Tử Trảm
                executeBangMaLinhTuTram(serverLevel, serverPlayer, stack);
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

        for (double d = 1.0D; d <= maxDist; d += 0.5D) {
            Vec3 center = eyePos.add(look.scale(d));
            double arcWidth = 1.2D + (d * 0.2D); // Lưỡi kiếm khí mở rộng dần theo khoảng cách

            // Quét kiếm khí hình vòng cung lưỡi liềm đỏ - đen
            for (double offset = -arcWidth; offset <= arcWidth; offset += 0.4D) {
                Vec3 p = center.add(right.scale(offset));

                // Hạt đỏ rực và đen u tối đan xen
                level.sendParticles(new DustParticleOptions(new Vector3f(0.85F, 0.05F, 0.05F), 1.5F),
                        p.x, p.y, p.z, 1, 0.05D, 0.05D, 0.05D, 0);
                level.sendParticles(new DustParticleOptions(new Vector3f(0.1F, 0.0F, 0.12F), 1.5F),
                        p.x, p.y + 0.1D, p.z, 1, 0.05D, 0.05D, 0.05D, 0);
                level.sendParticles(ParticleTypes.CRIMSON_SPORE, p.x, p.y, p.z, 1, 0.02D, 0.02D, 0.02D, 0.01D);
            }

            // Quét sát thương trảm sát mọi sinh vật
            AABB slashBox = new AABB(
                center.x - arcWidth, center.y - 1.2D, center.z - arcWidth,
                center.x + arcWidth, center.y + 1.2D, center.z + arcWidth
            );

            List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, slashBox, e -> e != player && e.isAlive());
            for (LivingEntity target : targets) {
                if (!killedEntities.contains(target)) {
                    target.hurt(level.damageSources().genericKill(), 100000.0F);
                    if (target.isAlive()) {
                        target.discard();
                    }
                    killedEntities.add(target);
                }
            }
        }

        player.displayClientMessage(
            Component.literal("§4§l[THẦN TỴ] §fĐã phóng trảm kích đỏ đen xé rách không gian 10 blocks! (Xóa sổ " + killedEntities.size() + " sinh vật)"),
            true
        );

        player.getCooldowns().addCooldown(this, 30); // 1.5 giây hồi chiêu
    }

    /**
     * Chiêu 2: Băng Ma Linh Tử Trảm (Bắn viên đạn ma pháp hoàng kim xuyên khối 10 blocks tìm và kết liễu sinh vật sống)
     */
    private void executeBangMaLinhTuTram(ServerLevel level, ServerPlayer player, ItemStack stack) {
        Vec3 start = player.getEyePosition(1.0F);
        Vec3 look = player.getLookAngle();
        double maxDist = 10.0D;

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.8F, 1.8F);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.5F, 1.2F);

        LivingEntity executedTarget = null;

        // Đạn hoàng kim bay xuyên mọi khối địa hình (phasing through blocks)
        for (double d = 0.5D; d <= maxDist; d += 0.3D) {
            Vec3 pos = start.add(look.scale(d));

            // Hiệu ứng hạt đạn vàng chói lọi
            level.sendParticles(ParticleTypes.WAX_OFF, pos.x, pos.y, pos.z, 3, 0.08D, 0.08D, 0.08D, 0.01D);
            level.sendParticles(ParticleTypes.END_ROD, pos.x, pos.y, pos.z, 1, 0.02D, 0.02D, 0.02D, 0.02D);
            level.sendParticles(ParticleTypes.GLOW, pos.x, pos.y, pos.z, 2, 0.05D, 0.05D, 0.05D, 0.01D);

            // Kiểm tra va chạm sinh vật sống
            AABB hitBox = new AABB(
                pos.x - 0.8D, pos.y - 0.8D, pos.z - 0.8D,
                pos.x + 0.8D, pos.y + 0.8D, pos.z + 0.8D
            );

            List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, hitBox, e -> e != player && e.isAlive());
            if (!targets.isEmpty()) {
                executedTarget = targets.get(0);

                // Nổ tung hạt hoàng kim rực rỡ khi chạm đích
                level.sendParticles(ParticleTypes.FLASH, pos.x, pos.y + 0.5D, pos.z, 1, 0, 0, 0, 0);
                level.sendParticles(ParticleTypes.WAX_OFF, pos.x, pos.y + 0.5D, pos.z, 25, 0.4D, 0.4D, 0.4D, 0.1D);

                level.playSound(null, pos.x, pos.y, pos.z,
                        SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS, 2.0F, 1.5F);
                level.playSound(null, pos.x, pos.y, pos.z,
                        SoundEvents.ARROW_HIT_PLAYER, SoundSource.PLAYERS, 2.0F, 1.0F);

                // Kết liễu mục tiêu lập tức
                executedTarget.hurt(level.damageSources().genericKill(), 100000.0F);
                if (executedTarget.isAlive()) {
                    executedTarget.discard();
                }

                // Dừng đạn ngay khi đã kết liễu được mục tiêu
                break;
            }
        }

        if (executedTarget != null) {
            player.displayClientMessage(
                Component.literal("§e§l[BĂNG MA LINH TỬ TRẢM] §fĐạn linh tử hoàng kim đã xuyên khối và kết liễu §c" + executedTarget.getName().getString() + "§f! ⚡"),
                true
            );
        } else {
            player.displayClientMessage(
                Component.literal("§e§l[BĂNG MA LINH TỬ TRẢM] §fĐạn linh tử hoàng kim đã phóng xuyên qua các khối phía trước!"),
                true
            );
        }

        player.getCooldowns().addCooldown(this, 30); // 1.5 giây hồi chiêu
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        int currentSkill = ItemStackDataHelper.getInt(stack, NBT_SKILL);

        tooltip.add(Component.literal("§6§l[VŨ KHÍ THẦN THOẠI]"));
        tooltip.add(Component.literal("§b§lNguyệt Quang Thần Tế Kiếm (Moonlight Ritual Sword)"));
        tooltip.add(Component.literal("§7Bảo kiếm hộ vệ thánh điện, chém nát vạn vật thế gian."));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§e⚡ Sức Mạnh Tối Thượng:"));
        tooltip.add(Component.literal("§7- §aChém thường 1 phát là chết quái thường"));
        tooltip.add(Component.literal("§7- §aĐánh vỡ nát toàn bộ Giáp & Vũ Khí của đối thủ trong đòn đầu tiên"));
        tooltip.add(Component.literal("§7- §aĐối với Boss (Rồng Ender, Wither, Warden, Iron Golem):"));
        tooltip.add(Component.literal("§7   + Nhát đầu tiên: Rút ngay §c80% máu tối đa"));
        tooltip.add(Component.literal("§7   + Nhát thứ hai: Chém kết liễu chết luôn"));
        tooltip.add(Component.literal("§7- §aĐộ bền bất tử, không bao giờ bị phá hủy"));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§6⚡ Kỹ Năng Đang Chọn: " + getSkillName(currentSkill)));
        tooltip.add(Component.literal("§7- Nhấn phím §e[Z] §7để chuyển đổi thứ tự chiêu thức"));
        tooltip.add(Component.literal("§7- Nhấn §a[Chuột Phải] §7để thi triển kỹ năng đã chọn"));
    }
}
