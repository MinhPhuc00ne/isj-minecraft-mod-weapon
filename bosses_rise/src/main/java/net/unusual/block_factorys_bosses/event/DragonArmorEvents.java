/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.Mth
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.Level$ExplosionInteraction
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.entity.living.LivingDamageEvent$Post
 *  net.neoforged.neoforge.event.tick.PlayerTickEvent$Post
 */
package net.unusual.block_factorys_bosses.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.unusual.block_factorys_bosses.entity.projectile.BlazingFireBallEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;

@EventBusSubscriber
public class DragonArmorEvents {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!(player instanceof ServerPlayer)) {
            return;
        }
        ServerPlayer player2 = (ServerPlayer)player;
        if (player2.level().getGameTime() % 20L != 0L) {
            return;
        }
        ItemStack helmet = player2.getInventory().getArmor(3);
        ItemStack chestplate = player2.getInventory().getArmor(2);
        ItemStack leggings = player2.getInventory().getArmor(1);
        ItemStack boots = player2.getInventory().getArmor(0);
        boolean hadHelmet = false;
        boolean hadChestplate = false;
        boolean hadLeggings = false;
        boolean hadBoots = false;
        if (!helmet.isEmpty() && helmet.getItem().asItem() == ((Item)BossesRiseItems.DRAGON_SKULL.get()).asItem()) {
            player2.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 60, 0, true, false));
            hadHelmet = true;
        }
        if (!chestplate.isEmpty() && chestplate.getItem() == ((Item)BossesRiseItems.DRAGON_BONES_CHESTPLATE.get()).asItem()) {
            hadChestplate = true;
        }
        if (!leggings.isEmpty() && leggings.getItem().asItem() == ((Item)BossesRiseItems.DRAGON_BONES_LEGGINGS.get()).asItem()) {
            if (player2.isCrouching()) {
                player2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 2, true, false));
                player2.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 40, 0, true, false));
            }
            hadLeggings = true;
        }
        if (!boots.isEmpty() && boots.getItem() == ((Item)BossesRiseItems.DRAGON_BONES_BOOTS.get()).asItem()) {
            hadBoots = true;
        }
        if (hadHelmet && hadChestplate && hadLeggings && hadBoots) {
            player2.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 0, true, false));
            player2.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 0, true, false));
        }
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingDamageEvent.Post event) {
        LivingEntity livingEntity = event.getEntity();
        if (!(livingEntity instanceof ServerPlayer)) {
            return;
        }
        ServerPlayer player = (ServerPlayer)livingEntity;
        ServerLevel level = (ServerLevel)player.level();
        ItemStack chestplate = player.getInventory().getArmor(2);
        ItemStack boots = player.getInventory().getArmor(0);
        if (boots.is(BossesRiseItems.DRAGON_BONES_BOOTS)) {
            DragonArmorEvents.handleDragonBonesBoots(level, player, event);
        }
        if (chestplate.is(BossesRiseItems.DRAGON_BONES_CHESTPLATE)) {
            DragonArmorEvents.handleDragonBonesChestplate(level, player, event);
        }
    }

    private static void handleDragonBonesBoots(ServerLevel level, ServerPlayer player, LivingDamageEvent.Post event) {
        if (player.fallDistance < 3.0f) {
            return;
        }
        double downwardsMomentum = Math.max(player.getKnownMovement().dot(new Vec3(0.0, -1.0, 0.0)), player.getDeltaMovement().dot(new Vec3(0.0, -1.0, 0.0)));
        if (downwardsMomentum < 0.5) {
            return;
        }
        float explosionRadius = Mth.clamp((float)((float)downwardsMomentum), (float)0.0f, (float)4.5f);
        level.explode((Entity)player, player.position().x, player.position().y, player.position().z, explosionRadius, Level.ExplosionInteraction.NONE).explode();
        level.getNearbyPlayers(TargetingConditions.DEFAULT, (LivingEntity)player, AABB.ofSize((Vec3)player.position(), (double)4.0, (double)4.0, (double)4.0)).forEach(p -> {
            if (p == player) {
                return;
            }
            p.setRemainingFireTicks(250);
        });
    }

    private static void handleDragonBonesChestplate(ServerLevel level, ServerPlayer player, LivingDamageEvent.Post event) {
        Entity causingEntity;
        if (Math.random() > 0.1) {
            return;
        }
        Entity entity = event.getSource().getDirectEntity();
        if (!(entity instanceof Projectile)) {
            return;
        }
        Projectile projectile = (Projectile)entity;
        Entity entity2 = causingEntity = projectile.getOwner() != null ? projectile.getOwner() : event.getSource().getEntity();
        if (causingEntity == null) {
            return;
        }
        if (causingEntity.distanceTo((Entity)player) < 3.0f) {
            return;
        }
        BlazingFireBallEntity fireball = new BlazingFireBallEntity((EntityType<? extends BlazingFireBallEntity>)((EntityType)BossesRiseEntities.BLAZING_FIRE_BALL.get()), (Level)level);
        fireball.setOwner((Entity)player);
        fireball.setBaseDamage(1.0);
        fireball.setSilent(true);
        fireball.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
        Vec3 directionToTarget = causingEntity.position().add(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5).subtract(player.getEyePosition()).normalize();
        fireball.shoot(directionToTarget.x, directionToTarget.y, directionToTarget.z, 1.5f, 3.0f);
        level.addFreshEntity((Entity)fireball);
    }

    public static boolean shouldDisableNightVisionFlashing(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).is(BossesRiseItems.DRAGON_SKULL);
    }
}

