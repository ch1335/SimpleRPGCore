package com.chen.simpleRPGCore.network;

import com.chen.simpleRPGCore.attachmentType.SRCAttachmentTypes;
import com.chen.simpleRPGCore.common.capability.PlayerExtraData;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {
    public static void handlePlayerData(final PlayerExtraData.DataHolder data, final IPayloadContext context){
        context.player().setData(SRCAttachmentTypes.PLAYER_DATA,data);
    }
}
