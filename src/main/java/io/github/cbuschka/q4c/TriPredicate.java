package io.github.cbuschka.q4c;

@FunctionalInterface
public interface TriPredicate<E1, E2, E3> {
    boolean test(E1 element1, E2 element2, E3 element3);
}
