package com.minhphuc.weapons.client.model;

import com.minhphuc.weapons.entity.tensura.DemonType;
import com.minhphuc.weapons.entity.tensura.PrimordialDemonEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PrimordialDemonModel extends HumanoidModel<PrimordialDemonEntity> {

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("weapons", "primordial_demon"), "main");

    // Noir parts (Diablo)
    public final ModelPart noirBangs;
    public final ModelPart noirTails;
    public final ModelPart wings;
    public final ModelPart leftWing;
    public final ModelPart rightWing;
    public final ModelPart noirClawsRight;
    public final ModelPart noirClawsLeft;

    // Rouge parts (Guy Crimson)
    public final ModelPart rougeMane;
    public final ModelPart rougeCollar;
    public final ModelPart rougeCloak;

    // Blanc parts (Testarossa)
    public final ModelPart blancLongHair;
    public final ModelPart blancRibbon;
    public final ModelPart blancCoat;

    // Jaune parts (Carrera)
    public final ModelPart jauneTwintailLeft;
    public final ModelPart jauneTwintailRight;
    public final ModelPart jauneEpaulets;

    // Violet parts (Ultima)
    public final ModelPart violetBuns;
    public final ModelPart violetSkirt;

    // Bleu parts (Rain)
    public final ModelPart bleuMaidBand;
    public final ModelPart bleuSkirt;

    // Vert parts (Misery)
    public final ModelPart vertHair;
    public final ModelPart vertMaidBand;
    public final ModelPart vertSkirt;

    public PrimordialDemonModel(ModelPart root) {
        super(root);
        ModelPart head = root.getChild("head");
        ModelPart body = root.getChild("body");

        // Noir
        this.noirBangs = head.getChild("noir_bangs");
        this.noirTails = body.getChild("noir_tails");
        this.wings = body.getChild("wings");
        this.leftWing = this.wings.getChild("left_wing");
        this.rightWing = this.wings.getChild("right_wing");
        this.noirClawsRight = this.rightArm.getChild("noir_claws_right");
        this.noirClawsLeft = this.leftArm.getChild("noir_claws_left");

        // Rouge
        this.rougeMane = head.getChild("rouge_mane");
        this.rougeCollar = body.getChild("rouge_collar");
        this.rougeCloak = body.getChild("rouge_cloak");

        // Blanc
        this.blancLongHair = head.getChild("blanc_long_hair");
        this.blancRibbon = head.getChild("blanc_ribbon");
        this.blancCoat = body.getChild("blanc_coat");

        // Jaune
        this.jauneTwintailLeft = head.getChild("jaune_twintail_left");
        this.jauneTwintailRight = head.getChild("jaune_twintail_right");
        this.jauneEpaulets = body.getChild("jaune_epaulets");

        // Violet
        this.violetBuns = head.getChild("violet_buns");
        this.violetSkirt = body.getChild("violet_skirt");

        // Bleu
        this.bleuMaidBand = head.getChild("bleu_maid_band");
        this.bleuSkirt = body.getChild("bleu_skirt");

        // Vert
        this.vertHair = head.getChild("vert_hair");
        this.vertMaidBand = head.getChild("vert_maid_band");
        this.vertSkirt = body.getChild("vert_skirt");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.getChild("head");
        PartDefinition body = partdefinition.getChild("body");

        // --- 1. NOIR (Diablo) ---
        head.addOrReplaceChild("noir_bangs", CubeListBuilder.create()
                .texOffs(40, 8).addBox(-4.0F, -8.0F, -4.2F, 8.0F, 5.0F, 0.25F), PartPose.ZERO);
        body.addOrReplaceChild("noir_tails", CubeListBuilder.create()
                .texOffs(16, 36).addBox(-3.8F, 11.5F, 1.6F, 7.6F, 9.0F, 0.5F, new CubeDeformation(0.1F)),
                PartPose.rotation(0.08F, 0.0F, 0.0F));

        // Noir Bat Wings
        PartDefinition wings = body.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 2.0F));
        wings.addOrReplaceChild("left_wing", CubeListBuilder.create()
                .texOffs(24, 0).addBox(0.0F, -2.0F, 0.0F, 14.0F, 12.0F, 0.01F)
                .texOffs(24, 12).addBox(0.0F, -3.0F, -0.5F, 14.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(1.5F, 0.0F, 0.5F, 0.2F, 0.35F, 0.1F));
        wings.addOrReplaceChild("right_wing", CubeListBuilder.create()
                .texOffs(24, 0).mirror().addBox(-14.0F, -2.0F, 0.0F, 14.0F, 12.0F, 0.01F)
                .texOffs(24, 12).mirror().addBox(-14.0F, -3.0F, -0.5F, 14.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(-1.5F, 0.0F, 0.5F, 0.2F, -0.35F, -0.1F));

        // Noir 5 Móng Vuốt Kim Loại Sắc Nhọn Cong Dài trên 2 bàn tay (Tay Phải & Tay Trái)
        PartDefinition rightArm = partdefinition.getChild("right_arm");
        rightArm.addOrReplaceChild("noir_claws_right", CubeListBuilder.create()
                // Ngón cái
                .texOffs(24, 0).addBox(0.3F, 9.5F, -1.0F, 0.6F, 7.0F, 0.6F)
                .texOffs(24, 6).addBox(0.3F, 16.0F, -2.0F, 0.5F, 5.0F, 0.5F)
                // Ngón trỏ
                .texOffs(24, 0).addBox(-0.5F, 10.0F, -1.2F, 0.6F, 8.0F, 0.6F)
                .texOffs(24, 6).addBox(-0.5F, 17.5F, -2.4F, 0.5F, 6.0F, 0.5F)
                // Ngón giữa (Dài nhất - 16px)
                .texOffs(24, 0).addBox(-1.4F, 10.0F, -1.2F, 0.6F, 9.0F, 0.6F)
                .texOffs(24, 6).addBox(-1.4F, 18.5F, -2.6F, 0.5F, 7.0F, 0.5F)
                // Ngón áp út
                .texOffs(24, 0).addBox(-2.2F, 10.0F, -1.0F, 0.6F, 8.0F, 0.6F)
                .texOffs(24, 6).addBox(-2.2F, 17.5F, -2.2F, 0.5F, 6.0F, 0.5F)
                // Ngón út
                .texOffs(24, 0).addBox(-2.9F, 9.5F, -0.8F, 0.5F, 7.0F, 0.5F)
                .texOffs(24, 6).addBox(-2.9F, 16.0F, -1.8F, 0.5F, 5.0F, 0.5F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15F, 0.0F, 0.0F));

        PartDefinition leftArm = partdefinition.getChild("left_arm");
        leftArm.addOrReplaceChild("noir_claws_left", CubeListBuilder.create()
                // Ngón cái
                .texOffs(24, 0).mirror().addBox(-0.9F, 9.5F, -1.0F, 0.6F, 7.0F, 0.6F)
                .texOffs(24, 6).mirror().addBox(-0.9F, 16.0F, -2.0F, 0.5F, 5.0F, 0.5F)
                // Ngón trỏ
                .texOffs(24, 0).mirror().addBox(-0.1F, 10.0F, -1.2F, 0.6F, 8.0F, 0.6F)
                .texOffs(24, 6).mirror().addBox(-0.1F, 17.5F, -2.4F, 0.5F, 6.0F, 0.5F)
                // Ngón giữa (Dài nhất!)
                .texOffs(24, 0).mirror().addBox(0.8F, 10.0F, -1.2F, 0.6F, 9.0F, 0.6F)
                .texOffs(24, 6).mirror().addBox(0.8F, 18.5F, -2.6F, 0.5F, 7.0F, 0.5F)
                // Ngón áp út
                .texOffs(24, 0).mirror().addBox(1.6F, 10.0F, -1.0F, 0.6F, 8.0F, 0.6F)
                .texOffs(24, 6).mirror().addBox(1.6F, 17.5F, -2.2F, 0.5F, 6.0F, 0.5F)
                // Ngón út
                .texOffs(24, 0).mirror().addBox(2.4F, 9.5F, -0.8F, 0.5F, 7.0F, 0.5F)
                .texOffs(24, 6).mirror().addBox(2.4F, 16.0F, -1.8F, 0.5F, 5.0F, 0.5F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15F, 0.0F, 0.0F));

        // --- 2. ROUGE (Guy Crimson) ---
        head.addOrReplaceChild("rouge_mane", CubeListBuilder.create()
                .texOffs(56, 0).addBox(-4.5F, -8.5F, 3.5F, 9.0F, 12.0F, 2.0F)
                .texOffs(32, 0).addBox(-4.8F, -8.0F, -3.5F, 1.0F, 9.0F, 7.0F)
                .texOffs(32, 0).mirror().addBox(3.8F, -8.0F, -3.5F, 1.0F, 9.0F, 7.0F),
                PartPose.rotation(0.05F, 0.0F, 0.0F));
        body.addOrReplaceChild("rouge_collar", CubeListBuilder.create()
                .texOffs(16, 32).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 3.5F, 5.0F, new CubeDeformation(0.4F)),
                PartPose.ZERO);
        body.addOrReplaceChild("rouge_cloak", CubeListBuilder.create()
                .texOffs(16, 36).addBox(-4.5F, 11.0F, -2.5F, 9.0F, 9.0F, 5.0F, new CubeDeformation(0.2F)),
                PartPose.ZERO);

        // --- 3. BLANC (Testarossa) ---
        head.addOrReplaceChild("blanc_long_hair", CubeListBuilder.create()
                .texOffs(56, 0).addBox(-3.5F, -1.0F, 3.8F, 7.0F, 13.0F, 0.75F),
                PartPose.rotation(0.08F, 0.0F, 0.0F));
        head.addOrReplaceChild("blanc_ribbon", CubeListBuilder.create()
                .texOffs(34, 10).addBox(-2.0F, -2.0F, 4.2F, 4.0F, 2.0F, 1.0F),
                PartPose.ZERO);
        body.addOrReplaceChild("blanc_coat", CubeListBuilder.create()
                .texOffs(16, 36).addBox(-4.0F, 11.5F, 1.5F, 8.0F, 8.0F, 0.5F, new CubeDeformation(0.15F)),
                PartPose.rotation(0.06F, 0.0F, 0.0F));

        // --- 4. JAUNE (Carrera) ---
        head.addOrReplaceChild("jaune_twintail_left", CubeListBuilder.create()
                .texOffs(56, 0).addBox(3.5F, -6.0F, 0.5F, 2.0F, 12.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.08F, 0.0F, -0.2F));
        head.addOrReplaceChild("jaune_twintail_right", CubeListBuilder.create()
                .texOffs(56, 0).mirror().addBox(-5.5F, -6.0F, 0.5F, 2.0F, 12.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.08F, 0.0F, 0.2F));
        body.addOrReplaceChild("jaune_epaulets", CubeListBuilder.create()
                .texOffs(16, 32).addBox(3.5F, -0.5F, -2.5F, 2.5F, 1.5F, 5.0F)
                .texOffs(16, 32).mirror().addBox(-6.0F, -0.5F, -2.5F, 2.5F, 1.5F, 5.0F),
                PartPose.ZERO);

        // --- 5. VIOLET (Ultima) ---
        head.addOrReplaceChild("violet_buns", CubeListBuilder.create()
                .texOffs(33, 2).addBox(3.5F, -8.5F, -1.5F, 3.0F, 3.0F, 3.0F)
                .texOffs(57, 2).addBox(-6.5F, -8.5F, -1.5F, 3.0F, 3.0F, 3.0F),
                PartPose.ZERO);
        body.addOrReplaceChild("violet_skirt", CubeListBuilder.create()
                .texOffs(16, 36).addBox(-5.0F, 10.5F, -3.0F, 10.0F, 6.0F, 6.0F, new CubeDeformation(0.4F)),
                PartPose.ZERO);

        // --- 6. BLEU (Rain) ---
        head.addOrReplaceChild("bleu_maid_band", CubeListBuilder.create()
                .texOffs(39, 0).addBox(-4.5F, -9.0F, -2.5F, 9.0F, 2.0F, 4.0F, new CubeDeformation(0.2F)),
                PartPose.ZERO);
        body.addOrReplaceChild("bleu_skirt", CubeListBuilder.create()
                .texOffs(16, 36).addBox(-4.5F, 11.0F, -2.5F, 9.0F, 6.5F, 5.0F, new CubeDeformation(0.25F)),
                PartPose.ZERO);

        // --- 7. VERT (Misery) ---
        head.addOrReplaceChild("vert_hair", CubeListBuilder.create()
                .texOffs(56, 0).addBox(-4.0F, -2.0F, 3.6F, 8.0F, 12.0F, 1.5F),
                PartPose.rotation(0.06F, 0.0F, 0.0F));
        head.addOrReplaceChild("vert_maid_band", CubeListBuilder.create()
                .texOffs(40, 0).addBox(-4.5F, -8.8F, -2.0F, 9.0F, 2.0F, 4.0F, new CubeDeformation(0.2F)),
                PartPose.ZERO);
        body.addOrReplaceChild("vert_skirt", CubeListBuilder.create()
                .texOffs(16, 36).addBox(-4.5F, 11.0F, -2.5F, 9.0F, 10.0F, 5.0F, new CubeDeformation(0.3F)),
                PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(PrimordialDemonEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        DemonType type = entity.getDemonType();
        if (type == null) type = DemonType.NOIR;

        boolean isNoir = (type == DemonType.NOIR);
        boolean isRouge = (type == DemonType.ROUGE);
        boolean isBlanc = (type == DemonType.BLANC);
        boolean isJaune = (type == DemonType.JAUNE);
        boolean isViolet = (type == DemonType.VIOLET);
        boolean isBleu = (type == DemonType.BLEU);
        boolean isVert = (type == DemonType.VERT);

        // Noir
        this.noirBangs.visible = isNoir;
        this.noirTails.visible = isNoir;
        this.wings.visible = entity.isWinged();

        boolean showNoirClaws = isNoir && (entity.isCombatClawsActive() || entity.getTarget() != null || entity.swingTime > 0);
        this.noirClawsRight.visible = showNoirClaws;
        this.noirClawsLeft.visible = showNoirClaws;

        // Rouge
        this.rougeMane.visible = isRouge;
        this.rougeCollar.visible = isRouge;
        this.rougeCloak.visible = isRouge;

        // Blanc
        this.blancLongHair.visible = isBlanc;
        this.blancRibbon.visible = isBlanc;
        this.blancCoat.visible = isBlanc;

        // Jaune
        this.jauneTwintailLeft.visible = isJaune;
        this.jauneTwintailRight.visible = isJaune;
        this.jauneEpaulets.visible = isJaune;

        // Violet
        this.violetBuns.visible = isViolet;
        this.violetSkirt.visible = isViolet;

        // Bleu
        this.bleuMaidBand.visible = isBleu;
        this.bleuSkirt.visible = isBleu;

        // Vert
        this.vertHair.visible = isVert;
        this.vertMaidBand.visible = isVert;
        this.vertSkirt.visible = isVert;

        // --- ANIMATIONS ---
        if (entity.isWinged()) {
            float flap = Mth.sin(ageInTicks * 0.2F) * 0.25F;
            float flapZ = Mth.cos(ageInTicks * 0.2F) * 0.15F;
            this.leftWing.yRot = 0.35F + flap;
            this.leftWing.zRot = 0.1F + flapZ;
            this.rightWing.yRot = -0.35F - flap;
            this.rightWing.zRot = -0.1F - flapZ;
        }

        // Jaune twin-tails swaying animation
        if (isJaune) {
            float sway = Mth.sin(limbSwing * 0.6662F) * 0.15F * limbSwingAmount;
            this.jauneTwintailLeft.zRot = -0.2F - sway;
            this.jauneTwintailRight.zRot = 0.2F + sway;
            this.jauneTwintailLeft.xRot = 0.08F + Mth.cos(ageInTicks * 0.08F) * 0.05F;
            this.jauneTwintailRight.xRot = 0.08F + Mth.cos(ageInTicks * 0.08F) * 0.05F;
        }

        // Blanc long hair drifting animation
        if (isBlanc) {
            this.blancLongHair.xRot = 0.08F + Mth.cos(limbSwing * 0.6662F) * 0.12F * limbSwingAmount;
        }

        // Vert long wavy hair
        if (isVert) {
            this.vertHair.xRot = 0.06F + Mth.cos(limbSwing * 0.6662F) * 0.1F * limbSwingAmount;
        }

        // Coat tails and skirts natural movement
        if (isNoir) {
            this.noirTails.xRot = 0.08F + Mth.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
        } else if (isBlanc) {
            this.blancCoat.xRot = 0.06F + Mth.cos(limbSwing * 0.6662F) * 0.15F * limbSwingAmount;
        }

        // Ritual rising pose
        if (entity.isRising()) {
            this.head.xRot = -0.2F;
            this.leftArm.xRot = 0.1F;
            this.rightArm.xRot = 0.1F;
            this.leftLeg.xRot = 0.0F;
            this.rightLeg.xRot = 0.0F;
        }
    }
}
