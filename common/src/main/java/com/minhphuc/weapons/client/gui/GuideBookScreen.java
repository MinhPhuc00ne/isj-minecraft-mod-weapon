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
    private static final int TOTAL_PAGES = 8;

    private static final net.minecraft.resources.ResourceLocation DRAGON_NOVA_TEXTURE =
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("weapons", "textures/gui/dragon_nova_art.png");
    private static final net.minecraft.resources.ResourceLocation TAISUI_ART_TEXTURE =
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("weapons", "textures/gui/taisui_art.png");

    // Kích thước khung sách rộng rãi 380px, căn chỉnh chuẩn xác không bao giờ tràn chữ
    private static final int BOOK_WIDTH = 380;
    private static final int BOOK_HEIGHT = 228;

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
        super(Component.literal("Ma Đạo Thư"));
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
        prevButton = Button.builder(Component.literal("◀ Trang Trước"), b -> {
            if (currentPage > 0) currentPage--;
            updateButtonState();
        }).bounds(left + 16, top + BOOK_HEIGHT - 26, 95, 20).build();

        // Nút trang sau
        nextButton = Button.builder(Component.literal("Trang Sau ▶"), b -> {
            if (currentPage < TOTAL_PAGES - 1) currentPage++;
            updateButtonState();
        }).bounds(left + BOOK_WIDTH - 111, top + BOOK_HEIGHT - 26, 95, 20).build();

        // Nút Đóng
        closeButton = Button.builder(Component.literal("§c✕ Đóng"), b -> {
            this.onClose();
        }).bounds(left + BOOK_WIDTH / 2 - 30, top + BOOK_HEIGHT - 26, 60, 20).build();

        this.addRenderableWidget(prevButton);
        this.addRenderableWidget(nextButton);
        this.addRenderableWidget(closeButton);

        // --- Nút trang 4 (Găng Tay) ---
        int gauntletBtnY = top + 80;
        gauntletEmptyBtn = Button.builder(Component.literal("1. Găng Trống"), b -> selectedGauntletTab = 0)
                .bounds(left + 130, gauntletBtnY, 100, 18).build();
        gauntletLoadedBtn = Button.builder(Component.literal("2. Khảm 6 Đá"), b -> selectedGauntletTab = 1)
                .bounds(left + 238, gauntletBtnY, 100, 18).build();

        this.addRenderableWidget(gauntletEmptyBtn);
        this.addRenderableWidget(gauntletLoadedBtn);

        // --- Nút trang 5 (4 Món Giáp) ---
        int armorBtnY = top + 80;
        armorHelmBtn = Button.builder(Component.literal("Mũ"), b -> selectedArmorPiece = 0)
                .bounds(left + 130, armorBtnY, 46, 18).build();
        armorChestBtn = Button.builder(Component.literal("Áo"), b -> selectedArmorPiece = 1)
                .bounds(left + 182, armorBtnY, 46, 18).build();
        armorLegsBtn = Button.builder(Component.literal("Quần"), b -> selectedArmorPiece = 2)
                .bounds(left + 234, armorBtnY, 46, 18).build();
        armorBootsBtn = Button.builder(Component.literal("Giày"), b -> selectedArmorPiece = 3)
                .bounds(left + 286, armorBtnY, 46, 18).build();

        this.addRenderableWidget(armorHelmBtn);
        this.addRenderableWidget(armorChestBtn);
        this.addRenderableWidget(armorLegsBtn);
        this.addRenderableWidget(armorBootsBtn);

        // --- Nút trang 6 (Tác Giả & GitHub) ---
        int authorBtnY = top + BOOK_HEIGHT - 54;
        openGithubBtn = Button.builder(Component.literal("§6🔗 Mở Link GitHub"), b -> {
            try {
                Util.getPlatform().openUri(URI.create(GITHUB_URL));
            } catch (Exception ignored) {}
        }).bounds(left + 50, authorBtnY, 130, 20).build();

        copyGithubBtn = Button.builder(Component.literal("§a📋 Sao Chép Link"), b -> {
            if (this.minecraft != null) {
                this.minecraft.keyboardHandler.setClipboard(GITHUB_URL);
                if (this.minecraft.player != null) {
                    this.minecraft.player.displayClientMessage(
                            Component.literal("§a✔ Đã sao chép link GitHub của MinhPhuc00ne / Jos vào Clipboard!"),
                            true
                    );
                }
            }
        }).bounds(left + 200, authorBtnY, 130, 20).build();

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

        boolean isAuthorPage = (currentPage == 7);
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
        String pageStr = "Trang " + (currentPage + 1) + " / " + TOTAL_PAGES;
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
            case 7 -> renderPageAuthor(guiGraphics, left, top, mouseX, mouseY);
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
        guiGraphics.drawString(font, "§6§l📜 MA ĐẠO THƯ", left + 14, top + 11, 0xFFFFAA00, false);

        int textY = top + 36;
        guiGraphics.drawString(font, "§eHI!", left + 14, textY, 0xFFFFFFFF, false);

        textY += 16;
        guiGraphics.drawString(font, "§fMa Đạo thư này lưu truyền tri thức cổ xưa về cách chế tác,", left + 14, textY, 0xFFDDDDDD, false);
        textY += 12;
        guiGraphics.drawString(font, "§fthức tỉnh và làm chủ những bảo vật tối thượng.", left + 14, textY, 0xFFDDDDDD, false);

        textY += 18;
        guiGraphics.drawString(font, "§b⚡ Thao Tác Kích Hoạt Kỹ Năng:", left + 14, textY, 0xFF55FFFF, false);

        textY += 14;
        guiGraphics.drawString(font, " • §ePhím [Z]: §fChuyển đổi qua lại thứ tự các chiêu thức", left + 18, textY, 0xFFFFFFFF, false);
        textY += 13;
        guiGraphics.drawString(font, " • §a[Chuột Phải]: §fKích hoạt thi triển kỹ năng đã chọn", left + 18, textY, 0xFFFFFFFF, false);
        textY += 13;
        guiGraphics.drawString(font, " • §bPhím [Page Up]: §fChọn Đá Vô Cực", left + 18, textY, 0xFFFFFFFF, false);
        textY += 11;
        guiGraphics.drawString(font, "   §fđể gắn hoặc chọn sức mạnh Đá Vô Cực.", left + 18, textY, 0xFFFFFFFF, false);

        textY += 20;
        guiGraphics.drawString(font, "§8👉 Bấm [Trang Sau ▶] để xem chi tiết công thức từng trang bị.", left + 14, textY, 0xFF888888, false);
    }

    /**
     * TRANG 2: NGUYỆT QUANG THẦN TẾ KIẾM
     */
    private void renderPageMoonlightSword(GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY) {
        guiGraphics.drawString(font, "§6§l⚔️ NGUYỆT QUANG THẦN TẾ KIẾM", left + 14, top + 11, 0xFFFFAA00, false);

        int gridX = left + 16;
        int gridY = top + 34;

        drawCraftingGrid(guiGraphics, gridX, gridY, moonlightSwordRecipe, new ItemStack(ModItems.MOONLIGHT_SWORD.get()), mouseX, mouseY);

        int descX = left + 134;
        int descY = top + 34;
        guiGraphics.drawString(font, "§6§lNguyệt Quang Thần Tế Kiếm", descX, descY, 0xFFFFAA00, false);
        descY += 14;
        guiGraphics.drawString(font, "§7Bảo kiếm hộ vệ thánh điện.", descX, descY, 0xFFDDDDDD, false);
        descY += 12;
        guiGraphics.drawString(font, "§aĐộ bền vĩnh cửu.", descX, descY, 0xFF55FF55, false);

        int skillY = top + 104;
        guiGraphics.drawString(font, "§e⚡ Kỹ Năng Tối Thượng (Phím [Z]):", left + 14, skillY, 0xFFFFDD00, false);

        skillY += 13;
        guiGraphics.drawString(font, " 1. §4Trảm Kích Hắc Thiểm Bá Vương", left + 18, skillY, 0xFFFFFFFF, false);
        skillY += 11;
        guiGraphics.drawString(font, " 2. §eTam Trọng Thánh Giới (Linh Tử Băng Hoại)", left + 18, skillY, 0xFFFFFFFF, false);
        skillY += 11;
        guiGraphics.drawString(font, " 3. §bNấc Thang Jacob (Tà Khứ Vũ Thê Tử)", left + 18, skillY, 0xFFFFFFFF, false);
        skillY += 11;
        guiGraphics.drawString(font, " 4. §dBạo Thực Vương Beelzebuth §7(Chân Ma Vương)", left + 18, skillY, 0xFFFFFFFF, false);
        skillY += 11;
        guiGraphics.drawString(font, " 5. §5Long Tinh Bộc Viêm Bá: §d[Mặc Đủ Giáp + Ma Vương]", left + 18, skillY, 0xFFFFAA00, false);
    }

    /**
     * TRANG 3: CON ĐƯỜNG THỨC TỈNH CHÂN MA VƯƠNG
     */
    private void renderPageDemonLord(GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY) {
        guiGraphics.drawString(font, "§d§l👑 THỨC TỈNH CHÂN MA VƯƠNG", left + 14, top + 11, 0xFFFF55FF, false);

        int gridX = left + 16;
        int gridY = top + 34;

        drawCraftingGrid(guiGraphics, gridX, gridY, demonLordSeedRecipe, new ItemStack(ModItems.DEMON_LORD_SEED.get()), mouseX, mouseY);

        int descX = left + 134;
        int descY = top + 34;
        guiGraphics.drawString(font, "§d§lHạt Giống Ma Vương", descX, descY, 0xFFFF55FF, false);
        descY += 13;
        guiGraphics.drawString(font, "§a⭐ Cách kiếm trong Survival:", descX, descY, 0xFF55FF55, false);
        descY += 11;
        guiGraphics.drawString(font, "§fCấp §e10+ EXP§f, đánh quái thường", descX, descY, 0xFFFFFFFF, false);
        descY += 10;
        guiGraphics.drawString(font, "§fđể gom góp §d10.000 Linh Hồn§f.", descX, descY, 0xFFFFFFFF, false);
        descY += 12;
        guiGraphics.drawString(font, "§c[Chuột Phải] §fđể bắt đầu", descX, descY, 0xFFFFAA00, false);
        descY += 10;
        guiGraphics.drawString(font, "§cLễ Hội Thu Hoạch (Harvest Festival).", descX, descY, 0xFFFFAA00, false);

        int awakenY = top + 104;
        guiGraphics.drawString(font, "§d⚡ Đặc Quyền Chân Ma Vương:", left + 14, awakenY, 0xFFFF55FF, false);

        awakenY += 13;
        guiGraphics.drawString(font, " • §eMáu Tối Đa: §aTăng vọt lên 40 Tim (80 HP)", left + 18, awakenY, 0xFFFFFFFF, false);
        awakenY += 11;
        guiGraphics.drawString(font, " • §bTốc Độ & Kháng Cự: §fSpeed I, Resistance I vĩnh cửu", left + 18, awakenY, 0xFFFFFFFF, false);
        awakenY += 11;
        guiGraphics.drawString(font, " • §5Bí Kíp Tay Không (Phím [Z]): §fThôn Phệ & Hủ Hóa", left + 18, awakenY, 0xFFFFFFFF, false);
        awakenY += 11;
        guiGraphics.drawString(font, " • §cKhi Mặc Đủ Giáp Thần Linh: §dKhai mở Dragon Nova!", left + 18, awakenY, 0xFFFF55FF, false);
    }

    /**
     * TRANG 4: GĂNG TAY VÔ CỰC & 6 VIÊN ĐÁ
     */
    private void renderPageInfinityGauntlet(GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY) {
        guiGraphics.drawString(font, "§e§l💎 GĂNG TAY VÔ CỰC & 6 ĐÁ", left + 14, top + 11, 0xFFFFFF55, false);

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
            guiGraphics.drawString(font, "§e§lGăng Tay Vô Cực Trống", descX, descY, 0xFFFFFF55, false);
            descY += 12;
            guiGraphics.drawString(font, "§7Găng tay vàng rèn từ Netherite.", descX, descY, 0xFFDDDDDD, false);
            descY += 11;
            guiGraphics.drawString(font, "§aCầm tay phụ để chống phản phệ.", descX, descY, 0xFF55FF55, false);
            descY += 11;
            guiGraphics.drawString(font, "§7Bấm nút bên dưới để đổi xem:", descX, descY, 0xFF888888, false);
        } else {
            guiGraphics.drawString(font, "§6§lGăng Tay Đầy Đủ 6 Đá", descX, descY, 0xFFFFAA00, false);
            descY += 12;
            guiGraphics.drawString(font, "§aHợp nhất 6 Viên Đá Vô Cực.", descX, descY, 0xFF55FF55, false);
            descY += 11;
            guiGraphics.drawString(font, "§bPhím [Page Up] chọn chiêu thức.", descX, descY, 0xFF55FFFF, false);
            descY += 11;
            guiGraphics.drawString(font, "§eCú Búng Tay (Snap) xóa sổ quái!", descX, descY, 0xFFFFFF55, false);
        }

        int bossY = top + 104;
        guiGraphics.drawString(font, "§6⚡ Săn 6 Đá Vanilla & Cầm Riêng Lẻ 20% Lực (Không Lag):", left + 14, bossY, 0xFFFFAA00, false);

        bossY += 13;
        guiGraphics.drawString(font, " 🔮 §dPower: §fHạ Wither | §cReality: §fHạ Piglin Brute", left + 18, bossY, 0xFFFFFFFF, false);
        bossY += 11;
        guiGraphics.drawString(font, " 🌌 §9Space: §fHạ Rồng Ender | ⌛ §aTime: §fHạ Elder Guardian", left + 18, bossY, 0xFFFFFFFF, false);
        bossY += 11;
        guiGraphics.drawString(font, " 🔥 §6Soul: §fHạ Warden | 🧠 §eMind: §fHạ Evoker Mansion", left + 18, bossY, 0xFFFFFFFF, false);
        bossY += 11;
        guiGraphics.drawString(font, " §c⚠️ Cảnh Báo: §fCầm đá tay không bị phản phệ (cần Găng Trống / Giáp)!", left + 18, bossY, 0xFFFF5555, false);
    }

    /**
     * TRANG 5: THẦN LINH VŨ TRANG (DIVINE ARMOR)
     */
    private void renderPageDivineArmor(GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY) {
        guiGraphics.drawString(font, "§6§l🛡️ THẦN LINH VŨ TRANG (DIVINE)", left + 14, top + 11, 0xFFFFAA00, false);

        int gridX = left + 16;
        int gridY = top + 34;

        ItemStack outputArmor = switch (selectedArmorPiece) {
            case 0 -> new ItemStack(ModItems.DIVINE_HELMET.get());
            case 1 -> new ItemStack(ModItems.DIVINE_CHESTPLATE.get());
            case 2 -> new ItemStack(ModItems.DIVINE_LEGGINGS.get());
            default -> new ItemStack(ModItems.DIVINE_BOOTS.get());
        };

        String armorPieceName = switch (selectedArmorPiece) {
            case 0 -> "Mũ Thần Linh Vũ Trang";
            case 1 -> "Áo Giáp Thần Linh Vũ Trang";
            case 2 -> "Quần Thần Linh Vũ Trang";
            default -> "Giày Thần Linh Vũ Trang";
        };

        drawCraftingGrid(guiGraphics, gridX, gridY, divineArmorRecipes[selectedArmorPiece], outputArmor, mouseX, mouseY);

        int descX = left + 134;
        int descY = top + 34;
        guiGraphics.drawString(font, "§e§l" + armorPieceName, descX, descY, 0xFFFFFF55, false);
        descY += 12;
        guiGraphics.drawString(font, "§7Nâng cấp từ Giáp Netherite tương ứng.", descX, descY, 0xFFFFFFFF, false);
        descY += 11;
        guiGraphics.drawString(font, "§aĐộ bền bất tử, không thể bị vỡ.", descX, descY, 0xFF55FF55, false);
        descY += 11;
        guiGraphics.drawString(font, "§7Bấm nút bên dưới để xem từng món:", descX, descY, 0xFF888888, false);

        int effY = top + 104;
        guiGraphics.drawString(font, "§6⚡ Đặc Tính Kháng Thần Thánh (Khi Mặc Đủ Cả 4 Món):", left + 14, effY, 0xFFFFAA00, false);

        effY += 13;
        guiGraphics.drawString(font, " §a✔ Miễn nhiễm 100% mọi sát thương: §fCận chiến, tên, lửa, nổ...", left + 18, effY, 0xFFFFFFFF, false);
        effY += 12;
        guiGraphics.drawString(font, " §a✔ Miễn nhiễm đòn đánh Boss: §fSonic Boom, Wither Skull...", left + 18, effY, 0xFFFFFFFF, false);
        effY += 12;
        guiGraphics.drawString(font, " §d★ Hợp Nhất Chân Ma Vương: §fKhai mở §5§lDragon Nova §f(Phím [Z])", left + 18, effY, 0xFFFF55FF, false);
        effY += 12;
        guiGraphics.drawString(font, " §c✘ 3 Điểm yếu: §fSát thương Độc (Poison), Đuối Nước và Rơi Hư Vô.", left + 18, effY, 0xFFFFFFFF, false);
    }

    /**
     * TRANG 6: CẤM THUẬT DIỆT RỒNG - LONG TINH BỘC VIÊM BÁ (DRAGON NOVA)
     */
    private void renderPageDragonNova(GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY) {
        guiGraphics.drawString(font, "§5§l🐲 CẤM THUẬT DIỆT RỒNG: DRAGO-NOVA", left + 14, top + 11, 0xFFFF55FF, false);

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
        guiGraphics.drawString(font, "§5§lCấm Thuật Diệt Rồng Cổ Đại", descX, descY, 0xFFFF55FF, true);
        descY += 12;
        guiGraphics.drawString(font, "§dLong Tinh Bộc Viêm Bá §e(Drago-Nova)", descX, descY, 0xFFFF88FF, true);
        descY += 12;
        guiGraphics.drawString(font, "§7Tuyệt kỹ diệt thế.", descX, descY, 0xFFDDDDDD, true);
        descY += 11;
        guiGraphics.drawString(font, "§7Từng dùng để §cthảm sát và thanh tẩy", descX, descY, 0xFFDDDDDD, true);
        descY += 11;
        guiGraphics.drawString(font, "§cTinh Linh Long§7 hóa điên.", descX, descY, 0xFFFF6666, true);
        descY += 11;
        guiGraphics.drawString(font, "§aBốc hơi vạn vật thành bình địa tro bụi.", descX, descY, 0xFF55FF55, true);

        int effY = top + 128;
        guiGraphics.drawString(font, "§6⚡ Uy Lực Hủy Diệt & Phá Vỡ Địa Hình:", left + 14, effY, 0xFFFFAA00, true);

        effY += 12;
        guiGraphics.drawString(font, " • §bLinh Tử Hội Tụ: §fHút sạch linh tử tinh khiết trong không gian.", left + 18, effY, 0xFFFFFFFF, true);
        effY += 11;
        guiGraphics.drawString(font, " • §cPhá Hủy Block: §fChùm tia xuyên thấu, san phẳng địa hình.", left + 18, effY, 0xFFFFFFFF, true);
        effY += 11;
        guiGraphics.drawString(font, " • §dSiêu Tân Tinh: §fTạo hố bom, rút §c88% Máu Đại Boss§f!", left + 18, effY, 0xFFFFFFFF, true);
        effY += 11;
        guiGraphics.drawString(font, " • §eĐiều Kiện: §fThức Tỉnh Chân Ma Vương + Mặc đủ 4 Món Giáp Thần Linh.", left + 18, effY, 0xFFFFFF55, true);
    }

    /**
     * TRANG 7: THÁI TUẾ TINH QUÂN - DARK GATHERING
     */
    private void renderPageTaisui(GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY) {
        guiGraphics.drawString(font, "§e§l👁️ Taisai Seikun", left + 14, top + 11, 0xFFFFCC00, true);

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
        guiGraphics.drawString(font, "§e§lThái Tuế Tinh Quân §6(泰歳星君)", descX, descY, 0xFFFFD700, true);
        descY += 12;
        guiGraphics.drawString(font, "§7Vị Thần Thống Trị Các Vì Tinh Tú §bDark Gathering§7.", descX, descY, 0xFFDDDDDD, true);
        descY += 11;
        guiGraphics.drawString(font, "§d• Tuyệt Diệt Tinh Tú (Extinction Stars):", descX, descY, 0xFFFF88FF, true);
        descY += 10;
        guiGraphics.drawString(font, " §f[Chuột Trái]: Bắn 1-Hit Kill & phá block!", descX, descY, 0xFFFFFFFF, true);
        descY += 11;
        guiGraphics.drawString(font, "§b• Trận Đồ Cưỡng Chế Tai Ương:", descX, descY, 0xFF55FFFF, true);
        descY += 10;
        guiGraphics.drawString(font, " §f12 Thập Nhị Thần Tướng", descX, descY, 0xFFFFFFFF, true);

        int effY = top + 128;
        guiGraphics.drawString(font, "§6⚡ Quy Tắc Vận Hành & Khắc Chế:", left + 14, effY, 0xFFFFAA00, true);

        effY += 12;
        guiGraphics.drawString(font, " • §eTuyệt Diệt Tinh Tú: §fBắn hết 5 sao sẽ tự động kết thúc chiêu.", left + 18, effY, 0xFFFFFFFF, true);
        effY += 11;
        guiGraphics.drawString(font, " • §aLớp Phòng Ngự: §fKháng 100% sát thương.", left + 18, effY, 0xFFFFFFFF, true);
        effY += 11;
        guiGraphics.drawString(font, " • §bThoát Kết Giới: §fNhấn Cách 2 lần để bay lên hoặc nhấp Chuột Phải.", left + 18, effY, 0xFFFFFFFF, true);
        effY += 11;
        guiGraphics.drawString(font, " • §cĐiều Kiện: §fThức Tỉnh Chân Ma Vương (chuyển đổi qua phím [Z]).", left + 18, effY, 0xFFFFFF55, true);
    }

    /**
     * TRANG 8: TÁC GIẢ & BẢN QUYỀN DỰ ÁN
     */
    private void renderPageAuthor(GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY) {
        guiGraphics.drawString(font, "§6§l👑 TÁC GIẢ & THÔNG TIN DỰ ÁN", left + 14, top + 11, 0xFFFFAA00, false);

        int textY = top + 34;
        guiGraphics.drawString(font, "§e⚡ Tác Giả Sáng Lập: §f§lMinhPhuc00ne / Jos", left + 18, textY, 0xFFFFAA00, false);

        textY += 13;
        guiGraphics.drawString(font, "§b🏢 Đơn Vị Phát Triển: §f§lIcey Studio", left + 18, textY, 0xFF55FFFF, false);

        textY += 13;
        guiGraphics.drawString(font, "§6🎓 Học Viện / Trường: §f§lFPT University", left + 18, textY, 0xFFFFAA00, false);

        textY += 13;
        guiGraphics.drawString(font, "§a🌟 Tên Bản Mod: §fWeapons Mod - Divine Armament & Infinity", left + 18, textY, 0xFF55FF55, false);

        textY += 13;
        guiGraphics.drawString(font, "§d📜 Bản Quyền: §fMIT License (Open Source)", left + 18, textY, 0xFFFF55FF, false);

        textY += 15;
        // Khung trích dẫn hoành tráng
        int quoteBoxW = BOOK_WIDTH - 36;
        guiGraphics.fill(left + 18, textY - 2, left + 18 + quoteBoxW, textY + 38, 0xFF231B30);
        drawBorder(guiGraphics, left + 18, textY - 2, quoteBoxW, 40, 0xFFD4AF37);

        guiGraphics.drawString(font, "§e\"Được kiến tạo từ niềm đam mê và sở thích của tác giả.", left + 24, textY + 3, 0xFFFFFF55, false);
        guiGraphics.drawString(font, "§eHy vọng bạn sẽ tìm thấy niềm vui trong từng trận chiến.", left + 24, textY + 14, 0xFFFFFF55, false);
        guiGraphics.drawString(font, "§eChúc bạn có một trải nghiệm thật bùng nổ và tuyệt vời!\"", left + 24, textY + 25, 0xFFFFFF55, false);

        textY += 44;
        guiGraphics.drawString(font, "§6🔗 Mã Nguồn Dự Án (GitHub):", left + 18, textY, 0xFFFFAA00, false);
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
