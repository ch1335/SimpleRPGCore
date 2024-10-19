package com.chen.simpleRPGCore.event.events;

import com.chen.simpleRPGCore.SimpleRPGConfig;
import net.neoforged.bus.api.Event;

public class SRCSetConfigEvent extends Event {
    private final SimpleRPGConfig.ClientConfig clientConfig;

    private final SimpleRPGConfig.CommonConfig commonConfig;

    public SRCSetConfigEvent(SimpleRPGConfig.ClientConfig clientConfig, SimpleRPGConfig.CommonConfig commonConfig) {
        this.clientConfig = clientConfig;
        this.commonConfig = commonConfig;
    }


    public SimpleRPGConfig.ClientConfig getClientConfig() {
        return clientConfig;
    }

    public SimpleRPGConfig.CommonConfig getCommonConfig() {
        return commonConfig;
    }
}
