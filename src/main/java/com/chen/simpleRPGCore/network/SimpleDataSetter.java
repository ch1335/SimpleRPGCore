package com.chen.simpleRPGCore.network;

import com.chen.simpleRPGCore.SimpleRPGCore;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.BiConsumer;

public record SimpleDataSetter<T>(int id, StreamCodec<ByteBuf, T> codec, T value) implements CustomPacketPayload {
    public static final Type<SimpleDataSetter<Object>> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID, "simple_data_setter"));

    public static final StreamCodec<ByteBuf, SimpleDataSetter<Object>> STREAM_CODEC = StreamCodec.of(
            (buffer, value) -> {
                ByteBufCodecs.VAR_INT.encode(buffer, value.id);
                value.codec.encode(buffer, value.value);
            },
            buffer -> {
                Integer v1 = ByteBufCodecs.VAR_INT.decode(buffer);
                DataSetterType<Object> dataSetterType = getDataSetter(v1);
                if (dataSetterType == null) {
                    throw new Error("DataSetterType of id: " + v1 + " is not exist");
                }
                StreamCodec<ByteBuf, Object> codec1 = dataSetterType.codec;
                Object value = codec1.decode(buffer);
                return new SimpleDataSetter<>(v1, codec1, value);
            }
    );

    private static int DataSetterId = 0;

    private static final ArrayList<DataSetterType<Object>> dates = new ArrayList<>();

    public static DataSetterType<Object> getDataSetter(int id) {
        if (id <= DataSetterId) {
            return dates.get(id);
        }
        return null;
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handler(IPayloadContext iPayloadContext) {
        Objects.requireNonNull(getDataSetter(id)).consumer.accept(value,iPayloadContext);
    }

    public record DataSetterType<T>(int id, StreamCodec<ByteBuf, T> codec, BiConsumer<T,IPayloadContext> consumer) {

        public static <T> DataSetterType<T> create(StreamCodec<ByteBuf, T> codec, BiConsumer<T,IPayloadContext>consumer) {
            DataSetterType<T> dataSetterType = new DataSetterType<T>(DataSetterId, codec, consumer);
            dates.add((DataSetterType<Object>) dataSetterType);
            DataSetterId++;
            return dataSetterType;
        }

        public void send(Player player, T vale) {
            if (player.level().isClientSide) {
                PacketDistributor.sendToServer(new SimpleDataSetter<>(id, codec, vale));
            }else {
                PacketDistributor.sendToPlayer((ServerPlayer) player, new SimpleDataSetter<>(id, codec, vale));
            }
        }
    }
}
