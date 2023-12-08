package io.github.cbuschka.q4c;

public interface FilterableQuadriSelect<E1, E2, E3, E4> extends QuadriSelect<E1, E2, E3, E4> {
    QuadriSelect<E1, E2, E3, E4> where(QuadriPredicate<E1, E2, E3, E4> condition);
}

