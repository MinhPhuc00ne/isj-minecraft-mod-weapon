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
 *  net.minecraft.util.Mth
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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.BossesRise;

public class Modelleggings<T extends Entity>
extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelleggings"), "main");
    public final ModelPart root;
    public final ModelPart waist;
    public final ModelPart body;
    public final ModelPart head;
    public final ModelPart mask;
    public final ModelPart leftArm;
    public final ModelPart arm;
    public final ModelPart hand;
    public final ModelPart rightArm;
    public final ModelPart arm2;
    public final ModelPart hand2;
    public final ModelPart legs;
    public final ModelPart leftLeg;
    public final ModelPart leftboots;
    public final ModelPart rightLeg;
    public final ModelPart rightboots;

    public Modelleggings(ModelPart root) {
        this.root = root.getChild("root");
        this.waist = this.root.getChild("waist");
        this.body = this.waist.getChild("body");
        this.head = this.body.getChild("head");
        this.mask = this.head.getChild("mask");
        this.leftArm = this.body.getChild("leftArm");
        this.arm = this.leftArm.getChild("arm");
        this.hand = this.leftArm.getChild("hand");
        this.rightArm = this.body.getChild("rightArm");
        this.arm2 = this.rightArm.getChild("arm2");
        this.hand2 = this.rightArm.getChild("hand2");
        this.legs = this.waist.getChild("legs");
        this.leftLeg = this.legs.getChild("leftLeg");
        this.leftboots = this.leftLeg.getChild("leftboots");
        this.rightLeg = this.legs.getChild("rightLeg");
        this.rightboots = this.rightLeg.getChild("rightboots");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)24.0f, (float)0.0f));
        PartDefinition waist = root.addOrReplaceChild("waist", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)-12.24f, (float)0.0f));
        PartDefinition body = waist.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 67).addBox(-4.0f, 0.48f, -2.0f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.5f)), PartPose.offset((float)0.0f, (float)-12.24f, (float)0.0f));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)0.0f, (float)0.0f));
        PartDefinition mask = head.addOrReplaceChild("mask", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)24.48f, (float)0.0f));
        PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create(), PartPose.offset((float)5.1f, (float)2.04f, (float)0.0f));
        PartDefinition arm = leftArm.addOrReplaceChild("arm", CubeListBuilder.create(), PartPose.offset((float)-5.1f, (float)22.44f, (float)5.1f));
        PartDefinition hand = leftArm.addOrReplaceChild("hand", CubeListBuilder.create(), PartPose.offset((float)-5.1f, (float)22.44f, (float)5.1f));
        PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create(), PartPose.offset((float)-5.1f, (float)2.04f, (float)0.0f));
        PartDefinition arm2 = rightArm.addOrReplaceChild("arm2", CubeListBuilder.create(), PartPose.offset((float)5.1f, (float)22.44f, (float)0.0f));
        PartDefinition hand2 = rightArm.addOrReplaceChild("hand2", CubeListBuilder.create(), PartPose.offset((float)5.1f, (float)22.44f, (float)0.0f));
        PartDefinition legs = waist.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset((float)1.938f, (float)0.0f, (float)0.0f));
        PartDefinition leftLeg = legs.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(0, 46).addBox(-1.938f, 0.24f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.5f)), PartPose.offset((float)0.0f, (float)0.0f, (float)0.0f));
        PartDefinition leftboots = leftLeg.addOrReplaceChild("leftboots", CubeListBuilder.create(), PartPose.offset((float)-3.876f, (float)0.0f, (float)0.0f));
        PartDefinition rightLeg = legs.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(0, 46).mirror().addBox(-2.062f, 0.24f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.5f)).mirror(false), PartPose.offset((float)-3.876f, (float)0.0f, (float)0.0f));
        PartDefinition rightboots = rightLeg.addOrReplaceChild("rightboots", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)0.0f, (float)0.0f));
        return LayerDefinition.create((MeshDefinition)meshdefinition, (int)128, (int)128);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
        this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.leftLeg.xRot = Mth.cos((float)(limbSwing * 1.0f)) * -1.0f * limbSwingAmount;
        this.rightLeg.xRot = Mth.cos((float)(limbSwing * 1.0f)) * 1.0f * limbSwingAmount;
    }
}

