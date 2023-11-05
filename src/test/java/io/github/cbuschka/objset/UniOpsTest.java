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
public class UniOpsTest {
  @Mock
  private Person person1;
  @Mock
  private Person person2;

  private List<Person> result;

  @Test
  void allOfMultipleEntities() {
    givenAreTwoPersons();

    whenQueried((objSet) -> objSet.selectFrom(Person.class)
        .toList());

    thenResultContainsAllPersons();
  }

  private void thenResultContainsAllPersons() {
    assertThat(result).containsExactly(person1, person2);
  }

  @Test
  void filteredOfMultipleEntity() {
    givenAreTwoPersons();

    whenQueried((objSet) -> objSet.selectFrom(Person.class)
        .where(i -> i.getName().equals("john"))
        .toList());

    thenResultContainsPerson2Only();
  }

  private void thenResultContainsPerson2Only() {
    assertThat(result).containsExactly(person2);
  }

  private void whenQueried(Function<ObjectSet, List<Person>> func) {
    this.result = func.apply(ObjectSet.of(Person.class, List.of(person1, person2)));
  }

  private void givenAreTwoPersons() {
    when(person1.getName()).thenReturn("frank");
    when(person2.getName()).thenReturn("john");
  }
}
