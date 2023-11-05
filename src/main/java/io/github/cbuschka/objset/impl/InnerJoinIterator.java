package io.github.cbuschka.objset.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class InnerJoinIterator<Element1, Element2, Key, Result> extends JoinIterator<Element1, Element2, Key, Result> {
    private final Iterator<Element1> element1s;
    private final Function<Element1, Key> element1KeyFunc;
    private final Map<Key, List<Element2>> element2sByKey;

    public InnerJoinIterator(Iterator<Element1> element1s, Function<Element1, Key> element1KeyFunc, Iterator<Element2> element2s, Function<Element2, Key> element2KeyFunc, BiFunction<Element1, Element2, Result> resultMapFunction) {
        super(resultMapFunction);
        this.element1s = element1s;
        this.element1KeyFunc = element1KeyFunc;
        this.element2sByKey = getElementsByKeyMap(element2s, element2KeyFunc);
    }

    @Override
    protected boolean fill() {
        if (!element1s.hasNext()) {
            return false;
        }

        Element1 left = element1s.next();
        Key element1Key = element1KeyFunc.apply(left);
        List<Element2> rights = element2sByKey.get(element1Key);
        if (rights != null && !rights.isEmpty()) {
            for (Element2 right : rights) {
                addToBuffer(left, right);
            }
        }

        return true;
    }
}
