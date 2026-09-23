/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MoverType
 *  net.minecraft.world.entity.TraceableEntity
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animatable.GeoEntity
 *  software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.animation.AnimationController
 *  software.bernie.geckolib.animation.PlayState
 *  software.bernie.geckolib.animation.RawAnimation
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package net.unusual.block_factorys_bosses.entity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.unusual.block_factorys_bosses.geckolib.DangerZonesProvider;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

@ParametersAreNonnullByDefault
public class SandColumnEntity
extends Entity
implements GeoEntity,
TraceableEntity,
DangerZonesProvider {
    public static final String TAG_DELAY = "delay";
    public static final String TAG_OWNER = "owner";
    public static final EntityDataAccessor<Boolean> DATA_ERUPTING = SynchedEntityData.defineId(SandColumnEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private static final String EMERGE_ANIM_NAME = "emerge";
    private static final RawAnimation EMERGE_ANIM = RawAnimation.begin().thenPlayAndHold("emerge");
    private static final int EMERGE_DURATION = 15;
    private static final DangerZonesProvider.DangerZone DANGER_ZONE = new DangerZonesProvider.DangerZone().setSize(1.8f, 1.8f);
    private static final List<DangerZonesProvider.DangerZone> DANGER_ZONE_LIST = List.of(DANGER_ZONE);
    private int delay = 0;
    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUUID;

    public SandColumnEntity(EntityType<SandColumnEntity> entityType, Level level) {
        super(entityType, level);
    }

    public SandColumnEntity(Level level, double x, double y, double z, @Nullable LivingEntity owner, int delay) {
        this((EntityType<SandColumnEntity>)((EntityType)BossesRiseEntities.SAND_COLUMN.get()), level);
        this.setPos(x, y, z);
        this.setDelay(delay);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.setOwner(owner);
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide() && !((Boolean)this.getEntityData().get(DATA_ERUPTING)).booleanValue()) {
            return;
        }
        int delay = this.getDelay();
        this.setDelay(delay - 1);
        if (delay == 0) {
            this.getEntityData().set(DATA_ERUPTING, true);
            this.triggerAnim("base_controller", EMERGE_ANIM_NAME);
            this.playSound((SoundEvent)BossesRiseSounds.SAND_COLUMN_ERUPTION.value(), 1.0f, 1.0f);
        } else if (delay == -2) {
            for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox())) {
                this.damageEntity(entity);
            }
        } else if (delay <= -15) {
            this.discard();
        }
        if (delay <= 0 && delay >= -3) {
            int index;
            for (index = 0; index < 10; ++index) {
                this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.GROUND_DUST.get(), this.getX(), this.getY() + 0.1, this.getZ(), (Math.random() - 0.5) * 4.0, Math.random() * 2.0, (Math.random() - 0.5) * 4.0);
            }
            for (index = 0; index < 5; ++index) {
                this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.DUST_CLOUD.get(), this.getX(), this.getY() + 0.1, this.getZ(), (Math.random() - 0.5) * 2.0, Math.random() * 2.0, (Math.random() - 0.5) * 2.0);
                this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.GROUND_DUST.get(), this.getX(), this.getY() + 0.1, this.getZ(), (Math.random() - 0.5) * 2.5, Math.random() * 4.0, (Math.random() - 0.5) * 2.5);
            }
        }
    }

    private void damageEntity(LivingEntity target) {
        Level level;
        DamageSource damageSource;
        if (!target.isAlive() || target.isInvulnerable()) {
            return;
        }
        LivingEntity owner = this.getOwner();
        if (target == owner) {
            return;
        }
        Vec3 direction = target.position().subtract(this.position()).normalize().multiply(1.0, 0.2, 1.0).add(0.0, 1.4, 0.0);
        target.push(direction);
        if (owner == null) {
            target.hurt(this.damageSources().magic(), 5.0f);
        } else if (!owner.isAlliedTo((Entity)target) && target.hurt(damageSource = this.damageSources().indirectMagic((Entity)this, (Entity)owner), 5.0f) && (level = this.level()) instanceof ServerLevel) {
            ServerLevel level2 = (ServerLevel)level;
            EnchantmentHelper.doPostAttackEffects((ServerLevel)level2, (Entity)target, (DamageSource)damageSource);
        }
    }

    @Override
    public Collection<DangerZonesProvider.DangerZone> getDangerZones() {
        if (this.isErupting()) {
            return List.of();
        }
        return DANGER_ZONE_LIST;
    }

    public boolean isErupting() {
        if (this.level().isClientSide()) {
            return ((AnimationController)this.getAnimatableInstanceCache().getManagerForId((long)this.getId()).getAnimationControllers().get("base_controller")).isPlayingTriggeredAnimation();
        }
        return this.delay <= 0;
    }

    @Nullable
    public LivingEntity getOwner() {
        ServerLevel level;
        Entity entity;
        Level level2;
        if (this.owner == null && this.ownerUUID != null && (level2 = this.level()) instanceof ServerLevel && (entity = (level = (ServerLevel)level2).getEntity(this.ownerUUID)) instanceof LivingEntity) {
            LivingEntity livingEntity;
            this.owner = livingEntity = (LivingEntity)entity;
        }
        return this.owner;
    }

    public void setOwner(@Nullable LivingEntity owner) {
        this.ownerUUID = owner == null ? null : owner.getUUID();
        this.owner = owner;
    }

    public int getDelay() {
        return this.delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
        this.getEntityData().set(DATA_ERUPTING, this.delay <= 0);
    }

    public boolean isPickable() {
        return false;
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_ERUPTING, false);
    }

    protected void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains(TAG_DELAY)) {
            this.setDelay(compound.getInt(TAG_DELAY));
        }
        if (compound.hasUUID(TAG_OWNER)) {
            this.ownerUUID = compound.getUUID(TAG_OWNER);
        }
    }

    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt(TAG_DELAY, this.getDelay());
        if (this.ownerUUID != null) {
            compound.putUUID(TAG_OWNER, this.ownerUUID);
        }
    }

    public void move(MoverType type, Vec3 pos) {
    }

    public void push(double x, double y, double z) {
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController((GeoAnimatable)this, state -> PlayState.STOP).triggerableAnim(EMERGE_ANIM_NAME, EMERGE_ANIM));
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    @Nullable
    public static SandColumnEntity spawnSandColumn(ServerLevel level, Vec3 position, int delay, @Nullable LivingEntity owner) {
        BlockHitResult groundHitResult = level.clip(new ClipContext(new Vec3(position.x(), position.y() + 6.0, position.z()), new Vec3(position.x(), position.y() - 8.0, position.z()), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty()));
        if (groundHitResult.getType() == HitResult.Type.MISS) {
            return null;
        }
        Vec3 columnPos = groundHitResult.getLocation();
        SandColumnEntity columnEntity = new SandColumnEntity((Level)level, columnPos.x, columnPos.y, columnPos.z, owner, delay);
        level.addFreshEntity((Entity)columnEntity);
        return columnEntity;
    }
}

