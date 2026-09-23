/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EquipmentSlotGroup
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.ShieldItem
 *  net.minecraft.world.item.component.ItemAttributeModifiers
 *  net.minecraft.world.item.component.ItemAttributeModifiers$Builder
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 */
package net.unusual.block_factorys_bosses.item;

import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;

public class DragonGuardShieldItem
extends ShieldItem {
    public DragonGuardShieldItem() {
        super(new Item.Properties().durability(750).fireResistant().attributes(DragonGuardShieldItem.createAttributes()));
    }

    public boolean isValidRepairItem(ItemStack itemstack, ItemStack repairitem) {
        return Ingredient.of((ItemStack[])new ItemStack[]{new ItemStack((ItemLike)Items.NETHERITE_SCRAP), new ItemStack((ItemLike)BossesRiseItems.DRAGON_BONE.get())}).test(repairitem);
    }

    public static ItemAttributeModifiers createAttributes() {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(BossesRise.prefix("effect.slow"), -0.01, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.OFFHAND);
        builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(BossesRise.prefix("effect.tough"), 2.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.OFFHAND);
        builder.add(Attributes.ARMOR, new AttributeModifier(BossesRise.prefix("effect.prot"), 1.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.OFFHAND);
        return builder.build();
    }
}

