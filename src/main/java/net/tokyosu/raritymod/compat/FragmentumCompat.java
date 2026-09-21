package net.tokyosu.raritymod.compat;

import dev.obscuria.fragmentum.api.common.resource.BuiltInPacks;
import dev.obscuria.fragmentum.api.common.resource.SelectionConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.tokyosu.raritymod.RarityMod;

public class FragmentumCompat
{
    public static void registerBuiltInPack() {
        BuiltInPacks.INSTANCE.createClientResources()
                .resourcesFrom(RarityMod.class, RarityMod.MOD_ID)
                .selectionConfig(new SelectionConfig(true, Pack.Position.TOP, false))
                .directory("packs/rarity_tooltips")
                .displayName(Component.translatable("rarity.resource_pack.name"))
                .packSource(PackSource.BUILT_IN)
                .build();
    }
}
