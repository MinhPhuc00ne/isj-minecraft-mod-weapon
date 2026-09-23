/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
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
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
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

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CageModel<T extends Entity>
extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelcage"), "main");
    public final ModelPart bone2;

    public CageModel(ModelPart root) {
        this.bone2 = root.getChild("bone2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition bone2 = partdefinition.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(80, 37).addBox(-2.0f, -15.0f, -5.0f, 0.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)).texOffs(80, 42).mirror().addBox(-2.0f, -15.0f, 24.0f, 0.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(80, 47).addBox(-19.0f, -15.0f, 12.0f, 5.0f, 5.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(80, 47).mirror().addBox(10.0f, -15.0f, 12.0f, 5.0f, 5.0f, 0.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(0, 62).addBox(-14.0f, 32.0f, 0.0f, 24.0f, 5.0f, 24.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-12.0f, -10.0f, 2.0f, 20.0f, 42.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(80, 0).addBox(-14.0f, -15.0f, 0.0f, 24.0f, 5.0f, 24.0f, new CubeDeformation(0.0f)), PartPose.offset((float)2.0f, (float)-13.0f, (float)-12.0f));
        bone2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(80, 29).addBox(-5.0f, -4.0f, 0.0f, 10.0f, 8.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)-2.0f, (float)-19.0f, (float)12.0f, (float)0.0f, (float)-0.7854f, (float)0.0f));
        bone2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(80, 29).addBox(-5.0f, -4.0f, 0.0f, 10.0f, 8.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)-2.0f, (float)-19.0f, (float)12.0f, (float)0.0f, (float)0.7854f, (float)0.0f));
        return LayerDefinition.create((MeshDefinition)meshdefinition, (int)256, (int)256);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
        this.bone2.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
}

