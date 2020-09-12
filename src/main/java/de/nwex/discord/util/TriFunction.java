package de.nwex.discord.util;

@FunctionalInterface
public interface TriFunction<T, E, U, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param e the second function argument
     * @param u the third function argument
     * @return the function result
     */
    R apply(T t, E e, U u);
}
