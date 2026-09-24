/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction$Plane
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.random.SimpleWeightedRandomList
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.StructureManager
 *  net.minecraft.world.level.WorldGenLevel
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BarrelBlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.ChestBlockEntity
 *  net.minecraft.world.level.block.entity.SpawnerBlockEntity
 *  net.minecraft.world.level.block.entity.TrialSpawnerBlockEntity
 *  net.minecraft.world.level.block.entity.trialspawner.PlayerDetector
 *  net.minecraft.world.level.block.entity.trialspawner.PlayerDetector$EntitySelector
 *  net.minecraft.world.level.block.entity.trialspawner.TrialSpawner
 *  net.minecraft.world.level.block.entity.trialspawner.TrialSpawner$StateAccessor
 *  net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerConfig
 *  net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerData
 *  net.minecraft.world.level.block.entity.vault.VaultBlockEntity
 *  net.minecraft.world.level.block.entity.vault.VaultConfig
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.chunk.ChunkGenerator
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.level.levelgen.WorldGenerationContext
 *  net.minecraft.world.level.levelgen.heightproviders.HeightProvider
 *  net.minecraft.world.level.levelgen.structure.BoundingBox
 *  net.minecraft.world.level.levelgen.structure.Structure
 *  net.minecraft.world.level.levelgen.structure.Structure$GenerationContext
 *  net.minecraft.world.level.levelgen.structure.Structure$GenerationStub
 *  net.minecraft.world.level.levelgen.structure.Structure$StructureSettings
 *  net.minecraft.world.level.levelgen.structure.StructureType
 *  net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer
 *  net.minecraft.world.level.levelgen.structure.pools.DimensionPadding
 *  net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement
 *  net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool
 *  net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup
 *  net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings
 *  net.minecraft.world.level.storage.loot.BuiltInLootTables
 */
package net.unusual.block_factorys_bosses.structures;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.entity.TrialSpawnerBlockEntity;
import net.minecraft.world.level.block.entity.trialspawner.PlayerDetector;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawner;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerConfig;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerData;
import net.minecraft.world.level.block.entity.vault.VaultBlockEntity;
import net.minecraft.world.level.block.entity.vault.VaultConfig;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseLootTables;

