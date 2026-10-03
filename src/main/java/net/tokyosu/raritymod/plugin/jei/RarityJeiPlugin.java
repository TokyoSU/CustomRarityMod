package net.tokyosu.raritymod.plugin.jei;

import java.util.List;
import net.minecraft.client.renderer.Rect2i;
import net.tokyosu.apocalypselib.compat.jei.GuiContainerHandler;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.resources.ResourceLocation;
import net.tokyosu.raritymod.RarityMod;
import net.tokyosu.raritymod.editor.screen.EditorScreen;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class RarityJeiPlugin implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(RarityMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {
        IModPlugin.super.registerGuiHandlers(registration);
        registration.addGuiContainerHandler(EditorScreen.class, new GuiContainerHandler<>(screen -> List.of(
                        new Rect2i(screen.getGuiLeft(), screen.getGuiTop(), 195, 113),
                        new Rect2i(screen.getGuiLeft() - 30, screen.getGuiTop() - 30, 245, 50))));
    }

}
