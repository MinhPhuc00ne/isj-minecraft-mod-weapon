package com.minhphuc.weapons.client.gui;

import com.minhphuc.weapons.init.ModItems;
import com.minhphuc.weapons.network.ModMessages;
import com.minhphuc.weapons.network.ServerboundMaterialCreationPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Giao diện Năng Lực [Sáng Tạo Vật Chất] (Material Creation Screen).
 * Cho phép Chân Ma Vương ngưng tụ các món thần khí:
 * - Vuốt Hắc Ám Tuyệt Vọng & Vuốt Hư Vô Tai Ương
 * - Súng Lục Hoàng Kim
 * - 4 Loại Ma Đạn Hoàng Kim (Tiêu hao sinh mệnh/máu)
 */
public class MaterialCreationScreen extends Screen {

    public static class CreationCard {
        public final int id;
        public final String name;
        public final String costInfo;
        public final ItemStack displayStack;
        public final int color;
        public int x;
        public int y;
        public int w = 180;
        public int h = 34;

        public CreationCard(int id, String name, String costInfo, ItemStack displayStack, int color) {
            this.id = id;
            this.name = name;
            this.costInfo = costInfo;
            this.displayStack = displayStack;
            this.color = color;
        }
    }

    private final List<CreationCard> cards = new ArrayList<>();
    private CreationCard hoveredCard = null;

    public MaterialCreationScreen() {
        super(Component.literal("Sáng Tạo Vật Chất (Material Creation)"));
    }

    @Override
    protected void init() {
        super.init();
        cards.clear();

        cards.add(new CreationCard(
                ServerboundMaterialCreationPacket.ID_CLAW_DESPAIR,
                "Vuốt Hắc Ám Tuyệt Vọng",
                "§6Thần Khí Huyền Thoại (Tử Hắc Trụ, Nổ Hư Vô)",
                new ItemStack(ModItems.ABYSSAL_CLAW_DESPAIR.get()),
                0xFF8B5CF6
        ));

        cards.add(new CreationCard(
                ServerboundMaterialCreationPacket.ID_CLAW_CALAMITY,
                "Vuốt Hư Vô Tai Ương",
                "§6Thần Khí Huyền Thoại (Trảo Hồn, Nổ Hư Vô)",
                new ItemStack(ModItems.VOID_CLAW_CALAMITY.get()),
                0xFFA855F7
        ));

        cards.add(new CreationCard(
                ServerboundMaterialCreationPacket.ID_GOLDEN_GUN,
                "Súng Lục Hoàng Kim",
                "§eThần Khí Súng Lục Carrera (Bắn 4 loại ma đạn)",
                new ItemStack(ModItems.GOLDEN_GUN.get()),
                0xFFFBBF24
        ));

        cards.add(new CreationCard(
                ServerboundMaterialCreationPacket.ID_BULLET_JUDGEMENT,
                "4x Đạn Thần Tốc Phán Quyết",
                "§c§lTIÊU HAO: 5 MÁU (2.5 TIM) §7- Diệt Hồn Bắn Chết Liền",
                new ItemStack(ModItems.CARRERA_BULLET_JUDGEMENT.get()),
                0xFFEF4444
        ));

        cards.add(new CreationCard(
                ServerboundMaterialCreationPacket.ID_BULLET_ABYSS,
                "8x Đạn Hạch Thâm Uyên",
                "§c§lTIÊU HAO: 2 MÁU (1 TIM) §7- Ma Pháp Hạt Nhân Hố Đen",
                new ItemStack(ModItems.CARRERA_BULLET_ABYSS.get()),
                0xFFEC4899
        ));

        cards.add(new CreationCard(
                ServerboundMaterialCreationPacket.ID_BULLET_GRAVITY,
                "8x Đạn Trọng Lực Sụp Đổ",
                "§c§lTIÊU HAO: 2 MÁU (1 TIM) §7- Đè Bẹp Trọng Lực 12m",
                new ItemStack(ModItems.CARRERA_BULLET_GRAVITY.get()),
                0xFF3B82F6
        ));

        cards.add(new CreationCard(
                ServerboundMaterialCreationPacket.ID_BULLET_RAPID,
                "8x Đạn Hoàng Kim Xạ Kích",
                "§c§lTIÊU HAO: 2 MÁU (1 TIM) §7- Bắn Tốc Độ Ánh Sáng",
                new ItemStack(ModItems.CARRERA_BULLET_RAPID.get()),
                0xFFF59E0B
        ));

        int startY = this.height / 2 - (cards.size() * 38) / 2 + 10;
        int startX = this.width / 2 - 90;

        for (int i = 0; i < cards.size(); i++) {
            CreationCard card = cards.get(i);
            card.x = startX;
            card.y = startY + i * 38;
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);

        int panelW = 340;
        int panelH = 300;
        int panelX = (this.width - panelW) / 2;
        int panelY = (this.height - panelH) / 2;

        // Nền tối ma thuật
        graphics.fillGradient(panelX, panelY, panelX + panelW, panelY + panelH, 0xEE120A24, 0xEE0A0515);
        graphics.renderOutline(panelX, panelY, panelW, panelH, 0xFF8B5CF6);

        // Tiêu đề
        graphics.drawCenteredString(this.font, "§d§l✦ SÁNG TẠO VẬT CHẤT (MATERIAL CREATION) ✦", this.width / 2, panelY + 12, 0xFFFFFF);
        graphics.drawCenteredString(this.font, "§7Chân Ma Vương trích xuất ma tố & sinh mệnh ngưng tụ thần khí", this.width / 2, panelY + 24, 0xAAAAAA);

        hoveredCard = null;

        for (CreationCard card : cards) {
            boolean isHovered = mouseX >= card.x && mouseX <= card.x + card.w && mouseY >= card.y && mouseY <= card.y + card.h;
            if (isHovered) hoveredCard = card;

            int bgCol = isHovered ? 0xCC3B1D6B : 0x881E1038;
            int borderCol = isHovered ? 0xFFF59E0B : card.color;

            graphics.fill(card.x, card.y, card.x + card.w, card.y + card.h, bgCol);
            graphics.renderOutline(card.x, card.y, card.w, card.h, borderCol);

            // Icon
            graphics.renderItem(card.displayStack, card.x + 6, card.y + 8);

            // Tên và chi phí
            graphics.drawString(this.font, card.name, card.x + 28, card.y + 6, isHovered ? 0xFFFFAA : 0xFFFFFF, false);
            graphics.drawString(this.font, card.costInfo, card.x + 28, card.y + 18, 0xDDDDDD, false);
        }

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && hoveredCard != null) {
            ModMessages.sendToServer(new ServerboundMaterialCreationPacket(hoveredCard.id));
            if (this.minecraft != null && this.minecraft.player != null) {
                this.minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1.0F, 1.2F);
            }
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
