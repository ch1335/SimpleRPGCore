package com.chen.simpleRPGCore.API.objects;

import com.chen.simpleRPGCore.network.NetHandlers;
import com.chen.simpleRPGCore.network.SimpleDataSetter;
import net.minecraft.network.codec.ByteBufCodecs;

public class DataSetterTypes {
    public static SimpleDataSetter.DataSetterType<Integer> MANA = SimpleDataSetter.DataSetterType.create(ByteBufCodecs.INT, NetHandlers::setMana);
    public static SimpleDataSetter.DataSetterType<Integer> SHIELD_AMOUNT = SimpleDataSetter.DataSetterType.create(ByteBufCodecs.INT, NetHandlers::setShieldAmount);
}
