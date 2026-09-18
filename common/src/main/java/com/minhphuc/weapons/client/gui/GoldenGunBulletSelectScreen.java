package com.minhphuc.weapons.client.gui;

import com.minhphuc.weapons.content.tensura.CarreraBulletItem;
import com.minhphuc.weapons.content.tensura.CarreraBulletItem.BulletType;
import com.minhphuc.weapons.content.tensura.GoldenGunItem;
import com.minhphuc.weapons.init.ModItems;
import com.minhphuc.weapons.network.ModMessages;
import com.minhphuc.weapons.network.ServerboundSelectBulletPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Giao diện Vòng Tròn Sáng Chọn Ma Đạn Súng Hoàng Kim (Page Up).
 * Tích hợp tranh Hoàng Sắc Thủy Tổ Carrera, vòng tròn đạn phát sáng, thông tin chi tiết.
 */
public class GoldenGunBulletSelectScreen extends Screen {

    private static final ResourceLocation CARRERA_ART_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("weapons", "textures/gui/carrera_art.png");

    private static final int PANEL_W = 430;
    private static final int PANEL_H = 236;
    private static final int ORBIT_RADIUS = 60;

    public static class BulletNode {
        public final BulletType type;
        public final ItemStack stack;
        public int x;
        public int y;
        public int count;

        public BulletNode(BulletType type, ItemStack stack) {
            this.type = type;
            this.stack = stack;
        }
    }

    private final List<BulletNode> nodes = new ArrayList<>();
    private BulletNode hoveredNode = null;
    private BulletType currentSelectedType = BulletType.JUDGEMENT;

    public GoldenGunBulletSelectScreen() {
        super(Component.literal("Ổ Xoay Ma Đạn Hoàng Kim"));
    }

    @Override
    protected void init() {
        super.init();
        nodes.clear();

        // 4 Loại ma đạn tương ứng
        nodes.add(new BulletNode(BulletType.JUDGEMENT, new ItemStack(ModItems.CARRERA_BULLET_JUDGEMENT.get())));
        nodes.add(new BulletNode(BulletType.ABYSS_CORE, new ItemStack(ModItems.CARRERA_BULLET_ABYSS.get())));
        nodes.add(new BulletNode(BulletType.GRAVITY, new ItemStack(ModItems.CARRERA_BULLET_GRAVITY.get())));
        nodes.add(new BulletNode(BulletType.RAPID, new ItemStack(ModItems.CARRERA_BULLET_RAPID.get())));

        // Lấy loại đạn đang nạp trong súng của người chơi
        if (minecraft != null && minecraft.player != null) {
            ItemStack gun = minecraft.player.getMainHandItem();
            if (!(gun.getItem() instanceof GoldenGunItem)) {
                gun = minecraft.player.getOffhandItem();
            }
            if (gun.getItem() instanceof GoldenGunItem) {
                currentSelectedType = GoldenGunItem.getSelectedBullet(gun);
            }
            updateBulletCounts(minecraft.player);
        }
    }

