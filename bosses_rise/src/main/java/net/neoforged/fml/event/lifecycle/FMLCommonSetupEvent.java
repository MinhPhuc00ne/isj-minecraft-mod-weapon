package net.neoforged.fml.event.lifecycle;

import java.util.function.Consumer;

public class FMLCommonSetupEvent {
    public void enqueueWork(Runnable work) {
        work.run();
    }
}
