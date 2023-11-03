package io.github.cbuschka.objset;

import java.util.stream.Stream;

public interface TriStream<Element1, Element2, Element3> {
    void forEach(TriConsumer<Element1, Element2, Element3> consumer);

    <Result> Stream<Result> map(TriFunction<Element1, Element2, Element3, Result> mapper);

    TriStream<Element1, Element2, Element3> filter(TriPredicate<Element1, Element2, Element3> condition);

    TriStream<Element1, Element2, Element3> peek(TriConsumer<Element1, Element2, Element3> consumer);
}
