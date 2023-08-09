package io.github.cbuschka.objset;

import java.util.function.Function;

public interface FilterableUniSelect<Element> extends UniSelect<Element> {
    UniSelect<Element> where(Function<Element, Boolean> condition);
}

