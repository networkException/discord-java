package de.nwex.discord.config;

import de.nwex.discord.snowflake.Snowflake;
import de.nwex.discord.snowflake.SnowyCachedSupplier;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import net.dv8tion.jda.api.entities.Role;

public class Roles {

    public static final Function<Snowflake, SnowyCachedSupplier<Role>> roleSupplier = (snowflake) -> new SnowyCachedSupplier<>(
        snowflake, (s) -> Channels.guild.supply().getRoleById(s.getValue())
    );

    public static final SnowyCachedSupplier<Role> user = roleSupplier.apply(new Snowflake(""));

    public enum RoleReactions {
        ANNOUNCEMENTS("", "");

        private final Snowflake message;
        private final Snowflake role;

        RoleReactions(String message, String role) {
            this.message = new Snowflake(message);
            this.role = new Snowflake(role);
        }

        public static List<RoleReactions> asList() {
            return Arrays.asList(values());
        }

        public Snowflake getMessage() {
            return message;
        }

        public Snowflake getRole() {
            return role;
        }
    }
}
