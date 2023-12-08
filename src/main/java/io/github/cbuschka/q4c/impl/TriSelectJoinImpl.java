package io.github.cbuschka.q4c.impl;

import io.github.cbuschka.q4c.*;
import lombok.AllArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Function;

@AllArgsConstructor
class TriSelectJoinImpl<Element1, Element2, Element3, Element4> implements TriSelectJoin<Element1, Element2, Element3, Element4> {
    private final JoinMode joinMode;
    private final Iterable<Triple<Element1, Element2, Element3>> source;
    private final Iterable<Element4> element4s;

    @Override
    public <Key> FilterableQuadriSelect<Element1, Element2, Element3, Element4> on(TriFunction<Element1, Element2, Element3, Key> element1And2KeyFunc, Function<Element4, Key> element3KeyFunc) {
        return on(element1And2KeyFunc, element3KeyFunc, QuadriPredicates.matchAll());
    }

    @Override
    public <Key> FilterableQuadriSelect<Element1, Element2, Element3, Element4> on(TriFunction<Element1, Element2, Element3, Key> element1KeyFunc, Function<Element4, Key> element2KeyFunc, QuadriPredicate<Element1, Element2, Element3, Element4> condition) {
        return new QuadriSelectImpl<>(JoinIterator.of(joinMode, source, (t) -> element1KeyFunc.apply(t.element1(), t.element2(), t.element3()), element4s, element2KeyFunc, (left, right) -> condition.test(left.element1(), left.element2(), left.element3(), right), (left, right) -> Quadruple.of(left.element1(), left.element2(), left.element3(), right)));
    }
}
