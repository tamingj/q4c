package io.github.cbuschka.objset.impl;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class FullOuterJoinIterator<Left, Right, Key, Result> extends JoinIterator<Left, Right, Key, Result> {
    private final Iterator<Map.Entry<Key, CombinedElements<Left, Right>>> combinedElementsIter;

    public FullOuterJoinIterator(Iterable<Left> lefts, Function<Left, Key> leftKeyFunc, Iterable<Right> rights, Function<Right, Key> rightKeyFunc, BiFunction<Left, Right, Result> resultMapFunc) {
        super(resultMapFunc);

        var combinedElementsByKeyMap = index(lefts.iterator(), leftKeyFunc, rights.iterator(), rightKeyFunc);
        combinedElementsIter = combinedElementsByKeyMap.entrySet().iterator();
    }

    private Map<Key, CombinedElements<Left, Right>> index(Iterator<Left> lefts, Function<Left, Key> leftKeyFunc, Iterator<Right> rights, Function<Right, Key> rightKeyFunc) {
        Map<Key, CombinedElements<Left, Right>> combinedElementsByKeyMap = new LinkedHashMap<>();

        while (lefts.hasNext()) {
            Left left = lefts.next();
            Key key = leftKeyFunc.apply(left);
            combinedElementsByKeyMap.computeIfAbsent(key, (unused) -> new CombinedElements<>())
                    .lefts.add(left);
        }

        while (rights.hasNext()) {
            Right right = rights.next();
            Key key = rightKeyFunc.apply(right);
            combinedElementsByKeyMap.computeIfAbsent(key, (unused) -> new CombinedElements<>())
                    .rights.add(right);
        }

        return combinedElementsByKeyMap;
    }

    @Override
    protected boolean fill() {
        if (!combinedElementsIter.hasNext()) {
            return false;
        }

        Map.Entry<Key, CombinedElements<Left, Right>> combinedElementsEntry = combinedElementsIter.next();
        CombinedElements<Left, Right> combinedElements = combinedElementsEntry.getValue();
        if (combinedElements.lefts.isEmpty()) {
            for (Right right : combinedElements.rights) {
                addToBuffer(null, right);
            }
        } else if (combinedElements.rights.isEmpty()) {
            for (Left left : combinedElements.lefts) {
                addToBuffer(left, null);
            }
        } else {
            for (Left left : combinedElements.lefts) {
                for (Right right : combinedElements.rights) {
                    addToBuffer(left, right);
                }
            }
        }

        return true;
    }

    private static class CombinedElements<Element1, Element2> {
        private final List<Element1> lefts = new ArrayList<>();
        private final List<Element2> rights = new ArrayList<>();
    }
}
