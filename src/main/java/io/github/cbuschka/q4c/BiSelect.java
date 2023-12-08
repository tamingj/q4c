package io.github.cbuschka.q4c;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface BiSelect<Element1, Element2> extends Iterable<Pair<Element1, Element2>> {

    <Element3> BiSelectJoin<Element1, Element2, Element3> join(Iterable<Element3> element3s);
    <Element3> BiSelectJoin<Element1, Element2, Element3> leftOuterJoin(Iterable<Element3> element3s);
    <Element3> BiSelectJoin<Element1, Element2, Element3> rightOuterJoin(Iterable<Element3> element3s);
    <Element3> BiSelectJoin<Element1, Element2, Element3> fullOuterJoin(Iterable<Element3> element3s);

    BiStream<Element1, Element2> stream();

    void forEach(BiConsumer<Element1, Element2> consumer);

    void forEach(Consumer<? super Pair<Element1, Element2>> consumer);

    List<Pair<Element1, Element2>> toList();

    Stream<Pair<Element1, Element2>> pairStream();
}

