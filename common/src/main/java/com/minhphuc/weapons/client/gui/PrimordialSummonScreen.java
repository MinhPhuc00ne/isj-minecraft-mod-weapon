package com.minhphuc.weapons.client.gui;

import com.minhphuc.weapons.entity.tensura.DemonType;
import com.minhphuc.weapons.network.ModMessages;
import com.minhphuc.weapons.network.ServerboundSummonDemonPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class PrimordialSummonScreen extends Screen {

    private static final int PANEL_W = 480;
    private static final int PANEL_H = 260;

    public static class SummonOption {
        public final DemonType demonType;
        public final boolean isWinged;
        public final String displayName;
        public final String subtitle;
        public final int colorHex;

        public SummonOption(DemonType demonType, boolean isWinged, String displayName, String subtitle, int colorHex) {
            this.demonType = demonType;
            this.isWinged = isWinged;
            this.displayName = displayName;
            this.subtitle = subtitle;
            this.colorHex = colorHex;
        }
    }

    private final List<SummonOption> options = new ArrayList<>();
    private int selectedIndex = 1;

    public PrimordialSummonScreen() {
        super(Component.literal("Pháp Điển Khế Ước Ác Ma Thủy Tổ"));
    }

    @Override
    protected void init() {
        super.init();
        options.clear();

        options.add(new SummonOption(DemonType.ROUGE, false, "Rouge", "xích Sắc Thủy Tổ - Ma Vương", 0xFF2211));
        options.add(new SummonOption(DemonType.NOIR, false, "Noir ", "Hắc Sắc Thủy Tổ", 0x44444A));
        options.add(new SummonOption(DemonType.NOIR, true, "Noir (Hắc)", "Hắc Vương", 0x111116));
        options.add(new SummonOption(DemonType.BLANC, false, "Blanc", "Bạch Sắc Thủy Tổ", 0xF0F4F8));
        options.add(new SummonOption(DemonType.JAUNE, false, "Jaune", "Hoàng Sắc Thủy Tổ - Ma Pháp Hạt Nhân", 0xEAB308));
        options.add(new SummonOption(DemonType.VIOLET, false, "Violet", "Tử Sắc Thủy Tổ - Độc Khởi Nguyên", 0x9333EA));
        options.add(new SummonOption(DemonType.BLEU, false, "Bleu", "Lam Sắc Thủy Tổ - Băng Cực", 0x2563EB));
        options.add(new SummonOption(DemonType.VERT, false, "Vert", "Lục Sắc Thủy Tổ - Phong Lôi", 0x16A34A));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        int left = (this.width - PANEL_W) / 2;
        int top = (this.height - PANEL_H) / 2;

        // 1. Khung nền Obsidian & Ma Pháp Đỏ Viền Vàng
        guiGraphics.fill(left - 3, top - 3, left + PANEL_W + 3, top + PANEL_H + 3, 0xFF140808);
        guiGraphics.fill(left - 1, top - 1, left + PANEL_W + 1, top + PANEL_H + 1, 0xFFD43515);
        guiGraphics.fill(left, top, left + PANEL_W, top + PANEL_H, 0xEE0D0606);

        // Header Title
        guiGraphics.drawCenteredString(this.font, "§c§l✦ PHÁP ĐIỂN KHẾ ƯỚC THÁNH MA • THẤT ĐẠI THỦY TỔ ✦", left + PANEL_W / 2, top + 9, 0xFFFFFF);
        guiGraphics.fill(left + 15, top + 22, left + PANEL_W - 15, top + 23, 0x66D43515);

        // 2. Danh Sách 8 Tùy Chọn Bên Trái (Tabs)
        int tabX = left + 14;
        int tabY = top + 28;
        int tabW = 160;
        int tabH = 24;

        for (int i = 0; i < options.size(); i++) {
            SummonOption opt = options.get(i);
            int currentY = tabY + (i * 27);
            boolean isSelected = (i == selectedIndex);
            boolean isHovered = (mouseX >= tabX && mouseX <= tabX + tabW && mouseY >= currentY && mouseY <= currentY + tabH);

            int bgColor = isSelected ? 0xFF351212 : (isHovered ? 0xFF220C0C : 0xFF150808);
            int borderColor = isSelected ? 0xFFFF4422 : (isHovered ? 0xFFA83215 : 0xFF3A1818);

            guiGraphics.fill(tabX, currentY, tabX + tabW, currentY + tabH, bgColor);
            guiGraphics.renderOutline(tabX, currentY, tabW, tabH, borderColor);

            // Icon màu đại diện
            guiGraphics.fill(tabX + 4, currentY + 4, tabX + 16, currentY + tabH - 4, 0xFF000000 | opt.colorHex);
            guiGraphics.renderOutline(tabX + 4, currentY + 4, 12, tabH - 8, 0xFFFFFFFF);

            // Tên Ác Ma
            String label = (isSelected ? "§e§l▶ " : "§7") + opt.displayName;
            guiGraphics.drawString(this.font, label, tabX + 22, currentY + 7, isSelected ? 0xFFFFAA : 0xDDDDDD);
        }

        // 3. Khung Chi Tiết Ác Ma Bên Phải (Rộng rãi 285px)
        int detailX = left + 185;
        int detailY = top + 28;
        int detailW = PANEL_W - 200;
        int detailH = PANEL_H - 40;

        guiGraphics.fill(detailX, detailY, detailX + detailW, detailY + detailH, 0xAA120808);
        guiGraphics.renderOutline(detailX, detailY, detailW, detailH, 0xFF4A1818);

        if (selectedIndex >= 0 && selectedIndex < options.size()) {
            SummonOption sel = options.get(selectedIndex);
            DemonType demon = sel.demonType;
            int maxTextW = detailW - 24;

            int curY = detailY + 8;
            guiGraphics.drawString(this.font, "§6§lÁC MA THỦY TỔ: §c§l" + demon.getColorName().toUpperCase(), detailX + 12, curY, 0xFFFFFF);
            curY += 12;
            guiGraphics.drawString(this.font, "§7Danh xưng: §f" + demon.getTitleVi(), detailX + 12, curY, 0xCCCCCC);
            curY += 11;
            guiGraphics.drawString(this.font, "§7Dạng hình: §b" + (sel.isWinged ? "Hắc Dực Ma Vương (Giải Phóng Cánh)" : (demon == DemonType.NOIR ? "Quản Gia Lịch Lãm (Dạng Thường)" : "Thủy Tổ Nguyên Thủy")), detailX + 12, curY, 0xCCCCCC);

            // Chỉ số & Tỷ Lệ Diệt Boss
            curY += 13;
            guiGraphics.fill(detailX + 12, curY, detailX + detailW - 12, curY + 1, 0x44FFFFFF);
            curY += 4;
            int hp = (int) demon.getMaxHealth();
            if (sel.isWinged) hp = (int) (hp * 1.2);
            int dmg = (int) demon.getAttackDamage();
            if (sel.isWinged) dmg = (int) (dmg * 1.25);

            guiGraphics.drawString(this.font, "§c❤ Sinh lực: §f" + hp + " HP §7| §e⚔ Sát thương: §f" + dmg, detailX + 12, curY, 0xFFFFFF);
            curY += 11;

            int winRate = (int) (demon.getBossWinRate() * 100);
            String winRateNote = (demon == DemonType.ROUGE || demon == DemonType.NOIR) ? " (10% rủi ro nếu chủ quan)" : "";
            guiGraphics.drawString(this.font, "§d★ Tỷ lệ diệt Boss: §a" + winRate + "%§7" + winRateNote, detailX + 12, curY, 0xFFFFFF);
            curY += 11;
            guiGraphics.drawString(this.font, "§b✦ Bành trướng Lãnh Địa: §a+50% Sức Mạnh §7(Khối kết giới 16m)", detailX + 12, curY, 0x88CCFF);

            // Kỹ năng độc bản
            curY += 13;
            guiGraphics.fill(detailX + 12, curY, detailX + detailW - 12, curY + 1, 0x44FFFFFF);
            curY += 4;
            guiGraphics.drawString(this.font, "§d§l[KỸ NĂNG & MA PHÁP]", detailX + 12, curY, 0xFFAAFF);
            curY += 11;

            String skillDesc = getSkillDescription(demon, sel.isWinged);
            List<FormattedCharSequence> descLines = this.font.split(Component.literal("§7" + skillDesc), maxTextW);
            for (FormattedCharSequence line : descLines) {
                guiGraphics.drawString(this.font, line, detailX + 12, curY, 0xAAAAAA);
                curY += 10;
            }

            // Lời thoại phục tùng
            curY += 3;
            List<FormattedCharSequence> quoteLines = this.font.split(Component.literal("§e\"" + demon.getSummonDialogue() + "\""), maxTextW);
            for (FormattedCharSequence line : quoteLines) {
                guiGraphics.drawString(this.font, line, detailX + 12, curY, 0xFFDD88);
                curY += 10;
            }

            // NÚT TRIỆU HỒI PHÁP TRẬN MA GIỚI
            int btnX = detailX + 12;
            int btnY = detailY + detailH - 28;
            int btnW = detailW - 24;
            int btnH = 20;
            boolean btnHovered = (mouseX >= btnX && mouseX <= btnX + btnW && mouseY >= btnY && mouseY <= btnY + btnH);

            guiGraphics.fill(btnX, btnY, btnX + btnW, btnY + btnH, btnHovered ? 0xFF991515 : 0xFF660C0C);
            guiGraphics.renderOutline(btnX, btnY, btnW, btnH, btnHovered ? 0xFFFF4444 : 0xFFCC2222);
            guiGraphics.drawCenteredString(this.font, "§e§l✦ KHỞI ĐỘNG PHÁP TRẬN TRIỆU HỒI ✦", btnX + btnW / 2, btnY + 6, 0xFFFFFF);
        }
    }

    private String getSkillDescription(DemonType type, boolean isWinged) {
        return switch (type) {
            case ROUGE -> "Hỏa Long Tận Diệt & Xích Diễm Bộc Phá: Sức mạnh ngang Noir, gấp 4 lần ác ma thường, 2 lần Blanc. 90% diệt boss.";
            case NOIR -> isWinged
                    ? "Hắc Dực Ma Vương: Bay lượn, xé toạc không gian, hút mục tiêu vào hố đen phân hủy. 90% diệt boss."
                    : "Móng Vuốt Tử Thần & Hắc Ma Cầu: Tốc biến đoạt mệnh, làm suy yếu tột cùng. 90% diệt boss.";
            case BLANC -> "Bạch Viêm Diệt Tuyệt & Thánh Ma Phân Rã: Bắn chùm tia nhiệt hạch trắng bộc phá hạt nhân. Mạnh gấp đôi ác ma thường, 75% diệt boss.";
            case JAUNE -> "Đại Ma Pháp Hủy Diệt & Súng Carrera: Đa tầng pháp trận hạt nhân, trọng lực sụp đổ, ma đạn liên hoàn bão táp. 65% diệt boss.";
            case VIOLET -> "Tử Độc Khởi Nguyên: Khí độc tím tử thần và gai hoa mạn đà la ăn mòn giáp và máu. 33% diệt boss.";
            case BLEU -> "Băng Cực Ma Trận: Lốc xoáy băng tuyết và gai băng cực hàn đóng băng cứng kẻ địch. 20% diệt boss.";
            case VERT -> "Bão Tố Lục Bảo & Kết Giới Hộ Mệnh: Bão lốc đẩy lùi kẻ thù đồng thời hồi phục chủ nhân. 30% diệt boss.";
        };
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int left = (this.width - PANEL_W) / 2;
            int top = (this.height - PANEL_H) / 2;

            int tabX = left + 14;
            int tabY = top + 28;
            int tabW = 160;
            int tabH = 24;

            for (int i = 0; i < options.size(); i++) {
                int currentY = tabY + (i * 27);
                if (mouseX >= tabX && mouseX <= tabX + tabW && mouseY >= currentY && mouseY <= currentY + tabH) {
                    this.selectedIndex = i;
                    if (this.minecraft != null && this.minecraft.player != null) {
                        this.minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 0.8F, 1.2F);
                    }
                    return true;
                }
            }

            int detailX = left + 185;
            int detailY = top + 28;
            int detailW = PANEL_W - 200;
            int detailH = PANEL_H - 40;

            int btnX = detailX + 12;
            int btnY = detailY + detailH - 28;
            int btnW = detailW - 24;
            int btnH = 20;

            if (mouseX >= btnX && mouseX <= btnX + btnW && mouseY >= btnY && mouseY <= btnY + btnH) {
                executeSummon();
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void executeSummon() {
        if (this.minecraft == null || this.minecraft.player == null) return;

        SummonOption sel = options.get(selectedIndex);
        Vec3 look = this.minecraft.player.getLookAngle();
        Vec3 targetPos = this.minecraft.player.position().add(look.scale(5.0D));

        HitResult hit = this.minecraft.hitResult;
        if (hit != null && hit.getType() == HitResult.Type.BLOCK) {
            targetPos = hit.getLocation();
        }

        ModMessages.sendToServer(new ServerboundSummonDemonPacket(
                sel.demonType.ordinal(),
                sel.isWinged,
                targetPos.x,
                targetPos.y,
                targetPos.z
        ));

        this.minecraft.player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 1.5F, 0.9F);
        this.onClose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
