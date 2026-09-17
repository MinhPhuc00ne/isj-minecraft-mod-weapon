package com.minhphuc.weapons.client.gui;

import com.minhphuc.weapons.init.ModItems;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.net.URI;

public class GuideBookScreen extends Screen {

    private int currentPage = 0;
    private static final int TOTAL_PAGES = 10;

    private static final net.minecraft.resources.ResourceLocation DRAGON_NOVA_TEXTURE =
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("weapons", "textures/gui/dragon_nova_art.png");
    private static final net.minecraft.resources.ResourceLocation TAISUI_ART_TEXTURE =
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("weapons", "textures/gui/taisui_art.png");
    private static final net.minecraft.resources.ResourceLocation SEER_FLESH_ART_TEXTURE =
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("weapons", "textures/gui/seer_flesh_art.png");
    private static final net.minecraft.resources.ResourceLocation SEER_ARM_COMBO_ART_TEXTURE =
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("weapons", "textures/gui/seer_arm_combo_art.png");

    // Kích thước khung sách rộng rãi 430px x 236px, tuyệt đối không bị tràn chữ ra màn hình
    private static final int BOOK_WIDTH = 430;
    private static final int BOOK_HEIGHT = 236;

    private Button prevButton;
    private Button nextButton;
    private Button closeButton;

    // Các nút chuyển tab Gauntlet (Trang 4)
    private Button gauntletEmptyBtn;
    private Button gauntletLoadedBtn;
    private int selectedGauntletTab = 0; // 0: Empty, 1: Loaded

    // Các nút chuyển giáp (Trang 5)
    private Button armorHelmBtn;
    private Button armorChestBtn;
    private Button armorLegsBtn;
    private Button armorBootsBtn;
    private int selectedArmorPiece = 1; // 0: Mũ, 1: Áo, 2: Quần, 3: Giày

    // Các nút trang tác giả (Trang 6)
    private Button openGithubBtn;
    private Button copyGithubBtn;
    private static final String GITHUB_URL = "https://github.com/MinhPhuc00ne/isj-minecraft-mod-weapon";

    private ItemStack hoveredTooltipStack = null;

    // Cache các lưới công thức 3x3
    private final ItemStack[][] moonlightSwordRecipe = new ItemStack[3][3];
    private final ItemStack[][] demonLordSeedRecipe = new ItemStack[3][3];
    private final ItemStack[][] emptyGauntletRecipe = new ItemStack[3][3];
    private final ItemStack[][] loadedGauntletRecipe = new ItemStack[3][3];
    private final ItemStack[][][] divineArmorRecipes = new ItemStack[4][3][3];

    public GuideBookScreen() {
        super(Component.translatable("gui.weapons.guide.screen_title"));
        initRecipes();
    }

