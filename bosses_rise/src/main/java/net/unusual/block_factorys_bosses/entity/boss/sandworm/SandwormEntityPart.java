/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 */
package net.unusual.block_factorys_bosses.entity.boss.sandworm;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.entity.boss.part.AbstractGeoEntityPart;
import net.unusual.block_factorys_bosses.entity.boss.sandworm.SandwormEntity;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SandwormEntityPart
extends AbstractGeoEntityPart<SandwormEntity, SandwormEntityPart> {
    public SandwormEntityPart(SandwormEntity parent, int partIndex, String anchorBoneName, String bodyBoneName, float width, float height) {
        super(parent, partIndex, anchorBoneName, bodyBoneName, width, height);
    }
}

