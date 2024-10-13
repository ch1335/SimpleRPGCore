package com.chen.simpleRPGCore.config;

import com.chen.simpleRPGCore.attribute.SRCAttributes;
import com.chen.simpleRPGCore.common.DamageSourceExtraData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class ConfigDataHolder {
    public static ConfigDataHolder config = new ConfigDataHolder();
    public ArmorAbsorbConsumer armorAbsorbFunction = (living, damage, damageSource, armorValue, armorToughness, extraData) -> {
        double armorPenetration = Math.max(0, extraData.getAttributeOriginalHolder(SRCAttributes.ARMOR_PENETRATION).getNew(0) - armorToughness);
        double effectArmor = Math.max(0, armorValue - armorPenetration);
        return (float) (damage * (1 - effectArmor / (effectArmor + 5)));
    };

    public interface ArmorAbsorbConsumer {
        float accept(LivingEntity living, float damage, DamageSource damageSource, float armorValue, float armorToughness, DamageSourceExtraData extraData);
    }

    public void setArmorAbsorbFunction(ArmorAbsorbConsumer consumer) {
        armorAbsorbFunction = consumer;
    }
}
