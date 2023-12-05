package io.github.cbuschka.q4c;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface BiSelectJoin<Element1, Element2, Element3> {
    <Key> FilterableTriSelect<Element1, Element2, Element3> on(BiFunction<Element1, Element2, Key> element1KeyFunc, Function<Element3, Key> element2KeyFunc);
    <Key> FilterableTriSelect<Element1, Element2, Element3> on(BiFunction<Element1, Element2, Key> element1KeyFunc, Function<Element3, Key> element2KeyFunc, TriPredicate<Element1, Element2, Element3> condition);
}