    private void initRecipes() {
        // 1. Công thức Nguyệt Quang Thần Tế Kiếm
        moonlightSwordRecipe[0][0] = new ItemStack(Items.DIAMOND);
        moonlightSwordRecipe[0][1] = new ItemStack(Items.NETHER_STAR);
        moonlightSwordRecipe[0][2] = new ItemStack(Items.DIAMOND);

        moonlightSwordRecipe[1][0] = new ItemStack(Items.ENDER_EYE);
        moonlightSwordRecipe[1][1] = new ItemStack(Items.GOLDEN_APPLE);
        moonlightSwordRecipe[1][2] = new ItemStack(Items.ENDER_EYE);

        moonlightSwordRecipe[2][0] = new ItemStack(Items.GOLD_INGOT);
        moonlightSwordRecipe[2][1] = new ItemStack(Items.DIAMOND_SWORD);
        moonlightSwordRecipe[2][2] = new ItemStack(Items.GOLD_INGOT);

        // 2. Công thức Hạt Giống Ma Vương
        demonLordSeedRecipe[0][0] = new ItemStack(Items.OBSIDIAN);
        demonLordSeedRecipe[0][1] = new ItemStack(Items.GHAST_TEAR);
        demonLordSeedRecipe[0][2] = new ItemStack(Items.OBSIDIAN);

        demonLordSeedRecipe[1][0] = new ItemStack(Items.GHAST_TEAR);
        demonLordSeedRecipe[1][1] = new ItemStack(Items.NETHER_STAR);
        demonLordSeedRecipe[1][2] = new ItemStack(Items.GHAST_TEAR);

        demonLordSeedRecipe[2][0] = new ItemStack(Items.OBSIDIAN);
        demonLordSeedRecipe[2][1] = new ItemStack(Items.GHAST_TEAR);
        demonLordSeedRecipe[2][2] = new ItemStack(Items.OBSIDIAN);

        // 3a. Công thức Găng Tay Trống (Empty Gauntlet)
        emptyGauntletRecipe[0][0] = new ItemStack(Items.GOLD_INGOT);
        emptyGauntletRecipe[0][1] = new ItemStack(Items.GOLD_BLOCK);
        emptyGauntletRecipe[0][2] = new ItemStack(Items.GOLD_INGOT);

        emptyGauntletRecipe[1][0] = new ItemStack(Items.GOLD_INGOT);
        emptyGauntletRecipe[1][1] = new ItemStack(Items.DIAMOND);
        emptyGauntletRecipe[1][2] = new ItemStack(Items.GOLD_INGOT);

        emptyGauntletRecipe[2][0] = new ItemStack(Items.GOLD_INGOT);
        emptyGauntletRecipe[2][1] = new ItemStack(Items.NETHERITE_INGOT);
        emptyGauntletRecipe[2][2] = new ItemStack(Items.GOLD_INGOT);

        // 3b. Công thức Găng Tay Đầy Đủ 6 Đá
        loadedGauntletRecipe[0][0] = new ItemStack(ModItems.POWER_STONE.get());
        loadedGauntletRecipe[0][1] = new ItemStack(ModItems.SPACE_STONE.get());
        loadedGauntletRecipe[0][2] = new ItemStack(ModItems.REALITY_STONE.get());

        loadedGauntletRecipe[1][0] = new ItemStack(ModItems.SOUL_STONE.get());
        loadedGauntletRecipe[1][1] = new ItemStack(ModItems.EMPTY_INFINITY_GAUNTLET.get());
        loadedGauntletRecipe[1][2] = new ItemStack(ModItems.TIME_STONE.get());

        loadedGauntletRecipe[2][0] = ItemStack.EMPTY;
        loadedGauntletRecipe[2][1] = new ItemStack(ModItems.MIND_STONE.get());
        loadedGauntletRecipe[2][2] = ItemStack.EMPTY;

        // 4. Công thức 4 Món Giáp Thần Linh (Mũ, Áo, Quần, Giày)
        ItemStack[] netheriteArmors = new ItemStack[] {
                new ItemStack(Items.NETHERITE_HELMET),
                new ItemStack(Items.NETHERITE_CHESTPLATE),
                new ItemStack(Items.NETHERITE_LEGGINGS),
                new ItemStack(Items.NETHERITE_BOOTS)
        };

        for (int p = 0; p < 4; p++) {
            divineArmorRecipes[p][0][0] = new ItemStack(Items.GOLD_BLOCK);
            divineArmorRecipes[p][0][1] = new ItemStack(Items.DIAMOND);
            divineArmorRecipes[p][0][2] = new ItemStack(Items.GOLD_BLOCK);

            divineArmorRecipes[p][1][0] = new ItemStack(Items.ENDER_EYE);
            divineArmorRecipes[p][1][1] = netheriteArmors[p];
            divineArmorRecipes[p][1][2] = new ItemStack(Items.ENDER_EYE);

            divineArmorRecipes[p][2][0] = new ItemStack(Items.GOLD_BLOCK);
            divineArmorRecipes[p][2][1] = new ItemStack(Items.DIAMOND);
            divineArmorRecipes[p][2][2] = new ItemStack(Items.GOLD_BLOCK);
        }
    }

