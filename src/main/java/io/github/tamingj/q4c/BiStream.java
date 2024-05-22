package io.github.tamingj.q4c;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public interface BiStream<E1, E2> {
    void forEach(BiConsumer<E1, E2> consumer);

    <Result> Stream<Result> map(BiFunction<E1, E2, Result> mapper);

    BiStream<E1, E2> filter(BiPredicate<E1, E2> condition);

    BiStream<E1, E2> peek(BiConsumer<E1, E2> consumer);
}
