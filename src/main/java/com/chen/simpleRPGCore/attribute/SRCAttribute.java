package com.chen.simpleRPGCore.attribute;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class SRCAttribute extends RangedAttribute {

    public boolean isPercentage = false;
    public boolean isReversal = false;

    public SRCAttribute(String pDescriptionId, double pDefaultValue, double pMin, double pMax) {
        super(pDescriptionId, pDefaultValue, pMin, pMax);
    }

    public SRCAttribute setPercentage(boolean percentage) {
        isPercentage = percentage;
        return this;
    }

    public SRCAttribute setReversal(boolean reversal) {
        isReversal = reversal;
        return this;
    }
}
