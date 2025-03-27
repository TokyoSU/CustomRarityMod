package net.tokyosu.raritymod.plugin.event;

import java.util.Hashtable;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.typings.Generics;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import dev.latvian.mods.rhino.util.HideFromJS;

public class RarityClientRegister extends EventJS {
	@HideFromJS
	private static final Hashtable<String, String> RARITY_ITEM_LIST = new Hashtable<>(); // resourceName, rarityId
	@HideFromJS
	private static final Hashtable<String, String> RARITY_MOD_LIST = new Hashtable<>(); // modID, rarityId
	private static String DefaultRarityName = "common";
	
	public static boolean isItemSame(String resourceName) {
		return RARITY_ITEM_LIST.containsKey(resourceName);
	}
	
	public static String getItemRarity(String resourceName) {
		return RARITY_ITEM_LIST.getOrDefault(resourceName, null);
	}
	
	public static boolean isModSame(String modId) {
		return RARITY_MOD_LIST.containsKey(modId);
	}
	
	public static String getModRarity(String modId) {
		return RARITY_MOD_LIST.getOrDefault(modId, null);
	}
	
	public static String getDefaultRarityId() {
		return DefaultRarityName;
	}
	
	public RarityClientRegister() {}
	public RarityClientRegister create() {
		return new RarityClientRegister();
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
