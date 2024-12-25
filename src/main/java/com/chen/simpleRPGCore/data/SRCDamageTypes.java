package com.chen.simpleRPGCore.data;

import com.chen.simpleRPGCore.SimpleRPGCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class SRCDamageTypes {
    public static final ResourceKey<DamageType> TRUE_DAMAGE_TAG_HOLDER = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID, "true_damage_tag_holder"));
    public static final ResourceKey<DamageType> BYPASSES_COOLDOWN_TAG_HOLDER = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID, "bypasses_cooldown_tag_holder"));
    public static final ResourceKey<DamageType> CAN_CRITICAL_TAG_HOLDER = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID, "can_critical_tag_holder"));
    public static final ResourceKey<DamageType> CAN_LIFE_STEAL_TAG_HOLDER = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID, "can_life_steal_tag_holder"));

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(TRUE_DAMAGE_TAG_HOLDER, new DamageType("true_damage_tag_holder", 0.1F));
        context.register(BYPASSES_COOLDOWN_TAG_HOLDER, new DamageType("bypasses_cooldown_tag_holder", 0.1F));
        context.register(CAN_CRITICAL_TAG_HOLDER, new DamageType("can_critical_tag_holder", 0.1F));
        context.register(CAN_LIFE_STEAL_TAG_HOLDER, new DamageType("can_life_steal_tag_holder", 0.1F));
    }
}
