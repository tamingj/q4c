package io.github.cbuschka.q4c.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class InnerJoinIterator<Left, Right, Key, Result> extends JoinIterator<Left, Right, Key, Result> {
    private final Iterator<Left> lefts;
    private final Function<Left, Key> leftKeyFunc;
    private final Map<Key, List<Right>> rights;

    public InnerJoinIterator(Iterable<Left> lefts, Function<Left, Key> leftKeyFunc, Iterable<Right> rights, Function<Right, Key> rightKeyFunc, BiFunction<Left, Right, Result> resultMapFunction) {
        super(resultMapFunction);
        this.lefts = lefts.iterator();
        this.leftKeyFunc = leftKeyFunc;
        this.rights = getElementsByKeyMap(rights, rightKeyFunc);
    }

    @Override
    protected boolean fill() {
        if (!lefts.hasNext()) {
            return false;
        }

        Left left = lefts.next();
        Key key = leftKeyFunc.apply(left);
        List<Right> rights = this.rights.getOrDefault(key, Collections.emptyList());
        for (Right right : rights) {
            addToBuffer(left, right);
        }

        return true;
    }
}
