package io.github.cbuschka.q4c;

import java.util.stream.Stream;

public interface QuadriStream<Element1, Element2, Element3, Element4> {
    void forEach(QuadriConsumer<Element1, Element2, Element3, Element4> consumer);

    <Result> Stream<Result> map(QuadriFunction<Element1, Element2, Element3, Element4, Result> mapper);

    QuadriStream<Element1, Element2, Element3, Element4> filter(QuadriPredicate<Element1, Element2, Element3, Element4> condition);

    QuadriStream<Element1, Element2, Element3, Element4> peek(QuadriConsumer<Element1, Element2, Element3, Element4> consumer);
}
