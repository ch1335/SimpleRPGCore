package com.chen.simpleRPGCore.event.events;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;

public class LivingHealByOtherEvent extends LivingHealEvent {

    private final Entity healer;

    public LivingHealByOtherEvent(LivingEntity entity, Entity healer, float amount) {
        super(entity, amount);
        this.healer = healer;
    }

    public Entity getHealer() {
        return healer;
    }
}
