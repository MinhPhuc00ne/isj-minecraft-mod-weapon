package net.neoforged.neoforge.client.event;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.DeltaTracker;

public class RenderGuiEvent {
    private final GuiGraphics guiGraphics;
    private final DeltaTracker deltaTracker;

    public RenderGuiEvent(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        this.guiGraphics = guiGraphics;
        this.deltaTracker = deltaTracker;
    }

    public GuiGraphics getGuiGraphics() { return guiGraphics; }
    public DeltaTracker getDeltaTracker() { return deltaTracker; }

    public static class Pre extends RenderGuiEvent {
        public Pre(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
            super(guiGraphics, deltaTracker);
        }
    }

    public static class Post extends RenderGuiEvent {
        public Post(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
            super(guiGraphics, deltaTracker);
        }
    }
}
