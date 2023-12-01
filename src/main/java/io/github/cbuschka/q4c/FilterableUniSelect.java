package io.github.cbuschka.q4c;

import java.util.function.Predicate;

public interface FilterableUniSelect<Element> extends UniSelect<Element> {
    UniSelect<Element> where(Predicate<Element> condition);
}