    @Override
    protected void init() {
        super.init();

        int left = (this.width - BOOK_WIDTH) / 2;
        int top = (this.height - BOOK_HEIGHT) / 2;

        // Nút trang trước
        prevButton = Button.builder(Component.translatable("gui.weapons.guide.btn.prev"), b -> {
            if (currentPage > 0) currentPage--;
            updateButtonState();
        }).bounds(left + 16, top + BOOK_HEIGHT - 26, 95, 20).build();

        // Nút trang sau
        nextButton = Button.builder(Component.translatable("gui.weapons.guide.btn.next"), b -> {
            if (currentPage < TOTAL_PAGES - 1) currentPage++;
            updateButtonState();
        }).bounds(left + BOOK_WIDTH - 111, top + BOOK_HEIGHT - 26, 95, 20).build();

        // Nút Đóng
        closeButton = Button.builder(Component.translatable("gui.weapons.guide.btn.close"), b -> {
            this.onClose();
        }).bounds(left + BOOK_WIDTH / 2 - 30, top + BOOK_HEIGHT - 26, 60, 20).build();

        this.addRenderableWidget(prevButton);
        this.addRenderableWidget(nextButton);
        this.addRenderableWidget(closeButton);

        // --- Nút trang 4 (Găng Tay) ---
        int gauntletBtnY = top + 80;
        gauntletEmptyBtn = Button.builder(Component.translatable("gui.weapons.guide.btn.empty_gauntlet"), b -> selectedGauntletTab = 0)
                .bounds(left + 130, gauntletBtnY, 100, 18).build();
        gauntletLoadedBtn = Button.builder(Component.translatable("gui.weapons.guide.btn.loaded_gauntlet"), b -> selectedGauntletTab = 1)
                .bounds(left + 238, gauntletBtnY, 100, 18).build();

        this.addRenderableWidget(gauntletEmptyBtn);
        this.addRenderableWidget(gauntletLoadedBtn);

        // --- Nút trang 5 (4 Món Giáp) ---
        int armorBtnY = top + 80;
        armorHelmBtn = Button.builder(Component.translatable("gui.weapons.guide.btn.armor_helm"), b -> selectedArmorPiece = 0)
                .bounds(left + 130, armorBtnY, 46, 18).build();
        armorChestBtn = Button.builder(Component.translatable("gui.weapons.guide.btn.armor_chest"), b -> selectedArmorPiece = 1)
                .bounds(left + 182, armorBtnY, 46, 18).build();
        armorLegsBtn = Button.builder(Component.translatable("gui.weapons.guide.btn.armor_legs"), b -> selectedArmorPiece = 2)
                .bounds(left + 234, armorBtnY, 46, 18).build();
        armorBootsBtn = Button.builder(Component.translatable("gui.weapons.guide.btn.armor_boots"), b -> selectedArmorPiece = 3)
                .bounds(left + 286, armorBtnY, 46, 18).build();

        this.addRenderableWidget(armorHelmBtn);
        this.addRenderableWidget(armorChestBtn);
        this.addRenderableWidget(armorLegsBtn);
        this.addRenderableWidget(armorBootsBtn);

        // --- Nút trang 10 (Tác Giả & GitHub) ---
        int authorBtnY = top + BOOK_HEIGHT - 54;
        openGithubBtn = Button.builder(Component.translatable("gui.weapons.guide.btn.open_github"), b -> {
            try {
                Util.getPlatform().openUri(URI.create(GITHUB_URL));
            } catch (Exception ignored) {}
        }).bounds(left + BOOK_WIDTH / 2 - 135, authorBtnY, 130, 20).build();

        copyGithubBtn = Button.builder(Component.translatable("gui.weapons.guide.btn.copy_github"), b -> {
            if (this.minecraft != null) {
                this.minecraft.keyboardHandler.setClipboard(GITHUB_URL);
                if (this.minecraft.player != null) {
                    this.minecraft.player.displayClientMessage(
                            Component.translatable("gui.weapons.guide.copy_success"),
                            true
                    );
                }
            }
        }).bounds(left + BOOK_WIDTH / 2 + 5, authorBtnY, 130, 20).build();

        this.addRenderableWidget(openGithubBtn);
        this.addRenderableWidget(copyGithubBtn);

        updateButtonState();
    }

    private void updateButtonState() {
        if (prevButton != null) prevButton.active = currentPage > 0;
        if (nextButton != null) nextButton.active = currentPage < TOTAL_PAGES - 1;

        boolean isGauntletPage = (currentPage == 3);
        if (gauntletEmptyBtn != null) gauntletEmptyBtn.visible = isGauntletPage;
        if (gauntletLoadedBtn != null) gauntletLoadedBtn.visible = isGauntletPage;

        boolean isArmorPage = (currentPage == 4);
        if (armorHelmBtn != null) armorHelmBtn.visible = isArmorPage;
        if (armorChestBtn != null) armorChestBtn.visible = isArmorPage;
        if (armorLegsBtn != null) armorLegsBtn.visible = isArmorPage;
        if (armorBootsBtn != null) armorBootsBtn.visible = isArmorPage;

        boolean isAuthorPage = (currentPage == 9);
        if (openGithubBtn != null) openGithubBtn.visible = isAuthorPage;
        if (copyGithubBtn != null) copyGithubBtn.visible = isAuthorPage;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        int left = (this.width - BOOK_WIDTH) / 2;
        int top = (this.height - BOOK_HEIGHT) / 2;

        hoveredTooltipStack = null;

        // 1. Khung bìa sách cổ huyền bí
        guiGraphics.fill(left - 3, top - 3, left + BOOK_WIDTH + 3, top + BOOK_HEIGHT + 3, 0xFF140D05); // Viền da
        guiGraphics.fill(left - 1, top - 1, left + BOOK_WIDTH + 1, top + BOOK_HEIGHT + 1, 0xFFD4AF37); // Chỉ vàng
        guiGraphics.fill(left, top, left + BOOK_WIDTH, top + BOOK_HEIGHT, 0xFF1B1622); // Ruột giấy cổ

        // Tiêu đề thanh trên
        guiGraphics.fill(left + 8, top + 6, left + BOOK_WIDTH - 8, top + 26, 0xFF282033);
        guiGraphics.fill(left + 8, top + 25, left + BOOK_WIDTH - 8, top + 26, 0xFFD4AF37);

        // Số trang góc phải
        String pageStr = Component.translatable("gui.weapons.guide.page_indicator", currentPage + 1, TOTAL_PAGES).getString();
        guiGraphics.drawString(font, "§7" + pageStr, left + BOOK_WIDTH - font.width(pageStr) - 14, top + 11, 0xFFFFFFFF, false);

        // 2. Nội dung từng trang
        switch (currentPage) {
            case 0 -> renderPageOverview(guiGraphics, left, top, mouseX, mouseY);
            case 1 -> renderPageMoonlightSword(guiGraphics, left, top, mouseX, mouseY);
            case 2 -> renderPageDemonLord(guiGraphics, left, top, mouseX, mouseY);
            case 3 -> renderPageInfinityGauntlet(guiGraphics, left, top, mouseX, mouseY);
            case 4 -> renderPageDivineArmor(guiGraphics, left, top, mouseX, mouseY);
            case 5 -> renderPageDragonNova(guiGraphics, left, top, mouseX, mouseY);
            case 6 -> renderPageTaisui(guiGraphics, left, top, mouseX, mouseY);
            case 7 -> renderPageSeerFlesh(guiGraphics, left, top, mouseX, mouseY);
            case 8 -> renderPageSeerArmCombo(guiGraphics, left, top, mouseX, mouseY);
            case 9 -> renderPageAuthor(guiGraphics, left, top, mouseX, mouseY);
        }

        // 3. Render Tooltip nếu đang di chuột qua slot item
        if (hoveredTooltipStack != null && !hoveredTooltipStack.isEmpty()) {
            guiGraphics.renderTooltip(font, hoveredTooltipStack, mouseX, mouseY);
        }
    }

