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
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.decoration.CageEntity;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BigCageModel<T extends CageEntity>
extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelunderworldcage"), "main");
    public final ModelPart main;

    public BigCageModel(ModelPart root) {
        this.main = root.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();
        PartDefinition main = root.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 126).addBox(-22.0f, -7.0f, -22.0f, 44.0f, 7.0f, 44.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-19.0f, -95.0f, -19.0f, 38.0f, 88.0f, 38.0f, new CubeDeformation(0.0f)).texOffs(152, 0).addBox(-22.0f, -102.0f, -22.0f, 44.0f, 7.0f, 44.0f, new CubeDeformation(0.0f)).texOffs(152, 78).addBox(22.0f, -102.0f, 0.0f, 7.0f, 7.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(152, 78).mirror().addBox(-29.0f, -102.0f, 0.0f, 7.0f, 7.0f, 0.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(152, 51).addBox(-7.0f, -113.0f, -2.0f, 14.0f, 11.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(152, 66).addBox(-4.0f, -110.0f, -2.0f, 8.0f, 8.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)24.0f, (float)0.0f));
        main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(152, 78).mirror().addBox(-29.0f, -3.5f, 0.0f, 7.0f, 7.0f, 0.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(152, 78).mirror().addBox(-29.0f, -3.5f, 0.0f, 7.0f, 7.0f, 0.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(152, 78).addBox(22.0f, -3.5f, 0.0f, 7.0f, 7.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.0f, (float)-98.5f, (float)0.0f, (float)0.0f, (float)-1.5708f, (float)0.0f));
        return LayerDefinition.create((MeshDefinition)meshdefinition, (int)512, (int)512);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
        this.main.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.main.yRot = ((CageEntity)((Object)entity)).getFlip() ? 1.5707964f : 0.0f;
    }
}

