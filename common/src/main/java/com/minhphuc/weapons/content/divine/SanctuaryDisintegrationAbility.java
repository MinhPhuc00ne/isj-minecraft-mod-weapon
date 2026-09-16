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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.*;

public class SanctuaryDisintegrationAbility {

    public static class ActiveSanctuary {
        public final ServerLevel level;
        public final ServerPlayer caster;
        public final Vec3 center;
        public final Display.ItemDisplay baseDisplay;
        public final Display.ItemDisplay midDisplay;
        public final Display.ItemDisplay topDisplay;
        public int ticksRemaining;
        public final int totalTicks;
        public float currentAngleDegrees;
        public final Set<UUID> trappedVictimUuids = new HashSet<>();

        public final SkillPowerRoll powerRoll;

        public ActiveSanctuary(ServerLevel level, ServerPlayer caster, Vec3 center,
                               Display.ItemDisplay baseDisplay,
                               Display.ItemDisplay midDisplay,
                               Display.ItemDisplay topDisplay,
                               int durationTicks,
                               SkillPowerRoll powerRoll) {
            this.level = level;
            this.caster = caster;
            this.center = center;
            this.baseDisplay = baseDisplay;
            this.midDisplay = midDisplay;
            this.topDisplay = topDisplay;
            this.ticksRemaining = durationTicks;
            this.totalTicks = durationTicks;
            this.currentAngleDegrees = 0.0F;
            this.powerRoll = powerRoll;
        }

        public void cleanupDisplays() {
            if (baseDisplay != null && baseDisplay.isAlive()) {
                baseDisplay.discard();
            }
            if (midDisplay != null && midDisplay.isAlive()) {
                midDisplay.discard();
            }
            if (topDisplay != null && topDisplay.isAlive()) {
                topDisplay.discard();
            }
        }
    }

    private static final List<ActiveSanctuary> ACTIVE_SANCTUARIES = new ArrayList<>();

    /**
     * Kích hoạt tuyệt kĩ: Tam Trọng Thánh Giới - Linh Tử Băng Hoại (Multi-Tier Sanctuary Disintegration)
     */
    public static void cast(ServerLevel level, ServerPlayer player, ItemStack sword) {
        // 1. Dò tìm mục tiêu: Quái vật hoặc Khối đất phía trước trong tầm 22 blocks
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

        // 2. Tạo 3 thực thể ItemDisplay tạo nên Tam Trọng Thánh Giới (Đa Tầng Lập Thể)
        // Tầng 1: Địa Trận (Y + 0.05, Vàng Hoàng Kim 0xFFD700, Scale 12m)
        Display.ItemDisplay baseDisplay = createMagicCircleDisplay(level, targetCenter, 0.05D, 0.1F, 0xFFD700);
        // Tầng 2: Trung Trận (Y + 2.80, Bạch Kim Thánh Quang 0xFFFFFF, Scale 9.5m)
        Display.ItemDisplay midDisplay = createMagicCircleDisplay(level, targetCenter, 2.80D, 0.1F, 0xFFFFFF);
        // Tầng 3: Thiên Trận (Y + 5.80, Hoàng Kim Rực Rỡ 0xFFEE33, Scale 7.5m)
        Display.ItemDisplay topDisplay = createMagicCircleDisplay(level, targetCenter, 5.80D, 0.1F, 0xFFEE33);

        // Gieo xúc xắc xuất lực ngẫu nhiên
        SkillPowerRoll roll = SkillPowerRoll.roll();
        roll.announceAndPlayEffects(player, "Tam Trọng Thánh Giới - Linh Tử Băng Hoại");

        ActiveSanctuary sanctuary = new ActiveSanctuary(level, player, targetCenter, baseDisplay, midDisplay, topDisplay, 90, roll);
        ACTIVE_SANCTUARIES.add(sanctuary);

        // Âm thanh thánh tích hình thành chấn động
        level.playSound(null, targetCenter.x, targetCenter.y, targetCenter.z,
                SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 3.0F, 1.3F);
        level.playSound(null, targetCenter.x, targetCenter.y, targetCenter.z,
                SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 3.0F, 1.1F);
        level.playSound(null, targetCenter.x, targetCenter.y, targetCenter.z,
                SoundEvents.EVOKER_PREPARE_SUMMON, SoundSource.PLAYERS, 2.5F, 1.6F);

        // Khóa mục tiêu ban đầu ngay tại tick 0
        lockAndAnchorVictimsEveryTick(sanctuary);

        player.getCooldowns().addCooldown(sword.getItem(), 140); // 7 giây hồi chiêu
    }

