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
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Holder$Reference
 *  net.minecraft.core.Position
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelHeightAccessor
 *  net.minecraft.world.level.StructureManager
 *  net.minecraft.world.level.WorldGenLevel
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.ChunkGenerator
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.level.levelgen.WorldGenerationContext
 *  net.minecraft.world.level.levelgen.heightproviders.HeightProvider
 *  net.minecraft.world.level.levelgen.structure.BoundingBox
 *  net.minecraft.world.level.levelgen.structure.Structure
 *  net.minecraft.world.level.levelgen.structure.Structure$GenerationContext
 *  net.minecraft.world.level.levelgen.structure.Structure$GenerationStub
 *  net.minecraft.world.level.levelgen.structure.Structure$StructureSettings
 *  net.minecraft.world.level.levelgen.structure.StructurePiece
 *  net.minecraft.world.level.levelgen.structure.StructureStart
 *  net.minecraft.world.level.levelgen.structure.StructureType
 *  net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder
 *  net.minecraft.world.level.levelgen.structure.pools.DimensionPadding
 *  net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement
 *  net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool
 *  net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup
 *  net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.level.ExplosionEvent$Detonate
 */
package net.unusual.block_factorys_bosses.structures;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.unusual.block_factorys_bosses.BossesRise;

@EventBusSubscriber
public class KrakenShipStructure
extends Structure {
    public static final MapCodec<KrakenShipStructure> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(KrakenShipStructure.settingsCodec(instance), StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter((KrakenShipStructure structure) -> structure.startPool), ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter((KrakenShipStructure structure) -> structure.startJigsawName), Codec.intRange((int)0, (int)20).fieldOf("size").forGetter((KrakenShipStructure structure) -> structure.maxDepth), HeightProvider.CODEC.fieldOf("start_height").forGetter((KrakenShipStructure structure) -> structure.startHeight), Codec.BOOL.fieldOf("use_expansion_hack").forGetter((KrakenShipStructure structure) -> structure.useExpansionHack), Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter((KrakenShipStructure structure) -> structure.projectStartToHeightmap), Codec.intRange((int)1, (int)160).fieldOf("max_distance_from_center").forGetter((KrakenShipStructure structure) -> structure.maxDistanceFromCenter), DimensionPadding.CODEC.optionalFieldOf("dimension_padding", DimensionPadding.ZERO).forGetter((KrakenShipStructure structure) -> structure.dimensionPadding), LiquidSettings.CODEC.optionalFieldOf("liquid_settings", LiquidSettings.APPLY_WATERLOGGING).forGetter((KrakenShipStructure structure) -> structure.liquidSettings)).apply(instance, KrakenShipStructure::new));
    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int maxDepth;
    private final HeightProvider startHeight;
    private final boolean useExpansionHack;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;
    private final DimensionPadding dimensionPadding;
    private final LiquidSettings liquidSettings;
    private static final Map<Vec3, BlockState> BLOCK_CACHE = new ConcurrentHashMap<Vec3, BlockState>();
    private static final Map<Long, List<Vec3>> SHIP_KEYS = new ConcurrentHashMap<Long, List<Vec3>>();

    public KrakenShipStructure(Structure.StructureSettings settings, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int maxDepth, HeightProvider startHeight, boolean useExpansionHack, Optional<Heightmap.Types> projectStartToHeightmap, int maxDistanceFromCenter, DimensionPadding dimensionPadding, LiquidSettings liquidSettings) {
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

    public StructureType<?> type() {
        return (StructureType)BossesRise.KRAKEN_SHIP.get();
    }

    public static void buildShipCache(ServerLevel level, BlockPos shipPos) {
        Structure fullStructure;
        StructureManager structureManager = level.structureManager();
        StructureStart fullStart = structureManager.getStructureAt(shipPos, fullStructure = (Structure)level.registryAccess().registryOrThrow(Registries.STRUCTURE).getOrThrow(ResourceKey.create((ResourceKey)Registries.STRUCTURE, (ResourceLocation)BossesRise.prefix("kraken_ship"))));
        if (!fullStart.isValid()) {
            return;
        }
        ChunkPos chunkPos = fullStart.getChunkPos();
        long shipChunkKey = chunkPos.toLong();
        if (SHIP_KEYS.containsKey(shipChunkKey)) {
            return;
        }
        List<StructurePiece> destPieces = KrakenShipStructure.buildDestPieces(level, chunkPos);
        if (destPieces.isEmpty()) {
            return;
        }
        ChunkGenerator gen = level.getChunkSource().getGenerator();
        BlockPos pivot = new BlockPos(chunkPos.getMiddleBlockX(), 0, chunkPos.getMiddleBlockZ());
        HashMap<Vec3, BlockState> destBlocks = new HashMap<Vec3, BlockState>();
        for (StructurePiece piece : destPieces) {
            BoundingBox bb = piece.getBoundingBox();
            Map<Vec3, BlockState> snapshot = KrakenShipStructure.captureRegion(level, bb);
            piece.postProcess((WorldGenLevel)level, structureManager, gen, level.getRandom(), bb, chunkPos, pivot);
            Map<Vec3, BlockState> after = KrakenShipStructure.captureRegion(level, bb);
            for (Map.Entry<Vec3, BlockState> e : after.entrySet()) {
                BlockState before = snapshot.get(e.getKey());
                if (e.getValue().equals(before)) continue;
                destBlocks.put(e.getKey(), e.getValue());
            }
            snapshot.forEach((pos, state) -> level.setBlock(BlockPos.containing((Position)pos), state, 3));
        }
        BLOCK_CACHE.putAll(destBlocks);
        SHIP_KEYS.put(shipChunkKey, new ArrayList(destBlocks.keySet()));
    }

    public static void replaceWithDestroyed(ServerLevel level, BlockPos pos) {
        BlockState state = BLOCK_CACHE.get(pos.getCenter());
        if (state == null) {
            return;
        }
        level.setBlock(pos, state, 3);
    }

    public static void evictCache(ServerLevel level, BlockPos pos) {
        Structure fullStructure = (Structure)level.registryAccess().registryOrThrow(Registries.STRUCTURE).getOrThrow(ResourceKey.create((ResourceKey)Registries.STRUCTURE, (ResourceLocation)BossesRise.prefix("kraken_ship")));
        StructureStart fullStart = level.structureManager().getStructureAt(pos, fullStructure);
        if (!fullStart.isValid()) {
            return;
        }
        List<Vec3> keys = SHIP_KEYS.remove(fullStart.getChunkPos().toLong());
        if (keys != null) {
            keys.forEach(BLOCK_CACHE::remove);
        }
    }

    private static Map<Vec3, BlockState> captureRegion(ServerLevel level, BoundingBox bb) {
        HashMap<Vec3, BlockState> map = new HashMap<Vec3, BlockState>();
        for (int x = bb.minX(); x <= bb.maxX(); ++x) {
            for (int y = bb.minY(); y <= bb.maxY(); ++y) {
                for (int z = bb.minZ(); z <= bb.maxZ(); ++z) {
                    BlockPos p = new BlockPos(x, y, z);
                    map.put(p.getCenter(), level.getBlockState(p));
                }
            }
        }
        return map;
    }

    private static List<StructurePiece> buildDestPieces(ServerLevel level, ChunkPos chunkPos) {
        ChunkGenerator chunkGenerator = level.getChunkSource().getGenerator();
        Registry poolRegistry = level.registryAccess().registryOrThrow(Registries.TEMPLATE_POOL);
        Holder.Reference destPool = poolRegistry.getHolderOrThrow(ResourceKey.create((ResourceKey)Registries.TEMPLATE_POOL, (ResourceLocation)BossesRise.prefix("kraken_ship_destroyed_seed")));
        Structure.GenerationContext genContext = new Structure.GenerationContext(level.registryAccess(), chunkGenerator, chunkGenerator.getBiomeSource(), level.getChunkSource().randomState(), level.getServer().getStructureManager(), level.getSeed(), chunkPos, (LevelHeightAccessor)level, biome -> true);
        BlockPos startPos = new BlockPos(chunkPos.getMinBlockX(), -4, chunkPos.getMinBlockZ());
        Optional stub = JigsawPlacement.addPieces((Structure.GenerationContext)genContext, (Holder)destPool, Optional.empty(), (int)4, (BlockPos)startPos, (boolean)false, Optional.of(Heightmap.Types.WORLD_SURFACE_WG), (int)160, (PoolAliasLookup)PoolAliasLookup.create(List.of(), (BlockPos)startPos, (long)level.getSeed()), (DimensionPadding)DimensionPadding.ZERO, (LiquidSettings)LiquidSettings.APPLY_WATERLOGGING);
        if (stub.isEmpty()) {
            return List.of();
        }
        StructurePiecesBuilder builder = new StructurePiecesBuilder();
        ((Structure.GenerationStub)stub.get()).generator().ifLeft(consumer -> consumer.accept(builder));
        StructurePiecesBuilder finalBuilder = ((Structure.GenerationStub)stub.get()).generator().right().orElse(builder);
        return List.copyOf(finalBuilder.build().pieces());
    }

    @SubscribeEvent
    public static void onExplosion(ExplosionEvent.Detonate event) {
        Level level = event.getLevel();
        if (!(level instanceof ServerLevel)) {
            return;
        }
        ServerLevel level2 = (ServerLevel)level;
        event.getAffectedBlocks().forEach(pos -> {
            Vec3 vec = pos.getCenter();
            BLOCK_CACHE.remove(vec);
            SHIP_KEYS.values().forEach(list -> list.remove(vec));
        });
    }
}

