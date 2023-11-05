package io.github.cbuschka.objset.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class RightOuterJoinIterator<Left, Right, Key, Result> extends JoinIterator<Left, Right, Key, Result> {
    private final Map<Key, List<Left>> leftsByKey;
    private final Iterator<Right> rights;
    private final Function<Right, Key> rightKeyFunc;

    public RightOuterJoinIterator(Iterable<Left> element1s, Function<Left, Key> element1KeyFunc, Iterable<Right> rights, Function<Right, Key> rightKeyFunc, BiFunction<Left, Right, Result> resultMapFunction) {
        super(resultMapFunction);
        this.rights = rights.iterator();
        this.rightKeyFunc = rightKeyFunc;
        this.leftsByKey = getElementsByKeyMap(element1s, element1KeyFunc);
    }

    @Override
    protected boolean fill() {
        if (!rights.hasNext()) {
            return false;
        }

        Right right = rights.next();
        Key key = rightKeyFunc.apply(right);
        List<Left> lefts = leftsByKey.getOrDefault(key, Collections.emptyList());
        if (lefts.isEmpty()) {
            addToBuffer(null, right);
        } else {
            for (Left left : lefts) {
                addToBuffer(left, right);
            }
        }

        return true;
    }
}
