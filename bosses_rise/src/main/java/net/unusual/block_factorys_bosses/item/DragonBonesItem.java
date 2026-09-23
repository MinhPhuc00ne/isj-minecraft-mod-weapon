/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.client.model.geom.ModelLayerLocation
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.EquipmentSlotGroup
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.item.ArmorItem
 *  net.minecraft.world.item.ArmorItem$Type
 *  net.minecraft.world.item.ArmorMaterial$Layer
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.item.component.ItemAttributeModifiers
 *  net.minecraft.world.item.component.ItemAttributeModifiers$Builder
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.extensions.common.IClientItemExtensions
 *  net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent
 */
package net.unusual.block_factorys_bosses.item;

import java.util.Collections;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.AnimationTickHolder;
import net.unusual.block_factorys_bosses.client.model.Modelchestplate;
import net.unusual.block_factorys_bosses.init.BossesRiseArmorMaterials;
import net.unusual.block_factorys_bosses.init.BossesRiseAttributes;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.util.ItemAttributeModifiersUtil;

@EventBusSubscriber
public abstract class DragonBonesItem
extends ArmorItem {
    @SubscribeEvent
    public static void registerItemExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions(){

            @OnlyIn(value=Dist.CLIENT)
            public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
                HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of("body", new Modelchestplate<>((ModelPart)Minecraft.getInstance().getEntityModels().bakeLayer((ModelLayerLocation)Modelchestplate.LAYER_LOCATION)).body, "left_arm", new Modelchestplate<>((ModelPart)Minecraft.getInstance().getEntityModels().bakeLayer((ModelLayerLocation)Modelchestplate.LAYER_LOCATION)).leftArm, "right_arm", new Modelchestplate<>((ModelPart)Minecraft.getInstance().getEntityModels().bakeLayer((ModelLayerLocation)Modelchestplate.LAYER_LOCATION)).rightArm, "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
                armorModel.crouching = living.isShiftKeyDown();
                armorModel.riding = defaultModel.riding;
                armorModel.young = living.isBaby();
                return armorModel;
            }
        }, new Item[]{(Item)BossesRiseItems.DRAGON_BONES_CHESTPLATE.get()});
    }

    public DragonBonesItem(ArmorItem.Type type, Item.Properties properties) {
        super(BossesRiseArmorMaterials.DRAGON_ARMOR, type, properties);
    }

    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        ItemAttributeModifiers.Builder builder = DragonBonesItem.createAttributesBuilder(this.getEquipmentSlot());
        ItemAttributeModifiersUtil.addAll(builder, super.getDefaultAttributeModifiers());
        return builder.build();
    }

    public static ItemAttributeModifiers createAttributes(EquipmentSlot slot) {
        return DragonBonesItem.createAttributesBuilder(slot).build();
    }

    public static ItemAttributeModifiers.Builder createAttributesBuilder(EquipmentSlot slot) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        EquipmentSlotGroup slotGroup = EquipmentSlotGroup.bySlot((EquipmentSlot)slot);
        builder.add(Attributes.BURNING_TIME, new AttributeModifier(BossesRise.prefix("effect.fire"), -2.0, AttributeModifier.Operation.ADD_VALUE), slotGroup);
        if (slot == EquipmentSlot.HEAD) {
            builder.add(Attributes.ARMOR, new AttributeModifier(BossesRise.prefix("effect.prot"), 3.0, AttributeModifier.Operation.ADD_VALUE), slotGroup);
            builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(BossesRise.prefix("effect.tough"), 2.0, AttributeModifier.Operation.ADD_VALUE), slotGroup);
        }
        builder.add(BossesRiseAttributes.ROLL_COUNT.getDelegate(), new AttributeModifier(BossesRise.prefix("effect.roll." + slot.getSerializedName()), 1.0, AttributeModifier.Operation.ADD_VALUE), slotGroup);
        return builder;
    }

    public static class Boots
    extends DragonBonesItem {
        public Boots() {
            super(ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(35)).fireResistant().rarity(Rarity.UNCOMMON));
        }

        public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
            return BossesRise.prefix("textures/entities/dragon_armor_boots.png");
        }
    }

    public static class Leggings
    extends DragonBonesItem {
        public Leggings() {
            super(ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(35)).fireResistant().rarity(Rarity.UNCOMMON));
        }

        public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
            int index = Mth.floor((float)((float)(Math.round(AnimationTickHolder.getRenderTime()) % 40) * 0.4f));
            return BossesRise.prefix("textures/entities/dragon_leggings/dragon_leggings_" + index + ".png");
        }
    }

    public static class Chestplate
    extends DragonBonesItem {
        public Chestplate() {
            super(ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(35)).fireResistant().rarity(Rarity.UNCOMMON));
        }

        public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
            int index = Mth.floor((float)((float)(Math.round(AnimationTickHolder.getRenderTime()) % 40) * 0.4f));
            return BossesRise.prefix("textures/entities/dragon_chestplate/dragon_chestplate_" + index + ".png");
        }
    }
}

