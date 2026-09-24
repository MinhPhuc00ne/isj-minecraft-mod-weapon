/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package net.unusual.block_factorys_bosses.block;

import java.util.List;
import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.block.abstracts.AbstractDoubleBlock;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TallCandlesBlock
extends AbstractDoubleBlock {
    private static final VoxelShape UPPER = TallCandlesBlock.box((double)3.0, (double)-16.0, (double)3.0, (double)13.0, (double)16.0, (double)13.0);
    private static final VoxelShape LOWER = TallCandlesBlock.box((double)3.0, (double)0.0, (double)3.0, (double)13.0, (double)32.0, (double)13.0);
    public static final Vec3[] FLAME_POINTS_TWISTED = new Vec3[]{new Vec3(0.6875, 0.8125, 0.5), new Vec3(0.5625, 1.125, 0.5625), new Vec3(0.4375, 1.0, 0.4375), new Vec3(0.3125, 0.625, 0.5)};
    public static final Vec3[] FLAME_POINTS_CROSS = new Vec3[]{new Vec3(0.5, 1.0625, 0.5), new Vec3(0.875, 1.0625, 0.5), new Vec3(0.125, 1.0625, 0.5), new Vec3(0.5, 1.0625, 0.125), new Vec3(0.5, 1.0625, 0.875)};
    private final Supplier<SimpleParticleType> particleType;
    private final Vec3[] particlePoints;

    public TallCandlesBlock(Supplier<SimpleParticleType> particleType, Vec3[] particlePoints) {
        super(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.0f, 10.0f).lightLevel(s -> (Integer)s.getValue((Property)AbstractDoubleBlock.BLOCK_HALF) == 1 ? 14 : 0).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        this.particleType = particleType;
        this.particlePoints = particlePoints;
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, list, tooltipFlag);
        list.add((Component)Component.translatable((String)(this.getDescriptionId() + ".description_0")));
    }

    @Override
    protected VoxelShape getUpperShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return UPPER;
    }

    @Override
    protected VoxelShape getLowerShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return LOWER;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        block4: {
            if ((Integer)state.getValue((Property)AbstractDoubleBlock.BLOCK_HALF) != 1) break block4;
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            if (random.nextBoolean()) {
                for (Vec3 point : this.particlePoints) {
                    level.addParticle((ParticleOptions)this.particleType.get(), (double)x + point.x, (double)y + point.y, (double)z + point.z, 0.0, 0.0, 0.0);
                }
            } else {
                for (Vec3 point : this.particlePoints) {
                    level.addParticle((ParticleOptions)this.particleType.get(), (double)x + point.x + Mth.nextDouble((RandomSource)random, (double)-0.05, (double)0.05), (double)y + point.y + Mth.nextDouble((RandomSource)random, (double)-0.05, (double)0.05), (double)z + point.z + Mth.nextDouble((RandomSource)random, (double)-0.05, (double)0.05), 0.0, 0.0, 0.0);
                }
            }
        }
    }
}

