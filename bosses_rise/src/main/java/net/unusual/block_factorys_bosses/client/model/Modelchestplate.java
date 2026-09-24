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

public class Modelchestplate<T extends Entity>
extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelchestplate"), "main");
    public final ModelPart body;
    public final ModelPart jetpack;
    public final ModelPart leftArm;
    public final ModelPart rightArm;

    public Modelchestplate(ModelPart root) {
        this.body = root.getChild("body");
        this.jetpack = this.body.getChild("jetpack");
        this.leftArm = root.getChild("leftArm");
        this.rightArm = root.getChild("rightArm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(40, 16).addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.76f)).texOffs(0, 0).addBox(-5.0f, 0.0f, -4.0f, 10.0f, 7.0f, 2.0f, new CubeDeformation(0.5f)).texOffs(24, 0).addBox(-5.0f, 0.0f, 2.0f, 10.0f, 7.0f, 2.0f, new CubeDeformation(0.5f)), PartPose.offset((float)0.0f, (float)0.0f, (float)0.0f));
        PartDefinition jetpack = body.addOrReplaceChild("jetpack", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)24.0f, (float)0.0f));
        PartDefinition leftArm = partdefinition.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(48, 0).addBox(-0.9f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.75f)).texOffs(18, 9).addBox(0.35f, -3.0f, -3.0f, 5.0f, 5.0f, 6.0f, new CubeDeformation(0.5f)).texOffs(0, 9).addBox(1.6f, 4.5f, -3.0f, 3.0f, 5.0f, 6.0f, new CubeDeformation(0.5f)), PartPose.offset((float)5.0f, (float)2.0f, (float)0.0f));
        PartDefinition rightArm = partdefinition.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(48, 0).mirror().addBox(-3.1f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.75f)).mirror(false).texOffs(18, 9).mirror().addBox(-5.35f, -3.0f, -3.0f, 5.0f, 5.0f, 6.0f, new CubeDeformation(0.5f)).mirror(false).texOffs(0, 9).mirror().addBox(-4.6f, 4.5f, -3.0f, 3.0f, 5.0f, 6.0f, new CubeDeformation(0.5f)).mirror(false), PartPose.offset((float)-5.0f, (float)2.0f, (float)0.0f));
        return LayerDefinition.create((MeshDefinition)meshdefinition, (int)64, (int)32);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
        this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
        this.leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
        this.rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
}

