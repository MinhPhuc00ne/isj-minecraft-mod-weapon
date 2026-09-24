/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.component.CustomData
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.phys.Vec3
 */
package net.unusual.block_factorys_bosses.item;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.decoration.CannonEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CannonEntityItem
extends Item {
    private final Supplier<? extends EntityType<? extends CannonEntity>> typeSupplier;

    public CannonEntityItem(Supplier<? extends EntityType<? extends CannonEntity>> typeSupplier, Item.Properties properties) {
        super(properties);
        this.typeSupplier = typeSupplier;
    }

    public InteractionResult useOn(UseOnContext context) {
        CustomData customdata;
        Direction viewDirection;
        Level level;
        ItemStack stack;
        BlockPos pos = context.getClickedPos();
        Direction face = context.getClickedFace();
        Player player = context.getPlayer();
        if (!this.mayPlace(player, face, stack = context.getItemInHand(), pos, level = context.getLevel())) {
            return InteractionResult.FAIL;
        }
        CannonEntity entity = (CannonEntity)this.typeSupplier.get().create(level);
        if (entity == null) {
            return InteractionResult.CONSUME;
        }
        Vec3 center = Vec3.atBottomCenterOf((Vec3i)pos);
        Direction direction = viewDirection = player != null ? Direction.fromYRot((double)player.getYRot()) : Direction.NORTH;
        if (face != Direction.UP) {
            return InteractionResult.FAIL;
        }
        Vec3 vec3 = new Vec3(center.x, context.getClickLocation().y, center.z);
        entity.moveTo(vec3);
        entity.setAttachDiff(pos.getY() - entity.blockPosition().getY());
        if (player != null) {
            entity.setYRot(viewDirection.toYRot());
            entity.setCoreYRot((int)viewDirection.toYRot());
        }
        if (!(customdata = (CustomData)stack.getOrDefault(DataComponents.ENTITY_DATA, (Object)CustomData.EMPTY)).isEmpty()) {
            EntityType.updateCustomEntityTag((Level)level, (Player)player, (Entity)entity, (CustomData)customdata);
        }
        if (entity.survives()) {
            if (!level.isClientSide) {
                entity.playSound(SoundType.HEAVY_CORE.getPlaceSound(), 1.0f, 1.0f);
                level.gameEvent((Entity)player, (Holder)GameEvent.ENTITY_PLACE, entity.position());
                level.addFreshEntity((Entity)entity);
            }
            stack.shrink(1);
            return InteractionResult.sidedSuccess((boolean)level.isClientSide);
        }
        return InteractionResult.CONSUME;
    }

    protected boolean mayPlace(@Nullable Player player, Direction face, ItemStack stack, BlockPos pos, Level level) {
        if (player != null && !player.mayUseItemAt(pos, face, stack)) {
            return false;
        }
        BlockState state = level.getBlockState(pos);
        if (state.is((Block)BossesRiseBlocks.BIG_CHAIN.get()) && level.getBlockState(pos.below()).canBeReplaced()) {
            return true;
        }
        return face.getAxis().isVertical() && level.getBlockState(pos.relative(face)).canBeReplaced();
    }
}

