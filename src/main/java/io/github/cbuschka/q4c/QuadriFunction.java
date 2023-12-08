package io.github.cbuschka.q4c;

@FunctionalInterface
public interface QuadriFunction<E1, E2, E3, E4, R> {
    R apply(E1 element1, E2 element2, E3 element3, E4 element4);
}
