package io.github.tamingj.q4c.impl;

import io.github.tamingj.q4c.*;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class QuintiStreamImpl<E1, E2, E3, E4, E5> implements QuintiStream<E1, E2, E3, E4, E5> {
    private final Stream<Quintuple<E1, E2, E3, E4, E5>> source;

    public QuintiStreamImpl(Stream<Quintuple<E1, E2, E3, E4, E5>> source) {
        this.source = source;
    }

    public Stream<Quintuple<E1, E2, E3, E4, E5>> quintupleStream() {
        return StreamSupport.stream(source.spliterator(), false);
    }

    @Override
    public void forEach(QuintiConsumer<E1, E2, E3, E4, E5> consumer) {
        quintupleStream()
                .forEach((t) -> consumer.accept(t.element1(), t.element2(), t.element3(), t.element4(), t.element5()));
    }

    @Override
    public <Result> Stream<Result> map(QuintiFunction<E1, E2, E3, E4, E5, Result> mapper) {
        return quintupleStream().map((t) -> mapper.apply(t.element1(), t.element2(), t.element3(), t.element4(), t.element5()));
    }

    @Override
    public QuintiStream<E1, E2, E3, E4, E5> filter(QuintiPredicate<E1, E2, E3, E4, E5> condition) {
        return new QuintiStreamImpl<>(source.filter(t -> condition.test(t.element1(), t.element2(), t.element3(), t.element4(), t.element5())));
    }

    @Override
    public QuintiStream<E1, E2, E3, E4, E5> peek(QuintiConsumer<E1, E2, E3, E4, E5> consumer) {
        return new QuintiStreamImpl<>(source.peek(t -> consumer.accept(t.element1(), t.element2(), t.element3(), t.element4(), t.element5())));
    }
}
