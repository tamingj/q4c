package io.github.cbuschka.objset;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface UniSelect<Element1> {
    <Element2> UniSelectJoin<Element1, Element2> join(Iterable<Element2> element2s);
    <Element2> UniSelectJoin<Element1, Element2> leftOuterJoin(Iterable<Element2> element2s);
    <Element2> UniSelectJoin<Element1, Element2> rightOuterJoin(Iterable<Element2> element2s);
    <Element2> UniSelectJoin<Element1, Element2> fullOuterJoin(Iterable<Element2> element2s);

    Stream<Element1> stream();

    default void forEach(Consumer<Element1> consumer) {
        stream().forEach(consumer);
    }

    default List<Element1> toList() {
        return stream().collect(Collectors.toList());
    }
}

