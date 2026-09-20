package com.minhphuc.weapons.content.tensura;

import com.minhphuc.weapons.entity.ModEntities;
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
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Nghi Lễ Giáng Thế 3D Chước Nhiệt Long Velgrynd (Velgrynd Summon Ritual):
 * - Giai đoạn 1: Khởi động Ma Trận Hỏa Diễm xoay phóng đại dưới chân (2m -> 12m), sấm sét đánh 8 hướng.
 * - Giai đoạn 2: Cột Sáng Chọc Trời 50m + Hư Ảnh Rồng 3D uốn lượn 3 vòng từ mây lao xuống đất với tiếng gầm rúng động.
 * - Giai đoạn 3: Đại bộc phá Siêu Tân Tinh và Velgrynd thăng hoa giáng thế với lời thoại kiêu hãnh!
 */
public class VelgryndSummonRitual {

    public static class ActiveRitual {
        public final ServerLevel level;
        public final ServerPlayer caster;
        public final Vec3 center;
        public int tick = 0;
        public final int maxTicks = 110;
        public Display.ItemDisplay magicCircle;

        public ActiveRitual(ServerLevel level, ServerPlayer caster, Vec3 center) {
            this.level = level;
            this.caster = caster;
            this.center = center;
        }

        public void cleanup() {
            if (magicCircle != null && magicCircle.isAlive()) {
                magicCircle.discard();
            }
        }
    }

    private static final List<ActiveRitual> ACTIVE_RITUALS = new ArrayList<>();

    public static void start(ServerLevel level, ServerPlayer caster, Vec3 center) {
        ActiveRitual ritual = new ActiveRitual(level, caster, center);

        // Tạo ma trận hỏa diễm ItemDisplay dưới chân
        Display.ItemDisplay circle = EntityType.ITEM_DISPLAY.create(level);
        if (circle != null) {
            circle.moveTo(center.x, center.y + 0.05D, center.z, 0.0F, 0.0F);
            ItemDisplayAccessor itemAcc = (ItemDisplayAccessor) circle;
            DisplayAccessor dispAcc = (DisplayAccessor) circle;

            itemAcc.weapons$setItemStack(new ItemStack(ModItems.MAGIC_CIRCLE_ROUGE.get()));
            itemAcc.weapons$setItemTransform(ItemDisplayContext.FIXED);
            dispAcc.weapons$setBillboardConstraints(Display.BillboardConstraints.FIXED);
            circle.setGlowingTag(true);
            dispAcc.weapons$setGlowColorOverride(0xFF2200);
            dispAcc.weapons$setViewRange(10.0F);

            Quaternionf rot = new Quaternionf().rotateX((float) Math.toRadians(90.0F));
            dispAcc.weapons$setTransformation(new Transformation(
                    new Vector3f(0.0F, 0.0F, 0.0F),
                    rot,
                    new Vector3f(2.0F, 2.0F, 0.01F),
                    null
            ));

            level.addFreshEntity(circle);
            ritual.magicCircle = circle;
        }

        ACTIVE_RITUALS.add(ritual);

        level.playSound(null, center.x, center.y, center.z,
                SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 4.0F, 0.75F);
        level.playSound(null, center.x, center.y, center.z,
                SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.PLAYERS, 3.5F, 1.2F);

        for (ServerPlayer p : level.players()) {
            if (p.distanceToSqr(center) <= 60.0D * 60.0D) {
                p.displayClientMessage(
                        Component.literal("§c§l✦ NGHI LỄ GIÁNG THẾ: §6§lCHƯỚC NHIỆT LONG VELGRYND ĐANG BẮT ĐẦU! ✦"),
                        false
                );
            }
        }
    }

