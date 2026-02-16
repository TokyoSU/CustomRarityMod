package net.tokyosu.raritymod.plugin.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import net.tokyosu.raritymod.plugin.kubejs.event.RarityStartupRegister;
import net.tokyosu.raritymod.utils.RarityRegistry;

public class RarityKubeJS extends KubeJSPlugin {
	public static EventGroup RARITY_EVENTS = EventGroup.of("RarityJSEvents");
	public static EventHandler STARTUP_REGISTER = RARITY_EVENTS.startup("register", () -> RarityStartupRegister.class);

	/// Starting point of kubejs plugin.
	@Override
    public void registerEvents() {
		RARITY_EVENTS.register();
		this.registerRarity();
	}

	/// Add new rarity from this mod, compatible with obscure tooltips.
	private void registerRarity() {
		RarityRegistry.register("minecraft:common", "white");
		RarityRegistry.register("minecraft:uncommon", "yellow");
		RarityRegistry.register("minecraft:rare", "aqua");
		RarityRegistry.register("minecraft:epic", "light_purple");
		RarityRegistry.register("raritymod.common", "white");
		RarityRegistry.register("raritymod.uncommon", "green");
		RarityRegistry.register("raritymod.rare", "blue");
		RarityRegistry.register("raritymod.unique", "gold");
		RarityRegistry.register("raritymod.legendary", "yellow");
		RarityRegistry.register("raritymod.epic", "light_purple");
		RarityRegistry.register("raritymod.mythic", "red");
		RarityRegistry.register("raritymod.god", "red");
	}
}
