package io.github.cbuschka.objset;

import java.util.function.Function;

public interface UniSelectJoin<Element1, Element2> {
    <Key> FilterableBiSelect<Element1, Element2> on(Function<Element1, Key> element1KeyFunc, Function<Element2, Key> element2KeyFunc);
}
