package com.chen.simpleRPGCore.mixinsAPI.minecraft;

import net.minecraft.world.entity.Entity;

public interface ILivingEntityMixinExtension {
    void src$healBy(Entity healer, float amount);
}
