package io.github.cbuschka.objset;

import io.github.cbuschka.objset.impl.ObjectSetImpl;

public interface ObjectSet {
    static <Element> ObjectSet of(Class<Element> type, Iterable<Element> elements) {
        return new ObjectSetImpl().with(type, elements);
    }

    <Element> ObjectSet with(Class<Element> type, Iterable<Element> elements);

    <Element> FilterableUniSelect<Element> select(Class<Element> type);
}
