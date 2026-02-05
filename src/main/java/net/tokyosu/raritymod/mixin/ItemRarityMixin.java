package net.tokyosu.raritymod.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.tokyosu.raritymod.utils.RarityUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemRarityMixin {
    /// This override the getRarity function of minecraft to use custom rarity by kubejs.
    @Inject(method = "getRarity", at = @At("RETURN"), cancellable = true)
    private void changeRarity(CallbackInfoReturnable<Rarity> ci)
    {
		ItemStack stack = (ItemStack)(Object)this;
		Item item = stack.getItem();
		if (item == null)
		{
			// If item is null, return default rarity.
			ci.setReturnValue(ci.getReturnValue());
			return;
		}

		var resourceLoc = RarityUtils.getResourceByItem(item);
		if (resourceLoc != null)
		{
			if (RarityUtils.processTagRarity(ci, stack))
				return;
			if (RarityUtils.processItemNBTRarity(ci, stack, resourceLoc))
				return;
			if (RarityUtils.processItemRarity(ci, resourceLoc))
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
