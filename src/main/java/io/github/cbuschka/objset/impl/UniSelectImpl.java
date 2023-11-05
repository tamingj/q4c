package io.github.cbuschka.objset.impl;

import io.github.cbuschka.objset.*;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class UniSelectImpl<Element1> implements FilterableUniSelect<Element1> {
    private final Iterable<Element1> elements;

    public UniSelectImpl(Iterable<Element1> elements) {
        this.elements = elements;
    }

    @Override
    public <Element2, KeyType> FilterableBiSelect<Element1, Element2> join(Iterable<Element2> element2s, Function<Element1, KeyType> element1KeyFunc, Function<Element2, KeyType> element2KeyFunc) {
        return new BiSelectImpl<>(JoinIterator.forInnerJoin(elements, element1KeyFunc, element2s, element2KeyFunc, Pair::of));
    }

    @Override
    public <Element2, KeyType> FilterableBiSelect<Element1, Element2> leftOuterJoin(Iterable<Element2> element2s, Function<Element1, KeyType> element1KeyFunc, Function<Element2, KeyType> element2KeyFunc) {
        return new BiSelectImpl<>(JoinIterator.forLeftOuterJoin(elements, element1KeyFunc, element2s, element2KeyFunc, Pair::of));
    }

    @Override
    public <Element2, Key> FilterableBiSelect<Element1, Element2> rightOuterJoin(Iterable<Element2> element2s, Function<Element1, Key> element1KeyFunc, Function<Element2, Key> element2KeyFunc) {
        return new BiSelectImpl<>(JoinIterator.forRightOuterJoin(elements, element1KeyFunc, element2s, element2KeyFunc, Pair::of));
    }

    @Override
    public <Element2, Key> FilterableBiSelect<Element1, Element2> fullOuterJoin(Iterable<Element2> element2s, Function<Element1, Key> element1KeyFunc, Function<Element2, Key> element2KeyFunc) {
        return new BiSelectImpl<>(JoinIterator.forFullOuterJoin(elements, element1KeyFunc, element2s, element2KeyFunc, Pair::of));
    }

    @Override
    public UniSelect<Element1> where(Predicate<Element1> condition) {
        return new UniSelectImpl<>(FilteredIterator.filtered(this.elements, condition));
    }

    @Override
    public Stream<Element1> stream() {
        return StreamSupport.stream(elements.spliterator(), false);
    }
}
