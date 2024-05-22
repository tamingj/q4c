package io.github.tamingj.q4c;

import io.github.tamingj.q4c.impl.BiSelectImpl;
import io.github.tamingj.q4c.impl.TriSelectImpl;
import io.github.tamingj.q4c.impl.UniSelectImpl;

import java.util.function.Supplier;

public class Queries {
    private Queries() {
        // dont instantiate
    }

    public static <E1> FilterableUniSelect<E1> selectFrom(Iterable<E1> elements) {
        return new UniSelectImpl<>(elements);
    }


    public static <E1> FilterableUniSelect<E1> selectFrom(UniSelect<E1> subselect) {
        return new UniSelectImpl<>(subselect);
    }

    public static <E1, E2> FilterableBiSelect<E1, E2> selectFrom(BiSelect<E1, E2> subselect) {
        return new BiSelectImpl<>(subselect);
    }

    public static <E1, E2, E3> FilterableTriSelect<E1, E2, E3> selectFrom(TriSelect<E1, E2, E3> subselect) {
        return new TriSelectImpl<>(subselect);
    }

    public static <E1> FilterableUniSelect<E1> selectFrom(Supplier<Iterable<E1>> elements) {
        return new UniSelectImpl<>(elements.get());
    }
}
