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
public class BigSkellyCageModel<T extends CageEntity>
extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelbigskellycage"), "main");
    public final ModelPart main;
    public final ModelPart skeleton2;
    public final ModelPart left_leg2;
    public final ModelPart right_leg2;
    public final ModelPart torso2;
    public final ModelPart head3;
    public final ModelPart right_arm2;
    public final ModelPart left_arm2;
    public final ModelPart skeleton4;
    public final ModelPart left_leg4;
    public final ModelPart right_leg4;
    public final ModelPart torso4;
    public final ModelPart head4;
    public final ModelPart right_arm4;
    public final ModelPart left_arm4;
    public final ModelPart head5;

    public BigSkellyCageModel(ModelPart root) {
        this.main = root.getChild("main");
        this.skeleton2 = root.getChild("skeleton2");
        this.left_leg2 = this.skeleton2.getChild("left_leg2");
        this.right_leg2 = this.skeleton2.getChild("right_leg2");
        this.torso2 = this.skeleton2.getChild("torso2");
        this.head3 = this.torso2.getChild("head3");
        this.right_arm2 = this.torso2.getChild("right_arm2");
        this.left_arm2 = this.torso2.getChild("left_arm2");
        this.skeleton4 = root.getChild("skeleton4");
        this.left_leg4 = this.skeleton4.getChild("left_leg4");
        this.right_leg4 = this.skeleton4.getChild("right_leg4");
        this.torso4 = this.skeleton4.getChild("torso4");
        this.head4 = this.torso4.getChild("head4");
        this.right_arm4 = this.torso4.getChild("right_arm4");
        this.left_arm4 = this.torso4.getChild("left_arm4");
        this.head5 = root.getChild("head5");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();
        PartDefinition main = root.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 126).addBox(-22.0f, -7.0f, -22.0f, 44.0f, 7.0f, 44.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-19.0f, -95.0f, -19.0f, 38.0f, 88.0f, 38.0f, new CubeDeformation(0.0f)).texOffs(152, 0).addBox(-22.0f, -102.0f, -22.0f, 44.0f, 7.0f, 44.0f, new CubeDeformation(0.0f)).texOffs(176, 172).addBox(22.0f, -102.0f, 0.0f, 7.0f, 7.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(176, 172).mirror().addBox(-29.0f, -102.0f, 0.0f, 7.0f, 7.0f, 0.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(152, 88).addBox(-7.0f, -113.0f, -2.0f, 14.0f, 11.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(152, 114).addBox(-4.0f, -110.0f, -2.0f, 8.0f, 8.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)24.0f, (float)0.0f));
        main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(176, 172).mirror().addBox(-29.0f, -3.5f, 0.0f, 7.0f, 7.0f, 0.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(176, 172).addBox(22.0f, -3.5f, 0.0f, 7.0f, 7.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.0f, (float)-98.5f, (float)0.0f, (float)0.0f, (float)-1.5708f, (float)0.0f));
        PartDefinition skeleton2 = root.addOrReplaceChild("skeleton2", CubeListBuilder.create(), PartPose.offsetAndRotation((float)1.8304f, (float)12.5034f, (float)0.5442f, (float)-1.6581f, (float)0.2618f, (float)0.0f));
        skeleton2.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(176, 139).addBox(-2.1229f, 0.3299f, -0.8955f, 3.0f, 22.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)2.534f, (float)7.4252f, (float)-0.7032f, (float)0.1981f, (float)0.2212f, (float)-0.1334f));
        skeleton2.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(176, 139).addBox(-2.123f, 0.3299f, -0.8955f, 3.0f, 22.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)-2.7192f, (float)7.1913f, (float)0.3024f, (float)0.0893f, (float)-0.3035f, (float)0.0416f));
        PartDefinition torso2 = skeleton2.addOrReplaceChild("torso2", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)7.15f, (float)1.7778f));
        torso2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(152, 51).addBox(-6.6266f, -10.1817f, -3.5196f, 12.0f, 12.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(152, 103).addBox(-5.6266f, 1.8183f, -1.5196f, 10.0f, 6.0f, 5.0f, new CubeDeformation(0.0f)).texOffs(176, 114).addBox(-2.6266f, -14.1817f, 0.4803f, 4.0f, 22.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.0036f, (float)-7.4717f, (float)-1.8758f, (float)0.0f, (float)-0.2618f, (float)0.0f));
        torso2.addOrReplaceChild("head3", CubeListBuilder.create().texOffs(152, 70).addBox(-5.623f, -8.4201f, -6.6455f, 10.0f, 9.0f, 9.0f, new CubeDeformation(0.0f)).texOffs(176, 164).addBox(-3.623f, 0.5799f, -6.6455f, 6.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)-13.9491f, (float)-14.4933f, (float)-0.7854f, (float)1.1645f, (float)0.1734f, (float)0.1955f));
        torso2.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(176, 139).mirror().addBox(-2.0778f, -2.6766f, -0.9109f, 3.0f, 22.0f, 3.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)-7.5452f, (float)-14.9768f, (float)-0.4846f, (float)0.0903f, (float)0.2608f, (float)0.4597f));
        torso2.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(176, 139).addBox(-2.0f, -3.0f, -0.6f, 3.0f, 22.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)7.377f, (float)-14.6534f, (float)-0.7955f, (float)-0.1088f, (float)-0.057f, (float)-0.8283f));
        PartDefinition skeleton4 = root.addOrReplaceChild("skeleton4", CubeListBuilder.create(), PartPose.offsetAndRotation((float)-2.5694f, (float)2.5034f, (float)10.0601f, (float)0.0f, (float)-0.7854f, (float)0.0f));
        skeleton4.addOrReplaceChild("left_leg4", CubeListBuilder.create().texOffs(176, 139).addBox(-2.123f, 0.3299f, -0.8955f, 3.0f, 22.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)3.3034f, (float)4.279f, (float)1.3028f, (float)-1.5213f, (float)-0.2947f, (float)0.2739f));
        skeleton4.addOrReplaceChild("right_leg4", CubeListBuilder.create().texOffs(176, 139).addBox(-2.123f, 0.33f, -0.8955f, 3.0f, 22.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)-4.8161f, (float)-6.3007f, (float)-4.3138f, (float)-0.9035f, (float)0.7702f, (float)0.0463f));
        PartDefinition torso4 = skeleton4.addOrReplaceChild("torso4", CubeListBuilder.create().texOffs(176, 114).addBox(-2.623f, -21.6534f, -1.3955f, 4.0f, 22.0f, 3.0f, new CubeDeformation(0.0f)).texOffs(152, 103).addBox(-5.623f, -5.6534f, -3.3955f, 10.0f, 6.0f, 5.0f, new CubeDeformation(0.0f)).texOffs(152, 51).addBox(-6.623f, -17.6534f, -5.3955f, 12.0f, 12.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.0f, (float)7.15f, (float)1.7778f, (float)-0.2182f, (float)0.0f, (float)0.0f));
        torso4.addOrReplaceChild("head4", CubeListBuilder.create().texOffs(152, 70).addBox(-5.623f, -8.4201f, -6.6455f, 10.0f, 9.0f, 9.0f, new CubeDeformation(0.0f)).texOffs(176, 164).addBox(-3.623f, 0.5799f, -6.6455f, 6.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.0f, (float)-22.2333f, (float)-0.75f, (float)0.4038f, (float)0.3456f, (float)0.426f));
        torso4.addOrReplaceChild("right_arm4", CubeListBuilder.create().texOffs(176, 139).mirror().addBox(-2.0778f, -2.6766f, -0.9109f, 3.0f, 22.0f, 3.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)-7.9781f, (float)-13.0242f, (float)-0.4846f, (float)0.1309f, (float)0.2618f, (float)0.2182f));
        torso4.addOrReplaceChild("left_arm4", CubeListBuilder.create().texOffs(176, 139).addBox(-2.0f, -3.0f, -0.6f, 3.0f, 22.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)7.6324f, (float)-12.7211f, (float)-1.41f, (float)-0.3052f, (float)-0.0131f, (float)-0.1725f));
        root.addOrReplaceChild("head5", CubeListBuilder.create().texOffs(152, 70).addBox(-5.6229f, -8.4201f, -6.6455f, 10.0f, 9.0f, 9.0f, new CubeDeformation(0.0f)).texOffs(176, 164).addBox(-3.6229f, 0.5799f, -6.6455f, 6.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)10.3934f, (float)15.7353f, (float)-9.0706f, (float)-0.678f, (float)-0.4378f, (float)-0.0222f));
        return LayerDefinition.create((MeshDefinition)meshdefinition, (int)512, (int)512);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
        this.main.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
        this.skeleton2.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
        this.skeleton4.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
        this.head5.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.main.yRot = ((CageEntity)((Object)entity)).getFlip() ? 1.5707964f : 0.0f;
    }
}

