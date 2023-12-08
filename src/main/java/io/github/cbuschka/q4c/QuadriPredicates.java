package io.github.cbuschka.q4c;

public class QuadriPredicates {
    private QuadriPredicates() {
    }

    public static <Element1, Element2, Element3, Element4> QuadriPredicate<Element1, Element2, Element3, Element4> matchAll() {
        return (a, b, c, d) -> true;
    }
}
