package net.tokyosu.raritymod;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = RarityMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final Logger LOGGER = LogUtils.getLogger();

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_RARITY_COMMON = BUILDER
            .comment("Define rarity for each item in this list to common rank, must be a resource location name !")
            .comment("If any item is already defined in another list, it will use the highest rarity first !")
            .defineListAllowEmpty("item_rarity_common_list", Collections.emptyList(), Config::validateItemName);
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_RARITY_UNCOMMON = BUILDER
            .comment("Define rarity for each item in this list to uncommon rank, must be a resource location name !")
            .comment("If any item is already defined in another list, it will use the highest rarity first !")
            .defineListAllowEmpty("item_rarity_uncommon_list", Collections.emptyList(), Config::validateItemName);
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_RARITY_RARE = BUILDER
            .comment("Define rarity for each item in this list to rare rank, must be a resource location name !")
            .comment("If any item is already defined in another list, it will use the highest rarity first !")
            .defineListAllowEmpty("item_rarity_rare_list", Collections.emptyList(), Config::validateItemName);
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_RARITY_EPIC = BUILDER
            .comment("Define rarity for each item in this list to epic rank, must be a resource location name !")
            .comment("If any item is already defined in another list, it will use the highest rarity first !")
            .defineListAllowEmpty("item_rarity_epic_list", Collections.emptyList(), Config::validateItemName);
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_RARITY_LEGENDARY = BUILDER
            .comment("Define rarity for each item in this list to legendary rank, must be a resource location name !")
            .comment("If any item is already defined in another list, it will use the highest rarity first !")
            .defineListAllowEmpty("item_rarity_legendary_list", Collections.emptyList(), Config::validateItemName);
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_RARITY_UNIQUE = BUILDER
            .comment("Define rarity for each item in this list to unique rank, must be a resource location name !")
            .comment("If any item is already defined in another list, it will use the highest rarity first !")
            .defineListAllowEmpty("item_rarity_unique_list", Collections.emptyList(), Config::validateItemName);
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_RARITY_MYTHIC = BUILDER
            .comment("Define rarity for each item in this list to mythic rank, must be a resource location name !")
            .comment("If any item is already defined in another list, it will use the highest rarity first !")
            .defineListAllowEmpty("item_rarity_mythic_list", Collections.emptyList(), Config::validateItemName);
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_RARITY_GOD = BUILDER
            .comment("Define rarity for each item in this list to god rank, must be a resource location name !")
            .comment("If any item is already defined in another list, it will use the highest rarity first !")
            .defineListAllowEmpty("item_rarity_god_list", Collections.emptyList(), Config::validateItemName);

    public static final ForgeConfigSpec CONFIG = BUILDER.build();

    public static Set<ResourceLocation> RARITY_COMMON_LIST;
    public static Set<ResourceLocation> RARITY_UNCOMMON_LIST;
    public static Set<ResourceLocation> RARITY_RARE_LIST;
    public static Set<ResourceLocation> RARITY_EPIC_LIST;
    public static Set<ResourceLocation> RARITY_LEGENDARY_LIST;
    public static Set<ResourceLocation> RARITY_UNIQUE_LIST;
    public static Set<ResourceLocation> RARITY_MYTHIC_LIST;
    public static Set<ResourceLocation> RARITY_GOD_LIST;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        RARITY_COMMON_LIST = ITEM_RARITY_COMMON.get().stream().map(ResourceLocation::new).collect(Collectors.toSet());
        RARITY_UNCOMMON_LIST = ITEM_RARITY_UNCOMMON.get().stream().map(ResourceLocation::new).collect(Collectors.toSet());
        RARITY_RARE_LIST = ITEM_RARITY_RARE.get().stream().map(ResourceLocation::new).collect(Collectors.toSet());
        RARITY_EPIC_LIST = ITEM_RARITY_EPIC.get().stream().map(ResourceLocation::new).collect(Collectors.toSet());
        RARITY_LEGENDARY_LIST = ITEM_RARITY_LEGENDARY.get().stream().map(ResourceLocation::new).collect(Collectors.toSet());
        RARITY_UNIQUE_LIST = ITEM_RARITY_UNIQUE.get().stream().map(ResourceLocation::new).collect(Collectors.toSet());
        RARITY_MYTHIC_LIST = ITEM_RARITY_MYTHIC.get().stream().map(ResourceLocation::new).collect(Collectors.toSet());
        RARITY_GOD_LIST = ITEM_RARITY_GOD.get().stream().map(ResourceLocation::new).collect(Collectors.toSet());
    }
}
