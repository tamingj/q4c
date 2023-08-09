package io.github.cbuschka.objset.impl;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

class JoinIterator<Left, Right, Key, Result> implements Iterator<Result> {

    enum JoinMode {
        LEFT_INNER,
        LEFT_OUTER;
    }

    private final JoinMode joinMode;
    private final Iterator<Left> elementType1Iterator;
    private final Function<Left, Key> elementType1KeyFunction;
    private final Map<Key, List<Right>> element2ByKeyMap;
    private final BiFunction<Left, Right, Result> mapFunction;
    private final List<Result> buffer = new LinkedList<>();

    static <Element1, Element2, Key, Result> Iterable<Result> wrap(JoinMode joinMode,
                                                                   Iterable<Element1> element1s, Function<Element1, Key> element1KeyFunc,
                                                                   Iterable<Element2> element2s, Function<Element2, Key> element2KeyFunc,
                                                                   BiFunction<Element1, Element2, Result> mapFunction) {
        return () -> {
            Map<Key, List<Element2>> element2ByKeyMap = new HashMap<>();
            for (Element2 element2 : element2s) {
                Key key = element2KeyFunc.apply(element2);
                List<Element2> element2sForKey = element2ByKeyMap.computeIfAbsent(key, (k) -> new ArrayList<>());
                element2sForKey.add(element2);
            }

            return new JoinIterator<>(joinMode, element1s.iterator(), element1KeyFunc, element2ByKeyMap,
                    mapFunction);
        };
    }

    private JoinIterator(JoinMode joinMode, Iterator<Left> elementType1Iterator, Function<Left, Key> elementType1KeyFunction, Map<Key, List<Right>> element2ByKeyMap,
                         BiFunction<Left, Right, Result> mapFunction) {
        this.joinMode = joinMode;
        this.elementType1Iterator = elementType1Iterator;
        this.elementType1KeyFunction = elementType1KeyFunction;
        this.element2ByKeyMap = element2ByKeyMap;
        this.mapFunction = mapFunction;
    }

    @Override
    public boolean hasNext() {
        fill();
        return !buffer.isEmpty();
    }

    @Override
    public Result next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return buffer.remove(0);
    }


    private void fill() {
        while (buffer.isEmpty() && elementType1Iterator.hasNext()) {
            Left nextLeft = elementType1Iterator.next();
            Key key = elementType1KeyFunction.apply(nextLeft);
            List<Right> rights = element2ByKeyMap.get(key);
            switch (joinMode) {
                case LEFT_INNER -> fillLeftInner(nextLeft, rights);
                case LEFT_OUTER -> fillLeftOuter(nextLeft, rights);
                default -> throw new IllegalArgumentException("Invalid join type %s.".formatted(joinMode));
            }
        }
    }

    private void fillLeftInner(Left nextLeft, List<Right> rights) {

        if (rights != null && !rights.isEmpty()) {
            for (Right nextRight : rights) {
                buffer.add(mapFunction.apply(nextLeft, nextRight));
            }
        }
    }

    private void fillLeftOuter(Left nextLeft, List<Right> rights) {
        if (rights == null || rights.isEmpty()) {
            buffer.add(mapFunction.apply(nextLeft, null));
            return;
        }

        for (Right nextRight : rights) {
            buffer.add(mapFunction.apply(nextLeft, nextRight));
        }
    }
}