package com.minhphuc.weapons.content.darkgathering;

import com.minhphuc.weapons.data.EntityDataHelper;
import com.minhphuc.weapons.init.ModItems;
import com.minhphuc.weapons.mixin.DisplayAccessor;
import com.minhphuc.weapons.mixin.ItemDisplayAccessor;
import com.mojang.math.Transformation;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.*;

/**
 * Kỹ Năng Thần Cấp: Lớp Phòng Ngự Lục Nhậm Thần Khóa - Trận Đồ Cưỡng Chế Tai Ương
 * (Liu Ren Shen Suo - Calamity Suppression Barrier / 六壬神課)
 * Sức mạnh phòng ngự tối thượng của Thái Tuế Tinh Quân trong anime Dark Gathering:
 * - Trận đồ Lục Nhậm 8.5m dưới chân & 12 Thức Thần Thần Tướng quỳ chầu bao bọc 360 độ.
 * - Thời gian duy trì 2 phút (120 giây / 2400 ticks), CÓ THỂ BẬT / TẮT CHỦ ĐỘNG.
 * - Khóa di chuyển tại tâm trận đồ (Rooted).
 * - BẤT TỬ TUYỆT ĐỐI 100%: Miễn nhiễm mọi sát thương, kể cả Sonic Boom của Warden!
 * - Tường kết giới cưỡng chế đẩy bật mọi quái vật ra ngoài.
 */
public class LiuRenBarrierAbility {

    public static class ActiveLiuRenBarrier {
        public final ServerLevel level;
        public final ServerPlayer caster;
        public final Vec3 centerPos;
        public int ticksRemaining = 2400; // 2 phút duy trì

        public Display.ItemDisplay groundArray = null;
        public final List<Display.ItemDisplay> shikigamis = new ArrayList<>();

        public ActiveLiuRenBarrier(ServerLevel level, ServerPlayer caster, Vec3 centerPos) {
            this.level = level;
            this.caster = caster;
            this.centerPos = centerPos;
        }

        public void cleanup() {
            if (groundArray != null && groundArray.isAlive()) {
                groundArray.discard();
                groundArray = null;
            }
            for (Display.ItemDisplay s : shikigamis) {
                if (s != null && s.isAlive()) {
                    s.discard();
                }
            }
            shikigamis.clear();
        }
    }

    public static final Map<UUID, ActiveLiuRenBarrier> ACTIVE_BARRIERS = new HashMap<>();

    private static final DustParticleOptions ELECTRIC_CYAN_DUST = new DustParticleOptions(new Vector3f(0.0F, 0.95F, 1.0F), 1.8F);

    /**
     * Kiểm tra người chơi có đang được bảo vệ bởi Trận Đồ Lục Nhậm không
     */
    public static boolean isBarrierActive(Player player) {
        if (player == null) return false;
        return ACTIVE_BARRIERS.containsKey(player.getUUID());
    }

    /**
     * Kích hoạt hoặc Thu hồi (Bật / Tắt chủ động) Lục Nhậm Thần Khóa
     */
    public static boolean toggleBarrier(ServerLevel level, ServerPlayer player) {
        // Nếu đang bật -> Thu hồi tắt trận pháp
        if (ACTIVE_BARRIERS.containsKey(player.getUUID())) {
            ActiveLiuRenBarrier barrier = ACTIVE_BARRIERS.remove(player.getUUID());
            if (barrier != null) {
                barrier.cleanup();
            }

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 2.5F, 1.2F);
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 2.0F, 0.8F);

