package com.minhphuc.weapons.content.tensura;

import com.minhphuc.weapons.entity.tensura.DemonType;
import com.minhphuc.weapons.entity.tensura.VelgryndEntity;
import com.minhphuc.weapons.init.ModItems;
import com.minhphuc.weapons.mixin.DisplayAccessor;
import com.minhphuc.weapons.mixin.ItemDisplayAccessor;
import com.mojang.math.Transformation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Skill 1 Chung: Tà Khứ Vũ Thê Tử (Death Streak / Nuclear Magic)
 * - Vòng tròn ma thuật khổng lồ phạm vi 10 block (3 tầng xếp chồng xoay ngược chiều).
 * - Cột sáng cực đại từ thiên không giáng xuống.
 * - Chưa là Ma Vương: Tuyệt đối KHÔNG phá hủy block.
 * - Đã là Ma Vương: Phá hủy toàn bộ block trong phạm vi cột sáng!
 */
public class DeathStreakAbility {

    public static class ActiveDeathStreak {
        public final ServerLevel level;
        public final ServerPlayer caster;
        public final Vec3 center;
        public final DemonType demonType;
        public final boolean isDemonLord;

        // 3 Tầng ma trận xếp chồng
        public Display.ItemDisplay circleOuter;
        public Display.ItemDisplay circleMiddle;
        public Display.ItemDisplay circleInner;

        // Cột sáng cực đại
        public Display.ItemDisplay lightBeam;

        public int ticksRemaining;
        public final int totalTicks; // 70 ticks (3.5 giây)
        public float rotationAngle;
        public boolean beamTriggered = false;

        public ActiveDeathStreak(ServerLevel level, ServerPlayer caster, Vec3 center, DemonType demonType, boolean isDemonLord,
                                 Display.ItemDisplay circleOuter, Display.ItemDisplay circleMiddle, Display.ItemDisplay circleInner,
                                 int totalTicks) {
            this.level = level;
            this.caster = caster;
            this.center = center;
            this.demonType = demonType;
            this.isDemonLord = isDemonLord;
            this.circleOuter = circleOuter;
            this.circleMiddle = circleMiddle;
            this.circleInner = circleInner;
            this.ticksRemaining = totalTicks;
            this.totalTicks = totalTicks;
        }
    }

    private static final List<ActiveDeathStreak> ACTIVE_STREAKS = new ArrayList<>();

    public static Item getCircleItem(DemonType type) {
        if (type == null) return ModItems.MAGIC_CIRCLE_NOIR.get();
        return switch (type) {
            case ROUGE -> ModItems.MAGIC_CIRCLE_ROUGE.get();
            case NOIR -> ModItems.MAGIC_CIRCLE_NOIR.get();
            case BLANC -> ModItems.MAGIC_CIRCLE_BLANC.get();
            case JAUNE -> ModItems.MAGIC_CIRCLE_JAUNE_NUCLEAR.get();
            case VIOLET -> ModItems.MAGIC_CIRCLE_VIOLET.get();
            case BLEU -> ModItems.MAGIC_CIRCLE_BLEU.get();
            case VERT -> ModItems.MAGIC_CIRCLE_VERT.get();
        };
    }

    public static void cast(ServerLevel level, ServerPlayer player) {
        DemonType type = PrimordialPlayerDataHelper.getPrimordialType(player);
        if (type == null) return;

        boolean isDemonLord = PrimordialPlayerDataHelper.isDemonLord(player);

        // Vị trí tâm: Đích ngắm phía trước người chơi 12 block trên mặt đất
        Vec3 look = player.getLookAngle();
        Vec3 target = player.position().add(look.x * 12.0, 0, look.z * 12.0);

        BlockPos targetPos = BlockPos.containing(target);
        // Tìm mặt đất phù hợp
        while (level.getBlockState(targetPos).isAir() && targetPos.getY() > level.getMinBuildHeight() + 2) {
            targetPos = targetPos.below();
        }
        Vec3 center = new Vec3(target.x, targetPos.getY() + 1.05, target.z);

        // Khởi tạo 3 tầng Ma Trận Xếp Chồng (Layered Magic Circles)
        Item circleItem = getCircleItem(type);
        Display.ItemDisplay outer = spawnCircleDisplay(level, center, circleItem, 20.0f, 0.05f);
        Display.ItemDisplay mid = spawnCircleDisplay(level, center, circleItem, 14.0f, 0.15f);
        Display.ItemDisplay inner = spawnCircleDisplay(level, center, circleItem, 8.0f, 0.25f);

        ACTIVE_STREAKS.add(new ActiveDeathStreak(level, player, center, type, isDemonLord, outer, mid, inner, 70));

        level.playSound(null, center.x, center.y, center.z,
                SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 2.5F, 0.7F);
        level.playSound(null, center.x, center.y, center.z,
                SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 2.5F, 0.8F);

        String demonVi = PrimordialPlayerDataHelper.getDemonTitleVi(type);
        player.displayClientMessage(
                Component.literal("§6§l✦ THẦN CHÚ THỦY TỔ ✦ §e" + demonVi + " §cđang khai mở Ma Trận Tuyệt Kỹ: §4§lTÀ KHỨ VŨ THÊ TỬ (Death Streak)!"),
                true
        );
    }

