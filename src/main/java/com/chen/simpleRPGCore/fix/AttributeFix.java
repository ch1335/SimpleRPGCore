package com.chen.simpleRPGCore.fix;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import java.lang.reflect.Field;

public class AttributeFix {
    public static void fix() {
        try {
            Field field = RangedAttribute.class.getDeclaredField("maxValue");
            field.setAccessible(true);
            field.setDouble(Attributes.ARMOR.value(),114514);
            field.setDouble(Attributes.MAX_HEALTH.value(),114514);
            field.setDouble(Attributes.ARMOR_TOUGHNESS.value(),114514);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
