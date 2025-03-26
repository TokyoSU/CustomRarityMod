package net.tokyosu.raritymod.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.ForgeRegistries;
import net.tokyosu.raritymod.plugin.RarityData;
import net.tokyosu.raritymod.plugin.event.RarityStartupRegister;
import net.tokyosu.raritymod.plugin.event.RarityClientRegister;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.logging.LogUtils;

@SuppressWarnings("all")
@Mixin(ItemStack.class)
public abstract class ItemRarityMixin {
    @Shadow public abstract Item getItem();
    
    private @Nullable ResourceLocation getResourceByItem(Item item) {
    	var items = ForgeRegistries.ITEMS;
    	if (items.containsValue(item))
    		return items.getKey(item);
		return null;
    }
    
    private Rarity getRarityByName(String rarity) {
    	for (Rarity value : RarityStartupRegister.RARITY_LIST)
    	{
    		if (value.name().equalsIgnoreCase(rarity))
    			return value;
    	}
    	return null;
    }
    
    @Inject(method = "getRarity", at = @At("RETURN"), cancellable = true)
    private void changeRarity(CallbackInfoReturnable<Rarity> ci) {
    	var resourceLoc = getResourceByItem(getItem());
    	if (resourceLoc == null) // If no resource then return default value !
    	{
            ci.setReturnValue(ci.getReturnValue());
    		return;
    	}
    	
    	// Search for the correct value.
    	for (RarityData holder : RarityClientRegister.RARITY_ITEM_LIST) {
			if (holder.resourceLoc.equals(resourceLoc))
			{
				var rarity = getRarityByName(holder.rarityName);
				ci.setReturnValue(rarity != null ? rarity : ci.getReturnValue()); // If the return is null, make it default value !
				return;
			}
		}
    	
        ci.setReturnValue(ci.getReturnValue()); // If nothing is found, return default value !
    }
}
