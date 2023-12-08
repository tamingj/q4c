package io.github.cbuschka.q4c;

public interface FilterableTriSelect<E1, E2, E3> extends TriSelect<E1, E2, E3> {
    TriSelect<E1, E2, E3> where(TriPredicate<E1, E2, E3> condition);
}

