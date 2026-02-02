package net.tokyosu.raritymod.plugin;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import net.tokyosu.raritymod.plugin.event.RarityStartupRegister;
import net.tokyosu.raritymod.utils.RarityRegistry;

public class RarityKubeJS extends KubeJSPlugin {
	public static EventGroup RARITY_EVENTS = EventGroup.of("RarityJSEvents");
	public static EventHandler STARTUP_REGISTER = RARITY_EVENTS.startup("register", () -> RarityStartupRegister.class);

	/// Starting point of kubejs plugin.
	@Override
    public void registerEvents() {
		RARITY_EVENTS.register();
		registerRarity();
		registerMinecraft();
		registerEggsAndSpawner();
	}

	/// Add new rarity from this mod, compatible with obscure tooltips.
	private void registerRarity() {
		RarityRegistry.register("raritymod.common", "white");
		RarityRegistry.register("raritymod.uncommon", "green");
		RarityRegistry.register("raritymod.rare", "blue");
		RarityRegistry.register("raritymod.unique", "gold");
		RarityRegistry.register("raritymod.legendary", "yellow");
		RarityRegistry.register("raritymod.epic", "light_purple");
		RarityRegistry.register("raritymod.mythic", "red");
		RarityRegistry.register("raritymod.god", "red");
	}

	/// Add base rarity to minecraft.
	private void registerMinecraft() {
		RarityRegistry.setItemRarity("minecraft:creeper_banner_pattern", "raritymod.uncommon");
		RarityRegistry.setItemRarity("minecraft:skull_banner_pattern", "raritymod.uncommon");
		RarityRegistry.setItemRarity("minecraft:mojang_banner_pattern", "raritymod.uncommon");
		RarityRegistry.setItemRarity("minecraft:creeper_banner_pattern", "raritymod.uncommon");
		RarityRegistry.setItemRarity("minecraft:golden_apple", "raritymod.uncommon");
		RarityRegistry.setItemRarity("minecraft:enchanted_golden_apple", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:end_crystal", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:beacon", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:elytra", "raritymod.unique");
		RarityRegistry.setItemRarity("minecraft:white_banner", "raritymod.uncommon");
		RarityRegistry.setItemRarity("minecraft:skeleton_skull", "raritymod.uncommon");
		RarityRegistry.setItemRarity("minecraft:wither_skeleton_skull", "raritymod.uncommon");
		RarityRegistry.setItemRarity("minecraft:player_head", "raritymod.uncommon");
		RarityRegistry.setItemRarity("minecraft:zombie_head", "raritymod.uncommon");
		RarityRegistry.setItemRarity("minecraft:creeper_head", "raritymod.uncommon");
		RarityRegistry.setItemRarity("minecraft:piglin_head", "raritymod.uncommon");
		RarityRegistry.setItemRarity("minecraft:dragon_head", "raritymod.uncommon");
		RarityRegistry.setItemRarity("minecraft:dragon_egg", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:music_disc_13", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:music_disc_cat", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:music_disc_blocks", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:music_disc_chirp", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:music_disc_far", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:music_disc_mall", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:music_disc_mellohi", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:music_disc_stal", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:music_disc_strad", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:music_disc_ward", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:music_disc_11", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:music_disc_wait", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:music_disc_otherside", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:music_disc_5", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:music_disc_pigstep", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:music_disc_relic", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:totem_of_undying", "raritymod.unique");
		RarityRegistry.setItemRarity("minecraft:dragon_breath", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:heart_of_the_sea", "raritymod.rare");
		RarityRegistry.setItemRarity("minecraft:nether_star", "raritymod.unique");
		RarityRegistry.setItemRarity("minecraft:experience_bottle", "raritymod.uncommon");
		RarityRegistry.setItemRarity("minecraft:enchanted_book", "raritymod.uncommon");
		RarityRegistry.setItemRarity("minecraft:conduit", "raritymod.rare");
	}

	/// Add rarity to spawn egg and the spawner, they normally can't be dropped in-game which make them legendary and mythic.
	private void registerEggsAndSpawner() {
		RarityRegistry.setItemRarity("minecraft:spawner", "raritymod.mythic");
		RarityRegistry.setItemRarity("minecraft:agent_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:allay_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:armadillo_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:axolotl_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:bat_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:bee_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:blaze_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:bogged_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:breeze_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:camel_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:camel_husk_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:cat_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:cave_spider_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:chicken_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:cod_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:copper_golem_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:cow_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:creaking_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:creeper_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:donkey_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:drowned_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:elder_guardian_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:ender_dragon_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:enderman_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:endermite_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:evoker_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:fox_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:frog_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:ghast_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:glow_squid_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:goat_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:guardian_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:happy_ghast_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:hoglin_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:horse_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:husk_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:iron_golem_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:llama_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:magma_cube_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:mooshroom_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:mule_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:nautilus_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:ocelot_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:panda_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:parched_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:parrot_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:phantom_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:pig_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:piglin_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:piglin_brute_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:pillager_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:polar_bear_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:pufferfish_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:rabbit_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:ravager_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:salmon_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:sheep_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:shulker_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:silverfish_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:skeleton_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:skeleton_horse_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:slime_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:sniffer_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:snow_golem_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:spider_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:squid_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:stray_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:strider_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:tadpole_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:trader_llama_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:tropical_fish_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:turtle_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:vex_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:villager_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:vindicator_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:wandering_trader_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:warden_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:wither_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:wither_skeleton_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:wolf_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:zoglin_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:zombie_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:zombie_horse_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:zombie_nautilus_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:zombie_villager_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:zombified_piglin_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:witch_spawn_egg", "raritymod.legendary");
		RarityRegistry.setItemRarity("minecraft:dolphin_spawn_egg", "raritymod.legendary");
	}
}
