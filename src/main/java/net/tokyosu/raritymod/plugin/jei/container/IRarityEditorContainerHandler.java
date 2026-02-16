package net.tokyosu.raritymod.plugin.jei.container;

import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.renderer.Rect2i;
import net.tokyosu.raritymod.editor.screen.RarityEditorScreen;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class IRarityEditorContainerHandler implements IGuiContainerHandler<RarityEditorScreen> {
    @Override
    public @NotNull List<Rect2i> getGuiExtraAreas(@NotNull RarityEditorScreen containerScreen) {
        List<Rect2i> extraAreas = new ArrayList<>();
        int xPos = containerScreen.getGuiLeft();
        int yPos = containerScreen.getGuiTop();
        int width = 195;
        int height = 113;
        extraAreas.add(new Rect2i(xPos, yPos, width, height));
        extraAreas.add(new Rect2i(xPos - 30, yPos - 30, width + 50, 50));
        return extraAreas;
    }
}
