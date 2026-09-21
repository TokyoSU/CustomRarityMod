package net.tokyosu.raritymod.handler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tokyosu.raritymod.RarityKeybindings;
import net.tokyosu.raritymod.RarityMod;
import net.tokyosu.raritymod.editor.network.NetworkHandler;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("SpellCheckingInspection")
@Mod.EventBusSubscriber(modid = RarityMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeHandler {
    @SubscribeEvent
    public static void onPlayerTick(@NotNull TickEvent.PlayerTickEvent event) {
        if (RarityKeybindings.RARITY_EDITOR.consumeClick() && event.player != null && event.player.isCreative()) {
            NetworkHandler.sendOpenEditor();
        }
    }
}
