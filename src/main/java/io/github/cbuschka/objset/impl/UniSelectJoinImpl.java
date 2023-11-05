package io.github.cbuschka.objset.impl;

import io.github.cbuschka.objset.FilterableBiSelect;
import io.github.cbuschka.objset.Pair;
import io.github.cbuschka.objset.UniSelectJoin;
import lombok.AllArgsConstructor;

import java.util.function.Function;

@AllArgsConstructor
class UniSelectJoinImpl<Element1, Element2> implements UniSelectJoin<Element1, Element2> {
    private final JoinMode joinMode;
    private final Iterable<Element1> elements;
    private final Iterable<Element2> element2s;

    @Override
    public <Key> FilterableBiSelect<Element1, Element2> on(Function<Element1, Key> element1KeyFunc, Function<Element2, Key> element2KeyFunc) {
        return new BiSelectImpl<>(JoinIterator.of(joinMode, elements, element1KeyFunc, element2s, element2KeyFunc, Pair::of));

    }
}
