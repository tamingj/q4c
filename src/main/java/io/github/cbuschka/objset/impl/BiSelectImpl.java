package io.github.cbuschka.objset.impl;

import io.github.cbuschka.objset.*;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BiSelectImpl<Element1, Element2> implements FilterableBiSelect<Element1, Element2> {
    private final ObjectSetImpl objectSet;
    private final Iterable<Pair<Element1, Element2>> source;

    public BiSelectImpl(ObjectSetImpl objectSet, Iterable<Pair<Element1, Element2>> source) {
        this.objectSet = objectSet;
        this.source = source;
    }

    @Override
    public Stream<Pair<Element1, Element2>> tupleStream() {
        return StreamSupport.stream(source.spliterator(), false);
    }

    @Override
    public BiSelect<Element1, Element2> where(BiFunction<Element1, Element2, Boolean> condition) {
        return new BiSelectImpl<>(objectSet, FilteredIterator.filter(source, (tuple) -> condition.apply(tuple.element1(), tuple.element2())));
    }

    @Override
    public BiStream<Element1, Element2> stream() {
        return new BiStreamImpl<>(tupleStream());
    }

    @Override
    public <Element3, Key> TriSelect<Element1, Element2, Element3> join(Class<Element3> element3Type, BiFunction<Element1, Element2, Key> element1And2KeyFunc, Function<Element3, Key> element3KeyFunc) {
        return new TriSelectImpl<>(objectSet, Joins.innerJoin(source, (t) -> element1And2KeyFunc.apply(t.element1(), t.element2()), objectSet.getElementsFor(element3Type), element3KeyFunc,
                (left, right) -> Triple.of(left.element1(), left.element2(), right)));
    }

    @Override
    public <Element3, Key> TriSelect<Element1, Element2, Element3> leftOuterJoin(Class<Element3> element3Type, BiFunction<Element1, Element2, Key> element1And2KeyFunc, Function<Element3, Key> element3KeyFunc) {
        return new TriSelectImpl<>(objectSet, Joins.leftOuterJoin(source, (t) -> element1And2KeyFunc.apply(t.element1(), t.element2()), objectSet.getElementsFor(element3Type), element3KeyFunc,
                (left, right) -> Triple.of(left.element1(), left.element2(), right)));
    }

    @Override
    public <Element3, Key> TriSelect<Element1, Element2, Element3> rightOuterJoin(Class<Element3> element3Type, BiFunction<Element1, Element2, Key> element1And2KeyFunc, Function<Element3, Key> element3KeyFunc) {
        return new TriSelectImpl<>(objectSet, Joins.leftOuterJoin(source, (t) -> element1And2KeyFunc.apply(t.element1(), t.element2()), objectSet.getElementsFor(element3Type), element3KeyFunc,
                (left, rigth) -> Triple.of(left.element1(), left.element2(), rigth)));
    }

    @Override
    public <Element3, Key> TriSelect<Element1, Element2, Element3> fullOuterJoin(Class<Element3> element3Type, BiFunction<Element1, Element2, Key> element1And2KeyFunc, Function<Element3, Key> element3KeyFunc) {
        return new TriSelectImpl<>(objectSet, Joins.leftOuterJoin(source, (t) -> element1And2KeyFunc.apply(t.element1(), t.element2()), objectSet.getElementsFor(element3Type), element3KeyFunc,
                (left, rigth) -> Triple.of(left.element1(), left.element2(), rigth)));
    }
}
