/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.ai.village.poi.PoiManager
 *  net.minecraft.world.entity.ai.village.poi.PoiManager$Occupancy
 *  net.minecraft.world.entity.ai.village.poi.PoiRecord
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.StructureManager
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.levelgen.structure.BoundingBox
 *  net.minecraft.world.level.levelgen.structure.Structure
 *  net.minecraft.world.level.levelgen.structure.StructureStart
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.level.BlockEvent$BreakEvent
 *  net.neoforged.neoforge.event.level.BlockEvent$EntityPlaceEvent
 *  net.neoforged.neoforge.event.level.ExplosionEvent$Detonate
 */
package net.unusual.block_factorys_bosses.event;

import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.init.BossesRisePOI;
import net.unusual.block_factorys_bosses.init.BossesRiseTags;

@EventBusSubscriber
public class StructureDestructionEvents {
    public static String DESTRUCTION_CANCEL_MESSAGE = "message.block_factorys_bosses.dragon_tower.block_protection";

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        LevelAccessor levelAccessor = event.getLevel();
        if (!(levelAccessor instanceof ServerLevel)) {
            return;
        }
        ServerLevel level = (ServerLevel)levelAccessor;
        if (event.getPlayer().isCreative()) {
            return;
        }
        if (StructureDestructionEvents.isProtectionExempt(event.getState())) {
            return;
        }
        if (StructureDestructionEvents.isTowerDefeated(level, StructureDestructionEvents.isPartOfDragonTower((Level)level, event.getPos()))) {
            return;
        }
        event.setCanceled(true);
        event.getPlayer().displayClientMessage((Component)Component.translatable((String)DESTRUCTION_CANCEL_MESSAGE).withStyle(ChatFormatting.RED), true);
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        Player player;
        LevelAccessor levelAccessor = event.getLevel();
        if (!(levelAccessor instanceof ServerLevel)) {
            return;
        }
        ServerLevel level = (ServerLevel)levelAccessor;
        Entity entity = event.getEntity();
        if (entity instanceof Player && (player = (Player)entity).isCreative()) {
            return;
        }
        if (StructureDestructionEvents.isProtectionExempt(event.getState())) {
            return;
        }
        if (StructureDestructionEvents.isTowerDefeated(level, StructureDestructionEvents.isPartOfDragonTower((Level)level, event.getPos()))) {
            return;
        }
        event.setCanceled(true);
        entity = event.getEntity();
        if (!(entity instanceof ServerPlayer)) {
            return;
        }
        player = (ServerPlayer)entity;
        player.displayClientMessage((Component)Component.translatable((String)DESTRUCTION_CANCEL_MESSAGE).withStyle(ChatFormatting.RED), true);
    }

    @SubscribeEvent
    public static void onExplosion(ExplosionEvent.Detonate event) {
        Level level = event.getLevel();
        if (!(level instanceof ServerLevel)) {
            return;
        }
        ServerLevel level2 = (ServerLevel)level;
        event.getAffectedBlocks().removeIf(pos -> !StructureDestructionEvents.isProtectionExempt(level2.getBlockState(pos)) && !StructureDestructionEvents.isTowerDefeated(level2, StructureDestructionEvents.isPartOfDragonTower((Level)level2, pos)));
    }

    private static boolean isProtectionExempt(BlockState blockState) {
        return blockState.is(BossesRiseTags.Blocks.PROTECTION_EXEMPT);
    }

    private static StructureStart isPartOfDragonTower(Level level, BlockPos pos) {
        if (!(level instanceof ServerLevel)) {
            return null;
        }
        ServerLevel serverLevel = (ServerLevel)level;
        StructureManager structureManager = serverLevel.structureManager();
        for (Map.Entry entry : structureManager.getAllStructuresAt(pos).entrySet()) {
            if (((Structure)entry.getKey()).type() != BossesRise.DRAGON_TOWER.get()) continue;
            return structureManager.getStructureAt(pos, (Structure)entry.getKey());
        }
        return null;
    }

    private static boolean isTowerDefeated(ServerLevel level, StructureStart structureStart) {
        if (structureStart == null || !structureStart.isValid()) {
            return true;
        }
        BoundingBox boundingBox = structureStart.getBoundingBox();
        PoiManager poiManager = level.getPoiManager();
        int radius = 32;
        boolean hasBossSpawner = poiManager.getInRange(holder -> holder.is(BossesRisePOI.BOSS_SPAWNER.getKey()), boundingBox.getCenter().above(32), radius, PoiManager.Occupancy.ANY).map(PoiRecord::getPos).anyMatch(arg_0 -> ((BoundingBox)boundingBox).isInside(arg_0));
        return !hasBossSpawner;
    }
}

