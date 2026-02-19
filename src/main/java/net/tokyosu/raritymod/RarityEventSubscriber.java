package net.tokyosu.raritymod;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.tokyosu.raritymod.plugin.kubejs.RarityKubeJS;
import net.tokyosu.raritymod.plugin.kubejs.event.RarityStartupRegister;

@Mod.EventBusSubscriber(modid = RarityMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RarityEventSubscriber {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        if (RarityKubeJS.STARTUP_REGISTER.hasListeners()) {
            RarityKubeJS.STARTUP_REGISTER.post(new RarityStartupRegister());
        }
    }
}