            player.displayClientMessage(
                Component.literal("§b§l[TRẬN ĐỒ CƯỠNG CHẾ TAI ƯƠNG] §fĐã thu hồi trận đồ. Bạn có thể di chuyển lại tự do!"),
                true
            );
            return true;
        }

        // Kiểm tra điều kiện Chân Ma Vương
        boolean isTrueDemonLord = EntityDataHelper.getCustomData(player).getBoolean("TensuraTrueDemonLord");
        if (!isTrueDemonLord) {
            player.displayClientMessage(
                Component.literal("§e§l[GIỌNG NÓI THẾ GIỚI] §cBáo cáo. Yêu cầu Thức Tỉnh Chân Ma Vương để khai mở Trận Đồ Cưỡng Chế Tai Ương!"),
                true
            );
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.2F, 0.5F);
            return false;
        }

        // Khóa kỹ năng nếu đang trong trạng thái Tuyệt Diệt Tinh Tú
        if (TaisuiExtinctionStarsAbility.isTaisuiActive(player)) {
            player.displayClientMessage(
                Component.literal("§c⚠️ Đang trong trạng thái Tuyệt Diệt Tinh Tú! Không thể thi triển kỹ năng khác!"),
                true
            );
            return false;
        }

        Vec3 center = player.position();
        ActiveLiuRenBarrier barrier = new ActiveLiuRenBarrier(level, player, center);

        // 1. Tạo Trận Đồ Lục Nhậm Đại Bàn dưới chân (8.5m)
        Display.ItemDisplay array = EntityType.ITEM_DISPLAY.create(level);
        if (array != null) {
            array.moveTo(center.x, center.y + 0.05D, center.z, 0.0F, 0.0F);
            ItemDisplayAccessor itemAcc = (ItemDisplayAccessor) array;
            DisplayAccessor dispAcc = (DisplayAccessor) array;

            itemAcc.weapons$setItemStack(new ItemStack(ModItems.LIUREN_MAGIC_ARRAY.get()));
            itemAcc.weapons$setItemTransform(ItemDisplayContext.FIXED);
            dispAcc.weapons$setBillboardConstraints(Display.BillboardConstraints.FIXED);
            array.setGlowingTag(true);
            dispAcc.weapons$setGlowColorOverride(0x00FFFF);
            dispAcc.weapons$setViewRange(3.5F);

            Quaternionf rot = new Quaternionf().rotateX((float) Math.toRadians(90.0F));
            dispAcc.weapons$setTransformation(new Transformation(
                    new Vector3f(0.0F, 0.0F, 0.0F),
                    rot,
                    new Vector3f(8.5F, 8.5F, 0.01F),
                    null
            ));

            level.addFreshEntity(array);
            barrier.groundArray = array;
        }

        // 2. Tạo 12 Thức Thần Thần Tướng 3D (Shikigami) quỳ chầu bao bọc 360 độ, mỗi con 1 màu sắc thần thánh
        final int[] SHIKIGAMI_COLORS = {
            0xE60026, // Chu Tước (Đỏ Rực)
            0x00F0FF, // Thanh Long (Lam Ngọc)
            0xFFD700, // Thần Hậu (Hoàng Kim)
            0x9933FF, // Thái Thường (Tím Huyền Bí)
            0x00E676, // Lục Hợp (Ngọc Bích)
            0xFF6D00, // Đằng Xà (Cam Lửa)
            0xFF4081, // Thái Âm (Hồng Phấn)
            0x2979FF, // Huyền Vũ (Lam Đại Dương)
            0xFFFFFF, // Bạch Hổ (Bạch Kim)
            0xAEEA00, // Câu Trận (Xanh Lục Dạ Quang)
            0x00BFA5, // Thiên Nhất (Xanh Ngọc Lam)
            0xD500F9  // Thiên Khôi (Tím Ánh Hồng)
        };

        for (int i = 0; i < 12; i++) {
            double theta = i * (Math.PI * 2.0D / 12.0D);
            double sx = center.x + Math.cos(theta) * 3.6D;
            double sz = center.z + Math.sin(theta) * 3.6D;
            // Quay mặt hướng về tâm bảo vệ người chơi
            float yaw = (float) Math.toDegrees(-theta - (Math.PI / 2.0D));

            Display.ItemDisplay shikigami = EntityType.ITEM_DISPLAY.create(level);
            if (shikigami != null) {
                shikigami.moveTo(sx, center.y + 0.1D, sz, yaw, 0.0F);
                ItemDisplayAccessor itemAcc = (ItemDisplayAccessor) shikigami;
                DisplayAccessor dispAcc = (DisplayAccessor) shikigami;

                itemAcc.weapons$setItemStack(new ItemStack(ModItems.SHIKIGAMI_GUARDIANS[i].get()));
                itemAcc.weapons$setItemTransform(ItemDisplayContext.FIXED);
                dispAcc.weapons$setBillboardConstraints(Display.BillboardConstraints.FIXED);
                shikigami.setGlowingTag(true);
                dispAcc.weapons$setGlowColorOverride(SHIKIGAMI_COLORS[i]);
                dispAcc.weapons$setViewRange(3.0F);

                dispAcc.weapons$setTransformation(new Transformation(
                        new Vector3f(0.0F, 0.0F, 0.0F),
                        new Quaternionf(),
                        new Vector3f(2.0F, 2.0F, 2.0F),
                        null
                ));

                level.addFreshEntity(shikigami);
                barrier.shikigamis.add(shikigami);
            }
        }

        ACTIVE_BARRIERS.put(player.getUUID(), barrier);

        // 3. Gửi Title
        if (player.connection != null) {
            player.connection.send(new ClientboundSetTitleTextPacket(Component.literal("§b§l✦ TRẬN ĐỒ CƯỠNG CHẾ TAI ƯƠNG ✦")));
            player.connection.send(new ClientboundSetSubtitleTextPacket(Component.literal("§3§l✦ 12 Thần Tướng Giáng Lâm • Bất Tử Tuyệt Đối ✦")));
        }

        // 4. Âm thanh thánh tích kích hoạt
        level.playSound(null, center.x, center.y, center.z,
                SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 3.5F, 1.4F);
        level.playSound(null, center.x, center.y, center.z,
                SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 3.0F, 0.9F);
        level.playSound(null, center.x, center.y, center.z,
                SoundEvents.EVOKER_PREPARE_SUMMON, SoundSource.PLAYERS, 2.5F, 1.5F);

        player.displayClientMessage(
            Component.literal("§b§l[TRẬN ĐỒ CƯỠNG CHẾ TAI ƯƠNG] §f12 Thần Tướng 3D đã giáng lâm! Bất tử 100% trong 2 phút. §e(Nhấn Cách 2 lần để bay lên thoát kết giới)"),
            true
        );

        return true;
    }

    /**
     * Vòng lặp Server Tick cập nhật kết giới Lục Nhậm
     */
    public static void tickBarriers(ServerLevel level) {
        if (ACTIVE_BARRIERS.isEmpty()) return;

        Iterator<Map.Entry<UUID, ActiveLiuRenBarrier>> it = ACTIVE_BARRIERS.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<UUID, ActiveLiuRenBarrier> entry = it.next();
            ActiveLiuRenBarrier barrier = entry.getValue();

            if (barrier.level != level) continue;

            if (barrier.caster == null || !barrier.caster.isAlive() || barrier.caster.hasDisconnected()) {
                barrier.cleanup();
                it.remove();
                continue;
            }

            ServerPlayer caster = barrier.caster;
            Vec3 center = barrier.centerPos;
            long gameTime = level.getGameTime();

            // 1. Cưỡng chế khóa vị trí tại tâm trận đồ (Rooted)
            caster.teleportTo(center.x, center.y, center.z);
            caster.setDeltaMovement(0, 0, 0);

            // 2. Xoay trận đồ chậm rãi
            if (barrier.groundArray != null && barrier.groundArray.isAlive()) {
                Quaternionf rot = new Quaternionf()
                        .rotateX((float) Math.toRadians(90.0F))
                        .rotateZ((float) Math.toRadians(gameTime * 1.5F));
                ((DisplayAccessor) barrier.groundArray).weapons$setTransformation(new Transformation(
                        new Vector3f(0.0F, 0.0F, 0.0F),
                        rot,
                        new Vector3f(8.5F, 8.5F, 0.01F),
                        null
                ));
            }

            // 3. Vòm ánh sáng ngọc bích quanh 12 Thức Thần
            if (gameTime % 2 == 0) {
                for (int i = 0; i < 16; i++) {
                    double ang = i * (Math.PI * 2.0D / 16.0D);
                    double px = center.x + Math.cos(ang) * 3.8D;
                    double pz = center.z + Math.sin(ang) * 3.8D;
                    level.sendParticles(ELECTRIC_CYAN_DUST, px, center.y + 0.3D, pz, 1, 0, 0.1D, 0, 0);
                    level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, px, center.y + 0.5D, pz, 1, 0, 0.05D, 0, 0.01D);
                }
            }

            // 4. Đẩy lùi toàn bộ quái vật dám tiến vào bán kính 4.2m
            AABB barrierBox = new AABB(center.x - 4.5D, center.y - 1.0D, center.z - 4.5D,
                                       center.x + 4.5D, center.y + 3.5D, center.z + 4.5D);
            List<LivingEntity> intruders = level.getEntitiesOfClass(LivingEntity.class, barrierBox,
                    e -> e != caster && e.isAlive());

            for (LivingEntity intruder : intruders) {
                double dist = intruder.position().distanceTo(center);
                if (dist <= 4.2D) {
                    Vec3 push = intruder.position().subtract(center).normalize().scale(1.8D).add(0, 0.2D, 0);
                    intruder.setDeltaMovement(push);
                    level.sendParticles(ParticleTypes.FLASH, intruder.getX(), intruder.getY() + 1.0D, intruder.getZ(), 1, 0, 0, 0, 0);
                }
            }

            barrier.ticksRemaining--;
            if (barrier.ticksRemaining <= 0) {
                barrier.cleanup();
                caster.displayClientMessage(
                    Component.literal("§7[TRẬN ĐỒ CƯỠNG CHẾ TAI ƯƠNG] Trận đồ đã hết thời gian hiệu lực 2 phút."),
                    true
                );
                it.remove();
            }
        }
    }
}
