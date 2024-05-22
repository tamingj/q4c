package io.github.tamingj.q4c;

@FunctionalInterface
public interface TriFunction<E1, E2, E3, R> {
    R apply(E1 element1, E2 element2, E3 element3);
}
