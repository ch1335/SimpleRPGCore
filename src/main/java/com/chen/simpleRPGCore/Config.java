package com.chen.simpleRPGCore;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static class ClientConfig {
        public static final ModConfigSpec CLIENT;
        public static Client CONFIG;

        public static class Client {

            public ModConfigSpec.BooleanValue enableManaBar;
            public ModConfigSpec.IntValue manaBarX;
            public ModConfigSpec.IntValue manaBarY;

            public Client(ModConfigSpec.Builder builder) {
                builder.comment("Client settings").push("client");
                {
                    builder.comment("Hud Config").push("hud");
                    enableManaBar = builder.define("enableManaBar", true);
                    manaBarX = builder.defineInRange("manaBarX", 100, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    manaBarY = builder.defineInRange("manaBarY", -13, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    builder.pop();
                }
                builder.pop();
            }
        }

        static {
            Pair<Client, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(Client::new);
            CLIENT = pair.getRight();
            CONFIG = pair.getLeft();
        }

    }

    public static class CommonConfig {
        private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
        static final ModConfigSpec SPEC = BUILDER.build();


    }
}
