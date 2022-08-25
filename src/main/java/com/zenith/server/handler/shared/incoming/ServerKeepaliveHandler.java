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

package com.zenith.server.handler.shared.incoming;

import com.github.steveice10.mc.protocol.packet.ingame.client.ClientKeepAlivePacket;
import lombok.NonNull;
import com.zenith.server.ServerConnection;
import com.zenith.util.handler.HandlerRegistry;

/**
 * @author DaPorkchop_
 */
public class ServerKeepaliveHandler implements HandlerRegistry.IncomingHandler<ClientKeepAlivePacket, ServerConnection> {
    @Override
    public boolean apply(@NonNull ClientKeepAlivePacket packet, @NonNull ServerConnection session) {
        return false;
    }

    @Override
    public Class<ClientKeepAlivePacket> getPacketClass() {
        return ClientKeepAlivePacket.class;
    }
}