    /**
     * TRANG 1: MA ĐẠO THƯ
     */
    private void renderPageOverview(GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY) {
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p0.title").getString(), left + 14, top + 11, 0xFFFFAA00, false);

        int textY = top + 36;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p0.greeting").getString(), left + 14, textY, 0xFFFFFFFF, false);

        textY += 16;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p0.intro1").getString(), left + 14, textY, 0xFFDDDDDD, false);
        textY += 12;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p0.intro2").getString(), left + 14, textY, 0xFFDDDDDD, false);

        textY += 18;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p0.controls_title").getString(), left + 14, textY, 0xFF55FFFF, false);

        textY += 14;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p0.control1").getString(), left + 18, textY, 0xFFFFFFFF, false);
        textY += 13;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p0.control2").getString(), left + 18, textY, 0xFFFFFFFF, false);
        textY += 13;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p0.control3a").getString(), left + 18, textY, 0xFFFFFFFF, false);
        textY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p0.control3b").getString(), left + 18, textY, 0xFFFFFFFF, false);

        textY += 20;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p0.footer").getString(), left + 14, textY, 0xFF888888, false);
    }

    /**
     * TRANG 2: NGUYỆT QUANG THẦN TẾ KIẾM
     */
    private void renderPageMoonlightSword(GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY) {
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p1.title").getString(), left + 14, top + 11, 0xFFFFAA00, false);

        int gridX = left + 16;
        int gridY = top + 34;

        drawCraftingGrid(guiGraphics, gridX, gridY, moonlightSwordRecipe, new ItemStack(ModItems.MOONLIGHT_SWORD.get()), mouseX, mouseY);

        int descX = left + 134;
        int descY = top + 34;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p1.name").getString(), descX, descY, 0xFFFFAA00, false);
        descY += 14;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p1.desc").getString(), descX, descY, 0xFFDDDDDD, false);
        descY += 12;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p1.durability").getString(), descX, descY, 0xFF55FF55, false);

        int skillY = top + 104;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p1.skills_title").getString(), left + 14, skillY, 0xFFFFDD00, false);

        skillY += 13;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p1.skill1").getString(), left + 18, skillY, 0xFFFFFFFF, false);
        skillY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p1.skill2").getString(), left + 18, skillY, 0xFFFFFFFF, false);
        skillY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p1.skill3").getString(), left + 18, skillY, 0xFFFFFFFF, false);
        skillY += 10;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p1.skill3a").getString(), left + 18, skillY, 0xFFFFFFFF, false);
        skillY += 10;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p1.skill3b").getString(), left + 18, skillY, 0xFFFFFFFF, false);
        skillY += 10;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p1.skill4").getString(), left + 18, skillY, 0xFFFFAA00, false);
    }

