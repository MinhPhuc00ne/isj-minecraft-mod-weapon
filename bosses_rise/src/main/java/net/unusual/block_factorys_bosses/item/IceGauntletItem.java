/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.core.component.DataComponentType
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.AnimationState
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlotGroup
 *  net.minecraft.world.entity.HumanoidArm
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.item.ShieldItem
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.component.CustomData
 *  net.minecraft.world.item.component.ItemAttributeModifiers
 *  net.minecraft.world.item.component.ItemAttributeModifiers$Builder
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.extensions.common.IClientItemExtensions
 *  net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent
 *  net.neoforged.neoforge.common.Tags$Items
 */
package net.unusual.block_factorys_bosses.item;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.Tags;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.attachment.entity.GauntletAttachment;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerAnimationHandler;
import net.unusual.block_factorys_bosses.client.renderer.FrozenFistItemRenderer;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeClusterEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;

@EventBusSubscriber
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class IceGauntletItem
extends ShieldItem {
    public static final Map<String, AnimationState> IDLE_STATES = new WeakHashMap<String, AnimationState>();
    public static final Map<String, AnimationState> SWING_STATES = new WeakHashMap<String, AnimationState>();
    public static final Map<ItemStack, Integer> IS_MINING = new WeakHashMap<ItemStack, Integer>();
    public static final float ICE_ANGLE = 80.0f;
    public static final int MAX_PLAYER_ICICLES = 32;

    public IceGauntletItem() {
        super(new Item.Properties().durability(1200).rarity(Rarity.UNCOMMON).attributes(IceGauntletItem.createAttributes()));
    }

    public int getBarColor(ItemStack stack) {
        return 52479;
    }

    public boolean isValidRepairItem(ItemStack stack, ItemStack repairItem) {
        return repairItem.is(Items.DIAMOND);
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
        boolean hurt = super.hurtEnemy(stack, entity, attacker);
        entity.setTicksFrozen(400);
        Level level = entity.level();
        if (level instanceof ServerLevel) {
            ServerLevel level2 = (ServerLevel)level;
            level2.sendParticles((ParticleOptions)ParticleTypes.SNOWFLAKE, entity.getX(), entity.getY() + 1.5, entity.getZ(), 10, 0.1, 0.1, 0.1, 0.0);
            level2.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.SNOW_CLOUD.get()), entity.getX(), entity.getY() + 1.5, entity.getZ(), 2, 0.6, 0.6, 0.6, 0.0);
        }
        return hurt;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(stack, context, list, flag);
        list.add((Component)Component.translatable((String)"item.block_factorys_bosses.ice_gauntlet.description_0"));
        list.add((Component)Component.translatable((String)"item.block_factorys_bosses.ice_gauntlet.description_1"));
        list.add((Component)Component.translatable((String)"item.block_factorys_bosses.ice_gauntlet.description_2"));
        list.add((Component)Component.translatable((String)"item.block_factorys_bosses.ice_gauntlet.description_3"));
        list.add((Component)Component.translatable((String)"item.block_factorys_bosses.ice_gauntlet.description_4"));
        list.add((Component)Component.translatable((String)"item.block_factorys_bosses.ice_gauntlet.description_5"));
        list.add((Component)Component.translatable((String)"item.block_factorys_bosses.ice_gauntlet.description_6"));
    }

    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        double swing_animtime;
        int is_mining;
        super.inventoryTick(stack, world, entity, slot, selected);
        if (((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, (Object)CustomData.EMPTY)).copyTag().getString("itemKey").isEmpty()) {
            CustomData.update((DataComponentType)DataComponents.CUSTOM_DATA, (ItemStack)stack, tag -> tag.putString("itemKey", UUID.randomUUID().toString()));
        }
        if ((is_mining = IS_MINING.computeIfAbsent(stack, s -> 0).intValue()) > 0) {
            IS_MINING.put(stack, is_mining - 1);
        }
        if ((swing_animtime = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, (Object)CustomData.EMPTY)).copyTag().getDouble("swing_animtime")) > 0.0) {
            CustomData.update((DataComponentType)DataComponents.CUSTOM_DATA, (ItemStack)stack, tag -> tag.putDouble("swing_animtime", ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, (Object)CustomData.EMPTY)).copyTag().getDouble("swing_animtime") - 1.0));
        }
    }

    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null) {
            Vec3 clicked = context.getClickLocation();
            AtomicBoolean flag = new AtomicBoolean(false);
            player.level().getEntitiesOfClass(IceSpikeClusterEntity.class, AABB.ofSize((Vec3)clicked, (double)3.0, (double)3.0, (double)3.0)).forEach(spike -> {
                InteractionResult result;
                if (player.level().isClientSide) {
                    flag.set(true);
                } else if (!flag.get() && (result = spike.interact(player, context.getHand())) == InteractionResult.CONSUME) {
                    flag.set(true);
                }
            });
            if (flag.get()) {
                return InteractionResult.CONSUME;
            }
        }
        return super.useOn(context);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        PlayerAnimationHandler.fromPlayer(player).startAnimation(player, GauntletAttachment.ATTACK_AND_HOLD);
        return super.use(level, player, hand);
    }

    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        IS_MINING.put(stack, 5);
        if (((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, (Object)CustomData.EMPTY)).copyTag().getDouble("swing_animtime") == 0.0) {
            return 1.0f;
        }
        return 0.0f;
    }

    public boolean onEntitySwing(ItemStack stack, LivingEntity entity, InteractionHand hand) {
        double swing_animtime;
        boolean hit = false;
        int is_mining = IS_MINING.computeIfAbsent(stack, s -> 0);
        if (is_mining == 0 && (swing_animtime = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, (Object)CustomData.EMPTY)).copyTag().getDouble("swing_animtime")) == 0.0 && entity instanceof Player) {
            Player plr = (Player)entity;
            this.playSound(plr, (SoundEvent)BossesRiseSounds.ICE_GAUNTLET_PUNCH.value());
            CustomData.update((DataComponentType)DataComponents.CUSTOM_DATA, (ItemStack)stack, tag -> tag.putString("itemKey", UUID.randomUUID().toString()));
            CustomData.update((DataComponentType)DataComponents.CUSTOM_DATA, (ItemStack)stack, tag -> tag.putDouble("swing_animtime", 18.0));
        }
        return hit;
    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)entityLiving;
            if (level instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)level;
                if (player.isSecondaryUseActive() && !player.getCooldowns().isOnCooldown((Item)this)) {
                    int i = this.getUseDuration(stack, entityLiving) - timeLeft;
                    if (i < 0) {
                        return;
                    }
                    PlayerAnimationHandler.fromPlayer((Player)player).startAnimation((Player)player, GauntletAttachment.ATTACK_END, 11);
                    double interaction = player.blockInteractionRange() * 1.1;
                    Vec3 eyePosition = player.getEyePosition(1.0f);
                    Vec3 viewVector = player.getViewVector(1.0f);
                    Vec3 vec32 = eyePosition.add(viewVector.x * interaction, viewVector.y * interaction, viewVector.z * interaction);
                    BlockHitResult blockHitResult = level.clip(new ClipContext(eyePosition, vec32, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity)player));
                    if (blockHitResult.getType() == HitResult.Type.MISS) {
                        return;
                    }
                    if (player.getXRot() > 85.0f) {
                        viewVector = player.calculateViewVector(85.0f, player.getViewYRot(1.0f));
                    } else if (player.getXRot() < -85.0f) {
                        viewVector = player.calculateViewVector(-85.0f, player.getViewYRot(1.0f));
                    }
                    GauntletAttachment.iceWave((LivingEntity)player, serverLevel, Math.min((float)i / 32.0f + 0.5f, 1.5f) * 12.0f, Math.min(i, 32), blockHitResult.getLocation(), viewVector, 10.0f, 1.0f);
                    player.getCooldowns().addCooldown(stack.getItem(), 80);
                    stack.hurtAndBreak(1, (LivingEntity)player, player.getEquipmentSlotForItem(stack));
                }
            }
        }
    }

    @Nullable
    public static Vec3 placeProper(ServerLevel level, LivingEntity living, Vec3 wantedPos, Vec3 eyePosition) {
        BlockHitResult blockHitResult = level.clip(new ClipContext(new Vec3(wantedPos.x, eyePosition.y, wantedPos.z), new Vec3(wantedPos.x, wantedPos.y - 3.0, wantedPos.z), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity)living));
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return null;
        }
        return blockHitResult.getLocation();
    }

    public static ItemAttributeModifiers createAttributes() {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        builder.add(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(BossesRise.prefix("effect.kb"), 2.5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 4.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -3.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        return builder.build();
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions(){
            private FrozenFistItemRenderer rendererInstance;

            @OnlyIn(value=Dist.CLIENT)
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                int is_mining = IS_MINING.computeIfAbsent(player.getMainHandItem(), s -> 0);
                double swing_animtime = ((CustomData)player.getMainHandItem().getOrDefault(DataComponents.CUSTOM_DATA, (Object)CustomData.EMPTY)).copyTag().getDouble("swing_animtime");
                if (is_mining == 0 && swing_animtime > 0.0) {
                    int i = arm == HumanoidArm.RIGHT ? 1 : -1;
                    poseStack.translate((float)i * 0.56f, -0.52f, -0.72f);
                    if (player.getUseItem() == itemInHand) {
                        poseStack.translate(0.05, 0.05, 0.05);
                    }
                    return true;
                }
                return false;
            }

            @OnlyIn(value=Dist.CLIENT)
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.rendererInstance == null) {
                    this.rendererInstance = new FrozenFistItemRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
                }
                return this.rendererInstance;
            }
        }, new Item[]{(Item)BossesRiseItems.ICE_GAUNTLET.get()});
    }

    protected void playSound(Player player, SoundEvent soundEvent) {
        player.playSound(soundEvent, 1.0f, 1.0f);
    }
}

