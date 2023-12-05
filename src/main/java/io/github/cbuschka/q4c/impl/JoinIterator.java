package io.github.cbuschka.q4c.impl;

import io.github.cbuschka.q4c.BiPredicate;
import io.github.cbuschka.q4c.BiPredicates;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class JoinIterator<Left, Right, Key, Result> implements Iterator<Result> {

    private BiPredicate<Left, Right> condition;
    private final BiFunction<Left, Right, Result> resultMapFunction;
    private final List<Result> buffer = new LinkedList<>();

    public static <Left, Right, Key, Result> Iterable<Result> of(JoinMode joinMode, Iterable<Left> lefts, Function<Left, Key> leftKeyFunction, Iterable<Right> rights, Function<Right, Key> rightKeyFunction, BiPredicate<Left, Right> condition, BiFunction<Left, Right, Result> resultMapFunction) {
        switch (joinMode) {
            case INNER:
                return forInnerJoin(lefts, leftKeyFunction, rights, rightKeyFunction, condition, resultMapFunction);
            case FULL_OUTER:
                return forFullOuterJoin(lefts, leftKeyFunction, rights, rightKeyFunction, condition, resultMapFunction);
            case LEFT_OUTER:
                return forLeftOuterJoin(lefts, leftKeyFunction, rights, rightKeyFunction, condition, resultMapFunction);
            case RIGHT_OUTER:
                return forRightOuterJoin(lefts, leftKeyFunction, rights, rightKeyFunction, condition, resultMapFunction);
            default:
                throw new IllegalArgumentException("Invalid join mode: " + joinMode + ".");
        }
    }

    public static <Left, Right, Key, Result> Iterable<Result> forFullOuterJoin(Iterable<Left> lefts, Function<Left, Key> leftKeyFunction, Iterable<Right> rights, Function<Right, Key> rightKeyFunction, BiPredicate<Left, Right> condition, BiFunction<Left, Right, Result> resultMapFunction) {
        return () -> new FullOuterJoinIterator<>(lefts, leftKeyFunction, rights, rightKeyFunction, condition, resultMapFunction);
    }

    public static <Left, Right, Key, Result> Iterable<Result> forRightOuterJoin(Iterable<Left> lefts, Function<Left, Key> leftKeyFunction, Iterable<Right> rights, Function<Right, Key> rightKeyFunction, BiPredicate<Left, Right> condition, BiFunction<Left, Right, Result> resultMapFunction) {
        return () -> new RightOuterJoinIterator<>(lefts, leftKeyFunction, rights, rightKeyFunction, condition, resultMapFunction);
    }

    public static <Left, Right, Key, Result> Iterable<Result> forLeftOuterJoin(Iterable<Left> lefts, Function<Left, Key> leftKeyFunction, Iterable<Right> rights, Function<Right, Key> rightKeyFunction, BiPredicate<Left, Right> condition, BiFunction<Left, Right, Result> resultMapFunction) {
        return () -> new LeftOuterJoinIterator<>(lefts, leftKeyFunction, rights, rightKeyFunction, condition, resultMapFunction);
    }

    public static <Left, Right, Key, Result> Iterable<Result> forInnerJoin(Iterable<Left> lefts, Function<Left, Key> leftKeyFunction, Iterable<Right> rights, Function<Right, Key> rightKeyFunction, BiPredicate<Left, Right> condition, BiFunction<Left, Right, Result> resultMapFunction) {
        return () -> new InnerJoinIterator<>(lefts, leftKeyFunction, rights, rightKeyFunction, condition, resultMapFunction);
    }

    protected JoinIterator(BiPredicate<Left, Right> condition, BiFunction<Left, Right, Result> resultMapFunction) {
        this.condition = condition;
        this.resultMapFunction = resultMapFunction;
    }

    @Override
    public boolean hasNext() {
        while (buffer.isEmpty()) {
            boolean more = fill();
            if (!more) {
                break;
            }
        }

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

    protected <Element> Map<Key, List<Element>> getElementsByKeyMap(Iterable<Element> elements, Function<Element, Key> keyFunction) {
        Map<Key, List<Element>> elementsByKey = new HashMap<>();
        for (Element element : elements) {
            Key key = keyFunction.apply(element);
            elementsByKey
                    .computeIfAbsent(key, k -> new ArrayList<>())
                    .add(element);
        }
        return elementsByKey;
    }

    protected boolean matchesCondition(Left left, Right right) {
        return condition.test(left, right);
    }

    protected abstract boolean fill();

    protected void addToBufferIfMatchesCondition(Left left, Right right) {
        if( matchesCondition(left, right) ) {
            addToBuffer(left, right);
        }
    }

    protected void addToBuffer(Left left, Right right) {
        Result entryToAdd = resultMapFunction.apply(left, right);
        buffer.add(entryToAdd);
    }
}
