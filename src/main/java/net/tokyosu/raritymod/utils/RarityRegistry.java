package net.tokyosu.raritymod.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;
import net.tokyosu.raritymod.plugin.event.RarityStartupRegister;

public class RarityRegistry {
    /// Register new rarity.
    public static void register(String rarityID, String chatFormattingID) {
        RarityStartupRegister.RARITY_LIST.putIfAbsent(rarityID, Rarity.create(rarityID, ChatFormatting.getByName(chatFormattingID)));
    }

    /// Set a specific item to a custom rarity.
    public static void setItemRarity(String resourceName, String rarityID) {
        RarityStartupRegister.RARITY_ITEM_LIST.put(resourceName, rarityID);
    }

    /// Set a specific mod to a custom rarity, all items of the mod will have this rarity.
    public static void setModRarity(String modID, String rarityID) {
        RarityStartupRegister.RARITY_MOD_LIST.put(modID, rarityID);
    }

    /// Set a specific tag to a custom rarity, all items that have this tag will have this rarity.
    public static void setTagRarity(String tagID, String rarityID) {
        RarityStartupRegister.RARITY_TAG_LIST.put(tagID, rarityID);
    }
}
