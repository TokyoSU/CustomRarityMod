package net.tokyosu.raritymod.plugin.event;

import com.mojang.logging.LogUtils;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import org.slf4j.Logger;

public interface RarityEventHandler {
    static final Logger LOGGER = LogUtils.getLogger();
	EventGroup RARITY_EVENTS = EventGroup.of("RarityJSEvents");
	EventHandler STARTUP_REGISTER = RARITY_EVENTS.startup("register", () -> RarityStartupRegister.class);
	
	static void register()
	{
		RARITY_EVENTS.register();
	}
}
