package com.minhphuc.weapons.content.divine;

import com.minhphuc.weapons.content.tensura.BeelzebuthAbility;
import com.minhphuc.weapons.data.EntityDataHelper;
import com.minhphuc.weapons.init.ModItems;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.InteractionEvent;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class DivineWeaponEvents {

    public static void register() {
        EntityEvent.LIVING_HURT.register(DivineWeaponEvents::onLivingHurt);

        // Tick cập nhật các Thánh Giới Linh Tử Băng Hoại đang kích hoạt
        dev.architectury.event.events.common.TickEvent.SERVER_LEVEL_POST.register(SanctuaryDisintegrationAbility::tickSanctuaries);

        // Tick cập nhật Tà Khứ Vũ Thê Tử (Jacob's Ladder)
        dev.architectury.event.events.common.TickEvent.SERVER_LEVEL_POST.register(JacobsLadderAbility::tickLadders);

        // Tick cập nhật Đầu Rồng Hư Không Bạo Thực Vương Beelzebuth
        dev.architectury.event.events.common.TickEvent.SERVER_LEVEL_POST.register(BeelzebuthAbility::tickDragons);

        // Tick cập nhật Lễ Hội Thu Hoạch Thức Tỉnh Chân Ma Vương (Harvest Festival)
        dev.architectury.event.events.common.TickEvent.SERVER_LEVEL_POST.register(com.minhphuc.weapons.content.tensura.HarvestFestival::tickRituals);

        // Chuột phải vào sinh vật khi tay không: Thi triển Kỹ Năng Chân Ma Vương
        InteractionEvent.INTERACT_ENTITY.register((player, target, hand) -> {
            if (player.level().isClientSide()) return EventResult.pass();
            if (player.getItemInHand(hand).isEmpty() && player instanceof ServerPlayer serverPlayer) {
                boolean isTrueDemonLord = EntityDataHelper.getCustomData(serverPlayer).getBoolean("TensuraTrueDemonLord");
                if (isTrueDemonLord) {
                    if (serverPlayer.getCooldowns().isOnCooldown(ModItems.DEMON_LORD_SEED.get())) {
                        return EventResult.interruptTrue();
                    }
                    int selectedSkill = EntityDataHelper.getCustomData(serverPlayer).getInt("TensuraDemonLordSkill");
                    ServerLevel sl = (ServerLevel) serverPlayer.level();
                    if (selectedSkill == 1) {
                        BeelzebuthAbility.executeCorrosion(sl, serverPlayer);
                    } else {
                        BeelzebuthAbility.executeBeelzebuth(sl, serverPlayer);
                    }
                    return EventResult.interruptTrue();
                }
            }
            return EventResult.pass();
        });
    }

    public static EventResult onLivingHurt(LivingEntity victim, DamageSource source, float amount) {
        // =========================================================================
        // 1. THẦN LINH VŨ TRANG (DIVINE ARMOR): Kháng gần 100% mọi sát thương trừ Độc & Dưới Nước
        // =========================================================================
        if (victim instanceof Player player) {
            if (DivineArmorItem.isWearingFullSet(player)) {
                // Ngoại lệ 1: Sát thương trúng độc (Poison)
                boolean isPoison = source.is(DamageTypes.MAGIC) && player.hasEffect(MobEffects.POISON);
                // Ngoại lệ 2: Không thở được dưới nước -> Chết đuối (Drown)
                boolean isDrowning = source.is(DamageTypes.DROWN);
                // Ngoại lệ 3: Rơi ra khỏi thế giới (Void)
                boolean isVoid = source.is(DamageTypes.FELL_OUT_OF_WORLD);

                if (!isPoison && !isDrowning && !isVoid) {
                    // Miễn nhiễm hoàn toàn mọi sát thương từ quái vật, người chơi, rơi, lửa, nổ, v.v.
                    return EventResult.interruptFalse();
                }
            }
        }

        // =========================================================================
        // 2. NGUYỆT QUANG THẦN TẾ KIẾM (MOONLIGHT SWORD): Vô Cực Sát Thương & Phá Hủy Giáp
        // =========================================================================
        Entity directAttacker = source.getEntity();
        if (directAttacker instanceof ServerPlayer attacker) {
            ItemStack weapon = attacker.getMainHandItem();
            if (weapon.getItem() instanceof MoonlightSwordItem) {
                if (victim.level().isClientSide()) return EventResult.pass();

                // 2.1. Phá vỡ toàn bộ giáp & vũ khí đang cầm của mục tiêu trong đòn đánh đầu
                boolean shatteredEquipment = false;
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    ItemStack equipped = victim.getItemBySlot(slot);
                    if (!equipped.isEmpty()) {
                        victim.setItemSlot(slot, ItemStack.EMPTY);
                        shatteredEquipment = true;
                    }
                }
                if (shatteredEquipment) {
                    victim.level().playSound(null, victim.getX(), victim.getY(), victim.getZ(),
                            SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 2.0F, 0.8F);
                    if (victim.level() instanceof ServerLevel sl) {
                        sl.sendParticles(ParticleTypes.ITEM_SNOWBALL, victim.getX(), victim.getY() + 1.0D, victim.getZ(), 20, 0.3D, 0.5D, 0.3D, 0.1D);
                    }
                }

                // 2.2. Kiểm tra mục tiêu có phải là Boss hoặc Sinh vật cực mạnh
                boolean isBoss = victim instanceof net.minecraft.world.entity.boss.enderdragon.EnderDragon
                        || victim instanceof net.minecraft.world.entity.boss.wither.WitherBoss
                        || victim instanceof net.minecraft.world.entity.monster.warden.Warden
                        || victim instanceof net.minecraft.world.entity.animal.IronGolem
                        || victim.getMaxHealth() >= 100.0F;

                if (isBoss) {
                    float maxHp = victim.getMaxHealth();
                    float currentHp = victim.getHealth();

                    // Đòn chém 1: Nếu máu còn trên 20% max HP -> Lấy đi 80% máu tối đa
                    if (currentHp > maxHp * 0.20F) {
                        float targetHp = Math.max(1.0F, maxHp * 0.20F);
                        victim.setHealth(targetHp);

                        victim.level().playSound(null, victim.getX(), victim.getY(), victim.getZ(),
                                SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.PLAYERS, 2.0F, 1.2F);
                        if (victim.level() instanceof ServerLevel sl) {
                            sl.sendParticles(ParticleTypes.FLASH, victim.getX(), victim.getY() + 1.0D, victim.getZ(), 2, 0, 0, 0, 0);
                            sl.sendParticles(ParticleTypes.CRIT, victim.getX(), victim.getY() + 1.0D, victim.getZ(), 30, 0.5D, 0.5D, 0.5D, 0.2D);
                        }

                        attacker.displayClientMessage(
                            Component.literal("§6§l[NGUYỆT QUANG THẦN TẾ KIẾM] §eTrảm kích thần thoại đã tước đoạt 80% sinh lực của " + victim.getName().getString() + "! (Còn 20% HP)"),
                            true
                        );
                        return EventResult.interruptFalse();
                    } else {
                        // Đòn chém 2: Máu <= 80% max HP (đã qua đòn 1) -> Kết liễu chết luôn!
                        victim.hurt(attacker.level().damageSources().genericKill(), 100000.0F);
                        if (victim.isAlive()) {
                            victim.discard();
                        }
                        if (victim.level() instanceof ServerLevel sl) {
                            sl.sendParticles(ParticleTypes.EXPLOSION_EMITTER, victim.getX(), victim.getY() + 1.0D, victim.getZ(), 1, 0, 0, 0, 0);
                        }
                        attacker.displayClientMessage(
                            Component.literal("§4§l[NGUYỆT QUANG THẦN TẾ KIẾM] §cTrảm sát kết liễu hoàn toàn " + victim.getName().getString() + "!"),
                            true
                        );
                        return EventResult.interruptFalse();
                    }
                } else {
                    // Quái vật bình thường: Nhất kiếm tất sát 1 hit chết luôn
                    victim.hurt(attacker.level().damageSources().genericKill(), 100000.0F);
                    if (victim.isAlive()) {
                        victim.discard();
                    }
                    if (victim.level() instanceof ServerLevel sl) {
                        sl.sendParticles(ParticleTypes.SWEEP_ATTACK, victim.getX(), victim.getY() + 1.0D, victim.getZ(), 2, 0.2D, 0.2D, 0.2D, 0);
                    }
                    attacker.displayClientMessage(
                        Component.literal("§4§l[NGUYỆT QUANG THẦN TẾ KIẾM] §fNhất kiếm tất sát " + victim.getName().getString() + "!"),
                        true
                    );
                    return EventResult.interruptFalse();
                }
            }
        }

        return EventResult.pass();
    }
}