    private static Display.ItemDisplay spawnCircleDisplay(ServerLevel level, Vec3 pos, Item item, float scale, float yOffset) {
        Display.ItemDisplay display = new Display.ItemDisplay(EntityType.ITEM_DISPLAY, level);
        display.setPos(pos.x, pos.y + yOffset, pos.z);
        ((ItemDisplayAccessor) display).weapons$setItemStack(new ItemStack(item));
        ((ItemDisplayAccessor) display).weapons$setItemTransform(ItemDisplayContext.FIXED);

        // Đặt ma trận nằm ngang xoay góc 90 độ X
        Quaternionf rot = new Quaternionf().rotateX((float) Math.toRadians(90.0));
        Transformation t = new Transformation(new Vector3f(0, 0, 0), rot, new Vector3f(scale, scale, 0.05f), new Quaternionf());
        ((DisplayAccessor) display).weapons$setTransformation(t);

        display.addTag("PrimordialDeathStreakDisplay");
        level.addFreshEntity(display);
        return display;
    }

    public static void tickStreaks(ServerLevel serverLevel) {
        if (ACTIVE_STREAKS.isEmpty()) return;

        Iterator<ActiveDeathStreak> it = ACTIVE_STREAKS.iterator();
        while (it.hasNext()) {
            ActiveDeathStreak s = it.next();
            if (s.level != serverLevel) continue;

            s.ticksRemaining--;
            int elapsed = s.totalTicks - s.ticksRemaining;
            s.rotationAngle += 4.5f;

            // Xoay 3 vòng ma trận ngược chiều nhau tạo chiều sâu không gian kỳ vĩ
            updateRotation(s.circleOuter, 20.0f, s.rotationAngle);
            updateRotation(s.circleMiddle, 14.0f, -s.rotationAngle * 1.3f);
            updateRotation(s.circleInner, 8.0f, s.rotationAngle * 1.8f);

            // Tụ lực hạt năng lượng nguyên thủy
            if (elapsed < 30) {
                double rad = 10.0;
                for (int i = 0; i < 4; i++) {
                    double ang = Math.toRadians((elapsed * 15 + i * 90) % 360);
                    double px = s.center.x + Math.cos(ang) * rad;
                    double pz = s.center.z + Math.sin(ang) * rad;
                    s.level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, px, s.center.y + 0.3, pz, 1, 0, 0, 0, 0);
                    s.level.sendParticles(ParticleTypes.PORTAL, s.center.x, s.center.y + 0.5, s.center.z, 2, 1.5, 0.5, 1.5, 0.1);
                }
            }

            // ================================================================
            // TICK 30 (Sau 1.5 giây): CỘT SÁNG CỰC ĐẠI CHIẾU THẲNG TỪ TRỜI XUỐNG!
            // ================================================================
            if (elapsed == 30 && !s.beamTriggered) {
                s.beamTriggered = true;

                // Tạo Cột Sáng từ trời (y+35m) giáng thẳng xuống
                s.lightBeam = new Display.ItemDisplay(EntityType.ITEM_DISPLAY, s.level);
                s.lightBeam.setPos(s.center.x, s.center.y + 18.0, s.center.z);
                ((ItemDisplayAccessor) s.lightBeam).weapons$setItemStack(new ItemStack(ModItems.DISINTEGRATION_LIGHT_BEAM.get()));
                ((ItemDisplayAccessor) s.lightBeam).weapons$setItemTransform(ItemDisplayContext.FIXED);

                Transformation beamTrans = new Transformation(
                        new Vector3f(0, 0, 0),
                        new Quaternionf(),
                        new Vector3f(12.0f, 40.0f, 12.0f),
                        new Quaternionf()
                );
                ((DisplayAccessor) s.lightBeam).weapons$setTransformation(beamTrans);
                s.lightBeam.addTag("PrimordialDeathStreakDisplay");
                s.level.addFreshEntity(s.lightBeam);

                // Âm thanh nổ sấm hủy diệt
                s.level.playSound(null, s.center.x, s.center.y, s.center.z,
                        SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 4.0F, 0.8F);
                s.level.playSound(null, s.center.x, s.center.y, s.center.z,
                        SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 3.5F, 0.7F);
                s.level.playSound(null, s.center.x, s.center.y, s.center.z,
                        SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 3.0F, 0.5F);

                s.level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, s.center.x, s.center.y + 1.0, s.center.z, 5, 1.0, 1.0, 1.0, 0.0);
                s.level.sendParticles(ParticleTypes.FLASH, s.center.x, s.center.y + 2.0, s.center.z, 3, 0, 0, 0, 0);

                // Xử lý Sát thương hủy diệt trong bán kính 10 block
                dealDeathStreakDamage(s);

                // Xử lý Phá Hủy Block:
                // NẾU LÀ MA VƯƠNG: Phá hủy toàn bộ block trong phạm vi 10 block!
                // NẾU CHƯA LÀ MA VƯƠNG: Tuyệt đối KHÔNG phá hủy block!
                if (s.isDemonLord) {
                    destroyBlocksInRadius(s.level, BlockPos.containing(s.center), 10);
                }
            }

