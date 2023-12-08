package io.github.cbuschka.q4c;

public interface FilterableTriSelect<Element1, Element2, Element3> extends TriSelect<Element1, Element2, Element3> {
    TriSelect<Element1, Element2, Element3> where(TriPredicate<Element1, Element2, Element3> condition);
}

