package de.nwex.discord.config;

import de.nwex.discord.Main;
import de.nwex.discord.snowflake.Snowflake;
import de.nwex.discord.snowflake.SnowyCachedSupplier;
import java.util.function.Function;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class Channels {

    public static final Function<Snowflake, SnowyCachedSupplier<Guild>> guildSupplier = (snowflake) -> new SnowyCachedSupplier<>(
        snowflake, (s) -> Main.getClient().getGuildById(s.getValue())
    );

    public static final Function<Snowflake, SnowyCachedSupplier<TextChannel>> textChannelSupplier = (snowflake) -> new SnowyCachedSupplier<>(
        snowflake, (s) -> Main.getClient().getTextChannelById(s.getValue())
    );

    public static final Function<Snowflake, SnowyCachedSupplier<Category>> categorySupplier = (snowflake) -> new SnowyCachedSupplier<>(
        snowflake, (s) -> Main.getClient().getCategoryById(s.getValue())
    );

    public static final SnowyCachedSupplier<Guild> guild = guildSupplier.apply(new Snowflake(""));
    public static final SnowyCachedSupplier<TextChannel> general = textChannelSupplier.apply(new Snowflake(""));
}
