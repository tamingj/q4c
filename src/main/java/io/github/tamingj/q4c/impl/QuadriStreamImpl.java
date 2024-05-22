package io.github.tamingj.q4c.impl;

import io.github.tamingj.q4c.*;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class QuadriStreamImpl<E1, E2, E3, E4> implements QuadriStream<E1, E2, E3, E4> {
    private final Stream<Quadruple<E1, E2, E3, E4>> source;

    public QuadriStreamImpl(Stream<Quadruple<E1, E2, E3, E4>> source) {
        this.source = source;
    }

    public Stream<Quadruple<E1, E2, E3, E4>> quadrupleStream() {
        return StreamSupport.stream(source.spliterator(), false);
    }

    @Override
    public void forEach(QuadriConsumer<E1, E2, E3, E4> consumer) {
        quadrupleStream()
                .forEach((t) -> consumer.accept(t.element1(), t.element2(), t.element3(), t.element4()));
    }

    @Override
    public <Result> Stream<Result> map(QuadriFunction<E1, E2, E3, E4, Result> mapper) {
        return quadrupleStream().map((t) -> mapper.apply(t.element1(), t.element2(), t.element3(), t.element4()));
    }

    @Override
    public QuadriStream<E1, E2, E3, E4> filter(QuadriPredicate<E1, E2, E3, E4> condition) {
        return new QuadriStreamImpl<>(source.filter(pair -> condition.test(pair.element1(), pair.element2(), pair.element3(), pair.element4())));
    }

    @Override
    public QuadriStream<E1, E2, E3, E4> peek(QuadriConsumer<E1, E2, E3, E4> consumer) {
        return new QuadriStreamImpl<>(source.peek(pair -> consumer.accept(pair.element1(), pair.element2(), pair.element3(), pair.element4())));
    }
}
