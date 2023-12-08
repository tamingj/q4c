package io.github.cbuschka.q4c;

@FunctionalInterface
public interface QuadriFunction<T, U, V, W, X> {
    X apply(T t, U u, V v, W w);
}
