/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.pipeline.RenderTarget
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.particle.ParticleRenderType
 *  net.minecraft.client.renderer.RenderStateShard
 *  net.minecraft.client.renderer.texture.TextureAtlas
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.Nullable
 */
package net.unusual.block_factorys_bosses.client.particle;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public interface BRParticleRenderType
extends ParticleRenderType {
    public static final ParticleRenderType NORMAL_TRANSLUCENT = new BRParticleRenderType(){

        @Override
        public void end() {
            if (Minecraft.useShaderTransparency()) {
                Minecraft instance = Minecraft.getInstance();
                RenderTarget particlesTarget = instance.levelRenderer.getParticlesTarget();
                particlesTarget.copyDepthFrom(instance.getMainRenderTarget());
                particlesTarget.bindWrite(false);
            }
            RenderSystem.disableBlend();
            RenderSystem.depthMask((boolean)true);
        }

        @Nullable
        public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
            if (Minecraft.useShaderTransparency()) {
                Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
            }
            RenderSystem.depthMask((boolean)false);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc((int)770, (int)771);
            RenderSystem.setShaderTexture((int)0, (ResourceLocation)TextureAtlas.LOCATION_PARTICLES);
            return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }
    };
    public static final BRParticleRenderType PARTICLE_SHEET_BLEND = new BRParticleRenderType(){

        public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
            if (Minecraft.useShaderTransparency()) {
                Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
            }
            RenderSystem.depthMask((boolean)false);
            RenderSystem.enableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderTexture((int)0, (ResourceLocation)TextureAtlas.LOCATION_PARTICLES);
            RenderStateShard.LIGHTMAP.setupRenderState();
            return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        @Override
        public void end() {
            if (Minecraft.useShaderTransparency()) {
                BRParticleRenderType.bindParticleRenderTarget();
            }
        }

        public String toString() {
            return "block_factorys_bosses:PARTICLE_SHEET_BLEND";
        }
    };
    public static final ParticleRenderType PARTICLE_SHEET_BLEND_NO_CULL = new BRParticleRenderType(){

        public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
            RenderSystem.disableCull();
            return PARTICLE_SHEET_BLEND.begin(tesselator, textureManager);
        }

        @Override
        public void end() {
            RenderSystem.enableCull();
            PARTICLE_SHEET_BLEND.end();
        }

        public String toString() {
            return "block_factorys_bosses:PARTICLE_SHEET_BLEND_NO_CULL";
        }
    };

    public static void bindParticleRenderTarget() {
        Minecraft instance = Minecraft.getInstance();
        RenderTarget particlesTarget = instance.levelRenderer.getParticlesTarget();
        particlesTarget.copyDepthFrom(instance.getMainRenderTarget());
        particlesTarget.bindWrite(false);
    }

    public void end();
}

