package com.chen.simpleRPGCore.item;

import com.chen.simpleRPGCore.SimpleRPGCore;
import com.chen.simpleRPGCore.attribute.SRCAttributes;
import com.chen.simpleRPGCore.item.items.TestItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SRCItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SimpleRPGCore.MODID);

    public static final DeferredItem<TestItem> TEST_ITEM = ITEMS.registerItem("test_item", properties -> {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        builder.add(SRCAttributes.CRITICAL_DAMAGE,new AttributeModifier(ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID,"test"),1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
        builder.add(SRCAttributes.CRITICAL_CHANCE,new AttributeModifier(ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID,"test"),1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
        builder.add(SRCAttributes.LIFE_STEAL,new AttributeModifier(ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID,"test"),1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
        builder.add(SRCAttributes.ARMOR_PENETRATION,new AttributeModifier(ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID,"test"),1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
        builder.add(SRCAttributes.MINING_FORTUNE,new AttributeModifier(ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID,"test"),1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
        builder.add(SRCAttributes.MOB_LOOTING,new AttributeModifier(ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID,"test"),1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
        builder.add(SRCAttributes.HEAL_EFFECT,new AttributeModifier(ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID,"test"),1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
        builder.add(SRCAttributes.MENDING,new AttributeModifier(ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID,"test"),1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
        builder.add(SRCAttributes.MAX_MANA,new AttributeModifier(ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID,"test"),1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
        builder.add(SRCAttributes.MANA_REGAIN,new AttributeModifier(ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID,"test"),1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
        builder.add(SRCAttributes.MANA_POWER,new AttributeModifier(ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID,"test"),1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
        builder.add(SRCAttributes.MANA_COST,new AttributeModifier(ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID,"test"),1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND);
        properties.attributes(builder.build());
        return new TestItem(properties);
    });
}
