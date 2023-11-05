package io.github.cbuschka.objset.impl;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class JoinIterator<Left, Right, Key, Result> implements Iterator<Result> {

    private final BiFunction<Left, Right, Result> resultMapFunction;
    private final List<Result> buffer = new LinkedList<>();

    public static <Left, Right, Key, Result> Iterable<Result> wrap(JoinMode joinMode,
                                                                   Iterator<Left> lefts,
                                                                   Function<Left, Key> leftKeyFunction,
                                                                   Iterator<Right> rights,
                                                                   Function<Right, Key> rightKeyFunction,
                                                                   BiFunction<Left, Right, Result> resultMapFunction) {

        switch (joinMode) {
            case INNER:
                return () -> new InnerJoinIterator<>(lefts, leftKeyFunction, rights, rightKeyFunction, resultMapFunction);
            case LEFT_OUTER:
                return () -> new LeftOuterJoinIterator<>(lefts, leftKeyFunction, rights, rightKeyFunction, resultMapFunction);
            case RIGHT_OUTER:
                return () -> new RightOuterJoinIterator<>(lefts, leftKeyFunction, rights, rightKeyFunction, resultMapFunction);
            case FULL_OUTER:
                return () -> new FullOuterJoinIterator<>(lefts, leftKeyFunction, rights, rightKeyFunction, resultMapFunction);
            default:
                throw new IllegalArgumentException("Unsupported join mode " + joinMode + ".");
        }
    }

    protected JoinIterator(BiFunction<Left, Right, Result> resultMapFunction) {
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

    protected <Element> Map<Key, List<Element>> getElementsByKeyMap(Iterator<Element> elements, Function<Element, Key> keyFunction) {
        Map<Key, List<Element>> elementsByKey = new HashMap<>();
        while (elements.hasNext()) {
            Element element = elements.next();
            Key key = keyFunction.apply(element);
            elementsByKey
                    .computeIfAbsent(key, k -> new ArrayList<>())
                    .add(element);
        }
        return elementsByKey;
    }

    protected abstract boolean fill();

    protected void addToBuffer(Left left, Right right) {
        Result entryToAdd = resultMapFunction.apply(left, right);
        buffer.add(entryToAdd);
    }
}
