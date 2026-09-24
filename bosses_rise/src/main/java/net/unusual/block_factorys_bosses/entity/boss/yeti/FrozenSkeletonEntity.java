/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.commands.arguments.EntityAnchorArgument$Anchor
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.Level
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animatable.GeoEntity
 *  software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.animation.AnimationController
 *  software.bernie.geckolib.animation.RawAnimation
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package net.unusual.block_factorys_bosses.entity.boss.yeti;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FrozenSkeletonEntity
extends Entity
implements GeoEntity {
    public static final EntityDataAccessor<Integer> DATA_HITS = SynchedEntityData.defineId(FrozenSkeletonEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_ANIM = SynchedEntityData.defineId(FrozenSkeletonEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_HIT_ANIMATION_TIME = SynchedEntityData.defineId(FrozenSkeletonEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    public static final RawAnimation POSE_1 = RawAnimation.begin().thenLoop("animation.bf_br.frozen_skeleton.pose_1");
    public static final RawAnimation POSE_2 = RawAnimation.begin().thenLoop("animation.bf_br.frozen_skeleton.pose_2");
    public static final RawAnimation POSE_3 = RawAnimation.begin().thenLoop("animation.bf_br.frozen_skeleton.pose_3");

    public FrozenSkeletonEntity(EntityType<FrozenSkeletonEntity> type, Level level) {
        super(type, level);
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController((GeoAnimatable)this, "main_controller", 3, state -> switch ((Integer)this.entityData.get(DATA_ANIM)) {
            case 1 -> state.setAndContinue(POSE_1);
            case 2 -> state.setAndContinue(POSE_2);
            default -> state.setAndContinue(POSE_3);
        }));
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_HITS, 0);
        builder.define(DATA_ANIM, 0);
        builder.define(DATA_HIT_ANIMATION_TIME, 0);
    }

    public InteractionResult interact(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).is(Items.DEBUG_STICK)) {
            this.lookAt(EntityAnchorArgument.Anchor.EYES, player.getEyePosition());
            return InteractionResult.SUCCESS;
        }
        return super.interact(player, hand);
    }

    public void baseTick() {
        super.baseTick();
        Level level = this.level();
        if (!(level instanceof ServerLevel)) {
            return;
        }
        ServerLevel level2 = (ServerLevel)level;
        if ((Integer)this.getEntityData().get(DATA_HIT_ANIMATION_TIME) > 0) {
            this.getEntityData().set(DATA_HIT_ANIMATION_TIME, ((Integer)this.getEntityData().get(DATA_HIT_ANIMATION_TIME) - 1));
        }
    }

    public boolean hurt(DamageSource damagesource, float amount) {
        if (this.level().isClientSide) {
            return false;
        }
        if (damagesource.getWeaponItem() != null && damagesource.getWeaponItem().is(Items.DEBUG_STICK)) {
            this.entityData.set(DATA_ANIM, (((Integer)this.entityData.get(DATA_ANIM) + 1) % 3));
            return false;
        }
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        this.playSound(SoundEvents.BONE_BLOCK_BREAK);
        if ((Integer)this.getEntityData().get(DATA_HIT_ANIMATION_TIME) == 0) {
            int health = (Integer)this.getEntityData().get(DATA_HITS);
            if (health <= 3) {
                this.getEntityData().set(DATA_HITS, (health + 1));
                this.level().playSound(null, BlockPos.containing((double)x, (double)y, (double)z), SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL, 1.0f, 3.0f);
                this.getEntityData().set(DATA_HIT_ANIMATION_TIME, 8);
            } else {
                this.level().playSound(null, BlockPos.containing((double)x, (double)y, (double)z), SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL, 1.0f, 2.0f);
                this.remove(Entity.RemovalReason.KILLED);
            }
            return true;
        }
        return false;
    }

    public boolean isPickable() {
        return true;
    }

    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("health", ((Integer)this.entityData.get(DATA_HITS)).intValue());
        tag.putInt("hurt_animation", ((Integer)this.entityData.get(DATA_HIT_ANIMATION_TIME)).intValue());
        tag.putInt("anim", ((Integer)this.entityData.get(DATA_ANIM)).intValue());
    }

    protected void readAdditionalSaveData(CompoundTag tag) {
        this.entityData.set(DATA_HITS, tag.getInt("health"));
        this.entityData.set(DATA_HIT_ANIMATION_TIME, tag.getInt("hurt_animation"));
        this.entityData.set(DATA_ANIM, tag.getInt("anim"));
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }
}

