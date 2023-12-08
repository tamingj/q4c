package io.github.cbuschka.q4c.impl;

import io.github.cbuschka.q4c.FilterableUniSelect;
import io.github.cbuschka.q4c.UniSelect;
import io.github.cbuschka.q4c.UniSelectJoin;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class UniSelectImpl<E1> implements FilterableUniSelect<E1> {
    private final Iterable<E1> elements;

    public UniSelectImpl(Iterable<E1> elements) {
        this.elements = elements;
    }

    @Override
    public <E2> UniSelectJoin<E1, E2> join(Iterable<E2> element2s) {
        return new UniSelectJoinImpl<>(JoinMode.INNER, elements, element2s);
    }

    @Override
    public <E2> UniSelectJoin<E1, E2> leftOuterJoin(Iterable<E2> element2s) {
        return new UniSelectJoinImpl<>(JoinMode.LEFT_OUTER, elements, element2s);
    }

    @Override
    public <E2> UniSelectJoin<E1, E2> rightOuterJoin(Iterable<E2> element2s) {
        return new UniSelectJoinImpl<>(JoinMode.RIGHT_OUTER, elements, element2s);
    }

    @Override
    public <E2> UniSelectJoin<E1, E2> fullOuterJoin(Iterable<E2> element2s) {
        return new UniSelectJoinImpl<>(JoinMode.FULL_OUTER, elements, element2s);
    }

    @Override
    public UniSelect<E1> where(Predicate<E1> condition) {
        return new UniSelectImpl<>(FilteredIterator.filtered(this.elements, condition));
    }

    @Override
    public Stream<E1> stream() {
        return StreamSupport.stream(elements.spliterator(), false);
    }

    @Override
    public Iterator<E1> iterator() {
        return elements.iterator();
    }

    @Override
    public void forEach(Consumer<? super E1> consumer) {
        stream().forEach(consumer);
    }

    @Override
    public List<E1> toList() {
        return stream().collect(Collectors.toList());
    }
}
