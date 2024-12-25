package com.chen.simpleRPGCore;

import com.chen.simpleRPGCore.API.objects.SRCAttributes;
import com.chen.simpleRPGCore.attachmentType.SRCAttachmentTypes;
import com.chen.simpleRPGCore.common.DamageSourceExtraData;
import com.chen.simpleRPGCore.common.EventHandler;
import com.chen.simpleRPGCore.common.ShieldSystem.Shield;
import com.chen.simpleRPGCore.data.SRCDamageTypeTagGenerator;
import com.chen.simpleRPGCore.data.SRCDamageTypes;
import com.chen.simpleRPGCore.event.events.SRCSetConfigEvent;
import com.chen.simpleRPGCore.fix.AttributeFix;
import com.chen.simpleRPGCore.item.SRCItems;
import com.mojang.logging.LogUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.io.File;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod(SimpleRPGCore.MODID)
public class SimpleRPGCore {
    public static boolean isRunData = false;
    public static final String MODID = "simple_rpg_core";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final File configDir;

    public static boolean apothicAttributesLoaded = false;

    public static boolean ironsSSpellBooksLoaded = false;

    static {
        configDir = new File(FMLPaths.CONFIGDIR.get().toFile(), MODID);
    }

    public SimpleRPGCore(IEventBus modEventBus, ModContainer modContainer) {
        CREATIVE_MODE_TABS.register(modEventBus);
        SRCAttributes.ATTRIBUTE_DEFERRED_REGISTER.register(modEventBus);
        SRCAttachmentTypes.ATTACHMENT_TYPES.register(modEventBus);
        SRCItems.ITEMS.register(modEventBus);
        modEventBus.register(this);

        if (ironsSSpellBooksLoaded) {
            NeoForge.EVENT_BUS.register(EventHandler.Game.IronsSpellBooksEventHandler.class);
        }
        SimpleRPGConfig.load();
    }

    static {
        if (ModList.get().isLoaded("apothic_attributes")) {
            apothicAttributesLoaded = true;
        }
        if (ModList.get().isLoaded("irons_spellbooks")) {
            ironsSSpellBooksLoaded = true;
        }
    }

    @SubscribeEvent
    public void gatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        PackOutput packOutput = dataGenerator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        DatapackBuiltinEntriesProvider provider = new DatapackBuiltinEntriesProvider(packOutput,
                lookupProvider,
                new RegistrySetBuilder().
                        add(Registries.DAMAGE_TYPE, SRCDamageTypes::bootstrap),
                Set.of(SimpleRPGCore.MODID));

        CompletableFuture<HolderLookup.Provider> providerCompletableFuture = provider.getRegistryProvider();
        dataGenerator.addProvider(event.includeServer(), provider);
        dataGenerator.addProvider(event.includeServer(), new SRCDamageTypeTagGenerator(packOutput, providerCompletableFuture, existingFileHelper));

    }

    public static File getConfigFile(String path) {
        return new File(configDir, path + ".cfg");
    }

    @SubscribeEvent
    private void commonSetup(final FMLCommonSetupEvent event) {
        DamageSourceExtraData.ExtraAttributes.addAttributes();
        NeoForge.EVENT_BUS.post(new SRCSetConfigEvent(SimpleRPGConfig.clientConfig, SimpleRPGConfig.commonConfig));
        if (SimpleRPGConfig.commonConfig.enableSimpleAttributeFix) {
            AttributeFix.fix();
        }
        Shield.setPriority();
    }
}
