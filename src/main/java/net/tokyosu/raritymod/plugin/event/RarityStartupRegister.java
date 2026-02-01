package net.tokyosu.raritymod.plugin.event;

import dev.latvian.mods.kubejs.event.StartupEventJS;
import dev.latvian.mods.kubejs.typings.Generics;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import java.util.Hashtable;

public class RarityStartupRegister extends StartupEventJS
{
	@HideFromJS
	private static final Hashtable<String, String> RARITY_ITEM_LIST = new Hashtable<>(); // resourceName, rarityId
	@HideFromJS
	private static final Hashtable<String, String> RARITY_MOD_LIST = new Hashtable<>(); // modID, rarityId
	@HideFromJS
	private static final Hashtable<String, String> RARITY_TAG_LIST = new Hashtable<>(); // tagID, rarityId
	@HideFromJS
	private static final Hashtable<String, Rarity> RARITY_LIST = new Hashtable<>(); // rarityName, formattingColorName
	@HideFromJS
	private static String DefaultRarityName = null; // Be sure it's null, since processDefaultRarity will return if null else every items will have a default rarity...
	
	public RarityStartupRegister() {
		addRarity("raritymod.common", "white");
		addRarity("raritymod.uncommon", "green");
		addRarity("raritymod.rare", "blue");
		addRarity("raritymod.unique", "gold");
		addRarity("raritymod.legendary", "yellow");
		addRarity("raritymod.epic", "light_purple");
		addRarity("raritymod.mythic", "red");
		addRarity("raritymod.god", "red");
		//setDefaultRarity("raritymod.common");
		setRarity("minecraft:creeper_banner_pattern", "raritymod.uncommon");
		setRarity("minecraft:skull_banner_pattern", "raritymod.uncommon");
		setRarity("minecraft:mojang_banner_pattern", "raritymod.uncommon");
		setRarity("minecraft:creeper_banner_pattern", "raritymod.uncommon");
		setRarity("minecraft:golden_apple", "raritymod.uncommon");
		setRarity("minecraft:enchanted_golden_apple", "raritymod.rare");
		setRarity("minecraft:end_crystal", "raritymod.rare");
		setRarity("minecraft:beacon", "raritymod.rare");
		setRarity("minecraft:elytra", "raritymod.unique");
		setRarity("minecraft:white_banner", "raritymod.uncommon");
		setRarity("minecraft:skeleton_skull", "raritymod.uncommon");
		setRarity("minecraft:wither_skeleton_skull", "raritymod.uncommon");
		setRarity("minecraft:player_head", "raritymod.uncommon");
		setRarity("minecraft:zombie_head", "raritymod.uncommon");
		setRarity("minecraft:creeper_head", "raritymod.uncommon");
		setRarity("minecraft:piglin_head", "raritymod.uncommon");
		setRarity("minecraft:dragon_head", "raritymod.uncommon");
		setRarity("minecraft:dragon_egg", "raritymod.rare");
		setRarity("minecraft:music_disc_13", "raritymod.rare");
		setRarity("minecraft:music_disc_cat", "raritymod.rare");
		setRarity("minecraft:music_disc_blocks", "raritymod.rare");
		setRarity("minecraft:music_disc_chirp", "raritymod.rare");
		setRarity("minecraft:music_disc_far", "raritymod.rare");
		setRarity("minecraft:music_disc_mall", "raritymod.rare");
		setRarity("minecraft:music_disc_mellohi", "raritymod.rare");
		setRarity("minecraft:music_disc_stal", "raritymod.rare");
		setRarity("minecraft:music_disc_strad", "raritymod.rare");
		setRarity("minecraft:music_disc_ward", "raritymod.rare");
		setRarity("minecraft:music_disc_11", "raritymod.rare");
		setRarity("minecraft:music_disc_wait", "raritymod.rare");
		setRarity("minecraft:music_disc_otherside", "raritymod.rare");
		setRarity("minecraft:music_disc_5", "raritymod.rare");
		setRarity("minecraft:music_disc_pigstep", "raritymod.rare");
		setRarity("minecraft:music_disc_relic", "raritymod.rare");
		setRarity("minecraft:totem_of_undying", "raritymod.unique");
		setRarity("minecraft:dragon_breath", "raritymod.rare");
		setRarity("minecraft:heart_of_the_sea", "raritymod.rare");
		setRarity("minecraft:nether_star", "raritymod.unique");
		setRarity("minecraft:experience_bottle", "raritymod.uncommon");
		setRarity("minecraft:enchanted_book", "raritymod.uncommon");
	}
	public static RarityStartupRegister create() {
		return new RarityStartupRegister();
	}

