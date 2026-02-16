package net.tokyosu.raritymod;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.tokyosu.apocalypselib.builder.KeybindBuilder;
import org.jetbrains.annotations.NotNull;

public class RarityKeybindings {
    public static final @NotNull KeyMapping RARITY_EDITOR = new KeybindBuilder("open_rarity_editor", true).conflict(KeyConflictContext.UNIVERSAL).key(InputConstants.KEY_F7).category(KeybindBuilder.InputCategory.UI).build();
}
