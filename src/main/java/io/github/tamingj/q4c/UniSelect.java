package io.github.tamingj.q4c;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface UniSelect<E1> extends Iterable<E1> {
    <E2> UniSelectJoin<E1, E2> join(Iterable<E2> element2s);

    <E2> UniSelectJoin<E1, E2> leftOuterJoin(Iterable<E2> element2s);

    <E2> UniSelectJoin<E1, E2> rightOuterJoin(Iterable<E2> element2s);

    <E2> UniSelectJoin<E1, E2> fullOuterJoin(Iterable<E2> element2s);

    Stream<E1> stream();

    void forEach(Consumer<? super E1> consumer);

    List<E1> toList();
}

