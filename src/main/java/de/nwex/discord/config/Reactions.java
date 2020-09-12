package de.nwex.discord.config;

import de.nwex.discord.config.Roles.RoleReactions;
import de.nwex.discord.listeners.react.Reaction;
import de.nwex.discord.snowflake.Snowflake;
import de.nwex.discord.snowflake.SnowySupplier;
import de.nwex.discord.snowflake.snowy.SnowyGenericMessageReactionEvent;
import de.nwex.discord.util.TriFunction;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.dv8tion.jda.api.entities.Role;

public class Reactions {

    private static final BiFunction<Snowflake, SnowyGenericMessageReactionEvent, Boolean> messageFilter =
        (snowflake, event) -> event.getSnowyMessage().equals(snowflake);

    private static final BiFunction<String, SnowyGenericMessageReactionEvent, Boolean> emoteFilter =
        (emoteName, event) -> event.getReactionEmote().getEmoji().equals(emoteName);

    private static final TriFunction<Snowflake, String, SnowyGenericMessageReactionEvent, Boolean> messageEmoteFilter =
        (snowflake, emoteName, event) -> messageFilter.apply(snowflake, event) && emoteFilter.apply(emoteName, event);

    private static final BiConsumer<SnowySupplier<Role>, Consumer<Role>> processRoleIfPresent =
        (role, roleConsumer) -> {
            Role suppliedRole = role.supply();

            if (suppliedRole != null) {
                roleConsumer.accept(suppliedRole);
            }
        };

    public static void register() {
        RoleReactions.asList().forEach(role -> Reaction.getReactions().add(new Reaction(
            event -> messageFilter.apply(role.getMessage(), event),
            event -> processRoleIfPresent.accept(Roles.roleSupplier.apply(role.getRole()),
                fetchedRole -> Channels.guild.supply().addRoleToMember(event.getUserIdLong(), fetchedRole).complete()),
            event -> processRoleIfPresent.accept(Roles.roleSupplier.apply(role.getRole()),
                fetchedRole -> Channels.guild.supply().removeRoleFromMember(event.getUserIdLong(), fetchedRole).complete())
        )));
    }
}
