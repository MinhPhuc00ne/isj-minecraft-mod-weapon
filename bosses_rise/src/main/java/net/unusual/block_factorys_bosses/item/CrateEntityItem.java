/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
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
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.neoforge.client.extensions.common.IClientItemExtensions
 */
package net.unusual.block_factorys_bosses.item;

import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
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
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.unusual.block_factorys_bosses.client.renderer.CrateItemRenderer;
import net.unusual.block_factorys_bosses.entity.decoration.CratePileEntity;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CrateEntityItem
extends Item {
    private final Supplier<? extends EntityType<? extends CratePileEntity>> typeSupplier;

    public CrateEntityItem(Supplier<? extends EntityType<? extends CratePileEntity>> typeSupplier, Item.Properties properties) {
        super(properties);
        this.typeSupplier = typeSupplier;
    }

    public EntityType<? extends CratePileEntity> getEntityType() {
        return this.typeSupplier.get();
    }

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions(){
            private BlockEntityWithoutLevelRenderer renderer;

            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    Minecraft mc = Minecraft.getInstance();
                    this.renderer = new CrateItemRenderer(mc.getBlockEntityRenderDispatcher(), mc.getEntityModels());
                }
                return this.renderer;
            }
        });
    }

    public InteractionResult useOn(UseOnContext context) {
        CustomData customdata;
        Level level;
        ItemStack stack;
        BlockPos pos = context.getClickedPos();
        Direction face = context.getClickedFace();
        Player player = context.getPlayer();
        if (!this.mayPlace(player, face, stack = context.getItemInHand(), pos, level = context.getLevel())) {
            return InteractionResult.FAIL;
        }
        CratePileEntity entity = (CratePileEntity)this.typeSupplier.get().create(level);
        if (entity == null) {
            return InteractionResult.CONSUME;
        }
        Vec3 center = Vec3.atBottomCenterOf((Vec3i)pos);
        Direction viewDirection = player != null ? Direction.fromYRot((double)player.getYRot()) : Direction.NORTH;
        Vec3 vec3 = face == Direction.UP ? new Vec3(center.x, context.getClickLocation().y, center.z) : new Vec3(center.x, context.getClickLocation().y - (double)entity.getBbHeight(), center.z);
        entity.moveTo(vec3);
        entity.setAttachDiff(pos.getY() - entity.blockPosition().getY());
        if (player != null) {
            entity.setYRot(viewDirection.getOpposite().toYRot());
        }
        if (!(customdata = (CustomData)stack.getOrDefault(DataComponents.ENTITY_DATA, (Object)CustomData.EMPTY)).isEmpty()) {
            EntityType.updateCustomEntityTag((Level)level, (Player)player, (Entity)entity, (CustomData)customdata);
        }
        if (entity.survives()) {
            if (!level.isClientSide) {
                entity.playSound(SoundType.WOOD.getPlaceSound(), 1.0f, 1.0f);
                level.gameEvent((Entity)player, (Holder)GameEvent.ENTITY_PLACE, entity.position());
                level.addFreshEntity((Entity)entity);
            }
            stack.shrink(1);
            return InteractionResult.sidedSuccess((boolean)level.isClientSide);
        }
        return InteractionResult.CONSUME;
    }

    protected boolean mayPlace(@Nullable Player player, Direction face, ItemStack stack, BlockPos pos, Level level) {
        return player == null || player.mayUseItemAt(pos, face, stack);
    }
}

