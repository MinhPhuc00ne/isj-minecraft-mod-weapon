/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.SwordItem
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.extensions.common.IClientItemExtensions
 *  net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent
 */
package net.unusual.block_factorys_bosses.item;

import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.unusual.block_factorys_bosses.client.renderer.WarriorSwordItemRenderer;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.item.AnimatedSwordItem;

@EventBusSubscriber
public class WarriorSwordItem
extends AnimatedSwordItem {
    private static final Tier TOOL_TIER = new Tier(){

        public int getUses() {
            return 300;
        }

        public float getSpeed() {
            return 4.0f;
        }

        public float getAttackDamageBonus() {
            return 0.0f;
        }

        public TagKey<Block> getIncorrectBlocksForDrops() {
            return BlockTags.INCORRECT_FOR_IRON_TOOL;
        }

        public int getEnchantmentValue() {
            return 2;
        }

        public Ingredient getRepairIngredient() {
            return Ingredient.of((ItemLike[])new ItemLike[]{Items.IRON_INGOT});
        }
    };

    public WarriorSwordItem() {
        super(TOOL_TIER, new Item.Properties().attributes(SwordItem.createAttributes((Tier)TOOL_TIER, 6, (float)-2.3f)));
    }

    @Override
    public int getAttackDelay() {
        return 14;
    }

    @Override
    public SoundEvent getSwingSound() {
        return (SoundEvent)BossesRiseSounds.WARRIOR_SWORD_SWING.value();
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        final Minecraft instance = Minecraft.getInstance();
        event.registerItem((IClientItemExtensions)new AnimatedSwordItem.AnimatedSwordItemExtension(){
            final WarriorSwordItemRenderer rendererInstance;
            {
                this.rendererInstance = new WarriorSwordItemRenderer(instance.getBlockEntityRenderDispatcher(), instance.getEntityModels());
            }

            @Nonnull
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return this.rendererInstance;
            }
        }, new Item[]{(Item)BossesRiseItems.WARRIOR_SWORD.get()});
    }
}

