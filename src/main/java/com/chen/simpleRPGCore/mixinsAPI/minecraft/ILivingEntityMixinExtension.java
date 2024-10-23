package com.chen.simpleRPGCore.mixinsAPI.minecraft;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface ILivingEntityMixinExtension {
    BiConsumer<Holder<Attribute>, AttributeModifier> EMPTY_CONSUMER = (attributeHolder, attributeModifier) -> {};
    ResourceLocation OVER_HEAL = ResourceLocation.fromNamespaceAndPath("apothic_attributes","src_over_heal");

    void src$healBy(Entity healer, float amount);
}
