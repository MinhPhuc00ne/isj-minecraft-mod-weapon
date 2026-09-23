/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package net.unusual.block_factorys_bosses.block.abstracts;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class AbstractDoubleBlock
extends Block {
    public static final IntegerProperty BLOCK_HALF = IntegerProperty.create((String)"blockstate", (int)1, (int)2);
    public static final int UPPER = 1;
    public static final int LOWER = 2;

    public AbstractDoubleBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(BLOCK_HALF, Integer.valueOf(2)));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        return blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(context) ? super.getStateForPlacement(context) : null;
    }

    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0;
    }

    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if ((Integer)state.getValue(BLOCK_HALF) == 1) {
            return this.getUpperShape(state, level, pos, context);
        }
        return this.getLowerShape(state, level, pos, context);
    }

    protected abstract VoxelShape getUpperShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4);

    protected abstract VoxelShape getLowerShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4);

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{BLOCK_HALF});
    }

    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }

    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative()) {
            AbstractDoubleBlock.preventCreativeDropFromBottomPart(level, pos, state, player);
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    protected static void preventCreativeDropFromBottomPart(Level level, BlockPos pos, BlockState state, Player player) {
        BlockPos blockpos;
        BlockState blockstate;
        if ((Integer)state.getValue(BLOCK_HALF) == 1 && (blockstate = level.getBlockState(blockpos = pos.below())).is(state.getBlock()) && (Integer)blockstate.getValue(BLOCK_HALF) == 2) {
            level.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
            level.levelEvent(player, 2001, blockpos, Block.getId((BlockState)blockstate));
        }
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if ((Integer)state.getValue(BLOCK_HALF) != 1) {
            return super.canSurvive(state, level, pos);
        }
        BlockState blockstate = level.getBlockState(pos.below());
        if (state.getBlock() != this) {
            return super.canSurvive(state, level, pos);
        }
        return blockstate.is((Block)this) && (Integer)blockstate.getValue(BLOCK_HALF) == 2;
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState blockState, LevelAccessor level, BlockPos pos, BlockPos blockPos) {
        int half = (Integer)state.getValue(BLOCK_HALF);
        if (direction.getAxis() != Direction.Axis.Y || half == 2 != (direction == Direction.UP) || blockState.is((Block)this) && (Integer)blockState.getValue(BLOCK_HALF) != half) {
            return half == 2 && direction == Direction.DOWN && !state.canSurvive((LevelReader)level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, blockState, level, pos, blockPos);
        }
        return Blocks.AIR.defaultBlockState();
    }

    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity living, ItemStack stack) {
        BlockPos blockpos = pos.above();
        level.setBlock(blockpos, (BlockState)this.defaultBlockState().setValue(BLOCK_HALF, Integer.valueOf(1)), 3);
    }
}

