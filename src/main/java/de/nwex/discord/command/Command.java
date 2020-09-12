package de.nwex.discord.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import com.mojang.brigadier.CommandDispatcher;

public abstract class Command {

    public static LiteralArgumentBuilder<MessageReceivedEvent> literal(String string) {
        return LiteralArgumentBuilder.literal(string);
    }

    public static <T> RequiredArgumentBuilder<MessageReceivedEvent, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    public abstract void register(CommandDispatcher<MessageReceivedEvent> dispatcher);
}

