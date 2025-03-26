package net.tokyosu.raritymod.plugin;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import net.tokyosu.raritymod.plugin.event.RarityEventHandler;

public class RarityKubeJS extends KubeJSPlugin {
    public static final Logger LOGGER = LogUtils.getLogger();
    
	@Override
	public void initStartup() {
		
	}
	
	@Override
    public void registerEvents() {
		RarityEventHandler.register();
	}
}
