package io.github.cbuschka.q4c;

@FunctionalInterface
public interface TriFunction<T, U, V, W> {
    W apply(T t, U u, V v);
}
