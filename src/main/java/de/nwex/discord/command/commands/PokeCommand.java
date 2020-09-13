package de.nwex.discord.command.commands;

import static de.nwex.discord.command.argument.DiscordUserArgumentType.getUser;
import static de.nwex.discord.command.argument.DiscordUserArgumentType.user;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import de.nwex.discord.command.Command;
import de.nwex.discord.command.argument.type.discord.DiscordUserResolvable;
import de.nwex.discord.config.Colors;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PokeCommand extends Command {

    @Override public void register(CommandDispatcher<MessageReceivedEvent> dispatcher) {
        dispatcher.register(
            literal("poke")
                .describes("pokes a member")
                .help(true)
                .then(argument("member", user())
                    .executes(this::executePoke))
                .executes(this::executeError)
        );
    }

    private int executePoke(CommandContext<MessageReceivedEvent> context) {
        DiscordUserResolvable resolvable = getUser(context, "member");
        User user = resolvable.resolve();

        context.getSource().getTextChannel().sendMessage(user.getAsMention()).queue();

        return 1;
    }

    private int executeError(CommandContext<MessageReceivedEvent> context) {
        context.getSource().getTextChannel().sendMessage(new EmbedBuilder()
            .setDescription("Please specify a user to poke")
            .setColor(Colors.red)
            .build()).queue();

        return -1;
    }
}
