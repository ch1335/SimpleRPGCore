package com.chen.simpleRPGCore.client.gui.hud;

import com.chen.simpleRPGCore.SimpleRPGConfig;
import com.chen.simpleRPGCore.SimpleRPGCore;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

public class Huds {
    public static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID,"textures/hud/icon.png");
    public static ManaBar manaBar;
    public static void register(RegisterGuiLayersEvent event){
        if (SimpleRPGConfig.clientConfig.enableManaBar) {
            manaBar = new ManaBar();
            event.registerAboveAll(getLocation("mana_bar"),manaBar);
        }
    }

    private static ResourceLocation getLocation(String name){
        return ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID,name);
    }
}
