package io.github.cbuschka.q4c;

import java.util.stream.Stream;

public interface QuintiStream<E1, E2, E3, E4, E5> {
    void forEach(QuintiConsumer<E1, E2, E3, E4, E5> consumer);

    <Result> Stream<Result> map(QuintiFunction<E1, E2, E3, E4, E5, Result> mapper);

    QuintiStream<E1, E2, E3, E4, E5> filter(QuintiPredicate<E1, E2, E3, E4, E5> condition);

    QuintiStream<E1, E2, E3, E4, E5> peek(QuintiConsumer<E1, E2, E3, E4, E5> consumer);
}
