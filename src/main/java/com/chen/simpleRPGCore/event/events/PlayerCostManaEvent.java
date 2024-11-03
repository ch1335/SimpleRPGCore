package com.chen.simpleRPGCore.event.events;

import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.Nullable;

public class PlayerCostManaEvent extends PlayerEvent implements ICancellableEvent {
    @Nullable
    private final Object costReason;
    private float amount;

    private SpellOnCastEvent handler;

    public PlayerCostManaEvent(Player player, float amount, @Nullable Object reason) {
        super(player);
        costReason = reason;
        this.amount = amount;
    }


    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Nullable
    public Object getCostReason() {
        return costReason;
    }

}
