package io.github.cbuschka.objset.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

class FilteredIterator<E> implements Iterator<E> {
    private final Iterator<E> source;
    private final Function<E, Boolean> filter;
    private Object[] buf;

    static <E> Iterable<E> filter(Iterable<E> source, Function<E, Boolean> filter) {
        return () -> new FilteredIterator<>(source.iterator(), filter);
    }

    private FilteredIterator(Iterator<E> source, Function<E, Boolean> filter) {
        this.source = source;
        this.filter = filter;
    }

    @Override
    public boolean hasNext() {
        fill();
        return buf != null;
    }

    @Override
    public E next() {
        fill();
        if (buf == null) {
            throw new NoSuchElementException();
        }

        @SuppressWarnings("unchecked")
        E element = (E) buf[0];
        buf = null;
        return element;
    }

    private void fill() {
        while (buf == null) {
            if (!this.source.hasNext()) {
                return;
            }

            E next = this.source.next();
            if (filter.apply(next) == Boolean.TRUE) {
                buf = new Object[]{next};
            }
        }
    }
}
