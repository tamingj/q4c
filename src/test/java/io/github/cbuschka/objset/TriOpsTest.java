package io.github.cbuschka.objset;

import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TriOpsTest {

  @Mock
  private Person person1;
  @Mock
  private Person person2;
  @Mock
  private Address address1;
  @Mock
  private Address address2;
  @Mock
  private Order order1;

  private List<Triple<Person, Address, Order>> result;

  @Test
  void joinThreeEntities() {
    givenPerson1WithTwoAddressesAndOrder();
    givenPerson2WithoutAddressesAndOrder();

    whenQueried((persons, addresses, orders) -> ObjectQuery.selectFrom(persons)
        .join(addresses).on(Person::getId, Address::getPersonId)
        .join(orders).on((person, address) -> person.getId(), Order::getPersonId)
        .toList());

    assertThat(result).containsExactly(Triple.of(person1, address1, order1),
        Triple.of(person1, address2, order1));
  }

  private void whenQueried(TriFunction<Iterable<Person>, Iterable<Address>, Iterable<Order>, List<Triple<Person, Address, Order>>> func) {
    List<Person> persons = List.of(person1, person2);
    List<Address> addresses = List.of(address1, address2);
    List<Order> orders = List.of(order1);
    this.result = func.apply(persons, addresses, orders);
  }

  private void givenPerson2WithoutAddressesAndOrder() {
    when(person2.getId()).thenReturn(2L);
  }

  private void givenPerson1WithTwoAddressesAndOrder() {
    when(person1.getId()).thenReturn(1L);
    when(address1.getPersonId()).thenReturn(1L);
    when(address2.getPersonId()).thenReturn(1L);
    when(order1.getPersonId()).thenReturn(1L);
  }

  @Test
  void filteredOfThreeEntities() {
    givenPerson1WithTwoAddressesAndOrder();
    givenPerson2WithoutAddressesAndOrder();

    whenQueried((persons, addresses, orders) -> ObjectQuery.selectFrom(persons)
        .join(addresses).on(Person::getId, Address::getPersonId)
        .join(orders).on((person, address) -> person.getId(), Order::getPersonId)
        .where((i, s, ss) -> s != address2)
        .toList());

    assertThat(result).containsExactly(Triple.of(person1, address1, order1));
  }
}
