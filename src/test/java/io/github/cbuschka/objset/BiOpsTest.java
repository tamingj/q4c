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

    whenQueried(objSet -> objSet.select(Person.class)
        .join(Address.class, Person::getId, Address::getPersonId)
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

    whenQueried(objSet -> objSet.select(Person.class)
        .leftOuterJoin(Address.class, Person::getId, Address::getPersonId)
        .toList());

    assertThat(result).containsExactly(Pair.of(person1, address1), Pair.of(person1, address2),
        Pair.of(person2, null));
  }

  @Test
  void filteredOfTwoEntities() {
    givenIsPerson1WithTwoAddresses();
    givenIsPerson2WithoutAddress();

    whenQueried(objSet -> objSet.select(Person.class)
        .leftOuterJoin(Address.class, Person::getId, Address::getPersonId)
        .where((i, s) -> s != address2)
        .toList());

    assertThat(result).containsExactly(Pair.of(person1, address1), Pair.of(person2, null));
  }

  private void whenQueried(Function<ObjectSet, List<Pair<Person, Address>>> func) {
    this.result = func.apply(ObjectSet.of(Person.class, List.of(person1, person2))
        .with(Address.class, List.of(address1, address2)));
  }
}
