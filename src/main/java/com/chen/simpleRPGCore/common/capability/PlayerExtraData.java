package com.chen.simpleRPGCore.common.capability;

import com.chen.simpleRPGCore.attachmentType.SRCAttachmentTypes;
import com.chen.simpleRPGCore.attribute.SRCAttributes;
import com.chen.simpleRPGCore.event.SRCEventFactory;
import com.chen.simpleRPGCore.network.PlayerExtraDataSycPack;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public class PlayerExtraData implements IPlayerExtraData {

    private final Player player;

    public PlayerExtraData(Player player) {
        this.player = player;
    }


    public float getMana() {
        return getDataHolder().mana;
    }

    public void setMana(float mana) {
        getDataHolder().mana = mana;
    }

    public boolean costMana(float amount,String reason){
       return costMana(amount,false,reason);
    }

    public boolean costMana(float amount, boolean absolute, String reason) {
        float finalCost = SRCEventFactory.onPlayerCostMana(player, amount * (absolute ? 1 : (float) player.getAttributeValue(SRCAttributes.MANA_COST)), reason);
        if (finalCost <= getMana()) {
            setMana(getMana() - finalCost);
            sycMana();
            return true;
        } else {
            return false;
        }
    }

    public void regainMana(float amount, boolean absolute) {
        float manaRegain = absolute ? 1 : (float) player.getAttributeValue(SRCAttributes.MANA_REGAIN);
        setMana((float) Math.min(player.getAttributeValue(SRCAttributes.MAX_MANA), getMana() + amount * manaRegain));
        sycMana();
    }

    public void regainMana(float amount) {
        regainMana(amount, false);
    }

    public DataHolder getDataHolder() {
        return player.getData(SRCAttachmentTypes.PLAYER_DATA);
    }

    @Override
    public void tick() {
        if (!player.isDeadOrDying()) {
            if (player instanceof ServerPlayer && player.level().getGameTime() % 20 == 0) {
                regainMana((float) (player.getAttributeValue(SRCAttributes.MAX_MANA) * 0.05));
            }
        }
    }

    public void sycMana() {
        if (player instanceof ServerPlayer serverPlayer) {
            PlayerExtraDataSycPack.SycMana(serverPlayer);
        }
    }

    public static class DataHolder implements INBTSerializable<CompoundTag> {

        public float mana;

        public DataHolder(float mana) {
            this.mana = mana;
        }

        @Override
        public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = new CompoundTag();
            tag.putFloat("currentMana", mana);
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag nbt) {
            mana = nbt.getFloat("currentMana");
        }
    }
}
