package io.github.cbuschka.q4c;

@FunctionalInterface
public interface QuadriPredicate<Element1, Element2, Element3, Element4> {

    boolean test(Element1 element1, Element2 element2, Element3 element3, Element4 element4);
}
