package net.tokyosu.raritymod.editor.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.tokyosu.raritymod.RarityMod;
import net.tokyosu.raritymod.editor.network.packet.EditorOpenPacket;

@SuppressWarnings("InstantiationOfUtilityClass")
public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    private static final SimpleChannel NETWORK_CHANNEL = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(RarityMod.MOD_ID, "rarity_network"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        NETWORK_CHANNEL.registerMessage(0, EditorOpenPacket.class, EditorOpenPacket::encode, EditorOpenPacket::decode, EditorOpenPacket::handle);
    }

    public static void sendOpenEditor() {
        NETWORK_CHANNEL.sendToServer(new EditorOpenPacket());
    }
}
