package com.zenith.server.handler.player.incoming.movement;

import com.github.steveice10.mc.protocol.data.game.entity.player.Animation;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerSwingArmPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityAnimationPacket;
import com.zenith.feature.handler.HandlerRegistry;
import com.zenith.server.ServerConnection;

import static com.zenith.Shared.CACHE;

public class PlayerSwingArmPacketHandler implements HandlerRegistry.AsyncIncomingHandler<ClientPlayerSwingArmPacket, ServerConnection> {
    @Override
    public boolean applyAsync(ClientPlayerSwingArmPacket packet, ServerConnection session) {
        session.getProxy().getSpectatorConnections().forEach(connection -> {
            connection.send(new ServerEntityAnimationPacket(
                    CACHE.getPlayerCache().getEntityId(),
                    Animation.SWING_ARM
            ));
        });
        return true;
    }

    @Override
    public Class<ClientPlayerSwingArmPacket> getPacketClass() {
        return ClientPlayerSwingArmPacket.class;
    }
}
