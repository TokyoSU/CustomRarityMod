package net.tokyosu.raritymod.plugin.event;

import java.util.ArrayList;

import dev.latvian.mods.kubejs.event.StartupEventJS;
import dev.latvian.mods.kubejs.typings.Generics;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;

public class RarityStartupRegister extends StartupEventJS {
	@HideFromJS
	public static final ArrayList<Rarity> RARITY_LIST = new ArrayList<Rarity>();
	
	public RarityStartupRegister() {}
	public static RarityStartupRegister create() {
		return new RarityStartupRegister();
	}

	@Info(value = "Register a new rarity", params = {
            @Param(name = "name", value = "The rarity identifier (example: raritymod.god)"),
            @Param(name = "formattingName", value = "The rarity color (example: white)(can found a list here: https://minecraft.fandom.com/wiki/Formatting_codes#Color_codes)")
    })
    @Generics(value = {String.class, String.class})
	public void addRarity(String name, String formattingName) {
		RARITY_LIST.add(Rarity.create(name, ChatFormatting.getByName(formattingName)));
	}
}
