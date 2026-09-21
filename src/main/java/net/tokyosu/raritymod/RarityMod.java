package net.tokyosu.raritymod;

import dev.obscuria.fragmentum.api.common.resource.BuiltInPacks;
import dev.obscuria.fragmentum.api.common.resource.SelectionConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tokyosu.raritymod.editor.RarityEditor;
import net.tokyosu.raritymod.editor.network.NetworkHandler;

@SuppressWarnings({"removal", "SpellCheckingInspection"})
@Mod(RarityMod.MOD_ID)
public class RarityMod
{
    public static final String MOD_ID = "raritymod";
    public RarityMod() {
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        RarityEditor.initialize(modEventBus);
        NetworkHandler.register();
        if (ModList.get().isLoaded("fragmentum")) {
            BuiltInPacks.INSTANCE.createClientResources()
                    .resourcesFrom(RarityMod.class, MOD_ID)
                    .selectionConfig(new SelectionConfig(true, Pack.Position.TOP, false))
                    .directory("packs/rarity_tooltips")
                    .displayName(Component.translatable("rarity.resource_pack.name"))
                    .packSource(PackSource.BUILT_IN)
                    .build();
        }
    }
}
