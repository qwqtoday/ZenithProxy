/*
 * Adapted from The MIT License (MIT)
 *
 * Copyright (c) 2016-2020 DaPorkchop_
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 *
 * Any persons and/or organizations using this software must include the above copyright notice and this permission notice,
 * provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.zenith.client.handler.incoming;

import com.github.steveice10.mc.protocol.packet.ingame.server.ServerStatisticsPacket;
import lombok.NonNull;
import net.daporkchop.lib.unsafe.PUnsafe;
import com.zenith.client.ClientSession;
import com.zenith.util.handler.HandlerRegistry;

import java.util.HashMap;

import static com.zenith.util.Constants.*;

/**
 * @author DaPorkchop_
 */
public class StatisticsHandler implements HandlerRegistry.AsyncIncomingHandler<ServerStatisticsPacket, ClientSession> {
    protected static final long PACKET_STATISTICS_OFFSET = PUnsafe.pork_getOffset(ServerStatisticsPacket.class, "statistics");

    @Override
    public boolean applyAsync(@NonNull ServerStatisticsPacket packet, @NonNull ClientSession session) {
        CACHE.getStatsCache().getStatistics().putAll(packet.getStatistics()); //cache all locally
        PUnsafe.putObject(packet, PACKET_STATISTICS_OFFSET, new HashMap<>(CACHE.getStatsCache().getStatistics())); //replace statistics packet with copy of local
        return true;
    }

    @Override
    public Class<ServerStatisticsPacket> getPacketClass() {
        return ServerStatisticsPacket.class;
    }
}
