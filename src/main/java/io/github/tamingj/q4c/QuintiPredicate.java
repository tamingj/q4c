package io.github.tamingj.q4c;

@FunctionalInterface
public interface QuintiPredicate<E1, E2, E3, E4, E5> {
    boolean test(E1 element1, E2 element2, E3 element3, E4 element4, E5 element5);
}
