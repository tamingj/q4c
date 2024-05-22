package io.github.tamingj.q4c.impl;

import io.github.tamingj.q4c.*;
import lombok.AllArgsConstructor;

import java.util.function.Function;

@AllArgsConstructor
class QuadriSelectJoinImpl<E1, E2, E3, E4, E5> implements QuadriSelectJoin<E1, E2, E3, E4, E5> {
    private final JoinMode joinMode;
    private final Iterable<Quadruple<E1, E2, E3, E4>> source;
    private final Iterable<E5> right;

    @Override
    public <Key> FilterableQuintiSelect<E1, E2, E3, E4, E5> on(QuadriFunction<E1, E2, E3, E4, Key> leftKeyFunc, Function<E5, Key> rightKeyFunc) {
        return on(leftKeyFunc, rightKeyFunc, QuintiPredicates.matchAll());
    }

    @Override
    public <Key> FilterableQuintiSelect<E1, E2, E3, E4, E5> on(QuadriFunction<E1, E2, E3, E4, Key> leftKeyFunc, Function<E5, Key> rightKeyFunc, QuintiPredicate<E1, E2, E3, E4, E5> condition) {
        return new QuintiSelectImpl<>(JoinIterator.of(joinMode, source, (t) -> leftKeyFunc.apply(t.element1(), t.element2(), t.element3(), t.element4()), right, rightKeyFunc, (left, right) -> condition.test(left.element1(), left.element2(), left.element3(), left.element4(), right), (left, right) -> Quintuple.of(left.element1(), left.element2(), left.element3(), left.element4(), right)));
    }
}
