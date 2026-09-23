package net.neoforged.fml.event.lifecycle;

import java.util.function.Consumer;

public class FMLConstructModEvent {
    public void enqueueWork(Runnable work) {
        if (work != null) {
            work.run();
        }
    }
}
