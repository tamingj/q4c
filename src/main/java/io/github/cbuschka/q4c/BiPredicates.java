package io.github.cbuschka.q4c;

public class BiPredicates {
    private BiPredicates() {
    }

    public static <E1, E2> BiPredicate<E1, E2> matchAll() {
        return (a, b) -> true;
    }
}
