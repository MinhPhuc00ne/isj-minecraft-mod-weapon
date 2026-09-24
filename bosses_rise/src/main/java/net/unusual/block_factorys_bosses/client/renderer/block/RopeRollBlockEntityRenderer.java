/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.phys.AABB
 *  software.bernie.geckolib.renderer.GeoBlockRenderer
 */
package net.unusual.block_factorys_bosses.client.renderer.block;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.block.entity.RopeRollBlockEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RopeRollBlockEntityRenderer
extends GeoBlockRenderer<RopeRollBlockEntity> {
    public RopeRollBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new CustomBlockGeoModel(BossesRise.prefix("rope_roll")));
    }

    public AABB getRenderBoundingBox(RopeRollBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(1.0);
    }
}

