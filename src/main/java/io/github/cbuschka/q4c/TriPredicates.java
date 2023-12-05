package io.github.cbuschka.q4c;

public class TriPredicates {
    private TriPredicates() {
    }

    public static <Element1, Element2, Element3> TriPredicate<Element1, Element2, Element3> matchAll() {
        return (a, b, c) -> true;
    }
}
