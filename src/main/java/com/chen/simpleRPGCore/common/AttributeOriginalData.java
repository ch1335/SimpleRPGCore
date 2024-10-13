package com.chen.simpleRPGCore.common;

public class AttributeOriginalData {
    private final Double original;
    public Double newAmount;


    public AttributeOriginalData(Double original) {
        this.original = original;
        newAmount = original;
    }

    public Double getOriginal() {
        return original;
    }

    // rest the new amount to original
    public void restToOriginal() {
        newAmount = original;
    }

    public static class AttributeOriginalDataHolder {
        private final AttributeOriginalData data;

        public AttributeOriginalDataHolder(AttributeOriginalData attributeOriginalData) {
            data = attributeOriginalData;
        }

        public static AttributeOriginalDataHolder of(AttributeOriginalData attributeOriginalData) {
            return new AttributeOriginalDataHolder(attributeOriginalData);
        }

        //get original amount. if Attribute don't exist then return def
        public double getOriginal(double def) {
            return data != null ? data.getOriginal() : def;
        }
        //get new amount. if Attribute don't exist then return def
        public double getNew(double def) {
            return data != null ? data.newAmount : def;
        }

        //set the new amount if Attribute is exist
        public void setNew(double newAmount) {
            if (data != null) data.newAmount = newAmount;
        }
    }
}
