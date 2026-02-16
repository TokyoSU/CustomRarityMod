package net.tokyosu.raritymod.editor;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tokyosu.apocalypselib.menu.component.ScrollableGrid;
import net.tokyosu.raritymod.RarityMod;
import net.tokyosu.raritymod.editor.menu.EditorMenu;

public class RarityEditor {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, RarityMod.MOD_ID);
    public static final RegistryObject<MenuType<EditorMenu>> RARITY_EDITOR_MENU = MENUS.register("menu." + RarityMod.MOD_ID + ".editor.name", () -> IForgeMenuType.create(EditorMenu::new));
    public static final SimpleContainer SLOT_CONTAINER = new SimpleContainer(ScrollableGrid.MAX_SLOTS);

    public static void initialize(IEventBus bus) {
        MENUS.register(bus);
    }
}
