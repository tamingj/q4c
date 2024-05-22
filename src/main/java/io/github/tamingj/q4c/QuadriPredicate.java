package io.github.tamingj.q4c;

@FunctionalInterface
public interface QuadriPredicate<E1, E2, E3, E4> {
    boolean test(E1 element1, E2 element2, E3 element3, E4 element4);
}
