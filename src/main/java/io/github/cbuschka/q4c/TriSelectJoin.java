package io.github.cbuschka.q4c;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface TriSelectJoin<E1, E2, E3, E4> {
    <Key> FilterableQuadriSelect<E1, E2, E3, E4> on(TriFunction<E1, E2, E3, Key> element1KeyFunc, Function<E4, Key> element2KeyFunc);

    <Key> FilterableQuadriSelect<E1, E2, E3, E4> on(TriFunction<E1, E2, E3, Key> element1KeyFunc, Function<E4, Key> element2KeyFunc, QuadriPredicate<E1, E2, E3, E4> condition);
}
