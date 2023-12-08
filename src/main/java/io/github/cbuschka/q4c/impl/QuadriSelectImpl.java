package io.github.cbuschka.q4c.impl;

import io.github.cbuschka.q4c.*;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class QuadriSelectImpl<E1, E2, E3, E4> implements FilterableQuadriSelect<E1, E2, E3, E4> {
    private final Iterable<Quadruple<E1, E2, E3, E4>> source;

    public QuadriSelectImpl(Iterable<Quadruple<E1, E2, E3, E4>> source) {
        this.source = source;
    }

    @Override
    public QuadriSelect<E1, E2, E3, E4> where(QuadriPredicate<E1, E2, E3, E4> condition) {
        return new QuadriSelectImpl<>(FilteredIterator.filtered(source, (t) -> condition.test(t.element1(), t.element2(), t.element3(), t.element4())));
    }

    @Override
    public Stream<Quadruple<E1, E2, E3, E4>> quadrupleStream() {
        return StreamSupport.stream(source.spliterator(), false);
    }

    @Override
    public QuadriStream<E1, E2, E3, E4> stream() {
        return new QuadriStreamImpl<>(quadrupleStream());
    }

    @Override
    public Iterator<Quadruple<E1, E2, E3, E4>> iterator() {
        return source.iterator();
    }

    @Override
    public List<Quadruple<E1, E2, E3, E4>> toList() {
        return quadrupleStream().collect(Collectors.toList());
    }

    @Override
    public void forEach(QuadriConsumer<E1, E2, E3, E4> consumer) {
        stream().forEach(consumer);
    }

    @Override
    public void forEach(Consumer<? super Quadruple<E1, E2, E3, E4>> consumer) {
        quadrupleStream().forEach(consumer);
    }
}
