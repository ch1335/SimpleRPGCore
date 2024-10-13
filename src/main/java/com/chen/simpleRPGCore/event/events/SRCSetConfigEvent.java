package com.chen.simpleRPGCore.event.events;

import com.chen.simpleRPGCore.config.ConfigDataHolder;
import net.neoforged.bus.api.Event;

public class SRCSetConfigEvent extends Event {
    private final ConfigDataHolder configDataHolder;

    public SRCSetConfigEvent(ConfigDataHolder configDataHolder) {
        this.configDataHolder = configDataHolder;
    }

    public ConfigDataHolder getConfigDataHolder() {
        return configDataHolder;
    }
}
