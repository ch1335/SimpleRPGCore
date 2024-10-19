package com.chen.simpleRPGCore.client;

import com.chen.simpleRPGCore.SimpleRPGCore;
import com.chen.simpleRPGCore.client.gui.hud.Huds;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

public class ClientEventHandler {
    @EventBusSubscriber(modid = SimpleRPGCore.MODID, bus = EventBusSubscriber.Bus.MOD,value = {Dist.CLIENT})
    public static class Mod{
        @SubscribeEvent
        public static void RegisterGuiLayersEvent(RegisterGuiLayersEvent event){
            Huds.register(event);
        }
    }
}
