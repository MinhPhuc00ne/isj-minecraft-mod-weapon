/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.RecipeInput
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.item.crafting.SingleRecipeInput
 *  net.minecraft.world.item.crafting.SmeltingRecipe
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.material.PushReaction
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package net.unusual.block_factorys_bosses.entity;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;

public class FireAreaEntity
extends Entity {
    private static final String TAG_LIVING_TIME = "Dataliving_time";
    public static final EntityDataAccessor<Integer> DATA_LIVING_TIME = SynchedEntityData.defineId(FireAreaEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    private static final String TAG_COOKING_TIME = "block_factorys_bosses:cook_time";

    public FireAreaEntity(EntityType<FireAreaEntity> type, Level world) {
        super(type, world);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_LIVING_TIME, 130);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt(TAG_LIVING_TIME, ((Integer)this.entityData.get(DATA_LIVING_TIME)).intValue());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains(TAG_LIVING_TIME)) {
            this.entityData.set(DATA_LIVING_TIME, compound.getInt(TAG_LIVING_TIME));
        }
    }

    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            List<LivingEntity> touchingEntities = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox());
            for (LivingEntity touchingEntity : touchingEntities) {
                if (touchingEntity.fireImmune()) continue;
                touchingEntity.hurt(this.damageSources().inFire(), 1.5f);
                touchingEntity.igniteForSeconds(4.0f);
            }
        }
    }

    public void baseTick() {
        super.baseTick();
        Level level = this.level();
        this.setDeltaMovement(new Vec3(0.0, 0.0, 0.0));
        if ((Integer)this.getEntityData().get(DATA_LIVING_TIME) > 0) {
            this.getEntityData().set(DATA_LIVING_TIME, ((Integer)this.getEntityData().get(DATA_LIVING_TIME) - 1));
        } else if (!level.isClientSide()) {
            this.discard();
        }
        if (this.isInWaterOrBubble() && !level.isClientSide()) {
            this.discard();
        }
        if ((Integer)this.getEntityData().get(DATA_LIVING_TIME) > 25 && level instanceof ServerLevel) {
            ServerLevel _level = (ServerLevel)level;
            _level.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.FIRE_AOE.get()), this.getX(), this.getY() + 0.05, this.getZ(), 4, 1.0, 0.0, 1.0, 0.0);
            _level.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.BLAZING_FLAME.get()), this.getX(), this.getY() + 0.05, this.getZ(), 4, 1.0, 0.0, 1.0, 0.0);
        }
        Vec3 center = this.position();
        List<ItemEntity> nearbyItemEntities = level.getEntitiesOfClass(ItemEntity.class, AABB.ofSize((Vec3)center, (double)1.5, (double)1.5, (double)1.5), e -> true).stream().sorted(Comparator.comparingDouble(entity -> entity.distanceToSqr(center))).toList();
        for (ItemEntity itemEntity : nearbyItemEntities) {
            Optional<net.minecraft.world.item.crafting.RecipeHolder<SmeltingRecipe>> smeltingRecipe = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(itemEntity.getItem()), level);
            if (smeltingRecipe.isEmpty()) continue;
            CompoundTag persistentData = net.unusual.block_factorys_bosses.util.EntityPersistentData.get(itemEntity);
            if (persistentData.getInt(TAG_COOKING_TIME) < 40) {
                persistentData.putInt(TAG_COOKING_TIME, persistentData.getInt(TAG_COOKING_TIME) + 1);
                level.addParticle((ParticleOptions)BossesRiseParticleTypes.FANCY_SMOKE.get(), itemEntity.getX(), itemEntity.getY() + 0.3, itemEntity.getZ(), (Math.random() - 0.5) * 0.8, 1.0, (Math.random() - 0.5) * 0.8);
                continue;
            }
            persistentData.putInt(TAG_COOKING_TIME, 0);
            ItemStack smeltedItem = smeltingRecipe.map(recipe -> ((SmeltingRecipe)recipe.value()).getResultItem((HolderLookup.Provider)level.registryAccess()).copy()).orElseThrow();
            ItemEntity smeltedItemEntity = new ItemEntity(level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), smeltedItem);
            smeltedItemEntity.setPickUpDelay(5);
            level.addFreshEntity((Entity)smeltedItemEntity);
            if (!level.isClientSide()) {
                itemEntity.playSound((SoundEvent)BossesRiseSounds.FIRE_SMELTED_ITEM.value(), 0.2f, 2.0f);
            }
            itemEntity.getItem().shrink(1);
        }
    }

    @Nonnull
    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }
}