	@HideFromJS
	public static boolean isItemSame(String resourceName) {
		return RARITY_ITEM_LIST.containsKey(resourceName);
	}

	@HideFromJS
	public static String getItemRarity(String resourceName) {
		return RARITY_ITEM_LIST.getOrDefault(resourceName, null);
	}

	@HideFromJS
	public static boolean isModSame(String modId) {
		return RARITY_MOD_LIST.containsKey(modId);
	}

	@HideFromJS
	public static String getModRarity(String modId) {
		return RARITY_MOD_LIST.getOrDefault(modId, null);
	}

	@HideFromJS
	public static String getDefaultRarityId() {
		return DefaultRarityName;
	}

	@HideFromJS
	public static String getTagRarity(ItemStack item) {
		if (item != null)
		{
			for (var tag : item.getTags().toList())
			{
				var tagKey = tag.location().toString();
				if (RARITY_TAG_LIST.containsKey(tagKey))
					return RARITY_TAG_LIST.get(tagKey);
			}
		}
		return null;
	}

	@HideFromJS
	public static Rarity getRarity(String rarityName) {
		return RARITY_LIST.getOrDefault(rarityName, null);
	}

	@Info(value = "Register a new rarity", params = {
            @Param(name = "name", value = "The rarity identifier (example: raritymod.god)"),
            @Param(name = "formattingName", value = "The rarity color (example: white)(can found a list here: https://minecraft.fandom.com/wiki/Formatting_codes#Color_codes)")
    })
    @Generics(value = {String.class, String.class})
	public void addRarity(String name, String formattingName) {
		RARITY_LIST.putIfAbsent(name, Rarity.create(name, ChatFormatting.getByName(formattingName)));
	}
	
	@Info(value = "Set a rarity to any items", params = {
            @Param(name = "resourceName", value = "The resource name (example: minecraft:apple)"),
            @Param(name = "rarityName", value = "The rarity name you given in addRarity (example: raritymod.god)")
    })
    @Generics(value = {String.class, String.class})
	public void setRarity(String resourceName, String rarityName) {
		RARITY_ITEM_LIST.put(resourceName, rarityName);
	}

	@Info(value = "Set a rarity by item tag", params = {
			@Param(name = "tagID", value = "The tag id (example: minecraft:logs)"),
			@Param(name = "rarityName", value = "The rarity name you given in addRarity (example: raritymod.god)")
	})
	public void setRarityByTag(String tagID, String rarityName) {
		RARITY_TAG_LIST.put(tagID, rarityName);
	}
	
	@Info(value = "Set a rarity by mod id", params = {
            @Param(name = "modId", value = "The mod id (example: minecraft/avaritia)"),
            @Param(name = "rarityName", value = "The rarity name you given in addRarity (example: raritymod.god)")
    })
    @Generics(value = {String.class, String.class})
	public void setRarityByMod(String modId, String rarityName) {
		RARITY_MOD_LIST.putIfAbsent(modId, rarityName);
	}
	
	@Info(value = "Set a default rarity for the game (defined rarity with setRarity/setRarityByMod will take priority)", params = {
            @Param(name = "rarityName", value = "The rarity name you given in addRarity (example: raritymod.god) or minecraft one.")
    })
    @Generics(value = {String.class})
	public void setDefaultRarity(String rarityName) {
		DefaultRarityName = rarityName;
	}
}
