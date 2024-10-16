package com.chen.simpleRPGCore.common.capability;

public interface IPlayerExtraData {
    void tick();

    float getMana();

    void setMana(float mana);

    PlayerExtraData.DataHolder getDataHolder();
}
