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

public class Modelicespike_pr<T extends Entity>
extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelicespike_pr"), "main");
    public final ModelPart icespike;

    public Modelicespike_pr(ModelPart root) {
        this.icespike = root.getChild("icespike");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition icespike = partdefinition.addOrReplaceChild("icespike", CubeListBuilder.create().texOffs(168, 98).addBox(-6.0f, -6.0f, -12.0f, 13.0f, 14.0f, 23.0f, new CubeDeformation(-0.05f)).texOffs(228, 216).addBox(-6.0f, -6.0f, -21.0f, 9.0f, 9.0f, 9.0f, new CubeDeformation(0.0f)).texOffs(222, 0).addBox(0.0f, -2.0f, -30.0f, 7.0f, 7.0f, 18.0f, new CubeDeformation(0.0f)).texOffs(220, 135).addBox(-6.0f, -6.0f, 11.0f, 10.0f, 11.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(68, 218).addBox(-9.0f, -9.0f, -7.0f, 11.0f, 12.0f, 14.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.25f, (float)6.0f, (float)0.5f, (float)1.5708f, (float)0.0f, (float)0.0f));
        return LayerDefinition.create((MeshDefinition)meshdefinition, (int)512, (int)512);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
        this.icespike.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
}

