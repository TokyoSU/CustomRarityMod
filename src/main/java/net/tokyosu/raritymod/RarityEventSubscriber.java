package net.tokyosu.raritymod;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.tokyosu.raritymod.plugin.event.RarityEventHandler;
import net.tokyosu.raritymod.plugin.event.RarityStartupRegister;

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
}
