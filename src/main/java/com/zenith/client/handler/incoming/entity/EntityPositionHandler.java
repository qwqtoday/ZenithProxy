package com.zenith.client.handler.incoming.entity;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityPositionPacket;
import com.zenith.cache.data.entity.Entity;
import com.zenith.client.ClientSession;
import com.zenith.feature.handler.HandlerRegistry;
import lombok.NonNull;

import static com.zenith.Shared.CACHE;
import static java.util.Objects.isNull;

public class EntityPositionHandler implements HandlerRegistry.AsyncIncomingHandler<ServerEntityPositionPacket, ClientSession> {
    @Override
    public boolean applyAsync(@NonNull ServerEntityPositionPacket packet, @NonNull ClientSession session) {
        Entity entity = CACHE.getEntityCache().get(packet.getEntityId());
        if (isNull(entity)) return false;
        entity.setX(entity.getX() + packet.getMovementX())
                .setY(entity.getY() + packet.getMovementY())
                .setZ(entity.getZ() + packet.getMovementZ());
        EntityPositionRotationHandler.trackPlayerVisualRangePosition(entity);
        return true;
    }

    @Override
    public Class<ServerEntityPositionPacket> getPacketClass() {
        return ServerEntityPositionPacket.class;
    }
}
