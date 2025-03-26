package net.tokyosu.raritymod.plugin.event;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface RarityEventHandler {
    static final Logger LOGGER = LogUtils.getLogger();
	EventGroup RARITY_EVENTS = EventGroup.of("RarityJSEvents");
	EventHandler STARTUP_REGISTER = RARITY_EVENTS.startup("startupRegister", () -> RarityStartupRegister.class);
	EventHandler CLIENT_REGISTER = RARITY_EVENTS.client("clientRegister", () -> RarityClientRegister.class);
	
	static void register()
	{
		RARITY_EVENTS.register();
	}
}