public class DragonTowerStructure
extends Structure {
    public static final MapCodec<DragonTowerStructure> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(DragonTowerStructure.settingsCodec(instance), StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter((DragonTowerStructure structure) -> structure.startPool), ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter((DragonTowerStructure structure) -> structure.startJigsawName), Codec.intRange((int)0, (int)20).fieldOf("size").forGetter((DragonTowerStructure structure) -> structure.maxDepth), HeightProvider.CODEC.fieldOf("start_height").forGetter((DragonTowerStructure structure) -> structure.startHeight), Codec.BOOL.fieldOf("use_expansion_hack").forGetter((DragonTowerStructure structure) -> structure.useExpansionHack), Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter((DragonTowerStructure structure) -> structure.projectStartToHeightmap), Codec.intRange((int)1, (int)160).fieldOf("max_distance_from_center").forGetter((DragonTowerStructure structure) -> structure.maxDistanceFromCenter), DimensionPadding.CODEC.optionalFieldOf("dimension_padding", DimensionPadding.ZERO).forGetter((DragonTowerStructure structure) -> structure.dimensionPadding), LiquidSettings.CODEC.optionalFieldOf("liquid_settings", LiquidSettings.APPLY_WATERLOGGING).forGetter((DragonTowerStructure structure) -> structure.liquidSettings)).apply(instance, DragonTowerStructure::new));
    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int maxDepth;
    private final HeightProvider startHeight;
    private final boolean useExpansionHack;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;
    private final DimensionPadding dimensionPadding;
    private final LiquidSettings liquidSettings;
    public static int FORWARD_MARKER_DISTANCE_FROM_SPAWNER = 6;
    public static int BACKWARD_MARKER_DISTANCE_FROM_SPAWNER = 11;
    private static final VaultConfig VAULT_CONFIG = new VaultConfig(BuiltInLootTables.TRIAL_CHAMBERS_REWARD, 4.0, 4.5, BossesRiseItems.ANCIENT_TRIAL_KEY.toStack(), Optional.empty(), PlayerDetector.INCLUDING_CREATIVE_PLAYERS, PlayerDetector.EntitySelector.SELECT_FROM_LEVEL);
    private static final TrialSpawnerConfig TRIAL_SPAWNER_CONFIG = new TrialSpawnerConfig(TrialSpawnerConfig.DEFAULT.spawnRange(), TrialSpawnerConfig.DEFAULT.totalMobs(), TrialSpawnerConfig.DEFAULT.simultaneousMobs(), TrialSpawnerConfig.DEFAULT.totalMobsAddedPerPlayer(), TrialSpawnerConfig.DEFAULT.simultaneousMobsAddedPerPlayer(), TrialSpawnerConfig.DEFAULT.ticksBetweenSpawn(), SimpleWeightedRandomList.empty(), SimpleWeightedRandomList.<net.minecraft.resources.ResourceKey<net.minecraft.world.level.storage.loot.LootTable>>builder().add(BuiltInLootTables.SPAWNER_TRIAL_CHAMBER_CONSUMABLES).add(BossesRiseLootTables.ANCIENT_TRIAL_KEY).build(), TrialSpawnerConfig.DEFAULT.itemsToDropWhenOminous());

    public DragonTowerStructure(Structure.StructureSettings settings, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int maxDepth, HeightProvider startHeight, boolean useExpansionHack, Optional<Heightmap.Types> projectStartToHeightmap, int maxDistanceFromCenter, DimensionPadding dimensionPadding, LiquidSettings liquidSettings) {
        super(settings);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.maxDepth = maxDepth;
        this.startHeight = startHeight;
        this.useExpansionHack = useExpansionHack;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
        this.dimensionPadding = dimensionPadding;
        this.liquidSettings = liquidSettings;
    }

    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        int startY = this.startHeight.sample((RandomSource)context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
        BlockPos startPos = new BlockPos(chunkPos.getMinBlockX(), startY, chunkPos.getMinBlockZ());
        return JigsawPlacement.addPieces((Structure.GenerationContext)context, this.startPool, this.startJigsawName, (int)this.maxDepth, (BlockPos)startPos, (boolean)this.useExpansionHack, this.projectStartToHeightmap, (int)this.maxDistanceFromCenter, (PoolAliasLookup)PoolAliasLookup.create(List.of(), (BlockPos)startPos, (long)context.seed()), (DimensionPadding)this.dimensionPadding, (LiquidSettings)this.liquidSettings);
    }

    public void afterPlace(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox boundingBox, ChunkPos chunkPos, PiecesContainer piecesContainer) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        int minBuildHeight = level.getMinBuildHeight();
        BoundingBox structureBoundingBox = piecesContainer.calculateBoundingBox();
        int structureBaseY = structureBoundingBox.minY();
        for (int x = boundingBox.minX(); x <= boundingBox.maxX(); ++x) {
            for (int z = boundingBox.minZ(); z <= boundingBox.maxZ(); ++z) {
                int y;
                mutablePos.set(x, structureBaseY, z);
                if (!level.isEmptyBlock((BlockPos)mutablePos) && structureBoundingBox.isInside((Vec3i)mutablePos) && piecesContainer.isInsidePiece((BlockPos)mutablePos)) {
                    for (y = structureBaseY - 1; y > minBuildHeight; --y) {
                        mutablePos.setY(y);
                        if (level.getBlockState((BlockPos)mutablePos).canOcclude()) break;
                        level.setBlock((BlockPos)mutablePos, Blocks.COBBLED_DEEPSLATE.defaultBlockState(), 2);
                    }
                }
                for (y = boundingBox.minY(); y <= boundingBox.maxY(); ++y) {
                    mutablePos.setY(y);
                    BlockState state = level.getBlockState((BlockPos)mutablePos);
                    if (state.is(Blocks.BARREL)) {
                        this.assignBarrelLootTable(level, (BlockPos)mutablePos);
                        continue;
                    }
                    if (!state.is(Blocks.YELLOW_TERRACOTTA)) continue;
                    this.computeMarker(level, mutablePos, state);
                }
            }
        }
    }

    private void computeMarker(WorldGenLevel level, BlockPos.MutableBlockPos pos, BlockState state) {
        level.setBlock((BlockPos)pos, this.findMarkerReplacement(level, (BlockPos)pos), 0);
        pos.setY(pos.getY() + 1);
        if (level.getBlockState((BlockPos)pos).is(Blocks.PURPLE_STAINED_GLASS)) {
            BlockState replacedState = Blocks.SPAWNER.defaultBlockState();
            level.setBlock((BlockPos)pos, replacedState, 0);
            BlockEntity blockEntity = level.getBlockEntity((BlockPos)pos);
            if (!(blockEntity instanceof SpawnerBlockEntity)) {
                return;
            }
            SpawnerBlockEntity spawner = (SpawnerBlockEntity)blockEntity;
            EntityType<?> spawnedType = BossesRiseEntities.FLAMING_SKELETON_GUARD_FIREBALL.get();
            double random = Math.random();
            if (random >= 0.33) {
                spawnedType = (EntityType)BossesRiseEntities.FLAMING_SKELETON_GUARD_SWORD.get();
            }
            if (random >= 0.66) {
                spawnedType = (EntityType)BossesRiseEntities.DRAGON_GUARD_SWORD.get();
            }
            spawner.setEntityId(spawnedType, level.getRandom());
            spawner.setChanged();
            return;
        }
        if (level.getBlockState((BlockPos)pos).is(Blocks.MAGENTA_STAINED_GLASS)) {
            BlockState replacedState = Blocks.TRIAL_SPAWNER.defaultBlockState();
            level.setBlock((BlockPos)pos, replacedState, 0);
            BlockEntity be = level.getBlockEntity((BlockPos)pos);
            if (!(be instanceof TrialSpawnerBlockEntity)) {
                return;
            }
            TrialSpawnerBlockEntity spawner = (TrialSpawnerBlockEntity)be;
            spawner.trialSpawner = DragonTowerStructure.createTrialSpawner(spawner);
            spawner.markUpdated();
            EntityType<?> spawnedType = BossesRiseEntities.FLAMING_SKELETON_GUARD_FIREBALL.get();
            double random = Math.random();
            if (random >= 0.33) {
                spawnedType = (EntityType)BossesRiseEntities.FLAMING_SKELETON_GUARD_SWORD.get();
            }
            if (random >= 0.66) {
                spawnedType = (EntityType)BossesRiseEntities.DRAGON_GUARD_SWORD.get();
            }
            spawner.setEntityId((EntityType)spawnedType, level.getRandom());
            spawner.setChanged();
            return;
        }
        if (level.getBlockState((BlockPos)pos).is(Blocks.BLUE_STAINED_GLASS)) {
            if (Math.random() < (double)0.15f) {
                this.replaceMarkerWithChest(level, pos, state);
            } else {
                level.setBlock((BlockPos)pos, Math.random() >= 0.5 ? ((Block)BossesRiseBlocks.VASE.get()).defaultBlockState() : ((Block)BossesRiseBlocks.TALL_VASE.get()).defaultBlockState(), 0);
            }
            return;
        }
        if (level.getBlockState((BlockPos)pos).is(Blocks.LIGHT_BLUE_STAINED_GLASS)) {
            this.replaceMarkerWithVault(level, pos, state);
            return;
        }
    }

    private void assignBarrelLootTable(WorldGenLevel level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof BarrelBlockEntity)) {
            return;
        }
        BarrelBlockEntity barrel = (BarrelBlockEntity)blockEntity;
        barrel.setLootTable(ResourceKey.create((ResourceKey)Registries.LOOT_TABLE, (ResourceLocation)BossesRise.prefix("chests/dragon_tower_common")));
        barrel.setChanged();
    }

    private void replaceMarkerWithChest(WorldGenLevel level, BlockPos.MutableBlockPos pos, BlockState state) {
        BlockState chestState = (BlockState)Blocks.CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.Plane.HORIZONTAL.getRandomDirection(level.getRandom()));
        level.setBlock((BlockPos)pos, chestState, 0);
        BlockEntity blockEntity = level.getBlockEntity((BlockPos)pos);
        if (!(blockEntity instanceof ChestBlockEntity)) {
            return;
        }
        ChestBlockEntity chest = (ChestBlockEntity)blockEntity;
        chest.setLootTable(ResourceKey.create((ResourceKey)Registries.LOOT_TABLE, (ResourceLocation)BossesRise.prefix("chests/dragon_tower")));
        chest.setChanged();
    }

    private void replaceMarkerWithVault(WorldGenLevel level, BlockPos.MutableBlockPos pos, BlockState state) {
        BlockState vaultState = (BlockState)Blocks.VAULT.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.Plane.HORIZONTAL.getRandomDirection(level.getRandom()));
        level.setBlock((BlockPos)pos, vaultState, 0);
        BlockEntity blockEntity = level.getBlockEntity((BlockPos)pos);
        if (!(blockEntity instanceof VaultBlockEntity)) {
            return;
        }
        VaultBlockEntity vault = (VaultBlockEntity)blockEntity;
        vault.setConfig(VAULT_CONFIG);
        vault.setChanged();
    }

    private BlockState findMarkerReplacement(WorldGenLevel level, BlockPos pos) {
        BlockState state = Blocks.DIRT.defaultBlockState();
        BlockState xMinus1 = level.getBlockState(pos.west());
        if (xMinus1.canOcclude()) {
            return xMinus1;
        }
        BlockState xPlus1 = level.getBlockState(pos.east());
        if (xPlus1.canOcclude()) {
            return xPlus1;
        }
        BlockState zMinus1 = level.getBlockState(pos.north());
        if (zMinus1.canOcclude()) {
            return zMinus1;
        }
        BlockState zPlus1 = level.getBlockState(pos.south());
        if (zPlus1.canOcclude()) {
            return zPlus1;
        }
        return state;
    }

    public StructureType<?> type() {
        return (StructureType)BossesRise.DRAGON_TOWER.get();
    }

    private static TrialSpawner createTrialSpawner(TrialSpawnerBlockEntity trialSpawnerBlockEntity) {
        return new TrialSpawner(TRIAL_SPAWNER_CONFIG, TRIAL_SPAWNER_CONFIG, new TrialSpawnerData(), 36000, 14, (TrialSpawner.StateAccessor)trialSpawnerBlockEntity, PlayerDetector.NO_CREATIVE_PLAYERS, PlayerDetector.EntitySelector.SELECT_FROM_LEVEL);
    }
}

