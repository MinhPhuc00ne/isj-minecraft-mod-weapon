package net.neoforged.neoforge.event.entity.living;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class MobEffectEvent {
    public static class Applicable {
        public enum Result { DENY, DEFAULT, ALLOW, DO_NOT_APPLY }
        private final LivingEntity entity;
        private final MobEffectInstance effectInstance;

        public Applicable(LivingEntity entity, MobEffectInstance effectInstance) {
            this.entity = entity;
            this.effectInstance = effectInstance;
        }

        public LivingEntity getEntity() { return entity; }
        public MobEffectInstance getEffectInstance() { return effectInstance; }
        public void setResult(Result result) {}
        public Result getResult() { return Result.DEFAULT; }
    }
}
