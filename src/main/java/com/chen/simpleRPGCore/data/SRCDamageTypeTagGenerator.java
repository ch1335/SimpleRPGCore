package com.chen.simpleRPGCore.data;

import com.chen.simpleRPGCore.SimpleRPGCore;
import com.chen.simpleRPGCore.tags.SRCDamageTags;
import io.redspace.ironsspellbooks.datagen.DamageTypeTagGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class SRCDamageTypeTagGenerator extends TagsProvider<DamageType> {

    public SRCDamageTypeTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, Registries.DAMAGE_TYPE, lookupProvider, SimpleRPGCore.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(SRCDamageTags.MOD_MAGE_DAMAGE).addOptionalTags(
                DamageTypeTagGenerator.FIRE_MAGIC,
                DamageTypeTagGenerator.ICE_MAGIC,
                DamageTypeTagGenerator.LIGHTNING_MAGIC,
                DamageTypeTagGenerator.HOLY_MAGIC,
                DamageTypeTagGenerator.ENDER_MAGIC,
                DamageTypeTagGenerator.BLOOD_MAGIC,
                DamageTypeTagGenerator.EVOCATION_MAGIC,
                DamageTypeTagGenerator.ELDRITCH_MAGIC,
                DamageTypeTagGenerator.NATURE_MAGIC
        );

        this.tag(SRCDamageTags.TRUE_DAMAGE).add(
                SRCDamageTypes.TRUE_DAMAGE_TAG_HOLDER
        );

        this.tag(DamageTypeTags.BYPASSES_COOLDOWN).add(
                SRCDamageTypes.BYPASSES_COOLDOWN_TAG_HOLDER
        );

        this.tag(SRCDamageTags.CAN_CRITICAL).add(
                SRCDamageTypes.CAN_CRITICAL_TAG_HOLDER
        );

        this.tag(SRCDamageTags.CAN_LIFE_STEAL).add(
                SRCDamageTypes.CAN_LIFE_STEAL_TAG_HOLDER
        ).addTags(DamageTypeTags.IS_PLAYER_ATTACK);


        this.tag(SRCDamageTags.BYPASSES_SHIELD).addTags(
                SRCDamageTags.TRUE_DAMAGE
        )
        ;
        this.tag(DamageTypeTags.BYPASSES_INVULNERABILITY).addTags(
                SRCDamageTags.TRUE_DAMAGE
        );
        this.tag(DamageTypeTags.BYPASSES_RESISTANCE).addTags(
                SRCDamageTags.TRUE_DAMAGE
        );
        this.tag(DamageTypeTags.BYPASSES_EFFECTS).addTags(
                SRCDamageTags.TRUE_DAMAGE
        );
        this.tag(DamageTypeTags.BYPASSES_ENCHANTMENTS).addTags(
                SRCDamageTags.TRUE_DAMAGE
        );
        this.tag(DamageTypeTags.BYPASSES_ARMOR).addTags(
                SRCDamageTags.TRUE_DAMAGE
        );
        this.tag(DamageTypeTags.BYPASSES_COOLDOWN).addTags(
                SRCDamageTags.TRUE_DAMAGE
        );
        this.tag(DamageTypeTags.BYPASSES_WOLF_ARMOR).addTags(
                SRCDamageTags.TRUE_DAMAGE
        );
    }
}
