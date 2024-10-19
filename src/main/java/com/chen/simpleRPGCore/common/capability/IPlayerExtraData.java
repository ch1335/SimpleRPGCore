package com.chen.simpleRPGCore.common.capability;

public interface IPlayerExtraData {
    void tick();

    float getMana();

    void setMana(float mana);

    boolean costMana(float amount, boolean absolute, String reason);

    PlayerExtraData.DataHolder getDataHolder();
}
