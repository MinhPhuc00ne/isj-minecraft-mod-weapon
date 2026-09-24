/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.model.HumanoidModel$ArmPose
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.core.Direction
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.stats.Stats
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
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
 *  net.minecraft.world.item.component.ItemAttributeModifiers$Entry
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.extensions.common.IClientItemExtensions
 *  net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animatable.GeoItem
 *  software.bernie.geckolib.animatable.SingletonGeoAnimatable
 *  software.bernie.geckolib.animatable.client.GeoRenderProvider
 *  software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.animation.AnimationController
 *  software.bernie.geckolib.animation.PlayState
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package net.unusual.block_factorys_bosses.item;

import java.lang.runtime.SwitchBootstraps;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.SandColumnEntity;
import net.unusual.block_factorys_bosses.entity.projectile.PoisonSpitPrEntity;
import net.unusual.block_factorys_bosses.geckolib.item.SandwormGauntletRenderer;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.item.component.OperationMode;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

@EventBusSubscriber
@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SandwormGauntletItem
extends Item
implements GeoItem {
    private static final String MODE_EARTHQUAKE = "earthquake";
    private static final String MODE_POISON_BARRAGE = "poison_barrage";
    private static final ResourceLocation ATTRIBUTE_KEY_SLOW = BossesRise.prefix("sandworm_gauntlet/slow");
    private static final ResourceLocation ATTRIBUTE_KEY_FREEZE = BossesRise.prefix("sandworm_gauntlet/freeze");
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);

    public SandwormGauntletItem() {
        super(new Item.Properties().durability(1000).rarity(Rarity.UNCOMMON));
        SingletonGeoAnimatable.registerSyncedAnimatable((GeoAnimatable)this);
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController((GeoAnimatable)this, state -> PlayState.STOP));
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.animatableInstanceCache;
    }

    public int getEnchantmentValue(ItemStack stack) {
        return 1;
    }

    public int getBarColor(ItemStack stack) {
        return 9697806;
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    public int getUseTick(ItemStack stack, LivingEntity entity, int timeLeft) {
        return this.getUseDuration(stack, entity) - timeLeft;
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
        OperationMode.set(context.getItemInHand(), MODE_EARTHQUAKE);
        return InteractionResult.PASS;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (OperationMode.get(stack) == null) {
            OperationMode.set(stack, MODE_POISON_BARRAGE);
        }
        String string = OperationMode.get(stack);
        if (MODE_EARTHQUAKE.equals(string)) {
            this.onEarthquakeStart(stack);
        } else if (MODE_POISON_BARRAGE.equals(string)) {
            this.onPoisonBarrageStart(stack);
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    public void onUseTick(Level level, LivingEntity user, ItemStack stack, int timeLeft) {
        if (!(level instanceof ServerLevel)) {
            return;
        }
        ServerLevel serverLevel = (ServerLevel)level;
        if (OperationMode.isMode(stack, MODE_EARTHQUAKE)) {
            this.onUseTickWithEarthquake(stack, serverLevel, user, timeLeft);
        }
    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int timeLeft) {
        if (OperationMode.isMode(stack, MODE_POISON_BARRAGE)) {
            this.releaseUsingWithPoisonBarrage(stack, level, user, timeLeft);
        }
        this.endUse(stack);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        list.add((Component)Component.translatable((String)"item.block_factorys_bosses.sandworm_gauntlet.description_0"));
        list.add((Component)Component.translatable((String)"item.block_factorys_bosses.sandworm_gauntlet.description_1"));
    }

    private void endUse(ItemStack stack) {
        OperationMode.set(stack, null);
        ItemAttributeModifiers itemModifiers = stack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        List<ItemAttributeModifiers.Entry> modifiers = itemModifiers.modifiers().stream().filter(modifier -> {
            AttributeModifier id = modifier.modifier();
            return !id.is(ATTRIBUTE_KEY_FREEZE) && !id.is(ATTRIBUTE_KEY_SLOW);
        }).toList();
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, new ItemAttributeModifiers(modifiers, itemModifiers.showInTooltip()));
    }

    private void onEarthquakeStart(ItemStack stack) {
        ItemAttributeModifiers itemModifiers = stack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, itemModifiers.withModifierAdded(Attributes.MOVEMENT_SPEED, new AttributeModifier(ATTRIBUTE_KEY_FREEZE, Double.NEGATIVE_INFINITY, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.ANY).withModifierAdded(Attributes.JUMP_STRENGTH, new AttributeModifier(ATTRIBUTE_KEY_FREEZE, Double.NEGATIVE_INFINITY, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.ANY));
    }

    private void onUseTickWithEarthquake(ItemStack stack, ServerLevel level, LivingEntity user, int timeLeft) {
        int useTick = this.getUseTick(stack, user, timeLeft);
        int ticksActive = useTick - 30;
        if (ticksActive < 0) {
            return;
        }
        if (ticksActive % 20 != 0) {
            return;
        }
        stack.hurtAndBreak(10, level, user instanceof ServerPlayer sp ? sp : null, item -> {});
        double maxDistance = 14.0;
        double maxDistanceSquared = 196.0;
        double minDistance = 3.0;
        double minDistanceSquared = 9.0;
        List targetEntities = level.getEntitiesOfClass(LivingEntity.class, AABB.ofSize((Vec3)user.position(), (double)28.0, (double)8.0, (double)28.0), target -> {
            if (target == user) {
                return false;
            }
            double distanceSquared = target.distanceToSqr((Entity)user);
            return distanceSquared < 196.0 && distanceSquared > 9.0;
        });
        int targetCount = 4;
        ArrayList<Vec3> targetPositions = new ArrayList<Vec3>(targetCount);
        RandomSource random = RandomSource.create();
        while (!targetEntities.isEmpty() && targetPositions.size() < targetCount) {
            targetPositions.add(((LivingEntity)targetEntities.remove(random.nextInt(targetEntities.size()))).position());
        }
        while (targetPositions.size() < targetCount) {
            float angle = random.nextFloat() * ((float)Math.PI * 2);
            double distance = 3.0 + (double)random.nextFloat() * 11.0;
            targetPositions.add(user.position().add((double)Mth.sin((float)angle) * distance, 0.0, (double)Mth.cos((float)angle) * distance));
        }
        for (Vec3 targetPosition : targetPositions) {
            SandColumnEntity.spawnSandColumn(level, targetPosition, 0, user);
        }
    }

    private float getPoisonChargeForce(int usageTicks) {
        return Math.clamp((float)(0.5f + (float)(usageTicks - 7) / 5.0f), (float)0.5f, (float)1.0f);
    }

    private int getPoisonChargeCount(int usageTicks) {
        return Math.clamp((long)((usageTicks - 3 + 7) / 10), (int)0, (int)4);
    }

    private void onPoisonBarrageStart(ItemStack stack) {
        ItemAttributeModifiers itemModifiers = stack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, itemModifiers.withModifierAdded(Attributes.MOVEMENT_SPEED, new AttributeModifier(ATTRIBUTE_KEY_SLOW, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.ANY));
    }

    private void releaseUsingWithPoisonBarrage(ItemStack stack, Level level, LivingEntity user, int timeLeft) {
        int useTick = this.getUseTick(stack, user, timeLeft);
        int poisonChargeCount = this.getPoisonChargeCount(useTick);
        if (poisonChargeCount < 1) {
            return;
        }
        float poisonChargeForce = this.getPoisonChargeForce(useTick);
        user.playSound((SoundEvent)BossesRiseSounds.SANDWORM_GAUNTLET_POISON_BARRAGE_RELEASE.value());
        if (level.isClientSide()) {
            return;
        }
        Vec3 pos = user.getEyePosition();
        Vec3 direction = user.getViewVector(1.0f);
        for (int count = 0; count < poisonChargeCount; ++count) {
            PoisonSpitPrEntity projectile = new PoisonSpitPrEntity((EntityType<? extends PoisonSpitPrEntity>)((EntityType)BossesRiseEntities.POISON_SPIT_PR.get()), pos.x, pos.y, pos.z, level, stack);
            projectile.setOwner((Entity)user);
            projectile.setSilent(true);
            projectile.setBaseDamage(6.0);
            projectile.setKnockback(5);
            projectile.shoot(direction.x, direction.y, direction.z, poisonChargeForce * 1.5f, 8.0f + (float)poisonChargeCount * 5.0f);
            level.addFreshEntity((Entity)projectile);
        }
        if (user instanceof Player) {
            Player player = (Player)user;
            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }

    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider(){
            @Nullable
            private SandwormGauntletRenderer renderer;

            public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                if (this.renderer == null) {
                    this.renderer = new SandwormGauntletRenderer();
                }
                return this.renderer;
            }
        });
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem((IClientItemExtensions)new SandwormGauntletItemClientExtensions(), new Item[]{(Item)BossesRiseItems.SANDWORM_GAUNTLET.get()});
    }

    public static class SandwormGauntletItemClientExtensions
    implements IClientItemExtensions {
        @Nullable
        public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
            return HumanoidModel.ArmPose.BOW_AND_ARROW;
        }
    }
}

