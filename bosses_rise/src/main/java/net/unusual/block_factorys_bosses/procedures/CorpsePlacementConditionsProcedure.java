/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 */
package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class CorpsePlacementConditionsProcedure {
    public static boolean execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
        int n;
        int n2;
        Property property = blockstate.getBlock().getStateDefinition().getProperty("blockstate");
        if (property instanceof IntegerProperty) {
            IntegerProperty _getip1 = (IntegerProperty)property;
            n2 = (Integer)blockstate.getValue((Property)_getip1);
        } else {
            n2 = -1;
        }
        if (2 == n2 && world.getBlockState(BlockPos.containing((double)x, (double)(y + 1.0), (double)z)).getBlock() == blockstate.getBlock()) {
            int n3;
            property = world.getBlockState(BlockPos.containing((double)x, (double)(y + 1.0), (double)z)).getBlock().getStateDefinition().getProperty("blockstate");
            if (property instanceof IntegerProperty) {
                IntegerProperty _getip6 = (IntegerProperty)property;
                n3 = (Integer)world.getBlockState(BlockPos.containing((double)x, (double)(y + 1.0), (double)z)).getValue((Property)_getip6);
            } else {
                n3 = -1;
            }
            if (1 == n3) {
                return true;
            }
        }
        if ((property = blockstate.getBlock().getStateDefinition().getProperty("blockstate")) instanceof IntegerProperty) {
            IntegerProperty _getip8 = (IntegerProperty)property;
            n = (Integer)blockstate.getValue((Property)_getip8);
        } else {
            n = -1;
        }
        if (1 == n && world.getBlockState(BlockPos.containing((double)x, (double)(y - 1.0), (double)z)).getBlock() == blockstate.getBlock()) {
            int n4;
            property = world.getBlockState(BlockPos.containing((double)x, (double)(y - 1.0), (double)z)).getBlock().getStateDefinition().getProperty("blockstate");
            if (property instanceof IntegerProperty) {
                IntegerProperty _getip13 = (IntegerProperty)property;
                n4 = (Integer)world.getBlockState(BlockPos.containing((double)x, (double)(y - 1.0), (double)z)).getValue((Property)_getip13);
            } else {
                n4 = -1;
            }
            if (2 == n4) {
                return true;
            }
        }
        if (world.getBlockState(BlockPos.containing((double)x, (double)(y + 1.0), (double)z)).getBlock() == Blocks.AIR || world.getBlockState(BlockPos.containing((double)x, (double)(y + 1.0), (double)z)).getBlock() == blockstate.getBlock()) {
            int n5;
            property = blockstate.getBlock().getStateDefinition().getProperty("blockstate");
            if (property instanceof IntegerProperty) {
                IntegerProperty _getip20 = (IntegerProperty)property;
                n5 = (Integer)blockstate.getValue((Property)_getip20);
            } else {
                n5 = -1;
            }
            if (0 == n5) {
                return true;
            }
        }
        return false;
    }
}

