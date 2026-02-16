package net.tokyosu.raritymod.editor.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.tokyosu.apocalypselib.utils.ResourceUtils;
import net.tokyosu.raritymod.RarityMod;
import net.tokyosu.raritymod.editor.menu.EditorMenu;
import net.tokyosu.raritymod.plugin.kubejs.event.RarityStartupRegister;
import net.tokyosu.raritymod.utils.RarityRegistry;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("DataFlowIssue")
public class RarityEditorScreen extends EditorScreen<Rarity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RarityMod.MOD_ID);

    public RarityEditorScreen(@NotNull EditorMenu menu, @NotNull Inventory playerInv, @NotNull Component menuName) {
        super(menu, playerInv, menuName);
    }

    @Override
    public void onInitEnd() {
        this.setValues(RarityStartupRegister.RARITY_LIST.values().stream().sorted(Rarity::compareTo).toList());
    }

    @Override
    public @NotNull Component onDropDownDisplay(@NotNull Rarity value) {
        return Component.literal(value.name()).withStyle(value.getStyleModifier());
    }

    @Override
    public void onDropDownItemSelected(@NotNull Rarity value) {
        if (this.selectedStack != null) {
            var resource = ResourceUtils.getResourcebyItem(this.selectedStack.getItem());
            if (resource != null) {
                if (this.selectedStack.hasTag()) {
                    RarityRegistry.setNbtRarity(resource.toString(), this.selectedStack.getTag(), value.name());
                } else {
                    RarityRegistry.setItemRarity(resource.toString(), value.name());
                }
            } else {
                LOGGER.error("Failed to select rarity for item {}, resource name not found !", this.selectedStack.getDisplayName());
            }
        }
    }

    @Override
    public @NotNull Rarity onItemStackClicked(@NotNull ItemStack stack) {
        return stack.getRarity();
    }
}
