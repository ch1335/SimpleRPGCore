package com.chen.simpleRPGCore;

import com.chen.simpleRPGCore.attachmentType.SRCAttachmentTypes;
import com.chen.simpleRPGCore.attribute.SRCAttributes;
import com.chen.simpleRPGCore.common.DamageSourceExtraData;
import com.chen.simpleRPGCore.event.events.SRCSetConfigEvent;
import com.chen.simpleRPGCore.fix.AttributeFix;
import com.chen.simpleRPGCore.item.SRCItems;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.io.File;

@Mod(SimpleRPGCore.MODID)
public class SimpleRPGCore {
    public static final String MODID = "simple_rpg_core";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final File configDir;

    public static boolean apothicAttributesLoaded = false;
    static {
        configDir = new File(FMLPaths.CONFIGDIR.get().toFile(), MODID);
    }

    public SimpleRPGCore(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        CREATIVE_MODE_TABS.register(modEventBus);
        SRCAttributes.ATTRIBUTE_DEFERRED_REGISTER.register(modEventBus);
        SRCAttachmentTypes.ATTACHMENT_TYPES.register(modEventBus);
        SRCItems.ITEMS.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

        SimpleRPGConfig.load();
    }

    static {
        if (ModList.get().isLoaded("apothic_attributes")) {
            apothicAttributesLoaded = true;
        }
    }

    public static File getConfigFile(String path) {
        return new File(configDir, path + ".cfg");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        DamageSourceExtraData.ExtraAttributes.addAttributes();
        NeoForge.EVENT_BUS.post(new SRCSetConfigEvent(SimpleRPGConfig.clientConfig, SimpleRPGConfig.commonConfig));
        if (SimpleRPGConfig.commonConfig.enableSimpleAttributeFix) {
            AttributeFix.fix();
        }
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
