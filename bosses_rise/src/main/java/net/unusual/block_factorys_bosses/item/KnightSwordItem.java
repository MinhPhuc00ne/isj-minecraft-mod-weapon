/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlotGroup
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.component.ItemAttributeModifiers
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.extensions.common.IClientItemExtensions
 *  net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent
 */
package net.unusual.block_factorys_bosses.item;

import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.renderer.KnightSwordItemRenderer;
import net.unusual.block_factorys_bosses.entity.projectile.SwordWaveEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.item.AnimatedSwordItem;

@EventBusSubscriber
public class KnightSwordItem
extends AnimatedSwordItem {
    private static final Tier TOOL_TIER = new Tier(){

        public int getUses() {
            return 1480;
        }

        public float getSpeed() {
            return 4.0f;
        }

        public float getAttackDamageBonus() {
            return 0.0f;
        }

        public TagKey<Block> getIncorrectBlocksForDrops() {
            return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
        }

        public int getEnchantmentValue() {
            return 2;
        }

        public Ingredient getRepairIngredient() {
            return Ingredient.of((ItemLike[])new ItemLike[]{Items.IRON_INGOT});
        }
    };
    private static final int USE_DURATION = 1200;

    public KnightSwordItem() {
        super(TOOL_TIER, new Item.Properties().rarity(Rarity.UNCOMMON).attributes(ItemAttributeModifiers.builder().add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(ResourceLocation.withDefaultNamespace((String)"base_entity_interaction_range"), 0.5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, (double)(13.0f + TOOL_TIER.getAttackDamageBonus()), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, (double)-3.1f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build()));
    }

    @OnlyIn(value=Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        list.add((Component)Component.translatable((String)"item.block_factorys_bosses.knight_sword.description_0"));
        list.add((Component)Component.translatable((String)"item.block_factorys_bosses.knight_sword.description_1"));
    }

    @Override
    public int getAttackDelay() {
        return 22;
    }

    @Override
    public SoundEvent getSwingSound() {
        return (SoundEvent)BossesRiseSounds.KNIGHT_SWORD_SWING.value();
    }

    @Override
    public void onEntitySwingHook(Entity entity, ItemStack stack) {
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 1200;
    }

    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        int timeCharged;
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
        if (!level.isClientSide() && (timeCharged = 1200 - remainingUseDuration - 5) % 20 == 0 && timeCharged >= 0 && timeCharged <= 60) {
            level.playSound(null, BlockPos.containing((double)livingEntity.getX(), (double)livingEntity.getY(), (double)livingEntity.getZ()), (SoundEvent)SoundEvents.NOTE_BLOCK_CHIME.value(), SoundSource.PLAYERS, 0.8f, 1.0f);
        }
    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        super.releaseUsing(stack, level, livingEntity, timeLeft);
        if (livingEntity instanceof Player) {
            Player player = (Player)livingEntity;
            if (!level.isClientSide()) {
                int timeCharged = 1200 - timeLeft;
                int slashes = timeCharged < 5 ? 0 : Math.min((timeCharged - 5) / 20 * 2 + 1, 7);
                BossesRise.LOGGER.debug(timeCharged + " " + slashes);
                if (slashes > 0) {
                    player.getCooldowns().addCooldown(stack.getItem(), 80);
                    level.playSound(null, BlockPos.containing((double)player.getX(), (double)player.getY(), (double)player.getZ()), SoundEvents.BREEZE_SHOOT, SoundSource.PLAYERS, 0.8f, 0.4f);
                    for (int i = 0; i < slashes; ++i) {
                        SwordWaveEntity swordWave = new SwordWaveEntity((EntityType<? extends SwordWaveEntity>)((EntityType)BossesRiseEntities.SWORD_WAVE.get()), level);
                        swordWave.setOwner((Entity)player);
                        swordWave.setBaseDamage(5.0);
                        swordWave.setSilent(true);
                        swordWave.setKnockback(0);
                        swordWave.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
                        swordWave.shoot(player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z, 2.0f, 0.0f);
                        level.addFreshEntity((Entity)swordWave);
                    }
                }
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        if (!player.getCooldowns().isOnCooldown(itemstack.getItem())) {
            player.startUsingItem(usedHand);
            return InteractionResultHolder.consume(itemstack);
        }
        return super.use(level, player, usedHand);
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        final Minecraft instance = Minecraft.getInstance();
        event.registerItem((IClientItemExtensions)new AnimatedSwordItem.AnimatedSwordItemExtension(){
            final KnightSwordItemRenderer rendererInstance;
            {
                this.rendererInstance = new KnightSwordItemRenderer(instance.getBlockEntityRenderDispatcher(), instance.getEntityModels());
            }

            @Nonnull
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return this.rendererInstance;
            }
        }, new Item[]{(Item)BossesRiseItems.KNIGHT_SWORD.get()});
    }
}

