package io.github.tamingj.q4c.impl;

import io.github.tamingj.q4c.*;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class QuintiSelectImpl<E1, E2, E3, E4, E5> implements FilterableQuintiSelect<E1, E2, E3, E4, E5> {
    private final Iterable<Quintuple<E1, E2, E3, E4, E5>> source;

    public QuintiSelectImpl(Iterable<Quintuple<E1, E2, E3, E4, E5>> source) {
        this.source = source;
    }

    @Override
    public QuintiSelect<E1, E2, E3, E4, E5> where(QuintiPredicate<E1, E2, E3, E4, E5> condition) {
        return new QuintiSelectImpl<>(FilteredIterator.filtered(source, (t) -> condition.test(t.element1(), t.element2(), t.element3(), t.element4(), t.element5())));
    }

    @Override
    public Stream<Quintuple<E1, E2, E3, E4, E5>> quintupleStream() {
        return StreamSupport.stream(source.spliterator(), false);
    }

    @Override
    public QuintiStream<E1, E2, E3, E4, E5> stream() {
        return new QuintiStreamImpl<>(quintupleStream());
    }

    @Override
    public Iterator<Quintuple<E1, E2, E3, E4, E5>> iterator() {
        return source.iterator();
    }

    @Override
    public List<Quintuple<E1, E2, E3, E4, E5>> toList() {
        return quintupleStream().collect(Collectors.toList());
    }

    @Override
    public void forEach(QuintiConsumer<E1, E2, E3, E4, E5> consumer) {
        stream().forEach(consumer);
    }

    @Override
    public void forEach(Consumer<? super Quintuple<E1, E2, E3, E4, E5>> consumer) {
        quintupleStream().forEach(consumer);
    }
}
