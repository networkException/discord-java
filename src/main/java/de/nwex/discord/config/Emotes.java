package de.nwex.discord.config;

import de.nwex.discord.Main;
import de.nwex.discord.snowflake.Snowflake;
import de.nwex.discord.snowflake.SnowyCachedSupplier;
import java.util.function.Function;
import net.dv8tion.jda.api.entities.Emote;

public class Emotes {

    public static final Function<Snowflake, SnowyCachedSupplier<Emote>> emoteSupplier = (snowflake) -> new SnowyCachedSupplier<>(
        snowflake, (s) -> Main.getClient().getEmoteById(snowflake.getValue())
    );

    public static final SnowyCachedSupplier<Emote> pepeD = emoteSupplier.apply(new Snowflake(""));
}
