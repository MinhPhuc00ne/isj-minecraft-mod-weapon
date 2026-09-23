/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.model.HierarchicalModel
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
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.animation.soul_knight_wither_skeletonAnimation;
import net.unusual.block_factorys_bosses.entity.monster.SoulKnightWitherSkeletonEntity;

public class Modelsoul_knight_wither_skeleton<T extends SoulKnightWitherSkeletonEntity>
extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelsoul_knight_wither_skeleton"), "main");
    public final ModelPart root;
    public final ModelPart all;
    public final ModelPart right_leg;
    public final ModelPart left_leg;
    public final ModelPart torso;
    public final ModelPart front_skirt;
    public final ModelPart back_skirt;
    public final ModelPart head;
    public final ModelPart left_arm;
    public final ModelPart right_arm;
    public final ModelPart sword;

    public Modelsoul_knight_wither_skeleton(ModelPart root) {
        this.root = root;
        this.all = root.getChild("all");
        this.right_leg = this.all.getChild("right_leg");
        this.left_leg = this.all.getChild("left_leg");
        this.torso = this.all.getChild("torso");
        this.front_skirt = this.torso.getChild("front_skirt");
        this.back_skirt = this.torso.getChild("back_skirt");
        this.head = this.torso.getChild("head");
        this.left_arm = this.torso.getChild("left_arm");
        this.right_arm = this.torso.getChild("right_arm");
        this.sword = this.right_arm.getChild("sword");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)24.0f, (float)0.0f));
        PartDefinition right_leg = all.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 54).addBox(-1.8f, 0.0f, -1.5f, 3.0f, 17.7f, 3.0f, new CubeDeformation(0.0f)), PartPose.offset((float)-2.0f, (float)-17.9f, (float)0.0f));
        PartDefinition right_leg_r1 = right_leg.addOrReplaceChild("right_leg_r1", CubeListBuilder.create().texOffs(48, 58).addBox(-1.5f, -3.35f, -1.5f, 3.0f, 6.7f, 3.0f, new CubeDeformation(0.25f)), PartPose.offsetAndRotation((float)-0.3f, (float)14.35f, (float)0.0f, (float)0.0f, (float)0.0698f, (float)0.0f));
        PartDefinition left_leg = all.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(52, 38).addBox(-1.5f, 0.0f, -1.5f, 3.0f, 17.7f, 3.0f, new CubeDeformation(0.0f)), PartPose.offset((float)2.3f, (float)-17.9f, (float)0.0f));
        PartDefinition left_leg_r1 = left_leg.addOrReplaceChild("left_leg_r1", CubeListBuilder.create().texOffs(36, 54).addBox(-1.5f, -3.35f, -1.5f, 3.0f, 6.7f, 3.0f, new CubeDeformation(0.25f)), PartPose.offsetAndRotation((float)0.0f, (float)14.35f, (float)0.0f, (float)0.0f, (float)-0.0698f, (float)0.0f));
        PartDefinition torso = all.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 38).addBox(-4.5f, -12.8f, -1.6f, 9.0f, 12.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(26, 38).addBox(-4.5f, -12.8f, -1.6f, 9.0f, 12.0f, 4.0f, new CubeDeformation(0.25f)), PartPose.offset((float)0.0f, (float)-17.1f, (float)-0.4f));
        PartDefinition front_skirt = torso.addOrReplaceChild("front_skirt", CubeListBuilder.create().texOffs(64, 36).addBox(-2.5f, 0.0f, 0.0f, 5.0f, 6.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)-0.6f, (float)-1.8f));
        PartDefinition back_skirt = torso.addOrReplaceChild("back_skirt", CubeListBuilder.create().texOffs(64, 29).addBox(-2.5f, 0.0f, 0.0f, 5.0f, 7.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)-0.6f, (float)2.6f));
        PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 21).addBox(-4.0f, -10.0f, -4.0f, 8.0f, 9.0f, 8.0f, new CubeDeformation(-0.25f)).texOffs(32, 21).addBox(-4.0f, -10.0f, -4.0f, 8.0f, 9.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(36, 0).addBox(-4.0f, -10.1f, -4.0f, 8.0f, 5.0f, 8.0f, new CubeDeformation(0.5f)), PartPose.offset((float)0.0f, (float)-11.8f, (float)0.4f));
        PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(64, 20).addBox(-0.1f, 0.2f, -2.0f, 0.0f, 5.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)-4.2f, (float)-6.5f, (float)0.0f, (float)0.0f, (float)0.0f, (float)0.2618f));
        PartDefinition head_r2 = head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(36, 63).addBox(0.1f, 0.2f, -2.0f, 0.0f, 5.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)4.2f, (float)-6.5f, (float)0.0f, (float)0.0f, (float)0.0f, (float)-0.2618f));
        PartDefinition left_arm = torso.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(12, 54).addBox(-0.4f, -1.4f, -1.5f, 3.0f, 16.0f, 3.0f, new CubeDeformation(0.0f)).texOffs(60, 58).addBox(2.8f, 2.8f, -2.0f, 0.0f, 5.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offset((float)4.4f, (float)-11.6f, (float)0.4f));
        PartDefinition left_arm_r1 = left_arm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(36, 13).addBox(-2.5f, -1.5f, -2.0f, 5.0f, 3.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)2.1f, (float)0.1f, (float)0.0f, (float)0.0f, (float)0.0f, (float)0.7854f));
        PartDefinition right_arm = torso.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(24, 54).addBox(-2.5f, -1.0f, -1.5f, 3.0f, 16.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offset((float)-4.5f, (float)-12.0f, (float)0.4f));
        PartDefinition sword = right_arm.addOrReplaceChild("sword", CubeListBuilder.create().texOffs(54, 13).addBox(-0.5f, -1.0f, -2.475f, 1.0f, 2.0f, 5.0f, new CubeDeformation(-0.01f)).texOffs(64, 42).addBox(-0.5f, -3.0f, -3.975f, 1.0f, 6.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(0.0f, -1.5f, -21.975f, 0.0f, 3.0f, 18.0f, new CubeDeformation(0.0f)), PartPose.offset((float)-0.5f, (float)12.7f, (float)0.475f));
        PartDefinition sword_r1 = sword.addOrReplaceChild("sword_r1", CubeListBuilder.create().texOffs(64, 50).addBox(-1.0f, -1.0f, -1.0f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.0f, (float)0.0f, (float)2.925f, (float)0.7854f, (float)0.0f, (float)0.0f));
        return LayerDefinition.create((MeshDefinition)meshdefinition, (int)128, (int)128);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
        this.all.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw / 57.295776f;
        this.head.xRot = headPitch / 57.295776f;
        this.animate(((SoulKnightWitherSkeletonEntity)((Object)entity)).attackAnimationState, soul_knight_wither_skeletonAnimation.ATTACK, ageInTicks);
        this.animate(((SoulKnightWitherSkeletonEntity)((Object)entity)).idleAnimationState, soul_knight_wither_skeletonAnimation.IDLE, ageInTicks);
        this.animateWalk(soul_knight_wither_skeletonAnimation.WALK, limbSwing, limbSwingAmount, 2.0f, 3.0f);
    }

    public ModelPart root() {
        return this.root;
    }

    private record ModelParts(ModelPart root) {
    }
}

