package com.minhphuc.weapons.content.divine;

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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SanctuaryDisintegrationAbility {

    public static class ActiveSanctuary {
        public final ServerLevel level;
        public final ServerPlayer caster;
        public final Vec3 center;
        public final Display.ItemDisplay displayEntity;
        public int ticksRemaining;
        public final int totalTicks;
        public float currentAngleDegrees;

        public ActiveSanctuary(ServerLevel level, ServerPlayer caster, Vec3 center, Display.ItemDisplay displayEntity, int durationTicks) {
            this.level = level;
            this.caster = caster;
            this.center = center;
            this.displayEntity = displayEntity;
            this.ticksRemaining = durationTicks;
            this.totalTicks = durationTicks;
            this.currentAngleDegrees = 0.0F;
        }
    }

    private static final List<ActiveSanctuary> ACTIVE_SANCTUARIES = new ArrayList<>();

    /**
     * Kích hoạt tuyệt kĩ: Thánh Giới Linh Tử Băng Hoại (Sanctuary Disintegration)
     */
    public static void cast(ServerLevel level, ServerPlayer player, ItemStack sword) {
        // 1. Dò tìm mục tiêu: Quái vật hoặc Khối đất phía trước trong tầm 22 blocks (tối ưu gọn gàng)
        Vec3 eyePos = player.getEyePosition(1.0F);
        Vec3 lookVec = player.getLookAngle();
        Vec3 traceEnd = eyePos.add(lookVec.scale(22.0D));

        BlockHitResult hitResult = level.clip(new ClipContext(
                eyePos, traceEnd,
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player
        ));

        Vec3 targetCenter;
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos bp = hitResult.getBlockPos();
            targetCenter = new Vec3(bp.getX() + 0.5D, bp.getY() + 1.0D, bp.getZ() + 0.5D);
        } else {
            // Nếu nhìn vào không khí, chiếu xuống mặt đất phía trước 12 blocks
            Vec3 forwardPos = eyePos.add(lookVec.scale(12.0D));
            BlockPos groundPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, BlockPos.containing(forwardPos));
            targetCenter = new Vec3(groundPos.getX() + 0.5D, groundPos.getY(), groundPos.getZ() + 0.5D);
        }

        // 2. Tạo thực thể ItemDisplay hiển thị Ma Pháp Trận sắc nét nằm phẳng trên mặt đất
        Display.ItemDisplay display = EntityType.ITEM_DISPLAY.create(level);
        if (display != null) {
            display.moveTo(targetCenter.x, targetCenter.y + 0.05D, targetCenter.z, 0.0F, 0.0F);
            ItemDisplayAccessor itemDisplayAcc = (ItemDisplayAccessor) display;
            DisplayAccessor displayAcc = (DisplayAccessor) display;

            itemDisplayAcc.weapons$setItemStack(new ItemStack(ModItems.DISINTEGRATION_MAGIC_CIRCLE.get()));
            itemDisplayAcc.weapons$setItemTransform(ItemDisplayContext.FIXED);
            displayAcc.weapons$setBillboardConstraints(Display.BillboardConstraints.FIXED);
            display.setGlowingTag(true);
            displayAcc.weapons$setGlowColorOverride(0xFFEE66); // Vàng kim thánh điện
            displayAcc.weapons$setViewRange(2.0F);

            // Xoay 90 độ quanh trục X để nằm phẳng trên mặt đất (Scale gọn 11m)
            float initialScale = 11.0F;
            Quaternionf rotation = new Quaternionf().rotateX((float) Math.toRadians(90.0F));
            displayAcc.weapons$setTransformation(new Transformation(
                    new Vector3f(0.0F, 0.02F, 0.0F),
                    rotation,
                    new Vector3f(initialScale, initialScale, 0.01F),
                    null
            ));

            level.addFreshEntity(display);

            // Đăng ký thánh giới hoạt động trong 90 ticks (4.5 giây)
            ACTIVE_SANCTUARIES.add(new ActiveSanctuary(level, player, targetCenter, display, 90));
        }

        // Âm thanh thánh tích hình thành
        level.playSound(null, targetCenter.x, targetCenter.y, targetCenter.z,
                SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 2.5F, 1.4F);
        level.playSound(null, targetCenter.x, targetCenter.y, targetCenter.z,
                SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 2.5F, 1.2F);

        // Khóa mục tiêu ban đầu
        lockTargetsInSanctuary(level, targetCenter, 5.5D, player);

        player.displayClientMessage(
            Component.literal("§e§l[NGUYỆT QUANG KIẾM] §fBáo cáo. Khởi động Thánh Giới Linh Tử Băng Hoại! Đang khóa kết giới cá thể... ⚔️✨"),
            true
        );

        player.getCooldowns().addCooldown(sword.getItem(), 140); // 7 giây hồi chiêu
    }

    /**
     * Cập nhật các thánh trận đang hoạt động mỗi tick
     */
    public static void tickSanctuaries(ServerLevel serverLevel) {
        if (ACTIVE_SANCTUARIES.isEmpty()) return;

        Iterator<ActiveSanctuary> it = ACTIVE_SANCTUARIES.iterator();
        while (it.hasNext()) {
            ActiveSanctuary s = it.next();
            if (s.level != serverLevel) continue;

            s.ticksRemaining--;
            int elapsed = s.totalTicks - s.ticksRemaining;

            // Tốc độ xoay ma pháp trận tăng dần theo giai đoạn tích tụ linh lực
            float rotationSpeed = 3.5F;
            if (elapsed > 70) {
                rotationSpeed = 10.0F; // Giai đoạn 3: Bùng nổ cực đại
            } else if (elapsed > 25) {
                rotationSpeed = 6.0F;  // Giai đoạn 2: Tụ năng lượng
            }
            s.currentAngleDegrees += rotationSpeed;

            // 1. Cập nhật thực thể Display ma pháp trận
            if (s.displayEntity != null && s.displayEntity.isAlive()) {
                float scale = 11.0F;
                // 15 ticks đầu: Ma pháp trận bung nở mượt mà từ tâm ra ngoài (0 -> 11)
                if (elapsed < 16) {
                    scale = (elapsed / 15.0F) * 11.0F;
                }

                Quaternionf rotation = new Quaternionf()
                        .rotateX((float) Math.toRadians(90.0F))
                        .rotateZ((float) Math.toRadians(s.currentAngleDegrees));

                ((DisplayAccessor) s.displayEntity).weapons$setTransformation(new Transformation(
                        new Vector3f(0.0F, 0.02F, 0.0F),
                        rotation,
                        new Vector3f(scale, scale, 0.01F),
                        null
                ));
            }

            // 2. GIAI ĐOẠN 1 (0..25 ticks): 7 Thánh Trụ Ánh Sáng vươn cao dựng thành kết giới (Bán kính 5.0m)
            double radius = 5.0D;
            if (elapsed <= 25) {
                if (s.ticksRemaining % 4 == 0) {
                    double pillarHeight = (elapsed / 25.0D) * 4.5D;
                    for (int i = 0; i < 7; i++) {
                        double rad = Math.toRadians((i * (360.0D / 7.0D)) + s.currentAngleDegrees);
                        double px = s.center.x + Math.cos(rad) * radius;
                        double pz = s.center.z + Math.sin(rad) * radius;

                        s.level.sendParticles(ParticleTypes.END_ROD, px, s.center.y + pillarHeight, pz, 1, 0.01D, 0.05D, 0.01D, 0.01D);
                    }
                }
            }

            // 3. GIAI ĐOẠN 2 (25..75 ticks): Linh Tử Hội Tụ (Inward Convergence) & Vòng Thánh Quang Nâng Cao
            if (elapsed > 25 && elapsed <= 75) {
                // 7 Thánh trụ duy trì phát sáng nhẹ mỗi 4 ticks
                if (s.ticksRemaining % 4 == 0) {
                    for (int i = 0; i < 7; i++) {
                        double rad = Math.toRadians((i * (360.0D / 7.0D)) + s.currentAngleDegrees);
                        double px = s.center.x + Math.cos(rad) * radius;
                        double pz = s.center.z + Math.sin(rad) * radius;
                        s.level.sendParticles(ParticleTypes.END_ROD, px, s.center.y + 0.8D, pz, 1, 0.01D, 0.2D, 0.01D, 0.01D);

                        // Hạt linh tử ánh sáng bay xoáy ốc hội tụ vào tâm
                        Vec3 toCenter = s.center.subtract(new Vec3(px, s.center.y + 1.2D, pz)).normalize().scale(0.35D);
                        s.level.sendParticles(ParticleTypes.WAX_OFF, px, s.center.y + 1.2D, pz, 0, toCenter.x, 0.03D, toCenter.z, 0.2D);
                    }
                }

                // Vòng tròn thánh quang nâng dần từ mặt đất lên độ cao 4m
                double ringY = s.center.y + ((elapsed - 25) / 50.0D) * 4.0D;
                double ringRadius = 4.8D - ((elapsed - 25) / 50.0D) * 2.5D; // Thu hẹp dần
                if (s.ticksRemaining % 5 == 0) {
                    for (int a = 0; a < 360; a += 60) {
                        double aRad = Math.toRadians(a + s.currentAngleDegrees * 2);
                        double rx = s.center.x + Math.cos(aRad) * ringRadius;
                        double rz = s.center.z + Math.sin(aRad) * ringRadius;
                        s.level.sendParticles(ParticleTypes.END_ROD, rx, ringY, rz, 1, 0, 0, 0, 0);
                    }
                }

                // Âm thanh thánh tích tích tụ năng lượng mỗi 12 ticks
                if (elapsed % 12 == 0) {
                    float pitch = 0.8F + ((elapsed - 25) / 50.0F) * 1.2F;
                    s.level.playSound(null, s.center.x, s.center.y, s.center.z,
                            SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.5F, pitch);
                }
            }

            // 4. GIAI ĐOẠN 3 (75..90 ticks): Bầu Trời Tích Tụ Quang Năng Chuẩn Bị Giáng Phạt
            if (elapsed > 75 && elapsed < 90) {
                // Hạt ánh sáng cực quang trên đỉnh trời chuẩn bị đánh xuống
                double skyY = s.center.y + 20.0D;
                s.level.sendParticles(ParticleTypes.FLASH, s.center.x, skyY, s.center.z, 1, 0.2D, 0.2D, 0.2D, 0);
                s.level.sendParticles(ParticleTypes.END_ROD, s.center.x, skyY, s.center.z, 2, 0.8D, 0.8D, 0.8D, 0.08D);

                if (elapsed == 80) {
                    s.level.playSound(null, s.center.x, s.center.y, s.center.z,
                            SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 1.0F, 1.8F);
                }
            }

            // Khóa cứng chuyển động toàn bộ kẻ địch trong thánh trận mỗi 10 ticks (0.5s)
            if (s.ticksRemaining % 10 == 0) {
                lockTargetsInSanctuary(s.level, s.center, 5.5D, s.caster);
            }

            // 5. KẾT THÚC: Cột Thiên Phạt Cực Quang giáng lâm & Phân Rã Linh Tử!
            if (s.ticksRemaining <= 0) {
                executeDisintegrationStrike(s.level, s.center, 5.5D, s.caster);

                // Xóa thực thể ma pháp trận
                if (s.displayEntity != null && s.displayEntity.isAlive()) {
                    s.displayEntity.discard();
                }

                it.remove();
            }
        }
    }

    /**
     * Khóa cứng mọi chuyển động của sinh vật bên trong kết giới
     */
    private static void lockTargetsInSanctuary(ServerLevel level, Vec3 center, double radius, ServerPlayer caster) {
        AABB box = new AABB(
                center.x - radius, center.y - 2.0D, center.z - radius,
                center.x + radius, center.y + 6.0D, center.z + radius
        );

        List<LivingEntity> victims = level.getEntitiesOfClass(LivingEntity.class, box, e -> e != caster && e.isAlive());
        for (LivingEntity v : victims) {
            double distSq = v.position().distanceToSqr(center);
            if (distSq <= radius * radius) {
                v.setDeltaMovement(0, 0, 0);
                v.hasImpulse = true;
                v.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 255, false, false, true));
                v.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 255, false, false, true));
                v.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, 0, false, false, true));
            }
        }
    }

    /**
     * Cột Thiên Phạt Linh Tử Băng Hoại giáng xuống xóa sổ toàn bộ mục tiêu
     */
    private static void executeDisintegrationStrike(ServerLevel level, Vec3 center, double radius, ServerPlayer caster) {
        // 1. Âm thanh thiên phạt chấn động thế giới
        level.playSound(null, center.x, center.y, center.z,
                SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 3.0F, 0.9F);
        level.playSound(null, center.x, center.y, center.z,
                SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 2.5F, 1.2F);
        level.playSound(null, center.x, center.y, center.z,
                SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS, 3.0F, 0.6F);
        level.playSound(null, center.x, center.y, center.z,
                SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 2.5F, 1.5F);

        // 2. Cột ánh sáng cực quang linh tử giáng từ trời cao xuống tâm trận (Chỉ ~6 hạt, 0% lag)
        for (double y = center.y; y <= center.y + 18.0D; y += 3.0D) {
            level.sendParticles(ParticleTypes.FLASH, center.x, y, center.z, 1, 0, 0, 0, 0);
            level.sendParticles(ParticleTypes.END_ROD, center.x, y, center.z, 1, 0.2D, 0.1D, 0.2D, 0.03D);
        }

        // Vụ nổ ánh sáng linh tử bao phủ toàn bộ thánh trận
        level.sendParticles(ParticleTypes.FLASH, center.x, center.y + 1.0D, center.z, 1, 0.2D, 0.2D, 0.2D, 0);
        level.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, center.x, center.y + 1.0D, center.z, 8, 1.5D, 0.8D, 1.5D, 0.08D);

        // 3. Tiêu diệt và phân rã linh tử vạn vật trong thánh trận
        AABB box = new AABB(
                center.x - radius, center.y - 2.0D, center.z - radius,
                center.x + radius, center.y + 7.0D, center.z + radius
        );

        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, box, e -> e != caster);
        List<String> victimNames = new ArrayList<>();
        int destroyedCount = 0;
        for (LivingEntity target : targets) {
            double distSq = target.position().distanceToSqr(center);
            if (distSq <= radius * radius) {
                victimNames.add("cá thể " + target.getDisplayName().getString());
                com.minhphuc.weapons.content.tensura.TensuraEvents.handleMobDeathDrop(caster, target);
                level.sendParticles(ParticleTypes.SOUL, target.getX(), target.getY() + 1.0D, target.getZ(), 2, 0.1D, 0.2D, 0.1D, 0.02D);
                target.hurt(level.damageSources().playerAttack(caster), 100000.0F);
                if (target.isAlive()) {
                    target.discard(); // Tiêu diệt triệt để cả Boss (Rồng Ender, Wither, Warden...)
                }
                destroyedCount++;
            }
        }

        // Tiêu biến các item rớt đất trong vùng phân rã linh tử
        List<ItemEntity> droppedItems = level.getEntitiesOfClass(ItemEntity.class, box, e -> e.position().distanceToSqr(center) <= radius * radius);
        for (ItemEntity item : droppedItems) {
            item.discard();
        }

        if (caster != null) {
            String killMessage;
            if (destroyedCount == 1) {
                killMessage = "Báo cáo: Cá thể " + victimNames.get(0).substring(7) + " đã bị tiêu diệt";
            } else if (destroyedCount > 1 && destroyedCount <= 3) {
                killMessage = "Báo cáo: " + String.join(", ", victimNames) + " đã bị tiêu diệt";
            } else if (destroyedCount > 3) {
                killMessage = "Báo cáo: " + victimNames.get(0) + " và " + (destroyedCount - 1) + " cá thể khác đã bị tiêu diệt";
            } else {
                killMessage = "Báo cáo: Không có cá thể nào trong phạm vi";
            }
            caster.displayClientMessage(
                Component.literal("§e§l[THÁNH GIỚI] §f" + killMessage),
                true
            );
        }
    }
}
