package com.chen.simpleRPGCore.mixinsAPI.minecraft;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public interface ILivingEntityMixinExtension {
    ResourceLocation OVER_HEAL = ResourceLocation.fromNamespaceAndPath("apothic_attributes","src_over_heal");

    void src$healBy(Entity healer, float amount);
}
