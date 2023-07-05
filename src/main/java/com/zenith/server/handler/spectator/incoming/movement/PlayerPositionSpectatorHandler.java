package com.zenith.server.handler.spectator.incoming.movement;

import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionPacket;
import com.zenith.feature.handler.HandlerRegistry;
import com.zenith.server.ServerConnection;
import com.zenith.spectator.SpectatorHelper;
import lombok.NonNull;

public class PlayerPositionSpectatorHandler implements HandlerRegistry.IncomingHandler<ClientPlayerPositionPacket, ServerConnection> {
    @Override
    public boolean apply(@NonNull ClientPlayerPositionPacket packet, @NonNull ServerConnection session) {
        session.getSpectatorPlayerCache()
                .setX(packet.getX())
                .setY(packet.getY())
                .setZ(packet.getZ());
        PlayerPositionRotationSpectatorHandler.updateSpectatorPosition(session);
        SpectatorHelper.checkSpectatorPositionOutOfRender(session);
        return false;
    }

    @Override
    public Class<ClientPlayerPositionPacket> getPacketClass() {
        return ClientPlayerPositionPacket.class;
    }
}
