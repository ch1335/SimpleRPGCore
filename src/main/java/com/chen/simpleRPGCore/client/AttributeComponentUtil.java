package com.chen.simpleRPGCore.client;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;

public class AttributeComponentUtil {
    public static Component getAttributeComponent(Player player, Holder<Attribute> holder){
        return Component.literal(String.valueOf(player.getAttributeValue(holder)));
    }
}
