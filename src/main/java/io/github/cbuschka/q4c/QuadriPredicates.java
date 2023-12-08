package io.github.cbuschka.q4c;

public class QuadriPredicates {
    private QuadriPredicates() {
    }

    public static <E1, E2, E3, E4> QuadriPredicate<E1, E2, E3, E4> matchAll() {
        return (a, b, c, d) -> true;
    }
}
