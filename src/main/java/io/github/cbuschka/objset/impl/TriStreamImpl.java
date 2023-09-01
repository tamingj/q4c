package io.github.cbuschka.objset.impl;

import io.github.cbuschka.objset.TriConsumer;
import io.github.cbuschka.objset.TriFunction;
import io.github.cbuschka.objset.TriStream;
import io.github.cbuschka.objset.Triple;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class TriStreamImpl<Element1, Element2, Element3> implements TriStream<Element1, Element2, Element3> {
    private final Iterable<Triple<Element1, Element2, Element3>> source;

    public TriStreamImpl(Iterable<Triple<Element1, Element2, Element3>> source) {
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
}
