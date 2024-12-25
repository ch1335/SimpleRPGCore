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
import net.minecraft.resources.ResourceLocation;
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

    public static ImmutableList<ShieldType<? extends IShield>> getShieldsPriority() {
        return SHIELDS_PRIORITY;
    }

    public static void handleShieldAbsorb(LivingDamageEvent.Pre event) {
        LivingEntity livingEntity = event.getEntity();
        DamageSourceExtraData extraData = ((IDamageSourceExtension) event.getSource()).src$getExtraData();
        MobExtraData mobExtraData = livingEntity.getCapability(SRCCapabilities.SRC_MOB_DATA);
        if (mobExtraData != null && !event.getSource().is(SRCDamageTags.BYPASSES_SHIELD)) {
            for (ShieldType<? extends IShield> shieldType : SHIELDS_PRIORITY) {
                if (extraData.byPassesShields.stream().noneMatch(byPassesShield -> byPassesShield == shieldType)) {
                    IShield shield = mobExtraData.getDataHolder().shield.SHIELD_HOLDERS.get(shieldType);
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
        shieldsPriority.add(ShieldTypes.OVER_HEAL_SHIELD);
        NeoForge.EVENT_BUS.post(new RegisterShieldPriorityEvent(shieldsPriority));
        SHIELDS_PRIORITY = ImmutableList.copyOf(shieldsPriority);
    }

    public static class ShieldType<T extends IShield> {
        public static final Map<ResourceLocation, ShieldType<? extends IShield>> REGISTERED_SHIELD_TYPE = new HashMap<>();
        public final ResourceLocation resourceLocation;
        public final ShieldFactory<T> factory;

        public static <T extends IShield> ShieldType<T> getOrCreate(ResourceLocation resourceLocation, ShieldFactory<T> factory) {
            return (ShieldType<T>) REGISTERED_SHIELD_TYPE.computeIfAbsent(resourceLocation, l -> new ShieldType<>(l, factory));
        }

        public static ShieldType<UnitShield> getOrCreate(ResourceLocation resourceLocation) {
            return getOrCreate(resourceLocation, UnitShield::new);
        }

        public static <T extends IShield> ShieldType<T> get(ResourceLocation resourceLocation) {
            return (ShieldType<T>) REGISTERED_SHIELD_TYPE.get(resourceLocation);
        }

        private ShieldType(ResourceLocation resourceLocation, ShieldFactory<T> factory) {
            this.resourceLocation = resourceLocation;
            this.factory = factory;
        }
    }

    public interface ShieldFactory<T extends IShield> {
        T create();
    }

    public interface UnitShieldFactory<T extends IUnitShield> {
        T create();
    }
}
