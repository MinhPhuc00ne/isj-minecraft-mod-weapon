package com.minhphuc.weapons.client.gui;

import com.minhphuc.weapons.entity.tensura.DemonType;
import com.minhphuc.weapons.network.ModMessages;
import com.minhphuc.weapons.network.ServerboundCapsuleEvolvePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;

public class IncubationCapsuleScreen extends Screen {

    private static final int PANEL_W = 380;
    private static final int PANEL_H = 220;

    private final BlockPos pos;
    private final DemonType demonType;
    private EditBox nameInput;

    public IncubationCapsuleScreen(BlockPos pos, DemonType demonType) {
        super(Component.literal("Bồn Chứa Thể Xác Nhân Tạo"));
        this.pos = pos;
        this.demonType = demonType;
    }

    @Override
    protected void init() {
        super.init();
        int left = (this.width - PANEL_W) / 2;
        int top = (this.height - PANEL_H) / 2;

        String defaultName = demonType.getRandomCanonName(RandomSource.create());
        this.nameInput = new EditBox(this.font, left + 130, top + 52, 160, 18, Component.literal("Tên Ác Ma"));
        this.nameInput.setValue(defaultName);
        this.nameInput.setMaxLength(24);
        this.addWidget(this.nameInput);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        int left = (this.width - PANEL_W) / 2;
        int top = (this.height - PANEL_H) / 2;

        // 1. Khung nền Dark Sci-fi Magisteel
        guiGraphics.fill(left - 3, top - 3, left + PANEL_W + 3, top + PANEL_H + 3, 0xFF081216);
        guiGraphics.fill(left - 1, top - 1, left + PANEL_W + 1, top + PANEL_H + 1, 0xFF00E5FF);
        guiGraphics.fill(left, top, left + PANEL_W, top + PANEL_H, 0xEE060D10);

        // Header Title
        guiGraphics.drawCenteredString(this.font, "§b§l✦ BỒN CHỨA THỂ XÁC NHÂN TẠO • TIẾN HÓA THẦN MA ✦", left + PANEL_W / 2, top + 8, 0xFFFFFF);
        guiGraphics.drawCenteredString(this.font, "§7Ác Ma Đang Nạp: §c§l" + demonType.getColorName().toUpperCase() + " §7(" + demonType.getTitleVi() + ")", left + PANEL_W / 2, top + 22, 0xCCCCCC);

        // Phân cách
        guiGraphics.fill(left + 15, top + 36, left + PANEL_W - 15, top + 37, 0x4400E5FF);

        // Ô nhập tên
        guiGraphics.drawString(this.font, "§eDanh Xưng (Tên):", left + 20, top + 56, 0xFFFFFF);
        this.nameInput.render(guiGraphics, mouseX, mouseY, partialTick);

        // Nút Random Tên
        int diceX = left + 296;
        int diceY = top + 52;
        int diceW = 64;
        int diceH = 18;
        boolean diceHover = mouseX >= diceX && mouseX <= diceX + diceW && mouseY >= diceY && mouseY <= diceY + diceH;
        guiGraphics.fill(diceX, diceY, diceX + diceW, diceY + diceH, diceHover ? 0xFF008899 : 0xFF004455);
        guiGraphics.renderOutline(diceX, diceY, diceW, diceH, diceHover ? 0xFF00FFFF : 0xFF0088AA);
        guiGraphics.drawCenteredString(this.font, "§bRandom", diceX + diceW / 2, diceY + 5, 0xFFFFFF);

        // 3 Tùy chọn Tiến Hóa (Cards)
        int cardX = left + 18;
        int cardW = PANEL_W - 36;
        int cardH = 36;
        int startY = top + 80;

        // Card 1: Thể Xác Vật Lý
        renderOptionCard(guiGraphics, cardX, startY, cardW, cardH,
                "§a§l1. CUNG CẤP THỂ XÁC VẬT LÝ",
                "§fChỉ số HP & Sát Thương §aTĂNG GẤP 5 LẦN§f | Tỷ lệ diệt Boss §a+5%§f | 3 Kỹ năng ngẫu nhiên",
                0xFF103020, 0xFF00FF66, mouseX, mouseY);

        // Card 2: Ban Tặng Danh Xưng
        renderOptionCard(guiGraphics, cardX, startY + 42, cardW, cardH,
                "§d§l2. BAN TẶNG DANH XƯNG (TÊN GỌI)",
                "§fChỉ số HP & Sát Thương §dTĂNG GẤP 5 LẦN§f | Tỷ lệ diệt Boss §d+5%§f | Nhận tên đã nhập",
                0xFF301535, 0xFFE040FB, mouseX, mouseY);

        // Card 3: Cả Thể Xác & Danh Xưng (Hoàn Mỹ)
        renderOptionCard(guiGraphics, cardX, startY + 84, cardW, cardH,
                "§6§l3. HOÀN HẢO: THỂ XÁC VẬT LÝ & BAN DANH XƯNG",
                "§eSỨC MẠNH GẤP 8 LẦN §7(Gấp 7 lần đơn lẻ) | Boss §e+8%§7 | Mở khóa §65 SIÊU KỸ NĂNG",
                0xFF352505, 0xFFFFB300, mouseX, mouseY);
    }

