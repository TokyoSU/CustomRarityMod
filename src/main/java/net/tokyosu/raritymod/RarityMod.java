package net.tokyosu.raritymod;

import dev.obscuria.fragmentum.packs.BuiltInPackBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.fml.common.Mod;

@Mod(RarityMod.MOD_ID)
public class RarityMod
{
    public static final String MOD_ID = "raritymod";
    public RarityMod() {
        BuiltInPackBuilder.resourcePack("packs/rarity_tooltips")
                .displayName(Component.translatable("rarity.resource_pack.name"))
                .packSource(PackSource.BUILT_IN)
                .register(MOD_ID);
    }
}
