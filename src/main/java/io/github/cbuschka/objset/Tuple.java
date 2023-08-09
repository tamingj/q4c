package io.github.cbuschka.objset;

public record Tuple<Element1, Element2>(Element1 element1, Element2 element2) {
    public static <Element1, Element2> Tuple<Element1, Element2> of(Element1 element1, Element2 element2) {
        return new Tuple<>(element1, element2);
    }
}
