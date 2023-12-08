package io.github.cbuschka.q4c;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Triple<E1, E2, E3> {

  private final E1 element1;
  private final E2 element2;
  private final E3 element3;

  public static <E1, E2, E3> Triple<E1, E2, E3> of(
      E1 element1, E2 element2, E3 element3) {
    return new Triple<>(element1, element2, element3);
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
}