    /**
     * TRANG 3: CON ĐƯỜNG THỨC TỈNH CHÂN MA VƯƠNG
     */
    private void renderPageDemonLord(GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY) {
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p2.title").getString(), left + 14, top + 11, 0xFFFF55FF, false);

        int gridX = left + 16;
        int gridY = top + 34;

        drawCraftingGrid(guiGraphics, gridX, gridY, demonLordSeedRecipe, new ItemStack(ModItems.DEMON_LORD_SEED.get()), mouseX, mouseY);

        int descX = left + 134;
        int descY = top + 34;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p2.seed_name").getString(), descX, descY, 0xFFFF55FF, false);
        descY += 13;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p2.how_to_get").getString(), descX, descY, 0xFF55FF55, false);
        descY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p2.exp_note").getString(), descX, descY, 0xFFFFFFFF, false);
        descY += 10;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p2.soul_note").getString(), descX, descY, 0xFFFFFFFF, false);
        descY += 12;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p2.right_click").getString(), descX, descY, 0xFFFFAA00, false);
        descY += 10;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p2.harvest").getString(), descX, descY, 0xFFFFAA00, false);

        int awakenY = top + 104;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p2.perks_title").getString(), left + 14, awakenY, 0xFFFF55FF, false);

        awakenY += 13;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p2.perk1").getString(), left + 18, awakenY, 0xFFFFFFFF, false);
        awakenY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p2.perk2").getString(), left + 18, awakenY, 0xFFFFFFFF, false);
        awakenY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p2.perk3").getString(), left + 18, awakenY, 0xFFFFFFFF, false);
        awakenY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p2.perk4").getString(), left + 18, awakenY, 0xFFFF55FF, false);
    }

    /**
     * TRANG 4: GĂNG TAY VÔ CỰC & 6 VIÊN ĐÁ
     */
    private void renderPageInfinityGauntlet(GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY) {
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p3.title").getString(), left + 14, top + 11, 0xFFFFFF55, false);

        int gridX = left + 16;
        int gridY = top + 34;

        if (selectedGauntletTab == 0) {
            drawCraftingGrid(guiGraphics, gridX, gridY, emptyGauntletRecipe, new ItemStack(ModItems.EMPTY_INFINITY_GAUNTLET.get()), mouseX, mouseY);
        } else {
            drawCraftingGrid(guiGraphics, gridX, gridY, loadedGauntletRecipe, new ItemStack(ModItems.INFINITY_GAUNTLET.get()), mouseX, mouseY);
        }

        int descX = left + 134;
        int descY = top + 34;
        if (selectedGauntletTab == 0) {
            guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p3.empty_name").getString(), descX, descY, 0xFFFFFF55, false);
            descY += 12;
            guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p3.empty_desc1").getString(), descX, descY, 0xFFDDDDDD, false);
            descY += 11;
            guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p3.empty_desc2").getString(), descX, descY, 0xFF55FF55, false);
            descY += 11;
            guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p3.empty_desc3").getString(), descX, descY, 0xFF888888, false);
        } else {
            guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p3.loaded_name").getString(), descX, descY, 0xFFFFAA00, false);
            descY += 12;
            guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p3.loaded_desc1").getString(), descX, descY, 0xFF55FF55, false);
            descY += 11;
            guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p3.loaded_desc2").getString(), descX, descY, 0xFF55FFFF, false);
            descY += 11;
            guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p3.loaded_desc3").getString(), descX, descY, 0xFFFFFF55, false);
        }

        int bossY = top + 104;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p3.boss_title").getString(), left + 14, bossY, 0xFFFFAA00, false);

        bossY += 13;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p3.boss1").getString(), left + 18, bossY, 0xFFFFFFFF, false);
        bossY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p3.boss2").getString(), left + 18, bossY, 0xFFFFFFFF, false);
        bossY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p3.boss3").getString(), left + 18, bossY, 0xFFFFFFFF, false);
        bossY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p3.backlash_warning").getString(), left + 18, bossY, 0xFFFF5555, false);
    }

    /**
     * TRANG 5: THẦN LINH VŨ TRANG (DIVINE ARMOR)
     */
    private void renderPageDivineArmor(GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY) {
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p4.title").getString(), left + 14, top + 11, 0xFFFFAA00, false);

        int gridX = left + 16;
        int gridY = top + 34;

        ItemStack outputArmor = switch (selectedArmorPiece) {
            case 0 -> new ItemStack(ModItems.DIVINE_HELMET.get());
            case 1 -> new ItemStack(ModItems.DIVINE_CHESTPLATE.get());
            case 2 -> new ItemStack(ModItems.DIVINE_LEGGINGS.get());
            default -> new ItemStack(ModItems.DIVINE_BOOTS.get());
        };

        String armorPieceName = switch (selectedArmorPiece) {
            case 0 -> Component.translatable("gui.weapons.guide.p4.piece_helm").getString();
            case 1 -> Component.translatable("gui.weapons.guide.p4.piece_chest").getString();
            case 2 -> Component.translatable("gui.weapons.guide.p4.piece_legs").getString();
            default -> Component.translatable("gui.weapons.guide.p4.piece_boots").getString();
        };

        drawCraftingGrid(guiGraphics, gridX, gridY, divineArmorRecipes[selectedArmorPiece], outputArmor, mouseX, mouseY);

        int descX = left + 134;
        int descY = top + 34;
        guiGraphics.drawString(font, "§e§l" + armorPieceName, descX, descY, 0xFFFFFF55, false);
        descY += 12;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p4.desc1").getString(), descX, descY, 0xFFFFFFFF, false);
        descY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p4.desc2").getString(), descX, descY, 0xFF55FF55, false);
        descY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p4.desc3").getString(), descX, descY, 0xFF888888, false);

        int effY = top + 104;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p4.imm_title").getString(), left + 14, effY, 0xFFFFAA00, false);

        effY += 13;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p4.imm1").getString(), left + 18, effY, 0xFFFFFFFF, false);
        effY += 12;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p4.imm2").getString(), left + 18, effY, 0xFFFFFFFF, false);
        effY += 12;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p4.imm3").getString(), left + 18, effY, 0xFFFF55FF, false);
        effY += 12;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p4.weakness").getString(), left + 18, effY, 0xFFFFFFFF, false);
    }