    /**
     * Tạo thực thể ItemDisplay hiển thị Ma Pháp Trận sắc nét tại tọa độ và độ cao chỉ định
     */
    private static Display.ItemDisplay createMagicCircleDisplay(ServerLevel level, Vec3 center, double yOffset, float initialScale, int glowColor) {
        Display.ItemDisplay display = EntityType.ITEM_DISPLAY.create(level);
        if (display != null) {
            display.moveTo(center.x, center.y + yOffset, center.z, 0.0F, 0.0F);
            ItemDisplayAccessor itemDisplayAcc = (ItemDisplayAccessor) display;
            DisplayAccessor displayAcc = (DisplayAccessor) display;

            itemDisplayAcc.weapons$setItemStack(new ItemStack(ModItems.DISINTEGRATION_MAGIC_CIRCLE.get()));
            itemDisplayAcc.weapons$setItemTransform(ItemDisplayContext.FIXED);
            displayAcc.weapons$setBillboardConstraints(Display.BillboardConstraints.FIXED);
            display.setGlowingTag(true);
            displayAcc.weapons$setGlowColorOverride(glowColor);
            displayAcc.weapons$setViewRange(2.5F);

            Quaternionf rotation = new Quaternionf().rotateX((float) Math.toRadians(90.0F));
            displayAcc.weapons$setTransformation(new Transformation(
                    new Vector3f(0.0F, 0.0F, 0.0F),
                    rotation,
                    new Vector3f(initialScale, initialScale, 0.01F),
                    null
            ));

            level.addFreshEntity(display);
        }
        return display;
    }

