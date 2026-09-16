package com.minhphuc.weapons.content.infinitygauntlet;

import com.minhphuc.weapons.data.ItemStackDataHelper;
import com.minhphuc.weapons.init.ModItems;
import com.minhphuc.weapons.network.ModMessages;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Giao diện Vòng Xoay Ma Thuật Găng Tay Vô Cực (Page Up).
 * Thiết kế giao diện Obsidian & Hoàng Kim sắc nét 100%, độ tương phản cao,
 * chữ to rõ ràng, không bị tràn viền và không bị mờ nhòe.
 */
public class InfinityStoneSelectScreen extends Screen {

    private static final int PANEL_W = 440;
    private static final int PANEL_H = 230;
    private static final int ORBIT_RADIUS = 48;

    public static class StoneEntry {
        public final int mode;
        public final String vietName;
        public final String engName;
        public final String shortDesc;
        public final String fullDesc;
        public final int color;
        public final ItemStack stack;
        public int nodeX;
        public int nodeY;
        public int rowX;
        public int rowY;
        public int rowW;
        public int rowH;

        public StoneEntry(int mode, String vietName, String engName, String shortDesc, String fullDesc, int color, ItemStack stack) {
            this.mode = mode;
            this.vietName = vietName;
            this.engName = engName;
            this.shortDesc = shortDesc;
            this.fullDesc = fullDesc;
            this.color = color;
            this.stack = stack;
        }
    }

    private final List<StoneEntry> stones = new ArrayList<>();
    private final ItemStack gauntletStack = new ItemStack(ModItems.INFINITY_GAUNTLET.get());

    public InfinityStoneSelectScreen() {
        super(Component.literal("Vòng Xoay Đá Vô Cực"));
        initStoneData();
    }

    private void initStoneData() {
        stones.clear();

        // 1. Power Stone (Tím Hồng Rực Rỡ)
        stones.add(new StoneEntry(
                1,
                "§d§lĐá Sức Mạnh",
                "§e(Power)",
                "§fTia năng lượng tím quét sạch mục tiêu",
                "§dTia chùm hủy diệt cực đại, đẩy lùi & sát thương liên tục mọi sinh vật.",
                0xFFFF40FF,
                new ItemStack(ModItems.POWER_STONE.get())
        ));

        // 2. Space Stone (Lam Ngọc Phát Sáng - KHÔNG dùng xanh đậm §9 tránh chìm nền)
        stones.add(new StoneEntry(
                2,
                "§b§lĐá Không Gian",
                "§e(Space)",
                "§fDịch chuyển tức thời & hố đen hút địch",
                "§bNhấp chuột để dịch chuyển tức thời; hoặc tạo hố đen khổng lồ hút mọi thực thể.",
                0xFF00E5FF,
                new ItemStack(ModItems.SPACE_STONE.get())
        ));

        // 3. Reality Stone (Đỏ Tươi Rực Rỡ)
        stones.add(new StoneEntry(
                3,
                "§c§lĐá Thực Tại",
                "§e(Reality)",
                "§fBóp méo không gian, phân rã vật chất",
                "§cThay đổi cấu trúc thực tại xung quanh, biến đổi khối và phân rã mục tiêu.",
                0xFFFF3344,
                new ItemStack(ModItems.REALITY_STONE.get())
        ));

        // 4. Soul Stone (Cam Hổ Phách Rực Lửa)
        stones.add(new StoneEntry(
                4,
                "§6§lĐá Linh Hồn",
                "§e(Soul)",
                "§fHút sinh lực kẻ địch, hồi phục 100% HP",
                "§6Tróc nã linh hồn các sinh vật xung quanh, hồi phục toàn bộ lượng máu của chủ nhân.",
                0xFFFF9100,
                new ItemStack(ModItems.SOUL_STONE.get())
        ));

        // 5. Time Stone (Lục Bảo Phát Quang)
        stones.add(new StoneEntry(
                5,
                "§a§lĐá Thời Gian",
                "§e(Time)",
                "§fNgưng đọng thời gian, làm chậm 90% quái",
                "§aĐóng băng dòng chảy thời gian xung quanh, làm chậm 90% toàn bộ kẻ địch lân cận.",
                0xFF00E676,
                new ItemStack(ModItems.TIME_STONE.get())
        ));

        // 6. Mind Stone (Vàng Điện Tinh Quang)
        stones.add(new StoneEntry(
                6,
                "§e§lĐá Tâm Trí",
                "§e(Mind)",
                "§fSóng não tâm linh khiến quái hoảng loạn",
                "§ePhát tán sóng xung kích tâm linh cực mạnh, khiến mọi mục tiêu mất kiểm soát.",
                0xFFFFEA00,
                new ItemStack(ModItems.MIND_STONE.get())
        ));
    }

