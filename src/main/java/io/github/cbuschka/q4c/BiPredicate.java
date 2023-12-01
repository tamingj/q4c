package io.github.cbuschka.q4c;

@FunctionalInterface
public interface BiPredicate<Element1, Element2> {

    boolean test(Element1 element1, Element2 element2);
}
