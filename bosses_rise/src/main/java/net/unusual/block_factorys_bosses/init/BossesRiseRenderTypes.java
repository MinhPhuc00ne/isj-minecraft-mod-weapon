/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.VertexFormat
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.Util
 *  net.minecraft.client.renderer.RenderStateShard
 *  net.minecraft.client.renderer.RenderStateShard$EmptyTextureStateShard
 *  net.minecraft.client.renderer.RenderStateShard$TextureStateShard
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.RenderType$CompositeState
 *  net.minecraft.resources.ResourceLocation
 */
package net.unusual.block_factorys_bosses.init;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.function.BiFunction;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class BossesRiseRenderTypes {
    public static final BiFunction<ResourceLocation, Boolean, RenderType> ENTITY_TRANSLUCENT_EMISSIVE_CULL = Util.memoize((textureLocation, outline) -> {
        RenderType.CompositeState state = RenderType.CompositeState.builder().setShaderState(RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER).setTextureState((RenderStateShard.EmptyTextureStateShard)new RenderStateShard.TextureStateShard(textureLocation, false, false)).setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY).setWriteMaskState(RenderStateShard.COLOR_WRITE).setOverlayState(RenderStateShard.OVERLAY).createCompositeState(outline.booleanValue());
        return RenderType.create((String)"block_factorys_bosses:entity_translucent_emissive_cull", (VertexFormat)DefaultVertexFormat.NEW_ENTITY, (VertexFormat.Mode)VertexFormat.Mode.QUADS, (int)1536, (boolean)true, (boolean)false, (RenderType.CompositeState)state);
    });

    public static RenderType entityTranslucentEmissiveCull(ResourceLocation textureLocation, boolean outline) {
        return ENTITY_TRANSLUCENT_EMISSIVE_CULL.apply(textureLocation, outline);
    }

    public static RenderType entityTranslucentEmissiveCull(ResourceLocation textureLocation) {
        return BossesRiseRenderTypes.entityTranslucentEmissiveCull(textureLocation, true);
    }
}

