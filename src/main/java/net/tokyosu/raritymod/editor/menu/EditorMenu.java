package net.tokyosu.raritymod.editor.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.tokyosu.apocalypselib.menu.base.MenuBase;
import net.tokyosu.apocalypselib.menu.button.TabButton;
import net.tokyosu.apocalypselib.menu.component.ScrollableGrid;
import net.tokyosu.raritymod.editor.RarityEditor;
import net.tokyosu.raritymod.editor.menu.slot.EditorDisplaySlot;
import net.tokyosu.raritymod.editor.tab.ModTabCollector;
import net.tokyosu.raritymod.editor.tab.TabCollector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EditorMenu extends MenuBase {
    public final List<TabButton> pTabButtonList = new ArrayList<>(); // Mod tab.
    public final int MAX_TAB_IN_PAGE = 7;
    public ScrollableGrid scrollableGrid;
    public Player player;
    public Inventory playerInv;
    public int currentTabPage = 0;

    public EditorMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
        super(RarityEditor.RARITY_EDITOR_MENU.get(), containerId);
        this.player = player;
        this.playerInv = playerInventory;
        this.scrollableGrid = new ScrollableGrid(RarityEditor.SLOT_CONTAINER);
        init(playerInventory);
    }

    public EditorMenu(int containerId, @NotNull Inventory playerInventory, @NotNull FriendlyByteBuf ignoredFriendlyByteBuf) {
        super(RarityEditor.RARITY_EDITOR_MENU.get(), containerId);
        this.player = playerInventory.player;
        this.playerInv = playerInventory;
        this.scrollableGrid = new ScrollableGrid(RarityEditor.SLOT_CONTAINER);
        init(playerInventory);
    }

    @Override
    public void init(@NotNull Inventory inventory) {
        var grid = this.scrollableGrid;
        if (grid == null) {
            throw new NullPointerException("Failed to initialize RarityEditorMenu, this.scrollableGrid is null !");
        }
        this.createDisplaySlots(grid.getColumnCount(), grid.getRowsCount(), grid.getSlotSize());

        // Collect every item and tabs icon.
        TabCollector.collectAllTabs();
        ModTabCollector.collectAllModTabs();
    }

    private void createDisplaySlots(int columnCount, int rowCount, int slotSize) {
        for (int rowId = 0; rowId < rowCount; rowId++) {
            for (int columnId = 0; columnId < columnCount; columnId++) {
                int slotId = (rowId * columnCount) + columnId;
                int x = 9 + columnId * slotSize;
                int y = 18 + rowId * slotSize;
                addSlot(new EditorDisplaySlot(RarityEditor.SLOT_CONTAINER, slotId, x, y));
            }
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int count) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    public void resetWithout(@NotNull TabButton button) {
        this.pTabButtonList.forEach((tab) -> {
            if (tab != button)
                tab.unselect();
        });
    }
}
