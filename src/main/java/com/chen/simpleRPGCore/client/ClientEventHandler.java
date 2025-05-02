package com.chen.simpleRPGCore.client;

import com.chen.simpleRPGCore.SimpleRPGCore;
import com.chen.simpleRPGCore.client.gui.hud.Huds;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class ClientEventHandler {
    @EventBusSubscriber(modid = SimpleRPGCore.MODID, bus = EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
    public static class Mod {
        @SubscribeEvent
        public static void RegisterGuiLayersEvent(RegisterGuiLayersEvent event) {
            Huds.register(event);
        }
    }

    @EventBusSubscriber(modid = SimpleRPGCore.MODID, bus = EventBusSubscriber.Bus.GAME, value = {Dist.CLIENT})
    public static class Game {
        @SubscribeEvent
        public static void RenderGuiLayerEvent(RenderGuiLayerEvent.Pre event) {
            if (event.getName().equals(VanillaGuiLayers.PLAYER_HEALTH)) {
                event.setCanceled(true);
            } else if (event.getName().equals(VanillaGuiLayers.ARMOR_LEVEL)) {
                event.setCanceled(true);
            } else if (event.getName().equals(VanillaGuiLayers.FOOD_LEVEL)) {
                event.setCanceled(true);
            }

            if (SimpleRPGCore.ironsSSpellBooksLoaded) {
                if (event.getName().equals(io.redspace.ironsspellbooks.IronsSpellbooks.id("mana_overlay"))) {
                    event.setCanceled(true);
                }
            }
        }

    }
}
