package de.nwex.discord.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import de.nwex.discord.command.argument.type.discord.DiscordUserResolvable;
import de.nwex.discord.command.argument.type.discord.DiscordUserResolvable.Type;
import de.nwex.discord.snowflake.Snowflake;
import java.util.Arrays;
import java.util.Collection;
import org.apache.commons.lang3.StringUtils;

public class DiscordUserArgumentType implements ArgumentType<DiscordUserResolvable> {

    private static final Collection<String> EXAMPLES = Arrays.asList("networkException#0001", "236617655858102273", "@<236617655858102273>");

    public static DiscordUserArgumentType user() {
        return new DiscordUserArgumentType();
    }

    public static DiscordUserResolvable getUser(final CommandContext<?> context, final String name) {
        return context.getArgument(name, DiscordUserResolvable.class);
    }

    @Override
    public DiscordUserResolvable parse(final StringReader reader) throws CommandSyntaxException {
        String result = reader.readString();

        if (result.matches("(.{2,32})#(\\d{4})")) {
            return new DiscordUserResolvable(result, Type.TAG);
        } else if (StringUtils.isNumeric(result) && Snowflake.validate(Long.parseLong(result))) {
            return new DiscordUserResolvable(result, Type.SNOWFLAKE);
        } else if (result.startsWith("<@!") && result.endsWith(">") && result.length() > 5 && StringUtils
            .isNumeric(result.substring(3, result.length() - 1)) && Snowflake.validate(Long.parseLong(result.substring(3, result.length() - 1)))) {
            return new DiscordUserResolvable(result, Type.MENTION);
        } else {
            throw new CommandSyntaxException(new SimpleCommandExceptionType(() -> "Input does not match a Discord user resolvable"),
                () -> "Input does not match a Discord user resolvable");
        }
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public String toString() {
        return "user()";
    }
}
