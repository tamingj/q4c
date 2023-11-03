package io.github.cbuschka.objset;

@FunctionalInterface
public interface BiPredicate<Element1, Element2> {

    boolean test(Element1 element1, Element2 element2);
}
