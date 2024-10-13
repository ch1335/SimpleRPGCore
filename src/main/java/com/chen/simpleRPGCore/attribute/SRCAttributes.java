package com.chen.simpleRPGCore.attribute;

import com.chen.simpleRPGCore.SimpleRPGCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class SRCAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTE_DEFERRED_REGISTER = DeferredRegister.create(Registries.ATTRIBUTE, SimpleRPGCore.MODID);

    public static final DeferredHolder<Attribute, SRCAttribute> CRITICAL_CHANCE = ATTRIBUTE_DEFERRED_REGISTER.register("critical_chance", () -> (SRCAttribute) new SRCAttribute(makeDescriptionId("critical_chance"), 0.1, 0, 1).setPercentage(true).setSyncable(true));

    public static final DeferredHolder<Attribute, SRCAttribute> CRITICAL_DAMAGE = ATTRIBUTE_DEFERRED_REGISTER.register("critical_damage", () -> (SRCAttribute) new SRCAttribute(makeDescriptionId("critical_damage"), 1.5, 1, 114514).setPercentage(true).setSyncable(true));

    public static final DeferredHolder<Attribute, SRCAttribute> LIFE_STEAL = ATTRIBUTE_DEFERRED_REGISTER.register("life_steal", () -> (SRCAttribute) new SRCAttribute(makeDescriptionId("life_steal"), 0, 0, 114514).setPercentage(true).setSyncable(true));

    public static final DeferredHolder<Attribute, SRCAttribute> ARMOR_PENETRATION  = ATTRIBUTE_DEFERRED_REGISTER.register("armor_penetration", () -> (SRCAttribute) new SRCAttribute(makeDescriptionId("armor_penetration"), 0, 0, 114514).setSyncable(true));

    private static String makeDescriptionId(String s) {
        return SimpleRPGCore.MODID + ".attribute.name.generic." + s;
    }
}
