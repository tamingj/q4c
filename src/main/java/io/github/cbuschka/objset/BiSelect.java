package io.github.cbuschka.objset;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface BiSelect<Element1, Element2> {
    BiSelect<Element1, Element2> where(BiFunction<Element1, Element2, Boolean> condition);

    <Element3, Key> TriSelect<Element1, Element2, Element3> join(Class<Element3> element3Type, BiFunction<Element1, Element2, Key> key1Func, Function<Element3, Key> key2Func);

    <Element3, Key> TriSelect<Element1, Element2, Element3> leftOuterJoin(Class<Element3> element3Type, BiFunction<Element1, Element2, Key> key1Func, Function<Element3, Key> key2Func);

    BiStream<Element1, Element2> stream();

    default void forEach(BiConsumer<Element1, Element2> consumer) {
        stream().forEach(consumer);
    }

    default List<Tuple<Element1, Element2>> toList() {
        return tupleStream().toList();
    }

    Stream<Tuple<Element1, Element2>> tupleStream();
}

