/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 */
package net.unusual.block_factorys_bosses.entity.boss.kraken;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenTentacleEntity;
import net.unusual.block_factorys_bosses.entity.boss.part.AbstractGeoEntityPart;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class KrakenTentacleEntityPart
extends AbstractGeoEntityPart<KrakenTentacleEntity, KrakenTentacleEntityPart> {
    public KrakenTentacleEntityPart(KrakenTentacleEntity parent, int partIndex, String anchorBoneName, String bodyBoneName, float width, float height) {
        super(parent, partIndex, anchorBoneName, bodyBoneName, width, height);
    }
}

