/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Position
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.EquipmentSlotGroup
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.projectile.AbstractArrow$Pickup
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.TridentItem
 *  net.minecraft.world.item.component.ItemAttributeModifiers
 *  net.minecraft.world.level.Level
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package net.unusual.block_factorys_bosses.item;

import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.entity.projectile.ThrownKrakenTridentEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class KrakenTridentItem
extends TridentItem {
    public KrakenTridentItem(Item.Properties properties) {
        super(properties);
    }

    public static ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 9.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, (double)-2.9f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    @OnlyIn(value=Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        list.add((Component)Component.translatable((String)"item.block_factorys_bosses.kraken_trident.description_0"));
        list.add((Component)Component.translatable((String)"item.block_factorys_bosses.kraken_trident.description_1"));
    }

    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return stack.is((Item)BossesRiseItems.KRAKEN_TOOTH.get());
    }

    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        ThrownKrakenTridentEntity entity = new ThrownKrakenTridentEntity(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1));
        entity.pickup = AbstractArrow.Pickup.ALLOWED;
        return entity;
    }
}

