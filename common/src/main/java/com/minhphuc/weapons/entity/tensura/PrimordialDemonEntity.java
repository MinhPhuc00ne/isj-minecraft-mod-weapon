package com.minhphuc.weapons.entity.tensura;

import com.minhphuc.weapons.content.divine.DivineArmorItem;
import com.minhphuc.weapons.content.infinitygauntlet.InfinityGauntletItem;
import com.minhphuc.weapons.content.tensura.VoiceOfTheWorld;
import com.minhphuc.weapons.content.tensura.PrimordialPactItem;
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
    }

    public void updateAttributesForType(DemonType type) {
        if (type == null) return;
        var maxHpAttr = this.getAttribute(Attributes.MAX_HEALTH);
        if (maxHpAttr != null) {
            double hp = type.getMaxHealth();
            if (isWinged() && type == DemonType.NOIR) hp *= 1.2D;
            maxHpAttr.setBaseValue(hp);
            if (this.getHealth() > (float) hp) {
                this.setHealth((float) hp);
            }
        }
        var dmgAttr = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (dmgAttr != null) {
            double dmg = type.getAttackDamage();
            if (isWinged() && type == DemonType.NOIR) dmg *= 1.25D;
            dmgAttr.setBaseValue(dmg);
        }
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

    public void dealDemonicDamage(LivingEntity target, float amount) {
        if (isTargetImmune(target)) {
            if (target instanceof ServerPlayer sp) {
                sp.displayClientMessage(Component.literal("§6§l✦ [BẤT DIỆT] Sức mạnh Thần Thoại / Găng Tay Vô Cực đã triệt tiêu hoàn toàn đòn đánh! ✦"), true);
            }
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
            if (type == DemonType.ROUGE || type == DemonType.NOIR) {
                // 90% áp đảo diệt Boss, 10% sơ suất/chủ quan
                if (this.random.nextFloat() < 0.10F) {
                    amount *= 0.85F;
                } else {
                    amount *= 2.6F;
                }
            } else if (type == DemonType.BLANC) {
                amount *= 2.1F; // 75%
            } else if (type == DemonType.JAUNE) {
                amount *= 1.9F; // 65%
            } else if (type == DemonType.VIOLET) {
                amount *= 1.25F; // 33%
            } else if (type == DemonType.VERT) {
                amount *= 1.15F; // 30%
            } else if (type == DemonType.BLEU) {
                amount *= 0.85F; // 20%
            }
        }

        target.hurt(this.level().damageSources().mobAttack(this), amount);
    }

    public void applyDemonicEffect(LivingEntity target, MobEffectInstance effect) {
        if (isTargetImmune(target)) return;
        target.addEffect(effect);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (target instanceof LivingEntity living) {
            if (isTargetImmune(living)) {
                if (living instanceof ServerPlayer sp) {
                    sp.displayClientMessage(Component.literal("§6§l✦ [BẤT DIỆT] Giáp Thần Thoại / Găng Tay Vô Cực đã vô hiệu hóa đòn cào của Ác Ma! ✦"), true);
                }
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
            // Hào quang ma tộc đặc trưng quanh người
            if (this.random.nextFloat() < (isWinged() ? 0.6F : 0.25F)) {
                double ox = (this.random.nextDouble() - 0.5D) * 0.8D;
                double oy = this.random.nextDouble() * 1.8D;
                double oz = (this.random.nextDouble() - 0.5D) * 0.8D;

                DemonType type = getDemonType();
                if (type == DemonType.ROUGE) {
                    this.level().addParticle(ParticleTypes.FLAME, this.getX() + ox, this.getY() + oy, this.getZ() + oz, 0, 0.02, 0);
                } else if (type == DemonType.NOIR) {
                    this.level().addParticle(ParticleTypes.DRAGON_BREATH, this.getX() + ox, this.getY() + oy, this.getZ() + oz, 0, 0.01, 0);
                    if (isWinged()) {
                        this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + ox, this.getY() + oy, this.getZ() + oz, 0, 0.02, 0);
                    }
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

        // Tự động hồi phục sinh lực ma tộc (3 HP mỗi giây, Rouge & Noir hồi 6 HP)
        if (this.tickCount % 20 == 0 && this.getHealth() < this.getMaxHealth()) {
            float healAmt = (getDemonType() == DemonType.ROUGE || getDemonType() == DemonType.NOIR) ? 6.0F : 3.0F;
            this.heal(healAmt);
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
                // Cho phép chết nếu bị kẻ khác kết liễu
                cleanDomainBarriers();
            }
        }

        return super.hurt(source, amount);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("DemonType", this.getDemonType().ordinal());
        compound.putBoolean("IsWinged", this.isWinged());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("DemonType")) {
            this.setDemonType(DemonType.byIndex(compound.getInt("DemonType")));
        }
        if (compound.contains("IsWinged")) {
            this.setWinged(compound.getBoolean("IsWinged"));
        }
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
    // AI CHIẾN ĐẤU: 4 KỸ NĂNG + ĐÁNH THƯỜNG XOAY VÒNG THEO LƯỢT CHO CẢ 7 ÁC MA
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
            return target != null && target.isAlive() && demon.canCastSkill() && demon.distanceToSqr(target) <= 28.0D * 28.0D;
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
                this.comboStep = (this.comboStep + 1) % 8;
                this.demon.triggerSkillCooldown(50);
            }
        }

        private void executeComboStep(LivingEntity target) {
            DemonType type = this.demon.getDemonType();

            switch (comboStep) {
                case 0:
                case 2:
                case 4:
                case 6:
                    executeNormalAttack(target, type);
                    break;
                case 1:
                    executeSkillOne(target, type);
                    break;
                case 3:
                    executeSkillTwo(target, type);
                    break;
                case 5:
                    executeSkillThree(target, type);
                    break;
                case 7:
                    executeSkillFourDomain(target, type);
                    break;
            }
        }

        private void announceSkill(String skillName) {
            ServerLevel level = (ServerLevel) this.demon.level();
            for (ServerPlayer sp : level.getPlayers(p -> p.distanceToSqr(demon) <= 32.0D * 32.0D)) {
                sp.displayClientMessage(Component.literal("§d§l[" + demon.getDemonType().getColorName().toUpperCase() + "] §6§l✦ " + skillName + " ✦"), true);
            }
        }

        // ==========================================
        // 1. ĐÒN ĐÁNH THƯỜNG (NORMAL ATTACK COMBO)
        // ==========================================
        private void executeNormalAttack(LivingEntity target, DemonType type) {
            ServerLevel level = (ServerLevel) this.demon.level();
            Vec3 tPos = target.position();
            this.demon.swing(InteractionHand.MAIN_HAND);

            if (type == DemonType.NOIR) {
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 2.0F, 0.9F);
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.EVOKER_FANGS_ATTACK, SoundSource.PLAYERS, 1.8F, 1.3F);
                level.sendParticles(ParticleTypes.SWEEP_ATTACK, tPos.x, tPos.y + 1.2D, tPos.z, 3, 0.4D, 0.4D, 0.4D, 0);
                level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, tPos.x, tPos.y + 1.0D, tPos.z, 15, 0.3D, 0.3D, 0.3D, 0.05D);
                demon.dealDemonicDamage(target, 160.0F);
            } else if (type == DemonType.ROUGE) {
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 2.0F, 1.0F);
                level.sendParticles(ParticleTypes.FLAME, tPos.x, tPos.y + 1.0D, tPos.z, 20, 0.4D, 0.4D, 0.4D, 0.08D);
                demon.dealDemonicDamage(target, 160.0F);
            } else if (type == DemonType.BLANC) {
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.PLAYERS, 2.0F, 1.8F);
                level.sendParticles(ParticleTypes.END_ROD, tPos.x, tPos.y + 1.0D, tPos.z, 15, 0.3D, 0.3D, 0.3D, 0.05D);
                demon.dealDemonicDamage(target, 80.0F);
            } else if (type == DemonType.JAUNE) {
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 1.8F, 1.2F);
                level.sendParticles(ParticleTypes.CRIT, tPos.x, tPos.y + 1.0D, tPos.z, 25, 0.5D, 0.5D, 0.5D, 0.1D);
                demon.dealDemonicDamage(target, 70.0F);
            } else if (type == DemonType.VIOLET) {
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.PLAYERS, 2.0F, 1.2F);
                level.sendParticles(ParticleTypes.WITCH, tPos.x, tPos.y + 1.0D, tPos.z, 20, 0.4D, 0.4D, 0.4D, 0.05D);
                demon.dealDemonicDamage(target, 40.0F);
            } else if (type == DemonType.BLEU) {
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 2.0F, 1.2F);
                level.sendParticles(ParticleTypes.SNOWFLAKE, tPos.x, tPos.y + 1.0D, tPos.z, 20, 0.4D, 0.4D, 0.4D, 0.02D);
                demon.dealDemonicDamage(target, 30.0F);
            } else {
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 2.0F, 1.4F);
                level.sendParticles(ParticleTypes.HAPPY_VILLAGER, tPos.x, tPos.y + 1.0D, tPos.z, 20, 0.4D, 0.4D, 0.4D, 0.05D);
                demon.dealDemonicDamage(target, 36.0F);
            }
        }

        // ==========================================
        // 2. SKILL 1: VÒNG MA PHÁP ĐỒNG TÂM BẮN NĂNG LƯỢNG
        // ==========================================
        private void executeSkillOne(LivingEntity target, DemonType type) {
            ServerLevel level = (ServerLevel) this.demon.level();
            Vec3 dPos = demon.position();
            Vec3 tPos = target.position();
            Vec3 look = demon.getLookAngle();
            Vec3 circlePos = dPos.add(look.scale(1.8D)).add(0, 1.2D, 0);

            announceSkill(type.getColorName() + ": " + getSkillOneName(type));
            level.playSound(null, dPos.x, dPos.y, dPos.z, SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 2.5F, 1.2F);

            if (type == DemonType.JAUNE) {
                // Jaune: Ma trận trọng lực kép (Orbital + Destruction)
                demon.spawnRotatingCircle(level, circlePos, 3.8F, 8.0F, 45, false, type.getGlowColor(), new ItemStack(ModItems.MAGIC_CIRCLE_JAUNE.get()));
                demon.spawnRotatingCircle(level, circlePos, 2.5F, -10.0F, 45, false, 0xFF4500, new ItemStack(ModItems.MAGIC_CIRCLE_JAUNE_DESTRUCTION.get()));
            } else {
                demon.spawnRotatingCircle(level, circlePos, 3.2F, 6.0F, 40, false, type.getGlowColor(), new ItemStack(type.getMagicCircleItem().get()));
                demon.spawnRotatingCircle(level, circlePos, 2.2F, -8.0F, 40, false, type.getGlowColor(), new ItemStack(type.getMagicCircleItem().get()));
            }

            // Bắn luồng cầu ma pháp xuyên thấu
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

            // Nổ tại điểm đích
            level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, tPos.x, tPos.y + 1.0D, tPos.z, 1, 0, 0, 0, 0);
            level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 2.0F, 1.2F);

            float damage = (float) (type.getAttackDamage() * 1.5D);
            demon.dealDemonicDamage(target, damage);
        }

        private String getSkillOneName(DemonType type) {
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

        // ==========================================
        // 3. SKILL 2: DỊCH CHUYỂN / LƯỚT / KHỐNG CHẾ KHÔNG GIAN
        // ==========================================
        private void executeSkillTwo(LivingEntity target, DemonType type) {
            ServerLevel level = (ServerLevel) this.demon.level();
            Vec3 tPos = target.position();

            announceSkill(type.getColorName() + ": " + getSkillTwoName(type));

            if (type == DemonType.NOIR) {
                Vec3 behind = tPos.add(target.getLookAngle().scale(-1.8D));
                demon.teleportTo(behind.x, behind.y, behind.z);
                level.playSound(null, behind.x, behind.y, behind.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 2.0F, 1.4F);
                level.sendParticles(ParticleTypes.FLASH, behind.x, behind.y + 1.2D, behind.z, 2, 0, 0, 0, 0);
                level.sendParticles(ParticleTypes.SWEEP_ATTACK, tPos.x, tPos.y + 1.2D, tPos.z, 6, 0.5D, 0.5D, 0.5D, 0);
                demon.dealDemonicDamage(target, 280.0F);
                demon.applyDemonicEffect(target, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 3));
            } else if (type == DemonType.ROUGE) {
                Vec3 dashPos = tPos.add(demon.getLookAngle().scale(2.5D));
                demon.teleportTo(dashPos.x, dashPos.y, dashPos.z);
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 2.5F, 0.8F);
                level.sendParticles(ParticleTypes.LAVA, tPos.x, tPos.y + 1.0D, tPos.z, 30, 0.5D, 0.8D, 0.5D, 0.1D);
                demon.dealDemonicDamage(target, 280.0F);
                target.setRemainingFireTicks(180);
            } else if (type == DemonType.BLANC) {
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 2.0F, 1.6F);
                Vec3 pull = demon.position().subtract(tPos).normalize().scale(1.2D);
                target.setDeltaMovement(pull.add(0, 0.4D, 0));
                target.hasImpulse = true;
                demon.dealDemonicDamage(target, 150.0F);
            } else if (type == DemonType.JAUNE) {
                // Jaune: Carrera Gun Barrage & 4 Floating Destruction Rings
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 2.5F, 1.4F);
                level.playSound(null, demon.getX(), demon.getY(), demon.getZ(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 2.0F, 1.6F);

                for (int ring = 0; ring < 4; ring++) {
                    double offAng = ring * Math.PI / 2.0D;
                    Vec3 ringPos = demon.position().add(Math.cos(offAng) * 2.0D, 1.2D, Math.sin(offAng) * 2.0D);
                    demon.spawnRotatingCircle(level, ringPos, 1.5F, 15.0F, 30, false, 0xFFCC00, new ItemStack(ModItems.MAGIC_CIRCLE_JAUNE_DESTRUCTION.get()));
                }

                for (int b = 0; b < 6; b++) {
                    Vec3 bulletPos = tPos.add((demon.random.nextDouble() - 0.5D) * 3.0D, 0.5D + b * 0.3D, (demon.random.nextDouble() - 0.5D) * 3.0D);
                    level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, bulletPos.x, bulletPos.y, bulletPos.z, 1, 0, 0, 0, 0);
                    level.sendParticles(ParticleTypes.CRIT, bulletPos.x, bulletPos.y, bulletPos.z, 20, 0.3D, 0.3D, 0.3D, 0.1D);
                }
                demon.dealDemonicDamage(target, 140.0F);
                target.setDeltaMovement(0, 1.2D, 0);
                target.hasImpulse = true;
            } else if (type == DemonType.VIOLET) {
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.BREWING_STAND_BREW, SoundSource.PLAYERS, 2.5F, 0.8F);
                level.sendParticles(ParticleTypes.WITCH, tPos.x, tPos.y + 1.0D, tPos.z, 50, 2.0D, 1.0D, 2.0D, 0.05D);
                demon.dealDemonicDamage(target, 70.0F);
                demon.applyDemonicEffect(target, new MobEffectInstance(MobEffects.POISON, 120, 2));
            } else if (type == DemonType.BLEU) {
                level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.PLAYER_HURT_FREEZE, SoundSource.PLAYERS, 2.5F, 1.0F);
                level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.PACKED_ICE.defaultBlockState()), tPos.x, tPos.y + 0.8D, tPos.z, 25, 0.3D, 0.8D, 0.3D, 0.05D);
                demon.dealDemonicDamage(target, 55.0F);
                target.setTicksFrozen(240);
            } else {
                level.playSound(null, demon.getX(), demon.getY(), demon.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 2.5F, 1.5F);
                for (int a = 0; a < 360; a += 30) {
                    double rad = Math.toRadians(a);
                    level.sendParticles(ParticleTypes.HAPPY_VILLAGER, demon.getX() + Math.cos(rad) * 3.0D, demon.getY() + 1.0D, demon.getZ() + Math.sin(rad) * 3.0D, 3, 0, 0, 0, 0.05D);
                }
                demon.dealDemonicDamage(target, 60.0F);
                if (demon.getOwner() instanceof LivingEntity owner) {
                    owner.heal(25.0F);
                }
            }
        }

        private String getSkillTwoName(DemonType type) {
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

        // ==========================================
        // 4. SKILL 3: SONG TRÙNG / TAM TRÙNG MA TRẬN CỘT SÁNG NỐI TRỜI & ĐẤT
        // ==========================================
        private void executeSkillThree(LivingEntity target, DemonType type) {
            ServerLevel level = (ServerLevel) this.demon.level();
            Vec3 tPos = target.position();

            announceSkill(type.getColorName() + ": " + getSkillThreeName(type));

            if (type == DemonType.JAUNE) {
                // Jaune Skill 3: Tam Trùng Ma Trận Hạt Nhân Diệt Thế (Nuclear Cannon Multi-Array)
                demon.spawnRotatingCircle(level, tPos.add(0, 0.05D, 0), 8.5F, 4.0F, 80, true, 0xFFA500, new ItemStack(ModItems.MAGIC_CIRCLE_JAUNE_DESTRUCTION.get()));
                demon.spawnRotatingCircle(level, tPos.add(0, 7.0D, 0), 6.5F, -6.0F, 80, true, 0xFFFF00, new ItemStack(ModItems.MAGIC_CIRCLE_JAUNE_NUCLEAR.get()));
                demon.spawnRotatingCircle(level, tPos.add(0, 14.0D, 0), 8.5F, 4.0F, 80, true, 0xFFCC00, new ItemStack(ModItems.MAGIC_CIRCLE_JAUNE.get()));
            } else {
                demon.spawnRotatingCircle(level, tPos.add(0, 0.05D, 0), 7.0F, 4.0F, 70, true, type.getGlowColor(), new ItemStack(type.getMagicCircleItem().get()));
                demon.spawnRotatingCircle(level, tPos.add(0, 14.0D, 0), 7.0F, -4.0F, 70, true, type.getGlowColor(), new ItemStack(type.getMagicCircleItem().get()));
            }

            level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 3.0F, 1.5F);
            level.playSound(null, tPos.x, tPos.y, tPos.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 2.5F, 0.8F);

            // Cột sáng năng lượng nối liền từ trời xuống đất
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
                    e -> e != demon && e != demon.getOwner()
            );

            float damage = (float) (type.getAttackDamage() * 2.2D);
            if (type == DemonType.JAUNE) {
                damage = 240.0F; // Carrera nuclear destruction burst
            }
            for (LivingEntity e : targets) {
                demon.dealDemonicDamage(e, damage);
            }
        }

        private String getSkillThreeName(DemonType type) {
            return switch (type) {
                case NOIR -> "Song Trùng Trận • Cột Sáng Hủy Diệt Tuyệt Đối";
                case ROUGE -> "Thiên Địa Ma Trận • Cột Hỏa Ngục Khởi Nguyên";
                case BLANC -> "Thánh Ma Đồng Quy • Cột Sáng Phân Rã Nhiệt Hạch";
                case JAUNE -> "Tam Trùng Trận • Đại Ma Pháp Hạt Nhân Hủy Diệt";
                case VIOLET -> "Tam Trùng Ma Trận • Cột Sáng Ăn Mòn";
                case BLEU -> "Thiên Địa Hàn Băng • Cột Sáng Băng Cực";
                case VERT -> "Song Trùng Phong Lôi • Cột Sáng Lục Bảo";
            };
        }

        // ==========================================
        // 5. SKILL 4: BỘC PHÁ LÃNH ĐỊA (DOMAIN EXPANSION)
        // ==========================================
        private void executeSkillFourDomain(LivingEntity target, DemonType type) {
            Vec3 center = demon.position();
            String domainName = getDomainName(type);
            String voiceLine = getDomainVoiceLine(type);

            demon.activateDomain(type, center, domainName, voiceLine);
        }

        private String getDomainName(DemonType type) {
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

        private String getDomainVoiceLine(DemonType type) {
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
    }
}
