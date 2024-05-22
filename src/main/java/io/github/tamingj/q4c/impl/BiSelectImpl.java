package io.github.tamingj.q4c.impl;

import io.github.tamingj.q4c.*;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BiSelectImpl<E1, E2> implements FilterableBiSelect<E1, E2> {
    private final Iterable<Pair<E1, E2>> source;

    public BiSelectImpl(Iterable<Pair<E1, E2>> source) {
        this.source = source;
    }

    @Override
    public Stream<Pair<E1, E2>> pairStream() {
        return StreamSupport.stream(source.spliterator(), false);
    }

    @Override
    public BiSelect<E1, E2> where(BiPredicate<E1, E2> condition) {
        return new BiSelectImpl<>(FilteredIterator.filtered(source, (tuple) -> condition.test(tuple.element1(), tuple.element2())));
    }

    @Override
    public BiStream<E1, E2> stream() {
        return new BiStreamImpl<>(pairStream());
    }

    @Override
    public <E3> BiSelectJoin<E1, E2, E3> join(Iterable<E3> element3s) {
        return new BiSelectJoinImpl<>(JoinMode.INNER, source, element3s);
    }

    @Override
    public <E3> BiSelectJoin<E1, E2, E3> leftOuterJoin(Iterable<E3> element3s) {
        return new BiSelectJoinImpl<>(JoinMode.LEFT_OUTER, source, element3s);
    }

    @Override
    public <E3> BiSelectJoin<E1, E2, E3> rightOuterJoin(Iterable<E3> element3s) {
        return new BiSelectJoinImpl<>(JoinMode.RIGHT_OUTER, source, element3s);
    }

    @Override
    public <E3> BiSelectJoin<E1, E2, E3> fullOuterJoin(Iterable<E3> element3s) {
        return new BiSelectJoinImpl<>(JoinMode.FULL_OUTER, source, element3s);
    }

    @Override
    public Iterator<Pair<E1, E2>> iterator() {
        return source.iterator();
    }

    @Override
    public void forEach(Consumer<? super Pair<E1, E2>> consumer) {
        forEach((e1, e2) -> consumer.accept(Pair.of(e1, e2)));
    }

    @Override
    public void forEach(BiConsumer<E1, E2> consumer) {
        stream().forEach(consumer);
    }

    @Override
    public List<Pair<E1, E2>> toList() {
        return pairStream().collect(Collectors.toList());
    }
}
