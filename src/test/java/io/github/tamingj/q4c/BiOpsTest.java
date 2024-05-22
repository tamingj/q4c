package io.github.tamingj.q4c;

import java.util.function.BiFunction;

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
public class BiOpsTest {

  @Mock
  private Person person1;
  @Mock
  private Person person2;
  @Mock
  private Address address1;
  @Mock
  private Address address2;
  private List<Pair<Person, Address>> result;

  @Test
  void joinTwoEntities() {
    givenIsPerson1WithTwoAddresses();
    givenIsPerson2WithoutAddress();

    whenQueried((persons, addresses) -> Queries.selectFrom(persons)
            .join(addresses).on(Person::getId, Address::getPersonId)
            .toList());

    assertThat(result).containsExactly(Pair.of(person1, address1), Pair.of(person1, address2));
  }

  private void givenIsPerson2WithoutAddress() {
    when(person2.getId()).thenReturn(2L);
  }

  private void givenIsPerson1WithTwoAddresses() {
    when(person1.getId()).thenReturn(1L);
    when(address1.getPersonId()).thenReturn(1L);
    when(address2.getPersonId()).thenReturn(1L);
  }

  @Test
  void leftOuterJoinTwoEntities() {
    givenIsPerson1WithTwoAddresses();
    givenIsPerson2WithoutAddress();

    whenQueried((persons, addresses) -> Queries.selectFrom(persons)
            .leftOuterJoin(addresses).on(Person::getId, Address::getPersonId)
            .toList());

    assertThat(result).containsExactly(Pair.of(person1, address1), Pair.of(person1, address2),
        Pair.of(person2, null));
  }

  @Test
  void filteredOfTwoEntities() {
    givenIsPerson1WithTwoAddresses();
    givenIsPerson2WithoutAddress();

    whenQueried((persons, addresses) -> Queries.selectFrom(persons)
        .leftOuterJoin(addresses).on(Person::getId, Address::getPersonId)
        .where((person, address) -> address != address2)
        .toList());

    assertThat(result).containsExactly(Pair.of(person1, address1), Pair.of(person2, null));
  }

  private void whenQueried(BiFunction<List<Person>, List<Address>, List<Pair<Person, Address>>> func) {
    List<Person> persons = List.of(person1, person2);
    List<Address> addresses = List.of(address1, address2);
    this.result = func.apply(persons, addresses);
  }
}
