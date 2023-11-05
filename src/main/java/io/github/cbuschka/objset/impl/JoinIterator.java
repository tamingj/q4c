package io.github.cbuschka.objset.impl;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class JoinIterator<Left, Right, Key, Result> implements Iterator<Result> {

    private final BiFunction<Left, Right, Result> resultMapFunction;
    private final List<Result> buffer = new LinkedList<>();

    public static <Element1, Element2, Key, Result> Iterable<Result> wrap(JoinMode joinMode,
                                                                          Iterator<Element1> element1s,
                                                                          Function<Element1, Key> element1KeyFunc,
                                                                          Iterator<Element2> element2s,
                                                                          Function<Element2, Key> element2KeyFunc,
                                                                          BiFunction<Element1, Element2, Result> resultMapFunction) {

        switch (joinMode) {
            case INNER:
                return () -> new InnerJoinIterator<>(element1s, element1KeyFunc, element2s, element2KeyFunc, resultMapFunction);
            case LEFT_OUTER:
                return () -> new LeftOuterJoinIterator<>(element1s, element1KeyFunc, element2s, element2KeyFunc, resultMapFunction);
            case RIGHT_OUTER:
                return () -> new RightOuterJoinIterator<>(element1s, element1KeyFunc, element2s, element2KeyFunc, resultMapFunction);
            case FULL_OUTER:
                return () -> new FullOuterJoinIterator<>(element1s, element1KeyFunc, element2s, element2KeyFunc, resultMapFunction);
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
        Map<Key, List<Element>> elementByKeyMap = new HashMap<>();
        while (elements.hasNext()) {
            Element element = elements.next();
            Key key = keyFunction.apply(element);
            elementByKeyMap
                    .computeIfAbsent(key, k -> new ArrayList<>())
                    .add(element);
        }
        return elementByKeyMap;
    }

    protected abstract boolean fill();

    protected void addToBuffer(Left left, Right right) {
        Result entryToAdd = resultMapFunction.apply(left, right);
        buffer.add(entryToAdd);
    }
}
