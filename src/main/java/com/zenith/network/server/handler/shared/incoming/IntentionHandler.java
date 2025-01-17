package com.zenith.network.server.handler.shared.incoming;

import com.zenith.feature.ratelimiter.RateLimiter;
import com.zenith.network.registry.PacketHandler;
import com.zenith.network.server.ServerSession;
import org.geysermc.mcprotocollib.protocol.MinecraftProtocol;
import org.geysermc.mcprotocollib.protocol.data.ProtocolState;
import org.geysermc.mcprotocollib.protocol.packet.handshake.serverbound.ClientIntentionPacket;

import static com.zenith.Shared.CONFIG;

public class IntentionHandler implements PacketHandler<ClientIntentionPacket, ServerSession> {
    private final RateLimiter rateLimiter = new RateLimiter(CONFIG.server.rateLimiter.rateLimitSeconds);

    @Override
    public ClientIntentionPacket apply(final ClientIntentionPacket packet, final ServerSession session) {
        MinecraftProtocol protocol = session.getPacketProtocol();
        switch (packet.getIntent()) {
            case STATUS -> protocol.setState(ProtocolState.STATUS);
            case LOGIN -> {
                protocol.setState(ProtocolState.LOGIN);
                if (CONFIG.server.rateLimiter.enabled && rateLimiter.isRateLimited(session)) {
                    session.disconnect("Login Rate Limited.");
                    return null;
                }
                if (packet.getProtocolVersion() > protocol.getCodec().getProtocolVersion()) {
                    session.disconnect("Outdated server! I'm still on " + protocol.getCodec()
                        .getMinecraftVersion() + ".");
                } else if (packet.getProtocolVersion() < protocol.getCodec().getProtocolVersion()) {
                    session.disconnect("Outdated client! Please use " + protocol.getCodec()
                        .getMinecraftVersion() + ".");
                }
            }
            default -> session.disconnect("Invalid client intention: " + packet.getIntent());
        }
        session.setProtocolVersion(packet.getProtocolVersion());
        return null;
    }
}
