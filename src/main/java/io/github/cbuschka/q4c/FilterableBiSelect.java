package io.github.cbuschka.q4c;

public interface FilterableBiSelect<E1, E2> extends BiSelect<E1, E2> {
    BiSelect<E1, E2> where(BiPredicate<E1, E2> condition);
}

