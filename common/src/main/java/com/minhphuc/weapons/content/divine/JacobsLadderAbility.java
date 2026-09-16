package com.minhphuc.weapons.content.divine;

import com.minhphuc.weapons.init.ModItems;
import com.minhphuc.weapons.mixin.DisplayAccessor;
import com.minhphuc.weapons.mixin.ItemDisplayAccessor;
import com.mojang.math.Transformation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemDisplayContext;
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

/**
 * Tuyệt kĩ: Tà Khứ Vũ Thê Tử (Jacob's Ladder - 邪去侮の梯子)
 * Kịch bản:
 * 1. Kích hoạt: 1 ma pháp trận duy nhất xuất hiện dưới đất và xoay tròn.
 * 2. Đột ngột biến mất hoàn toàn (pha lừa khiến đối thủ tưởng ma pháp xịt / không dùng được).
 * 3. Ngay sau đó, Cột Sáng Thiên Phạt hình vuông 4x4 chọc trời cực đại giáng thẳng từ mây xuống
 *    chạm chuẩn xác mặt phẳng mặt đất (không xuyên đất) và đồng tâm 100% với vị trí ma pháp trận.
 */
public class JacobsLadderAbility {

    public static class ActiveLadder {
        public final ServerLevel level;
        public final ServerPlayer caster;
        public final Vec3 center;
        public Display.ItemDisplay groundCircleDisplay;
        public final List<Display.ItemDisplay> beamDisplays = new ArrayList<>();
        public int ticksRemaining;
        public final int totalTicks;
        public float circleAngleDegrees;
        public boolean beamSpawned = false;

        public ActiveLadder(ServerLevel level, ServerPlayer caster, Vec3 center, int durationTicks) {
            this.level = level;
            this.caster = caster;
            this.center = center;
            this.ticksRemaining = durationTicks;
            this.totalTicks = durationTicks;
            this.circleAngleDegrees = 0.0F;
        }

        public void cleanupDisplays() {
            if (groundCircleDisplay != null && groundCircleDisplay.isAlive()) {
                groundCircleDisplay.discard();
                groundCircleDisplay = null;
            }
            for (Display.ItemDisplay d : beamDisplays) {
                if (d != null && d.isAlive()) {
                    d.discard();
                }
            }
            beamDisplays.clear();
        }
    }

    private static final List<ActiveLadder> ACTIVE_LADDERS = new ArrayList<>();

