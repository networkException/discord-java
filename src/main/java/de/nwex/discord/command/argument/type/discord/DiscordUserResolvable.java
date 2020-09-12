package de.nwex.discord.command.argument.type.discord;

import de.nwex.discord.Main;
import de.nwex.discord.command.argument.type.Resolvable;
import de.nwex.discord.command.argument.type.ResolvableType;
import net.dv8tion.jda.api.entities.User;

public class DiscordUserResolvable extends Resolvable<User> {

    public DiscordUserResolvable(String value, Type type) {
        super(value, type);
    }

    @Override
    public User resolve() {
        return switch ((Type) getType()) {
            case TAG -> Main.getClient().getUserByTag(getValue());
            case MENTION -> Main.getClient().retrieveUserById(getValue().substring(3, getValue().length() - 1)).complete();
            case SNOWFLAKE -> Main.getClient().retrieveUserById(getValue()).complete();
        };
    }

    public enum Type implements ResolvableType {
        TAG,
        SNOWFLAKE,
        MENTION;

        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }
}
