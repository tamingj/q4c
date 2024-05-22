package io.github.tamingj.q4c;

import java.util.function.Function;

public interface UniSelectJoin<E1, E2> {
    <Key> FilterableBiSelect<E1, E2> on(Function<E1, Key> element1KeyFunc, Function<E2, Key> element2KeyFunc);

    <Key> FilterableBiSelect<E1, E2> on(Function<E1, Key> element1KeyFunc, Function<E2, Key> element2KeyFunc,
                                                    BiPredicate<E1, E2> condition);
}