    /**
     * TRANG 6: CẤM THUẬT DIỆT RỒNG - LONG TINH BỘC VIÊM BÁ (DRAGON NOVA)
     */
    private void renderPageDragonNova(GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY) {
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p5.title").getString(), left + 14, top + 11, 0xFFFF55FF, false);

        int imgX = left + 14;
        int imgY = top + 34;
        int imgW = 120;
        int imgH = 88;

        // Viền vàng kim tinh xảo quanh tranh minh họa
        guiGraphics.fill(imgX - 2, imgY - 2, imgX + imgW + 2, imgY + imgH + 2, 0xFFD4AF37);
        guiGraphics.fill(imgX - 1, imgY - 1, imgX + imgW + 1, imgY + imgH + 1, 0xFF1B1622);
        guiGraphics.blit(DRAGON_NOVA_TEXTURE, imgX, imgY, 0.0F, 0.0F, imgW, imgH, imgW, imgH);

        // Văn phong thần thoại mô tả trận trảm sát Tinh Long
        int descX = left + 144;
        int descY = top + 34;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p5.name1").getString(), descX, descY, 0xFFFF55FF, true);
        descY += 12;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p5.name2").getString(), descX, descY, 0xFFFF88FF, true);
        descY += 12;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p5.desc1").getString(), descX, descY, 0xFFDDDDDD, true);
        descY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p5.desc2").getString(), descX, descY, 0xFFDDDDDD, true);
        descY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p5.desc3").getString(), descX, descY, 0xFFFF6666, true);
        descY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p5.desc4").getString(), descX, descY, 0xFF55FF55, true);

        int effY = top + 128;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p5.power_title").getString(), left + 14, effY, 0xFFFFAA00, true);

        effY += 12;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p5.power1").getString(), left + 18, effY, 0xFFFFFFFF, true);
        effY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p5.power2").getString(), left + 18, effY, 0xFFFFFFFF, true);
        effY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p5.power3").getString(), left + 18, effY, 0xFFFFFFFF, true);
        effY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p5.power4").getString(), left + 18, effY, 0xFFFFFF55, true);
    }

    /**
     * TRANG 7: THÁI TUẾ TINH QUÂN - DARK GATHERING
     */
    private void renderPageTaisui(GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY) {
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p6.title").getString(), left + 14, top + 11, 0xFFFFCC00, true);

        int imgX = left + 14;
        int imgY = top + 34;
        int imgW = 120;
        int imgH = 88;

        // Viền hoàng kim cổ thần
        guiGraphics.fill(imgX - 2, imgY - 2, imgX + imgW + 2, imgY + imgH + 2, 0xFFE5C158);
        guiGraphics.fill(imgX - 1, imgY - 1, imgX + imgW + 1, imgY + imgH + 1, 0xFF1B1622);
        guiGraphics.blit(TAISUI_ART_TEXTURE, imgX, imgY, 0.0F, 0.0F, imgW, imgH, imgW, imgH);

        // Mô tả Thái Tuế Tinh Quân
        int descX = left + 144;
        int descY = top + 34;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p6.name").getString(), descX, descY, 0xFFFFD700, true);
        descY += 12;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p6.desc").getString(), descX, descY, 0xFFDDDDDD, true);
        descY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p6.star_name").getString(), descX, descY, 0xFFFF88FF, true);
        descY += 10;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p6.star_click").getString(), descX, descY, 0xFFFFFFFF, true);
        descY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p6.array_name").getString(), descX, descY, 0xFF55FFFF, true);
        descY += 10;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p6.array_sub").getString(), descX, descY, 0xFFFFFFFF, true);

        int effY = top + 128;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p6.rules_title").getString(), left + 14, effY, 0xFFFFAA00, true);

        effY += 12;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p6.rule1").getString(), left + 18, effY, 0xFFFFFFFF, true);
        effY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p6.rule2").getString(), left + 18, effY, 0xFFFFFFFF, true);
        effY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p6.rule3").getString(), left + 18, effY, 0xFFFFFFFF, true);
        effY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p6.rule4").getString(), left + 18, effY, 0xFFFFFF55, true);
    }

