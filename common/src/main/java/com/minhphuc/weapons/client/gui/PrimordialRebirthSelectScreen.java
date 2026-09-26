package com.minhphuc.weapons.client.gui;

import com.minhphuc.weapons.content.tensura.PrimordialPlayerDataHelper;
import com.minhphuc.weapons.entity.tensura.DemonType;
import com.minhphuc.weapons.network.ModMessages;
import com.minhphuc.weapons.network.ServerboundSelectPrimordialRebirthPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

import java.util.ArrayList;
import java.util.List;

public class PrimordialRebirthSelectScreen extends Screen {

    private static final int PANEL_W = 500;
    private static final int PANEL_H = 270;

    public static class RebirthOption {
        public final DemonType demonType;
        public final String displayName;
        public final String roleTitle;
        public final int colorHex;
        public final String perkVi;
        public final String[] skills;

        public RebirthOption(DemonType demonType, String displayName, String roleTitle, int colorHex, String perkVi, String[] skills) {
            this.demonType = demonType;
            this.displayName = displayName;
            this.roleTitle = roleTitle;
            this.colorHex = colorHex;
            this.perkVi = perkVi;
            this.skills = skills;
        }
    }

    private final List<RebirthOption> options = new ArrayList<>();
    private int selectedIndex = 0;

    public PrimordialRebirthSelectScreen() {
        super(Component.literal("Sách Cổ Khởi Nguyên Thủy Tổ"));
    }

