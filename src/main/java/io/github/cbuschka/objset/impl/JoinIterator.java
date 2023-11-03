package io.github.cbuschka.objset.impl;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class JoinIterator<Left, Right, Key, Result> implements Iterator<Result> {

    public enum JoinMode {
        INNER,
        LEFT_OUTER;
    }

    private final JoinMode joinMode;
    private final Iterator<Left> elementType1Iterator;
    private final Function<Left, Key> elementType1KeyFunction;
    private final Map<Key, List<Right>> element2ByKeyMap;
    private final BiFunction<Left, Right, Result> mapFunction;
    private final List<Result> buffer = new LinkedList<>();

    public static <Element1, Element2, Key, Result> Iterable<Result> wrap(JoinMode joinMode,
                                                                          Iterator<Element1> element1s, Function<Element1, Key> element1KeyFunc,
                                                                          Iterator<Element2> element2s, Function<Element2, Key> element2KeyFunc,
                                                                          BiFunction<Element1, Element2, Result> mapFunction) {
        return () -> {
            Map<Key, List<Element2>> element2ByKeyMap = new HashMap<>();
            for (Element2 element2 : (Iterable<Element2>)() -> element2s) {
                Key key = element2KeyFunc.apply(element2);
                List<Element2> element2sForKey = element2ByKeyMap.computeIfAbsent(key, (k) -> new ArrayList<>());
                element2sForKey.add(element2);
            }

            return new JoinIterator<>(joinMode, element1s, element1KeyFunc, element2ByKeyMap,
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

    public Stream<Result> stream() {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(this, Spliterator.ORDERED),
                false);
    }

    private void fill() {
        while (buffer.isEmpty() && elementType1Iterator.hasNext()) {
            Left nextLeft = elementType1Iterator.next();
            Key key = elementType1KeyFunction.apply(nextLeft);
            List<Right> rights = element2ByKeyMap.get(key);
            switch (joinMode) {
                case INNER:
                    fillLeftInner(nextLeft, rights);
                    break;
                case LEFT_OUTER:
                    fillLeftOuter(nextLeft, rights);
                    break;
                default:
                    throw new IllegalArgumentException(String.format("Invalid join type %s.", joinMode));
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
