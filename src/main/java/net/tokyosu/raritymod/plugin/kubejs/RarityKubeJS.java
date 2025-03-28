package net.tokyosu.raritymod.plugin.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import net.tokyosu.raritymod.plugin.kubejs.event.RarityEventHandler;

public class RarityKubeJS extends KubeJSPlugin {
	@Override
    public void registerEvents() {
		RarityEventHandler.register();
	}
}
