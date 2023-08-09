package io.github.cbuschka.objset;

import java.util.function.BiFunction;

public interface FilterableBiSelect<Element1, Element2> extends BiSelect<Element1, Element2> {
    BiSelect<Element1, Element2> where(BiFunction<Element1, Element2, Boolean> condition);
}