    @Override
    protected void init() {
        super.init();
        options.clear();

        options.add(new RebirthOption(
                DemonType.ROUGE,
                "Rouge (Guy Crimson)",
                "Xích Sắc Thủy Tổ - Ma Thần Đỉnh Cao",
                0xFF1E1E,
                "§cKháng cháy 100%, uy áp Hỏa Ngục áp chế mọi ác ma.",
                new String[]{
                        "Skill 1: Tà Khứ Vũ Thê Tử (Ma Trận Huyết Sắc & Cột Sáng)",
                        "Skill 2: Hỏa Ngục Bộc Viêm (Prominence Flare - 2 Ma Trận Xoay)",
                        "Skill 3: Xích Hồng Ma Trảm (Crimson Severance - Trảm Kích 30 Block)",
                        "Skill 4: Bức Tường Hỏa Ma (Infernal Bastion - 3 Tầng Ma Trận)",
                        "Skill 5: Lôi Hỏa Diệt Thế (Lucifer's Judgment - 8 Thiên Lôi Đỏ)"
                }
        ));

        options.add(new RebirthOption(
                DemonType.NOIR,
                "Noir (Diablo)",
                "Hắc Sắc Thủy Tổ - Quản Gia Vực Thẳm",
                0x33333E,
                "§d★ MẶC ĐỊNH NHẬN NGAY: §fMóng Vuốt Thủy Tổ (Abyssal Claws)",
                new String[]{
                        "Skill 1: Tà Khứ Vũ Thê Tử (Ma Trận Hắc Sắc & Cột Sáng)",
                        "Skill 2: Thế Giới Cám Dỗ (Temptation World - Khống Chế Tuyệt Đối)",
                        "Skill 3: Vũ Điệu Móng Vuốt (End of Despair - 5 Trảm Kích Không Gian)",
                        "Skill 4: Nghịch Chuyển Sinh Tử (Reversal of Life & Death - Hồi Máu)",
                        "Skill 5: Hắc Hạch Hư Vô Sụp Đổ (Black Hole - 3 Ma Trận Xếp Chồng)"
                }
        ));

        options.add(new RebirthOption(
                DemonType.BLANC,
                "Blanc (Testarossa)",
                "Bạch Sắc Thủy Tổ - Nữ Hoàng Tinh Thần",
                0xF0F4F8,
                "§fMiễn nhiễm mọi hiệu ứng suy yếu và thôi miên tinh thần.",
                new String[]{
                        "Skill 1: Tà Khứ Vũ Thê Tử (Ma Trận Tuyết Sắc & Cột Sáng)",
                        "Skill 2: Bạch Viêm Tuyệt Diệt (White Flare - Lửa Trắng Xuyên Giáp)",
                        "Skill 3: Mị Hoặc Tinh Thần (Mind Domination - Quái Tự Tàn Sát)",
                        "Skill 4: Lãnh Băng Hồ Điệp (Frost Butterflies - Đóng Băng 5 Giây)",
                        "Skill 5: Trắng Xóa Hư Vô (Absolute Annihilation - Diệt Thế Diện Rộng)"
                }
        ));

        options.add(new RebirthOption(
                DemonType.JAUNE,
                "Jaune (Carrera)",
                "Hoàng Sắc Thủy Tổ - Phá Diệt Chi Vương",
                0xFACC15,
                "§6★ TIẾN HÓA MA VƯƠNG SẼ NHẬN: §eSúng Lục Hoàng Kim (Golden Gun)",
                new String[]{
                        "Skill 1: Tà Khứ Vũ Thê Tử (Ma Trận Hoàng Kim & Cột Sáng)",
                        "Skill 2: Sụp Đổ Trọng Lực (Gravity Collapse - Dìm Quái Xuống Đất)",
                        "Skill 3: Tia Sáng Hoàng Kim (Golden Breaker - Xuyên Thủng Mục Tiêu)",
                        "Skill 4: Tập Trung Xạ Kích (Abaddon Focus - Đạn Ma Pháp Nổ Chùm)",
                        "Skill 5: Pháo Hạt Nhân Khởi Nguyên (Nuclear Cannon - Pháo Hủy Diệt)"
                }
        ));

        options.add(new RebirthOption(
                DemonType.VIOLET,
                "Violet (Ultima)",
                "Tử Sắc Thủy Tổ - Độc Khởi Nguyên",
                0xA855F7,
                "§5Độc tố ăn mòn giáp trụ và trừ thẳng theo % máu tối đa.",
                new String[]{
                        "Skill 1: Tà Khứ Vũ Thê Tử (Ma Trận Tử Độc & Cột Sáng)",
                        "Skill 2: Tử Độc Nở Rộ (Toxic Bloom - Bung Tỏa Khói Độc Wither IV)",
                        "Skill 3: Hắc Tử Xuyên Tâm (Shadow Poison Ray - Giảm 60% Sát Thương Địch)",
                        "Skill 4: Huyễn Ảnh Tốc Biến (Violet Phantom Dash - Tốc Biến Dư Ảnh)",
                        "Skill 5: Mưa Ăn Mòn Tuyệt Tự (Corrosive Ruin - Mưa Axit Tan Chảy)"
                }
        ));

        options.add(new RebirthOption(
                DemonType.BLEU,
                "Bleu (Rein)",
                "Lam Sắc Thủy Tổ - Băng Cực Tuyệt Đối",
                0x3B82F6,
                "§9Phòng thủ kiên cố, khả năng đóng băng toàn diện.",
                new String[]{
                        "Skill 1: Tà Khứ Vũ Thê Tử (Ma Trận Lam Sắc & Cột Sáng)",
                        "Skill 2: Băng Tinh Vũ Bão (Absolute Zero Shards - Phi Châm Băng)",
                        "Skill 3: Hàn Băng Pháo Đài (Glacial Bastion - Kén Băng Đẩy Lùi)",
                        "Skill 4: Lồng Giam Không Gian (Dimensional Canvas - Nhốt Kẻ Địch)",
                        "Skill 5: Bão Tuyết Vĩnh Cửu (Eternal Blizzard - Đóng Băng Thành Tượng)"
                }
        ));

        options.add(new RebirthOption(
                DemonType.VERT,
                "Vert (Misery)",
                "Lục Sắc Thủy Tổ - Cuồng Phong Vực Sâu",
                0x22C55E,
                "§aHút cạn sinh lực kẻ thù và phản hồi các đòn đánh tầm xa.",
                new String[]{
                        "Skill 1: Tà Khứ Vũ Thê Tử (Ma Trận Lục Sắc & Cột Sáng)",
                        "Skill 2: Lục Phong Tiễu Sát (Emerald Tempest Blades - Đao Gió Xoay)",
                        "Skill 3: Hấp Thụ Sinh Khí (Abyssal Vitality Drain - Hút Máu Hóa Khiên)",
                        "Skill 4: Kết Giới Lục Thần (Emerald Barrier Gale - Hất Ngược Mũi Tên)",
                        "Skill 5: Cuồng Phong Tai Ương (Calamity Maelstrom - Vòi Rồng Hút Quái)"
                }
        ));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        int left = (this.width - PANEL_W) / 2;
        int top = (this.height - PANEL_H) / 2;

        // 1. Viền ngoài & Background
        guiGraphics.fill(left - 3, top - 3, left + PANEL_W + 3, top + PANEL_H + 3, 0xFF140818);
        guiGraphics.fill(left - 1, top - 1, left + PANEL_W + 1, top + PANEL_H + 1, 0xFF7E22CE);
        guiGraphics.fill(left, top, left + PANEL_W, top + PANEL_H, 0xF50D0614);

        // Header Title
        guiGraphics.drawCenteredString(this.font, "§6§l✦ SÁCH CỔ KHỞI NGUYÊN • CHUYỂN SINH THỦY TỔ ÁC MA ✦", left + PANEL_W / 2, top + 9, 0xFFFFFF);
        guiGraphics.fill(left + 15, top + 22, left + PANEL_W - 15, top + 23, 0x66A855F7);

        // 2. Danh Sách 7 Tùy Chọn Bên Trái
        int tabX = left + 14;
        int tabY = top + 28;
        int tabW = 160;
        int tabH = 30;

        for (int i = 0; i < options.size(); i++) {
            RebirthOption opt = options.get(i);
            int currentY = tabY + (i * 33);
            boolean isSelected = (i == selectedIndex);
            boolean isHovered = (mouseX >= tabX && mouseX <= tabX + tabW && mouseY >= currentY && mouseY <= currentY + tabH);

            int bgColor = isSelected ? 0xFF28103A : (isHovered ? 0xFF1C0B29 : 0xFF100618);
            int borderColor = isSelected ? 0xFFC084FC : (isHovered ? 0xFF9333EA : 0xFF3B1854);

            guiGraphics.fill(tabX, currentY, tabX + tabW, currentY + tabH, bgColor);
            guiGraphics.renderOutline(tabX, currentY, tabW, tabH, borderColor);

            // Icon màu đại diện
            guiGraphics.fill(tabX + 4, currentY + 4, tabX + 18, currentY + tabH - 4, 0xFF000000 | opt.colorHex);
            guiGraphics.renderOutline(tabX + 4, currentY + 4, 14, tabH - 8, 0xFFFFFFFF);

            // Tên Ác Ma
            String label = (isSelected ? "§e§l▶ " : "§7") + opt.displayName;
            guiGraphics.drawString(this.font, label, tabX + 23, currentY + 6, isSelected ? 0xFFFFAA : 0xCCCCCC);
            guiGraphics.drawString(this.font, "§8" + opt.demonType.name(), tabX + 23, currentY + 17, 0x888888);
        }

        // 3. Khung Chi Tiết Bên Phải
        int detailX = left + 184;
        int detailY = top + 28;
        int detailW = PANEL_W - 198;
        int detailH = PANEL_H - 38;

        guiGraphics.fill(detailX, detailY, detailX + detailW, detailY + detailH, 0x88120622);
        guiGraphics.renderOutline(detailX, detailY, detailW, detailH, 0xFF581C87);

        RebirthOption current = options.get(selectedIndex);

        // Header thông tin Thủy Tổ
        guiGraphics.fill(detailX + 4, detailY + 4, detailX + 10, detailY + 20, 0xFF000000 | current.colorHex);
        guiGraphics.drawString(this.font, "§e§l" + current.displayName, detailX + 16, detailY + 6, 0xFFFFFF);
        guiGraphics.drawString(this.font, "§b" + current.roleTitle, detailX + 16, detailY + 18, 0xAAAAAA);

        guiGraphics.fill(detailX + 8, detailY + 30, detailX + detailW - 8, detailY + 31, 0x44FFFFFF);

        // Đặc quyền độc quyền
        guiGraphics.drawString(this.font, "§6✦ Đặc Quyền Thần Vị:", detailX + 10, detailY + 36, 0xFFFFAA);
        guiGraphics.drawString(this.font, current.perkVi, detailX + 14, detailY + 47, 0xFFFFFF);

        // 3 Giai đoạn tiến hóa
        guiGraphics.drawString(this.font, "§d✦ Quy Tắc Thể Xác & Tiến Hóa:", detailX + 10, detailY + 62, 0xFFAAFF);
        guiGraphics.drawString(this.font, "§7• §bÁc Ma Linh Thể: §aQuái thường không thể đả thương §7(Trừ Creeper/Ravager/Boss/Velgrynd).", detailX + 14, detailY + 73, 0xCCCCCC);
        guiGraphics.drawString(this.font, "§7• §eCó Thể Xác (40 Hồn): §7Quái thường đánh được; §6Bản thân & Skill 2 hit diệt Boss!", detailX + 14, detailY + 84, 0xCCCCCC);
        guiGraphics.drawString(this.font, "§7• §cChân Ma Vương (64 Hồn): §4BẤT TỬ TUYỆT ĐỐI! §7(Chỉ chịu sát thương từ §cChước Nhiệt Long§7).", detailX + 14, detailY + 95, 0xCCCCCC);

        guiGraphics.fill(detailX + 8, detailY + 108, detailX + detailW - 8, detailY + 109, 0x44FFFFFF);

        // Danh sách 5 Kỹ Năng
        guiGraphics.drawString(this.font, "§a✦ Bộ 5 Kỹ Năng Tối Thượng (Chuyển bằng phím Z):", detailX + 10, detailY + 114, 0xAAFFAA);
        int skillY = detailY + 126;
        for (String skill : current.skills) {
            guiGraphics.drawString(this.font, "§e▶ §f" + skill, detailX + 14, skillY, 0xEEEEEE);
            skillY += 12;
        }

        // Cảnh báo tử trận
        guiGraphics.drawString(this.font, "§c⚠ Tử trận sẽ mất vĩnh viễn thân phận Ác Ma (Phải dùng sách mới).", detailX + 10, detailY + detailH - 38, 0xFF6666);

        // Nút THỨC TỈNH THỦY TỔ
        int btnW = 200;
        int btnH = 22;
        int btnX = detailX + (detailW - btnW) / 2;
        int btnY = detailY + detailH - 26;

        boolean btnHovered = (mouseX >= btnX && mouseX <= btnX + btnW && mouseY >= btnY && mouseY <= btnY + btnH);
        int btnBg = btnHovered ? 0xFF9333EA : 0xFF581C87;
        int btnBorder = btnHovered ? 0xFFF0ABFC : 0xFFC084FC;

        guiGraphics.fill(btnX, btnY, btnX + btnW, btnY + btnH, btnBg);
        guiGraphics.renderOutline(btnX, btnY, btnW, btnH, btnBorder);
        guiGraphics.drawCenteredString(this.font, "§6§l✦ THỨC TỈNH THÂN PHẬN ✦", btnX + btnW / 2, btnY + 7, 0xFFFFFF);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int left = (this.width - PANEL_W) / 2;
        int top = (this.height - PANEL_H) / 2;

        int tabX = left + 14;
        int tabY = top + 28;
        int tabW = 160;
        int tabH = 30;

        // Click chọn tab
        for (int i = 0; i < options.size(); i++) {
            int currentY = tabY + (i * 33);
            if (mouseX >= tabX && mouseX <= tabX + tabW && mouseY >= currentY && mouseY <= currentY + tabH) {
                if (selectedIndex != i) {
                    selectedIndex = i;
                    if (minecraft != null && minecraft.player != null) {
                        minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1.0F, 1.2F);
                    }
                }
                return true;
            }
        }

        // Click nút Thức Tỉnh
        int detailX = left + 184;
        int detailY = top + 28;
        int detailW = PANEL_W - 198;
        int detailH = PANEL_H - 38;

        int btnW = 200;
        int btnH = 22;
        int btnX = detailX + (detailW - btnW) / 2;
        int btnY = detailY + detailH - 26;

        if (mouseX >= btnX && mouseX <= btnX + btnW && mouseY >= btnY && mouseY <= btnY + btnH) {
            RebirthOption chosen = options.get(selectedIndex);
            ModMessages.sendToServer(new ServerboundSelectPrimordialRebirthPacket(chosen.demonType));
            this.onClose();
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
