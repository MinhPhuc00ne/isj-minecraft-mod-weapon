/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Explosion
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  org.joml.Vector3f
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animatable.GeoEntity
 *  software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package net.unusual.block_factorys_bosses.entity.boss.yeti;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.unusual.block_factorys_bosses.attachment.entity.RollAttachment;
import net.unusual.block_factorys_bosses.entity.OwnableByAllEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.YetiEntity;
import net.unusual.block_factorys_bosses.geckolib.DangerZonesProvider;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.util.SpatialUtil;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GlacialShoveEntity
extends Entity
implements GeoEntity,
OwnableByAllEntity,
DangerZonesProvider {
    protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID = SynchedEntityData.defineId(GlacialShoveEntity.class, (EntityDataSerializer)EntityDataSerializers.OPTIONAL_UUID);
    public static final EntityDataAccessor<Integer> DATA_TARGET = SynchedEntityData.defineId(GlacialShoveEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_DELAY = SynchedEntityData.defineId(GlacialShoveEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final int HIT_TICK = 31;
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private final DangerZonesProvider.DangerZone zone = new DangerZonesProvider.DangerZone();
    public boolean shouldSetAttack = true;

    public GlacialShoveEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.zone.setSize(3.0f, 3.0f);
        this.zone.setOffset(new Vector3f(0.0f, 0.0f, 0.0f));
        this.zone.setColor(0xFF0000);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_OWNER_UUID, Optional.empty());
        builder.define(DATA_TARGET, -1);
        builder.define(DATA_DELAY, 31);
    }

    public void setTarget(LivingEntity target) {
        this.entityData.set(DATA_TARGET, target.getId());
    }

    public void setDelay(int delay) {
        this.entityData.set(DATA_DELAY, delay);
    }

    public int getDelay() {
        return (Integer)this.entityData.get(DATA_DELAY);
    }

    @Nullable
    public LivingEntity getTarget() {
        LivingEntity living;
        Entity entity;
        int target = (Integer)this.entityData.get(DATA_TARGET);
        return target != -1 && (entity = this.level().getEntity(target)) instanceof LivingEntity ? (living = (LivingEntity)entity) : null;
    }

    public Vec3 position() {
        LivingEntity target;
        if (this.tickCount < this.getDelay() && (target = this.getTarget()) != null) {
            Vec3 pos = target.position();
            BlockHitResult clip = this.level().clip(new ClipContext(pos, pos.subtract(0.0, 10.0, 0.0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty()));
            return clip.getLocation();
        }
        return super.position();
    }

    public void tick() {
        super.tick();
        LivingEntity target = this.getTarget();
        if (this.tickCount < this.getDelay() && target != null) {
            Vec3 pos = target.position();
            BlockHitResult clip = this.level().clip(new ClipContext(pos, pos.subtract(0.0, 10.0, 0.0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty()));
            this.setPos(clip.getLocation());
        } else if (this.tickCount == this.getDelay()) {
            this.playSound((SoundEvent)BossesRiseSounds.YETI_RANGED_CONTROL_ICE_BLOCK.value(), 1.0f, 0.4f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
            LivingEntity owner = this.getOwner();
            if (owner instanceof YetiEntity) {
                YetiEntity yeti = (YetiEntity)owner;
                boolean worked = false;
                for (Entity entity : this.level().getEntities((Entity)this, this.getBoundingBox())) {
                    Vec3 diff;
                    if (entity instanceof ServerPlayer) {
                        ServerPlayer player = (ServerPlayer)entity;
                        if (RollAttachment.fromPlayer((Player)player).isInvulnerable()) continue;
                        diff = owner.position().subtract(player.position()).scale((double)0.05f).add(0.0, 0.5, 0.0).scale(3.0);
                        SpatialUtil.pushEntity((Entity)player, diff);
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
                        worked = true;
                        continue;
                    }
                    if (!(entity instanceof LivingEntity)) continue;
                    LivingEntity living = (LivingEntity)entity;
                    diff = owner.position().subtract(living.position()).scale((double)0.05f).add(0.0, 0.5, 0.0).scale(3.0);
                    living.push(diff);
                    living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
                    worked = true;
                }
                if (!this.level().isClientSide && this.shouldSetAttack) {
                    if (worked) {
                        yeti.attackBuffer = YetiEntity.YetiState.PUNCH.attackOrDefault();
                    } else {
                        yeti.doAttack(YetiEntity.YetiState.LEAP_SMASH.attackOrDefault());
                    }
                }
            }
        } else if (this.tickCount > 180) {
            this.discard();
        }
    }

    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.hasUUID("owner")) {
            this.setOwnerUUID(tag.getUUID("owner"));
        }
        this.tickCount = tag.getInt("tick_count");
    }

    protected void addAdditionalSaveData(CompoundTag tag) {
        if (this.getOwnerUUID() != null) {
            tag.putUUID("Owner", this.getOwnerUUID());
        }
        tag.putInt("tick_count", this.tickCount);
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    @Nullable
    public UUID getOwnerUUID() {
        return ((Optional<UUID>)this.entityData.get(DATA_OWNER_UUID)).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID uuid) {
        this.entityData.set(DATA_OWNER_UUID, Optional.ofNullable(uuid));
    }

    public boolean ignoreExplosion(Explosion explosion) {
        return true;
    }

    public boolean fireImmune() {
        return true;
    }

    public boolean canCollideWith(Entity entity) {
        return this.tickCount > this.getDelay();
    }

    public boolean canBeCollidedWith() {
        return this.tickCount > this.getDelay();
    }

    @Override
    public Collection<DangerZonesProvider.DangerZone> getDangerZones() {
        if (this.tickCount < this.getDelay()) {
            return List.of(this.zone);
        }
        return List.of();
    }
}

