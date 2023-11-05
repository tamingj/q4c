package io.github.cbuschka.objset;

public interface FilterableBiSelect<Element1, Element2> extends BiSelect<Element1, Element2> {
    BiSelect<Element1, Element2> where(BiPredicate<Element1, Element2> condition);
}

