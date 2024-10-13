package com.chen.simpleRPGCore.event;

import com.chen.simpleRPGCore.event.events.AddDamageSourceExtraAttributesEvent;
import com.chen.simpleRPGCore.event.events.ModPreCriticalEvent;
import com.chen.simpleRPGCore.event.events.ModifyDamageEvent;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

import java.util.Set;

public class SRCEventFactory {

    public static void modifyDamageBeforeCritical(DamageContainer damageContainer, LivingEntity target){
        NeoForge.EVENT_BUS.post(new ModifyDamageEvent.BeforeCritical(damageContainer,target));
    }

    public static void modifyDamageAfterCritical(DamageContainer damageContainer, LivingEntity target){
        NeoForge.EVENT_BUS.post(new ModifyDamageEvent.AfterCritical(damageContainer,target));
    }

    public static boolean modPreCritical(DamageContainer damageContainer, LivingEntity target){
       return !NeoForge.EVENT_BUS.post(new ModPreCriticalEvent(damageContainer,target)).isCanceled();
    }

    public static void addDamageSourceExtraAttributes(Set<Holder<Attribute>> attributes){
        NeoForge.EVENT_BUS.post(new AddDamageSourceExtraAttributesEvent(attributes));
    }
}
