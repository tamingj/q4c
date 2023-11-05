package io.github.cbuschka.objset.impl;

import io.github.cbuschka.objset.*;
import lombok.AllArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Function;

@AllArgsConstructor
class BiSelectJoinImpl<Element1, Element2, Element3> implements BiSelectJoin<Element1, Element2, Element3> {
    private final JoinMode joinMode;
    private final Iterable<Pair<Element1, Element2>> source;
    private final Iterable<Element3> element3s;

    @Override
    public <Key> FilterableTriSelect<Element1, Element2, Element3> on(BiFunction<Element1, Element2, Key> element1And2KeyFunc, Function<Element3, Key> element3KeyFunc) {
        return new TriSelectImpl<>(JoinIterator.of(joinMode, source, (t) -> element1And2KeyFunc.apply(t.element1(), t.element2()), element3s, element3KeyFunc, (left, right) -> Triple.of(left.element1(), left.element2(), right)));
    }
}
