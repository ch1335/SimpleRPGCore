package com.chen.simpleRPGCore.attribute;

import com.chen.simpleRPGCore.SimpleRPGCore;
import dev.shadowsoffire.apothic_attributes.api.ALObjects;
import dev.shadowsoffire.apothic_attributes.impl.PercentBasedAttribute;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class SRCAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTE_DEFERRED_REGISTER = DeferredRegister.create(Registries.ATTRIBUTE, SimpleRPGCore.MODID);

    public static DeferredHolder<Attribute, Attribute> CRITICAL_CHANCE;

    public static DeferredHolder<Attribute, Attribute> CRITICAL_DAMAGE;

    public static DeferredHolder<Attribute, Attribute> LIFE_STEAL;

    public static DeferredHolder<Attribute, Attribute> ARMOR_PENETRATION;

    public static final DeferredHolder<Attribute, Attribute> MINING_FORTUNE = ATTRIBUTE_DEFERRED_REGISTER.register("mining_fortune", () -> new SRCAttribute(makeDescriptionId("mining_fortune"), 0, 0, 114514).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> MOB_LOOTING = ATTRIBUTE_DEFERRED_REGISTER.register("mob_looting", () -> new SRCAttribute(makeDescriptionId("mob_looting"), 0, 0, 114514).setSyncable(true));

    public static DeferredHolder<Attribute, Attribute> HEAL_EFFECT;

    public static final DeferredHolder<Attribute, Attribute> MENDING = ATTRIBUTE_DEFERRED_REGISTER.register("mending", () -> new SRCAttribute(makeDescriptionId("mending"), 1, 0, 114514).setPercentage(true).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> MAX_MANA = ATTRIBUTE_DEFERRED_REGISTER.register("max_mana", () -> new SRCAttribute(makeDescriptionId("max_mana"), 100, 0, 114514).setSyncable(true));

    public static DeferredHolder<Attribute, Attribute> MANA_REGAIN;

    public static final DeferredHolder<Attribute, Attribute> MANA_POWER = ATTRIBUTE_DEFERRED_REGISTER.register("mana_power", () -> new SRCAttribute(makeDescriptionId("mana_power"), 0, 0, 114514).setSyncable(true));

    public static DeferredHolder<Attribute, Attribute> MANA_COST;

    public static DeferredHolder<Attribute, Attribute> OVER_HEAL;
    public static DeferredHolder<Attribute, Attribute> MAX_OVER_HEAL_AMOUNT = ATTRIBUTE_DEFERRED_REGISTER.register("max_over_heal_amount", () -> new SRCAttribute(makeDescriptionId("max_over_heal_amount"), 4, 0, 114514).setSyncable(true));

    static {
        if (SimpleRPGCore.apothicAttributesLoaded) {
            CRITICAL_CHANCE = (DeferredHolder<Attribute, Attribute>) ALObjects.Attributes.CRIT_CHANCE;
            CRITICAL_DAMAGE = (DeferredHolder<Attribute, Attribute>) ALObjects.Attributes.CRIT_DAMAGE;
            LIFE_STEAL = (DeferredHolder<Attribute, Attribute>) ALObjects.Attributes.LIFE_STEAL;
            ARMOR_PENETRATION = (DeferredHolder<Attribute, Attribute>) ALObjects.Attributes.ARMOR_PIERCE;
            HEAL_EFFECT = (DeferredHolder<Attribute, Attribute>) ALObjects.Attributes.HEALING_RECEIVED;
            MANA_REGAIN = ATTRIBUTE_DEFERRED_REGISTER.register("mana_regain", () -> new PercentBasedAttribute(makeDescriptionId("mana_regain"), 1, 0, 114514).setSyncable(true));
            MANA_COST = ATTRIBUTE_DEFERRED_REGISTER.register("mana_cost", () -> new PercentBasedAttribute(makeDescriptionId("mana_cost"), 1, 0.1, 114514).setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE));
            OVER_HEAL = (DeferredHolder<Attribute, Attribute>) ALObjects.Attributes.OVERHEAL;
        } else {
            CRITICAL_CHANCE = ATTRIBUTE_DEFERRED_REGISTER.register("critical_chance", () -> new SRCAttribute(makeDescriptionId("critical_chance"), 0, 0, 1).setPercentage(true).setSyncable(true));
            CRITICAL_DAMAGE = ATTRIBUTE_DEFERRED_REGISTER.register("critical_damage", () -> new SRCAttribute(makeDescriptionId("critical_damage"), 1.5, 1, 114514).setPercentage(true).setSyncable(true));
            LIFE_STEAL = ATTRIBUTE_DEFERRED_REGISTER.register("life_steal", () -> new SRCAttribute(makeDescriptionId("life_steal"), 0, 0, 114514).setPercentage(true).setSyncable(true));
            ARMOR_PENETRATION = ATTRIBUTE_DEFERRED_REGISTER.register("armor_penetration", () -> new SRCAttribute(makeDescriptionId("armor_penetration"), 0, 0, 114514).setSyncable(true));
            HEAL_EFFECT = ATTRIBUTE_DEFERRED_REGISTER.register("heal_effect", () -> new SRCAttribute(makeDescriptionId("heal_effect"), 1, 0, 114514).setPercentage(true).setSyncable(true));
            MANA_REGAIN = ATTRIBUTE_DEFERRED_REGISTER.register("mana_regain", () -> new SRCAttribute(makeDescriptionId("mana_regain"), 1, 0, 114514).setPercentage(true).setSyncable(true));
            MANA_COST = ATTRIBUTE_DEFERRED_REGISTER.register("mana_cost", () -> new SRCAttribute(makeDescriptionId("mana_cost"), 1, 0.1, 114514).setPercentage(true).setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE));
            OVER_HEAL = ATTRIBUTE_DEFERRED_REGISTER.register("over_heal", () -> new SRCAttribute(makeDescriptionId("over_heal"), 1, 0.1, 114514).setPercentage(true).setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE));
        }
    }

    private static String makeDescriptionId(String s) {
        return SimpleRPGCore.MODID + ".attribute.name.generic." + s;
    }
}
