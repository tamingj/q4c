package io.github.cbuschka.q4c;

public interface FilterableQuadriSelect<Element1, Element2, Element3, Element4> extends QuadriSelect<Element1, Element2, Element3, Element4> {
    QuadriSelect<Element1, Element2, Element3, Element4> where(QuadriPredicate<Element1, Element2, Element3, Element4> condition);
}

