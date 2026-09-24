package net.neoforged.neoforge.event.entity.living;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class LivingDamageEvent {
    private final LivingEntity entity;
    private final DamageSource source;
    private float amount;

    public LivingDamageEvent(LivingEntity entity, DamageSource source, float amount) {
        this.entity = entity;
        this.source = source;
        this.amount = amount;
    }

    public LivingEntity getEntity() { return entity; }
    public DamageSource getSource() { return source; }
    public float getAmount() { return amount; }
    public void setAmount(float amount) { this.amount = amount; }

    public static class Pre extends LivingDamageEvent {
        private boolean canceled;
        public Pre(LivingEntity entity, DamageSource source, float amount) { super(entity, source, amount); }
        public void setCanceled(boolean canceled) { this.canceled = canceled; }
        public boolean isCanceled() { return canceled; }
    }

    public static class Post extends LivingDamageEvent {
        public Post(LivingEntity entity, DamageSource source, float amount) { super(entity, source, amount); }
    }
}
