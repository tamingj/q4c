package io.github.tamingj.q4c.impl;

import io.github.tamingj.q4c.*;
import lombok.AllArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Function;

@AllArgsConstructor
class BiSelectJoinImpl<E1, E2, E3> implements BiSelectJoin<E1, E2, E3> {
    private final JoinMode joinMode;
    private final Iterable<Pair<E1, E2>> source;
    private final Iterable<E3> element3s;

    @Override
    public <Key> FilterableTriSelect<E1, E2, E3> on(BiFunction<E1, E2, Key> element1And2KeyFunc, Function<E3, Key> element3KeyFunc) {
        return on(element1And2KeyFunc, element3KeyFunc, TriPredicates.matchAll());
    }

    @Override
    public <Key> FilterableTriSelect<E1, E2, E3> on(BiFunction<E1, E2, Key> element1And2KeyFunc, Function<E3, Key> element3KeyFunc, TriPredicate<E1, E2, E3> condition) {
        return new TriSelectImpl<>(JoinIterator.of(joinMode, source, (t) -> element1And2KeyFunc.apply(t.element1(), t.element2()), element3s, element3KeyFunc, (left, right) -> condition.test(left.element1(), left.element2(), right), (left, right) -> Triple.of(left.element1(), left.element2(), right)));
    }
}
