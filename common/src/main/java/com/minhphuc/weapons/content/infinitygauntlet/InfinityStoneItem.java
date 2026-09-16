package com.minhphuc.weapons.content.infinitygauntlet;

import com.minhphuc.weapons.content.divine.DivineArmorItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class InfinityStoneItem extends Item {

    public enum StoneType {
        POWER("§d§lĐá Sức Mạnh (Power)", 80),
        SPACE("§9§lĐá Không Gian (Space)", 80),
        REALITY("§c§lĐá Thực Tại (Reality)", 160),
        SOUL("§6§lĐá Linh Hồn (Soul)", 120),
        TIME("§a§lĐá Thời Gian (Time)", 140),
        MIND("§e§lĐá Tâm Trí (Mind)", 120);

        public final String displayName;
        public final int cooldownTicks;

        StoneType(String displayName, int cooldownTicks) {
            this.displayName = displayName;
            this.cooldownTicks = cooldownTicks;
        }
    }

    private final StoneType stoneType;

    public InfinityStoneItem(StoneType stoneType) {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant());
        this.stoneType = stoneType;
    }

    public StoneType getStoneType() {
        return stoneType;
    }

    /**
     * Kiểm tra người chơi có trang bị bảo vệ khỏi bức xạ vũ trụ hay không
     */
    public static boolean isProtected(Player player) {
        if (player.isCreative() || player.isSpectator()) return true;

        // 1. Cầm Găng Tay Trống ở tay phụ hoặc tay chính
        if (player.getMainHandItem().getItem() instanceof EmptyInfinityGauntletItem ||
            player.getOffhandItem().getItem() instanceof EmptyInfinityGauntletItem) {
            return true;
        }

        // 2. Mặc ít nhất 1 món Giáp Thần Linh (Divine Armor)
        for (ItemStack armor : player.getArmorSlots()) {
            if (armor.getItem() instanceof DivineArmorItem) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide() && entity instanceof Player player) {
            boolean isHeld = (player.getMainHandItem() == stack || player.getOffhandItem() == stack);
            if (isHeld) {
                // Kiểm tra lời nguyền phản phệ nếu cầm tay không
                if (!isProtected(player)) {
                    if (player.tickCount % 20 == 0) { // Đúng 1 giây 1 lần, tuyệt đối không lag
                        player.hurt(player.damageSources().magic(), 3.0F);
                        player.displayClientMessage(
                                Component.literal("§c⚠️ [NĂNG LƯỢNG VŨ TRỤ QUÁ TẢI] Cần Găng Tay Trống hoặc Giáp Thần Linh để cầm!"),
                                true
                        );
                        if (level instanceof ServerLevel serverLevel) {
                            serverLevel.sendParticles(ParticleTypes.CRIT, player.getX(), player.getY() + 1.0D, player.getZ(), 3, 0.2D, 0.3D, 0.2D, 0.05D);
                        }
                    }
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            switch (stoneType) {
                case POWER -> usePowerStone(level, player);
                case SPACE -> useSpaceStone(level, player);
                case REALITY -> useRealityStone(level, player);
                case SOUL -> useSoulStone(level, player);
                case TIME -> useTimeStone(level, player);
                case MIND -> useMindStone(level, player);
            }

            // Gán thời gian hồi chiêu
            player.getCooldowns().addCooldown(this, stoneType.cooldownTicks);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    /**
     * 1. POWER STONE (20%): Xung kích tím gây 12 sát thương diện rộng, đẩy lùi 4m (Không phá block)
     */
    private void usePowerStone(Level level, Player player) {
        Vec3 pos = player.position();
        AABB area = new AABB(pos.x - 5.0, pos.y - 2.0, pos.z - 5.0, pos.x + 5.0, pos.y + 3.0, pos.z + 5.0);

        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, area, e -> e != player && e.isAlive());
        for (LivingEntity target : targets) {
            target.hurt(player.damageSources().playerAttack(player), 12.0F);
            Vec3 knock = target.position().subtract(pos).normalize().scale(1.2);
            target.setDeltaMovement(knock.x, 0.35, knock.z);
        }

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.WITCH, pos.x, pos.y + 1.0, pos.z, 15, 1.5, 0.5, 1.5, 0.1);
        }
        level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 0.8F, 1.3F);
        player.displayClientMessage(Component.literal("§d[Đá Sức Mạnh] §fPhóng xung kích tím: Đẩy lùi diện rộng!"), true);
    }

    /**
     * 2. SPACE STONE (20%): Dịch chuyển tức thời Blink 16 blocks an toàn
     */
    private void useSpaceStone(Level level, Player player) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 look = player.getViewVector(1.0F);
        Vec3 target = eyePos.add(look.scale(16.0));

        BlockHitResult hit = level.clip(new ClipContext(eyePos, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        Vec3 dest = (hit.getType() == HitResult.Type.BLOCK) ? hit.getLocation().subtract(look.scale(0.5)) : target;

        player.teleportTo(dest.x, dest.y, dest.z);
        player.resetFallDistance();

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.PORTAL, dest.x, dest.y + 1.0, dest.z, 16, 0.5, 0.5, 0.5, 0.1);
        }
        level.playSound(null, dest.x, dest.y, dest.z, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.1F);
        player.displayClientMessage(Component.literal("§9[Đá Không Gian] §fDịch chuyển tức thời thành công!"), true);
    }

    /**
     * 3. REALITY STONE (20%): Xóa mọi hiệu ứng xấu, dập lửa, cấp Kháng Cự I 5s
     */
    private void useRealityStone(Level level, Player player) {
        player.removeEffect(MobEffects.POISON);
        player.removeEffect(MobEffects.WITHER);
        player.removeEffect(MobEffects.BLINDNESS);
        player.removeEffect(MobEffects.DARKNESS);
        player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
        player.removeEffect(MobEffects.WEAKNESS);
        player.removeEffect(MobEffects.DIG_SLOWDOWN);
        player.clearFire();

        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 1, false, true));

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, player.getX(), player.getY() + 1.0, player.getZ(), 10, 0.5, 0.5, 0.5, 0.1);
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, 1.2F);
        player.displayClientMessage(Component.literal("§c[Đá Thực Tại] §fThanh tẩy thực tại: Hóa giải mọi hiệu ứng xấu!"), true);
    }

    /**
     * 4. SOUL STONE (20%): Hút 6 HP của quái vật gần nhất, hồi 3 tim cho người chơi
     */
    private void useSoulStone(Level level, Player player) {
        Vec3 pos = player.position();
        AABB area = new AABB(pos.x - 8.0, pos.y - 3.0, pos.z - 8.0, pos.x + 8.0, pos.y + 3.0, pos.z + 8.0);

        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, area, e -> e != player && e.isAlive() && (e instanceof Mob));
        targets.sort(Comparator.comparingDouble(e -> e.distanceToSqr(player)));

        if (!targets.isEmpty()) {
            LivingEntity target = targets.get(0);
            target.hurt(player.damageSources().magic(), 6.0F);
            player.heal(6.0F);

            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.SOUL, target.getX(), target.getY() + 1.0, target.getZ(), 8, 0.3, 0.3, 0.3, 0.05);
                serverLevel.sendParticles(ParticleTypes.HEART, player.getX(), player.getY() + 1.2, player.getZ(), 4, 0.3, 0.3, 0.3, 0.05);
            }
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.2F);
            player.displayClientMessage(Component.literal("§6[Đá Linh Hồn] §fHút sinh lực đối phương, hồi phục 3 tim!"), true);
        } else {
            // Hồi phục 2 tim nếu không có quái xung quanh
            player.heal(4.0F);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.8F, 1.0F);
            player.displayClientMessage(Component.literal("§6[Đá Linh Hồn] §fTái tạo sinh mệnh: Hồi phục 2 tim!"), true);
        }
    }

    /**
     * 5. TIME STONE (20%): Làm chậm 1 mục tiêu chỉ định (Slowness IV) trong 5s
     */
    private void useTimeStone(Level level, Player player) {
        Vec3 pos = player.position();
        AABB area = new AABB(pos.x - 12.0, pos.y - 3.0, pos.z - 12.0, pos.x + 12.0, pos.y + 3.0, pos.z + 12.0);

        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, area, e -> e != player && e.isAlive() && (e instanceof Mob));
        targets.sort(Comparator.comparingDouble(e -> e.distanceToSqr(player)));

        if (!targets.isEmpty()) {
            LivingEntity target = targets.get(0);
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 3, false, true));
            target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 2, false, true));

            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.ENCHANT, target.getX(), target.getY() + 1.0, target.getZ(), 8, 0.3, 0.3, 0.3, 0.05);
            }
            level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 1.0F, 1.5F);
            player.displayClientMessage(Component.literal("§a[Đá Thời Gian] §fNgưng đọng mục tiêu: Làm chậm 90% trong 5 giây!"), true);
        } else {
            player.displayClientMessage(Component.literal("§a[Đá Thời Gian] §7Không tìm thấy mục tiêu trong phạm vi 12m."), true);
        }
    }

    /**
     * 6. MIND STONE (20%): Gây suy yếu và xóa mục tiêu tấn công của quái trong 5s
     */
    private void useMindStone(Level level, Player player) {
        Vec3 pos = player.position();
        AABB area = new AABB(pos.x - 12.0, pos.y - 3.0, pos.z - 12.0, pos.x + 12.0, pos.y + 3.0, pos.z + 12.0);

        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, area, e -> e != player && e.isAlive() && (e instanceof Mob));
        targets.sort(Comparator.comparingDouble(e -> e.distanceToSqr(player)));

        if (!targets.isEmpty()) {
            LivingEntity target = targets.get(0);
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 2, false, true));
            target.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 80, 0, false, true));

            if (target instanceof Mob mob) {
                mob.setTarget(null); // Quái bị mất phương hướng
            }

            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.ENCHANT, target.getX(), target.getY() + 1.0, target.getZ(), 10, 0.3, 0.5, 0.3, 0.1);
            }
            level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.ILLUSIONER_PREPARE_BLINDNESS, SoundSource.PLAYERS, 1.0F, 1.2F);
            player.displayClientMessage(Component.literal("§e[Đá Tâm Trí] §fSóng tâm linh: Gây choáng và xóa mục tiêu của quái!"), true);
        } else {
            player.displayClientMessage(Component.literal("§e[Đá Tâm Trí] §7Không tìm thấy mục tiêu trong phạm vi 12m."), true);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal(stoneType.displayName));
        tooltip.add(Component.literal("§7Một trong 6 Viên Đá Vô Cực cội nguồn vũ trụ."));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§c⚠️ Lời nguyền: Cầm tay không sẽ bị phản phệ giật máu!"));
        tooltip.add(Component.literal("§a🛡️ Cách an toàn: Cầm Găng Trống ở tay phụ hoặc mặc Giáp Thần."));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§e👉 [Chuột Phải]: Kích hoạt 20% thần uy nguyên bản"));
        tooltip.add(Component.literal("§6⭐ Khảm đủ 6 Đá lên Găng Tay để đạt 100% công lực & SNAP!"));
    }
}
