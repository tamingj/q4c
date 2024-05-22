package io.github.tamingj.q4c.impl;

import io.github.tamingj.q4c.*;
import lombok.AllArgsConstructor;

import java.util.function.Function;

@AllArgsConstructor
class UniSelectJoinImpl<E1, E2> implements UniSelectJoin<E1, E2> {
    private final JoinMode joinMode;
    private final Iterable<E1> elements;
    private final Iterable<E2> element2s;

    @Override
    public <Key> FilterableBiSelect<E1, E2> on(Function<E1, Key> element1KeyFunc, Function<E2, Key> element2KeyFunc) {
        return on(element1KeyFunc, element2KeyFunc, BiPredicates.matchAll());
    }

    @Override
    public <Key> FilterableBiSelect<E1, E2> on(Function<E1, Key> element1KeyFunc, Function<E2, Key> element2KeyFunc, BiPredicate<E1, E2> condition) {
        return new BiSelectImpl<>(JoinIterator.of(joinMode, elements, element1KeyFunc, element2s, element2KeyFunc, condition, Pair::of));
    }
}
