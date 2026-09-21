package net.tokyosu.raritymod.plugin.kubejs.event;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.latvian.mods.kubejs.event.StartupEventJS;
import dev.latvian.mods.kubejs.typings.Generics;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Hashtable;

@SuppressWarnings("unused")
public class RarityStartupRegister extends StartupEventJS
{
	@HideFromJS
	public static final Hashtable<String, String> RARITY_ITEM_LIST = new Hashtable<>(); // resourceName, rarityId
	@HideFromJS
	public static final Hashtable<String, String> RARITY_MOD_LIST = new Hashtable<>(); // modID, rarityId
	@HideFromJS
	public static final Hashtable<String, String> RARITY_TAG_LIST = new Hashtable<>(); // tagID, rarityId
	@HideFromJS
	public static final Hashtable<String, Tuple<CompoundTag, String>> RARITY_NBT_LIST = new Hashtable<>(); // resourceName, Tuple<NBTTag, RarityId>
	@HideFromJS
	public static final Hashtable<String, Rarity> RARITY_LIST = new Hashtable<>(); // rarityName, formattingColorName
    @HideFromJS
    public static final Hashtable<String, String> RARITY_REPLACE_LIST = new Hashtable<>(); // oldRarity, newRarity
	@HideFromJS
	private static String DEFAULT_RARITY_ID = null;

	/// Does the item id match ? if true then found same item.
	@HideFromJS
	public static boolean isItemNotSame(@NotNull String resourceName) {
		return !RARITY_ITEM_LIST.containsKey(resourceName);
	}

	/// Get the custom rarity of the item by resource name.
	@HideFromJS
	public static @Nullable String getItemRarity(@NotNull String resourceName) {
		return RARITY_ITEM_LIST.getOrDefault(resourceName, null);
	}

	/// Does a mod id match ? if true then found same mod.
	@HideFromJS
	public static boolean isModSame(@NotNull String modId) {
		return RARITY_MOD_LIST.containsKey(modId);
	}

	@HideFromJS
	public static @Nullable String getModRarity(@NotNull String modId) {
		return RARITY_MOD_LIST.getOrDefault(modId, null);
	}

	@HideFromJS
	public static boolean isItemNBTFound(@NotNull String resourceName) {
		return RARITY_NBT_LIST.containsKey(resourceName);
	}

	/// Get default rarity, used by all items if != null.
	@HideFromJS
	public static @Nullable String getDefaultRarityId() {
		return DEFAULT_RARITY_ID;
	}

	/// Get custom rarity by tag.
	@HideFromJS
	public static @Nullable String getTagRarity(@NotNull ItemStack stack) {
		return stack.getTags()
				.map(TagKey::location)
				.map(ResourceLocation::toString)
				.filter(RARITY_TAG_LIST::containsKey)
				.findFirst()
				.map(RARITY_TAG_LIST::get)
				.orElse(null);
	}

	/// Get custom rarity from item resource name and then by nbt tag.
	@HideFromJS
	public static @Nullable Tuple<CompoundTag, String> getNBTRarity(@NotNull String resourceName) {
		return RARITY_NBT_LIST.getOrDefault(resourceName, null);
	}

	/// Get custom rarity from this mod by name id.
	@HideFromJS
	public static @Nullable Rarity getRarity(@NotNull String rarityName) {
		return RARITY_LIST.getOrDefault(rarityName, null);
	}

    /// Get a replaced rarity if oldRarity is valid, if it's not found, return null to avoid replacing the rarity.
    public static @Nullable String getReplacedRarity(@NotNull String oldRarity) {
        return RARITY_REPLACE_LIST.getOrDefault(oldRarity, null);
    }

	/// Register a new rarity, used by kubejs.
	@Info(value = "Register a new rarity", params = {
            @Param(name = "name", value = "The rarity identifier (example: raritymod.god)"),
            @Param(name = "formattingName", value = "The rarity color (example: white)(can found a list here: https://minecraft.fandom.com/wiki/Formatting_codes#Color_codes)")
    })
    @Generics(value = {String.class, String.class})
	public void addRarity(@NotNull String name, @NotNull String formattingName) {
		RARITY_LIST.putIfAbsent(name, Rarity.create(name, ChatFormatting.getByName(formattingName)));
	}

	/// Set rarity by item resource name, used by kubejs.
	@Info(value = "Set a rarity by resource name.", params = {
            @Param(name = "resourceName", value = "The resource name (example: minecraft:apple)"),
            @Param(name = "rarityName", value = "The rarity name you given in addRarity (example: raritymod.god)")
    })
    @Generics(value = {String.class, String.class})
	public void setRarity(@NotNull String resourceName, @NotNull String rarityName) {
		RARITY_ITEM_LIST.put(resourceName, rarityName);
	}

	/// Set rarity by item resource name and nbt tag, used by kubejs.
	@Info(value = "Set a rarity based on nbt.", params = {
			@Param(name = "resourceName", value = "The resource name (example: minecraft:apple)"),
			@Param(name = "nbt", value = "The nbt used."),
			@Param(name = "rarityName", value = "The rarity name you given in addRarity (example: raritymod.god)")
	})
	@Generics(value = {String.class, String.class, String.class})
	public void setRarityByNBT(@NotNull String resourceName, @NotNull String nbt, @NotNull String rarityName) throws CommandSyntaxException {
		RARITY_NBT_LIST.put(resourceName, new Tuple<>(TagParser.parseTag(nbt), rarityName));
	}

	/// Set rarity by tag id, used by kubejs.
	@Info(value = "Set a rarity based on item tag.", params = {
			@Param(name = "tagID", value = "The tag id (example: minecraft:logs)"),
			@Param(name = "rarityName", value = "The rarity name you given in addRarity (example: raritymod.god)")
	})
	@Generics(value = {String.class, String.class})
	public void setRarityByTag(@NotNull String tagID, @NotNull String rarityName) {
		RARITY_TAG_LIST.put(tagID, rarityName);
	}

	/// Set rarity by mod id, used by kubejs.
	@Info(value = "Set a rarity based on mod id.", params = {
            @Param(name = "modId", value = "The mod id (example: minecraft/avaritia)"),
            @Param(name = "rarityName", value = "The rarity name you given in addRarity (example: raritymod.god)")
    })
    @Generics(value = {String.class, String.class})
	public void setRarityByMod(@NotNull String modId, @NotNull String rarityName) {
		RARITY_MOD_LIST.putIfAbsent(modId, rarityName);
	}

	/// Set default rarity for items, used by kubejs.
	@Info(value = "Set a default rarity for the game (defined rarity with setRarity/setRarityByMod will take priority)", params = {
            @Param(name = "rarityName", value = "The rarity name you given in addRarity (example: raritymod.god) or minecraft one.")
    })
    @Generics(value = {String.class})
	public void setDefaultRarity(@NotNull String rarityName) {
		DEFAULT_RARITY_ID = rarityName;
	}

    @Info(value = "Replace a rarity by another rarity (only support minecraft rarity), other mods not supported !", params = {
            @Param(name = "oldRarity", value = "The rarity you want to replace"),
            @Param(name = "newRarity", value = "The rarity you want to assign to the replaced rarity")
    })
    public void replaceRarityBy(@NotNull String oldRarity, @NotNull String newRarity) {
        RARITY_REPLACE_LIST.putIfAbsent(oldRarity, newRarity);
    }
}
