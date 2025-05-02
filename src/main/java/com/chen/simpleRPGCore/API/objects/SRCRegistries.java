package com.chen.simpleRPGCore.API.objects;

import com.chen.simpleRPGCore.API.IShield;
import com.chen.simpleRPGCore.SimpleRPGCore;
import com.chen.simpleRPGCore.common.ShieldSystem.Shield;
import com.chen.simpleRPGCore.common.specialEffect.SpecialEffect;
import com.chen.simpleRPGCore.common.specialEffect.SpecialEffectType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class SRCRegistries {
    public static final ResourceKey<Registry<SpecialEffectType<? extends SpecialEffect>>> SPECIAL_EFFECT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID, "special_mob_effect"));
    public static final Registry<SpecialEffectType<? extends SpecialEffect>> SPECIAL_EFFECT = new RegistryBuilder<>(SPECIAL_EFFECT_KEY)
            .sync(true)
            .create();

    public static final ResourceKey<Registry<Shield.ShieldType<? extends IShield>>> SHIELD_TYPE_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID, "shield_type"));
    public static final Registry<Shield.ShieldType<? extends IShield>> SHIELD_TYPE = new RegistryBuilder<>(SHIELD_TYPE_KEY)
            .sync(true)
            .create();
}
