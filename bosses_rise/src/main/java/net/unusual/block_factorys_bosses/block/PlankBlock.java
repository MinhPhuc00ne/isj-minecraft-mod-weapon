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
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.EntityBlock
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.DirectionProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package net.unusual.block_factorys_bosses.block;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.unusual.block_factorys_bosses.block.abstracts.AbstractStackableBlock;
import net.unusual.block_factorys_bosses.block.entity.PlankBlockEntity;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PlankBlock
extends AbstractStackableBlock
implements EntityBlock {
    public static final int MIN_STACK = 1;
    public static final int MAX_STACK = 5;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty PLANKS = IntegerProperty.create((String)"planks", (int)1, (int)5);
    private static final VoxelShape FLOOR = PlankBlock.box((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)3.0, (double)16.0);

    public PlankBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1.0f, 5.0f).noOcclusion().noCollission().isRedstoneConductor((bs, br, bp) -> false), PLANKS, 1, 5);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(PLANKS, Integer.valueOf(1)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, PLANKS});
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (!this.canSurvive(this.defaultBlockState(), (LevelReader)context.getLevel(), context.getClickedPos())) {
            return null;
        }
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        return blockstate.is((Block)this) ? (BlockState)blockstate.setValue(this.stackableProperty, Integer.valueOf(Math.min(this.maxStack, (Integer)blockstate.getValue(this.stackableProperty) + 1))) : (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return FLOOR;
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return 0;
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PlankBlockEntity(pos, state);
    }
}

