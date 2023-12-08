package io.github.cbuschka.q4c;

public class TriPredicates {
    private TriPredicates() {
    }

    public static <E1, E2, E3> TriPredicate<E1, E2, E3> matchAll() {
        return (a, b, c) -> true;
    }
}
