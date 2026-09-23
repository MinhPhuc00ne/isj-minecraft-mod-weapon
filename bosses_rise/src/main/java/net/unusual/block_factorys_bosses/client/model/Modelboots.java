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

public class Modelboots<T extends Entity>
extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelboots"), "main");
    public final ModelPart root;
    public final ModelPart leftLeg;
    public final ModelPart leftboots;
    public final ModelPart rightLeg;
    public final ModelPart rightboots;

    public Modelboots(ModelPart root) {
        this.root = root.getChild("root");
        this.leftLeg = this.root.getChild("leftLeg");
        this.leftboots = this.leftLeg.getChild("leftboots");
        this.rightLeg = this.root.getChild("rightLeg");
        this.rightboots = this.rightLeg.getChild("rightboots");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)24.0f, (float)0.0f));
        PartDefinition leftLeg = root.addOrReplaceChild("leftLeg", CubeListBuilder.create(), PartPose.offset((float)2.938f, (float)-12.24f, (float)0.0f));
        PartDefinition leftboots = leftLeg.addOrReplaceChild("leftboots", CubeListBuilder.create().texOffs(56, 50).addBox(0.938f, 5.24f, -2.0f, 4.0f, 7.0f, 4.0f, new CubeDeformation(0.5f)), PartPose.offset((float)-3.876f, (float)0.0f, (float)0.0f));
        PartDefinition rightLeg = root.addOrReplaceChild("rightLeg", CubeListBuilder.create(), PartPose.offset((float)-1.938f, (float)-12.24f, (float)0.0f));
        PartDefinition rightboots = rightLeg.addOrReplaceChild("rightboots", CubeListBuilder.create().texOffs(56, 50).mirror().addBox(-2.062f, 5.24f, -2.0f, 4.0f, 7.0f, 4.0f, new CubeDeformation(0.5f)).mirror(false), PartPose.offset((float)0.0f, (float)0.0f, (float)0.0f));
        return LayerDefinition.create((MeshDefinition)meshdefinition, (int)128, (int)128);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
        this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.rightLeg.xRot = Mth.cos((float)(limbSwing * 1.0f)) * 1.0f * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos((float)(limbSwing * 1.0f)) * -1.0f * limbSwingAmount;
    }
}

