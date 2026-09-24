package net.unusual.block_factorys_bosses.entity.boss.yeti;

import java.util.UUID;

public interface IIceSpike {
    void setDamage(float damage);
    void setOwnerUUID(UUID uuid);
    void setEvil(boolean evil);
    void setDelay(int delay);
    default void setScale(int scale) {}
    default int getDelay() { return 0; }
}
