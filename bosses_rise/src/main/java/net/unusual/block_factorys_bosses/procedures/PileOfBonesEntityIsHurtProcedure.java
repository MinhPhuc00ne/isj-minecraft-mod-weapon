/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.FallingBlockEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.DirectionProperty
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.Property
 */
package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.unusual.block_factorys_bosses.init.BossesRiseTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.decoration.PileOfBonesEntity;

public class PileOfBonesEntityIsHurtProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        int n;
        if (entity == null) {
            return;
        }
        double distance = 0.0;
        if (entity instanceof PileOfBonesEntity) {
            PileOfBonesEntity _datEntI = (PileOfBonesEntity)entity;
            n = (Integer)_datEntI.getEntityData().get(PileOfBonesEntity.DATA_hit_animation_time);
        } else {
            n = 0;
        }
        if (0 == n) {
            int n2;
            if (entity instanceof PileOfBonesEntity) {
                PileOfBonesEntity _datEntI = (PileOfBonesEntity)entity;
                n2 = (Integer)_datEntI.getEntityData().get(PileOfBonesEntity.DATA_remaning_hit);
            } else {
                n2 = 0;
            }
            if (0 < n2) {
                PileOfBonesEntity _datEntSetI;
                if (entity instanceof PileOfBonesEntity) {
                    int n3;
                    _datEntSetI = (PileOfBonesEntity)entity;
                    SynchedEntityData synchedEntityData = _datEntSetI.getEntityData();
                    if (entity instanceof PileOfBonesEntity) {
                        PileOfBonesEntity _datEntI = (PileOfBonesEntity)entity;
                        n3 = (Integer)_datEntI.getEntityData().get(PileOfBonesEntity.DATA_remaning_hit);
                    } else {
                        n3 = 0;
                    }
                    synchedEntityData.set(PileOfBonesEntity.DATA_remaning_hit, (n3 - 1));
                }
                if (!world.isClientSide() && world instanceof Level) {
                    Level _level = (Level)world;
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing((double)x, (double)y, (double)z), SoundEvents.WITHER_SKELETON_HURT, SoundSource.NEUTRAL, 1.0f, -2.0f);
                    } else {
                        _level.playLocalSound(x, y, z, SoundEvents.WITHER_SKELETON_HURT, SoundSource.NEUTRAL, 1.0f, -2.0f, false);
                    }
                }
                if (entity instanceof PileOfBonesEntity) {
                    _datEntSetI = (PileOfBonesEntity)entity;
                    _datEntSetI.getEntityData().set(PileOfBonesEntity.DATA_hit_animation_time, 4);
                }
            } else {
                if (!entity.level().isClientSide()) {
                    entity.discard();
                }
                if (world instanceof Level) {
                    Level _level = (Level)world;
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing((double)x, (double)y, (double)z), SoundEvents.WITHER_SKELETON_DEATH, SoundSource.NEUTRAL, 1.0f, -2.0f);
                    } else {
                        _level.playLocalSound(x, y, z, SoundEvents.WITHER_SKELETON_DEATH, SoundSource.NEUTRAL, 1.0f, -2.0f, false);
                    }
                }
                for (int index0 = 0; index0 < Mth.nextInt((RandomSource)RandomSource.create(), (int)8, (int)12); ++index0) {
                    if (!(world instanceof ServerLevel)) continue;
                    ServerLevel _level = (ServerLevel)world;
                    FallingBlockEntity.fall((Level)_level, (BlockPos)BlockPos.containing((double)(x + (double)Mth.nextInt((RandomSource)RandomSource.create(), (int)-4, (int)4)), (double)(y + (double)Mth.nextInt((RandomSource)RandomSource.create(), (int)0, (int)3)), (double)(z + (double)Mth.nextInt((RandomSource)RandomSource.create(), (int)-4, (int)4))), (BlockState)new Object(){

                        public BlockState with(BlockState _bs, Direction newValue) {
                            EnumProperty _ep;
                            DirectionProperty _dp;
                            Property _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
                            if (_prop instanceof DirectionProperty && (_dp = (DirectionProperty)_prop).getPossibleValues().contains(newValue)) {
                                return (BlockState)_bs.setValue(_dp, newValue);
                            }
                            _prop = _bs.getBlock().getStateDefinition().getProperty("axis");
                            return _prop instanceof EnumProperty && (_ep = (EnumProperty)_prop).getPossibleValues().contains(newValue.getAxis()) ? (BlockState)_bs.setValue(_ep, newValue.getAxis()) : _bs;
                        }
                    }.with(BuiltInRegistries.BLOCK.getOrCreateTag(BossesRiseTags.Blocks.BONE_REMAINS).getRandomElement(RandomSource.create()).orElseGet(() -> BuiltInRegistries.BLOCK.wrapAsHolder(Blocks.AIR)).value().defaultBlockState(), Direction.getRandom(RandomSource.create())));
                }
            }
        }
    }
}

