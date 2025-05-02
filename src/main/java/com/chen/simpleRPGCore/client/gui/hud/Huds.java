package com.chen.simpleRPGCore.client.gui.hud;

import com.chen.simpleRPGCore.SimpleRPGCore;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class Huds {
    public static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID, "textures/hud/icon.png");
    public static final ResourceLocation CRITICAL_ICON = ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID, "textures/hud/critical.png");
    public static final ResourceLocation ARMOR_FULL_SPRITE = ResourceLocation.withDefaultNamespace("hud/armor_full");


    public static ManaBar MANA_BAR;
    public static HealthBar HEALTH_BAR;
    public static ArmorState ARMOR_STATE;
    public static FoodState FOOD_STATE;

    public static void register(RegisterGuiLayersEvent event) {
        MANA_BAR = new ManaBar();
        HEALTH_BAR = new HealthBar();
        ARMOR_STATE = new ArmorState();
        FOOD_STATE = new FoodState();
        event.registerBelow(VanillaGuiLayers.FOOD_LEVEL, getLocation("mana_bar"), MANA_BAR);
        event.registerAbove(getLocation("mana_bar"), getLocation("food_state"), FOOD_STATE);
        event.registerBelow(VanillaGuiLayers.PLAYER_HEALTH, getLocation("health_bar"), HEALTH_BAR);
        event.registerAbove(getLocation("health_bar"), getLocation("armor_state"), ARMOR_STATE);
    }

    private static ResourceLocation getLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID, name);
    }
}
