package net.tokyosu.raritymod.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Rarity;
import net.tokyosu.apocalypselib.utils.TagUtils;
import net.tokyosu.raritymod.plugin.kubejs.event.RarityStartupRegister;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

public class RarityRegistry {
    /// Register new rarity.
    public static void register(@NotNull String rarityID, @NotNull String chatFormattingID) {
        if (rarityID.contains("minecraft")) {
            RarityStartupRegister.RARITY_LIST.putIfAbsent(rarityID, RarityRegistry.getMinecraftRarityByName(rarityID));
        } else {
            RarityStartupRegister.RARITY_LIST.putIfAbsent(rarityID, Rarity.create(rarityID, ChatFormatting.getByName(chatFormattingID)));
        }
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

    public static void removeItemRarity(@NotNull String resourceName) {
        RarityStartupRegister.RARITY_ITEM_LIST.remove(resourceName);
    }

    public static void removeModRarity(@NotNull String modId) {
        RarityStartupRegister.RARITY_MOD_LIST.remove(modId);
    }

    public static void removeTagRarity(@NotNull String tagId) {
        RarityStartupRegister.RARITY_TAG_LIST.remove(tagId);
    }

    public static void removeNbtRarity(@NotNull String itemId) {
        RarityStartupRegister.RARITY_NBT_LIST.remove(itemId);
    }

    public static @Nullable String getMinecraftRarityIdByRarity(@NotNull Rarity rarity) {
        var name = rarity.toString();
        if (name.equalsIgnoreCase("UNCOMMON"))
            return "minecraft:uncommon";
        else if (name.equalsIgnoreCase("COMMON"))
            return "minecraft:common";
        else if (name.equalsIgnoreCase("RARE"))
            return "minecraft:rare";
        else if (name.equalsIgnoreCase("EPIC"))
            return "minecraft:epic";
        return null;
    }

    public static @Nullable Rarity getMinecraftRarityByName(@NotNull String name) {
        if (name.equalsIgnoreCase("minecraft:common"))
            return Rarity.COMMON;
        else if (name.equalsIgnoreCase("minecraft:uncommon"))
            return Rarity.UNCOMMON;
        else if (name.equalsIgnoreCase("minecraft:rare"))
            return Rarity.RARE;
        else if (name.equalsIgnoreCase("minecraft:epic"))
            return Rarity.EPIC;
        return null;
    }
}
