package net.tokyosu.raritymod;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tokyosu.raritymod.compat.FragmentumCompat;
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
            FragmentumCompat.registerBuiltInPack();
        }
    }
}
