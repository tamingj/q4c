package io.github.tamingj.q4c;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Pair<E1, E2> {
    private final E1 element1;
    private final E2 element2;

    public static <E1, E2> Pair<E1, E2> of(E1 element1, E2 element2) {
        return new Pair<>(element1, element2);
    }

    public E1 element1() {
        return element1;
    }

    public E2 element2() {
        return element2;
    }
}
