package io.github.cbuschka.q4c;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public interface BiStream<Element1, Element2> {
    void forEach(BiConsumer<Element1, Element2> consumer);

    <Result> Stream<Result> map(BiFunction<Element1, Element2, Result> mapper);

    BiStream<Element1, Element2> filter(BiPredicate<Element1, Element2> condition);

    BiStream<Element1, Element2> peek(BiConsumer<Element1, Element2> consumer);
}