    public static void tickRituals(ServerLevel level) {
        if (ACTIVE_RITUALS.isEmpty()) return;

        Iterator<ActiveRitual> it = ACTIVE_RITUALS.iterator();
        while (it.hasNext()) {
            ActiveRitual r = it.next();
            if (r.level != level) continue;

            r.tick++;
            int t = r.tick;
            Vec3 c = r.center;

            // =========================================================
            // GIAI ĐOẠN 1 (0 -> 35 ticks): MỞ RỘNG MA TRẬN & SẤM SÉT 8 HƯỚNG
            // =========================================================
            if (t <= 35) {
                float scale = 2.0F + (t / 35.0F) * 10.0F; // Phóng đại từ 2m lên 12m
                float angleDeg = t * 9.5F; // Xoay vòng ma trận

                if (r.magicCircle != null && r.magicCircle.isAlive()) {
                    DisplayAccessor dispAcc = (DisplayAccessor) r.magicCircle;
                    Quaternionf rot = new Quaternionf()
                            .rotateX((float) Math.toRadians(90.0F))
                            .rotateZ((float) Math.toRadians(angleDeg));
                    dispAcc.weapons$setTransformation(new Transformation(
                            new Vector3f(0.0F, 0.0F, 0.0F),
                            rot,
                            new Vector3f(scale, scale, 0.01F),
                            null
                    ));
                }

                // Hạt lửa tụ vào tâm
                for (int i = 0; i < 8; i++) {
                    double ang = level.random.nextDouble() * Math.PI * 2.0D;
                    double dist = 2.0D + level.random.nextDouble() * scale * 0.5D;
                    double px = c.x + Math.cos(ang) * dist;
                    double pz = c.z + Math.sin(ang) * dist;
                    level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, px, c.y + 0.1D, pz, 1, 0, 0.05D, 0, 0.02D);
                    level.sendParticles(ParticleTypes.FLAME, px, c.y + 0.1D, pz, 1, 0, 0.04D, 0, 0.02D);
                }

                if (t % 7 == 0) {
                    double ang = (t / 7) * (Math.PI * 2.0D / 5.0D);
                    double lx = c.x + Math.cos(ang) * (scale * 0.5D);
                    double lz = c.z + Math.sin(ang) * (scale * 0.5D);
                    level.playSound(null, lx, c.y, lz, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 3.0F, 1.4F);
                    level.sendParticles(ParticleTypes.FLASH, lx, c.y + 0.5D, lz, 1, 0, 0, 0, 0);
                    level.sendParticles(ParticleTypes.LAVA, lx, c.y + 0.5D, lz, 6, 0.3D, 0.5D, 0.3D, 0.1D);
                }
            }

            // =========================================================
            // GIAI ĐOẠN 2 (36 -> 80 ticks): CỘT SÁNG CHỌC TRỜI 50M & HƯ ẢNH RỒNG 3D
            // =========================================================
            else if (t <= 80) {
                float angleDeg = t * 15.0F; // Xoay cực nhanh
                if (r.magicCircle != null && r.magicCircle.isAlive()) {
                    DisplayAccessor dispAcc = (DisplayAccessor) r.magicCircle;
                    Quaternionf rot = new Quaternionf()
                            .rotateX((float) Math.toRadians(90.0F))
                            .rotateZ((float) Math.toRadians(angleDeg));
                    dispAcc.weapons$setTransformation(new Transformation(
                            new Vector3f(0.0F, 0.0F, 0.0F),
                            rot,
                            new Vector3f(12.0F, 12.0F, 0.01F),
                            null
                    ));
                }

                // 1. Cột Sáng Chọc Trời 50m (Celestial Pillar)
                for (double y = 0; y <= 50.0D; y += 2.0D) {
                    level.sendParticles(ParticleTypes.END_ROD, c.x, c.y + y, c.z, 2, 0.8D, 0.3D, 0.8D, 0.05D);
                    level.sendParticles(ParticleTypes.DRAGON_BREATH, c.x, c.y + y, c.z, 3, 1.2D, 0.3D, 1.2D, 0.03D);
                    level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, c.x, c.y + y, c.z, 2, 1.0D, 0.3D, 1.0D, 0.02D);
                    if (((int) y) % 8 == 0) {
                        level.sendParticles(ParticleTypes.FLASH, c.x, c.y + y, c.z, 1, 0, 0, 0, 0);
                    }
                }

                // 2. RỒNG 3D HƯ ẢNH (Spectral 3D Dragon Phantom)
                // Quỹ đạo xoắn ốc hạ độ cao từ tầng mây (y+45) xoay 3.5 vòng quanh cột sáng lao xuống đất
                float progress = (t - 35) / 45.0F; // 0.0 -> 1.0
                double dragonY = c.y + 45.0D - (progress * 44.0D);
                double spiralAngle = progress * Math.PI * 7.0D;
                double spiralRadius = 7.5D - (progress * 4.5D);

                double headX = c.x + Math.cos(spiralAngle) * spiralRadius;
                double headZ = c.z + Math.sin(spiralAngle) * spiralRadius;

                // Đầu Rồng phát quang rực rỡ
                level.sendParticles(ParticleTypes.EXPLOSION, headX, dragonY, headZ, 2, 0.4D, 0.4D, 0.4D, 0.02D);
                level.sendParticles(ParticleTypes.FLASH, headX, dragonY, headZ, 2, 0.2D, 0.2D, 0.2D, 0);
                level.sendParticles(ParticleTypes.LAVA, headX, dragonY, headZ, 5, 0.4D, 0.4D, 0.4D, 0.1D);

                // Thân Rồng uốn lượn hình sin phía sau đầu (8 đốt thân rồng)
                for (int seg = 1; seg <= 8; seg++) {
                    double segOffset = seg * 0.12D;
                    double segProg = Math.max(0.0D, progress - segOffset);
                    double segY = c.y + 45.0D - (segProg * 44.0D);
                    double segAngle = segProg * Math.PI * 7.0D;
                    double segRad = 7.5D - (segProg * 4.5D);

                    double segX = c.x + Math.cos(segAngle) * segRad;
                    double segZ = c.z + Math.sin(segAngle) * segRad;

                    level.sendParticles(ParticleTypes.DRAGON_BREATH, segX, segY, segZ, 4, 0.5D, 0.5D, 0.5D, 0.04D);
                    level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, segX, segY, segZ, 3, 0.4D, 0.4D, 0.4D, 0.03D);
                    level.sendParticles(ParticleTypes.FLAME, segX, segY, segZ, 2, 0.3D, 0.3D, 0.3D, 0.02D);
                }