    private void renderOptionCard(GuiGraphics guiGraphics, int x, int y, int w, int h, String title, String desc, int bg, int border, int mouseX, int mouseY) {
        boolean hovered = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
        guiGraphics.fill(x, y, x + w, y + h, hovered ? (bg | 0x333333) : bg);
        guiGraphics.renderOutline(x, y, w, h, hovered ? 0xFFFFFFFF : border);
        guiGraphics.drawString(this.font, (hovered ? "§f▶ " : "") + title, x + 8, y + 6, 0xFFFFFF);
        guiGraphics.drawString(this.font, desc, x + 8, y + 20, 0xCCCCCC);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int left = (this.width - PANEL_W) / 2;
            int top = (this.height - PANEL_H) / 2;

            // Click nút Random tên
            int diceX = left + 296;
            int diceY = top + 52;
            int diceW = 64;
            int diceH = 18;
            if (mouseX >= diceX && mouseX <= diceX + diceW && mouseY >= diceY && mouseY <= diceY + diceH) {
                playClick();
                this.nameInput.setValue(demonType.getRandomCanonName(RandomSource.create()));
                return true;
            }

            int cardX = left + 18;
            int cardW = PANEL_W - 36;
            int cardH = 36;
            int startY = top + 80;

            String chosenName = this.nameInput.getValue().trim();
            if (chosenName.isEmpty()) chosenName = demonType.getRandomCanonName(RandomSource.create());

            // Click Option 1: Body
            if (mouseX >= cardX && mouseX <= cardX + cardW && mouseY >= startY && mouseY <= startY + cardH) {
                playClick();
                ModMessages.sendToServer(new ServerboundCapsuleEvolvePacket(pos.getX(), pos.getY(), pos.getZ(), 1, chosenName));
                this.onClose();
                return true;
            }

            // Click Option 2: Name
            if (mouseX >= cardX && mouseX <= cardX + cardW && mouseY >= startY + 42 && mouseY <= startY + 42 + cardH) {
                playClick();
                ModMessages.sendToServer(new ServerboundCapsuleEvolvePacket(pos.getX(), pos.getY(), pos.getZ(), 2, chosenName));
                this.onClose();
                return true;
            }

            // Click Option 3: Both
            if (mouseX >= cardX && mouseX <= cardX + cardW && mouseY >= startY + 84 && mouseY <= startY + 84 + cardH) {
                playClick();
                ModMessages.sendToServer(new ServerboundCapsuleEvolvePacket(pos.getX(), pos.getY(), pos.getZ(), 3, chosenName));
                this.onClose();
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void playClick() {
        if (this.minecraft != null && this.minecraft.player != null) {
            this.minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 0.8F, 1.2F);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
