package com.minhphuc.weapons.entity.tensura;

import com.minhphuc.weapons.init.ModBlocks;
import com.minhphuc.weapons.init.ModItems;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public enum DemonType {
    ROUGE(
            "Rouge",
            "Hồng Sắc Thủy Tổ",
            "The Primordial Red",
            0xFF1E1E,
            0xD41515,
            "Hỏa Ngục Thủy Tổ",
            "Primordial Hellfire",
            "Hmph... Ta đã đáp lại lời triệu hồi của ngươi.",
            ResourceLocation.fromNamespaceAndPath("weapons", "textures/entity/demon/rouge.png"),
            ResourceLocation.fromNamespaceAndPath("weapons", "textures/entity/demon/eyes_rouge.png"),
            1.16F,
            3600.0D,
            160.0D,
            0.90F
    ),
    NOIR(
            "Noir",
            "Hắc Sắc Thủy Tổ",
            "The Primordial Black",
            0x222226,
            0x111116,
            "Móng Vuốt Tuyệt Vọng",
            "Claws of Despair",
            "Kufufufu... Tôi đã chờ đợi giây phút được phụng sự ngài từ lâu lắm rồi!",
            ResourceLocation.fromNamespaceAndPath("weapons", "textures/entity/demon/noir.png"),
            ResourceLocation.fromNamespaceAndPath("weapons", "textures/entity/demon/eyes_noir.png"),
            1.08F,
            3600.0D,
            160.0D,
            0.90F
    ),
    BLANC(
            "Blanc",
            "Bạch Sắc Thủy Tổ",
            "The Primordial White",
            0xF0F4F8,
            0xE2E8F0,
            "Bạch Viêm Diệt Tuyệt",
            "White Flare",
            "Ara ara... Ngươi là người đã đánh thức ta sao? Thú vị đấy.",
            ResourceLocation.fromNamespaceAndPath("weapons", "textures/entity/demon/blanc.png"),
            ResourceLocation.fromNamespaceAndPath("weapons", "textures/entity/demon/eyes_blanc.png"),
            1.02F,
            1800.0D,
            80.0D,
            0.75F
    ),
    JAUNE(
            "Jaune",
            "Hoàng Sắc Thủy Tổ",
            "The Primordial Yellow",
            0xFACC15,
            0xEAB308,
            "Trọng Lực Tuyệt Diệt",
            "Gravity Annihilation",
            "Haha! Kẻ nào dám triệu hồi ta đó? Hãy cho ta một trận chiến ra trò nào!",
            ResourceLocation.fromNamespaceAndPath("weapons", "textures/entity/demon/jaune.png"),
            ResourceLocation.fromNamespaceAndPath("weapons", "textures/entity/demon/eyes_jaune.png"),
            1.00F,
            1500.0D,
            70.0D,
            0.65F
    ),
    VIOLET(
            "Violet",
            "Tử Sắc Thủy Tổ",
            "The Primordial Purple",
            0xA855F7,
            0x9333EA,
            "Tử Độc Khởi Nguyên",
            "Primordial Toxic Nova",
            "Hihihi, gọi ta ra đây có gì vui không nào, chủ nhân?",
            ResourceLocation.fromNamespaceAndPath("weapons", "textures/entity/demon/violet.png"),
            ResourceLocation.fromNamespaceAndPath("weapons", "textures/entity/demon/eyes_violet.png"),
            0.88F,
            950.0D,
            40.0D,
            0.33F
    ),
    BLEU(
            "Bleu",
            "Lam Sắc Thủy Tổ",
            "The Primordial Blue",
            0x3B82F6,
            0x2563EB,
            "Băng Cực Ma Trận",
            "Absolute Zero Spikes",
            "Tôi đã tới theo lời giao ước... Ngài cần tôi giải quyết kẻ nào?",
            ResourceLocation.fromNamespaceAndPath("weapons", "textures/entity/demon/bleu.png"),
            ResourceLocation.fromNamespaceAndPath("weapons", "textures/entity/demon/eyes_bleu.png"),
            0.96F,
            800.0D,
            30.0D,
            0.20F
    ),
    VERT(
            "Vert",
            "Lục Sắc Thủy Tổ",
            "The Primordial Green",
            0x22C55E,
            0x16A34A,
            "Bão Tố Lục Bảo & Kết Giới",
            "Emerald Tempest & Aegis",
            "Mọi sự hỗn loạn sẽ được vãn hồi. Tôi xin kính cẩn tuân lệnh ngài.",
            ResourceLocation.fromNamespaceAndPath("weapons", "textures/entity/demon/vert.png"),
            ResourceLocation.fromNamespaceAndPath("weapons", "textures/entity/demon/eyes_vert.png"),
            1.00F,
            900.0D,
            36.0D,
            0.30F
    );

    private final String colorName;
    private final String titleVi;
    private final String titleEn;
    private final int glowColor;
    private final int primaryColor;
    private final String skillNameVi;
    private final String skillNameEn;
    private final String summonDialogue;
    private final ResourceLocation textureLocation;
    private final ResourceLocation eyesTextureLocation;
    private final float scale;
    private final double maxHealth;
    private final double attackDamage;
    private final float bossWinRate;

    DemonType(String colorName, String titleVi, String titleEn, int glowColor, int primaryColor,
              String skillNameVi, String skillNameEn, String summonDialogue,
              ResourceLocation textureLocation, ResourceLocation eyesTextureLocation, float scale,
              double maxHealth, double attackDamage, float bossWinRate) {
        this.colorName = colorName;
        this.titleVi = titleVi;
        this.titleEn = titleEn;
        this.glowColor = glowColor;
        this.primaryColor = primaryColor;
        this.skillNameVi = skillNameVi;
        this.skillNameEn = skillNameEn;
        this.summonDialogue = summonDialogue;
        this.textureLocation = textureLocation;
        this.eyesTextureLocation = eyesTextureLocation;
        this.scale = scale;
        this.maxHealth = maxHealth;
        this.attackDamage = attackDamage;
        this.bossWinRate = bossWinRate;
    }

    public String getColorName() {
        return colorName;
    }

    public String getTitleVi() {
        return titleVi;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public int getGlowColor() {
        return glowColor;
    }

    public int getPrimaryColor() {
        return primaryColor;
    }

    public String getSkillNameVi() {
        return skillNameVi;
    }

    public String getSkillNameEn() {
        return skillNameEn;
    }

    public String getSummonDialogue() {
        return summonDialogue;
    }

    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }

    public ResourceLocation getEyesTextureLocation() {
        return eyesTextureLocation;
    }

    public float getScale() {
        return scale;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getAttackDamage() {
        return attackDamage;
    }

    public float getBossWinRate() {
        return bossWinRate;
    }

    public RegistrySupplier<Item> getMagicCircleItem() {
        return switch (this) {
            case NOIR -> ModItems.MAGIC_CIRCLE_NOIR;
            case ROUGE -> ModItems.MAGIC_CIRCLE_ROUGE;
            case BLANC -> ModItems.MAGIC_CIRCLE_BLANC;
            case JAUNE -> ModItems.MAGIC_CIRCLE_JAUNE;
            case VIOLET -> ModItems.MAGIC_CIRCLE_VIOLET;
            case BLEU -> ModItems.MAGIC_CIRCLE_BLEU;
            case VERT -> ModItems.MAGIC_CIRCLE_VERT;
        };
    }

    public RegistrySupplier<Item> getPactItem() {
        return switch (this) {
            case NOIR -> ModItems.PRIMORDIAL_PACT_NOIR;
            case ROUGE -> ModItems.PRIMORDIAL_PACT_ROUGE;
            case BLANC -> ModItems.PRIMORDIAL_PACT_BLANC;
            case JAUNE -> ModItems.PRIMORDIAL_PACT_JAUNE;
            case VIOLET -> ModItems.PRIMORDIAL_PACT_VIOLET;
            case BLEU -> ModItems.PRIMORDIAL_PACT_BLEU;
            case VERT -> ModItems.PRIMORDIAL_PACT_VERT;
        };
    }

    public RegistrySupplier<Block> getDomainBarrierBlock() {
        return switch (this) {
            case NOIR -> ModBlocks.DOMAIN_BARRIER_NOIR;
            case ROUGE -> ModBlocks.DOMAIN_BARRIER_ROUGE;
            case BLANC -> ModBlocks.DOMAIN_BARRIER_BLANC;
            case JAUNE -> ModBlocks.DOMAIN_BARRIER_JAUNE;
            case VIOLET -> ModBlocks.DOMAIN_BARRIER_VIOLET;
            case BLEU -> ModBlocks.DOMAIN_BARRIER_BLEU;
            case VERT -> ModBlocks.DOMAIN_BARRIER_VERT;
        };
    }

    public static DemonType byIndex(int index) {
        DemonType[] vals = values();
        if (index < 0 || index >= vals.length) return NOIR;
        return vals[index];
    }
}
