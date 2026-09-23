/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.advancements.AdvancementHolder
 *  net.minecraft.advancements.AdvancementProgress
 *  net.minecraft.commands.arguments.EntityAnchorArgument$Anchor
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.PlayerAdvancements
 *  net.minecraft.server.ServerAdvancementManager
 *  net.minecraft.server.level.ServerBossEvent
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.DamageTypeTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.fml.loading.FMLEnvironment
 *  net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector3d
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animatable.GeoEntity
 *  software.bernie.geckolib.animatable.SingletonGeoAnimatable
 *  software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package net.unusual.block_factorys_bosses.entity.boss;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerVariables;
import net.unusual.block_factorys_bosses.entity.IParticleAttachment;
import net.unusual.block_factorys_bosses.entity.SlowRotMoveControl;
import net.unusual.block_factorys_bosses.init.BossesRiseDataAttachments;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.init.BossesRiseTags;
import net.unusual.block_factorys_bosses.network.ShutUpPacket;
import net.unusual.block_factorys_bosses.network.UpdateBossBarTypeMessage;
import net.unusual.block_factorys_bosses.state.StateController;
import net.unusual.block_factorys_bosses.util.BossHandling;
import net.unusual.block_factorys_bosses.util.RiseUtil;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class AbstractStateBossEntity
extends Monster
implements GeoEntity,
IParticleAttachment {
    public static final EntityDataAccessor<Integer> DATA_BOSS_PHASE = SynchedEntityData.defineId(AbstractStateBossEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_ATTACK_PHASE = SynchedEntityData.defineId(AbstractStateBossEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_SPAWN_ANIMTIME = SynchedEntityData.defineId(AbstractStateBossEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_ATTACK_COOLDOWN = SynchedEntityData.defineId(AbstractStateBossEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_ATTACK_ANIMTIME = SynchedEntityData.defineId(AbstractStateBossEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_HIT_ANIMTIME = SynchedEntityData.defineId(AbstractStateBossEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_BATTLE_TIME = SynchedEntityData.defineId(AbstractStateBossEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_DIE_ANIMTIME = SynchedEntityData.defineId(AbstractStateBossEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<String> DATA_SOURCE_UUID = SynchedEntityData.defineId(AbstractStateBossEntity.class, (EntityDataSerializer)EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Boolean> DATA_CINEMATIC_BLACK_SCREEN = SynchedEntityData.defineId(AbstractStateBossEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    public static final int INTRO_PHASE = -1;
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    protected final ServerBossEvent bossEvent;

    protected AbstractStateBossEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 0;
        this.setNoAi(false);
        this.setPersistenceRequired();
        this.moveControl = new SlowRotMoveControl((Mob)this);
        SingletonGeoAnimatable.registerSyncedAnimatable((GeoAnimatable)this);
        this.bossEvent = this.createBossEvent();
    }

    public abstract <T extends AbstractStateBossEntity> StateController<T> getStateController();

    protected abstract ServerBossEvent createBossEvent();

    protected abstract int getBossType();

    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        if (BossHandling.TRACKED_BOSSES.get(this.bossEvent.getId()) == null) {
            BossHandling.TRACKED_BOSSES.put(this.bossEvent.getId(), new BossHandling.TrackedBoss(this.getBossType(), true));
        }
        this.bossEvent.addPlayer(player);
    }

    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    public void remove(Entity.RemovalReason reason) {
        BossHandling.TRACKED_BOSSES.remove(this.bossEvent.getId());
        super.remove(reason);
    }

    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    public void setPlayingMusic(boolean music) {
        BossHandling.TrackedBoss boss;
        if (!this.level().isClientSide && (boss = BossHandling.TRACKED_BOSSES.get(this.bossEvent.getId())) != null && boss.playingMusic() != music) {
            BossHandling.TrackedBoss augmented = boss.setMusic(music);
            BossHandling.TRACKED_BOSSES.put(this.bossEvent.getId(), augmented);
            this.bossEvent.getPlayers().forEach(player -> net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.send(player, new UpdateBossBarTypeMessage(this.bossEvent.getId(), augmented)));
        }
    }

    public void setBarVisible(boolean visible) {
        if (!this.level().isClientSide) {
            this.bossEvent.setVisible(visible);
        }
    }

    public void onBossSpawnerSpawn() {
    }

    protected Vec3 toVec3(Vector3d vec) {
        return new Vec3(vec.x, vec.y, vec.z);
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_BOSS_PHASE, 0);
        builder.define(DATA_ATTACK_PHASE, 0);
        builder.define(DATA_SPAWN_ANIMTIME, 0);
        builder.define(DATA_ATTACK_COOLDOWN, 0);
        builder.define(DATA_ATTACK_ANIMTIME, 0);
        builder.define(DATA_HIT_ANIMTIME, 0);
        builder.define(DATA_BATTLE_TIME, 0);
        builder.define(DATA_DIE_ANIMTIME, 0);
        builder.define(DATA_SOURCE_UUID, "");
        builder.define(DATA_CINEMATIC_BLACK_SCREEN, false);
    }

    public int getBossPhase() {
        return (Integer)this.getEntityData().get(DATA_BOSS_PHASE);
    }

    public void setBossPhase(int phase) {
        this.getEntityData().set(DATA_BOSS_PHASE, phase);
    }

    public void incrementBattleTime() {
        this.entityData.set(DATA_BATTLE_TIME, ((Integer)this.entityData.get(DATA_BATTLE_TIME) + 1));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        this.getStateController().addAdditionalSaveData(compound);
        compound.putInt("BossPhase", ((Integer)this.entityData.get(DATA_BOSS_PHASE)).intValue());
        compound.putInt("AttackPhase", ((Integer)this.entityData.get(DATA_ATTACK_PHASE)).intValue());
        compound.putInt("SpawnAnimtime", ((Integer)this.entityData.get(DATA_SPAWN_ANIMTIME)).intValue());
        compound.putInt("AttackCooldown", ((Integer)this.entityData.get(DATA_ATTACK_COOLDOWN)).intValue());
        compound.putInt("AttackAnimtime", ((Integer)this.entityData.get(DATA_ATTACK_ANIMTIME)).intValue());
        compound.putInt("BattleTime", ((Integer)this.entityData.get(DATA_BATTLE_TIME)).intValue());
        compound.putInt("DieAnimtime", ((Integer)this.entityData.get(DATA_DIE_ANIMTIME)).intValue());
        compound.putString("SourceUUID", (String)this.entityData.get(DATA_SOURCE_UUID));
        compound.putBoolean("CinematicBlack", ((Boolean)this.entityData.get(DATA_CINEMATIC_BLACK_SCREEN)).booleanValue());
        compound.putBoolean("BossBarVisible", this.bossEvent.isVisible());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.getStateController().readAdditionalSaveData(compound);
        if (!compound.contains("BossPhase")) {
            return;
        }
        this.entityData.set(DATA_BOSS_PHASE, compound.getInt("BossPhase"));
        this.entityData.set(DATA_ATTACK_PHASE, compound.getInt("AttackPhase"));
        this.entityData.set(DATA_SPAWN_ANIMTIME, compound.getInt("SpawnAnimtime"));
        this.entityData.set(DATA_ATTACK_COOLDOWN, compound.getInt("AttackCooldown"));
        this.entityData.set(DATA_ATTACK_ANIMTIME, compound.getInt("AttackAnimtime"));
        this.entityData.set(DATA_BATTLE_TIME, compound.getInt("BattleTime"));
        this.entityData.set(DATA_DIE_ANIMTIME, compound.getInt("DieAnimtime"));
        this.entityData.set(DATA_SOURCE_UUID, compound.getString("SourceUUID"));
        this.entityData.set(DATA_CINEMATIC_BLACK_SCREEN, compound.getBoolean("CinematicBlack"));
        RiseUtil.ifBoolPresent(compound, "BossBarVisible", arg_0 -> ((ServerBossEvent)this.bossEvent).setVisible(arg_0));
    }

    public void tick() {
        super.tick();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    public boolean getCinematicBlackScreen() {
        return (Boolean)this.getEntityData().get(DATA_CINEMATIC_BLACK_SCREEN);
    }

    public void setCinematicBlackScreen(boolean enabled) {
        this.getEntityData().set(DATA_CINEMATIC_BLACK_SCREEN, enabled);
    }

    public int getAnimTransitionTime() {
        return 0;
    }

    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    public static void init(RegisterSpawnPlacementsEvent event) {
    }

    public void playSound(SoundEvent sound, float volume, float pitch) {
        if (!this.isSilent()) {
            this.stopSoundForNearbyPlayers(sound);
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), sound, SoundSource.HOSTILE, volume, pitch);
        }
    }

    public void playSound(SoundEvent sound) {
        this.playSound(sound, 8.0f, 1.0f);
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent)BossesRiseSounds.SILENCE.value();
    }

    protected abstract ResourceLocation getNoHitAdvancement();

    protected abstract ResourceLocation getKillAdvancement();

    public double getHealthRatio() {
        return this.getHealth() / this.getMaxHealth();
    }

    public void log(Object message) {
        if (!this.level().isClientSide() && !FMLEnvironment.production) {
            BossesRise.LOGGER.debug(message);
        }
    }

    protected void actuallyHurt(DamageSource damageSource, float damageAmount) {
        super.actuallyHurt(damageSource, damageAmount);
        this.maybeCancelDeath(damageSource);
    }

    protected void maybeCancelDeath(DamageSource damageContainer) {
        if (!this.isDeadOrDying()) {
            return;
        }
        if (damageContainer.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return;
        }
        if (!this.shouldCancelDeath()) {
            return;
        }
        if (this.isDeadOrDying()) {
            this.setHealth(0.1f);
        }
        this.cancelDeath();
    }

    public boolean shouldCancelDeath() {
        return false;
    }

    protected void cancelDeath() {
    }

    public void die(DamageSource source) {
        super.die(source);
        Level level = this.level();
        if (level instanceof ServerLevel) {
            ServerLevel world = (ServerLevel)level;
            for (Player p : world.getNearbyPlayers(TargetingConditions.DEFAULT, (LivingEntity)this, AABB.ofSize((Vec3)this.position(), (double)256.0, (double)256.0, (double)256.0))) {
                AdvancementProgress progress;
                AdvancementProgress progress2;
                ServerPlayer player = (ServerPlayer)p;
                PlayerAdvancements playerAdvancements = player.getAdvancements();
                ServerAdvancementManager advancementManager = world.getServer().getAdvancements();
                AdvancementHolder killBossUnderMinute = advancementManager.get(BossesRise.prefix("kill_boss_under_minute"));
                AdvancementHolder bossNoHit = advancementManager.get(this.getNoHitAdvancement());
                AdvancementHolder killAdvancement = advancementManager.get(this.getKillAdvancement());
                if (killBossUnderMinute != null && (Integer)this.getEntityData().get(DATA_BATTLE_TIME) <= 1200 && !(progress2 = playerAdvancements.getOrStartProgress(killBossUnderMinute)).isDone()) {
                    for (String criteria : progress2.getRemainingCriteria()) {
                        playerAdvancements.award(killBossUnderMinute, criteria);
                    }
                }
                PlayerVariables playerVars = net.neoforged.neoforge.attachment.AttachmentHolder.getData(player, BossesRiseDataAttachments.PLAYER_VARIABLES);
                if (bossNoHit != null && playerVars.boss_no_hit && !(progress = playerAdvancements.getOrStartProgress(bossNoHit)).isDone()) {
                    for (String criteria : progress.getRemainingCriteria()) {
                        playerAdvancements.award(bossNoHit, criteria);
                    }
                }
                if (killAdvancement != null && !(progress = playerAdvancements.getOrStartProgress(killAdvancement)).isDone()) {
                    for (String criteria : progress.getRemainingCriteria()) {
                        playerAdvancements.award(killAdvancement, criteria);
                    }
                }
                playerVars.boss_no_hit = true;
                playerVars.syncPlayerVariables((Entity)player);
            }
            this.discard();
        }
    }

    @Nullable
    public ItemEntity spawnAtLocation(ItemStack itemStack, float yOffset) {
        ItemEntity itemEntity = super.spawnAtLocation(itemStack, yOffset);
        if (itemEntity != null && itemStack.is(BossesRiseTags.Items.GLOWING_LOOT)) {
            itemEntity.setGlowingTag(true);
        }
        return itemEntity;
    }

    public void lookAtTarget() {
        if (this.getTarget() instanceof LivingEntity) {
            LivingEntity target = this.getTarget();
            this.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(target.getX(), target.getY() + (double)target.getBbHeight() * 0.5, target.getZ()));
        }
    }

    public void forEachNearbyEntity(double aabbSize, Vec3 offset, Predicate<Entity> filter, Consumer<LivingEntity> callback) {
        this.level().getEntitiesOfClass(LivingEntity.class, AABB.ofSize((Vec3)this.position().add(offset), (double)aabbSize, (double)aabbSize, (double)aabbSize), e -> e != this && filter.test((Entity)e)).forEach(callback);
    }

    public void forEachNearbyEntity(double aabbSize, Vec3 offset, Consumer<LivingEntity> callback) {
        this.forEachNearbyEntity(aabbSize, offset, e -> true, callback);
    }

    public void forEachNearbyEntity(double aabbSize, Consumer<LivingEntity> callback) {
        this.forEachNearbyEntity(aabbSize, Vec3.ZERO, e -> true, callback);
    }

    public void forEachNearbyPlayer(double aabbSize, Consumer<Player> callback) {
        this.level().getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)this.position(), (double)aabbSize, (double)aabbSize, (double)aabbSize)).forEach(callback);
    }

    protected void stopSoundForNearbyPlayers(SoundEvent sound) {
        Level level = this.level();
        if (!(level instanceof ServerLevel)) {
            return;
        }
        ServerLevel serverLevel = (ServerLevel)level;
        if (BossesRiseSounds.CANCELABLE_SOUNDS.contains(sound.getLocation())) {
            ShutUpPacket shutUpPacket = new ShutUpPacket();
            serverLevel.getEntitiesOfClass(ServerPlayer.class, this.getBoundingBox().inflate(64.0)).forEach(player -> net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.send(player, shutUpPacket));
        }
    }

    protected List<Player> getNearbyPlayers(double aabbSize) {
        return this.level().getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)this.position(), (double)aabbSize, (double)aabbSize, (double)aabbSize));
    }

    public boolean attackEntity(Entity target, float damageScale) {
        return this.attackEntity(target, damageScale, this.damageSources().mobAttack((LivingEntity)this));
    }

    public boolean attackEntity(Entity target, float damageScale, DamageSource damageSource) {
        ServerLevel level;
        if (damageScale <= 0.0f) {
            return false;
        }
        Level level2 = this.level();
        ServerLevel serverLevel = level2 instanceof ServerLevel ? (level = (ServerLevel)level2) : null;
        float damage = damageScale * (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        if (serverLevel != null) {
            damage = EnchantmentHelper.modifyDamage((ServerLevel)serverLevel, (ItemStack)this.getWeaponItem(), (Entity)target, (DamageSource)damageSource, (float)damage);
        }
        if (!target.hurt(damageSource, damage)) {
            return false;
        }
        if (serverLevel != null) {
            EnchantmentHelper.doPostAttackEffects((ServerLevel)serverLevel, (Entity)target, (DamageSource)damageSource);
        }
        this.setLastHurtMob(target);
        return true;
    }

    public void meleeAttack(double aabbSize, double reach, double pushForce, float damageScale, Consumer<LivingEntity> callback) {
        this.forEachNearbyEntity(aabbSize, this.getLookOffset(reach), entity -> {
            if (!this.attackEntity((Entity)entity, damageScale)) {
                return;
            }
            Vec3 pushForceVec = entity.position().subtract(this.position()).normalize().scale(pushForce);
            entity.push(pushForceVec.x, 0.2, pushForceVec.z);
            callback.accept((LivingEntity)entity);
        });
    }

    public void meleeAttack(double aabbSize, double reach, double pushForce, Consumer<LivingEntity> callback) {
        this.meleeAttack(aabbSize, reach, pushForce, 1.0f, callback);
    }

    public void meleeAttack(double aabbSize, double reach, double pushForce, float damageScale) {
        this.meleeAttack(aabbSize, reach, pushForce, damageScale, e -> {});
    }

    public void meleeAttack(double aabbSize, double reach, double pushForce) {
        this.meleeAttack(aabbSize, reach, pushForce, 1.0f, e -> {});
    }

    public Vec3 getLookOffset(double length, double height) {
        float f = -this.getYRot() * ((float)Math.PI / 180);
        float x = Mth.sin((float)f);
        float z = Mth.cos((float)f);
        return new Vec3((double)x * length, height, (double)z * length);
    }

    public Vec3 getLookOffset(double length) {
        return this.getLookOffset(length, 1.5);
    }

    public boolean isTargetNear(double aabbSize) {
        return this.getTarget() instanceof LivingEntity && AABB.ofSize((Vec3)this.position(), (double)aabbSize, (double)aabbSize, (double)aabbSize).contains(this.getTarget().position());
    }

    public LivingEntity getSourceEntity() {
        try {
            Level level = this.level();
            if (level instanceof ServerLevel serverLevel) {
                Entity e = serverLevel.getEntity(UUID.fromString((String)this.entityData.get(DATA_SOURCE_UUID)));
                if (e instanceof LivingEntity living) {
                    return living;
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (this.getTarget() instanceof LivingEntity) {
            return this.getTarget();
        }
        return this;
    }

    public void simulatePlayerKill() {
        LivingEntity source = this.getSourceEntity();
        if (source instanceof Player player) {
            this.hurt(this.damageSources().playerAttack(player), 999.0f);
        } else {
            this.hurt(this.damageSources().mobAttack(source), 999.0f);
        }
    }

    public void handleParticleEvent(String name) {
    }
}

