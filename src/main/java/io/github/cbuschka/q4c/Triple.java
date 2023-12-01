package io.github.cbuschka.q4c;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Triple<Element1, Element2, Element3> {

  private final Element1 element1;
  private final Element2 element2;
  private final Element3 element3;

  public static <Element1, Element2, Element3> Triple<Element1, Element2, Element3> of(
      Element1 element1, Element2 element2, Element3 element3) {
    return new Triple<>(element1, element2, element3);
  }

  public Element1 element1() {
    return element1;
  }

  public Element2 element2() {
    return element2;
  }

  public Element3 element3() {
    return element3;
  }
}
