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

public class UniSelectImpl<Element1> implements FilterableUniSelect<Element1> {
    private final Iterable<Element1> elements;

    public UniSelectImpl(Iterable<Element1> elements) {
        this.elements = elements;
    }

    @Override
    public <Element2> UniSelectJoin<Element1, Element2> join(Iterable<Element2> element2s) {
        return new UniSelectJoinImpl<>(JoinMode.INNER, elements, element2s);
    }

    @Override
    public <Element2> UniSelectJoin<Element1, Element2> leftOuterJoin(Iterable<Element2> element2s) {
        return new UniSelectJoinImpl<>(JoinMode.LEFT_OUTER, elements, element2s);
    }

    @Override
    public <Element2> UniSelectJoin<Element1, Element2> rightOuterJoin(Iterable<Element2> element2s) {
        return new UniSelectJoinImpl<>(JoinMode.RIGHT_OUTER, elements, element2s);
    }

    @Override
    public <Element2> UniSelectJoin<Element1, Element2> fullOuterJoin(Iterable<Element2> element2s) {
        return new UniSelectJoinImpl<>(JoinMode.FULL_OUTER, elements, element2s);
    }

    @Override
    public UniSelect<Element1> where(Predicate<Element1> condition) {
        return new UniSelectImpl<>(FilteredIterator.filtered(this.elements, condition));
    }

    @Override
    public Stream<Element1> stream() {
        return StreamSupport.stream(elements.spliterator(), false);
    }

    @Override
    public Iterator<Element1> iterator() {
        return elements.iterator();
    }

    @Override
    public void forEach(Consumer<? super Element1> consumer) {
        stream().forEach(consumer);
    }

    @Override
    public List<Element1> toList() {
        return stream().collect(Collectors.toList());
    }
}