    @Override
    protected void init() {
        super.init();

        int panelLeft = (this.width - PANEL_W) / 2;
        int panelTop = (this.height - PANEL_H) / 2;

        int wheelCenterX = panelLeft + 105;
        int wheelCenterY = panelTop + 124;

        // Tọa độ 6 đỉnh lục giác quanh vòng tròn ma thuật
        for (int i = 0; i < stones.size(); i++) {
            double angle = Math.toRadians(-90 + i * 60);
            StoneEntry stone = stones.get(i);
            stone.nodeX = (int) (wheelCenterX + Math.cos(angle) * ORBIT_RADIUS);
            stone.nodeY = (int) (wheelCenterY + Math.sin(angle) * ORBIT_RADIUS);
        }

        // Tọa độ danh sách 6 viên đá bên phải
        int listLeft = panelLeft + 215;
        int listTop = panelTop + 29;
        int rowHeight = 24;

        for (int i = 0; i < stones.size(); i++) {
            StoneEntry stone = stones.get(i);
            stone.rowX = listLeft;
            stone.rowY = listTop + 14 + i * 26;
            stone.rowW = 215;
            stone.rowH = rowHeight;
        }
    }

    private int getCurrentlyEquippedMode() {
        if (this.minecraft != null && this.minecraft.player != null) {
            ItemStack main = this.minecraft.player.getMainHandItem();
            if (main.getItem() instanceof InfinityGauntletItem) {
                return ItemStackDataHelper.getInt(main, InfinityGauntletItem.NBT_MODE);
            }
            ItemStack off = this.minecraft.player.getOffhandItem();
            if (off.getItem() instanceof InfinityGauntletItem) {
                return ItemStackDataHelper.getInt(off, InfinityGauntletItem.NBT_MODE);
            }
        }
        return -1;
    }

    private void selectMode(int modeOrdinal) {
        if (this.minecraft != null && this.minecraft.player != null) {
            this.minecraft.player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0F, 1.2F);
        }
        ModMessages.sendToServer(new ServerboundSelectModePacket(modeOrdinal));
        this.onClose();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        // 1. Làm mờ nhẹ nền thế giới xung quanh (85% Black Obsidian)
        guiGraphics.fill(0, 0, this.width, this.height, 0xD8000000);

        int panelLeft = (this.width - PANEL_W) / 2;
        int panelTop = (this.height - PANEL_H) / 2;

        // 2. Khung Master Panel (Đậm đặc 100%, sắc nét tuyệt đối, viền Hoàng Kim)
        guiGraphics.fill(panelLeft - 3, panelTop - 3, panelLeft + PANEL_W + 3, panelTop + PANEL_H + 3, 0xFF050308); // Đổ bóng viền
        guiGraphics.fill(panelLeft - 1, panelTop - 1, panelLeft + PANEL_W + 1, panelTop + PANEL_H + 1, 0xFFDAA520); // Viền vàng kim
        guiGraphics.fill(panelLeft, panelTop, panelLeft + PANEL_W, panelTop + PANEL_H, 0xFF120E22); // Thân nền Obsidian

        // Thanh tiêu đề phía trên
        guiGraphics.fill(panelLeft, panelTop, panelLeft + PANEL_W, panelTop + 24, 0xFF24163B);
        guiGraphics.fill(panelLeft, panelTop + 24, panelLeft + PANEL_W, panelTop + 25, 0xFFFFD700);

        guiGraphics.drawString(font, "§6§l✦ VÒNG XOAY MA THUẬT GĂNG TAY VÔ CỰC ✦", panelLeft + 10, panelTop + 7, 0xFFFFAA00, true);

        // Nút Đóng [X] ở góc trên bên phải
        boolean hoverClose = (mouseX >= panelLeft + PANEL_W - 20 && mouseX <= panelLeft + PANEL_W - 4 &&
                mouseY >= panelTop + 4 && mouseY <= panelTop + 20);
        guiGraphics.drawString(font, hoverClose ? "§c§l[✕]" : "§7[✕]", panelLeft + PANEL_W - 20, panelTop + 7, 0xFFFFFFFF, true);

        int wheelCenterX = panelLeft + 105;
        int wheelCenterY = panelTop + 124;

