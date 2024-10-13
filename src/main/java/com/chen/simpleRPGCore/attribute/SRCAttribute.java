package com.chen.simpleRPGCore.attribute;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class SRCAttribute extends RangedAttribute {

    public SRCAttribute(String pDescriptionId, double pDefaultValue, double pMin, double pMax) {
        super(pDescriptionId, pDefaultValue, pMin, pMax);
    }

    public boolean isPercentage = false;

    public SRCAttribute setPercentage(boolean percentage) {
        isPercentage = percentage;
        return this;
    }
}
