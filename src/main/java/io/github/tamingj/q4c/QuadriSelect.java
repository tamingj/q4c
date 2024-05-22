package io.github.tamingj.q4c;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface QuadriSelect<E1, E2, E3, E4> extends Iterable<Quadruple<E1, E2, E3, E4>> {
    <E5> QuadriSelectJoin<E1, E2, E3, E4, E5> join(Iterable<E5> right);
    <E5> QuadriSelectJoin<E1, E2, E3, E4, E5> leftOuterJoin(Iterable<E5> right);
    <E5> QuadriSelectJoin<E1, E2, E3, E4, E5> rightOuterJoin(Iterable<E5> right);
    <E5> QuadriSelectJoin<E1, E2, E3, E4, E5> fullOuterJoin(Iterable<E5> right);

    QuadriStream<E1, E2, E3, E4> stream();

    Stream<Quadruple<E1, E2, E3, E4>> quadrupleStream();

    List<Quadruple<E1, E2, E3, E4>> toList();

    void forEach(QuadriConsumer<E1, E2, E3, E4> consumer);

    void forEach(Consumer<? super Quadruple<E1, E2, E3, E4>> consumer);
}