    /**
     * TRANG 8: THÁI TUẾ TINH QUÂN - THỊ NHỤC (SEER FLESH)
     */
    private void renderPageSeerFlesh(GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY) {
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p7.title").getString(), left + 14, top + 11, 0xFFFF4444, true);

        int imgX = left + 14;
        int imgY = top + 34;
        int imgW = 120;
        int imgH = 88;

        // Viền đỏ thẫm huyết tế quanh tranh minh họa
        guiGraphics.fill(imgX - 2, imgY - 2, imgX + imgW + 2, imgY + imgH + 2, 0xFFCC0022);
        guiGraphics.fill(imgX - 1, imgY - 1, imgX + imgW + 1, imgY + imgH + 1, 0xFF1B1622);
        guiGraphics.blit(SEER_FLESH_ART_TEXTURE, imgX, imgY, 0.0F, 0.0F, imgW, imgH, imgW, imgH);

        // Mô tả Thị Nhục
        int descX = left + 144;
        int descY = top + 34;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p7.name").getString(), descX, descY, 0xFFFF5555, true);
        descY += 12;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p7.desc").getString(), descX, descY, 0xFFDDDDDD, true);
        descY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p7.summon").getString(), descX, descY, 0xFFFFAA00, true);
        descY += 10;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p7.distance").getString(), descX, descY, 0xFF888888, true);
        descY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p7.harvest").getString(), descX, descY, 0xFF55FF55, true);
        descY += 10;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p7.attack").getString(), descX, descY, 0xFFFF8888, true);

        int effY = top + 128;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p7.effects_title").getString(), left + 14, effY, 0xFFFFAA00, true);

        effY += 12;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p7.eff1").getString(), left + 18, effY, 0xFFFFFFFF, true);
        effY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p7.eff2").getString(), left + 18, effY, 0xFFFFFFFF, true);
        effY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p7.eff3").getString(), left + 18, effY, 0xFFFFFFFF, true);
        effY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p7.eff4").getString(), left + 18, effY, 0xFFFFFF55, true);
    }

    /**
     * TRANG 9: CỘNG HƯỞNG THÁI TUẾ - THỊ NHỤC BỌC TAY & TUYỆT DIỆT TINH TÚ
     */
    private void renderPageSeerArmCombo(GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY) {
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p8.title").getString(), left + 14, top + 11, 0xFFFFAA00, false);

        // Ảnh minh họa chiến đấu Dark Gathering hoành tráng
        int imgX = left + 16;
        int imgY = top + 34;
        int imgW = 126;
        int imgH = 92;

        // Viền ngọc bích ma quái quanh tranh minh họa
        guiGraphics.fill(imgX - 2, imgY - 2, imgX + imgW + 2, imgY + imgH + 2, 0xFF2E5A36);
        guiGraphics.fill(imgX - 1, imgY - 1, imgX + imgW + 1, imgY + imgH + 1, 0xFF1B1622);
        guiGraphics.blit(SEER_ARM_COMBO_ART_TEXTURE, imgX, imgY, 0.0F, 0.0F, imgW, imgH, imgW, imgH);

        // Mô tả hình thái cộng hưởng bên phải ảnh
        int descX = left + 150;
        int descY = top + 34;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p8.name").getString(), descX, descY, 0xFF55FF55, true);
        descY += 12;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p8.desc").getString(), descX, descY, 0xFFDDDDDD, true);
        descY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p8.combo_trigger").getString(), descX, descY, 0xFFFFAA00, true);
        descY += 12;

        // Hiển thị 2 vật phẩm preview trực quan có tooltip
        drawSingleItemSlot(guiGraphics, descX, descY, new ItemStack(ModItems.SEER_FLESH_ARM.get()), mouseX, mouseY);
        drawSingleItemSlot(guiGraphics, descX + 26, descY, new ItemStack(ModItems.EXTINCTION_STAR.get()), mouseX, mouseY);
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p8.item_note").getString(), descX + 54, descY + 6, 0xFFFFFF55, true);

        // Các quy tắc và uy lực chiến đấu
        int effY = top + 130;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p8.effects_title").getString(), left + 14, effY, 0xFFFFAA00, true);

        effY += 12;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p8.eff1").getString(), left + 18, effY, 0xFFFFFFFF, true);
        effY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p8.eff2").getString(), left + 18, effY, 0xFFFFFFFF, true);
        effY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p8.eff3").getString(), left + 18, effY, 0xFFFFFFFF, true);
        effY += 11;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p8.eff4").getString(), left + 18, effY, 0xFF55FFFF, true);
    }

