package io.github.cbuschka.q4c;

@FunctionalInterface
public interface TriPredicate<Element1, Element2, Element3> {

    boolean test(Element1 element1, Element2 element2, Element3 element3);
}
