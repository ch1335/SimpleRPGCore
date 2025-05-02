package com.chen.simpleRPGCore.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class SimpleRPGCoreClient {
    public static Player getLocalPlayer(){
        return Minecraft.getInstance().player;
    }
}
