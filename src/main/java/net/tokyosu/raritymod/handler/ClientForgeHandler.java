package net.tokyosu.raritymod.handler;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tokyosu.raritymod.RarityKeybindings;
import net.tokyosu.raritymod.RarityMod;
import net.tokyosu.raritymod.editor.network.NetworkHandler;
import net.tokyosu.raritymod.plugin.kubejs.RarityKubeJSScriptExporter;

@Mod.EventBusSubscriber(modid = RarityMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeHandler {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (RarityKeybindings.RARITY_EDITOR.consumeClick() && event.player != null && event.player.isCreative()) {
            NetworkHandler.sendOpenEditor();
        }
    }

    @SuppressWarnings("SpellCheckingInspection")
    @SubscribeEvent
    public static void onChatCommand(RegisterCommandsEvent event) {
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
