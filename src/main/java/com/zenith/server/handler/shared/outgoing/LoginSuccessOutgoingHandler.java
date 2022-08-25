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

package com.zenith.server.handler.shared.outgoing;

import com.github.steveice10.mc.protocol.packet.login.server.LoginSuccessPacket;
import com.zenith.server.ServerConnection;
import com.zenith.util.Wait;
import com.zenith.util.handler.HandlerRegistry;
import lombok.NonNull;

import static com.zenith.util.Constants.*;
import static java.util.Objects.isNull;

/**
 * @author DaPorkchop_
 */
public class LoginSuccessOutgoingHandler implements HandlerRegistry.OutgoingHandler<LoginSuccessPacket, ServerConnection> {
    @Override
    public LoginSuccessPacket apply(@NonNull LoginSuccessPacket packet, @NonNull ServerConnection session) {
        // profile could be null at this point?
        int tryCount = 0;
        while (tryCount < 3 && CACHE.getProfileCache().getProfile() == null) {
            Wait.waitALittleMs(500);
            tryCount++;
        }
        if (CACHE.getProfileCache().getProfile() == null) {
            session.disconnect(MANUAL_DISCONNECT);
            return null;
        } else {
            SERVER_LOG.debug("User UUID: %s\nBot UUID: %s", packet.getProfile().getId().toString(), CACHE.getProfileCache().getProfile().getId().toString());
            session.getProfileCache().setProfile(packet.getProfile());
            if (isNull(session.getProxy().getCurrentPlayer().get()))
            {
                return new LoginSuccessPacket(CACHE.getProfileCache().getProfile());
            } else {
                return new LoginSuccessPacket(session.getProfileCache().getProfile());
            }
        }
    }

    @Override
    public Class<LoginSuccessPacket> getPacketClass() {
        return LoginSuccessPacket.class;
    }
}
