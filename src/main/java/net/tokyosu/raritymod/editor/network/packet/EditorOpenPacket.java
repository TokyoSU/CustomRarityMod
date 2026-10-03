package net.tokyosu.raritymod.editor.network.packet;

import net.tokyosu.apocalypselib.utils.NetworkUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import net.tokyosu.raritymod.editor.network.provider.EditorMenuProvider;
import org.jetbrains.annotations.NotNull;
import java.util.function.Supplier;

public class EditorOpenPacket {
    public static void encode(@NotNull EditorOpenPacket msg, @NotNull FriendlyByteBuf buf) {
    }

    public static @NotNull EditorOpenPacket decode(@NotNull FriendlyByteBuf buf) {
        return new EditorOpenPacket();
    }

    public static void handle(@NotNull EditorOpenPacket msg, @NotNull Supplier<NetworkEvent.Context> ctx) {
        NetworkUtils.handleServer(ctx, player -> {
            if (player.isCreative()) {
                NetworkHooks.openScreen(player, new EditorMenuProvider());
            }
        });
    }
}
