package io.github.cbuschka.objset.impl;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static io.github.cbuschka.objset.impl.JoinIterator.JoinMode.FULL_OUTER;
import static io.github.cbuschka.objset.impl.JoinIterator.JoinMode.INNER;
import static io.github.cbuschka.objset.impl.JoinIterator.JoinMode.LEFT_OUTER;
import static io.github.cbuschka.objset.impl.JoinIterator.JoinMode.RIGHT_OUTER;

public class JoinIterator<Left, Right, Key, Result> implements Iterator<Result> {

    public enum JoinMode {
        INNER,
        LEFT_OUTER,
        RIGHT_OUTER,
        FULL_OUTER;
    }

    private final JoinMode joinMode;
    private final Iterator<Left> elementType1Iterator;
    private final Iterator<Right> elementType2Iterator;
    private final Function<Left, Key> elementType1KeyFunction;
    private final Function<Right, Key> elementType2KeyFunction;
    private final Map<Key, List<Left>> element1ByKeyMap;
    private final Map<Key, List<Right>> element2ByKeyMap;
    private final BiFunction<Left, Right, Result> mapFunction;
    private final List<Result> buffer = new LinkedList<>();

    public static <Element1, Element2, Key, Result> Iterable<Result> wrap(JoinMode joinMode,
                                                                          Iterator<Element1> element1s,
                                                                          Function<Element1, Key> element1KeyFunc,
                                                                          Iterator<Element2> element2s,
                                                                          Function<Element2, Key> element2KeyFunc,
                                                                          BiFunction<Element1, Element2, Result> mapFunction) {
        return () -> {
            List<Element1> leftList = new ArrayList<>();
            element1s.forEachRemaining(leftList::add);
            List<Element2> rightList = new ArrayList<>();
            element2s.forEachRemaining(rightList::add);

            Map<Key, List<Element2>> element2ByKeyMap = getElementsByKeyMap(rightList, element2KeyFunc);
            Map<Key, List<Element1>> element1ByKeyMap = getElementsByKeyMap(leftList, element1KeyFunc);

            return new JoinIterator<>(joinMode, leftList.iterator(), rightList.iterator(), element1KeyFunc, element2KeyFunc, element1ByKeyMap, element2ByKeyMap, mapFunction);
        };
    }

    private static <E, K> Map<K, List<E>> getElementsByKeyMap(List<E> elementList, Function<E, K> keyFunction) {
        Map<K, List<E>> elementByKeyMap = new HashMap<>();
        for (E element : elementList) {
            K key = keyFunction.apply(element);
            elementByKeyMap
                    .computeIfAbsent(key, k -> new ArrayList<>())
                    .add(element);
        }
        return elementByKeyMap;
    }

    public JoinIterator(JoinMode joinMode,
                        Iterator<Left> elementType1Iterator, Iterator<Right> elementType2Iterator,
                        Function<Left, Key> elementType1KeyFunction, Function<Right, Key> elementType2KeyFunction,
                        Map<Key, List<Left>> element1ByKeyMap, Map<Key, List<Right>> element2ByKeyMap,
                        BiFunction<Left, Right, Result> mapFunction) {
        this.joinMode = joinMode;
        this.elementType1Iterator = elementType1Iterator;
        this.elementType2Iterator = elementType2Iterator;
        this.elementType1KeyFunction = elementType1KeyFunction;
        this.elementType2KeyFunction = elementType2KeyFunction;
        this.element1ByKeyMap = element1ByKeyMap;
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
        boolean canLeftJoin = List.of(INNER, LEFT_OUTER).contains(joinMode) && elementType1Iterator.hasNext();
        boolean canRightJoin = RIGHT_OUTER == joinMode && elementType2Iterator.hasNext();
        boolean canFullJoin = FULL_OUTER == joinMode && (elementType1Iterator.hasNext() || elementType2Iterator.hasNext());
        while (buffer.isEmpty() && (canLeftJoin || canRightJoin || canFullJoin)) {
            Left nextLeft = elementType1Iterator.hasNext() ? elementType1Iterator.next() : null;
            Key element1Key = nextLeft != null ? elementType1KeyFunction.apply(nextLeft) : null;
            List<Right> rights = element1Key != null ? element2ByKeyMap.get(element1Key) : Collections.emptyList();

            Right nextRight = elementType2Iterator.hasNext() ? elementType2Iterator.next() : null;
            Key element2Key = nextRight != null ? elementType2KeyFunction.apply(nextRight) : null;
            List<Left> lefts = element2Key != null ? element1ByKeyMap.get(element2Key) : Collections.emptyList();

            switch (joinMode) {
                case INNER:
                    fillLeftInner(nextLeft, rights);
                    break;
                case LEFT_OUTER:
                    fillLeftOuter(nextLeft, rights);
                    break;
                case RIGHT_OUTER:
                    fillRightOuter(nextRight, lefts);
                    break;
                case FULL_OUTER:
                    fillFullOuter(nextLeft, nextRight, lefts, rights);
                    break;
                default:
                    throw new IllegalArgumentException(String.format("Invalid join type %s.", joinMode));
            }
        }
    }

    private void fillLeftInner(Left nextLeft, List<Right> rights) {

        if (rights != null && !rights.isEmpty()) {
            for (Right nextRight : rights) {
                addToBufferIfNotExists(nextLeft, nextRight);
            }
        }
    }

    private void fillLeftOuter(Left nextLeft, List<Right> rights) {
        if (rights == null || rights.isEmpty()) {
            addToBufferIfNotExists(nextLeft, null);
            return;
        }

        for (Right nextRight : rights) {
            addToBufferIfNotExists(nextLeft, nextRight);
        }
    }

    private void fillRightOuter(Right nextRight, List<Left> lefts) {
        if (lefts == null || lefts.isEmpty()) {
            addToBufferIfNotExists(null, nextRight);
            return;
        }

        for (Left nextLeft : lefts) {
            addToBufferIfNotExists(nextLeft, nextRight);
        }
    }

    private void fillFullOuter(Left nextLeft, Right nextRight, List<Left> lefts, List<Right> rights) {
        if (nextLeft != null) {
            fillLeftOuter(nextLeft, rights);
        }
        if (nextRight != null) {
            fillRightOuter(nextRight, lefts);
        }
    }

    private void addToBufferIfNotExists(Left left, Right right) {
        Result entryToAdd = mapFunction.apply(left, right);
        if (!buffer.contains(entryToAdd)) {
            buffer.add(entryToAdd);
        }
    }
}