    /**
     * Cập nhật kích thước và góc xoay cho từng tầng ma pháp trận
     */
    private static void updateDisplayTransformation(Display.ItemDisplay display, float scale, float angleDeg) {
        if (display != null && display.isAlive()) {
            Quaternionf rotation = new Quaternionf()
                    .rotateX((float) Math.toRadians(90.0F))
                    .rotateZ((float) Math.toRadians(angleDeg));

            ((DisplayAccessor) display).weapons$setTransformation(new Transformation(
                    new Vector3f(0.0F, 0.0F, 0.0F),
                    rotation,
                    new Vector3f(scale, scale, 0.01F),
                    null
            ));
        }
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
            if (elapsed > 75) {
                rotationSpeed = 12.0F; // Giai đoạn 3: Bùng nổ cực đại
            } else if (elapsed > 25) {
                rotationSpeed = 6.5F;  // Giai đoạn 2: Tụ năng lượng
            }
            s.currentAngleDegrees += rotationSpeed;

            // 1. Cập nhật chuyển động xoay & bung nở của 3 tầng ma pháp trận
            // Tầng 1 (Địa Trận): Bung nở 0 -> 12m trong 15 ticks đầu, xoay thuận kim đồng hồ
            float baseScale = Math.min(1.0F, elapsed / 15.0F) * 12.0F;
            updateDisplayTransformation(s.baseDisplay, baseScale, s.currentAngleDegrees);

            // Tầng 2 (Trung Trận): Xuất hiện từ tick 4..18 (Scale 9.5m), xoay NGƯỢC chiều kim đồng hồ
            float midScale = elapsed < 4 ? 0.01F : Math.min(1.0F, (elapsed - 4) / 14.0F) * 9.5F;
            updateDisplayTransformation(s.midDisplay, midScale, -s.currentAngleDegrees * 1.5F);

            // Tầng 3 (Thiên Trận): Xuất hiện từ tick 8..22 (Scale 7.5m), xoay cùng chiều siêu tốc
            float topScale = elapsed < 8 ? 0.01F : Math.min(1.0F, (elapsed - 8) / 14.0F) * 7.5F;
            updateDisplayTransformation(s.topDisplay, topScale, s.currentAngleDegrees * 2.2F);

            // 2. GIAI ĐOẠN 1 (0..25 ticks): 8 Thánh Trụ Ánh Sáng phóng thẳng từ đất lên trời nối 3 tầng
            double pillarRadius = 5.2D;
            if (elapsed <= 25) {
                double pillarHeight = Math.min(6.0D, (elapsed / 25.0D) * 6.0D);
                if (s.ticksRemaining % 2 == 0) {
                    for (int i = 0; i < 8; i++) {
                        double rad = Math.toRadians((i * 45.0D) + s.currentAngleDegrees * 0.5D);
                        double px = s.center.x + Math.cos(rad) * pillarRadius;
                        double pz = s.center.z + Math.sin(rad) * pillarRadius;

                        for (double y = s.center.y; y <= s.center.y + pillarHeight; y += 1.5D) {
                            s.level.sendParticles(ParticleTypes.END_ROD, px, y, pz, 1, 0.01D, 0.02D, 0.01D, 0.005D);
                        }
                    }
                }
            }

            // 3. GIAI ĐOẠN 2 (25..75 ticks): Lồng Giam Hoàn Tất & Linh Tử Hội Tụ Xoáy Ốc Nén Ép
            if (elapsed > 25 && elapsed <= 75) {
                // Duy trì 8 Thánh Trụ kết giới nối dọc 3 tầng
                if (s.ticksRemaining % 3 == 0) {
                    for (int i = 0; i < 8; i++) {
                        double rad = Math.toRadians((i * 45.0D) + s.currentAngleDegrees * 0.5D);
                        double px = s.center.x + Math.cos(rad) * pillarRadius;
                        double pz = s.center.z + Math.sin(rad) * pillarRadius;

                        s.level.sendParticles(ParticleTypes.END_ROD, px, s.center.y + 1.0D, pz, 1, 0.01D, 0.1D, 0.01D, 0.005D);
                        s.level.sendParticles(ParticleTypes.END_ROD, px, s.center.y + 3.0D, pz, 1, 0.01D, 0.1D, 0.01D, 0.005D);
                        s.level.sendParticles(ParticleTypes.END_ROD, px, s.center.y + 5.5D, pz, 1, 0.01D, 0.1D, 0.01D, 0.005D);

                        // Hạt linh tử ánh sáng từ 8 thánh trụ bắn xoáy ốc vào tâm giam cầm
                        Vec3 lockPos = new Vec3(s.center.x, s.center.y + 1.8D, s.center.z);
                        Vec3 toCenter = lockPos.subtract(new Vec3(px, s.center.y + 2.0D, pz)).normalize().scale(0.35D);
                        s.level.sendParticles(ParticleTypes.WAX_OFF, px, s.center.y + 2.0D, pz, 0, toCenter.x, 0.02D, toCenter.z, 0.22D);
                    }
                }

                // Vòng nén thánh quang co thắt dần xung quanh nạn nhân (từ bán kính 4.5m xuống 1.5m)
                double compressionProgress = (elapsed - 25) / 50.0D;
                double ringRadius = 4.5D - compressionProgress * 3.0D;
                if (s.ticksRemaining % 4 == 0) {
                    for (int a = 0; a < 360; a += 45) {
                        double aRad = Math.toRadians(a + s.currentAngleDegrees * 2.5D);
                        double rx = s.center.x + Math.cos(aRad) * ringRadius;
                        double rz = s.center.z + Math.sin(aRad) * ringRadius;
                        s.level.sendParticles(ParticleTypes.END_ROD, rx, s.center.y + 1.8D, rz, 1, 0, 0, 0, 0);
                    }
                }

                // Âm thanh thánh tích nén năng lượng theo tần số tăng dần
                if (elapsed % 10 == 0) {
                    float pitch = 0.8F + (float) compressionProgress * 1.2F;
                    s.level.playSound(null, s.center.x, s.center.y + 1.8D, s.center.z,
                            SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.8F, pitch);
                }
            }

            // 4. GIAI ĐOẠN 3 (75..90 ticks): Thiên Trận Tụ Quang Sấm Sét Giáng Phạt
            if (elapsed > 75 && elapsed < 90) {
                double topY = s.center.y + 6.0D;
                s.level.sendParticles(ParticleTypes.FLASH, s.center.x, topY, s.center.z, 2, 0.2D, 0.2D, 0.2D, 0);
                s.level.sendParticles(ParticleTypes.END_ROD, s.center.x, topY, s.center.z, 3, 0.5D, 0.5D, 0.5D, 0.08D);

                // Tia chớp định vị mục tiêu ở tâm
                s.level.sendParticles(ParticleTypes.ELECTRIC_SPARK, s.center.x, s.center.y + 1.8D, s.center.z, 5, 0.3D, 0.5D, 0.3D, 0.05D);

                if (elapsed == 80) {
                    s.level.playSound(null, s.center.x, s.center.y, s.center.z,
                            SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 1.5F, 1.8F);
                }
            }

            // KHÓA MỤC TIÊU TUYỆT ĐỐI MỖI TICK (0.05s) - KHÔNG THỂ THOÁT RA!
            lockAndAnchorVictimsEveryTick(s);

            // 5. KẾT THÚC: Cột Thiên Phạt Cực Quang giáng lâm & Phân Rã Toàn Bộ Linh Tử!
            if (s.ticksRemaining <= 0) {
                executeDisintegrationStrike(s.level, s.center, 6.0D, s.caster, s.powerRoll);
                s.cleanupDisplays();
                it.remove();
            }
        }
    }

    /**
     * Khóa mục tiêu tuyệt đối mỗi tick (0.05s) - Triệt tiêu di chuyển, nhảy, đẩy lùi, lơ lửng và chặn dịch chuyển
     */
    private static void lockAndAnchorVictimsEveryTick(ActiveSanctuary s) {
        double radius = 5.8D;
        AABB box = new AABB(
                s.center.x - radius, s.center.y - 2.0D, s.center.z - radius,
                s.center.x + radius, s.center.y + 8.5D, s.center.z + radius
        );

        // 1. Dò tìm và đăng ký các mục tiêu mới lọt vào vùng thánh giới
        List<LivingEntity> inArea = s.level.getEntitiesOfClass(LivingEntity.class, box, e -> e != s.caster && e.isAlive());
        for (LivingEntity e : inArea) {
            double distSq = e.position().distanceToSqr(s.center);
            if (distSq <= radius * radius) {
                s.trappedVictimUuids.add(e.getUUID());
            }
        }

        if (s.trappedVictimUuids.isEmpty()) return;

        Vec3 lockCenter = new Vec3(s.center.x, s.center.y + 1.8D, s.center.z);
        Iterator<UUID> uuidIt = s.trappedVictimUuids.iterator();
        while (uuidIt.hasNext()) {
            UUID uuid = uuidIt.next();
            Entity entity = s.level.getEntity(uuid);
            if (!(entity instanceof LivingEntity victim) || !victim.isAlive() || victim.isRemoved()) {
                continue;
            }

            Vec3 victimPos = victim.position();
            double horizontalDist = Math.sqrt(
                    Math.pow(victimPos.x - s.center.x, 2) + Math.pow(victimPos.z - s.center.z, 2)
            );
            double verticalDist = Math.abs(victimPos.y - (s.center.y + 1.8D));

            // Nếu xuất lực thấp (hụt lực), chỉ làm chậm nhẹ chứ không giam giữ đơ cứng hoàn toàn
            if (s.powerRoll.isLow()) {
                victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 2, false, false, true));
                Vec3 pullVec = lockCenter.subtract(victimPos);
                if (pullVec.length() > 0.5D) {
                    victim.setDeltaMovement(pullVec.normalize().scale(0.12D));
                    victim.hasImpulse = true;
                }
                continue;
            }

            // CHỐNG ĐÀO THOÁT BẰNG DỊCH CHUYỂN TỨC THỜI (Anti-Teleport Snap-Back)
            // Nếu mục tiêu đã bị đánh dấu phong ấn cố dịch chuyển hoặc bị văng ra xa -> Giật ngược về tâm lơ lửng!
            if (horizontalDist > 5.5D || verticalDist > 4.0D) {
                victim.teleportTo(lockCenter.x, lockCenter.y, lockCenter.z);
                victim.setDeltaMovement(0, 0, 0);
                s.level.sendParticles(ParticleTypes.REVERSE_PORTAL, victim.getX(), victim.getY() + 1.0D, victim.getZ(), 8, 0.2D, 0.3D, 0.2D, 0.05D);
            } else {
                // Lực hút chân không linh tử giữ mục tiêu lơ lửng ổn định ở tâm tầng 2
                Vec3 pullVec = lockCenter.subtract(victimPos);
                double pullDist = pullVec.length();
                if (pullDist > 0.15D) {
                    victim.setDeltaMovement(pullVec.normalize().scale(Math.min(pullDist * 0.3D, 0.35D)));
                } else {
                    victim.setDeltaMovement(0, 0, 0);
                }
            }

            victim.hasImpulse = true;
            victim.fallDistance = 0.0F;

            // Áp dụng các hiệu ứng khóa chuyển động và vô hiệu hóa năng lực
            victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 255, false, false, true));
            victim.addEffect(new MobEffectInstance(MobEffects.JUMP, 30, -255, false, false, true)); // Negative Jump Boost triệt tiêu nhảy
            victim.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 30, 255, false, false, true));
            victim.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 30, 255, false, false, true));
            victim.addEffect(new MobEffectInstance(MobEffects.GLOWING, 30, 0, false, false, true));

            // Nếu nạn nhân là Player: Khóa Ender Pearl & Chorus Fruit để ngăn chặn đào thoát
            if (victim instanceof ServerPlayer playerVictim) {
                playerVictim.getCooldowns().addCooldown(Items.ENDER_PEARL, 40);
                playerVictim.getCooldowns().addCooldown(Items.CHORUS_FRUIT, 40);
                playerVictim.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 30, 0, false, false, false));
            }
        }
    }

    /**
     * Cột Thiên Phạt Linh Tử Băng Hoại giáng xuống xóa sổ toàn bộ mục tiêu theo cấp độ xuất lực
     */
    private static void executeDisintegrationStrike(ServerLevel level, Vec3 center, double radius, ServerPlayer caster, SkillPowerRoll roll) {
        // 1. Âm thanh thiên phạt chấn động thế giới
        level.playSound(null, center.x, center.y, center.z,
                SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 3.5F, 0.85F);
        level.playSound(null, center.x, center.y, center.z,
                SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 3.0F, 1.1F);
        level.playSound(null, center.x, center.y, center.z,
                SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS, 3.5F, 0.5F);
        level.playSound(null, center.x, center.y, center.z,
                SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 2.5F, 1.4F);
        level.playSound(null, center.x, center.y, center.z,
                SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 2.5F, 1.5F);

        // 2. Cột ánh sáng cực quang linh tử giáng từ thiên đỉnh xuyên qua 3 tầng ma pháp trận
        for (double y = center.y; y <= center.y + 24.0D; y += 1.5D) {
            level.sendParticles(ParticleTypes.FLASH, center.x, y, center.z, 2, 0.2D, 0.2D, 0.2D, 0);
            level.sendParticles(ParticleTypes.END_ROD, center.x, y, center.z, 3, 0.4D, 0.2D, 0.4D, 0.05D);
        }

        // Vụ nổ ánh sáng linh tử & sóng xung kích vàng kim bao phủ toàn bộ thánh trận
        level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, center.x, center.y + 1.8D, center.z, 2, 0, 0, 0, 0);
        level.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, center.x, center.y + 1.8D, center.z, 50, 2.5D, 2.0D, 2.5D, 0.2D);
        level.sendParticles(ParticleTypes.WAX_OFF, center.x, center.y + 1.8D, center.z, 30, 2.0D, 1.5D, 2.0D, 0.1D);

        // 3. Tiêu diệt và phân rã linh tử vạn vật trong thánh trận
        AABB box = new AABB(
                center.x - radius, center.y - 2.0D, center.z - radius,
                center.x + radius, center.y + 9.0D, center.z + radius
        );

        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, box, e -> e != caster);
        List<String> victimNames = new ArrayList<>();
        int destroyedCount = 0;
        DamageSource dmgSource = (caster != null) ? level.damageSources().playerAttack(caster) : level.damageSources().genericKill();

        for (LivingEntity target : targets) {
            double distSq = target.position().distanceToSqr(center);
            if (distSq <= radius * radius) {
                victimNames.add("cá thể " + target.getDisplayName().getString());
                if (caster != null) {
                    com.minhphuc.weapons.content.tensura.TensuraEvents.handleMobDeathDrop(caster, target);
                }
                level.sendParticles(ParticleTypes.SOUL, target.getX(), target.getY() + 1.0D, target.getZ(), 5, 0.2D, 0.3D, 0.2D, 0.02D);

                if (roll != null && roll.isOverdrive()) {
                    // BẠO KÍCH CỰC HẠN (20%): Phân rã 100% linh tử, tất sát cả Boss
                    target.hurt(dmgSource, 100000.0F);
                    if (target.isAlive()) {
                        target.discard();
                    }
                } else if (roll != null && roll.isNormal()) {
                    // XUẤT LỰC CHUẨN (50%): 550 sát thương * multiplier, quái thường bốc hơi, Boss rút máu nặng
                    float damage = 550.0F * roll.multiplier;
                    target.hurt(dmgSource, damage);
                } else {
                    // ĐẦU RA THẤP (30%): 90 sát thương * multiplier, làm choáng và thiêu đốt
                    float damage = 90.0F * (roll != null ? roll.multiplier : 0.35F);
                    target.hurt(dmgSource, damage);
                    target.setRemainingFireTicks(100);
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
                killMessage = "Báo cáo: Cá thể " + victimNames.get(0).substring(7) + " đã bị ảnh hưởng bởi Linh Tử Băng Hoại! ⚡✨";
            } else if (destroyedCount > 1 && destroyedCount <= 3) {
                killMessage = "Báo cáo: " + String.join(", ", victimNames) + " đã bị phân rã bởi Linh Tử Băng Hoại! ⚡✨";
            } else if (destroyedCount > 3) {
                killMessage = "Báo cáo: " + victimNames.get(0) + " và " + (destroyedCount - 1) + " cá thể khác đã bị đánh trúng! ⚡✨";
            } else {
                killMessage = "Báo cáo: Không có cá thể nào trong phạm vi thánh trận";
            }
            caster.displayClientMessage(
                Component.literal("§e§l[TAM TRỌNG THÁNH GIỚI] §f" + killMessage),
                true
            );
        }
    }
}
