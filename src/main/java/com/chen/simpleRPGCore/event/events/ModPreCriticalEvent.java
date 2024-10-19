package com.chen.simpleRPGCore.event.events;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

public class ModPreCriticalEvent extends Event implements ICancellableEvent {
    private final DamageContainer damageContainer;
    private final LivingEntity entity;

    public ModPreCriticalEvent(DamageContainer damageContainer, LivingEntity entity) {
        this.damageContainer = damageContainer;
        this.entity = entity;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public DamageContainer getDamageContainer() {
        return damageContainer;
    }
}
