package de.nwex.discord.snowflake;

import java.util.function.Function;

public class SnowySupplier<T> extends SnowyObject<Function<Snowflake, T>> {

    public SnowySupplier(String id, Function<Snowflake, T> object) {
        super(id, object);
    }

    public SnowySupplier(Long id, Function<Snowflake, T> object) {
        super(id, object);
    }

    public SnowySupplier(Snowflake id, Function<Snowflake, T> object) {
        super(id, object);
    }

    public T supply() {
        return getObject().apply(this);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
