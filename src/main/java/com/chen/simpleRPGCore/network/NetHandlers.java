package com.chen.simpleRPGCore.network;


import com.chen.simpleRPGCore.API.objects.DataSetterTypes;
import com.chen.simpleRPGCore.common.capability.MobExtraData;
import com.chen.simpleRPGCore.common.capability.PlayerExtraData;
import com.chen.simpleRPGCore.common.capability.SRCCapabilities;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class NetHandlers {
    public static void sendMana(Player player) {
        PlayerExtraData playerExtraData = player.getCapability(SRCCapabilities.SRC_PLAYER_DATA);
        if (playerExtraData != null) {
            float mana1 = playerExtraData.getMana();
            int mana = (int) Math.ceil(playerExtraData.getMana());
            DataSetterTypes.MANA.send(player, mana);
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

    public static void sendShieldAmount(Player player) {
        MobExtraData mobExtraData = player.getCapability(SRCCapabilities.SRC_MOB_DATA);
        if (mobExtraData != null) {
            DataSetterTypes.SHIELD_AMOUNT.send(player, (int) mobExtraData.getRenderShieldAmount());
        }
    }

    public static void setShieldAmount(int overHealAmount, IPayloadContext context) {
        Player player = context.player();
        if (player.level().isClientSide) {
            MobExtraData mobExtraData = player.getCapability(SRCCapabilities.SRC_MOB_DATA);
            if (mobExtraData != null) {
                mobExtraData.setRenderShieldAmount(overHealAmount);
            }
        }
    }
}
