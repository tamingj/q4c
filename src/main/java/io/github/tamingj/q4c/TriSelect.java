package io.github.tamingj.q4c;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface TriSelect<E1, E2, E3> extends Iterable<Triple<E1, E2, E3>> {

    <E4> TriSelectJoin<E1, E2, E3, E4> join(Iterable<E4> element4s);
    <E4> TriSelectJoin<E1, E2, E3, E4> leftOuterJoin(Iterable<E4> element4s);
    <E4> TriSelectJoin<E1, E2, E3, E4> rightOuterJoin(Iterable<E4> element4s);
    <E4> TriSelectJoin<E1, E2, E3, E4> fullOuterJoin(Iterable<E4> element4s);

    TriStream<E1, E2, E3> stream();

    Stream<Triple<E1, E2, E3>> tripleStream();

    List<Triple<E1, E2, E3>> toList();

    void forEach(TriConsumer<E1, E2, E3> consumer);

    void forEach(Consumer<? super Triple<E1, E2, E3>> consumer);
}

