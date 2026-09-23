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
public class SkellyCageModel<T extends Entity>
extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelskellycage"), "main");
    public final ModelPart bone;
    public final ModelPart skeleton80;
    public final ModelPart left_leg11;
    public final ModelPart right_leg10;
    public final ModelPart torso80;
    public final ModelPart head2;
    public final ModelPart right_arm38;
    public final ModelPart left_arm33;

    public SkellyCageModel(ModelPart root) {
        this.bone = root.getChild("bone");
        this.skeleton80 = root.getChild("skeleton80");
        this.left_leg11 = this.skeleton80.getChild("left_leg11");
        this.right_leg10 = this.skeleton80.getChild("right_leg10");
        this.torso80 = this.skeleton80.getChild("torso80");
        this.head2 = this.torso80.getChild("head2");
        this.right_arm38 = this.torso80.getChild("right_arm38");
        this.left_arm33 = this.torso80.getChild("left_arm33");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(96, 59).addBox(-2.0f, -15.0f, -5.0f, 0.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)).texOffs(96, 64).addBox(-2.0f, -15.0f, 24.0f, 0.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)).texOffs(96, 69).addBox(-19.0f, -15.0f, 12.0f, 5.0f, 5.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(96, 64).addBox(10.0f, -15.0f, 12.0f, 5.0f, 5.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 62).addBox(-14.0f, 32.0f, 0.0f, 24.0f, 5.0f, 24.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-12.0f, -10.0f, 2.0f, 20.0f, 42.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(80, 0).addBox(-14.0f, -15.0f, 0.0f, 24.0f, 5.0f, 24.0f, new CubeDeformation(0.0f)), PartPose.offset((float)2.0f, (float)-13.0f, (float)-12.0f));
        bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(64, 91).addBox(-5.0f, -4.0f, 0.0f, 10.0f, 8.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)-2.0f, (float)-19.0f, (float)12.0f, (float)0.0f, (float)-0.7854f, (float)0.0f));
        bone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(64, 91).addBox(-5.0f, -4.0f, 0.0f, 10.0f, 8.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)-2.0f, (float)-19.0f, (float)12.0f, (float)0.0f, (float)0.7854f, (float)0.0f));
        PartDefinition skeleton80 = partdefinition.addOrReplaceChild("skeleton80", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)43.0f, (float)-1.25f));
        skeleton80.addOrReplaceChild("left_leg11", CubeListBuilder.create().texOffs(52, 91).addBox(-2.1286f, 0.0f, -1.3318f, 3.0f, 22.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)5.3728f, (float)-30.7412f, (float)-5.0388f, (float)-1.0908f, (float)-0.3054f, (float)0.0f));
        skeleton80.addOrReplaceChild("right_leg10", CubeListBuilder.create().texOffs(52, 91).addBox(-1.1286f, 0.0f, -1.3318f, 3.0f, 22.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)-4.8714f, (float)-37.0f, (float)-5.4182f, (float)-0.7069f, (float)0.3927f, (float)0.0f));
        PartDefinition torso80 = skeleton80.addOrReplaceChild("torso80", CubeListBuilder.create(), PartPose.offsetAndRotation((float)-0.3714f, (float)-25.8618f, (float)-0.3966f, (float)-0.3927f, (float)0.0f, (float)0.0f));
        torso80.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(80, 29).addBox(-6.6322f, -9.7616f, -2.9559f, 12.0f, 12.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(80, 48).addBox(-5.6322f, 2.2384f, -0.9559f, 10.0f, 6.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)1.0036f, (float)-7.4717f, (float)-1.8759f, (float)0.0873f, (float)-0.3491f, (float)0.0f));
        torso80.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(38, 91).addBox(-1.6322f, -13.7616f, 1.0441f, 4.0f, 22.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.0036f, (float)-7.4717f, (float)-1.8759f, (float)0.0873f, (float)-0.3491f, (float)0.0f));
        torso80.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(0, 91).addBox(-5.6286f, -8.75f, -7.0818f, 10.0f, 9.0f, 9.0f, new CubeDeformation(0.0f)).texOffs(84, 91).addBox(-3.6286f, 0.25f, -7.0818f, 6.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.8519f, (float)-21.9502f, (float)-1.2068f, (float)0.3303f, (float)-0.1578f, (float)-0.5493f));
        torso80.addOrReplaceChild("right_arm38", CubeListBuilder.create().texOffs(52, 91).mirror().addBox(-0.5834f, -3.0065f, -2.3472f, 3.0f, 22.0f, 3.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)-8.9925f, (float)-12.4103f, (float)-2.4644f, (float)0.3024f, (float)0.1864f, (float)0.5872f));
        torso80.addOrReplaceChild("left_arm33", CubeListBuilder.create().texOffs(52, 91).addBox(-1.5f, -3.0f, -1.5f, 3.0f, 22.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)9.1214f, (float)-13.5183f, (float)1.3575f, (float)0.4771f, (float)0.0293f, (float)-0.3522f));
        return LayerDefinition.create((MeshDefinition)meshdefinition, (int)256, (int)256);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
        this.bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
        this.skeleton80.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
}

