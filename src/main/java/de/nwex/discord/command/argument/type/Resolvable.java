package de.nwex.discord.command.argument.type;

public abstract class Resolvable<T> {

    private final ResolvableType type;
    private final String value;

    public Resolvable(String value, ResolvableType type) {
        this.type = type;
        this.value = value;
    }

    public abstract T resolve();

    public String getValue() {
        return value;
    }

    public ResolvableType getType() {
        return type;
    }
}
