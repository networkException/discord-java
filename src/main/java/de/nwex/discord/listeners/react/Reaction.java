package de.nwex.discord.listeners.react;

import de.nwex.discord.snowflake.snowy.SnowyGenericMessageReactionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Reaction {

    private static final List<Reaction> reactions = new ArrayList<>();

    private final Function<SnowyGenericMessageReactionEvent, Boolean> filter;
    private final Consumer<SnowyGenericMessageReactionEvent> add;
    private final Consumer<SnowyGenericMessageReactionEvent> remove;

    public Reaction(Function<SnowyGenericMessageReactionEvent, Boolean> filter,
        Consumer<SnowyGenericMessageReactionEvent> add, Consumer<SnowyGenericMessageReactionEvent> remove) {
        this.filter = filter;
        this.add = add;
        this.remove = remove;
    }

    public static List<Reaction> getReactions() {
        return reactions;
    }

    public Function<SnowyGenericMessageReactionEvent, Boolean> getFilter() {
        return filter;
    }

    public Consumer<SnowyGenericMessageReactionEvent> getAdd() {
        return add;
    }

    public Consumer<SnowyGenericMessageReactionEvent> getRemove() {
        return remove;
    }
}
