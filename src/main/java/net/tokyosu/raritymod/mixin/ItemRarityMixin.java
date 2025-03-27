package net.tokyosu.raritymod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.tokyosu.raritymod.utils.RarityUtils;

@SuppressWarnings("all")
@Mixin(ItemStack.class)
public abstract class ItemRarityMixin {
    @Shadow public abstract Item getItem();
    
    @Inject(method = "getRarity", at = @At("RETURN"), cancellable = true)
    private void changeRarity(CallbackInfoReturnable<Rarity> ci)
    {
    	var item = getItem();
    	var resourceLoc = RarityUtils.getResourceByItem(item);
    	if (resourceLoc != null)
    	{
    		if (RarityUtils.processItemRarity(ci, item, resourceLoc))
        		return;
        	if (RarityUtils.processModRarity(ci, resourceLoc))
        		return;
    	}
    	
    	// Check default rarity.
    	if (RarityUtils.processDefaultRarity(ci))
    		return;
    	
    	 // If nothing is found, return default value !
        ci.setReturnValue(ci.getReturnValue());
    }
}
