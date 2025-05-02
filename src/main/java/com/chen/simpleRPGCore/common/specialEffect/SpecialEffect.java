package com.chen.simpleRPGCore.common.specialEffect;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.UUID;

public abstract class SpecialEffect {
    private final Entity source;

    public int duration;

    private final SpecialEffectType<?> type;

    public SpecialEffect(Entity source, SpecialEffectType<?> type) {
        this.source = source;
        this.type = type;
    }

    public void tick(LivingEntity livingEntity) {
        duration--;
    }

    public SpecialEffectType<?> getType() {
        return type;
    }

    public Entity getSource() {
        return source;
    }

    public Entity getEntityByUUid(MinecraftServer server, UUID uuid) {
        Entity entity = null;
        for (ServerLevel level : server.getAllLevels()) {
            entity = level.getEntity(uuid);
            if (entity != null) {
                break;
            }
        }
        return entity;
    }
}
