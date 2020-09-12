package de.nwex.discord.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import de.nwex.discord.command.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TestCommand extends Command {

    @Override public void register(CommandDispatcher<MessageReceivedEvent> dispatcher) {
        dispatcher.register(
            literal("help")
                .executes(context -> {
                    context.getSource().getMessage().getTextChannel().sendMessage("a").complete();
                    return 1;
                })
        );
    }
}
