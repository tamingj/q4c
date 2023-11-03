package io.github.cbuschka.objset;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Pair<Element1, Element2> {
    private final Element1 element1;
    private final Element2 element2;

    public static <Element1, Element2> Pair<Element1, Element2> of(Element1 element1, Element2 element2) {
        return new Pair<>(element1, element2);
    }

    public Element1 element1() {
        return element1;
    }

    public Element2 element2() {
        return element2;
    }
}
