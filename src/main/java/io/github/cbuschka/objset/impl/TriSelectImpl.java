package io.github.cbuschka.objset.impl;

import io.github.cbuschka.objset.*;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TriSelectImpl<Element1, Element2, Element3> implements FilterableTriSelect<Element1, Element2, Element3> {
    private final Iterable<Triple<Element1, Element2, Element3>> source;

    public TriSelectImpl(Iterable<Triple<Element1, Element2, Element3>> source) {
        this.source = source;
    }

    @Override
    public TriSelect<Element1, Element2, Element3> where(TriFunction<Element1, Element2, Element3, Boolean> condition) {
        return new TriSelectImpl<>(FilteredIterator.filtered(source, (t) -> condition.apply(t.element1(), t.element2(), t.element3())));
    }

    @Override
    public Stream<Triple<Element1, Element2, Element3>> tripleStream() {
        return StreamSupport.stream(source.spliterator(), false);
    }

    @Override
    public TriStream<Element1, Element2, Element3> stream() {
        return new TriStreamImpl<>(tripleStream());
    }
}
