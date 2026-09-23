package net.neoforged.neoforge.client.event;

public class RenderBlockScreenEffectEvent {
    public enum OverlayType {
        FIRE, WATER, BLOCK
    }
    public OverlayType getOverlayType() { return OverlayType.FIRE; }
    public void setCanceled(boolean canceled) {}
}
