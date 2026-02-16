package net.tokyosu.raritymod.plugin.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import mezz.jei.api.runtime.config.IJeiConfigManager;
import net.minecraft.resources.ResourceLocation;
import net.tokyosu.raritymod.RarityMod;
import net.tokyosu.raritymod.editor.screen.RarityEditorScreen;
import net.tokyosu.raritymod.plugin.jei.container.IRarityEditorContainerHandler;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@JeiPlugin
public class RarityJeiPlugin implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(RarityMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerItemSubtypes(@NotNull ISubtypeRegistration registration) {
        IModPlugin.super.registerItemSubtypes(registration);
    }

    @Override
    public <T> void registerFluidSubtypes(@NotNull ISubtypeRegistration registration, @NotNull IPlatformFluidHelper<T> platformFluidHelper) {
        IModPlugin.super.registerFluidSubtypes(registration, platformFluidHelper);
    }

    @Override
    public void registerIngredients(@NotNull IModIngredientRegistration registration) {
        IModPlugin.super.registerIngredients(registration);
    }

    @Override
    public void registerExtraIngredients(@NotNull IExtraIngredientRegistration registration) {
        IModPlugin.super.registerExtraIngredients(registration);
    }

    @Override
    public void registerIngredientAliases(@NotNull IIngredientAliasRegistration registration) {
        IModPlugin.super.registerIngredientAliases(registration);
    }

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
        IModPlugin.super.registerCategories(registration);
    }

    @Override
    public void registerVanillaCategoryExtensions(@NotNull IVanillaCategoryExtensionRegistration registration) {
        IModPlugin.super.registerVanillaCategoryExtensions(registration);
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        IModPlugin.super.registerRecipes(registration);
    }

    @Override
    public void registerRecipeTransferHandlers(@NotNull IRecipeTransferRegistration registration) {
        IModPlugin.super.registerRecipeTransferHandlers(registration);
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        IModPlugin.super.registerRecipeCatalysts(registration);
    }

    @Override
    public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {
        IModPlugin.super.registerGuiHandlers(registration);
        registration.addGuiContainerHandler(RarityEditorScreen.class, new IRarityEditorContainerHandler());
    }

    @Override
    public void registerAdvanced(@NotNull IAdvancedRegistration registration) {
        IModPlugin.super.registerAdvanced(registration);
    }

    @Override
    public void registerRuntime(@NotNull IRuntimeRegistration registration) {
        IModPlugin.super.registerRuntime(registration);
    }

    @Override
    public void onRuntimeAvailable(@NotNull IJeiRuntime jeiRuntime) {
        IModPlugin.super.onRuntimeAvailable(jeiRuntime);
    }

    @Override
    public void onRuntimeUnavailable() {
        IModPlugin.super.onRuntimeUnavailable();
    }

    @Override
    public void onConfigManagerAvailable(@NotNull IJeiConfigManager configManager) {
        IModPlugin.super.onConfigManagerAvailable(configManager);
    }
}
