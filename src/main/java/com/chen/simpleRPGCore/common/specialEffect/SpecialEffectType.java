package com.chen.simpleRPGCore.common.specialEffect;

import net.minecraft.world.entity.Entity;

public class SpecialEffectType<T extends SpecialEffect> {
    private SpecialEffectFactory<T> factory;

    public SpecialEffectType(SpecialEffectFactory<T> factory) {

    }


    public interface SpecialEffectFactory<T extends SpecialEffect> {
        SpecialEffect create(Entity source);
    }
}
