/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.decoration.BlockAttachedEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Explosion
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.neoforge.registries.DeferredItem
 *  org.jetbrains.annotations.Nullable
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animatable.GeoEntity
 *  software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package net.unusual.block_factorys_bosses.entity.decoration;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.BlockAttachedEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class AnchorEntity
extends BlockAttachedEntity
implements GeoEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    protected static final EntityDataAccessor<Integer> HURT_ANIMATION_TIMER = SynchedEntityData.defineId(AnchorEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> HURT_ANIMATION_DIRECTION = SynchedEntityData.defineId(AnchorEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(AnchorEntity.class, (EntityDataSerializer)EntityDataSerializers.FLOAT);
    private final DeferredItem<Item> item;
    @Nullable
    protected BlockPos attachment = null;
    protected int attachDiff = -1;

    public AnchorEntity(EntityType<? extends AnchorEntity> type, Level world, DeferredItem<Item> item) {
        super(type, world);
        this.item = item;
    }

    public AABB getBoundingBoxForCulling() {
        return super.getBoundingBoxForCulling().inflate(2.0);
    }

    public void setAttachDiff(int attachDiff) {
        this.attachDiff = attachDiff;
    }

    @Nullable
    public ItemStack getPickResult() {
        return this.item.toStack();
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(HURT_ANIMATION_TIMER, 0);
        builder.define(HURT_ANIMATION_DIRECTION, 1);
        builder.define(DAMAGE, Float.valueOf(0.0f));
    }

    public void setHurtTime(int hurtTime) {
        this.entityData.set(HURT_ANIMATION_TIMER, hurtTime);
    }

    public void setHurtDir(int hurtDir) {
        this.entityData.set(HURT_ANIMATION_DIRECTION, hurtDir);
    }

    public void setDamage(float damage) {
        this.entityData.set(DAMAGE, Float.valueOf(damage));
    }

    public float getDamage() {
        return ((Float)this.entityData.get(DAMAGE)).floatValue();
    }

    public int getHurtTime() {
        return (Integer)this.entityData.get(HURT_ANIMATION_TIMER);
    }

    public int getHurtDir() {
        return (Integer)this.entityData.get(HURT_ANIMATION_DIRECTION);
    }

    public boolean causeFallDamage(float l, float d, DamageSource source) {
        return false;
    }

    protected void recalculateBoundingBox() {
        Vec3 center = this.position();
        double width = this.getBbWidth();
        double height = this.getBbHeight();
        this.setBoundingBox(new AABB(center.x - width / 2.0, center.y, center.z - width / 2.0, center.x + width / 2.0, center.y + height, center.z + width / 2.0));
    }

    public boolean survives() {
        return !this.level().getBlockState(this.blockPosition().relative(Direction.UP, this.attachDiff)).canBeReplaced();
    }

    public void tick() {
        if (this.getHurtTime() > 0) {
            this.setHurtTime(this.getHurtTime() - 1);
        }
        if (this.getDamage() > 0.0f) {
            this.setDamage(this.getDamage() - 1.0f);
        }
        super.tick();
    }

    public boolean skipAttackInteraction(Entity entity) {
        return false;
    }

    public boolean hurt(DamageSource source, float amount) {
        boolean creativePlayer;
        Player p;
        Player sourcePlayer;
        if (this.level().isClientSide || this.isRemoved()) {
            return true;
        }
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        Entity entity = source.getEntity();
        Player player = sourcePlayer = entity instanceof Player ? (p = (Player)entity) : null;
        if (sourcePlayer != null && !sourcePlayer.getAbilities().mayBuild) {
            return false;
        }
        this.setHurtDir(-this.getHurtDir());
        this.setHurtTime(10);
        this.markHurt();
        this.setDamage(this.getDamage() + amount * 10.0f);
        this.gameEvent((Holder)GameEvent.ENTITY_DAMAGE, source.getEntity());
        boolean bl = creativePlayer = sourcePlayer != null && sourcePlayer.getAbilities().instabuild;
        if (creativePlayer || !(this.getDamage() > 40.0f)) {
            if (creativePlayer) {
                this.discard();
            }
        } else {
            this.kill();
            this.dropItem((Entity)sourcePlayer);
        }
        return true;
    }

    public boolean ignoreExplosion(Explosion explosion) {
        return true;
    }

    public boolean fireImmune() {
        return true;
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("hurt", ((Integer)this.entityData.get(HURT_ANIMATION_TIMER)).intValue());
        compound.putInt("hurt_dir", ((Integer)this.entityData.get(HURT_ANIMATION_DIRECTION)).intValue());
        compound.putFloat("damage", ((Float)this.entityData.get(DAMAGE)).floatValue());
        compound.putInt("attachment", this.attachDiff);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("hurt")) {
            this.entityData.set(HURT_ANIMATION_TIMER, compound.getInt("hurt"));
        }
        if (compound.contains("hurt_dir")) {
            this.entityData.set(HURT_ANIMATION_DIRECTION, compound.getInt("hurt_dir"));
        }
        if (compound.contains("damage")) {
            this.entityData.set(DAMAGE, Float.valueOf(compound.getFloat("damage")));
        }
        if (compound.contains("attachment")) {
            this.attachDiff = compound.getInt("attachment");
        }
        this.pos = this.blockPosition();
    }

    public void dropItem(@Nullable Entity entity) {
        if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            Player player;
            this.playSound(SoundEvents.METAL_HIT, 1.0f, 1.0f);
            if (entity instanceof Player && (player = (Player)entity).hasInfiniteMaterials()) {
                return;
            }
            this.spawnAtLocation((ItemLike)this.item.get());
        }
    }

    public boolean canCollideWith(Entity entity) {
        return true;
    }

    public boolean canBeCollidedWith() {
        return true;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }
}

