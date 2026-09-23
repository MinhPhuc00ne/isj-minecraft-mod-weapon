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
 *  net.minecraft.world.entity.AnimationState
 *  net.minecraft.world.entity.Entity
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
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.animation.swordAnimation;
import net.unusual.block_factorys_bosses.item.WarriorSwordItem;

public class Modelwarrior_sword<T extends Entity>
extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelwarrior_sword"), "main");
    public final ModelPart root;
    public final ModelPart bone;
    public final ModelPart main;

    public Modelwarrior_sword(ModelPart root) {
        this.root = root;
        this.bone = root.getChild("bone");
        this.main = this.bone.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offsetAndRotation((float)0.0f, (float)24.0f, (float)0.0f, (float)0.0f, (float)-1.5708f, (float)0.0f));
        PartDefinition main = bone.addOrReplaceChild("main", CubeListBuilder.create().texOffs(6, 0).addBox(-5.0f, -7.0f, 0.0f, 10.0f, 4.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(6, 4).addBox(-1.0f, -5.0f, -1.0f, 2.0f, 10.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(14, 4).addBox(-3.0f, -7.0f, -1.0f, 6.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-1.5f, -36.0f, 0.0f, 3.0f, 29.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)-5.0f, (float)0.0f));
        return LayerDefinition.create((MeshDefinition)meshdefinition, (int)32, (int)32);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
        this.bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
    }

    public void setupItemAnim(String stack, int firstPersonContext, float ageInTicks) {
        if (firstPersonContext > 0) {
            AnimationState idleState = WarriorSwordItem.IDLE_STATES.computeIfAbsent(stack, s -> new AnimationState());
            this.animate(idleState, swordAnimation.IDLE, ageInTicks, 1.0f);
            AnimationState swingState = WarriorSwordItem.SWING_STATES.computeIfAbsent(stack, s -> new AnimationState());
            if (firstPersonContext == 1) {
                this.animate(swingState, swordAnimation.ATTACK_1, ageInTicks, 1.0f);
            }
            if (firstPersonContext == 2) {
                this.animate(swingState, swordAnimation.ATTACK_LEFT_1, ageInTicks, 1.0f);
            }
        }
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    public ModelPart root() {
        return this.root;
    }
}

