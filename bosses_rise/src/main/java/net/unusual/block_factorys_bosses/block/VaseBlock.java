/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package net.unusual.block_factorys_bosses.block;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class VaseBlock
extends Block {
    public VaseBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.DECORATED_POT).strength(1.0f, 10.0f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
    }

    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0;
    }

    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.or((VoxelShape)VaseBlock.box((double)3.0, (double)0.0, (double)3.0, (double)13.0, (double)2.0, (double)13.0), (VoxelShape[])new VoxelShape[]{VaseBlock.box((double)1.0, (double)2.0, (double)1.0, (double)15.0, (double)13.0, (double)15.0), VaseBlock.box((double)4.0, (double)13.0, (double)4.0, (double)12.0, (double)15.0, (double)12.0), VaseBlock.box((double)3.0, (double)15.0, (double)3.0, (double)13.0, (double)17.0, (double)13.0), VaseBlock.box((double)5.0, (double)16.0, (double)5.0, (double)11.0, (double)17.0, (double)11.0)});
    }
}

