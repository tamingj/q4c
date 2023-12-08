package io.github.cbuschka.q4c;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Quadruple<Element1, Element2, Element3, Element4> {

  private final Element1 element1;
  private final Element2 element2;
  private final Element3 element3;
  private final Element4 element4;

  public static <Element1, Element2, Element3, Element4> Quadruple<Element1, Element2, Element3, Element4> of(
      Element1 element1, Element2 element2, Element3 element3, Element4 element4) {
    return new Quadruple<>(element1, element2, element3, element4);
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

  public Element4 element4() {
    return element4;
  }
}
