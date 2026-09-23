/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.AABB
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoBlockRenderer
 */
package net.unusual.block_factorys_bosses.geckolib;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.block.PlankBlock;
import net.unusual.block_factorys_bosses.block.entity.PlankBlockEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomBlockGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PlankBlockEntityRenderer
extends GeoBlockRenderer<PlankBlockEntity> {
    public PlankBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super((GeoModel)new CustomBlockGeoModel<PlankBlockEntity>(BossesRise.prefix("plank")){

            public ResourceLocation getTextureResource(PlankBlockEntity entity) {
                return BossesRise.prefix("textures/block/plank.png");
            }

            public ResourceLocation getModelResource(PlankBlockEntity animatable) {
                return BossesRise.prefix("geo/block/plank_" + String.valueOf(animatable.getBlockState().getValue((Property)PlankBlock.PLANKS)) + ".geo.json");
            }

            public ResourceLocation getAnimationResource(PlankBlockEntity animatable) {
                return BossesRise.prefix("animations/block/plank_" + String.valueOf(animatable.getBlockState().getValue((Property)PlankBlock.PLANKS)) + ".animation.json");
            }
        });
    }
}

