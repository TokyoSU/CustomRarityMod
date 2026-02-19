package net.tokyosu.raritymod.editor.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tokyosu.apocalypselib.menu.component.DropdownList;
import net.tokyosu.apocalypselib.utils.ResourceUtils;
import net.tokyosu.raritymod.editor.menu.EditorMenu;
import net.tokyosu.raritymod.plugin.kubejs.event.RarityStartupRegister;
import net.tokyosu.raritymod.utils.RarityRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class EditorScreen extends AbstractContainerScreen<EditorMenu> {
    private DropdownList<Rarity> selector;
    private @Nullable Rect2i selectedRect;
    private ItemStack selectedStack;
    private boolean subGuiOpened = false;

    public EditorScreen(@NotNull EditorMenu menu, @NotNull Inventory playerInv, @NotNull Component menuName) {
        super(menu, playerInv, menuName);
        this.inventoryLabelX = 8000;
        this.inventoryLabelY = 8000;
        this.titleLabelX = 8000;
        this.titleLabelY = 8000;
    }

    @Override
    protected void init() {
        super.init();

        // Initialize gui.
        this.menu.creativeMenu.init(this.font, this.width, this.height, 50);
        this.leftPos = this.menu.creativeMenu.getX();
        this.topPos = this.menu.creativeMenu.getY();

        // Create drop down for rarity.
        this.selector = new DropdownList<>(
                new Rect2i(this.leftPos, this.topPos, 145, 148),
                new Rect2i(0, 129, 145, 20),
                new Rect2i(0, 150, 145, 20),
                new Rect2i(0, 171, 145, 20),
                new Rect2i(0, 192, 145, 20),
                new Rect2i(0, 0, 145, 128),
                new Rect2i(129, 1, 12, 123),
                new Rect2i(146, 1, 12, 15),
                new Rect2i(1, 1, 123, 18),
                new Rect2i(3, 0, 125, 125),
                null, 256, 256,
                18,
                this::onDisplayedItem,
                this::onSelectedItem
        );

        // Initialize tabs.
        this.selector.setValues(RarityStartupRegister.RARITY_LIST.values().stream().sorted(Rarity::compareTo).toList());
    }

    private @NotNull Component onDisplayedItem(@NotNull Rarity value) {
        return Component.literal(value.name()).withStyle(value.getStyleModifier());
    }

    @SuppressWarnings("DataFlowIssue")
    private void onSelectedItem(@NotNull Rarity value) {
        if (this.selectedStack != null) {
            var resource = ResourceUtils.getResourcebyItem(this.selectedStack.getItem());
            if (resource != null) {
                var minecraftRarityStr = RarityRegistry.getMinecraftRarityIdByRarity(value);
                var rarityName = Objects.requireNonNullElseGet(minecraftRarityStr, value::name);
                if (this.selectedStack.hasTag()) {
                    RarityRegistry.setNbtRarity(resource.toString(), this.selectedStack.getTag(), rarityName);
                } else {
                    RarityRegistry.setItemRarity(resource.toString(), rarityName);
                }
            }
        }
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        this.menu.creativeMenu.tick();
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
        this.menu.creativeMenu.render(pGui, pMouseX, pMouseY, pPartialTick);

        if (this.subGuiOpened) {
            pGui.pose().pushPose();
            pGui.pose().translate(0, 0, 1200.0F);

            this.renderBackground(pGui);
            this.selector.render(pGui, pMouseX, pMouseY, pPartialTick);
            if (this.subGuiOpened && this.selector.isClosing()) {
                this.subGuiOpened = false;
            }

            pGui.pose().popPose();
        }

        this.renderTooltip(pGui, pMouseX, pMouseY);
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

    private @NotNull Rarity onItemStackClicked(@NotNull ItemStack stack) {
        return stack.getRarity();
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        this.renderBackground(guiGraphics);
        this.menu.creativeMenu.renderBg(guiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        if (this.menu.creativeMenu.isMouseOver(mouseX, mouseY))
            return true;
        return super.isMouseOver(mouseX, mouseY);
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

        if (this.menu.creativeMenu.mouseClicked(this, x, y, type))
            return true;
        return super.mouseClicked(x, y, type);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.menu.creativeMenu.keyPressed(keyCode, scanCode, modifiers))
            return true;
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (this.menu.creativeMenu.charTyped(codePoint, modifiers))
            return true;
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (this.subGuiOpened) {
            return this.selector.mouseScrolled(mouseX, mouseY, delta);
        }
        return this.menu.creativeMenu.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.subGuiOpened) {
            return this.selector.mouseDragged(mouseY);
        }
        if (this.menu.creativeMenu.mouseDragged(mouseX, mouseY, button, dragX, dragY))
            return true;
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.subGuiOpened) {
            this.selector.mouseReleased();
            return true;
        }
        this.menu.creativeMenu.mouseReleased();
        return super.mouseReleased(mouseX, mouseY, button);
    }
}
