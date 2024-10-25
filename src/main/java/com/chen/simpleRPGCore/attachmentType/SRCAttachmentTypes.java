package com.chen.simpleRPGCore.attachmentType;

import com.chen.simpleRPGCore.SimpleRPGCore;
import com.chen.simpleRPGCore.common.capability.PlayerExtraData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class SRCAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, SimpleRPGCore.MODID);

    public static final Supplier<AttachmentType<PlayerExtraData.DataHolder>> PLAYER_DATA = ATTACHMENT_TYPES.register(
            "player_data", () -> AttachmentType.serializable((holder) -> new PlayerExtraData.DataHolder(0)).build()
    );
}
