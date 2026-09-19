package com.minhphuc.weapons.entity.tensura;

import com.minhphuc.weapons.content.divine.DivineArmorItem;
import com.minhphuc.weapons.content.infinitygauntlet.InfinityGauntletItem;
import com.minhphuc.weapons.content.tensura.VoiceOfTheWorld;
import com.minhphuc.weapons.content.tensura.PrimordialPactItem;
import com.minhphuc.weapons.content.tensura.PrimordialSkillPool;
import com.minhphuc.weapons.init.ModBlocks;
import com.minhphuc.weapons.init.ModItems;
import com.minhphuc.weapons.mixin.DisplayAccessor;
import com.minhphuc.weapons.mixin.ItemDisplayAccessor;
import com.mojang.math.Transformation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class PrimordialDemonEntity extends TamableAnimal {

    private static final EntityDataAccessor<Integer> DATA_DEMON_TYPE =
            SynchedEntityData.defineId(PrimordialDemonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_IS_WINGED =
            SynchedEntityData.defineId(PrimordialDemonEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_RISING =
            SynchedEntityData.defineId(PrimordialDemonEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> DATA_RISING_PROGRESS =
            SynchedEntityData.defineId(PrimordialDemonEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> DATA_COMBAT_CLAWS =
            SynchedEntityData.defineId(PrimordialDemonEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_HAS_BODY =
            SynchedEntityData.defineId(PrimordialDemonEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_NAMED =
            SynchedEntityData.defineId(PrimordialDemonEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_EVOLUTION_TIER =
            SynchedEntityData.defineId(PrimordialDemonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> DATA_CUSTOM_NAME =
            SynchedEntityData.defineId(PrimordialDemonEntity.class, EntityDataSerializers.STRING);

    private final List<PrimordialSkillPool.SkillEntry> randomSkills = new ArrayList<>();

    private int skillCooldownTicks = 0;
    public float wingFlapAngle = 0.0F;
    public float prevWingFlapAngle = 0.0F;

    // Lãnh Địa (Domain Expansion)
    public int activeDomainTicks = 0;
    public Vec3 domainCenter = null;
    public DemonType activeDomainDemonType = null;
    private final List<BlockPos> domainBarrierBlocks = new ArrayList<>();

    // Quản lý các ma trận xoay động (ItemDisplay)
    private final List<ActiveDisplayCircle> activeCircles = new ArrayList<>();

    public static class ActiveDisplayCircle {
        public final Display.ItemDisplay display;
        public int ticksRemaining;
        public float currentAngle;
        public final float rotationSpeed;
        public final float scale;
        public final boolean isHorizontal;
        public final Vec3 relativeOffset;

        public ActiveDisplayCircle(Display.ItemDisplay display, int ticksRemaining, float rotationSpeed, float scale, boolean isHorizontal, Vec3 relativeOffset) {
            this.display = display;
            this.ticksRemaining = ticksRemaining;
            this.rotationSpeed = rotationSpeed;
            this.scale = scale;
            this.isHorizontal = isHorizontal;
            this.relativeOffset = relativeOffset;
            this.currentAngle = 0.0F;
        }
    }

    public PrimordialDemonEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.setTame(false, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 3600.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.38D)
                .add(Attributes.ATTACK_DAMAGE, 160.0D)
                .add(Attributes.ARMOR, 24.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 16.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.95D)
                .add(Attributes.FOLLOW_RANGE, 56.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_DEMON_TYPE, DemonType.NOIR.ordinal());
        builder.define(DATA_IS_WINGED, false);
        builder.define(DATA_IS_RISING, false);
        builder.define(DATA_RISING_PROGRESS, 0.0F);
        builder.define(DATA_COMBAT_CLAWS, false);
        builder.define(DATA_HAS_BODY, false);
        builder.define(DATA_IS_NAMED, false);
        builder.define(DATA_EVOLUTION_TIER, 0);
        builder.define(DATA_CUSTOM_NAME, "");
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new PrimordialSkillGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.4D, true));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.25D, 7.0F, 2.5F));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        // THỦY TỔ CHƯA THU PHỤC: ƯU TIÊN SỐ 1 TẤN CÔNG ÁC MA ĐÃ KÝ KHẾ ƯỚC CỦA NGƯỜI CHƠI TRƯỚC!
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, PrimordialDemonEntity.class, 10, true, false,
                e -> !this.isTame() && e instanceof PrimordialDemonEntity pde && pde.isTame() && pde.isAlive()));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        // Khi CHƯA THU PHỤC: Thử thách người chơi
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, e -> !this.isTame()));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Monster.class, 10, true, false, e -> e instanceof Enemy));
    }

    public DemonType getDemonType() {
        int idx = this.entityData.get(DATA_DEMON_TYPE);
        return DemonType.byIndex(idx);
    }

    public void setDemonType(DemonType type) {
        this.entityData.set(DATA_DEMON_TYPE, type.ordinal());
        updateAttributesForType(type);
        updateDemonNametag();
    }

    public void updateAttributesForType(DemonType type) {
        if (type == null) return;
        double multiplier = 1.0D;
        if (hasPhysicalBody() && isNamed()) {
            multiplier = 8.0D; // 8x ác ma bình thường khi có cả hai đặc tính
        } else if (hasPhysicalBody() || isNamed()) {
            multiplier = 5.0D; // 5x ác ma bình thường khi có 1 trong 2 đặc tính
        }

        var maxHpAttr = this.getAttribute(Attributes.MAX_HEALTH);
        if (maxHpAttr != null) {
            double hp = type.getMaxHealth() * multiplier;
            if (isWinged() && type == DemonType.NOIR) hp *= 1.2D;
            maxHpAttr.setBaseValue(hp);
            if (this.getHealth() > (float) hp) {
                this.setHealth((float) hp);
            }
        }
        var dmgAttr = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (dmgAttr != null) {
            double dmg = type.getAttackDamage() * multiplier;
            if (isWinged() && type == DemonType.NOIR) dmg *= 1.25D;
            dmgAttr.setBaseValue(dmg);
        }
    }

    public void updateDemonNametag() {
        DemonType type = getDemonType();
        String colorPrefix = type != null ? type.getColorName() : "Thủy Tổ";
        if (isNamed() && getCustomDemonName() != null && !getCustomDemonName().isEmpty()) {
            if (hasPhysicalBody()) {
                // Tier 3: Awakened Ma Thần
                this.setCustomName(Component.literal("§6§l★ MA THẦN: §e§l" + getCustomDemonName() + " §6§l★"));
            } else {
                // Tier 2: Named Only
                this.setCustomName(Component.literal("§b§l★ THỦY TỔ: §e§l" + getCustomDemonName() + " §b§l★"));
            }
        } else if (hasPhysicalBody()) {
            // Tier 1: Body Only
            this.setCustomName(Component.literal("§c§l★ THỂ XÁC: §f§l" + colorPrefix.toUpperCase() + " §c§l★"));
        } else {
            // Tier 0: Base / Wild
            this.setCustomName(Component.literal("§7★ THỦY TỔ: §f" + colorPrefix + " §7★"));
        }
        this.setCustomNameVisible(true);
    }

    public String getEffectiveDemonName() {
        if (isNamed() && getCustomDemonName() != null && !getCustomDemonName().isEmpty()) {
            return getCustomDemonName();
        }
        return getDemonType() != null ? getDemonType().getColorName() : "Ác Ma";
    }

    @Override
    public Component getName() {
        if (isNamed() && getCustomDemonName() != null && !getCustomDemonName().isEmpty()) {
            return Component.literal(getCustomDemonName());
        }
        return super.getName();
    }

    @Override
    public Component getDisplayName() {
        if (isNamed() && getCustomDemonName() != null && !getCustomDemonName().isEmpty()) {
            return Component.literal(getCustomDemonName());
        }
        return super.getDisplayName();
    }

    public boolean hasPhysicalBody() {
        return this.entityData.get(DATA_HAS_BODY);
    }

    public void setPhysicalBody(boolean body) {
        this.entityData.set(DATA_HAS_BODY, body);
        updateAttributesForType(getDemonType());
        updateDemonNametag();
    }

    public boolean isNamed() {
        return this.entityData.get(DATA_IS_NAMED);
    }

    public void setNamed(boolean named) {
        this.entityData.set(DATA_IS_NAMED, named);
        updateAttributesForType(getDemonType());
        updateDemonNametag();
    }

    public int getEvolutionTier() {
        return this.entityData.get(DATA_EVOLUTION_TIER);
    }

    public void setEvolutionTier(int tier) {
        this.entityData.set(DATA_EVOLUTION_TIER, tier);
        updateAttributesForType(getDemonType());
        updateDemonNametag();
    }

    public String getCustomDemonName() {
        return this.entityData.get(DATA_CUSTOM_NAME);
    }

    public void setCustomDemonName(String name) {
        this.entityData.set(DATA_CUSTOM_NAME, name != null ? name : "");
        updateDemonNametag();
    }

    public List<PrimordialSkillPool.SkillEntry> getRandomSkills() {
        ensureRandomSkills();
        return this.randomSkills;
    }

    public void setRandomSkillIds(List<String> skillIds) {
        this.randomSkills.clear();
        for (String id : skillIds) {
            PrimordialSkillPool.SkillEntry entry = PrimordialSkillPool.getSkillById(id);
            if (entry != null) {
                this.randomSkills.add(entry);
            }
        }
    }

    public void ensureRandomSkills() {
        int targetCount = (hasPhysicalBody() && isNamed()) ? 5 : ((hasPhysicalBody() || isNamed()) ? 3 : 2);
        if (this.randomSkills.size() < targetCount) {
            List<PrimordialSkillPool.SkillEntry> rolled = PrimordialSkillPool.rollRandomSkills(targetCount, this.random);
            this.randomSkills.clear();
            this.randomSkills.addAll(rolled);
        }
    }

    public double getDemonAttackDamage() {
        var a = getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE);
        return a != null ? a.getValue() : 160.0D;
    }

    public double getAttackDamage() {
        return getDemonAttackDamage();
    }

    public boolean isWinged() {
        return this.entityData.get(DATA_IS_WINGED);
    }

    public void setWinged(boolean winged) {
        this.entityData.set(DATA_IS_WINGED, winged);
        updateAttributesForType(getDemonType());
    }

    public boolean isRising() {
        return this.entityData.get(DATA_IS_RISING);
    }

    public void setRising(boolean rising) {
        this.entityData.set(DATA_IS_RISING, rising);
    }

    public float getRisingProgress() {
        return this.entityData.get(DATA_RISING_PROGRESS);
    }

    public void setRisingProgress(float progress) {
        this.entityData.set(DATA_RISING_PROGRESS, progress);
    }

    public boolean isCombatClawsActive() {
        return this.entityData.get(DATA_COMBAT_CLAWS);
    }

    public void setCombatClawsActive(boolean active) {
        this.entityData.set(DATA_COMBAT_CLAWS, active);
    }

    // =========================================================================
    // MIỄN NHIỄM TUYỆT ĐỐI CHO NGƯỜI CHƠI CẦM GĂNG TAY VÔ CỰC HOẶC MẶC GIÁP THẦN THOẠI
    // =========================================================================
    public static boolean isTargetImmune(LivingEntity target) {
        if (target instanceof Player player) {
            ItemStack main = player.getMainHandItem();
            ItemStack off = player.getOffhandItem();
            if (main.getItem() instanceof InfinityGauntletItem || off.getItem() instanceof InfinityGauntletItem) {
                return true;
            }
            for (ItemStack armor : player.getArmorSlots()) {
                if (armor.getItem() instanceof DivineArmorItem) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isBossTarget(LivingEntity target) {
        if (target == null) return false;
        if (target instanceof WitherBoss || target instanceof EnderDragon || target instanceof Warden) {
            return true;
        }
        return target.getMaxHealth() >= 250.0F;
    }

    private int lastRivalryShoutTick = 0;
    private int lastKillDialogueTick = 0;

    public void broadcastNearbyDialogue(String dialogue, double radius) {
        if (!(this.level() instanceof ServerLevel sl)) return;
        Component msg = Component.literal("§d§l[" + getEffectiveDemonName().toUpperCase() + "] §f\"" + dialogue + "\"");
        for (ServerPlayer p : sl.getPlayers(p -> p.distanceToSqr(this) <= radius * radius)) {
            p.sendSystemMessage(msg);
        }
    }

    public void triggerKillDialogue(LivingEntity victim) {
        if (this.tickCount - lastKillDialogueTick < 10) return;
        lastKillDialogueTick = this.tickCount;

        DemonType type = getDemonType();
        String demonSpeaker = getEffectiveDemonName().toUpperCase();
        if (victim instanceof Player) {
            String[] pool = type.getPlayerKillDialogues();
            if (pool.length > 0) {
                String d = pool[this.random.nextInt(pool.length)];
                broadcastNearbyDialogue(d, 48.0D);
                if (this.level() instanceof ServerLevel sl) {
                    sl.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.PLAYERS, 2.5F, 1.2F);
                }
            }
        } else {
            if (this.level() instanceof ServerLevel sl) {
                List<ServerPlayer> nearbyPlayers = sl.getPlayers(p -> p.distanceToSqr(this) <= 32.0D * 32.0D);
                if (!nearbyPlayers.isEmpty()) {
                    String[] pool = type.getMobKillDialogues();
                    if (pool.length > 0) {
                        String d = pool[this.random.nextInt(pool.length)];
                        for (ServerPlayer p : nearbyPlayers) {
                            p.sendSystemMessage(Component.literal("§d§l[" + demonSpeaker + "] §f\"" + d + "\""));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void awardKillScore(Entity victim, int score, DamageSource damageSource) {
        super.awardKillScore(victim, score, damageSource);
        if (victim instanceof LivingEntity living) {
            triggerKillDialogue(living);
        }
    }

    public void dealDemonicDamage(LivingEntity target, float amount) {
        if (isTargetImmune(target)) {
            // Không hiển thị tin nhắn găng tay/áo giáp theo yêu cầu người chơi
            if (this.level() instanceof ServerLevel sl) {
                sl.sendParticles(ParticleTypes.FLASH, target.getX(), target.getY() + 1.0D, target.getZ(), 1, 0, 0, 0, 0);
            }
            return;
        }

        // Tăng thêm +50% sức mạnh khi đang trong Lãnh Địa của ác ma
        if (activeDomainTicks > 0) {
            amount *= 1.5F;
        }

        // CÂN BẰNG TỶ LỆ GIAO CHIẾN BOSS
        if (isBossTarget(target)) {
            DemonType type = getDemonType();
            float bonus = 1.0F;
            if (hasPhysicalBody() && isNamed()) {
                bonus = 1.25F; // +8% win rate boost against boss
            } else if (hasPhysicalBody() || isNamed()) {
                bonus = 1.15F; // +5% win rate boost against boss
            }

            if (type == DemonType.ROUGE || type == DemonType.NOIR) {
                // 90% áp đảo diệt Boss, 10% sơ suất/chủ quan
                if (this.random.nextFloat() < (hasPhysicalBody() && isNamed() ? 0.02F : 0.05F)) {
                    amount *= 0.85F;
                } else {
                    amount *= (2.6F * bonus);
                }
            } else if (type == DemonType.BLANC) {
                amount *= (2.1F * bonus); // 75% -> 80% / 83%
            } else if (type == DemonType.JAUNE) {
                amount *= (1.9F * bonus); // 65% -> 70% / 73%
            } else if (type == DemonType.VIOLET) {
                amount *= (1.25F * bonus); // 33% -> 38% / 41%
            } else if (type == DemonType.VERT) {
                amount *= (1.15F * bonus); // 30% -> 35% / 38%
            } else if (type == DemonType.BLEU) {
                amount *= (0.85F * bonus); // 20% -> 25% / 28%
            }
        }

        target.hurt(this.level().damageSources().mobAttack(this), amount);
        if (target.isDeadOrDying() || target.getHealth() <= 0) {
            triggerKillDialogue(target);
        }
    }

    public void applyDemonicEffect(LivingEntity target, MobEffectInstance effect) {
        if (isTargetImmune(target)) return;
        target.addEffect(effect);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (target instanceof LivingEntity living) {
            if (isTargetImmune(living)) {
                // Không hiển thị tin nhắn găng tay/áo giáp theo yêu cầu người chơi
                if (this.level() instanceof ServerLevel sl) {
                    sl.sendParticles(ParticleTypes.FLASH, living.getX(), living.getY() + 1.0D, living.getZ(), 1, 0, 0, 0, 0);
                }
                return false;
            }
        }
        return super.doHurtTarget(target);
    }

    @Override
    public void tick() {
        super.tick();

        // Đồng bộ thuộc tính theo DemonType nếu chưa gán
        if (!this.level().isClientSide() && this.tickCount == 1) {
            updateAttributesForType(getDemonType());
        }

        // Animation vỗ cánh
        this.prevWingFlapAngle = this.wingFlapAngle;
        if (isWinged()) {
            this.wingFlapAngle += 0.15F;
            if (!this.onGround() && this.getDeltaMovement().y < 0.0D) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
                this.fallDistance = 0.0F;
            }
        }

        // Cập nhật trạng thái bung móng vuốt khi có mục tiêu
        if (!this.level().isClientSide()) {
            boolean inCombat = this.getTarget() != null && this.getTarget().isAlive();
            if (this.isCombatClawsActive() != inCombat) {
                this.setCombatClawsActive(inCombat);
            }
        }

        if (this.level().isClientSide()) {
            // Hào quang ma tộc đặc trưng quanh người theo từng trạng thái tiến hóa
            boolean isAwakened = hasPhysicalBody() && isNamed();
            float particleChance = isAwakened ? 0.85F : (isWinged() ? 0.6F : 0.3F);
            if (this.random.nextFloat() < particleChance) {
                double ox = (this.random.nextDouble() - 0.5D) * (isAwakened ? 1.0D : 0.8D);
                double oy = this.random.nextDouble() * 1.9D;
                double oz = (this.random.nextDouble() - 0.5D) * (isAwakened ? 1.0D : 0.8D);

                DemonType type = getDemonType();
                if (isAwakened) {
                    // Ma Thần Tối Thượng: Hào quang cuồn cuộn với linh diễm và cánh cổng ma giới
                    this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + ox, this.getY() + oy, this.getZ() + oz, 0, 0.03, 0);
                    this.level().addParticle(ParticleTypes.PORTAL, this.getX() + ox, this.getY() + oy, this.getZ() + oz, 0, 0.02, 0);
                    if (this.random.nextFloat() < 0.2F) {
                        this.level().addParticle(ParticleTypes.TOTEM_OF_UNDYING, this.getX() + ox, this.getY() + oy, this.getZ() + oz, 0, 0.05, 0);
                    }
                } else if (hasPhysicalBody()) {
                    // Thể Xác Ma Thiết: Tia lửa kim loại
                    this.level().addParticle(ParticleTypes.CRIT, this.getX() + ox, this.getY() + oy, this.getZ() + oz, 0, 0.02, 0);
                } else if (isNamed()) {
                    // Đã Ban Danh Xưng: Bụi phù chú huyền bí
                    this.level().addParticle(ParticleTypes.ENCHANT, this.getX() + ox, this.getY() + oy, this.getZ() + oz, 0, 0.03, 0);
                }

                if (type == DemonType.ROUGE) {
                    this.level().addParticle(ParticleTypes.FLAME, this.getX() + ox, this.getY() + oy, this.getZ() + oz, 0, 0.02, 0);
                } else if (type == DemonType.NOIR) {
                    this.level().addParticle(ParticleTypes.DRAGON_BREATH, this.getX() + ox, this.getY() + oy, this.getZ() + oz, 0, 0.01, 0);
                } else if (type == DemonType.BLANC) {
                    this.level().addParticle(ParticleTypes.END_ROD, this.getX() + ox, this.getY() + oy, this.getZ() + oz, 0, 0.01, 0);
                } else if (type == DemonType.JAUNE) {
                    this.level().addParticle(ParticleTypes.CRIT, this.getX() + ox, this.getY() + oy, this.getZ() + oz, 0, 0.02, 0);
                    this.level().addParticle(ParticleTypes.LAVA, this.getX() + ox, this.getY() + oy, this.getZ() + oz, 0, 0.01, 0);
                } else if (type == DemonType.VIOLET) {
                    this.level().addParticle(ParticleTypes.WITCH, this.getX() + ox, this.getY() + oy, this.getZ() + oz, 0, 0.01, 0);
                } else if (type == DemonType.BLEU) {
                    this.level().addParticle(ParticleTypes.SNOWFLAKE, this.getX() + ox, this.getY() + oy, this.getZ() + oz, 0, 0.01, 0);
                } else if (type == DemonType.VERT) {
                    this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getX() + ox, this.getY() + oy, this.getZ() + oz, 0, 0.02, 0);
                }
            }
            return;
        }

        // Tự động hồi phục sinh lực ma tộc từ từ (10 HP/s khi đã thuần phục, 3-6 HP/s khi hoang dã)
        if (this.tickCount % 20 == 0 && this.getHealth() < this.getMaxHealth()) {
            float healAmt = this.isTame() ? 10.0F : ((getDemonType() == DemonType.ROUGE || getDemonType() == DemonType.NOIR) ? 6.0F : 3.0F);
            this.heal(healAmt);
            if (this.isTame() && this.level() instanceof ServerLevel sl) {
                sl.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getY() + 1.2D, this.getZ(), 3, 0.3D, 0.4D, 0.3D, 0.02D);
            }
        }

        // THỦY TỔ CHƯA THU PHỤC: ƯU TIÊN SỐ 1 TẤN CÔNG ÁC MA ĐÃ THUẦN PHỤC CỦA NGƯỜI CHƠI TRƯỚC!
        if (!this.level().isClientSide() && !this.isTame() && this.tickCount % 10 == 0) {
            if (this.getTarget() instanceof Player || this.getTarget() == null) {
                List<PrimordialDemonEntity> tamedDemons = this.level().getEntitiesOfClass(
                        PrimordialDemonEntity.class,
                        this.getBoundingBox().inflate(36.0D),
                        pde -> pde.isAlive() && pde.isTame()
                );
                if (!tamedDemons.isEmpty()) {
                    PrimordialDemonEntity rival = tamedDemons.get(0);
                    if (this.getTarget() != rival) {
                        this.setTarget(rival);
                        if (this.tickCount - lastRivalryShoutTick > 200) {
                            lastRivalryShoutTick = this.tickCount;
                            String rivalryMsg = getDemonType().getRivalryDialogue(rival.getDemonType());
                            broadcastNearbyDialogue(rivalryMsg, 48.0D);
                            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WARDEN_ROAR, SoundSource.HOSTILE, 2.0F, 1.2F);
                        }
                    }
                }
            }
        }

        if (skillCooldownTicks > 0) {
            skillCooldownTicks--;
        }

        // Cập nhật và xoay các vòng tròn ma thuật ItemDisplay
        updateDisplayCircles();

        // Xử lý Lãnh Địa (Domain Expansion)
        tickActiveDomain();

        // Trạng thái trồi lên từ nghi thức triệu hồi
        if (isRising()) {
            this.setDeltaMovement(0, 0, 0);
            this.setNoGravity(true);
        } else {
            this.setNoGravity(false);
        }
    }

    private void updateDisplayCircles() {
        if (this.activeCircles.isEmpty()) return;

        List<ActiveDisplayCircle> toRemove = new ArrayList<>();
        for (ActiveDisplayCircle circle : this.activeCircles) {
            circle.ticksRemaining--;
            if (circle.ticksRemaining <= 0 || !circle.display.isAlive()) {
                if (circle.display.isAlive()) {
                    circle.display.discard();
                }
                toRemove.add(circle);
                continue;
            }

            // Cập nhật góc xoay
            circle.currentAngle += circle.rotationSpeed;
            DisplayAccessor dispAcc = (DisplayAccessor) circle.display;

            Quaternionf rot;
            if (circle.isHorizontal) {
                rot = new Quaternionf().rotateX((float) Math.toRadians(90.0F)).rotateZ((float) Math.toRadians(circle.currentAngle));
            } else {
                rot = new Quaternionf().rotateY((float) Math.toRadians(this.getYRot())).rotateZ((float) Math.toRadians(circle.currentAngle));
            }

            dispAcc.weapons$setTransformation(new Transformation(
                    new Vector3f(0.0F, 0.0F, 0.0F),
                    rot,
                    new Vector3f(circle.scale, circle.scale, 0.01F),
                    null
            ));
        }
        this.activeCircles.removeAll(toRemove);
    }

    public boolean isDomainBarrierBlock(Block block) {
        return block == ModBlocks.DOMAIN_BARRIER_NOIR.get() ||
               block == ModBlocks.DOMAIN_BARRIER_ROUGE.get() ||
               block == ModBlocks.DOMAIN_BARRIER_BLANC.get() ||
               block == ModBlocks.DOMAIN_BARRIER_JAUNE.get() ||
               block == ModBlocks.DOMAIN_BARRIER_VIOLET.get() ||
               block == ModBlocks.DOMAIN_BARRIER_BLEU.get() ||
               block == ModBlocks.DOMAIN_BARRIER_VERT.get();
    }

    public void cleanDomainBarriers() {
        if (domainBarrierBlocks.isEmpty()) return;
        if (this.level() instanceof ServerLevel sl) {
            for (BlockPos p : domainBarrierBlocks) {
                if (isDomainBarrierBlock(sl.getBlockState(p).getBlock())) {
                    sl.setBlock(p, Blocks.AIR.defaultBlockState(), 3);
                }
            }
            if (domainCenter != null) {
                sl.playSound(null, domainCenter.x, domainCenter.y, domainCenter.z, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 2.5F, 1.2F);
            }
        }
        domainBarrierBlocks.clear();
    }

    @Override
    public void remove(RemovalReason reason) {
        cleanDomainBarriers();
        super.remove(reason);
    }

    private void tickActiveDomain() {
        if (activeDomainTicks <= 0) {
            cleanDomainBarriers();
            return;
        }
        activeDomainTicks--;

        ServerLevel level = (ServerLevel) this.level();
        DemonType type = this.activeDomainDemonType != null ? this.activeDomainDemonType : getDemonType();
        double radius = 16.0D;

        // Vẽ chu vi biên giới hạt kết giới Lãnh Địa
        for (int a = 0; a < 360; a += 15) {
            double rad = Math.toRadians(a);
            double px = domainCenter.x + Math.cos(rad) * radius;
            double pz = domainCenter.z + Math.sin(rad) * radius;

            if (type == DemonType.NOIR) {
                level.sendParticles(ParticleTypes.DRAGON_BREATH, px, domainCenter.y + 0.5D, pz, 2, 0.2D, 1.0D, 0.2D, 0.02D);
                level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, px, domainCenter.y + 1.2D, pz, 1, 0.1D, 0.5D, 0.1D, 0.01D);
            } else if (type == DemonType.ROUGE) {
                level.sendParticles(ParticleTypes.FLAME, px, domainCenter.y + 0.5D, pz, 2, 0.2D, 1.2D, 0.2D, 0.02D);
            } else if (type == DemonType.BLANC) {
                level.sendParticles(ParticleTypes.END_ROD, px, domainCenter.y + 0.8D, pz, 1, 0.1D, 0.8D, 0.1D, 0.01D);
            } else if (type == DemonType.JAUNE) {
                level.sendParticles(ParticleTypes.CRIT, px, domainCenter.y + 0.5D, pz, 2, 0.2D, 0.5D, 0.2D, 0.05D);
                level.sendParticles(ParticleTypes.LAVA, px, domainCenter.y + 1.0D, pz, 1, 0.1D, 0.5D, 0.1D, 0.02D);
            } else if (type == DemonType.VIOLET) {
                level.sendParticles(ParticleTypes.WITCH, px, domainCenter.y + 0.5D, pz, 2, 0.2D, 0.8D, 0.2D, 0.02D);
            } else if (type == DemonType.BLEU) {
                level.sendParticles(ParticleTypes.SNOWFLAKE, px, domainCenter.y + 0.5D, pz, 2, 0.2D, 0.8D, 0.2D, 0.01D);
            } else if (type == DemonType.VERT) {
                level.sendParticles(ParticleTypes.HAPPY_VILLAGER, px, domainCenter.y + 0.5D, pz, 2, 0.2D, 0.8D, 0.2D, 0.02D);
            }
        }

        // Tác động lên các thực thể bên trong kết giới
        List<LivingEntity> entities = level.getEntitiesOfClass(
                LivingEntity.class,
                new AABB(domainCenter.x - 20, domainCenter.y - 6, domainCenter.z - 20, domainCenter.x + 20, domainCenter.y + 18, domainCenter.z + 20)
        );

        for (LivingEntity e : entities) {
            if (e == this || e == this.getOwner()) continue;
            if (isTargetImmune(e)) continue;

            double dist = e.distanceToSqr(domainCenter);

            // Giam hãm: Nếu chạm rìa kết giới (14m - 19m), giật ngược lại tâm kết giới
            if (dist >= 14.0D * 14.0D && dist <= 19.0D * 19.0D) {
                Vec3 pull = domainCenter.subtract(e.position()).normalize().scale(0.85D);
                e.setDeltaMovement(pull.x, 0.2D, pull.z);
                e.hasImpulse = true;
                level.sendParticles(ParticleTypes.FLASH, e.getX(), e.getY() + 1.0D, e.getZ(), 1, 0, 0, 0, 0);
            }

            // Áp chế suy yếu bên trong lãnh địa
            if (dist <= radius * radius) {
                applyDemonicEffect(e, new MobEffectInstance(MobEffects.DARKNESS, 40, 0));
                applyDemonicEffect(e, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2));

                if (this.tickCount % 20 == 0) {
                    dealDemonicDamage(e, 35.0F);
                }
            }
        }

        // Tăng cường +50% SỨC MẠNH cho Ác ma khi đứng trong Lãnh Địa
        this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 1));
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 1));
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 2));
    }

    public void activateDomain(DemonType type, Vec3 center, String domainName, String voiceLine) {
        this.activeDomainTicks = 300; // 15 giây
        this.domainCenter = center;
        this.activeDomainDemonType = type;
        cleanDomainBarriers();

        ServerLevel level = (ServerLevel) this.level();
        Block barrierBlock = type.getDomainBarrierBlock().get();
        int radius = 16;
        BlockPos cPos = BlockPos.containing(center);

        // Tạo vòm kết giới khối thực tế theo màu ác ma
        for (int x = -radius; x <= radius; x++) {
            for (int y = -3; y <= 16; y++) {
                for (int z = -radius; z <= radius; z++) {
                    double dist = Math.sqrt(x * x + (y * 0.9) * (y * 0.9) + z * z);
                    if (dist >= radius - 0.9 && dist <= radius + 0.9) {
                        BlockPos bPos = cPos.offset(x, y, z);
                        if (level.getBlockState(bPos).isAir() || level.getBlockState(bPos).liquid()) {
                            level.setBlock(bPos, barrierBlock.defaultBlockState(), 3);
                            domainBarrierBlocks.add(bPos);
                        }
                    }
                }
            }
        }

        // Tạo vòng ma pháp khổng lồ 16m dưới chân Lãnh Địa
        ItemStack circleStack = (type == DemonType.JAUNE)
                ? new ItemStack(ModItems.MAGIC_CIRCLE_JAUNE_NUCLEAR.get())
                : new ItemStack(type.getMagicCircleItem().get());
        spawnRotatingCircle(level, center.add(0, 0.05D, 0), 16.0F, 1.5F, 300, true, type.getGlowColor(), circleStack);

        // Thông báo toàn bộ người chơi trong bán kính 64 khối
        for (ServerPlayer sp : level.getPlayers(p -> p.distanceToSqr(this) <= 64.0D * 64.0D)) {
            sp.connection.send(new ClientboundSetTitleTextPacket(Component.literal("§8§l【 LÃNH ĐỊA 】 §c§l" + domainName.toUpperCase())));
            sp.connection.send(new ClientboundSetSubtitleTextPacket(Component.literal("§e" + type.getColorName() + " §7đã giải phóng Không Gian Tuyệt Đối! (+50% Sức Mạnh)")));
            sp.displayClientMessage(Component.literal("§4§l[LÃNH ĐỊA THỦY TỔ] §c" + type.getColorName() + ": §f\"" + voiceLine + "\""), false);
        }
    }

    public Display.ItemDisplay spawnRotatingCircle(ServerLevel level, Vec3 pos, float scale, float rotSpeed, int duration, boolean isHorizontal, int glowColor, ItemStack circleStack) {
        Display.ItemDisplay display = EntityType.ITEM_DISPLAY.create(level);
        if (display != null) {
            display.moveTo(pos.x, pos.y, pos.z, 0.0F, 0.0F);
            ItemDisplayAccessor itemAcc = (ItemDisplayAccessor) display;
            DisplayAccessor dispAcc = (DisplayAccessor) display;

            itemAcc.weapons$setItemStack(circleStack != null && !circleStack.isEmpty() ? circleStack : new ItemStack(getDemonType().getMagicCircleItem().get()));
            itemAcc.weapons$setItemTransform(ItemDisplayContext.FIXED);
            dispAcc.weapons$setBillboardConstraints(Display.BillboardConstraints.FIXED);
            display.setGlowingTag(true);
            dispAcc.weapons$setGlowColorOverride(glowColor);
            dispAcc.weapons$setViewRange(6.0F);

            Quaternionf rot;
            if (isHorizontal) {
                rot = new Quaternionf().rotateX((float) Math.toRadians(90.0F));
            } else {
                rot = new Quaternionf().rotateY((float) Math.toRadians(this.getYRot()));
            }

            dispAcc.weapons$setTransformation(new Transformation(
                    new Vector3f(0.0F, 0.0F, 0.0F),
                    rot,
                    new Vector3f(scale, scale, 0.01F),
                    null
            ));

            level.addFreshEntity(display);
            this.activeCircles.add(new ActiveDisplayCircle(display, duration, rotSpeed, scale, isHorizontal, Vec3.ZERO));
        }
        return display;
    }

    public Display.ItemDisplay spawnRotatingCircle(ServerLevel level, Vec3 pos, float scale, float rotSpeed, int duration, boolean isHorizontal, int glowColor) {
        return spawnRotatingCircle(level, pos, scale, rotSpeed, duration, isHorizontal, glowColor, new ItemStack(getDemonType().getMagicCircleItem().get()));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;

        if (this.isOwnedBy(player)) {
            if (player.isShiftKeyDown()) {
                if (this.getDemonType() == DemonType.NOIR) {
                    boolean nowWinged = !this.isWinged();
                    this.setWinged(nowWinged);

                    this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                            nowWinged ? SoundEvents.ENDER_DRAGON_FLAP : SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.8F, 0.9F);
                    this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                            SoundEvents.WARDEN_HEARTBEAT, SoundSource.PLAYERS, 1.5F, 1.2F);

                    if (this.level() instanceof ServerLevel sl) {
                        sl.sendParticles(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY() + 1.0D, this.getZ(), 30, 0.4D, 0.6D, 0.4D, 0.08D);
                        sl.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY() + 1.2D, this.getZ(), 20, 0.5D, 0.5D, 0.5D, 0.05D);
                    }

                    if (nowWinged) {
                        player.displayClientMessage(Component.literal("§8§l[NOIR] §c§l★ GIẢI PHÓNG HẮC DỰC MA VƯƠNG ★\n§7\"Kufufufu... Ngài cho phép tôi giải phóng đôi cánh hắc ám sao? Thật vinh hạnh tột cùng!\""), false);
                    } else {
                        player.displayClientMessage(Component.literal("§8§l[NOIR] §f§l✦ THU HỒI CÁNH - DẠNG QUẢN GIA ✦\n§7\"Như người mong muốn, tôi sẽ giữ phong thái thanh lịch khi bên cạnh ngài.\""), false);
                    }
                    return InteractionResult.SUCCESS;
                } else {
                    player.displayClientMessage(Component.literal("§d§l[" + this.getDemonType().getColorName().toUpperCase() + "] §f" + this.getDemonType().getSummonDialogue()), false);
                    return InteractionResult.SUCCESS;
                }
            } else {
                boolean newSitting = !this.isOrderedToSit();
                this.setOrderedToSit(newSitting);
                this.jumping = false;
                this.navigation.stop();

                if (newSitting) {
                    player.displayClientMessage(Component.literal("§e§l[" + this.getDemonType().getColorName() + "] §7đang §cĐỨNG CHỜ LỆNH§7 của chủ nhân."), true);
                } else {
                    player.displayClientMessage(Component.literal("§e§l[" + this.getDemonType().getColorName() + "] §aĐI THEO BẢO VỆ§7 chủ nhân!"), true);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (isRising()) return false;
        if (source.is(net.minecraft.tags.DamageTypeTags.IS_FIRE) || source.is(net.minecraft.tags.DamageTypeTags.IS_FALL)) {
            return false;
        }

        // TÍNH TOÁN RỦI RO CHIẾN ĐẤU VỚI BOSS
        if (source.getEntity() instanceof LivingEntity attacker && isBossTarget(attacker)) {
            DemonType type = getDemonType();
            if (type == DemonType.ROUGE || type == DemonType.NOIR) {
                if (this.random.nextFloat() < 0.10F) {
                    // 10% "chủ quan vẫn sẽ thua"
                    amount *= 2.0F;
                    if (this.level() instanceof ServerLevel sl) {
                        sl.sendParticles(ParticleTypes.ANGRY_VILLAGER, this.getX(), this.getY() + 1.8D, this.getZ(), 6, 0.3D, 0.3D, 0.3D, 0);
                    }
                } else {
                    amount *= 0.25F; // 90% kháng sát thương boss
                }
            } else if (type == DemonType.BLANC) {
                amount *= 0.35F; // 75%
            } else if (type == DemonType.JAUNE) {
                amount *= 0.45F; // 65%
            } else if (type == DemonType.VIOLET) {
                amount *= 0.80F; // 33%
            } else if (type == DemonType.VERT) {
                amount *= 0.85F; // 30%
            } else if (type == DemonType.BLEU) {
                amount *= 1.15F; // 20%
            }
        }

        // KHI BỊ ĐÁNH VỀ 0 MÁU
        if (amount >= this.getHealth()) {
            if (!this.isTame()) {
                Player conqueror = null;
                if (source.getEntity() instanceof Player p) {
                    conqueror = p;
                } else if (this.getLastHurtByMob() instanceof Player p) {
                    conqueror = p;
                } else if (this.getTarget() instanceof Player p) {
                    conqueror = p;
                } else if (this.level() instanceof ServerLevel sl) {
                    conqueror = sl.getNearestPlayer(this, 32.0D);
                }

                if (conqueror != null) {
                    // Rớt vật phẩm Khế Ước Thủy Tổ tương ứng của ác ma đó
                    ItemStack pactStack = PrimordialPactItem.createPact(getDemonType(), conqueror);
                    ItemEntity itemEntity = new ItemEntity(
                            this.level(), this.getX(), this.getY() + 0.5D, this.getZ(), pactStack
                    );
                    itemEntity.setDefaultPickUpDelay();
                    this.level().addFreshEntity(itemEntity);

                    if (this.level() instanceof ServerLevel sl) {
                        sl.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, this.getX(), this.getY() + 1.2D, this.getZ(), 45, 0.5D, 0.8D, 0.5D, 0.15D);
                        sl.sendParticles(ParticleTypes.FLASH, this.getX(), this.getY() + 1.5D, this.getZ(), 2, 0, 0, 0, 0);
                        sl.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.PLAYERS, 2.5F, 1.0F);
                    }

                    conqueror.displayClientMessage(Component.literal("§6§l★ ĐÃ ĐÁNH BẠI: §e" + getDemonType().getColorName() + " (" + getDemonType().getTitleVi() + ")!"), false);
                    conqueror.displayClientMessage(Component.literal("§a✔ Rơi ra: §e" + pactStack.getHoverName().getString() + "§a. Bấm Chuột Phải vào cuộn khế ước để triệu hồi và họ sẽ phục tùng bạn!"), false);

                    this.discard();
                    return false;
                }
            } else {
                // Đã thuần phục: Phục tùng trừ khi bị ai đó khác giết chết
                cleanDomainBarriers();
                if (this.getOwner() instanceof ServerPlayer sp) {
                    destroyOwnerPact(sp);
                }
            }
        }

        return super.hurt(source, amount);
    }

    @Override
    public void die(DamageSource cause) {
        cleanDomainBarriers();
        if (this.isTame() && this.getOwner() instanceof ServerPlayer sp) {
            destroyOwnerPact(sp);
        }
        super.die(cause);
    }

    public void destroyOwnerPact(ServerPlayer owner) {
        DemonType type = getDemonType();
        boolean foundAndRemoved = false;
        for (int i = 0; i < owner.getInventory().getContainerSize(); i++) {
            ItemStack stack = owner.getInventory().getItem(i);
            if (!stack.isEmpty() && stack.getItem() instanceof PrimordialPactItem) {
                DemonType pactType = PrimordialPactItem.getDemonType(stack);
                String uuidStr = com.minhphuc.weapons.data.ItemStackDataHelper.getString(stack, "DemonUUID");
                if (pactType == type || this.getStringUUID().equals(uuidStr)) {
                    owner.getInventory().setItem(i, ItemStack.EMPTY);
                    foundAndRemoved = true;
                }
            }
        }
        if (foundAndRemoved) {
            owner.level().playSound(null, owner.getX(), owner.getY(), owner.getZ(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 2.5F, 0.8F);
            owner.level().playSound(null, owner.getX(), owner.getY(), owner.getZ(), SoundEvents.WITHER_DEATH, SoundSource.PLAYERS, 2.0F, 0.9F);
            if (owner.level() instanceof ServerLevel sl) {
                sl.sendParticles(ParticleTypes.LAVA, owner.getX(), owner.getY() + 1.0D, owner.getZ(), 25, 0.4D, 0.5D, 0.4D, 0.1D);
                sl.sendParticles(ParticleTypes.LARGE_SMOKE, owner.getX(), owner.getY() + 1.0D, owner.getZ(), 20, 0.4D, 0.5D, 0.4D, 0.05D);
            }
            owner.sendSystemMessage(Component.literal("§4§l✦ [KHẾ ƯỚC TAN VỠ] ✦\n§cÁc Ma Thủy Tổ §6" + getEffectiveDemonName() + " §cđã tử trận trên chiến trường!\n§7Khế ước linh hồn đã bị đứt gãy, cuộn khế ước đã hóa thành tro tàn và tiêu biến vĩnh viễn!"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("DemonType", getDemonType().ordinal());
        tag.putBoolean("IsWinged", isWinged());
        tag.putBoolean("HasPhysicalBody", hasPhysicalBody());
        tag.putBoolean("IsNamed", isNamed());
        tag.putInt("EvolutionTier", getEvolutionTier());
        tag.putString("CustomDemonName", getCustomDemonName());

        net.minecraft.nbt.ListTag skillsList = new net.minecraft.nbt.ListTag();
        for (PrimordialSkillPool.SkillEntry entry : randomSkills) {
            skillsList.add(net.minecraft.nbt.StringTag.valueOf(String.valueOf(entry.id())));
        }
        tag.put("PrimordialSkills", skillsList);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("DemonType")) {
            setDemonType(DemonType.byIndex(tag.getInt("DemonType")));
        }
        if (tag.contains("IsWinged")) {
            setWinged(tag.getBoolean("IsWinged"));
        }
        if (tag.contains("HasPhysicalBody")) {
            setPhysicalBody(tag.getBoolean("HasPhysicalBody"));
        }
        if (tag.contains("IsNamed")) {
            setNamed(tag.getBoolean("IsNamed"));
        }
        if (tag.contains("EvolutionTier")) {
            setEvolutionTier(tag.getInt("EvolutionTier"));
        }
        if (tag.contains("CustomDemonName")) {
            setCustomDemonName(tag.getString("CustomDemonName"));
        }
        if (tag.contains("PrimordialSkills", 9)) {
            net.minecraft.nbt.ListTag list = tag.getList("PrimordialSkills", 8);
            List<String> ids = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                ids.add(list.getString(i));
            }
            setRandomSkillIds(ids);
        }
        ensureRandomSkills();
        updateAttributesForType(getDemonType());
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    public boolean canCastSkill() {
        return skillCooldownTicks <= 0 && !isRising();
    }

    public void triggerSkillCooldown(int ticks) {
        this.skillCooldownTicks = ticks;
    }

    // =========================================================================
    // CỘT SÁNG & MA TRẬN PHÁP BẢO (ITEM DISPLAY HELPER)
    // =========================================================================
    public Display.ItemDisplay createPillarDisplay(ServerLevel level, Vec3 center,
                                                  float scaleX, float scaleY, float scaleZ,
                                                  int glowColor, int duration) {
        Display.ItemDisplay display = EntityType.ITEM_DISPLAY.create(level);
        if (display != null) {
            display.moveTo(center.x, center.y, center.z, 0.0F, 0.0F);
            ItemDisplayAccessor itemDisplayAcc = (ItemDisplayAccessor) display;
            DisplayAccessor displayAcc = (DisplayAccessor) display;

            itemDisplayAcc.weapons$setItemStack(new ItemStack(ModItems.JACOB_LIGHT_PILLAR.get()));
            itemDisplayAcc.weapons$setItemTransform(ItemDisplayContext.FIXED);
            displayAcc.weapons$setBillboardConstraints(Display.BillboardConstraints.FIXED);
            display.setGlowingTag(true);
            displayAcc.weapons$setGlowColorOverride(glowColor);
            displayAcc.weapons$setViewRange(12.0F);

            displayAcc.weapons$setTransformation(new Transformation(
                    new Vector3f(0.0F, scaleY / 2.0F, 0.0F),
                    new Quaternionf(),
                    new Vector3f(scaleX, scaleY, scaleZ),
                    null
            ));

            level.addFreshEntity(display);
            this.activeCircles.add(new ActiveDisplayCircle(display, duration, 0.0F, scaleX, false, Vec3.ZERO));
        }
        return display;
    }

    public void announceSkill(String skillName) {
        if (this.level() instanceof ServerLevel level) {
            for (ServerPlayer sp : level.getPlayers(p -> p.distanceToSqr(this) <= 36.0D * 36.0D)) {
                sp.displayClientMessage(Component.literal("§d§l[" + getDemonType().getColorName().toUpperCase() + "] §6§l✦ " + skillName + " ✦"), true);
            }
        }
    }

    // =========================================================================
    // HỆ THỐNG KỸ NĂNG TÁC CHIẾN TOÀN DIỆN CHO 7 ÁC MA THỦY TỔ
    // =========================================================================

    public void executeNormalAttack(LivingEntity target, DemonType type) {
        if (!(this.level() instanceof ServerLevel level)) return;
        Vec3 tPos = target.position();
        this.swing(InteractionHand.MAIN_HAND);

        if (type == DemonType.NOIR) {
            level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 2.0F, 0.9F);
            level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.EVOKER_FANGS_ATTACK, SoundSource.PLAYERS, 1.8F, 1.3F);
            level.sendParticles(ParticleTypes.SWEEP_ATTACK, tPos.x, tPos.y + 1.2D, tPos.z, 3, 0.4D, 0.4D, 0.4D, 0);
            level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, tPos.x, tPos.y + 1.0D, tPos.z, 15, 0.3D, 0.3D, 0.3D, 0.05D);
            dealDemonicDamage(target, 160.0F);
        } else if (type == DemonType.ROUGE) {
            level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 2.0F, 1.0F);
            level.sendParticles(ParticleTypes.FLAME, tPos.x, tPos.y + 1.0D, tPos.z, 20, 0.4D, 0.4D, 0.4D, 0.08D);
            dealDemonicDamage(target, 160.0F);
        } else if (type == DemonType.BLANC) {
            level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.PLAYERS, 2.0F, 1.8F);
            level.sendParticles(ParticleTypes.END_ROD, tPos.x, tPos.y + 1.0D, tPos.z, 15, 0.3D, 0.3D, 0.3D, 0.05D);
            dealDemonicDamage(target, 80.0F);
        } else if (type == DemonType.JAUNE) {
            level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 1.8F, 1.2F);
            level.sendParticles(ParticleTypes.CRIT, tPos.x, tPos.y + 1.0D, tPos.z, 25, 0.5D, 0.5D, 0.5D, 0.1D);
            dealDemonicDamage(target, 70.0F);
        } else if (type == DemonType.VIOLET) {
            level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.PLAYERS, 2.0F, 1.2F);
            level.sendParticles(ParticleTypes.WITCH, tPos.x, tPos.y + 1.0D, tPos.z, 20, 0.4D, 0.4D, 0.4D, 0.05D);
            dealDemonicDamage(target, 40.0F);
        } else if (type == DemonType.BLEU) {
            level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 2.0F, 1.2F);
            level.sendParticles(ParticleTypes.SNOWFLAKE, tPos.x, tPos.y + 1.0D, tPos.z, 20, 0.4D, 0.4D, 0.4D, 0.02D);
            dealDemonicDamage(target, 30.0F);
        } else {
            level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 2.0F, 1.4F);
            level.sendParticles(ParticleTypes.HAPPY_VILLAGER, tPos.x, tPos.y + 1.0D, tPos.z, 20, 0.4D, 0.4D, 0.4D, 0.05D);
            dealDemonicDamage(target, 36.0F);
        }
    }

    public void executeSkillOne(LivingEntity target, DemonType type) {
        if (!(this.level() instanceof ServerLevel level)) return;
        Vec3 dPos = this.position();
        Vec3 tPos = target.position();
        Vec3 look = this.getLookAngle();
        Vec3 circlePos = dPos.add(look.scale(1.8D)).add(0, 1.2D, 0);

        announceSkill(type.getColorName() + ": " + getSkillOneName(type));
        level.playSound(null, dPos.x, dPos.y, dPos.z, SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 2.5F, 1.2F);

        if (type == DemonType.JAUNE) {
            spawnRotatingCircle(level, circlePos, 3.8F, 8.0F, 45, false, type.getGlowColor(), new ItemStack(ModItems.MAGIC_CIRCLE_JAUNE.get()));
            spawnRotatingCircle(level, circlePos, 2.5F, -10.0F, 45, false, 0xFF4500, new ItemStack(ModItems.MAGIC_CIRCLE_JAUNE_DESTRUCTION.get()));
        } else {
            spawnRotatingCircle(level, circlePos, 3.2F, 6.0F, 40, false, type.getGlowColor(), new ItemStack(type.getMagicCircleItem().get()));
            spawnRotatingCircle(level, circlePos, 2.2F, -8.0F, 40, false, type.getGlowColor(), new ItemStack(type.getMagicCircleItem().get()));
        }

        Vec3 dir = tPos.add(0, 0.8D, 0).subtract(circlePos).normalize();
        for (double d = 0.5D; d <= circlePos.distanceTo(tPos); d += 0.8D) {
            Vec3 p = circlePos.add(dir.scale(d));
            if (type == DemonType.NOIR) {
                level.sendParticles(ParticleTypes.DRAGON_BREATH, p.x, p.y, p.z, 3, 0.1D, 0.1D, 0.1D, 0.01D);
                level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, p.x, p.y, p.z, 2, 0.05D, 0.05D, 0.05D, 0.02D);
            } else if (type == DemonType.ROUGE) {
                level.sendParticles(ParticleTypes.FLAME, p.x, p.y, p.z, 4, 0.1D, 0.1D, 0.1D, 0.03D);
            } else if (type == DemonType.BLANC) {
                level.sendParticles(ParticleTypes.END_ROD, p.x, p.y, p.z, 3, 0.05D, 0.05D, 0.05D, 0.01D);
            } else if (type == DemonType.JAUNE) {
                level.sendParticles(ParticleTypes.CRIT, p.x, p.y, p.z, 4, 0.1D, 0.1D, 0.1D, 0.05D);
                level.sendParticles(ParticleTypes.LAVA, p.x, p.y, p.z, 2, 0.05D, 0.05D, 0.05D, 0.02D);
            } else if (type == DemonType.VIOLET) {
                level.sendParticles(ParticleTypes.WITCH, p.x, p.y, p.z, 3, 0.1D, 0.1D, 0.1D, 0.02D);
            } else if (type == DemonType.BLEU) {
                level.sendParticles(ParticleTypes.SNOWFLAKE, p.x, p.y, p.z, 4, 0.1D, 0.1D, 0.1D, 0.02D);
            } else {
                level.sendParticles(ParticleTypes.HAPPY_VILLAGER, p.x, p.y, p.z, 3, 0.1D, 0.1D, 0.1D, 0.02D);
            }
        }

        level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, tPos.x, tPos.y + 1.0D, tPos.z, 1, 0, 0, 0, 0);
        level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 2.0F, 1.2F);

        float damage = (float) (type.getAttackDamage() * 1.5D);
        dealDemonicDamage(target, damage);
    }

    public void executeSkillTwo(LivingEntity target, DemonType type) {
        if (!(this.level() instanceof ServerLevel level)) return;
        Vec3 tPos = target.position();

        announceSkill(type.getColorName() + ": " + getSkillTwoName(type));

        if (type == DemonType.NOIR) {
            Vec3 behind = tPos.add(target.getLookAngle().scale(-1.8D));
            this.teleportTo(behind.x, behind.y, behind.z);
            level.playSound(null, behind.x, behind.y, behind.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 2.0F, 1.4F);
            level.sendParticles(ParticleTypes.FLASH, behind.x, behind.y + 1.2D, behind.z, 2, 0, 0, 0, 0);
            level.sendParticles(ParticleTypes.SWEEP_ATTACK, tPos.x, tPos.y + 1.2D, tPos.z, 6, 0.5D, 0.5D, 0.5D, 0);
            dealDemonicDamage(target, 280.0F);
            applyDemonicEffect(target, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 3));
        } else if (type == DemonType.ROUGE) {
            Vec3 dashPos = tPos.add(this.getLookAngle().scale(2.5D));
            this.teleportTo(dashPos.x, dashPos.y, dashPos.z);
            level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 2.5F, 0.8F);
            level.sendParticles(ParticleTypes.LAVA, tPos.x, tPos.y + 1.0D, tPos.z, 30, 0.5D, 0.8D, 0.5D, 0.1D);
            dealDemonicDamage(target, 280.0F);
            target.setRemainingFireTicks(180);
        } else if (type == DemonType.BLANC) {
            level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 2.0F, 1.6F);
            Vec3 pull = this.position().subtract(tPos).normalize().scale(1.2D);
            target.setDeltaMovement(pull.add(0, 0.4D, 0));
            target.hasImpulse = true;
            dealDemonicDamage(target, 150.0F);
        } else if (type == DemonType.JAUNE) {
            level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 2.5F, 1.4F);
            level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 2.0F, 1.6F);

            for (int ring = 0; ring < 4; ring++) {
                double offAng = ring * Math.PI / 2.0D;
                Vec3 ringPos = this.position().add(Math.cos(offAng) * 2.0D, 1.2D, Math.sin(offAng) * 2.0D);
                spawnRotatingCircle(level, ringPos, 1.5F, 15.0F, 30, false, 0xFFCC00, new ItemStack(ModItems.MAGIC_CIRCLE_JAUNE_DESTRUCTION.get()));
            }

            for (int b = 0; b < 6; b++) {
                Vec3 bulletPos = tPos.add((this.random.nextDouble() - 0.5D) * 3.0D, 0.5D + b * 0.3D, (this.random.nextDouble() - 0.5D) * 3.0D);
                level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, bulletPos.x, bulletPos.y, bulletPos.z, 1, 0, 0, 0, 0);
                level.sendParticles(ParticleTypes.CRIT, bulletPos.x, bulletPos.y, bulletPos.z, 20, 0.3D, 0.3D, 0.3D, 0.1D);
            }
            dealDemonicDamage(target, 140.0F);
            target.setDeltaMovement(0, 1.2D, 0);
            target.hasImpulse = true;
        } else if (type == DemonType.VIOLET) {
            level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.BREWING_STAND_BREW, SoundSource.PLAYERS, 2.5F, 0.8F);
            level.sendParticles(ParticleTypes.WITCH, tPos.x, tPos.y + 1.0D, tPos.z, 50, 2.0D, 1.0D, 2.0D, 0.05D);
            dealDemonicDamage(target, 70.0F);
            applyDemonicEffect(target, new MobEffectInstance(MobEffects.POISON, 120, 2));
        } else if (type == DemonType.BLEU) {
            level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.PLAYER_HURT_FREEZE, SoundSource.PLAYERS, 2.5F, 1.0F);
            level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.PACKED_ICE.defaultBlockState()), tPos.x, tPos.y + 0.8D, tPos.z, 25, 0.3D, 0.8D, 0.3D, 0.05D);
            dealDemonicDamage(target, 55.0F);
            target.setTicksFrozen(240);
        } else {
            level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 2.5F, 1.5F);
            for (int a = 0; a < 360; a += 30) {
                double rad = Math.toRadians(a);
                level.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX() + Math.cos(rad) * 3.0D, this.getY() + 1.0D, this.getZ() + Math.sin(rad) * 3.0D, 3, 0, 0, 0, 0.05D);
            }
            dealDemonicDamage(target, 60.0F);
            if (this.getOwner() instanceof LivingEntity owner) {
                owner.heal(25.0F);
            }
        }
    }

    public void executeSkillThree(LivingEntity target, DemonType type) {
        if (!(this.level() instanceof ServerLevel level)) return;
        Vec3 tPos = target.position();

        announceSkill(type.getColorName() + ": " + getSkillThreeName(type));

        if (type == DemonType.JAUNE) {
            spawnRotatingCircle(level, tPos.add(0, 0.05D, 0), 8.5F, 4.0F, 80, true, 0xFFA500, new ItemStack(ModItems.MAGIC_CIRCLE_JAUNE_DESTRUCTION.get()));
            spawnRotatingCircle(level, tPos.add(0, 7.0D, 0), 6.5F, -6.0F, 80, true, 0xFFFF00, new ItemStack(ModItems.MAGIC_CIRCLE_JAUNE_NUCLEAR.get()));
            spawnRotatingCircle(level, tPos.add(0, 14.0D, 0), 8.5F, 4.0F, 80, true, 0xFFCC00, new ItemStack(ModItems.MAGIC_CIRCLE_JAUNE.get()));
        } else {
            spawnRotatingCircle(level, tPos.add(0, 0.05D, 0), 7.0F, 4.0F, 70, true, type.getGlowColor(), new ItemStack(type.getMagicCircleItem().get()));
            spawnRotatingCircle(level, tPos.add(0, 14.0D, 0), 7.0F, -4.0F, 70, true, type.getGlowColor(), new ItemStack(type.getMagicCircleItem().get()));
        }

        level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 3.0F, 1.5F);
        level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 2.5F, 0.8F);

        for (double y = 0.0D; y <= 14.0D; y += 0.5D) {
            for (int i = 0; i < 6; i++) {
                double angle = (i * Math.PI / 3.0D) + (y * 0.4D);
                double px = tPos.x + Math.cos(angle) * 1.6D;
                double pz = tPos.z + Math.sin(angle) * 1.6D;

                if (type == DemonType.NOIR) {
                    level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, px, tPos.y + y, pz, 2, 0.05D, 0.05D, 0.05D, 0.01D);
                    level.sendParticles(ParticleTypes.DRAGON_BREATH, px, tPos.y + y, pz, 1, 0.1D, 0.1D, 0.1D, 0.02D);
                } else if (type == DemonType.ROUGE) {
                    level.sendParticles(ParticleTypes.FLAME, px, tPos.y + y, pz, 3, 0.1D, 0.1D, 0.1D, 0.02D);
                } else if (type == DemonType.BLANC) {
                    level.sendParticles(ParticleTypes.END_ROD, px, tPos.y + y, pz, 2, 0.05D, 0.05D, 0.05D, 0.01D);
                } else if (type == DemonType.JAUNE) {
                    level.sendParticles(ParticleTypes.CRIT, px, tPos.y + y, pz, 3, 0.1D, 0.1D, 0.1D, 0.02D);
                    level.sendParticles(ParticleTypes.LAVA, px, tPos.y + y, pz, 1, 0.05D, 0.05D, 0.05D, 0.01D);
                } else if (type == DemonType.VIOLET) {
                    level.sendParticles(ParticleTypes.WITCH, px, tPos.y + y, pz, 3, 0.1D, 0.1D, 0.1D, 0.02D);
                } else if (type == DemonType.BLEU) {
                    level.sendParticles(ParticleTypes.SNOWFLAKE, px, tPos.y + y, pz, 3, 0.1D, 0.1D, 0.1D, 0.02D);
                } else {
                    level.sendParticles(ParticleTypes.HAPPY_VILLAGER, px, tPos.y + y, pz, 2, 0.1D, 0.1D, 0.1D, 0.02D);
                }
            }
        }

        level.sendParticles(ParticleTypes.FLASH, tPos.x, tPos.y + 2.0D, tPos.z, 2, 0, 0, 0, 0);
        List<LivingEntity> targets = level.getEntitiesOfClass(
                LivingEntity.class,
                new AABB(tPos.x - 6, tPos.y - 2, tPos.z - 6, tPos.x + 6, tPos.y + 15, tPos.z + 6),
                e -> e != this && e != this.getOwner()
        );

        float damage = (float) (type.getAttackDamage() * 2.2D);
        if (type == DemonType.JAUNE) {
            damage = 240.0F;
        }
        for (LivingEntity e : targets) {
            dealDemonicDamage(e, damage);
        }
    }

    // =========================================================================
    // KỸ NĂNG MỚI 1: THẤT SẮC THẦN TRỤ • CỘT SÁNG CỰC ĐẠI
    // =========================================================================
    public void executeChromaticLightPillar(LivingEntity target, DemonType type) {
        if (!(this.level() instanceof ServerLevel level)) return;
        Vec3 tPos = target.position();

        announceSkill(type.getColorName() + ": Thất Sắc Thần Trụ • Cột Sáng Cực Đại");

        // 1. Tạo Cột Sáng Thần Khí vươn cao chọc trời 60m
        createPillarDisplay(level, tPos, 6.5F, 60.0F, 6.5F, type.getGlowColor(), 80);

        // 2. Pháp trận xoay dưới đất và trên đỉnh cột sáng
        spawnRotatingCircle(level, tPos.add(0, 0.05D, 0), 8.0F, 6.0F, 80, true, type.getGlowColor());
        spawnRotatingCircle(level, tPos.add(0, 16.0D, 0), 8.0F, -6.0F, 80, true, type.getGlowColor());

        // 3. Vòng xoáy 7 sắc cầu vồng bao quanh thân cột sáng
        for (double y = 0.0D; y <= 24.0D; y += 1.0D) {
            for (int c = 0; c < 7; c++) {
                double angle = (c * (2 * Math.PI / 7)) + (y * 0.5D);
                double px = tPos.x + Math.cos(angle) * 2.8D;
                double pz = tPos.z + Math.sin(angle) * 2.8D;

                switch (c) {
                    case 0 -> level.sendParticles(ParticleTypes.FLAME, px, tPos.y + y, pz, 1, 0, 0, 0, 0.01D);
                    case 1 -> level.sendParticles(ParticleTypes.LAVA, px, tPos.y + y, pz, 1, 0, 0, 0, 0.01D);
                    case 2 -> level.sendParticles(ParticleTypes.CRIT, px, tPos.y + y, pz, 1, 0, 0, 0, 0.01D);
                    case 3 -> level.sendParticles(ParticleTypes.HAPPY_VILLAGER, px, tPos.y + y, pz, 1, 0, 0, 0, 0.01D);
                    case 4 -> level.sendParticles(ParticleTypes.SNOWFLAKE, px, tPos.y + y, pz, 1, 0, 0, 0, 0.01D);
                    case 5 -> level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, px, tPos.y + y, pz, 1, 0, 0, 0, 0.01D);
                    case 6 -> level.sendParticles(ParticleTypes.WITCH, px, tPos.y + y, pz, 1, 0, 0, 0, 0.01D);
                }
            }
        }

        level.playSound(null, tPos.x, tPos.y + 4.0D, tPos.z, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 3.5F, 1.3F);
        level.playSound(null, tPos.x, tPos.y + 4.0D, tPos.z, SoundEvents.CHORUS_FLOWER_GROW, SoundSource.PLAYERS, 3.0F, 1.0F);
        level.playSound(null, tPos.x, tPos.y + 4.0D, tPos.z, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 2.5F, 1.1F);

        List<LivingEntity> targets = level.getEntitiesOfClass(
                LivingEntity.class,
                new AABB(tPos.x - 7, tPos.y - 2, tPos.z - 7, tPos.x + 7, tPos.y + 20, tPos.z + 7),
                e -> e != this && e != this.getOwner()
        );

        for (LivingEntity e : targets) {
            dealDemonicDamage(e, 340.0F);
            e.setDeltaMovement(0, 0.6D, 0);
            e.hasImpulse = true;
        }
    }

    // =========================================================================
    // KỸ NĂNG MỚI 2: LINH TỬ BĂNG HOẠI (DISINTEGRATION)
    // =========================================================================
    public void executeDisintegration(LivingEntity target, DemonType type) {
        if (!(this.level() instanceof ServerLevel level)) return;
        Vec3 tPos = target.position();

        announceSkill(type.getColorName() + ": Linh Tử Băng Hoại (Disintegration)");

        // 1. Pháp trận đồng tâm đa tầng
        spawnRotatingCircle(level, tPos.add(0, 0.05D, 0), 8.5F, 6.0F, 70, true, 0xFFFFFF);
        spawnRotatingCircle(level, tPos.add(0, 4.0D, 0), 6.0F, -8.0F, 70, true, 0xF0F4F8);
        spawnRotatingCircle(level, tPos.add(0, 8.5D, 0), 7.0F, 4.0F, 70, false, 0xFFFFFF);

        // 2. Chùm tia linh tử phân rã trắng chói lòa
        for (double y = 0.0D; y <= 20.0D; y += 0.4D) {
            for (int i = 0; i < 8; i++) {
                double angle = (i * Math.PI / 4.0D) + (y * 0.3D);
                double px = tPos.x + Math.cos(angle) * 1.4D;
                double pz = tPos.z + Math.sin(angle) * 1.4D;
                level.sendParticles(ParticleTypes.END_ROD, px, tPos.y + y, pz, 1, 0, 0, 0, 0.02D);
                level.sendParticles(ParticleTypes.ELECTRIC_SPARK, px, tPos.y + y, pz, 1, 0, 0, 0, 0.01D);
            }
        }

        level.sendParticles(ParticleTypes.FLASH, tPos.x, tPos.y + 2.0D, tPos.z, 3, 0, 0, 0, 0);
        level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, tPos.x, tPos.y + 1.0D, tPos.z, 2, 0, 0, 0, 0);

        level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 3.5F, 1.2F);
        level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 3.0F, 1.6F);
        level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 3.0F, 1.8F);

        List<LivingEntity> targets = level.getEntitiesOfClass(
                LivingEntity.class,
                new AABB(tPos.x - 8, tPos.y - 2, tPos.z - 8, tPos.x + 8, tPos.y + 16, tPos.z + 8),
                e -> e != this && e != this.getOwner()
        );

        for (LivingEntity e : targets) {
            dealDemonicDamage(e, 420.0F);
            applyDemonicEffect(e, new MobEffectInstance(MobEffects.WEAKNESS, 160, 3));
            applyDemonicEffect(e, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 3));
        }
    }

    // =========================================================================
    // KỸ NĂNG MỚI 3: CHIÊU ĐỘC BẢN 1 CỦA TỪNG ÁC MA
    // =========================================================================
    public void executeSignatureSkillOne(LivingEntity target, DemonType type) {
        if (!(this.level() instanceof ServerLevel level)) return;
        Vec3 tPos = target.position();

        announceSkill(type.getColorName() + ": " + getSignatureOneName(type));

        switch (type) {
            case ROUGE -> {
                // Xích Diễm Hỏa Long Bộc Phá: Lượn sóng rồng lửa càn quét 20m
                Vec3 dir = target.position().subtract(this.position()).normalize();
                for (double d = 1.0D; d <= 20.0D; d += 0.8D) {
                    Vec3 p = this.position().add(dir.scale(d)).add(0, Math.sin(d * 0.8D) * 1.2D, 0);
                    level.sendParticles(ParticleTypes.FLAME, p.x, p.y + 1.0D, p.z, 8, 0.4D, 0.4D, 0.4D, 0.05D);
                    level.sendParticles(ParticleTypes.LAVA, p.x, p.y + 1.0D, p.z, 3, 0.2D, 0.2D, 0.2D, 0.02D);
                    if (d % 4.0D < 1.0D) {
                        level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, p.x, p.y + 1.0D, p.z, 1, 0, 0, 0, 0);
                    }
                }
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.ENDER_DRAGON_GROWL, SoundSource.PLAYERS, 3.0F, 0.9F);
                dealDemonicDamage(target, 300.0F);
                target.setRemainingFireTicks(200);
            }
            case NOIR -> {
                // Hắc Ám Không Gian Phân Cắt: Cắt đứt không gian tạo hố đen nuốt chửng
                Vec3 mid = this.position().add(target.position()).scale(0.5D);
                for (double y = 0.0D; y <= 6.0D; y += 0.4D) {
                    level.sendParticles(ParticleTypes.SWEEP_ATTACK, mid.x, mid.y + y, mid.z, 4, 0.2D, 0.2D, 0.2D, 0);
                    level.sendParticles(ParticleTypes.DRAGON_BREATH, mid.x, mid.y + y, mid.z, 6, 0.3D, 0.3D, 0.3D, 0.02D);
                    level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, mid.x, mid.y + y, mid.z, 4, 0.2D, 0.2D, 0.2D, 0.02D);
                }
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 2.5F, 1.5F);
                dealDemonicDamage(target, 320.0F);
                applyDemonicEffect(target, new MobEffectInstance(MobEffects.DARKNESS, 120, 0));
            }
            case BLANC -> {
                // Bạch Quang Tuyệt Diệt Tuyến: 4 gương ma pháp bắn chùm laser trắng hội tụ
                for (int i = 0; i < 4; i++) {
                    double ang = i * Math.PI / 2.0D;
                    Vec3 mPos = tPos.add(Math.cos(ang) * 5.0D, 2.5D, Math.sin(ang) * 5.0D);
                    spawnRotatingCircle(level, mPos, 2.0F, 10.0F, 40, false, 0xF0F4F8);

                    Vec3 bDir = tPos.subtract(mPos).normalize();
                    for (double d = 0.5D; d <= 5.0D; d += 0.5D) {
                        Vec3 bp = mPos.add(bDir.scale(d));
                        level.sendParticles(ParticleTypes.END_ROD, bp.x, bp.y, bp.z, 2, 0.02D, 0.02D, 0.02D, 0.01D);
                        level.sendParticles(ParticleTypes.FLASH, bp.x, bp.y, bp.z, 1, 0, 0, 0, 0);
                    }
                }
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.PLAYERS, 3.0F, 1.8F);
                dealDemonicDamage(target, 280.0F);
            }
            case JAUNE -> {
                // Carrera Gatling Barrage: Xả 16 ma đạn hoàng kim liên hoàn
                for (int b = 0; b < 16; b++) {
                    Vec3 bp = tPos.add((this.random.nextDouble() - 0.5D) * 4.0D, this.random.nextDouble() * 2.0D, (this.random.nextDouble() - 0.5D) * 4.0D);
                    level.sendParticles(ParticleTypes.CRIT, bp.x, bp.y, bp.z, 15, 0.2D, 0.2D, 0.2D, 0.05D);
                    level.sendParticles(ParticleTypes.LAVA, bp.x, bp.y, bp.z, 3, 0.1D, 0.1D, 0.1D, 0.02D);
                }
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 2.5F, 1.6F);
                level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 2.0F, 1.4F);
                dealDemonicDamage(target, 300.0F);
            }
            case VIOLET -> {
                // Tử Hoa Mạn Đà La: Khai nở mandala hoa sen độc trên mặt đất 8m
                for (int a = 0; a < 360; a += 20) {
                    double rad = Math.toRadians(a);
                    double rx = tPos.x + Math.cos(rad) * 4.5D;
                    double rz = tPos.z + Math.sin(rad) * 4.5D;
                    level.sendParticles(ParticleTypes.WITCH, rx, tPos.y + 0.2D, rz, 4, 0.1D, 0.2D, 0.1D, 0.02D);
                    level.sendParticles(ParticleTypes.DRAGON_BREATH, rx, tPos.y + 0.4D, rz, 2, 0.1D, 0.1D, 0.1D, 0.01D);
                }
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.BREWING_STAND_BREW, SoundSource.PLAYERS, 3.0F, 0.8F);
                dealDemonicDamage(target, 200.0F);
                applyDemonicEffect(target, new MobEffectInstance(MobEffects.WITHER, 160, 3));
                applyDemonicEffect(target, new MobEffectInstance(MobEffects.POISON, 160, 3));
            }
            case BLEU -> {
                // Băng Cực Phong Ấn Vĩnh Cửu: Đóng băng mục tiêu trong kết giới hàn băng
                for (int a = 0; a < 360; a += 30) {
                    double rad = Math.toRadians(a);
                    level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.PACKED_ICE.defaultBlockState()),
                            tPos.x + Math.cos(rad) * 2.0D, tPos.y + 1.0D, tPos.z + Math.sin(rad) * 2.0D, 10, 0.2D, 0.5D, 0.2D, 0.02D);
                }
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 3.0F, 0.8F);
                target.setTicksFrozen(360);
                dealDemonicDamage(target, 180.0F);
                applyDemonicEffect(target, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 160, 4));
            }
            case VERT -> {
                // Lục Bảo Phong Bạo Cuồng Nộ: Lốc xoáy ngọc bích hất tung kẻ địch lên không
                for (double y = 0.0D; y <= 12.0D; y += 0.8D) {
                    double r = 1.0D + y * 0.3D;
                    for (int i = 0; i < 4; i++) {
                        double ang = (i * Math.PI / 2.0D) + (y * 0.8D);
                        level.sendParticles(ParticleTypes.HAPPY_VILLAGER, tPos.x + Math.cos(ang) * r, tPos.y + y, tPos.z + Math.sin(ang) * r, 2, 0, 0, 0, 0.05D);
                    }
                }
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 3.0F, 0.7F);
                target.setDeltaMovement(0, 1.4D, 0);
                target.hasImpulse = true;
                dealDemonicDamage(target, 200.0F);
            }
        }
    }

    // =========================================================================
    // KỸ NĂNG MỚI 4: CHIÊU ĐỘC BẢN 2 CỦA TỪNG ÁC MA
    // =========================================================================
    public void executeSignatureSkillTwo(LivingEntity target, DemonType type) {
        if (!(this.level() instanceof ServerLevel level)) return;
        Vec3 tPos = target.position();

        announceSkill(type.getColorName() + ": " + getSignatureTwoName(type));

        switch (type) {
            case ROUGE -> {
                // Hỏa Ngục Toái Phiến Tiễn: 8 mũi giáo lửa giáng xuống từ vòng tròn trên không
                for (int i = 0; i < 8; i++) {
                    double ang = i * Math.PI / 4.0D;
                    Vec3 spearPos = tPos.add(Math.cos(ang) * 4.0D, 8.0D, Math.sin(ang) * 4.0D);
                    level.sendParticles(ParticleTypes.FLAME, spearPos.x, spearPos.y, spearPos.z, 15, 0.2D, 1.0D, 0.2D, 0.05D);
                    level.sendParticles(ParticleTypes.LAVA, spearPos.x, spearPos.y, spearPos.z, 3, 0.1D, 0.5D, 0.1D, 0.02D);
                }
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 3.0F, 1.2F);
                dealDemonicDamage(target, 280.0F);
            }
            case NOIR -> {
                // Phán Quyết Tuyệt Vọng: Máy chém bóng tối rơi từ thiên đỉnh
                Vec3 dropPos = tPos.add(0, 10.0D, 0);
                for (double y = 0.0D; y <= 10.0D; y += 0.5D) {
                    level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, tPos.x, tPos.y + y, tPos.z, 4, 0.3D, 0.2D, 0.3D, 0.02D);
                    level.sendParticles(ParticleTypes.FLASH, tPos.x, tPos.y + y, tPos.z, 1, 0, 0, 0, 0);
                }
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 3.0F, 0.7F);
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.WARDEN_ROAR, SoundSource.PLAYERS, 2.5F, 1.2F);
                dealDemonicDamage(target, 350.0F);
            }
            case BLANC -> {
                // Bạch Viêm Huyết Vũ: Cơn mưa lửa trắng rơi từ đám mây 12m
                for (int d = 0; d < 25; d++) {
                    double ox = (this.random.nextDouble() - 0.5D) * 10.0D;
                    double oz = (this.random.nextDouble() - 0.5D) * 10.0D;
                    level.sendParticles(ParticleTypes.END_ROD, tPos.x + ox, tPos.y + 6.0D, tPos.z + oz, 3, 0.1D, 0.8D, 0.1D, 0.04D);
                }
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 2.5F, 1.8F);
                dealDemonicDamage(target, 240.0F);
            }
            case JAUNE -> {
                // Trọng Lực Thâm Uyên Điểm Kỳ Dị: Hút mọi quái trong 16m về tâm rồi nổ tung
                List<LivingEntity> cluster = level.getEntitiesOfClass(
                        LivingEntity.class,
                        this.getBoundingBox().inflate(16.0D),
                        e -> e != this && e != this.getOwner()
                );
                for (LivingEntity e : cluster) {
                    Vec3 pull = tPos.subtract(e.position()).normalize().scale(1.2D);
                    e.setDeltaMovement(pull.add(0, 0.3D, 0));
                    e.hasImpulse = true;
                    dealDemonicDamage(e, 270.0F);
                }
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 3.0F, 1.6F);
            }
            case VIOLET -> {
                // Huyết Độc Ma Trảo Tam Kích: Tốc biến 3 lần cào xé mục tiêu
                for (int k = 0; k < 3; k++) {
                    double ang = k * (2 * Math.PI / 3);
                    Vec3 dashP = tPos.add(Math.cos(ang) * 2.0D, 0.5D, Math.sin(ang) * 2.0D);
                    level.sendParticles(ParticleTypes.SWEEP_ATTACK, dashP.x, dashP.y + 1.0D, dashP.z, 5, 0.3D, 0.3D, 0.3D, 0);
                    level.sendParticles(ParticleTypes.WITCH, dashP.x, dashP.y + 1.0D, dashP.z, 15, 0.3D, 0.3D, 0.3D, 0.05D);
                }
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 3.0F, 1.5F);
                dealDemonicDamage(target, 260.0F);
            }
            case BLEU -> {
                // Bão Băng Ngưng Đọng: Cơn mưa gai băng cực hàn
                for (int b = 0; b < 20; b++) {
                    double ox = (this.random.nextDouble() - 0.5D) * 8.0D;
                    double oz = (this.random.nextDouble() - 0.5D) * 8.0D;
                    level.sendParticles(ParticleTypes.SNOWFLAKE, tPos.x + ox, tPos.y + 5.0D, tPos.z + oz, 5, 0.2D, 0.6D, 0.2D, 0.02D);
                }
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.PLAYER_HURT_FREEZE, SoundSource.PLAYERS, 3.0F, 1.2F);
                dealDemonicDamage(target, 220.0F);
            }
            case VERT -> {
                // Lục Bảo Thánh Thuẫn: Ban giáp bảo hộ cho chủ nhân & ác ma
                level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 3.0F, 1.4F);
                spawnRotatingCircle(level, this.position().add(0, 1.0D, 0), 4.5F, 12.0F, 60, true, 0x22C55E);
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 2));
                this.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 3));
                if (this.getOwner() instanceof LivingEntity owner) {
                    owner.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 2));
                    owner.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 3));
                    owner.heal(30.0F);
                }
            }
        }
    }

    // =========================================================================
    // KỸ NĂNG MỚI 5: CHIÊU ĐỘC BẢN 3 CỦA TỪNG ÁC MA
    // =========================================================================
    public void executeSignatureSkillThree(LivingEntity target, DemonType type) {
        if (!(this.level() instanceof ServerLevel level)) return;
        Vec3 tPos = target.position();

        announceSkill(type.getColorName() + ": " + getSignatureThreeName(type));

        switch (type) {
            case ROUGE -> {
                // Hỏa Đế Trảm Không: Nhảy vút lên không rồi bổ xuống chém nứt mặt đất
                level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, tPos.x, tPos.y + 1.0D, tPos.z, 2, 0, 0, 0, 0);
                level.sendParticles(ParticleTypes.LAVA, tPos.x, tPos.y + 0.5D, tPos.z, 40, 1.5D, 0.5D, 1.5D, 0.1D);
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 3.5F, 0.8F);
                dealDemonicDamage(target, 360.0F);
            }
            case NOIR -> {
                // Hắc Linh Hồn Thôn Phệ: Hút cạn sinh lực kẻ thù xung quanh hồi máu cho Diablo
                List<LivingEntity> victims = level.getEntitiesOfClass(
                        LivingEntity.class,
                        this.getBoundingBox().inflate(16.0D),
                        e -> e != this && e != this.getOwner()
                );
                for (LivingEntity v : victims) {
                    for (double d = 0.2D; d <= 1.0D; d += 0.2D) {
                        Vec3 p = v.position().lerp(this.position(), d);
                        level.sendParticles(ParticleTypes.SOUL, p.x, p.y + 1.0D, p.z, 2, 0.1D, 0.1D, 0.1D, 0.01D);
                    }
                    dealDemonicDamage(v, 200.0F);
                }
                this.heal(60.0F);
                level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.PLAYERS, 3.0F, 1.4F);
            }
            case BLANC -> {
                // Thánh Ma Phân Rã Ba Động: 3 vòng sóng xung kích phân rã
                for (int ring = 1; ring <= 3; ring++) {
                    double r = ring * 3.0D;
                    for (int a = 0; a < 360; a += 15) {
                        double rad = Math.toRadians(a);
                        level.sendParticles(ParticleTypes.END_ROD, tPos.x + Math.cos(rad) * r, tPos.y + 0.5D, tPos.z + Math.sin(rad) * r, 2, 0, 0, 0, 0.02D);
                    }
                }
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 3.0F, 1.6F);
                dealDemonicDamage(target, 260.0F);
                applyDemonicEffect(target, new MobEffectInstance(MobEffects.WEAKNESS, 120, 2));
            }
            case JAUNE -> {
                // Hạch Tâm Quá Tải: Nấm mây vàng rực bộc phá
                level.sendParticles(ParticleTypes.FLASH, tPos.x, tPos.y + 2.0D, tPos.z, 3, 0, 0, 0, 0);
                level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, tPos.x, tPos.y + 1.0D, tPos.z, 3, 0, 0, 0, 0);
                level.sendParticles(ParticleTypes.CRIT, tPos.x, tPos.y + 1.5D, tPos.z, 60, 2.0D, 2.0D, 2.0D, 0.2D);
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 4.0F, 0.6F);
                dealDemonicDamage(target, 350.0F);
            }
            case VIOLET -> {
                // Tử Khí Trầm Luân: Cột khói tím độc phun trào từ lòng đất
                for (double y = 0.0D; y <= 8.0D; y += 0.5D) {
                    level.sendParticles(ParticleTypes.WITCH, tPos.x, tPos.y + y, tPos.z, 10, 0.8D, 0.2D, 0.8D, 0.05D);
                }
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.BREWING_STAND_BREW, SoundSource.PLAYERS, 3.0F, 1.2F);
                dealDemonicDamage(target, 220.0F);
                applyDemonicEffect(target, new MobEffectInstance(MobEffects.BLINDNESS, 100, 0));
            }
            case BLEU -> {
                // Băng Kính Khởi Nguyên: 3 ảnh phân thân băng bắn tia hàn băng hội tụ
                for (int i = 0; i < 3; i++) {
                    double ang = i * (2 * Math.PI / 3);
                    Vec3 mirPos = tPos.add(Math.cos(ang) * 4.0D, 1.5D, Math.sin(ang) * 4.0D);
                    spawnRotatingCircle(level, mirPos, 2.0F, 8.0F, 40, false, 0x2563EB);
                    for (double d = 0.5D; d <= 4.0D; d += 0.5D) {
                        Vec3 lp = mirPos.lerp(tPos, d / 4.0D);
                        level.sendParticles(ParticleTypes.SNOWFLAKE, lp.x, lp.y, lp.z, 2, 0.05D, 0.05D, 0.05D, 0.01D);
                    }
                }
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 3.0F, 1.4F);
                dealDemonicDamage(target, 250.0F);
            }
            case VERT -> {
                // Lục Lôi Ma Trận: 5 luồng sét lục bảo giáng xuống đồng loạt
                for (int s = 0; s < 5; s++) {
                    double ox = (this.random.nextDouble() - 0.5D) * 6.0D;
                    double oz = (this.random.nextDouble() - 0.5D) * 6.0D;
                    for (double y = 0.0D; y <= 16.0D; y += 0.8D) {
                        level.sendParticles(ParticleTypes.HAPPY_VILLAGER, tPos.x + ox, tPos.y + y, tPos.z + oz, 3, 0.1D, 0.1D, 0.1D, 0.02D);
                    }
                }
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 3.0F, 1.4F);
                dealDemonicDamage(target, 260.0F);
            }
        }
    }

    public void executeSkillFourDomain(LivingEntity target, DemonType type) {
        Vec3 center = this.position();
        String domainName = getDomainName(type);
        String voiceLine = getDomainVoiceLine(type);
        this.activateDomain(type, center, domainName, voiceLine);
    }

    // =========================================================================
    // LỆNH THI TRIỂN KỸ NĂNG TỪ XA (COMMAND EXECUTION TỪ CUỘN KHẾ ƯỚC)
    // =========================================================================
    public void executeCommandedSkill(int skillIndex, LivingEntity target) {
        if (target == null || !target.isAlive()) {
            target = this.getTarget();
        }
        if (target == null) {
            List<LivingEntity> nearby = this.level().getEntitiesOfClass(
                    LivingEntity.class,
                    this.getBoundingBox().inflate(24.0D),
                    e -> e != this && e != this.getOwner() && e instanceof Enemy && e.isAlive()
            );
            if (!nearby.isEmpty()) {
                target = nearby.get(0);
            }
        }
        if (target == null) return;

        this.setTarget(target);
        this.getLookControl().setLookAt(target, 30.0F, 30.0F);

        if (skillIndex >= 10) {
            int randomSkillIdx = skillIndex - 10;
            List<PrimordialSkillPool.SkillEntry> rSkills = getRandomSkills();
            if (randomSkillIdx >= 0 && randomSkillIdx < rSkills.size()) {
                PrimordialSkillPool.executeSkill(this, target, rSkills.get(randomSkillIdx));
                this.triggerSkillCooldown(40);
                return;
            }
        }

        DemonType type = getDemonType();
        switch (skillIndex) {
            case 0 -> executeNormalAttack(target, type);
            case 1 -> executeSkillOne(target, type);
            case 2 -> executeSkillTwo(target, type);
            case 3 -> executeSkillThree(target, type);
            case 4 -> executeChromaticLightPillar(target, type);
            case 5 -> executeDisintegration(target, type);
            case 6 -> executeSignatureSkillOne(target, type);
            case 7 -> executeSignatureSkillTwo(target, type);
            case 8 -> executeSignatureSkillThree(target, type);
            case 9 -> executeSkillFourDomain(target, type);
            default -> executeSkillOne(target, type);
        }
        this.triggerSkillCooldown(40);
    }

    // =========================================================================
    // TÊN GỌI KỸ NĂNG CỦA 7 THỦY TỔ
    // =========================================================================
    public static String getSkillOneName(DemonType type) {
        return switch (type) {
            case NOIR -> "Pháp Trận Hắc Ma Cầu Thôn Phệ";
            case ROUGE -> "Ma Trận Xích Diễm Bộc Phá";
            case BLANC -> "Pháp Trận Bạch Viêm Khởi Nguyên";
            case JAUNE -> "Hoàng Kim Trọng Lực Ma Trận";
            case VIOLET -> "Pháp Trận Tử Độc Khởi Nguyên";
            case BLEU -> "Pháp Trận Băng Tiễn Cực Hàn";
            case VERT -> "Pháp Trận Phong Ma Tiễn";
        };
    }

    public static String getSkillTwoName(DemonType type) {
        return switch (type) {
            case NOIR -> "Vực Sâu Tận Cùng • Đoạt Mệnh Hắc Dực";
            case ROUGE -> "Hỏa Long Bão Tố Tận Diệt";
            case BLANC -> "Dải Lụa Thánh Ma Tử Thần";
            case JAUNE -> "Tuyệt Diệt Ma Pháo • Carrera Bullet Storm";
            case VIOLET -> "Vũ Điệu Bóng Ma Ăn Mòn";
            case BLEU -> "Lốc Xoáy Băng Tuyết Cực Hàn";
            case VERT -> "Bão Lốc Đẩy Lùi & Bảo Hộ";
        };
    }

    public static String getSkillThreeName(DemonType type) {
        return switch (type) {
            case NOIR -> "Song Trùng Trận • Cột Sáng Hủy Diệt";
            case ROUGE -> "Thiên Địa Ma Trận • Cột Hỏa Ngục";
            case BLANC -> "Thánh Ma Đồng Quy • Cột Sáng Phân Rã";
            case JAUNE -> "Tam Trùng Trận • Đại Ma Pháp Hạt Nhân";
            case VIOLET -> "Tam Trùng Ma Trận • Cột Sáng Ăn Mòn";
            case BLEU -> "Thiên Địa Hàn Băng • Cột Sáng Băng Cực";
            case VERT -> "Song Trùng Phong Lôi • Cột Sáng Lục Bảo";
        };
    }

    public static String getSignatureOneName(DemonType type) {
        return switch (type) {
            case ROUGE -> "Xích Diễm Hỏa Long Bộc Phá";
            case NOIR -> "Hắc Ám Không Gian Phân Cắt";
            case BLANC -> "Bạch Quang Tuyệt Diệt Tuyến";
            case JAUNE -> "Carrera Gatling Barrage";
            case VIOLET -> "Tử Hoa Mạn Đà La";
            case BLEU -> "Băng Cực Phong Ấn Vĩnh Cửu";
            case VERT -> "Lục Bảo Phong Bạo Cuồng Nộ";
        };
    }

    public static String getSignatureTwoName(DemonType type) {
        return switch (type) {
            case ROUGE -> "Hỏa Ngục Toái Phiến Tiễn";
            case NOIR -> "Phán Quyết Tuyệt Vọng";
            case BLANC -> "Bạch Viêm Huyết Vũ";
            case JAUNE -> "Trọng Lực Thâm Uyên Điểm Kỳ Dị";
            case VIOLET -> "Huyết Độc Ma Trảo Tam Kích";
            case BLEU -> "Bão Băng Ngưng Đọng";
            case VERT -> "Lục Bảo Thánh Thuẫn";
        };
    }

    public static String getSignatureThreeName(DemonType type) {
        return switch (type) {
            case ROUGE -> "Hỏa Đế Trảm Không";
            case NOIR -> "Hắc Linh Hồn Thôn Phệ";
            case BLANC -> "Thánh Ma Phân Rã Ba Động";
            case JAUNE -> "Hạch Tâm Quá Tải";
            case VIOLET -> "Tử Khí Trầm Luân";
            case BLEU -> "Băng Kính Khởi Nguyên";
            case VERT -> "Lục Lôi Ma Trận";
        };
    }

    public static String getDomainName(DemonType type) {
        return switch (type) {
            case NOIR -> "Thế Giới Cám Dỗ (Temptation World)";
            case ROUGE -> "Hỏa Ngục Ma Vương (Crimson Hell)";
            case BLANC -> "Bạch Viêm Tận Diệt (Absolute White)";
            case JAUNE -> "Hạch Tâm Thâm Uyên (Abyss Gravity & Nuclear Domain)";
            case VIOLET -> "Vườn Độc Tử Thần (Poison Blossom)";
            case BLEU -> "Hàn Băng Vĩnh Cửu (Absolute Zero)";
            case VERT -> "Bảo Hộ Lục Bảo & Bão Tố (Emerald Sanctuary)";
        };
    }

    public static String getDomainVoiceLine(DemonType type) {
        return switch (type) {
            case NOIR -> "Kufufufu... Chào mừng đến với Thế Giới Cám Dỗ của tôi. Tại nơi này, chân lý và sinh mệnh của các người... hoàn toàn thuộc về tôi!";
            case ROUGE -> "Hmph! Hãy nếm thử sức nóng thiêu rụi cả linh hồn trong Hỏa Ngục của ta!";
            case BLANC -> "Ara ara... Vùng đất trắng này sẽ là nấm mồ thanh nhã dành riêng cho các ngươi~";
            case JAUNE -> "Haha! Hãy thử chống cự lại đại ma pháp hạt nhân và trọng lực thâm uyên trong Lãnh Địa của ta xem nào!";
            case VIOLET -> "Hihihi~ Chào mừng đến với khu vườn đầy hoa độc của ta! Đừng chết sớm quá nhé~";
            case BLEU -> "Lạnh lắm đấy... Tốt nhất các người nên ngủ mãi mãi trong băng tuyết đi.";
            case VERT -> "Trong kết giới này, mọi hỗn loạn sẽ bị dẹp tan dưới trật tự tuyệt đối.";
        };
    }

    public static String getSkillDisplayName(DemonType type, int skillIndex) {
        return switch (skillIndex) {
            case 0 -> "Đòn Đánh Thường";
            case 1 -> getSkillOneName(type);
            case 2 -> getSkillTwoName(type);
            case 3 -> getSkillThreeName(type);
            case 4 -> "Thất Sắc Thần Trụ • Cột Sáng Cực Đại";
            case 5 -> "Linh Tử Băng Hoại (Disintegration)";
            case 6 -> getSignatureOneName(type);
            case 7 -> getSignatureTwoName(type);
            case 8 -> getSignatureThreeName(type);
            case 9 -> "Lãnh Địa: " + getDomainName(type);
            default -> "Kỹ Năng Ma Giới";
        };
    }

    // =========================================================================
    // AI CHIẾN ĐẤU: XOAY VÒNG 12 BƯỚC COMBO KỸ NĂNG CHO 7 ÁC MA
    // =========================================================================
    static class PrimordialSkillGoal extends Goal {
        private final PrimordialDemonEntity demon;
        private int warmUpTicks = 0;
        private int comboStep = 0;

        public PrimordialSkillGoal(PrimordialDemonEntity demon) {
            this.demon = demon;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.demon.getTarget();
            return target != null && target.isAlive() && demon.canCastSkill() && demon.distanceToSqr(target) <= 32.0D * 32.0D;
        }

        @Override
        public void start() {
            this.warmUpTicks = 12;
            this.demon.getNavigation().stop();
        }

        @Override
        public void tick() {
            LivingEntity target = this.demon.getTarget();
            if (target == null) return;

            this.demon.getLookControl().setLookAt(target, 30.0F, 30.0F);
            this.warmUpTicks--;

            if (this.warmUpTicks <= 0) {
                executeComboStep(target);
                this.comboStep = (this.comboStep + 1) % 12;
                this.demon.triggerSkillCooldown(45);
            }
        }

        private void executeComboStep(LivingEntity target) {
            DemonType type = this.demon.getDemonType();

            // Nếu ác ma có kỹ năng ngẫu nhiên và trúng 35% tỉ lệ thi triển: kích hoạt kỹ năng ngẫu nhiên!
            List<PrimordialSkillPool.SkillEntry> skills = this.demon.getRandomSkills();
            if (!skills.isEmpty() && this.demon.getRandom().nextFloat() < 0.35F) {
                PrimordialSkillPool.SkillEntry randomSkill = skills.get(this.demon.getRandom().nextInt(skills.size()));
                PrimordialSkillPool.executeSkill(this.demon, target, randomSkill);
                return;
            }

            switch (comboStep) {
                case 0 -> demon.executeNormalAttack(target, type);
                case 1 -> demon.executeSkillOne(target, type);
                case 2 -> demon.executeSignatureSkillOne(target, type);
                case 3 -> demon.executeSkillTwo(target, type);
                case 4 -> demon.executeSignatureSkillTwo(target, type);
                case 5 -> demon.executeSkillThree(target, type);
                case 6 -> demon.executeChromaticLightPillar(target, type);
                case 7 -> demon.executeSignatureSkillThree(target, type);
                case 8 -> demon.executeDisintegration(target, type);
                case 9 -> demon.executeNormalAttack(target, type);
                case 10 -> demon.executeSkillFourDomain(target, type);
                case 11 -> demon.executeChromaticLightPillar(target, type);
            }
        }
    }
}
