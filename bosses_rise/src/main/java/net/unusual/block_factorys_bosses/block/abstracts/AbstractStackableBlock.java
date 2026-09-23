/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 */
package net.unusual.block_factorys_bosses.block.abstracts;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class AbstractStackableBlock
extends Block {
    protected final IntegerProperty stackableProperty;
    protected final int minStack;
    protected final int maxStack;

    public AbstractStackableBlock(BlockBehaviour.Properties properties, IntegerProperty stackableProperty, int minStack, int maxStack) {
        super(properties);
        this.stackableProperty = stackableProperty;
        this.minStack = minStack;
        this.maxStack = maxStack;
    }

    public IntegerProperty getStackableProperty() {
        return this.stackableProperty;
    }

    public int getMinStack() {
        return this.minStack;
    }

    public int getMaxStack() {
        return this.maxStack;
    }

    protected abstract void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1);

    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return !context.isSecondaryUseActive() && context.getItemInHand().is(this.asItem()) && (Integer)state.getValue(this.stackableProperty) < this.maxStack || super.canBeReplaced(state, context);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (!this.canSurvive(this.defaultBlockState(), (LevelReader)context.getLevel(), context.getClickedPos())) {
            return null;
        }
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        return blockstate.is((Block)this) ? (BlockState)blockstate.setValue(this.stackableProperty, Integer.valueOf(Math.min(this.maxStack, (Integer)blockstate.getValue(this.stackableProperty) + 1))) : this.defaultBlockState();
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isFaceSturdy((BlockGetter)level, pos.below(), Direction.UP);
    }

    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
        if (facing == Direction.DOWN && !this.canSurvive(state, (LevelReader)level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, facing, facingState, level, pos, facingPos);
    }
}

