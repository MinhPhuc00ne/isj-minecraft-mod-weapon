package net.neoforged.bus.api;

public class Event {
    private boolean canceled = false;
    public boolean isCanceled() { return canceled; }
    public void setCanceled(boolean canceled) { this.canceled = canceled; }
}
