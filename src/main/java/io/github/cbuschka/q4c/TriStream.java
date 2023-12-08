package io.github.cbuschka.q4c;

import java.util.stream.Stream;

public interface TriStream<E1, E2, E3> {
    void forEach(TriConsumer<E1, E2, E3> consumer);

    <Result> Stream<Result> map(TriFunction<E1, E2, E3, Result> mapper);

    TriStream<E1, E2, E3> filter(TriPredicate<E1, E2, E3> condition);

    TriStream<E1, E2, E3> peek(TriConsumer<E1, E2, E3> consumer);
}
