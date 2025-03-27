package net.tokyosu.raritymod.utils;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.ForgeRegistries;
import net.tokyosu.raritymod.plugin.event.RarityClientRegister;
import net.tokyosu.raritymod.plugin.event.RarityStartupRegister;

public class RarityUtils
{
	// STEP 1: Process item rarity (top priority !)
	public static boolean processItemRarity(CallbackInfoReturnable<Rarity> ci, Item item, ResourceLocation resource)
	{
		// TODO: need to add variant !
		var resourceId = resource.toString();
		
		if (!RarityClientRegister.isItemSame(resourceId)) // No item registered, return.
			return false;
		
		var rarityId = RarityClientRegister.getItemRarity(resourceId);
		if (rarityId == null) // Id is null return.
			return false;
		
		var rarity = RarityStartupRegister.getRarity(rarityId);
		if (rarity == null) // If custom rarity failed, check minecraft rarity.
		{
			rarity = getMinecraftRarityByName(rarityId);
			if (rarity == null) // If even minecraft failed, return.
				return false;
		}
		
		ci.setReturnValue(rarity);
		return true; // Everything is good, avoid other process !
	}
	
	// STEP 2: Process mod rarity (middle priority !)
	public static boolean processModRarity(CallbackInfoReturnable<Rarity> ci, ResourceLocation resource)
	{
		var itemModId = getModNameByResource(resource);
		
		// Now check if a registered modid exist.
		if (!RarityClientRegister.isModSame(itemModId)) // If false: return, nothing to see.
			return false;
		
		var rarityId = RarityClientRegister.getModRarity(itemModId);
		if (rarityId == null) // Id is null, return.
			return false;
		
		var rarity = RarityStartupRegister.getRarity(rarityId);
		if (rarity == null) // If custom rarity failed, check minecraft rarity.
		{
			rarity = getMinecraftRarityByName(rarityId);
			if (rarity == null) // If even minecraft failed, return.
				return false;
		}
		
		ci.setReturnValue(rarity);
		return true; // Everything is good, avoid other process !
	}
	
	// STEP 3: Process default rarity (lowest priority !)
	public static boolean processDefaultRarity(CallbackInfoReturnable<Rarity> ci)
	{
		// If nothing is enabled, just return.
		var defaultRarity = RarityClientRegister.getDefaultRarityId();
		if (defaultRarity == null) // Rarity is empty or not defined return.
			return false;
		
		var rarity = RarityStartupRegister.getRarity(defaultRarity);
		if (rarity == null) // If default rarity is null, search for minecraft rarity.
		{
			rarity = getMinecraftRarityByName(defaultRarity);
			if (rarity == null) // If it's still null then return.
				return false;
		}
		
		ci.setReturnValue(rarity);
		return true; // Everything is good, avoid other process !
	}
	
	public static @Nullable ResourceLocation getResourceByItem(Item item) {
		var items = ForgeRegistries.ITEMS;
    	if (items.containsValue(item))
    		return items.getKey(item);
		return null;
	}
	
	public static @Nullable Rarity getMinecraftRarityByName(String name) {
    	if (name.equalsIgnoreCase("minecraft.common"))
    		return Rarity.COMMON;
    	else if (name.equalsIgnoreCase("minecraft.uncommon"))
    		return Rarity.UNCOMMON;
    	else if (name.equalsIgnoreCase("minecraft.rare"))
    		return Rarity.RARE;
    	else if (name.equalsIgnoreCase("minecraft.epic"))
    		return Rarity.EPIC;
    	return null;
    }
	
	public static String getModNameByResource(ResourceLocation location) {
		return location.getNamespace();
	}
	
	public static String getItemNameByResource(ResourceLocation location) {
		return location.getPath();
	}
}
