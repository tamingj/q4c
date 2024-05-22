package io.github.tamingj.q4c;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface QuintiSelect<E1, E2, E3, E4, E5> extends Iterable<Quintuple<E1, E2, E3, E4, E5>> {

    QuintiStream<E1, E2, E3, E4, E5> stream();

    Stream<Quintuple<E1, E2, E3, E4, E5>> quintupleStream();

    List<Quintuple<E1, E2, E3, E4, E5>> toList();

    void forEach(QuintiConsumer<E1, E2, E3, E4, E5> consumer);

    void forEach(Consumer<? super Quintuple<E1, E2, E3, E4, E5>> consumer);
}

