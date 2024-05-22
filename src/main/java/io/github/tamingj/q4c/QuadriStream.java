package io.github.tamingj.q4c;

import java.util.stream.Stream;

public interface QuadriStream<E1, E2, E3, E4> {
    void forEach(QuadriConsumer<E1, E2, E3, E4> consumer);

    <Result> Stream<Result> map(QuadriFunction<E1, E2, E3, E4, Result> mapper);

    QuadriStream<E1, E2, E3, E4> filter(QuadriPredicate<E1, E2, E3, E4> condition);

    QuadriStream<E1, E2, E3, E4> peek(QuadriConsumer<E1, E2, E3, E4> consumer);
}
