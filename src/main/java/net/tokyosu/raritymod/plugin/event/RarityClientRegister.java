package net.tokyosu.raritymod.plugin.event;

import java.util.ArrayList;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.typings.Generics;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.tokyosu.raritymod.plugin.RarityData;

public class RarityClientRegister extends EventJS {
	@HideFromJS
	public static final ArrayList<RarityData> RARITY_ITEM_LIST = new ArrayList<RarityData>();
	
	public RarityClientRegister() {}
	public RarityClientRegister create() {
		return new RarityClientRegister();
	}
	
	@Info(value = "Set a rarity to any items", params = {
            @Param(name = "resourceName", value = "The resource name (example: minecraft:apple)"),
            @Param(name = "rarityName", value = "The rarity name you given in addRarity (example: raritymod.god)")
    })
    @Generics(value = {ResourceLocation.class, String.class})
	public void setRarity(ResourceLocation resourceName, String rarityName) {
		RARITY_ITEM_LIST.add(new RarityData(resourceName, rarityName));
	}
}
