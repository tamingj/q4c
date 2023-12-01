package io.github.cbuschka.q4c.impl;

import io.github.cbuschka.q4c.TriConsumer;
import io.github.cbuschka.q4c.TriFunction;
import io.github.cbuschka.q4c.TriPredicate;
import io.github.cbuschka.q4c.TriStream;
import io.github.cbuschka.q4c.Triple;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class TriStreamImpl<Element1, Element2, Element3> implements TriStream<Element1, Element2, Element3> {
    private final Stream<Triple<Element1, Element2, Element3>> source;

    public TriStreamImpl(Stream<Triple<Element1, Element2, Element3>> source) {
        this.source = source;
    }

    public Stream<Triple<Element1, Element2, Element3>> tripleStream() {
        return StreamSupport.stream(source.spliterator(), false);
    }

    @Override
    public void forEach(TriConsumer<Element1, Element2, Element3> consumer) {
        tripleStream()
                .forEach((t) -> consumer.accept(t.element1(), t.element2(), t.element3()));
    }

    @Override
    public <Result> Stream<Result> map(TriFunction<Element1, Element2, Element3, Result> mapper) {
        return tripleStream().map((t) -> mapper.apply(t.element1(), t.element2(), t.element3()));
    }

    @Override
    public TriStream<Element1, Element2, Element3> filter(TriPredicate<Element1, Element2, Element3> condition) {
        return new TriStreamImpl<>(source.filter(pair -> condition.test(pair.element1(), pair.element2(), pair.element3())));
    }

    @Override
    public TriStream<Element1, Element2, Element3> peek(TriConsumer<Element1, Element2, Element3> consumer) {
        return new TriStreamImpl<>(source.peek(pair -> consumer.accept(pair.element1(), pair.element2(), pair.element3())));
    }
}
