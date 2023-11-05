package io.github.cbuschka.objset.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class LeftOuterJoinIterator<Left, Right, Key, Result> extends JoinIterator<Left, Right, Key, Result> {
    private final Iterator<Left> lefts;
    private final Function<Left, Key> leftKeyFunc;
    private final Map<Key, List<Right>> rightsByKey;

    public LeftOuterJoinIterator(Iterator<Left> lefts, Function<Left, Key> leftKeyFunc, Iterator<Right> element2s, Function<Right, Key> element2KeyFunc, BiFunction<Left, Right, Result> resultMapFunction) {
        super(resultMapFunction);
        this.lefts = lefts;
        this.leftKeyFunc = leftKeyFunc;
        this.rightsByKey = getElementsByKeyMap(element2s, element2KeyFunc);
    }

    @Override
    protected boolean fill() {
        if (!lefts.hasNext()) {
            return false;
        }

        Left left = lefts.next();
        Key key = leftKeyFunc.apply(left);
        List<Right> rights = rightsByKey.getOrDefault(key, Collections.emptyList());
        if (rights.isEmpty()) {
            addToBuffer(left, null);
        } else {
            for (Right right : rights) {
                addToBuffer(left, right);
            }
        }

        return true;
    }
}
