package com.chen.simpleRPGCore.event.events;

import com.chen.simpleRPGCore.common.DamageSourceExtraData;
import com.chen.simpleRPGCore.mixinsAPI.minecraft.IDamageSourceMixin;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

public abstract class ModifyDamageEvent extends Event implements ICancellableEvent {
    private final DamageContainer damageContainer;
    private final LivingEntity entity;
    private final DamageSourceExtraData extraData;
    public ModifyDamageEvent(DamageContainer damageContainer, LivingEntity livingEntity) {
        this.damageContainer = damageContainer;
        this.entity = livingEntity;
        this.extraData = ((IDamageSourceMixin)damageContainer.getSource()).src$getExtraData();
    }

    public DamageContainer getDamageContainer() {
        return damageContainer;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public DamageSourceExtraData getExtraData() {
        return extraData;
    }

    public static class BeforeCritical extends ModifyDamageEvent{

        public BeforeCritical(DamageContainer damageContainer, LivingEntity entity) {
            super(damageContainer, entity);
        }
    }

    public static class AfterCritical extends ModifyDamageEvent{

        public AfterCritical(DamageContainer damageContainer, LivingEntity entity) {
            super(damageContainer, entity);
        }
    }

}
