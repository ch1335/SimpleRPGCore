package com.chen.simpleRPGCore.event.events;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerCostManaEvent extends PlayerEvent implements ICancellableEvent {

    private final String costReason;
    private float amount;

    public PlayerCostManaEvent(Player player, float amount, String reason) {
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

    public String getCostReason() {
        return costReason;
    }
}
