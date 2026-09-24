/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.item.ItemStack
 *  net.neoforged.neoforge.entity.PartEntity
 */
package net.unusual.block_factorys_bosses.entity.boss.part;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.entity.PartEntity;
import net.unusual.block_factorys_bosses.entity.boss.part.AbstractEntityPartParent;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class AbstractEntityPart<ParentT extends Entity, PartT extends AbstractEntityPart<ParentT, PartT>>
extends PartEntity<ParentT> {
    private final int partIndex;
    private EntityDimensions size;

    public AbstractEntityPart(ParentT parent, int partIndex, float width, float height) {
        super(parent);
        this.partIndex = partIndex;
        this.setBoundingBoxSize(width, height);
    }

    public ParentT getParent() {
        return (ParentT)super.getParent();
    }

    public int getPartIndex() {
        return this.partIndex;
    }

    public void setBoundingBoxSize(float width, float height) {
        this.size = EntityDimensions.scalable((float)width, (float)height);
        this.refreshDimensions();
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    protected void readAdditionalSaveData(CompoundTag compound) {
    }

    protected void addAdditionalSaveData(CompoundTag compound) {
    }

    public boolean hurt(DamageSource source, float amount) {
        return ((AbstractEntityPartParent)this.getParent()).hurt(this, source, amount);
    }

    public void igniteForTicks(int ticks) {
        this.getParent().igniteForTicks(ticks);
    }

    public int getRemainingFireTicks() {
        return this.getParent().getRemainingFireTicks();
    }

    public void clearFire() {
        this.getParent().clearFire();
    }

    public boolean is(Entity entity) {
        return this == entity || this.getParent() == entity;
    }

    public boolean isPickable() {
        return this.getParent().isPickable();
    }

    public boolean isAttackable() {
        return this.getParent().isAttackable();
    }

    @Nullable
    public ItemStack getPickResult() {
        return this.getParent().getPickResult();
    }

    public EntityDimensions getDimensions(Pose pose) {
        return this.size;
    }

    public boolean shouldBeSaved() {
        return false;
    }
}

