package io.github.cbuschka.objset.impl;

import io.github.cbuschka.objset.FilterableUniSelect;
import io.github.cbuschka.objset.UniSelect;
import io.github.cbuschka.objset.UniSelectJoin;

import java.util.function.Predicate;
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
}
