package net.tokyosu.raritymod.editor.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tokyosu.apocalypselib.builder.InventoryBuilder;
import net.tokyosu.apocalypselib.menu.button.TabButton;
import net.tokyosu.apocalypselib.menu.component.DropdownList;
import net.tokyosu.apocalypselib.utils.ModUtils;
import net.tokyosu.raritymod.RarityMod;
import net.tokyosu.raritymod.editor.menu.EditorMenu;
import net.tokyosu.raritymod.editor.component.ModTabButton;
import net.tokyosu.raritymod.editor.tab.ModTabCollector;
import net.tokyosu.raritymod.editor.tab.TabCollector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public abstract class EditorScreen<T> extends AbstractContainerScreen<EditorMenu> {
    private static final ResourceLocation EDITOR_TEXTURE = ResourceLocation.fromNamespaceAndPath(RarityMod.MOD_ID, "textures/gui/editor.png"); // 256x256
    private static final ResourceLocation EDITOR_SCROLL_TEXTURE = ResourceLocation.fromNamespaceAndPath(RarityMod.MOD_ID, "textures/gui/editor_scrollbar.png"); // 12x15
    private static final ResourceLocation EDITOR_DROP_DOWN_TEXTURE = ResourceLocation.fromNamespaceAndPath(RarityMod.MOD_ID, "textures/gui/dropdown.png"); // 256x256
    private final InventoryBuilder baseGUI;
    private EditBox searchBox;
    private Button nextPageButton;
    private Button previousPageButton;
    private boolean subGuiOpened = false;
    private DropdownList<T> selector;
    protected ItemStack selectedStack;

    public EditorScreen(@NotNull EditorMenu menu, @NotNull Inventory playerInv, @NotNull Component menuName) {
        super(menu, playerInv, menuName);
        this.baseGUI = new InventoryBuilder(EDITOR_TEXTURE, 195, 113, 256, 256);
        this.inventoryLabelX = 8000;
        this.inventoryLabelY = 8000;
        this.titleLabelX = 8000;
        this.titleLabelY = 8000;
    }

    /**
     * Called when the init() end.
     */
    public abstract void onInitEnd();

    @Override
    protected void init() {
        super.init();

        // Initialize gui.
        this.baseGUI.setFont(this.font);
        this.baseGUI.init(this.width, this.height);
        this.leftPos = this.baseGUI.getPosX();
        this.topPos = this.baseGUI.getPosY();

        // Initialize scrollable grid.
        this.menu.scrollableGrid.setItemList(TabCollector.TAB_ITEMS);
        this.menu.scrollableGrid.setScrollTexture(EDITOR_SCROLL_TEXTURE);
        this.menu.scrollableGrid.setScrollPos(this.baseGUI.getPosX() + 175, this.baseGUI.getPosY() + 18);
        this.menu.scrollableGrid.setTabIdentifier("minecraft", ModUtils.getModName("minecraft"));

        // Initialize search.
        this.searchBox = new EditBox(this.font, this.baseGUI.getPosX() + 99, this.baseGUI.getPosY() + 6, 88, 10, Component.literal("Search"));
        this.searchBox.setMaxLength(50);
        this.searchBox.setBordered(false);
        this.searchBox.setTextColor(0xFFFFFF);
        this.searchBox.setResponder(this::onSearchChanged);
        this.addRenderableWidget(this.searchBox);

        // Create drop down for rarity.
        int baseX = this.baseGUI.getPosX();
        int baseY = this.baseGUI.getPosY();
        this.selector = new DropdownList<>(
                new Rect2i(baseX, baseY, 145, 148),
                new Rect2i(0, 129, 145, 20),
                new Rect2i(0, 150, 145, 20),
                new Rect2i(0, 171, 145, 20),
                new Rect2i(0, 192, 145, 20),
                new Rect2i(0, 0, 145, 128),
                new Rect2i(129, 1, 12, 123),
                new Rect2i(146, 1, 12, 15),
                new Rect2i(1, 1, 123, 18),
                new Rect2i(3, 0, 125, 125),
                EDITOR_DROP_DOWN_TEXTURE, 256, 256,
                18,
                this::onDisplayedItem,
                this::onSelectedItem
        );

        // Initialize tabs.
        this.makeTabs();
        this.onInitEnd();
    }

    /**
     * Set the dropdown list.
     * @param values A valid list.
     */
    public void setValues(@NotNull List<T> values) {
        this.selector.setValues(values);
    }

    /**
     * Abstract function to draw an item being shown when dropdown is opened.
     * @param value A valid value.
     * @return A valid Component.
     */
    public abstract @NotNull Component onDropDownDisplay(@NotNull T value);

    /**
     * Abstract function when an item is selected on the dropdown list.
     * @param value A valid value.
     */
    public abstract void onDropDownItemSelected(@NotNull T value);

    /**
     * Abstract function when a ItemStack is selected with a right click on the editor.
     * @param stack A valid ItemStack is used.
     * @return A selected item to set for the selector.
     */
    public abstract @NotNull T onItemStackClicked(@NotNull ItemStack stack);

    private @NotNull Component onDisplayedItem(@NotNull T value) {
        return this.onDropDownDisplay(value);
    }

    private void onSelectedItem(@NotNull T value) {
        this.onDropDownItemSelected(value);
    }

    private void onSearchChanged(@NotNull String searchText) {
        if (searchText.isEmpty()) {
            this.menu.scrollableGrid.resetSearch();
            return;
        }
        this.menu.scrollableGrid.setSearchFilter(searchText);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        if (this.searchBox != null) {
            this.searchBox.tick();  // Required for cursor blinking and updates
        }
        this.menu.scrollableGrid.tick();
    }

    @Override
    protected boolean isHovering(int p_97768_, int p_97769_, int p_97770_, int p_97771_, double p_97772_, double p_97773_) {
        if (this.subGuiOpened) {
            return false;
        }
        return super.isHovering(p_97768_, p_97769_, p_97770_, p_97771_, p_97772_, p_97773_);
    }

    @Override
    public void render(@NotNull GuiGraphics pGui, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGui, pMouseX, pMouseY, pPartialTick);
        this.menu.scrollableGrid.renderScrollbar(pGui);

        if (this.subGuiOpened) {
            pGui.pose().pushPose();
            pGui.pose().translate(0, 0, 1200.0F);

            this.renderBackground(pGui);
            this.selector.render(pGui, pMouseX, pMouseY, pPartialTick);
            if (this.subGuiOpened && this.selector.isClosing()) {
                this.subGuiOpened = false;
            }

            pGui.pose().popPose();
        } else {

            // Render tab name
            int modCount = ModUtils.getModCount();
            int startIndex = this.menu.currentTabPage * this.menu.MAX_TAB_IN_PAGE;

            for (int tabId = 0; tabId < this.menu.pTabButtonList.size(); tabId++) {
                int modIndex = startIndex + tabId;
                if (modIndex >= modCount) break;

                var button = this.menu.pTabButtonList.get(tabId);
                if (button instanceof ModTabButton tab) {
                    var info = tab.getModInfo();
                    if (info != null) {
                        var stack = info.iconItem();
                        if (tab.isActive() && stack != null && !stack.isEmpty() && tab.isHovered()) {
                            pGui.renderTooltip(this.font, Component.literal(info.displayName()), pMouseX, pMouseY);
                        }
                    }
                }
            }

            this.renderTooltip(pGui, pMouseX, pMouseY);
        }
    }

    @SuppressWarnings({"NullableProblems", "ConstantValue"})
    @Override
    protected void slotClicked(Slot slot, int slotId, int mouseButton, @NotNull ClickType clickType) { // Slot can be null if clicked outside scope, need to check it.
        super.slotClicked(slot, slotId, mouseButton, clickType);
        if (slot == null) return;
        if (mouseButton == 1) {
            var stack = slot.getItem();
            if (!stack.isEmpty()) {
                this.subGuiOpened = true;
                this.selector.setClosing(false);
                this.selector.setSelected(this.onItemStackClicked(stack.copy()));
                this.selectedStack = stack.copy();
            }
        }
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        this.renderBackground(guiGraphics);
        this.baseGUI.setGraphics(guiGraphics);
        this.baseGUI.drawBackground(0, 0, 0, 0);
    }

    @Override
    public boolean mouseClicked(double x, double y, int type) {
        // Handle sub-GUI interactions
        if (this.subGuiOpened) {
            if (this.selector.mouseClicked(x, y, type)) {
                return true;
            }
            // Close sub-GUI if clicked outside
            if (!this.selector.isHovering(x, y)) {
                this.subGuiOpened = false;
                return true;
            }
            return true; // Consume click to prevent slot interaction
        }

        // Check search box FIRST before other interactions
        if (this.searchBox != null && this.searchBox.mouseClicked(x, y, type)) {
            this.setFocused(this.searchBox);  // Explicitly set focus
            return true;
        }
        // Clicking outside the search box should unfocus it
        if (this.searchBox != null) this.searchBox.setFocused(false);
        if (this.menu.scrollableGrid.mouseClicked(x, y, type)) return true;
        return super.mouseClicked(x, y, type);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.searchBox.isFocused()) {
            return this.searchBox.keyPressed(keyCode, scanCode, modifiers);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (this.searchBox.isFocused()) {
            return this.searchBox.charTyped(codePoint, modifiers);
        }
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (this.subGuiOpened) {
            return this.selector.mouseScrolled(mouseX, mouseY, delta);
        }
        return this.menu.scrollableGrid.mouseScrolled(delta);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.subGuiOpened) {
            return this.selector.mouseDragged(mouseY);
        }
        if (this.menu.scrollableGrid.mouseDragged(mouseY)) return true;
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.menu.scrollableGrid.mouseReleased();
        if (this.subGuiOpened) {
            this.selector.mouseReleased();
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private void onTabButtonPressed(@NotNull TabButton button, int modIndex) {
        List<ModTabCollector.ModTabInfo> allMods = new ArrayList<>(ModTabCollector.getModTabs().values());
        if (modIndex >= allMods.size()) return;
        var modInfo = allMods.get(modIndex);
        this.menu.resetWithout(button);
        this.searchBox.setValue("");
        this.menu.scrollableGrid.resetSearch();
        this.menu.scrollableGrid.setTabIdentifier(modInfo.namespace(), modInfo.displayName());
    }

    private void makeTabs() {
        // Remove old tabs
        for (var button : this.menu.pTabButtonList) {
            this.removeWidget(button);
        }
        this.menu.pTabButtonList.clear();

        // Get all mod tabs
        List<ModTabCollector.ModTabInfo> allMods = new ArrayList<>(ModTabCollector.getModTabs().values());
        int totalMods = allMods.size();
        int totalPages = (int)Math.ceil((double) totalMods / this.menu.MAX_TAB_IN_PAGE);

        // Calculate range for current page
        int startIndex = this.menu.currentTabPage * this.menu.MAX_TAB_IN_PAGE;
        int baseX = this.baseGUI.getPosX() + 6;
        int baseY = this.baseGUI.getPosY() - 28;

        // Create tabs for current page
        for (int tabId = 0; tabId < this.menu.MAX_TAB_IN_PAGE; tabId++) {
            int modIndex = startIndex + tabId;
            var tab = this.addRenderableWidget(new ModTabButton(
                    baseX + (tabId * 26),
                    baseY,
                    26, 29, // tab size.
                    0,
                    EDITOR_TEXTURE,
                    new Rect2i(196, 0, 256, 256),
                    new Rect2i(196, 29, 256, 256),
                    (e) -> onTabButtonPressed(e, modIndex),
                    Component.empty()));

            // Disable if no mod for this slot
            if (modIndex >= totalMods) {
                tab.active = false;
                tab.visible = false;
            } else {
                tab.setModInfo(allMods.get(modIndex));
            }

            // Auto-select first tab on first page
            if (tabId == 0 && this.menu.currentTabPage == 0) {
                tab.onPress();
            }

            this.menu.pTabButtonList.add(tab);
        }

        // Create/update pagination buttons
        this.updatePaginationButtons(totalPages);
    }

    private void updatePaginationButtons(int totalPages) {
        int paginationY = this.baseGUI.getPosY() - 24;
        int leftX = this.baseGUI.getPosX() - 20;
        int rightX = this.baseGUI.getPosX() + 195 + 5;

        // Remove old pagination buttons
        if (previousPageButton != null) this.removeWidget(previousPageButton);
        if (nextPageButton != null) this.removeWidget(nextPageButton);

        // Previous page button
        previousPageButton = this.addRenderableWidget(
                Button.builder(Component.literal("<"), btn -> {
                            if (this.menu.currentTabPage > 0) {
                                this.menu.currentTabPage--;
                                this.makeTabs();
                                this.menu.scrollableGrid.resetSearch();
                                this.menu.scrollableGrid.setDirty();
                            }
                        })
                        .bounds(leftX, paginationY, 15, 20)
                        .build()
        );
        previousPageButton.active = this.menu.currentTabPage > 0;

        // Next page button
        nextPageButton = this.addRenderableWidget(
                Button.builder(Component.literal(">"), btn -> {
                            if (this.menu.currentTabPage < totalPages - 1) {
                                this.menu.currentTabPage++;
                                this.makeTabs();
                                this.menu.scrollableGrid.resetSearch();
                                this.menu.scrollableGrid.setDirty();
                            }
                        })
                        .bounds(rightX, paginationY, 15, 20)
                        .build()
        );
        nextPageButton.active = this.menu.currentTabPage < totalPages - 1;
    }
}
