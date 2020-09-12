package de.nwex.discord.listeners;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.nwex.discord.Main;
import de.nwex.discord.config.Colors;
import de.nwex.discord.config.Tokens;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    private final CommandDispatcher<MessageReceivedEvent> commandDispatcher = Main.getCommandDispatcher();

    @Override public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.getAuthor().getIdLong() == event.getJDA().getSelfUser().getIdLong()) {
            return;
        }

        if (event.getMessage().getContentDisplay().trim().startsWith(Tokens.commandPrefix)) {
            ParseResults<MessageReceivedEvent> parseResults = commandDispatcher.parse(event.getMessage().getContentRaw().substring(1), event);

            List<String> unmet = parseResults.getContext().getNodes().stream()
                .filter(command -> !command.getNode().canUse(event))
                .map(command -> command.getNode().getUnmet())
                .collect(Collectors.toList());

            if (unmet.size() != 0) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Unmet requirement" + (unmet.size() != 1 ? "s" : ""));
                builder.setColor(Colors.red);
                builder.setDescription(String.join("\n", unmet));

                event.getTextChannel().sendMessage(builder.build()).queue();

                return;
            }

            try {
                commandDispatcher.execute(parseResults);
            } catch (CommandSyntaxException e) {
                if (e.getCursor() != 0) {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle(e.getType().toString());
                    builder.setColor(Colors.red);
                    builder.addField(
                        e.getMessage(),
                        Arrays.stream(commandDispatcher.getAllUsage(parseResults.getContext().getNodes().get(0).getNode(), event, false))
                            .map(usage -> String.format(
                                "!%s %s",
                                parseResults.getContext().getNodes().get(0).getNode().getName(),
                                usage
                            )).collect(Collectors.joining("\n")),
                        false
                    );

                    event.getTextChannel().sendMessage(builder.build()).queue();
                }
            }
        }
    }
}
