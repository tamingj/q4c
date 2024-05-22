package io.github.tamingj.q4c;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Quintuple<E1, E2, E3, E4, E5> {

    private final E1 element1;
    private final E2 element2;
    private final E3 element3;
    private final E4 element4;
    private final E5 element5;

    public static <E1, E2, E3, E4, E5> Quintuple<E1, E2, E3, E4, E5> of(
            E1 element1, E2 element2, E3 element3, E4 element4, E5 element5) {
        return new Quintuple<>(element1, element2, element3, element4, element5);
    }

    public E1 element1() {
        return element1;
    }

    public E2 element2() {
        return element2;
    }

    public E3 element3() {
        return element3;
    }

    public E4 element4() {
        return element4;
    }

    public E5 element5() {
        return element5;
    }
}
