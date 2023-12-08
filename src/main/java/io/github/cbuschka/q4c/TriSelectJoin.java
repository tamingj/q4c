package io.github.cbuschka.q4c;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface TriSelectJoin<Element1, Element2, Element3, Element4> {
    <Key> FilterableQuadriSelect<Element1, Element2, Element3, Element4> on(TriFunction<Element1, Element2, Element3, Key> element1KeyFunc, Function<Element4, Key> element2KeyFunc);

    <Key> FilterableQuadriSelect<Element1, Element2, Element3, Element4> on(TriFunction<Element1, Element2, Element3, Key> element1KeyFunc, Function<Element4, Key> element2KeyFunc, QuadriPredicate<Element1, Element2, Element3, Element4> condition);
}