    /**
     * Kích hoạt tuyệt kĩ: Tà Khứ Vũ Thê Tử (Jacob's Ladder)
     */
    public static void cast(ServerLevel level, ServerPlayer player, ItemStack sword) {
        // 1. Dò tìm tọa độ mục tiêu phía trước trong tầm 26 blocks
        Vec3 eyePos = player.getEyePosition(1.0F);
        Vec3 lookVec = player.getLookAngle();
        Vec3 traceEnd = eyePos.add(lookVec.scale(26.0D));

        BlockHitResult hitResult = level.clip(new ClipContext(
                eyePos, traceEnd,
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player
        ));

        Vec3 targetCenter;
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            Direction hitFace = hitResult.getDirection();
            BlockPos bp = hitResult.getBlockPos();
            if (hitFace == Direction.UP) {
                targetCenter = new Vec3(bp.getX() + 0.5D, bp.getY() + 1.0D, bp.getZ() + 0.5D);
            } else {
                Vec3 hitLoc = hitResult.getLocation();
                BlockPos groundPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, BlockPos.containing(hitLoc));
                targetCenter = new Vec3(groundPos.getX() + 0.5D, groundPos.getY(), groundPos.getZ() + 0.5D);
            }
        } else {
            Vec3 forwardPos = eyePos.add(lookVec.scale(14.0D));
            BlockPos groundPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, BlockPos.containing(forwardPos));
            targetCenter = new Vec3(groundPos.getX() + 0.5D, groundPos.getY(), groundPos.getZ() + 0.5D);
        }

        // Tổng thời lượng 135 ticks (~6.75 giây)
        ActiveLadder ladder = new ActiveLadder(level, player, targetCenter, 135);

        // 2. TẠO 1 MA PHÁP TRẬN DUY NHẤT DƯỚI MẶT ĐẤT (Y + 0.03m để không z-fight với đất)
        ladder.groundCircleDisplay = createFlatMagicCircle(level, targetCenter, 0.1F, 0xFFEE55);

        ACTIVE_LADDERS.add(ladder);

        // 3. Âm thanh chuông thánh & mở màn
        level.playSound(null, targetCenter.x, targetCenter.y, targetCenter.z,
                SoundEvents.EVOKER_PREPARE_SUMMON, SoundSource.PLAYERS, 3.0F, 1.4F);
        level.playSound(null, targetCenter.x, targetCenter.y, targetCenter.z,
                SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 3.0F, 1.1F);

        player.displayClientMessage(
                Component.literal("§e§l[TÀ KHỨ VŨ THÊ TỬ] §f\"Mọi điều xấu xa, tội lỗi và sự khốn khổ... Hãy dẫn chúng về với ánh sáng!\" 🪽✨"),
                true
        );

        player.getCooldowns().addCooldown(sword.getItem(), 180); // 9 giây hồi chiêu
    }

    /**
     * Cập nhật Cột Sáng Jacob mỗi tick theo kịch bản điện ảnh
     */
    public static void tickLadders(ServerLevel serverLevel) {
        if (ACTIVE_LADDERS.isEmpty()) return;

        Iterator<ActiveLadder> it = ACTIVE_LADDERS.iterator();
        while (it.hasNext()) {
            ActiveLadder l = it.next();
            if (l.level != serverLevel) continue;

            l.ticksRemaining--;
            int elapsed = l.totalTicks - l.ticksRemaining;

            // =========================================================================
            // GIAI ĐOẠN 1: MA PHÁP TRẬN XUẤT HIỆN DƯỚI ĐẤT & XOAY XOAY (Tick 0..24)
            // =========================================================================
            if (elapsed < 25) {
                l.circleAngleDegrees += 6.0F;

                if (l.groundCircleDisplay != null && l.groundCircleDisplay.isAlive()) {
                    // Nở to mượt mà trong 10 ticks đầu từ 0 -> 6.0m (bao trọn cột 4x4)
                    float circleScale = Math.min(1.0F, elapsed / 10.0F) * 6.0F;
                    Quaternionf rot = new Quaternionf()
                            .rotateX((float) Math.toRadians(90.0F))
                            .rotateZ((float) Math.toRadians(l.circleAngleDegrees));

                    ((DisplayAccessor) l.groundCircleDisplay).weapons$setTransformation(new Transformation(
                            new Vector3f(0.0F, 0.0F, 0.0F),
                            rot,
                            new Vector3f(circleScale, circleScale, 0.01F),
                            null
                    ));
                }

                // Hạt thánh quang xoay nhẹ quanh ma pháp trận trên mặt đất
                if (elapsed % 3 == 0) {
                    double rad = Math.toRadians((elapsed * 15.0D) % 360.0D);
                    double rx = l.center.x + Math.cos(rad) * 2.8D;
                    double rz = l.center.z + Math.sin(rad) * 2.8D;
                    l.level.sendParticles(ParticleTypes.WAX_OFF, rx, l.center.y + 0.08D, rz, 1, 0, 0.01D, 0, 0.02D);
                }
            }

            // =========================================================================
            // GIAI ĐOẠN 2: MA PHÁP TRẬN BIẾN MẤT (PHA LỪA ĐỐI THỦ TƯỞNG PHÉP XỊT) (Tick 25..37)
            // =========================================================================
            if (elapsed == 25) {
                // Ma pháp trận đột ngột biến mất hoàn toàn!
                if (l.groundCircleDisplay != null && l.groundCircleDisplay.isAlive()) {
                    l.groundCircleDisplay.discard();
                    l.groundCircleDisplay = null;
                }

                // Âm thanh phép thuật "tắt ngấm" giả vờ hỏng
                l.level.playSound(null, l.center.x, l.center.y, l.center.z,
                        SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 2.0F, 1.8F);
                l.level.playSound(null, l.center.x, l.center.y, l.center.z,
                        SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.2F, 1.6F);

                // Khói tan mờ nhẹ
                l.level.sendParticles(ParticleTypes.POOF, l.center.x, l.center.y + 0.1D, l.center.z, 4, 0.3D, 0.05D, 0.3D, 0.01D);
                l.level.sendParticles(ParticleTypes.SMOKE, l.center.x, l.center.y + 0.1D, l.center.z, 3, 0.2D, 0.05D, 0.2D, 0.01D);
            }

            // Giai đoạn tĩnh lặng (Tick 26..37): Không có gì xuất hiện, đối thủ mất cảnh giác!

            // =========================================================================
            // GIAI ĐOẠN 3: CỘT SÁNG 4x4 TỪ TRÊN TRỜI CHIẾU XUỐNG MẶT ĐẤT! (Tick 38)
            // =========================================================================
            if (elapsed >= 38 && !l.beamSpawned) {
                l.beamSpawned = true;

                // 1. Cột sáng vỏ ngoài hình vuông 4x4, tiếp đất ngay tại l.center.y, cao 50m lên trời
                Display.ItemDisplay outerBeam = createBeamDisplay(l.level, l.center, 4.0F, 50.0F, 4.0F, 0xFFFFFF);
                if (outerBeam != null) l.beamDisplays.add(outerBeam);

                // 2. Lõi sáng trung tâm 2.6x2.6 trắng chói lòa
                Display.ItemDisplay innerCore = createBeamDisplay(l.level, l.center, 2.6F, 50.0F, 2.6F, 0xFFFFFF);
                if (innerCore != null) l.beamDisplays.add(innerCore);

                // Âm thanh thiên phạt chấn động thế giới
                l.level.playSound(null, l.center.x, l.center.y, l.center.z,
                        SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 4.0F, 1.0F);
                l.level.playSound(null, l.center.x, l.center.y, l.center.z,
                        SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 4.0F, 1.4F);
                l.level.playSound(null, l.center.x, l.center.y, l.center.z,
                        SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 3.0F, 1.3F);
                l.level.playSound(null, l.center.x, l.center.y, l.center.z,
                        SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS, 3.5F, 0.6F);

                // Vệt sáng chớp từ trên trời giáng thẳng xuống đất
                for (double y = l.center.y; y <= l.center.y + 50.0D; y += 2.0D) {
                    l.level.sendParticles(ParticleTypes.FLASH, l.center.x, y, l.center.z, 1, 0.4D, 0.4D, 0.4D, 0);
                    l.level.sendParticles(ParticleTypes.END_ROD, l.center.x, y, l.center.z, 2, 0.8D, 0.2D, 0.8D, 0.05D);
                }
                l.level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, l.center.x, l.center.y + 1.0D, l.center.z, 2, 0, 0, 0, 0);
            }

            // Kích thước ranh giới cột vuông 4x4
            double halfSize = 2.0D;
            double minX = l.center.x - halfSize;
            double maxX = l.center.x + halfSize;
            double minZ = l.center.z - halfSize;
            double maxZ = l.center.z + halfSize;
            double groundY = l.center.y;
            double topY = l.center.y + 50.0D;

            if (l.beamSpawned) {
                // 1. VIỀN LỬA THÁNH CHẠY QUANH CHU VI HÌNH VUÔNG 4x4 TRÊN MẶT ĐẤT
                if (elapsed % 2 == 0) {
                    for (int p = 0; p < 8; p++) {
                        double t = ((elapsed * 0.6D + p * 2.0D) % 16.0D);
                        double px, pz;
                        if (t < 4.0D) {
                            px = minX + t;
                            pz = minZ;
                        } else if (t < 8.0D) {
                            px = maxX;
                            pz = minZ + (t - 4.0D);
                        } else if (t < 12.0D) {
                            px = maxX - (t - 8.0D);
                            pz = maxZ;
                        } else {
                            px = minX;
                            pz = maxZ - (t - 12.0D);
                        }
                        l.level.sendParticles(ParticleTypes.FLAME, px, groundY + 0.06D, pz, 1, 0.02D, 0.03D, 0.02D, 0.01D);
                        l.level.sendParticles(ParticleTypes.WAX_OFF, px, groundY + 0.08D, pz, 1, 0.01D, 0.02D, 0.01D, 0.02D);
                    }
                }

                // 2. CÁC NGÔI SAO CHỮ THẬP 4 CÁNH THÁNH QUANG LƠ LỬNG
                if (elapsed % 3 == 0) {
                    for (int s = 0; s < 3; s++) {
                        double starAngle = Math.toRadians((elapsed * 15.0D + s * 120.0D) % 360.0D);
                        double starDist = 2.3D + (s * 0.5D);
                        double starX = l.center.x + Math.cos(starAngle) * starDist;
                        double starZ = l.center.z + Math.sin(starAngle) * starDist;
                        double starY = groundY + 1.5D + ((s * 4.0D + elapsed * 0.5D) % 18.0D);

                        double d = 0.35D;
                        l.level.sendParticles(ParticleTypes.END_ROD, starX, starY, starZ, 1, 0, 0, 0, 0);
                        l.level.sendParticles(ParticleTypes.END_ROD, starX + d, starY, starZ, 1, 0, 0, 0, 0);
                        l.level.sendParticles(ParticleTypes.END_ROD, starX - d, starY, starZ, 1, 0, 0, 0, 0);
                        l.level.sendParticles(ParticleTypes.END_ROD, starX, starY + d, starZ, 1, 0, 0, 0, 0);
                        l.level.sendParticles(ParticleTypes.END_ROD, starX, starY - d, starZ, 1, 0, 0, 0, 0);
                    }
                }

                // Hộp va chạm chính xác 4x4 blocks từ mặt đất lên trời (không lấy dưới đất)
                AABB pillarBox = new AABB(minX, groundY, minZ, maxX, topY, maxZ);

                // 3. TRIỆT TIÊU ĐẠN ĐẠO (Projectile Erasure)
                List<Projectile> projectiles = l.level.getEntitiesOfClass(Projectile.class, pillarBox);
                for (Projectile p : projectiles) {
                    l.level.sendParticles(ParticleTypes.FLASH, p.getX(), p.getY(), p.getZ(), 1, 0, 0, 0, 0);
                    p.discard();
                }

                // 4. LỰC HÚT THÁNH QUANG: Hút nhẹ mục tiêu lân cận bán kính 6m vào giữa cột sáng
                AABB pullZone = new AABB(l.center.x - 6.0D, groundY - 1.0D, l.center.z - 6.0D,
                        l.center.x + 6.0D, groundY + 8.0D, l.center.z + 6.0D);
                List<LivingEntity> pullables = l.level.getEntitiesOfClass(LivingEntity.class, pullZone, e -> e != l.caster && e.isAlive());
                for (LivingEntity e : pullables) {
                    double dx = l.center.x - e.getX();
                    double dz = l.center.z - e.getZ();
                    double dist = Math.sqrt(dx * dx + dz * dz);
                    if (dist > halfSize && dist <= 6.0D) {
                        Vec3 pullMotion = new Vec3(dx, 0, dz).normalize().scale(0.22D);
                        e.setDeltaMovement(pullMotion);
                    }
                }

                // 5. THANH TẨY TÀ THUẬT & SÁT THƯƠNG THIÊU RỤI (Mỗi 5 ticks = 0.25s)
                if (elapsed % 5 == 0) {
                    List<LivingEntity> victims = l.level.getEntitiesOfClass(LivingEntity.class, pillarBox, e -> e != l.caster && e.isAlive());
                    DamageSource dmgSource = (l.caster != null) ? l.level.damageSources().playerAttack(l.caster) : l.level.damageSources().magic();

                    for (LivingEntity victim : victims) {
                        victim.removeEffect(MobEffects.MOVEMENT_SPEED);
                        victim.removeEffect(MobEffects.DAMAGE_BOOST);
                        victim.removeEffect(MobEffects.REGENERATION);
                        victim.removeEffect(MobEffects.DAMAGE_RESISTANCE);
                        victim.removeEffect(MobEffects.FIRE_RESISTANCE);
                        victim.removeEffect(MobEffects.INVISIBILITY);
                        victim.removeEffect(MobEffects.ABSORPTION);
                        victim.removeEffect(MobEffects.HEALTH_BOOST);

                        victim.setRemainingFireTicks(160);

                        victim.setDeltaMovement(0, 0, 0);
                        victim.hasImpulse = true;
                        victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 255, false, false, true));
                        victim.addEffect(new MobEffectInstance(MobEffects.JUMP, 20, -255, false, false, true));
                        victim.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 255, false, false, true));
                        victim.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20, 0, false, false, true));

                        boolean isUndeadOrCursed = victim.isInvertedHealAndHarm()
                                || victim instanceof net.minecraft.world.entity.boss.wither.WitherBoss
                                || victim instanceof net.minecraft.world.entity.monster.warden.Warden
                                || victim instanceof net.minecraft.world.entity.monster.Phantom
                                || victim.getMaxHealth() >= 100.0F;

                        float damage = isUndeadOrCursed ? 150.0F : 50.0F;
                        victim.hurt(dmgSource, damage);

                        l.level.sendParticles(ParticleTypes.SOUL, victim.getX(), victim.getY() + 1.0D, victim.getZ(), 3, 0.1D, 0.2D, 0.1D, 0.02D);
                    }
                }

                // Âm thanh thánh tích duy trì
                if (elapsed % 20 == 0) {
                    l.level.playSound(null, l.center.x, groundY + 2.0D, l.center.z,
                            SoundEvents.BEACON_AMBIENT, SoundSource.PLAYERS, 2.5F, 1.2F);
                    l.level.playSound(null, l.center.x, groundY + 2.0D, l.center.z,
                            SoundEvents.FIRE_AMBIENT, SoundSource.PLAYERS, 2.0F, 1.0F);
                }
            }

            // =========================================================================
            // GIAI ĐOẠN 4: BỘC PHÁT "XUẤT LỰC TỐI ĐA" (Tick 105)
            // =========================================================================
            if (elapsed == 105) {
                if (l.caster != null) {
                    l.caster.displayClientMessage(
                            Component.literal("§6§l[TÀ KHỨ VŨ THÊ TỬ] §c§lXUẤT LỰC TỐI ĐA (MAXIMUM OUTPUT)!! ⚡💥"),
                            true
                    );
                }
                l.level.playSound(null, l.center.x, groundY + 2.0D, l.center.z,
                        SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 3.5F, 1.2F);
                l.level.playSound(null, l.center.x, groundY + 2.0D, l.center.z,
                        SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 3.0F, 1.5F);
            }

            // =========================================================================
            // GIAI ĐOẠN 5: KẾT THÚC: Nổ tung sóng xung kích thánh quang & Dọn dẹp (Tick >= 125)
            // =========================================================================
            if (elapsed >= 125) {
                AABB shockwaveBox = new AABB(l.center.x - 4.5D, groundY, l.center.z - 4.5D,
                        l.center.x + 4.5D, groundY + 12.0D, l.center.z + 4.5D);

                l.level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, l.center.x, groundY + 1.5D, l.center.z, 2, 0, 0, 0, 0);
                l.level.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, l.center.x, groundY + 2.0D, l.center.z, 60, 3.0D, 2.0D, 3.0D, 0.25D);
                l.level.sendParticles(ParticleTypes.FLASH, l.center.x, groundY + 2.0D, l.center.z, 3, 0.5D, 0.5D, 0.5D, 0);

                List<LivingEntity> finalTargets = l.level.getEntitiesOfClass(LivingEntity.class, shockwaveBox, e -> e != l.caster && e.isAlive());
                DamageSource finalDmgSource = (l.caster != null) ? l.level.damageSources().playerAttack(l.caster) : l.level.damageSources().genericKill();

                for (LivingEntity target : finalTargets) {
                    target.hurt(finalDmgSource, 2000.0F);
                    if (target.isAlive() && target.getMaxHealth() >= 100.0F) {
                        target.discard();
                    }
                }

                l.cleanupDisplays();
                it.remove();
            }
        }
    }

    /**
     * Tạo ItemDisplay cho Cột Sáng 3D bắt đầu từ mặt đất l.center.y trở lên,
     * tâm X=0, Z=0 hoàn toàn đồng tâm tuyệt đối với ma pháp trận.
     */
    private static Display.ItemDisplay createBeamDisplay(ServerLevel level, Vec3 center,
                                                         float scaleX, float scaleY, float scaleZ,
                                                         int glowColor) {
        Display.ItemDisplay display = EntityType.ITEM_DISPLAY.create(level);
        if (display != null) {
            display.moveTo(center.x, center.y, center.z, 0.0F, 0.0F);
            ItemDisplayAccessor itemDisplayAcc = (ItemDisplayAccessor) display;
            DisplayAccessor displayAcc = (DisplayAccessor) display;

            itemDisplayAcc.weapons$setItemStack(new ItemStack(ModItems.JACOB_LIGHT_PILLAR.get()));
            itemDisplayAcc.weapons$setItemTransform(ItemDisplayContext.FIXED);
            displayAcc.weapons$setBillboardConstraints(Display.BillboardConstraints.FIXED);
            display.setGlowingTag(true);
            displayAcc.weapons$setGlowColorOverride(glowColor);
            displayAcc.weapons$setViewRange(10.0F); // Hiển thị xa hơn 160 blocks

            // Model jacob_light_pillar [0,0,0] -> [16,16,16].
            // ItemRenderer translates (-0.5, -0.5, -0.5), centering X and Z at 0.0.
            // Y ranges from -scaleY/2 to +scaleY/2.
            // Setting translation Y = scaleY / 2.0F shifts the base to Y=0.0 (ground surface).
            displayAcc.weapons$setTransformation(new Transformation(
                    new Vector3f(0.0F, scaleY / 2.0F, 0.0F),
                    new Quaternionf(),
                    new Vector3f(scaleX, scaleY, scaleZ),
                    null
            ));

            level.addFreshEntity(display);
        }
        return display;
    }

    /**
     * Tạo 1 Ma Pháp Trận duy nhất dưới mặt đất, xoay theo góc Z
     */
    private static Display.ItemDisplay createFlatMagicCircle(ServerLevel level, Vec3 center, float initialScale, int glowColor) {
        Display.ItemDisplay display = EntityType.ITEM_DISPLAY.create(level);
        if (display != null) {
            display.moveTo(center.x, center.y + 0.03D, center.z, 0.0F, 0.0F);
            ItemDisplayAccessor itemDisplayAcc = (ItemDisplayAccessor) display;
            DisplayAccessor displayAcc = (DisplayAccessor) display;

            itemDisplayAcc.weapons$setItemStack(new ItemStack(ModItems.DISINTEGRATION_MAGIC_CIRCLE.get()));
            itemDisplayAcc.weapons$setItemTransform(ItemDisplayContext.FIXED);
            displayAcc.weapons$setBillboardConstraints(Display.BillboardConstraints.FIXED);
            display.setGlowingTag(true);
            displayAcc.weapons$setGlowColorOverride(glowColor);
            displayAcc.weapons$setViewRange(10.0F);

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
}
