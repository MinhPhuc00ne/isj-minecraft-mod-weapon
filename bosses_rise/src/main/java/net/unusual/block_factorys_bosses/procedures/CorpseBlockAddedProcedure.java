/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 */
package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class CorpseBlockAddedProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
        int n;
        Property property = blockstate.getBlock().getStateDefinition().getProperty("blockstate");
        if (property instanceof IntegerProperty) {
            IntegerProperty _getip1 = (IntegerProperty)property;
            n = (Integer)blockstate.getValue(_getip1);
        } else {
            n = -1;
        }
        if (n == 0) {
            IntegerProperty _integerProp;
            world.setBlock(BlockPos.containing((double)x, (double)(y + 1.0), (double)z), blockstate, 3);
            int _value = 1;
            BlockPos _pos = BlockPos.containing((double)x, (double)(y + 1.0), (double)z);
            BlockState _bs = world.getBlockState(_pos);
            Property property2 = _bs.getBlock().getStateDefinition().getProperty("blockstate");
            if (property2 instanceof IntegerProperty && (_integerProp = (IntegerProperty)property2).getPossibleValues().contains(_value)) {
                world.setBlock(_pos, (BlockState)_bs.setValue(_integerProp, Integer.valueOf(_value)), 3);
            }
            _value = 2;
            _pos = BlockPos.containing((double)x, (double)y, (double)z);
            _bs = world.getBlockState(_pos);
            property2 = _bs.getBlock().getStateDefinition().getProperty("blockstate");
            if (property2 instanceof IntegerProperty && (_integerProp = (IntegerProperty)property2).getPossibleValues().contains(_value)) {
                world.setBlock(_pos, (BlockState)_bs.setValue(_integerProp, Integer.valueOf(_value)), 3);
            }
        }
    }
}

