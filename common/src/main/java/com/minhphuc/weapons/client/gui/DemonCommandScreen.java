package com.minhphuc.weapons.client.gui;

import com.minhphuc.weapons.content.tensura.PrimordialPactItem;
import com.minhphuc.weapons.content.tensura.PrimordialSkillPool;
import com.minhphuc.weapons.data.ItemStackDataHelper;
import com.minhphuc.weapons.entity.tensura.DemonType;
import com.minhphuc.weapons.entity.tensura.PrimordialDemonEntity;
import com.minhphuc.weapons.network.ModMessages;
import com.minhphuc.weapons.network.ServerboundDemonCommandPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DemonCommandScreen extends Screen {

    private static final int PANEL_W = 540;
    private static final int PANEL_H = 264;

    private final ItemStack pactStack;
    private final DemonType demonType;

    public static class SkillEntry {
        public final int index;
        public final String title;
        public final String desc;
        public final int colorHex;

        public SkillEntry(int index, String title, String desc, int colorHex) {
            this.index = index;
            this.title = title;
            this.desc = desc;
            this.colorHex = colorHex;
        }
    }

    private final List<SkillEntry> skills = new ArrayList<>();
    private String statusFeedback = "";
    private int feedbackTimer = 0;

    public DemonCommandScreen(ItemStack pactStack) {
        super(Component.literal("Bảng Điều Khiển Ác Ma Thủy Tổ"));
        this.pactStack = pactStack;
        this.demonType = PrimordialPactItem.getDemonType(pactStack);
    }

    @Override
    protected void init() {
        super.init();
        skills.clear();

        // 10 Kỹ năng khởi nguyên mặc định
        skills.add(new SkillEntry(0, "Cào Cấu Ma Giới", "Đòn cào xé móng vuốt cận chiến", 0xDDDDDD));
        skills.add(new SkillEntry(1, getSkillOneName(demonType), "Kỹ năng khởi nguyên giai đoạn 1", 0xFFA500));
        skills.add(new SkillEntry(2, getSkillTwoName(demonType), "Kỹ năng khởi nguyên giai đoạn 2", 0xFF6347));
        skills.add(new SkillEntry(3, getSkillThreeName(demonType), "Đại ma pháp tối thượng giai đoạn 3", 0xDC143C));
        skills.add(new SkillEntry(4, "Cột Sáng 7 Sắc Cầu Vồng", "Quang trụ Jacob bộc phá năng lượng", 0x00FFFF));
        skills.add(new SkillEntry(5, "Linh Tử Băng Hoại", "Disintegration diệt nguyên tử", 0xFFD700));
        skills.add(new SkillEntry(6, getSignatureOneName(demonType), "Bí thuật Thủy Tổ độc bản cấp 1", 0xBA55D3));
        skills.add(new SkillEntry(7, getSignatureTwoName(demonType), "Bí thuật Thủy Tổ độc bản cấp 2", 0x9932CC));
        skills.add(new SkillEntry(8, getSignatureThreeName(demonType), "Bí thuật hủy diệt độc bản cấp 3", 0x8A2BE2));
        skills.add(new SkillEntry(9, "Bành Trướng Lãnh Địa", "Triển khai kết giới lãnh địa 16m", 0x4B0082));

        // Nạp kỹ năng ngẫu nhiên đã thức tỉnh (từ cuộn khế ước hoặc entity)
        String savedSkills = ItemStackDataHelper.getString(pactStack, "PrimordialSkills");
        List<String> skillIds = new ArrayList<>();
        if (!savedSkills.isEmpty()) {
            skillIds.addAll(Arrays.asList(savedSkills.split(",")));
        } else {
            PrimordialDemonEntity demon = findClientDemon();
            if (demon != null) {
                for (var sk : demon.getRandomSkills()) {
                    skillIds.add(String.valueOf(sk.id()));
                }
            }
        }

        for (int i = 0; i < skillIds.size(); i++) {
            var entry = PrimordialSkillPool.getSkillById(skillIds.get(i).trim());
            if (entry != null) {
                skills.add(new SkillEntry(10 + i, entry.displayNameVi(), entry.descVi(), entry.iconColorHex()));
            }
        }
    }

    private PrimordialDemonEntity findClientDemon() {
        if (this.minecraft == null || this.minecraft.level == null || this.minecraft.player == null) return null;
        ClientLevel level = this.minecraft.level;

        String uuidStr = ItemStackDataHelper.getString(pactStack, "DemonUUID");
        if (!uuidStr.isEmpty()) {
            try {
                UUID id = UUID.fromString(uuidStr);
                for (PrimordialDemonEntity e : level.getEntitiesOfClass(
                        PrimordialDemonEntity.class,
                        new AABB(this.minecraft.player.getX() - 128, this.minecraft.player.getY() - 64, this.minecraft.player.getZ() - 128,
                                this.minecraft.player.getX() + 128, this.minecraft.player.getY() + 64, this.minecraft.player.getZ() + 128))) {
                    if (e.getUUID().equals(id) && e.isAlive()) {
                        return e;
                    }
                }
            } catch (Exception ignored) {}
        }

        List<PrimordialDemonEntity> list = level.getEntitiesOfClass(
                PrimordialDemonEntity.class,
                new AABB(this.minecraft.player.getX() - 128, this.minecraft.player.getY() - 64, this.minecraft.player.getZ() - 128,
                        this.minecraft.player.getX() + 128, this.minecraft.player.getY() + 64, this.minecraft.player.getZ() + 128),
                d -> d.isAlive() && d.getDemonType() == demonType && d.isOwnedBy(this.minecraft.player)
        );
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void tick() {
        super.tick();
        if (feedbackTimer > 0) feedbackTimer--;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        int left = (this.width - PANEL_W) / 2;
        int top = (this.height - PANEL_H) / 2;

        // 1. Khung nền Dark Fantasy
        guiGraphics.fill(left - 3, top - 3, left + PANEL_W + 3, top + PANEL_H + 3, 0xFF140808);
        guiGraphics.fill(left - 1, top - 1, left + PANEL_W + 1, top + PANEL_H + 1, 0xFFB22222);
        guiGraphics.fill(left, top, left + PANEL_W, top + PANEL_H, 0xEE0F0707);

        // Header Title
        String customName = ItemStackDataHelper.getString(pactStack, "CustomDemonName");
        String nameDisplay = !customName.isEmpty() ? customName : demonType.getColorName().toUpperCase();
        guiGraphics.drawCenteredString(this.font,
                "§c§l✦ MỆNH LỆNH THỦY TỔ ÁC MA: §e§l" + nameDisplay + " §7(" + demonType.getTitleVi() + ") ✦",
                left + PANEL_W / 2, top + 7, 0xFFFFFF);

        // 2. Thông tin Sinh lực & Trạng thái
        PrimordialDemonEntity demon = findClientDemon();
        float currentHp = demon != null ? demon.getHealth() : ItemStackDataHelper.getFloat(pactStack, "CurrentHp", (float) demonType.getMaxHealth());
        float maxHp = demon != null ? demon.getMaxHealth() : ItemStackDataHelper.getFloat(pactStack, "MaxHp", (float) demonType.getMaxHealth());
        if (maxHp <= 0) maxHp = (float) demonType.getMaxHealth();
        float hpRatio = Math.max(0.0F, Math.min(1.0F, currentHp / maxHp));

        // Thanh máu
        int barX = left + 14;
        int barY = top + 22;
        int barW = 220;
        int barH = 13;
        guiGraphics.fill(barX - 1, barY - 1, barX + barW + 1, barY + barH + 1, 0xFF330000);
        guiGraphics.fill(barX, barY, barX + barW, barY + barH, 0xFF1A0505);
        int fillW = (int) (barW * hpRatio);
        int hpColor = hpRatio > 0.5F ? 0xFF22C55E : (hpRatio > 0.25F ? 0xFFEAB308 : 0xFFEF4444);
        guiGraphics.fill(barX, barY, barX + fillW, barY + barH, hpColor);
        String hpText = String.format("❤ HP: %.0f / %.0f (%.0f%%)", currentHp, maxHp, hpRatio * 100);
        guiGraphics.drawCenteredString(this.font, "§f§l" + hpText, barX + barW / 2, barY + 3, 0xFFFFFF);

        // Trạng thái canh giữ/đi theo
        boolean isGuarding = demon != null ? demon.isOrderedToSit() : false;
        String stanceText = isGuarding ? "§e[Canh Giữ Vị Trí]" : "§a[Đi Theo Bảo Vệ]";
        guiGraphics.drawString(this.font, "§7Thế trận: " + stanceText, barX, barY + 16, 0xCCCCCC);

        // Nút lệnh Thế trận (Stance) bên phải
        int btnStanceX = left + 265;
        int btnW = 80;
        int btnH = 18;

        renderButton(guiGraphics, btnStanceX, barY, btnW, btnH, "§aĐi Theo", mouseX, mouseY);
        renderButton(guiGraphics, btnStanceX + 88, barY, btnW, btnH, "§eCanh Giữ", mouseX, mouseY);
        renderButton(guiGraphics, btnStanceX + 176, barY, btnW, btnH, "§cThu Hồi", mouseX, mouseY);

        // Dòng phản hồi hoặc chỉ dẫn
        if (feedbackTimer > 0 && !statusFeedback.isEmpty()) {
            guiGraphics.drawCenteredString(this.font, statusFeedback, left + PANEL_W / 2, top + 46, 0xFFFF55);
        } else {
            guiGraphics.drawString(this.font, "§d▶ Nhấp vào chiêu thức bên dưới để hạ lệnh cho Ác Ma thi triển ngay lập tức:", left + 14, top + 44, 0xAAAAAA);
        }

        // Đường kẻ phân cách
        guiGraphics.fill(left + 12, top + 55, left + PANEL_W - 12, top + 56, 0x44FFFFFF);

        // 3. Lưới 3 Cột Kỹ Năng (Col 1: Khởi Nguyên, Col 2: Tối Thượng, Col 3: Thức Tỉnh)
        int gridX = left + 14;
        int gridY = top + 60;
        int colW = 162;
        int cardH = 34;
        int gapX = 12;
        int gapY = 5;

        for (int i = 0; i < skills.size(); i++) {
            SkillEntry s = skills.get(i);
            int col = i / 5;
            int row = i % 5;
            int cardX = gridX + col * (colW + gapX);
            int cardY = gridY + row * (cardH + gapY);

            boolean hovered = mouseX >= cardX && mouseX <= cardX + colW && mouseY >= cardY && mouseY <= cardY + cardH;
            int bg = hovered ? (col == 2 ? 0xFF351235 : 0xFF2A1010) : (col == 2 ? 0xFF1C091C : 0xFF180A0A);
            int border = hovered ? (col == 2 ? 0xFFFF77FF : 0xFFFF4444) : (col == 2 ? 0xFF7A287A : 0xFF4A1818);

            guiGraphics.fill(cardX, cardY, cardX + colW, cardY + cardH, bg);
            guiGraphics.renderOutline(cardX, cardY, colW, cardH, border);

            // Icon màu kỹ năng
            guiGraphics.fill(cardX + 4, cardY + 4, cardX + 8, cardY + cardH - 4, 0xFF000000 | s.colorHex);

            // Tên chiêu
            String titleStr = (hovered ? "§e▶ " : "§f") + s.title;
            guiGraphics.drawString(this.font, titleStr, cardX + 12, cardY + 4, 0xFFFFFF);

            // Mô tả ngắn
            guiGraphics.drawString(this.font, "§7" + s.desc, cardX + 12, cardY + 17, 0x888888);
        }

        // Placeholder cho cột 3 nếu chưa thức tỉnh kỹ năng
        if (skills.size() <= 10) {
            int cardX = gridX + 2 * (colW + gapX);
            int cardY = gridY;
            guiGraphics.fill(cardX, cardY, cardX + colW, cardY + (cardH + gapY) * 5 - gapY, 0x33100818);
            guiGraphics.renderOutline(cardX, cardY, colW, (cardH + gapY) * 5 - gapY, 0x66552255);
            guiGraphics.drawCenteredString(this.font, "§d✦ KỸ NĂNG THỨC TỈNH ✦", cardX + colW / 2, cardY + 20, 0xEE88EE);
            guiGraphics.drawCenteredString(this.font, "§7Chưa mở khóa", cardX + colW / 2, cardY + 40, 0x888888);
            guiGraphics.drawCenteredString(this.font, "§8Đặt vào Bồn Chứa", cardX + colW / 2, cardY + 65, 0x666666);
            guiGraphics.drawCenteredString(this.font, "§8hoặc hiến tế 10 Dân Làng", cardX + colW / 2, cardY + 80, 0x666666);
            guiGraphics.drawCenteredString(this.font, "§8để thức tỉnh!", cardX + colW / 2, cardY + 95, 0x666666);
        }
    }

    private void renderButton(GuiGraphics guiGraphics, int x, int y, int w, int h, String text, int mouseX, int mouseY) {
        boolean hovered = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
        int bg = hovered ? 0xFF441818 : 0xFF220C0C;
        int border = hovered ? 0xFFFF5555 : 0xFF661E1E;
        guiGraphics.fill(x, y, x + w, y + h, bg);
        guiGraphics.renderOutline(x, y, w, h, border);
        guiGraphics.drawCenteredString(this.font, text, x + w / 2, y + 5, 0xFFFFFF);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int left = (this.width - PANEL_W) / 2;
            int top = (this.height - PANEL_H) / 2;
            int barY = top + 22;
            int btnStanceX = left + 265;
            int btnW = 80;
            int btnH = 18;

            // Nút 1: Đi Theo
            if (mouseX >= btnStanceX && mouseX <= btnStanceX + btnW && mouseY >= barY && mouseY <= barY + btnH) {
                playClick();
                ModMessages.sendToServer(new ServerboundDemonCommandPacket(ServerboundDemonCommandPacket.ACTION_STANCE_FOLLOW, 0));
                showFeedback("§a✔ Đã ra lệnh: ĐI THEO BẢO VỆ CHỦ NHÂN!");
                return true;
            }

            // Nút 2: Canh Giữ
            if (mouseX >= btnStanceX + 88 && mouseX <= btnStanceX + 88 + btnW && mouseY >= barY && mouseY <= barY + btnH) {
                playClick();
                ModMessages.sendToServer(new ServerboundDemonCommandPacket(ServerboundDemonCommandPacket.ACTION_STANCE_GUARD, 0));
                showFeedback("§e✔ Đã ra lệnh: CANH GIỮ VỊ TRÍ NÀY!");
                return true;
            }

            // Nút 3: Thu Hồi
            if (mouseX >= btnStanceX + 176 && mouseX <= btnStanceX + 176 + btnW && mouseY >= barY && mouseY <= barY + btnH) {
                playClick();
                ModMessages.sendToServer(new ServerboundDemonCommandPacket(ServerboundDemonCommandPacket.ACTION_DISMISS, 0));
                this.onClose();
                return true;
            }

            // Kiểm tra click vào Kỹ năng (3 Cột x 5 Hàng)
            int gridX = left + 14;
            int gridY = top + 60;
            int colW = 162;
            int cardH = 34;
            int gapX = 12;
            int gapY = 5;

            for (int i = 0; i < skills.size(); i++) {
                SkillEntry s = skills.get(i);
                int col = i / 5;
                int row = i % 5;
                int cardX = gridX + col * (colW + gapX);
                int cardY = gridY + row * (cardH + gapY);

                if (mouseX >= cardX && mouseX <= cardX + colW && mouseY >= cardY && mouseY <= cardY + cardH) {
                    playClick();
                    ModMessages.sendToServer(new ServerboundDemonCommandPacket(ServerboundDemonCommandPacket.ACTION_CAST_SKILL, s.index));
                    showFeedback("§6⚡ ĐÃ RA LỆNH THI TRIỂN: §e" + s.title + "!");
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void playClick() {
        if (this.minecraft != null && this.minecraft.player != null) {
            this.minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 0.8F, 1.2F);
        }
    }

    private void showFeedback(String msg) {
        this.statusFeedback = msg;
        this.feedbackTimer = 60; // 3 seconds
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private static String getSkillOneName(DemonType type) {
        return switch (type) {
            case ROUGE -> "Hỏa Long Tận Diệt";
            case NOIR -> "Hắc Ma Cầu";
            case BLANC -> "Bạch Viêm Diệt Tuyệt";
            case JAUNE -> "Đại Ma Pháp Hạt Nhân";
            case VIOLET -> "Tử Độc Khởi Nguyên";
            case BLEU -> "Băng Cực Ma Trận";
            case VERT -> "Bão Tố Lục Bảo";
        };
    }

    private static String getSkillTwoName(DemonType type) {
        return switch (type) {
            case ROUGE -> "Xích Diễm Bộc Phá";
            case NOIR -> "Móng Vuốt Tử Thần";
            case BLANC -> "Thánh Ma Phân Rã";
            case JAUNE -> "Trọng Lực Sụp Đổ";
            case VIOLET -> "Gai Hoa Mạn Đà La";
            case BLEU -> "Gai Băng Hàn Cực";
            case VERT -> "Kết Giới Bão Tố";
        };
    }

    private static String getSkillThreeName(DemonType type) {
        return switch (type) {
            case ROUGE -> "Địa Ngục Hỏa Xích Sắc";
            case NOIR -> "Hắc Dực Ma Vương (Hố Đen)";
            case BLANC -> "Tia Nhiệt Hạch Cực Quang";
            case JAUNE -> "Súng Thần Công Carrera";
            case VIOLET -> "Vũ Điệu Tử Hoa Độc";
            case BLEU -> "Hàn Ngục Tuyệt Đối";
            case VERT -> "Bão Tố Phục Sinh";
        };
    }

    private static String getSignatureOneName(DemonType type) {
        return switch (type) {
            case ROUGE -> "Guy: Prominence Flare";
            case NOIR -> "Noir: Đoạt Mệnh Hư Không";
            case BLANC -> "Blanc: Bạch Diễm Lãnh Nguyệt";
            case JAUNE -> "Carrera: Thần Uy Hạch Pháo";
            case VIOLET -> "Ultima: Vũ Điệu Tử Độc";
            case BLEU -> "Rain: Hàn Băng Tuyệt Đối";
            case VERT -> "Misery: Phong Giới Thánh Địa";
        };
    }

    private static String getSignatureTwoName(DemonType type) {
        return switch (type) {
            case ROUGE -> "Guy: Crimson Nova";
            case NOIR -> "Noir: Móng Vuốt Tuyệt Vọng";
            case BLANC -> "Blanc: Nuclear Holocaust";
            case JAUNE -> "Carrera: Không Gian Liệt Phá";
            case VIOLET -> "Ultima: Bụi Hoa Tử Thần";
            case BLEU -> "Rain: Băng Ngục Cực Hàn";
            case VERT -> "Misery: Bão Tố Tận Diệt";
        };
    }

    private static String getSignatureThreeName(DemonType type) {
        return switch (type) {
            case ROUGE -> "Guy: Hư Vô Tận Diệt";
            case NOIR -> "Noir: Hắc Ám Bất Tận";
            case BLANC -> "Blanc: Bạch Quang Hủy Diệt";
            case JAUNE -> "Carrera: Siêu Tân Tinh (Supernova)";
            case VIOLET -> "Ultima: Tử Độc Mạn Đà La";
            case BLEU -> "Rain: Bão Tuyết Vĩnh Hằng";
            case VERT -> "Misery: Phong Bão Thế Giới";
        };
    }
}
