/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.ContainerHelper
 *  net.minecraft.world.WorldlyContainer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.ChestMenu
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.StructureManager
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.levelgen.structure.Structure
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.neoforge.items.wrapper.SidedInvWrapper
 */
package net.unusual.block_factorys_bosses.block.entity;

import java.util.Map;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.boss.AbstractBossEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseBlockEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;

public class BossSpawnerBlockEntity
extends RandomizableContainerBlockEntity
implements WorldlyContainer {
    private NonNullList<ItemStack> stacks = NonNullList.withSize((int)9, ItemStack.EMPTY);
    private final SidedInvWrapper handler = new SidedInvWrapper((WorldlyContainer)this, null);

    public BossSpawnerBlockEntity(BlockPos position, BlockState state) {
        super((BlockEntityType)BossesRiseBlockEntities.BOSS_SPAWNER.get(), position, state);
    }

    public void tick(Level level) {
        Level level2 = this.getLevel();
        if (!(level2 instanceof ServerLevel)) {
            return;
        }
        ServerLevel serverLevel = (ServerLevel)level2;
        if (!serverLevel.getEntitiesOfClass(AbstractBossEntity.class, AABB.ofSize((Vec3)this.getBlockPos().getCenter(), (double)16.0, (double)16.0, (double)16.0)).isEmpty()) {
            serverLevel.destroyBlock(this.getBlockPos(), false);
            return;
        }
        Player p = level.getNearestPlayer(TargetingConditions.forCombat(), (double)this.getBlockPos().getX(), (double)this.getBlockPos().getY(), (double)this.getBlockPos().getZ());
        if (p == null || p.distanceToSqr(this.getBlockPos().getBottomCenter()) >= 64.0) {
            return;
        }
        StructureManager structureManager = serverLevel.structureManager();
        EntityType toSpawn = null;
        for (Map.Entry entry : structureManager.getAllStructuresAt(this.getBlockPos()).entrySet()) {
            if (((Structure)entry.getKey()).type() != BossesRise.DRAGON_TOWER.get()) continue;
            toSpawn = (EntityType)BossesRiseEntities.INFERNAL_DRAGON.get();
        }
        if (toSpawn == null) {
            return;
        }
        Entity entity = toSpawn.create(level);
        if (entity == null) {
            return;
        }
        entity.setPos((double)this.getBlockPos().getX(), (double)this.getBlockPos().getY(), (double)this.getBlockPos().getZ());
        level.addFreshEntity(entity);
        if (entity instanceof AbstractBossEntity) {
            AbstractBossEntity bossEntity = (AbstractBossEntity)entity;
            bossEntity.onBossSpawnerSpawn(this);
        }
        level.removeBlock(this.getBlockPos(), false);
    }

    public void loadAdditional(CompoundTag compound, HolderLookup.Provider lookupProvider) {
        super.loadAdditional(compound, lookupProvider);
        if (!this.tryLoadLootTable(compound)) {
            this.stacks = NonNullList.withSize((int)this.getContainerSize(), ItemStack.EMPTY);
        }
        ContainerHelper.loadAllItems((CompoundTag)compound, this.stacks, (HolderLookup.Provider)lookupProvider);
    }

    public void saveAdditional(CompoundTag compound, HolderLookup.Provider lookupProvider) {
        super.saveAdditional(compound, lookupProvider);
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems((CompoundTag)compound, this.stacks, (HolderLookup.Provider)lookupProvider);
        }
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create((BlockEntity)this);
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
        return this.saveWithFullMetadata(lookupProvider);
    }

    public int getContainerSize() {
        return this.stacks.size();
    }

    public boolean isEmpty() {
        for (ItemStack itemstack : this.stacks) {
            if (itemstack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    public Component getDefaultName() {
        return this.getBlockState().getBlock().getName();
    }

    public int getMaxStackSize() {
        return 64;
    }

    public AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return ChestMenu.threeRows((int)id, (Inventory)inventory);
    }

    public Component getDisplayName() {
        return this.getDefaultName();
    }

    protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    protected void setItems(NonNullList<ItemStack> stacks) {
        this.stacks = stacks;
    }

    public boolean canPlaceItem(int index, ItemStack stack) {
        return true;
    }

    public int[] getSlotsForFace(Direction side) {
        return IntStream.range(0, this.getContainerSize()).toArray();
    }

    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(index, stack);
    }

    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }

    public SidedInvWrapper getItemHandler() {
        return this.handler;
    }
}

