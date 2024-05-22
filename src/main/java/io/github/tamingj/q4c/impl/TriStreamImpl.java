package io.github.tamingj.q4c.impl;

import io.github.tamingj.q4c.TriConsumer;
import io.github.tamingj.q4c.TriFunction;
import io.github.tamingj.q4c.TriPredicate;
import io.github.tamingj.q4c.TriStream;
import io.github.tamingj.q4c.Triple;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class TriStreamImpl<E1, E2, E3> implements TriStream<E1, E2, E3> {
    private final Stream<Triple<E1, E2, E3>> source;

    public TriStreamImpl(Stream<Triple<E1, E2, E3>> source) {
        this.source = source;
    }

    public Stream<Triple<E1, E2, E3>> tripleStream() {
        return StreamSupport.stream(source.spliterator(), false);
    }

    @Override
    public void forEach(TriConsumer<E1, E2, E3> consumer) {
        tripleStream()
                .forEach((t) -> consumer.accept(t.element1(), t.element2(), t.element3()));
    }

    @Override
    public <Result> Stream<Result> map(TriFunction<E1, E2, E3, Result> mapper) {
        return tripleStream().map((t) -> mapper.apply(t.element1(), t.element2(), t.element3()));
    }

    @Override
    public TriStream<E1, E2, E3> filter(TriPredicate<E1, E2, E3> condition) {
        return new TriStreamImpl<>(source.filter(pair -> condition.test(pair.element1(), pair.element2(), pair.element3())));
    }

    @Override
    public TriStream<E1, E2, E3> peek(TriConsumer<E1, E2, E3> consumer) {
        return new TriStreamImpl<>(source.peek(pair -> consumer.accept(pair.element1(), pair.element2(), pair.element3())));
    }
}
