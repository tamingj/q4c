package io.github.cbuschka.objset.impl;

import io.github.cbuschka.objset.*;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class BiSelectImpl<Element1, Element2> implements FilterableBiSelect<Element1, Element2> {
    private final ObjectSetImpl objectSet;
    private final Iterable<Tuple<Element1, Element2>> tupleStream;

    BiSelectImpl(ObjectSetImpl objectSet, Iterable<Tuple<Element1, Element2>> tupleStream) {
        this.objectSet = objectSet;
        this.tupleStream = tupleStream;
    }

    @Override
    public Stream<Tuple<Element1, Element2>> tupleStream() {
        return StreamSupport.stream(tupleStream.spliterator(), false);
    }

    @Override
    public BiSelect<Element1, Element2> where(BiFunction<Element1, Element2, Boolean> condition) {
        return new BiSelectImpl<>(objectSet, FilteredIterator.filter(tupleStream, (tuple) -> condition.apply(tuple.element1(), tuple.element2())));
    }

    @Override
    public BiStream<Element1, Element2> stream() {
        return new BiStreamImpl(tupleStream());
    }

    @Override
    public <Element3, Key> TriSelect<Element1, Element2, Element3> join(Class<Element3> element3Type, BiFunction<Element1, Element2, Key> element1And2KeyFunc, Function<Element3, Key> element3KeyFunc) {
        return new TriSelectImpl<>(objectSet, JoinIterator.wrap(JoinIterator.JoinMode.LEFT_INNER,
                tupleStream, (t) -> element1And2KeyFunc.apply(t.element1(), t.element2()), objectSet.getElementsFor(element3Type), element3KeyFunc,
                (left, rigth) -> Triple.of(left.element1(), left.element2(), rigth)));
    }

    @Override
    public <Element3, Key> TriSelect<Element1, Element2, Element3> leftOuterJoin(Class<Element3> element3Type, BiFunction<Element1, Element2, Key> element1And2KeyFunc, Function<Element3, Key> element3KeyFunc) {
        return new TriSelectImpl<>(objectSet, JoinIterator.wrap(JoinIterator.JoinMode.LEFT_OUTER,
                tupleStream, (t) -> element1And2KeyFunc.apply(t.element1(), t.element2()), objectSet.getElementsFor(element3Type), element3KeyFunc,
                (left, rigth) -> Triple.of(left.element1(), left.element2(), rigth)));
    }
}
