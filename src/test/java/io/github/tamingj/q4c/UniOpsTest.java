package io.github.tamingj.q4c;

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

    whenQueried((persons) -> Queries.selectFrom(persons)
        .toList());

    thenResultContainsAllPersons();
  }

  @Test
  void supportsDatasourceViaSupplier() {
    givenAreTwoPersons();

    whenQueried((persons) -> Queries.selectFrom(() -> persons)
        .toList());

    thenResultContainsAllPersons();
  }

  private void thenResultContainsAllPersons() {
    assertThat(result).containsExactly(person1, person2);
  }

  @Test
  void filteredOfMultipleEntity() {
    givenAreTwoPersons();

    whenQueried((persons) -> Queries.selectFrom(persons)
        .where(person -> person.getName().equals("john"))
        .toList());

    thenResultContainsPerson2Only();
  }

  private void thenResultContainsPerson2Only() {
    assertThat(result).containsExactly(person2);
  }

  private void whenQueried(Function<List<Person>, List<Person>> func) {
    this.result = func.apply(List.of(person1, person2));
  }

  private void givenAreTwoPersons() {
    when(person1.getName()).thenReturn("frank");
    when(person2.getName()).thenReturn("john");
  }
}
