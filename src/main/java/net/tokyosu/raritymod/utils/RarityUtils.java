package net.tokyosu.raritymod.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.ForgeRegistries;
import net.tokyosu.raritymod.plugin.event.RarityStartupRegister;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

public class RarityUtils
{
	// STEP 1: Process item rarity (top priority !)
	public static boolean processItemRarity(CallbackInfoReturnable<Rarity> ci, Item item, ResourceLocation resource)
	{
		var resourceId = resource.toString();
		if (!RarityStartupRegister.isItemSame(resourceId)) // No item registered, return.
			return false;
		
		var rarityId = RarityStartupRegister.getItemRarity(resourceId);
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

	// STEP 2: Process tag rarity (middle priority !)
	public static boolean processTagRarity(CallbackInfoReturnable<Rarity> ci, Item item) {
		var rarityId = RarityStartupRegister.getTagRarity(new ItemStack(item));
		if (rarityId == null) return false;

		var rarity = RarityStartupRegister.getRarity(rarityId);
		if (rarity == null)
		{
			rarity = getMinecraftRarityByName(rarityId);
			if (rarity == null) // If even minecraft failed, return.
				return false;
		}

		ci.setReturnValue(rarity);
		return true;
	}
	
	// STEP 3: Process mod rarity (after middle priority !)
	public static boolean processModRarity(CallbackInfoReturnable<Rarity> ci, ResourceLocation resource)
	{
		var itemModId = getModNameByResource(resource);
		
		// Now check if a registered modid exist.
		if (!RarityStartupRegister.isModSame(itemModId)) // If false: return, nothing to see.
			return false;
		
		var rarityId = RarityStartupRegister.getModRarity(itemModId);
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
	
	// STEP 4: Process default rarity (lowest priority !)
	public static boolean processDefaultRarity(CallbackInfoReturnable<Rarity> ci)
	{
		// If nothing is enabled, just return.
		var defaultRarity = RarityStartupRegister.getDefaultRarityId();
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
    	if (name.contains("common"))
    		return Rarity.COMMON;
    	else if (name.contains("minecraft.uncommon"))
    		return Rarity.UNCOMMON;
    	else if (name.contains("minecraft.rare"))
    		return Rarity.RARE;
    	else if (name.contains("minecraft.epic"))
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
