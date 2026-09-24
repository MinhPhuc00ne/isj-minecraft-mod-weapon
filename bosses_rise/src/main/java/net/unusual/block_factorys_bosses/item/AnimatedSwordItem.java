/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.component.DataComponentType
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.AnimationState
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.HumanoidArm
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.SwordItem
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.component.CustomData
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.neoforge.client.extensions.common.IClientItemExtensions
 */
package net.unusual.block_factorys_bosses.item;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.unusual.block_factorys_bosses.client.AnimationTickHolder;

public abstract class AnimatedSwordItem
extends SwordItem {
    public static final Map<String, AnimationState> IDLE_STATES = new WeakHashMap<String, AnimationState>();
    public static final Map<String, AnimationState> SWING_STATES = new WeakHashMap<String, AnimationState>();
    public static final Map<ItemStack, Integer> IS_MINING = new WeakHashMap<ItemStack, Integer>();

    public AnimatedSwordItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    public abstract int getAttackDelay();

    public abstract SoundEvent getSwingSound();

    public boolean isDualHanded() {
        return false;
    }

    public void onEntitySwingHook(Entity entity, ItemStack stack) {
    }

    protected static int getIsMining(ItemStack stack) {
        return IS_MINING.computeIfAbsent(stack, s -> 0);
    }

    protected static String getItemKey(ItemStack stack) {
        return ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, (Object)CustomData.EMPTY)).copyTag().getString("itemKey");
    }

    protected static void randomizeItemKey(ItemStack stack) {
        CustomData.update((DataComponentType)DataComponents.CUSTOM_DATA, (ItemStack)stack, tag -> tag.putString("itemKey", UUID.randomUUID().toString()));
    }

    protected static double getSwingAnimtime(ItemStack stack) {
        return ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, (Object)CustomData.EMPTY)).copyTag().getDouble("swing_animtime");
    }

    protected static void setSwingAnimtime(ItemStack stack, double swingAnimtime) {
        CustomData.update((DataComponentType)DataComponents.CUSTOM_DATA, (ItemStack)stack, tag -> tag.putDouble("swing_animtime", swingAnimtime));
    }

    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (AnimatedSwordItem.getItemKey(stack).isEmpty()) {
            AnimatedSwordItem.randomizeItemKey(stack);
        }
        int is_mining = AnimatedSwordItem.getIsMining(stack);
        double swing_animtime = AnimatedSwordItem.getSwingAnimtime(stack);
        if (is_mining > 0) {
            IS_MINING.put(stack, is_mining - 1);
        }
        if (swing_animtime > 0.0 && !level.isClientSide()) {
            AnimatedSwordItem.setSwingAnimtime(stack, swing_animtime - 1.0);
        }
    }

    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        IS_MINING.put(stack, 5);
        return AnimatedSwordItem.getSwingAnimtime(stack) == 0.0 ? 1.0f : 0.0f;
    }

    public boolean onEntitySwing(ItemStack stack, LivingEntity entity, InteractionHand hand) {
        ItemStack offhand;
        boolean retval = false;
        if (AnimatedSwordItem.getIsMining(stack) != 0 && !(entity instanceof Player)) {
            return retval;
        }
        if (AnimatedSwordItem.getSwingAnimtime(stack) == 0.0) {
            this.playSwingAnimation((Entity)entity, stack);
        } else if (this.isDualHanded() && (offhand = entity.getOffhandItem()).getItem() == stack.getItem() && AnimatedSwordItem.getSwingAnimtime(offhand) == 0.0) {
            this.playSwingAnimation((Entity)entity, offhand);
        }
        return retval;
    }

    private void playSwingAnimation(Entity entity, ItemStack stack) {
        if (!entity.level().isClientSide()) {
            AnimatedSwordItem.randomizeItemKey(stack);
            AnimatedSwordItem.setSwingAnimtime(stack, this.getAttackDelay());
        }
        entity.playSound(this.getSwingSound(), 1.0f, 1.0f);
        this.onEntitySwingHook(entity, stack);
        if (entity.level().isClientSide()) {
            SWING_STATES.computeIfAbsent(AnimatedSwordItem.getItemKey(stack), s -> new AnimationState()).start(Math.round(AnimationTickHolder.getRenderTime()));
        }
    }

    protected static class AnimatedSwordItemExtension
    implements IClientItemExtensions {
        protected AnimatedSwordItemExtension() {
        }

        @OnlyIn(value=Dist.CLIENT)
        public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
            ItemStack stack = player.getMainHandItem();
            if (AnimatedSwordItem.getIsMining(stack) == 0) {
                int i = arm == HumanoidArm.RIGHT ? 1 : -1;
                poseStack.translate((float)i * 0.56f, -0.52f, -0.72f);
                if (player.getUseItem() == itemInHand) {
                    poseStack.translate(0.05, 0.05, 0.05);
                }
                return true;
            }
            return false;
        }
    }
}

