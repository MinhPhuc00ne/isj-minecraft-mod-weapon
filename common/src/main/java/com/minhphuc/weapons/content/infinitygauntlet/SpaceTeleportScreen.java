package com.minhphuc.weapons.content.infinitygauntlet;

import com.minhphuc.weapons.network.ModMessages;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SpaceTeleportScreen extends Screen {

    public SpaceTeleportScreen() {
        super(Component.literal("Space Stone Teleport Menu"));
    }

    @Override
    protected void init() {
        super.init();

        int buttonWidth = 240;
        int buttonHeight = 22;
        int centerX = this.width / 2 - buttonWidth / 2;
        int startY = this.height / 2 - 70;

        // Button 0: Overworld
        this.addRenderableWidget(
            Button.builder(Component.literal("🌍 Thế Giới Thường (Overworld)"), button -> {
                selectDestination(0);
            }).bounds(centerX, startY, buttonWidth, buttonHeight).build()
        );
        startY += 26;

        // Button 1: Nether
        this.addRenderableWidget(
            Button.builder(Component.literal("🔥 Cõi Địa Ngục (Nether Dimension)"), button -> {
                selectDestination(1);
            }).bounds(centerX, startY, buttonWidth, buttonHeight).build()
        );
        startY += 26;

        // Button 2: The End
        this.addRenderableWidget(
            Button.builder(Component.literal("🌌 Thế Giới Kết Thúc (The End)"), button -> {
                selectDestination(2);
            }).bounds(centerX, startY, buttonWidth, buttonHeight).build()
        );
        startY += 26;

        // Button 3: Village
        this.addRenderableWidget(
            Button.builder(Component.literal("🏡 Dịch Chuyển Đến Làng Dân Làng (Village)"), button -> {
                selectDestination(3);
            }).bounds(centerX, startY, buttonWidth, buttonHeight).build()
        );
        startY += 26;

        // Button 4: Ancient City / Warden City
        this.addRenderableWidget(
            Button.builder(Component.literal("🦇 Thành Phố Cổ Warden (Ancient City)"), button -> {
                selectDestination(4);
            }).bounds(centerX, startY, buttonWidth, buttonHeight).build()
        );
    }

    private void selectDestination(int destinationId) {
        ModMessages.sendToServer(new ServerboundSpaceTeleportPacket(destinationId));
        this.onClose();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.fillGradient(0, 0, this.width, this.height, 0xD0000000, 0xEE08162E);

        guiGraphics.drawCenteredString(
            this.font,
            "§9§l🌌 ĐÁ KHÔNG GIAN - MENU DỊCH CHUYỂN ĐA CHIỀU",
            this.width / 2,
            this.height / 2 - 95,
            0xFFFFFF
        );

        guiGraphics.drawCenteredString(
            this.font,
            "§7Chọn chiều không gian hoặc địa danh để xé rách không gian tức thời",
            this.width / 2,
            this.height / 2 + 85,
            0xAAAAAA
        );

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
