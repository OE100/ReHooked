package com.oe.rehooked.network.handlers;

import com.oe.rehooked.ReHookedMod;
import com.oe.rehooked.network.packets.client.CHookCapabilityPacket;
import com.oe.rehooked.network.packets.client.processing.CHookCapabilityProcessor;
import com.oe.rehooked.network.packets.server.SHookCapabilityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    
    private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
            new ResourceLocation(ReHookedMod.MOD_ID, "main"))
            .serverAcceptedVersions(s -> true)
            .clientAcceptedVersions(s -> true)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();
    
    public static void register() {
        ReHookedMod.LOGGER.debug("Packet handler register started...");
        
        INSTANCE.messageBuilder(SHookCapabilityPacket.class, NetworkDirection.PLAY_TO_SERVER.ordinal())
                .encoder(SHookCapabilityPacket::encode)
                .decoder(SHookCapabilityPacket::new)
                .consumerMainThread(SHookCapabilityPacket::handle)
                .add();

        ReHookedMod.LOGGER.debug("Registered ServerHookPacket");
        
        INSTANCE.messageBuilder(CHookCapabilityPacket.class, NetworkDirection.PLAY_TO_CLIENT.ordinal())
                .encoder(CHookCapabilityPacket::encode)
                .decoder(CHookCapabilityPacket::new)
                .consumerMainThread(CHookCapabilityProcessor::handle)
                .add();

        ReHookedMod.LOGGER.debug("Registered ClientHookPacket");
        
//        INSTANCE.messageBuilder(CPushPlayerPacket.class, NetworkDirection.PLAY_TO_CLIENT.ordinal())
//                .encoder(CPushPlayerPacket::encode)
//                .decoder(CPushPlayerPacket::new)
//                .consumerMainThread(CPushPlayerPacket::handle)
//                .add();
    }
    
    public static void sendToServer(Object msg) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), msg);
    }
    
    public static void sendToPlayer(Object msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }
    
    public static void sendToAllClients(Object msg) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }
}
