package io.github.cbuschka.objset.impl;

import io.github.cbuschka.objset.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class UniSelectImpl<Element1> implements FilterableUniSelect<Element1> {
    private final ObjectSetImpl objectSet;
    private final Iterable<Element1> elements;

    public UniSelectImpl(ObjectSetImpl objectSet, Iterable<Element1> elements) {
        this.objectSet = objectSet;
        this.elements = elements;
    }

    @Override
    public <Element2, KeyType> FilterableBiSelect<Element1, Element2> join(Class<Element2> element2Type, Function<Element1, KeyType> element1KeyFunc, Function<Element2, KeyType> element2KeyFunc) {
        List<Element2> right = objectSet.getElementsFor(element2Type);
        return new BiSelectImpl<>(objectSet, Joins.innerJoin(elements, element1KeyFunc, right, element2KeyFunc, Pair::of));
    }

    @Override
    public <Element2, KeyType> BiSelect<Element1, Element2> leftOuterJoin(Class<Element2> element2Type, Function<Element1, KeyType> element1KeyFunc, Function<Element2, KeyType> element2KeyFunc) {
        List<Element2> right = objectSet.getElementsFor(element2Type);
        return new BiSelectImpl<>(objectSet, Joins.leftOuterJoin(elements, element1KeyFunc, right, element2KeyFunc, Pair::of));
    }

    @Override
    public UniSelect<Element1> where(Function<Element1, Boolean> condition) {
        return new UniSelectImpl<>(objectSet, FilteredIterator.filter(this.elements, condition));
    }

    @Override
    public Stream<Element1> stream() {
        return StreamSupport.stream(elements.spliterator(), false);
    }
}
