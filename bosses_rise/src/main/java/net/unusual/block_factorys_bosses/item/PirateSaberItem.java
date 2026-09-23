/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.stats.Stats
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.ItemUtils
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.SwordItem
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.Tiers
 *  net.minecraft.world.item.UseAnim
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 */
package net.unusual.block_factorys_bosses.item;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.unusual.block_factorys_bosses.entity.boss.kraken.summons.CrossbowPirateEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.summons.PirateRookEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PirateSaberItem
extends SwordItem {
    public PirateSaberItem() {
        super((Tier)Tiers.IRON, new Item.Properties().attributes(SwordItem.createAttributes((Tier)Tiers.IRON, (int)9, (float)-2.4f)).durability(2000));
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity living) {
        if (living instanceof Player) {
            Player player = (Player)living;
            if (!level.isClientSide) {
                CrossbowPirateEntity crossbowPirate = new CrossbowPirateEntity((EntityType<CrossbowPirateEntity>)((EntityType)BossesRiseEntities.CROSSBOW_PIRATE.get()), level);
                crossbowPirate.setOwnerUUID(player.getUUID());
                crossbowPirate.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.CROSSBOW));
                crossbowPirate.moveTo(player.position().add(player.calculateViewVector(0.0f, player.getYRot()).yRot(1.5707964f)), player.getYRot(), player.getXRot());
                level.addFreshEntity((Entity)crossbowPirate);
                crossbowPirate.triggerAnim("main_controller", "spawn");
                crossbowPirate.skipDropExperience();
                PirateRookEntity pirateRook = new PirateRookEntity((EntityType<PirateRookEntity>)((EntityType)BossesRiseEntities.PIRATE_ROOK.get()), level);
                pirateRook.setOwnerUUID(player.getUUID());
                pirateRook.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)BossesRiseItems.PIRATE_SABER.get()));
                pirateRook.moveTo(player.position().add(player.calculateViewVector(0.0f, player.getYRot()).yRot(-1.5707964f)), player.getYRot(), player.getXRot());
                level.addFreshEntity((Entity)pirateRook);
                pirateRook.triggerAnim("main_controller", "spawn");
                pirateRook.skipDropExperience();
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            player.getCooldowns().addCooldown(stack.getItem(), 80);
            if (!player.hasInfiniteMaterials()) {
                stack.hurtAndBreak(10, (LivingEntity)player, player.getEquipmentSlotForItem(stack));
            }
        }
        return stack;
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 14;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly((Level)level, (Player)player, (InteractionHand)hand);
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }
}

