/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.boss.enderdragon.EnderDragon
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.neoforged.bus.api.Event
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent
 */
package net.unusual.block_factorys_bosses.procedures;

import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.unusual.block_factorys_bosses.entity.boss.dragon.boss.InfernalDragonEntity;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;
import net.unusual.block_factorys_bosses.entity.boss.sandworm.SandwormEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.YetiEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseTags;

@EventBusSubscriber
public class BossCancelDie2Procedure {
    @SubscribeEvent
    public static void onEntityAttacked(LivingIncomingDamageEvent event) {
        BossCancelDie2Procedure.execute((Event)event, (LevelAccessor)event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), (Entity)event.getEntity(), event.getSource().getEntity(), event.getAmount());
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, double amount) {
        BossCancelDie2Procedure.execute(null, world, x, y, z, entity, sourceentity, amount);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, double amount) {
        block23: {
            block22: {
                int n;
                block26: {
                    int n2;
                    block25: {
                        int n3;
                        block24: {
                            int n4;
                            float f;
                            if (entity == null || sourceentity == null) {
                                return;
                            }
                            if (!entity.getType().is(BossesRiseTags.EntityTypes.BF_BOSS)) break block22;
                            if (entity instanceof LivingEntity) {
                                LivingEntity _livEnt = (LivingEntity)entity;
                                f = _livEnt.getHealth();
                            } else {
                                f = -1.0f;
                            }
                            if (!((double)f - amount <= 0.0)) break block23;
                            if (entity instanceof SandwormEntity) {
                                SandwormEntity _datEntI = (SandwormEntity)entity;
                                n4 = (Integer)_datEntI.getEntityData().get(SandwormEntity.DATA_DIE_ANIMTIME);
                            } else {
                                n4 = 0;
                            }
                            if (n4 != 0) break block24;
                            if (entity instanceof SandwormEntity) {
                                SandwormEntity _datEntSetS = (SandwormEntity)entity;
                                _datEntSetS.getEntityData().set(SandwormEntity.DATA_SOURCE_UUID, sourceentity.getStringUUID());
                            }
                            break block23;
                        }
                        if (entity instanceof InfernalDragonEntity) {
                            InfernalDragonEntity _datEntI = (InfernalDragonEntity)entity;
                            n3 = (Integer)_datEntI.getEntityData().get(InfernalDragonEntity.DATA_DIE_ANIMTIME);
                        } else {
                            n3 = 0;
                        }
                        if (n3 != 0) break block25;
                        if (entity instanceof InfernalDragonEntity) {
                            InfernalDragonEntity _datEntSetS = (InfernalDragonEntity)entity;
                            _datEntSetS.getEntityData().set(InfernalDragonEntity.DATA_SOURCE_UUID, sourceentity.getStringUUID());
                        }
                        break block23;
                    }
                    if (entity instanceof YetiEntity) {
                        YetiEntity _datEntI = (YetiEntity)entity;
                        n2 = (Integer)_datEntI.getEntityData().get(YetiEntity.DATA_DIE_ANIMTIME);
                    } else {
                        n2 = 0;
                    }
                    if (n2 != 0) break block26;
                    if (entity instanceof YetiEntity) {
                        YetiEntity _datEntSetS = (YetiEntity)entity;
                        _datEntSetS.getEntityData().set(YetiEntity.DATA_SOURCE_UUID, sourceentity.getStringUUID());
                    }
                    break block23;
                }
                if (entity instanceof UnderworldKnightEntity) {
                    UnderworldKnightEntity _datEntI = (UnderworldKnightEntity)entity;
                    n = (Integer)_datEntI.getEntityData().get(UnderworldKnightEntity.DATA_DIE_ANIMTIME);
                } else {
                    n = 0;
                }
                if (n != 0 || !(entity instanceof UnderworldKnightEntity)) break block23;
                UnderworldKnightEntity _datEntSetS = (UnderworldKnightEntity)entity;
                _datEntSetS.getEntityData().set(UnderworldKnightEntity.DATA_SOURCE_UUID, sourceentity.getStringUUID());
                break block23;
            }
            if (entity instanceof EnderDragon) {
                ItemStack itemStack;
                if (sourceentity instanceof LivingEntity) {
                    LivingEntity _livEnt = (LivingEntity)sourceentity;
                    itemStack = _livEnt.getMainHandItem();
                } else {
                    itemStack = ItemStack.EMPTY;
                }
                if (itemStack.getItem() == BossesRiseItems.KNIGHT_SWORD.get()) {
                    float f;
                    if (entity instanceof LivingEntity) {
                        LivingEntity _livEnt = (LivingEntity)entity;
                        f = _livEnt.getHealth();
                    } else {
                        f = -1.0f;
                    }
                    if ((double)f - amount <= 0.0) {
                        for (int index0 = 0; index0 < 12; ++index0) {
                            if (!(world instanceof ServerLevel)) continue;
                            ServerLevel _level = (ServerLevel)world;
                            ItemEntity entityToSpawn = new ItemEntity((Level)_level, x, y, z, new ItemStack((ItemLike)BossesRiseItems.DRAGON_SHANK.get()));
                            entityToSpawn.setPickUpDelay(10);
                            _level.addFreshEntity((Entity)entityToSpawn);
                        }
                    }
                }
            }
        }
    }
}

