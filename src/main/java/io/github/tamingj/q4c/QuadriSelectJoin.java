package io.github.tamingj.q4c;

import java.util.function.Function;

public interface QuadriSelectJoin<E1, E2, E3, E4, E5> {
    <Key> FilterableQuintiSelect<E1, E2, E3, E4, E5> on(QuadriFunction<E1, E2, E3, E4, Key> leftKeyFunc, Function<E5, Key> rightKeyFunc);

    <Key> FilterableQuintiSelect<E1, E2, E3, E4, E5> on(QuadriFunction<E1, E2, E3, E4, Key> leftKeyFunc, Function<E5, Key> rightKeyFunc, QuintiPredicate<E1, E2, E3, E4, E5> condition);
}
