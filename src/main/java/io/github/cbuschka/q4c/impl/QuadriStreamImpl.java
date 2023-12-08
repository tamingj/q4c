package io.github.cbuschka.q4c.impl;

import io.github.cbuschka.q4c.*;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class QuadriStreamImpl<Element1, Element2, Element3, Element4> implements QuadriStream<Element1, Element2, Element3, Element4> {
    private final Stream<Quadruple<Element1, Element2, Element3, Element4>> source;

    public QuadriStreamImpl(Stream<Quadruple<Element1, Element2, Element3, Element4>> source) {
        this.source = source;
    }

    public Stream<Quadruple<Element1, Element2, Element3, Element4>> quadrupleStream() {
        return StreamSupport.stream(source.spliterator(), false);
    }

    @Override
    public void forEach(QuadriConsumer<Element1, Element2, Element3, Element4> consumer) {
        quadrupleStream()
                .forEach((t) -> consumer.accept(t.element1(), t.element2(), t.element3(), t.element4()));
    }

    @Override
    public <Result> Stream<Result> map(QuadriFunction<Element1, Element2, Element3, Element4, Result> mapper) {
        return quadrupleStream().map((t) -> mapper.apply(t.element1(), t.element2(), t.element3(), t.element4()));
    }

    @Override
    public QuadriStream<Element1, Element2, Element3, Element4> filter(QuadriPredicate<Element1, Element2, Element3, Element4> condition) {
        return new QuadriStreamImpl<>(source.filter(pair -> condition.test(pair.element1(), pair.element2(), pair.element3(), pair.element4())));
    }

    @Override
    public QuadriStream<Element1, Element2, Element3, Element4> peek(QuadriConsumer<Element1, Element2, Element3, Element4> consumer) {
        return new QuadriStreamImpl<>(source.peek(pair -> consumer.accept(pair.element1(), pair.element2(), pair.element3(), pair.element4())));
    }
}
