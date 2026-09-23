/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.client.model.geom.ModelLayerLocation
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.model.geom.PartPose
 *  net.minecraft.client.model.geom.builders.CubeDeformation
 *  net.minecraft.client.model.geom.builders.CubeListBuilder
 *  net.minecraft.client.model.geom.builders.LayerDefinition
 *  net.minecraft.client.model.geom.builders.MeshDefinition
 *  net.minecraft.client.model.geom.builders.PartDefinition
 *  net.minecraft.world.entity.Entity
 */
package net.unusual.block_factorys_bosses.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.BossesRise;

public class Modelhelmet<T extends Entity>
extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelhelmet"), "main");
    public final ModelPart root_item;
    public final ModelPart head;

    public Modelhelmet(ModelPart root) {
        this.root_item = root.getChild("root_item");
        this.head = this.root_item.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition root_item = partdefinition.addOrReplaceChild("root_item", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)24.0f, (float)0.0f));
        PartDefinition head = root_item.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.2f, -34.5f, -4.5f, 9.0f, 7.0f, 9.0f, new CubeDeformation(0.0f)).texOffs(44, 57).addBox(3.8f, -37.5f, 0.5f, 2.0f, 6.0f, 3.0f, new CubeDeformation(0.0f)).texOffs(16, 57).addBox(3.8f, -37.5f, 3.5f, 2.0f, 3.0f, 6.0f, new CubeDeformation(0.0f)).texOffs(44, 57).mirror().addBox(-5.2f, -37.5f, 0.5f, 2.0f, 6.0f, 3.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(16, 57).mirror().addBox(-5.2f, -37.5f, 3.5f, 2.0f, 3.0f, 6.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(24, 30).addBox(-2.2f, -34.5f, -10.5f, 5.0f, 5.0f, 6.0f, new CubeDeformation(0.0f)).texOffs(32, 57).addBox(2.8f, -36.5f, -10.5f, 0.0f, 2.0f, 6.0f, new CubeDeformation(0.0f)).texOffs(32, 57).addBox(-2.2f, -36.5f, -10.5f, 0.0f, 2.0f, 6.0f, new CubeDeformation(0.0f)).texOffs(56, 11).addBox(-2.2f, -29.5f, -10.5f, 5.0f, 2.0f, 6.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)26.0f, (float)0.0f));
        return LayerDefinition.create((MeshDefinition)meshdefinition, (int)128, (int)128);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
        this.root_item.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root_item.yRot = netHeadYaw / 57.295776f;
        this.root_item.xRot = headPitch / 57.295776f;
    }
}