            // Giai đoạn duy trì cột sáng và hạt bụi (Tick 30..60)
            if (elapsed > 30 && elapsed < 60) {
                s.level.sendParticles(ParticleTypes.FLASH, s.center.x, s.center.y + 1.5, s.center.z, 1, 0, 0, 0, 0);
                s.level.sendParticles(ParticleTypes.DRAGON_BREATH, s.center.x, s.center.y + 1.0, s.center.z, 15, 4.0, 2.0, 4.0, 0.1);
            }

            // Kết thúc (Tick 70): Dọn dẹp Entity ItemDisplay
            if (s.ticksRemaining <= 0) {
                if (s.circleOuter != null) s.circleOuter.discard();
                if (s.circleMiddle != null) s.circleMiddle.discard();
                if (s.circleInner != null) s.circleInner.discard();
                if (s.lightBeam != null) s.lightBeam.discard();
                it.remove();
            }
        }
    }

    private static void updateRotation(Display.ItemDisplay display, float scale, float angleDeg) {
        if (display == null || !display.isAlive()) return;
        Quaternionf rot = new Quaternionf()
                .rotateX((float) Math.toRadians(90.0))
                .rotateZ((float) Math.toRadians(angleDeg));
        Transformation t = new Transformation(new Vector3f(0, 0, 0), rot, new Vector3f(scale, scale, 0.05f), new Quaternionf());
        ((DisplayAccessor) display).weapons$setTransformation(t);
    }

    private static void dealDeathStreakDamage(ActiveDeathStreak s) {
        AABB box = new AABB(s.center.x - 10.0, s.center.y - 4.0, s.center.z - 10.0,
                s.center.x + 10.0, s.center.y + 35.0, s.center.z + 10.0);

        List<LivingEntity> targets = s.level.getEntitiesOfClass(LivingEntity.class, box,
                e -> e.isAlive() && e != s.caster);

        boolean hasBody = PrimordialPlayerDataHelper.hasPhysicalBody(s.caster);
        boolean isDemonLord = s.isDemonLord;

        for (LivingEntity victim : targets) {
            boolean isBoss = (victim instanceof net.minecraft.world.entity.boss.wither.WitherBoss)
                    || (victim instanceof net.minecraft.world.entity.boss.enderdragon.EnderDragon)
                    || (victim instanceof net.minecraft.world.entity.monster.warden.Warden)
                    || (victim instanceof net.minecraft.world.entity.animal.IronGolem)
                    || (victim instanceof net.minecraft.world.entity.monster.ElderGuardian);

            if (hasBody || isDemonLord) {
                // Đạt thể xác / Ma Vương: 2 đòn diệt Boss!
                if (isBoss) {
                    victim.hurt(s.caster.damageSources().magic(), victim.getMaxHealth() * 0.55F);
                } else if (!(victim instanceof VelgryndEntity)) {
                    victim.hurt(s.caster.damageSources().magic(), victim.getMaxHealth() * 4.0F);
                }
            } else {
                // Linh thể: Sát thương mạnh hơn Người Sắt nhưng yếu hơn Boss
                if (isBoss) {
                    victim.hurt(s.caster.damageSources().magic(), 60.0F);
                } else {
                    victim.hurt(s.caster.damageSources().magic(), 120.0F);
                }
            }
        }
    }

    private static void destroyBlocksInRadius(ServerLevel level, BlockPos center, int radius) {
        int rSq = radius * radius;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (dx * dx + dz * dz > rSq) continue;
                for (int dy = -3; dy <= 8; dy++) {
                    BlockPos p = center.offset(dx, dy, dz);
                    BlockState st = level.getBlockState(p);
                    if (!st.isAir() && st.getBlock() != Blocks.BEDROCK) {
                        level.setBlock(p, Blocks.AIR.defaultBlockState(), 2);
                    }
                }
            }
        }
    }
}
