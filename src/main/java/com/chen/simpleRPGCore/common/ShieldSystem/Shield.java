package com.chen.simpleRPGCore.common.ShieldSystem;

import com.chen.simpleRPGCore.API.IShield;
import com.chen.simpleRPGCore.API.IUnitShield;
import com.chen.simpleRPGCore.API.objects.ShieldTypes;
import com.chen.simpleRPGCore.common.DamageSourceExtraData;
import com.chen.simpleRPGCore.common.capability.MobExtraData;
import com.chen.simpleRPGCore.common.capability.SRCCapabilities;
import com.chen.simpleRPGCore.event.events.RegisterShieldPriorityEvent;
import com.chen.simpleRPGCore.mixinsAPI.minecraft.IDamageSourceExtension;
import com.chen.simpleRPGCore.tags.SRCDamageTags;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shield {
    private static ImmutableList<ShieldType<? extends IShield>> SHIELDS_PRIORITY = ImmutableList.of();
    public final Map<ShieldType<? extends IShield>, IShield> SHIELD_HOLDERS = new HashMap<>();

    public <T extends IShield> T getShield(ShieldType<T> shieldType) {
        return (T) SHIELD_HOLDERS.get(shieldType);
    }

    public static ImmutableList<ShieldType<?>> getShieldsPriority() {
        return SHIELDS_PRIORITY;
    }

    public static void handleShieldAbsorb(LivingDamageEvent.Pre event) {
        LivingEntity livingEntity = event.getEntity();
        DamageSourceExtraData extraData = ((IDamageSourceExtension) event.getSource()).src$getExtraData();
        MobExtraData mobExtraData = livingEntity.getCapability(SRCCapabilities.SRC_MOB_DATA);
        if (mobExtraData != null && !event.getSource().is(SRCDamageTags.BYPASSES_SHIELD)) {
            for (ShieldType<? extends IShield> holder : SHIELDS_PRIORITY) {
                if (extraData.byPassesShields.stream().noneMatch(byPassesShield -> byPassesShield == holder)) {
                    IShield shield = mobExtraData.getShieldManager().shield.SHIELD_HOLDERS.get(holder);
                    if (shield != null && shield.getTotalAmount() > 0) {
                        float newDamage = shield.tryAbsorb(livingEntity, shield, event.getSource(), event.getNewDamage());
                        event.setNewDamage(newDamage);
                    }
                }
            }
        }
    }

    public void init() {
        SHIELDS_PRIORITY.forEach(shieldType -> {
            SHIELD_HOLDERS.put(shieldType, shieldType.factory.create());
        });
    }

    public static void setPriority() {
        List<ShieldType<? extends IShield>> shieldsPriority = new ArrayList<>(64);
        shieldsPriority.add(ShieldTypes.OVER_HEAL_SHIELD.get());
        NeoForge.EVENT_BUS.post(new RegisterShieldPriorityEvent(shieldsPriority));
        SHIELDS_PRIORITY = ImmutableList.copyOf(shieldsPriority);
    }

    public record ShieldType<T extends IShield>(ShieldFactory<T> factory) {
    }

    public interface ShieldFactory<T extends IShield> {
        T create();
    }

    public interface UnitShieldFactory<T extends IUnitShield> {
        T create();
    }
}
