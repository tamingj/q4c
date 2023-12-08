package io.github.cbuschka.q4c.impl;

import io.github.cbuschka.q4c.*;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TriSelectImpl<E1, E2, E3> implements FilterableTriSelect<E1, E2, E3> {
    private final Iterable<Triple<E1, E2, E3>> source;

    public TriSelectImpl(Iterable<Triple<E1, E2, E3>> source) {
        this.source = source;
    }

    @Override
    public <E4> TriSelectJoin<E1, E2, E3, E4> join(Iterable<E4> element4s) {
        return new TriSelectJoinImpl<>(JoinMode.INNER, source, element4s);
    }

    @Override
    public <E4> TriSelectJoin<E1, E2, E3, E4> leftOuterJoin(Iterable<E4> element4s) {
        return new TriSelectJoinImpl<>(JoinMode.LEFT_OUTER, source, element4s);
    }

    @Override
    public <E4> TriSelectJoin<E1, E2, E3, E4> rightOuterJoin(Iterable<E4> element4s) {
        return new TriSelectJoinImpl<>(JoinMode.RIGHT_OUTER, source, element4s);
    }

    @Override
    public <E4> TriSelectJoin<E1, E2, E3, E4> fullOuterJoin(Iterable<E4> element4s) {
        return new TriSelectJoinImpl<>(JoinMode.FULL_OUTER, source, element4s);
    }


    @Override
    public TriSelect<E1, E2, E3> where(TriPredicate<E1, E2, E3> condition) {
        return new TriSelectImpl<>(FilteredIterator.filtered(source, (t) -> condition.test(t.element1(), t.element2(), t.element3())));
    }

    @Override
    public Stream<Triple<E1, E2, E3>> tripleStream() {
        return StreamSupport.stream(source.spliterator(), false);
    }

    @Override
    public TriStream<E1, E2, E3> stream() {
        return new TriStreamImpl<>(tripleStream());
    }

    @Override
    public Iterator<Triple<E1, E2, E3>> iterator() {
        return source.iterator();
    }

    @Override
    public List<Triple<E1, E2, E3>> toList() {
        return tripleStream().collect(Collectors.toList());
    }

    @Override
    public void forEach(TriConsumer<E1, E2, E3> consumer) {
        stream().forEach(consumer);
    }

    @Override
    public void forEach(Consumer<? super Triple<E1, E2, E3>> consumer) {
        tripleStream().forEach(consumer);
    }
}
