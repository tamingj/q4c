package io.github.tamingj.q4c;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface BiSelectJoin<E1, E2, E3> {
    <Key> FilterableTriSelect<E1, E2, E3> on(BiFunction<E1, E2, Key> element1KeyFunc, Function<E3, Key> element2KeyFunc);
    <Key> FilterableTriSelect<E1, E2, E3> on(BiFunction<E1, E2, Key> element1KeyFunc, Function<E3, Key> element2KeyFunc, TriPredicate<E1, E2, E3> condition);
}
