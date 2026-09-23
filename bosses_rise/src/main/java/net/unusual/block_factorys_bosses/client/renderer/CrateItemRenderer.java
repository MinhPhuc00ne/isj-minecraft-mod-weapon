/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.client.model.geom.EntityModelSet
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 */
package net.unusual.block_factorys_bosses.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.model.ModelCrate1;
import net.unusual.block_factorys_bosses.item.CrateEntityItem;

public class CrateItemRenderer
extends BlockEntityWithoutLevelRenderer {
    private static final ResourceLocation TEXTURE = BossesRise.prefix("textures/entities/crate.png");
    private final EntityModel<?> model;

    public CrateItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
        super(dispatcher, modelSet);
        this.model = new ModelCrate1(modelSet.bakeLayer(ModelCrate1.LAYER_LOCATION));
    }

    public void renderByItem(ItemStack stack, ItemDisplayContext ctx, PoseStack pose, MultiBufferSource buffer, int light, int overlay) {
        if (!(stack.getItem() instanceof CrateEntityItem)) {
            return;
        }
        VertexConsumer vb = buffer.getBuffer(RenderType.entityCutout((ResourceLocation)TEXTURE));
        pose.pushPose();
        pose.translate(0.5f, 1.5f, 0.5f);
        pose.mulPose(Axis.XP.rotationDegrees(180.0f));
        this.model.renderToBuffer(pose, vb, light, overlay);
        pose.popPose();
    }
}

