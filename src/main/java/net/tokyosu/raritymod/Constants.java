package net.tokyosu.raritymod;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;

public class Constants {
    public static final Rarity RARITY_COMMON = Rarity.create("raritymod.common", ChatFormatting.WHITE);
    public static final Rarity RARITY_UNCOMMON = Rarity.create("raritymod.uncommon", ChatFormatting.GREEN);
    public static final Rarity RARITY_RARE = Rarity.create("raritymod.rare", ChatFormatting.BLUE);
    public static final Rarity RARITY_EPIC = Rarity.create("raritymod.epic", ChatFormatting.YELLOW);
    public static final Rarity RARITY_LEGENDARY = Rarity.create("raritymod.legendary", ChatFormatting.GOLD);
    public static final Rarity RARITY_UNIQUE = Rarity.create("raritymod.unique", ChatFormatting.DARK_PURPLE);
    public static final Rarity RARITY_MYTHIC = Rarity.create("raritymod.mythic", ChatFormatting.RED);
    public static final Rarity RARITY_GOD = Rarity.create("raritymod.god", ChatFormatting.DARK_RED);
}
