package io.github.cbuschka.q4c;

@FunctionalInterface
public interface TriConsumer<E1, E2, E3> {
    void accept(E1 element1, E2 element2, E3 element3);
}