        // 3. VẼ PHÁP TRẬN MA THUẬT (BÊN TRÁI)
        drawArcaneCircle(guiGraphics, wheelCenterX, wheelCenterY);

        int currentActiveMode = getCurrentlyEquippedMode();

        // Kiểm tra hover tâm điểm SNAP
        double distCenter = Math.hypot(mouseX - wheelCenterX, mouseY - wheelCenterY);
        boolean hoverCenter = distCenter <= 20;

        // Tâm điểm SNAP (Găng tay vô cực)
        int snapBox = 36;
        int snapX = wheelCenterX - snapBox / 2;
        int snapY = wheelCenterY - snapBox / 2;
        boolean isSnapActive = (currentActiveMode == 7);

        guiGraphics.fill(snapX, snapY, snapX + snapBox, snapY + snapBox,
                isSnapActive ? 0xFF5A3E14 : (hoverCenter ? 0xFF4A3418 : 0xFF281D0D));
        drawBorder(guiGraphics, snapX, snapY, snapBox, snapBox,
                (isSnapActive || hoverCenter) ? 0xFFFFD700 : 0xFFDAA520);

        guiGraphics.renderItem(gauntletStack, wheelCenterX - 8, wheelCenterY - 12);
        guiGraphics.drawCenteredString(font, hoverCenter ? "§e§lSNAP!" : "§6§lSNAP", wheelCenterX, wheelCenterY + 6, 0xFFFFAA00);

        // 4. VẼ 6 VỆ TINH ĐÁ TRÊN VÒNG TRÒN
        StoneEntry activeHoveredStone = null;

        for (StoneEntry stone : stones) {
            double distNode = Math.hypot(mouseX - stone.nodeX, mouseY - stone.nodeY);
            boolean hoverNode = distNode <= 14;
            boolean hoverRow = (mouseX >= stone.rowX && mouseX <= stone.rowX + stone.rowW &&
                    mouseY >= stone.rowY && mouseY <= stone.rowY + stone.rowH);

            boolean isHovered = hoverNode || hoverRow;
            if (isHovered) {
                activeHoveredStone = stone;
            }

            boolean isThisStoneActive = (currentActiveMode == stone.mode);

            // Khung vệ tinh trên vòng tròn
            int boxSize = isHovered ? 28 : 24;
            int bx = stone.nodeX - boxSize / 2;
            int by = stone.nodeY - boxSize / 2;

            guiGraphics.fill(bx, by, bx + boxSize, by + boxSize,
                    isThisStoneActive ? 0xFF4A366E : (isHovered ? 0xFF3D2C58 : 0xFF1D152C));

            int borderColor = isThisStoneActive ? 0xFFFFD700 : (isHovered ? stone.color : (0xCC000000 | (stone.color & 0x00FFFFFF)));
            drawBorder(guiGraphics, bx, by, boxSize, boxSize, borderColor);

            guiGraphics.renderItem(stone.stack, stone.nodeX - 8, stone.nodeY - 8);

            // Nối tia sáng năng lượng nếu đang hover
            if (isHovered) {
                drawDottedLine(guiGraphics, wheelCenterX, wheelCenterY, stone.nodeX, stone.nodeY, stone.color);
            }
        }

        // Chú thích dưới vòng tròn ma thuật
        guiGraphics.drawCenteredString(font, "§7[ Nhấp đá để chọn ]", wheelCenterX, panelTop + PANEL_H - 14, 0xFFAAAAAA);

        // 5. VẼ BẢNG DANH SÁCH SẮC NÉT (BÊN PHẢI)
        int listLeft = panelLeft + 215;
        int listTop = panelTop + 29;
        int listW = 215;

        // Tiêu đề nhỏ bên phải
        guiGraphics.drawString(font, "§e§l📜 CHỌN CHẾ ĐỘ SỨC MẠNH:", listLeft, listTop + 2, 0xFFFFDD55, true);

