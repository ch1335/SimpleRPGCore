package com.chen.simpleRPGCore;

import com.chen.simpleRPGCore.attribute.SRCAttributes;
import com.chen.simpleRPGCore.common.DamageSourceExtraData;
import dev.shadowsoffire.placebo.config.Configuration;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class SimpleRPGConfig {
    public static final ClientConfig clientConfig = new ClientConfig();
    public static final CommonConfig commonConfig = new CommonConfig();

    public static void load() {
        Configuration cfg = new Configuration(SimpleRPGCore.getConfigFile(SimpleRPGCore.MODID));

        clientConfig.load(cfg);
        commonConfig.load(cfg);
        if (cfg.hasChanged()) {
            cfg.save();
        }
    }

    public static class ClientConfig {
        public boolean enableManaBar = true;

        public int manaBarX = 100;
        public int manaBarY = -13;
        public void load(Configuration cfg) {
            enableManaBar = cfg.getBoolean("enableManaBar", "Client", true, "Configure whether to enable mana bar");
            manaBarX = cfg.getInt("manaBarX","Client",100,Integer.MIN_VALUE,Integer.MAX_VALUE,"manaBar x position (center is zero)");
            manaBarY = cfg.getInt("manaBarY","Client",-13,Integer.MIN_VALUE,Integer.MAX_VALUE,"manaBar y position (underside is zero)");
        }
    }

    public static class CommonConfig {
        public boolean useVanillaArmorAbsorbFunction = false;
        public boolean enableSimpleAttributeFix = true;
        public ArmorAbsorbConsumer armorAbsorbFunction = (living, damage, damageSource, armorValue, armorToughness, extraData) -> {
            double armorPenetration = Math.max(0, extraData.getAttributeOriginalHolder(SRCAttributes.ARMOR_PENETRATION).getNew(0) - armorToughness);
            double effectArmor = Math.max(0, armorValue - armorPenetration);
            return (float) (damage * (1 - effectArmor / (effectArmor + 5)));
        };

        public void load(Configuration cfg) {
            useVanillaArmorAbsorbFunction = cfg.getBoolean("useVanillaArmorAbsorbFunction", "Common", false, "Configure whether use Vanilla ArmorAbsorbFunction\n special if you load apothicAttributes this config will be locked to true and use apothicAttributes CombatRules");
            enableSimpleAttributeFix = cfg.getBoolean("enableSimpleAttributeFix","Common",true,"enable maxHealth , maxArmor , maxArmorToughness AttributeFix,if true , will set their maximum value to a big number.");
            if (SimpleRPGCore.apothicAttributesLoaded) useVanillaArmorAbsorbFunction = true;
        }

        public void setArmorAbsorbFunction(ArmorAbsorbConsumer consumer) {
            armorAbsorbFunction = consumer;
        }

        public interface ArmorAbsorbConsumer {
            float accept(LivingEntity living, float damage, DamageSource damageSource, float armorValue, float armorToughness, DamageSourceExtraData extraData);
        }
    }
}
