package io.github.tamingj.q4c.impl;

import io.github.tamingj.q4c.BiPredicate;
import io.github.tamingj.q4c.BiStream;
import io.github.tamingj.q4c.Pair;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BiStreamImpl<E1, E2> implements BiStream<E1, E2> {
    private final Stream<Pair<E1, E2>> source;

    public BiStreamImpl(Stream<Pair<E1, E2>> source) {
        this.source = source;
    }

    private Stream<Pair<E1, E2>> tupleStream() {
        return StreamSupport.stream(source.spliterator(), false);
    }

    @Override
    public void forEach(BiConsumer<E1, E2> consumer) {
        source.forEach(tuple -> consumer.accept(tuple.element1(), tuple.element2()));
    }

    @Override
    public <Result> Stream<Result> map(BiFunction<E1, E2, Result> mapper) {
        return tupleStream().map((t) -> mapper.apply(t.element1(), t.element2()));
    }

    @Override
    public BiStream<E1, E2> filter(BiPredicate<E1, E2> condition) {
        return new BiStreamImpl<>(source.filter(pair -> condition.test(pair.element1(), pair.element2())));
    }

    @Override
    public BiStream<E1, E2> peek(BiConsumer<E1, E2> consumer) {
        return new BiStreamImpl<>(source.peek(pair -> consumer.accept(pair.element1(), pair.element2())));
    }
}
