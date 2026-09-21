package net.tokyosu.raritymod.editor.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.tokyosu.apocalypselib.menu.base.MenuBase;
import net.tokyosu.apocalypselib.menu.component.CreativePanel;
import net.tokyosu.raritymod.editor.RarityEditor;
import org.jetbrains.annotations.NotNull;

public class EditorMenu extends MenuBase {
    public final CreativePanel creativeMenu;

    public EditorMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
        super(RarityEditor.RARITY_EDITOR_MENU.get(), containerId);
        this.creativeMenu = new CreativePanel(RarityEditor.SLOT_CONTAINER);
        this.init(playerInventory);
    }

    public EditorMenu(int containerId, @NotNull Inventory playerInventory, @NotNull FriendlyByteBuf ignoredFriendlyByteBuf) {
        super(RarityEditor.RARITY_EDITOR_MENU.get(), containerId);
        this.creativeMenu = new CreativePanel(RarityEditor.SLOT_CONTAINER);
        this.init(playerInventory);
    }

    @Override
    public void init(@NotNull Inventory inventory) {
        var slots = this.creativeMenu.createSlots(RarityEditor.SLOT_CONTAINER);
        for (var slot : slots) {
            this.addSlot(slot);
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int count) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.creativeMenu.stillValid();
    }


}
