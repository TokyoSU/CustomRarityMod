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
	public static final Hashtable<String, String> RARITY_ITEM_LIST = new Hashtable<>(); // resourceName, rarityId
	@HideFromJS
	public static final Hashtable<String, String> RARITY_MOD_LIST = new Hashtable<>(); // modID, rarityId
	@HideFromJS
	public static final Hashtable<String, String> RARITY_TAG_LIST = new Hashtable<>(); // tagID, rarityId
	@HideFromJS
	public static final Hashtable<String, Rarity> RARITY_LIST = new Hashtable<>(); // rarityName, formattingColorName
	@HideFromJS
	private static String DEFAULT_RARITY_ID = null;

	/// Does the item id match ? if true then found same item.
	@HideFromJS
	public static boolean isItemSame(String resourceName) {
		return RARITY_ITEM_LIST.containsKey(resourceName);
	}

	/// Get the custom rarity of the item by resource name.
	@HideFromJS
	public static String getItemRarity(String resourceName) {
		return RARITY_ITEM_LIST.getOrDefault(resourceName, null);
	}

	/// Does a mod id match ? if true then found same mod.
	@HideFromJS
	public static boolean isModSame(String modId) {
		return RARITY_MOD_LIST.containsKey(modId);
	}

	@HideFromJS
	public static String getModRarity(String modId) {
		return RARITY_MOD_LIST.getOrDefault(modId, null);
	}

	/// Get default rarity, used by all items if != null.
	@HideFromJS
	public static String getDefaultRarityId() {
		return DEFAULT_RARITY_ID;
	}

	/// Get custom rarity by tag.
	@HideFromJS
	public static String getTagRarity(ItemStack item) {
		if (item != null)
		{
			for (var tag : item.getTags().toList())
			{
				var tagKey = tag.location().toString();
				if (RARITY_TAG_LIST.containsKey(tagKey))
					return RARITY_TAG_LIST.get(tagKey); // If any tag is the same, return a registered rarity name id.
			}
		}
		return null;
	}

	/// Get custom rarity from this mod by name id.
	@HideFromJS
	public static Rarity getRarity(String rarityName) {
		return RARITY_LIST.getOrDefault(rarityName, null);
	}

	/// Register a new rarity, used by kubejs.
	@Info(value = "Register a new rarity", params = {
            @Param(name = "name", value = "The rarity identifier (example: raritymod.god)"),
            @Param(name = "formattingName", value = "The rarity color (example: white)(can found a list here: https://minecraft.fandom.com/wiki/Formatting_codes#Color_codes)")
    })
    @Generics(value = {String.class, String.class})
	public void addRarity(String name, String formattingName) {
		RARITY_LIST.putIfAbsent(name, Rarity.create(name, ChatFormatting.getByName(formattingName)));
	}

	/// Set rarity by item resource name, used by kubejs.
	@Info(value = "Set a rarity to any items", params = {
            @Param(name = "resourceName", value = "The resource name (example: minecraft:apple)"),
            @Param(name = "rarityName", value = "The rarity name you given in addRarity (example: raritymod.god)")
    })
    @Generics(value = {String.class, String.class})
	public void setRarity(String resourceName, String rarityName) {
		RARITY_ITEM_LIST.put(resourceName, rarityName);
	}

	/// Set rarity by tag id, used by kubejs.
	@Info(value = "Set a rarity by item tag", params = {
			@Param(name = "tagID", value = "The tag id (example: minecraft:logs)"),
			@Param(name = "rarityName", value = "The rarity name you given in addRarity (example: raritymod.god)")
	})
	public void setRarityByTag(String tagID, String rarityName) {
		RARITY_TAG_LIST.put(tagID, rarityName);
	}

	/// Set rarity by mod id, used by kubejs.
	@Info(value = "Set a rarity by mod id", params = {
            @Param(name = "modId", value = "The mod id (example: minecraft/avaritia)"),
            @Param(name = "rarityName", value = "The rarity name you given in addRarity (example: raritymod.god)")
    })
    @Generics(value = {String.class, String.class})
	public void setRarityByMod(String modId, String rarityName) {
		RARITY_MOD_LIST.putIfAbsent(modId, rarityName);
	}

	/// Set default rarity for items, used by kubejs.
	@Info(value = "Set a default rarity for the game (defined rarity with setRarity/setRarityByMod will take priority)", params = {
            @Param(name = "rarityName", value = "The rarity name you given in addRarity (example: raritymod.god) or minecraft one.")
    })
    @Generics(value = {String.class})
	public void setDefaultRarity(String rarityName) {
		DEFAULT_RARITY_ID = rarityName;
	}
}
