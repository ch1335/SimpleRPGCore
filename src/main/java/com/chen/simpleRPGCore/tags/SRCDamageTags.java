package com.chen.simpleRPGCore.tags;

import com.chen.simpleRPGCore.SimpleRPGCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class SRCDamageTags {
    public static final TagKey<DamageType> MOD_MAGE_DAMAGE = create("mod_mage_damage");
    public static final TagKey<DamageType> BYPASSES_SHIELD = create("bypasses_shield");
    public static final TagKey<DamageType> CAN_CRITICAL = create("can_critical");
    public static final TagKey<DamageType> CAN_LIFE_STEAL = create("can_life_steal");
    public static final TagKey<DamageType> TRUE_DAMAGE = create("true_damage");

    private static TagKey<DamageType> create(String name) {
        return TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID, name));
    }
}
