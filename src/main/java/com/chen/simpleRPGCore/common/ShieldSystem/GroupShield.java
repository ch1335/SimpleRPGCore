package com.chen.simpleRPGCore.common.ShieldSystem;

import com.chen.simpleRPGCore.API.IShield;
import com.chen.simpleRPGCore.API.IUnitShield;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.ArrayList;
import java.util.Iterator;

public class GroupShield<T extends IUnitShield> implements IShield {
    private final ArrayList<T> units = new ArrayList<>();
    private final Shield.UnitShieldFactory<T> unitShieldFactory;

    @Override
    public void tick() {
        Iterator<T> iterator = units.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            next.tick();
            if (next.getAmount() <= 0) {
                iterator.remove();
            }
        }
    }

    public GroupShield(Shield.UnitShieldFactory<T> factory) {
        this.unitShieldFactory = factory;
    }

    public T addUnit(T unit) {
        units.add(unit);
        return unit;
    }

    @Override
    public float getTotalAmount() {
        float amount = 0;
        for (T unit : units) {
            amount += unit.getTotalAmount();
        }
        return amount;
    }

    @Override
    public void reduceShieldAmount(float absorbAmount) {
        for (T unit : units) {
            float reducedThis = Math.min(absorbAmount, unit.getAmount());
            absorbAmount -= reducedThis;
            unit.reduceShieldAmount(reducedThis);
            if (absorbAmount <= 0) {
                break;
            }
        }
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        ListTag listTag = new ListTag();
        units.forEach(unit -> {
            listTag.add(unit.serializeNBT(provider));
        });
        CompoundTag tag = new CompoundTag();
        tag.put("unitsData", listTag);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag nbt) {
        ListTag listTag = nbt.getList("unitsData", ListTag.TAG_COMPOUND);
        for (int i = 0; i < listTag.size(); i++) {
            T unit = unitShieldFactory.create();
            unit.deserializeNBT(provider, listTag.getCompound(i));
            units.add(unit);
        }
    }


}
