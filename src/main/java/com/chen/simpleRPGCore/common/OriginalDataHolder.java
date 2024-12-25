package com.chen.simpleRPGCore.common;

public class OriginalDataHolder<T> {
    private T originalData;
    public T newData;


    public OriginalDataHolder(T original) {
        this.originalData = original;
        this.newData = original;
    }

    public static <V> OriginalDataHolder<V> crate(V value){
        return new OriginalDataHolder<>(value);
    }

    public void restToOriginal() {
        newData = originalData;
    }

    public T getOriginalData() {
        return originalData;
    }

    public T getNew() {
        return newData;
    }

    public void setOriginalData(T newOriginal) {
        originalData = newOriginal;
    }

    public void setNew(T newAmount) {
        this.newData = newAmount;
    }

    public static class AttributeOriginalDataHolder {
        private final OriginalDataHolder<Double> data;

        public AttributeOriginalDataHolder(OriginalDataHolder<Double> attributeOriginalData) {
            data = attributeOriginalData;
        }

        public static AttributeOriginalDataHolder of(OriginalDataHolder<Double> attributeOriginalData) {
            return new AttributeOriginalDataHolder(attributeOriginalData);
        }

        //get original amount. if Attribute don't exist then return def
        public double getOriginal(double def) {
            return data != null ? data.getOriginalData() : def;
        }

        //get new amount. if Attribute don't exist then return def
        public double getNew(double def) {
            return data != null ? data.newData : def;
        }

        public void setOriginal(double newOriginal) {
            if (data != null) data.originalData = newOriginal;
        }

        //set the new amount if Attribute is exist
        public void setNew(double newAmount) {
            if (data != null) data.newData = newAmount;
        }
    }
}
