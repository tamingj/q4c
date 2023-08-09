package io.github.cbuschka.objset;

public interface FilterableTriSelect<Element1, Element2, Element3> extends TriSelect<Element1, Element2, Element3> {
    TriSelect<Element1, Element2, Element3> where(TriFunction<Element1, Element2, Element3, Boolean> condition);
}