        // Vẽ từng hàng danh sách 6 viên đá
        for (StoneEntry stone : stones) {
            boolean isHovered = (stone == activeHoveredStone);
            boolean isThisStoneActive = (currentActiveMode == stone.mode);

            // Nền hàng
            int rowBg = isThisStoneActive ? 0xFF422F5E : (isHovered ? 0xFF36264C : 0xFF1B1428);
            guiGraphics.fill(stone.rowX, stone.rowY, stone.rowX + stone.rowW, stone.rowY + stone.rowH, rowBg);

            // Viền hàng
            int rowBorder = isThisStoneActive ? 0xFFFFD700 : (isHovered ? stone.color : 0xFF4A3866);
            drawBorder(guiGraphics, stone.rowX, stone.rowY, stone.rowW, stone.rowH, rowBorder);

            // Icon viên đá
            guiGraphics.renderItem(stone.stack, stone.rowX + 4, stone.rowY + 4);

            // Tên đá (Sắc nét, màu rực rỡ, dropShadow = true)
            guiGraphics.drawString(font, stone.vietName + " " + stone.engName, stone.rowX + 24, stone.rowY + 3, 0xFFFFFFFF, true);

            // Dòng mô tả ngắn (Vừa khít trong khung, chữ trắng rõ ràng)
            guiGraphics.drawString(font, stone.shortDesc, stone.rowX + 24, stone.rowY + 13, 0xFFEAEAEA, true);

            // Đánh dấu tích xanh nếu đang kích hoạt chế độ này
            if (isThisStoneActive) {
                guiGraphics.drawString(font, "§a✔", stone.rowX + stone.rowW - 13, stone.rowY + 3, 0xFF55FF55, true);
            }
        }

        // Hàng 7: SNAP TẤT SÁT BÚNG TAY ở dưới cùng danh sách
        int snapRowY = listTop + 14 + 6 * 26;
        int snapRowH = 24;
        boolean hoverSnapRow = (mouseX >= listLeft && mouseX <= listLeft + listW &&
                mouseY >= snapRowY && mouseY <= snapRowY + snapRowH);

        boolean isHoveredSnap = (hoverCenter || hoverSnapRow);

        guiGraphics.fill(listLeft, snapRowY, listLeft + listW, snapRowY + snapRowH,
                isSnapActive ? 0xFF5C4215 : (isHoveredSnap ? 0xFF4D3712 : 0xFF2C1F0A));
        drawBorder(guiGraphics, listLeft, snapRowY, listW, snapRowH,
                (isSnapActive || isHoveredSnap) ? 0xFFFFD700 : 0xFFC8961E);

        guiGraphics.renderItem(gauntletStack, listLeft + 4, snapRowY + 4);
        guiGraphics.drawString(font, "§6§l✦ TẤT SÁT BÚNG TAY (SNAP)", listLeft + 24, snapRowY + 3, 0xFFFFD700, true);
        guiGraphics.drawString(font, "§fXóa sổ 50% quái & lệnh Gemini AI", listLeft + 24, snapRowY + 13, 0xFFFFFFFF, true);

        if (isSnapActive) {
            guiGraphics.drawString(font, "§a✔", listLeft + listW - 13, snapRowY + 3, 0xFF55FF55, true);
        }

