package io.github.cbuschka.objset;

import io.github.cbuschka.objset.impl.ObjectSetImpl;
import java.util.stream.Stream;

public interface ObjectSet {
    static ObjectSet empty() {
        return new ObjectSetImpl();
    }

    static <Element> ObjectSet of(Class<Element> type, Iterable<Element> elements) {
        return new ObjectSetImpl().with(type, elements);
    }

    <Element> ObjectSet with(Class<Element> type, Stream<Element> elements);

    <Element> ObjectSet with(Class<Element> type, Iterable<Element> elements);

    <Element> FilterableUniSelect<Element> select(Class<Element> type);
}
