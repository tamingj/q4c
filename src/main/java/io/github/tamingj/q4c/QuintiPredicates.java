package io.github.tamingj.q4c;

public class QuintiPredicates {
    private QuintiPredicates() {
    }

    public static <E1, E2, E3, E4, E5> QuintiPredicate<E1, E2, E3, E4, E5> matchAll() {
        return (a, b, c, d, e) -> true;
    }
}
