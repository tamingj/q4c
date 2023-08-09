package io.github.cbuschka.objset;

public record Triple<Element1, Element2, Element3>(Element1 element1, Element2 element2,
                                                   Element3 element3) {
    public static <Element1, Element2, Element3> Triple<Element1, Element2, Element3> of(Element1 element1, Element2 element2, Element3 element3) {
        return new Triple<>(element1, element2, element3);
    }
}
