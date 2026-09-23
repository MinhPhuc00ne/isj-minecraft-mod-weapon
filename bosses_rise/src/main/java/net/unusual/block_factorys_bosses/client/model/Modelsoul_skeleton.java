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
import net.unusual.block_factorys_bosses.client.animation.soul_skeletonAnimation;
import net.unusual.block_factorys_bosses.entity.monster.SoulSkeletonEntity;

public class Modelsoul_skeleton<T extends SoulSkeletonEntity>
extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelsoul_skeleton"), "main");
    public final ModelPart root;
    public final ModelPart all;
    public final ModelPart right_leg;
    public final ModelPart left_leg;
    public final ModelPart torso;
    public final ModelPart skirt;
    public final ModelPart head;
    public final ModelPart left_arm;
    public final ModelPart right_arm;
    public final ModelPart sword;

    public Modelsoul_skeleton(ModelPart root) {
        this.root = root;
        this.all = root.getChild("all");
        this.right_leg = this.all.getChild("right_leg");
        this.left_leg = this.all.getChild("left_leg");
        this.torso = this.all.getChild("torso");
        this.skirt = this.torso.getChild("skirt");
        this.head = this.torso.getChild("head");
        this.left_arm = this.torso.getChild("left_arm");
        this.right_arm = this.torso.getChild("right_arm");
        this.sword = this.right_arm.getChild("sword");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)24.0f, (float)0.0f));
        PartDefinition right_leg = all.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(24, 43).addBox(-1.0f, -1.0f, -1.0f, 2.0f, 14.7f, 2.0f, new CubeDeformation(0.0f)), PartPose.offset((float)-2.0f, (float)-13.7f, (float)0.0f));
        PartDefinition left_leg = all.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(32, 43).addBox(-1.0f, -1.0f, -1.0f, 2.0f, 14.7f, 2.0f, new CubeDeformation(0.0f)), PartPose.offset((float)2.0f, (float)-13.7f, (float)0.0f));
        PartDefinition torso = all.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0f, -12.8f, -1.6f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(32, 20).addBox(-4.0f, -12.8f, -1.6f, 8.0f, 7.0f, 4.0f, new CubeDeformation(0.25f)), PartPose.offset((float)0.0f, (float)-13.9f, (float)-0.4f));
        PartDefinition torso_r1 = torso.addOrReplaceChild("torso_r1", CubeListBuilder.create().texOffs(40, 46).addBox(-2.0f, -2.5f, -0.5f, 4.0f, 5.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.0f, (float)-2.3f, (float)-1.6f, (float)-0.0865f, (float)0.0114f, (float)0.1304f));
        PartDefinition torso_r2 = torso.addOrReplaceChild("torso_r2", CubeListBuilder.create().texOffs(24, 32).addBox(-4.0f, -1.0f, -2.0f, 8.0f, 2.0f, 4.0f, new CubeDeformation(0.15f)), PartPose.offsetAndRotation((float)0.0f, (float)-1.8f, (float)0.4f, (float)0.0f, (float)0.0f, (float)0.0873f));
        PartDefinition skirt = torso.addOrReplaceChild("skirt", CubeListBuilder.create().texOffs(32, 4).addBox(-4.0f, -3.0f, -2.0f, 8.0f, 8.0f, 4.0f, new CubeDeformation(0.01f)), PartPose.offset((float)0.0f, (float)0.2f, (float)0.4f));
        PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -9.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(-0.25f)).texOffs(0, 16).addBox(-4.0f, -9.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)-11.8f, (float)0.4f));
        PartDefinition left_arm = torso.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 16).addBox(-1.9667f, -1.3f, -1.0f, 14.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(0, 48).addBox(5.7333f, -1.3f, -1.0f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.25f)).texOffs(24, 38).addBox(3.7333f, -2.3f, -2.0f, 6.0f, 1.0f, 4.0f, new CubeDeformation(0.15f)), PartPose.offsetAndRotation((float)4.7667f, (float)-11.0f, (float)0.4f, (float)0.0f, (float)0.0f, (float)1.5708f));
        PartDefinition right_arm = torso.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(32, 0).addBox(-11.9f, -1.3f, -1.0f, 14.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)-4.9f, (float)-10.8f, (float)0.4f, (float)0.0f, (float)0.0f, (float)-1.5708f));
        PartDefinition sword = right_arm.addOrReplaceChild("sword", CubeListBuilder.create().texOffs(44, 38).addBox(-1.0f, -0.5f, -1.7778f, 2.0f, 1.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(40, 43).addBox(-3.0f, -0.5f, -3.7778f, 6.0f, 1.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(45, 32).addBox(-0.5f, 0.0f, -17.7778f, 1.0f, 0.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(16, 48).addBox(-1.5f, 0.0f, -15.7778f, 3.0f, 0.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(40, 46).addBox(-2.5f, 0.0f, -13.7778f, 5.0f, 0.0f, 10.0f, new CubeDeformation(0.0f)), PartPose.offset((float)-9.4f, (float)-0.3f, (float)0.7778f));
        PartDefinition sword_r1 = sword.addOrReplaceChild("sword_r1", CubeListBuilder.create().texOffs(8, 48).addBox(-1.0f, -1.5f, -1.0f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.0f, (float)0.5f, (float)2.2222f, (float)0.0f, (float)-0.7854f, (float)0.0f));
        return LayerDefinition.create((MeshDefinition)meshdefinition, (int)64, (int)64);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
        this.all.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw / 57.295776f;
        this.head.xRot = headPitch / 57.295776f;
        this.animate(((SoulSkeletonEntity)((Object)entity)).attackAnimationState, soul_skeletonAnimation.ATTACK, ageInTicks);
        this.animate(((SoulSkeletonEntity)((Object)entity)).idleAnimationState, soul_skeletonAnimation.IDLE, ageInTicks);
        this.animateWalk(soul_skeletonAnimation.WALK, limbSwing, limbSwingAmount, 2.0f, 3.0f);
    }

    public ModelPart root() {
        return this.root;
    }

    private record ModelParts(ModelPart root) {
    }
}

