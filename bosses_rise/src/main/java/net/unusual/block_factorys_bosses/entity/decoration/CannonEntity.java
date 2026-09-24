/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MoverType
 *  net.minecraft.world.entity.decoration.BlockAttachedEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Explosion
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.common.Tags$Items
 *  net.neoforged.neoforge.event.entity.player.PlayerInteractEvent$RightClickEmpty
 *  net.neoforged.neoforge.registries.DeferredItem
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector3d
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animatable.GeoEntity
 *  software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.animatable.stateless.StatelessAnimationController
 *  software.bernie.geckolib.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.animation.AnimationController
 *  software.bernie.geckolib.animation.RawAnimation
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package net.unusual.block_factorys_bosses.entity.decoration;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.decoration.BlockAttachedEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenTentacleEntity;
import net.unusual.block_factorys_bosses.entity.projectile.CannonballEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.item.CannonEntityItem;
import net.unusual.block_factorys_bosses.util.SpatialUtil;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.stateless.StatelessAnimationController;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CannonEntity
extends BlockAttachedEntity
implements GeoEntity {
    public static String NO_BALLS_MESSAGE = "message.block_factorys_bosses.cannon.no_balls";
    protected static final EntityDataAccessor<Integer> HURT_ANIMATION_TIMER = SynchedEntityData.defineId(CannonEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> HURT_ANIMATION_DIRECTION = SynchedEntityData.defineId(CannonEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> CORE_Y_ROT = SynchedEntityData.defineId(CannonEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(CannonEntity.class, (EntityDataSerializer)EntityDataSerializers.FLOAT);
    protected static final float Y_VIEW_HALF_RANGE = 40.0f;
    protected static final float X_VIEW_HALF_RANGE = 60.0f;
    protected static final int ANIM_TIME = 60;
    protected static final int RELOAD_SOUND_AT = 50;
    private final DeferredItem<CannonEntityItem> item;
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    @Nullable
    public Vector3d bonePos = null;
    @Nullable
    protected BlockPos attachment = null;
    protected int attachDiff = -1;
    protected int timer = -1;
    public static final RawAnimation FIRE = RawAnimation.begin().thenPlay("animation.bf_br_kraken_canon_tpp.fire");
    public static final RawAnimation RELOAD = RawAnimation.begin().thenPlay("animation.bf_br_kraken_canon_tpp.reload");

    public CannonEntity(EntityType<CannonEntity> type, Level world, DeferredItem<CannonEntityItem> item) {
        super(type, world);
        this.item = item;
    }

    public void setAttachDiff(int attachDiff) {
        this.attachDiff = attachDiff;
    }

    @Nullable
    public ItemStack getPickResult() {
        return this.item.toStack();
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(HURT_ANIMATION_TIMER, 0);
        builder.define(HURT_ANIMATION_DIRECTION, 1);
        builder.define(DAMAGE, Float.valueOf(0.0f));
        builder.define(CORE_Y_ROT, 0);
    }

    public void setHurtTime(int hurtTime) {
        this.entityData.set(HURT_ANIMATION_TIMER, hurtTime);
    }

    public void setHurtDir(int hurtDir) {
        this.entityData.set(HURT_ANIMATION_DIRECTION, hurtDir);
    }

    public void setDamage(float damage) {
        this.entityData.set(DAMAGE, Float.valueOf(damage));
    }

    public float getDamage() {
        return ((Float)this.entityData.get(DAMAGE)).floatValue();
    }

    public int getHurtTime() {
        return (Integer)this.entityData.get(HURT_ANIMATION_TIMER);
    }

    public int getHurtDir() {
        return (Integer)this.entityData.get(HURT_ANIMATION_DIRECTION);
    }

    public void setCoreYRot(int coreYRot) {
        this.entityData.set(CORE_Y_ROT, coreYRot);
    }

    public int getCoreYRot() {
        return (Integer)this.entityData.get(CORE_Y_ROT);
    }

    public boolean causeFallDamage(float l, float d, DamageSource source) {
        return false;
    }

    protected void recalculateBoundingBox() {
        Vec3 center = this.position();
        double width = this.getBbWidth();
        double height = this.getBbHeight();
        this.setBoundingBox(new AABB(center.x - width / 2.0, center.y, center.z - width / 2.0, center.x + width / 2.0, center.y + height, center.z + width / 2.0));
    }

    public boolean survives() {
        return !this.level().getBlockState(this.blockPosition().relative(Direction.UP, this.attachDiff)).canBeReplaced();
    }

    public void tick() {
        if (this.getHurtTime() > 0) {
            this.setHurtTime(this.getHurtTime() - 1);
        }
        if (this.getDamage() > 0.0f) {
            this.setDamage(this.getDamage() - 1.0f);
        }
        super.tick();
        if (this.isControlledByLocalInstance() && this.level().isClientSide) {
            this.controlCannon();
        }
        if (this.level() instanceof ServerLevel) {
            if (this.timer == 50) {
                this.playSound((SoundEvent)BossesRiseSounds.CANNON_RELOAD.value(), 1.0f, 1.0f);
            }
            --this.timer;
        }
    }

    private void controlCannon() {
        if (this.getControllingPassenger() != null) {
            this.xRotO = this.getXRot();
            this.setXRot(Mth.approachDegrees((float)this.getXRot(), (float)this.getControllingPassenger().getXRot(), (float)4.0f));
            this.setYRot(Mth.approachDegrees((float)(this.getYRot() + 90.0f), (float)(this.getControllingPassenger().getYRot() + 90.0f), (float)4.0f) - 90.0f);
        }
    }

    public void move(MoverType type, Vec3 pos) {
    }

    public boolean skipAttackInteraction(Entity entity) {
        return false;
    }

    public boolean hurt(DamageSource source, float amount) {
        boolean creativePlayer;
        Player p;
        Player sourcePlayer;
        LivingEntity livingEntity = this.getControllingPassenger();
        if (livingEntity instanceof KrakenTentacleEntity) {
            KrakenTentacleEntity tentacle = (KrakenTentacleEntity)livingEntity;
            return tentacle.hurt(source, amount);
        }
        if (this.level().isClientSide || this.isRemoved()) {
            return true;
        }
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        Entity entity = source.getEntity();
        Player player = sourcePlayer = entity instanceof Player ? (p = (Player)entity) : null;
        if (sourcePlayer != null && !sourcePlayer.getAbilities().mayBuild) {
            return false;
        }
        if (sourcePlayer != null && sourcePlayer.getWeaponItem().is(Tags.Items.MELEE_WEAPON_TOOLS)) {
            return false;
        }
        this.setHurtDir(-this.getHurtDir());
        this.setHurtTime(10);
        this.markHurt();
        this.setDamage(this.getDamage() + amount * 10.0f);
        this.gameEvent((Holder)GameEvent.ENTITY_DAMAGE, source.getEntity());
        boolean bl = creativePlayer = sourcePlayer != null && sourcePlayer.getAbilities().instabuild;
        if (creativePlayer || !(this.getDamage() > 40.0f)) {
            if (creativePlayer) {
                this.discard();
            }
        } else {
            this.kill();
            this.dropItem((Entity)sourcePlayer);
        }
        return true;
    }

    public boolean ignoreExplosion(Explosion explosion) {
        return true;
    }

    public boolean fireImmune() {
        return true;
    }

    @Nullable
    public LivingEntity getControllingPassenger() {
        LivingEntity living;
        Entity entity = this.getFirstPassenger();
        return entity instanceof LivingEntity ? (living = (LivingEntity)entity) : null;
    }

    protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float partialTick) {
        if (entity instanceof KrakenTentacleEntity) {
            KrakenTentacleEntity tentacle = (KrakenTentacleEntity)entity;
            return new Vec3(-1.75, -12.0, 0.0).yRot((-tentacle.getYRot() - 180.0f) * ((float)Math.PI / 180));
        }
        Vec3 pivotPoint = new Vec3(0.0, (double)this.getEyeHeight(), 0.0);
        Vec3 offset = new Vec3(0.0, 0.75, 1.75);
        return pivotPoint.add(offset.xRot(this.getXRot() * ((float)Math.PI / 180)).yRot((-this.getYRot() - 180.0f) * ((float)Math.PI / 180)));
    }

    public void onPassengerTurned(Entity entityToUpdate) {
        if (entityToUpdate instanceof KrakenTentacleEntity) {
            return;
        }
        this.clampRotation(entityToUpdate);
    }

    protected void clampRotation(Entity entityToUpdate) {
        entityToUpdate.setYBodyRot(this.getYRot());
        float yWrap = Mth.wrapDegrees((float)(entityToUpdate.getYRot() - (float)this.getCoreYRot()));
        float yClamp = Mth.clamp((float)yWrap, (float)-40.0f, (float)40.0f);
        entityToUpdate.yRotO += yClamp - yWrap;
        entityToUpdate.setYRot(entityToUpdate.getYRot() + yClamp - yWrap);
        entityToUpdate.setYHeadRot(entityToUpdate.getYRot());
        entityToUpdate.setXRot(Math.clamp((float)entityToUpdate.getXRot(), (float)-60.0f, (float)60.0f));
        entityToUpdate.xRotO = Math.clamp((float)entityToUpdate.xRotO, (float)-60.0f, (float)60.0f);
    }

    public InteractionResult interact(Player player, InteractionHand hand) {
        InteractionResult interactionresult = super.interact(player, hand);
        if (interactionresult != InteractionResult.PASS) {
            return interactionresult;
        }
        if (player.isSecondaryUseActive()) {
            return InteractionResult.PASS;
        }
        if (this.level().isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (this.getControllingPassenger() == player) {
            if (this.timer < 0) {
                if (!player.hasInfiniteMaterials()) {
                    ItemStack cannonBall = null;
                    if (player.getItemInHand(hand).is((Item)BossesRiseItems.CANNONBALL.get())) {
                        cannonBall = player.getItemInHand(hand);
                    } else {
                        Inventory inventory = player.getInventory();
                        for (ItemStack stack : inventory.items) {
                            if (!stack.is((Item)BossesRiseItems.CANNONBALL.get())) continue;
                            cannonBall = stack;
                            break;
                        }
                    }
                    if (cannonBall == null) {
                        player.displayClientMessage((Component)Component.translatable((String)NO_BALLS_MESSAGE), true);
                        return InteractionResult.SUCCESS;
                    }
                    cannonBall.shrink(1);
                }
                this.triggerAnim("main_controller", "fire");
                CannonballEntity cannonball = new CannonballEntity((EntityType<? extends CannonballEntity>)((EntityType)BossesRiseEntities.CANNONBALL.get()), this.level());
                cannonball.setOwner((Entity)player);
                cannonball.setPos(this.getX(), this.getEyeY(), this.getZ());
                cannonball.shootFromRotation((Entity)this, this.getXRot(), this.getYRot(), 0.0f, 2.5f, 1.0f);
                cannonball.setYRot(this.getYRot());
                cannonball.setXRot(this.getXRot());
                this.level().addFreshEntity((Entity)cannonball);
                this.playSound((SoundEvent)BossesRiseSounds.CANNON_FIRE.value(), 1.0f, 1.0f);
                this.timer = 60;
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        if (player.startRiding((Entity)this)) {
            this.playSound((SoundEvent)BossesRiseSounds.CANNON_SEAT.value(), 1.0f, 1.0f);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        this.playSound((SoundEvent)BossesRiseSounds.CANNON_DISMOUNT.value(), 1.0f, 1.0f);
        return this.position().add(new Vec3(0.0, 0.0, 2.0).yRot(((float)(-this.getCoreYRot()) - 180.0f) * ((float)Math.PI / 180)));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("hurt", ((Integer)this.entityData.get(HURT_ANIMATION_TIMER)).intValue());
        compound.putInt("hurt_dir", ((Integer)this.entityData.get(HURT_ANIMATION_DIRECTION)).intValue());
        compound.putFloat("damage", ((Float)this.entityData.get(DAMAGE)).floatValue());
        compound.putInt("core_y_rot", ((Integer)this.entityData.get(CORE_Y_ROT)).intValue());
        compound.putInt("attachment", this.attachDiff);
        compound.putInt("anim_time", this.timer);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("hurt")) {
            this.entityData.set(HURT_ANIMATION_TIMER, compound.getInt("hurt"));
        }
        if (compound.contains("hurt_dir")) {
            this.entityData.set(HURT_ANIMATION_DIRECTION, compound.getInt("hurt_dir"));
        }
        if (compound.contains("damage")) {
            this.entityData.set(DAMAGE, Float.valueOf(compound.getFloat("damage")));
        }
        if (compound.contains("core_y_rot")) {
            this.entityData.set(CORE_Y_ROT, compound.getInt("core_y_rot"));
        }
        if (compound.contains("attachment")) {
            this.attachDiff = compound.getInt("attachment");
        }
        if (compound.contains("anim_time")) {
            this.timer = compound.getInt("anim_time");
        }
        this.pos = this.blockPosition();
    }

    public float rotate(Rotation transformRotation) {
        this.setCoreYRot((int)SpatialUtil.rotateDegrees(this.getCoreYRot(), transformRotation));
        return super.rotate(transformRotation);
    }

    public float mirror(Mirror transformMirror) {
        this.setCoreYRot((int)SpatialUtil.mirrorDegrees(this.getCoreYRot(), transformMirror));
        return super.mirror(transformMirror);
    }

    public void dropItem(@Nullable Entity entity) {
        if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            Player player;
            this.playSound(SoundEvents.HEAVY_CORE_BREAK, 1.0f, 1.0f);
            if (entity instanceof Player && (player = (Player)entity).hasInfiniteMaterials()) {
                return;
            }
            this.spawnAtLocation((ItemLike)this.item.get());
        }
    }

    public boolean canRiderInteract() {
        return true;
    }

    public boolean canCollideWith(Entity entity) {
        return true;
    }

    public boolean canBeCollidedWith() {
        return true;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    public AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(2.0);
    }

    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        StatelessAnimationController controller = new StatelessAnimationController((GeoAnimatable)this, "main_controller");
        controller.triggerableAnim("fire", FIRE);
        controller.triggerableAnim("reload", RELOAD);
        controllers.add((AnimationController)controller);
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    @EventBusSubscriber(value={Dist.CLIENT})
    public static class Listener {
        @SubscribeEvent
        public static void onEmptyInteract(PlayerInteractEvent.RightClickEmpty event) {
            Entity entity = event.getEntity().getVehicle();
            if (entity instanceof CannonEntity) {
                CannonEntity cannon = (CannonEntity)entity;
                if (Minecraft.getInstance().gameMode != null) {
                    Minecraft.getInstance().gameMode.interact(event.getEntity(), (Entity)cannon, event.getHand());
                }
            }
        }
    }
}

