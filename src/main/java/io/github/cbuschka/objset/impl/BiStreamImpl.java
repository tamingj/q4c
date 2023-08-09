package io.github.cbuschka.objset.impl;

import io.github.cbuschka.objset.BiStream;
import io.github.cbuschka.objset.Tuple;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class BiStreamImpl<Element1, Element2> implements BiStream<Element1, Element2> {
    private final Stream<Tuple<Element1, Element2>> source;

    BiStreamImpl(Stream<Tuple<Element1, Element2>> source) {
        this.source = source;
    }

    private Stream<Tuple<Element1, Element2>> tupleStream() {
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
}
