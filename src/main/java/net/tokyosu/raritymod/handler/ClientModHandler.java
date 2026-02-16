package net.tokyosu.raritymod.handler;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.tokyosu.raritymod.RarityKeybindings;
import net.tokyosu.raritymod.RarityMod;
import net.tokyosu.raritymod.editor.RarityEditor;
import net.tokyosu.raritymod.editor.screen.RarityEditorScreen;

@Mod.EventBusSubscriber(modid = RarityMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModHandler {
    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(RarityKeybindings.RARITY_EDITOR);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(RarityEditor.RARITY_EDITOR_MENU.get(), RarityEditorScreen::new);
        });
    }
}
