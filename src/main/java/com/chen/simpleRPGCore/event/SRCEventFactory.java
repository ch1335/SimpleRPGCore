package com.chen.simpleRPGCore.event;

import com.chen.simpleRPGCore.event.events.*;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

import java.util.Set;

public class SRCEventFactory {

    public static boolean modifyDamageBeforeCritical(DamageContainer damageContainer, LivingEntity target) {
        return NeoForge.EVENT_BUS.post(new ModifyDamageEvent.BeforeCritical(damageContainer, target)).isCanceled();
    }

    public static boolean modifyDamageAfterCritical(DamageContainer damageContainer, LivingEntity target) {
        return NeoForge.EVENT_BUS.post(new ModifyDamageEvent.AfterCritical(damageContainer, target)).isCanceled();
    }

    public static boolean modPreCritical(DamageContainer damageContainer, LivingEntity target) {
        return !NeoForge.EVENT_BUS.post(new ModPreCriticalEvent(damageContainer, target)).isCanceled();
    }

    public static void addDamageSourceExtraAttributes(Set<Holder<Attribute>> attributes) {
        NeoForge.EVENT_BUS.post(new AddDamageSourceExtraAttributesEvent(attributes));
    }

    public static float onLivingBeHeal(LivingEntity livingEntity, Entity healer, float amount) {
        LivingHealByOtherEvent event = NeoForge.EVENT_BUS.post(new LivingHealByOtherEvent(livingEntity, healer, amount));
        return event.isCanceled() ? 0 : event.getAmount();
    }

    public static float onPlayerCostMana(Player player, float amount, String reason) {
        PlayerCostManaEvent event = NeoForge.EVENT_BUS.post(new PlayerCostManaEvent(player, amount, reason));
        return event.isCanceled() ? 0 : event.getAmount();
    }
}
