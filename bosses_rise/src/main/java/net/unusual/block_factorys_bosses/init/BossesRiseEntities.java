/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EntityType$Builder
 *  net.minecraft.world.entity.MobCategory
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent
 *  net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent
 *  net.neoforged.neoforge.registries.DeferredHolder
 *  net.neoforged.neoforge.registries.DeferredRegister
 */
package net.unusual.block_factorys_bosses.init;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unusual.block_factorys_bosses.entity.FireAreaEntity;
import net.unusual.block_factorys_bosses.entity.PoisonAreaEntity;
import net.unusual.block_factorys_bosses.entity.RiftEntity;
import net.unusual.block_factorys_bosses.entity.SandColumnEntity;
import net.unusual.block_factorys_bosses.entity.boss.dragon.boss.InfernalDragonEntity;
import net.unusual.block_factorys_bosses.entity.boss.dragon.guardians.DragonGuardSwordEntity;
import net.unusual.block_factorys_bosses.entity.boss.dragon.guardians.FlamingSkeletonGuardFireballEntity;
import net.unusual.block_factorys_bosses.entity.boss.dragon.guardians.FlamingSkeletonGuardSwordEntity;
import net.unusual.block_factorys_bosses.entity.boss.knight.KnightMarkEntity;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenCinematicEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenTentacleEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.summons.CrossbowPirateEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.summons.GhostTentacleEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.summons.PirateCaptainEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.summons.PirateRookEntity;
import net.unusual.block_factorys_bosses.entity.boss.sandworm.SandwormEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.FrozenSkeletonEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.GlacialShoveEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeClusterEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeProjectileEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.YetiEntity;
import net.unusual.block_factorys_bosses.entity.decoration.AnchorEntity;
import net.unusual.block_factorys_bosses.entity.decoration.CageEntity;
import net.unusual.block_factorys_bosses.entity.decoration.CannonEntity;
import net.unusual.block_factorys_bosses.entity.decoration.CratePileEntity;
import net.unusual.block_factorys_bosses.entity.decoration.PileOfBonesEntity;
import net.unusual.block_factorys_bosses.entity.monster.SoulKnightWitherSkeletonEntity;
import net.unusual.block_factorys_bosses.entity.monster.SoulSkeletonEntity;
import net.unusual.block_factorys_bosses.entity.projectile.BigRiftProjectileEntity;
import net.unusual.block_factorys_bosses.entity.projectile.BlazingFireBallEntity;
import net.unusual.block_factorys_bosses.entity.projectile.CannonballEntity;
import net.unusual.block_factorys_bosses.entity.projectile.PoisonSpitPrEntity;
import net.unusual.block_factorys_bosses.entity.projectile.RiftProjectileEntity;
import net.unusual.block_factorys_bosses.entity.projectile.SoulShockwaveEntity;
import net.unusual.block_factorys_bosses.entity.projectile.SwordWaveEntity;
import net.unusual.block_factorys_bosses.entity.projectile.ThrownCrateEntity;
import net.unusual.block_factorys_bosses.entity.projectile.ThrownKrakenTridentEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create((ResourceKey)Registries.ENTITY_TYPE, (String)"block_factorys_bosses");
    public static final DeferredHolder<EntityType<?>, EntityType<PileOfBonesEntity>> PILE_OF_BONES = BossesRiseEntities.register("pile_of_bones", EntityType.Builder.<PileOfBonesEntity>of(PileOfBonesEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(3).fireImmune().sized(5.0f, 3.0f));
    public static final DeferredHolder<EntityType<?>, EntityType<YetiEntity>> YETI = BossesRiseEntities.register("yeti", EntityType.Builder.<YetiEntity>of(YetiEntity::new, MobCategory.MONSTER).clientTrackingRange(128).updateInterval(3).sized(3.2f, 4.95f));
    public static final DeferredHolder<EntityType<?>, EntityType<GlacialShoveEntity>> GLACIAL_SHOVE = BossesRiseEntities.register("glacial_shove", EntityType.Builder.<GlacialShoveEntity>of(GlacialShoveEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(3).fireImmune().sized(3.0f, 2.5f));
    public static final DeferredHolder<EntityType<?>, EntityType<IceSpikeEntity>> ICE_SPIKE = BossesRiseEntities.register("ice_spike", EntityType.Builder.<IceSpikeEntity>of(IceSpikeEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(3).fireImmune().sized(1.0f, 1.0f));
    public static final DeferredHolder<EntityType<?>, EntityType<IceSpikeClusterEntity>> ICE_SPIKE_CLUSTER = BossesRiseEntities.register("ice_spike_cluster", EntityType.Builder.<IceSpikeClusterEntity>of(IceSpikeClusterEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(3).fireImmune().sized(2.0f, 2.0f));
    public static final DeferredHolder<EntityType<?>, EntityType<FrozenSkeletonEntity>> FROZEN_SKELETON = BossesRiseEntities.register("frozen_skeleton", EntityType.Builder.<FrozenSkeletonEntity>of(FrozenSkeletonEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(3).fireImmune().sized(0.6f, 1.99f));
    public static final DeferredHolder<EntityType<?>, EntityType<CageEntity>> CAGE = BossesRiseEntities.register("cage", EntityType.Builder.<CageEntity>of((type, world) -> new CageEntity(type, world, BossesRiseItems.CAGE_ITEM), MobCategory.MISC).sized(1.7f, 3.2f).eyeHeight(0.0f).clientTrackingRange(10).fireImmune());
    public static final DeferredHolder<EntityType<?>, EntityType<CageEntity>> CAGE_SKELLY = BossesRiseEntities.register("cage_skelly", EntityType.Builder.<CageEntity>of((type, world) -> new CageEntity(type, world, BossesRiseItems.CAGE_SKELLY_ITEM), MobCategory.MISC).sized(1.7f, 3.2f).eyeHeight(0.0f).clientTrackingRange(10).fireImmune());
    public static final DeferredHolder<EntityType<?>, EntityType<CageEntity>> BIG_CAGE = BossesRiseEntities.register("big_cage", EntityType.Builder.<CageEntity>of((type, world) -> new CageEntity(type, world, BossesRiseItems.BIG_CAGE_ITEM), MobCategory.MISC).sized(3.0f, 6.5f).eyeHeight(0.0f).clientTrackingRange(10).fireImmune());
    public static final DeferredHolder<EntityType<?>, EntityType<CageEntity>> BIG_CAGE_SKELLY = BossesRiseEntities.register("big_cage_skelly", EntityType.Builder.<CageEntity>of((type, world) -> new CageEntity(type, world, BossesRiseItems.BIG_CAGE_SKELLY_ITEM), MobCategory.MISC).sized(3.0f, 6.5f).eyeHeight(0.0f).clientTrackingRange(10).fireImmune());
    public static final DeferredHolder<EntityType<?>, EntityType<CannonEntity>> KRAKEN_CANNON = BossesRiseEntities.register("kraken_cannon", EntityType.Builder.<CannonEntity>of((type, world) -> new CannonEntity(type, world, BossesRiseItems.KRAKEN_CANNON_ITEM), MobCategory.MISC).sized(3.0f, 4.0f).eyeHeight(2.25f).clientTrackingRange(10).fireImmune());
    public static final DeferredHolder<EntityType<?>, EntityType<CannonballEntity>> CANNONBALL = BossesRiseEntities.register("cannonball", EntityType.Builder.<CannonballEntity>of(CannonballEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(1).sized(0.2f, 0.2f));
    public static final DeferredHolder<EntityType<?>, EntityType<ThrownCrateEntity>> THROWN_CRATE = BossesRiseEntities.register("thrown_crate", EntityType.Builder.<ThrownCrateEntity>of(ThrownCrateEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(1).sized(0.2f, 0.2f));
    public static final DeferredHolder<EntityType<?>, EntityType<IceSpikeProjectileEntity>> ICE_SPIKE_PR = BossesRiseEntities.register("ice_spike_pr", EntityType.Builder.<IceSpikeProjectileEntity>of(IceSpikeProjectileEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(1).sized(0.5f, 0.5f));
    public static final DeferredHolder<EntityType<?>, EntityType<InfernalDragonEntity>> INFERNAL_DRAGON = BossesRiseEntities.register("infernal_dragon", EntityType.Builder.<InfernalDragonEntity>of(InfernalDragonEntity::new, MobCategory.MONSTER).clientTrackingRange(200).updateInterval(3).fireImmune().sized(5.0f, 4.0f));
    public static final DeferredHolder<EntityType<?>, EntityType<BlazingFireBallEntity>> BLAZING_FIRE_BALL = BossesRiseEntities.register("blazing_fire_ball", EntityType.Builder.<BlazingFireBallEntity>of(BlazingFireBallEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(1).sized(0.5f, 0.5f));
    public static final DeferredHolder<EntityType<?>, EntityType<FireAreaEntity>> FIRE_AREA = BossesRiseEntities.register("fire_area", EntityType.Builder.<FireAreaEntity>of(FireAreaEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(3).fireImmune().sized(3.5f, 0.3f));
    public static final DeferredHolder<EntityType<?>, EntityType<UnderworldKnightEntity>> UNDERWORLD_KNIGHT = BossesRiseEntities.register("underworld_knight", EntityType.Builder.<UnderworldKnightEntity>of(UnderworldKnightEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(3).fireImmune().sized(3.0f, 5.3f));
    public static final DeferredHolder<EntityType<?>, EntityType<DragonGuardSwordEntity>> DRAGON_GUARD_SWORD = BossesRiseEntities.register("dragon_guard_sword", EntityType.Builder.<DragonGuardSwordEntity>of(DragonGuardSwordEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(3).fireImmune().sized(0.6f, 2.5f));
    public static final DeferredHolder<EntityType<?>, EntityType<FlamingSkeletonGuardSwordEntity>> FLAMING_SKELETON_GUARD_SWORD = BossesRiseEntities.register("flaming_skeleton_guard_sword", EntityType.Builder.<FlamingSkeletonGuardSwordEntity>of(FlamingSkeletonGuardSwordEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(3).fireImmune().sized(0.6f, 2.3f));
    public static final DeferredHolder<EntityType<?>, EntityType<FlamingSkeletonGuardFireballEntity>> FLAMING_SKELETON_GUARD_FIREBALL = BossesRiseEntities.register("flaming_skeleton_guard_fireball", EntityType.Builder.<FlamingSkeletonGuardFireballEntity>of(FlamingSkeletonGuardFireballEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(3).fireImmune().sized(0.6f, 2.3f));
    public static final DeferredHolder<EntityType<?>, EntityType<SoulSkeletonEntity>> SOUL_SKELETON = BossesRiseEntities.register("soul_skeleton", EntityType.Builder.<SoulSkeletonEntity>of(SoulSkeletonEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(3).fireImmune().sized(0.6f, 2.0f));
    public static final DeferredHolder<EntityType<?>, EntityType<SoulKnightWitherSkeletonEntity>> SOUL_KNIGHT_WITHER_SKELETON = BossesRiseEntities.register("soul_knight_wither_skeleton", EntityType.Builder.<SoulKnightWitherSkeletonEntity>of(SoulKnightWitherSkeletonEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(3).fireImmune().sized(0.6f, 2.5f));
    public static final DeferredHolder<EntityType<?>, EntityType<SoulShockwaveEntity>> SOUL_SHOCKWAVE = BossesRiseEntities.register("soul_shockwave", EntityType.Builder.<SoulShockwaveEntity>of(SoulShockwaveEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(1).sized(1.0f, 1.0f));
    public static final DeferredHolder<EntityType<?>, EntityType<SandColumnEntity>> SAND_COLUMN = BossesRiseEntities.register("sand_column", EntityType.Builder.<SandColumnEntity>of(SandColumnEntity::new, MobCategory.MISC).clientTrackingRange(96).updateInterval(3).fireImmune().sized(1.8f, 3.0f));
    public static final DeferredHolder<EntityType<?>, EntityType<SandwormEntity>> SANDWORM = BossesRiseEntities.register("sandworm", EntityType.Builder.<SandwormEntity>of(SandwormEntity::new, MobCategory.MONSTER).clientTrackingRange(96).updateInterval(1).sized(0.8f, 0.9f));
    public static final DeferredHolder<EntityType<?>, EntityType<PoisonAreaEntity>> POISON_AREA = BossesRiseEntities.register("poison_area", EntityType.Builder.<PoisonAreaEntity>of(PoisonAreaEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(3).fireImmune().sized(2.7f, 0.3f));
    public static final DeferredHolder<EntityType<?>, EntityType<PoisonSpitPrEntity>> POISON_SPIT_PR = BossesRiseEntities.register("poison_spit_pr", EntityType.Builder.<PoisonSpitPrEntity>of(PoisonSpitPrEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(1).sized(0.5f, 0.5f));
    public static final DeferredHolder<EntityType<?>, EntityType<SwordWaveEntity>> SWORD_WAVE = BossesRiseEntities.register("sword_wave", EntityType.Builder.<SwordWaveEntity>of(SwordWaveEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(1).sized(4.0f, 0.2f));
    public static final DeferredHolder<EntityType<?>, EntityType<RiftEntity>> RIFT = BossesRiseEntities.register("rift", EntityType.Builder.<RiftEntity>of(RiftEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(1).fireImmune().sized(1.0f, 1.0f));
    public static final DeferredHolder<EntityType<?>, EntityType<RiftProjectileEntity>> RIFT_PROJECTILE = BossesRiseEntities.register("rift_projectile", EntityType.Builder.<RiftProjectileEntity>of(RiftProjectileEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(1).sized(0.2f, 0.2f));
    public static final DeferredHolder<EntityType<?>, EntityType<BigRiftProjectileEntity>> BIG_RIFT_PROJECTILE = BossesRiseEntities.register("big_rift_projectile", EntityType.Builder.<BigRiftProjectileEntity>of(BigRiftProjectileEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(1).sized(0.2f, 0.2f));
    public static final DeferredHolder<EntityType<?>, EntityType<KnightMarkEntity>> KNIGHT_MARK = BossesRiseEntities.register("knight_mark", EntityType.Builder.<KnightMarkEntity>of(KnightMarkEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(1).fireImmune().sized(1.0f, 1.0f));
    public static final DeferredHolder<EntityType<?>, EntityType<KrakenEntity>> KRAKEN = BossesRiseEntities.register("kraken", EntityType.Builder.<KrakenEntity>of(KrakenEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(1).fireImmune().sized(10.0f, 11.0f));
    public static final DeferredHolder<EntityType<?>, EntityType<KrakenCinematicEntity>> CINEMATIC_KRAKEN = BossesRiseEntities.register("cinematic_kraken", EntityType.Builder.<KrakenCinematicEntity>of(KrakenCinematicEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(1).fireImmune().sized(10.0f, 11.0f));
    public static final DeferredHolder<EntityType<?>, EntityType<KrakenTentacleEntity>> KRAKEN_TENTACLE = BossesRiseEntities.register("kraken_tentacle", EntityType.Builder.<KrakenTentacleEntity>of(KrakenTentacleEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(1).fireImmune().sized(1.0f, 1.0f));
    public static final DeferredHolder<EntityType<?>, EntityType<ThrownKrakenTridentEntity>> KRAKEN_TRIDENT = BossesRiseEntities.register("kraken_trident", EntityType.Builder.<ThrownKrakenTridentEntity>of(ThrownKrakenTridentEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).eyeHeight(0.13f).clientTrackingRange(4).updateInterval(20));
    public static final DeferredHolder<EntityType<?>, EntityType<PirateCaptainEntity>> PIRATE_CAPTAIN = BossesRiseEntities.register("pirate_captain", EntityType.Builder.<PirateCaptainEntity>of(PirateCaptainEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(3).sized(0.6f, 2.3f));
    public static final DeferredHolder<EntityType<?>, EntityType<PirateRookEntity>> PIRATE_ROOK = BossesRiseEntities.register("pirate_rook", EntityType.Builder.<PirateRookEntity>of(PirateRookEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(3).sized(0.6f, 2.3f));
    public static final DeferredHolder<EntityType<?>, EntityType<CrossbowPirateEntity>> CROSSBOW_PIRATE = BossesRiseEntities.register("crossbow_pirate", EntityType.Builder.<CrossbowPirateEntity>of(CrossbowPirateEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(3).sized(0.6f, 2.3f));
    public static final DeferredHolder<EntityType<?>, EntityType<GhostTentacleEntity>> GHOST_TENTACLE = BossesRiseEntities.register("ghost_tentacle", EntityType.Builder.<GhostTentacleEntity>of(GhostTentacleEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(3).sized(0.6f, 2.3f));
    public static final DeferredHolder<EntityType<?>, EntityType<AnchorEntity>> ANCHOR = BossesRiseEntities.register("anchor", EntityType.Builder.<AnchorEntity>of((type, world) -> new AnchorEntity(type, world, BossesRiseItems.ANCHOR_ITEM), MobCategory.MISC).sized(1.0f, 3.5f).eyeHeight(0.0f).clientTrackingRange(10).fireImmune());
    public static final DeferredHolder<EntityType<?>, EntityType<CratePileEntity>> CRATE_PILE = BossesRiseEntities.register("crate_pile", EntityType.Builder.<CratePileEntity>of((type, world) -> new CratePileEntity(type, world, BossesRiseItems.CRATE), MobCategory.MISC).sized(1.0f, 1.0f).eyeHeight(0.0f).clientTrackingRange(10));

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryName, EntityType.Builder<T> entityTypeBuilder) {
        return REGISTRY.register(registryName, () -> entityTypeBuilder.build(registryName));
    }

    @SubscribeEvent
    public static void init(RegisterSpawnPlacementsEvent event) {
        PileOfBonesEntity.init(event);
        YetiEntity.init(event);
        InfernalDragonEntity.init(event);
        UnderworldKnightEntity.init(event);
        DragonGuardSwordEntity.init(event);
        FlamingSkeletonGuardSwordEntity.init(event);
        FlamingSkeletonGuardFireballEntity.init(event);
        SoulSkeletonEntity.init(event);
        SoulKnightWitherSkeletonEntity.init(event);
        SandwormEntity.init(event);
        PirateCaptainEntity.init(event);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put((EntityType)PILE_OF_BONES.get(), PileOfBonesEntity.createAttributes().build());
        event.put((EntityType)YETI.get(), YetiEntity.createAttributes().build());
        event.put((EntityType)INFERNAL_DRAGON.get(), InfernalDragonEntity.createAttributes().build());
        event.put((EntityType)UNDERWORLD_KNIGHT.get(), UnderworldKnightEntity.createAttributes().build());
        event.put((EntityType)DRAGON_GUARD_SWORD.get(), DragonGuardSwordEntity.createAttributes().build());
        event.put((EntityType)FLAMING_SKELETON_GUARD_SWORD.get(), FlamingSkeletonGuardSwordEntity.createAttributes().build());
        event.put((EntityType)FLAMING_SKELETON_GUARD_FIREBALL.get(), FlamingSkeletonGuardFireballEntity.createAttributes().build());
        event.put((EntityType)SOUL_SKELETON.get(), SoulSkeletonEntity.createAttributes().build());
        event.put((EntityType)SOUL_KNIGHT_WITHER_SKELETON.get(), SoulKnightWitherSkeletonEntity.createAttributes().build());
        event.put((EntityType)SANDWORM.get(), SandwormEntity.createAttributes().build());
        event.put((EntityType)KRAKEN.get(), KrakenEntity.createAttributes().build());
        event.put((EntityType)CINEMATIC_KRAKEN.get(), KrakenCinematicEntity.createMonsterAttributes().build());
        event.put((EntityType)KRAKEN_TENTACLE.get(), KrakenTentacleEntity.createAttributes().build());
        event.put((EntityType)PIRATE_CAPTAIN.get(), PirateCaptainEntity.createAttributes().build());
        event.put((EntityType)PIRATE_ROOK.get(), PirateRookEntity.createAttributes().build());
        event.put((EntityType)CROSSBOW_PIRATE.get(), CrossbowPirateEntity.createAttributes().build());
        event.put((EntityType)GHOST_TENTACLE.get(), GhostTentacleEntity.createAttributes().build());
    }
}

