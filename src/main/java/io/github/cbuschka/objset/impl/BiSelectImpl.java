package io.github.cbuschka.objset.impl;

import io.github.cbuschka.objset.*;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BiSelectImpl<Element1, Element2> implements FilterableBiSelect<Element1, Element2> {
    private final Iterable<Pair<Element1, Element2>> source;

    public BiSelectImpl(Iterable<Pair<Element1, Element2>> source) {
        this.source = source;
    }

    @Override
    public Stream<Pair<Element1, Element2>> tupleStream() {
        return StreamSupport.stream(source.spliterator(), false);
    }

    @Override
    public BiSelect<Element1, Element2> where(BiPredicate<Element1, Element2> condition) {
        return new BiSelectImpl<>(FilteredIterator.filtered(source, (tuple) -> condition.test(tuple.element1(), tuple.element2())));
    }

    @Override
    public BiStream<Element1, Element2> stream() {
        return new BiStreamImpl<>(tupleStream());
    }

    @Override
    public <Element3> BiSelectJoin<Element1, Element2, Element3> join(Iterable<Element3> element3s) {
        return new BiSelectJoinImpl<>(JoinMode.INNER, source, element3s);
    }

    @Override
    public <Element3> BiSelectJoin<Element1, Element2, Element3> leftOuterJoin(Iterable<Element3> element3s) {
        return new BiSelectJoinImpl<>(JoinMode.LEFT_OUTER, source, element3s);
    }

    @Override
    public <Element3> BiSelectJoin<Element1, Element2, Element3> rightOuterJoin(Iterable<Element3> element3s) {
        return new BiSelectJoinImpl<>(JoinMode.RIGHT_OUTER, source, element3s);
    }

    @Override
    public <Element3> BiSelectJoin<Element1, Element2, Element3> fullOuterJoin(Iterable<Element3> element3s) {
        return new BiSelectJoinImpl<>(JoinMode.FULL_OUTER, source, element3s);
    }
}
