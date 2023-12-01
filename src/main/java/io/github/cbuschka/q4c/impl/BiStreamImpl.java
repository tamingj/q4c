package io.github.cbuschka.q4c.impl;

import io.github.cbuschka.q4c.BiPredicate;
import io.github.cbuschka.q4c.BiStream;
import io.github.cbuschka.q4c.Pair;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BiStreamImpl<Element1, Element2> implements BiStream<Element1, Element2> {
    private final Stream<Pair<Element1, Element2>> source;

    public BiStreamImpl(Stream<Pair<Element1, Element2>> source) {
        this.source = source;
    }

    private Stream<Pair<Element1, Element2>> tupleStream() {
        return StreamSupport.stream(source.spliterator(), false);
    }

    @Override
    public void forEach(BiConsumer<Element1, Element2> consumer) {
        source.forEach(tuple -> consumer.accept(tuple.element1(), tuple.element2()));
    }

    @Override
    public <Result> Stream<Result> map(BiFunction<Element1, Element2, Result> mapper) {
        return tupleStream().map((t) -> mapper.apply(t.element1(), t.element2()));
    }

    @Override
    public BiStream<Element1, Element2> filter(BiPredicate<Element1, Element2> condition) {
        return new BiStreamImpl<>(source.filter(pair -> condition.test(pair.element1(), pair.element2())));
    }

    @Override
    public BiStream<Element1, Element2> peek(BiConsumer<Element1, Element2> consumer) {
        return new BiStreamImpl<>(source.peek(pair -> consumer.accept(pair.element1(), pair.element2())));
    }
}
