package io.github.cbuschka.q4c;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface QuadriSelect<Element1, Element2, Element3, Element4> extends Iterable<Quadruple<Element1, Element2, Element3, Element4>> {
    QuadriSelect<Element1, Element2, Element3, Element4> where(QuadriPredicate<Element1, Element2, Element3, Element4> condition);

    QuadriStream<Element1, Element2, Element3, Element4> stream();

    Stream<Quadruple<Element1, Element2, Element3, Element4>> quadrupleStream();

    List<Quadruple<Element1, Element2, Element3, Element4>> toList();

    void forEach(QuadriConsumer<Element1, Element2, Element3, Element4> consumer);

    void forEach(Consumer<? super Quadruple<Element1, Element2, Element3, Element4>> consumer);
}

