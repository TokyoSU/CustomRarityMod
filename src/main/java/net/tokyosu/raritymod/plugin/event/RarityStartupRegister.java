package net.tokyosu.raritymod.plugin.event;

import java.util.Hashtable;

import dev.latvian.mods.kubejs.event.StartupEventJS;
import dev.latvian.mods.kubejs.typings.Generics;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;

public class RarityStartupRegister extends StartupEventJS
{
	@HideFromJS
	private static final Hashtable<String, String> RARITY_ITEM_LIST = new Hashtable<>(); // resourceName, rarityId
	@HideFromJS
	private static final Hashtable<String, String> RARITY_MOD_LIST = new Hashtable<>(); // modID, rarityId
	@HideFromJS
	private static final Hashtable<String, Rarity> RARITY_LIST = new Hashtable<>(); // rarityName, formattingColorName
	@HideFromJS
	private static String DefaultRarityName = null; // Be sure it's null, since processDefaultRarity will return if null else every items will have a default rarity...
	
	public RarityStartupRegister() {}
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
		RARITY_ITEM_LIST.putIfAbsent(resourceName, rarityName);
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
