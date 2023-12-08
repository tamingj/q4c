package io.github.cbuschka.q4c;

@FunctionalInterface
public interface QuadriConsumer<T, U, V, W> {
    void accept(T t, U u, V v, W w);
}
