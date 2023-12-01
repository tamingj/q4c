package io.github.cbuschka.q4c.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class FilteredIterator<E> implements Iterator<E> {
    private final Iterator<E> source;
    private final Predicate<E> filter;
    private Object[] buf;

    public static <E> Iterable<E> filtered(Iterable<E> source, Predicate<E> filter) {
        return () -> new FilteredIterator<>(source.iterator(), filter);
    }

    public FilteredIterator(Iterator<E> source, Predicate<E> filter) {
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
            if (filter.test(next)) {
                buf = new Object[]{next};
            }
        }
    }
}
