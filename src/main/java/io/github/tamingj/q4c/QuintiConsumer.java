package io.github.tamingj.q4c;

@FunctionalInterface
public interface QuintiConsumer<E1, E2, E3, E4, E5> {
    void accept(E1 element1, E2 element2, E3 element3, E4 element4, E5 element5);
}
