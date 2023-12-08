package io.github.cbuschka.q4c;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface TriSelect<Element1, Element2, Element3> extends Iterable<Triple<Element1, Element2, Element3>> {
    TriSelect<Element1, Element2, Element3> where(TriFunction<Element1, Element2, Element3, Boolean> condition);

    TriStream<Element1, Element2, Element3> stream();

    Stream<Triple<Element1, Element2, Element3>> tripleStream();

    List<Triple<Element1, Element2, Element3>> toList();

    void forEach(TriConsumer<Element1, Element2, Element3> consumer);

    void forEach(Consumer<? super Triple<Element1, Element2, Element3>> consumer);
}

