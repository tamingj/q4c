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
    public <Element3, Key> TriSelect<Element1, Element2, Element3> join(Iterable<Element3> element3s, BiFunction<Element1, Element2, Key> element1And2KeyFunc, Function<Element3, Key> element3KeyFunc) {
        return new TriSelectImpl<>(JoinIterator.forInnerJoin(source, (t) -> element1And2KeyFunc.apply(t.element1(), t.element2()), element3s, element3KeyFunc, (left, right) -> Triple.of(left.element1(), left.element2(), right)));
    }

    @Override
    public <Element3, Key> TriSelect<Element1, Element2, Element3> leftOuterJoin(Iterable<Element3> element3s, BiFunction<Element1, Element2, Key> element1And2KeyFunc, Function<Element3, Key> element3KeyFunc) {
        return new TriSelectImpl<>(JoinIterator.forLeftOuterJoin(source, (t) -> element1And2KeyFunc.apply(t.element1(), t.element2()), element3s, element3KeyFunc, (left, right) -> Triple.of(left.element1(), left.element2(), right)));
    }

    @Override
    public <Element3, Key> TriSelect<Element1, Element2, Element3> rightOuterJoin(Iterable<Element3> element3s, BiFunction<Element1, Element2, Key> element1And2KeyFunc, Function<Element3, Key> element3KeyFunc) {
        return new TriSelectImpl<>(JoinIterator.forRightOuterJoin(source, (t) -> element1And2KeyFunc.apply(t.element1(), t.element2()), element3s, element3KeyFunc, (left, rigth) -> Triple.of(left.element1(), left.element2(), rigth)));
    }

    @Override
    public <Element3, Key> TriSelect<Element1, Element2, Element3> fullOuterJoin(Iterable<Element3> element3s, BiFunction<Element1, Element2, Key> element1And2KeyFunc, Function<Element3, Key> element3KeyFunc) {
        return new TriSelectImpl<>(JoinIterator.forFullOuterJoin(source, (t) -> element1And2KeyFunc.apply(t.element1(), t.element2()), element3s, element3KeyFunc, (left, rigth) -> Triple.of(left.element1(), left.element2(), rigth)));
    }
}
