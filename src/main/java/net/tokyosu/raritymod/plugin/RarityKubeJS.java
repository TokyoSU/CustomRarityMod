package net.tokyosu.raritymod.plugin;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import net.tokyosu.raritymod.plugin.event.RarityEventHandler;

public class RarityKubeJS extends KubeJSPlugin {
	@Override
    public void registerEvents() {
		RarityEventHandler.register();
	}
}
