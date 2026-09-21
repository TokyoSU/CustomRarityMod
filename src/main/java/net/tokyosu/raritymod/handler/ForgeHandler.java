package net.tokyosu.raritymod.handler;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tokyosu.raritymod.RarityMod;
import net.tokyosu.raritymod.plugin.kubejs.RarityKubeJSScriptExporter;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("SpellCheckingInspection")
@Mod.EventBusSubscriber(modid = RarityMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeHandler {
    @SubscribeEvent
    public static void onChatCommand(@NotNull RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("rarityjs").requires(source -> source.hasPermission(1))
                        .then(Commands.literal("export").then(Commands.argument("filename", StringArgumentType.string()).executes(context -> {
                            String filename = StringArgumentType.getString(context, "filename");
                            RarityKubeJSScriptExporter.exportToJs(filename);
                            context.getSource().sendSuccess(() -> Component.literal("Exported rarity config to " + filename + ".js"), true);
                            return 1;
                        })))

        );
    }
}
