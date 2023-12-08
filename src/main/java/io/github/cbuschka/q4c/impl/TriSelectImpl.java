package io.github.cbuschka.q4c.impl;

import io.github.cbuschka.q4c.*;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
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

    @Override
    public Iterator<Triple<Element1, Element2, Element3>> iterator() {
        return source.iterator();
    }

    @Override
    public List<Triple<Element1, Element2, Element3>> toList() {
        return tripleStream().collect(Collectors.toList());
    }

    @Override
    public void forEach(TriConsumer<Element1, Element2, Element3> consumer) {
        stream().forEach(consumer);
    }

    @Override
    public void forEach(Consumer<? super Triple<Element1, Element2, Element3>> consumer) {
        tripleStream().forEach(consumer);
    }
}
