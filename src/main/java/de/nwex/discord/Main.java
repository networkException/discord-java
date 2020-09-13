package de.nwex.discord;

import com.mojang.brigadier.CommandDispatcher;
import de.nwex.discord.command.Command;
import de.nwex.discord.config.Reactions;
import de.nwex.discord.config.Tokens;
import java.lang.reflect.InvocationTargetException;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.reflections.Reflections;

public class Main {

    private static final CommandDispatcher<MessageReceivedEvent> commandDispatcher = new CommandDispatcher<>();
    private static JDA client;

    public static void main(String[] args) {
        JDABuilder builder = JDABuilder.createDefault(Tokens.discord);

        builder.setBulkDeleteSplittingEnabled(false);
        builder.setActivity(Activity.playing("with github.com/networkException/discord-java"));

        // TODO: Add valid snowflakes to RoleReactions
        // Reactions.register();

        new Reflections("de.nwex.discord.command.commands")
            .getSubTypesOf(Command.class)
            .forEach(command -> {
                try {
                    ((Command) command.getConstructors()[0].newInstance()).register(commandDispatcher);

                    System.out.println("Command '" + command.getName() + "' initialised");
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    System.err.println("Command '" + command.getName() + "' could not be initialised");
                }
            });

        new Reflections("de.nwex.discord.listeners")
            .getSubTypesOf(ListenerAdapter.class)
            .forEach(listener -> {
                try {
                    builder.addEventListeners(listener.getConstructors()[0].newInstance());

                    System.out.println("Listener '" + listener.getName() + "' initialised");
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    System.err.println("Listener '" + listener.getName() + "' could not be initialised");
                }
            });

        try {
            client = builder.build().awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("JDA is ready");
    }

    public static JDA getClient() {
        return client;
    }

    public static CommandDispatcher<MessageReceivedEvent> getCommandDispatcher() {
        return commandDispatcher;
    }
}
