package net.tokyosu.raritymod;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.tokyosu.raritymod.plugin.kubejs.event.RarityClientRegister;
import net.tokyosu.raritymod.plugin.kubejs.event.RarityEventHandler;
import net.tokyosu.raritymod.plugin.kubejs.event.RarityStartupRegister;

@Mod.EventBusSubscriber(modid = RarityMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RarityEventSubscriber {
	// Make sure to register rarity event.
	
    @SubscribeEvent
    static void onCommonSetup(FMLCommonSetupEvent event) {
		if (RarityEventHandler.STARTUP_REGISTER.hasListeners()) {
			RarityEventHandler.STARTUP_REGISTER.post(new RarityStartupRegister());
		}
    }
	
	@SubscribeEvent
	static void onLoadComplete(FMLLoadCompleteEvent event) {
		if (RarityEventHandler.CLIENT_REGISTER.hasListeners()) {
			RarityEventHandler.CLIENT_REGISTER.post(new RarityClientRegister());
		}
	}
}
