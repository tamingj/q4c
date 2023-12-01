package io.github.cbuschka.q4c;

import io.github.cbuschka.q4c.impl.UniSelectImpl;

import java.util.function.Supplier;

public class Queries {
    public static <Element1> FilterableUniSelect<Element1> selectFrom(Iterable<Element1> elements) {
        return new UniSelectImpl<>(elements);
    }

    public static <Element1> FilterableUniSelect<Element1> selectFrom(Supplier<Iterable<Element1>> elements) {
        return new UniSelectImpl<>(elements.get());
    }
}
