package com.chen.simpleRPGCore.network;


import com.chen.simpleRPGCore.API.objects.DataSetterTypes;
import com.chen.simpleRPGCore.common.capability.MobExtraData;
import com.chen.simpleRPGCore.common.capability.PlayerExtraData;
import com.chen.simpleRPGCore.common.capability.SRCCapabilities;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class NetHandlers {
    public static void sendMana(Player player) {
        PlayerExtraData playerData = player.getCapability(SRCCapabilities.SRC_PLAYER_DATA);
        if (playerData != null) {
            DataSetterTypes.MANA.send(player, (int) playerData.getMana());
        }
    }

    public static void setMana(int mana, IPayloadContext context) {
        Player player = context.player();
        if (player.level().isClientSide) {
            PlayerExtraData playerExtraData = player.getCapability(SRCCapabilities.SRC_PLAYER_DATA);
            if (playerExtraData != null) {
                playerExtraData.setMana(mana);
            }
        }
    }


    public static void sendShieldAmount(Player player, float amount) {
        MobExtraData mobExtraData = player.getCapability(SRCCapabilities.SRC_MOB_DATA);
        if (mobExtraData != null) {
            if (amount == -1) {
                DataSetterTypes.SHIELD_AMOUNT.send(player, mobExtraData.getShieldManager().getShieldAmount());
            } else {
                DataSetterTypes.SHIELD_AMOUNT.send(player, amount);
            }
        }
    }

    public static void setShieldAmount(float overHealAmount, IPayloadContext context) {
        Player player = context.player();
        if (player.level().isClientSide) {
            MobExtraData mobExtraData = player.getCapability(SRCCapabilities.SRC_MOB_DATA);
            if (mobExtraData != null) {
                mobExtraData.getShieldManager().renderShieldAmount = overHealAmount;
            }
        }
    }

}
