package io.github.cbuschka.q4c;

public interface FilterableQuintiSelect<E1, E2, E3, E4, E5> extends QuintiSelect<E1, E2, E3, E4, E5> {
    QuintiSelect<E1, E2, E3, E4, E5> where(QuintiPredicate<E1, E2, E3, E4, E5> condition);
}

