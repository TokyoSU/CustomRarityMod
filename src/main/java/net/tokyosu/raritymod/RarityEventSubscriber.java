package net.tokyosu.raritymod;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.tokyosu.raritymod.plugin.event.RarityEventHandler;
import net.tokyosu.raritymod.plugin.event.RarityStartupRegister;
import net.tokyosu.raritymod.plugin.event.RarityClientRegister;

@Mod.EventBusSubscriber(modid = RarityMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RarityEventSubscriber
{
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
