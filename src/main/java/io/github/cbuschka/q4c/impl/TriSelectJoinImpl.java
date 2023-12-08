package io.github.cbuschka.q4c.impl;

import io.github.cbuschka.q4c.*;
import lombok.AllArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Function;

@AllArgsConstructor
class TriSelectJoinImpl<E1, E2, E3, E4> implements TriSelectJoin<E1, E2, E3, E4> {
    private final JoinMode joinMode;
    private final Iterable<Triple<E1, E2, E3>> source;
    private final Iterable<E4> element4s;

    @Override
    public <Key> FilterableQuadriSelect<E1, E2, E3, E4> on(TriFunction<E1, E2, E3, Key> element1And2KeyFunc, Function<E4, Key> element3KeyFunc) {
        return on(element1And2KeyFunc, element3KeyFunc, QuadriPredicates.matchAll());
    }

    @Override
    public <Key> FilterableQuadriSelect<E1, E2, E3, E4> on(TriFunction<E1, E2, E3, Key> element1KeyFunc, Function<E4, Key> element2KeyFunc, QuadriPredicate<E1, E2, E3, E4> condition) {
        return new QuadriSelectImpl<>(JoinIterator.of(joinMode, source, (t) -> element1KeyFunc.apply(t.element1(), t.element2(), t.element3()), element4s, element2KeyFunc, (left, right) -> condition.test(left.element1(), left.element2(), left.element3(), right), (left, right) -> Quadruple.of(left.element1(), left.element2(), left.element3(), right)));
    }
}