        // 6. TOOLTIP CHI TIẾT KHI HOVER
        if (isHoveredSnap) {
            List<Component> snapTooltip = List.of(
                    Component.literal("§6§l✦ TẤT SÁT BÚNG TAY VÔ CỰC (SNAP) ✦"),
                    Component.literal("§eHợp nhất sức mạnh tối thượng của cả 6 Viên Đá Vô Cực!"),
                    Component.literal("§7• Xóa sổ 50% sinh vật thù địch trong bán kính"),
                    Component.literal("§7• Thức tỉnh trí tuệ Gemini AI để ban hành ý chí"),
                    Component.literal("§a👉 Nhấp chuột để kích hoạt chế độ này!")
            );
            guiGraphics.renderComponentTooltip(font, snapTooltip, mouseX, mouseY);
        } else if (activeHoveredStone != null) {
            List<Component> stoneTooltip = List.of(
                    Component.literal(activeHoveredStone.vietName + " " + activeHoveredStone.engName),
                    Component.literal(activeHoveredStone.fullDesc),
                    Component.literal("§7• Nhấp Chuột Phải khi cầm Găng Tay để thi triển"),
                    Component.literal("§a👉 Nhấp chuột để kích hoạt chế độ này!")
            );
            guiGraphics.renderComponentTooltip(font, stoneTooltip, mouseX, mouseY);
        }
    }

    private void drawArcaneCircle(GuiGraphics guiGraphics, int cx, int cy) {
        // Vẽ 2 vòng tròn ma thuật đồng tâm
        int segments = 48;
        for (int i = 0; i < segments; i++) {
            double angle = (2 * Math.PI / segments) * i;

            // Vòng ngoài
            int px = (int) (cx + Math.cos(angle) * ORBIT_RADIUS);
            int py = (int) (cy + Math.sin(angle) * ORBIT_RADIUS);
            guiGraphics.fill(px, py, px + 1, py + 1, (i % 4 == 0) ? 0xFFFFD700 : 0x6600E5FF);

            // Vòng trong
            int px2 = (int) (cx + Math.cos(angle) * (ORBIT_RADIUS - 14));
            int py2 = (int) (cy + Math.sin(angle) * (ORBIT_RADIUS - 14));
            guiGraphics.fill(px2, py2, px2 + 1, py2 + 1, (i % 6 == 0) ? 0xFFFF9100 : 0x448888AA);
        }

        // Vẽ các đường liên kết Lục Giác Sao 6 Cánh (Hexagram)
        if (stones.size() >= 6) {
            // Tam giác 1: Đỉnh 0, 2, 4
            drawDottedLine(guiGraphics, stones.get(0).nodeX, stones.get(0).nodeY, stones.get(2).nodeX, stones.get(2).nodeY, 0x44FFD700);
            drawDottedLine(guiGraphics, stones.get(2).nodeX, stones.get(2).nodeY, stones.get(4).nodeX, stones.get(4).nodeY, 0x44FFD700);
            drawDottedLine(guiGraphics, stones.get(4).nodeX, stones.get(4).nodeY, stones.get(0).nodeX, stones.get(0).nodeY, 0x44FFD700);

            // Tam giác 2: Đỉnh 1, 3, 5
            drawDottedLine(guiGraphics, stones.get(1).nodeX, stones.get(1).nodeY, stones.get(3).nodeX, stones.get(3).nodeY, 0x44FFD700);
            drawDottedLine(guiGraphics, stones.get(3).nodeX, stones.get(3).nodeY, stones.get(5).nodeX, stones.get(5).nodeY, 0x44FFD700);
            drawDottedLine(guiGraphics, stones.get(5).nodeX, stones.get(5).nodeY, stones.get(1).nodeX, stones.get(1).nodeY, 0x44FFD700);
        }
    }

    private void drawDottedLine(GuiGraphics guiGraphics, int x1, int y1, int x2, int y2, int color) {
        int steps = 14;
        for (int i = 1; i < steps; i++) {
            int x = x1 + (x2 - x1) * i / steps;
            int y = y1 + (y2 - y1) * i / steps;
            guiGraphics.fill(x, y, x + 1, y + 1, color);
        }
    }

    private void drawBorder(GuiGraphics guiGraphics, int x, int y, int width, int height, int color) {
        guiGraphics.fill(x, y, x + width, y + 1, color);
        guiGraphics.fill(x, y + height - 1, x + width, y + height, color);
        guiGraphics.fill(x, y, x + 1, y + height, color);
        guiGraphics.fill(x + width - 1, y, x + width, y + height, color);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) { // Chuột trái
            int panelLeft = (this.width - PANEL_W) / 2;
            int panelTop = (this.height - PANEL_H) / 2;

            // Nút Đóng [X]
            if (mouseX >= panelLeft + PANEL_W - 20 && mouseX <= panelLeft + PANEL_W - 4 &&
                    mouseY >= panelTop + 4 && mouseY <= panelTop + 20) {
                this.onClose();
                return true;
            }

            int wheelCenterX = panelLeft + 105;
            int wheelCenterY = panelTop + 124;

            // 1. Click tâm SNAP
            if (Math.hypot(mouseX - wheelCenterX, mouseY - wheelCenterY) <= 20) {
                selectMode(7);
                return true;
            }

            // Click hàng SNAP trong bảng danh sách
            int listLeft = panelLeft + 215;
            int listTop = panelTop + 29;
            int snapRowY = listTop + 14 + 6 * 26;
            if (mouseX >= listLeft && mouseX <= listLeft + 215 && mouseY >= snapRowY && mouseY <= snapRowY + 24) {
                selectMode(7);
                return true;
            }

            // 2. Click từng viên đá (Node trên vòng tròn hoặc Hàng trong danh sách)
            for (StoneEntry stone : stones) {
                boolean clickNode = Math.hypot(mouseX - stone.nodeX, mouseY - stone.nodeY) <= 14;
                boolean clickRow = (mouseX >= stone.rowX && mouseX <= stone.rowX + stone.rowW &&
                        mouseY >= stone.rowY && mouseY <= stone.rowY + stone.rowH);

                if (clickNode || clickRow) {
                    selectMode(stone.mode);
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
