package com.chen.simpleRPGCore.event.events;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.bus.api.Event;

import java.util.Set;

public class AddDamageSourceExtraAttributesEvent extends Event {
    public final Set<Holder<Attribute>> attributes;

    public AddDamageSourceExtraAttributesEvent(Set<Holder<Attribute>> attributes) {
        this.attributes = attributes;
    }

    public void add(Holder<Attribute> attributeHolder) {
        attributes.add(attributeHolder);
    }
}
