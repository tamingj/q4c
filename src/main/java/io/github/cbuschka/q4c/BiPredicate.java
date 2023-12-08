package io.github.cbuschka.q4c;

@FunctionalInterface
public interface BiPredicate<E1, E2> {

    boolean test(E1 element1, E2 element2);
}
