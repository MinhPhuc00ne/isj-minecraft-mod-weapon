package net.neoforged.neoforge.client.event;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.BossEvent;

public class CustomizeGuiOverlayEvent {
    public static class BossEventProgress {
        public GuiGraphics getGuiGraphics() { return null; }
        public int getX() { return 0; }
        public int getY() { return 0; }
        public net.minecraft.client.gui.components.LerpingBossEvent getBossEvent() { return null; }
        public void setIncrement(int inc) {}
        public int getIncrement() { return 0; }
        public void setCanceled(boolean canceled) {}
    }
}
