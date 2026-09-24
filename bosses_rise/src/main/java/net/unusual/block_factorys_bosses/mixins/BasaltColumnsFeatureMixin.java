/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.levelgen.feature.BasaltColumnsFeature
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Mutable
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package net.unusual.block_factorys_bosses.mixins;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.BasaltColumnsFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={BasaltColumnsFeature.class})
public class BasaltColumnsFeatureMixin {
    @Shadow
    @Final
    @Mutable
    private static ImmutableList<Block> CANNOT_PLACE_ON;

    @Inject(method={"<clinit>"}, at={@At(value="TAIL")})
    private static void addAdditionalCannotPlaceOnBlocks(CallbackInfo ci) {
        ImmutableList.Builder builder = ImmutableList.builder();
        builder.addAll(CANNOT_PLACE_ON);
        builder.add((Object)Blocks.WARPED_STAIRS);
        builder.add((Object)Blocks.DEEPSLATE_TILE_STAIRS);
        builder.add((Object)Blocks.DARK_PRISMARINE_STAIRS);
        builder.add((Object)Blocks.PRISMARINE_BRICKS);
        builder.add((Object)Blocks.DARK_PRISMARINE);
        builder.add((Object)Blocks.PRISMARINE_BRICK_STAIRS);
        builder.add((Object)Blocks.COBBLED_DEEPSLATE);
        builder.add((Object)Blocks.COBBLED_DEEPSLATE_SLAB);
        builder.add((Object)Blocks.DEEPSLATE);
        builder.add((Object)Blocks.CYAN_WOOL);
        builder.add((Object)Blocks.CYAN_CONCRETE_POWDER);
        builder.add((Object)Blocks.GRAY_CONCRETE_POWDER);
        builder.add((Object)Blocks.PRISMARINE_STAIRS);
        builder.add((Object)Blocks.WAXED_WEATHERED_COPPER_TRAPDOOR);
        builder.add((Object)Blocks.DEEPSLATE_TILE_SLAB);
        builder.add((Object)Blocks.BLACK_CANDLE);
        builder.add((Object)Blocks.CYAN_CANDLE);
        builder.add((Object)Blocks.PRISMARINE_WALL);
        builder.add((Object)Blocks.COBBLED_DEEPSLATE_STAIRS);
        builder.add((Object)Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS);
        builder.add((Object)Blocks.WARPED_WART_BLOCK);
        builder.add((Object)Blocks.WAXED_WEATHERED_COPPER_BULB);
        CANNOT_PLACE_ON = builder.build();
    }
}

