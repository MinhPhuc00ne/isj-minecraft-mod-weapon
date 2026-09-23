package net.neoforged.neoforge.common.damagesource;

import net.minecraft.world.damagesource.DamageSource;

public class DamageContainer {
    private float newDamage;
    private DamageSource source;

    public DamageContainer(float newDamage) { this.newDamage = newDamage; }
    public DamageContainer(DamageSource source, float newDamage) { this.source = source; this.newDamage = newDamage; }
    public float getNewDamage() { return newDamage; }
    public void setNewDamage(float newDamage) { this.newDamage = newDamage; }
    public DamageSource getSource() { return source; }
}
