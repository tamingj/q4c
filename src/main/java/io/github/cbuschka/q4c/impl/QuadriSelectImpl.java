package io.github.cbuschka.q4c.impl;

import io.github.cbuschka.q4c.*;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class QuadriSelectImpl<Element1, Element2, Element3, Element4> implements FilterableQuadriSelect<Element1, Element2, Element3, Element4> {
    private final Iterable<Quadruple<Element1, Element2, Element3, Element4>> source;

    public QuadriSelectImpl(Iterable<Quadruple<Element1, Element2, Element3, Element4>> source) {
        this.source = source;
    }

    @Override
    public QuadriSelect<Element1, Element2, Element3, Element4> where(QuadriPredicate<Element1, Element2, Element3, Element4> condition) {
        return new QuadriSelectImpl<>(FilteredIterator.filtered(source, (t) -> condition.test(t.element1(), t.element2(), t.element3(), t.element4())));
    }

    @Override
    public Stream<Quadruple<Element1, Element2, Element3, Element4>> quadrupleStream() {
        return StreamSupport.stream(source.spliterator(), false);
    }

    @Override
    public QuadriStream<Element1, Element2, Element3, Element4> stream() {
        return new QuadriStreamImpl<>(quadrupleStream());
    }

    @Override
    public Iterator<Quadruple<Element1, Element2, Element3, Element4>> iterator() {
        return source.iterator();
    }

    @Override
    public List<Quadruple<Element1, Element2, Element3, Element4>> toList() {
        return quadrupleStream().collect(Collectors.toList());
    }

    @Override
    public void forEach(QuadriConsumer<Element1, Element2, Element3, Element4> consumer) {
        stream().forEach(consumer);
    }

    @Override
    public void forEach(Consumer<? super Quadruple<Element1, Element2, Element3, Element4>> consumer) {
        quadrupleStream().forEach(consumer);
    }
}
