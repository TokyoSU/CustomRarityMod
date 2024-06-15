package net.tokyosu.raritymod.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.ForgeRegistries;
import net.tokyosu.raritymod.Config;
import net.tokyosu.raritymod.Constants;

public class RarityUtils {

    public static Rarity GetRarityFromResourceLocation(ResourceLocation location, Rarity oldRarity)
    {
        if (Config.RARITY_GOD_LIST.contains(location))
            return Constants.RARITY_GOD;
        else if (Config.RARITY_MYTHIC_LIST.contains(location))
            return Constants.RARITY_MYTHIC;
        else if (Config.RARITY_UNIQUE_LIST.contains(location))
            return Constants.RARITY_UNIQUE;
        else if (Config.RARITY_LEGENDARY_LIST.contains(location))
            return Constants.RARITY_LEGENDARY;
        else if (Config.RARITY_EPIC_LIST.contains(location))
            return Constants.RARITY_EPIC;
        else if (Config.RARITY_RARE_LIST.contains(location))
            return Constants.RARITY_RARE;
        else if (Config.RARITY_UNCOMMON_LIST.contains(location))
            return Constants.RARITY_UNCOMMON;
        else if (Config.RARITY_COMMON_LIST.contains(location))
            return Constants.RARITY_COMMON;
        else return oldRarity;
    }

    public static Rarity GetRarityFromItem(Item item, Rarity old) {
        var items = ForgeRegistries.ITEMS;
        if (items.containsValue(item))
            return GetRarityFromResourceLocation(items.getKey(item), old);
        return old;
    }
}
