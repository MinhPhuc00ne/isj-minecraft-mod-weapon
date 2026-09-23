/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.geom.EntityModelSet
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.AnimationState
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.component.CustomData
 *  net.minecraft.world.level.ItemLike
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package net.unusual.block_factorys_bosses.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ItemLike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.AnimationTickHolder;
import net.unusual.block_factorys_bosses.client.model.Modelknight_sword;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.item.KnightSwordItem;

@OnlyIn(value=Dist.CLIENT)
public class KnightSwordItemRenderer
extends BlockEntityWithoutLevelRenderer {
    private final EntityModelSet entityModelSet;
    private final ItemStack transformSource;

    public KnightSwordItemRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
        this.entityModelSet = entityModelSet;
        this.transformSource = new ItemStack((ItemLike)BossesRiseItems.KNIGHT_SWORD.get());
    }

    public void renderByItem(ItemStack itemstack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Modelknight_sword model = new Modelknight_sword(this.entityModelSet.bakeLayer(Modelknight_sword.LAYER_LOCATION));
        if (model == null) {
            return;
        }
        if (displayContext != ItemDisplayContext.GUI) {
            float tick = AnimationTickHolder.getRenderTime();
            poseStack.pushPose();
            Minecraft.getInstance().getItemRenderer().getModel(this.transformSource, null, null, 0).getTransforms().getTransform(displayContext).apply(KnightSwordItemRenderer.isLeftHand(displayContext), poseStack);
            poseStack.translate(0.5, 1.55, 0.6);
            poseStack.scale(1.05f, 1.05f, 1.05f);
            poseStack.scale(1.0f, -1.0f, displayContext == ItemDisplayContext.GUI ? -1.0f : 1.0f);
            if (displayContext.firstPerson()) {
                poseStack.scale(1.1f, 1.1f, 1.1f);
                poseStack.mulPose(Axis.XP.rotationDegrees(-22.5f));
                if (displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) {
                    poseStack.translate(0.5, 0.4, -0.2);
                }
                if (displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND) {
                    poseStack.translate(-0.5, 0.4, -0.2);
                }
            }
            String key = ((CustomData)itemstack.getOrDefault(DataComponents.CUSTOM_DATA, (Object)CustomData.EMPTY)).copyTag().getString("itemKey");
            AnimationState idleState = KnightSwordItem.IDLE_STATES.computeIfAbsent(key, s -> new AnimationState());
            AnimationState swingState = KnightSwordItem.SWING_STATES.computeIfAbsent(key, s -> new AnimationState());
            double swing_animtime = ((CustomData)itemstack.getOrDefault(DataComponents.CUSTOM_DATA, (Object)CustomData.EMPTY)).copyTag().getDouble("swing_animtime");
            idleState.animateWhen(swing_animtime == 0.0, Math.round(tick));
            swingState.animateWhen(swing_animtime > 0.0, Math.round(tick));
            int firstPersonContext = 0;
            if (displayContext.firstPerson()) {
                if (displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) {
                    firstPersonContext = 1;
                }
                if (displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND) {
                    firstPersonContext = 2;
                }
            }
            model.setupItemAnim(key, firstPersonContext, tick);
            int index = Mth.floor((float)((float)(Math.round(AnimationTickHolder.getRenderTime()) % 100) * 0.1f));
            VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect((MultiBufferSource)bufferSource, (RenderType)model.renderType(BossesRise.prefix("textures/entities/knight_sword/knight_sword_" + index + ".png")), (boolean)false, (boolean)itemstack.hasFoil());
            model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
            poseStack.popPose();
        }
    }

    private static boolean isLeftHand(ItemDisplayContext type) {
        return type == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || type == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
    }

    private static boolean isInventory(ItemDisplayContext type) {
        return type == ItemDisplayContext.GUI || type == ItemDisplayContext.FIXED;
    }
}

