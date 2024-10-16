package com.chen.simpleRPGCore.network;

import com.chen.simpleRPGCore.SimpleRPGCore;
import com.chen.simpleRPGCore.attachmentType.SRCAttachmentTypes;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;


public record PlayerExtraDataSycPack(SycType sycType, CompoundTag data) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<PlayerExtraDataSycPack> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID, "player_data"));


    public static final StreamCodec<ByteBuf, PlayerExtraDataSycPack> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            (pack)->pack.sycType.ordinal(),
            ByteBufCodecs.COMPOUND_TAG,
            (pack)->pack.data,
            (sycType,data)->new PlayerExtraDataSycPack(SycType.values()[sycType],data)
    );

    public static void SycMana(ServerPlayer player,float mana){
        CompoundTag tag = new CompoundTag();
        tag.putFloat("value",mana);
        PacketDistributor.sendToPlayer(player, new PlayerExtraDataSycPack(PlayerExtraDataSycPack.SycType.MANA,tag));
    }

    public static void clientHandler(final PlayerExtraDataSycPack pack, final IPayloadContext context){
        Player player = context.player();
        if (pack.sycType == SycType.MANA) {
            player.getData(SRCAttachmentTypes.PLAYER_DATA).mana = pack.data.getFloat("value");
        }
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public enum SycType{
        MANA
    }
}
