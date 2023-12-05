package io.github.cbuschka.q4c;

public class BiPredicates {
    private BiPredicates() {
    }

    public static <Element1, Element2> BiPredicate<Element1, Element2> matchAll() {
        return (a, b) -> true;
    }
}
