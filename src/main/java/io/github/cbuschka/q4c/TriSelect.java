package io.github.cbuschka.q4c;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface TriSelect<Element1, Element2, Element3> {
    TriSelect<Element1, Element2, Element3> where(TriFunction<Element1, Element2, Element3, Boolean> condition);

    TriStream<Element1, Element2, Element3> stream();

    Stream<Triple<Element1, Element2, Element3>> tripleStream();

    default List<Triple<Element1, Element2, Element3>> toList() {
        return tripleStream().collect(Collectors.toList());
    }

    default void forEach(TriConsumer<Element1, Element2, Element3> consumer) {
        stream().forEach(consumer);
    }

    default void forEach(Consumer<Triple<Element1, Element2, Element3>> consumer) {
        tripleStream().forEach(consumer);
    }
}

