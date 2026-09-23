/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemCooldowns
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.neoforged.bus.api.Event
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent
 */
package net.unusual.block_factorys_bosses.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerVariables;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseDataAttachments;
import net.unusual.block_factorys_bosses.init.BossesRiseTags;

@EventBusSubscriber
public class NoHitBossBattleTriggerProcedure {
    @SubscribeEvent
    public static void onEntityAttacked(LivingIncomingDamageEvent event) {
        if (event.getEntity() != null) {
            NoHitBossBattleTriggerProcedure.execute((Event)event, (LevelAccessor)event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), (Entity)event.getEntity(), event.getSource().getDirectEntity(), event.getSource().getEntity());
        }
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
        NoHitBossBattleTriggerProcedure.execute(null, world, x, y, z, entity, immediatesourceentity, sourceentity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
        LivingEntity _livEnt2;
        if (entity == null || immediatesourceentity == null || sourceentity == null) {
            return;
        }
        if (sourceentity.getType().is(BossesRiseTags.EntityTypes.BF_BOSS)) {
            PlayerVariables _vars = net.neoforged.neoforge.attachment.AttachmentHolder.getData(entity, BossesRiseDataAttachments.PLAYER_VARIABLES);
            _vars.boss_no_hit = false;
            _vars.syncPlayerVariables(entity);
        }
        if (immediatesourceentity instanceof UnderworldKnightEntity && entity instanceof LivingEntity && (_livEnt2 = (LivingEntity)entity).isBlocking() && Math.random() < 0.6) {
            if (entity instanceof Player) {
                ItemStack itemStack;
                Player _player = (Player)entity;
                ItemCooldowns itemCooldowns = _player.getCooldowns();
                if (entity instanceof LivingEntity) {
                    LivingEntity _entUseItem3 = (LivingEntity)entity;
                    itemStack = _entUseItem3.getUseItem();
                } else {
                    itemStack = ItemStack.EMPTY;
                }
                itemCooldowns.addCooldown(itemStack.getItem(), 80);
            }
            if (world instanceof Level) {
                Level _level = (Level)world;
                if (!_level.isClientSide()) {
                    _level.playSound(null, BlockPos.containing((double)x, (double)y, (double)z), SoundEvents.SHIELD_BREAK, SoundSource.PLAYERS, 0.9f, 1.0f);
                } else {
                    _level.playLocalSound(x, y, z, SoundEvents.SHIELD_BREAK, SoundSource.PLAYERS, 0.9f, 1.0f, false);
                }
            }
            if (entity instanceof LivingEntity) {
                LivingEntity _entity = (LivingEntity)entity;
                _entity.stopUsingItem();
            }
        }
    }
}