    private void updateBulletCounts(Player player) {
        for (BulletNode node : nodes) {
            int count = 0;
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack s = player.getInventory().getItem(i);
                if (s.getItem() instanceof CarreraBulletItem bulletItem && bulletItem.getBulletType() == node.type) {
                    count += s.getCount();
                }
            }
            node.count = count;
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        int left = (this.width - PANEL_W) / 2;
        int top = (this.height - PANEL_H) / 2;

        // 1. Khung nền Hoàng Kim & Obsidian
        guiGraphics.fill(left - 2, top - 2, left + PANEL_W + 2, top + PANEL_H + 2, 0xFF140D05);
        guiGraphics.fill(left - 1, top - 1, left + PANEL_W + 1, top + PANEL_H + 1, 0xFFD4AF37);
        guiGraphics.fill(left, top, left + PANEL_W, top + PANEL_H, 0xEE120E1A);

        // Thanh tiêu đề trên
        guiGraphics.fill(left + 8, top + 6, left + PANEL_W - 8, top + 26, 0xFF2A1C38);
        guiGraphics.fill(left + 8, top + 25, left + PANEL_W - 8, top + 26, 0xFFD4AF37);
        guiGraphics.drawString(font, "§6§l[THẦN THOẠI CẤP] §eỔ XOAY MA ĐẠN - HOÀNG SẮC THỦY TỔ CARRERA", left + 14, top + 11, 0xFFFFFFFF, false);

        // 2. Bên trái: Tranh chân dung Hoàng Sắc Thủy Tổ Carrera
        int artX = left + 14;
        int artY = top + 34;
        int artSize = 136;
        guiGraphics.fill(artX - 1, artY - 1, artX + artSize + 1, artY + artSize + 1, 0xFFD4AF37);
        guiGraphics.blit(CARRERA_ART_TEXTURE, artX, artY, 0, 0, artSize, artSize, artSize, artSize);

        // Chú thích dưới ảnh Carrera
        guiGraphics.drawString(font, "§e§lCarrera (Jaune)", artX + 20, artY + artSize + 6, 0xFFFFAA00, false);
        guiGraphics.drawString(font, "§7Phá Diệt Chi Vương Abaddon", artX + 4, artY + artSize + 18, 0xFFBBBBBB, false);
        guiGraphics.drawString(font, "§cKondou's God-grade Gun", artX + 10, artY + artSize + 28, 0xFFFF5555, false);

        // 3. Bên phải: Vòng Tròn Sáng Chọn Đạn (Radial Orbit)
        int orbitCenterX = left + 295;
        int orbitCenterY = top + 105;

        // Vẽ vòng hào quang tròn mờ
        for (int r = ORBIT_RADIUS - 2; r <= ORBIT_RADIUS + 2; r += 2) {
            drawCircleOutline(guiGraphics, orbitCenterX, orbitCenterY, r, 0x44FFDD00);
        }

        // Icon trung tâm: Súng Lục Hoàng Kim
        ItemStack gunIcon = new ItemStack(ModItems.GOLDEN_GUN.get());
        guiGraphics.renderItem(gunIcon, orbitCenterX - 8, orbitCenterY - 8);

        // 4. Định vị và vẽ 4 node đạn trên vòng tròn
        // Node 0: Trên (-90 deg), Node 1: Phải (0 deg), Node 2: Dưới (90 deg), Node 3: Trái (180 deg)
        double[] angles = { -Math.PI / 2, 0, Math.PI / 2, Math.PI };
        hoveredNode = null;

        for (int i = 0; i < nodes.size(); i++) {
            BulletNode node = nodes.get(i);
            double angle = angles[i];
            node.x = (int) (orbitCenterX + Math.cos(angle) * ORBIT_RADIUS);
            node.y = (int) (orbitCenterY + Math.sin(angle) * ORBIT_RADIUS);

            boolean isHovered = (mouseX >= node.x - 14 && mouseX <= node.x + 14 && mouseY >= node.y - 14 && mouseY <= node.y + 14);
            boolean isSelected = (node.type == currentSelectedType);

            if (isHovered) {
                hoveredNode = node;
            }

            // Vẽ viền sáng phát quang cho node
            int haloColor = isSelected ? 0xFFFFDD00 : (isHovered ? 0xFF55FFFF : node.type.color);
            guiGraphics.fill(node.x - 14, node.y - 14, node.x + 14, node.y + 14, 0xCC1A1426);
            guiGraphics.fill(node.x - 13, node.y - 13, node.x + 13, node.y + 13, (haloColor & 0x00FFFFFF) | 0x44000000);

            // Viền ô
            guiGraphics.renderOutline(node.x - 14, node.y - 14, 28, 28, haloColor);

            // Item đạn
            guiGraphics.renderItem(node.stack, node.x - 8, node.y - 8);

            // Số lượng đạn người chơi đang sở hữu
            String countText = node.count > 0 ? "§a" + node.count : "§c0";
            guiGraphics.drawString(font, countText, node.x - font.width(countText) / 2, node.y + 7, 0xFFFFFFFF, true);
        }

        // 5. Khung mô tả chi tiết ở góc dưới bên phải (Căn chỉnh gọn gàng, chống tràn viền)
        int descX = left + 160;
        int descY = top + 175;
        int descW = 260;
        int descH = 52;
        guiGraphics.fill(descX, descY, descX + descW, descY + descH, 0xEE1A1326);
        guiGraphics.renderOutline(descX, descY, descW, descH, 0xFFD4AF37);

        BulletNode displayNode = (hoveredNode != null) ? hoveredNode : getNodeForType(currentSelectedType);
        if (displayNode != null) {
            String title = displayNode.type.vietName + " §7(" + displayNode.type.engName + "§7)";
            guiGraphics.drawString(font, title, descX + 6, descY + 5, 0xFFFFAA00, false);

            String status = (displayNode.count > 0) ? "§aCó sẵn: " + displayNode.count + " viên" : "§cKhông có đạn trong túi đồ!";
            guiGraphics.drawString(font, status, descX + descW - font.width(status) - 8, descY + 5, 0xFFFFFFFF, false);

            // Mô tả chi tiết ngắt dòng vừa vặn
            String desc = displayNode.type.description;
            guiGraphics.drawWordWrap(font, Component.literal("§f" + desc), descX + 6, descY + 18, descW - 12, 0xFFDDDDDD);

            guiGraphics.drawString(font, "§e👉 Nhấp Chuột Trái để nạp loại đạn này vào súng!", descX + 6, descY + 39, 0xFF55FFFF, false);
        }
    }

    private BulletNode getNodeForType(BulletType type) {
        for (BulletNode node : nodes) {
            if (node.type == type) return node;
        }
        return nodes.isEmpty() ? null : nodes.get(0);
    }

    private void drawCircleOutline(GuiGraphics guiGraphics, int cx, int cy, int radius, int color) {
        int steps = 40;
        for (int i = 0; i < steps; i++) {
            double a1 = i * (Math.PI * 2 / steps);
            int px = (int) (cx + Math.cos(a1) * radius);
            int py = (int) (cy + Math.sin(a1) * radius);
            guiGraphics.fill(px, py, px + 1, py + 1, color);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && hoveredNode != null) {
            // Gửi packet chuyển loại đạn lên server
            ModMessages.sendToServer(new ServerboundSelectBulletPacket(hoveredNode.type.id));
            if (minecraft != null) {
                minecraft.getSoundManager().play(
                        net.minecraft.client.resources.sounds.SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.2F)
                );
            }
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
