package io.github.cbuschka.objset;

import io.github.cbuschka.objset.impl.UniSelectImpl;

public class ObjectQuery {
    public static <Element1> FilterableUniSelect<Element1> selectFrom(Iterable<Element1> elements) {
        return new UniSelectImpl<>(elements);
    }
}
