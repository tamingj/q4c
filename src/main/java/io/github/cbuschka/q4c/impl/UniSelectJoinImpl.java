package io.github.cbuschka.q4c.impl;

import io.github.cbuschka.q4c.*;
import lombok.AllArgsConstructor;

import java.util.function.Function;

@AllArgsConstructor
class UniSelectJoinImpl<Element1, Element2> implements UniSelectJoin<Element1, Element2> {
    private final JoinMode joinMode;
    private final Iterable<Element1> elements;
    private final Iterable<Element2> element2s;

    @Override
    public <Key> FilterableBiSelect<Element1, Element2> on(Function<Element1, Key> element1KeyFunc, Function<Element2, Key> element2KeyFunc) {
        return on(element1KeyFunc, element2KeyFunc, BiPredicates.matchAll());
    }

    @Override
    public <Key> FilterableBiSelect<Element1, Element2> on(Function<Element1, Key> element1KeyFunc, Function<Element2, Key> element2KeyFunc, BiPredicate<Element1, Element2> condition) {
        return new BiSelectImpl<>(JoinIterator.of(joinMode, elements, element1KeyFunc, element2s, element2KeyFunc, condition, Pair::of));
    }
}
