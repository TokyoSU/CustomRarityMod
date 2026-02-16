package net.tokyosu.raritymod.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Rarity;
import net.tokyosu.apocalypselib.utils.TagUtils;
import net.tokyosu.raritymod.plugin.kubejs.event.RarityStartupRegister;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RarityRegistry {
    /// Register new rarity.
    public static void register(@NotNull String rarityID, @NotNull String chatFormattingID) {
        RarityStartupRegister.RARITY_LIST.putIfAbsent(rarityID, Rarity.create(rarityID, ChatFormatting.getByName(chatFormattingID)));
    }

    /// Set a specific item to a custom rarity.
    public static void setItemRarity(@NotNull String resourceName, @NotNull String rarityID) {
        RarityStartupRegister.RARITY_ITEM_LIST.put(resourceName, rarityID);
    }

    /// Set a specific mod to a custom rarity, all items of the mod will have this rarity.
    public static void setModRarity(@NotNull String modID, @NotNull String rarityID) {
        RarityStartupRegister.RARITY_MOD_LIST.put(modID, rarityID);
    }

    /// Set a specific tag to a custom rarity, all items that have this tag will have this rarity.
    public static void setTagRarity(@NotNull String tagID, @NotNull String rarityID) {
        RarityStartupRegister.RARITY_TAG_LIST.put(tagID, rarityID);
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static void setNbtRarity(@NotNull String itemId, @NotNull String nbt, @NotNull String rarityID) {
        try {
            RarityStartupRegister.RARITY_NBT_LIST.put(itemId, new Tuple<>(Objects.requireNonNull(TagUtils.stringToNBT(nbt)), rarityID));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setNbtRarity(@NotNull String itemId, @NotNull CompoundTag nbt, @NotNull String rarityID) {
        RarityStartupRegister.RARITY_NBT_LIST.put(itemId, new Tuple<>(nbt, rarityID));
    }
}
