package net.tokyosu.raritymod.plugin.event;

import java.util.Hashtable;

import dev.latvian.mods.kubejs.event.StartupEventJS;
import dev.latvian.mods.kubejs.typings.Generics;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;

public class RarityStartupRegister extends StartupEventJS {
	@HideFromJS
	private static final Hashtable<String, Rarity> RARITY_LIST = new Hashtable<>();
	
	public RarityStartupRegister() {}
	public static RarityStartupRegister create() {
		return new RarityStartupRegister();
	}
	
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
}
