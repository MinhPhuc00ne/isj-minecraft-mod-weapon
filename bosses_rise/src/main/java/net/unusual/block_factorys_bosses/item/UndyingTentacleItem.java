/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
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
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.UseAnim
 *  net.minecraft.world.item.component.ItemAttributeModifiers
 *  net.minecraft.world.item.component.ItemAttributeModifiers$Builder
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.material.PushReaction
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.neoforge.common.Tags$EntityTypes
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animatable.GeoItem
 *  software.bernie.geckolib.animatable.SingletonGeoAnimatable
 *  software.bernie.geckolib.animatable.client.GeoRenderProvider
 *  software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.animatable.stateless.StatelessAnimationController
 *  software.bernie.geckolib.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.animation.AnimationController
 *  software.bernie.geckolib.animation.RawAnimation
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package net.unusual.block_factorys_bosses.item;

import com.mojang.datafixers.util.Pair;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.Tags;
import net.unusual.block_factorys_bosses.attachment.entity.GauntletAttachment;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerAnimationHandler;
import net.unusual.block_factorys_bosses.entity.boss.kraken.summons.GhostTentacleEntity;
import net.unusual.block_factorys_bosses.geckolib.item.UndyingTentacleRenderer;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.util.SpatialUtil;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.stateless.StatelessAnimationController;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class UndyingTentacleItem
extends Item
implements GeoItem {
    public static final RawAnimation WHIP = RawAnimation.begin().thenPlay("animation.block_factory.br.undying_tentacle.tpp.ability1_start").thenPlay("animation.block_factory.br.undying_tentacle.tpp.ability1_end");
    public static final RawAnimation SUMMON_START = RawAnimation.begin().thenPlay("animation.block_factory.br.undying_tentacle.tpp.ability2_start").thenLoop("animation.block_factory.br.undying_tentacle.tpp.ability2_idle");
    public static final RawAnimation SUMMON_END = RawAnimation.begin().thenPlay("animation.block_factory.br.undying_tentacle.tpp.ability2_end");
    private static final double ITEM_RANGE = 40.0;
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);

    public UndyingTentacleItem() {
        super(new Item.Properties().durability(1000).rarity(Rarity.UNCOMMON).attributes(UndyingTentacleItem.createAttributes()));
        SingletonGeoAnimatable.registerSyncedAnimatable((GeoAnimatable)this);
    }

    public static ItemAttributeModifiers createAttributes() {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 2.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -3.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        builder.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(ResourceLocation.withDefaultNamespace((String)"base_entity_interaction_range"), 1.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        builder.add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(ResourceLocation.withDefaultNamespace((String)"base_block_interaction_range"), 1.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        return builder.build();
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        StatelessAnimationController controller = new StatelessAnimationController((GeoAnimatable)this, "main_controller");
        controller.triggerableAnim("whip", WHIP);
        controller.triggerableAnim("summon_start", SUMMON_START);
        controller.triggerableAnim("summon_end", SUMMON_END);
        controllers.add((AnimationController)controller);
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.animatableInstanceCache;
    }

    public int getEnchantmentValue(ItemStack stack) {
        return 1;
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null || !player.isCrouching() || context.getClickedFace() != Direction.UP) {
            return super.useOn(context);
        }
        return InteractionResult.PASS;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isSecondaryUseActive()) {
            player.startUsingItem(hand);
            this.triggerAnim((Entity)player, GeoItem.getId((ItemStack)stack), "main_controller", "summon_start");
            if (level instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)level;
                PlayerAnimationHandler.fromPlayer(player).startAnimation(player, GauntletAttachment.ATTACK_AND_HOLD);
                UndyingTentacleItem.playSound(serverLevel, player.getEyePosition(), BossesRiseSounds.UNDYING_TENTACLE_CHARGING);
            }
            return InteractionResultHolder.consume(stack);
        }
        this.triggerAnim((Entity)player, GeoItem.getId((ItemStack)stack), "main_controller", "whip");
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            player.getCooldowns().addCooldown((Item)this, 40);
            stack.hurtAndBreak(1, serverLevel, player instanceof ServerPlayer sp ? sp : null, item -> {});
            Vec3 eyePos = player.getEyePosition(1.0f);
            UndyingTentacleItem.playSound(serverLevel, eyePos, BossesRiseSounds.UNDYING_TENTACLE_THROW_FORTH);
            serverLevel.getEntities((Entity)player, player.getBoundingBox().inflate(40.0)).stream().map(entity -> {
                if (entity.getPistonPushReaction() != PushReaction.NORMAL || entity.getType().is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("c", "bosses")))) {
                    return null;
                }
                AABB box = entity.getBoundingBox();
                Vec3 closestPoint = new Vec3(Mth.clamp((double)eyePos.x, (double)box.minX, (double)box.maxX), Mth.clamp((double)eyePos.y, (double)box.minY, (double)box.maxY), Mth.clamp((double)eyePos.z, (double)box.minZ, (double)box.maxZ));
                double distance = closestPoint.distanceTo(eyePos);
                if (distance > 40.0) {
                    return null;
                }
                Vec3 scaled = eyePos.add(player.getViewVector(1.0f).scale(distance));
                return scaled.distanceTo(closestPoint) < 1.5 && player.hasLineOfSight(entity) ? Pair.of((Object)closestPoint, (Object)entity) : null;
            }).filter(Objects::nonNull).min(Comparator.comparingDouble(entity -> ((Vec3)entity.getFirst()).distanceTo(eyePos))).ifPresentOrElse(pair -> {
                UndyingTentacleItem.playSound(serverLevel, (Vec3)pair.getFirst(), BossesRiseSounds.UNDYING_TENTACLE_THROW_GRAB);
                Vec3 diff = eyePos.subtract((Vec3)pair.getFirst()).scale(0.25);
                ((Entity)pair.getSecond()).setDeltaMovement(diff);
            }, () -> {
                BlockHitResult blockHitResult;
                BlockHitResult hitresult = serverLevel.clip(new ClipContext(eyePos, eyePos.add(player.getViewVector(1.0f).scale(40.0)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity)player));
                if (hitresult instanceof BlockHitResult && (blockHitResult = hitresult).getType() != HitResult.Type.MISS) {
                    Vec3 diff = blockHitResult.getLocation().subtract(player.position()).scale(0.25);
                    UndyingTentacleItem.playSound(serverLevel, blockHitResult.getLocation(), BossesRiseSounds.UNDYING_TENTACLE_THROW_BACK);
                    SpatialUtil.pushEntity((Entity)player, diff);
                }
            });
        }
        return new InteractionResultHolder(InteractionResult.CONSUME_PARTIAL, (Object)stack);
    }

    protected static void playSound(ServerLevel level, Vec3 vec3, Holder<SoundEvent> sound) {
        level.playSound(null, vec3.x, vec3.y, vec3.z, (SoundEvent)sound.value(), SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 40;
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        this.triggerAnim((Entity)user, GeoItem.getId((ItemStack)stack), "main_controller", "summon_end");
        if (user instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)user;
            boolean spawnedAny = false;
            spawnedAny |= this.spawnGhost(player.serverLevel(), player, Direction.NORTH);
            spawnedAny |= this.spawnGhost(player.serverLevel(), player, Direction.EAST);
            spawnedAny |= this.spawnGhost(player.serverLevel(), player, Direction.SOUTH);
            if (!(spawnedAny |= this.spawnGhost(player.serverLevel(), player, Direction.WEST))) {
                player.getCooldowns().addCooldown((Item)this, 10);
                return stack;
            }
            player.swing(player.getUsedItemHand());
            player.getCooldowns().addCooldown((Item)this, 40);
            PlayerAnimationHandler.fromPlayer((Player)player).startAnimation((Player)player, GauntletAttachment.LAND, 12);
            UndyingTentacleItem.playSound(player.serverLevel(), player.getEyePosition(), BossesRiseSounds.UNDYING_TENTACLE_SMASH_GROUND);
            player.serverLevel().getEntities((Entity)player, player.getBoundingBox().inflate(3.0)).forEach(entity -> {
                if (entity.distanceTo((Entity)player) < 3.0f) {
                    entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, 3.0, 0.0));
                }
            });
        }
        return stack;
    }

    private boolean spawnGhost(ServerLevel level, ServerPlayer player, Direction direction) {
        Vec3 spawnPosition = player.position().relative(direction, 3.0);
        if (!level.noBlockCollision(null, ((EntityType)BossesRiseEntities.GHOST_TENTACLE.get()).getSpawnAABB(spawnPosition.x(), spawnPosition.y(), spawnPosition.z()))) {
            return false;
        }
        GhostTentacleEntity undyingTentacle = new GhostTentacleEntity((EntityType<GhostTentacleEntity>)((EntityType)BossesRiseEntities.GHOST_TENTACLE.get()), (Level)level);
        undyingTentacle.setOwnerUUID(player.getUUID());
        undyingTentacle.moveTo(spawnPosition, player.getYRot(), player.getXRot());
        level.addFreshEntity((Entity)undyingTentacle);
        undyingTentacle.triggerAnim("main_controller", "spawn");
        undyingTentacle.skipDropExperience();
        return true;
    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int timeLeft) {
        this.triggerAnim((Entity)user, GeoItem.getId((ItemStack)stack), "main_controller", "summon_end");
        if (user instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)user;
            player.getCooldowns().addCooldown((Item)this, 40);
            PlayerAnimationHandler.fromPlayer((Player)player).startAnimation((Player)player, GauntletAttachment.SPIKE_THROW, 9);
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        list.add((Component)Component.translatable((String)"item.block_factorys_bosses.undying_tentacle.description_0"));
        list.add((Component)Component.translatable((String)"item.block_factorys_bosses.undying_tentacle.description_1"));
        list.add((Component)Component.translatable((String)"item.block_factorys_bosses.undying_tentacle.description_2"));
        list.add((Component)Component.translatable((String)"item.block_factorys_bosses.undying_tentacle.description_3"));
    }

    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider(){
            @Nullable
            private UndyingTentacleRenderer renderer;

            public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                if (this.renderer == null) {
                    this.renderer = new UndyingTentacleRenderer();
                }
                return this.renderer;
            }
        });
    }
}

