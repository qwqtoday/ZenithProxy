package com.zenith.command.impl;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.zenith.Proxy;
import com.zenith.command.Command;
import com.zenith.command.CommandContext;
import com.zenith.command.CommandUsage;

import static com.zenith.Shared.SYSTEM_DISCONNECT;

public class ReconnectCommand extends Command {
    @Override
    public CommandUsage commandUsage() {
        return CommandUsage.simple(
                "reconnect",
                "disconnect and reconnect the proxy client"
        );
    }

    @Override
    public LiteralArgumentBuilder<CommandContext> register() {
        return command("reconnect").executes(c -> {
            Proxy.getInstance().disconnect(SYSTEM_DISCONNECT);
            Proxy.getInstance().cancelAutoReconnect();
            Proxy.getInstance().connect();
        });
    }
}
