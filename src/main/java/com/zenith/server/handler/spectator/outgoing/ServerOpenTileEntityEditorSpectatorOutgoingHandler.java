package com.zenith.server.handler.spectator.outgoing;

import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerOpenTileEntityEditorPacket;
import com.zenith.server.ServerConnection;
import com.zenith.util.handler.HandlerRegistry;

public class ServerOpenTileEntityEditorSpectatorOutgoingHandler implements HandlerRegistry.OutgoingHandler<ServerOpenTileEntityEditorPacket, ServerConnection> {
    @Override
    public ServerOpenTileEntityEditorPacket apply(ServerOpenTileEntityEditorPacket packet, ServerConnection session) {
        return null;
    }

    @Override
    public Class<ServerOpenTileEntityEditorPacket> getPacketClass() {
        return ServerOpenTileEntityEditorPacket.class;
    }
}
