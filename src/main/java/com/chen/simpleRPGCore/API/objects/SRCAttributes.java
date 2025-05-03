package com.chen.simpleRPGCore.API.objects;

import com.chen.simpleRPGCore.SimpleRPGCore;
import com.chen.simpleRPGCore.mixinsAPI.minecraft.IDataMainMixinExtension;
import dev.shadowsoffire.apothic_attributes.api.ALObjects;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.common.PercentageAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class SRCAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTE_DEFERRED_REGISTER = DeferredRegister.create(Registries.ATTRIBUTE, SimpleRPGCore.MODID);

    public static final DeferredHolder<Attribute, Attribute> CRITICAL_CHANCE;

    public static final DeferredHolder<Attribute, Attribute> CRITICAL_DAMAGE;

    public static final DeferredHolder<Attribute, Attribute> LIFE_STEAL;

    public static final DeferredHolder<Attribute, Attribute> ARMOR_PENETRATION;

    public static final DeferredHolder<Attribute, Attribute> HEAL_EFFECT;

    public static final DeferredHolder<Attribute, Attribute> OVER_HEAL;

    public static final DeferredHolder<Attribute, Attribute> MINING_FORTUNE = ATTRIBUTE_DEFERRED_REGISTER.register("mining_fortune", () -> new RangedAttribute(makeDescriptionId("mining_fortune"), 0, 0, 114514).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> MOB_LOOTING = ATTRIBUTE_DEFERRED_REGISTER.register("mob_looting", () -> new RangedAttribute(makeDescriptionId("mob_looting"), 0, 0, 114514).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> MENDING = ATTRIBUTE_DEFERRED_REGISTER.register("mending", () -> new PercentageAttribute(makeDescriptionId("mending"), 1, 0, 114514).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> MAX_MANA;

    public static final DeferredHolder<Attribute, Attribute> MANA_REGAIN;

    public static final DeferredHolder<Attribute, Attribute> MANA_POWER;

    public static final DeferredHolder<Attribute, Attribute> MANA_COST = ATTRIBUTE_DEFERRED_REGISTER.register("mana_cost", () -> new PercentageAttribute(makeDescriptionId("mana_cost"), 1, 0.1, 114514).setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE));

    public static final DeferredHolder<Attribute, Attribute> MAX_OVER_HEAL_PERCENTAGE = ATTRIBUTE_DEFERRED_REGISTER.register("max_over_heal_percentage", () -> new PercentageAttribute(makeDescriptionId("max_over_heal_percentage"), 0, 0, 114514).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> CAUSE_DAMAGE = ATTRIBUTE_DEFERRED_REGISTER.register("cause_damage", () -> new PercentageAttribute(makeDescriptionId("cause_damage"), 1, 0, 114514).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> RECEIVE_DAMAGE = ATTRIBUTE_DEFERRED_REGISTER.register("receive_damage", () -> new PercentageAttribute(makeDescriptionId("receive_damage"), 1, 0, 114514).setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE));

    static {
        boolean apothicAttributesLoaded = SimpleRPGCore.apothicAttributesLoaded && !IDataMainMixinExtension.isRunData.get();
        boolean ironsSSpellBooksLoaded = SimpleRPGCore.ironsSSpellBooksLoaded && !IDataMainMixinExtension.isRunData.get();

        if (apothicAttributesLoaded) {
            CRITICAL_CHANCE = (DeferredHolder<Attribute, Attribute>) ALObjects.Attributes.CRIT_CHANCE;
            CRITICAL_DAMAGE = (DeferredHolder<Attribute, Attribute>) ALObjects.Attributes.CRIT_DAMAGE;
            LIFE_STEAL = (DeferredHolder<Attribute, Attribute>) ALObjects.Attributes.LIFE_STEAL;
            ARMOR_PENETRATION = (DeferredHolder<Attribute, Attribute>) ALObjects.Attributes.ARMOR_PIERCE;
            HEAL_EFFECT = (DeferredHolder<Attribute, Attribute>) ALObjects.Attributes.HEALING_RECEIVED;
            OVER_HEAL = (DeferredHolder<Attribute, Attribute>) ALObjects.Attributes.OVERHEAL;
        } else {
            CRITICAL_CHANCE = ATTRIBUTE_DEFERRED_REGISTER.register("critical_chance", () -> new PercentageAttribute(makeDescriptionId("critical_chance"), 0.05, 0, 1).setSyncable(true));
            CRITICAL_DAMAGE = ATTRIBUTE_DEFERRED_REGISTER.register("critical_damage", () -> new PercentageAttribute(makeDescriptionId("critical_damage"), 1.5, 1, 114514).setSyncable(true));
            LIFE_STEAL = ATTRIBUTE_DEFERRED_REGISTER.register("life_steal", () -> new PercentageAttribute(makeDescriptionId("life_steal"), 0, 0, 114514).setSyncable(true));
            ARMOR_PENETRATION = ATTRIBUTE_DEFERRED_REGISTER.register("armor_penetration", () -> new RangedAttribute(makeDescriptionId("armor_penetration"), 0, 0, 114514).setSyncable(true));
            HEAL_EFFECT = ATTRIBUTE_DEFERRED_REGISTER.register("heal_effect", () -> new PercentageAttribute(makeDescriptionId("heal_effect"), 1, 0, 114514).setSyncable(true));
            OVER_HEAL = ATTRIBUTE_DEFERRED_REGISTER.register("over_heal", () -> new PercentageAttribute(makeDescriptionId("over_heal"), 0, 0, 114514).setSyncable(true));
        }

        if (ironsSSpellBooksLoaded) {
            MAX_MANA = AttributeRegistry.MAX_MANA;
            MANA_REGAIN = AttributeRegistry.MANA_REGEN;
            MANA_POWER = AttributeRegistry.SPELL_POWER;
        } else {
            MAX_MANA = ATTRIBUTE_DEFERRED_REGISTER.register("max_mana", () -> new RangedAttribute(makeDescriptionId("max_mana"), 100, 0, 114514).setSyncable(true));
            MANA_REGAIN = ATTRIBUTE_DEFERRED_REGISTER.register("mana_regain", () -> new PercentageAttribute(makeDescriptionId("mana_regain"), 1, 0, 114514).setSyncable(true));
            MANA_POWER = ATTRIBUTE_DEFERRED_REGISTER.register("mana_power", () -> new RangedAttribute(makeDescriptionId("mana_power"), 0, 0, 114514).setSyncable(true));
        }
    }

    public enum AttributeResourceLocationHolder {
        CRITICAL_CHANCE("critical_chance"),
        CRITICAL_DAMAGE("critical_damage"),
        LIFE_STEAL("life_steal"),
        ARMOR_PENETRATION("armor_penetration"),
        HEAL_EFFECT("heal_effect"),
        OVER_HEAL("over_heal"),

        MAX_MANA("max_mana"),
        MANA_REGAIN("mana_regain"),
        MANA_POWER("mana_power");
        public final String name;

        AttributeResourceLocationHolder(String name) {
            this.name = name;
        }

        public ResourceLocation getResourceLocation() {
            return ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID, name);
        }

    }

    private static String makeDescriptionId(String s) {
        return SimpleRPGCore.MODID + ".attribute.name.generic." + s;
    }
}
