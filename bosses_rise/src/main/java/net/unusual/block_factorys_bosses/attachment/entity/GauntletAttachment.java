/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.commands.arguments.EntityAnchorArgument$Anchor
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.OwnableEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.common.util.INBTSerializable
 *  net.neoforged.neoforge.event.entity.living.LivingEvent$LivingJumpEvent
 *  net.neoforged.neoforge.event.entity.living.LivingFallEvent
 *  net.neoforged.neoforge.event.entity.player.PlayerFlyableFallEvent
 *  net.neoforged.neoforge.event.tick.PlayerTickEvent$Post
 *  net.neoforged.neoforge.network.PacketDistributor
 *  org.jetbrains.annotations.UnknownNullability
 */
package net.unusual.block_factorys_bosses.attachment.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.player.PlayerFlyableFallEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerAnimationHandler;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeClusterEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeProjectileEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.YetiEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseDataAttachments;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.item.IceGauntletItem;
import net.unusual.block_factorys_bosses.network.IceGauntletMessage;
import net.unusual.block_factorys_bosses.util.SpatialUtil;
import org.jetbrains.annotations.UnknownNullability;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GauntletAttachment
implements INBTSerializable<CompoundTag> {
    public static final int SOUND_CHANCE = 5;
    public static final long SHOT_DELAY = 2L;
    public static final float LERP = 0.5f;
    protected static final ResourceLocation PATH = BossesRise.prefix("animations/player/frozen_gauntlet.animation.json");
    public static final PlayerAnimationHandler.QueuedPlayerAnimation ATTACK_AND_HOLD = new PlayerAnimationHandler.QueuedPlayerAnimation(List.of(new PlayerAnimationHandler.TimedPlayerAnimation(new PlayerAnimationHandler.PlayerAnimation(PATH, "animation.charge_attack_start", 0.5f), 6), new PlayerAnimationHandler.TimedPlayerAnimation(new PlayerAnimationHandler.PlayerAnimation(PATH, "animation.charge_attack_hold", 0.5f, true), 4)));
    public static final PlayerAnimationHandler.PlayerAnimation ATTACK_END = new PlayerAnimationHandler.PlayerAnimation(PATH, "animation.charge_attack", 0.5f);
    public static final PlayerAnimationHandler.QueuedPlayerAnimation GRAB_AND_HOLD = new PlayerAnimationHandler.QueuedPlayerAnimation(List.of(new PlayerAnimationHandler.TimedPlayerAnimation(new PlayerAnimationHandler.PlayerAnimation(PATH, "animation.grab_icespike", 0.5f), 4), new PlayerAnimationHandler.TimedPlayerAnimation(new PlayerAnimationHandler.PlayerAnimation(PATH, "animation.hold_icespike", 0.5f, true), 8)));
    public static final PlayerAnimationHandler.PlayerAnimation SPIKE_THROW = new PlayerAnimationHandler.PlayerAnimation(PATH, "animation.throw_icespike", 0.5f);
    public static final PlayerAnimationHandler.QueuedPlayerAnimation JUMP_AND_HOLD = new PlayerAnimationHandler.QueuedPlayerAnimation(List.of(new PlayerAnimationHandler.TimedPlayerAnimation(new PlayerAnimationHandler.PlayerAnimation(PATH, "animation.yeti_gaunltet_leap_smash_start", 0.5f), 11), new PlayerAnimationHandler.TimedPlayerAnimation(new PlayerAnimationHandler.PlayerAnimation(PATH, "animation.yeti_gaunltet_leap_smash_loop_air", 0.5f, true), 5)));
    public static final PlayerAnimationHandler.PlayerAnimation LAND = new PlayerAnimationHandler.PlayerAnimation(PATH, "animation.yeti_gauntlet_leap_smash_hit", 0.5f);
    protected boolean active = false;
    protected UUID[] shards = new UUID[4];
    protected long lastShotTick = 0L;

    public static GauntletAttachment fromPlayer(Player player) {
        return net.neoforged.neoforge.attachment.AttachmentHolder.getData(player, BossesRiseDataAttachments.GAUNTLET_ATTACHMENT);
    }

    public UUID[] getShards() {
        return this.shards;
    }

    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("active", this.active);
        if (this.shards[0] != null) {
            tag.putUUID("shard_0", this.shards[0]);
        }
        if (this.shards[1] != null) {
            tag.putUUID("shard_1", this.shards[1]);
        }
        if (this.shards[2] != null) {
            tag.putUUID("shard_2", this.shards[2]);
        }
        if (this.shards[3] != null) {
            tag.putUUID("shard_3", this.shards[3]);
        }
        tag.putLong("last_shot", this.lastShotTick);
        return tag;
    }

    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        this.active = tag.getBoolean("active");
        if (tag.contains("shard_0")) {
            this.shards[0] = tag.getUUID("shard_0");
        }
        if (tag.contains("shard_1")) {
            this.shards[1] = tag.getUUID("shard_1");
        }
        if (tag.contains("shard_2")) {
            this.shards[2] = tag.getUUID("shard_2");
        }
        if (tag.contains("shard_3")) {
            this.shards[3] = tag.getUUID("shard_3");
        }
        this.lastShotTick = tag.getLong("last_shot");
    }

    public int getNewShardIndex(RandomSource random) {
        ArrayList options = new ArrayList();
        for (int i = 0; i < this.shards.length; ++i) {
            if (this.shards[i] != null) continue;
            options.add(i);
        }
        if (options.isEmpty()) {
            return -1;
        }
        return options.size() > 1 ? (Integer)options.get(random.nextInt(options.size())) : (Integer)options.getFirst();
    }

    public boolean isHoldingAnyShards() {
        for (int i = 0; i < this.shards.length; ++i) {
            if (this.shards[i] == null) continue;
            return true;
        }
        return false;
    }

    @SubscribeEvent
    private static void tickEvent(PlayerTickEvent.Post event) {
        GauntletAttachment.fromPlayer(event.getEntity()).tick(event.getEntity());
    }

    public void tick(Player player) {
        block11: {
            int i;
            if (!(player instanceof ServerPlayer)) break block11;
            ServerPlayer serverPlayer = (ServerPlayer)player;
            boolean hasShard = false;
            boolean hasSpace = false;
            for (i = 0; i < this.shards.length; ++i) {
                if (this.shards[i] != null) {
                    if (serverPlayer.serverLevel().getEntity(this.shards[i]) instanceof IceSpikeClusterEntity) {
                        hasShard = true;
                        continue;
                    }
                    this.shards[i] = null;
                    hasSpace = true;
                    continue;
                }
                hasSpace = true;
            }
            if (hasShard) {
                if (serverPlayer.isUsingItem() && serverPlayer.getUseItem().is((Item)BossesRiseItems.ICE_GAUNTLET.get())) {
                    if (!hasSpace) {
                        return;
                    }
                    for (IceSpikeClusterEntity spike : serverPlayer.level().getEntitiesOfClass(IceSpikeClusterEntity.class, AABB.ofSize((Vec3)player.getEyePosition().add(player.getViewVector(1.0f)), (double)3.0, (double)2.0, (double)3.0))) {
                        if (spike.getHeld()) continue;
                        int index = this.getNewShardIndex(player.getRandom());
                        if (index != -1) {
                            spike.getEntityData().set(IceSpikeClusterEntity.DATA_REMAINING_HIT, index);
                            this.shards[index] = spike.getUUID();
                            serverPlayer.serverLevel().sendParticles((ParticleOptions)ParticleTypes.SNOWFLAKE, spike.getX(), spike.getY(), spike.getZ(), 15, 0.25, 0.5, 0.25, 0.0);
                            serverPlayer.serverLevel().playSound(null, spike.blockPosition(), (SoundEvent)BossesRiseSounds.ICICLE_PICKUP.value(), SoundSource.PLAYERS, 0.9f, (float)Mth.nextDouble((RandomSource)player.getRandom(), (double)0.15, (double)0.35));
                            spike.setHeld(true);
                            spike.setOwnerUUID(player.getUUID());
                            continue;
                        }
                        break;
                    }
                } else {
                    PlayerAnimationHandler.fromPlayer(player).startAnimation(player, SPIKE_THROW, 9);
                    if (player.level().getGameTime() - this.lastShotTick < 2L) {
                        return;
                    }
                    for (i = 0; i < this.shards.length; ++i) {
                        Entity index;
                        if (this.shards[i] == null || !((index = serverPlayer.serverLevel().getEntity(this.shards[i])) instanceof IceSpikeClusterEntity)) continue;
                        IceSpikeClusterEntity spikeCluster = (IceSpikeClusterEntity)index;
                        Vec3 viewVector = player.getViewVector(1.0f);
                        IceSpikeProjectileEntity spike = new IceSpikeProjectileEntity((EntityType<? extends IceSpikeProjectileEntity>)((EntityType)BossesRiseEntities.ICE_SPIKE_PR.get()), (LivingEntity)player, player.level());
                        spike.setPos(player.getEyePosition());
                        spike.shoot(viewVector.x, viewVector.y, viewVector.z, 4.0f, i * 5);
                        spike.setSilent(true);
                        spike.setBaseDamage(4.0);
                        spike.setKnockback(8);
                        player.level().addFreshEntity((Entity)spike);
                        serverPlayer.serverLevel().playSound(null, spikeCluster.blockPosition(), (SoundEvent)BossesRiseSounds.THROW_ICICLE.value(), SoundSource.PLAYERS, 0.9f, (float)Mth.nextDouble((RandomSource)spikeCluster.getRandom(), (double)1.15, (double)1.75));
                        spikeCluster.discard();
                        this.shards[i] = null;
                        this.lastShotTick = player.level().getGameTime();
                        break;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    private static void preFall(LivingFallEvent event) {
        ServerPlayer player;
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof ServerPlayer && GauntletAttachment.onFall(player = (ServerPlayer)livingEntity)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    private static void preFlyingFall(PlayerFlyableFallEvent event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer) {
            ServerPlayer player2 = (ServerPlayer)player;
            GauntletAttachment.onFall(player2);
        }
    }

    public static boolean onFall(ServerPlayer player) {
        GauntletAttachment attachment = GauntletAttachment.fromPlayer((Player)player);
        if (attachment.active) {
            PlayerAnimationHandler.fromPlayer((Player)player).startAnimation((Player)player, LAND, 12);
            attachment.active = false;
            GauntletAttachment.iceBurst((LivingEntity)player, player.position(), player.serverLevel(), 8.0f, 40, 3.0f, (random, integer) -> random.nextInt(10) == 0 || integer < 4);
            player.getCooldowns().addCooldown((Item)BossesRiseItems.ICE_GAUNTLET.get(), 200);
            player.level().getEntities((Entity)player, player.getBoundingBox().inflate(2.0), entity1 -> {
                OwnableEntity ownable;
                if (entity1 == player) {
                    return false;
                }
                if (entity1 instanceof OwnableEntity && (ownable = (OwnableEntity)entity1).getOwner() == player) {
                    return false;
                }
                return entity1.isAlive() && !entity1.isSpectator() && !(entity1 instanceof IceSpikeEntity) && !(entity1 instanceof IceSpikeClusterEntity);
            }).forEach(entity -> {
                entity.hurt(entity.level().damageSources().freeze(), 10.0f);
                entity.setTicksFrozen(200);
            });
            return true;
        }
        return false;
    }

    @SubscribeEvent
    private static void onJump(LivingEvent.LivingJumpEvent event) {
        ServerPlayer player;
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof ServerPlayer && (player = (ServerPlayer)livingEntity).isUsingItem() && player.getUseItem().is(BossesRiseItems.ICE_GAUNTLET) && !player.getCooldowns().isOnCooldown((Item)BossesRiseItems.ICE_GAUNTLET.get())) {
            GauntletAttachment attachment = GauntletAttachment.fromPlayer((Player)player);
            if (!attachment.active && !attachment.isHoldingAnyShards()) {
                attachment.active = true;
                player.getUseItem().hurtAndBreak(10, (LivingEntity)player, player.getEquipmentSlotForItem(player.getUseItem()));
                player.stopUsingItem();
                PlayerAnimationHandler.fromPlayer((Player)player).startAnimation((Player)player, JUMP_AND_HOLD);
                player.getCooldowns().addCooldown((Item)BossesRiseItems.ICE_GAUNTLET.get(), 400);
                SpatialUtil.pushEntity((Entity)player, player.getViewVector(1.0f).add(0.0, 2.0, 0.0));
            }
        }
    }

    public static void iceBurst(LivingEntity living, Vec3 center, ServerLevel level, float range, int count, float damage, BiFunction<RandomSource, Integer, Boolean> cluster) {
        Vec3 viewVector = living.getViewVector(1.0f);
        if (living.getXRot() > 85.0f) {
            viewVector = living.calculateViewVector(85.0f, living.getViewYRot(1.0f));
        } else if (living.getXRot() < -85.0f) {
            viewVector = living.calculateViewVector(-85.0f, living.getViewYRot(1.0f));
        }
        Vec3 diff = new Vec3(viewVector.x, 0.0, viewVector.z).normalize().scale((double)range);
        PacketDistributor.sendToPlayersTrackingEntityAndSelf((Entity)living, (CustomPacketPayload)new IceGauntletMessage(center, diff.length()), (CustomPacketPayload[])new CustomPacketPayload[0]);
        for (int j = 0; j < count; ++j) {
            Vec3 hit;
            Vec3 wanted;
            net.unusual.block_factorys_bosses.entity.boss.yeti.IIceSpike spike; Entity spikeEntity;
            if (cluster.apply(level.random, j).booleanValue()) {
                IceSpikeClusterEntity clusterSpike = new IceSpikeClusterEntity((EntityType<IceSpikeClusterEntity>)((EntityType)BossesRiseEntities.ICE_SPIKE_CLUSTER.get()), (Level)level); spikeEntity = clusterSpike; spike = clusterSpike;
                wanted = diff.scale((double)(j + 1) / 35.0 + 0.25).yRot(360.0f * level.random.nextFloat() * ((float)Math.PI / 180));
                hit = IceGauntletItem.placeProper(level, living, center.add(wanted), living.getEyePosition());
                if (hit == null) continue;
                spikeEntity.moveTo(hit);
                spike.setDelay((int)Math.sqrt(16 + j));
                spike.setDamage(damage);
                spike.setOwnerUUID(living.getUUID());
                spike.setEvil(living instanceof YetiEntity);
                if (j % 5 != 0) {
                    spikeEntity.setSilent(true);
                }
                level.addFreshEntity(spikeEntity);
                spikeEntity.lookAt(EntityAnchorArgument.Anchor.FEET, spikeEntity.position().add(wanted.add(0.0, wanted.length() * (double)Mth.randomBetween((RandomSource)level.random, (float)0.75f, (float)1.0f), 0.0)));
                continue;
            }
            IceSpikeEntity regSpike = new IceSpikeEntity((EntityType<IceSpikeEntity>)((EntityType)BossesRiseEntities.ICE_SPIKE.get()), (Level)level); spikeEntity = regSpike; spike = regSpike;
            wanted = diff.scale((double)(j + 1) / 35.0 + 0.25).yRot(360.0f * level.random.nextFloat() * ((float)Math.PI / 180));
            hit = IceGauntletItem.placeProper(level, living, center.add(wanted), living.getEyePosition());
            if (hit == null) continue;
            spikeEntity.moveTo(hit);
            spike.setScale(j / (count / 4) + 1);
            spike.setDelay((int)Math.sqrt(spike.getDelay() + j));
            spike.setDamage(damage);
            spike.setOwnerUUID(living.getUUID());
            spike.setEvil(living instanceof YetiEntity);
            if (j % 5 != 0) {
                spikeEntity.setSilent(true);
            }
            level.addFreshEntity(spikeEntity);
            spikeEntity.lookAt(EntityAnchorArgument.Anchor.FEET, spikeEntity.position().add(wanted.add(0.0, wanted.length() * (double)Mth.randomBetween((RandomSource)level.random, (float)0.75f, (float)1.0f), 0.0)));
        }
    }

    public static void delayedBurst(LivingEntity living, Vec3 center, ServerLevel level, float range, int count, float damage, int delay) {
        Vec3 viewVector = living.getViewVector(1.0f);
        if (living.getXRot() > 85.0f) {
            viewVector = living.calculateViewVector(85.0f, living.getViewYRot(1.0f));
        } else if (living.getXRot() < -85.0f) {
            viewVector = living.calculateViewVector(-85.0f, living.getViewYRot(1.0f));
        }
        Vec3 diff = new Vec3(viewVector.x, 0.0, viewVector.z).normalize().scale((double)range);
        PacketDistributor.sendToPlayersTrackingEntityAndSelf((Entity)living, (CustomPacketPayload)new IceGauntletMessage(center, diff.length()), (CustomPacketPayload[])new CustomPacketPayload[0]);
        for (int j = 0; j < count; ++j) {
            Vec3 hit;
            Vec3 wanted;
            net.unusual.block_factorys_bosses.entity.boss.yeti.IIceSpike spike; Entity spikeEntity;
            if (level.random.nextInt(10) == 0) {
                IceSpikeClusterEntity clusterSpike = new IceSpikeClusterEntity((EntityType<IceSpikeClusterEntity>)((EntityType)BossesRiseEntities.ICE_SPIKE_CLUSTER.get()), (Level)level); spikeEntity = clusterSpike; spike = clusterSpike;
                wanted = diff.scale((double)(j + 1) / 35.0 + 0.25).yRot(360.0f * level.random.nextFloat() * ((float)Math.PI / 180));
                hit = IceGauntletItem.placeProper(level, living, center.add(wanted), living.getEyePosition());
                if (hit == null) continue;
                spikeEntity.moveTo(hit);
                spike.setDelay(delay);
                spike.setDamage(damage);
                spike.setOwnerUUID(living.getUUID());
                spike.setEvil(living instanceof YetiEntity);
                if (j % 5 != 0) {
                    spikeEntity.setSilent(true);
                }
                level.addFreshEntity(spikeEntity);
                spikeEntity.lookAt(EntityAnchorArgument.Anchor.FEET, spikeEntity.position().add(wanted.add(0.0, wanted.length() * (double)Mth.randomBetween((RandomSource)level.random, (float)0.75f, (float)1.0f), 0.0)));
                continue;
            }
            IceSpikeEntity regSpike = new IceSpikeEntity((EntityType<IceSpikeEntity>)((EntityType)BossesRiseEntities.ICE_SPIKE.get()), (Level)level); spikeEntity = regSpike; spike = regSpike;
            wanted = diff.scale((double)(j + 1) / 35.0 + 0.25).yRot(360.0f * level.random.nextFloat() * ((float)Math.PI / 180));
            hit = IceGauntletItem.placeProper(level, living, center.add(wanted), living.getEyePosition());
            if (hit == null) continue;
            spikeEntity.moveTo(hit);
            spike.setScale(j / (count / 4) + 1);
            spike.setDelay(delay);
            spike.setDamage(damage);
            spike.setOwnerUUID(living.getUUID());
            spike.setEvil(living instanceof YetiEntity);
            if (j % 5 != 0) {
                spikeEntity.setSilent(true);
            }
            level.addFreshEntity(spikeEntity);
            spikeEntity.lookAt(EntityAnchorArgument.Anchor.FEET, spikeEntity.position().add(wanted.add(0.0, wanted.length() * (double)Mth.randomBetween((RandomSource)level.random, (float)0.75f, (float)1.0f), 0.0)));
        }
    }

    public static void iceWave(LivingEntity living, ServerLevel level, float range, int count, Vec3 center, Vec3 angle, float damage, float speed) {
        Vec3 diff = new Vec3(angle.x, 0.0, angle.z).normalize().scale((double)range);
        PacketDistributor.sendToPlayersTrackingEntityAndSelf((Entity)living, (CustomPacketPayload)new IceGauntletMessage(center, diff.length()), (CustomPacketPayload[])new CustomPacketPayload[0]);
        level.sendParticles((ParticleOptions)ParticleTypes.SNOWFLAKE, center.x, center.y, center.z, 15, 2.0, 0.5, 2.0, 0.0);
        for (int j = 0; j < count; ++j) {
            Vec3 hit;
            Vec3 wanted;
            net.unusual.block_factorys_bosses.entity.boss.yeti.IIceSpike spike; Entity spikeEntity;
            if (level.random.nextInt(10) == 0) {
                IceSpikeClusterEntity clusterSpike = new IceSpikeClusterEntity((EntityType<IceSpikeClusterEntity>)((EntityType)BossesRiseEntities.ICE_SPIKE_CLUSTER.get()), (Level)level); spikeEntity = clusterSpike; spike = clusterSpike;
                wanted = diff.scale((double)(j + 1) / 35.0).yRot((80.0f * level.random.nextFloat() - 40.0f) * ((float)Math.PI / 180));
                hit = IceGauntletItem.placeProper(level, living, center.add(wanted), living.getEyePosition());
                if (hit == null) continue;
                spikeEntity.moveTo(hit);
                spike.setDelay(16 + (int)((float)j / speed));
                spike.setDamage(damage);
                spike.setOwnerUUID(living.getUUID());
                spike.setEvil(living instanceof YetiEntity);
                if (j % 5 != 0) {
                    spikeEntity.setSilent(true);
                }
                level.addFreshEntity(spikeEntity);
                spikeEntity.lookAt(EntityAnchorArgument.Anchor.FEET, spikeEntity.position().add(wanted.add(0.0, wanted.length() * (double)Mth.randomBetween((RandomSource)level.random, (float)0.75f, (float)1.0f), 0.0)));
                continue;
            }
            IceSpikeEntity regSpike = new IceSpikeEntity((EntityType<IceSpikeEntity>)((EntityType)BossesRiseEntities.ICE_SPIKE.get()), (Level)level); spikeEntity = regSpike; spike = regSpike;
            wanted = diff.scale((double)(j + 1) / 35.0).yRot((80.0f * level.random.nextFloat() - 40.0f) * ((float)Math.PI / 180));
            hit = IceGauntletItem.placeProper(level, living, center.add(wanted), living.getEyePosition());
            if (hit == null) continue;
            spikeEntity.moveTo(hit);
            spike.setScale(j / 8 + 1);
            spike.setDelay(spike.getDelay() + (int)((float)j / speed));
            spike.setDamage(damage);
            spike.setOwnerUUID(living.getUUID());
            spike.setEvil(living instanceof YetiEntity);
            if (j % 5 != 0) {
                spikeEntity.setSilent(true);
            }
            level.addFreshEntity(spikeEntity);
            spikeEntity.lookAt(EntityAnchorArgument.Anchor.FEET, spikeEntity.position().add(wanted.add(0.0, wanted.length() * (double)Mth.randomBetween((RandomSource)level.random, (float)0.75f, (float)1.0f), 0.0)));
        }
    }
}

