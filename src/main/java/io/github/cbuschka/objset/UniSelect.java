package io.github.cbuschka.objset;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface UniSelect<Element1> {
    <Element2, Key> FilterableBiSelect<Element1, Element2> join(Iterable<Element2> element2s, Function<Element1, Key> element1KeyFunc, Function<Element2, Key> element2KeyFunc);

    <Element2, Key> FilterableBiSelect<Element1, Element2> leftOuterJoin(Iterable<Element2> element2s, Function<Element1, Key> element1KeyFunc, Function<Element2, Key> element2KeyFunc);

    <Element2, Key> FilterableBiSelect<Element1, Element2> rightOuterJoin(Iterable<Element2> element2s, Function<Element1, Key> element1KeyFunc, Function<Element2, Key> element2KeyFunc);

    <Element2, Key> FilterableBiSelect<Element1, Element2> fullOuterJoin(Iterable<Element2> element2s, Function<Element1, Key> element1KeyFunc, Function<Element2, Key> element2KeyFunc);

    Stream<Element1> stream();

    default void forEach(Consumer<Element1> consumer) {
        stream().forEach(consumer);
    }

    default List<Element1> toList() {
        return stream().collect(Collectors.toList());
    }
}

