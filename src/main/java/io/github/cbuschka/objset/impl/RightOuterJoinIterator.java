package io.github.cbuschka.objset.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class RightOuterJoinIterator<Element1, Element2, Key, Result> extends JoinIterator<Element1, Element2, Key, Result> {
    private final Map<Key, List<Element1>> element1sByKey;
    private Function<Element1, Key> element1KeyFunc;
    private Iterator<Element2> element2s;
    private Function<Element2, Key> element2KeyFunc;

    public RightOuterJoinIterator(Iterator<Element1> element1s, Function<Element1, Key> element1KeyFunc, Iterator<Element2> element2s, Function<Element2, Key> element2KeyFunc, BiFunction<Element1, Element2, Result> resultMapFunction) {
        super(resultMapFunction);
        this.element1KeyFunc = element1KeyFunc;
        this.element2s = element2s;
        this.element2KeyFunc = element2KeyFunc;
        this.element1sByKey = getElementsByKeyMap(element1s, element1KeyFunc);
    }

    @Override
    protected boolean fill() {
        if (!element2s.hasNext()) {
            return false;
        }

        Element2 right = element2s.next();
        Key element2Key = element2KeyFunc.apply(right);
        List<Element1> lefts = element1sByKey.get(element2Key);
        if (lefts == null || lefts.isEmpty()) {
            addToBuffer(null, right);
        } else {
            for (Element1 left : lefts) {
                addToBuffer(left, right);
            }
        }

        return true;
    }
}
