package net.tokyosu.raritymod.mixin.minecraft;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.ForgeRegistries;
import net.tokyosu.apocalypselib.utils.ResourceUtils;
import net.tokyosu.apocalypselib.utils.TagUtils;
import net.tokyosu.raritymod.plugin.kubejs.event.RarityStartupRegister;
import net.tokyosu.raritymod.utils.RarityRegistry;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@SuppressWarnings("AddedMixinMembersNamePattern")
@Mixin(ItemStack.class)
public abstract class ItemRarityMixin {
    /// This override the getRarity function of minecraft to use custom rarity by kubejs.
    @Inject(method = "getRarity", at = @At("RETURN"), cancellable = true)
    private void changeRarity(@NotNull CallbackInfoReturnable<Rarity> ci) {
		var stack = (ItemStack)(Object)this;
		var item = stack.getItem();
		if (item == null) {
			// If item is null, return default rarity.
			ci.setReturnValue(ci.getReturnValue());
			return;
		}

		var resourceLoc = ResourceUtils.getResourcebyItem(item);
		if (resourceLoc != null) {
			if (processTagRarity(ci, stack))
				return;
			if (processItemNBTRarity(ci, stack, resourceLoc))
				return;
			if (processItemRarity(ci, resourceLoc))
				return;
			if (processModRarity(ci, resourceLoc))
				return;
		}

        // Only minecraft rarity can be replaced.
        if (processRarityReplacement(ci))
            return;

		// Check default rarity.
		if (processDefaultRarity(ci))
			return;
    	
		// If nothing is found, return default value !
        ci.setReturnValue(ci.getReturnValue());
    }

	/// STEP 1: Process tag rarity.
	@Unique
    private boolean processTagRarity(@NotNull CallbackInfoReturnable<Rarity> ci, @NotNull ItemStack stack) {
		var rarityId = RarityStartupRegister.getTagRarity(stack);
		if (rarityId == null) return false;

		var rarity = RarityStartupRegister.getRarity(rarityId);
		if (rarity == null) return false;

		ci.setReturnValue(rarity);
		return true;
	}

	/// STEP 2: Process nbt rarity.
	@Unique
    private boolean processItemNBTRarity(@NotNull CallbackInfoReturnable<Rarity> ci, @NotNull ItemStack stack, @NotNull ResourceLocation resource) {
		var resourceId = resource.toString();
		if (!RarityStartupRegister.isItemNBTFound(resourceId)) // No item registered, return.
			return false;

		var nbt = RarityStartupRegister.getNBTRarity(resourceId);
		if (nbt == null) return false;

		if (!TagUtils.containsNBT(stack, nbt.getA()))
			return false;

		var rarity = RarityStartupRegister.getRarity(nbt.getB());
		if (rarity == null) return false;

		ci.setReturnValue(rarity);
		return true; // Everything is good, avoid other process !
	}

	/// STEP 3: Process item rarity.
	@Unique
    private boolean processItemRarity(@NotNull CallbackInfoReturnable<Rarity> ci, @NotNull ResourceLocation resource) {
		var resourceId = resource.toString();
		if (RarityStartupRegister.isItemNotSame(resourceId)) // No item registered, return.
			return false;

		var rarityId = RarityStartupRegister.getItemRarity(resourceId);
		if (rarityId == null) return false;

		var rarity = RarityStartupRegister.getRarity(rarityId);
		if (rarity == null) return false;

		ci.setReturnValue(rarity);
		return true; // Everything is good, avoid other process !
	}

	/// STEP 4: Process mod rarity.
	@Unique
	private boolean processModRarity(@NotNull CallbackInfoReturnable<Rarity> ci, @NotNull ResourceLocation resource) {
		// Now check if a registered mod id exist.
		var itemModId = resource.getNamespace();
		if (!RarityStartupRegister.isModSame(itemModId)) // If false: return, nothing to see.
			return false;

		var rarityId = RarityStartupRegister.getModRarity(itemModId);
		if (rarityId == null) return false;

		var rarity = RarityStartupRegister.getRarity(rarityId);
		if (rarity == null) return false;

		ci.setReturnValue(rarity);
		return true; // Everything is good, avoid other process !
	}

	/// STEP 5: Process default rarity.
	@Unique
	private boolean processDefaultRarity(@NotNull CallbackInfoReturnable<Rarity> ci) {
		// If nothing is enabled, just return.
		var defaultRarity = RarityStartupRegister.getDefaultRarityId();
		if (defaultRarity == null) // Rarity is empty or not defined return.
			return false;

		var modrarity = RarityStartupRegister.getRarity(defaultRarity);
		if (modrarity == null) // If default rarity is null, search for minecraft rarity.
		{
			modrarity = RarityRegistry.getMinecraftRarityByName(defaultRarity);
			if (modrarity == null) // If it's still null then return.
				return false;
		}

		ci.setReturnValue(modrarity);
		return true; // Everything is good, avoid other process !
	}

    /// Replace an old rarity to a new rarity without replacing other rarity set function and before the default function.
    @Unique
    private boolean processRarityReplacement(@NotNull CallbackInfoReturnable<Rarity> ci) {
        Rarity currentRarity = ci.getReturnValue();

        // IMPORTANT:
        // Only Minecraft's built-in rarities can be replaced here.
        String currentRarityId = RarityRegistry.getMinecraftRarityIdByRarity(currentRarity);
        if (currentRarityId == null)
            return false;

        String replacementId = RarityStartupRegister.getReplacedRarity(currentRarityId);
        if (replacementId == null)
            return false;

        // Custom RarityJS rarity.
        Rarity replacement = RarityStartupRegister.getRarity(replacementId);

        // Or allow replacement -> Minecraft rarity too.
        if (replacement == null) {
            replacement = RarityRegistry.getMinecraftRarityByName(replacementId);
        }

        if (replacement == null)
            return false;

        ci.setReturnValue(replacement);
        return true;
    }
}
