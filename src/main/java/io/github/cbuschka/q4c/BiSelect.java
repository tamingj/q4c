package io.github.cbuschka.q4c;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface BiSelect<E1, E2> extends Iterable<Pair<E1, E2>> {

    <E3> BiSelectJoin<E1, E2, E3> join(Iterable<E3> element3s);
    <E3> BiSelectJoin<E1, E2, E3> leftOuterJoin(Iterable<E3> element3s);
    <E3> BiSelectJoin<E1, E2, E3> rightOuterJoin(Iterable<E3> element3s);
    <E3> BiSelectJoin<E1, E2, E3> fullOuterJoin(Iterable<E3> element3s);

    BiStream<E1, E2> stream();

    void forEach(BiConsumer<E1, E2> consumer);

    void forEach(Consumer<? super Pair<E1, E2>> consumer);

    List<Pair<E1, E2>> toList();

    Stream<Pair<E1, E2>> pairStream();
}

