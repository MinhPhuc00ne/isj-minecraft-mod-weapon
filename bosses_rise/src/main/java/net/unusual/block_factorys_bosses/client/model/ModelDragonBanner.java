/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.model.Model
 *  net.minecraft.client.model.geom.ModelLayerLocation
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.model.geom.PartPose
 *  net.minecraft.client.model.geom.builders.CubeDeformation
 *  net.minecraft.client.model.geom.builders.CubeListBuilder
 *  net.minecraft.client.model.geom.builders.LayerDefinition
 *  net.minecraft.client.model.geom.builders.MeshDefinition
 *  net.minecraft.client.model.geom.builders.PartDefinition
 *  net.minecraft.client.renderer.RenderType
 */
package net.unusual.block_factorys_bosses.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.unusual.block_factorys_bosses.BossesRise;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ModelDragonBanner
extends Model {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("model_dragon_banner"), "main");
    public final ModelPart main;

    public ModelDragonBanner(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.main = root.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-17.0f, -92.0f, -1.0f, 38.0f, 92.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offset((float)-2.0f, (float)0.0f, (float)0.0f));
        return LayerDefinition.create((MeshDefinition)meshdefinition, (int)256, (int)256);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        this.main.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}