    private void drawSingleItemSlot(GuiGraphics guiGraphics, int slotX, int slotY, ItemStack item, int mouseX, int mouseY) {
        guiGraphics.fill(slotX, slotY, slotX + 20, slotY + 20, 0xFF373737);
        guiGraphics.fill(slotX + 1, slotY + 1, slotX + 19, slotY + 19, 0xFF8B8B8B);
        guiGraphics.fill(slotX + 1, slotY + 1, slotX + 18, slotY + 18, 0xFF2A2A2A);

        if (item != null && !item.isEmpty()) {
            guiGraphics.renderItem(item, slotX + 2, slotY + 2);
            if (mouseX >= slotX && mouseX <= slotX + 20 && mouseY >= slotY && mouseY <= slotY + 20) {
                hoveredTooltipStack = item;
            }
        }
    }

    /**
     * TRANG 10: TÁC GIẢ & BẢN QUYỀN DỰ ÁN
     */
    private void renderPageAuthor(GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY) {
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p9.title").getString(), left + 14, top + 11, 0xFFFFAA00, false);

        int textY = top + 34;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p9.author").getString(), left + 18, textY, 0xFFFFAA00, false);

        textY += 13;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p9.studio").getString(), left + 18, textY, 0xFF55FFFF, false);

        textY += 13;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p9.university").getString(), left + 18, textY, 0xFFFFAA00, false);

        textY += 13;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p9.mod_name").getString(), left + 18, textY, 0xFF55FF55, false);

        textY += 13;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p9.license").getString(), left + 18, textY, 0xFFFF55FF, false);

        textY += 15;
        // Khung trích dẫn hoành tráng
        int quoteBoxW = BOOK_WIDTH - 36;
        guiGraphics.fill(left + 18, textY - 2, left + 18 + quoteBoxW, textY + 38, 0xFF231B30);
        drawBorder(guiGraphics, left + 18, textY - 2, quoteBoxW, 40, 0xFFD4AF37);

        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p9.quote1").getString(), left + 24, textY + 3, 0xFFFFFF55, false);
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p9.quote2").getString(), left + 24, textY + 14, 0xFFFFFF55, false);
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p9.quote3").getString(), left + 24, textY + 25, 0xFFFFFF55, false);

        textY += 44;
        guiGraphics.drawString(font, Component.translatable("gui.weapons.guide.p9.github").getString(), left + 18, textY, 0xFFFFAA00, false);
        textY += 11;
        guiGraphics.drawString(font, "§9§n" + GITHUB_URL, left + 18, textY, 0xFF55FFFF, false);
    }

    /**
     * Hàm vẽ lưới Bàn Chế Tạo 3x3 và ô thành phẩm trực quan
     */
    private void drawCraftingGrid(GuiGraphics guiGraphics, int startX, int startY, ItemStack[][] grid, ItemStack output, int mouseX, int mouseY) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int slotX = startX + col * 19;
                int slotY = startY + row * 19;

                guiGraphics.fill(slotX, slotY, slotX + 18, slotY + 18, 0xFF373737);
                guiGraphics.fill(slotX + 1, slotY + 1, slotX + 17, slotY + 17, 0xFF8B8B8B);
                guiGraphics.fill(slotX + 1, slotY + 1, slotX + 16, slotY + 16, 0xFF313131);

                ItemStack item = grid[row][col];
                if (item != null && !item.isEmpty()) {
                    guiGraphics.renderItem(item, slotX + 1, slotY + 1);
                    if (mouseX >= slotX && mouseX <= slotX + 18 && mouseY >= slotY && mouseY <= slotY + 18) {
                        hoveredTooltipStack = item;
                    }
                }
            }
        }

        // Mũi tên chế tạo ->
        int arrowX = startX + 3 * 19 + 5;
        int arrowY = startY + 22;
        guiGraphics.drawString(font, "§6§l➔", arrowX, arrowY, 0xFFFFAA00, false);

        // Ô Output to hơn viền vàng
        int outX = arrowX + 16;
        int outY = startY + 16;
        guiGraphics.fill(outX, outY, outX + 26, outY + 26, 0xFFFFD700);
        guiGraphics.fill(outX + 1, outY + 1, outX + 25, outY + 25, 0xFF2A2A2A);

        if (output != null && !output.isEmpty()) {
            guiGraphics.renderItem(output, outX + 5, outY + 5);
            if (mouseX >= outX && mouseX <= outX + 26 && mouseY >= outY && mouseY <= outY + 26) {
                hoveredTooltipStack = output;
            }
        }
    }

    private void drawBorder(GuiGraphics guiGraphics, int x, int y, int width, int height, int color) {
        guiGraphics.fill(x, y, x + width, y + 1, color);
        guiGraphics.fill(x, y + height - 1, x + width, y + height, color);
        guiGraphics.fill(x, y + 1, x + 1, y + height, color);
        guiGraphics.fill(x + width - 1, y, x + width, y + height, color);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
