package net.tokyosu.raritymod.plugin.kubejs.event;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface RarityEventHandler {
	EventGroup RARITY_EVENTS = EventGroup.of("RarityJSEvents");
	EventHandler STARTUP_REGISTER = RARITY_EVENTS.startup("startupRegister", () -> RarityStartupRegister.class);
	EventHandler CLIENT_REGISTER = RARITY_EVENTS.client("clientRegister", () -> RarityClientRegister.class);
	
	static void register()
	{
		RARITY_EVENTS.register();
	}
}
