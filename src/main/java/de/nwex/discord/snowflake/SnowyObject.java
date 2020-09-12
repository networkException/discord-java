package de.nwex.discord.snowflake;

public class SnowyObject<T> extends Snowflake {

    private final T object;

    public SnowyObject(String id, T object) {
        super(id);

        this.object = object;
    }

    public SnowyObject(Long id, T object) {
        super(id);

        this.object = object;
    }

    public SnowyObject(Snowflake id, T object) {
        super(id.getValue());

        this.object = object;
    }

    public T getObject() {
        return object;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
