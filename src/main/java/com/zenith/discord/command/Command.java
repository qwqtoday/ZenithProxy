package com.zenith.discord.command;

import com.zenith.Proxy;
import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.discordjson.json.MessageCreateRequest;
import discord4j.rest.entity.RestChannel;
import discord4j.rest.util.MultipartRequest;

import java.util.Optional;

import static com.zenith.util.Constants.CONFIG;

public abstract class Command {
    final Proxy proxy;
    final String name;
    final String description;

    public Command(Proxy proxy, String name, String description) {
        this.proxy = proxy;
        this.name = name;
        this.description = description;
    }

    public abstract MultipartRequest<MessageCreateRequest> execute(MessageCreateEvent event, RestChannel restChannel);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Call this in execute for child classes to validate discrd user permissions
     * @param event
     */
    void validateUserHasAccountOwnerRole(MessageCreateEvent event) {
        Optional<String> roleContainsOptional = event.getMember().get().getRoleIds().stream()
                .map(Snowflake::asString)
                .filter(roleId -> roleId.equals(CONFIG.discord.accountOwnerRoleId))
                .findAny();
        if (!roleContainsOptional.isPresent()) {
            throw new RuntimeException("User: " + event.getMember().get().getUsername() + "#" + event.getMember().get().getDiscriminator() + " is not an account owner!");
        }
    }
}
