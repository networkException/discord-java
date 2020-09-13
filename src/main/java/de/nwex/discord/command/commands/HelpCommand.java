package de.nwex.discord.command.commands;

import static com.mojang.brigadier.arguments.StringArgumentType.word;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;
import de.nwex.discord.command.Command;
import de.nwex.discord.config.Colors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelpCommand extends Command {

    @Override
    public void register(CommandDispatcher<MessageReceivedEvent> dispatcher) {
        dispatcher.register(
            literal("help")
                .describes("a list of all commands you can use or information about a command")
                .help(false)
                .then(argument("command", word())
                    .executes(context -> execute(context, dispatcher, StringArgumentType.getString(context, "command"))))
                .executes(context -> execute(context, dispatcher))
        );
    }

    private int execute(CommandContext<MessageReceivedEvent> context, CommandDispatcher<MessageReceivedEvent> dispatcher, String... commands) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Help");
        builder.setColor(Colors.orange);

        List<String> commandList = Arrays.asList(commands);

        dispatcher.getRoot().getChildren().stream()
            .filter(command -> commandList.size() != 0 || command.isHelp())
            .filter(command -> commandList.size() == 0 || commandList.contains(command.getName()))
            .forEach(command -> builder.addField(
                String.format("!%s • %s", command.getName(), command.getDescription()),
                String.join("\n", formatUsage(context.getSource(), dispatcher, command)),
                false
            ));

        context.getSource().getTextChannel().sendMessage(builder.build()).queue();

        return 1;
    }

    private String formatUsage(MessageReceivedEvent source, CommandDispatcher<MessageReceivedEvent> dispatcher,
        CommandNode<MessageReceivedEvent> command) {
        List<String> formatted = new ArrayList<>();

        if (command.getUnmet() != null) {
            formatted.add(String.format("*%s*", command.getUnmet()));
        }

        formatted.addAll(Arrays.stream(dispatcher.getAllUsage(command, source, false))
            .map(arguments -> String.format("!%s %s", command.getName(), arguments))
            .collect(Collectors.toList()));

        return String.join("\n", formatted);
    }
}
