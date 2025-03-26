package net.tokyosu.raritymod.plugin;

import net.minecraft.resources.ResourceLocation;

public class RarityData {
	public ResourceLocation resourceLoc;
	public String rarityName;
	
	public RarityData() {}
	public RarityData(ResourceLocation _resourceName, String _rarityName) {
		resourceLoc = _resourceName;
		rarityName = _rarityName;
	}
}
