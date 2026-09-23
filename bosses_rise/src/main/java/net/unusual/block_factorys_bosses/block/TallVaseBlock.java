/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.BlockGetter
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
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.unusual.block_factorys_bosses.block.abstracts.AbstractDoubleBlock;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TallVaseBlock
extends AbstractDoubleBlock {
    private static final VoxelShape UPPER = Shapes.or((VoxelShape)TallVaseBlock.box((double)5.0, (double)-16.0, (double)5.0, (double)11.0, (double)-14.0, (double)11.0), (VoxelShape[])new VoxelShape[]{TallVaseBlock.box((double)3.0, (double)-14.0, (double)3.0, (double)13.0, (double)6.0, (double)13.0), TallVaseBlock.box((double)6.0, (double)6.0, (double)6.0, (double)10.0, (double)11.0, (double)10.0), TallVaseBlock.box((double)5.0, (double)11.0, (double)5.0, (double)11.0, (double)13.0, (double)11.0)});
    private static final VoxelShape LOWER = Shapes.or((VoxelShape)TallVaseBlock.box((double)5.0, (double)0.0, (double)5.0, (double)11.0, (double)2.0, (double)11.0), (VoxelShape[])new VoxelShape[]{TallVaseBlock.box((double)3.0, (double)2.0, (double)3.0, (double)13.0, (double)22.0, (double)13.0), TallVaseBlock.box((double)6.0, (double)22.0, (double)6.0, (double)10.0, (double)27.0, (double)10.0), TallVaseBlock.box((double)5.0, (double)27.0, (double)5.0, (double)11.0, (double)29.0, (double)11.0)});

    public TallVaseBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.DECORATED_POT).strength(1.0f, 10.0f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
    }

    @Override
    protected VoxelShape getUpperShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return UPPER;
    }

    @Override
    protected VoxelShape getLowerShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return LOWER;
    }
}

