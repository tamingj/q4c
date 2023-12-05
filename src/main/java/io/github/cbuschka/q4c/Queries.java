package io.github.cbuschka.q4c;

import io.github.cbuschka.q4c.impl.BiSelectImpl;
import io.github.cbuschka.q4c.impl.TriSelectImpl;
import io.github.cbuschka.q4c.impl.UniSelectImpl;

import java.util.function.Supplier;

public class Queries {
    private Queries() {
        // dont instantiate
    }

    public static <Element1> FilterableUniSelect<Element1> selectFrom(Iterable<Element1> elements) {
        return new UniSelectImpl<>(elements);
    }


    public static <Element1> FilterableUniSelect<Element1> selectFrom(UniSelect<Element1> subselect) {
        return new UniSelectImpl<>(subselect);
    }

    public static <Element1, Element2> FilterableBiSelect<Element1, Element2> selectFrom(BiSelect<Element1, Element2> subselect) {
        return new BiSelectImpl<>(subselect);
    }

    public static <Element1, Element2, Element3> FilterableTriSelect<Element1, Element2, Element3> selectFrom(TriSelect<Element1, Element2, Element3> subselect) {
        return new TriSelectImpl<>(subselect);
    }

    public static <Element1> FilterableUniSelect<Element1> selectFrom(Supplier<Iterable<Element1>> elements) {
        return new UniSelectImpl<>(elements.get());
    }
}
