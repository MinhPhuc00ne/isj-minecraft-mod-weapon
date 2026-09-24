/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.Difficulty
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.ai.village.poi.PoiManager$Occupancy
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.EventHooks
 *  net.neoforged.neoforge.event.entity.living.LivingDeathEvent
 *  net.neoforged.neoforge.registries.DeferredHolder
 */
package net.unusual.block_factorys_bosses.block.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.unusual.block_factorys_bosses.block.KrakenSpawnerBlock;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.summons.CrossbowPirateEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.summons.PirateCaptainEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.summons.PirateRookEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseBlockEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRisePOI;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class KrakenSpawnerBlockEntity
extends BlockEntity {
    private static final int PLAYER_CHECK_RANGE = 32;
    private static final int PIRATE_DEATH_RANGE = 64;
    private static final Vec3 KRAKEN_POS_OFFSET = new Vec3(-24.0, -3.5, 4.0);
    public static final PiratePoint<?>[] PIRATE_POINTS = new PiratePoint[]{new PiratePoint<CrossbowPirateEntity>(BossesRiseEntities.CROSSBOW_PIRATE, new Vec3(-7.0, 1.5, 21.5), new Vec3(5.0, 0.0, -7.0), new Vec3(-1.0, 3.0, 5.0)), new PiratePoint<PirateRookEntity>(BossesRiseEntities.PIRATE_ROOK, new Vec3(6.0, 0.0, 15.0), new Vec3(-5.0, 0.0, -7.0), new Vec3(2.0, 0.0, 6.0)), new PiratePoint<PirateCaptainEntity>(BossesRiseEntities.PIRATE_CAPTAIN, new Vec3(0.0, 3.0, 24.0), new Vec3(0.0, 2.0, -14.0), new Vec3(0.0, 0.0, 0.0))};
    private List<UUID> pirateList = new ArrayList<UUID>();
    private boolean spawnedPiratesFlag = false;

    public KrakenSpawnerBlockEntity(BlockPos position, BlockState state) {
        super((BlockEntityType)BossesRiseBlockEntities.KRAKEN_SPAWNER.get(), position, state);
    }

    public static Vec3 rotatedPoint(Vec3 vec3, Direction facing) {
        return switch (facing) {
            case Direction.EAST -> new Vec3(-vec3.z(), vec3.y(), vec3.x());
            case Direction.SOUTH -> new Vec3(-vec3.x(), vec3.y(), -vec3.z());
            case Direction.WEST -> new Vec3(vec3.z(), vec3.y(), -vec3.x());
            default -> vec3;
        };
    }

    public static float rotatedRot(Direction facing) {
        return switch (facing) {
            case Direction.EAST -> -90.0f;
            case Direction.WEST -> 90.0f;
            case Direction.SOUTH -> 0.0f;
            default -> 180.0f;
        };
    }

    public void tick(ServerLevel level, BlockPos pos, BlockState state) {
        Vec3 center = Vec3.atBottomCenterOf((Vec3i)pos);
        if (!this.isNearPlayer(level, center)) {
            return;
        }
        if (!this.spawnedPiratesFlag) {
            if (level.getDifficulty() == Difficulty.PEACEFUL) {
                return;
            }
            for (PiratePoint<?> point : PIRATE_POINTS) {
                UUID id = point.trySummon(level, center, (Direction)state.getValue((Property)KrakenSpawnerBlock.FACING));
                if (id == null) continue;
                this.pirateList.add(id);
            }
            this.spawnedPiratesFlag = true;
        } else if (!this.pirateList.isEmpty()) {
            if (level.getDifficulty() == Difficulty.PEACEFUL) {
                this.spawnedPiratesFlag = false;
                this.pirateList.clear();
            }
        } else {
            level.setWeatherParameters(0, ServerLevel.THUNDER_DURATION.sample(level.getRandom()), true, true);
            KrakenEntity kraken = (KrakenEntity)((EntityType)BossesRiseEntities.KRAKEN.get()).create((Level)level);
            if (kraken == null) {
                return;
            }
            Direction facing = (Direction)state.getValue((Property)KrakenSpawnerBlock.FACING);
            Vec3 offset = KrakenSpawnerBlockEntity.rotatedPoint(KRAKEN_POS_OFFSET, facing);
            float rot = KrakenSpawnerBlockEntity.rotatedRot(facing.getClockWise());
            kraken.setYRot(rot);
            kraken.yRotO = rot;
            kraken.setYBodyRot(rot);
            kraken.yBodyRotO = rot;
            kraken.setYHeadRot(rot);
            kraken.yHeadRotO = rot;
            kraken.setShipPosition(pos, facing);
            kraken.setPos(center.add(offset));
            kraken.setBossPhase(-1);
            level.addFreshEntity((Entity)kraken);
            level.removeBlock(pos, false);
        }
    }

    private boolean isNearPlayer(ServerLevel level, Vec3 pos) {
        return level.hasNearbyAlivePlayer(pos.x, pos.y, pos.z, 32.0);
    }

    public void loadAdditional(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.loadAdditional(tag, lookupProvider);
        this.spawnedPiratesFlag = tag.getBoolean("SpawnedPirates");
        if (!this.spawnedPiratesFlag) {
            return;
        }
        this.pirateList.clear();
        for (Tag pirateUuidTag : tag.getList("Pirates", 11)) {
            this.pirateList.add(NbtUtils.loadUUID((Tag)pirateUuidTag));
        }
    }

    public void saveAdditional(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.saveAdditional(tag, lookupProvider);
        tag.putBoolean("SpawnedPirates", this.spawnedPiratesFlag);
        if (!this.spawnedPiratesFlag) {
            return;
        }
        ListTag pirateUuidsTag = new ListTag();
        for (UUID pirateUuid : this.pirateList) {
            pirateUuidsTag.add(NbtUtils.createUUID(pirateUuid));
        }
        tag.put("Pirates", (Tag)pirateUuidsTag);
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create((BlockEntity)this);
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
        return this.saveWithFullMetadata(lookupProvider);
    }

    @EventBusSubscriber
    public record PiratePoint<T extends Monster>(DeferredHolder<EntityType<?>, EntityType<T>> type, Vec3... offset) {
        @Nullable
        public UUID trySummon(ServerLevel level, Vec3 center, Direction facing) {
            Monster pirate = (Monster)((EntityType)this.type().get()).create((Level)level);
            if (pirate == null) {
                return null;
            }
            Vec3 offset = KrakenSpawnerBlockEntity.rotatedPoint(this.offset()[level.random.nextInt(this.offset().length)], facing);
            float rot = KrakenSpawnerBlockEntity.rotatedRot(Direction.getRandom((RandomSource)level.random));
            pirate.setYRot(rot);
            pirate.yRotO = rot;
            pirate.setYBodyRot(rot);
            pirate.yBodyRotO = rot;
            pirate.setYHeadRot(rot);
            pirate.yHeadRotO = rot;
            pirate.setPos(center.add(offset));
            EventHooks.finalizeMobSpawn((Mob)pirate, (ServerLevelAccessor)level, (DifficultyInstance)level.getCurrentDifficultyAt(pirate.blockPosition()), (MobSpawnType)MobSpawnType.MOB_SUMMONED, null);
            return level.addFreshEntity((Entity)pirate) ? pirate.getUUID() : null;
        }

        @SubscribeEvent
        private static void onDied(LivingDeathEvent event) {
            Level level;
            LivingEntity living = event.getEntity();
            if (!(living instanceof SpawnerTied) || !((level = living.level()) instanceof ServerLevel)) {
                return;
            }
            ServerLevel level2 = (ServerLevel)level;
            level2.getPoiManager().findClosestWithType(holder -> holder.is(BossesRisePOI.KRAKEN_SPAWNER.getKey()), living.blockPosition(), 64, PoiManager.Occupancy.ANY).ifPresent(pair -> {
                BlockEntity patt0$temp = level2.getBlockEntity((BlockPos)pair.getSecond());
                if (patt0$temp instanceof KrakenSpawnerBlockEntity) {
                    KrakenSpawnerBlockEntity krakenSpawner = (KrakenSpawnerBlockEntity)patt0$temp;
                    krakenSpawner.pirateList.removeIf(uuid -> uuid.equals(living.getUUID()));
                    RandomSource random = living.getRandom();
                    Vec3 pos = living.position().add((double)((random.nextFloat() - 0.5f) * 5.0f), -5.0, (double)((random.nextFloat() - 0.5f) * 5.0f));
                    level2.playSound(null, pos.x, pos.y, pos.z, (SoundEvent)BossesRiseSounds.KRAKEN_WARNING.value(), SoundSource.HOSTILE, 2.0f, 1.0f);
                }
            });
        }
    }

    public static interface SpawnerTied {
    }
}

