package net.tokyosu.raritymod.editor.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import net.tokyosu.raritymod.editor.network.provider.EditorMenuProvider;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@SuppressWarnings("InstantiationOfUtilityClass")
public class EditorOpenPacket {
    public static void encode(@NotNull EditorOpenPacket msg, @NotNull FriendlyByteBuf buf) {
    }

    public static @NotNull EditorOpenPacket decode(@NotNull FriendlyByteBuf buf) {
        return new EditorOpenPacket();
    }

    public static void handle(@NotNull EditorOpenPacket msg, @NotNull Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                NetworkHooks.openScreen(player, new EditorMenuProvider());
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
