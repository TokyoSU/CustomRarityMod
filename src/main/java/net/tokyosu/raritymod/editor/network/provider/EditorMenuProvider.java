package net.tokyosu.raritymod.editor.network.provider;

import net.minecraft.network.chat.Component;
import net.tokyosu.apocalypselib.menu.base.MenuProviderBase;
import net.tokyosu.raritymod.editor.menu.EditorMenu;

public class EditorMenuProvider extends MenuProviderBase {
    public EditorMenuProvider() {
        super(EditorMenu::new, Component.translatable("menu.rarity_editor"));
    }
}
