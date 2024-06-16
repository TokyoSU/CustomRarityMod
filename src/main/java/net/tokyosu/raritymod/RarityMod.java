package net.tokyosu.raritymod;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RarityMod.MOD_ID)
@OnlyIn(Dist.CLIENT)
public class RarityMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "raritymod";
    public RarityMod()
    {
        Config.RegisterConfig();
    }
}
