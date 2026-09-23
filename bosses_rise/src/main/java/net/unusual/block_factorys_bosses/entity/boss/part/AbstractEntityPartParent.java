/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 */
package net.unusual.block_factorys_bosses.entity.boss.part;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.entity.boss.part.AbstractEntityPart;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface AbstractEntityPartParent<ParentT extends Entity, PartT extends AbstractEntityPart<ParentT, PartT>> {
    public boolean hurt(PartT var1, DamageSource var2, float var3);
}

