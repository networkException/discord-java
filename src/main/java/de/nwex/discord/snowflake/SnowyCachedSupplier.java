package de.nwex.discord.snowflake;

import java.util.function.Function;

public class SnowyCachedSupplier<T> extends SnowySupplier<T> {

    private T supplied;

    public SnowyCachedSupplier(String id, Function<Snowflake, T> object) {
        super(id, object);
    }

    public SnowyCachedSupplier(Long id, Function<Snowflake, T> object) {
        super(id, object);
    }

    public SnowyCachedSupplier(Snowflake id, Function<Snowflake, T> object) {
        super(id, object);
    }

    public T supply() {
        if (supplied == null) {
            supplied = getObject().apply(this);
        }

        return supplied;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
