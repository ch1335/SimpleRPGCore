package com.chen.simpleRPGCore.event.events;

import com.chen.simpleRPGCore.API.IShield;
import com.chen.simpleRPGCore.common.ShieldSystem.Shield;
import net.minecraft.core.Holder;
import net.neoforged.bus.api.Event;

import java.util.List;

public class RegisterShieldPriorityEvent extends Event {

    private final List<Shield.ShieldType<? extends IShield>> shieldsPriority;

    public RegisterShieldPriorityEvent(List<Shield.ShieldType<? extends IShield>> shieldsPriority) {
        this.shieldsPriority = shieldsPriority;
    }

    public List<Shield.ShieldType<? extends IShield>> getShieldsPriority() {
        return shieldsPriority;
    }

    public void addShieldType(Shield.ShieldType<? extends IShield> shieldType) {
        shieldsPriority.add(shieldType);
    }

    public void addShieldType(int priority, Shield.ShieldType<? extends IShield> shieldType) {
        shieldsPriority.add(priority, shieldType);
    }
}
