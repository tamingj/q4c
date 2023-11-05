package io.github.cbuschka.objset.impl;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class FullOuterJoinIterator<Element1, Element2, Key, Result> extends JoinIterator<Element1, Element2, Key, Result> {
    private final Iterator<Map.Entry<Key, CombinedElements<Element1, Element2>>> combinedElementsIter;

    public FullOuterJoinIterator(Iterator<Element1> element1s, Function<Element1, Key> element1KeyFunc, Iterator<Element2> element2s, Function<Element2, Key> element2KeyFunc, BiFunction<Element1, Element2, Result> resultMapFunction) {
        super(resultMapFunction);

        var combinedElementsByKeyMap = index(element1s, element1KeyFunc, element2s, element2KeyFunc);
        combinedElementsIter = combinedElementsByKeyMap.entrySet().iterator();
    }

    private Map<Key, CombinedElements<Element1, Element2>> index(Iterator<Element1> element1s, Function<Element1, Key> element1KeyFunc, Iterator<Element2> element2s, Function<Element2, Key> element2KeyFunc) {
        Map<Key, CombinedElements<Element1, Element2>> combinedElementsByKeyMap = new LinkedHashMap<>();

        while (element1s.hasNext()) {
            Element1 element1 = element1s.next();
            Key element1Key = element1KeyFunc.apply(element1);
            combinedElementsByKeyMap.computeIfAbsent(element1Key, (key) -> new CombinedElements<>())
                    .lefts.add(element1);
        }

        while (element2s.hasNext()) {
            Element2 element2 = element2s.next();
            Key element2Key = element2KeyFunc.apply(element2);
            combinedElementsByKeyMap.computeIfAbsent(element2Key, (key) -> new CombinedElements<>())
                    .rights.add(element2);
        }

        return combinedElementsByKeyMap;
    }

    @Override
    protected boolean fill() {
        if (!combinedElementsIter.hasNext()) {
            return false;
        }

        Map.Entry<Key, CombinedElements<Element1, Element2>> combinedElementsEntry = combinedElementsIter.next();
        CombinedElements<Element1, Element2> combinedElements = combinedElementsEntry.getValue();
        if (combinedElements.lefts.isEmpty()) {
            for (Element2 right : combinedElements.rights) {
                addToBuffer(null, right);
            }
        } else if (combinedElements.rights.isEmpty()) {
            for (Element1 left : combinedElements.lefts) {
                addToBuffer(left, null);
            }
        } else {
            for (Element1 left : combinedElements.lefts) {
                for (Element2 right : combinedElements.rights) {
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
