package net.tokyosu.raritymod.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.ForgeRegistries;
import net.tokyosu.raritymod.plugin.event.RarityStartupRegister;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

public class RarityUtils {
	/// STEP 1: Process item rarity (top priority !)
	public static boolean processItemRarity(CallbackInfoReturnable<Rarity> ci, ResourceLocation resource)
	{
		var resourceId = resource.toString();

		if (!RarityStartupRegister.isItemSame(resourceId)) // No item registered, return.
			return false;
		
		var rarityId = RarityStartupRegister.getItemRarity(resourceId);
		if (rarityId == null) return false;

		var rarity = RarityStartupRegister.getRarity(rarityId);
		if (rarity == null) return false;
		
		ci.setReturnValue(rarity);
		return true; // Everything is good, avoid other process !
	}

	/// STEP 2: Process tag rarity (middle priority !)
	public static boolean processTagRarity(CallbackInfoReturnable<Rarity> ci, Item item) {
		var rarityId = RarityStartupRegister.getTagRarity(new ItemStack(item));
		if (rarityId == null) return false;

		var rarity = RarityStartupRegister.getRarity(rarityId);
		if (rarity == null) return false;

		ci.setReturnValue(rarity);
		return true;
	}
	
	/// STEP 3: Process mod rarity (after middle priority !)
	public static boolean processModRarity(CallbackInfoReturnable<Rarity> ci, ResourceLocation resource)
	{
		var itemModId = getModNameByResource(resource);
		
		// Now check if a registered mod id exist.
		if (!RarityStartupRegister.isModSame(itemModId)) // If false: return, nothing to see.
			return false;
		
		var rarityId = RarityStartupRegister.getModRarity(itemModId);
		if (rarityId == null) return false;

		var rarity = RarityStartupRegister.getRarity(rarityId);
		if (rarity == null) return false;
		
		ci.setReturnValue(rarity);
		return true; // Everything is good, avoid other process !
	}
	
	/// STEP 4: Process default rarity (lowest priority !)
	public static boolean processDefaultRarity(CallbackInfoReturnable<Rarity> ci)
	{
		// If nothing is enabled, just return.
		var defaultRarity = RarityStartupRegister.getDefaultRarityId();
		if (defaultRarity == null) // Rarity is empty or not defined return.
			return false;
		
		var modrarity = RarityStartupRegister.getRarity(defaultRarity);
		if (modrarity == null) // If default rarity is null, search for minecraft rarity.
		{
			modrarity = getMinecraftRarityByName(defaultRarity);
			if (modrarity == null) // If it's still null then return.
				return false;
		}
		
		ci.setReturnValue(modrarity);
		return true; // Everything is good, avoid other process !
	}

	/// Get resource location by item.
	public static @Nullable ResourceLocation getResourceByItem(Item item) {
		var items = ForgeRegistries.ITEMS;
    	if (items.containsValue(item))
    		return items.getKey(item);
		return null;
	}

	/// Get minecraft rarity by name from kubejs script.
	public static @Nullable Rarity getMinecraftRarityByName(String name) {
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

	/// Get mod name by resource location.
	public static String getModNameByResource(ResourceLocation location) {
		return location.getNamespace();
	}
}