                if (t == 45) {
                    level.playSound(null, c.x, c.y + 30.0D, c.z, SoundEvents.ENDER_DRAGON_GROWL, SoundSource.HOSTILE, 6.0F, 0.9F);
                }
                if (t == 65) {
                    level.playSound(null, c.x, c.y + 10.0D, c.z, SoundEvents.WARDEN_ROAR, SoundSource.HOSTILE, 5.0F, 0.8F);
                    level.playSound(null, c.x, c.y, c.z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 5.0F, 0.85F);
                }
            }

            // =========================================================
            // GIAI ĐOẠN 3 (81 -> 110 ticks): ĐẠI SIÊU TÂN TINH & VELGRYND GIÁNG THẾ!
            // =========================================================
            else {
                if (t == 81) {
                    // Đại vụ nổ chấn động khi đầu rồng chạm đất
                    level.playSound(null, c.x, c.y, c.z, SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 7.0F, 0.7F);
                    level.playSound(null, c.x, c.y, c.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 6.0F, 0.8F);
                    level.playSound(null, c.x, c.y, c.z, SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.PLAYERS, 4.0F, 0.8F);

                    level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, c.x, c.y + 1.5D, c.z, 8, 1.5D, 1.5D, 1.5D, 0.1D);
                    level.sendParticles(ParticleTypes.FLASH, c.x, c.y + 1.5D, c.z, 6, 1.0D, 1.0D, 1.0D, 0);
                    level.sendParticles(ParticleTypes.SONIC_BOOM, c.x, c.y + 1.5D, c.z, 3, 0.5D, 0.5D, 0.5D, 0);

                    // Triệu hồi Velgrynd Entity tại tâm
                    VelgryndEntity velgrynd = new VelgryndEntity(ModEntities.VELGRYND.get(), level);
                    velgrynd.moveTo(c.x, c.y, c.z, r.caster != null ? r.caster.getYRot() + 180.0F : 0.0F, 0.0F);
                    level.addFreshEntity(velgrynd);

                    velgrynd.broadcastDialogue("Lũ giun dế hạ đẳng... Các ngươi nghĩ ai cho phép các ngươi ngẩng đầu nhìn thẳng vào Chước Nhiệt Long Velgrynd ta?!");
                }

                // Dư chấn tàn lửa sau khi giáng thế
                if (t % 3 == 0) {
                    level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, c.x, c.y + 1.0D, c.z, 4, 1.5D, 0.5D, 1.5D, 0.02D);
                }

                if (t >= r.maxTicks) {
                    r.cleanup();
                    it.remove();
                }
            }
        }
    }
}
