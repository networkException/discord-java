package de.nwex.discord.snowflake.snowy;

import de.nwex.discord.snowflake.Snowflake;
import de.nwex.discord.snowflake.SnowyObject;
import java.util.Objects;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;

public class SnowyGenericMessageReactionEvent extends GenericMessageReactionEvent {

    private final Snowflake snowyMessage;
    private final SnowyObject<User> snowyUser;
    private final SnowyObject<Member> snowyMember;
    private final SnowyObject<MessageReaction> snowyMessageReaction;

    public SnowyGenericMessageReactionEvent(GenericMessageReactionEvent event) {
        super(event.getJDA(), event.getResponseNumber(), Objects.requireNonNull(event.getUser()), event.getMember(),
            event.getReaction(), event.getUserIdLong());

        snowyUser = new SnowyObject<>(getUserId(), event.getUser());
        snowyMember = new SnowyObject<>(getUserId(), member);
        snowyMessageReaction = new SnowyObject<>(getMessageId(), getReaction());
        snowyMessage = new Snowflake(event.getMessageId());
    }

    public SnowyObject<User> getSnowyUser() {
        return snowyUser;
    }

    public SnowyObject<Member> getSnowyMember() {
        return snowyMember;
    }

    public SnowyObject<MessageReaction> getSnowyMessageReaction() {
        return snowyMessageReaction;
    }

    public Snowflake getMessageSnowflake() {
        return snowyMessageReaction;
    }

    public Snowflake getSnowyMessage() {
        return snowyMessage;
    }
}
