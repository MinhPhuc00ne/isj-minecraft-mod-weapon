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

public class ModelCrate6<T extends Entity>
extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("crate_6"), "main");
    private final ModelPart bone;

    public ModelCrate6(ModelPart root) {
        this.bone = root.getChild("bone");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset((float)8.0f, (float)24.0f, (float)-8.0f));
        PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0f, -8.0f, -8.0f, 16.0f, 16.0f, 16.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)-16.0f, (float)-8.0f, (float)-4.0f, (float)0.0f, (float)-0.2618f, (float)0.0f));
        PartDefinition cube_r2 = bone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0f, -16.0f, -1.0f, 16.0f, 16.0f, 16.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)-0.8f, (float)-16.0f, (float)-8.0f, (float)0.0f, (float)-0.1222f, (float)0.0f));
        PartDefinition cube_r3 = bone.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0f, -16.0f, -1.0f, 16.0f, 16.0f, 16.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)-0.8f, (float)-16.0f, (float)10.0f, (float)0.0f, (float)0.0087f, (float)0.0f));
        PartDefinition cube_r4 = bone.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0f, -16.0f, -1.0f, 16.0f, 16.0f, 16.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)10.2f, (float)0.0f, (float)-6.0f, (float)0.0f, (float)0.0087f, (float)0.0f));
        PartDefinition cube_r5 = bone.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0f, -16.0f, -1.0f, 16.0f, 16.0f, 16.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)-14.7f, (float)0.0f, (float)7.0f, (float)0.0f, (float)0.1396f, (float)0.0f));
        PartDefinition cube_r6 = bone.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0f, -16.0f, -1.0f, 16.0f, 16.0f, 16.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)6.3f, (float)0.0f, (float)14.0f, (float)0.0f, (float)-0.1658f, (float)0.0f));
        return LayerDefinition.create((MeshDefinition)meshdefinition, (int)64, (int)64);
    }

    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgba) {
        this.bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgba);
    }
}

